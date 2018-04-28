package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 支付日志 实体类
 * 2017-03-21 by chen
 */
public class PayLog  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer paylog_id;     //支付日志ID 
	private String trans_id;       //支付流水号
	private Integer order_id;      //订单编号（支付成功的）
	private String order_code;     //支付的订单编号
	private Integer customer_id;   //用户id
	private Integer pay_type;      //支付类型，1：微信支付，2：支付宝支付  3:一卡通  4：礼品卡支付
	private String pay_money;      //支付的金额 
	private String pay_time;       //支付成功的时间
	private String create_time;    //创建时间
	private Integer coupon_id;     //使用优惠券ID 
	private String conpon_money;   //使用优惠券金额
	private String gift_card_no;  //使用礼品卡编号
	private String gitf_card_money;// 使用礼品卡金额
	
	private Integer use_single_coupon_id; //使用单品红包的id
	
	//---------------  不属于原始表字段  -----------------------------
	
	
	
	//--------------------------------------------
	
	
	public Integer getPaylog_id() {
		return paylog_id;
	}
	
	public Integer getUse_single_coupon_id() {
		return use_single_coupon_id;
	}
	
	public void setUse_single_coupon_id(Integer use_single_coupon_id) {
		this.use_single_coupon_id = use_single_coupon_id;
	}
	public void setPaylog_id(Integer paylog_id) {
		this.paylog_id = paylog_id;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getConpon_money() {
		return conpon_money;
	}
	public void setConpon_money(String conpon_money) {
		this.conpon_money = conpon_money;
	}
	public String getGift_card_no() {
		return gift_card_no;
	}
	public void setGift_card_no(String gift_card_no) {
		this.gift_card_no = gift_card_no;
	}
	public String getGitf_card_money() {
		return gitf_card_money;
	}
	public void setGitf_card_money(String gitf_card_money) {
		this.gitf_card_money = gitf_card_money;
	}
	
}
