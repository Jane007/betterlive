package com.kingleadsw.betterlive.controller.app;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.BannerInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ExtensionManager;
import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PropIndexManager;
import com.kingleadsw.betterlive.biz.SearchRecordManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
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
 * app 首页接口
 * @author zhangjing
 *
 * @date 2017年4月12日
 */
@Controller
@RequestMapping("/app/index")
public class AppIndexController extends AbstractWebController{
	
	private Logger logger = Logger.getLogger(AppIndexController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ExtensionManager extensionManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private BannerInfoManager bannerInfoManager;
	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private LabelManager labelManager;
	@Autowired
	private SearchRecordManager searchRecordManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductLabelManager productLabelManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private PropIndexManager propIndexManager;
	
	
	
	/**
	 * app 首页   Banner图
	 * @return
	 */
	@RequestMapping(value = "/bannerList")
	@ResponseBody
	public Map<String,Object> bannerList(){
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		
		List<BannerInfoVo> bannerInfos = bannerInfoManager.queryAllBannersList(pd);
		if(bannerInfos==null|| bannerInfos.size()<=0){
			bannerInfos = new ArrayList<BannerInfoVo>();
		}
		return CallBackConstant.SUCCESS.callback(bannerInfos);
	}
	
	/**
	 * app 首页   每周新品列表
	 * @return
	 */
	@RequestMapping(value = "/listNewProduct")
	@ResponseBody
	public Map<String,Object> listNewProduct(){
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		
		List<ProductVo> newProducts = this.productManager.queryIndexProduct(1,1,1,pd, 3, 1);//每周新品
		if(newProducts==null || newProducts.size()<=0){
			newProducts = new ArrayList<ProductVo>();
		}
		return CallBackConstant.SUCCESS.callback(newProducts);
	}
	
	/**
	 * app 首页   人气推荐列表
	 * @return
	 */
	@RequestMapping(value = "/listRecommentProduct")
	@ResponseBody
	public Map<String,Object> listRecommentProduct(){
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		
		List<ProductVo> recommendProducts = this.productManager.queryIndexProduct(1,2,1,pd, 8, 1);//人气推荐
		if(recommendProducts==null || recommendProducts.size()<=0){
			recommendProducts = new ArrayList<ProductVo>();
		}
		return CallBackConstant.SUCCESS.callback(recommendProducts);
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
		PageData pd = this.getPageData();
		String token = pd.getString("token"); // 用户标识
		//用户标识
		int customerId = 0;
		if(StringUtil.isNotNull(token)){
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}
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
	 * 查询json数据  搜索结果
	 * @param request
	 * @param response
	 * @param page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/searchProductAllJson")
	@ResponseBody
	public Map<String,Object> searchProductAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd=this.getPageData();
		try {
			String productName=pd.getString("productName");
			if(null==productName || "".equals(productName)){
				return CallBackConstant.PARAMETER_ERROR.callbackError("参数不能为空");
			}
			
			productName=URLDecoder.decode(productName,"utf-8");
			pd.put("productName",productName);
			pd.put("status","1");
			List<ProductVo> list = productManager.queryProductListPage(pd);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("list", list);
		    return CallBackConstant.SUCCESS.callback(map);
		} catch (UnsupportedEncodingException e) {
			logger.error("/app/index/searchProductAllJson --error", e);
			return CallBackConstant.FAILED.callback();
		} 
		
	}
	
	/**
	 * 每周新品/人气推荐列表
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/product/productListAllJson",method=RequestMethod.POST)
	@ResponseBody
	public void productListAllJson(HttpServletRequest request,HttpServletResponse response){
		JSONObject json=new JSONObject(); 
		PageData pd = this.getPageData();
		
		String type = pd.getString("extensionType");
		int extensionType = 0;
		if(!StringUtils.isEmpty(type)){
			extensionType = Integer.parseInt(type);
		}
		pd.put("orderFlag", 1);
		List<ProductVo> productVos = this.productManager.queryIndexProduct(1,extensionType,0,pd,10, 1);//每周新品、人气推荐列表
		pd.put("status", 1);//PreProduct正常状态
		if(extensionType==1){
			json.put("title", "挥货-每周新品");
		}else{
			json.put("title", "挥货-人气推荐");
		}
		json.put("list", productVos);
		this.outObjectToJson(json, response);
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
	
	/**
	 * 1.1app 首页  废弃
	 * @return
	 */
	@RequestMapping(value = "/showIndex")
	@ResponseBody
	public Map<String,Object> showIndex(){
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			List<BannerInfoVo> bannerInfos = bannerInfoManager.queryAllBannersList(pd);
			if(bannerInfos==null|| bannerInfos.size()<=0){
				bannerInfos = new ArrayList<BannerInfoVo>();
			}
			map.put("bannerInfos", bannerInfos);		
			
			int msgCount = 0; //未读消息默认为0
			String token = pd.getString("token");  							//用户标识
			int hasExpiring = 0;
			PageData couponParam = new PageData();
			if (StringUtil.isNoNull(token)) {
				CustomerVo customer = customerManager.queryCustomerByToken(token);
				if(customer != null){
					//未读消息
					pd.put("customerId", customer.getCustomer_id());
					msgCount = messageManager.selectCountByUnread(pd);
					
					//即将过期的优惠券/单品红包
					couponParam.put("customerId", customer.getCustomer_id());
					
					//有可领取的优惠券和红包
					List<SingleCouponVo> singleCoupons = this.singleCouponManager.queryEffectiveCouponList(couponParam);
					couponParam.put("couponType", 2);
					List<CouponManagerVo> couponManagers = this.couponManagerManager.queryEffectiveCouponList(couponParam);
					if(singleCoupons != null && couponManagers != null 
							&& singleCoupons.size() > 0 && couponManagers.size() > 0){
						map.put("singleCoupons", singleCoupons);
						map.put("couponManagers", couponManagers);
					}else if (singleCoupons != null && singleCoupons.size() > 0
							&& (couponManagers == null || couponManagers.size() <= 0)){
						map.put("singleCoupons", singleCoupons);
					}else if ((singleCoupons == null || singleCoupons.size() <= 0) 
							&& couponManagers != null && couponManagers.size() > 0){
						map.put("couponManagers", couponManagers);
					}
				}
			}
			
			map.put("hasExpiring", hasExpiring);
			map.put("unreadCount", msgCount);
			
			List<ProductVo> newProducts = this.productManager.queryIndexProduct(1,1,1,pd, 3, 1);//每周新品
			if(newProducts==null || newProducts.size()<=0){
				newProducts = new ArrayList<ProductVo>();
			}
			map.put("newProducts", newProducts);
			
			pd.clear();
			List<ProductVo> recommendProducts = this.productManager.queryIndexProduct(1,2,1,pd, 6, 1);//人气推荐
			if(recommendProducts==null || recommendProducts.size()<=0){
				recommendProducts = new ArrayList<ProductVo>();
			}

			map.put("recommendProducts", recommendProducts);	
			
			pd.clear();
			pd.put("picture_type", 3);
			pd.put("status", 1);
			PicturesVo groupPic = picturesManager.queryOne(pd);
			if(groupPic == null){	//团购封面图
				groupPic = new PicturesVo();
			}
			
			map.put("groupPicVo", groupPic);
			
			pd.clear();
			pd.put("picture_type", 4);
			pd.put("status", 1);
			PicturesVo limitPic = picturesManager.queryOne(pd);
			if(limitPic == null){	//限量抢购封面图
				limitPic = new PicturesVo();
			}
			map.put("limitPicVo", limitPic);
			
			//美食推荐文章
			PageData sapd = new PageData();
			sapd.put("status", 1);
			sapd.put("homeFlag", 1);
			sapd.put("homeFlagShow", 1);
			SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(sapd);
			if(specialArticleVo == null){
				specialArticleVo = new SpecialArticleVo();
				specialArticleVo.setArticleId(0);
			}
			map.put("specialArticleVo", specialArticleVo);
		} catch (Exception e) {
			logger.error("/app/index/showIndex----error", e);
		}
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	
	
	
	/**
	 * 1.1app 首页加红包领取改版
	 * @return
	 */
	@RequestMapping(value = "queryRedCoupon")
	@ResponseBody
	public Map<String,Object> queryRedCoupon(){
		PageData pd = this.getPageData();
		pd.put("status", 1);//banner正常状态
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			String token = pd.getString("token");  							//用户标识
			PageData couponParam = new PageData();
			if (StringUtil.isNoNull(token)) {
				CustomerVo customer = customerManager.queryCustomerByToken(token);
				if(customer != null){
					//即将过期的优惠券/单品红包
					couponParam.put("customerId", customer.getCustomer_id());
					if(StringUtil.isNotNull(pd.getString("productId"))){
						couponParam.put("productId", pd.getInteger("productId"));
					}
					couponParam.put("homeFlag", 1);
					if(StringUtil.isNotNull(pd.getString("specId"))){
						couponParam.put("specId", pd.getInteger("specId"));
					}
					//新版首页显示没购买过商品的红包
					String couponId = WebConstant.NEW_USER_COUPON;
					couponParam.put("couponIds", couponId);
					couponParam.put("couponType", 2);
					List<CouponManagerVo> newUserCoupon = this.couponManagerManager.queryNewUserCouponList(couponParam);
					
					if(newUserCoupon!=null&&newUserCoupon.size()>0&&StringUtil.isNull(pd.getString("specId"))){//首页的红包，详情红包调这个接口里不在这里return
						map.put("couponManagers", newUserCoupon);
						map.put("newUserCoupon", "1");//新人
						return CallBackConstant.SUCCESS.callback(map);
					}
					
					//有可领取的优惠券和红包
					List<SingleCouponVo> singleCoupons = this.singleCouponManager.queryEffectiveCouponListNew(couponParam);
					couponParam.put("couponType", 2);
					map.put("newUserCoupon", "0");//老人
					List<CouponManagerVo> couponManagers = this.couponManagerManager.queryEffectiveCouponList(couponParam);
					//把新手券放详情里展示
					if(couponManagers!=null&&couponManagers.size()>0){
						if(newUserCoupon!=null&&newUserCoupon.size()>0){
							couponManagers.addAll(newUserCoupon);
						}
					}else{
						if(newUserCoupon!=null&&newUserCoupon.size()>0){
							couponManagers=newUserCoupon;
						}
					}
					
					
					if(singleCoupons != null && couponManagers != null 
							&& singleCoupons.size() > 0 && couponManagers.size() > 0){
						map.put("singleCoupons", singleCoupons);
						map.put("couponManagers", couponManagers);
					}else if (singleCoupons != null && singleCoupons.size() > 0
							&& (couponManagers == null || couponManagers.size() <= 0)){
						map.put("singleCoupons", singleCoupons);
					}else if ((singleCoupons == null || singleCoupons.size() <= 0) 
							&& couponManagers != null && couponManagers.size() > 0){
						map.put("couponManagers", couponManagers);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("/app/index/queryRedCoupon----error", e);
		}
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 首页专题弹窗活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySpecialAlertInfo")
	@ResponseBody
	public Map<String, Object> querySpecialAlertInfo(HttpServletRequest request){
		PageData pd = new PageData();
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
			map.put("backGroundUrl", propIndex.getLinkImg());
			map.put("specialId", propIndex.getObjId());
			map.put("cachingTime", 3600000); //1小时
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/weixn/getSpecialAlertInfo -------error", e);
			return CallBackConstant.FAILED.callback();
		}
	}

	/**
	 * app1.1 查询json数据  搜索标签
	 * @param request
	 * @param response
	 * @param page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/searchLabelAllJson")
	@ResponseBody
	public Map<String,Object> searchLabelAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd=this.getPageData();
		
		String labelType=pd.getString("labelType"); //1热门搜索
		if(StringUtil.isNull(labelType)){
			return CallBackConstant.FAILED.callbackError("标签类型不能为空");
		}
		
		pd.put("status", 0);
		List<LabelVo> labels = labelManager.queryListPage(pd);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", labels);
	    return CallBackConstant.SUCCESS.callback(map);
		
	}
	
	/**
	 * app1.1 查询json数据  搜索结果
	 * @param request
	 * @param response
	 * @param page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/searchProductAllJsonByPage")
	@ResponseBody
	public Map<String,Object> searchProductAllJsonByPage(HttpServletRequest request,HttpServletResponse response) {
		PageData pd=this.getPageData();
		
		String productName=pd.getString("productName");
		if(StringUtil.isNull(productName)){
			return CallBackConstant.FAILED.callbackError("参数不能为空");
		}
		try {
			productName=URLDecoder.decode(productName,"utf-8").trim();
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
				String token = pd.getString("token");  							//用户标识
				if (StringUtil.isNoNull(token)) {
					CustomerVo customer = customerManager.queryCustomerByToken(token);
					if(customer != null){
						srVo.setCustomerId(customer.getCustomer_id());
					}
				}
				srVo.setLabelId(labelVo.getLabelId());
				srVo.setLabelType(labelVo.getLabelType());
				searchRecordManager.insert(srVo);
				if (null != labelVo.getProductIds() && !"".equals(labelVo.getProductIds())) {
					pd.put("pro_ids", labelVo.getProductIds());
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("/app/product/searchProductAllJsonByPage -----error", e);
		} 

		pd.put("productStatus", 1);//状态 正常
		pd.put("isOnline", 1);
		List<ProductVo> list = productManager.queryProductListPage(pd);
		if(list == null){
			list = new ArrayList<ProductVo>();
		}else{
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
	    return CallBackConstant.SUCCESS.callback(map);
		
	}	
	
	/**
	 * app1.1 查询json数据  首页四个小图标
	 * @param request
	 * @param response
	 * @param page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/queryIcon")
	@ResponseBody
	public Map<String,Object> queryIcon(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
//新春快乐
//		map.put("menu01", "http://images.hlife.shop/2018x1.png");
//		map.put("menu02", "http://images.hlife.shop/2018x2.png");
//		map.put("menu03", "http://images.hlife.shop/2018x3.png");
//		map.put("menu04", "http://images.hlife.shop/2018x4.png");
//----------------------一周年庆
		 map.put("menu01", "http://images.hlife.shop/zhounianqing1201804041319.png");
		 map.put("menu02", "http://images.hlife.shop/zhounianqing2201804041319.png");
		 map.put("menu03", "http://images.hlife.shop/zhounianqing3201804041319.png");
		 map.put("menu04", "http://images.hlife.shop/zhounianqing4201804041319.png");
	    return CallBackConstant.SUCCESS.callback(map);
	}
}
