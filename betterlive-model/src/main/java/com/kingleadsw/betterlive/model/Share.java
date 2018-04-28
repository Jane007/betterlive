package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 我的分享
 *
 */
public class Share extends BasePO {
	
	
	private static final long serialVersionUID = -6198287938699196427L;
	
	private int shareId;
	private int customerId;
	private int shareType; //分享类型 1商品，2限时活动，3美食教程，4发现文章
	private Date shareTime;
	private int objId;
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getShareType() {
		return shareType;
	}
	public void setShareType(int shareType) {
		this.shareType = shareType;
	}
	public Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
}
