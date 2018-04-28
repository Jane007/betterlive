package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 购物车实体类
 * 2017-03-11 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ShoppingCartVo extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer cart_id;           //购物车ID
	private Integer customer_id;       //用户id
	private Integer product_id;        //商品id
	private Integer spec_id;           //商品规格id
	private Integer amount;            //购买数量
	private String create_time;        //创建时间
	private String extension_type;     //商品类型  1 每周新品 2人气推荐 
	
	
	//-------- 不属于 table 表原始字段 ----------
	private String nickname;
	private String product_code;
	private String product_name;
	private String spec_name;
	private String spec_img;
	private String spec_price;
	private String activity_price;        //活动价格
	private Integer status;               //产品状态 
	private Integer rest_copy;			//剩余购买数量，供页面展示
	private Integer hasBuy_copy;			//已经购买数量，供页面展示
	private Integer carNums;			//购物车里有多少件限购规格商品
	private Integer stock_copy;			//库存
	private Integer carCanAdd;			//购物车还可以添加多少份
	private Integer limit_max_copy;		//最大份数 -1代表没限制
	private String limit_start_time;		//限份数开始时间
	private String package_desc;		//说明
	private String label_name;	//标签名称
	private String discount_price;	//优惠价
	private String activity_type;
	private String activity_id;
	
	private String salePromotionString;		//活动促销提示
	private String couponNumString;			//优惠券+红包数量提示
	private String integralString;         //金币优惠购提示
	private String specialString;		   //专题活动提示
	
	public Integer getLimit_max_copy() {
		return limit_max_copy;
	}
	public void setLimit_max_copy(Integer limit_max_copy) {
		this.limit_max_copy = limit_max_copy;
	}
	public String getLimit_start_time() {
		return limit_start_time;
	}
	public void setLimit_start_time(String limit_start_time) {
		this.limit_start_time = limit_start_time;
	}
	public String getLimit_end_time() {
		return limit_end_time;
	}
	public void setLimit_end_time(String limit_end_time) {
		this.limit_end_time = limit_end_time;
	}
	private String limit_end_time;			//限份数结束时间
		
	//-----------------------------------------------
	public Integer getCart_id() {
		return cart_id;
	}
	public void setCart_id(Integer cart_id) {
		this.cart_id = cart_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Integer getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Integer spec_id) {
		this.spec_id = spec_id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}
	public String getSpec_img() {
		return spec_img;
	}
	public void setSpec_img(String spec_img) {
		this.spec_img = spec_img;
	}
	public String getSpec_price() {
		return spec_price;
	}
	public void setSpec_price(String spec_price) {
		this.spec_price = spec_price;
	}
	public String getExtension_type() {
		return extension_type;
	}
	public void setExtension_type(String extension_type) {
		this.extension_type = extension_type;
	}
	public String getActivity_price() {
		return activity_price;
	}
	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRest_copy() {
		return rest_copy;
	}
	public void setRest_copy(Integer rest_copy) {
		this.rest_copy = rest_copy;
	}
	public Integer getHasBuy_copy() {
		return hasBuy_copy;
	}
	public void setHasBuy_copy(Integer hasBuy_copy) {
		this.hasBuy_copy = hasBuy_copy;
	}
	public Integer getCarNums() {
		return carNums;
	}
	public void setCarNums(Integer carNums) {
		this.carNums = carNums;
	}
	public Integer getCarCanAdd() {
		return carCanAdd;
	}
	public void setCarCanAdd(Integer carCanAdd) {
		this.carCanAdd = carCanAdd;
	}
	public Integer getStock_copy() {
		return stock_copy;
	}
	public void setStock_copy(Integer stock_copy) {
		this.stock_copy = stock_copy;
	}
	public String getLabel_name() {
		return label_name;
	}
	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}
	public String getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getPackage_desc() {
		return package_desc;
	}
	public void setPackage_desc(String package_desc) {
		this.package_desc = package_desc;
	}
	public String getSalePromotionString() {
		return salePromotionString;
	}
	public void setSalePromotionString(String salePromotionString) {
		this.salePromotionString = salePromotionString;
	}
	public String getCouponNumString() {
		return couponNumString;
	}
	public void setCouponNumString(String couponNumString) {
		this.couponNumString = couponNumString;
	}
	public String getIntegralString() {
		return integralString;
	}
	public void setIntegralString(String integralString) {
		this.integralString = integralString;
	}
	public String getSpecialString() {
		return specialString;
	}
	public void setSpecialString(String specialString) {
		this.specialString = specialString;
	}
	
	
}
