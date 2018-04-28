package com.kingleadsw.betterlive.pay.alipay.util;

/**
 * 支付宝APP支付支付提交数据
 * @author 
 *
 */
public class AliPayFormForWap {
	
	private String service;
	//合作身份者ID
	private String partner;
	//收款支付宝账号
	private String seller_id;
	//字符编码格式
	private String _input_charset;
	//支付类型
	private String payment_type;
	//服务器异步通知页面路径
	//需http://格式的完整路径，不能加?id=123这类自定义参数
	private String notify_url;
	//页面跳转同步通知页面路径
	//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	private String return_url;
	//商户订单号
	//商户网站订单系统中唯一订单号，必填
	private	String out_trade_no;
	//订单名称
	private	String subject;
	//付款金额
	private String total_fee;
	//商品展示地址
	private String show_url;
	//订单描述
	private	String body;
	//超时时间
	private	String it_b_pay;
	//钱包token
	private	String extern_token;
	//主动唤醒APP支付宝客户端
	private	String app_pay; 
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIt_b_pay() {
		return it_b_pay;
	}
	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}
	public String getExtern_token() {
		return extern_token;
	}
	public void setExtern_token(String extern_token) {
		this.extern_token = extern_token;
	}
	public String getApp_pay() {
		return app_pay;
	}
	public void setApp_pay(String app_pay) {
		this.app_pay = app_pay;
	}
	
}
