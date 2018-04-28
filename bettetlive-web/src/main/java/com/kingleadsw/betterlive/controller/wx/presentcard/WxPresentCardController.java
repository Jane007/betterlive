package com.kingleadsw.betterlive.controller.wx.presentcard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.kingleadsw.betterlive.biz.BanManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
import com.kingleadsw.betterlive.biz.MobileMessageManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.BanVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;
import com.kingleadsw.betterlive.vo.GiftCardVo;
import com.kingleadsw.betterlive.vo.MobileMessageVo;

/**
 * 礼品卡管理
 * 
 * @author zhangjing
 *
 *         2017年3月21日
 */
@Controller
@RequestMapping(value = "/weixin/presentcard")
public class WxPresentCardController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(WxPresentCardController.class);
	
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private MobileMessageManager mobileMessageManager;
	@Autowired
	private BanManager banManager;

	@Autowired
	private RedisService redisService;

	@Autowired
	private GiftCardRecordManager giftCardRecordManager;

	@RequestMapping("/findList")
	public ModelAndView findList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		logger.info("进入微信我的礼品卡....开始");
		PageData pd = this.getPageData();
		CustomerVo customerVo = Constants.getCustomer(request);

		// ----//日志:定位用户
		if (customerVo == null || customerVo.getCustomer_id() == null) {
			 return new ModelAndView("redirect:/weixin/tologin");
		}

		List<GiftCardVo> list = new ArrayList<GiftCardVo>();
		pd.put("customer_id", customerVo.getCustomer_id());
		pd.put("customerId", customerVo.getCustomer_id());
		if (StringUtils.isNotBlank(customerVo.getPay_pwd())) {
			model.addAttribute("payPwd", "true");
		}
		model.addAttribute("mobile", customerVo.getMobile());
		pd.put("status", 1);
		list = giftCardManager.findListGiftCard(pd);

		BigDecimal restMoney = BigDecimal.ZERO;
		if (list != null && !list.isEmpty() && list.size() > 0) {
			BigDecimal sum = giftCardManager.querySumCardMoney(pd);
			BigDecimal used = giftCardManager.querySumUsedMoney(pd);
			restMoney = sum.subtract(used);
		}
		model.addAttribute("restMoney", restMoney);
		ModelAndView modelAndView = new ModelAndView(
				"weixin/usercenter/wx_presentcard");
		logger.info("进入微信我的礼品卡....结束");
		return modelAndView;
	}

	@RequestMapping("/toPresentcard")
	public ModelAndView toPresentcard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		logger.info("进入微信我的礼品卡....开始");
		PageData pd = this.getPageData();
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}

		ModelAndView modelAndView = new ModelAndView(
				"weixin/presentcard/wx_new_presentcard");
		customerVo = customerManager.selectByPrimaryKey(customerVo
				.getCustomer_id());
		// ----//日志:定位用户
		if (customerVo == null) {
			modelAndView.addObject("tipsTitle", "账户信息提示");
			modelAndView.addObject("tipsContent", "您的账户信息异常");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}

		List<GiftCardVo> list = new ArrayList<GiftCardVo>();
		
		pd.put("customer_id", customerVo.getCustomer_id());
		pd.put("customerId", customerVo.getCustomer_id());
		if (StringUtils.isNotBlank(customerVo.getPay_pwd())) {
			model.addAttribute("payPwd", "true");
		}
		model.addAttribute("mobile", customerVo.getMobile());
		pd.put("status", 1);
		list = giftCardManager.findListGiftCard(pd);

		BigDecimal restMoney = BigDecimal.ZERO;
		if (list != null && !list.isEmpty() && list.size() > 0) {
			BigDecimal sum = giftCardManager.querySumCardMoney(pd);
			BigDecimal used = giftCardManager.querySumUsedMoney(pd);
			restMoney = sum.subtract(used);
		}
		model.addAttribute("restMoney", restMoney);

		logger.info("进入微信我的礼品卡....结束");
		return modelAndView;
	}

	@RequestMapping("setPresentWhat")
	public ModelAndView setPresentWhat(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(
				"weixin/presentcard/wx_new_what");
		return modelAndView;
	}

	@RequestMapping("setPayPassword")
	public ModelAndView setPayPassword(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(
				"weixin/usercenter/wx_setpaypwd");
		return modelAndView;
	}

	@RequestMapping("sendMessage")
	@ResponseBody
	public void sendMessage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		CustomerVo customer = Constants.getCustomer(request);
		// ----日志信息
		logger.info("请求验证码：对应手机号：" + phoneNum);

		JSONObject json = new JSONObject();
		if (StringUtil.isNull(phoneNum)) { // 校验用户
			json.put("result", "fail");
			json.put("msg", "请输入手机号码");
			logger.info(phoneNum + "请输入手机号码");
			return;
		}
		PageData msgpd = new PageData();
