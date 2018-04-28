package com.kingleadsw.betterlive.pay.wechat.util;

import com.kingleadsw.betterlive.pay.wechat.DictionarySortUtil;
import com.kingleadsw.betterlive.pay.wechat.config.PayConfigUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class WechatUtil {

	private static Logger logger = Logger.getLogger(WechatUtil.class);
	
	/**
	 * 验证消息是否是微信发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	public static boolean verify(Map<String, String> params) {

		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign");
		}
		boolean isSign = getSignVeryfy(params, sign);
		return isSign;
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
		// 过滤空值、sign参数
		Map<String, String> sParaNew = paraFilter(Params);
		// 获取待签名字符串
		String trade_type = sParaNew.get("trade_type");
		String paySecret = PayConfigUtil.API_NOTWEB_KEY;
		if (PayConfigUtil.WEIXIN_PREPAY_TRADE_TYPE_JSAPI.equals(trade_type)) {
			paySecret = PayConfigUtil.API_NOTWEB_KEY;
		}
		String preSignStr = DictionarySortUtil.createSign(sParaNew, paySecret);
		// 获得签名验证结果
		boolean isSign = false;
		if (sign.equals(preSignStr)) {
			isSign = true;
		}
		return isSign;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("myxml")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

}
