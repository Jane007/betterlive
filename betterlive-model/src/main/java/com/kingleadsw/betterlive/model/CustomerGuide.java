package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class CustomerGuide extends BasePO {
	
	private static final long serialVersionUID = 1L;	
	
	private long customerGuideId;	//用户新手指引记录ID
	private long sysGuideId;		//系统新手指引ID
	private Integer guideType;		//指引类型：0每日签到、1当前金币、2发布动态、3分享文章视频有奖
	private long customerId;		//用户ID
	private String createTime;		//创建时间
	

	public long getCustomerGuideId() {
		return customerGuideId;
	}
	public void setCustomerGuideId(long customerGuideId) {
		this.customerGuideId = customerGuideId;
	}
	public long getSysGuideId() {
		return sysGuideId;
	}
	public void setSysGuideId(long sysGuideId) {
		this.sysGuideId = sysGuideId;
	}
	public Integer getGuideType() {
		return guideType;
	}
	public void setGuideType(Integer guideType) {
		this.guideType = guideType;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}