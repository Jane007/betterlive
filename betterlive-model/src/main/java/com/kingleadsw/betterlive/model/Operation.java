package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 运营推广活动
 *
 */
public class Operation extends BasePO{
	
	private static final long serialVersionUID = 8270026093591196212L;
	
	private int operationId;
	private int operationType; //1.下载APP送优惠券，2. 下载APP送红包
	private String operationBanner;
	private Date createTime;
	private Date updateTime;
	private int objId; //业务主键ID（系统优惠券ID，系统单品红包规格ID）
	private int status;	//状态：0删除 1进行中 2下架
	private Date startTime;
	private Date endTime;
	private String linkPro;	//爆款推荐商品的ID如：1,2,3
	
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
	public String getOperationBanner() {
		return operationBanner;
	}
	public void setOperationBanner(String operationBanner) {
		this.operationBanner = operationBanner;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLinkPro() {
		return linkPro;
	}
	public void setLinkPro(String linkPro) {
		this.linkPro = linkPro;
	}


}