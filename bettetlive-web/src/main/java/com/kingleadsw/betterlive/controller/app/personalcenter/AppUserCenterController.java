package com.kingleadsw.betterlive.controller.app.personalcenter;

import java.math.BigDecimal;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerLotteryManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.biz.SysInviteManager;
import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.enums.SmsTempleEnums;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.Keys;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardVo;
import com.kingleadsw.betterlive.vo.SysInviteVo;
import com.kingleadsw.betterlive.vo.SystemLevelVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 微信端  个人中心管理
 * 2017-03-21 by chen
 */
@Controller
@RequestMapping(value = "/app/usercenter")
public class AppUserCenterController extends AbstractWebController{
	private static Logger logger = Logger.getLogger(AppUserCenterController.class);
	
	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private CollectionManager collectionManager;
	@Autowired
	private CustomerFansManager customerFansManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private SysInviteManager sysInviteManager;
	@Autowired
	private SystemLevelManager systemLevelManager;
	@Autowired
	private CustomerLotteryManager customerLotteryManager;
	
	/**
	 * 我的主页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/myOrderNum")
	@ResponseBody
	public Map<String,Object> myOrderNum(HttpServletRequest request){
		logger.info("/app/usercenter/myOrderNum/begin");
		PageData pd = this.getPageData();
		String token = pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		int requirementIntegral = 0;	//当前等级要求积分
		
		PageData cl = new PageData();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("articleFrom", 1);
		cl.put("statusLine", "1,3");
		//我的动态数量
		int circleCount = specialArticleManager.querySpecialArticleCount(cl);
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		CustomerFansVo fansVo = customerFansManager.queryFansCount(cl);
		int concernedCount = 0; //我的粉丝数量
		int customerCount = 0;	//我关注的数量
		if(fansVo != null){
			concernedCount = fansVo.getConcernedCount();
			customerCount = fansVo.getCustomerCount();
		}
		
		cl.put("collectionTypes", "1,3,4");
		int collectionCount = collectionManager.queryCollectionCount(cl);
		
		cl.clear();
		cl.put("canusecoupon", 0);
		cl.put("customerId", customer.getCustomer_id());
		List<CouponInfoVo> couponList = couponInfoManager.findUserCouponListPage(cl);
		int couponCount = 0;
		if(couponList != null && couponList.size() > 0){
			couponCount = couponList.size();
		}
		
		List<UserSingleCouponVo> uscs = userSingleCouponManager.queryListPage(cl);
		int uscCount = 0;
		if(uscs != null && uscs.size() > 0){
			uscCount = uscs.size();
		}
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("status", 1);
		List<GiftCardVo> myGiftCards = giftCardManager.findListGiftCard(cl);

		BigDecimal myGiftMoney = BigDecimal.ZERO;
		if (myGiftCards != null &&  myGiftCards.size() > 0) {
			BigDecimal sum = giftCardManager.querySumCardMoney(cl);
			BigDecimal used = giftCardManager.querySumUsedMoney(cl);
			myGiftMoney = sum.subtract(used);
		}
		
		PageData sysLevelParams = new PageData();
		sysLevelParams.put("status", 0);
		sysLevelParams.put("systemLevelId", customer.getLevelId());
		
		SystemLevelVo sysLevelVo = systemLevelManager.queryOne(sysLevelParams);
		if (sysLevelVo != null && sysLevelVo.getSystemLevelId() != null) {
			requirementIntegral = sysLevelVo.getRequirementIntegral();
		}
		
		//订单数量查询
		Map<String, Integer> orderNums = this.orderManager.queryMyOrderNums(customer.getCustomer_id());
		
		Map<String, Object> result = new HashMap<String, Object>();
		//待付款订单
		result.put("waitPayOrderNum", orderNums.get("waitPayOrderNum"));
		result.put("waitDeliveryOrderNum", orderNums.get("waitDeliveryOrderNum"));
		result.put("waitReceiveOrderNum", orderNums.get("waitReceiveOrderNum"));
		result.put("waitCommentOrderNum", orderNums.get("waitCommentOrderNum"));
		result.put("circleCount", circleCount);
		result.put("concernedCount", concernedCount);
		result.put("customerCount", customerCount);
		result.put("collectionCount", collectionCount);
		result.put("couponCount", couponCount);
		result.put("uscCount", uscCount);
		result.put("myGiftMoney", myGiftMoney);
		result.put("requirementIntegral", requirementIntegral);
		result.put("currentIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		result.put("accumulativeIntegral", BigDecimalUtil.subZeroAndDot(customer.getAccumulativeIntegral()));
		result.put("levelName", customer.getLevelName());
		
		logger.info("/app/usercenter/myOrderNum/end");
		return CallBackConstant.SUCCESS.callback(result);
	}
	
	/**
	 * 每日签到信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryLotterySign")
	@ResponseBody
	public Map<String,Object> queryLotterySign(HttpServletRequest request){
		PageData pd = this.getPageData();
		int customerId = 0;
		if (StringUtil.isNotNull(pd.getString("token"))) {
			CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
			if (customer != null) {
				customerId = customer.getCustomer_id();
			}
		}
		
		//每日签到活动信息
		Map<String, Object> lotterySignInfo = this.customerLotteryManager.queryCustomerSignInfo(customerId);
		
		return CallBackConstant.SUCCESS.callback(lotterySignInfo);
	}
	
	/**
	 * 邀请信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSysInvite")
	@ResponseBody
	public Map<String,Object> getSysInvite(HttpServletRequest request){
		PageData pd = this.getPageData();
		
		pd.put("dictType", 1);
		SysInviteVo sysInviteVo = sysInviteManager.queryOne(pd);
		int inviteStatus = 0; //关闭
		String inviteBanner = "";
		if(sysInviteVo != null){
			inviteStatus = sysInviteVo.getObjId();
			inviteBanner = sysInviteVo.getObjValue();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("inviteStatus",inviteStatus);
		result.put("inviteBanner",inviteBanner);
		return CallBackConstant.SUCCESS.callback(result);
	}
	

	/**
	 * 修改个人信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateUserInfo")
	@ResponseBody
	public Map<String,Object> updateUserInfo(HttpServletRequest request){
		logger.info("/app/usercenter/updateUserInfo/begin");
		PageData pd = this.getPageData();
		String token = pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		pd.put("mobile",customer.getMobile());
		customer = customerManager.findCustomer(pd);  //通过手机号码查询用户
		if(null == customer){
			return CallBackConstant.NOT_EXIST.callback();
		}
		
		pd.put("customer_id", customer.getCustomer_id());
		
		try {
			if (StringUtil.isNotNull(pd.getString("unionid"))) {
				PageData openpd = new PageData();
				openpd.put("unionid", pd.getString("unionid"));
				CustomerVo checkCust = this.customerManager.queryOne(openpd);
				if(checkCust != null && checkCust.getCustomer_id() > 0){
					return CallBackConstant.FAILED.callbackError("微信号已被其他账户绑定");
				}
			}
			int result = customerManager.updateCustoemrById(pd);
			if (result > 0) {
				//更新redis中缓存的用户信息
				long tokenExpire = redisService.ttl(Keys.APP_CUSTOMER_PREFIX + customer.getMobile());
				if (tokenExpire > 0) {
					int expire = new Long(tokenExpire).intValue();
					if (StringUtil.isNotNull(pd.getString("head_url"))) {
						customer.setHead_url(pd.getString("head_url"));
					}
					if (StringUtil.isNotNull(pd.getString("nickname"))) {
						customer.setNickname(pd.getString("nickname"));
					}
					if (StringUtil.isNotNull(pd.getString("sex"))) {
						customer.setSex(pd.getInteger("sex"));
					}
					if (StringUtil.isNotNull(pd.getString("birthday"))) {
						customer.setBirthday(pd.getString("birthday"));
					}
					if (StringUtil.isNotNull(pd.getString("signature"))) {
						customer.setSignature(pd.getString("signature"));
					}
					if (StringUtil.isNotNull(pd.getString("openid"))) {
						customer.setOpenid(pd.getString("openid"));
					}
					if (StringUtil.isNotNull(pd.getString("unionid"))) {
						customer.setUnionid(pd.getString("unionid"));
					}
					
					customer.setToken(token);
					
					customer.setPassword(null); //将登陆密码设置为null
		            if (customer.getPay_pwd() != null && !"".equals(customer.getPay_pwd())) {
		            	customer.setIs_paypwd(1);
		            } else {
		            	customer.setIs_paypwd(0);
		            }
		            customer.setPay_pwd(null); //将支付设置为null
					
					
					redisService.setex(Keys.APP_TOKEN_PREFIX + token, customer.getMobile(), expire);
		            redisService.setObject(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), customer);
		            redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), expire);
				}
				return CallBackConstant.SUCCESS.callback();
			}

			return CallBackConstant.FAILED.callbackError("修改失败，未更新任何数据");
		} catch (Exception e) {
			logger.error("/app/usercenter/updateUserInfo", e);
			return CallBackConstant.FAILED.callbackError("系统异常");
		}
	}
	
	
	/**
	 * 后台验证密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/affirmPayPwd")
	@ResponseBody
	public Map<String,Object> affirmPayPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="affirmPayPwd";
		logger.info("/app/usercenter/ "+msg+"  start ...");
		
		PageData pd = this.getPageData();
		String oldPwd = pd.getString("payPwd");
		CustomerVo customer = Constants.getCustomer(request);
			
		if(!oldPwd.equals(customer.getPay_pwd())){
			return CallBackConstant.FAILED.callback();
		}
		return CallBackConstant.SUCCESS.callback();
	}
	
	
	/**
	 * 校验用户短信验证码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkSmsCode")
	@ResponseBody
	public Map<String,Object> checkSmsCode(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/presentcard/checkSmsCode--->begin");
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");
		
		//---->日志信息
		logger.info("校验操作页面传入的：手机号:"+phoneNum+",验证码:"+checkCode);
		
		
		CustomerVo customer = Constants.getCustomer(request);
		customer = this.customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if (StringUtil.isNull(phoneNum)) { 
			//判断是否有填写手机号码提交
			logger.error("用户校验用户验证码没有填手机号码");
			return CallBackConstant.FAILED.callbackError("手机号码不能为空");
		}
		
		if(phoneNum.equals(customer.getMobile()) || StringUtil.isNull(customer.getMobile())){ ////判断填写的手机号码是否和用户绑定的一致
			if (StringUtil.isNull(checkCode)) {
				logger.error("用户【" + phoneNum + "】验证码为空");
				return CallBackConstant.FAILED.callbackError("验证码不能为空");
			}
			
			if (checkCode.equals(redisService.getString(phoneNum))) {
				return CallBackConstant.SUCCESS.callBackByMsg("短信验证成功");
			}
			return CallBackConstant.FAILED.callbackError("验证码已经过期");
		} else {//手机号与绑定的手机号不一致
			return CallBackConstant.FAILED.callbackError("用户没有绑定手机或者绑定手机号码不一致");
		}
		
	}
	
	/**
	 * 修改手机号码时验证用户手机信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkOldPhone")
	@ResponseBody
	public Map<String,Object> checkOldPhone(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		String token=pd.getString("token");
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		//CustomerVo customerVo=redisService.getObject(Keys.APP_CUSTOMER_PREFIX + token);
		if(customerVo==null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		String mobile=pd.getString("mobile");
		String code=pd.getString("code");
		
		if(StringUtil.isNull(mobile)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("手机号码不能为空");
		}
		
		if(StringUtil.isNull(code)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
		}
		
		String verfiType = "modifyphone";
		String verfityCode = redisService.getString(mobile + "_" + verfiType);

		if (StringUtil.isNull(verfityCode)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码为空");
		}
		
		if (Integer.parseInt(verfityCode)!=Integer.parseInt(code)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码错误");
		}
		PageData pd2=new PageData();
		pd2.put("customer_id", customerVo.getCustomer_id());
		CustomerVo customerVo2 =customerManager.queryOne(pd2);
		
		if(customerVo2.getMobile().equals(mobile)){
			return CallBackConstant.SUCCESS.callBackByMsg("验证手机成功");
		}
		
		return CallBackConstant.FAILED.callbackError("手机验证错误");
	}
	
	/**
	 * 查询手机号是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkMobileExist")
	@ResponseBody
	public Map<String,Object> checkMobileExist(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		
		String mobile = pd.getString("mobile");
		if (StringUtil.isNull(mobile)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("手机号码不能为空");
		}
		
		CustomerVo customerVo = customerManager.selectByMobile(mobile);
		int hasFlag = 0;  //不存在
		if(customerVo != null){
			hasFlag = 1;
		}
		
		return CallBackConstant.SUCCESS.callback(hasFlag);
	}
	
	
	/**
	 * 修改用户手机号码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateNewPhone")
	@ResponseBody
	public Map<String,Object> updateNewPhone(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		String token=pd.getString("token");
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		
		if (customerVo == null) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		String mobile2=customerVo.getMobile();
		String mobile=pd.getString("mobile");
		String code=pd.getString("code");
		
		if(StringUtil.isNull(mobile)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("手机号码不能为空");
		}
		
		if(StringUtil.isNull(code)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
		}
		
		String verfiType = "modifyphone";
		String verfityCode = redisService.getString(mobile + "_" + verfiType);

		if (StringUtil.isNull(verfityCode)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码失效");
		}
		
		if (Integer.parseInt(verfityCode)!=Integer.parseInt(code)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码错误");
		}
		
		PageData pd2=new PageData();
		pd2.put("mobile", mobile);
		CustomerVo customerVo2=customerManager.findCustomer(pd2);
		if(customerVo2!=null){
			return CallBackConstant.FAILED.callback("该手机号码已注册");
		}
		customerVo.setMobile(mobile);
		pd2.put("customer_id", customerVo.getCustomer_id());
		customerManager.updateCustoemrById(pd2);
		
		
		long tokenExpire = redisService.ttl(Keys.APP_CUSTOMER_PREFIX + mobile2);
		if (tokenExpire > 0) {
			int expire = new Long(tokenExpire).intValue();
			redisService.setex(Keys.APP_TOKEN_PREFIX + token, customerVo.getMobile(), expire);
            redisService.setObject(Keys.APP_CUSTOMER_PREFIX + customerVo.getMobile(), customerVo);
            redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + customerVo.getMobile(), expire);
		}
		
		return CallBackConstant.SUCCESS.callBackByMsg("修改成功");
	}
	
	/**
	 * 根据token获取用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getUserInfoByToken")
	@ResponseBody
	public Map<String,Object> getUserInfoByToken(HttpServletRequest request,HttpServletResponse response){
		PageData pageDate=this.getPageData();
		//StringUtil
		String token=pageDate.getString("token");
		if(StringUtil.isNull(token)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("token不能为空"); 
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		if (customerVo == null) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		return CallBackConstant.SUCCESS.callback(customerVo);
	}

	
	
	
	
	
	
	/**
	 * 下单时 使用礼品卡
	 * 
	 * 目前礼品卡与优惠券不能够同时使用,所以需要去除优惠券,在绑定礼品卡
	 */
	@RequestMapping(value="/affirmPayPwdByOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> affirmPayPwdByOrder(HttpServletRequest request,HttpServletResponse response,Model model){
		
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");  							//用户标识
		if (com.kingleadsw.betterlive.init.StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		String oldPwd = pd.getString("payPwd");
		if (com.kingleadsw.betterlive.init.StringUtil.isEmpty(oldPwd)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("支付密码不能为空");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		
		if (null == customer) {
			return CallBackConstant.DATA_NOT_FOUND.callbackError("用户信息为空");
		}
		
		pd.put("mobile",customer.getMobile());
		customer = customerManager.findCustomer(pd);
		if(null==customer){
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户不存在");
		}
		
		if(!oldPwd.equals(customer.getPay_pwd())){
			return CallBackConstant.FAILED.callbackError("密码错误");
		}
		return CallBackConstant.SUCCESS.callback();
	}

	
	
	/**
	 * 注册用户  2017-4-26
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request,HttpServletResponse response){
		logger.info("App端开始注册用户....startting");
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		String mobile=pd.getString("mobile");
		if(null==mobile || "".equals(mobile)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("手机号码不能为空");
		}
		
		String password=pd.getString("password");
		if(null==password || "".equals(password)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("登录密码不能为空");
		}
		
		String verifiCode = pd.getString("verifiCode");
		if(null == verifiCode || "".equals(verifiCode)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
		}
		
		String code = redisService.getString(mobile + "_" +  SmsTempleEnums.REGISTER.getVerfiType());
		if(null == code || "".equals(code)){
			return CallBackConstant.VERIFI_CODE_ERROR.callbackError("验证码已过期");
		}
		
		if(!code.equals(verifiCode)){
			return CallBackConstant.VERIFI_CODE_ERROR.callbackError("验证码错误");
		}
		
		int result = 0;
		try {
			PageData pds=new PageData();
			pds.put("mobile",mobile);
			CustomerVo customer = customerManager.findCustomer(pds);
			CustomerVo customerVo = new CustomerVo();
			if(null == customer){  //用户不存在
				customerVo.setMobile(mobile);
				customerVo.setPassword(password);
				customerVo.setSource(pd.getString("reg_source"));
				
				PageData sysLevelParams = new PageData();
				sysLevelParams.put("status", 0);
				sysLevelParams.put("level", 1);
				SystemLevelVo sysLevelVo = systemLevelManager.queryOne(sysLevelParams);
				if (sysLevelVo != null && sysLevelVo.getSystemLevelId() == null) {
					customerVo.setLevelId(sysLevelVo.getSystemLevelId());
				}
				
				result = customerManager.insertCustomer(customerVo);
				if(result>0){
					customerVo.setPassword(null); //将登陆密码设置为null
			        customerVo.setPay_pwd(null); //将支付设置为null
					customer = this.customerManager.queryOne(pds);
					couponInfoManager.insertRegisterCoupon(customer);
					String token = StringUtil.get32UUID();
			        customerVo.setToken(token);
			        redisService.setex(Keys.APP_TOKEN_PREFIX + token, mobile, WebConstant.TOKEN_TIME);
			        redisService.setObject(Keys.APP_CUSTOMER_PREFIX + mobile, customerVo);
			        redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + mobile, WebConstant.TOKEN_TIME);
				}
			//用户在微信端已经绑定，没有在app注册过
			}else if(null==customer.getPassword() || "".equals(customer.getPassword())){
				pds.put("customer_id",customer.getCustomer_id());
				pds.put("password", password);
				result = customerManager.updateCustoemrById(pds);
			} else {
				return CallBackConstant.FAILED.callbackError("用户已存在");
			}
			if(result>0){
				map = CallBackConstant.SUCCESS.callback(customerVo);
		    	map.put("customerVo", customerVo); //为了兼容旧版本， 后续删除掉
		    	return map;
			}
		} catch (Exception e) {
			logger.info("App端结束注册用户....ending");
			return CallBackConstant.FAILED.callbackError("注册异常");
		}
		
		return CallBackConstant.FAILED.callbackError("注册失败");
	}
	

	
	@RequestMapping("/forgetpwd")
	@ResponseBody
	public Map<String, Object> forgetpwd() {

		PageData pd = this.getPageData();

		// 当前密码
		String password = pd.getString("password");
        //手机号码
		String mobile = pd.getString("mobile");
        //验证码
		String code = pd.getString("code");

		if (StringUtil.isNull(code)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
		}

		String verfiType = "forgetpwd";
		String verfityCode = redisService.getString(mobile + "_" + verfiType);

		if (StringUtil.isNull(verfityCode)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码失效为空");
		}
		
		if (Integer.parseInt(verfityCode)!=Integer.parseInt(code)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码错误");
		}

		if (StringUtil.isNull(password)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("请输入密码");
		}
		PageData queryMobilePd = new PageData();

		queryMobilePd.put("mobile", mobile);

		CustomerVo userCheck = customerManager.findCustomer(queryMobilePd);

		if (userCheck == null) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("该用户不存在");
		}
		try {
			PageData pd2=new PageData();
			pd2.put("password", password);
			pd2.put("customer_id", userCheck.getCustomer_id());
			int result=customerManager.updateCustoemrById(pd2);
			if(result>0){
				return CallBackConstant.SUCCESS.callback();
			}else{
				return CallBackConstant.FAILED.callback();
			}
			
		} catch (Exception e) {
			return CallBackConstant.FAILED.callbackError("重置失败");
		}
	}
	
	
	
	
	@RequestMapping("/getUserPassowrd")
	@ResponseBody
	public Map<String, Object> getUserPassowrd() {
		PageData pd = this.getPageData();
		String token = pd.getString("token"); //用户标识
		String password = pd.getString("password");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.DATA_NOT_FOUND.callback();
		}
		
		CustomerVo customervo = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(!password.equals(customervo.getPassword())){
			return CallBackConstant.PASSWORD_WRONG.callback();
		}
		return CallBackConstant.SUCCESS.callback();
	}
	
	@RequestMapping("/editUserPassowrd")
	@ResponseBody
	public Map<String, Object> editUserPassowrd() {
		PageData pd = this.getPageData();
		String token = pd.getString("token"); //用户标识
		//新密码 
		String password = pd.getString("password");
		CustomerVo customer = customerManager.queryCustomerByToken(token);
	    if(customer==null){
	    	return CallBackConstant.DATA_NOT_FOUND.callback();
	    }
	    
	    try {
			PageData pd2=new PageData();
			pd2.put("password", password);
			pd2.put("customer_id", customer.getCustomer_id());
			int result=customerManager.updateCustoemrById(pd2);
			if(result>0){
				return CallBackConstant.SUCCESS.callback();
			}else{
				return CallBackConstant.FAILED.callback();
			}
			
		} catch (Exception e) {
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 第三方登录绑定手机号码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/bingphone")
	@ResponseBody
	public Map<String,Object> bingPhone(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		// 用户微信唯一标识
		String unionid = pd.getString("unionid");
        //手机号码
		String mobile = pd.getString("mobile");
        //验证码
		String code = pd.getString("code");
		//用户微信openid
		String openid = pd.getString("openid");
		//微信头像
		String head_url = pd.getString("head_url");
		//微信昵称
		String nickname = pd.getString("nickname");
		
		// 登录密码
		String password = pd.getString("password");
		
		//注册来源
		String regSource = pd.getString("reg_source");
		
		if (StringUtil.isNull(unionid)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("unionid不能为空");
		}
		
		if (StringUtil.isNull(mobile)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("mobile不能为空");
		}
		
		/*if (StringUtil.isNull(password)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("password不能为空");
		}*/
		
		/*if (StringUtil.isNull(code)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码不能为空");
		}*/

		String verfityCode = redisService.getString(mobile + "_" + SmsTempleEnums.BINDMOBILE.getVerfiType());

		if (StringUtil.isNull(verfityCode)) {
			return CallBackConstant.VERIFI_CODE_ERROR.callbackError("验证码失效为空");
		}
		
		if (!code.equals(verfityCode)) {
			return CallBackConstant.VERIFI_CODE_ERROR.callbackError("验证码错误");
		}

    	PageData pageData=new PageData();
    	pageData.put("mobile", mobile);
        CustomerVo customerVo = customerManager.queryOne(pageData);
        pageData.clear();
     	pageData.put("unionid", unionid);
        CustomerVo customerVo1 = customerManager.queryOne(pageData);
        if(customerVo == null && customerVo1 == null){
        	customerVo = new CustomerVo();
        	customerVo.setUnionid(unionid);
        	customerVo.setPassword(password);
        	customerVo.setMobile(mobile);
        	customerVo.setCreate_time(DateUtil.FormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	customerVo.setOpenid(openid);
        	customerVo.setHead_url(head_url);
        	customerVo.setNickname(nickname);
        	customerVo.setSource(regSource);
        	int count = customerManager.insertCustomer(customerVo);
        	customerVo = customerManager.queryOne(pageData);
        	if(count>0){
				couponInfoManager.insertRegisterCoupon(customerVo);
			}
        }else {
        	int cusId = 0;
        	if(customerVo != null && customerVo.getCustomer_id() > 0){
        		cusId = customerVo.getCustomer_id();
        	}else{
        		cusId = customerVo1.getCustomer_id();
        		customerVo = customerVo1;
        	}
        	PageData pd2 = new PageData();
        	pd2.put("password",password);
        	pd2.put("unionid", unionid);
        	pd2.put("customer_id", cusId);
        	pd2.put("mobile", mobile);
        	pd2.put("openid", openid);
        	pd2.put("head_url", head_url);
        	pd2.put("nickname", nickname);
        	customerManager.updateCustoemrById(pd2);
        	customerVo.setMobile(mobile);
        	customerVo.setUnionid(unionid);
        	customerVo.setNickname(nickname);
        	customerVo.setHead_url(head_url);
        }
        
        String token = StringUtil.get32UUID();
        customerVo.setToken(token);
        //不能将登陆密码和支付密码返回给客户端
        if (customerVo.getPay_pwd() != null && !"".equals(customerVo.getPay_pwd())) {
        	customerVo.setIs_paypwd(1);
        } else {
        	customerVo.setIs_paypwd(0);
        }
        
        customerVo.setPassword(null); //将登陆密码设置为null
        customerVo.setPay_pwd(null); //将支付设置为null
        redisService.setex(Keys.APP_TOKEN_PREFIX + token, mobile, WebConstant.TOKEN_TIME);
        redisService.setObject(Keys.APP_CUSTOMER_PREFIX + mobile, customerVo);
        redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + mobile, WebConstant.TOKEN_TIME);
        return CallBackConstant.SUCCESS.callback(customerVo);
	} 
}
