package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 订单分析实体类
 * 2017-09-03 by lee
 */
public class OrderSales extends BasePO {

	private int orderId;			//订单ID
	private String orderSource;		//订单来源
	private String customerSource;	//用户来源
	private String totalPrice;		//订单金额
	private String totalPay;		//实付金额
	private String productId;
	private String productName;		//商品名称
	private String productCode;
	private int quantity;			//数量
	private int orderCount;			//订单份数
	private int productCount;		//商品份数
	
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
}
