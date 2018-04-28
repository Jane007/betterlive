package com.kingleadsw.betterlive.core.constant;

public class OrderConstants {

	/**
	 *  订单状态；0：已删除
	 */
	public final static int STATUS_ORDER_DEL = 0;
	
	/**
	 *  订单状态；1：待付款
	 */
	public final static int STATUS_ORDER_NOPAY=1;
	
	
	/**
	 *  订单状态；2：待发货
	 */
	public final static int STATUS_ORDER_SEND_PRODUCT=2;

	
	/**
	 *  订单状态；3：已发货；
	 */
	public final static int STATUS_ORDER_SEND=3;
	
	/**
	 *  订单状态；4：待评价
	 */
	public final static int STATUS_ORDER_WAIT_COMMENT=4;
	
	/**
	 *  订单状态；5：已完成
	 */
	public final static int STATUS_ORDER_FINSH=5;
	
	/**
	 * 订单状态；6：已取消
	 */
	public final static int STATUS_ORDER_CANCEL=6;
	
	/**
	 * 订单状态；7：已退款
	 */
	public final static int STATUS_ORDER_REFUND = 7;
}
