package com.kingleadsw.betterlive.controller.wx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.BannerInfoManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OperationManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.PropIndexManager;
import com.kingleadsw.betterlive.biz.SearchRecordManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.wx.SignUtil;
import com.kingleadsw.betterlive.util.wx.service.WeixinService;
import com.kingleadsw.betterlive.vo.BannerInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.LabelVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.PropIndexVo;
import com.kingleadsw.betterlive.vo.SearchRecordVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 挥货首页控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/weixin")
public class WxIndexController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxIndexController.class);

	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private BannerInfoManager bannerInfoManager;
	@Autowired
	private WeixinService weixinService;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private LabelManager labelManager;
	@Autowired
	private SearchRecordManager searchRecordManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private OperationManager operationManager;
	@Autowired
	private PropIndexManager propIndexManager;
	
	
	@RequestMapping(value = "/index")
	public ModelAndView doindex(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		try {
			List<BannerInfoVo> bannerInfos = bannerInfoManager.queryAllBannersList(pd);
			if(bannerInfos==null|| bannerInfos.size()<=0){
				bannerInfos = new ArrayList<BannerInfoVo>();
			}
			model.addAttribute("banners", bannerInfos);
			
			int msgCount = 0; //未读消息默认为0
			
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null&&customer.getCustomer_id()!=null){
				//未读消息
				pd.put("customerId", customer.getCustomer_id());
				msgCount = messageManager.selectCountByUnread(pd);
			}
			
			model.addAttribute("unreadCount", msgCount);
		} catch (Exception e) {
			logger.error("/weixin/index----error", e);
		}
		
		return new ModelAndView("weixin/wx_index");
	}
	
	@RequestMapping(value = "/getCoupons")
	@ResponseBody
	public Map<String, Object> getCoupons(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerVo customer=Constants.getCustomer(request);
		int hasExpiring = 0;
		PageData pd = this.getPageData();
		PageData couponParam = new PageData();
		try {
			if(customer != null && customer.getCustomer_id() != null){
				//即将过期的优惠券/单品红包
				couponParam.put("customerId", customer.getCustomer_id());
				//有可领取的优惠券和红包
				if(StringUtil.isNotNull(pd.getString("productId"))){
					couponParam.put("productId", pd.getInteger("productId"));
				}
				couponParam.put("homeFlag", 1);
				//新版首页显示没购买过商品的红包
				String couponId = WebConstant.NEW_USER_COUPON;
				couponParam.put("couponIds", couponId);
				couponParam.put("couponType", 2);
				List<CouponManagerVo> newUserCoupon = this.couponManagerManager.queryNewUserCouponList(couponParam);
				//productId 为空说明是首页展示红包否则就是商品详展示红包
				if(newUserCoupon!=null&&newUserCoupon.size()>0&&StringUtil.isNull(pd.getString("productId"))){
					map.put("couponManagers", newUserCoupon);
					//有可领取的优惠券
					map.put("hasExpiring", 6);
					map.put("newUserCoupon", "1");//新人
					return map;
				}
				
				
				List<SingleCouponVo> singleCoupons = this.singleCouponManager.queryEffectiveCouponListNew(couponParam);
				
				List<CouponManagerVo> couponManagers = this.couponManagerManager.queryEffectiveCouponList(couponParam);
				
				//把新手红包展示在商品详情里面
				if(newUserCoupon!=null&&newUserCoupon.size()>0){
					if(couponManagers!=null&&couponManagers.size()>0){
						couponManagers.addAll(newUserCoupon);
					}else{
						couponManagers=newUserCoupon;
					}
						
				}
				
				if(singleCoupons != null && couponManagers != null 
						&& singleCoupons.size() > 0 && couponManagers.size() > 0){
					map.put("singleCoupons", singleCoupons);
					map.put("couponManagers", couponManagers);
					hasExpiring = 4; //有可领取的优惠券和单品红包
				}else if (singleCoupons != null && singleCoupons.size() > 0
						&& (couponManagers == null || couponManagers.size() <= 0)){
					map.put("singleCoupons", singleCoupons);
					hasExpiring = 5; //有可领取的单品红包
				}else if ((singleCoupons == null || singleCoupons.size() <= 0) 
						&& couponManagers != null && couponManagers.size() > 0){
					map.put("couponManagers", couponManagers);
					hasExpiring = 6; //有可领取的优惠券
				}
			}
			map.put("newUserCoupon", "0");//老人
			map.put("hasExpiring", hasExpiring);
		} catch (Exception e) {
			logger.error("/weixn/getCoupons -------error", e);
		}
		return map;
	}
	
	/**
	 * 首页专题弹窗活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySpecialAlertInfo")
	@ResponseBody
	public Map<String, Object> querySpecialAlertInfo(HttpServletRequest request){
		PageData pd = this.getPageData();
		try {
			pd.put("propType", 2);
			pd.put("status", 1);
			pd.put("specialStatus", 1);
			pd.put("specialType", 1);
			pd.put("checkTime", DateUtil.getCurrentTime());
			PropIndexVo propIndex = this.propIndexManager.queryOne(pd);
			if(propIndex == null){
				return CallBackConstant.DATA_NOT_FOUND.callback();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("specialId", propIndex.getObjId());
			map.put("linkUrl", propIndex.getLinkUrl());
			map.put("backGroundUrl", propIndex.getLinkImg());
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/weixn/getSpecialAlertInfo -------error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 首页下载占位符活动替换
	 * @param
	 * @return
	 * @author zhangjing 2018年4月11日 上午11:14:19
	 */
	@RequestMapping(value = "/queryPlaceholder")
	@ResponseBody
	public Map<String, Object> queryPlaceholder(HttpServletRequest request){
		PageData pd = this.getPageData();
		try {
			pd.put("status",1);
			pd.put("propType",1);
			pd.put("specialStatus", 1);
			pd.put("specialType", 1);
			pd.put("checkTime", DateUtil.getCurrentTime());
			PropIndexVo propIndex = this.propIndexManager.queryOne(pd);
			if(propIndex == null){
				return CallBackConstant.DATA_NOT_FOUND.callback();
			}
			return CallBackConstant.SUCCESS.callback(propIndex);
		} catch (Exception e) {
			logger.error("/weixn/queryPlaceholder -------error", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
	
	
	/**
	 * 首页每周新品/人气推荐
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryIndexPro")
	@ResponseBody
	public Map<String, Object> queryIndexPro(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		try {
			if(StringUtil.isNull(pd.getString("extensionType"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("产品参数异常");
			}
			
			int extensionType = pd.getInteger("extensionType");
			
			pd.put("status", 1);//banner正常状态
			List<ProductVo> productVos = new ArrayList<ProductVo>();
			if(extensionType == 1){
				productVos = this.productManager.queryIndexProduct(1,extensionType,1,pd,3, 1);//每周新品
			}else{
				productVos = this.productManager.queryIndexProduct(1,extensionType,1,pd,8, 1);//人气推荐列表
			}
			if(productVos == null){
				productVos = new ArrayList<ProductVo>();
			}
			return CallBackConstant.SUCCESS.callback(productVos);
		} catch (Exception e) {
			logger.error("/weixin/queryIndexPro", e);
			return CallBackConstant.FAILED.callback();
		}

	}
	
	/**
	 * 查询推荐首页的限量抢购列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryLimitByHome")
	@ResponseBody
	public Map<String, Object> queryLimitByHome(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = new PageData();
		pd.put("specialType", 2);
		pd.put("status", 1);
		pd.put("homeFlag", 1);
		pd.put("rowStart", 0);
		pd.put("pageSize", 4);
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		if(list ==null)
		{
			list = new ArrayList<SpecialVo>();
		}
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	/**
	 * 查询推荐首页的视频
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryVideoByHome")
	@ResponseBody
	public Map<String, Object> queryVideoByHome(HttpServletRequest request,HttpServletResponse response) {
		CustomerVo customerVo = Constants.getCustomer(request);
		int customerId = 0;
		if(customerVo != null && customerVo.getCustomer_id() != null){
			customerId = customerVo.getCustomer_id();
		}
		PageData pd = this.getPageData();
		
		pd.put("specialType", 4);
		pd.put("status", 1);
		pd.put("homeFlag", 1);
		pd.put("collectionType", 3);
		pd.put("shareType", 3);
		pd.put("praiseType", 2);
		pd.put("customerId", customerId);
		SpecialVo video = specialMananger.queryOneByTutorial(pd);
		if(video ==null)
		{
			video = new SpecialVo();
		}
		video.setProductLabel("每日视频");
		return CallBackConstant.SUCCESS.callback(video);
	}
	
	/**
	 * 查询限量、团购活动
	 * @param request
	 * @param response
	 * @param page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/querySpecialInfo")
	@ResponseBody
	public Map<String,Object> querySpecialInfo(HttpServletRequest request,HttpServletResponse response) {

		PageData pd = new PageData();
		pd.put("picture_type", 3);
		pd.put("status", 1);
		PicturesVo groupPic = picturesManager.queryOne(pd);
		if(groupPic == null){	//团购封面图
			groupPic = new PicturesVo();
		}
		
		pd.clear();
		pd.put("picture_type", 4);
		pd.put("status", 1);
		PicturesVo limitPic = picturesManager.queryOne(pd);
		if(limitPic == null){	//限量抢购封面图
			limitPic = new PicturesVo();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupPic", groupPic.getOriginal_img());
		map.put("limitPic", limitPic.getOriginal_img());
		
	    return CallBackConstant.SUCCESS.callback(map);
		
	}
	
	/**
	 * 查询推荐到首页的文章列表
	 */                     
	@RequestMapping(value="/queryRecomendArticles")
	@ResponseBody
	public Map<String,Object> queryRecomendArticles(HttpServletRequest req, HttpServletResponse resp){
		//美食推荐文章
		PageData sapd = new PageData();
		sapd.put("status", 1);
		sapd.put("homeFlag", 1);
		PageView pv = new PageView(10, 1);
		sapd.put("pageView", pv);
		List<SpecialArticleVo> articles = this.specialArticleManager.querySpecialArticleListPage(sapd);
		if(articles == null){
			articles = new ArrayList<SpecialArticleVo>();
		}
		
		return CallBackConstant.SUCCESS.callback(articles);
	}
	
	@RequestMapping(value = "/shareWeixin")
	@ResponseBody
	public void shareWeixin(HttpServletRequest request,HttpServletResponse response){
		JSONObject json=new JSONObject(); 
		String jsApiTicket = weixinService.getJsApiTicket();
		if (jsApiTicket == null) {
			return;
		}
		PageData pd = this.getPageData();
		String url = pd.getString("url");
		String share_url = String.format(url);
		Map<String, String> ticketMap = SignUtil.jsApiTicktSign(jsApiTicket, share_url);
		if (ticketMap != null) {
			ticketMap.put("appId", WebConstant.WX_APPID);
		}
		json.put("shareInfo", ticketMap);
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 每周新品列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/product/productList")
	public ModelAndView toProductList(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/goods/wx_product_list");
		model.addAttribute("extensionType", this.getPageData().getString("extensionType"));
		return modelAndView;
	}
	
	/**
	 * 首页查询跳转
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchProduct(HttpServletRequest request,HttpServletResponse response,Model model){
		PageData pd = this.getPageData();
//		model.addAttribute("productName", pd.get("productName"));
		ModelAndView modelAndView=new ModelAndView("weixin/wx_search");
		
		pd.put("labelType", 1); //1热门搜索
		pd.put("status", 0);
		List<LabelVo> labels = labelManager.queryListPage(pd);
		model.addAttribute("productLabels", labels);
		model.addAttribute("stype", pd.getString("stype"));
		return modelAndView;
		
	}
	
	/**
	 * 查询json数据搜索列表
	 * @param request
	 * @param response
	 * @param page
	 */
	@RequestMapping(value = "/searchProductAllJson")
	@ResponseBody
	public Map<String,Object> searchProductAllJson(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		List<ProductVo> list = new ArrayList<ProductVo>();
		String productName="";
		try {
			if(StringUtil.isNull(pd.getString("productName"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("参数不能为空");
			}
			
			productName=URLDecoder.decode(pd.getString("productName"),"utf-8").trim();
			pd.put("productName",productName);
			
			PageData labelParam = new PageData();
			labelParam.put("labelName", productName);
			labelParam.put("labelType", pd.getString("labelType"));
			List<LabelVo> labels = this.labelManager.queryListPage(labelParam);
			if(labels != null && labels.size() > 0){
				LabelVo labelVo = labels.get(0);
				labelVo.setSearchCount(labelVo.getSearchCount()+1);
				this.labelManager.updateByPrimaryKeySelective(labelVo);
				
				SearchRecordVo srVo = new SearchRecordVo();
				CustomerVo customer=Constants.getCustomer(request);
				if(customer != null && customer.getCustomer_id() != null){
					srVo.setCustomerId(customer.getCustomer_id());
				}
				srVo.setLabelId(labelVo.getLabelId());
				srVo.setLabelType(labelVo.getLabelType());
				searchRecordManager.insert(srVo);
				if (StringUtil.isNotNull(labelVo.getProductIds())) {
					pd.put("pro_ids", labelVo.getProductIds());
				}
			}
			pd.put("productStatus", 1);//状态 正常
			pd.put("isOnline", 1);
			list = productManager.queryProductListPage(pd);
			
			if(list == null){
				list = new ArrayList<ProductVo>();
			}else {
				for (ProductVo productVo : list) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", productVo.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					PageData activityMap = new PageData();
					activityMap.put("productId", productVo.getProduct_id());
					activityMap.put("proStatus", 1);
					activityMap.put("specStatus", 1);
					if(specialVo != null){
						activityMap.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
					if(proSpec == null){
						continue;
					}
					productVo.setPrice(proSpec.getSpec_price());
					if (proSpec.getDiscount_price() != null && proSpec.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
						productVo.setDiscountPrice(proSpec.getDiscount_price()+"");
					}
					
					int fakeSalesCopy = productVo.getFake_sales_copy();
					if(proSpec.getTotal_sales_copy() != null){
						productVo.setSalesVolume(proSpec.getTotal_sales_copy()+fakeSalesCopy);
					}else{
						productVo.setSalesVolume(fakeSalesCopy);
					}
					
					if(specialVo == null){
						specialVo = new SpecialVo();
						specialVo.setSpecialId(0);
					}else{
						productVo.setActivityType(specialVo.getSpecialType());
						productVo.setIsActivity("NO");
						if(StringUtil.isNotNull(proSpec.getActivity_price()) && new BigDecimal(proSpec.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
							productVo.setActivityPrice(proSpec.getActivity_price());
							productVo.setIsActivity("YES");
						}
						productVo.setActivity_id(specialVo.getSpecialId());
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("/weixin/product/searchProductAllJson -----error", e);
		} 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	
	}
	
	@RequestMapping(value="/loginOut")
	public void loginOut(HttpServletRequest request,HttpServletResponse response){
		Constants.setCustomer(request, null);
		try {
			response.sendRedirect("index");
		} catch (IOException e) {
			logger.error("/weixin/index/loginOut --error", e);
		}
	}
	
	/**
	 * 限量抢购列表页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toLimitGoods")
	public ModelAndView toLimitGoods(HttpServletRequest request,HttpServletResponse response,Model model){
		return new ModelAndView("weixin/goods/wx_limit_list");
	}
	
	/**
	 * 团购列表页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toGroupGoods")
	public ModelAndView toGroupGoods(HttpServletRequest request,HttpServletResponse response,Model model){
		return new ModelAndView("weixin/goods/wx_groupsd_list");
	}

}
