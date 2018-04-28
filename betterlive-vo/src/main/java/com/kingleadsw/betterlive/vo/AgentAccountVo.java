package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class AgentAccountVo extends BaseVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7251121654140399508L;
	
	private long accountId;                      		// 账户id
	private long  agentId;                   			// 代理人id
	private BigDecimal  accountAmount;         			// 账户总收益金额
	private BigDecimal cashAllowAmount;         		// 账户可提现余额
	private BigDecimal cashDisallowAmount;       		// 账户不可提现余额
	private Integer status;  								// 状态(0启用，1失效)
	private String createTime; 				   			// 创建时间
	private String  modifyTime;  			 			// 改变时间
	
	
	//###########非数据库字段#################
	


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public long getAgentId() {
		return agentId;
	}


	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}


	public BigDecimal getAccountAmount() {
		return accountAmount;
	}


	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}


	public BigDecimal getCashAllowAmount() {
		return cashAllowAmount;
	}


	public void setCashAllowAmount(BigDecimal cashAllowAmount) {
		this.cashAllowAmount = cashAllowAmount;
	}


	public BigDecimal getCashDisallowAmount() {
		return cashDisallowAmount;
	}


	public void setCashDisallowAmount(BigDecimal cashDisallowAmount) {
		this.cashDisallowAmount = cashDisallowAmount;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}
