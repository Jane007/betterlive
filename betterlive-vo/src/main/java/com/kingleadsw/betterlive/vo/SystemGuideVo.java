package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 系统新手指引
 * 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)

public class SystemGuideVo extends BaseVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private long systemGuideId;		// 系统新手指引ID
	private int guideType;			// 指引类型：0每日签到、1当前金币、2发布动态、3分享文章视频有奖
	private String guideName;		// 指引名称
	private int status;				// 状态0启用、1失效
	private long creator;			// 作者
	private String createTime;		// 创建时间
	private long modifier;			// 最后修改人
	private String modifyTime;		// 修改时间
	private String remark;			// 备注
	
	
	public long getSystemGuideId() {
		return systemGuideId;
	}
	public void setSystemGuideId(long systemGuideId) {
		this.systemGuideId = systemGuideId;
	}
	public int getGuideType() {
		return guideType;
	}
	public void setGuideType(int guideType) {
		this.guideType = guideType;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getModifier() {
		return modifier;
	}
	public void setModifier(long modifier) {
		this.modifier = modifier;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}