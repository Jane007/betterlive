package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 美食达人 VO
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CustomerMasterVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = -917335815926107130L;
	
	private int masterId; //达人ID
	private int customerId; //用户ID
	private int status;	//状态 ：0失效 1正常
	private int recommendFlag;	//推荐达人 0 默认，1推荐
	private int sorts;		//排序 升序
	private String createTime;	//创建时间
	
	private CustomerVo customerVo;

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

	public CustomerVo getCustomerVo() {
		return customerVo;
	}

	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	
}