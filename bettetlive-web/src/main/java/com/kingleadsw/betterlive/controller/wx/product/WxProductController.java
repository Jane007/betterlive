package com.kingleadsw.betterlive.controller.wx.product;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityManager;
import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.AgentProductManager;
import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.CustomerSourceUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.AgentProductVo;
import com.kingleadsw.betterlive.vo.CollectionVo;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerAgentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
import com.kingleadsw.betterlive.vo.UserGroupVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 会生活首页控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/weixin/product")
public class WxProductController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxProductController.class);

	@Autowired
	private ProductManager productManager;  
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ActivityManager activityManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private PreProductManager preProductManager;
	
	@Autowired
	private OrderProductManager orderProductManager; 
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private SalePromotionManager salePromotionManager;
	
	@Autowired
	private CollectionManager collectionManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
	@Autowired
	private PostageManager postageManager; 
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private CustomerAgentManager customerAgentManager;
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	@Autowired
	private AgentProductManager agentProductManager;
	
	
	/**
	 * 每周新品/人气推荐
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/productListAllJson")
	@ResponseBody
	public void productListAllJson(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/productListAllJson--->begin");
		JSONObject json = new JSONObject(); 
		try {
			PageData pd = this.getPageData();
			String type = pd.getString("extensionType");
			int extensionType = 0;
			if(!StringUtils.isEmpty(type)){
				extensionType = Integer.parseInt(type);
			}
			pd.put("orderFlag", 1);
			List<ProductVo> productVos = this.productManager.queryIndexProduct(1,extensionType,0,pd,10, 1);//每周新品、人气推荐列表
			json.put("pageInfo", pd.get("pageView"));
			json.put("list", productVos);
		} catch (Exception e) {
			logger.error("/weixin/product/productListAllJson", e);
		}
		logger.info("/weixin/productListAllJson--->end");
		this.outObjectToJson(json, response);
	}
	
	@RequestMapping(value = "/productOneJson")
	@ResponseBody
	public void productOneJson(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject(); 
		String productId = pd.getString("productId");
		if ("0".equals(productId)) {
			return;
		}
		ProductVo productVo = this.productManager.selectByPrimaryKey(Integer.parseInt(productId));
		if (null != productVo) {
			json.put("productVo", productVo);
		}else {
			json.put("productVo", new ProductVo());
		}
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 访问商品分类页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toProductsByType")
	public ModelAndView toProductsByType(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/goods/wx_productByTypes");
		PageData pd = this.getPageData();
		String whoClick = pd.getString("whoClick");
		if (StringUtil.isNull(whoClick)) {
			whoClick = "0";
		}
		
		int msgCount = 0; //未读消息默认为0
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null&&customer.getCustomer_id()!=null){
			//未读消息
			pd.put("customerId", customer.getCustomer_id());
			msgCount = messageManager.selectCountByUnread(pd);
		}
		
		model.addAttribute("unreadCount", msgCount);
		model.addAttribute("whoClick", whoClick);
		return modelAndView;
	}
	
	/**
	 * 商品分类查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryProductListByType")
	@ResponseBody
	public Map<String, Object> queryProductListByType(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入商品分类列表查看开始");
		
		PageData pd = this.getPageData();
		
		String strProductType = pd.getString("productType");
		if (!StringUtil.isIntger(strProductType)) {
        	return CallBackConstant.PARAMETER_ERROR.callback("商品类型为空");
        }

		List<ProductVo> listProduct = null;
		try {
			int productType = 0;
			if(!StringUtils.isEmpty(strProductType)){
				productType = Integer.parseInt(strProductType);
			}
			pd.put("productType", productType);//类型
			pd.put("productStatus", 1);//状态 正常
			pd.put("isOnline", 1);
			listProduct = this.productManager.queryProductListPage(pd);
			if(listProduct == null){
				listProduct = new ArrayList<ProductVo>();
			}
			else{
				for (ProductVo productVo : listProduct) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", productVo.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					//商品普通价
					PageData appd = new PageData();
					appd.put("productId", productVo.getProduct_id());
					appd.put("proStatus", 1);
					appd.put("specStatus", 1);
					if(specialVo != null){
						appd.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(appd);
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
						productVo.setSalesVolume(0);
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
		} 
		catch (Exception e) {
			logger.error("查询产品异常...", e);
			return CallBackConstant.FAILED.callback();
		}
		Map<String, Object> map = CallBackConstant.SUCCESS.callback(listProduct);
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	
	/**
	 * 跳转到商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/towxgoodsdetails")
	public ModelAndView towxgoodsdetails(HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView();
		PageData pd=this.getPageData();
		String backUrl = pd.getString("backUrl");
		
		if(StringUtil.isNull(pd.getString("productId"))){
			modelAndView.addObject("tipsTitle", "商品信息提示");
			modelAndView.addObject("tipsContent", "您访问的商品不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", "1");
		pd.put("isOnline", 1); // 只查询线上的商品
		ProductVo product=productManager.selectProductByOption(pd);
		
		if (null == product) {
			modelAndView.addObject("tipsTitle", "商品信息提示");
			modelAndView.addObject("tipsContent", "您访问的商品已下架");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		int isAgent = 0;    //是否是代理人分销（0不是1是）
		
		String orderSource = "weixin";
		orderSource = CustomerSourceUtil.checkOrderSource(request, pd.getString("source"), pd.getString("orderSource"), orderSource);
		//判断订单来源是否为代理分销
		CustomerAgentVo customerAgent = customerAgentManager.findAgentBySource(orderSource);
		if (null != customerAgent && customerAgent.getStatus().intValue() == 0) {
			PageData agentParams = new PageData();
			agentParams.put("productId", product.getProduct_id());
			agentParams.put("status", 0);
			AgentProductVo agentProduct = agentProductManager.queryOne(agentParams);
			if(agentProduct != null) {
				isAgent = 1;
			}
		}
		
			
		String details=product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		//专题
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		PageData specialParams = new PageData();
		specialParams.put("status", 1);
		specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		specialParams.put("productId", product.getProduct_id());
		//specialParams.put("activityFlag", 1);
		SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
		//设置每个商品规格的活动价
		List<ProductSpecVo> speclist = product.getListSpecVo();
		if(specialVo==null){
			specialVo = new SpecialVo();
			specialVo.setSpecialId(0);
		}else{
			if(specialVo.getSpecialType()==3){//团购
				return new ModelAndView("redirect:/weixin/product/toGroupGoodsdetails?specialId="+specialVo.getSpecialId()+"&productId="+product.getProduct_id());
			}else if(specialVo.getSpecialType()==2){//限量
				return new ModelAndView("redirect:/weixin/product/toLimitGoodsdetails?specialId="+specialVo.getSpecialId()+"&productId="+product.getProduct_id());
			}else{//限时活动/美食教程走之前的逻辑
				//查询是否存在活动 ，如果是 就加入活动价
				PageData pdData=new PageData();
				pdData.put("productId", String.valueOf(product.getProduct_id()));
				pdData.put("activityFlag", 1);
				pdData.put("activityId", specialVo.getSpecialId());
				listActivtyProduct = activityProductManager.queryList(pdData);
				
				if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
					Date dt = DateUtil.stringToDate(specialVo.getEndTime());
					Date start = DateUtil.stringToDate(specialVo.getStartTime());
					
					for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
						if(specialVo!=null){//专题活动
							if(dt.after(new Date())&&start.before(new Date())){
								if(speclist!=null&&speclist.size()>0){
									for (ProductSpecVo productSpecVo : speclist) {
										if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
											productSpecVo.setActivity_price(spec.getActivity_price());
											
										}
									}
								}
							}
						}
					}
					
				}
				
			}
		}
		
		
		
		//满减活动不需要登陆
		for (ProductSpecVo productSpecVo : speclist) {
			fullMoneyCut(productSpecVo);
		}
		product.setListSpecVo(speclist);
		//满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",product.getProduct_id());
		SalePromotionVo salePromotionVo = new SalePromotionVo();
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
		if(listPromotions!=null&&listPromotions.size()>0){
			salePromotionVo = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
		}
		
		PageData commpd = new PageData();
		commpd.put("parentFlag", 1);	//评论数（不包括回复数）
		commpd.put("status",2); //评论通过
		commpd.put("productId", product.getProduct_id());
		List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
		if(comments == null){
			comments = new ArrayList<CommentVo>();
		}
		modelAndView.addObject("comments", comments);
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		int collectionId = 0;
		CustomerVo customer = Constants.getCustomer(request);
		String userMobile = "";
		int customerId = 0;
		int cartCnt = 0;
		if (customer != null && customer.getCustomer_id() != null) {  //用户不为空
			customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
			if(StringUtil.isNotNull(customer.getMobile())){
				userMobile = customer.getMobile();
			}
			cartCnt = shoppingCartManager.queryShoppingCartCnt(customer.getCustomer_id());
			
			customerId = customer.getCustomer_id();
			productPd.put("customerId", customer.getCustomer_id());
			
			List<ProductSpecVo> speclists = product.getListSpecVo();
			if(speclists!=null&&speclists.size()>0){
				for (ProductSpecVo productSpecVo : speclists) {
					if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
						
						Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
						Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
						if(dt.after(new Date())&&start.before(new Date())){//限购时间内
							if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
								
								productPd.put("startTime", productSpecVo.getLimit_start_time());
								productPd.put("endTime", productSpecVo.getLimit_end_time());
								
								productPd.put("checkStatus", "2,3,4,5");//已付款的订单
								productPd.put("customerId", String.valueOf(customerId));
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
								
								//按产品id 和规格查出所有买过此商品的订单详情
								if(orderQuantity > 0){
									int restCopy = productSpecVo.getLimit_max_copy() - orderQuantity;
									productSpecVo.setRest_copy(restCopy);
									productSpecVo.setHasBuy_copy(orderQuantity);
								}else{
									productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
									productSpecVo.setHasBuy_copy(0);
								}
								int carNums = shoppingCartManager.queryCartCountByParams(productPd);
								int carCanAdd=0;
								if(carNums > 0){
									carCanAdd = productSpecVo.getLimit_max_copy()-carNums;
									productSpecVo.setCarNums(carNums);
									productSpecVo.setCarCanAdd(carCanAdd);
								}else{
									productSpecVo.setCarNums(0);
									productSpecVo.setCarCanAdd(productSpecVo.getLimit_max_copy());
								}
							}
						}else{//限购时间外
							productSpecVo.setLimit_max_copy(-1);
						}
					}
				}
			}
			
			PageData cpd = new PageData();
			cpd.put("objId", product.getProduct_id());
			cpd.put("customerId", customer.getCustomer_id());
			CollectionVo coll = this.collectionManager.queryOne(cpd);
			if(coll != null && coll.getCollectionId() > 0){
				collectionId = coll.getCollectionId();
			}
			
			//优惠券
			//设置优惠券数量
			product = getCouponCount(product, customer);
			
		}
		
		PageData postagepd= new PageData();
		postagepd.put("productId", product.getProduct_id());
		PostageVo postageVo = postageManager.queryOne(postagepd);
		if (null != postageVo) {
			modelAndView.addObject("postageId",postageVo.getPostageId());
			modelAndView.addObject("postageMsg",postageVo.getPostageMsg());
		}
		
		
		//红包单个规格进详情页面specId也传过来了,供页面展示默认规格
		String specId = pd.getString("specId");
		modelAndView.addObject("specId", specId);
		
		product.setSalesVolume(product.getSalesVolume()+product.getFake_sales_copy());
		modelAndView.addObject("collectionId", collectionId);
		modelAndView.addObject("productInfo", product);
		modelAndView.addObject("productId", product.getProduct_id());
		modelAndView.addObject("specialVo", specialVo);
		modelAndView.addObject("salePromotionVo", salePromotionVo);
		modelAndView.addObject("shoppingCarts", cartCnt);
		modelAndView.addObject("mobile",userMobile);
		modelAndView.addObject("customerId",customerId);
		modelAndView.addObject("orderSource", orderSource);
		modelAndView.addObject("backUrl", backUrl);
		modelAndView.addObject("isAgent", isAgent);
		
		modelAndView.setViewName("weixin/goods/wx_goodsdetails");
		
		return modelAndView;
	}
	
	/**
	 * 设置优惠券数量
	 * @param
	 * @return
	 * @author zhangjing 2017年12月15日 上午9:48:26
	 */
	private ProductVo getCouponCount(ProductVo product,CustomerVo customer){
		//优惠券
		PageData coupon = new PageData();
		coupon.put("customerId", customer.getCustomer_id());
		coupon.put("productId", product.getProduct_id());
		List<ProductSpecVo> listSpecVo = product.getListSpecVo();
		if(listSpecVo!=null&&listSpecVo.size()>0){
			for (ProductSpecVo productSpecVo : listSpecVo) {
				coupon.put("price", productSpecVo.getSpec_price());
				coupon.put("canusecoupon","0");
				List<CouponInfoVo> couponManagers = couponInfoManager.findListUserCoupon(coupon);
				if(couponManagers!=null&&couponManagers.size()!=0){
					productSpecVo.setCouponCount(couponManagers.size());
				}
				coupon.put("specId", productSpecVo.getSpec_id());
				coupon.put("startTime", new Date());
				coupon.put("endTime", new Date());
				coupon.put("status", 0);
				coupon.put("fullMoney", productSpecVo.getSpec_price());
				coupon.put("canusecoupon", 0);
				UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(coupon);
				if(uscVo!=null){
					productSpecVo.setSingleCouponCount(1);
				}
				
				//fullMoneyCut(productSpecVo);
			}
			
		}
		
		return product;
	}
	
	
	
	
	
	/**
	 * 跳转到限量抢购商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toLimitGoodsdetails")
	@ResponseBody
	public ModelAndView toLimitGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入微信端查询限量商品详情页面");
		
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		String backUrl = pd.getString("backUrl");
		
		if(StringUtil.isNull(pd.getString("productId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		PageData spd=new PageData();
		spd.put("specialId", pd.getString("specialId"));
		SpecialVo specialVo = specialMananger.queryOne(spd);
		if(specialVo == null || specialVo.getStatus().intValue() != 1){
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "您查看的限购活动已结束啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
			specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
		} catch (ParseException e) {
			logger.error("/weixin/product/toLimitGoodsdetails ---error:specialId="+pd.getString("specialId"), e);
		}
		
		pd.put("status", 1); //上架的商品
		pd.put("isOnline", 1);  //只查询线上的商品
		pd.put("specialId", pd.getString("specialId"));
		ProductVo product = productManager.selectProductByOption(pd);
		if(null == product){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int isAgent = 0;
		String orderSource = "weixin_limit_"+pd.getString("specialId");
		orderSource = CustomerSourceUtil.checkOrderSource(request, pd.getString("source"), pd.getString("orderSource"), orderSource);
		//判断订单来源是否为代理分销
		CustomerAgentVo customerAgent = customerAgentManager.findAgentBySource(orderSource);
		if (null != customerAgent && customerAgent.getStatus().intValue() == 0) {
			PageData agentParams = new PageData();
			agentParams.put("productId", product.getProduct_id());
			agentParams.put("status", 0);
			AgentProductVo agentProduct = agentProductManager.queryOne(agentParams);
			if(agentProduct != null) {
				isAgent = 1;
			}
		}
		
		String details = product.getDetails();
		if (null != details && !"".equals(details)) {
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		int hasStock = 0;
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
		pdData.put("activityType", 3);//限量
		pdData.put("activityId", pd.getInteger("specialId"));
		List<ActivityProductVo> listActivtyProduct = activityProductManager.queryList(pdData);
		List<ProductSpecVo> isSpecs = new ArrayList<ProductSpecVo>();
		//设置每个商品规格的活动价
		List<ProductSpecVo> speclist = product.getListSpecVo();
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				if(specialVo!=null){//专题活动
//					Date dt = DateUtil.stringToDate(specialVo.getEndTime());
//					Date start = DateUtil.stringToDate(specialVo.getStartTime());
//					if(dt.after(new Date())&&start.before(new Date())){
						if(speclist!=null&&speclist.size()>0){
							for (ProductSpecVo productSpecVo : speclist) {
								if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
									hasStock = hasStock + productSpecVo.getStock_copy();
									isSpecs.add(productSpecVo);
								}
							}
						}
//					}
				}
				
			}
		}
		
		//满减活动不需要登陆
		for (ProductSpecVo productSpecVo : speclist) {
			fullMoneyCut(productSpecVo);
		}
		product.setListSpecVo(speclist);
		
		//满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",product.getProduct_id());
		SalePromotionVo salePromotionVo = new SalePromotionVo();
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
		if(listPromotions!=null&&listPromotions.size()>0){
			salePromotionVo = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
		}
		
		PageData commpd = new PageData();
		commpd.put("parentFlag", 1);	//评论数（不包括回复数）
		commpd.put("status",2); //评论通过
		commpd.put("productId", product.getProduct_id());
		List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
		if(comments == null){
			comments = new ArrayList<CommentVo>();
		}
		mv.addObject("comments", comments);
		
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		int collectionId = 0;
		CustomerVo customer = Constants.getCustomer(request);
		String userMobile = "";
		int customerId = 0;
		int cartCnt = 0;
		if (customer != null && customer.getCustomer_id() != null) {  //用户不为空
			customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
			if(StringUtil.isNotNull(customer.getMobile())){
				userMobile = customer.getMobile();
			}
			cartCnt = shoppingCartManager.queryShoppingCartCnt(customer.getCustomer_id());
			
			customerId = customer.getCustomer_id();
			productPd.put("customerId", customer.getCustomer_id());

			if(isSpecs!=null&&isSpecs.size()>0){
				for (ProductSpecVo productSpecVo : isSpecs) {
					int restCopy = 0;
					if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
						
						Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
						Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
						if(dt.after(new Date())&&start.before(new Date())){//限购时间内
							if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
								productPd.put("startTime", productSpecVo.getLimit_start_time());
								productPd.put("endTime", productSpecVo.getLimit_end_time());
								//按产品id 和规格查出所有买过此商品的订单详情
								productPd.put("checkStatus", "2,3,4,5");//已付款的订单
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
								
								if(orderQuantity > 0){
									restCopy = productSpecVo.getLimit_max_copy() - orderQuantity;
									productSpecVo.setRest_copy(restCopy);
									productSpecVo.setHasBuy_copy(orderQuantity);
								}else{
									productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
									productSpecVo.setHasBuy_copy(0);
								}
								int carNums = shoppingCartManager.queryCartCountByParams(productPd);
								int carCanAdd=0;
								if(carNums > 0){
									carCanAdd = productSpecVo.getLimit_max_copy()-carNums;
									productSpecVo.setCarNums(carNums);
									productSpecVo.setCarCanAdd(carCanAdd);
								}else{
									productSpecVo.setCarNums(0);
									productSpecVo.setCarCanAdd(productSpecVo.getLimit_max_copy());
								}
							}
						}else{//限购时间外
							productSpecVo.setLimit_max_copy(-1);
						}
					}
					
				}
			}
			
			PageData cpd = new PageData();
			cpd.put("objId", product.getProduct_id());
			cpd.put("customerId", customer.getCustomer_id());
			CollectionVo coll = this.collectionManager.queryOne(cpd);
			if(coll != null && coll.getCollectionId() > 0){
				collectionId = coll.getCollectionId();
			}
			//设置优惠券数量
			product = getCouponCount(product, customer);
		}
		
		int unrealSales = product.getSalesVolume()+product.getFake_sales_copy();
		PageData postagepd= new PageData();
		postagepd.put("productId", product.getProduct_id());
		PostageVo postageVo = postageManager.queryOne(postagepd);
		if (null != postageVo) {
			mv.addObject("postageId",postageVo.getPostageId());
			mv.addObject("postageMsg",postageVo.getPostageMsg());
		}
		product.setListSpecVo(isSpecs);
		mv.addObject("collectionId", collectionId);
		mv.addObject("productInfo", product);
		mv.addObject("specialVo", specialVo);
		mv.addObject("salePromotionVo", salePromotionVo);
		mv.addObject("unrealSales", unrealSales);
		mv.addObject("hasStock", hasStock);
		mv.addObject("shoppingCarts", cartCnt);
		mv.addObject("currentTime", System.currentTimeMillis());
		mv.addObject("mobile",userMobile);
		mv.addObject("customerId", customerId);
		mv.addObject("orderSource", orderSource);
		mv.addObject("backUrl", backUrl);
		mv.addObject("isAgent", isAgent);
		mv.setViewName("weixin/goods/wx_limit_details");
		return mv;
	}
	
	/**
	 * 跳转到团购商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toGroupGoodsdetails")
	@ResponseBody
	public ModelAndView toGroupGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入微信端查询团购商品详情页面");
		
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		String backUrl = pd.getString("backUrl");
		
		if(StringUtil.isNull(pd.getString("productId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		PageData spd=new PageData();
		spd.put("specialId", pd.getString("specialId"));
		SpecialVo specialVo = specialMananger.queryOne(spd);
		if(null == specialVo){
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "您访问的团购活动已结束啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			SysGroupVo sysGroupVo = sysGroupManager.queryOne(spd);
			if(sysGroupVo == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已结束啦");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			List<UserGroupVo> userGroups = new ArrayList<UserGroupVo>();
			
			specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
			specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
			long longStart = sdf.parse(specialVo.getStartTime()).getTime();
			long longEnd = sdf.parse(specialVo.getEndTime()).getTime();
			long ntime = System.currentTimeMillis();
			if(specialVo.getStatus().intValue() == 1 && longStart <= ntime && longEnd > ntime){
				spd.put("productId", sysGroupVo.getProductId());
				spd.put("specialId", specialVo.getSpecialId());
//				spd.put("isProgress", 1);
				spd.put("statusLine", 1);
				userGroups = this.userGroupManager.queryList(spd);
				if(userGroups == null){
					userGroups = new ArrayList<UserGroupVo>();
				}
			}
			
			pd.put("status", 1); //上架的商品
			pd.put("isOnline", 1);  //只查询线上的商品
			ProductVo product = productManager.selectProductByOption(pd);
			if(null == product){
				mv.addObject("tipsTitle", "商品信息提示");
				mv.addObject("tipsContent", "您访问的商品已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			int isAgent = 0;
			String orderSource = "weixin_group_" + pd.getString("specialId");
			orderSource = CustomerSourceUtil.checkOrderSource(request, pd.getString("source"), pd.getString("orderSource"), orderSource);
			//判断订单来源是否为代理分销
			CustomerAgentVo customerAgent = customerAgentManager.findAgentBySource(orderSource);
			if (null != customerAgent && customerAgent.getStatus().intValue() == 0) {
				PageData agentParams = new PageData();
				agentParams.put("productId", product.getProduct_id());
				agentParams.put("status", 0);
				AgentProductVo agentProduct = agentProductManager.queryOne(agentParams);
				if(agentProduct != null) {
					isAgent = 1;
				}
			}
			
//			int unrealSales = product.getSalesVolume();
//			if(specialVo.getFakeSalesCopy() > 0){
//				unrealSales = product.getSalesVolume()+specialVo.getFakeSalesCopy();
//			}
			
			String details = product.getDetails();
			if(null!=details && !"".equals(details)){
				product.setParamAndValue(details.split("@\\#@\\$"));
			}
			
			if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
				String[] pictureArrays=product.getProduct_logo().split(",");
				product.setPictureArray(pictureArrays);
			}
			
			List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
			//查询是否存在活动 ，如果是 就加入活动价
			PageData pdData=new PageData();
			pdData.put("productId", String.valueOf(product.getProduct_id()));
			//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
			pdData.put("activityType", 4);//拼团
			pdData.put("activityId", pd.getInteger("specialId"));
			listActivtyProduct = activityProductManager.queryList(pdData);
			List<ProductSpecVo> isSpecs = new ArrayList<ProductSpecVo>();
			if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
				//设置每个商品规格的活动价
				List<ProductSpecVo> speclist = product.getListSpecVo();
				for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
					if(specialVo!=null){//专题活动
	//					Date dt = DateUtil.stringToDate(specialVo.getEndTime());
	//					if(dt.after(new Date())&&start.before(new Date())){
							if(speclist!=null&&speclist.size()>0){
								for (ProductSpecVo productSpecVo : speclist) {
									if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
										productSpecVo.setActivity_price(spec.getActivity_price());
										isSpecs.add(productSpecVo);
									}
								}
							}
	//					}
					}
					
				}
			}
			
			PageData commpd = new PageData();
			commpd.put("parentFlag", 1);	//评论数（不包括回复数）
			commpd.put("status",2); //评论通过
			commpd.put("productId", product.getProduct_id());
			List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
			if(comments == null){
				comments = new ArrayList<CommentVo>();
			}
			mv.addObject("comments", comments);
			
			
			//根据条件查出此用户是否买过此产品
			PageData productPd = new PageData();
			productPd.put("productId", product.getProduct_id());
			
			int collectionId = 0;
			CustomerVo customer = Constants.getCustomer(request);
			String userMobile = "";
			int customerId = 0;
			int cartCnt = 0;
			if (customer != null && customer.getCustomer_id() != null) {  //用户不为空
				customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
				if(StringUtil.isNotNull(customer.getMobile())){
					userMobile = customer.getMobile();
				}
				cartCnt = shoppingCartManager.queryShoppingCartCnt(customer.getCustomer_id());
				
				customerId = customer.getCustomer_id();
				productPd.put("customerId", customer.getCustomer_id());
				
				if(isSpecs!=null&&isSpecs.size()>0){
					for (ProductSpecVo productSpecVo : isSpecs) {
						int restCopy = 0;
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
									productPd.put("startTime", productSpecVo.getLimit_start_time());
									productPd.put("endTime", productSpecVo.getLimit_end_time());
									
									//按产品id 和规格查出所有买过此商品的订单详情
									productPd.put("checkStatus", "2,3,4,5");//已付款的订单
									productPd.put("specId", productSpecVo.getSpec_id());//规格
									int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
									if(orderQuantity > 0){
										restCopy = productSpecVo.getLimit_max_copy()-orderQuantity;
										productSpecVo.setRest_copy(restCopy);
										productSpecVo.setHasBuy_copy(orderQuantity);
									}else{
										productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
										productSpecVo.setHasBuy_copy(0);
									}
									int carNums = shoppingCartManager.queryCartCountByParams(productPd);
									int carCanAdd=0;
									if(carNums > 0){
										carCanAdd = productSpecVo.getLimit_max_copy() - carNums;
										productSpecVo.setCarNums(carNums);
										productSpecVo.setCarCanAdd(carCanAdd);
									}else{
										productSpecVo.setCarNums(0);
										productSpecVo.setCarCanAdd(productSpecVo.getLimit_max_copy());
									}
								}
							}else{//限购时间外
								productSpecVo.setLimit_max_copy(-1);
							}
						}
					}
					
					PageData cpd = new PageData();
					cpd.put("objId", product.getProduct_id());
					cpd.put("customerId", customer.getCustomer_id());
					CollectionVo coll = this.collectionManager.queryOne(cpd);
					if(coll != null && coll.getCollectionId() > 0){
						collectionId = coll.getCollectionId();
					}
				}
				
			}
			
			long currTime = System.currentTimeMillis();
			int specialStatus = 0;
			if(specialVo.getLongEnd() <= currTime || specialVo.getStatus().intValue() != 1){
				specialStatus = 1;
			}
			
			product.setListSpecVo(isSpecs);
			
			int unrealSales = product.getSalesVolume()+product.getFake_sales_copy();
			PageData postagepd= new PageData();
			postagepd.put("productId", product.getProduct_id());
			PostageVo postageVo = postageManager.queryOne(postagepd);
			if (null != postageVo) {
				mv.addObject("postageId",postageVo.getPostageId());
				mv.addObject("postageMsg",postageVo.getPostageMsg());
			}
			mv.addObject("specialStatus", specialStatus);		
			mv.addObject("unrealSales", unrealSales);
			mv.addObject("collectionId", collectionId);
			mv.addObject("productInfo", product);
			mv.addObject("productId", product.getProduct_id());
			mv.addObject("specialVo", specialVo);
			mv.addObject("sysGroup", sysGroupVo);
			mv.addObject("userGroups", userGroups);
			mv.addObject("shoppingCarts", cartCnt);
			mv.addObject("mobile",userMobile);
			mv.addObject("customerId", customerId);
			mv.addObject("orderSource", orderSource);
			mv.addObject("backUrl", backUrl);
			mv.addObject("currentTime", System.currentTimeMillis());
			mv.addObject("isAgent", isAgent);
		} catch (ParseException e) {
			logger.error("/weixin/product/toLimitGoodsdetails ---error:specialId="+pd.getString("specialId"), e);
		}
		mv.setViewName("/weixin/goods/wx_groupsdetails");
		return mv;
	} 
	
	
	/**
	 * 跳转到商品线下详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toxxgoodsdetails")
	public ModelAndView toxxgoodsdetails(HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView();
		PageData pd=this.getPageData();
		
		CustomerVo customer=Constants.getCustomer(request);
		pd.put("status","1");
		ProductVo product=productManager.selectProductByOption(pd);
		
		if(null==product){
			modelAndView.addObject("tipsTitle", "商品信息提示");
			modelAndView.addObject("tipsContent", "您访问的商品已下架");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}	
		
		String orderSource = "weixin_offline";
		orderSource = CustomerSourceUtil.checkOrderSource(request, pd.getString("source"), pd.getString("orderSource"), orderSource);
			
		String details=product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
	
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
		pdData.put("activityType", 2);//专题
		listActivtyProduct = activityProductManager.queryList(pdData);
		String activityTheme="";
		String linkUrl="";//点专题跳转页面
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			for (ActivityProductVo spec : listActivtyProduct) {//符合时间的活动
				PageData spd=new PageData();
				spd.put("specialId", spec.getActivity_id());
				spd.put("status", 1);
				SpecialVo svo = specialMananger.selectSpecialByOption(spd);
				if(svo!=null&&svo.getSpecialType()==1){//限时活动
					Date dt = DateUtil.stringToDate(svo.getEndTime());
					Date start = DateUtil.stringToDate(svo.getStartTime());
					if(dt.after(new Date())&&start.before(new Date())){
						activityTheme = svo.getSpecialName();
						linkUrl = svo.getSpecialPage();
						break;
					}
				}
			}
			
			
			//设置每个商品规格的活动价
			List<ProductSpecVo> speclist = product.getListSpecVo();
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				PageData spd=new PageData();
				spd.put("specialId", spec.getActivity_id());
				SpecialVo svo = specialMananger.selectSpecialByOption(spd);
				if(svo!=null){//专题活动
					Date dt = DateUtil.stringToDate(svo.getEndTime());
					Date start = DateUtil.stringToDate(svo.getStartTime());
					if(dt.after(new Date())&&start.before(new Date())){
						if(speclist!=null&&speclist.size()>0){
							for (ProductSpecVo productSpecVo : speclist) {
								if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
								}
							}
						}
					}
				}
				
			}
		}
		
		List<CommentVo> comments=commentManager.queryCommentListByProductId(product.getProduct_id());
		modelAndView.addObject("comments",comments);
		modelAndView.addObject("linkUrl",linkUrl);
		modelAndView.addObject("activityTheme",activityTheme);
		
		
		/**
		 * 产品详情下面显示 猜你喜欢
		 */
		PageData pageData=new PageData();
		pageData.put("eatRecommend","1");
		pageData.put("productStatus", 1);
		List<ProductVo> listProduct=productManager.queryProductList(pageData);
		if(!listProduct.isEmpty() && listProduct.size()>0){
			
			for (int i = 0; i <listProduct.size(); i++) {
				if(null!=listProduct.get(i).getProduct_logo() && !"".equals(listProduct.get(i).getProduct_logo())){
					String[] pictureArrays=listProduct.get(i).getProduct_logo().split(",");
					listProduct.get(i).setPictureArray(pictureArrays);
				}
			}
		}
		
		String userMobile = "";
		int customerId = 0;
		if(customer != null && customer.getCustomer_id() != null){
			customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
			userMobile = customer.getMobile();
			customerId = customer.getCustomer_id();
			//根据条件查出此用户是否买过限购产品
			PageData productPd = new PageData();
			productPd.put("productId", product.getProduct_id());
			productPd.put("customerId", customer.getCustomer_id());
			List<ProductSpecVo> speclists = product.getListSpecVo();
			if(speclists!=null&&speclists.size()>0){
				for (ProductSpecVo productSpecVo : speclists) {
					int hasBuy = 0;
					int restCopy = 100;
					if((productSpecVo.getLimit_max_copy() != -1) 
							&& StringUtils.isNotBlank(productSpecVo.getLimit_start_time())
							&& StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
						
						Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
						Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
						if(dt.after(new Date())&&start.before(new Date())){//限购时间内
							if((productSpecVo.getLimit_max_copy() != -1) 
									&& productSpecVo.getLimit_max_copy() != null
									&& productSpecVo.getLimit_max_copy()>0){//说明是限购的
								productPd.put("startTime", productSpecVo.getLimit_start_time());
								productPd.put("endTime", productSpecVo.getLimit_end_time());
								
								productPd.put("hasBuy", "hasBuy");//已付款的订单
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								//按产品id 和规格查出所有买过此商品的订单详情
								List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(productPd);//查出之前下过的单
								if(orderProductlist!=null && orderProductlist.size()>0){
									
									for (OrderProductVo orderProductVo : orderProductlist) {
										hasBuy+=orderProductVo.getQuantity();
									}
									restCopy = productSpecVo.getLimit_max_copy()-hasBuy;
									productSpecVo.setRest_copy(restCopy);
									productSpecVo.setHasBuy_copy(hasBuy);
								}else{
									productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
									productSpecVo.setHasBuy_copy(0);
								}
							} 
						} else {//限购时间外的就是不限购
							productSpecVo.setLimit_max_copy(-1);
							productSpecVo.setHasBuy_copy(0);
							productSpecVo.setRest_copy(productSpecVo.getStock_copy());
						}
					} else {  //没有限购
						productSpecVo.setLimit_max_copy(-1);
						productSpecVo.setHasBuy_copy(0);
						productSpecVo.setRest_copy(productSpecVo.getStock_copy());
					}
				}
			}
		}
		
		modelAndView.addObject("mobile",userMobile);
		modelAndView.addObject("customerId", customerId);
		modelAndView.addObject("productInfo",product);
		modelAndView.addObject("listProduct",listProduct);
		modelAndView.addObject("orderSource", orderSource);
		modelAndView.setViewName("weixin/goods/wx_xx_goodsdetails");
		return modelAndView;
	} 
	
	
	private ProductSpecVo fullMoneyCut(ProductSpecVo productSpecVo){
		logger.info("fullMoneyCut()-----加入满减活动  ------------------------开始------------");
		//加入满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",productSpecVo.getProduct_id());
		mcps.put("specId",productSpecVo.getSpec_id());
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
			if(listPromotions!=null&&listPromotions.size()>0){
				SalePromotionVo salePromotion = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
				productSpecVo.setPromoitionName(salePromotion.getPromotionName());
				productSpecVo.setFullMoney(salePromotion.getFullMoney().toString());
				productSpecVo.setCutMoney(salePromotion.getCutMoney().toString());
			}
			return productSpecVo;
		}
		
		
	
	private List<ProductVo> fullMoneyCut(List<ProductVo> products){
		logger.info("fullMoneyCut()-----加入满减活动  ------------------------开始------------");
		//加入满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		for (ProductVo productVo : products) {
			mcps.put("productId",productVo.getProduct_id());
			List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
			if(listPromotions!=null&&listPromotions.size()>0){
				productVo.setFullCutPromoition("YES");
			}else{
				productVo.setFullCutPromoition("NO");
			}
		}
		return products;
		
	}
	
	/**
	 * 为你推荐
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryLikeProducts")
	@ResponseBody
	public Map<String, Object> queryLikeProducts(HttpServletRequest request,HttpServletResponse response) {
		List<ProductVo> listProduct = new ArrayList<ProductVo>();
		try {
			PageData pd = this.getPageData();
			int pageSize = 2;
			PageData productPd = new PageData();
			productPd.put("rowStart", 0);
			
			if(StringUtil.isNotNull(pd.getString("proIds"))){
				String[] proIdArray = pd.getString("proIds").split(",");
				pageSize = proIdArray.length;
				productPd.put("proIds", pd.getString("proIds"));
			}else{
				productPd.put("eatRecommend", 1);
			}
			
			productPd.put("pageSize", pageSize);
			productPd.put("productStatus", 1);
			productPd.put("isOnline", 1);
			listProduct = this.productManager.queryProductListPage(productPd);
			if(listProduct == null){
				listProduct = new ArrayList<ProductVo>();
			}else{
				for (ProductVo productVo : listProduct) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", productVo.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					//普通价
					PageData appd = new PageData();
					appd.put("productId", productVo.getProduct_id());
					appd.put("proStatus", 1);
					appd.put("specStatus", 1);
					if(specialVo != null){
						appd.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(appd);
					if(proSpec == null){
						continue;
					}
					productVo.setPrice(proSpec.getSpec_price());
					if (proSpec.getDiscount_price() != null && proSpec.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
						productVo.setDiscountPrice(proSpec.getDiscount_price()+"");
					}
					
					int fakeSalesCopy = productVo.getFake_sales_copy();
					if(proSpec.getTotal_sales_copy() != null){
						productVo.setSalesVolume(proSpec.getTotal_sales_copy().intValue()+fakeSalesCopy);
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
			return CallBackConstant.SUCCESS.callback(listProduct);
		} catch (Exception e) {
			logger.error("为你推荐异常：/weixin/product/queryLikeProducts", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 检查商品是否参与活动
	 * @param request
	 * @param response
	 * @param page
	 */
	@RequestMapping(value = "/checkActivityByProduct")
	@ResponseBody
	public Map<String,Object> checkActivityByProduct(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		try {
			if(StringUtil.isNull(pd.getString("productId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("参数不能为空");
			}
			
			int productId = pd.getInteger("productId");
			
			//专题
			PageData specialParams = new PageData();
			specialParams.put("status", 1);
			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", productId);
			SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
			
			PageData activityMap = new PageData();
			activityMap.put("productId", productId);
			activityMap.put("proStatus", 1);
			activityMap.put("specStatus", 1);
			if(specialVo != null){
				activityMap.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
			if(proSpec == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("商品已下架");
			}
			
			int specialId = 0;
			int specialType = 0;
			if(specialVo != null && specialVo.getSpecialId().intValue() > 0){
				specialId = specialVo.getSpecialId();
				specialType = specialVo.getSpecialType();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("specialId", specialId);
			map.put("specialType", specialType);
			map.put("productId", productId);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/weixin/product/checkActivityByProduct -----error", e);
			return CallBackConstant.FAILED.callback();
		} 
	
	}
	
	/**
	 * 活动促销
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getPromotion")
	@ResponseBody
	public Map<String,Object> getPromotion(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		if(StringUtil.isNotNull(pd.getString("productId")) && StringUtil.isNotNull(pd.getString("specId"))){
			pd.put("productId", pd.getInteger("productId"));
			pd.put("specId", pd.getInteger("specId"));
			//满多少钱减多少钱
			SalePromotionVo salePromotion = this.salePromotionManager.queryOne(pd);
			if (salePromotion != null) {
				map.put("salePromotionStr", "满" + BigDecimalUtil.subZeroAndDot(salePromotion.getFullMoney()) +"元减" + BigDecimalUtil.subZeroAndDot(salePromotion.getCutMoney()) + "元");
			}
			//积分换购
			pd.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
			ProductRedeemSpecVo productRedeemSpec = productRedeemSpecManager.queryOne(pd);
			if (productRedeemSpec != null) {
				if(productRedeemSpec.getRedeemType() == 0) {
					map.put("productRedeemSpecStr", "使用" + BigDecimalUtil.subZeroAndDot(productRedeemSpec.getNeedIntegral()) + "个金币可抵" + BigDecimalUtil.subZeroAndDot(productRedeemSpec.getDeductibleAmount()) + "元");
				}
			}
		}		
		return CallBackConstant.SUCCESS.callback(map);
	}
}
