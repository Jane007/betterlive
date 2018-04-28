package com.kingleadsw.betterlive.controller.app.article;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.AgentAccountManager;
import com.kingleadsw.betterlive.biz.ArticlePeriodicalManager;
import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.biz.SpecialVideoTypeManager;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.PicUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ArticlePeriodicalVo;
import com.kingleadsw.betterlive.vo.CustomerAgentVo;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;

/**
 * app1.2版本 专题（发现）
 */
@Controller
@RequestMapping("/app/discovery")
public class AppDiscoveryController  extends AbstractWebController{
protected Logger logger = Logger.getLogger(AppDiscoveryController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private ArticlePeriodicalManager articlePeriodicalManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private CustomerFansManager customerFansManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private SpecialVideoTypeManager specialVideoTypeManager;
	@Autowired
	private CustomerAgentManager customerAgentManager;
	@Autowired
	private AgentAccountManager agentAccountManager;
	
	/**
	 * 查询动态（文章）列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryDynamicList")
	@ResponseBody
	public Map<String, Object> queryDynamicList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		int customerId = 0;
		
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}

		pd.put("status", 1);
		pd.put("customerId", customerId);
		pd.put("articleFrom", 1);
		List<SpecialArticleVo> list = specialArticleManager.queryCircleArticleListPage(pd);
		
		if(list == null){
			list = new ArrayList<SpecialArticleVo>();
		}
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	/**
	 * 动态详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryDynamicDetail")
	@ResponseBody
	public Map<String, Object> queryDynamicDetail(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("业务主键为空");
		}
		
		int customerId = 0;
		
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		PageData specialParams = new PageData();
		specialParams.put("articleId", pd.getString("articleId"));
		specialParams.put("customerId", customerId);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryCircleOne(specialParams);
		if(specialArticleVo.getStatus() != 1){ //未审核通过的文章只能作者自己查看
			if(customerId == 0 || customerId != specialArticleVo.getCustomerId()){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("您访问的文章不存在");
			}
		}
		CustomerFansVo concernedVo = new CustomerFansVo();
		if(specialArticleVo.getCustomerId() > 0 && customerId > 0){
			pd.clear();
			pd.put("customerId", customerId);
			pd.put("concernedId", specialArticleVo.getCustomerId());
			concernedVo = this.customerFansManager.queryOne(pd);
			if(concernedVo == null){
				concernedVo = new CustomerFansVo();	
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("specialArticleVo", specialArticleVo);
		map.put("concernedVo", concernedVo);
		map.put("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 精选（文章）分类专区
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/selectedMainArea")
	@ResponseBody
	public Map<String, Object> selectedMainArea(HttpServletRequest req, HttpServletResponse resp){
		PageData pd = new PageData();
		pd.put("status", 1);
		List<SpecialArticleTypeVo> list = this.specialArticleTypeManager.queryList(pd);
		if(list == null){
			list = new ArrayList<SpecialArticleTypeVo>();
		}
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	/**
	 * 查询精选（文章）列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySelectedList")
	@ResponseBody
	public Map<String, Object> querySelectedList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		try {
			int customerId = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer != null){
					customerId = customer.getCustomer_id();
				}
			}

			pd.put("customerId", customerId);
			List<ArticlePeriodicalVo> aps = this.articlePeriodicalManager.queryByArticleListPage(pd);
			
			PageView pv = (PageView) pd.get("pageView");
			Map<String, Object> map = CallBackConstant.SUCCESS.callback(aps);
			map.put("pageCount", pv.getPageCount());
			return map;
		} catch (Exception e) {
			logger.error("app/discovery/querySelectedList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 精选文章详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/querySelectedDetail")
	@ResponseBody
	public Map<String, Object> querySelectedDetail(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			return CallBackConstant.FAILED.callbackError("业务主键ID为空");
		}
		
		int customerId = 0;
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		PageData specialParams = new PageData();
		specialParams.put("articleId", pd.getString("articleId"));
		specialParams.put("customerId", customerId);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(specialParams);
		
		CustomerFansVo concernedVo = new CustomerFansVo();
		if(specialArticleVo.getCustomerId() > 0 && customerId > 0){
			pd.clear();
			pd.put("customerId", customerId);
			pd.put("concernedId", specialArticleVo.getCustomerId());
			concernedVo = this.customerFansManager.queryOne(pd);
			if(concernedVo == null){
				concernedVo = new CustomerFansVo();	
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		if(customerId>0){
			pd.clear();
			pd.put("customerId", customerId);
			pd.put("status", 0);
			CustomerAgentVo cavo = customerAgentManager.queryOne(pd);
			if (cavo != null) {// 有代理账户就拿代理账户的code
				code = cavo.getAgentCode();
				map.put("source", code);
			}
//			else{//没有代销账户就去创建
//				pd.clear();
//				code = AgentUtil.createAgentCode();
//				pd.put("agentCode", code);
//				CustomerAgentVo cvAgentVo = customerAgentManager.queryOne(pd);
//				if(cvAgentVo!=null){//如果存在就再生成一次code
//					 code = AgentUtil.createAgentCode();
//				}
				//如果有代理账户就放进去，没有就新建
//				pd.put("customerId", customerId);
//				pd.put("code", code);
//				Map<String, Object> savemap = agentAccountManager.saveAgentAccount(pd);
//				if(savemap.get("code").toString().equals("1010")){
//					map.put("source", code);
//				}
//			}
			
		}
		map.put("specialArticleVo", specialArticleVo);
		map.put("concernedVo", concernedVo);
		map.put("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 每日推荐
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryHomeArticle")
	@ResponseBody
	public Map<String, Object> queryHomeArticle(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		try {
			int customerId = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer != null){
					customerId = customer.getCustomer_id();
				}
			}

			pd.put("customerId", customerId);
			pd.put("status", 1);
			pd.put("recommendFlag", 1);
			SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(pd);
			Map<String, Object> map = CallBackConstant.SUCCESS.callback(specialArticleVo);
			map.put("productLabel", "每日推荐");
			return map;
		} catch (Exception e) {
			logger.error("/app/discovery/queryHomeArticle --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 查询视频列表信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryVideoList")
	@ResponseBody
	public Map<String, Object> queryVideoList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		try {
			int customerId = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer != null){
					customerId = customer.getCustomer_id();
				}
			}
			
			pd.put("customerId", customerId);
			List<SpecialVideoTypeVo> list = specialVideoTypeManager.queryVideoTypeListPage(pd);
			
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/discovery/queryVideoList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 文章详情相关产品
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryLinkPros")
	@ResponseBody
	public Map<String, Object> queryLinkPros(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			return CallBackConstant.FAILED.callbackError("业务主键ID为空");
		}
		
		PageData specialParams = new PageData();
		specialParams.put("articleId", pd.getString("articleId"));
		List<ProductVo> pros = productManager.queryListByArticle(specialParams);
		if(pros == null){
			pros = new ArrayList<ProductVo>();
		}
		
		return CallBackConstant.SUCCESS.callback(pros);
	}
	
	/**
	 * 文章详情更多动态
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryLinkArticles")
	@ResponseBody
	public Map<String, Object> queryLinkArticles(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			return CallBackConstant.FAILED.callbackError("业务主键ID为空");
		}
		
		int customerId = 0;
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		PageData specialParams = new PageData();
//		specialParams.put("articleId", pd.getString("articleId"));
		specialParams.put("customerId", customerId);
		specialParams.put("status", 1);
		specialParams.put("removeId", pd.getInteger("articleId"));
		specialParams.put("recommendMore", 1);
		List<SpecialArticleVo> linkArticles = specialArticleManager.querySpecialArticleListPage(specialParams);
		if(linkArticles == null){
			linkArticles = new ArrayList<SpecialArticleVo>();
		}
		
		return CallBackConstant.SUCCESS.callback(linkArticles);
	}
	
	/**
	 * 动态详情文章图片
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryLinkPictures")
	@ResponseBody
	public Map<String, Object> queryLinkPictures(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			return CallBackConstant.FAILED.callbackError("业务主键ID为空");
		}
		
		pd.put("objectId", pd.getString("articleId"));
		pd.put("pictureType", 5);
		List<PicturesVo> pictures = this.picturesManager.queryList(pd);
		if(pictures == null){
			pictures = new ArrayList<PicturesVo>();
		}
		
		return CallBackConstant.SUCCESS.callback(pictures);
	}
	
	/**
	 * 发动态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/publishDynamic")
	@ResponseBody
	public Map<String, Object> publishDynamic(HttpServletRequest request,HttpServletResponse response,Model model) {
		try {
			PageData pd = this.getPageData();
			String token = pd.getString("token");     	//文章标题
			String articleTitle=pd.getString("articleTitle");     	//文章标题
			String content=pd.getString("content");     		//文章内容
		    String pictureArray = pd.getString("pictureArray");	//图片
			
			if(StringUtil.isNull(token)){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			if(StringUtil.isNull(articleTitle) || articleTitle.length() <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请添加标题");
			}
			if(articleTitle.length() > 30){
				return CallBackConstant.PARAMETER_ERROR.callbackError("标题长度不能超过30");
			}
			if(StringUtil.isNull(content) || content.length() <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请写下心得");
			}
			
			if(StringUtil.isNull(pictureArray) || pictureArray.split(",").length <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请添加照片");
			}
			
			CustomerVo customer = this.customerManager.queryCustomerByToken(token);
			
			int failCount = 0;
			String[] pics = pictureArray.split(",");
			List<PicturesVo> pictures = new ArrayList<PicturesVo>();
			for (int i = 0; i < pics.length; i++) {
				String picLocal = pics[i];
	 			String prefix = picLocal.substring(picLocal.lastIndexOf("."), picLocal.length()); // 文件后缀名
				switch (prefix.toLowerCase()) {
				case ".jpg":
					break;
				case ".peg":
					break;
				case ".png":
					break;
				case ".gif":
					break;
				default:
					failCount++;
					break;
				}
	 			if(failCount == 0){
	 				BufferedImage buffImg = PicUtil.getImg(picLocal);
	 				PicturesVo pictureVo = new PicturesVo();
	 				pictureVo.setOriginal_img(picLocal);
	 				pictureVo.setSmall_img(picLocal);
	 				pictureVo.setStatus(1);
	 				pictureVo.setPicture_type(5);
	 				pictureVo.setPicHeight(buffImg.getHeight());
	 				pictureVo.setPicWidth(buffImg.getWidth());
	 				pictures.add(pictureVo);
	 			}
			}
			if(failCount > 0){
				return CallBackConstant.FAILED.callbackError("请上传正确类型的图片");
			}
			
			SpecialArticleVo vo = new SpecialArticleVo();
			vo.setArticleTitle(articleTitle);
			vo.setContent(content);
			vo.setCustomerId(customer.getCustomer_id());
			vo.setArticleFrom(1);
			vo.setStatus(3);
			int count = this.specialArticleManager.insert(vo);
			if(count > 0){
				for (PicturesVo picVo : pictures) {
					picVo.setObject_id(vo.getArticleId());
				}
				picturesManager.insertBatchFromList(pictures);
				vo.setArticleCover(pictures.get(0).getOriginal_img());
				vo.setHomeCover(pictures.get(0).getOriginal_img());
				specialArticleManager.updateByPrimaryKeySelective(vo);
				
				//此处做添加图片逻辑处理
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/app/discovery/publishDynamic --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
		/**
		 * 圈子的顶部背景图
		 * @param  request
		 * @param  response
		 */
		@RequestMapping(value = "/queryDynamicBanner")
		@ResponseBody
		public Map<String, Object> queryDynamicBanner(HttpServletRequest request,HttpServletResponse response) {
			return CallBackConstant.SUCCESS.callback("http://images.hlife.shop/quanzitop20180208.png");
		}
	
	/**
	 * 发现好友列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryFindFriendList")
	@ResponseBody
	public Map<String, Object> queryFindFriendList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		int customerId = 0;
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
	
		try {
			pd.put("customerId", customerId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryFindFriendListPage(pd);
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}else{
				for (CustomerFansVo customerFansVo : list) {//显示发现好友三篇文章
					PageData pdt = new PageData();
					pdt.put("customerId", customerFansVo.getCustomerId());
					List<SpecialArticleVo> articleVos = specialArticleManager.queryTopThreeArticle(pdt);
					customerFansVo.setArticleVos(articleVos);
				}
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryFindFriendList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
