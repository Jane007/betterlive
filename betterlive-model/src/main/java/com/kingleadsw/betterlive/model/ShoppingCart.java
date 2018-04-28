package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 购物车实体类
 * 2017-03-11 by chen
 */
public class ShoppingCart  extends BasePO{
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
	
	
	
}