//		msgpd.put("requestIp", getIpAddr(request));
//		List<BanVo> banIps = this.banManager.queryListPage(msgpd);
//		if (banIps != null && banIps.size() > 0) {
//			json.put("result", "fail");
//			json.put("msg", "频繁请求，已被禁止访问");
//			this.outObjectToJson(json, response);
//			return;
//		}
//		msgpd.clear();
		
		msgpd.put("requestMobile", phoneNum);
		List<BanVo> banPhones = this.banManager.queryListPage(msgpd);
		if (banPhones != null && banPhones.size() > 0) {
			json.put("result", "fail");
			json.put("msg", "频繁请求，已被禁止访问");
			this.outObjectToJson(json, response);
			return;
		}

//		msgpd.clear();
//		msgpd.put("requestIp", getIpAddr(request));
//		msgpd.put("msgType", 1);
//		msgpd.put("queryFlag", 2);
//		List<MobileMessageVo> msgIps = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgIps != null && msgIps.size() >= 5) {
//			BanVo banVo = new BanVo();
//			banVo.setRequestIp(getIpAddr(request));
//			banVo.setRequestMobile("");
//			banManager.insert(banVo);
//			json.put("result", "fail");
//			json.put("msg", "频繁请求，已被禁止访问");
//			this.outObjectToJson(json, response);
//			return;
//		}

		msgpd.clear();
		msgpd.put("requestMobile", phoneNum);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 2);
		List<MobileMessageVo> msgPhones = mobileMessageManager
				.queryListPage(msgpd);
		if (msgPhones != null && msgPhones.size() >= 5) {
			BanVo banVo = new BanVo();
			banVo.setRequestIp("");
			banVo.setRequestMobile(phoneNum);
			banManager.insert(banVo);
			json.put("result", "fail");
			json.put("msg", "频繁请求，已被禁止访问");
			this.outObjectToJson(json, response);
			return;
		}

		int custId = 0;
		if (customer != null && customer.getCustomer_id() != null) {
			custId = customer.getCustomer_id();
		}
		Random rnd = new Random();
		int num = rnd.nextInt(89999) + 10000;
//		msgpd.clear();
//		msgpd.put("msgType", 1);
//		msgpd.put("queryFlag", 1);
//		msgpd.put("requestIp", getIpAddr(request));
//		msgpd.put("tag", 1);
//		List<MobileMessageVo> msgips = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgips != null && msgips.size() > 1) {
//			json.put("result", "fail");
//			json.put("msg", "一分钟内只能请求发送两次");
//			logger.info("[" + getIpAddr(request) + "]" + "一分钟内只能请求发送两次");
//			this.insertMobileMessage(custId, num, phoneNum, request, 0);
//			this.outObjectToJson(json, response);
//			return;
//		}

