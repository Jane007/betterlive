package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class AgentProduct extends BasePO {
	private static final long serialVersionUID = 8626195400211880386L;
	
	private Long agentProId;       	//代理商品匹配ID
	private Integer productId;      //商品ID
	private Integer status;       	//代理状态：0：启用，1：禁用
	private String createTime;		//创建时间
	
	//--------------------------------------------------------------
	private String productName;
	private String productCode;
	private String productLogo;
	private Integer totalStockCopy;			 //总库存
	private int fakeSalesCopy;				 //虚拟销量
	private int productStatus;				 //商品状态
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
	public Integer getTotalStockCopy() {
		return totalStockCopy;
	}
	public void setTotalStockCopy(Integer totalStockCopy) {
		this.totalStockCopy = totalStockCopy;
	}
	public int getFakeSalesCopy() {
		return fakeSalesCopy;
	}
	public void setFakeSalesCopy(int fakeSalesCopy) {
		this.fakeSalesCopy = fakeSalesCopy;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public String getShareExplain() {
		return shareExplain;
	}
	public void setShareExplain(String shareExplain) {
		this.shareExplain = shareExplain;
	}
	
}
