package com.kingleadsw.betterlive.util.wx.templatemessage.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingleadsw.betterlive.util.HttpClientTool;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgBodyDto;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgDataDto;


public class WxTemplateMessageUtil {

	private static Logger logger = Logger.getLogger(WxTemplateMessageUtil.class);

	// 发送模板消息接口地址
	public static final String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	/**
	 * 发送模板消息
	 * 
	 * @throws JSONException
	 * @throws IOException 
	 */
	public static String send(TemplateMsgBodyDto postData) throws JSONException, IOException {
		// 构造请求数据
		String url = SEND_MSG_URL + postData.getAccessToken();
		JSONObject json = new JSONObject();
		json.put("touser", postData.getTouser());
		json.put("template_id", postData.getTemplateId());
		json.put("url", postData.getUrl());
		json.put("topcolor", postData.getTopcolor());
		JSONObject jsonData = new JSONObject();
		for (TemplateMsgDataDto data : postData.getData()) {
			JSONObject keynote = new JSONObject();
			keynote.put("value", data.getValue());
			keynote.put("color", data.getColor());
			jsonData.put(data.getName(), keynote);
		}
		json.put("data", jsonData);

		String data = json.toString();
		//String result = HttpsUtils.post(url, data);
		String result =HttpClientTool.post(url, data, "utf-8");
		logger.info("post result:" + result);
		// 解析请求数据
		JSONObject resultJson = new JSONObject(result);
		//resultJson=resultJson.getJSONObject("result");
		if (resultJson.getInt("errcode")==0) {
			return resultJson.getInt("msgid")+"";
		}
		logger.error("send template message error:" + resultJson.getString("errmsg"));
		return null;
	}
	
	public static void test() {
		/**
		 *  模板ID7-FznPxXQx69l260B6hJhHhXR2FMIw0iWfuFn7xapLI
			开发者调用模板消息接口时需提供模板ID
			标题商品已发出通知
			行业IT科技 - 互联网|电子商务
			详细内容
			{{first.DATA}} 
			
			快递公司：{{delivername.DATA}}
			快递单号：{{ordername.DATA}}
			 {{remark.DATA}}  
			在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
			内容示例
			亲，宝贝已经启程了，好想快点来到你身边
			
			
			快递公司：顺丰快递
			快递单号：3291987391
			商品信息：韩版修身中长款风衣外套
			商品数量：共10件
			备注：如果疑问，请在微信服务号中输入“KF”，**将在第一时间为您服务！
		 */
		String touser="oF4kgxAhik-Z2QTPO0iegUcVFxLc";
		String templateId="7-FznPxXQx69l260B6hJhHhXR2FMIw0iWfuFn7xapLI";
		String url="http://hlife.shop/huihuo/weixin/index";
		String topcolor="#000";
		List<TemplateMsgDataDto> data=new ArrayList<TemplateMsgDataDto>();
		TemplateMsgDataDto first=new TemplateMsgDataDto("first","亲，宝贝已经启程了，好想快点来到你身边","#000");
		TemplateMsgDataDto delivername=new TemplateMsgDataDto("delivername","顺丰","#000");
		TemplateMsgDataDto ordername=new TemplateMsgDataDto("ordername","4234234543","#000");
		TemplateMsgDataDto remark=new TemplateMsgDataDto("remark","商品信息：韩版修身中长款风衣外套 商品数量：共10件  备注：如果疑问，请在微信服务号中输入“KF”，**将在第一时间为您服务！","#000");
		data.add(first);
		data.add(delivername);
		data.add(ordername);
		data.add(remark);
		String accessToken="rlAh9sIf8brWn6-AFQXwtvxKLZZBVD-iVtpdGx9C9qsjYdc7qJObYXjTLHkgxngg3zwWEgIw0lJ3qPbJYxOC9bivZa8e2nY5CU0Rgy-QP6G_p62rbtYyQEXTpkR1T40iWIZfAGAPBS";
		TemplateMsgBodyDto dto=new TemplateMsgBodyDto(touser,templateId,url,topcolor,data,accessToken);
		try {
			send(dto);
		} catch (JSONException e) {
			logger.error("WxTemplateMessageUtil/test --error", e);
		} catch (IOException e) {
			logger.error("WxTemplateMessageUtil/test --error", e);
		}
	}


}
