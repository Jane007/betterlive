package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 运营推广活动 领取奖励记录
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class OperationRecordVo extends BaseVO implements Serializable{
	private static final long serialVersionUID = 4757531294197685743L;
	
	private int recordId;
	private int operationId;
	private int operationType;
	private int customerId;
	private Date createTime;
	private int objId; //业务主键ID（优惠券ID，单品红包ID）
	private int sysObjId;	//系统优惠活动主键ID 对应operation表的objId
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getOperationId() {
		return operationId;
	}
	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public int getSysObjId() {
		return sysObjId;
	}
	public void setSysObjId(int sysObjId) {
		this.sysObjId = sysObjId;
	}
	
}
