/**
 * 
 */
package com.kingleadsw.betterlive.util.wx.templatemessage.dto;

import java.util.List;

public class TemplateMsgBodyDto {
	// 接收消息的用户
	private String touser;
	// 接收的模板id
	private String templateId;
	// 对应的url
	private String url;
	// 顶部使用的颜色
	private String topcolor;
	// 本次访问的令牌
	private String accessToken;
	// 传送的参数
	private List<TemplateMsgDataDto> data;

	/**
	 * 接收消息的用户
	 * 
	 * @return
	 */
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	/**
	 * 接收的模板id
	 * 
	 * @return
	 */
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * 对应的url
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 顶部使用的颜色
	 * 
	 * @return
	 */
	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	/**
	 * 传送的参数
	 * 
	 * @return
	 */
	public List<TemplateMsgDataDto> getData() {
		return data;
	}

	public void setData(List<TemplateMsgDataDto> data) {
		this.data = data;
	}

	/**
	 * 本次访问的令牌
	 * 
	 * @return
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public TemplateMsgBodyDto() {

	}

	public TemplateMsgBodyDto(String touser, String templateId, String url, String topcolor,
			List<TemplateMsgDataDto> data,String accessToken) {
		this.touser = touser;
		this.templateId = templateId;
		this.url = url;
		this.topcolor = topcolor;
		this.data = data;
		this.accessToken = accessToken;
	}

}
