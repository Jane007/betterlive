package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 视频类型
 *
 */
public class SpecialVideoType  extends BasePO{
	
	private int typeId;			//id
	private String typeName;	//类型名称
	private int status;			//状态 ：1正常 0失效
	private int sort;			//排序，升序
	private String typeCover;	//类型图片
	private int typeCount;		//分类文章数量
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getTypeCover() {
		return typeCover;
	}
	public void setTypeCover(String typeCover) {
		this.typeCover = typeCover;
	}
	public int getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}
}
