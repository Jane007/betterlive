package com.kingleadsw.betterlive.bean;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.kingleadsw.betterlive.util.wx.XmlParser;
import com.kingleadsw.betterlive.util.wx.bean.Message;
import com.kingleadsw.betterlive.util.wx.bean.ScanMessage;
import com.kingleadsw.betterlive.util.wx.bean.SubscribeMessage;
import com.kingleadsw.betterlive.util.wx.bean.TextMessage;


public class MessageHandler {
	private static final Logger logger = Logger.getLogger(MessageHandler.class);

	/**
	 * 订阅事件
	 */
	public static final String EVENT_SUBSCRIBE = "subscribe";

	/**
	 * 用户取消关注事件
	 */
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 点击菜单跳转链接时的事件推送事件
	 */
	public static final String EVENT_VIEW = "VIEW";

	/**
	 * 扫码事件
	 */
	public static final String EVENT_SCAN = "SCAN";

	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void process(HttpServletRequest request) {

		String xmlData = getXmlData(request);

		logger.info("用户事件消息:" + xmlData);

		try {
			Element root = XmlParser.getRootElement(xmlData);
			Element element = root.element("Event");
			if (element != null) {
				String event = element.getTextTrim();
				if (EVENT_SUBSCRIBE.equals(event)
						|| EVENT_UNSUBSCRIBE.equals(event)) { // 用户关注事件
					Message message = (SubscribeMessage) XmlParser.parser(
							xmlData, SubscribeMessage.class);
					this.message = message;
				}
				if (EVENT_SCAN.equals(event)) {
					Message message = (ScanMessage) XmlParser.parser(xmlData,
							ScanMessage.class);
					this.message = message;
				}
			}
			Element msgType = root.element("MsgType");
			if (msgType != null) {
				if ("text".equals(msgType.getTextTrim())) {
					Message message = (Message) XmlParser.parser(xmlData,
							TextMessage.class);
					this.message = message;
				}
			}
		} catch (DocumentException e) {
			logger.error(e, e);
		}

	}

	private String getXmlData(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer("");
		InputStream input = null;
		BufferedReader reader = null;
		try {
			input = request.getInputStream();

			reader = new BufferedReader(new InputStreamReader(
					input));

			String str = "";

			// 解析字符串
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}

		return buffer.toString();
	}

}
