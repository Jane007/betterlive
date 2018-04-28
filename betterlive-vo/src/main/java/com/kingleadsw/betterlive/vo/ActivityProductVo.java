package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 活动对应的商品规格 实体类
 * 2017-03-15 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ActivityProductVo  extends BaseVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer activity_spec_id;        //活动对应的商品规格主键
	private Integer activity_id;             //活动ID
	private Integer spec_id;                 //活动对应的商品规格Id
	private String activity_price;           //活动价格
	private String create_time;              //创建时间
	private Integer activity_type;			 //'类型1 预售 2 专题'
	
	private Integer product_id;				//项目id


	//*********** 非原始字段  *************
	private String spec_name;      //规格名称
	private String spec_price;      //规格价格
	private String discount_price;  //优惠价格

	
	//------------------------------
	public Integer getActivity_spec_id() {
		return activity_spec_id;
	}
	public void setActivity_spec_id(Integer activity_spec_id) {
		this.activity_spec_id = activity_spec_id;
	}
	public Integer getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Integer spec_id) {
		this.spec_id = spec_id;
	}
	public String getActivity_price() {
		return activity_price;
	}
	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}
	public String getSpec_price() {
		return spec_price;
	}
	public void setSpec_price(String spec_price) {
		this.spec_price = spec_price;
	}
	public Integer getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(Integer activity_type) {
		this.activity_type = activity_type;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
   
}