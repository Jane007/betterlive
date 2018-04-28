package com.kingleadsw.betterlive.model;

import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 活动实体类
 * 2017-03-15 by chen
 */
public class Activity  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer activity_id;            //活动Id
	private String activity_theme;          //活动主题
	private Integer product_id;             //产品ID
	private String starttime;               //活动开始时间
	private String endtime;                 //活动结束时间
	private String create_time;             //创建时间
	
	
	//*********** 非原始字段  *************
	private String product_name;         //产品名称
	private List<ActivityProduct> listActivityProduct;    //活动产品规格优惠 
	
	//------------------------------
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivity_theme() {
		return activity_theme;
	}
	public void setActivity_theme(String activity_theme) {
		this.activity_theme = activity_theme;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public List<ActivityProduct> getListActivityProduct() {
		return listActivityProduct;
	}
	public void setListActivityProduct(List<ActivityProduct> listActivityProduct) {
		this.listActivityProduct = listActivityProduct;
	}
	
	
}
