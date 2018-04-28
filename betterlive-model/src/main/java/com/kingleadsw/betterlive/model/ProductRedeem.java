package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 积分抵扣活动
 */
public class ProductRedeem extends BasePO{
	
	private static final long serialVersionUID = 1L;
	
	private Long redeemId;            		//活动Id
	private Integer productId;             	//产品ID
	private Integer redeemType;				//抵扣类型: 0优惠购（自定义积分抵扣金额），1固定积分抵扣金额（如100积分抵扣1元）
	private Long creator;					//创建人（管理员ID）
	private String createTime;              //创建时间
	private Long modifier;					//最后修改人（管理员ID）
	private String modifyTime;              //最后修改时间
	private String startTime;               //活动开始时间
	private String endTime;                 //活动结束时间
	private Integer status;					//状态 0启用，1失效
	
	//---------------------------------------------------
	private String productName;
	private String productCode;
	private String productLogo;
	private Integer totalStockCopy;			 //总库存
	private Integer fakeSalesCopy;			 //虚拟销量
	
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
	public Integer getRedeemType() {
		return redeemType;
	}
	public void setRedeemType(Integer redeemType) {
		this.redeemType = redeemType;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
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
	public Integer getFakeSalesCopy() {
		return fakeSalesCopy;
	}
	public void setFakeSalesCopy(Integer fakeSalesCopy) {
		this.fakeSalesCopy = fakeSalesCopy;
	}
}
