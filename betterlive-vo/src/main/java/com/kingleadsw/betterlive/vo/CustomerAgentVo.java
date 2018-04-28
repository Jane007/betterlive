package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 标签
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CustomerAgentVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = -826353914115503339L;
	
	private long agentId;	// 代理人ID
	private Integer customerId; //用户ID
	private String password; //提现密码
	private Integer status; //状态（0.启用，1.禁用）
	private String agentCode;//分销标识（唯一）
	private long sysConfigId;//分销收益ID
	private String createTime;//创建时间
	public long getAgentId() {
		return agentId;
	}
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public long getSysConfigId() {
		return sysConfigId;
	}
	public void setSysConfigId(long sysConfigId) {
		this.sysConfigId = sysConfigId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}