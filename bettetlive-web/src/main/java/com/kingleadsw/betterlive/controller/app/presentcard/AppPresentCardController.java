package com.kingleadsw.betterlive.controller.app.presentcard;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
import com.kingleadsw.betterlive.biz.UseLockManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.enums.SmsTempleEnums;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.Keys;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;
import com.kingleadsw.betterlive.vo.GiftCardVo;

/**
 * 礼品卡管理
 * @author zhangjing
 *
 * 2017年3月21日
 */
@Controller
@RequestMapping(value = "/app/presentcard")
public class AppPresentCardController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(AppPresentCardController.class);
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private  GiftCardManager giftCardManager;
	@Autowired
	private UseLockManager useLockManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private GiftCardRecordManager giftCardRecordManager;
	
	@RequestMapping("/findList")
	@ResponseBody
	public Map<String,Object> findList(HttpServletRequest request,HttpServletResponse response){
		logger.info("进入app我的礼品卡....开始");
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		CustomerVo customerVo = Constants.getCustomer(request);
		
		//----//日志:定位用户
		if(customerVo == null){
			logger.info("我的礼品卡:用户信息为空");
		}
		
		List<GiftCardVo> list = new ArrayList<GiftCardVo>();
		if(customerVo!=null){
			pd.put("customer_id", customerVo.getCustomer_id());
			pd.put("customerId", Constants.getCustomer(request).getCustomer_id());
			if(StringUtils.isNotBlank(customerVo.getPay_pwd())){
				map.put("payPwd", "true");
			}
			
			map.put("mobile", customerVo.getMobile());
			
			pd.put("status", 1);
			list = giftCardManager.findListGiftCard(pd);
		}
		
		BigDecimal restMoney = BigDecimal.ZERO;
		if(list!=null&&!list.isEmpty()&&list.size()>0){
			BigDecimal sum = giftCardManager.querySumCardMoney(pd);
			BigDecimal used = giftCardManager.querySumUsedMoney(pd);
			restMoney = sum.subtract(used);
		}
		
		map.put("restMoney", restMoney);
		
		logger.info("进入微信我的礼品卡....结束");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 礼品卡，使用记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/recordList")
	@ResponseBody
	public Map<String,Object> recordList(HttpServletRequest request,HttpServletResponse response){
		logger.info("进入app我的礼品卡，查询礼品卡使用记录开始");
		Map<String, Object> resultMap = new HashMap<>();
		String token = request.getParameter("token");
		logger.info("token:" + token);
		if(StringUtil.isNull(token)){
			logger.info("查询礼品卡余额:用户信息为空");
			return CallBackConstant.PARAMETER_ERROR.callback("token为空");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户设置支付密码，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		PageData pd = this.getPageData();
		pd.put("customerId", customer.getCustomer_id());
		List<GiftCardRecordVo> recordList = giftCardRecordManager.queryListPage(pd);
		resultMap.put("recordList", recordList);
		
		logger.info("进入微信我的礼品卡，查询礼品卡使用记录结束");
		
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询礼品卡可用余额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/balance")
	@ResponseBody
	public Map<String,Object> balance(HttpServletRequest request,HttpServletResponse response){
		logger.info("进入app我的礼品卡，查询礼品卡余额开始");
		String token = request.getParameter("token");
		logger.info("token:" + token);
		//----//日志:定位用户
		if(StringUtil.isNull(token)){
			logger.info("查询礼品卡余额:用户信息为空");
			return CallBackConstant.PARAMETER_ERROR.callback("token为空");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户设置支付密码，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		
		PageData pd = this.getPageData();
		pd.put("status", 1);
		pd.put("customerId", customer.getCustomer_id());
		BigDecimal restMoney = BigDecimal.ZERO;
		BigDecimal sum = giftCardManager.querySumCardMoney(pd);
		BigDecimal used = giftCardManager.querySumUsedMoney(pd);
		if(sum!=null){
			if(used==null){
				used =new BigDecimal(0);
			}
			restMoney = sum.subtract(used).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		
		logger.info("进入app我的礼品卡，查询礼品卡余额....结束");
		return CallBackConstant.SUCCESS.callback(restMoney.floatValue());
	}
	
	/**
	 * 设置支付密码，校验验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public Map<String,Object> checkCode(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/presentcard/checkCode--->begin");
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {
			logger.error("用户设置支付密码，通过token获取用户信息失败，token：" + token);
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有登陆");
		}
		if (StringUtil.isNull(phoneNum)) { //判断是否有填写手机号码提交
			logger.error("用户设置支付密码没有填手机号码");
			return CallBackConstant.PARAMETER_ERROR.callbackError("手机号码不能为空");
		}
		
		if(StringUtil.isNull(customer.getMobile())){
			logger.error(phoneNum + "用户没有绑定手机");
			return CallBackConstant.NOT_EXIST.callbackError("用户没有注册");
		}
		
		if(StringUtil.isNull(checkCode)){
			logger.error("用户设置支付密码，短信验证码为空");
			return CallBackConstant.PARAMETER_ERROR.callbackError("验证码为空");
		}
		
		if(phoneNum.equals(customer.getMobile()) ){ ////判断填写的手机号码是否和用户绑定的一致
			if (checkCode.equals(redisService.getString(phoneNum + 
					"_" + SmsTempleEnums.SETPAYPWD.getVerfiType()))) {
				redisService.delKey(redisService.getString(phoneNum + 
					"_" + SmsTempleEnums.SETPAYPWD.getVerfiType()));
				return CallBackConstant.SUCCESS.callback();
			} else {
				return CallBackConstant.VERIFI_CODE_ERROR.callbackError("验证码不正确");
			}
		} else {//手机号与绑定的手机号不一致
			logger.info(phoneNum + "设置支付密码的用户和登陆用户不一致，登陆用户：" + customer.getMobile());
			return CallBackConstant.VERIFI_CODE_ERROR.callbackError("设置支付密码的用户和登陆用户不一致");
		}
	}
	
	/**
	 * 设置支付密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/setpwd")
	@ResponseBody
	public Map<String,Object> setpwd(HttpServletRequest request,HttpServletResponse response){
		logger.info("用户设置支付密码开始");
		PageData pd = this.getPageData();
		String token = pd.getString("token");
		logger.info("token:" + token);
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		String pay_pwd = pd.getString("pay_pwd");
		logger.info("pay_pwd:" + pay_pwd);
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		
		if(customer != null){
			pd.put("customer_id", customer.getCustomer_id());
			int count = customerManager.updateCustoemrById(pd);
			if(count > 0){
				customer.setIs_paypwd(1);
				//更新redis中缓存的用户信息
				long tokenExpire = redisService.ttl(Keys.APP_CUSTOMER_PREFIX + token);
				if (tokenExpire > 0) {
					int expire = new Long(tokenExpire).intValue();
					redisService.setex(Keys.APP_TOKEN_PREFIX + token, customer.getMobile(), expire);
		            redisService.setObject(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), customer);
		            redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), expire);
				}
				
				return CallBackConstant.SUCCESS.callback();
			} else {
				return CallBackConstant.FAILED.callbackError("未更新任何数据");
			}
		} else {
			logger.info("用户信息为空-------！");
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户没有注册");
		}
	}
	
	
	/**
	 * 添加礼品卡
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/binding")
	@ResponseBody
	public Map<String,Object> binding(HttpServletRequest request,HttpServletResponse response){
		logger.info("用户绑定礼品卡开始");
		PageData pd = this.getPageData();
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		String giftCardNo = pd.getString("giftCardNo");
		if (giftCardNo == null) {
			CallBackConstant.PARAMETER_ERROR.callback("礼品卡号为空");
		}
		
		try {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.LOGIN_TIME_OUT.callbackError("访问超时,请重新登录");
			}
			GiftCardVo card = giftCardManager.queryOne(pd);
			pd.put("customerId", customer.getCustomer_id());
			if (card == null || card.getStatus() != 0) {
				return CallBackConstant.FAILED.callbackError("没有此礼品卡或者此礼品卡已使用");
			}
			
			pd.put("cardId", card.getCard_id());
			pd.put("status", 1);
			int count = giftCardManager.updateGiftCardByGid(pd);
			if (count > 0) {
				GiftCardRecordVo recordVo = new GiftCardRecordVo();
				recordVo.setCardNo(card.getCard_no());
				recordVo.setCustomerId(customer.getCustomer_id());
				BigDecimal bd = new BigDecimal(card.getCard_money());
				recordVo.setMoney(bd);
				recordVo.setRecordRemak("添加礼品卡:" + card.getCard_no() + "使用");
				byte by = 1;
				recordVo.setRecordType(by);

				giftCardRecordManager.insertGiftCard(recordVo);

				pd.put("status", 1);
				pd.put("customerId", customer.getCustomer_id());
				BigDecimal restMoney = BigDecimal.ZERO;
				BigDecimal sum = giftCardManager.querySumCardMoney(pd);
				BigDecimal used = giftCardManager.querySumUsedMoney(pd);
				restMoney = sum.subtract(used).setScale(2, BigDecimal.ROUND_HALF_UP);
				return CallBackConstant.SUCCESS.callback(restMoney.floatValue());
			}
			return CallBackConstant.FAILED.callback();	
		} catch (Exception e) {
			logger.error("/app/presentcard/binding --error", e);
			return CallBackConstant.FAILED.callback();	
		}
	}
	
	
}
