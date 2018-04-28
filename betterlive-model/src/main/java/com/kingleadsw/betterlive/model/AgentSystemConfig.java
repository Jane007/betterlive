package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


public class AgentSystemConfig extends BasePO {
	private static final long serialVersionUID = 8626195400211880386L;
	
	private long configId;       //收益配置id
	private String proportion;       //收益比例（百分比例，系统计算是添加%)
	private Integer status;			//状态（0启用，1失效）
	private Integer isDefault;		//默认注册启用（0否，1是）,只能有一条数据被启用
	private String createTime;		//创建时间
	private String modifyTime;		//修改时间
	private Long creator;			//创建者 
	//非表内字段
	private String userName;		//操作人名字

	public long getConfigId() {
		return configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}
	

	
}
