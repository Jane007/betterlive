package com.kingleadsw.betterlive.util;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.init.StringUtil;

public class SendMsgUtil {
	
	private static final Logger logger = Logger.getLogger(SendMsgUtil.class);

	private static final String SENDURL = "http://sms.253.com/msg/send";
	
	private static final String ACCOUNT ="N8501531";
	
	private static final String PASSWORD ="bsSdvJY0jT84e2";
	
	/**
	 * 发送普通短信方法
	 * @param mobile 短信接收号码,多个号码用英文,隔开
	 * @param message
	 */
	public static boolean sendMessage(String mobile, String message) {
		String needstatus = "0";// 是否需要状态报告，0表示不需要，1表示需要
		String extno = null;// 扩展码(可选参数,可自定义)
		try {
			String returnString = HttpSender.batchSend(SENDURL, ACCOUNT, PASSWORD,
					mobile, message, needstatus, extno);
			logger.info("调用发送短信接口，returnString：" + returnString);
			if (StringUtil.isNotEmpty(returnString)) {
				String []response = returnString.split("\\r?\\n");
				if (response == null || response.length < 2) {
					return false;
				}
				String []result = response[0].split(",");
				if (result.length == 2 && "0".equals(result[1])) {
					return true;
				}
				logger.error("发送验证码失败，errorMsg：" + response[0]);
			}
		} catch (Exception e) {
			logger.error("发送短信失败：" + e);
		}
		return false;
	}

	/**
	 * 发送短信验证码
	 * @param mobile 手机号码
	 * @param code   验证码
	 */
	public static boolean sendCheckCode(String mobile, int code) {
		String msg = "您正在使用挥货平台进行短信认证，您的验证码是: " + code + "，请在 5分钟内完成验证。";// 短信内容
		String needstatus = "0";  // 是否需要状态报告，0表示不需要，1表示需要
		String extno = null;  // 扩展码(可选参数,可自定义)
		try {
			String returnString = HttpSender.batchSend(SENDURL, ACCOUNT, PASSWORD,
					mobile, msg, needstatus, extno);
			logger.info("调用发送短信接口，returnString：" + returnString);
			if (StringUtil.isNotEmpty(returnString)) {
				String []response = returnString.split("\\r?\\n");
				if (response == null || response.length < 2) {
					return false;
				}
				String []result = response[0].split(",");
				if (result.length == 2 && "0".equals(result[1])) {
					return true;
				}
				logger.error("发送验证码失败，errorMsg：" + response[0]);
			}
		} catch (Exception e) {
			logger.error("发送短信失败：" + e);
		}
		return false;
	}

}
