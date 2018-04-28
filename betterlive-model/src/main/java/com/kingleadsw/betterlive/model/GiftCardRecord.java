package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 礼品卡记录
 * @author zhangjing
 *
 * 2017年3月17日
 */
public class GiftCardRecord extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1179433104898042871L;
	
	private Integer recordId; 			//礼品卡记录id
	
	private String cardNo;				//礼品卡编号
	
	private Integer customerId;			//用户id
	
	private String orderNo;				//订单编号
	
	private String recordRemak;			//记录描述
	   
	private byte recordType;			//记录类型，1：添加礼品卡；2：消费
	
	private BigDecimal money;			//金额
	
	private String recordTime;			//记录时间
	
	private String customerMobile;		//用户手机
	
	private String customerName;		//用户名字

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRecordRemak() {
		return recordRemak;
	}

	public void setRecordRemak(String recordRemak) {
		this.recordRemak = recordRemak;
	}

	public byte getRecordType() {
		return recordType;
	}

	public void setRecordType(byte recordType) {
		this.recordType = recordType;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	

}
