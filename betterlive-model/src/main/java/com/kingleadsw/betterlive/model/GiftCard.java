package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 优惠券管理实体类
 * 2017-03-14 by chen
 */
public class GiftCard  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer card_id;       //礼品卡ID
	private String card_no;        //礼品卡编号
	private String card_pwd;       //礼品卡密码
	private String binding_user;   //绑定用户id
	private String binding_time;   //绑定时间
	private Integer status;        //礼品卡状态，0：未使用；1：已使用；2：已过期
	private String card_money;     //礼品卡金额  
	private String card_use;       //已使用金额
	private String create_time;    //创建时间
	
	
	//************** 非原始表字段 **************
	private String customer_name;       //用户名称
	
	//------------------------------
	public Integer getCard_id() {
		return card_id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_pwd() {
		return card_pwd;
	}
	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}
	public String getBinding_user() {
		return binding_user;
	}
	public void setBinding_user(String binding_user) {
		this.binding_user = binding_user;
	}
	public String getBinding_time() {
		return binding_time;
	}
	public void setBinding_time(String binding_time) {
		this.binding_time = binding_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCard_money() {
		return card_money;
	}
	public void setCard_money(String card_money) {
		this.card_money = card_money;
	}
	public String getCard_use() {
		return card_use;
	}
	public void setCard_use(String card_use) {
		this.card_use = card_use;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
}
