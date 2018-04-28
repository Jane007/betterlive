package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 锁定礼品卡和优惠券  实体类
 * 2017-03-21 by chen
 */
public class UseLock  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer lock_id;                //主键 ID 
	private String use_giftcard_no;        //使用的礼品卡编号
	private String use_card_money;          //使用礼品卡金额
	private Integer use_coupon_id;          //优惠券Id
	private Integer customer_id;            //客户ID
	private String order_code;              //订单编号
	private Integer lock_status;            //锁定状态：1 已锁定 2 已解除 3 已取消订单 4已过期
	private String create_time;             //创建时间  
	
	
	//---------------  不属于原始表字段  -----------------------------
	
	
	
	//--------------------------------------------
	public Integer getLock_id() {
		return lock_id;
	}
	public void setLock_id(Integer lock_id) {
		this.lock_id = lock_id;
	}
	public String getUse_giftcard_no() {
		return use_giftcard_no;
	}
	public void setUse_giftcard_no(String use_giftcard_no) {
		this.use_giftcard_no = use_giftcard_no;
	}
	public String getUse_card_money() {
		return use_card_money;
	}
	public void setUse_card_money(String use_card_money) {
		this.use_card_money = use_card_money;
	}
	public Integer getUse_coupon_id() {
		return use_coupon_id;
	}
	public void setUse_coupon_id(Integer use_coupon_id) {
		this.use_coupon_id = use_coupon_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getLock_status() {
		return lock_status;
	}
	public void setLock_status(Integer lock_status) {
		this.lock_status = lock_status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	
}
