package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class SystemLevel extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4386104337823835470L;
	
	
	private Integer systemLevelId;
	
	private String levelName;//等级名称
	private Integer status;//'状态：0启用、1失效'
	private String  remark;	//备注
	
	private String createTime;		//创建时间
	private String modifyTime;  	//修改时间
	private Long creator;			//创建者
	private Long modifier;			//修改人ID
	
	private Integer requirementIntegral;// 升级需达到会员累计积分值
	private Integer level;			//等级：从0开始
	private String imgUrl;			//等级图标地址

	public Integer getSystemLevelId() {
		return systemLevelId;
	}

	public void setSystemLevelId(Integer systemLevelId) {
		this.systemLevelId = systemLevelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Integer getRequirementIntegral() {
		return requirementIntegral;
	}

	public void setRequirementIntegral(Integer requirementIntegral) {
		this.requirementIntegral = requirementIntegral;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
