package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 标签
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SearchLabelVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = -826353914115503339L;
	
	private int searchId;//搜索记录Id
	private int customerId;//搜寻记录客户ID
	private String nickName;//搜寻记录客户昵称
	private int labelId; //标签主键
	private int labelType; //标签类型 1热门搜索
	private String createTime; //搜索时间
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
}