//		msgpd.put("tag", 60);
//		List<MobileMessageVo> msgipvos = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgipvos != null && msgipvos.size() > 3) {
//			json.put("result", "fail");
//			json.put("msg", "一小时内只能请求发送三次");
//			logger.info("[" + getIpAddr(request) + "]" + "一小时内只能请求发送三次");
//			this.insertMobileMessage(custId, num, phoneNum, request, 0);
//			this.outObjectToJson(json, response);
//			return;
//		}

		msgpd.clear();
		msgpd.put("tag", 1);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 1);
		msgpd.put("requestMobile", phoneNum);
		List<MobileMessageVo> msgms = mobileMessageManager
				.queryListPage(msgpd);
		if (msgms != null && msgms.size() > 1) {
			json.put("result", "fail");
			json.put("msg", "一分钟内只能请求发送两次");
			logger.info(phoneNum + "1分钟内请求发送次数不能超过2条");
			this.insertMobileMessage(custId, num, phoneNum, request, 0);
			this.outObjectToJson(json, response);
			return;
		}

		msgpd.put("tag", 60);
		List<MobileMessageVo> msgs = mobileMessageManager
				.queryListPage(msgpd);
		if (msgs != null && msgs.size() > 2) {
			json.put("result", "fail");
			json.put("msg", "一小时内只能请求发送三次");
			logger.info(phoneNum + "1小时内请求发送次数不能超过3条");
			this.insertMobileMessage(custId, num, phoneNum, request, 0);
			this.outObjectToJson(json, response);
			return;
		}

		
		 boolean sendResult = SendMsgUtil.sendCheckCode(phoneNum, num);
		 logger.info("sendMessage----------code"+sendResult); 
		 int flage = 0;
		 if(sendResult) { //发送成功
			flage = 1;
			redisService.setex(phoneNum, String.valueOf(num), 300); // 验证码  保存本地缓存，有效期5分钟
			json.put("result", "succ");
			json.put("msg", "短信发送成功");
			logger.info("IP：" + getIpAddr(request) + "," + phoneNum + "获取验证码【"
					+ num + "】,有效期5分钟");
		
		  } else { 
			  json.put("result", "fail"); 
			  json.put("msg", "短信发送失败"); 
		  }
		  this.insertMobileMessage(custId, num, phoneNum, request, flage);

		this.outObjectToJson(json, response);
	}

	@RequestMapping("sendMessageEdit")
	@ResponseBody
	public void sendMessageEdit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		logger.info("/weixin/presentcard/sendMessageEdit--->begin");
		JSONObject json = new JSONObject();
		
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		if(StringUtil.isNull(phoneNum)){
			json.put("result", "fail");
			json.put("msg", "请输入手机号码");
			this.outObjectToJson(json, response);
			return;
		}
		
		CustomerVo customer = Constants.getCustomer(request);
		customer = customerManager
				.selectByPrimaryKey(customer.getCustomer_id());
		// ----日志信息
		logger.info("请求验证码：对应手机号：" + phoneNum);

		if (StringUtil.isNull(phoneNum)) {
			json.put("result", "fail");
			json.put("msg", "请输入手机号码");
			this.outObjectToJson(json, response);
			return;
		}

		PageData msgpd = new PageData();
//		msgpd.put("requestIp", getIpAddr(request));
//		List<BanVo> banIps = this.banManager.queryListPage(msgpd);
//		if (banIps != null && banIps.size() > 0) {
//			json.put("result", "fail");
//			json.put("msg", "频繁请求，已被禁止访问");
//			this.outObjectToJson(json, response);
//			return;
//		}
		
		msgpd.clear();
		msgpd.put("requestMobile", phoneNum);
		List<BanVo> banPhones = this.banManager.queryListPage(msgpd);
		if (banPhones != null && banPhones.size() > 0) {
			json.put("result", "fail");
			json.put("msg", "频繁请求，已被禁止访问");
			this.outObjectToJson(json, response);
			return;
		}

