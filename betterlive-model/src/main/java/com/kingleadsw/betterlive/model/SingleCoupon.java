package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;
import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 单品红包实体
 * @author zhangjing
 *
 * @date 2017年5月9日
 */
public class SingleCoupon extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7765316661990239091L;
	/**
	 * 
	 */
	
	
	private Integer couponId;   		//单品优惠券id
	private String  couponName; 		//单品优惠券名称
	private BigDecimal fullMoney; 		//满多少钱
	
	private String couponContent;		//单品优惠券活动内容
	private BigDecimal couponMoney;		//单品优惠券金额
	private String startTime;			//单品优惠券开始时间
	private String endTime;             //单品优惠券束时间
	private String createTime;          //优惠券创建时间
	private Integer status;				//状态 1：正常 0 ：删除',
	private int homeFlag;				//是否推荐到首页 0否   1是
	private String couponBanner;		//红包banner图
	
	private Integer limitCopy;			//限制份数;
	private Integer jumpToPage;		//跳转页面 1：跳转商品详情，2跳转购买页面
	
	//*********** 非原始字段  *************
	private int couponSpecId;	//单品红包规格
	private int productId;
	private List<SingleCouponSpec> listSpec;	//优惠券规格集合
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public BigDecimal getFullMoney() {
		return fullMoney;
	}
	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}
	
	public String getCouponContent() {
		return couponContent;
	}
	public void setCouponContent(String couponContent) {
		this.couponContent = couponContent;
	}
	public BigDecimal getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<SingleCouponSpec> getListSpec() {
		return listSpec;
	}
	public void setListSpec(List<SingleCouponSpec> listSpec) {
		this.listSpec = listSpec;
	}
	public int getCouponSpecId() {
		return couponSpecId;
	}
	public void setCouponSpecId(int couponSpecId) {
		this.couponSpecId = couponSpecId;
	}
	public int getHomeFlag() {
		return homeFlag;
	}
	public void setHomeFlag(int homeFlag) {
		this.homeFlag = homeFlag;
	}
	public String getCouponBanner() {
		return couponBanner;
	}
	public void setCouponBanner(String couponBanner) {
		this.couponBanner = couponBanner;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public Integer getLimitCopy() {
		return limitCopy;
	}
	public void setLimitCopy(Integer limitCopy) {
		this.limitCopy = limitCopy;
	}
	public Integer getJumpToPage() {
		return jumpToPage;
	}
	public void setJumpToPage(Integer jumpToPage) {
		this.jumpToPage = jumpToPage;
	}
	
	

}
