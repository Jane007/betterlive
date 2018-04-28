package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 促销活动规格
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
public class SingleCouponSpec extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373475258489061079L;
	
	
	private Integer couponSpecId; 				//单品优惠券规格id
	private Integer couponId;         			//单品优惠券id
	private Integer productId;         				//产品id
	private Integer specId;						    //规格
	private String linkUrl;							//链接地址
	//************非原始字段*******************
	
	private String productName;						//产品名称
	private String specName;						//规格名称
	private String couponName;						//优惠券名称
	public Integer getCouponSpecId() {
		return couponSpecId;
	}
	public void setCouponSpecId(Integer couponSpecId) {
		this.couponSpecId = couponSpecId;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSpecId() {
		return specId;
	}
	public void setSpecId(Integer specId) {
		this.specId = specId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
}
