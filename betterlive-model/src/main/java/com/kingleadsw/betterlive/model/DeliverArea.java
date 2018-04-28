package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品配送地区
 * 2017-04-17 by chen
 */
public class DeliverArea extends BasePO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer deliverAreaId;         //配送地区id主键
	private Integer productId;              //产品Id
	private Integer deliverType;            //地址类型；1：同城；2：本省；3：自定义
	private String areaCode;                //地区编码
	private Integer allChild;               //是否包含归属的所有下级地区；1：是；2：否
	
	
	//------------- 非原始表数据库 -------------
	 
	
	//------------------------------------
	public Integer getDeliverAreaId() {
		return deliverAreaId;
	}

	public void setDeliverAreaId(Integer deliverAreaId) {
		this.deliverAreaId = deliverAreaId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(Integer deliverType) {
		this.deliverType = deliverType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getAllChild() {
		return allChild;
	}

	public void setAllChild(Integer allChild) {
		this.allChild = allChild;
	}
	
	
}
