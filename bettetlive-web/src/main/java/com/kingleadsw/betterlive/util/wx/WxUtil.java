package com.kingleadsw.betterlive.util.wx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.SysConstants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.IpAndMac;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.init.SpringContextHolder;
import com.kingleadsw.betterlive.pay.wechat.DictionarySortUtil;
import com.kingleadsw.betterlive.pay.wechat.config.PayConfigUtil;
import com.kingleadsw.betterlive.util.HttpClientTool;
import com.kingleadsw.betterlive.util.JsonUtil;
import com.kingleadsw.betterlive.util.wx.bean.PayResData;
import com.kingleadsw.betterlive.util.wx.service.WeixinService;
import com.kingleadsw.betterlive.vo.CustomerVo;


public class WxUtil {
    
    private final static Logger logger = Logger.getLogger(WxUtil.class);
    
    //请求失败
    public static final String NOT_CONNECTED = "-1";
    
    //code无效或为空
    public static final String INVALID_CODE = "40029";
    
    /**
     * 不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID
     */
    public static final String INVALID_OPENID = "40003";
    
 
    /**
     * 用户静默登录
     * @param request
     */
    public synchronized static String slientLogin(HttpServletRequest request) {
    	logger.info("静默获取用户信息开始");
    	
        CustomerVo customer = Constants.getCustomer(request);
        
        if (customer != null && StringUtil.isNotNull(customer.getOpenid())
				&& StringUtil.isNotNull(customer.getUnionid())) {
			return "0";
		}
        
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        logger.info("code:"+code);
        
        if (StringUtil.isNull(code)) {  //用户拒绝授权
            logger.info("用户没有用户code");
            return INVALID_CODE;
        }
  		
        String get_access_token_url = new StringBuilder(WebConstant.WX_ACCESS_TOKEN_URL).append("?appid=").append(WebConstant.WX_APPID).append("&secret=")
        		.append(WebConstant.WX_SECRET).append("&code=").append(code).append("&grant_type=authorization_code").toString();

        JSONObject jsonObject = HttpClientTool.httpsRequest(get_access_token_url, "GET", null);
        
        if (jsonObject == null) {
        	logger.error("获取网页授权access_token失败");
        	return NOT_CONNECTED;
        }
    	Object open_id = jsonObject.get("openid");
        if (open_id == null) {
        	logger.error("调用网页静默授权获取用户信息失败！");
        	logger.info("errcode:	" + jsonObject.get("errcode") 
        			+"；errmsg:  " + jsonObject.get("errmsg"));
        	return INVALID_CODE;
        }

    	CustomerManager customerManager= SpringContextHolder.getBean("customerManagerImpl");
        
    	String openid = String.valueOf(open_id);
    	logger.info("openid:   " + openid);
      	PageData pd = new PageData();
        pd.put("openid",openid);
    	//从数据库获取用户信息
    	CustomerVo checkCust = customerManager.queryOne(pd);
    	
    	String source = request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
    	if(StringUtils.isBlank(source)){
    		source = "weixin_browser";
    	}
    	String token = getAccessToken();
        JSONObject userInfo = getUserInfo(openid, token);
        logger.info("调用微信获取的用户信息：" + JsonUtil.toJsonString(userInfo));
        if(userInfo.get("errcode") != null){   //未获取到用户信息，一般来说是用户未关注该公众号
//        	logger.error("通过微信接口：获取用户基本信息失败，errcode:  " + userInfo.get("errcode") + ",errmsg:  " + jsonObject.get("errmsg"));
//        	if (customer == null) {  //如果用户信息为空，则保存匿名用户信息
//        		customer = new CustomerVo();
//            	customer.setOpenid(openid);
//            	customer.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
//            	customer.setNickname("");
//            	customer.setHead_url(WebConstant.MAIN_SERVER+"/resources/images/default_photo.png");
//            	customer.setSource(source);
//        		customerManager.insert(customer);
//        	}
        	if(customer == null){
        		if(checkCust != null){
        			Constants.setCustomer(request, checkCust);
        		}else{
        			customer = new CustomerVo();
            		customer = checkUserInfo(userInfo, customer);
                	customer.setOpenid(openid);
                	customer.setCustomer_id(0);
                	customer.setSource(source);
                	request.getSession(true).setAttribute("yk_customer", customer);
        		}
        	}else{
        		customer.setOpenid(openid);
        		customer.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
        		customerManager.updateByPrimaryKey(customer);
        		Constants.setCustomer(request, customer);
        	}
        } else {
        	// 获取微信用户成功，但是用户不在数据，如果用户未存在则创建该用户
            if (customer == null) {
            	if(checkCust != null){
            		checkCust = checkUserInfo(userInfo, checkCust);
        	    	if ("0".equals(userInfo.getString("subscribe"))) {  //微信说用户取消关注了
        	    		checkCust.setSubscribe(SysConstants.SUBSCRIBE_NO);
        	    	} else {
        	    		checkCust.setSubscribe(SysConstants.SUBSCRIBE_YES);
        	    	}
                 	customerManager.updateByPrimaryKey(checkCust);
                 	Constants.setCustomer(request, checkCust);
            	}else{//要把从微信获得的数据放到缓存属于新用户
            		customer = new CustomerVo();
            		customer = checkUserInfo(userInfo, customer);
                	customer.setOpenid(openid);
                	customer.setCustomer_id(0);
                	customer.setSource(source);
                	request.getSession(true).setAttribute("yk_customer", customer);
            	}
        	} else {
        		customer = checkUserInfo(userInfo, customer);
        		if ("0".equals(userInfo.getString("subscribe"))) {  //微信说用户取消关注了
        			customer.setSubscribe(SysConstants.SUBSCRIBE_NO);
    	    	} else {
    	    		customer.setSubscribe(SysConstants.SUBSCRIBE_YES);
    	    	}
        		if(StringUtil.isNull(customer.getOpenid())){
					customer.setOpenid(openid);
				}
			  	customerManager.updateByPrimaryKey(customer);
            	Constants.setCustomer(request, customer);
            }
        }
        return "0";
    }
    
    
	/**
	 * 网页授权登录
	 * @param request
	 */
	public synchronized static void authLogin(HttpServletRequest request){
		
		logger.info("获取用户信息开始");

		CustomerVo customer = Constants.getCustomer(request);

		if (customer != null && StringUtil.isNotNull(customer.getOpenid())
				&& StringUtil.isNotNull(customer.getUnionid())) {
			return;
		}
		
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		logger.info("code:" + code);

		if (null == code || "".equals(code)) {
			logger.info("用户没有用户code");
			return;
		}
		
		JSONObject json = getAuthAccessToken(code);
		String access_token = json.getString("access_token");
		String openid = json.getString("openid");
		String source = request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
		if(StringUtils.isBlank(source)){
    		source = "weixin_browser";
    	}
		
		if (StringUtil.isNull(openid)) {
			return;
		}
		
		CustomerManager customerManager = SpringContextHolder.getBean("customerManagerImpl");
		
		PageData pd = new PageData();
		pd.put("openid", openid);
		logger.info("openid:   " + openid);
		try {
			// 从数据库获取用户信息
			CustomerVo checkCust = customerManager.queryOne(pd);
			JSONObject userInfo = getAuthUserInfo(openid,access_token);
			logger.info("nickname:    " + userInfo.getString("nickname"));
			// 如果用户未存在则创建该用户
			if (customer == null) {	//用户未登录
				if(checkCust != null){ //openid有账户
					checkCust = checkUserInfo(userInfo, checkCust);
				}else if(userInfo.get("unionid") != null){	//openid没账户
            		pd.clear();
                	String unionid = userInfo.getString("unionid");
    				pd.put("unionid", unionid);
					checkCust = customerManager.queryOne(pd);
					if(checkCust != null){
						checkCust = checkUserInfo(userInfo, checkCust);
					}else{//游客
						customer = new CustomerVo();
	            		customer = checkUserInfo(userInfo, customer);
	                	customer.setOpenid(openid);
	                	customer.setCustomer_id(0);
	                	customer.setSource(source);
	                	request.getSession(true).setAttribute("yk_customer", customer);
					}
            	}
				
				if(checkCust != null){
					if(StringUtil.isNull(checkCust.getOpenid())){
						checkCust.setOpenid(openid);
					}
				 	customerManager.updateByPrimaryKey(checkCust);
	            	Constants.setCustomer(request, checkCust);
				}else{//游客
					customer = new CustomerVo();
            		customer = checkUserInfo(userInfo, customer);
                	customer.setOpenid(openid);
                	customer.setCustomer_id(0);
                	customer.setSource(source);
                	request.getSession(true).setAttribute("yk_customer", customer);
				}
			}else{	//已登录
				customer = checkUserInfo(userInfo, customer);
				if(StringUtil.isNull(customer.getOpenid())){
					customer.setOpenid(openid);
				}
			  	customerManager.updateByPrimaryKey(customer);
            	Constants.setCustomer(request, customer);
			}
			
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
    
    /**
     * 获取用户token
     * 
     * @return
     */
    public static String getAccessToken() {
    	WeixinService weixinService= SpringContextHolder.getBean("weixinService");
    	String token = weixinService.getAccessToken();
        return token;
    }

	public static JSONObject getAuthAccessToken(String code){
    	String url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token").append("?appid=").append(WebConstant.WX_APPID)
    			.append("&secret=").append(WebConstant.WX_SECRET).append("&code=").append(code).append("&grant_type=authorization_code").toString();
		
		return HttpClientTool.httpsRequest(url, "GET", null);
    }
	
    /**
     * 网页授权，获取用户信息
     */
    public static JSONObject getAuthUserInfo(String openid, String accessToken) {
    	logger.info("获取网页授权用户信息开始");
    	JSONObject jsonObject = new JSONObject();
        String url = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?access_token=").append(accessToken).append("&openid=").append(openid).append("&lang=zh_CN").toString();
        logger.info("url:" + url);

        //获取用户json信息
        jsonObject = HttpClientTool.httpsRequest(url, "GET", null);
        logger.info("获取网页授权用户信息结束");

        return jsonObject;
    }
	
    /**
     * 通过微信接口：获取用户基本信息(UnionID机制)
     * @param openid
     * @param accessToken
     * @return
     */
    public static JSONObject getUserInfo(String openid, String accessToken){
        String url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/user/info?access_token=").append(accessToken).append("&openid=").append(openid).toString();
        return HttpClientTool.httpsRequest(url, "GET", null);
    }
    
    public static String getAuthUrlInvoke(String url){
    	StringBuilder sb = new StringBuilder();
    	sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(WebConstant.WX_APPID).append("&redirect_uri=");
    	try {
			sb.append(URLEncoder.encode(url,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString(), e);
		}
    	sb.append("&response_type=code&scope=snsapi_base&state=basic#wechat_redirect");
    	return sb.toString();
    }
    
    /**
     * 网页授权地址
     * @param url
     * @return
     */
    public static String getAuthUrl(String url){
    	StringBuilder sb = new StringBuilder();
    	sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(WebConstant.WX_APPID).append("&redirect_uri=");
    	try {
			sb.append(URLEncoder.encode(url,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString(), e);
		}
    	sb.append("&response_type=code&scope=snsapi_userinfo&state=userinfo#wechat_redirect");
    	return sb.toString();
    }
    
    public static Map<String, String> configPayParam(HttpServletRequest request,float amount,
            String sn,String openId, String goods_desc,String pay_notify) throws Exception {
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        Map<String, String> params = new HashMap<String, String>();
        String nonce_str = StringUtil.get32UUID();
        
        params.put("appid", WebConstant.WX_APPID);
        params.put("body", goods_desc);
        params.put("mch_id", WebConstant.WX_MCH_ID);
        params.put("nonce_str", nonce_str);
        
       /* String notify_url = WebConstants.WX_PAY_NOTIFY;
        params.put("notify_url", notify_url);*/
        params.put("notify_url", pay_notify);
        
        params.put("openid", openId);
        params.put("out_trade_no", sn);
        params.put("spbill_create_ip", IpAndMac.getIpAddr(request));
        
        BigDecimal bigMoney = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        bigMoney = bigMoney.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        params.put("total_fee", bigMoney.intValue()+"");
        //params.put("total_fee", String.valueOf(1));

        params.put("trade_type", "JSAPI");

        String sign = WxSign.getSign(params);

        params.put("sign", sign);

        logger.info("--------------------微信支付页面请求开始---------------------------");

        String xml = configXml(params);

        logger.info("签名请求报文：" + xml);

        String result = HttpClientTool.post(url, xml, "utf-8");
        PayResData payReseData = (PayResData) XmlUtil.getObjectFromXML(result, PayResData.class);

        Map<String, String> payParam = new HashMap<String, String>();
        payParam.put("appId", payReseData.getAppid());
        payParam.put("timeStamp", String.valueOf(System.currentTimeMillis()));
        payParam.put("nonceStr", nonce_str);
        payParam.put("package", "prepay_id=" + payReseData.getPrepay_id());
        payParam.put("signType", "MD5");
        String paySign = WxSign.getSign(payParam);
        payParam.put("paySign", paySign);

        return payParam;

    }
    
	/**
	 * 微信app支付工具方法
	 * amount 支付的总费用
	 * sn 微信交易号 (唯一性) 对于我们这里的订单编号
	 * goods_desc 商品描述
	 * pay_notify 微信支付异步回调地址
	 * paySecret 支付密钥
	 * Map<String,String>
	 */
	public static  Map<String, String> wechatAppPay(HttpServletRequest request,float amount,
            String sn, String goods_desc,String pay_notify,String paySecret){
		try {
			return configPayParam(request,amount,sn,null, goods_desc,pay_notify,"APP",paySecret);
		} catch (Exception e) {
			logger.error("WxUtil/wechatAppPay --error", e);
			return null;
		}
	}
    
	/**
	 * 微信发起支付获取prepayid
	  * Map<String,String>
	 */
    public static Map<String, String> configPayParam(HttpServletRequest request,float amount,
            String sn,String openId, String goods_desc,String pay_notify,String payType,String paySecret) throws Exception {
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        Map<String, String> params = new HashMap<String, String>();
        String nonce_str = StringUtil.get32UUID();
        
/*        params.put("appid", PayConfigUtil.APP_NOTWEB_ID);
        params.put("body", goods_desc);
        params.put("mch_id", PayConfigUtil.MCH_NOTWEB_ID);
        params.put("nonce_str", nonce_str);
        params.put("notify_url", pay_notify);
        params.put("out_trade_no", sn);
        //params.put("spbill_create_ip", IpAndMac.getIpAddr(request));
        params.put("spbill_create_ip", "139.196.30.90");
        params.put("total_fee", String.valueOf((int) (amount* 100)));
        params.put("trade_type",payType);*/
        
        
        params.put("appid", PayConfigUtil.APP_NOTWEB_ID);
        params.put("body", goods_desc);
        params.put("mch_id", PayConfigUtil.MCH_NOTWEB_ID);
        params.put("nonce_str", nonce_str);
        
        params.put("notify_url", pay_notify);
        
        //params.put("openid", openId);
        params.put("out_trade_no", sn);
        params.put("spbill_create_ip", "139.196.30.90");
       // params.put("spbill_create_ip", IpAndMac.getIpAddr(request));
        params.put("total_fee", String.valueOf((int) (amount* 100)));
        params.put("trade_type",payType);
        
        
        
        if("JSAPI".equals(payType)){
        	params.put("openid", openId);
        }
       // String paySecret="380e0febf3f949c3bbdb0d729c7be529";
        String sign = DictionarySortUtil.createSign(params, paySecret);
       // String sign = WxSign.getSign(params);
        params.put("sign", sign);

        logger.info("--------------------微信支付页面请求开始---------------------------");

        String xml = configXml(params);

        logger.info("签名请求报文：" + xml);

        String result = HttpClientTool.post(url, xml, "utf-8");
        PayResData payReseData = (PayResData) XmlUtil.getObjectFromXML(result, PayResData.class);

        Map<String, String> payParam = new HashMap<String, String>();

        
        if("JSAPI".equals(payType)){
            payParam.put("appId", WebConstant.WX_APPID);
            payParam.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000+""));
            payParam.put("nonceStr", nonce_str);
        	payParam.put("package", "prepay_id=" + payReseData.getPrepay_id());
        	payParam.put("signType", "MD5");
        	
        }else{
        	payParam.put("appid", PayConfigUtil.APP_NOTWEB_ID);
        	payParam.put("partnerid", PayConfigUtil.MCH_NOTWEB_ID);
        	payParam.put("noncestr", nonce_str);
        	payParam.put("prepayid",  payReseData.getPrepay_id());
        	payParam.put("package", "Sign=WXPay");
        	payParam.put("timestamp", String.valueOf(System.currentTimeMillis()/1000+""));
        }
       // sign = WxSign.getSign(payParam);
        sign=DictionarySortUtil.createSign(payParam, paySecret);
        payParam.put("paySign", sign);

        return payParam;

    }
    
    
    public static CustomerVo checkUserInfo(JSONObject userInfo, CustomerVo myCust){
    	if (userInfo.get("unionid") != null) {
    		myCust.setUnionid(userInfo.getString("unionid"));
    	} 
    	if (userInfo.get("nickname") != null && (StringUtil.isNull(myCust.getNickname())
    			|| myCust.getNickname().contains("小挥挥"))) {
    		myCust.setNickname(userInfo.getString("nickname"));
    	}
    	if (userInfo.get("headimgurl") != null && (StringUtil.isNull(myCust.getHead_url())
    			|| myCust.getHead_url().contains("default_photo.png"))) {
    		myCust.setHead_url(userInfo.getString("headimgurl"));
    	}
    	if (userInfo.get("sex") != null && myCust.getSex() == null) {
    		myCust.setSex(userInfo.getInt("sex"));
    	} 
    	if(StringUtil.isNull(myCust.getSource())){
    		myCust.setSource("weixin_auto");
    	}
    	return myCust;
    }
    
    
    
    
    public static String configXml(Map<String, String> params) throws IOException {
        StringBuffer buffer=new StringBuffer();
        
        buffer.append("<xml>");
        for(Entry<String,String> entry:params.entrySet()){
            buffer.append("<").append(entry.getKey()).append(">");
            buffer.append(entry.getValue());
            buffer.append("</").append(entry.getKey()).append(">");
        }
        buffer.append("</xml>");
        
        return buffer.toString();
     } 
    
}
