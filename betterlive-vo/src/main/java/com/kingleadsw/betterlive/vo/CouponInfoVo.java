package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 用户 优惠券实体类
 * 2017-03-14 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CouponInfoVo  extends BaseVO implements Serializable,Comparable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer coupon_id;         //用户优惠券Id
	private Integer cm_id;             //优惠券管理ID
	private String mobile;             //领取优惠券的手机号码
	private Integer customer_id;       //用户id
	private Integer coupon_money;       //优惠券的金额 
	private String starttime;          //优惠券的开始时间
	private String endtime;            //优惠券的结束时间
	private Integer start_money;        //优惠券门槛（   订单满此金额值，才可使用此优惠券）
	private Integer coupon_from;       //优惠券来源，1代表新人所得，2代表分享所得
	private String used_time;          // 优惠券使用时间
	private Integer status;             //优惠券的状态，0：未使用，1：已使用，2：已过期
	private String order_code;         //使用时订单编号
	private Integer from_user_id;      //来自对应用户分享的id 
	private Integer from_order_id;     //来自哪个订单分享所得
	private String create_time;        //创建时间 （优惠券领取时间）
	
	
	
	//*********** 非原始字段  *************
	private String coupon_name;        //优惠券名称
	private String nickname;           //用户名称
	
	private int coupon_type;		//优惠券类型
	private String coupon_content;	//优惠券摘要		
	//------------------------------
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Integer getCm_id() {
		return cm_id;
	}
	public void setCm_id(Integer cm_id) {
		this.cm_id = cm_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getCoupon_money() {
		return coupon_money;
	}
	public void setCoupon_money(Integer coupon_money) {
		this.coupon_money = coupon_money;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getStart_money() {
		return start_money;
	}
	public void setStart_money(Integer start_money) {
		this.start_money = start_money;
	}
	public Integer getCoupon_from() {
		return coupon_from;
	}
	public void setCoupon_from(Integer coupon_from) {
		this.coupon_from = coupon_from;
	}
	public String getUsed_time() {
		return used_time;
	}
	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getFrom_user_id() {
		return from_user_id;
	}
	public void setFrom_user_id(Integer from_user_id) {
		this.from_user_id = from_user_id;
	}
	public Integer getFrom_order_id() {
		return from_order_id;
	}
	public void setFrom_order_id(Integer from_order_id) {
		this.from_order_id = from_order_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
		public int getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(int coupon_type) {
		this.coupon_type = coupon_type;
	}
	public String getCoupon_content() {
		return coupon_content;
	}
	public void setCoupon_content(String coupon_content) {
		this.coupon_content = coupon_content;
	}
	@Override
	public int compareTo(Object arg0) {
		CouponInfoVo dept=(CouponInfoVo) arg0;  
        if(this.coupon_money>dept.coupon_money)return -1;  
        else if(this.coupon_money<dept.coupon_money)return 1;  
        else return 0; 
	}
			
	
   
}