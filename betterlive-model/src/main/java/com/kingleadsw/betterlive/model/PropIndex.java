package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class PropIndex extends BasePO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8037416948601248563L;

	private Integer propId;
	
	private Integer propType;	//类型 1活动，2链接
	
	private Integer objId;		//关联主体id(活动)
	
	private String resume;		//简介
	
	private String title;		//标题
	
	private Integer status;		//状态 1正常0禁用
	
	private String createTime;	//创建时间
	
	private String modifyTime; 	//修改时间
	
	private String modifier;	//修改人id
	
	private String linkUrl;		//链接地址
	
	private String linkImg;		//链接图片

	
	
	
	public Integer getPropId() {
		return propId;
	}

	public void setPropId(Integer propId) {
		this.propId = propId;
	}

	public Integer getPropType() {
		return propType;
	}

	public void setPropType(Integer propType) {
		this.propType = propType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
