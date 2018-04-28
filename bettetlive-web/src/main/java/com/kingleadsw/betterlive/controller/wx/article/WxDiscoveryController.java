package com.kingleadsw.betterlive.controller.wx.article;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.AgentAccountManager;
import com.kingleadsw.betterlive.biz.ArticlePeriodicalManager;
import com.kingleadsw.betterlive.biz.ArticleRelationProManager;
import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SpecialVideoTypeManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
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
import com.kingleadsw.betterlive.vo.SpecialCommentVo;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * wx1.2版本 专题（发现）
 */
@Controller
@RequestMapping("/weixin/discovery")
public class WxDiscoveryController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(WxDiscoveryController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private ArticlePeriodicalManager articlePeriodicalManager;
	@Autowired
	private ArticleRelationProManager articleRelationProManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private CustomerFansManager customerFansManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private SpecialCommentManager specialCommentManager;
	@Autowired
	private SpecialVideoTypeManager specialVideoTypeManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private AgentAccountManager agentAccountManager;
	@Autowired
	private CustomerAgentManager customerAgentManager;
	@Autowired
	private CustomerIntegralRecordManager customerIntegralRecordManager;
	
	/**
	 * 去动态（文章）首页列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toDynamic")
	public ModelAndView toDynamic(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_dynamic");
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		mv.addObject("customerId", customerId);
		return mv;
	}
	
	/**
	 * 发动态的页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPublishDynamic")
	public ModelAndView toPublishDynamic(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/discovery/wx_Publish");
		PageData pd = this.getPageData();
		CustomerVo myCustVo = Constants.getCustomer(request);
		if(myCustVo == null || myCustVo.getCustomer_id() <= 0){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		try {
			md.addObject("myCustId", myCustVo.getCustomer_id());
			md.addObject("backUrl",pd.getString("backUrl"));
			md.addObject("backType", pd.getString("backType"));
		} catch (Exception e) {
			logger.error("/weixin/discovery/toPublishDynamic --error", e);
		}
		return md;
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
		CustomerVo myCustVo = Constants.getCustomer(request);
		if(myCustVo == null || myCustVo.getCustomer_id() <= 0){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		long fileSize = 2 * 1024 * 1024;	//2M
		try {
			int checkGif = 0;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			String articleTitle=multipartRequest.getParameter("articleTitle");     	//文章标题
			String content=multipartRequest.getParameter("content");     		//文章内容
			List<MultipartFile> files = multipartRequest.getFiles("atcImgs");
			if(StringUtil.isNull(articleTitle) || articleTitle.length() <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请添加标题");
			}
			if(articleTitle.length() > 30){
				return CallBackConstant.PARAMETER_ERROR.callbackError("标题长度不能超过30");
			}
			if(StringUtil.isNull(content) || content.length() <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请写下心得");
			}
			if(files.size() <= 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请添加照片");
			}
			
			int failCount = 0;
			String[] checkPrefix = { ".jpg", ".jpeg", ".png", ".gif"};
			for (int i = 0; i < files.size(); i++) {
				MultipartFile myFile = files.get(i);
				String fileName = myFile.getOriginalFilename(); // 原始文件名
	 			String prefix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); // 文件后缀名
	 			if (!Arrays.asList(checkPrefix).contains(prefix)) {
					failCount++;
				}
	 			
	 			if(prefix.equals(".gif") && myFile.getSize() > fileSize){
	 				checkGif++;
	 			}
			}
			if(checkGif > 0){
				return CallBackConstant.FAILED.callbackError("gif动图不能超过2M大小");
			}
			if(failCount > 0){
				return CallBackConstant.FAILED.callbackError("图片只能是jpg、jpeg、png、gif格式");
			}
			int count = savePic(files, articleTitle, content, myCustVo.getCustomer_id());
			if(count == 1){
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callbackError("发布失败");
		} catch (Exception e) {
			logger.error("/weixin/discovery/publishDynamic --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	public int savePic(List<MultipartFile> files, String articleTitle, String content, int customerId){
		long fileSize = 204800;	//200KB以内不需要压缩
//		Thread messageThread = new Thread() {
//			@Override
//			public void run() {
				
				try {
					int result = 1;
					String homeFilePath = "discoveryarticle/";
					List<PicturesVo> pictures = new ArrayList<PicturesVo>();
					for (int i = 0; i < files.size(); i++) {
						MultipartFile myFile = files.get(i);
		 				String imgLocal = ImageUpload.uploadTempFile(myFile, homeFilePath);
		 				BufferedImage buffImg = PicUtil.getImg(imgLocal);
		 				int height = buffImg.getHeight();
		 				int width = buffImg.getWidth();
		 				String fileName = myFile.getOriginalFilename(); // 原始文件名
		 				String prefix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); // 文件后缀名
		 				if (myFile.getSize() > fileSize && !prefix.equals(".gif")) {
		 					Map<String, Object> mm = ImageUpload.saveImageByQuality(buffImg,imgLocal, 0.6f, homeFilePath, myFile.getSize());
		 					if(String.valueOf(mm.get("flag")).equals("0")){
		 						result = 0;
		 						return result;
		 					}
		 					imgLocal = mm.get("saveLocal")+"";
		 					height = (int)mm.get("height");
		 					width = (int)mm.get("width");
						}
		 				
		 				PicturesVo pictureVo = new PicturesVo();
		 				pictureVo.setOriginal_img(imgLocal);
		 				pictureVo.setSmall_img(imgLocal);
		 				pictureVo.setStatus(1);
		 				pictureVo.setPicture_type(5);
		 				pictureVo.setPicHeight(height);
		 				pictureVo.setPicWidth(width);
		 				pictures.add(pictureVo);
					}
					SpecialArticleVo vo = new SpecialArticleVo();
					vo.setArticleTitle(articleTitle);
					vo.setContent(content);
					vo.setCustomerId(customerId);
					vo.setArticleFrom(1);
					vo.setStatus(3);
					int count = specialArticleManager.insert(vo);
					if(count > 0){
						for (PicturesVo picturesVo : pictures) {
							picturesVo.setObject_id(vo.getArticleId());
						}
						picturesManager.insertBatchFromList(pictures);
						vo.setArticleCover(pictures.get(0).getOriginal_img());
						vo.setHomeCover(pictures.get(0).getOriginal_img());
						specialArticleManager.updateByPrimaryKeySelective(vo);
					}
					return result;
				} catch (Exception e) {
					logger.error("/weixin/discovery/savePic ---error", e);
					return 0;
				}
//			}
//		};
//		messageThread.start();
	}
	
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
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}

		pd.put("status", 1);
		pd.put("customerId", customerId);
		pd.put("articleFrom", 1);
		List<SpecialArticleVo> list = specialArticleManager.queryCircleArticleListPage(pd);
		
		if(list == null){
			list = new ArrayList<SpecialArticleVo>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	/**
	 * 动态详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toDynamicDetail")
	public ModelAndView toDynamicDetail(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_dynamic_detail");
		PageData pd = this.getPageData();
		
		String backUrl = pd.getString("backUrl");
		if(StringUtil.isNull(pd.getString("articleId"))){
			mv.addObject("tipsTitle", "文章提示");
			mv.addObject("tipsContent", "您访问的文章内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		int articleId = pd.getInteger("articleId");
		PageData specialParams = new PageData();
		specialParams.put("articleId", articleId);
		specialParams.put("customerId", customerId);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(specialParams);
		if(specialArticleVo.getStatus() != 1){ //未审核通过的文章只能作者自己查看
			if(customerId == 0 || customerId != specialArticleVo.getCustomerId()){
				mv.addObject("tipsTitle", "文章提示");
				mv.addObject("tipsContent", "您访问的文章内容不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
		}
		
		CustomerVo authorVo = this.customerManager.selectByPrimaryKey(specialArticleVo.getCustomerId());
		
		
		List<ProductVo> pros = productManager.queryListByArticle(specialParams);
		if(pros == null){
			pros = new ArrayList<ProductVo>();
		}
		
		specialParams.clear();
		specialParams.put("recommendMore", 1);
		specialParams.put("status", 1);
		specialParams.put("removeId", articleId);
		specialParams.put("customerId", customerId);
		List<SpecialArticleVo> linkArticles = specialArticleManager.querySpecialArticleListPage(specialParams);
		if(linkArticles == null){
			linkArticles = new ArrayList<SpecialArticleVo>();
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
		
		specialParams.clear();
		specialParams.put("objectId", articleId);
		specialParams.put("pictureType", 5);
		List<PicturesVo> pictures = this.picturesManager.queryList(specialParams);
		if(pictures == null){
			pictures = new ArrayList<PicturesVo>();
		}
		
		model.addAttribute("specialArticleVo", specialArticleVo);
		model.addAttribute("customerId", customerId);
		model.addAttribute("authorVo", authorVo);
		model.addAttribute("linkPros", pros);
		model.addAttribute("linkArticles", linkArticles);
		model.addAttribute("concernedVo", concernedVo);
		model.addAttribute("pictures", pictures);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return mv;
	}
	
	/**
	 * 去精选（文章）首页列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toSelected")
	public ModelAndView toSelected(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_selected");
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		mv.addObject("customerId", customerId);
		return mv;
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
	 * 去精选（文章）专区列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toSelectedArea")
	public ModelAndView toSelectedArea(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_selected_area");
		try {
			int customerId = 0;
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getString("typeId"))){
				mv.addObject("tipsTitle", "文章提示");
				mv.addObject("tipsContent", "您访问的文章内容不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			CustomerVo customer=Constants.getCustomer(req);
			if(customer != null && customer.getCustomer_id() != null){
				customerId = customer.getCustomer_id();
			}
			SpecialArticleTypeVo typeVo = this.specialArticleTypeManager.selectByPrimaryKey(pd.getInteger("typeId"));
			if(typeVo == null || typeVo.getTypeId() <= 0){
				mv.addObject("tipsTitle", "文章提示");
				mv.addObject("tipsContent", "您访问的文章内容不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			mv.addObject("typeId", pd.getString("typeId"));
			mv.addObject("customerId", customerId);
			mv.addObject("typeVo", typeVo);
			return mv;
		} catch (Exception e) {
			logger.error("/weixin/discovery/toSelectedArea", e);
			mv.addObject("tipsTitle", "文章提示");
			mv.addObject("tipsContent", "您访问的文章内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
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
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}

		pd.put("customerId", customerId);
		List<ArticlePeriodicalVo> aps = this.articlePeriodicalManager.queryByArticleListPage(pd);
		
		Map<String, Object> map = CallBackConstant.SUCCESS.callback(aps);
		map.put("pageInfo", pd.get("pageView"));
		return map;
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
			
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}

			pd.put("customerId", customerId);
			pd.put("status", 1);
			pd.put("recommendFlag", 1);
			SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(pd);
			return CallBackConstant.SUCCESS.callback(specialArticleVo);
		} catch (Exception e) {
			logger.error("/weixin/discovery/queryHomeArticle --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 去精选（文章）专区列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toSelectedRetrispect")
	public ModelAndView toSelectedRetrispect(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_selected_retrospect");
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null){
			customerId = customer.getCustomer_id();
		}
		mv.addObject("customerId", customerId);
		return mv;
	}
	
	/**
	 * 精选文章详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toSelectedDetail")
	public ModelAndView toSelectedDetail(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_selected_detail");
		PageData pd = this.getPageData();
		String backUrl = "";
		if(StringUtil.isNotNull(pd.getString("backUrl"))){
			backUrl=pd.getString("backUrl");
		}
		if(StringUtil.isNull(pd.getString("articleId"))){
			mv.addObject("tipsTitle", "文章提示");
			mv.addObject("tipsContent", "您访问的文章内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null){
			customerId = customer.getCustomer_id();
		}
		
		PageData specialParams = new PageData();
		specialParams.put("articleId", pd.getString("articleId"));
		specialParams.put("customerId", customerId);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(specialParams);
		List<ProductVo> pros = productManager.queryListByArticle(specialParams);
		if(pros == null){
			pros = new ArrayList<ProductVo>();
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
		
		String code = "";
		if(customerId>0){
			pd.clear();
			pd.put("customerId", customerId);
			pd.put("status", 0);
			CustomerAgentVo cavo = customerAgentManager.queryOne(pd);
			if (cavo != null) {// 有代理账户就拿代理账户的code
				code = cavo.getAgentCode();
				model.addAttribute("source", code);
			}
//			else{//没有代销账户就去创建
//				pd.clear();
//				code = AgentUtil.createAgentCode();
//				pd.put("agentCode", code);
//				CustomerAgentVo cvAgentVo = customerAgentManager.queryOne(pd);
//				if(cvAgentVo!=null){//如果存在就再生成一次code
//					 code = AgentUtil.createAgentCode();
//				}
//				//如果有代理账户就放进去，没有就新建
//				pd.put("customerId", customerId);
//				pd.put("code", code);
//				Map<String, Object> map = agentAccountManager.saveAgentAccount(pd);
//				if(map.get("code").toString().equals("1010")){
//					model.addAttribute("source", code);
//				}
//			}
			
		}
		model.addAttribute("specialArticleVo", specialArticleVo);
		model.addAttribute("customerId", customerId);
		model.addAttribute("linkPros", pros);
		model.addAttribute("concernedVo", concernedVo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return mv;
	}
	
	@RequestMapping(value="/toHelp")
	public ModelAndView toHelp(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_selected_help");
		mv.addObject("backUrl", req.getParameter("backUrl"));
		return mv;
	}
	
	/**
	 * 去视频（文章）首页列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toVideo")
	public ModelAndView toVideo(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_video");
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		mv.addObject("customerId", customerId);
		mv.addObject("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return mv;
	}
	
	/**
	 * 查询视频列表信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryVideoList")
	@ResponseBody
	public Map<String, Object> queryVideoList(HttpServletRequest request,HttpServletResponse response) {
		try {
			PageData pd = this.getPageData();
			int customerId = 0;
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null && customer.getCustomer_id() != null){
				customerId = customer.getCustomer_id();
			}
			
			pd.put("customerId", customerId);
			List<SpecialVideoTypeVo> list = specialVideoTypeManager.queryVideoTypeListPage(pd);
			Map<String, Object> map = CallBackConstant.SUCCESS.callback(list);
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/discovery/queryVideoList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 去视频分类列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toVideoArea")
	public ModelAndView toVideoArea(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_video_area");
		int customerId = 0;
		PageData pd = this.getPageData();
		if(pd.getInteger("typeId") == null){
			mv.addObject("tipsTitle", "视频信息提示");
			mv.addObject("tipsContent", "您访问的视频列表内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		pd.put("status", 1);
		SpecialVideoTypeVo svtVo = this.specialVideoTypeManager.queryOne(pd);
		if(svtVo == null){
			mv.addObject("tipsTitle", "视频信息提示");
			mv.addObject("tipsContent", "您访问的视频列表内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		mv.addObject("customerId", customerId);
		mv.addObject("svtVo", svtVo);
		mv.addObject("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return mv;
	}
	
	/**
	 *  文章评论详情
	 */
	@RequestMapping(value = "/toCommentDetail")
    public ModelAndView toCommentDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();
    	ModelAndView mv = new ModelAndView("weixin/discovery/wx_article_comment_detail");
    	
		if(StringUtil.isNull(pd.getString("articleId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
	
		if(StringUtil.isNull(pd.getString("commentPraiseType"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		if(StringUtil.isNull(pd.getString("commentId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		try {
			pd.put("currentId", customerId);
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				mv.addObject("tipsTitle", "评论信息提示");
				mv.addObject("tipsContent", "您访问的评论内容不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			mv.addObject("commentVo", commentVo);
			mv.addObject("customerId", customerId);
			mv.addObject("backUrl", pd.getString("backUrl"));
			return mv;
		} catch (Exception e) {
			logger.info("/weixin/discovery/toCommentDetail --error", e);
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
    }
	
	/**
	 * 去评论
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toAddComment")
	public ModelAndView toAddComment(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_article_comment");
		int customerId = 0;
		
		CustomerVo customerVo=Constants.getCustomer(req);
		if(customerVo == null || customerVo.getCustomer_id() <= 0){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			mv.addObject("tipsTitle", "评论提示");
			mv.addObject("tipsContent", "您访问的评论页不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isNotNull(pd.getString("commentId"))){
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				commentVo=new SpecialCommentVo();
			}
			mv.addObject("commentVo", commentVo);
		}
		mv.addObject("customerId", customerId);
		mv.addObject("articleId", pd.getString("articleId"));
		return mv;
	}
	
	/**
	 * 如何写一篇优秀的好文章
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toArticleRight")
	public ModelAndView toArticleRight(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_Experience");
		return mv;
	}
	
	/**
	 * 如何写一篇优秀的好文章
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toFindFriends")
	public ModelAndView toFindFriends(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_find_friends");
		int customerId = 0;
		CustomerVo customerVo=Constants.getCustomer(req);
		if(customerVo != null && customerVo.getCustomer_id() > 0){
			customerId = customerVo.getCustomer_id();
		}
		mv.addObject("myCustId", customerId);
		return mv;
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
		
		int myCustId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() > 0){
			myCustId = customer.getCustomer_id();
		}
	
		try {
			pd.put("customerId", myCustId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryFindFriendListPage(pd);
			if(list == null || list.size()<=0){
				list = new ArrayList<CustomerFansVo>();
			}else{
				for (CustomerFansVo customerFansVo : list) {//显示发现好友三篇文章
					PageData pdt = new PageData();
					pdt.put("customerId", customerFansVo.getCustomerId());
					List<SpecialArticleVo> articleVos = specialArticleManager.queryTopThreeArticle(pdt);
					customerFansVo.setArticleVos(articleVos);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryFindFriendList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	@RequestMapping(value="/toTorialComment")
	public ModelAndView toTorialComment(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/discovery/wx_video_detail");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		PageData specialParams = new PageData();
		specialParams.put("specialId", pd.getString("specialId"));
		specialParams.put("customerId", customerId);
		specialParams.put("collectionType", 3);
		specialParams.put("shareType", 3);
		specialParams.put("praiseType", 2);
		specialParams.put("specialType", 4);
		SpecialVo specialVo = this.specialMananger.queryOneByTutorial(specialParams);
		String maodian = pd.getString("maodian");
		model.addAttribute("maodian", maodian);
		model.addAttribute("specialVo", specialVo);
		model.addAttribute("customerId", customerId);
		model.addAttribute("backUrl", pd.getString("backUrl"));
		return mv;
	}
}
