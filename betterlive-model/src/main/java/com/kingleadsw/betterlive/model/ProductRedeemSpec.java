package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 积分抵扣活动规格
 */
public class ProductRedeemSpec extends BasePO{
	
	private static final long serialVersionUID = 1L;
	
	private Long redeemSpecId;            	//活动规格Id
	private Long redeemId;            		//活动Id
	private Integer productId;             	//产品ID
	private Integer specId;             	//规格ID
	private Integer redeemType;				//抵扣类型: 0优惠购（自定义积分抵扣金额），1固定积分抵扣金额（如100积分抵扣1元）
	private BigDecimal needIntegral;		//优惠购需要积分
	private BigDecimal deductibleAmount;	//优惠购抵扣商品金额
	private String startTime;               //活动开始时间
	private String endTime;                 //活动结束时间
	private Integer status;					//状态 0启用，1失效
	
	//---------------------------------------------------
	private BigDecimal specPrice;
	private BigDecimal discountPrice;
	
	public Long getRedeemSpecId() {
		return redeemSpecId;
	}
	public void setRedeemSpecId(Long redeemSpecId) {
		this.redeemSpecId = redeemSpecId;
	}
	public Long getRedeemId() {
		return redeemId;
	}
	public void setRedeemId(Long redeemId) {
		this.redeemId = redeemId;
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
	public Integer getRedeemType() {
		return redeemType;
	}
	public void setRedeemType(Integer redeemType) {
		this.redeemType = redeemType;
	}
	public BigDecimal getNeedIntegral() {
		return needIntegral;
	}
	public void setNeedIntegral(BigDecimal needIntegral) {
		this.needIntegral = needIntegral;
	}
	public BigDecimal getDeductibleAmount() {
		return deductibleAmount;
	}
	public void setDeductibleAmount(BigDecimal deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getSpecPrice() {
		return specPrice;
	}
	public void setSpecPrice(BigDecimal specPrice) {
		this.specPrice = specPrice;
	}
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}
