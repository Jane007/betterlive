package com.kingleadsw.betterlive.controller.app.order;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
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
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.constant.SysDictKey;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.JsonUtil;
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
 * app下单
 * @author xz
 */
@Controller
@RequestMapping(value = "/app/order")
public class AppOrderController extends AbstractWebController{
	
	private Logger logger = Logger.getLogger(AppOrderController.class);
	
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private GiftCardRecordManager giftCardRecordManager;
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
	private CustomerManager customerManager;
	
	@Autowired
	private LogisticsCompanyManager logisticsCompanyManager;
	
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	
	@Autowired
	private SalePromotionManager salePromotionManager;
	
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
	@Autowired
	private ProductLabelManager productLabelManager;
	
	@Autowired
    private RedisService redisService;
	@Autowired
	private PostageManager postageManager; 
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	
	/**
	 * 查询全部订单
	 */                     
	@RequestMapping(value="/shareRedpackedNum")
	@ResponseBody
	public Map<String,Object> shareRedpackedNum(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =new PageData();
		pd.put("dictCode","SHARE_REDPACKED_NUM");
		SysDictVo vo = sysDictManager.selectByCode(pd);
		
		return CallBackConstant.SUCCESS.callback(vo.getDictValue());
	}
	
	/**
	 * 查询全部订单
	 */                     
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest req, HttpServletResponse resp){
		logger.info("app我的订单列表....开始");
		PageData pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("进入我的订单列表，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		pd.put("customerId", customer.getCustomer_id());
		
		//日志信息
		logger.info("用户id为"+customer.getCustomer_id()+"查询所有的订单");
		String status = pd.getString("status");
		if (StringUtil.isNull(status)) {
			logger.info("订单状态为空，查询未删除的全部订单");
			pd.put("statusALL", "0");
		}
		
		//获取系统参数
		pd.put("dictCode", SysDictKey.SYSTEM_ORDER_EXPIRE);
		SysDictVo sysDict = sysDictManager.selectByCode(pd);
		if(null != sysDict && sysDict.getStatus() == 1){
			pd.put("expiretime",sysDict.getDictValue());
		}
		List<OrderVo> orderList = orderManager.findAllorder2ListPage(pd);     //查询订单
		if(orderList == null){
			orderList = new ArrayList<OrderVo>();
		}
		resultMap.put("orderList", orderList);
		logger.info("app我的订单列表....结束");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询全部订单  添加物流功能版本
	 */                     
	@RequestMapping(value="/list2")
	@ResponseBody
	public Map<String,Object> list2(HttpServletRequest req, HttpServletResponse resp){
		logger.info("app我的订单列表....开始");
		PageData pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("进入我的订单列表，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		pd.put("customerId", customer.getCustomer_id());
		
		//日志信息
		logger.info("用户id为"+customer.getCustomer_id()+"查询所有的订单");
		String status = pd.getString("status");
		if (StringUtil.isNull(status)) {
			logger.info("订单状态为空，查询未删除的全部订单");
			pd.put("statusALL", "0");
		} else {
			logger.error("进入我的订单列表，订单状态错误，status：" + status);
			return CallBackConstant.PARAMETER_ERROR.callbackError("订单状态错误");
		}
		
		//获取系统参数
		pd.put("dictCode", SysDictKey.SYSTEM_ORDER_EXPIRE);
		SysDictVo sysDict = sysDictManager.selectByCode(pd);
		if(null != sysDict && sysDict.getStatus() == 1){
			pd.put("expiretime",sysDict.getDictValue());
		}
		
		List<OrderVo> orderList = orderManager.findAllorder2ListPage(pd);     //查询订单
		resultMap.put("orderList", orderList);
		logger.info("app我的订单列表....结束");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 个人中心查看全部订单
	 * 订单查询
	 */
	@RequestMapping(value="/queryLogiticsInfo")
	public ModelAndView queryLogiticsInfo(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("/app/order/logisticsInfo");
		String orderpro_id=req.getParameter("orderpro_id");
		if(StringUtils.isEmpty(orderpro_id)){
			return null;
		}
		PageData pd=new PageData();
		pd.put("orderpro_id", orderpro_id);
		OrderProductVo orderProductVo=orderProductManager.queryOne(pd);
		if(orderProductVo==null){
			return null;
		}
		//查询订单信息
		PageData pd2=new PageData();
		pd2.put("orderId", orderProductVo.getOrder_id());
		
		mv.addObject("orderVo", orderManager.findOrder(pd2));
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		
		//查询所用物流公司
		if(StringUtils.isNotEmpty(orderProductVo.getCompany_code())){
			pd.put("companyCode", orderProductVo.getCompany_code());
			List<LogisticsCompanyVo> list=logisticsCompanyManager.queryList(pd);
			if(list!=null&&list.size()>0){
				mv.addObject("conpanyName", list.get(0).getCompanyName());
			}
			//查询物流状态
			map2=queryKuaidiInfo(orderProductVo.getCompany_code(), orderProductVo.getLogistics_code());
		}
		
		if(map2 == null){
			map2 = new HashMap<String, Object>();
			map2.put("code", 1020);
			map2.put("msg", "暂无物流信息");
		}
		
		mv.addObject("logistics", map2);
		mv.addObject("orderProductVo", orderProductVo);
		return  mv;
	}
	
	private Map<String,Object>  queryKuaidiInfo(String com,String num){
		String kuaidiInfo = redisService.getString(com+"_"+num);
    	logger.info("当前缓存的access_token为：  "+kuaidiInfo);
    	if (StringUtil.isNotNull(kuaidiInfo)) {  //缓存存在，直接返回
    		try {
				return (Map<String, Object>) JsonUtil.jsonToBean(kuaidiInfo, Map.class);
			} catch (Exception e) {
				logger.error("/app/order/queryKuaidiInfo --error", e);
				return null;
			}
    	}else{
    		Map<String,Object> map=Kuaidi100Util.queryKuaidiInfo(com,num);
    		redisService.setex(com+"_"+num, JsonUtil.toJsonString(map), 3600);
    		return map;
    	}
	}
	
	
	
	/**
	 * APP下单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/create",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String,Object> create(HttpServletRequest request){
		PageData pd = this.getPageData();
		Map<String,Object> resultMap = orderManager.createOrder(pd);
		return resultMap;
	}
	
	
	//获取订单商品价格
	public Map<String,Object> getOrderPrice(String[] productIdArray,String[] specIdArray,String[] amountArray,CustomerVo customer){
		OrderProductVo orderProduct=null;
		List<OrderProductVo> listOrderProducts=new ArrayList<OrderProductVo>();
		
		BigDecimal totalPrice=new BigDecimal(0);
		BigDecimal payPrice=new BigDecimal(0);
		Map<String,Object> map=new HashMap<String, Object>();
		
		//查询购买的商品价格信息
		PageData pageData = new PageData();
		for (int i = 0; i < productIdArray.length; i++) {
			BigDecimal price=new BigDecimal(0);
			
			pageData.put("productId", productIdArray[i]);
			pageData.put("specId", specIdArray[i]);
			
			pageData.put("status","1");
			ProductVo product = productManager.selectProductByOption(pageData);
			if(null==product){
				map.put("exist","not");
				return map;
			}
			//判断库存
			ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pageData);
			
			if(specVo!=null&&specVo.getStock_copy()!=null){
				int restCopy = specVo.getStock_copy()-Integer.parseInt(amountArray[i]);
				if(restCopy<0){
					map.put("noStockCopy",product.getProduct_name()+"没有足够的库存");
					return map;
				}
			}
			
			
			logger.info("商品id是productId---"+productIdArray[i]+"--商品名称为"+product.getProduct_name()+"--------规格specId-----"+specIdArray[i]);
			
			//List<ProductSpecVo> listProduct=productSpecManager.queryListProductSpec(pageData);
			List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
			listActivtyProduct = activityProductManager.queryList(pageData);
			
			//优惠多少钱
			BigDecimal leftMoney=null;
			boolean isSpec = false;
			boolean isPre = false;
			int prelimit = 0;
			PreProductVo preProductVo = new PreProductVo();
			SpecialVo svo = new SpecialVo();
			//计算此商品的总价格
			if(listActivtyProduct!=null&&listActivtyProduct.size()>=0){
				String actPrice = "";
				for (ActivityProductVo spec : listActivtyProduct) {//符合时间的活动
					PageData spd=new PageData();
					if(spec.getActivity_type()==1){//预售
						spd.put("preId", spec.getActivity_id());
						 preProductVo = preProductManager.selectPreProductByOption(spd);
						if(preProductVo!=null){
							Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
							Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
							if(dt.after(new Date())&&start.before(new Date())){
								actPrice=spec.getActivity_price();
								prelimit = preProductVo.getLimitBuy()==null?-1:preProductVo.getLimitBuy();
								isPre = true;
								logger.info(preProductVo.getPreName()+"---此预售商品活动价格为------"+actPrice);
								break;
							}
						}
						
					}else{//专题
						spd.put("specialId", spec.getActivity_id());
						 svo = specialMananger.selectSpecialByOption(spd);
						if(svo!=null){
							
							Date dt = DateUtil.stringToDate(svo.getEndTime());
							Date start = DateUtil.stringToDate(svo.getStartTime());
							if(dt.after(new Date())&&start.before(new Date())){
								actPrice=spec.getActivity_price();
								isSpec = true;
								logger.info(svo.getSpecialName()+"---此专题商品活动价格为------"+actPrice);
								break;
							}
						}
					}
				}
				if(actPrice!=""){
					leftMoney=new BigDecimal(actPrice);
				}
				
			}
			
			if(null!=product&&null!=specVo){
				orderProduct=new OrderProductVo();
				orderProduct.setProduct_id(product.getProduct_id());
				orderProduct.setProduct_name(product.getProduct_name());
				orderProduct.setSpec_id(specVo.getSpec_id());
				orderProduct.setSpec_name(specVo.getSpec_name());
				orderProduct.setPrice(specVo.getSpec_price());
				price = new BigDecimal(specVo.getSpec_price());
				
				orderProduct.setQuantity(Integer.parseInt(amountArray[i]));
				
			}
			if(null !=leftMoney){
				orderProduct.setActivity_price(String.valueOf(leftMoney));
			}
			
			orderProduct.setIs_evaluate(0);
			listOrderProducts.add(orderProduct);
			
			price=price.multiply(new BigDecimal(amountArray[i]));          //计算出商品总价格
			totalPrice=totalPrice.add(price);
			if(orderProduct.getActivity_price()!=null&&orderProduct.getActivity_price()!=""){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getActivity_price()));
				payPrice=payPrice.add(payMoney);
			}else{
				payPrice=totalPrice;
			}
		}
			
		map.put("payPrice", payPrice);
		map.put("price", totalPrice);
		map.put("listOrderProducts", listOrderProducts);
		
		return map;
	}
	
	
	/**
	 * 支付后直接跳转 查看订单详情
	 */
	@RequestMapping(value = "/details")
	@ResponseBody
	public Map<String,Object> details(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
    	PageData pd = this.getPageData();
		String token = pd.getString("token");  //用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		
		if (customer == null) {
			logger.error("查看支付订单详情，用户信息为空，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		String oId = pd.getString("orderId");
		if (StringUtil.isNull(oId)) {
			logger.error("查询订单为空");
			return CallBackConstant.PARAMETER_ERROR.callbackError("订单id为空");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		//OrderVo orderVo = orderManager.findOrder(pd);
		Integer orderpro_id=pd.getInteger("orderpro_id");
		OrderVo orderVo =null;
		if(orderpro_id!=null &&orderpro_id>0){   //单个商品详情页
			orderVo =orderManager.findOrder2(pd);
			
		}else{  //支付成功后的详情页
			orderVo =orderManager.findOrder(pd);
			
			
		}
		pd.put("orderpro_id", orderpro_id);
		
		if(null == orderVo){
			logger.error("查询订单为空");
			return CallBackConstant.PARAMETER_ERROR.callback(resultMap);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd2=new PageData();
		pd2.put("orderpro_id", orderpro_id);
		OrderProductVo orderProductVo=orderProductManager.queryOne(pd2);
		if(orderProductVo==null){
			map.put("code", 1020);
			map.put("msg", "您访问的订单物流信息不存在或已被删除");
		}else{
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
		}
		resultMap.put("logistics", map);
		
		if (orderVo.getStatus() == OrderConstants.STATUS_ORDER_NOPAY) {
			getOrderExpireTime(orderVo);
		}
		
		//订单支付详情
		PayLogVo payLogInfo = null;
		//根据ID查询优惠券信息
		CouponManagerVo couponManager = null;
				
		try {
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
					resultMap.put("isShare",false);
				}else{
					resultMap.put("isShare",true);
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
	        	resultMap.put("groupRules",groupRules);
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
				
				int invitedFlag = 0;
				int hasNum = 0;
				long crrt = System.currentTimeMillis();
				String tuanDesc = "";
				if(specialVo.getLongEnd() <= crrt || specialVo.getStatus().intValue() != 1){
					invitedFlag = 1; //活动结束
				}else if(userGroupVo.getCustNum() < sysGroupVo.getLimitCopy() && userGroupVo.getStatus() == 1){
					invitedFlag = 2; //此团正在进行中
					hasNum = sysGroupVo.getLimitCopy() - userGroupVo.getCustNum();
					tuanDesc = "还差"+hasNum+"人，快邀请小伙伴一起参团吧";
				}else{
					hasNum = sysGroupVo.getLimitCopy();
					tuanDesc = "已满"+hasNum+"人，快邀请好友再次开团吧";
				}
				
				resultMap.put("invitedFlag",invitedFlag);
				resultMap.put("tuanDesc",tuanDesc);
	        	resultMap.put("groupJoins",groupJoins);
	        	resultMap.put("specialVo",specialVo);
	        	resultMap.put("userGroupId",userGroupVo.getUserGroupId());
	    	}
		} catch (NumberFormatException e) {
			logger.error("判断分享红包数失败!", e);
			resultMap.put("isShare",true);
		} catch (ParseException e) {
			resultMap.put("isShare",true);
			logger.error("判断分享红包数失败!", e);
		}
		
		resultMap.put("orderInfo", orderVo);
		
		return CallBackConstant.SUCCESS.callback(resultMap);
		
	}
	
	/**
	 * 设置订单的倒计时时间
	 * @param order
	 */
	private void getOrderExpireTime(OrderVo order) {
		//获取系统参数
		PageData pd = new PageData();
		pd.put("dictCode", SysDictKey.SYSTEM_ORDER_EXPIRE);
		SysDictVo sysDict = sysDictManager.selectByCode(pd);
		if(null != sysDict && sysDict.getStatus() == 1){
			String expiretime = sysDict.getDictValue();
			 int expireMinutes = 0;  //过期分钟数
			 if (StringUtil.isNotNull(expiretime)) {
				 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
			 }
			 Date orderTime = DateUtil.stringToDate(order.getOrder_time());  
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(orderTime);
			 calendar.add(Calendar.MINUTE, expireMinutes);
			
			 Date current = new Date();
			 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
			 if(current.after(calendar.getTime())){      //超过有效期
				 order.setIsExpire(1);
			 }else{
				Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
				String time = "";
				
				long hours=l/1000/3600;
				long myushu=l/1000%3600;
				long minites=myushu/60;
				long syushu=myushu%60;
				
				if(hours>0){
					if(String.valueOf(hours).length()>1){
						time=hours+":";
					}else{
						time="0"+hours+":";
					}
				}else{
					time="00:";
				}
				
				if(minites>0){
					if(String.valueOf(minites).length()>1){
						time+=minites+":";
					}else{
						time+="0"+minites+":";
					}
				}else{
					time+="00:";
				}
				
				if(syushu>0){
					if(String.valueOf(syushu).length()>1){
						time+=syushu;
					}else{
						time+="0"+syushu;
					}
				}else{
					time+="00";
				}
				order.setSurplusTiem(time);
			}
		}
	}
	
	
	/**
	 * 根据订单ID，操作状态，更新订单信息</br>
	 * 次方法适用于，1:取消订单，0：删除订单，4:确认收货
	 */                     
	@RequestMapping(value="/operate")
	@ResponseBody
	public Map<String,Object> operate(HttpServletRequest req, HttpServletResponse resp){
		logger.info("用户订单操作开始");
    	PageData pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		String orderId = pd.getString("orderId");
		if (StringUtil.isNull(orderId)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("订单id为空");
		}
		
		String tstatus = pd.getString("tstatus");
		if (StringUtil.isNull(tstatus)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("操作类型为空");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("操作订单，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		pd.put("customerId", customer.getCustomer_id());
		
		try {
			OrderVo orderVo = orderManager.findOrder(pd);
			if(null == orderVo){
				return CallBackConstant.PARAMETER_ERROR.callbackError("订单编号不存在");
			}
			
			pd.put("status", tstatus);
			Integer status = pd.getInteger("tstatus");
			int result = 0;
			//取消订单
			if (OrderConstants.STATUS_ORDER_CANCEL == status){
				result = orderManager.cancelOrder(orderVo);
				PageData pd2=new PageData();
				pd2.put("status", OrderConstants.STATUS_ORDER_CANCEL);
				pd2.put("orderId", pd.getInteger("orderId"));
				result = orderProductManager.updateOrderProductStatusByOrderId(pd2);
			} else if (OrderConstants.STATUS_ORDER_WAIT_COMMENT == status) { //确认收货
				PageData pd2=new PageData();
				pd2.put("status", status);
				pd2.put("orderpro_id", pd.getInteger("orderpro_id"));
				pd2.put("orderId", Integer.parseInt(orderId));
				result = orderProductManager.updateOrderProductStatusByOrderId(pd2);
			}else if (OrderConstants.STATUS_ORDER_DEL == status) {  //删除订单
				PageData pd2=new PageData();
				pd2.put("status", OrderConstants.STATUS_ORDER_DEL);
				pd2.put("orderId", pd.getInteger("orderId"));
				pd2.put("orderpro_id", pd.getInteger("orderpro_id"));
				result = orderProductManager.updateOrderProductStatusByOrderId(pd2);
				PageData pd3=new PageData();
				pd3.put("orderId", pd.getInteger("orderId"));
				List<OrderProductVo> ops = this.orderProductManager.findListOrderProduct(pd3);
				pd3.put("sub_status", OrderConstants.STATUS_ORDER_DEL);
				List<OrderProductVo> delops = this.orderProductManager.findListOrderProduct(pd3);
				if(ops.size() == delops.size()){
					pd3.put("status", OrderConstants.STATUS_ORDER_DEL);
					result = orderManager.editOder(pd3);
				}
			}
			
			if(result > 0){
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/app/order/operate --error", e);
			return CallBackConstant.PARAMETER_ERROR.callback(resultMap);
		}
	}
	
	
	
	/**
	 * 如果存在 可用的优惠券（未被锁定）,取最大面值的优惠券 
	 * @param listCustomerCoupon
	 * @param totalPrice
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
	 * app1.1 准备下单
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping(value = "/addOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrder(HttpServletRequest request,HttpServletResponse response) {
		logger.info(" /app/order/addOrder  start ...");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		
		String token=pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		String productId=pd.getString("productId");               //购买产品ID
		String productSpecId=pd.getString("productSpecId");       //商品规格
		String buyAmount=pd.getString("buyAmount");               //购买数量
		String userCouponId=pd.getString("couponId");               //使用优惠券
		String userSingleCouponId=pd.getString("userSingleCouponId");   //使用红包
		String useAddress=pd.getString("useAddress");               //选择地址的ID
		float totalPrice = 0f;
		logger.info("用户【"+customer.getCustomer_id()+"】购买商品：" + productId + "，规格：" + productSpecId
				+ "数量：" + buyAmount + "，使用优惠券：" + useAddress);
		
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
		
//		Map<String, Object> freightMap = null;  //运费信息
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
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品已下架");
		}	
		
		
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pagedata);
		if(specVo ==null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品规格不存在");
		}
		if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
			return CallBackConstant.FAILED.callbackError("库存不够");
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
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品不存在");
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
		int count = 0; 					//计算总共多少活动
		List<ProductSpecVo> listProduct=new ArrayList<ProductSpecVo>();
		
		PageData spepd = new PageData();
		spepd.put("productId", productId);
		spepd.put("status", 1);
		spepd.put("activityFlag", 1);
		spepd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		spepd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
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
				map.put("fullCut",salePromotion.getCutMoney());
			}
			
			map.put("salePromotion",salePromotion);
		}
		
		//查询优惠券 
	
		PageData pagedatas = new PageData();
		//查询用户可用优惠券
		CouponInfoVo couponInfo=new CouponInfoVo();
		List<CouponInfoVo> listCustomerCoupon = new ArrayList<CouponInfoVo>();
		BigDecimal ifCouponMoney = BigDecimal.ZERO;
		String ifCouponProIds = "";
		String ifCouponSpecIds = "";
		if(product.getIfCoupon() == 0){	//允许使用优惠券和红包
			ifCouponMoney = totoalAmount;
			ifCouponProIds = productId;
			ifCouponSpecIds = productSpecId;
			pagedatas.put("canusecoupon","0");
			if(com.kingleadsw.betterlive.core.util.StringUtil.isNotNull(userCouponId)){
				pagedatas.put("couponId",userCouponId);
			}	
			pagedatas.put("price", ifCouponMoney);
			pagedatas.put("customerId",customer.getCustomer_id().toString());
			listCustomerCoupon=couponInfoManager.findListUserCoupon(pagedatas);
			
			if(StringUtil.isNotNull(userCouponId) && null!=listCustomerCoupon && listCustomerCoupon.size()>0){ //已选择优惠券
					couponInfo=listCustomerCoupon.get(0);
					price = price.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}else if(StringUtil.isNull(userCouponId) && listCustomerCoupon != null && listCustomerCoupon.size() > 0){
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
				map.put("singleCouponVo",uscVo);
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
		postagePd.put("productId",productId);
		PostageVo postageVo = postageManager.queryOne(postagePd);
		if (postageVo != null) {
			if(totalPrice>= Float.valueOf(postageVo.getMeetConditions())){
				nextBuyFreePrice=new BigDecimal(0);
			}else{
				freight=Float.valueOf(postageVo.getPostage());
				if(freight > 0){
					nextBuyFreePrice=new BigDecimal(postageVo.getMeetConditions()).subtract(new BigDecimal(totalPrice));
					price = price.add(new BigDecimal(freight));
					nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("满"+nextBuyFreePrice+"减运费");
				}
			}
		}
		
		pdData.remove("redeemType");
		pdData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ONE);
		ProductRedeemSpecVo goldSpecVo = this.productRedeemSpecManager.queryOne(pdData);
		long goldDeductSpecId = 0;
		if(goldSpecVo != null){
			goldDeductSpecId = goldSpecVo.getRedeemSpecId();
		}
		
		if(price.compareTo(BigDecimal.ZERO) == -1){
			price = BigDecimal.ZERO;
		}
		
		map.put("customer",customer);
		map.put("listCustomerCoupon",listCustomerCoupon);
		map.put("couponInfo",couponInfo);
		map.put("ifCouponMoney",ifCouponMoney);
		map.put("ifCouponProIds", ifCouponProIds);
		map.put("ifCouponSpecIds", ifCouponSpecIds);
		if(receiverAddressVo == null){
			receiverAddressVo = new ReceiverAddressVo();
		}
		map.put("receiverAddress",receiverAddressVo);
		map.put("totalActivityPrice", totalActivcityDis);	//活动优惠金额
		map.put("productId",productId);                    //购买产品ID
		map.put("productSpecId",productSpecId);            //商品规格
		map.put("buyAmount",buyAmount);                    //购买数量
		map.put("totalPrice",totoalAmount);				   //商品总价
		map.put("listProduct",listProduct);
		map.put("returnType",1);                          //返回类型
		
		map.put("count",count);
		map.put("specialVo", specialVo);
		map.put("price",price);						//实付金额
		map.put("is_deilver", is_deilver);
		map.put("deilver", deilver);
		if(nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)){
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length()-1) + "】不支持配送";
		}
		map.put("nonDeliveryMsg", nonDeliveryMsg); //不支持配送提示消息
		map.put("freeFreight",Math.round(Float.valueOf(nextBuyFreePrice+"")));       	//在满多少免邮费
		map.put("freight",Math.round(freight));       					//邮费
		map.put("userMoney",canUseTotalMoney);      //用户剩余可用余额
		map.put("goldDeductSpecId", goldDeductSpecId);
		map.put("myIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		map.put("minLimitIntegral", IntegralConstants.MIN_USE_THRESHOLD);
		map.put("maxLimitIntegral", IntegralConstants.MAX_USE_THRESHOLD);
		map.put("integralDeductionRate", IntegralConstants.DEDUCTION_RATE);
		logger.info("/app/order/addOrder  end ...");
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * app1.1 团购准备下单
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping(value = "/addOrderByGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrderByGroup(HttpServletRequest request,HttpServletResponse response) {
		logger.info(" /app/order/addOrderByGroup  start ...");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		
		String token=pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null==customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		logger.info("用户准备下单---customerid-"+customer.getCustomer_id());
		
		String productId=pd.getString("productId");               //购买产品ID
		String userGroupId=pd.getString("userGroupId");				//开团信息，为空或0，代表开团否则参团
		String productSpecId=pd.getString("productSpecId");       //商品规格
		String buyAmount=pd.getString("buyAmount");               //购买数量
		String useAddress=pd.getString("useAddress");               //选择地址的ID
		String specialId=pd.getString("specialId");					//活动ID
		
		if(StringUtil.isNull(pd.getString("productId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品参数为空");
		}
		if(StringUtil.isNull(pd.getString("productSpecId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品规格参数为空");
		}
		if(StringUtil.isNull(pd.getString("buyAmount"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("请选择购买数量");
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("团购活动不存在");
		}
		
		
		logger.info("用户【"+customer.getCustomer_id()+"】购买商品：" + productId + "，规格：" + productSpecId
				+ "数量：" + buyAmount + "，使用优惠券：" + useAddress+"团购ID"+ specialId);
		
		
		PageData spepd = new PageData();
		spepd.put("specialId", specialId);
		spepd.put("productId", productId);
		spepd.put("status", 1);
		spepd.put("specialType", 3);
		spepd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		spepd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		SpecialVo specialVo = this.specialMananger.queryOneByProductId(spepd);
		if(specialVo == null){
			return CallBackConstant.FAILED.callbackError("团购活动已下架");
		}
		PageData propd = new PageData();
		propd.put("productId", productId);
		propd.put("status", 1);
		ProductVo product = productManager.selectProductByOption(propd);
		
		if(null == product){
			return CallBackConstant.FAILED.callbackError("产品已下架");
		}
		
		propd.put("specStatus", 1);
		propd.put("specId", productSpecId);
		propd.put("proStatus", 1);
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(propd);
		if(specVo ==null){
			return CallBackConstant.FAILED.callbackError("商品规格不存在");
		}
		if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
			return CallBackConstant.FAILED.callbackError("商品库存不够");
		}
		
		propd.put("activityId", specialVo.getSpecialId());
		propd.put("specId", productSpecId);
		ActivityProductVo actp = this.activityProductManager.queryOne(propd);
		if(actp == null){
			return CallBackConstant.FAILED.callbackError("团购规格不存在");
		}
		
		
		propd.put("specialId", specialId);
		SysGroupVo sysgroup = sysGroupManager.queryOne(propd);
		if(sysgroup == null){
			return CallBackConstant.FAILED.callbackError("团购活动已下架");	
		}
		map.put("sysgroup", sysgroup);
		if(StringUtil.isNoNull(userGroupId) && Integer.parseInt(userGroupId) > 0){
			propd.put("userGroupId", userGroupId);
			UserGroupVo usergroup = this.userGroupManager.queryOne(propd);
			if(usergroup == null || usergroup.getStatus() != 1){
				return CallBackConstant.FAILED.callbackError("此团已结束拼单");
			}
			if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
				return CallBackConstant.FAILED.callbackError("此团已完成拼单");
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
						return CallBackConstant.FAILED.callbackError("已达到购买上限");
					}
				}
				map.put("groupJoin", groupJoin);
			}
		}else{	//开团
			if(sysgroup.getJoinCopy() >= sysgroup.getGroupCopy()){
				return CallBackConstant.FAILED.callbackError("可开团数量已达到上线");
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
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品不存在");
		}
		BigDecimal totalPrice = price;
		
		price = new BigDecimal(actp.getActivity_price()).multiply(new BigDecimal(buyAmount));
		
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
				freight=Float.valueOf(postageVo.getPostage());
				if(freight > 0){
					nextBuyFreePrice=new BigDecimal(postageVo.getMeetConditions()).subtract(new BigDecimal(totalPrices));
					price = price.add(new BigDecimal(freight));
					nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("满"+nextBuyFreePrice+"减运费");
				}
			}
		}
		
		if(price.compareTo(BigDecimal.ZERO) == -1){
			price = BigDecimal.ZERO;
		}
		
		map.put("customer",customer);
		
		if(receiverAddressVo == null){
			receiverAddressVo = new ReceiverAddressVo();
		}
		map.put("receiverAddress",receiverAddressVo);
		map.put("productSpecId",productSpecId);            //商品规格
		map.put("buyAmount",buyAmount);                    //购买数量
		map.put("totalPrice",totalPrice);				   //商品总价
		map.put("price",price);						//实付金额
		map.put("productSpec",specVo);		
		map.put("product",product);		
		map.put("specialVo",specialVo);			//活动
		map.put("is_deilver", is_deilver);
		map.put("deilver", deilver);
		if(nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)){
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length()-1) + "】不支持配送";
		}
		map.put("nonDeliveryMsg", nonDeliveryMsg); //不支持配送提示消息
		map.put("freeFreight",Math.round(Float.valueOf(nextBuyFreePrice+"")));       	//在满多少免邮费
		map.put("freight",Math.round(freight));       					//邮费
		map.put("userMoney",canUseTotalMoney);      //用户剩余可用余额
		logger.info(" /app/order/addOrderByGroup  end ...");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * app1.1点击购物车去结算
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addOrders",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrders(HttpServletRequest request, HttpServletResponse response) {
	    Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null==customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		logger.info("用户id为"+customer.getCustomer_id()+"开始结算-------------------------------------------------------");
		
		String cartIds = pd.getString("cartIds");     //购物车的Id,每件商品就是一个购物车  
		if( null == cartIds || "".equals(cartIds)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("购物车商品为空");
		}
		if(cartIds.length() > 0 && (cartIds.substring(cartIds.length()-1, cartIds.length())).equals(",")){
			cartIds = cartIds.substring(0, cartIds.length() - 1);
			pd.put("cartIds", cartIds);
		}
		Integer customerId = customer.getCustomer_id();
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		pd.put("customerId",String.valueOf(customer.getCustomer_id()));
		
		String userCouponId=pd.getString("couponId");               //使用优惠券
		String userSingleCouponId=pd.getString("userSingleCouponId");   //使用红包
		String useAddress=pd.getString("useAddress");               //选择地址的ID
		
		//查询用户默认地址，如果为空  就直接查询所有且获取第一个
		PageData pagedata = new PageData();
		if(null==useAddress || "".equals(useAddress)){
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
		String goldDeductSpecIds = ""; //普通金币抵扣ID集
		if(listShoppingCartVo != null&&listShoppingCartVo.size() > 0){
			
			for (int i = 0; i < listShoppingCartVo.size(); i++) {
				int productId = listShoppingCartVo.get(i).getProduct_id();
				int specId = listShoppingCartVo.get(i).getSpec_id();
				int amount = listShoppingCartVo.get(i).getAmount();
				productIds = productIds + productId + ",";
				specIds = specIds + specId + ",";
				//查询是否存在活动 ，如果是 就加入活动价\
				PageData pdData=new PageData();
				pdData.put("productId", productId);
				pdData.put("specId", specId);
				pdData.put("proStatus", 1);
				pdData.put("specStatus", 1);
				ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pdData);
				if(psvo == null){
					shelves = listShoppingCartVo.get(i).getProduct_name() + ","+ shelves;
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
				if(psvo.getIfCoupon() == 0){	//允许参与使用优惠券或红包的金额
					ifCouponMoney = ifCouponMoney.add(thePrice);
					ifCouponProIds += productId + ",";
					ifCouponSpecIds += specId + ",";
				}
				if(discount.compareTo(BigDecimal.ZERO) >= 0){
					totalPay = totalPay.add(discount.multiply(new BigDecimal(amount)));
					totalActivcityDis = totalActivcityDis.add(thePrice.subtract(discount.multiply(new BigDecimal(amount))));
				}else{
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
				
				PageData plpd = new PageData();
				plpd.put("productId", psvo.getProduct_id());
				plpd.put("nowTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				plpd.put("status", 0);
				ProductLabelVo productLabelVo = productLabelManager.queryOne(plpd);
				if(productLabelVo != null && StringUtil.isNotNull(productLabelVo.getLabelTitle())){
					productSpecVo.setLabel_name(productLabelVo.getLabelTitle()); //商品标签
				}
				
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
				
				//满减活动
				PageData mcps = new PageData();
				mcps.put("productId", psvo.getProduct_id());
				mcps.put("specId", specId);
				SalePromotionVo salePromotion = salePromotionManager.queryOne(mcps);
				if(salePromotion != null && fullCut.compareTo(BigDecimal.ZERO) == 0) {
					if (totalPrice.compareTo(salePromotion.getFullMoney()) >= 0) {// 如果总额大于满减的金额，就要减去多少钱
						totalPay = totalPay.subtract(salePromotion.getCutMoney());
						fullCut = salePromotion.getCutMoney();
					}
				}
				
				//如果选择了收货地址,则查询购物车中的商品是否都支持配送
				if(null != receiverAddressVo){
					deilver = shoppingCartManager.queryDeliver(String.valueOf(productId), 
							province_id, city_id, area_id);
					if (deilver != null && "true".equals(deilver.get("vilidate"))) {
						productSpecVo.setDeliver_status(1); //该商品配送
					} else {
						is_deilver = "false";
						productSpecVo.setDeliver_status(0);
						nonDeliveryMsg += listShoppingCartVo.get(i).getProduct_name()+"、";
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
			return CallBackConstant.PARAMETER_ERROR.callbackError("产品不存在");
		}
		
		List<CouponInfoVo> listCustomerCoupon = new ArrayList<CouponInfoVo>();
		CouponInfoVo couponInfo = new CouponInfoVo();
		if(ifCouponMoney.compareTo(BigDecimal.ZERO) > 0){
			PageData pagedatas=new PageData();
			pagedatas.put("canusecoupon","0");
			pagedatas.put("price", ifCouponMoney);
			pagedatas.put("customerId",customer.getCustomer_id().toString());
			if(StringUtil.isNotNull(userCouponId)){
				pagedatas.put("couponId", userCouponId);
			}
			//查询用户可用优惠券
			listCustomerCoupon = couponInfoManager.findListUserCoupon(pagedatas);
			if(listCustomerCoupon != null && listCustomerCoupon.size() > 0){
				//选择一个金额最大的优惠券
				couponInfo = listCustomerCoupon.get(0);
	//			totalActivcityDis = totalActivcityDis.add(new BigDecimal(couponInfo.getCoupon_money()));
				totalPay = totalPay.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}
			
			PageData singlePd = new PageData();
			productIds = productIds.substring(0, productIds.lastIndexOf(","));
			specIds = specIds.substring(0, specIds.lastIndexOf(","));
			if(ifCouponProIds.length() > 0 && ifCouponSpecIds.length() > 0){
				ifCouponProIds = ifCouponProIds.substring(0, ifCouponProIds.lastIndexOf(","));
				ifCouponSpecIds = ifCouponSpecIds.substring(0, ifCouponSpecIds.lastIndexOf(","));
			}
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
			if(uscVo!=null){
				if(totalPrice.compareTo(uscVo.getFullMoney())>=0){//满了多少钱才可以使用
					totalPay = totalPay.subtract(uscVo.getCouponMoney());
	//					totalActivcityDis = totalActivcityDis.add(uscVo.getCouponMoney());
					map.put("singleCouponVo",uscVo);
				}
			}
		}
		logger.info("用户id为"+customer.getCustomer_id()+"结算总价为--"+totalPrice);
		
		//还差多少钱免运费
				BigDecimal nextBuyFreePrice = BigDecimal.ZERO;
				if (StringUtil.isNotNull(meet_conditions)) {
					//如果超过满减，运费就为0;否则计算 还差多少钱满减
					float totalFullPrices=totalFullPrice.floatValue();
					if(totalFullPrices >= Float.valueOf(meet_conditions)){
						nextBuyFreePrice=new BigDecimal(0);
						postage = 0f;
					}else{
						//得到运费,在得到满多少减运费
						if(postage > 0){
							nextBuyFreePrice=new BigDecimal(meet_conditions).subtract(totalFullPrice);
							nextBuyFreePrice = nextBuyFreePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
							totalPay = totalPay.add(new BigDecimal(postage));
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
				
		if(useLockMoney!=null&&useLockMoney.intValue()>0){
			canUseTotalMoney = canUseTotalMoney.subtract(useLockMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		if(StringUtil.isNotNull(shelves)){
			shelves.substring(0, shelves.length()-1);
		}		
		if(totalPay.compareTo(BigDecimal.ZERO) == -1){
			totalPay = BigDecimal.ZERO;
		}
		
		if(receiverAddressVo == null){
			receiverAddressVo = new ReceiverAddressVo();
		}
		
		if(StringUtil.isNotNull(goldDeductSpecIds) && goldDeductSpecIds.split(",").length > 0){
			goldDeductSpecIds = goldDeductSpecIds.substring(0, goldDeductSpecIds.length() - 1);
		}
		
		map.put("customer",customer);
		map.put("listCustomerCoupon",listCustomerCoupon);
		map.put("couponInfo",couponInfo);
		map.put("receiverAddress",receiverAddressVo);
		map.put("count",count);
		map.put("listProduct",listProduct);
		map.put("price",totalPay);
		map.put("totalPrice",totalPrice);
		map.put("totalActivityPrice", totalActivcityDis);	//活动优惠金额
		map.put("fullCut",fullCut);
		map.put("freeFreight",nextBuyFreePrice);       	//在满多少免邮费
		map.put("is_deilver", is_deilver);
		map.put("deilver", deilver);
		map.put("shelves", shelves);	//下架商品
		if(nonDeliveryMsg != null && !"".equals(nonDeliveryMsg)){
			nonDeliveryMsg = "您下单的商品【" + nonDeliveryMsg.substring(0, nonDeliveryMsg.length()-1) + "】不支持配送";
		}
		map.put("nonDeliveryMsg", nonDeliveryMsg); //不支持配送提示消息
		map.put("freight",postage);       					//邮费
		map.put("userMoney",canUseTotalMoney);      //用户剩余可用余额
		map.put("cartIds",cartIds);
		map.put("productIds", productIds);
		map.put("specIds", specIds);
		map.put("ifCouponMoney", ifCouponMoney);
		map.put("ifCouponProIds", ifCouponProIds);
		map.put("ifCouponSpecIds", ifCouponSpecIds);
		map.put("myIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		map.put("goldDeductSpecIds", goldDeductSpecIds);
		map.put("minLimitIntegral", IntegralConstants.MIN_USE_THRESHOLD);
		map.put("maxLimitIntegral", IntegralConstants.MAX_USE_THRESHOLD);
		map.put("integralDeductionRate", IntegralConstants.DEDUCTION_RATE);
		return CallBackConstant.SUCCESS.callback(map);
	}
}
