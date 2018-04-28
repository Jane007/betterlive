package com.kingleadsw.betterlive.controller.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.BanManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MobileMessageManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.enums.SmsTempleEnums;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.RSAUtils;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.BanVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MobileMessageVo;
import com.qiniu.util.Auth;
import sun.misc.BASE64Decoder;

@Controller("apppcommon")
@RequestMapping(value = "/app/common")
public class AppCommonController extends AbstractWebController {
	
	private Logger logger = Logger.getLogger(AppCommonController.class);
	
	@Autowired
    private RedisService redisService;
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private MobileMessageManager mobileMessageManager;
	
	@Autowired
	private BanManager banManager;
	
	
	private static final String REGISTER="register";   //register：注册
	/**private static final String FORGETPWD="forgetpwd"; 
	private static final String MODIFYPHONE="modifyphone"; */
	
    
    /**
     * 获取随机字符做标识
     * @return
     */
    @RequestMapping("/getRoundStr")
    @ResponseBody
    public Map<String,Object> getRoundStr(HttpServletRequest request,HttpServletResponse response){
    	PageData pd = this.getPageData();
    	if(StringUtil.isNull(pd.getString("strMobile"))){
    		return CallBackConstant.PARAMETER_ERROR.callbackError("非法访问");
    	}
    	Random rnd = new Random();
        int num = rnd.nextInt(89999) + 10000;
        String rdNum = System.currentTimeMillis()+""+num;
        redisService.setex("reqTag"+pd.getString("strMobile"), rdNum + "", 300);
        return CallBackConstant.SUCCESS.callback(rdNum);
    }
    
    /**
     * 新版获取短信验证码（防短信轰炸）
     * @param phone 获取验证码的手机号码
     * @param verfiType 验证码类型，register：注册；forgetpwd：忘记密码；modifyphone：修改手机
     * @return
     */
    @RequestMapping("/getIdentiycode")
    @ResponseBody
    public Map<String,Object> getIdentiycode(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String phone = pd.getString("mobile");
		String verfiType = pd.getString("verfiType");
		if(StringUtil.isNotNull(request.getHeader("user-agent"))){
			String checkHead = request.getHeader("user-agent").toLowerCase();
			logger.info("/app/common/getIdentiycode checkHead:" + checkHead);
			if(!checkHead.contains("iphone") && !checkHead.contains("android") && !checkHead.contains("okhttp/3.8.1")){
				return CallBackConstant.SUCCESS.callback();
			}
		}
        if(StringUtil.isNull(phone)){
            return CallBackConstant.PARAMETER_ERROR.callbackError("电话号码为空");
        }
        if(StringUtil.isNull(verfiType)){
            return CallBackConstant.PARAMETER_ERROR.callbackError("验证码类型为空");
        }
        
        verfiType = StringUtil.nullToString(verfiType,SmsTempleEnums.DEFAULT.getVerfiType());
        
        if(verfiType.equals(REGISTER)){  //验证码类型为注册
        	CustomerVo customer = customerManager.findCustomer(pd);
        	if(null != customer && null != customer.getPassword() && !"".equals(customer.getPassword())){
        		return CallBackConstant.REGISTERED.callbackError("用户已注册");
        	}
        }
        
        if(verfiType.equals(SmsTempleEnums.SETPAYPWD.getVerfiType())){ //验证码类型为设置支付密码
        	String token = pd.getString("token");
        	CustomerVo customer = customerManager.queryCustomerByToken(token);
        	if(null == customer){
        		return CallBackConstant.LOGIN_TIME_OUT.callbackError("访问超时");
        	}
        	if (!phone.equals(customer.getMobile())) {  //获取验证码的号码和登陆用户不匹配
        		return CallBackConstant.USER_MISMATCH.callbackError("请求验证的手机号和登录用户不匹配");
        	}
        }
        
        if(StringUtil.isNull(pd.getString("reqTag"))){
        	return CallBackConstant.DATA_NOT_FOUND.callbackError("没有权限访问");
        }
        
        String reqTag = redisService.getString("reqTag"+phone);
        if(StringUtil.isNull(reqTag)){
        	return CallBackConstant.FAILED.callbackError("访问出错");
        }
        
        if(!pd.getString("reqTag").equals(reqTag)){
        	logger.error("请求标识不匹配[缓存的标识："+reqTag+"传入的标识："+pd.getString("reqTag")+"]");
        	return CallBackConstant.DATA_NOT_FOUND.callbackError("访问出错");
        }
        
        Random rnd = new Random();
        int num = rnd.nextInt(89999) + 10000;
        
        PageData msgpd = new PageData();
		
		msgpd.clear();
		msgpd.put("requestMobile", phone);
		List<BanVo> banPhones = this.banManager.queryListPage(msgpd);
		if(banPhones != null && banPhones.size() > 0){
			return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
		}
		
		msgpd.clear();
		msgpd.put("requestMobile", phone);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 2);
		List<MobileMessageVo> msgPhones = mobileMessageManager.queryListPage(msgpd);
		if(msgPhones != null && msgPhones.size() >= 5){
			BanVo banVo = new BanVo();
			banVo.setRequestIp("");
			banVo.setRequestMobile(phone);
			banManager.insert(banVo);
			return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
		}
        
