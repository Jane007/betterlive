package com.kingleadsw.betterlive.pay.alipay.util;

/**
 * Ali订单结果查询, 业务参数(biz_content内用)
 * @copright 
 * @author 
 * @date 
 * @version
 */
public class AliPayQueryBizParam {
	
	//商户订单号,和支付宝交易号(下)不能同时为空,(优先使用)
	private String out_trade_no;
	//支付宝交易号,和商户订单号不能同时为空
	private String trade_no;
	
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
}