//		msgpd.clear();
//		msgpd.put("requestIp", getIpAddr(request));
//		msgpd.put("msgType", 1);
//		msgpd.put("queryFlag", 2);
//		List<MobileMessageVo> msgIps = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgIps != null && msgIps.size() >= 5) {
//			BanVo banVo = new BanVo();
//			banVo.setRequestIp(getIpAddr(request));
//			banVo.setRequestMobile("");
//			banManager.insert(banVo);
//			json.put("result", "fail");
//			json.put("msg", "频繁请求，已被禁止访问");
//			this.outObjectToJson(json, response);
//			return;
//		}

		msgpd.clear();
		msgpd.put("requestMobile", phoneNum);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 2);
		List<MobileMessageVo> msgPhones = mobileMessageManager
				.queryListPage(msgpd);
		if (msgPhones != null && msgPhones.size() >= 5) {
			BanVo banVo = new BanVo();
			banVo.setRequestIp("");
			banVo.setRequestMobile(phoneNum);
			banManager.insert(banVo);
			json.put("result", "fail");
			json.put("msg", "频繁请求，已被禁止访问");
			this.outObjectToJson(json, response);
			return;
		}

		int custId = 0;
		if (customer != null && customer.getCustomer_id() != null) {
			custId = customer.getCustomer_id();
		}
		Random rnd = new Random();
		int num = rnd.nextInt(89999) + 10000;
		
