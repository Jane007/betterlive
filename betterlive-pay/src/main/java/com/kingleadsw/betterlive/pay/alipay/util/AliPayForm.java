package com.kingleadsw.betterlive.pay.alipay.util;

/**
 * 支付宝APP支付支付提交数据
 * @author 
 *
 */
public class AliPayForm {
	
	//支付宝分配给开发者的应用ID。以2088开头的16位纯数字组成。
	private String app_id;
	//接口名称--alipay.trade.app.pay
	private String method;
	//仅支持JSON
	private String format;
	//字符编码格式.固定为UTF-8
	private String charset;
	//签名方式
	private String sign_type;
	//请求参数签名
	private String sign;
	//发送请求的时间
	private String timestamp;
	//调用的接口版本,固定为1.0
	private String version;
	//服务器异步通知页面路径
	//需http://格式的完整路径，不能加?id=123这类自定义参数
	private String notify_url;
	//业务请求参数
	private String biz_content;
	
	private String service;
	//合作身份者ID
	private String partner;
	//收款支付宝账号
	private String seller_id;
	//字符编码格式
	private String _input_charset;
	
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
}
