package com.kingleadsw.betterlive.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 积分抵扣活动规格
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ProductRedeemSpecVo extends BaseVO{
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
	
	//--------------------------------------------------------------
	private String productName;
	private String productCode;
	private String productLogo;
	private Integer totalStockCopy;			 //总库存
	private int salesVolume;				 //总销量
	private Integer activityType;			//活动类型
	private Integer activityId;				//活动ID
	private BigDecimal activityPrice;        //商品活动价格
	private BigDecimal discountPrice;        //商品折扣价格
	private BigDecimal specPrice;          	 //商品基本价格
	private int productStatus;				 //商品状态
	private String productLabel;			//商品标签
	
	
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductLogo() {
		return productLogo;
	}
	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}
	public Integer getTotalStockCopy() {
		return totalStockCopy;
	}
	public void setTotalStockCopy(Integer totalStockCopy) {
		this.totalStockCopy = totalStockCopy;
	}
	public int getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public BigDecimal getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public BigDecimal getSpecPrice() {
		return specPrice;
	}
	public void setSpecPrice(BigDecimal specPrice) {
		this.specPrice = specPrice;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	
}
