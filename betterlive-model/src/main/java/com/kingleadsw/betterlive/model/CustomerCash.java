package com.kingleadsw.betterlive.model;


import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 用户优惠券兑换活动表
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CustomerCash extends BasePO{
	
	private int recordId; //ID
	private int sysId;	  //活动ID
	private String codeValue; //兑换码
	private int bandType;	//兑换类型 1优惠券 2单品红包
	private String bandCoupon;	//兑换的系统券ID
	private String createTime;	//创建时间
	private int status;		//状态 0进行中 1失效
	private int customerId;	//用户ID
	private Date receiveTime; //领取时间
	
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getSysId() {
		return sysId;
	}
	public void setSysId(int sysId) {
		this.sysId = sysId;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public int getBandType() {
		return bandType;
	}
	public void setBandType(int bandType) {
		this.bandType = bandType;
	}
	public String getBandCoupon() {
		return bandCoupon;
	}
	public void setBandCoupon(String bandCoupon) {
		this.bandCoupon = bandCoupon;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
}