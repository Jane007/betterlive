package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 搜索关键词记录
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SearchRecordVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = 6049632278029118474L;
	
	private int searchId; //搜索主键
	private int labelType; //标签类型 1热门搜索
	private int customerId;	//用户ID //0代表游客
	private int labelId;	//标签ID
	private	Date createTime;	//创建时间 
	
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public int getLabelType() {
		return labelType;
	}
	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}
	public int getSearchId() {
		return searchId;
	}
	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}