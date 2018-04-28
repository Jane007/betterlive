package com.kingleadsw.betterlive.pay.alipay.util;

/**
 * Ali支付请求业务参数(bigz_content内用)
 * @copright 
 * @author 
 * @date 
 * @version 
 */
public class AliPayBizParam {
	
	//对交易的具体描述-选填
	private String body;
	//商品标题
	private String subject;
	//商户网站唯一订单号
	private String out_trade_no;
	//订单允许的最晚付款时间,逾期关闭
	private String timeout_express;
	//订单总金额,单位元,保留小数点后两位
	private String total_amount;
	//销售产品码,固定值
	private String product_code;
	//请求主动唤醒
	private String app_pay;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTimeout_express() {
		return timeout_express;
	}
	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getApp_pay() {
		return app_pay;
	}
	public void setApp_pay(String app_pay) {
		this.app_pay = app_pay;
	}
	
}