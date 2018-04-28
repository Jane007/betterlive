package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 我的收藏
 *
 */
public class Collection extends BasePO {
	private static final long serialVersionUID = -8688012205027144296L;
	
	private int collectionId;
	private int customerId;
	private int collectionType; //收藏类型：1商品，2限时活动，3美食教程, 4美食推荐
	private int collectionTime;
	private int objId;
	
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

}
