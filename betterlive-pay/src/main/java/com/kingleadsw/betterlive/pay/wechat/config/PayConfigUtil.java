package com.kingleadsw.betterlive.pay.wechat.config;

import java.util.ResourceBundle;

public class PayConfigUtil {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 资源配置信息.
	 */
	private final static ResourceBundle config = ResourceBundle.getBundle("web-constants");
	//微信分配的公众账号ID（企业号corpid即为此appId）
	public static String APP_NOTWEB_ID="wx31a34c0e2bd49051";
	//public static String APP_NOTWEB_ID="wx3fb5ecac15f48ab8";
	
	//微信支付分配的商户号
	public static String  MCH_NOTWEB_ID="1472446502";
	//public static String  MCH_NOTWEB_ID="1458039202";
	
	//秘钥
	public static String API_NOTWEB_KEY="7294812f16754e398e34ef2821962cc3";
	//public static String API_NOTWEB_KEY="d73a2d7f45ef264f637d1ba1a1db8b7a";

	//异步通知接口                                                 
	public static String NOTIFY_URL= config.getString("wx.app.pay.notify.path");


	/** 微信客户端调用微信支付的指定包名 */
	public static final String WEIXIN_PREPAY_PACKAGE = "Sign=WXPay";

	/** 微信交易类型 APP支付 */
	public static final String WEIXIN_PREPAY_TRADE_TYPE_APP = "APP";

	/** 微信交易类型 网页支付 */
	public static final String WEIXIN_PREPAY_TRADE_TYPE_JSAPI = "JSAPI";

	//统一下单接口
	public static String UFDODER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**微信支付通知成功状态**/
	public static final String WECHAT_RESPONSE_SUCCESS = "SUCCESS";
	/**微信支付通知失败状态**/
	public static final String WECHAT_RESPONSE_FAIL = "FAIL";

	public static final String WECHAT_RESPONSE_OK = "OK";
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}
