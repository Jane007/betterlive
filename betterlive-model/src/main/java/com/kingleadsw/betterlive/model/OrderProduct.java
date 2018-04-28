package com.kingleadsw.betterlive.model;


import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 订单商品 实体类
 * 2017-03-10 by chen
 */
public class OrderProduct  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer orderpro_id;         //订单商品id
	private Integer order_id;            //订单id
	private Integer product_id;          //商品id
	private String product_name;         //商品名称
	private Integer spec_id;             //用户选择规格id，关联商品规格id
	private String spec_name;            //商品规格名称
	private String spec_img;			 //商品规格图片
	private Integer quantity;            //用户购买的数量
	private String price;                //价格 
	private Integer is_evaluate;         //是否已评价，1：是；0：否 
	private String activity_price;      //活动优惠价格
	private BigDecimal full_money;	//满减
	private Integer promotion_id;	//满减活动id
	private BigDecimal cut_money;	//减多少钱
	private Integer user_single_coupon_id;		//用户使用单品红包
	private BigDecimal coupon_money;			//优惠金额
	private String logistics_code;//快递单号
	private String company_code;  //要查询的快递公司代码
	private String company_name;  //要查询的快递公司名称
	private String send_time;     //发货时间 
	private Integer status;       //订单状态，0：已删除；1：待付款；2：待发货；3：待签收；4：待评价；5：已完成;6:系统取消;7:已退款 
	private String sub_order_code;//子订单编号
	private String discount_price;//优惠价
	private int ifCoupon;	//不参与使用优惠券
	private String confirmTime;	//确认收货
	private BigDecimal redeemIntegral;		//金币优惠购扣除金币
	private BigDecimal goldIntegral;		//普通金币扣除
	private BigDecimal redeemMoney;		//金币优惠购抵扣金额
	private BigDecimal goldMoney;		//普通金币抵扣金额
	private Long redeemSpecId;		//金币优惠购规格ID
	private Long goldSpecId;		//普通金币抵扣规格ID
	
	//---------------- 非原始表字段 ----------------------------
	private String product_status;      //商品状态  
	private String giftCardMoney;	//礼品卡优惠金额
    
	//----------------------------------------------------------
	
	
	public Integer getOrderpro_id() {
		return orderpro_id;
	}
	/**
	 * @return the logistics_code
	 */
	public String getLogistics_code() {
		return logistics_code;
	}
	/**
	 * @param logistics_code the logistics_code to set
	 */
	public void setLogistics_code(String logistics_code) {
		this.logistics_code = logistics_code;
	}
	/**
	 * @return the company_code
	 */
	public String getCompany_code() {
		return company_code;
	}
	/**
	 * @param company_code the company_code to set
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}
	/**
	 * @return the send_time
	 */
	public String getSend_time() {
		return send_time;
	}
	/**
	 * @param send_time the send_time to set
	 */
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the sub_order_code
	 */
	public String getSub_order_code() {
		return sub_order_code;
	}
	/**
	 * @param sub_order_code the sub_order_code to set
	 */
	public void setSub_order_code(String sub_order_code) {
		this.sub_order_code = sub_order_code;
	}
	public void setOrderpro_id(Integer orderpro_id) {
		this.orderpro_id = orderpro_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Integer spec_id) {
		this.spec_id = spec_id;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getIs_evaluate() {
		return is_evaluate;
	}
	public void setIs_evaluate(Integer is_evaluate) {
		this.is_evaluate = is_evaluate;
	}
	public String getActivity_price() {
		return activity_price;
	}
	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}
	public String getProduct_status() {
		return product_status;
	}
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}
	public BigDecimal getFull_money() {
		return full_money;
	}
	public void setFull_money(BigDecimal full_money) {
		this.full_money = full_money;
	}
	public Integer getPromotion_id() {
		return promotion_id;
	}
	public void setPromotion_id(Integer promotion_id) {
		this.promotion_id = promotion_id;
	}
	public BigDecimal getCut_money() {
		return cut_money;
	}
	public void setCut_money(BigDecimal cut_money) {
		this.cut_money = cut_money;
	}
	public Integer getUser_single_coupon_id() {
		return user_single_coupon_id;
	}
	public void setUser_single_coupon_id(Integer user_single_coupon_id) {
		this.user_single_coupon_id = user_single_coupon_id;
	}
	public BigDecimal getCoupon_money() {
		return coupon_money;
	}
	public void setCoupon_money(BigDecimal coupon_money) {
		this.coupon_money = coupon_money;
	}
	public String getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
	public String getGiftCardMoney() {
		return giftCardMoney;
	}
	public void setGiftCardMoney(String giftCardMoney) {
		this.giftCardMoney = giftCardMoney;
	}
	public int getIfCoupon() {
		return ifCoupon;
	}
	public void setIfCoupon(int ifCoupon) {
		this.ifCoupon = ifCoupon;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public BigDecimal getRedeemIntegral() {
		return redeemIntegral;
	}
	public void setRedeemIntegral(BigDecimal redeemIntegral) {
		this.redeemIntegral = redeemIntegral;
	}
	public BigDecimal getGoldIntegral() {
		return goldIntegral;
	}
	public void setGoldIntegral(BigDecimal goldIntegral) {
		this.goldIntegral = goldIntegral;
	}
	public BigDecimal getRedeemMoney() {
		return redeemMoney;
	}
	public void setRedeemMoney(BigDecimal redeemMoney) {
		this.redeemMoney = redeemMoney;
	}
	public BigDecimal getGoldMoney() {
		return goldMoney;
	}
	public void setGoldMoney(BigDecimal goldMoney) {
		this.goldMoney = goldMoney;
	}
	public Long getRedeemSpecId() {
		return redeemSpecId;
	}
	public void setRedeemSpecId(Long redeemSpecId) {
		this.redeemSpecId = redeemSpecId;
	}
	public Long getGoldSpecId() {
		return goldSpecId;
	}
	public void setGoldSpecId(Long goldSpecId) {
		this.goldSpecId = goldSpecId;
	}
	
}
