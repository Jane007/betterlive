package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 优惠券管理实体类
 * 2017-03-14 by chen
 */
public class CouponManager  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer cm_id;          //优惠券管理Id
	private String coupon_name;     //优惠券名称
	private Integer coupon_type;    //优惠券类型，1：分享券
	private Integer get_source;     //获取来源，1：用户分享
	private String coupon_money;    //优惠券金额（固定金额）
	private String usemin_money;   //使用门槛   订单金额超过多少钱才可以使用
	private String usemax_date;   //优惠券有效期(天)
	private String create_by;       // 创建人
	private String create_time;     //创建时间
	private String coupon_content;		//优惠券摘要	
	private int home_flag;			//是否推荐到首页 0否   1是
	//------------------------------
	public Integer getCm_id() {
		return cm_id;
	}
	public void setCm_id(Integer cm_id) {
		this.cm_id = cm_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public Integer getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(Integer coupon_type) {
		this.coupon_type = coupon_type;
	}
	public Integer getGet_source() {
		return get_source;
	}
	public void setGet_source(Integer get_source) {
		this.get_source = get_source;
	}
	public String getCoupon_money() {
		return coupon_money;
	}
	public void setCoupon_money(String coupon_money) {
		this.coupon_money = coupon_money;
	}
	public String getUsemin_money() {
		return usemin_money;
	}
	public void setUsemin_money(String usemin_money) {
		this.usemin_money = usemin_money;
	}
	public String getUsemax_date() {
		return usemax_date;
	}
	public void setUsemax_date(String usemax_date) {
		this.usemax_date = usemax_date;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCoupon_content() {
		return coupon_content;
	}
	public void setCoupon_content(String coupon_content) {
		this.coupon_content = coupon_content;
	}
	public int getHome_flag() {
		return home_flag;
	}
	public void setHome_flag(int home_flag) {
		this.home_flag = home_flag;
	}	
	
}
