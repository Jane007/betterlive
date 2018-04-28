package com.kingleadsw.betterlive.controller.app.coupon;

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
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * app  用户优惠券管理
 * @author xz
 *
 */
@Controller
@RequestMapping(value = "/app/customercoupon")
public class AppCustomerCouponController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(AppCustomerCouponController.class);

	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private MessageManager messageManager;	
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	
	/**
	 * 下单时: 根据客户ID查询所有的可用优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toChooseCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/app/customercoupon/toChooseCoupon  begin");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		String price = pd.getString("price");
		String returnType = pd.getString("returnType");
		String token = pd.getString("token");  							//用户标识
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		if (null == customerVo) {
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		if (StringUtil.isEmpty(price)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品价格不能为空");
		}
		
		
		List<CouponInfoVo> aboveUseCoupons=null;
		List<CouponInfoVo> belowUseCoupons=null;
		
		try {
			PageData pagedata=new PageData();
			pagedata.put("customerId", customerVo.getCustomer_id());
			pagedata.put("canusecoupon","0");
			pagedata.put("price",price);
			aboveUseCoupons = couponInfoManager.findListUserCoupon(pagedata);
			
			pagedata.remove("price");
			pagedata.put("abovePrice",price);
			belowUseCoupons = couponInfoManager.findListUserCoupon(pagedata);
		} catch (Exception e) {
			logger.error("/app/customercoupon/toChooseCoupon  出现异常");
			return CallBackConstant.FAILED.callbackError("查询用户可用优惠券异常");
		}
		
		map.put("aboveUseCoupons", aboveUseCoupons);
		map.put("belowUseCoupons", belowUseCoupons);
		map.put("returnType", returnType);              //根据商品多少来判定是单个购买还是从购物车结算 
		
		logger.info("/app/customercoupon/toChooseCoupon  end");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 优惠券列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, HttpServletResponse response){
		logger.info("进入app我的优惠券列表....开始");
		PageData pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户获取优惠券列表，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		String couponType = pd.getString("couponType");      //优惠券类型
		logger.info("优惠券类型：" + couponType);
		if (!StringUtil.isInteger(couponType)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("优惠券类型错误");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		List<CouponInfoVo> couponList = null;                 
		
		//可用优惠券
		if(null !=couponType &&  "1".equals(couponType)){
			pd.put("canusecoupon","0"); 
			couponList = couponInfoManager.findUserCouponListPage(pd);
		} else {   //已过期或者已使用优惠券（不可用）
			pd.put("notusecoupon","2");
			couponList = couponInfoManager.findUserCouponListPage(pd);
		}
		
		resultMap.put("couponList", couponList);
		logger.info("进入微信我的优惠券列表....结束");		
		return CallBackConstant.SUCCESS.callback(resultMap);
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
		logger.info("/app/customercoupon/receiveCoupon  begin");
		
		PageData pd = this.getPageData();
		String token = pd.getString("token");  	
		CustomerVo customer;

		//用户标识
		if(StringUtil.empty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}else{
			customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
			}
		}
		
		if(StringUtil.isEmpty("cmId")){
			return CallBackConstant.PARAMETER_ERROR.callbackError("优惠券参数为空");
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
				if(StringUtil.isNotEmpty(couponManager.getCoupon_content())){
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
			logger.error("/app/customercoupon/receiveCoupon  error", e);
			
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
		logger.info("/app/customercoupon/receiveSingleCoupon  begin");
		
		PageData pd = this.getPageData();
		String token = pd.getString("token");  	
		CustomerVo customer;
	
		//用户标识
		if(StringUtil.empty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}else{
			customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
			}
			if(StringUtil.isEmpty(customer.getMobile())){
				return CallBackConstant.FAILED.callbackError("请先绑定手机号");
			}
		}
		
		if(StringUtil.isEmpty(pd.getString("couponSpecId"))){
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
				pd.put("specStatus", 1);
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
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
				return CallBackConstant.FAILED.callbackError("您已经领取过了");
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/app/customercoupon/receiveSingleCoupon  error", e);
			
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
	public Map<String,Object> receiveSingleCouponNew(HttpServletRequest request) throws ParseException {
		logger.info("/app/customercoupon/receiveSingleCouponList  begin");
		
		PageData pd = this.getPageData();
		String token = pd.getString("token");  	
		CustomerVo customer;
	
		//用户标识
		if(StringUtil.empty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}else{
			customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
			}
			if(StringUtil.isEmpty(customer.getMobile())){
				return CallBackConstant.FAILED.callbackError("请先绑定手机号");
			}
		}
		
		String couponId = pd.getString("couponId");
		if(StringUtil.isEmpty(couponId)){
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
			pd.put("couponId", singleCoupon.getCouponId());
		
			pd.put("phoneNum", customer.getMobile());
			Map<String, Object> map = userSingleCouponManager.insertSingleCouponByProductId(pd);
			return map;
			
		} catch (Exception e) {
			logger.error("/app/customercoupon/receiveSingleCouponList  error", e);
			
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
		String token = pd.getString("token");  	 
	
		//用户标识
		if(StringUtil.empty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.FAILED.callbackError("用户信息不存在");
		}
		if(StringUtil.isEmpty(customer.getMobile())){
			return CallBackConstant.FAILED.callbackError("请先绑定手机号");
		}
		
		String couponSpecIds = pd.getString("couponSpecIds");
		String cmIds = pd.getString("cmIds");
		if(StringUtil.isNotEmpty(couponSpecIds)){
			String[] ids = couponSpecIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				try {
					PageData pd1 = new PageData();
					pd1.put("couponSpecId", ids[i]);
					SingleCouponSpecVo scs = singleCouponSpecManager.queryOne(pd1);
					if(scs == null){
						return CallBackConstant.FAILED.callbackError("红包规格不存在");
					}
					SingleCouponVo singleCoupon= singleCouponManager.selectByPrimaryKey(scs.getCouponId());
					if(singleCoupon == null){
						logger.error("红包不存在" + ids[i]);
						continue;
						//return CallBackConstant.FAILED.callback("红包不存在");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date endTime = sdf.parse(singleCoupon.getEndTime());
					long systime = System.currentTimeMillis();
					if(singleCoupon.getStatus().intValue() == 0
							|| endTime.getTime() <= systime){
						logger.error("红包活动已过期" + ids[i]);
						continue;
						//return CallBackConstant.DATA_TIME_TOU.callback("红包活动已过期");
					}
					
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
						pd.put("specStatus", 1);
						ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
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
							
						}
					}else{
						logger.error("您已经领取过了" + ids[i]);
						//return CallBackConstant.FAILED.callback("您已经领取过了");
					}
				
					//return CallBackConstant.FAILED.callback();
				} catch (Exception e) {
					logger.error("/app/customercoupon/receiveMultiCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取单品红包异常");
				}
			}	

		}

		if(StringUtil.isNotEmpty(cmIds)){
			String[] youhuiids = cmIds.split(",");
			for (int i = 0; i < youhuiids.length; i++) {
				try {
					PageData pd1 = new PageData();
					pd1.put("cmId", youhuiids[i]);
					CouponManagerVo couponManager = couponManagerManager.findCouponManager(pd1);
					if(couponManager == null){
						continue;
						//return CallBackConstant.FAILED.callback("优惠券不存在");
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
						if(StringUtil.isNotEmpty(couponManager.getCoupon_content())){
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
					logger.error("/app/customercoupon/receiveMultiCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取优惠券异常");
				}
			}
		}
		return CallBackConstant.SUCCESS.callback();	
	}
	
	
	
	/**
	 * 一键领取
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveMultiCouponNew",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveMultiCouponNew(HttpServletRequest request) throws ParseException {
		logger.info("/app/customercoupon/receiveMultiCoupon  begin");
		
		PageData pd = this.getPageData();
		String token = pd.getString("token");  	 
	
		//用户标识
		if(StringUtil.empty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.FAILED.callbackError("用户信息不存在");
		}
		if(StringUtil.isEmpty(customer.getMobile())){
			return CallBackConstant.FAILED.callbackError("请先绑定手机号");
		}
		
		//新改版单品红包首页一键领取
		String couponIds = pd.getString("couponIds");
				
		String cmIds = pd.getString("cmIds");
		if(StringUtil.isNotEmpty(couponIds)){
			String[] ids = couponIds.split(",");
			TreeSet<String> hset = new TreeSet<String>(Arrays.asList(ids));
			
			for (String id : hset) {
				try {
					PageData pd1 = new PageData();
					
					SingleCouponVo singleCoupon= singleCouponManager.selectByPrimaryKey(Integer.parseInt(id));
					if(singleCoupon == null){
						logger.error("红包不存在" + ids);
						continue;
					
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date endTime = sdf.parse(singleCoupon.getEndTime());
					long systime = System.currentTimeMillis();
					if(singleCoupon.getStatus().intValue() == 0
							|| endTime.getTime()<=systime){
						logger.error("红包活动已过期" + id);
						continue;
					}
					
					pd.put("customerId", customer.getCustomer_id());
					pd.put("couponId", id);
					List<SingleCouponSpecVo> scslist = singleCoupon.getListSpec();
					if(scslist!=null&&scslist.size()>0){
						for (SingleCouponSpecVo singleCouponSpecVo : scslist) {
							pd.put("specId", singleCouponSpecVo.getSpecId()+"");
							pd.put("phoneNum", customer.getMobile());
							Map<String, Object> map = userSingleCouponManager.insertSingleCoupon(pd);
							if(map!=null&&map.get("result").equals("fail")){
								return map;
							}
						}
					}
					
				} catch (Exception e) {
					logger.error("/app/customercoupon/receiveMultiCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取单品红包异常");
				}
			}	
		}

		if(StringUtil.isNotEmpty(cmIds)){
			String[] youhuiids = cmIds.split(",");
			for (int i = 0; i < youhuiids.length; i++) {
				try {
					PageData pd1 = new PageData();
					pd1.put("cmId", youhuiids[i]);
					CouponManagerVo couponManager = couponManagerManager.findCouponManager(pd1);
					if(couponManager == null){
						continue;
						//return CallBackConstant.FAILED.callback("优惠券不存在");
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
						if(StringUtil.isNotEmpty(couponManager.getCoupon_content())){
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
					logger.error("/app/customercoupon/receiveMultiCoupon  error", e);
					
					return CallBackConstant.FAILED.callbackError("领取优惠券异常");
				}
			}
		}
		return CallBackConstant.SUCCESS.callback();	
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
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		//useFlag  0 未使用，1已使用，3已过期
		if (StringUtil.isEmpty(pd.getString("useFlag"))) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户获取优惠券列表，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		List<CouponInfoVo> couponList = null;                 
		String useFlag = pd.getString("useFlag");
		//可用优惠券
		if("1".equals(useFlag)){ //已使用
			pd.put("status",useFlag); 
		}else{
			pd.put("canusecoupon",useFlag); 
		}
		couponList = couponInfoManager.findUserCouponListPage(pd);
		
		resultMap.put("couponList", couponList);
		logger.info("进入微信我的优惠券列表....结束");		
		return CallBackConstant.SUCCESS.callback(resultMap);
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
		logger.info("进入app我的单品红包列表....开始");
		PageData pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String token = pd.getString("token");
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		//useFlag  0 未使用，1已使用，3已过期
		if (StringUtil.isEmpty(pd.getString("useFlag"))) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("找不到红包类型");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户获取单品红包列表，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		pd.put("customerId", customer.getCustomer_id());
		List<UserSingleCouponVo> couponList = null;                 
		String useFlag = pd.getString("useFlag");
		//可用优惠券
		if("1".equals(useFlag)){ //已使用
			pd.put("status",useFlag); 
		}else{
			pd.put("canusecoupon",useFlag); 
		}
		couponList = userSingleCouponManager.queryListPage(pd);
		
		resultMap.put("couponList", couponList);
		logger.info("进入微信我的单品红包列表....结束");		
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	

	/**
	 * 单个商品下单时: 根据客户信息，商品ID和规格获取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseSingleCoupon")
	@ResponseBody
	public Map<String,Object> toChooseSingleCoupon(HttpServletRequest request) throws ParseException {
		String msg = "toChooseSingleCoupon";
		logger.info("/app/customercoupon/"+msg+" begin");
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd =this.getPageData();
		
		if (StringUtil.isEmpty(pd.getString("token"))) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(customerVo == null){
			return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
		}
		
		if(StringUtil.isEmpty(pd.getString("productId")) || StringUtil.isEmpty(pd.getString("specId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("没有可用的红包");
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("没有可用的红包");
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
		map.put("userSingleCoupons", uscVos);
		logger.info("/app/customercoupon/"+msg+" end");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 多个商品下单时: 根据客户信息，商品ID和规格获取单品红包
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseSingleCouponByProducts")
	@ResponseBody
	public Map<String,Object> toChooseSingleCouponByProducts(HttpServletRequest request) throws ParseException {
		String msg = "toChooseSingleCouponByProducts";
		logger.info("/app/customercoupon/"+msg+" begin");
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd =this.getPageData();
		
		if (StringUtil.isEmpty(pd.getString("token"))) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(customerVo == null){
			return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
		}
		
		if(StringUtil.isEmpty(pd.getString("productIds")) || StringUtil.isEmpty(pd.getString("specIds"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("没有可用的红包");
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("没有可用的红包");
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
		map.put("userSingleCoupons", uscVos);
		logger.info("/app/customercoupon/"+msg+" end");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	
	/**
	 * 商品下单时: 根据客户信息和消费总额获取优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toChooseCouponInfo")
	@ResponseBody
	public Map<String,Object> toChooseCouponInfo(HttpServletRequest request) throws ParseException {
		String msg = "toChooseCouponInfo";
		logger.info("/app/customercoupon/"+msg+" begin");
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd =this.getPageData();
		
		if (StringUtil.isEmpty(pd.getString("token"))) {
			return CallBackConstant.TOKEN_ERROR.callbackError("用户信息不存在");
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(customerVo == null){
			return CallBackConstant.TOKEN_TIME_OUT.callbackError("用户信息不存在");
		}
		
		String totalPrice=pd.getString("totalPrice"); //消费总额
		if(StringUtil.isEmpty(totalPrice)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("消费总额为空");
		}
		
		List<CouponInfoVo> coupons = new ArrayList<CouponInfoVo>();
		if(new BigDecimal(totalPrice).compareTo(BigDecimal.ZERO) == 1){
			PageData pagedata=new PageData();
			pagedata.put("customerId", String.valueOf(customerVo.getCustomer_id()));
			pagedata.put("canusecoupon","0");
			pagedata.put("price",totalPrice);
			coupons = couponInfoManager.findUserCouponListPage(pagedata);
		}
		map.put("userCoupons", coupons);
		logger.info("/app/customercoupon/"+msg+" end");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
}
