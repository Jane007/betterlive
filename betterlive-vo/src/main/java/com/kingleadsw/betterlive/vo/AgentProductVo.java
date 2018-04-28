package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class AgentProductVo extends BaseVO implements Serializable {
	private static final long serialVersionUID = 8626195400211880386L;
	
	private Long agentProId;       	//代理商品匹配ID
	private Integer productId;      //商品ID
	private Integer status;       	//代理状态：0：启用，1：禁用
	private String createTime;		//创建时间
	
	//--------------------------------------------------------------
	private String productName;
	private String productCode;
	private String productLogo;
	private Integer specId;             	//规格ID
	private Integer totalStockCopy;			 //总库存
	private int salesVolume;				 //总销量
	private Integer activityType;			//活动类型
	private Integer activityId;				//活动ID
	private BigDecimal activityPrice;        //商品活动价格
	private BigDecimal discountPrice;        //商品折扣价格
	private BigDecimal specPrice;          	 //商品基本价格
	private int productStatus;				 //商品状态
	private String productLabel;			//商品标签
	private String shareExplain;			//分享简介
	
	public Long getAgentProId() {
		return agentProId;
	}
	public void setAgentProId(Long agentProId) {
		this.agentProId = agentProId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	public Integer getSpecId() {
		return specId;
	}
	public void setSpecId(Integer specId) {
		this.specId = specId;
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
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	public String getShareExplain() {
		return shareExplain;
	}
	public void setShareExplain(String shareExplain) {
		this.shareExplain = shareExplain;
	}
	
}
