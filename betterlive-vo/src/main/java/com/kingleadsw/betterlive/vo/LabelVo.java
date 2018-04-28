package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 标签
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class LabelVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = -826353914115503339L;
	
	private int labelId; //标签主键
	private int labelType; //标签类型 1热门搜索
	private String labelName; //标签名称
	private int searchCount;	//搜索次数
	private int status;		//状态 0正常 1失效
	private int labelSort;	//排序 升序
	private int homeFlag;	//是否推荐为热门搜索 0否 1是
	private String productIds;	//
	
	
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
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLabelSort() {
		return labelSort;
	}
	public void setLabelSort(int labelSort) {
		this.labelSort = labelSort;
	}
	public int getHomeFlag() {
		return homeFlag;
	}
	public void setHomeFlag(int homeFlag) {
		this.homeFlag = homeFlag;
	}
	
	
}