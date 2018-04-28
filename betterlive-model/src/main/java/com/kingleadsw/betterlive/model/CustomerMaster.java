package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 美食达人 实体类
 *
 */
public class CustomerMaster extends BasePO {
	private static final long serialVersionUID = -3955305860351903640L;
	
	private int masterId; //达人ID
	private int customerId; //用户ID
	private int status;	//状态 ：0失效 1正常
	private int recommendFlag;	//推荐达人 0 默认，1推荐
	private int sorts;		//排序 升序
	private String createTime;	//创建时间
	
	private Customer customer;

	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(int recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public int getSorts() {
		return sorts;
	}

	public void setSorts(int sorts) {
		this.sorts = sorts;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
