package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 我的分享VO
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ShareVo extends BaseVO implements Serializable{
	private static final long serialVersionUID = 8621419279785411625L;
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
