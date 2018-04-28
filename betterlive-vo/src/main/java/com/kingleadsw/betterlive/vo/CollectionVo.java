package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 我的收藏VO
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CollectionVo extends BaseVO implements Serializable{
	private static final long serialVersionUID = 6217335553349294116L;

	private int collectionId;
	private int customerId;
	private int collectionType; //收藏类型：1商品，2限时活动，3美食教程, 4美食推荐
	private int collectionTime;
	private int objId;
	
	private ProductVo productVo;
	private SpecialVo specialVo;
	private SpecialArticleVo specialArticleVo;

	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}
	public int getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(int collectionTime) {
		this.collectionTime = collectionTime;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public ProductVo getProductVo() {
		return productVo;
	}
	public void setProductVo(ProductVo productVo) {
		this.productVo = productVo;
	}
	public SpecialVo getSpecialVo() {
		return specialVo;
	}
	public void setSpecialVo(SpecialVo specialVo) {
		this.specialVo = specialVo;
	}
	public SpecialArticleVo getSpecialArticleVo() {
		return specialArticleVo;
	}
	public void setSpecialArticleVo(SpecialArticleVo specialArticleVo) {
		this.specialArticleVo = specialArticleVo;
	}
}