		msgpd.clear();
		msgpd.put("tag", 1);
		msgpd.put("msgType", 1);
		msgpd.put("queryFlag", 1);
		msgpd.put("requestMobile", phone);
		List<MobileMessageVo> msgs = mobileMessageManager.queryListPage(msgpd);
		if(msgs != null && msgs.size() > 1){
		    this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
			logger.info("["+phone+"]" + "一分钟内只能请求发送两次");
        	return CallBackConstant.FAILED.callbackError("一分钟内只能请求发送两次");
		}
		 
		msgpd.put("tag", 60);
		List<MobileMessageVo> msgms = mobileMessageManager.queryListPage(msgpd);
		if(msgms != null && msgms.size() > 2){
        	logger.info(phone + "1小时内请求发送次数不能超过3条");
        	this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
        	return CallBackConstant.FAILED.callbackError("一小时内只能请求发送三次");
		}
		
        SmsTempleEnums smsTempleEnums = SmsTempleEnums.DEFAULT.getTemp(verfiType);
        try{
        	boolean sendResult = SendMsgUtil.sendMessage(phone, smsTempleEnums.getMsgTemplate().replace("{}", num + "") + "(10分钟内有效)");
        	if(sendResult){
	            redisService.setex(phone + "_" + verfiType, num + "", 300);
	            logger.info("IP："+getIpAddr(request)+"手机号：" + phone + "的验证码是：" + num);
	            
	            this.insertMobileMessage(pd.getString("token"), num, phone, request, 1);
	            return CallBackConstant.SUCCESS.callback();
        	}
        	 this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
        	 return CallBackConstant.FAILED.callbackError("短信发送失败");
        }catch (Exception e){
            logger.error("手机号：" + phone + "的验证码[" + smsTempleEnums.getVerfiType() + "]发送失败：" + num, e);
            return CallBackConstant.FAILED.callbackError("短信发送请求异常");
        }
       
    }
    
    /**
     * App1.2.7新版获取短信验证码（防短信轰炸）
     * @param phone 获取验证码的手机号码
     * @param verfiType 验证码类型，register：注册；forgetpwd：忘记密码；modifyphone：修改手机
     * @return
     */
    @RequestMapping("/getVerificationCode")
    @ResponseBody
    public Map<String,Object> getVerificationCode(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String paramPhone = pd.getString("mobile");
		String verfiType = pd.getString("verfiType");
		if(StringUtil.isNotNull(request.getHeader("user-agent"))){
			String checkHead = request.getHeader("user-agent").toLowerCase();
			logger.info("/app/common/getVerificationCode checkHead:" + checkHead);
			if(!checkHead.contains("mobile_model") || !checkHead.contains("mobile_style") 
				|| !checkHead.contains("mobile_sys_version") || !checkHead.contains("app_version") 
				|| !checkHead.contains("req_source")){
				//防抓包，故返回成功
				return CallBackConstant.SUCCESS.callback();
			}
		}
        if(StringUtil.isNull(paramPhone)){
            return CallBackConstant.PARAMETER_ERROR.callbackError("电话号码为空");
        }
        if(StringUtil.isNull(verfiType)){
            return CallBackConstant.PARAMETER_ERROR.callbackError("验证码类型为空");
        }
        
    	
		try {
			HashMap<String, String> map = RSAUtils.getKeys();
	    	RSAPrivateKey privateKey = RSAUtils.loadPrivateKey(map.get("privateKey"));
	    	String phone = RSAUtils.decrypt(privateKey, RSAUtils.strToBase64(paramPhone));
	    	if(!phone.contains("mobile_")){
	        	//防抓包，故返回成功
	        	return CallBackConstant.SUCCESS.callback();
	        }
	        
	        phone = phone.substring(phone.indexOf("_") + 1, phone.length()).trim();
	        
	        
	        verfiType = StringUtil.nullToString(verfiType,SmsTempleEnums.DEFAULT.getVerfiType());
	        
	        if(verfiType.equals(REGISTER)){  //验证码类型为注册
	        	CustomerVo customer = customerManager.findCustomer(pd);
	        	if(null != customer && null != customer.getPassword() && !"".equals(customer.getPassword())){
	        		return CallBackConstant.REGISTERED.callbackError("用户已注册");
	        	}
	        }
	        
	        if(verfiType.equals(SmsTempleEnums.SETPAYPWD.getVerfiType())){ //验证码类型为设置支付密码
	        	String token = pd.getString("token");
	        	CustomerVo customer = customerManager.queryCustomerByToken(token);
	        	if(null == customer){
	        		return CallBackConstant.LOGIN_TIME_OUT.callbackError("访问超时");
	        	}
	        	if (!phone.equals(customer.getMobile())) {  //获取验证码的号码和登陆用户不匹配
	        		return CallBackConstant.USER_MISMATCH.callbackError("请求验证的手机号和登录用户不匹配");
	        	}
	        }
	        
	        Random rnd = new Random();
	        int num = rnd.nextInt(89999) + 10000;
	        
	        PageData msgpd = new PageData();
	        msgpd.put("requestIp", getIpAddr(request));
			List<BanVo> banIps = this.banManager.queryListPage(msgpd);
			if(banIps != null && banIps.size() > 0){
	        	return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
			}
			
			msgpd.clear();
			msgpd.put("requestMobile", phone);
			List<BanVo> banPhones = this.banManager.queryListPage(msgpd);
			if(banPhones != null && banPhones.size() > 0){
				return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
			}
			
			msgpd.clear();
			msgpd.put("requestIp", getIpAddr(request));
			msgpd.put("msgType", 1);
			msgpd.put("queryFlag", 2);
			List<MobileMessageVo> msgIps = mobileMessageManager.queryListPage(msgpd);
			if(msgIps != null && msgIps.size() >= 1000){ //一天1000次
				BanVo banVo = new BanVo();
				banVo.setRequestIp(getIpAddr(request));
				banVo.setRequestMobile("");
				banManager.insert(banVo);
				return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
			}
			
			msgpd.clear();
			msgpd.put("requestMobile", phone);
			msgpd.put("msgType", 1);
			msgpd.put("queryFlag", 2);
			List<MobileMessageVo> msgPhones = mobileMessageManager.queryListPage(msgpd);
			if(msgPhones != null && msgPhones.size() >= 5){
				BanVo banVo = new BanVo();
				banVo.setRequestIp("");
				banVo.setRequestMobile(phone);
				banManager.insert(banVo);
				return CallBackConstant.FAILED.callbackError("频繁请求，已被禁止访问");
			}
			
			msgpd.clear();
			msgpd.put("msgType", 1);
			msgpd.put("queryFlag", 1);
			msgpd.put("requestIp", getIpAddr(request));
			msgpd.put("tag", 1);
			List<MobileMessageVo> msgips = mobileMessageManager.queryListPage(msgpd);
			if(msgips != null && msgips.size() > 100){
			    this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
				logger.info("["+getIpAddr(request)+"]" + "一分钟内只能请求发送100次");
	        	return CallBackConstant.FAILED.callbackError("一分钟内只能请求发送100次");
			}
			
			msgpd.put("tag", 60);
			List<MobileMessageVo> msgipvos = mobileMessageManager.queryListPage(msgpd);
			if(msgipvos != null && msgipvos.size() > 200){
	        	logger.info("["+getIpAddr(request)+"]" + "一小时内只能请求发送三次");
	    	    this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
	        	return CallBackConstant.FAILED.callbackError("一小时内只能请求发送三次");
			}
			
			msgpd.clear();
			msgpd.put("tag", 1);
			msgpd.put("msgType", 1);
			msgpd.put("queryFlag", 1);
			msgpd.put("requestMobile", phone);
			List<MobileMessageVo> msgs = mobileMessageManager.queryListPage(msgpd);
			if(msgs != null && msgs.size() > 1){
			    this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
				logger.info("["+phone+"]" + "一分钟内只能请求发送两次");
	        	return CallBackConstant.FAILED.callbackError("一分钟内只能请求发送两次");
			}
			 
			msgpd.put("tag", 60);
			List<MobileMessageVo> msgms = mobileMessageManager.queryListPage(msgpd);
			if(msgms != null && msgms.size() > 2){
	        	logger.info(phone + "1小时内请求发送次数不能超过3条");
	        	this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
	        	return CallBackConstant.FAILED.callbackError("一小时内只能请求发送三次");
			}
			
	        SmsTempleEnums smsTempleEnums = SmsTempleEnums.DEFAULT.getTemp(verfiType);
        	boolean sendResult = SendMsgUtil.sendMessage(phone, smsTempleEnums.getMsgTemplate().replace("{}", num + "") + "(10分钟内有效)");
        	if(sendResult){
	            redisService.setex(phone + "_" + verfiType, num + "", 300);
	            logger.info("IP："+getIpAddr(request)+"手机号：" + phone + "的验证码是：" + num);
	            
	            this.insertMobileMessage(pd.getString("token"), num, phone, request, 1);
	            return CallBackConstant.SUCCESS.callback();
        	}
        	 this.insertMobileMessage(pd.getString("token"), num, phone, request, 0);
        	 return CallBackConstant.FAILED.callbackError("短信发送失败");
		} catch (Exception e) {
			logger.error("/app/common/getVerificationCode --error", e);
			 return CallBackConstant.FAILED.callbackError("短信发送请求异常");
		}
    }
    
    public void insertMobileMessage(String token, int num, String phoneNum, HttpServletRequest request, int flag){
    	int custId = 0;
    	if(StringUtil.isNotNull(token)){
    		CustomerVo customer = customerManager.queryCustomerByToken(token);
    		custId = customer.getCustomer_id();
    	}
    	MobileMessageVo mmvo = new MobileMessageVo();
    	mmvo.setCustomerId(custId);
    	mmvo.setMsgContent("["+phoneNum+"]绑定手机号，验证码["+num+"]");
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
     * 七牛上传token
     */
    @RequestMapping("/qntoken")
    @ResponseBody
    public Map<String,Object> getQNToken(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	logger.info("用户【" + token + "】获取七牛token开始");
    	if (StringUtil.isNull(token)) {
    		logger.error("获取token失败，用户token为空");
    		return CallBackConstant.LOGIN_TIME_OUT.callback("");
    	}
    	PageData pd = this.getPageData();
    	CustomerVo customer = customerManager.findCustomer(pd);
    	if(null == customer){
    		logger.error("获取token失败，用户登陆超时");
    		return CallBackConstant.LOGIN_TIME_OUT.callback();
    	}
    	
        Map<String,Object> uptoken = new HashMap<String, Object>();
        uptoken.put("uptoken", QiNiuConfig.getUpToken());
        logger.info("用户【" + token + "】获取七牛token结束，token：" + uptoken.get("uptoken"));
        return CallBackConstant.SUCCESS.callback(uptoken);
    }

    /**
     * 七牛配置信息
     */
    @RequestMapping("/qnconfig")
    @ResponseBody
    public Map<String,Object> getQNConfig(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	logger.info("用户【" + token + "】获取七牛token开始");
    	if (StringUtil.isNull(token)) {
    		logger.error("获取token失败，用户token为空");
    		return CallBackConstant.LOGIN_TIME_OUT.callback("");
    	}
    	PageData pd = this.getPageData();
    	CustomerVo customer = customerManager.findCustomer(pd);
    	if(null == customer){
    		logger.error("获取token失败，用户登陆超时");
    		return CallBackConstant.LOGIN_TIME_OUT.callback();
    	}
        Map<String,Object> uptoken = new HashMap<String, Object>();
        uptoken.put("QN_DOMAIN_LINK",WebConstant.QINIU_LINK);
        uptoken.put("QN_CLOUD",WebConstant.QINIU_CLOUD_KEY);
        uptoken.put("QN_ACCESS",WebConstant.QINIU_ACCESS_KEY);
        uptoken.put("QN_SECRET",WebConstant.QINIU_SECRET_KEY);
        logger.info("用户【" + token + "】获取七牛token结束，token：" + uptoken.get("uptoken"));
        return CallBackConstant.SUCCESS.callback(uptoken);
    }

    /**
     * 七牛配置
     */
    private static class QiNiuConfig {
        private static Auth auth;
        static{
            //设置好账号的ACCESS_KEY和SECRET_KEY
            //密钥配置
            auth = Auth.create(WebConstant.QINIU_ACCESS_KEY, WebConstant.QINIU_SECRET_KEY);
        }
        //简单上传，使用默认策略，只需要设置上传的空间名就可以了
        public static  String getUpToken(){
            return auth.uploadToken(WebConstant.QINIU_CLOUD_KEY);
        }
    }
    
    /**图片上传接口
     * base64字符串转化成图片
     */
    @RequestMapping("/simpleUpload")
    @ResponseBody
    public Map<String,Object> simpleUpload(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	logger.info("用户【" + token + "】获取七牛token开始");
    	if (StringUtil.isNull(token)) {
    		logger.error("获取token失败，用户token为空");
    		return CallBackConstant.LOGIN_TIME_OUT.callback("");
    	}
    	String imgStr = request.getParameter("imgStr");
    	String fileName = request.getParameter("fileName");
    	String picPostfix = request.getParameter("picPostfix");
    	String picName="";
    	if(StringUtil.isNotNull(picPostfix)){
    		picName = StringUtil.get32UUID() + picPostfix;
    	}
    	
    	boolean result = generateImage(imgStr, fileName, picName);
    	if (result) {
    		 return CallBackConstant.SUCCESS.callback(WebConstant.MAIN_SERVER + WebConstant.UPLOAT_ROOT_PATH_IMAGE + "/" + fileName + "/" + picName);
		} else {
			 return CallBackConstant.FAILED.callbackError("上传出错");
		}
    	
    }
    
    // base64字符串转化成图片
    public static boolean generateImage(String imgStr,String filename, String picName) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            String fileRealLocal = WebConstant.UPLOAT_ROOT_PATH+WebConstant.UPLOAT_ROOT_PATH_IMAGE+"/"+filename;
            File file = new File(fileRealLocal);
            if(!file.exists()){
            	file.mkdirs();
            }
            
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(fileRealLocal + "/" + picName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
		String mobile = "mobile_13600417912";
		String b = mobile.substring(mobile.indexOf("_") + 1, mobile.length()).trim();
		System.out.println(b);
	}
}
