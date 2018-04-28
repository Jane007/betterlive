package com.kingleadsw.betterlive.controller.wx.order;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.biz.LogisticsCompanyManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PayLogManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.ReceiverAddressManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.biz.UseLockManager;
import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.CustomerSourceUtil;
import com.kingleadsw.betterlive.util.JsonUtil;
import com.kingleadsw.betterlive.util.TokenProcessor;
import com.kingleadsw.betterlive.util.kuaidi.demo.Kuaidi100Util;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GroupJoinVo;
import com.kingleadsw.betterlive.vo.LogisticsCompanyVo;
import com.kingleadsw.betterlive.vo.LogisticsVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.PayLogVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.ReceiverAddressVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysDictVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
import com.kingleadsw.betterlive.vo.UserGroupVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 微信端下单
 * 2017-03-18 by chen
 */
@Controller
@RequestMapping(value = "/weixin/order")
public class WxOrderController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxOrderController.class);
	
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private ReceiverAddressManager receiverAddressManager;
	@Autowired
	private FreightManager freightManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private ActivityManager activityManager;
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private OrderProductManager orderProductManager; 
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private UseLockManager useLockManager; 
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private SalePromotionManager salePromotionManager;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private LogisticsCompanyManager logisticsCompanyManager;
	@Autowired
	private ProductLabelManager productLabelManager;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
    
	@Autowired
    private RedisService redisService;
	@Autowired
	private PostageManager postageManager;
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	@Autowired
	private CustomerManager customerManager;
	
	/**
	 * 个人中心查看全部订单
	 * 订单查询
	 */
	@RequestMapping(value="/queryLogiticsInfo")
	public ModelAndView queryLogiticsInfo(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("/weixin/order/logisticsInfo");
		
		String orderpro_id=req.getParameter("orderpro_id");
		if(StringUtils.isEmpty(orderpro_id)){
			mv.addObject("tipsTitle", "物流信息提示");
			mv.addObject("tipsContent", "您访问的物流信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		PageData pd=new PageData();
		pd.put("orderpro_id", orderpro_id);
		OrderProductVo orderProductVo=orderProductManager.queryOne(pd);
		if(orderProductVo==null){
			mv.addObject("tipsTitle", "订单信息提示");
			mv.addObject("tipsContent", "您访问的订单物流信息不存在或已被删除");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		//查询订单信息
		PageData pd2=new PageData();
		pd2.put("orderId", orderProductVo.getOrder_id());
		mv.addObject("orderVo",orderManager.findOrder(pd2));
		
		Map<String,Object> map = new HashMap<String, Object>();
		//查询所用物流公司
		if(StringUtils.isNotEmpty(orderProductVo.getCompany_code())){
			pd.put("companyCode", orderProductVo.getCompany_code());
			List<LogisticsCompanyVo> list=logisticsCompanyManager.queryList(pd);
			if(list!=null&&list.size()>0){
				mv.addObject("conpanyName", list.get(0).getCompanyName());
			}
			//查询物流状态
			// redis缓存
			map=queryKuaidiInfo(orderProductVo.getCompany_code(), orderProductVo.getLogistics_code());
		}
		
		if(map == null){
			map = new HashMap<String, Object>();
			map.put("code", 1020);
			map.put("msg", "暂无物流信息");
		}
		
		mv.addObject("logistics", map);
		mv.addObject("orderProductVo",orderProductVo);
		String type = "";
		PageData params=this.getPageData();
		if(StringUtil.isEmpty(params.getString("type"))){
			type = "0";
		}else{
			type = params.getString("type");
		}
		mv.addObject("type", type);
		
		return  mv;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object>  queryKuaidiInfo(String com,String num){
		String kuaidiInfo = redisService.getString(com+"_"+num);
    	logger.info("当前缓存的access_token为：  "+kuaidiInfo);
    	if (StringUtil.isNotNull(kuaidiInfo)) {  //缓存存在，直接返回
    		try {
				return (Map<String, Object>) JsonUtil.jsonToBean(kuaidiInfo, Map.class);
			} catch (Exception e) {
				logger.error("/weixin/order/queryKuaidiInfo --error", e);
				return null;
			}
    	}else{
    		Map<String,Object> map=Kuaidi100Util.queryKuaidiInfo(com,num);
    		redisService.setex(com+"_"+num, JsonUtil.toJsonString(map), 3600);
    		return map;
    	}
	}
	
	/**
	 * 个人中心查看全部订单
	 * 订单查询
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		
		CustomerVo customer = Constants.getCustomer(req);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		ModelAndView mv = new ModelAndView("/weixin/order/wx_myorders");
		PageData pd=this.getPageData();
		String type=pd.getString("status");
		mv.addObject("type",type);
		return  mv;
	}
	
	/**
	 * 查询全部订单
	 */                     
	@RequestMapping(value="/queryOrderAllJson")
	@ResponseBody
	public Map<String, Object> queryOrderAllJson(HttpServletRequest req, HttpServletResponse resp){
		
		PageData pd = this.getPageData();
		CustomerVo customerInfo = Constants.getCustomer(req);
		if(customerInfo == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		pd.put("customerId", String.valueOf(customerInfo.getCustomer_id()));
		logger.info("用户id为" + customerInfo.getCustomer_id() + "查询所有的订单");
		PageData pageData = new PageData();
		pageData.put("dictCode", "SYSTEM_ORDER_EXPIRE");
		SysDictVo sysDict = sysDictManager.selectByCode(pageData);
		if (null != sysDict && sysDict.getStatus() == 1) {
			pd.put("expiretime", sysDict.getDictValue());
		}
		
		List<OrderVo> listOrder = orderManager.findAllorder2ListPage(pd);     //订单
		if(listOrder == null){
			listOrder = new ArrayList<OrderVo>();
		}
		
		return CallBackConstant.SUCCESS.callbackPageInfo(listOrder, pd.get("pageView"));
	}
	
	
	/**
	 *  未支付订单详情
	 *  2017-02-27 by chen
	 */
	@RequestMapping(value = "/toPayOrderDetail")
    public ModelAndView toPayOrderDetail(HttpServletRequest request) throws Exception {  
		CustomerVo coustomerVo=Constants.getCustomer(request);
		if(coustomerVo == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		ModelAndView mv=new ModelAndView("/weixin/order/wx_pay_order_detail");
		
    	PageData pd=this.getPageData();
    	
    	//订单编号
		String orderCode=null;
		String orderId=null;
		OrderVo order=null;
		
		try {
			orderCode = pd.getString("orderCode");
			orderId = pd.getString("orderId");
			if(null==orderCode || "".equals(orderCode) ||  null==orderId || "".equals(orderId) ){
				logger.info(" 订单ID ："+orderCode+" 编号:"+orderCode);  
				throw new Exception();
			}else {
				logger.info("未支付订单详情====orderCode=="+orderCode+"----orderId---"+orderId);
				pd.put("customerId",String.valueOf(coustomerVo.getCustomer_id()));
				
				PageData pageData=new PageData();
				pageData.put("dictCode","SYSTEM_ORDER_EXPIRE");
				SysDictVo sysDict=sysDictManager.selectByCode(pageData);
				if(null !=sysDict&& sysDict.getStatus()==1 ){
					pd.put("expiretime",sysDict.getDictValue());
				}
				
				order=orderManager.findOrder(pd);
				if(null ==order ){
					logger.info(" 订单为空："+order);  
					mv.addObject("tipsTitle", "订单信息提示");
					mv.addObject("tipsContent", "您访问的订单信息不存在或已被删除");
					mv.setViewName("/weixin/fuwubc");
				}
			}
		} catch (Exception e) {
			logger.error("进入订单支付详情页面异常 :  订单ID或编号为空! ", e);  
		}
    	String type = "";
    	if(StringUtil.isEmpty(pd.getString("type"))){
    		type = "0";
    	}else{
    		type = pd.getString("type");
    	}
		mv.addObject("orderInfo",order);
		mv.addObject("type",type);
		return mv;  
    }
	
	/**
	 * 准备下单
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping("/addBuyOrder")
	public ModelAndView addBuyOrder(HttpServletRequest request,HttpServletResponse response) {
		String msg="addBuyOrder";
		logger.info(" /weixin/order/ "+msg+"  start ...");
		
		ModelAndView mv=new ModelAndView();
		
		PageData pd=this.getPageData();
		
		CustomerVo customer = Constants.getCustomer(request);
		if (null==customer || customer.getCustomer_id() == null) {
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		String key="readybuy_"+customer.getCustomer_id();
		
		logger.info("用户准备下单---customerid-"+customer.getCustomer_id());
		
		String isNew=pd.getString("isNew");
		if(null!=isNew && !"".equals(isNew) && "1".equals(isNew) ){
			pd.remove("isNew");
			request.getSession().removeAttribute(key);
		}
		
		String productId=pd.getString("productId");               //购买产品ID
		String productSpecId=pd.getString("productSpecId");       //商品规格
		String buyAmount=pd.getString("buyAmount");               //购买数量
		String userCouponId=pd.getString("couponId");               //使用优惠券
		String userSingleCouponId=pd.getString("userSingleCouponId");   //使用红包
		String useAddress=pd.getString("useAddress");               //选择地址的ID
		String returnType=pd.getString("returnType");				// 1普通商品，2限量  6团购单独购买
		String tzhuantiId =pd.getString("tzhuantiId");				//团购单独购买时专用，活动ID
		String orderSource = pd.getString("orderSource");			//订单来源
		orderSource = CustomerSourceUtil.getOrderSource(request, orderSource);
		
		float totalPrice = 0f;
		logger.info("用户【"+customer.getCustomer_id()+"】购买商品：" + productId + "，规格：" + productSpecId
				+ "数量：" + buyAmount + "，使用优惠券：" + useAddress);
		
		//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
		PageData pds=(PageData) request.getSession().getAttribute(key);
		if(null!=pds && pds.size()>0){
			productId=pds.getString("productId");               //购买产品ID
			productSpecId=pds.getString("productSpecId");       //商品规格
			buyAmount=pds.getString("buyAmount");               //购买数量
			returnType=pds.getString("returnType");
			orderSource=pds.getString("orderSource");
			pd.put("productId",productId);
			pd.put("productSpecId",productSpecId);
			pd.put("buyAmount",buyAmount);
			pd.put("returnType", returnType);
			pd.put("orderSource", orderSource);
			
			if(StringUtil.isEmpty(userCouponId)){
				userCouponId = pds.getString("userCouponId");
				pd.put("couponId",userCouponId);
			}
			if(StringUtil.isEmpty(userSingleCouponId)){
				userSingleCouponId = pds.getString("userSingleCouponId");
				pd.put("userSingleCouponId",pds.getString("userSingleCouponId"));
			}
			if(StringUtil.isEmpty(tzhuantiId)){
				tzhuantiId = pds.getString("tzhuantiId");
				pd.put("tzhuantiId", tzhuantiId);
			}
			if(null==useAddress || "".equals(useAddress)){
				useAddress=pds.getString("useAddress");               //选择地址
				pd.put("useAddress",useAddress);
			}
		}
		
		request.getSession().setAttribute(key,pd);
		if(StringUtil.isEmpty(returnType)){
			mv.setViewName("/weixin/404");
			return mv;
		}
		//查询用户默认地址，如果为空  就直接查询所有且获取第一个
		PageData pageData = new PageData();
		if(null==useAddress || "".equals(useAddress)){
			pageData.put("isDefault","1");
		}else{
			pageData.put("receriverId",useAddress);
		}
		pageData.put("customerId",String.valueOf(customer.getCustomer_id()));
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.queryOne(pageData);
		if(null == receiverAddressVo){
			pageData.remove("isDefault");
			List<ReceiverAddressVo> listReceive=receiverAddressManager.queryListPage(pageData);
			if(listReceive != null && listReceive.size()>0){
				receiverAddressVo=listReceive.get(0);
			}
		}
		
		Map<String, Object> deilver = null; //配送信息
		String province_id = ""; //配送地址省编码
		String city_id = ""; //配送地址城市编码
		String area_id = ""; //配送地址地区编码
		String is_deilver = "true";  //改订单是否支持配送
		String nonDeliveryMsg = "";
		if(null != receiverAddressVo){
			province_id = String.valueOf(receiverAddressVo.getProvinceId());
			city_id = String.valueOf(receiverAddressVo.getCityId());
			area_id = String.valueOf(receiverAddressVo.getAreaId());
		}
		
		//查询购买的商品价格信息
		PageData pagedata = new PageData();
		pagedata.put("productId", productId);
		pagedata.put("specId", productSpecId);
		pagedata.put("proStatus", 1);  //TODO:魔鬼数字，标识商品是已上架状态
		pagedata.put("specStatus", 1); //TODO:魔鬼数字，标识商品规格是有效的
		
		pagedata.put("status","1");
		ProductVo product = productManager.selectProductByOption(pagedata);
		
		if(null == product){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}	
		
		
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pagedata);
		if(specVo ==null){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品库存不足");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		specVo.setLabel_name(product.getLabelName());
		//如果选择了收货地址,则查询购物车中的商品是否都支持配送
		if(null != receiverAddressVo){
			deilver = shoppingCartManager.queryDeliver(String.valueOf(product.getProduct_id()), 
					province_id, city_id, area_id);
			if (deilver != null && "true".equals(deilver.get("vilidate"))) {
				specVo.setDeliver_status(1); //该商品配送
			} else {
				is_deilver = "false";
				specVo.setDeliver_status(0);
				nonDeliveryMsg += product.getProduct_name()+"、";
			}
		}
		
		
		specVo.setAmount(Integer.parseInt(buyAmount));
		
		logger.info("用户customerId为"+customer.getCustomer_id()+"准备购买产品----"+product.getProduct_name()+"---规格为名为--"+specVo.getSpec_name()
				+"-Spec_id为--"+specVo.getSpec_id()+"购买数量为--"+buyAmount);
		
		BigDecimal price = new BigDecimal(specVo.getSpec_price());  //实付金额
		price = price.multiply(new BigDecimal(buyAmount));//计算商品实付金额

		if(null == price){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "商品信息异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		BigDecimal totoalAmount = price; //商品总价格
		BigDecimal totalActivcityDis = BigDecimal.ZERO; //活动优惠金额
		totalPrice = price.floatValue();
		//如果有优惠价 重新计算实付价
		if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
			price = specVo.getDiscount_price().multiply(new BigDecimal(buyAmount));
			totalPrice = price.floatValue();
			totalActivcityDis = totoalAmount.subtract(price);
		}
		
		BigDecimal activityPrice=null;    //活动优惠多少钱 
		List<ProductSpecVo> listProduct=new ArrayList<ProductSpecVo>();
		
		PageData spepd = new PageData();
		spepd.put("productId", productId);
		spepd.put("activityFlag", 1);
		spepd.put("status", 1);
		spepd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		spepd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		if(StringUtil.isNotNull(tzhuantiId)){
			spepd.put("specialId", tzhuantiId);
		}
		SpecialVo specialVo = this.specialMananger.queryOneByProductId(spepd);
		if(specialVo != null){
			spepd.clear();
			spepd.put("productId", productId);
			spepd.put("activityId", specialVo.getSpecialId());
			spepd.put("specId", specVo.getSpec_id());
			ActivityProductVo ap = this.activityProductManager.queryOne(spepd);
			if(ap != null){
				activityPrice = new BigDecimal(ap.getActivity_price());
				specVo.setActivity_price(activityPrice+"");
			}
		}
	
		if(activityPrice != null){
			BigDecimal totalActivityPrice = activityPrice.multiply(new BigDecimal(buyAmount));
			totalActivcityDis = totoalAmount.subtract(totalActivityPrice);
			price = totalActivityPrice;
			totalPrice = price.floatValue();
		}
		
		PageData pdData = new PageData();
		pdData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
		pdData.put("productId", specVo.getProduct_id());
		pdData.put("specId", specVo.getSpec_id());
		ProductRedeemSpecVo redeemSpecVo = this.productRedeemSpecManager.queryOne(pdData);
		if(redeemSpecVo != null){
			specVo.setRedeemSpecId(redeemSpecVo.getRedeemSpecId());
			specVo.setRedeemDesc(BigDecimalUtil.subZeroAndDot(redeemSpecVo.getNeedIntegral())
					+ "个金币可抵￥" + redeemSpecVo.getDeductibleAmount());
			specVo.setNeedIntegral(redeemSpecVo.getNeedIntegral());
			specVo.setDeductibleAmount(redeemSpecVo.getDeductibleAmount());
		}
		
		listProduct.add(specVo);
		
		//满减活动
		PageData mcps = new PageData();
		mcps.put("productId", specVo.getProduct_id());
		mcps.put("specId", specVo.getSpec_id());
		SalePromotionVo salePromotion = salePromotionManager.queryOne(mcps);
		if(salePromotion != null) {
			if (totoalAmount.compareTo(salePromotion.getFullMoney()) >= 0) {// 如果总额大于满减的金额，就要减去多少钱
				price = price.subtract(salePromotion.getCutMoney());
				mv.addObject("fullCut",salePromotion.getCutMoney());
			}
			
			mv.addObject("salePromotion",salePromotion);
		}
		
		
		logger.info("***********************************   进入查询优惠券   ************************************************************  开始  ");
		
		//查询用户可用优惠券
		CouponInfoVo couponInfo=new CouponInfoVo();
		BigDecimal ifCouponMoney = BigDecimal.ZERO;
		String ifCouponProIds = "";
		String ifCouponSpecIds = "";
		if(product.getIfCoupon() == 0){	//允许使用优惠券和红包
			ifCouponMoney = totoalAmount;
			ifCouponProIds = productId;
			ifCouponSpecIds = productSpecId;
			PageData pagedatas = new PageData();
			pagedatas.put("canusecoupon","0");
			if(com.kingleadsw.betterlive.core.util.StringUtil.isNotNull(userCouponId)){
				pagedatas.put("couponId",userCouponId);
			}	
			
			//查询优惠券
			pagedatas.put("price", ifCouponMoney);
			pagedatas.put("customerId",customer.getCustomer_id().toString());
			List<CouponInfoVo> listCustomerCoupon = couponInfoManager.findListUserCoupon(pagedatas);
			
			if(StringUtil.isNotNull(userCouponId) && null!=listCustomerCoupon && listCustomerCoupon.size()>0){ //已选择优惠券
					couponInfo=listCustomerCoupon.get(0);
					price = price.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}else if(StringUtil.isEmpty(userCouponId) && listCustomerCoupon != null && listCustomerCoupon.size() > 0){
				couponInfo=selectCoupon(listCustomerCoupon,totoalAmount);
				if(couponInfo != null){
					price = price.subtract(new BigDecimal(couponInfo.getCoupon_money()));
				}
			}
	
			//查询单品红包
			PageData singlePd = new PageData();
			singlePd.put("productId", specVo.getProduct_id());
			singlePd.put("specId", specVo.getSpec_id());
			singlePd.put("customerId", customer.getCustomer_id());
			singlePd.put("startTime", new Date());
			singlePd.put("endTime", new Date());
			singlePd.put("status", 0);
			singlePd.put("fullMoney", ifCouponMoney);
			singlePd.put("canusecoupon", 0);
			if(StringUtils.isNotBlank(userSingleCouponId)){
				singlePd.put("userSingleId", userSingleCouponId);
			}
			UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(singlePd);
			if(uscVo!=null){
				price = price.subtract(uscVo.getCouponMoney());
				mv.addObject("singleCouponVo",uscVo);
			}
		}
		
		//查看用户礼品卡可用总余额
		BigDecimal canUseTotalMoney=giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
		//查看用户礼品卡锁定余额
		BigDecimal useLockMoney=new BigDecimal(0);
		float userLockMoney = useLockManager.findCustomerLockMoney(customer.getCustomer_id().toString());
		if(userLockMoney > 0){
			useLockMoney = new BigDecimal(userLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		if(useLockMoney.floatValue()>0){
			canUseTotalMoney = canUseTotalMoney.subtract(useLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		BigDecimal nextBuyFreePrice = BigDecimal.ZERO;  //再买多少免运费
		float freight = 0f;  //运费
		
		PageData postagePd = new PageData();
		postagePd.put("productId", productId);
		PostageVo postageVo = postageManager.queryOne(postagePd);
		if (postageVo != null) {
			if(totalPrice>= Float.valueOf(postageVo.getMeetConditions())){
				nextBuyFreePrice=new BigDecimal(0);
			}else{
				freight=Float.valueOf(String.valueOf(postageVo.getPostage()));
				if(freight > 0){
					nextBuyFreePrice=new BigDecimal(postageVo.getMeetConditions()).subtract(new BigDecimal(totalPrice));
					
					nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("满"+nextBuyFreePrice+"减运费");
				}
			}
		}
		
		pdData.remove("redeemType");
		pdData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ONE);
		pdData.put("specId", specVo.getSpec_id());
		ProductRedeemSpecVo goldSpecVo = this.productRedeemSpecManager.queryOne(pdData);
		long goldDeductSpecId = 0;
		if(goldSpecVo != null){
			goldDeductSpecId = goldSpecVo.getRedeemSpecId();
			
		}
		
		if(price.compareTo(BigDecimal.ZERO) == -1){
			price = BigDecimal.ZERO;
		}
		
		mv.addObject("customer",customer);
		mv.addObject("couponInfo",couponInfo);
		
		if(receiverAddressVo == null){
			receiverAddressVo = new ReceiverAddressVo();
		}
		
		mv.addObject("receiverAddress",receiverAddressVo);
		mv.addObject("totalActivityPrice", totalActivcityDis);	//活动优惠金额
		mv.addObject("productId",productId);                    //购买产品ID
		mv.addObject("productSpecId",productSpecId);            //商品规格
		mv.addObject("buyAmount",buyAmount);                    //购买数量
		mv.addObject("totalPrice",totoalAmount);				//商品总价
		mv.addObject("listProduct",listProduct);
		mv.addObject("returnType",returnType);                  //返回类型
		mv.addObject("ifCouponMoney", ifCouponMoney);		//参与使用优惠券和红包的金额
		mv.addObject("ifCouponProIds", ifCouponProIds);
		mv.addObject("ifCouponSpecIds", ifCouponSpecIds);
		mv.addObject("specialVo", specialVo);
		mv.addObject("price",price);						//实付金额
		mv.addObject("is_deilver", is_deilver);
		mv.addObject("deilver", deilver);
		if(nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)){
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length()-1) + "】不支持配送";
		}
		mv.addObject("nonDeliveryMsg", nonDeliveryMsg); //不支持配送提示消息
		mv.addObject("freeFreight",nextBuyFreePrice);       	//在满多少免邮费
		mv.addObject("freight",freight);       					//邮费
		mv.addObject("userMoney",canUseTotalMoney);      //用户剩余可用余额
		mv.addObject("tzhuantiId", tzhuantiId);
		mv.addObject("orderSource", orderSource);
		mv.addObject("goldDeductSpecId", goldDeductSpecId);
		mv.addObject("myIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		mv.addObject("minLimitIntegral", IntegralConstants.MIN_USE_THRESHOLD);
		mv.addObject("maxLimitIntegral", IntegralConstants.MAX_USE_THRESHOLD);
		mv.addObject("integralDeductionRate", IntegralConstants.DEDUCTION_RATE);
		
		//创建一个表单token
		String token = TokenProcessor.getInstance().generateToken(request);
		mv.addObject("token", token);
		
		logger.info(" /weixin/order/addBuyOrder  end ...");
		
		mv.setViewName("weixin/order/wx_buy_order");
		
		return mv;
	}
	
	/**
	 * app1.1 团购准备下单
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping(value = "/addOrderByGroup")
	public ModelAndView addOrderByGroup(HttpServletRequest request,HttpServletResponse response) {
		logger.info(" /weixin/order/addOrderByGroup  start ...");
		ModelAndView mv=new ModelAndView("weixin/order/wx_buy_ordercar");
		
		PageData pd=this.getPageData();
		
		CustomerVo customer = Constants.getCustomer(request);
		if (null==customer || customer.getCustomer_id() == null) {
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		logger.info("用户准备下单---customerid-"+customer.getCustomer_id());
		
		String productId=pd.getString("productId");               //购买产品ID
		String userGroupId=pd.getString("userGroupId");				//开团信息，为空或0，代表开团否则参团
		String productSpecId=pd.getString("productSpecId");       //商品规格
		String buyAmount=pd.getString("buyAmount");               //购买数量
		String useAddress=pd.getString("useAddress");               //选择地址的ID
		String specialId=pd.getString("specialId");					//活动ID
		String orderSource=pd.getString("orderSource");				//订单来源
		orderSource=CustomerSourceUtil.getOrderSource(request, orderSource);
		
		logger.info("用户【"+customer.getCustomer_id()+"】购买商品：" + productId + "，规格：" + productSpecId
				+ "数量：" + buyAmount + "，使用优惠券：" + useAddress+"团购ID"+ specialId);
		
		String key="readybuy_"+customer.getCustomer_id();
		
		logger.info("用户准备下单---customerid-"+customer.getCustomer_id());
		
		String isNew=pd.getString("isNew");
		if(null!=isNew && !"".equals(isNew) && "1".equals(isNew) ){
			pd.remove("isNew");
			request.getSession().removeAttribute(key);
		}
		
		//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
		PageData pds=(PageData) request.getSession().getAttribute(key);
		if(null!=pds && pds.size()>0){
			productId=pds.getString("productId");               //购买产品ID
			productSpecId=pds.getString("productSpecId");       //商品规格
			buyAmount=pds.getString("buyAmount");               //购买数量
			userGroupId=pds.getString("userGroupId");
			specialId=pds.getString("specialId");
			orderSource=pds.getString("orderSource");
			pd.put("productId",productId);
			pd.put("productSpecId",productSpecId);
			pd.put("buyAmount",buyAmount);
			pd.put("userGroupId",userGroupId);
			pd.put("specialId", specialId);
			pd.put("orderSource", orderSource);
			if(null==useAddress || "".equals(useAddress)){
				useAddress=pds.getString("useAddress");               //选择地址
				pd.put("useAddress",useAddress);
			}
		}
		
		request.getSession().setAttribute(key,pd);
		
		if(StringUtil.isEmpty(pd.getString("productId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isEmpty(pd.getString("productSpecId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isEmpty(pd.getString("buyAmount"))){
			mv.addObject("tipsTitle", "下单信息提示");
			mv.addObject("tipsContent", "您还没有选择商品下单数量");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isEmpty(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您还没有选择商品规格");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		PageData spepd = new PageData();
		spepd.put("specialId", specialId);
		spepd.put("productId", productId);
		spepd.put("specialType", 3);
		spepd.put("status", 1);
		spepd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		spepd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		SpecialVo specialVo = this.specialMananger.queryOneByProductId(spepd);
		if(specialVo == null){
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "该活动已结束啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		PageData propd = new PageData();
		propd.put("productId", productId);
		propd.put("status", 1);
		ProductVo product = productManager.selectProductByOption(propd);
		
		if(null == product){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		propd.put("specStatus", 1);
		propd.put("specId", productSpecId);
		propd.put("proStatus", 1);
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(propd);
		if(specVo ==null){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品库存不足");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		propd.put("activityId", specialVo.getSpecialId());
		propd.put("specId", productSpecId);
		ActivityProductVo actp = this.activityProductManager.queryOne(propd);
		if(actp == null){
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "该活动已结束啦~");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		
		propd.put("specialId", specialId);
		SysGroupVo sysgroup = sysGroupManager.queryOne(propd);
		if(sysgroup == null){
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "该活动已结束啦~");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		mv.addObject("sysgroup", sysgroup);
		if(StringUtil.isNotNull(userGroupId) && Integer.parseInt(userGroupId) > 0){
			propd.put("userGroupId", userGroupId);
			UserGroupVo usergroup = this.userGroupManager.queryOne(propd);
			if(usergroup == null || usergroup.getStatus() != 1){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "此团已结束");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "参团人数已满");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			propd.put("customerId", customer.getCustomer_id());
			GroupJoinVo groupJoin = groupJoinManager.queryOne(propd);
			if(groupJoin != null){ //当前用户已参团
				if(specVo.getLimit_max_copy() > 0 && specVo.getLimit_end_time() != null
						&& specVo.getLimit_start_time() != null){
					Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
					Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
					if((dt.after(new Date())&&start.before(new Date()))
							&& groupJoin.getTotalBuyNum() >= specVo.getLimit_max_copy()){
						mv.addObject("tipsTitle", "团购信息提示");
						mv.addObject("tipsContent", "购买份数已达上限");
						mv.setViewName("/weixin/fuwubc");
						return mv;
					}
				}
				mv.addObject("groupJoin", groupJoin);
			}
		}else{	//开团
			if(sysgroup.getJoinCopy() >= sysgroup.getGroupCopy()){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "活动参与名额已满");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
		}
		
		specVo.setActivity_price(actp.getActivity_price());
		specVo.setLabel_name(product.getLabelName());
		
		//查询用户默认地址，如果为空  就直接查询所有且获取第一个
		PageData pageData = new PageData();
		if(null==useAddress || "".equals(useAddress)){
			pageData.put("isDefault","1");
		}else{
			pageData.put("receriverId",useAddress);
		}
		pageData.put("customerId",String.valueOf(customer.getCustomer_id()));
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.queryOne(pageData);
		if(null == receiverAddressVo){
			pd.remove("isDefault");
			List<ReceiverAddressVo> listReceive=receiverAddressManager.queryListPage(pageData);
			if(listReceive != null && listReceive.size()>0){
				receiverAddressVo=listReceive.get(0);
			}
		}
		
		Map<String, Object> deilver = null; //配送信息
		String province_id = ""; //配送地址省编码
		String city_id = ""; //配送地址城市编码
		String area_id = ""; //配送地址地区编码
		String is_deilver = "true";  //改订单是否支持配送
		String nonDeliveryMsg = "";
		if(null != receiverAddressVo){
			province_id = String.valueOf(receiverAddressVo.getProvinceId());
			city_id = String.valueOf(receiverAddressVo.getCityId());
			area_id = String.valueOf(receiverAddressVo.getAreaId());
		}
		
		//如果选择了收货地址,则查询购物车中的商品是否都支持配送
		if(null != receiverAddressVo){
			deilver = shoppingCartManager.queryDeliver(String.valueOf(product.getProduct_id()), 
					province_id, city_id, area_id);
			if (deilver != null && "true".equals(deilver.get("vilidate"))) {
				specVo.setDeliver_status(1); //该商品配送
			} else {
				is_deilver = "false";
				specVo.setDeliver_status(0);
				nonDeliveryMsg += product.getProduct_name()+"、";
			}
		}
		
		
		specVo.setAmount(Integer.parseInt(buyAmount));
		
		logger.info("用户customerId为"+customer.getCustomer_id()+"准备购买产品----"+product.getProduct_name()+"---规格为名为--"+specVo.getSpec_name()
				+"-Spec_id为--"+specVo.getSpec_id()+"购买数量为--"+buyAmount);
		
		BigDecimal price = new BigDecimal(specVo.getSpec_price());  //实付金额
		price = price.multiply(new BigDecimal(buyAmount));//计算商品实付金额
		
		if(null == price){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "商品金额异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		BigDecimal totalPrice = price;
	
		price = new BigDecimal(actp.getActivity_price()).multiply(new BigDecimal(buyAmount));
		BigDecimal totalActivityPrice = totalPrice.subtract(price);
		//查看用户礼品卡可用总余额
		BigDecimal canUseTotalMoney=giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
		//查看用户礼品卡锁定余额
		BigDecimal useLockMoney=new BigDecimal(0);
		float userLockMoney = useLockManager.findCustomerLockMoney(customer.getCustomer_id().toString());
		if(userLockMoney > 0){
			useLockMoney = new BigDecimal(userLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		if(useLockMoney.floatValue()>0){
			canUseTotalMoney = canUseTotalMoney.subtract(useLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		

		BigDecimal nextBuyFreePrice = BigDecimal.ZERO;  //再买多少免运费
		float freight = 0f;  //运费
		
		PageData postagePd = new PageData();
		postagePd.put("productId",productId);
		PostageVo postageVo = postageManager.queryOne(postagePd);
		if (postageVo != null) {
			float totalPrices = price.floatValue();
			if(totalPrices>= Float.valueOf(postageVo.getMeetConditions())){
				nextBuyFreePrice=new BigDecimal(0);
			}else{
				freight=Float.valueOf(String.valueOf(postageVo.getPostage()));
				if(freight > 0){
					nextBuyFreePrice=new BigDecimal(postageVo.getMeetConditions()).subtract(new BigDecimal(totalPrices));
					
					nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("满"+nextBuyFreePrice+"减运费");
					price = price.add(new BigDecimal(freight));
				}
			}
		}
		List<ProductSpecVo> listProduct=new ArrayList<ProductSpecVo>();
		listProduct.add(specVo);
		if(price.compareTo(BigDecimal.ZERO) == -1){
			price = BigDecimal.ZERO;
		}
		if(receiverAddressVo == null){
			receiverAddressVo = new ReceiverAddressVo();
		}
		mv.addObject("customer",customer);
		mv.addObject("receiverAddress",receiverAddressVo);
		mv.addObject("productSpecId",productSpecId);            //商品规格
		mv.addObject("buyAmount",buyAmount);                    //购买数量
		mv.addObject("totalPrice",totalPrice);				   //商品总价
		mv.addObject("totalActivityPrice", totalActivityPrice);	//活动优惠金额
		mv.addObject("price",price);						//实付金额
		mv.addObject("productSpec",specVo);		
		mv.addObject("product",product);
		mv.addObject("listProduct", listProduct);
		mv.addObject("specialVo",specialVo);			//活动
		mv.addObject("is_deilver", is_deilver);
		mv.addObject("deilver", deilver);
		if(nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)){
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length()-1) + "】不支持配送";
		}
		mv.addObject("nonDeliveryMsg", nonDeliveryMsg); //不支持配送提示消息
		mv.addObject("freeFreight",nextBuyFreePrice);       	//在满多少免邮费
		mv.addObject("freight",freight);       					//邮费
		mv.addObject("userMoney",canUseTotalMoney);      //用户剩余可用余额
		mv.addObject("returnType",4);                          //返回类型
		mv.addObject("userGroupId", userGroupId);
		mv.addObject("orderSource", orderSource);
		//创建一个表单token
		String token = TokenProcessor.getInstance().generateToken(request);
		mv.addObject("token", token);
		mv.setViewName("weixin/order/wx_buy_order");

		logger.info(" /weixin/order/addOrderByGroup  end ...");
		
		return mv;
	}
	
	
	/**
	 * 线下准备下单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addXXBuyOrder")
	public ModelAndView addXXBuyOrder(HttpServletRequest request,HttpServletResponse response) {
		String msg="addXXBuyOrder";
		logger.info(" /weixin/order/ "+msg+"  start ...");
		
		ModelAndView mv=new ModelAndView();
		
		PageData pd=this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		String key="readybuy_"+customer.getCustomer_id();
		logger.info("用户准备下单---customerid-"+customer.getCustomer_id());
		
		String isNew=pd.getString("isNew");
		if(null!=isNew && !"".equals(isNew) && "1".equals(isNew) ){
			pd.remove("isNew");
			request.getSession().removeAttribute(key);
		}
		
		String productId=pd.getString("productId");               //购买产品ID
		String extensionType=pd.getString("extension_type");      //推荐类型，1：每周新品；2：人气推荐
		String productSpecId=pd.getString("productSpecId");       //商品规格
		String buyAmount=pd.getString("buyAmount");               //购买数量
		String useCoupon=pd.getString("useCoupon");               //使用优惠券
		String useAddress=pd.getString("useAddress");               //选择地址
		String orderSource=pd.getString("orderSource");				//订单来源
		orderSource=CustomerSourceUtil.getOrderSource(request, orderSource);
		
		logger.info("用户【"+customer.getCustomer_id()+"】购买商品：" + productId + "，规格：" + productSpecId
				+ "数量：" + buyAmount + "，使用优惠券：" + useAddress);
		
		//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
		PageData pds=(PageData) request.getSession().getAttribute(key);
		if(null!=pds && pds.size()>0){
			productId=pds.getString("productId");               //购买产品ID
			extensionType=pds.getString("extension_type");      //推荐类型，1：每周新品；2：人气推荐
			productSpecId=pds.getString("productSpecId");       //商品规格
			buyAmount=pds.getString("buyAmount");               //购买数量
			orderSource=pds.getString("orderSource"); 
			pd.put("productId",productId);
			pd.put("extension_type",extensionType);
			pd.put("productSpecId",productSpecId);
			pd.put("buyAmount",buyAmount);
			pd.put("orderSource", orderSource);
			if(null==useCoupon || "".equals(useCoupon)){
				useCoupon=pds.getString("useCoupon");               //使用优惠券
				pd.put("useCoupon",useCoupon);
			}
			
			if(null==useAddress || "".equals(useAddress)){
				useAddress=pds.getString("useAddress");               //选择地址
				pd.put("useAddress",useAddress);
			}
		}
		
		request.getSession().setAttribute(key,pd);
		
		//查询用户默认地址，如果为空  就直接查询所有且获取第一个
		PageData pageData = new PageData();
		if(null==useAddress || "".equals(useAddress)){
			pageData.put("isDefault","1");
		}else{
			pageData.put("receriverId",useAddress);
		}
		pageData.put("customerId",String.valueOf(customer.getCustomer_id()));
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.queryOne(pageData);
		if(null == receiverAddressVo){
			pd.remove("isDefault");
			List<ReceiverAddressVo> listReceive=receiverAddressManager.queryListPage(pageData);
			if(listReceive != null && listReceive.size()>0){
				receiverAddressVo=listReceive.get(0);
			}
		}
	
		//查询购买的商品价格信息
		PageData pagedata = new PageData();
		pagedata.put("productId", productId);
		pagedata.put("specId", productSpecId);
		pagedata.put("proStatus", 1);  //TODO:魔鬼数字，标识商品是已上架状态
		pagedata.put("specStatus", 1); //TODO:魔鬼数字，标识商品规格是有效的
		
		pagedata.put("status","1");
		ProductVo product = productManager.selectProductByOption(pagedata);
		
		if(null == product){
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您下单的商品已下架");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}	
		
		
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pagedata);
		List<ProductSpecVo> listProduct=new ArrayList<ProductSpecVo>();
		
		int count = 0; 					//计算总共多少活动
		
		if(product!=null&&specVo!=null){
			specVo.setDeliver_status(1); //该商品配送
			
			specVo.setAmount(Integer.parseInt(buyAmount));
			
			logger.info("用户customerId为"+customer.getCustomer_id()+"准备购买产品----"+product.getProduct_name()+"---规格为名为--"+specVo.getSpec_name()
					+"-Spec_id为--"+specVo.getSpec_id()+"购买数量为--"+buyAmount);
	
			listProduct.add(specVo);
		}
		
		BigDecimal price = null;  //商品总价格
		//折扣金额
		BigDecimal zhekou = new BigDecimal("-1");
		price = new BigDecimal(specVo.getSpec_price());
		
		 //计算商品总价格
		price = price.multiply(new BigDecimal(buyAmount));   
		
		if(StringUtil.isNotNull(specVo.getActivity_price()) && new BigDecimal(specVo.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
			zhekou = new BigDecimal(specVo.getActivity_price()).multiply(new BigDecimal(buyAmount));
		}else if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
			zhekou = specVo.getDiscount_price().multiply(new BigDecimal(buyAmount));
		}
		
		if(null == price){
			mv.setViewName("/weixin/order/wx_myorders");
			return mv;	
		}
		
		BigDecimal nextBuyFreePrice = BigDecimal.ZERO;  //再买多少免运费
		float freight = 0f;  //运费
		//查看用户礼品卡可用总余额
		BigDecimal canUseTotalMoney=giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
		
		mv.addObject("customer",customer);
		mv.addObject("receiverAddress",receiverAddressVo);
		mv.addObject("zhekou",zhekou);
		
		mv.addObject("productId",productId);                    //购买产品ID
		mv.addObject("extension_type",extensionType);           //推荐类型，1：每周新品；2：人气推荐
		mv.addObject("productSpecId",productSpecId);             //商品规格
		mv.addObject("buyAmount",buyAmount);                    //购买数量
		
		mv.addObject("listProduct",listProduct);
		mv.addObject("returnType",3);                          //返回类型3线下活动
		
		mv.addObject("count",count);
		
		mv.addObject("price",price);
		mv.addObject("freeFreight",nextBuyFreePrice);       	//在满多少免邮费
		mv.addObject("freight",freight);       					//邮费
		mv.addObject("userMoney",canUseTotalMoney);      //用户剩余可用余额
		String token = TokenProcessor.getInstance().generateToken(request);
		mv.addObject("token", token);
		mv.addObject("orderSource", orderSource);
		logger.info(" /weixin/order/ "+msg+"  end ...");
		
		mv.setViewName("weixin/order/wx_xx_buy_order");
		
		return mv;
	}
	
	
	
	/**
	 * 如果存在 可用的优惠券（未被锁定）,取最大面值的优惠券 
	 * @param listCustomerCoupon 用户可以使用的优惠券
	 * @param totalPrice 订单总金额
	 * @return
	 */
	private CouponInfoVo selectCoupon(List<CouponInfoVo> listCustomerCoupon,BigDecimal totalPrice){
		if(listCustomerCoupon==null || listCustomerCoupon.size()==0){
			return null;
		}

		List<CouponInfoVo> canCoupons=new ArrayList<CouponInfoVo>();
		for(CouponInfoVo vo:listCustomerCoupon){
			if((vo.getStart_money()==null ||totalPrice.intValue()>=vo.getStart_money())&&vo.getCoupon_money()!=null&&vo.getCoupon_money()>0){
				canCoupons.add(vo);
			}
		}
		
		if(canCoupons==null||canCoupons.size()==0){
			return null;
		}

		Collections.sort(canCoupons, new Comparator<Object>(){  
            public int compare(Object arg0, Object arg1) {  
            	CouponInfoVo dept1=(CouponInfoVo)arg0;  
            	CouponInfoVo dept2=(CouponInfoVo)arg1;  
                return dept1.compareTo(dept2);  
        }});
		return canCoupons.get(0);
	}
	
	
	
	/**
	 * 点击购物车结算
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addBuyOrders")
	public ModelAndView addBuyOrders(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("weixin/order/wx_buy_ordercar");
		
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		if (null==customer || customer.getCustomer_id() == null) {
			return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		String key="readybuy_"+customer.getCustomer_id();
		
		String isNew = pd.getString("isNew");
		if(null!=isNew && !"".equals(isNew) && "1".equals(isNew) ){
			pd.remove("isNew");
			request.getSession().removeAttribute(key);
		}
		
		String cartIds = pd.getString("cartIds");     //购物车的Id,每件商品就是一个购物车  
		if(cartIds.length() > 0 && (cartIds.substring(cartIds.length()-1, cartIds.length())).equals(",")){
			cartIds = cartIds.substring(0, cartIds.length() - 1);
			pd.put("cartIds", cartIds);
		}
		Integer customerId = customer.getCustomer_id();
		pd.put("customerId",String.valueOf(customer.getCustomer_id()));
		
		String userCouponId = pd.getString("couponId"); // 使用优惠券
		String userSingleCouponId = pd.getString("userSingleCouponId"); // 使用红包
		String useAddress = pd.getString("useAddress"); // 选择地址的ID
		String orderSource = "weixin_cart"; // 订单来源
		orderSource = CustomerSourceUtil.checkOrderSource(request, "", "", orderSource);
		
		//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
		PageData pds = (PageData) request.getSession().getAttribute(key);
		if (null != pds && pds.size() > 0) {
			cartIds=pds.getString("cartIds");               //购买产品ID
			orderSource=pds.getString("orderSource");
			pd.put("cartIds",cartIds);
			pd.put("orderSource", orderSource);
			
			if(StringUtil.isEmpty(userCouponId)){
				pd.put("couponId",pds.getString("userCouponId"));
			}
			if(StringUtil.isEmpty(userSingleCouponId)){
				pd.put("userSingleCouponId",pds.getString("userSingleCouponId"));
			}
			
			if(null==useAddress || "".equals(useAddress)){
				useAddress=pds.getString("useAddress");               //选择地址
				pd.put("useAddress",useAddress);
			}
		}
		if( null == cartIds || "".equals(cartIds)){
			mv.addObject("tipsTitle", "下单提示");
			mv.addObject("tipsContent", "您还没有选择商品");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		request.getSession().setAttribute(key, pd);
		
		
		//查询用户默认地址，如果为空  就直接查询所有且获取第一个
		PageData pagedata = new PageData();
		if(StringUtil.isNull(useAddress)){
			pagedata.put("isDefault","1");
		}else{
			pagedata.put("receriverId",useAddress);
		}
		pagedata.put("customerId",customerId);
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.queryOne(pagedata);
		if(null == receiverAddressVo){
			pagedata.remove("isDefault");
			List<ReceiverAddressVo> listReceive=receiverAddressManager.queryListPage(pagedata);
			if(listReceive != null && listReceive.size()>0){
				receiverAddressVo=listReceive.get(0);
			}
		}

		Map<String, Object> deilver = null; //配送信息
		String province_id = ""; //配送地址省编码
		String city_id = ""; //配送地址城市编码
		String area_id = ""; //配送地址地区编码
		if(null != receiverAddressVo){
			province_id = String.valueOf(receiverAddressVo.getProvinceId());
			city_id = String.valueOf(receiverAddressVo.getCityId());
			area_id = String.valueOf(receiverAddressVo.getAreaId());
		}
		
		//根据客户ID与购物车Id查询需要购买的所有商品
		List<ShoppingCartVo> listShoppingCartVo = shoppingCartManager.queryListShoppingCart(pd);     
		
		BigDecimal totalPay = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal fullCut = new BigDecimal(0);
		BigDecimal totalActivcityDis = new BigDecimal(0);
		BigDecimal totalFullPrice = new BigDecimal(0);  //计算购物车内需要计算免运费条件的商品总价格
		String meet_conditions = "";         //满包邮的条件
		float postage = 0.0f;
		
		List<ProductSpecVo> listProduct = new ArrayList<ProductSpecVo>();
		ProductSpecVo productSpecVo = null;
		int count = 0;			//计算活动数
		//折扣金额
		String is_deilver = "true";
		String nonDeliveryMsg = "";
		String shelves = "";
		String productIds = "";
		String specIds = "";
		String ifCouponProIds = "";
		String ifCouponSpecIds = "";
		BigDecimal ifCouponMoney = BigDecimal.ZERO;
		String goldDeductSpecIds = ""; //普通金币抵扣规格ID集合
		if(listShoppingCartVo != null && listShoppingCartVo.size() > 0){
			
			for (int i = 0; i < listShoppingCartVo.size(); i++) {
				int productId = listShoppingCartVo.get(i).getProduct_id();
				int specId = listShoppingCartVo.get(i).getSpec_id();
				int amount = listShoppingCartVo.get(i).getAmount();
				productIds = productIds + productId + ",";
				specIds = specIds + specId + ",";
				
				//查询是否存在活动 ，如果是 就加入活动价
				PageData pdData=new PageData();
				pdData.put("productId", productId);
				pdData.put("specId", specId);
				pdData.put("proStatus", 1);
				pdData.put("specStatus", 1);
				ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pdData);
				if(psvo == null){
					shelves = listShoppingCartVo.get(i).getProduct_name() + "," + shelves;
					continue;
				}
				String specPrice = psvo.getSpec_price();
				
				BigDecimal discountPrice = new BigDecimal("-1");
				BigDecimal discount = new BigDecimal("-1");
				if(psvo.getDiscount_price() != null && psvo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
					discountPrice = psvo.getDiscount_price();
					discount = psvo.getDiscount_price();
				}
			
				String activityPrice = "";
				//专题
				PageData specialParams = new PageData();
				specialParams.put("status", 1);
				specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("productId", productId);
				SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
				if(specialVo != null){
					specialParams.clear();
					specialParams.put("activityId", specialVo.getSpecialId());
					specialParams.put("productId", productId);
					specialParams.put("specId", specId);
					ActivityProductVo ap = this.activityProductManager.queryOne(specialParams);
					if(ap != null){
						count++;
						activityPrice = ap.getActivity_price();
						if(StringUtil.isNotNull(ap.getDiscount_price()) && new BigDecimal(ap.getDiscount_price()).compareTo(BigDecimal.ZERO) >= 0){
							discountPrice = psvo.getDiscount_price();
							discount = new BigDecimal(ap.getDiscount_price());
						}
						if(StringUtil.isNotNull(ap.getActivity_price()) && new BigDecimal(ap.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
							activityPrice = ap.getActivity_price();
							discount = new BigDecimal(ap.getActivity_price());
						}
						specPrice = ap.getSpec_price();
					}
				}
				
				BigDecimal thePrice = new BigDecimal(specPrice).multiply(new BigDecimal(amount));
				totalPrice = totalPrice.add(thePrice);
				if (psvo.getIfCoupon() == 0) { // 允许参与使用优惠券或红包的金额
					ifCouponMoney = ifCouponMoney.add(thePrice);
					ifCouponProIds += productId + ",";
					ifCouponSpecIds += specId + ",";
				}
				
				if (discount.compareTo(BigDecimal.ZERO) >= 0) {
					totalPay = totalPay.add(discount.multiply(new BigDecimal(amount)));
					totalActivcityDis = totalActivcityDis.add(thePrice.subtract(discount.multiply(new BigDecimal(amount))));
				} else {
					totalPay = totalPay.add(thePrice);
				}
				
				productSpecVo = new ProductSpecVo();
				productSpecVo.setCart_id(listShoppingCartVo.get(i).getCart_id());
				productSpecVo.setProduct_id(productId);
				productSpecVo.setProduct_name(listShoppingCartVo.get(i).getProduct_name());
				productSpecVo.setActivity_price(activityPrice);
				productSpecVo.setDiscount_price(discountPrice);
				productSpecVo.setAmount(listShoppingCartVo.get(i).getAmount());
				productSpecVo.setSpec_id(listShoppingCartVo.get(i).getSpec_id());
				productSpecVo.setSpec_name(listShoppingCartVo.get(i).getSpec_name());
				productSpecVo.setSpec_img(listShoppingCartVo.get(i).getSpec_img());
				productSpecVo.setSpec_price(specPrice);
				
				PageData prsData = new PageData();
				prsData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
				prsData.put("productId", productId);
				prsData.put("specId", specId);
				ProductRedeemSpecVo redeemSpecVo = this.productRedeemSpecManager.queryOne(prsData);
				if(redeemSpecVo != null){
					productSpecVo.setRedeemSpecId(redeemSpecVo.getRedeemSpecId());
					productSpecVo.setRedeemDesc(BigDecimalUtil.subZeroAndDot(redeemSpecVo.getNeedIntegral())
							+ "个金币可抵￥" + redeemSpecVo.getDeductibleAmount());
					productSpecVo.setNeedIntegral(redeemSpecVo.getNeedIntegral());
					productSpecVo.setDeductibleAmount(redeemSpecVo.getDeductibleAmount());
				}
				
				prsData.remove("redeemType");
				prsData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ONE);
				ProductRedeemSpecVo goldSpecVo = this.productRedeemSpecManager.queryOne(prsData);
				if(goldSpecVo != null){
					goldDeductSpecIds += goldSpecVo.getRedeemSpecId() + ",";
					productSpecVo.setGoldDeductSpecId(goldSpecVo.getRedeemSpecId());
				}
				
				PageData plpd = new PageData();
				plpd.put("productId", psvo.getProduct_id());
				plpd.put("nowTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				plpd.put("status", 0);
				ProductLabelVo productLabelVo = productLabelManager.queryOne(plpd);
				if(productLabelVo != null && StringUtil.isNotNull(productLabelVo.getLabelTitle())){
					productSpecVo.setLabel_name(productLabelVo.getLabelTitle()); //商品标签
				}
				
				//满减活动
				PageData mcps = new PageData();
				mcps.put("productId", productId);
				mcps.put("specId", specId);
				SalePromotionVo salePromotion = salePromotionManager.queryOne(mcps);
				if(salePromotion != null && fullCut.compareTo(BigDecimal.ZERO) == 0) {
					if (totalPrice.compareTo(salePromotion.getFullMoney()) >= 0) {// 如果总额大于满减的金额，就要减去多少钱
						totalPay = totalPay.subtract(salePromotion.getCutMoney());
						fullCut = salePromotion.getCutMoney();
					}
				}
				
				//如果选择了收货地址,则查询购物车中的商品是否都支持配送
				if (null != receiverAddressVo) {
					deilver = shoppingCartManager.queryDeliver(String.valueOf(productId), province_id, city_id, area_id);
					if (deilver != null && "true".equals(deilver.get("vilidate"))) {
						productSpecVo.setDeliver_status(1); // 该商品配送
					} else {
						is_deilver = "false";
						productSpecVo.setDeliver_status(0);
						nonDeliveryMsg += listShoppingCartVo.get(i).getProduct_name() + "、";
					}
				}
				listProduct.add(productSpecVo);
				PageData postagePd = new PageData();
				postagePd.put("productId", listShoppingCartVo.get(i).getProduct_id());
				PostageVo postageVo = postageManager.queryOne(postagePd);
				if (postageVo != null) {
					if(discount.compareTo(BigDecimal.ZERO) >= 0){
						totalFullPrice = totalFullPrice.add(discount.multiply(new BigDecimal(amount)));
					}else {
						totalFullPrice = totalFullPrice.add(thePrice);
					}
					meet_conditions = postageVo.getMeetConditions();
					postage = Float.valueOf(postageVo.getPostage());
				}
			}
		}
		
		if(null == totalPay){
			mv.addObject("tipsTitle", "下单提示");
			mv.addObject("tipsContent", "订单金额异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		CouponInfoVo couponInfo = new CouponInfoVo();
		if(ifCouponMoney.compareTo(BigDecimal.ZERO) > 0) {
			
			PageData pagedatas=new PageData();
			pagedatas.put("canusecoupon","0");
			pagedatas.put("price", ifCouponMoney);
			pagedatas.put("customerId",customer.getCustomer_id().toString());
			if(StringUtil.isNotNull(userCouponId)){
				pagedatas.put("couponId", userCouponId);
			}
			//查询用户可用优惠券
			List<CouponInfoVo> listCustomerCoupon = couponInfoManager.findListUserCoupon(pagedatas);
			if(listCustomerCoupon != null && listCustomerCoupon.size() > 0){
				//选择一个金额最大的优惠券
				couponInfo = listCustomerCoupon.get(0);
				totalPay = totalPay.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}
			
			productIds = productIds.substring(0, productIds.lastIndexOf(","));
			specIds = specIds.substring(0, specIds.lastIndexOf(","));
			if(ifCouponProIds.length() > 0 && ifCouponSpecIds.length() > 0){
				ifCouponProIds = ifCouponProIds.substring(0, ifCouponProIds.lastIndexOf(","));
				ifCouponSpecIds = ifCouponSpecIds.substring(0, ifCouponSpecIds.lastIndexOf(","));
			}
			
			PageData singlePd = new PageData();
			singlePd.put("productIds", productIds);
			singlePd.put("specIds", specIds);
			singlePd.put("customerId", customer.getCustomer_id());
			singlePd.put("startTime", new Date());
			singlePd.put("endTime", new Date());
			singlePd.put("status", 0);
			singlePd.put("fullMoney", ifCouponMoney);
			singlePd.put("canusecoupon", 0);
			if(StringUtils.isNotBlank(userSingleCouponId)){
				singlePd.put("userSingleId", userSingleCouponId);
			}
			UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(singlePd);
			if (uscVo != null) {
				if (totalPrice.compareTo(uscVo.getFullMoney()) >= 0) { // 满了多少钱才可以使用
					totalPay = totalPay.subtract(uscVo.getCouponMoney());
					mv.addObject("singleCouponVo", uscVo);
				}
			}
		}
		
		logger.info("用户id为" + customer.getCustomer_id() + "结算总价为--" + totalPrice);
		
		//还差多少钱免运费
		BigDecimal nextBuyFreePrice = BigDecimal.ZERO;
		if (StringUtil.isNotNull(meet_conditions)) {
			//如果超过满减，运费就为0;否则计算 还差多少钱满减
			float totalFullPrices = totalFullPrice.floatValue();
			if (totalFullPrices >= Float.valueOf(meet_conditions)) {
				nextBuyFreePrice = new BigDecimal(0);
			} else {
				// 得到运费,在得到满多少减运费
				if (postage > 0) {
					nextBuyFreePrice = new BigDecimal(meet_conditions).subtract(totalFullPrice);
					nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			}
		}
		
		//查看用户礼品卡可用总余额
		BigDecimal canUseTotalMoney = giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
		//查看用户礼品卡锁定余额
		BigDecimal useLockMoney = new BigDecimal(0);
		float userLockMoney = useLockManager.findCustomerLockMoney(customer.getCustomer_id().toString());
		if(userLockMoney > 0){
			useLockMoney = new BigDecimal(userLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
				
		if (useLockMoney != null && useLockMoney.intValue() > 0) {
			canUseTotalMoney = canUseTotalMoney.subtract(useLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		if (StringUtil.isNotNull(shelves)) {
			shelves.substring(0, shelves.length() - 1);
		}
		if (totalPay.compareTo(BigDecimal.ZERO) == -1) {
			totalPay = BigDecimal.ZERO;
		}
		if (receiverAddressVo == null) {
			receiverAddressVo = new ReceiverAddressVo();
		}
		
		if (nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)) {
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length() - 1) + "】不支持配送";
		}
		
		if(StringUtil.isNotNull(goldDeductSpecIds) && goldDeductSpecIds.split(",").length > 0){
			goldDeductSpecIds = goldDeductSpecIds.substring(0, goldDeductSpecIds.length() - 1);
		}
		
		mv.addObject("customer", customer);
		mv.addObject("couponInfo", couponInfo);
		mv.addObject("receiverAddress", receiverAddressVo);
		mv.addObject("count", count);
		mv.addObject("listProduct", listProduct);
		mv.addObject("price", totalPay);
		mv.addObject("totalPrice", totalPrice);
		mv.addObject("totalActivityPrice", totalActivcityDis); // 活动优惠金额
		mv.addObject("fullCut", fullCut);
		mv.addObject("freePostage", nextBuyFreePrice); // 在满多少免邮费
		mv.addObject("totalFullPrice", totalFullPrice);
		mv.addObject("meet_conditions", meet_conditions);
		mv.addObject("is_deilver", is_deilver);
		mv.addObject("deilver", deilver);
		mv.addObject("shelves", shelves); // 下架商品
		mv.addObject("nonDeliveryMsg", nonDeliveryMsg); // 不支持配送提示消息
		mv.addObject("postage", postage); // 邮费
		mv.addObject("userMoney", canUseTotalMoney); // 用户剩余可用余额
		mv.addObject("cartIds", cartIds);
		mv.addObject("returnType", 2); // 返回类型
		mv.addObject("orderSource", orderSource);
		mv.addObject("productIds", productIds);
		mv.addObject("specIds", specIds);
		mv.addObject("ifCouponMoney", ifCouponMoney); // 可参与使用优惠券和红包的金额
		mv.addObject("ifCouponProIds", ifCouponProIds);
		mv.addObject("ifCouponSpecIds", ifCouponSpecIds);
		mv.addObject("myIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral())); // 当前金币数
		mv.addObject("goldDeductSpecIds", goldDeductSpecIds);
		mv.addObject("minLimitIntegral", IntegralConstants.MIN_USE_THRESHOLD);
		mv.addObject("maxLimitIntegral", IntegralConstants.MAX_USE_THRESHOLD);
		mv.addObject("integralDeductionRate", IntegralConstants.DEDUCTION_RATE);
		//创建一个表单token
		String token = TokenProcessor.getInstance().generateToken(request);
		mv.addObject("token", token);
		
		return mv;
	}
	
	
	/**
	 * 查看订单详情
	 */
	@RequestMapping(value = "/queryOrderDetails")
	public ModelAndView queryOrderDetails(HttpServletRequest request) {
		
		CustomerVo coustomerVo=Constants.getCustomer(request);
		if(coustomerVo == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		ModelAndView mv=new ModelAndView();
		
		PageData pd =this.getPageData();
		
//		String type =pd.getString("type");
		String orderCode = pd.getString("orderCode");
		String orderId = pd.getString("orderId");		
		if (StringUtil.isNull(orderCode) && StringUtil.isNull(orderId)) {
			logger.error("查询订单为空");
			mv.setViewName("wein/404");
			return mv;
		}
		OrderVo orderVo =null;
		int invitedFlag = 0;
		try {
			pd.put("customerId", String.valueOf(coustomerVo.getCustomer_id()));
			Integer orderpro_id=pd.getInteger("orderpro_id");
			if(orderpro_id!=null &&orderpro_id>0){   //单个商品详情页
				orderVo =orderManager.findOrder2(pd);
				
			}else{  //支付成功后的详情页
				orderVo =orderManager.findOrder(pd);
			}
			mv.addObject("orderpro_id", orderpro_id);
			
			if(null==orderVo){
				mv.addObject("tipsTitle", "订单信息提示");
				mv.addObject("tipsContent", "您查看的订单不存在或已被删除");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			PageData pd2=new PageData();
			pd2.put("orderpro_id", orderpro_id);
			OrderProductVo orderProductVo=orderProductManager.queryOne(pd2);
			if(orderProductVo==null){
				mv.addObject("tipsTitle", "订单信息提示");
				mv.addObject("tipsContent", "您访问的订单物流信息不存在或已被删除");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			//查询所用物流公司
			if(StringUtils.isNotEmpty(orderProductVo.getCompany_code())){
				//查询物流状态
				// redis缓存
				map=queryKuaidiInfo(orderProductVo.getCompany_code(), orderProductVo.getLogistics_code());
			}
			
			if(map == null || map.size()<1){
				map = new HashMap<String, Object>();
				map.put("code", 1020);
				map.put("msg", "暂无物流信息");
			}
			Integer code = (Integer) map.get("code");
			if (1010==code) {
				List<LogisticsVo> lgss =  (List<LogisticsVo>) map.get("data");
				map.clear();
				map.put("code", 1010);
				map.put("msg", "获取物流信息成功");
				map.put("data", lgss.get(0));
			}
			mv.addObject("logistics", map);
			
			
			//订单支付详情
			PayLogVo payLogInfo = null;
			//根据ID查询优惠券信息
			CouponManagerVo couponManager = null;
			/*//系统参数配置： 支付成功后可以分享的红包总数
			pd.put("dictCode","SHARE_REDPACKED_NUM");
			SysDictVo vo = sysDictManager.selectByCode(pd);
			
			//根据订单ID查询已付款订单中领取优惠券的数量
			int couponCount=couponInfoManager.findShareCountByOrderId(orderVo.getOrder_id());
			mv.addObject("shareCount",vo.getDictValue());*/
			
			pd.put("couponType","1");   //分享券
			couponManager = couponManagerManager.findCouponManager(pd);
			//查询支付日志
			payLogInfo = payLogManager.findPayLog(pd);
			
			if(null != couponManager && null != payLogInfo && 
					null!=payLogInfo.getPay_time() && !"".equals(payLogInfo.getPay_time())){
				
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(DateUtil.stringToDate(payLogInfo.getPay_time()));
				calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
				Date endDate = calendar.getTime();
				Date currentDate = new Date();
				
				if(endDate.before(currentDate)){
					mv.addObject("isShare",false);
				}else{
					mv.addObject("isShare",true);
				}
			}
			
	    	List<String> groupRules = new ArrayList<String>();
	    	if(orderVo.getGroupJoinId() > 0){ //团购商品
	    		PageData tgpd = new PageData();
	        	tgpd.put("groupJoinId", orderVo.getGroupJoinId());
	        	GroupJoinVo groupJoinVo = groupJoinManager.queryOne(tgpd);
	        	
	        	tgpd.clear();
	        	tgpd.put("groupId", groupJoinVo.getGroupId());
	        	SysGroupVo sysGroupVo = sysGroupManager.queryOne(tgpd);
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc1())){
	        		groupRules.add(sysGroupVo.getDesc1());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc2())){
	        		groupRules.add(sysGroupVo.getDesc2());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc3())){
	        		groupRules.add(sysGroupVo.getDesc3());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc4())){
	        		groupRules.add(sysGroupVo.getDesc4());
	        	}
	        	tgpd.clear();
	        	tgpd.put("specialId", sysGroupVo.getSpecialId());
	        	SpecialVo specialVo = specialMananger.queryOne(tgpd);
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
				specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
				
	        	tgpd.clear();
	        	tgpd.put("userGroupId", groupJoinVo.getUserGroupId());
	        	UserGroupVo userGroupVo = this.userGroupManager.queryOne(tgpd);
	        	
	        	tgpd.put("status", 1);
				List<GroupJoinVo> groupJoins = this.groupJoinManager.queryList(tgpd);
				
				
				int hasNum = 0;
				long crrt = System.currentTimeMillis();
				String tuanDesc = "";
				if(specialVo.getLongEnd() <= crrt || specialVo.getStatus().intValue() != 1){
					invitedFlag = 1; //活动结束
				}else if(userGroupVo.getCustNum() < sysGroupVo.getLimitCopy() && userGroupVo.getStatus() == 1){
					invitedFlag = 2; //此团正在进行中
					hasNum = sysGroupVo.getLimitCopy() - userGroupVo.getCustNum();
					tuanDesc = "还差<strong>"+hasNum+"</strong>人，快邀请小伙伴一起参团吧";
				}else{
					hasNum = sysGroupVo.getLimitCopy();
					tuanDesc = "已满"+hasNum+"人，快邀请好友再次开团吧";
				}
				
		    	mv.addObject("groupRules",groupRules);
				mv.addObject("invitedFlag",invitedFlag);
				mv.addObject("tuanDesc",tuanDesc);
	        	mv.addObject("groupJoins",groupJoins);
	        	mv.addObject("specialVo",specialVo);
	        	mv.addObject("userGroupId",userGroupVo.getUserGroupId());
	    	}
	    	
    	
		}catch (Exception e) {
			mv.addObject("isShare",true);
			logger.error("/weixin/order/queryOrderDetails ---error", e);
		}
		
		String tabType = "";
    	if(StringUtil.isEmpty(pd.getString("tabType"))){
    		tabType = "0";
    	}else{
    		tabType = pd.getString("tabType");
    	}
		mv.addObject("tabType",tabType);
		
		mv.addObject("coustomer", coustomerVo);
		mv.addObject("orderInfo", orderVo);
		mv.addObject("type", tabType);
		
		if (orderVo.getGroupJoinId() > 0 && invitedFlag != 1 && orderVo.getListOrderProductVo().get(0).getStatus() != 6) {
			mv.setViewName("weixin/order/wx_group");
		}else if (orderVo.getGroupJoinId() == 0 || invitedFlag == 1 || orderVo.getListOrderProductVo().get(0).getStatus() == 6) {
			mv.setViewName("weixin/order/wx_order_detail");
		}
		return mv;
	}	
	
	
	
	
	
	/**
	 * 根据订单ID删除订单
	 */                     
	@RequestMapping(value="/deleteOrderById")
	@ResponseBody
	public void deleteOrderById(HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		PageData pd=this.getPageData();
		CustomerVo customerInfo = Constants.getCustomer(req);
		pd.put("customerId", String.valueOf(customerInfo.getCustomer_id()));
		logger.info("用户id为"+customerInfo.getCustomer_id()+"开始删除订单--------------------");
		try {
			OrderVo orderVo=orderManager.findOrder(pd);
			if(null!=orderVo){
				pd.put("status","0");   //0： 删除
				//int rsult=orderManager.editOder(pd);
				PageData pd2=new PageData();
				pd2.put("status", "0");
				pd2.put("orderpro_id", pd.getInteger("orderproid"));
				int rsult=orderProductManager.editOrderProductByOrderProductId(pd2);
				if(rsult>0){
					PageData pd3=new PageData();
					pd3.put("orderId", pd.getInteger("orderId"));
					List<OrderProductVo> ops = this.orderProductManager.findListOrderProduct(pd3);
					pd3.put("sub_status", OrderConstants.STATUS_ORDER_DEL);
					List<OrderProductVo> delops = this.orderProductManager.findListOrderProduct(pd3);
					if(ops.size() == delops.size()){
						pd3.put("status", OrderConstants.STATUS_ORDER_DEL);
						orderManager.editOder(pd3);
					}
					json.put("result", "succ");
					json.put("msg", "删除成功");
					logger.info("用户id为"+customerInfo.getCustomer_id()+"删除订单"+orderVo.getOrder_id()+"成功--------------------");
				}else{
					json.put("result", "succ");
					json.put("msg", "删除失败");
					logger.info("用户id为"+customerInfo.getCustomer_id()+"删除订单"+orderVo.getOrder_id()+"成功--------------------");
				}
			}else{
				json.put("result", "exec");
				json.put("msg", "此订单不存在");
			}
		} catch (Exception e) {
			logger.error("/weixin/order/deleteOrderById --error", e);
			
			json.put("result", "exec");
			json.put("msg", "出现异常");
		}
		
		this.outObjectToJson(json, resp);
	}
	
	
	/**
	 * 根据订单ID取消订单或者确认收货
	 */                     
	@RequestMapping(value="/orderStatus")
	@ResponseBody
	public void orderStatus(HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		PageData pd = this.getPageData();
		CustomerVo customerInfo = Constants.getCustomer(req);
		pd.put("customerId", customerInfo.getCustomer_id());
		try {
			OrderVo orderVo = orderManager.findOrder(pd);
			if(null!=orderVo){
				int status = pd.getInteger("tstatus");
				int rsult = 0;
				//取消订单
				if (OrderConstants.STATUS_ORDER_CANCEL == status){
					rsult = orderManager.cancelOrder(orderVo);
					
					PageData pd2=new PageData();
					pd2.put("status", OrderConstants.STATUS_ORDER_CANCEL);
					pd2.put("orderId", pd.getInteger("orderId"));
					rsult = orderProductManager.updateOrderProductStatusByOrderId(pd2);
				} else if (OrderConstants.STATUS_ORDER_WAIT_COMMENT == status) { //确认收货  
//					pd.put("status", status);
//					rsult = orderManager.editOder(pd);
					PageData pd2=new PageData();
					pd2.put("status", status);
					pd2.put("orderId", pd.getInteger("orderId"));
					pd2.put("orderpro_id", pd.getInteger("orderproid"));
					rsult = orderProductManager.updateOrderProductStatusByOrderId(pd2);
				}
				if(rsult>0){
					json.put("result", "succ");
					json.put("msg", "操作成功");
					logger.info("用户id为"+customerInfo.getCustomer_id()+"操作"+orderVo.getOrder_id()+"成功--------------------");
				}else{
					json.put("result", "fail");
					json.put("msg", "操作失败");
					logger.info("用户id为"+customerInfo.getCustomer_id()+"操作订单"+orderVo.getOrder_id()+"失败--------------------");
				}
			}else{
				json.put("result", "exec");
				json.put("msg", "此订单不存在");
				logger.info("用户id为"+customerInfo.getCustomer_id()+"此订单不存在--------------------");
			}
		} catch (Exception e) {
			logger.error("/weixin/order/orderStatus --error", e);
			
			json.put("result", "exec");
			json.put("msg", "出现异常");
		}
		
		this.outObjectToJson(json, resp);
	}
	
	
	/**
	 *  评价订单商品
	 */
	@RequestMapping(value = "/toCommentProduct")
    public ModelAndView toCommentProduct(HttpServletRequest request) throws Exception {  
    	
		ModelAndView mv=new ModelAndView("/weixin/order/wx_evaluate_order");
		
    	PageData pd=this.getPageData();
    	CustomerVo customerVo = Constants.getCustomer(request);
    	
    	String  productId = (String) pd.get("productId");
    	//订单编号
		String orderCode=null;
		String orderId=null;
		OrderVo order=null;
		logger.info("用户id为"+customerVo.getCustomer_id()+"评价订单商品开始");
		try {
			orderCode = pd.getString("orderCode");
			orderId = pd.getString("orderId");
			if(null==orderCode || "".equals(orderCode) ||  null==orderId || "".equals(orderId) ){
				logger.info(" 订单ID ："+orderCode+" 编号:"+orderCode);  
				throw new Exception();
			}else {
				pd.put("customerId",String.valueOf(customerVo.getCustomer_id()));
				
				PageData pageData=new PageData();
				pageData.put("dictCode","SYSTEM_ORDER_EXPIRE");
				SysDictVo sysDict=sysDictManager.selectByCode(pageData);
				if(null !=sysDict&& sysDict.getStatus()==1 ){
					pd.put("expiretime",sysDict.getDictValue());
				}
				
				order=orderManager.findOrder(pd);
				
				//上方法有问题，按道理应该评论那个商品，就显示那个商品，但是这里是将所有的订单商品查出。
				//由于上方法可能其他其他地方会用到，所有不修改底层方法，选择剃除相应的商品
				//弊端：可能消耗一定的性能，优势：但对现有功能影响较小。
				List<OrderProductVo> listOrderProductVo = order.getListOrderProductVo();
				for(OrderProductVo op : listOrderProductVo){
					if(op.getProduct_id() == Integer.parseInt(productId)){
						List<OrderProductVo> op1 = new ArrayList<OrderProductVo>();
						op1.add(op);
						order.setListOrderProductVo(op1);
					}
				}
			}
		} catch (Exception e) {
			logger.info("进入订单评论页面异常 :  订单ID或编号为空! ");  
		}
    	
		
		
		String tabType = "";
    	if(StringUtil.isEmpty(pd.getString("type"))){
    		tabType = "0";
    	}else{
    		tabType = pd.getString("type");
    	}
		mv.addObject("type",tabType);
		mv.addObject("orderInfo",order);
		mv.addObject("productId",productId);
		return mv;  
    }
	
	
	/**
	 * 选择使用优惠券或者礼品卡的时候  清除相应的sesion 缓存
	 */
	@RequestMapping("/chooseOrderDistinctByType")
	@ResponseBody
	public void chooseOrderDistinctByType(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="chooseOrderDistinctByType";
		logger.info(" /weixin/order/ "+msg+"  start ...选择使用优惠券或者礼品卡的时候  清除相应的sesion 缓存");
		
		JSONObject json=new JSONObject(); 
		
		CustomerVo customer=Constants.getCustomer(request);
		
		PageData pd = this.getPageData();
		
		String flag=pd.getString("flag");     //区分礼品卡或者优惠券
		
		String key="readybuy_"+customer.getCustomer_id();               //缓存参数
		PageData pds=(PageData) request.getSession().getAttribute(key);
		if(null!=pds && !"".equals(pds)){
		//	String productId=pds.getString("productId");               //购买产品ID
		//	String extensionType=pds.getString("extension_type");      //推荐类型，1：每周新品；2：人气推荐
		//	String productSpecId=pds.getString("productSpecId");       //商品规格
		//	String buyAmount=pds.getString("buyAmount");               //购买数量
		//	String useAddress=pds.getString("useAddress");             //选择地址
			/*String useGiftCard=pds.getString("useGiftCard");           //使用礼品卡
			String useCoupon=pds.getString("useCoupon");               //选择优惠券
	*/		
		//	logger.info("用户id为-"+customer.getCustomer_id()+"购买产品id为"+productId+"规格为"+productSpecId+"购买数量为"+buyAmount);
			if("useGiftCard".equals(flag)){     //此为礼品卡 ,需要清除 优惠券
				pds.remove("userSingleCouponId");
				pds.remove("couponId");
				json.put("result", "succ");
			}else if("useCoupon".equals(flag)){
				pds.remove("useGiftCard");
				
				json.put("result", "succ");
			}else if ("useSingleCoupon".equals(flag)){
				pds.remove("userSingleCouponId");
				json.put("result", "succ");
			}else{
				json.put("result", "exec");
			}
			
			//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
			request.getSession().setAttribute(key,pds);
			
		}else{
			json.put("result", "exec");
		}           
			
		logger.info(" /weixin/order/ "+msg+"  end ...选择使用优惠券或者礼品卡的时候  清除相应的sesion 缓存");
		
		this.outObjectToJson(json, response);
	}
	
	
	/**
	 *  去商品详情
	 */
	@RequestMapping(value = "/toGoodsShow")
	@ResponseBody
    public String toGoodsShow(HttpServletRequest request) throws Exception {  
    	
		//这里首先要分情况，查取商品的情况状态 
		
		//1:获取页面参数与封装数据
		PageData pd = this.getPageData();
		JSONObject json=new JSONObject();
		
		//2:查询商品详情
		ProductVo pro = productManager.selectProductByOption(pd);
		List<PreProductVo> pre = preProductManager.queryList(pd);
		
		//3:判断状态进行
		if(null != pro){
			//判断是否下架
			if(pro.getStatus()==1){
				if(null != pre && pre.size()>0){
					PreProductVo pre1= pre.get(0);
					json.put("preId", pre1.getPreId());
				}
				json.put("result", "succ");
				json.put("msg","操作成功");
			}else{
				json.put("result", "failure");
				json.put("msg","已下架");
			}
		}else{
			json.put("result", "failure");
			json.put("msg","参数错误，未找到响应商品");
		}
		
		return json.toString();
    }
	
	/**
	 * 检查支付结果
	 */
	@RequestMapping(value="/payResult")
	public ModelAndView payResult(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("/weixin/order/wx_payResult");
		PageData pd=this.getPageData();
		try {
			OrderVo orderVo = this.orderManager.findOrder(pd);
			PayLogVo payLogVo = this.payLogManager.findPayLog(pd);
			if(orderVo == null || payLogVo == null){
				mv.addObject("tipsTitle", "订单信息提示");
				mv.addObject("tipsContent", "您访问的订单不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			int isMine = 0;	//是否自己开团 0不是团购或不是自己开团
			if(orderVo.getGroupJoinId() > 0){
				PageData grppd = new PageData();
				grppd.put("groupJoinId", orderVo.getGroupJoinId());
				GroupJoinVo gjVo = this.groupJoinManager.queryOne(grppd);
				if(gjVo.getOriginator() == gjVo.getCustomerId()){	//自己开团
					isMine = 1;
				}
				grppd.clear();
				grppd.put("userGroupId", gjVo.getUserGroupId());
				List<GroupJoinVo> groupJoins  = this.groupJoinManager.queryList(grppd);
				mv.addObject("groupJoins", groupJoins);
				
				grppd.put("specialId", gjVo.getSpecialId());
	        	SpecialVo specialVo = specialMananger.queryOne(grppd);
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
				specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
				
				grppd.clear();
				grppd.put("userGroupId", gjVo.getUserGroupId());
	        	UserGroupVo userGroupVo = this.userGroupManager.queryOne(grppd);
	        	
	        	grppd.clear();
	        	grppd.put("groupId", gjVo.getGroupId());
	        	SysGroupVo sysGroupVo = sysGroupManager.queryOne(grppd);
	        	List<String> groupRules = new ArrayList<String>();
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc1())){
	        		groupRules.add(sysGroupVo.getDesc1());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc2())){
	        		groupRules.add(sysGroupVo.getDesc2());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc3())){
	        		groupRules.add(sysGroupVo.getDesc3());
	        	}
	        	if(StringUtil.isNotNull(sysGroupVo.getDesc4())){
	        		groupRules.add(sysGroupVo.getDesc4());
	        	}
	        	
				String tuanDesc = "";
				int hasNum = 0;
				if(userGroupVo.getCustNum() < sysGroupVo.getLimitCopy() && userGroupVo.getStatus() == 1){
					hasNum = sysGroupVo.getLimitCopy() - userGroupVo.getCustNum();
					tuanDesc = "还差<strong>"+hasNum+"</strong>人，快邀请小伙伴一起参团吧";
				}else{
					hasNum = sysGroupVo.getLimitCopy();
					tuanDesc = "已满"+hasNum+"人，快邀请好友再次开团吧";
				}
				
				mv.addObject("tuanDesc", tuanDesc);
				mv.addObject("userGroupId", userGroupVo.getUserGroupId());
				mv.addObject("specialVo", specialVo);
			}
			if(orderVo.getQuantity_detail() == 1){	//如果只有一个商品
				List<OrderProductVo> orderPros = this.orderProductManager.findListOrderProduct(pd);
				mv.addObject("productName", orderPros.get(0).getProduct_name());
				mv.addObject("productId", orderPros.get(0).getProduct_id());
			}
			mv.addObject("orderVo", orderVo);
			mv.addObject("payLogVo", payLogVo);
			mv.addObject("isMine", isMine);
		
			return  mv;
		} catch (ParseException e) {
			logger.error("/weixin/order/payResult --error", e);
			mv.addObject("tipsTitle", "订单信息提示");
			mv.addObject("tipsContent", "您访问的订单信息异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
	}
	
	/**
	 * 查询订单状态
	 */
	@RequestMapping(value="/checkPayStatus")
	@ResponseBody
	public Map<String, Object> checkPayStatus(HttpServletRequest req, HttpServletResponse resp){
		
		CustomerVo customer = Constants.getCustomer(req);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		PageData pd=this.getPageData();
		OrderVo orderVo = this.orderManager.findOrder(pd);
		if(orderVo == null){
			return CallBackConstant.FAILED.callbackError("订单信息不存在");
		}
		return CallBackConstant.SUCCESS.callback(orderVo.getStatus());
	}
}
