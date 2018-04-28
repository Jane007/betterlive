package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SpecialVideoTypeVo extends BaseVO implements Serializable {
	private static final long serialVersionUID = -6088043775767381390L;
	
	public SpecialVideoTypeVo(){}
	public SpecialVideoTypeVo(int typeId, String typeName){
		this.typeId = typeId;
		this.typeName = typeName;
	}
	
	private int typeId;			//id
	private String typeName;	//类型名称
	private int status;			//状态 ：1正常 0失效
	private int sort;			//排序，升序
	private int typeCount;		//分类文章数量
	private String typeCover;	//主页类型图片
	
	private List<SpecialVo> specialList;	//视频列表
	
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
	public List<SpecialVo> getSpecialList() {
		return specialList;
	}
	public void setSpecialList(List<SpecialVo> specialList) {
		this.specialList = specialList;
	}
}
