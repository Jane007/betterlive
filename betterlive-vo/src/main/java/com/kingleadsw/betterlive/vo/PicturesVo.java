package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 缩略图实体类
 * 2017-03-11 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class PicturesVo extends BaseVO implements Serializable {

	private static final long serialVersionUID = -7876449792184559020L;

	private Integer picture_id; // 图片id
	private Integer object_id; // 图片对象id，根据图片类型确定对象主体
	private Integer picture_type; //图片类型，1：产品图片表； 2：订单评价图片；3团购广告图片，4限量广告图片，5动态文章图片
	private String small_img; // 缩略图
	private String original_img; // 原图
	private String create_time; // 创建时间
	private int status;	//状态 1正常，0失效
	private int picWidth;	//图片宽度
	private int picHeight;	//图片高度
	private int sort;		//排序
	
	public Integer getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(Integer picture_id) {
		this.picture_id = picture_id;
	}
	public Integer getObject_id() {
		return object_id;
	}
	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	}
	public Integer getPicture_type() {
		return picture_type;
	}
	public void setPicture_type(Integer picture_type) {
		this.picture_type = picture_type;
	}
	public String getSmall_img() {
		return small_img;
	}
	public void setSmall_img(String small_img) {
		this.small_img = small_img;
	}
	public String getOriginal_img() {
		return original_img;
	}
	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPicWidth() {
		return picWidth;
	}
	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}
	public int getPicHeight() {
		return picHeight;
	}
	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
