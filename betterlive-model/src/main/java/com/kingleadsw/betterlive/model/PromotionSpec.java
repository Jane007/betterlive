package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 促销活动规格
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
public class PromotionSpec extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373475258489061079L;
	
	
	private Integer promotionSpecId; 				//活动规格id
	private Integer promotionId;         			//活动id
	private Integer productId;         				//产品id
	private Integer specId;						    //规格
	
	//************非原始字段*******************
	
	private String productName;						//产品名称
	private String specName;						//规格名称
	public Integer getPromotionSpecId() {
		return promotionSpecId;
	}
	public void setPromotionSpecId(Integer promotionSpecId) {
		this.promotionSpecId = promotionSpecId;
	}
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
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
	
	
	
	
}