//		msgpd.clear();
//		msgpd.put("msgType", 1);
//		msgpd.put("queryFlag", 1);
//		msgpd.put("requestIp", getIpAddr(request));
//		msgpd.put("tag", 1);
//		List<MobileMessageVo> msgips = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgips != null && msgips.size() > 1) {
//			json.put("result", "fail");
//			json.put("msg", "一分钟内只能请求发送两次");
//			logger.info("[" + getIpAddr(request) + "]" + "一分钟内只能请求发送两次");
//			this.insertMobileMessage(custId, num, phoneNum, request, 0);
//			this.outObjectToJson(json, response);
//			return;
//		}
//
//		msgpd.put("tag", 60);
//		List<MobileMessageVo> msgipvos = mobileMessageManager
//				.queryListPage(msgpd);
//		if (msgipvos != null && msgipvos.size() > 3) {
//			json.put("result", "fail");
//			json.put("msg", "一小时内只能请求发送三次");
//			logger.info("[" + getIpAddr(request) + "]" + "一小时内只能请求发送三次");
//			this.insertMobileMessage(custId, num, phoneNum, request, 0);
//			this.outObjectToJson(json, response);
//			return;
//		}

		msgpd.clear();
		msgpd.put("tag", 1);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 1);
		msgpd.put("requestMobile", phoneNum);
		List<MobileMessageVo> msgms = mobileMessageManager
				.queryListPage(msgpd);
		if (msgms != null && msgms.size() > 1) {
			json.put("result", "fail");
			json.put("msg", "一分钟内只能请求发送两次");
			logger.info(phoneNum + "1分钟内请求发送次数不能超过2条");
			this.insertMobileMessage(custId, num, phoneNum, request, 0);
			this.outObjectToJson(json, response);
			return;
		}

		msgpd.put("tag", 60);
		List<MobileMessageVo> msgs = mobileMessageManager
				.queryListPage(msgpd);
		if (msgs != null && msgs.size() > 1) {
			json.put("result", "fail");
			json.put("msg", "一小时内只能请求发送三次");
			logger.info(phoneNum + "1小时内请求发送次数不能超过3条");
			this.insertMobileMessage(custId, num, phoneNum, request, 0);
			this.outObjectToJson(json, response);
			return;
		}
		
		 boolean sendResult = SendMsgUtil.sendCheckCode(phoneNum, num);
		 logger.info("sendMessage----------code"+sendResult);
		 int flage = 0;
		 if (sendResult) { //发送成功
			 flage = 1;
		    redisService.setex(phoneNum, String.valueOf(num), 300); // 验证码 保存本地缓存，有效期5分钟
		    model.addAttribute("num", num); 
		    
			json.put("result", "succ");
			json.put("msg", "短信发送成功");
			logger.info("IP：" + getIpAddr(request) + "," + phoneNum + "获取验证码【"
					+ num + "】,有效期5分钟");
		
		  } else { 
			  json.put("result", "fail"); 
			  json.put("msg", "短信发送失败"); 
		  }
		this.insertMobileMessage(custId, num, phoneNum, request, flage);
		logger.info("/weixin/presentcard/sendMessageEdit--->end");
		this.outObjectToJson(json, response);
	}

	public void insertMobileMessage(int custId, int num, String phoneNum,
			HttpServletRequest request, int flag) {
		MobileMessageVo mmvo = new MobileMessageVo();
		mmvo.setCustomerId(custId);
		mmvo.setMsgContent("[" + phoneNum + "]绑定手机号，验证码[" + num + "]");
		mmvo.setMsgType(1);
		mmvo.setRequestIp(getIpAddr(request));
		mmvo.setRequestMobile(phoneNum);
		mmvo.setSendFlag(flag);
		mobileMessageManager.insert(mmvo);
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("x-forwarded-for");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 校验用户短信验证码是否正确
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkSmsCode")
	@ResponseBody
	public JSONObject checkSmsCode(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");

		// ---->日志信息
		logger.info("校验操作页面传入的：手机号:" + phoneNum + ",验证码:" + checkCode);

		JSONObject json = new JSONObject();
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() <= 0){
			json.put("result","failure");
			json.put("msg","请先登录");
			this.outObjectToJson(json, response);	
		}
		
		if (StringUtil.isNull(phoneNum)) { // 判断是否有填写手机号码提交
			json.put("result", "fail");
			json.put("msg", "手机号码不能为空");
			return json;
		}
		
		if (StringUtil.isNull(checkCode)) {
			json.put("result", "fail");
			json.put("msg", "验证码不能为空");
			logger.error("用户【" + phoneNum + "】验证码为空");
			return json;
		}
		
		String redisCode = redisService.getString(phoneNum);
		if(StringUtil.isNull(redisCode)){
			json.put("result", "fail");
			json.put("msg", "验证码已过期");
			return json;
		}
		
		if (!checkCode.equals(redisCode)) {
			json.put("result", "fail");
			json.put("msg", "请输入正确的验证码");
			return json;
		}
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if (customer == null || StringUtil.isNull(customer.getMobile())) {
			json.put("result", "fail");
			json.put("msg", "用户没有绑定手机");
			return json;
		}

		if (!phoneNum.equals(customer.getMobile())) { // //判断填写的手机号码是否和用户绑定的一致
			json.put("result", "fail");
			json.put("msg", "用户号码与绑定号码一致");
			return json;
		}
		
		json.put("result", "success");
		json.put("msg", "短信验证成功");
		redisService.delKey(phoneNum);
		return json;
	}

	/**
	 * 校验用户短信验证码是否正确 更换手机号码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkNewSmsCode")
	@ResponseBody
	public JSONObject checkNewSmsCode(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");

		JSONObject json = new JSONObject();
		
		if (StringUtil.isNull(phoneNum)) {
			json.put("result", "fail");
			json.put("msg", "请输入手机号码");
			return json;
		}
		
		if (StringUtil.isNull(checkCode)) {
			json.put("result", "fail");
			json.put("msg", "验证码不能为空");
			return json;
		}

		String redisCode = redisService.getString(phoneNum);
		if (StringUtil.isNull(redisCode)) {
			json.put("result", "fail");
			json.put("msg", "验证码已过期");
			return json;
		}
		if (!checkCode.equals(redisCode)) {
			json.put("result", "fail");
			json.put("msg", "请输入正确的验证码");
			return json;
		}
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() <= 0){
			json.put("result","failure");
			json.put("msg","请先登录");
			return json;
		}
		
		json.put("result", "success");
		json.put("msg", "短信验证成功");
		return json;
	}

	@RequestMapping("savePayPassword")
	@ResponseBody
	public void savePayPassword(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("存储支付密码---------");

		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		CustomerVo customerVo = Constants.getCustomer(request);
		customerVo = customerManager.selectByPrimaryKey(customerVo
				.getCustomer_id());

		if (customerVo != null && customerVo.getCustomer_id() != null) {
			pd.put("customer_id", customerVo.getCustomer_id());

			logger.info(pd.getString("pay_pwd") + "--------pay_pwd11");
			int count = customerManager.updateCustoemrById(pd);
			customerVo.setPay_pwd(pd.getString("pay_pwd"));
			Constants.setCustomer(request, customerVo);
			if (count > 0) {
				json.put("msg", "success");
			} else {
				json.put("msg", "faild");
			}
		} else {
			logger.info("用户信息为空-------！");
		}
		this.outObjectToJson(json, response);
	}

	@RequestMapping("findMobile")
	@ResponseBody
	public void findMobile(HttpServletRequest request,
			HttpServletResponse response) {


		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		CustomerVo customerVo = Constants.getCustomer(request);
		String mobile = pd.getString("phoneNum");
		customerVo = customerManager.selectByMobile(mobile);

		if (customerVo != null) {
			json.put("msg", "该号码已经绑定了!");
			json.put("result","success");
		} else {
			json.put("result", "fail");
			json.put("msg", "该手机号还没有绑定");
		}
		this.outObjectToJson(json, response);
	}

	@RequestMapping("addPresentCard")
	@ResponseBody
	public void addPresentCard(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();

		// ----日志
		String giftCarNo = (String) pd.get("giftCardNo");
		logger.info("添加礼品卡方法:礼品卡ID ：" + giftCarNo);

		pd.put("customerId", null);
		pd.put("status", 0);
		JSONObject json = new JSONObject();
		
		GiftCardVo card = giftCardManager.queryOne(pd);
		if(card==null){
			json.put("result", "fils");
			json.put("msg", "没有此礼品卡请重新输入");
			this.outObjectToJson(json, response);
			return;
		}
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null){
			json.put("result", "fils");
			json.put("msg", "访问超时，请重新登录");
			this.outObjectToJson(json, response);
			return;
		}
		pd.put("customerId", customerVo.getCustomer_id());
		pd.put("money", card.getCard_money());
		pd.put("cardNo", card.getCard_no());
		pd.put("recordRemak", "添加礼品卡:" + card.getCard_no() + "使用");
		pd.put("recordType", 1);
		GiftCardRecordVo recordVo = new GiftCardRecordVo();
		recordVo.setCardNo(card.getCard_no());
		recordVo.setCustomerId(customerVo.getCustomer_id());
		BigDecimal bd = new BigDecimal(card.getCard_money());
		recordVo.setMoney(bd);
		recordVo.setRecordRemak("添加礼品卡:" + card.getCard_no() + "使用");
		byte by = 1;
		recordVo.setRecordType(by);

		int ret = giftCardRecordManager.insertGiftCard(recordVo);
		if (ret < 0) {
			json.put("result", "faild");
			json.put("msg", "礼品卡记录添加失败");
			this.outObjectToJson(json, response);
			return;
		}

		if (card != null && card.getStatus() == 0) {
			pd.put("cardId", card.getCard_id());
			pd.put("status", 1);
			int count = giftCardManager.updateGiftCardByGid(pd);
			if (count > 0) {
				json.put("result", "success");
				this.outObjectToJson(json, response);
			} else {
				json.put("result", "faild");
				json.put("msg", "礼品卡添加失败");
				this.outObjectToJson(json, response);
			}
		} else {
			json.put("result", "noCardNo");
			json.put("msg", "该卡号已使用");
			this.outObjectToJson(json, response);
		}
		
		this.outObjectToJson(json, response);
	}

}
