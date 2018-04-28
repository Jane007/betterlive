package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
/**
 * 活动规格关联
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class PromotionSpecVo extends BaseVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3077374262218288244L;
	private Integer promotionSpecId; 				//活动规格id
	private Integer promotionId;         			//活动id
	private Integer productId;         				//产品id
	private Integer specId;		

	
	
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((promotionSpecId == null) ? 0 : promotionSpecId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromotionSpecVo other = (PromotionSpecVo) obj;
		if (promotionSpecId == null) {
			if (other.promotionSpecId != null)
				return false;
		} else if (!promotionSpecId.equals(other.promotionSpecId))
			return false;
		return true;
	}
	
	
	
	
	
}
