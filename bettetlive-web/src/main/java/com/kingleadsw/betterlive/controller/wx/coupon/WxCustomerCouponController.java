package com.kingleadsw.betterlive.controller.wx.coupon;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.SysConstants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.wechat.Md5Util;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 微信端  用户优惠券管理
 * 2017-03-13 by chen
 *
 */
@Controller
@RequestMapping(value = "/weixin/customercoupon")
public class WxCustomerCouponController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxCustomerCouponController.class);

	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据客户ID查询所有的可用优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toCustomerCoupon")
	public ModelAndView toCustomerCanUseCoupon(HttpServletRequest request) throws ParseException {
		String msg = "toCustomerCanUseCoupon";
		logger.info("/weixin/customercoupon/"+msg+" begin");
		
		CustomerVo customerVo=Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		ModelAndView modelAndView=new ModelAndView();

		PageData pd = this.getPageData();
		
		pd.put("customerId", customerVo.getCustomer_id());
		
//		String canusecoupon=pd.getString("canusecoupon");      //可用优惠券 标识
		String notusecoupon=pd.getString("notusecoupon");      //已过期
		String status=pd.getString("status");         			//已使用
		
		List<CouponInfoVo> listCouponInfo=null;                 
		List<UserSingleCouponVo> listSingleCoupon = null;
		if( null !=notusecoupon &&  !"".equals(notusecoupon) && "2".equals(notusecoupon)){
			pd.put("notusecoupon","2");    
			listCouponInfo=couponInfoManager.findListUserCoupon(pd);
			pd.put("customerId", customerVo.getCustomer_id());
			pd.put("status", 1);
			listSingleCoupon = userSingleCouponManager.queryList(pd);
			modelAndView.setViewName("weixin/coupon/wx_notcanuse_coupon");
		}else if("1".equals(status)){
			pd.put("status","1");    
			listCouponInfo=couponInfoManager.findListUserCoupon(pd);
			//TODO:此处没有返回视图
		}else{
			pd.put("canusecoupon","0");
			pd.put("customerId", customerVo.getCustomer_id());
			listCouponInfo=couponInfoManager.findListUserCoupon(pd);
			pd.put("customerId", customerVo.getCustomer_id());
			pd.put("startTime", new Date());
			pd.put("endTime", new Date());
			pd.put("status", 0);
			listSingleCoupon = userSingleCouponManager.queryList(pd);
			modelAndView.setViewName("weixin/coupon/wx_canuse_coupon");
		}
		modelAndView.addObject("listSingleCoupon", listSingleCoupon);
		modelAndView.addObject("customer", customerVo);
		modelAndView.addObject("listCustomerCoupon", listCouponInfo);
		
		logger.info("/weixin/customercoupon/"+msg+" end");
		return modelAndView;
	}
	
	
	/**
	 * 商品下单时: 根据客户信息和消费总额获取优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseCouponInfo")
	public ModelAndView toChooseCouponInfo(HttpServletRequest request) throws ParseException {
		String msg = "toChooseCouponInfo";
		logger.info("/weixin/customercoupon/"+msg+" begin");
		
		ModelAndView mv = new ModelAndView("/weixin/coupon/wx_choose_ordercoupon");
		
		PageData pd =this.getPageData();
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			mv.addObject("tipsTitle", "访问出错");
			mv.addObject("tipsContent", "订单金额丢失啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		List<CouponInfoVo> coupons = new ArrayList<CouponInfoVo>();
		if(new BigDecimal(totalPrice).compareTo(BigDecimal.ZERO) == 1){
			PageData pagedata=new PageData();
			pagedata.put("customerId", String.valueOf(customerVo.getCustomer_id()));
			pagedata.put("canusecoupon",0);
			pagedata.put("price",totalPrice);
			coupons=couponInfoManager.findListUserCoupon(pagedata);
		}
		
		mv.addObject("userCoupons", coupons);
		mv.addObject("returnType", pd.getString("returnType"));
		logger.info("/weixin/customercoupon/"+msg+" end");
		
		return mv;
	}
	
	
	/**
	 * 单个商品下单时: 根据客户信息，商品ID和规格获取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseSingleCoupon")
	public ModelAndView toChooseSingleCoupon(HttpServletRequest request) throws ParseException {
		String msg = "toChooseSingleCoupon";
		logger.info("/weixin/customercoupon/"+msg+" begin");
		ModelAndView mv = new ModelAndView("/weixin/hongbao/wx_choice_ordersinglecoupon");
		
		PageData pd =this.getPageData();
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		if(StringUtil.isEmpty(pd.getString("productId")) || StringUtil.isEmpty(pd.getString("specId"))){
			mv.addObject("tipsTitle", "访问出错");
			mv.addObject("tipsContent", "红包参数异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			mv.addObject("tipsTitle", "访问出错");
			mv.addObject("tipsContent", "订单金额丢失啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		//单品红包
		List<UserSingleCouponVo> uscVos = new ArrayList<UserSingleCouponVo>();
		if(new BigDecimal(totalPrice).compareTo(BigDecimal.ZERO) == 1){
			PageData singlePd = new PageData();
			singlePd.put("productId", pd.getString("productId"));
			singlePd.put("specId", pd.getString("specId"));
			singlePd.put("startTime", new Date());
			singlePd.put("endTime", new Date());
			singlePd.put("canusecoupon", 0);
			singlePd.put("customerId", customerVo.getCustomer_id());
			singlePd.put("price", totalPrice);
			uscVos = userSingleCouponManager.queryListPage(singlePd);
		}
		
		mv.addObject("returnType", pd.getString("returnType"));
		mv.addObject("userSingleCoupons", uscVos);
		logger.info("/weixin/customercoupon/"+msg+" end");
		
		return mv;
	}
	
	/**
	 * 多个商品下单时: 根据客户信息，商品ID和规格获取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseSingleCouponByProducts")
	public ModelAndView toChooseSingleCouponByProducts(HttpServletRequest request) throws ParseException {
		String msg = "toChooseSingleCouponByProducts";
		logger.info("/weixin/customercoupon/"+msg+" begin");
		ModelAndView mv = new ModelAndView("/weixin/hongbao/wx_choice_ordersinglecoupon");
		
		PageData pd =this.getPageData();
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		if(StringUtil.isEmpty(pd.getString("productIds")) || StringUtil.isEmpty(pd.getString("specIds"))){
			mv.addObject("tipsTitle", "访问出错");
			mv.addObject("tipsContent", "红包参数异常");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			mv.addObject("tipsTitle", "访问出错");
			mv.addObject("tipsContent", "订单金额丢失啦");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		//单品红包
		List<UserSingleCouponVo> uscVos = new ArrayList<UserSingleCouponVo>();
		if(new BigDecimal(totalPrice).compareTo(BigDecimal.ZERO) == 1){
			PageData singlePd = new PageData();
			singlePd.put("productIds", pd.getString("productIds"));
			singlePd.put("specIds", pd.getString("specIds"));
			singlePd.put("startTime", new Date());
			singlePd.put("endTime", new Date());
			singlePd.put("canusecoupon", 0);
			singlePd.put("customerId", customerVo.getCustomer_id());
			singlePd.put("price", totalPrice);
			uscVos = userSingleCouponManager.queryListPage(singlePd);
		}
		
		mv.addObject("returnType", pd.getString("returnType"));
		mv.addObject("userSingleCoupons", uscVos);
		logger.info("/weixin/customercoupon/"+msg+" end");
		
		return mv;
	}
	
	
	/**
	 * 领取优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/weixin/customercoupon/receiveCoupon  begin");
		
		PageData pd = this.getPageData();

		//用户
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
		}
		
		if(StringUtil.isNull("cmId")){
			return CallBackConstant.PARAMETER_ERROR.callbackError("参数为空");
		}
		CouponManagerVo couponManager = couponManagerManager.findCouponManager(pd);
		if(couponManager == null){
			return CallBackConstant.FAILED.callbackError("优惠券不存在");
		}
		try {
			String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
			String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
			String title = "恭喜您获得了一张优惠券";
			CouponInfoVo couponInfoVo=new CouponInfoVo();
			
			couponInfoVo.setMobile(customer.getMobile());
			couponInfoVo.setCustomer_id(customer.getCustomer_id());
			
			BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
			BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
			
			couponInfoVo.setCm_id(couponManager.getCm_id());
			couponInfoVo.setCoupon_money(couponMoney.intValue());
			couponInfoVo.setStarttime(currentDate);
			couponInfoVo.setEndtime(endDate);
			couponInfoVo.setStart_money(useminMoney.intValue());
			couponInfoVo.setCoupon_from(2);
			couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期'
			couponInfoVo.setFrom_user_id(Integer.parseInt(couponManager.getCreate_by()));
			
			int result = couponInfoManager.insertUserCoupon(couponInfoVo);
			if(result > 0){
				//发系统消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(2);
				msgVo.setSubMsgType(1);
				msgVo.setMsgTitle(title);
				String couponContent = "";
				if(StringUtil.isNotNull(couponManager.getCoupon_content())){
					couponContent = couponManager.getCoupon_content();
				}
				msgVo.setMsgDetail(couponContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(customer.getCustomer_id());
				msgVo.setObjId(couponInfoVo.getCoupon_id());
				messageManager.insert(msgVo);
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/weixin/customercoupon/receiveCoupon  error", e);
			
			return CallBackConstant.FAILED.callbackError("领取优惠券异常");
		}
	}
	
	
	
	
	
	/**
	 * 领取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveSingleCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveSingleCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/weixin/customercoupon/receiveSingleCoupon  begin");
		
		PageData pd = this.getPageData();
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
		}
		
		if(StringUtil.isNull(pd.getString("couponSpecId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("红包参数为空");
		}
		SingleCouponSpecVo scs = singleCouponSpecManager.queryOne(pd);
		if(scs == null){
			return CallBackConstant.FAILED.callbackError("红包规格不存在");
		}
		SingleCouponVo singleCoupon= singleCouponManager.selectByPrimaryKey(scs.getCouponId());
		if(singleCoupon == null){
			return CallBackConstant.FAILED.callbackError("红包不存在");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = sdf.parse(singleCoupon.getEndTime());
		long systime = System.currentTimeMillis();
		if(singleCoupon.getStatus().intValue() == 0
				|| endTime.getTime() <= systime){
			return CallBackConstant.DATA_TIME_TOU.callbackError("红包活动已过期");
		}
		try {
			pd.put("customerId", customer.getCustomer_id());
			pd.put("couponId", singleCoupon.getCouponId());
			pd.put("specId", scs.getSpecId());
			UserSingleCouponVo usCoupon = userSingleCouponManager.queryOne(pd);
			if(usCoupon==null){
				usCoupon = new UserSingleCouponVo();
				//用户的信息
				usCoupon.setCustomerId(customer.getCustomer_id());
				usCoupon.setMobile(customer.getMobile());
				//券的信息
				usCoupon.setCouponId(singleCoupon.getCouponId());
				usCoupon.setStartTime(singleCoupon.getStartTime());
				usCoupon.setEndTime(singleCoupon.getEndTime());
				usCoupon.setFullMoney(singleCoupon.getFullMoney());
				usCoupon.setCouponMoney(singleCoupon.getCouponMoney());
				PageData pdps = new PageData();
				pdps.put("productId", scs.getProductId());
				pdps.put("specId", scs.getSpecId());
				pdps.put("specStatus", 1);
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pdps);
				if(pspec == null){
					return CallBackConstant.FAILED.callbackError("商品规格不存在");
				}
				usCoupon.setSpecId(scs.getSpecId());
				usCoupon.setProductId(pspec.getProduct_id());
				int count = userSingleCouponManager.insert(usCoupon);
				if(count>0){
					//发系统消息给用户
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(MessageVo.MSGTYPE_COUPON);
					msgVo.setSubMsgType(2);
					msgVo.setMsgTitle("恭喜您获得了一个单品红包");
					msgVo.setMsgDetail(singleCoupon.getCouponContent());
					msgVo.setIsRead(0);
					msgVo.setCustomerId(customer.getCustomer_id());
					msgVo.setObjId(usCoupon.getUserSingleId());
					messageManager.insert(msgVo);			
					return CallBackConstant.SUCCESS.callback();	
				}
			}else{
				return CallBackConstant.DATA_HAD_FOUND.callbackError("您已经领取过了");
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/weixin/customercoupon/receiveSingleCoupon  error", e);
			
			return CallBackConstant.FAILED.callbackError("领取单品红包异常");
		}
	}
	
	
	/**
	 * 领取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveSingleCouponList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveSingleCouponList(HttpServletRequest request) throws ParseException {
		logger.info("/weixin/customercoupon/receiveSingleCoupon  begin");
		
		PageData pd = this.getPageData();
		String couponId = pd.getString("couponId");
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没登录");
		}
		
		if(StringUtil.isNull(couponId)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("红包参数为空");
		}
		
		SingleCouponVo singleCoupon= singleCouponManager.selectByPrimaryKey(Integer.parseInt(couponId));
		if(singleCoupon == null){
			return CallBackConstant.FAILED.callbackError("红包不存在");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = sdf.parse(singleCoupon.getEndTime());
		long systime = System.currentTimeMillis();
		if(singleCoupon.getStatus().intValue() == 0
				|| endTime.getTime() <= systime){
			return CallBackConstant.DATA_TIME_TOU.callbackError("红包活动已过期");
		}
		try {
			pd.put("customerId", customer.getCustomer_id());
			pd.put("phoneNum", customer.getMobile());
			return userSingleCouponManager.insertSingleCouponByProductId(pd);
		} catch (Exception e) {
			logger.error("/weixin/customercoupon/receiveSingleCoupon  error", e);
			return CallBackConstant.FAILED.callbackError("领取单品红包异常");
		}
	}
	

	/**
	 * 一键领取
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveMultiCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveMultiCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/app/customercoupon/receiveMultiCoupon  begin");
		
		
		PageData pd = this.getPageData();
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
		}
		customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(StringUtil.isNull(customer.getMobile())){
			return CallBackConstant.FAILED.callbackError("请先绑定手机号");
		}
		
		
		//新改版单品红包首页一键领取
		String couponIds = pd.getString("couponIds");
		String cmIds = pd.getString("cmIds");
		pd.put("phoneNum", customer.getMobile());
		if(StringUtil.isNotNull(couponIds)){
			String[] ids = couponIds.split(",");
			
			TreeSet<String> hset = new TreeSet<String>(Arrays.asList(ids));
		   
			for (String id : hset) {//去除重复数据
				try {
					PageData pd1 = new PageData();
					pd1.put("couponId", id);
					SingleCouponVo singleCoupon = singleCouponManager.selectByPrimaryKey(Integer.parseInt(id));
					
					if(singleCoupon == null){
						logger.error("红包不存在" + id);
						continue;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date endTime = sdf.parse(singleCoupon.getEndTime());
					long systime = System.currentTimeMillis();
					if(singleCoupon.getStatus().intValue() == 0
							|| endTime.getTime() <= systime){
						logger.error("红包活动已过期" + id);
						continue;
					}
					
					pd.put("customerId", customer.getCustomer_id());
					pd.put("couponId", id);
					
					List<SingleCouponSpecVo> scslist = singleCoupon.getListSpec();
					if(scslist!=null&&scslist.size()>0){
						for (SingleCouponSpecVo singleCouponSpecVo : scslist) {
							pd.put("specId", singleCouponSpecVo.getSpecId()+"");
							pd.put("productId", singleCouponSpecVo.getProductId());
							Map<String, Object> map = userSingleCouponManager.insertSingleCouponByProductId(pd);
							if(map.get("result")!=null&&map.get("result").equals("fail")){
								return CallBackConstant.FAILED.callbackError(map.get("msg").toString());
							}
						}
					}
				} catch (Exception e) {
					logger.error("/app/customercoupon/receiveSingleCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取单品红包异常");
				}
			}	

		}

		if(StringUtil.isNotNull(cmIds)){
			String[] youhuiids = cmIds.split(",");
			for (int i = 0; i < youhuiids.length; i++) {
				try {
					PageData pd1 = new PageData();
					pd1.put("cmId", youhuiids[i]);
					CouponManagerVo couponManager = couponManagerManager.findCouponManager(pd1);
					if(couponManager == null){
						continue;
					}
					String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
					Calendar calendar = Calendar.getInstance();  
					calendar.setTime(new Date());
					calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
					String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
					String title = "恭喜您获得了一张优惠券";
					CouponInfoVo couponInfoVo=new CouponInfoVo();
					
					couponInfoVo.setMobile(customer.getMobile());
					couponInfoVo.setCustomer_id(customer.getCustomer_id());
					
					BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
					BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
					
					couponInfoVo.setCm_id(couponManager.getCm_id());
					couponInfoVo.setCoupon_money(couponMoney.intValue());
					couponInfoVo.setStarttime(currentDate);
					couponInfoVo.setEndtime(endDate);
					couponInfoVo.setStart_money(useminMoney.intValue());
					couponInfoVo.setCoupon_from(2);
					couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期'
					couponInfoVo.setFrom_user_id(Integer.parseInt(couponManager.getCreate_by()));
					
					int result = couponInfoManager.insertUserCoupon(couponInfoVo);
					if(result > 0){
						//发系统消息给用户
						MessageVo msgVo = new MessageVo();
						msgVo.setMsgType(2);
						msgVo.setSubMsgType(1);
						msgVo.setMsgTitle(title);
						String couponContent = "";
						if(!StringUtil.isEmpty(couponManager.getCoupon_content())){
							couponContent = couponManager.getCoupon_content();
						}
						msgVo.setMsgDetail(couponContent);
						msgVo.setIsRead(0);
						msgVo.setCustomerId(customer.getCustomer_id());
						msgVo.setObjId(couponInfoVo.getCoupon_id());
						messageManager.insert(msgVo);
						//return CallBackConstant.SUCCESS.callback();
					}
					//return CallBackConstant.FAILED.callback();
				} catch (Exception e) {
					logger.error("/app/customercoupon/receiveCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取优惠券异常");
				}
			}
		}
		return CallBackConstant.SUCCESS.callback();	
	}
	
	/**
	 * 每周新品列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myCoupon")
	public ModelAndView toProductList(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/coupon/wx_my_coupon");
		return modelAndView;
	}
	
	@RequestMapping(value = "/mySingleCoupon")
	public ModelAndView mySingleCoupon(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/hongbao/wx_my_hongbao");
		return modelAndView;
	}
	
	/**
	 * 可使用的优惠券列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/myCouponList")
	@ResponseBody
	public Map<String,Object> myCouponList(HttpServletRequest request, HttpServletResponse response){
		logger.info("进入app我的优惠券列表....开始");
		PageData pd = this.getPageData();
		
		//useFlag  0 未使用，1已使用，3已过期
		if (StringUtil.isNull(pd.getString("useFlag"))) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer=Constants.getCustomer(request);
		if (customer == null || customer.getCustomer_id() == null) {
			logger.error("用户获取优惠券列表，通过customer获取用户信息失败" );
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		                
		String useFlag = pd.getString("useFlag");
		//可用优惠券
		if("1".equals(useFlag)){ //已使用
			pd.put("status",useFlag); 
		}else{
			pd.put("canusecoupon",useFlag); 
		}
		List<CouponInfoVo> couponList = couponInfoManager.findUserCouponListPage(pd);	
		if(couponList ==null)
		{
			couponList = new ArrayList<CouponInfoVo>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", couponList);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	/**
	 * 可使用的单品红包列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mySingleCouponList")
	@ResponseBody
	public Map<String,Object> mySingleCouponList(HttpServletRequest request, HttpServletResponse response){
		logger.info("进入微信我的单品红包列表....开始");
		PageData pd = this.getPageData();
		
		/*Map<String, Object> result = new HashMap<String, Object>();*/
		//useFlag  0 未使用，1已使用，3已过期
		if (StringUtil.isNull(pd.getString("useFlag"))) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer=Constants.getCustomer(request);
		if (customer == null || customer.getCustomer_id() == null) {
			logger.error("用户获取单品红包列表，通过customer获取用户信息失败" );
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		String useFlag = pd.getString("useFlag");
		//可用优惠券
		if("1".equals(useFlag)){ //已使用
			pd.put("status",useFlag); 
		}else{
			pd.put("canusecoupon",useFlag); 
		}	
		List<UserSingleCouponVo> couponList = userSingleCouponManager.queryListPage(pd);
		if(couponList == null){
			couponList = new ArrayList<UserSingleCouponVo>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", couponList);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	/**
	 * 访问领券落地页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCollarCoupon")
	public ModelAndView toCollarCoupon(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/coupon/wx_collar_coupon");
		try {
			PageData pd = this.getPageData();
			String cmId = pd.getString("cmId");
			if(StringUtil.isNull(cmId)){  
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "红包参数异常");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			CustomerVo customerVo = Constants.getCustomer(request);
			if(customerVo != null && customerVo.getCustomer_id() != null){
				customerVo = this.customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			}
			
			CouponManagerVo cmVo = this.couponManagerManager.selectByPrimaryKey(Integer.parseInt(cmId));
			if(cmVo == null){
				modelAndView.addObject("tipsTitle", "活动信息提示");
				modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			modelAndView.addObject("customerVo", customerVo);
			modelAndView.addObject("cmId", cmVo.getCm_id());
			return modelAndView;
		}catch(Exception e){
			logger.error("weixin/customercoupon/toCollarCoupon --error", e);
			modelAndView.addObject("tipsTitle", "活动信息提示");
			modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
	}
	
	/**
	 * 领取优惠券
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/collarCoupon")
	@ResponseBody
	public Map<String, Object> collarCoupon(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		
			String cmId = pd.getString("cmId");
			if(StringUtil.isNull(cmId)){
				return CallBackConstant.FAILED.callbackError("非法访问");
			}
			
			CustomerVo myCustVo = Constants.getCustomer(request);
			if(myCustVo != null && myCustVo.getCustomer_id() != null){
				myCustVo = this.customerManager.selectByPrimaryKey(myCustVo.getCustomer_id());
			}
			String mobile = pd.getString("mobile");
			if(myCustVo == null || StringUtil.isNull(myCustVo.getMobile())){
				String checkCode = pd.getString("checkCode");
				if (StringUtil.isNull(checkCode)) {
					return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
				}
				
				if(StringUtil.isNull(mobile)){
					return CallBackConstant.PARAMETER_ERROR.callbackError("请输入手机号码");
				}
				
				if (!checkCode.equals(redisService.getString(mobile))) {
					return CallBackConstant.PARAMETER_ERROR.callbackError("验证码错误或已过期");
				}
				redisService.delKey(mobile);
			}else{
				mobile = myCustVo.getMobile();
			}
			
			
			try {
				
				String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
				CustomerVo customer = customerManager.selectByMobile(mobile);
				
				CouponInfoVo couponInfoVo = new CouponInfoVo();
				if(myCustVo == null && customer == null){	//注册
					customer = (CustomerVo)request.getSession().getAttribute("yk_customer");
					myCustVo = new CustomerVo();
					String sessionSource = (String) request.getSession().getAttribute("source");
					String source = "weixin_browser";
					if(StringUtil.isNotNull(sessionSource)){
						source = sessionSource;
					}
					myCustVo.setSource(source);
					myCustVo.setMobile(pd.getString("mobile"));
					myCustVo.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
					
					if(customer != null){
						myCustVo.setSubscribe(customer.getSubscribe());
						myCustVo.setNickname(customer.getNickname());
						myCustVo.setOpenid(customer.getOpenid());
						myCustVo.setUnionid(customer.getUnionid());
						myCustVo.setSex(customer.getSex());
					}else{
						myCustVo.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
						myCustVo.setNickname("");
					}
	            	Random rnd = new Random();
	        		int num = rnd.nextInt(89999) + 10000000;
	        		String pwd = Md5Util.md532(String.valueOf(num));
	        		myCustVo.setPassword(pwd);
	        		int count = customerManager.insertCustomer(myCustVo);
	        		if(count <= 0){
						return CallBackConstant.FAILED.callbackError("注册失败");
	        		}
	        		
	        		String msg = "恭喜您成为挥货平台的会员，您的登录密码是["+num+"]，请及时修改您的登录密码！";
	        		SendMsgUtil.sendMessage(mobile, msg);
	        		myCustVo.setPassword(null);
	        		Constants.setCustomer(request, myCustVo);
	        		request.getSession().removeAttribute("yk_customer");
	    			
	        		couponInfoManager.insertRegisterCoupon(myCustVo); //新手红包
				}else{//检查手机号码 并绑定
					if(customer != null && myCustVo != null){
						if(customer.getCustomer_id().intValue() != myCustVo.getCustomer_id().intValue()){ //当前登录用户不是正在领券的用户
							request.getSession().invalidate();
						}
						myCustVo = customer;
					}else if (myCustVo != null && customer == null){
						PageData custpd = new PageData();
						custpd.put("customer_id", myCustVo.getCustomer_id());
						custpd.put("mobile", mobile);
						this.customerManager.updateCustoemrById(custpd);
					}else if (myCustVo == null){
						myCustVo = customer;
					}
					
					myCustVo.setMobile(mobile);
					myCustVo.setPassword(null);
	        		Constants.setCustomer(request, myCustVo);
	        		
	        		PageData cpd = new PageData();
					cpd.put("cmId", cmId);
					cpd.put("customerId", myCustVo.getCustomer_id());
					couponInfoVo = this.couponInfoManager.queryOne(cpd);
					if(couponInfoVo != null){
						return CallBackConstant.DATA_HAD_FOUND.callbackError("您已经领取过该红包啦，进入挥货商城参与更多优惠");
					}
				}
				
				CouponManagerVo cmVo = couponManagerManager.selectByPrimaryKey(Integer.parseInt(cmId));
				if(cmVo == null){
					return CallBackConstant.DATA_NOT_FOUND.callbackError("该活动已结束啦，进入挥货商城参与更多优惠");
				}
				BigDecimal couponMoney=new BigDecimal(cmVo.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(cmVo.getUsemin_money());
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,Integer.parseInt(cmVo.getUsemax_date()));
				String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
				
    			couponInfoVo = new CouponInfoVo();
				couponInfoVo.setMobile(mobile);
				couponInfoVo.setCustomer_id(myCustVo.getCustomer_id());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
				couponInfoVo.setFrom_user_id(0);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCm_id(cmVo.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
        		this.couponInfoManager.insertUserCoupon(couponInfoVo);
        		
        		//发系统消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(2);
				msgVo.setSubMsgType(1);
				msgVo.setMsgTitle("恭喜您获得了一张优惠券");
				String couponContent = "";
				if(StringUtil.isNotNull(cmVo.getCoupon_content())){
					couponContent = cmVo.getCoupon_content();
				}
				msgVo.setMsgDetail(couponContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(myCustVo.getCustomer_id());
				msgVo.setObjId(couponInfoVo.getCoupon_id());
				messageManager.insert(msgVo);

				msgVo.setMsgDetail(couponContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(myCustVo.getCustomer_id());
				msgVo.setObjId(couponInfoVo.getCoupon_id());
				messageManager.insert(msgVo);
				return CallBackConstant.SUCCESS.callback();
			} catch (Exception e) {
				logger.error("/weixin/customercoupon/collarCoupon --error", e);
				return CallBackConstant.FAILED.callbackError("您来晚了，红包已被抢光了~");
			}
	}
	
	
}
