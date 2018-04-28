package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 用户领取单品红包
 * @author zhangjing
 *
 * @date 2017年5月10日
 */
public class UserSingleCoupon extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615984870538977405L;
	
	private Integer userSingleId;       //用户领取券id
	private Integer couponId;			//单品券id
	private Integer customerId;  		//用户id
	private String mobile;				//手机号
	private BigDecimal fullMoney;		//满多少钱
	private BigDecimal couponMoney;		//红包金额
	private Integer specId;				//规格id
	private Integer productId;			//产品id
	private String startTime;			//开始时间
	private String endTime;				//结束时间
	private Integer status;				//0：未使用，1：已使用，2：已过期
	private String createTime;		 	//领取时间
	private String usedTime;		 	//使用时间
	private String product_name;        //产品名称
	
	private String couponName;			//券名
	private String couponContent;      //券摘要
	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}
	public Integer getUserSingleId() {
		return userSingleId;
	}
	public void setUserSingleId(Integer userSingleId) {
		this.userSingleId = userSingleId;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getFullMoney() {
		return fullMoney;
	}
	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}
	public BigDecimal getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getSpecId() {
		return specId;
	}
	public void setSpecId(Integer specId) {
		this.specId = specId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCouponContent() {
		return couponContent;
	}
	public void setCouponContent(String couponContent) {
		this.couponContent = couponContent;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}	
}
