package com.kingleadsw.betterlive.consts;



/**
 * 系统常量类
 * 
 * @author Administrator
 *
 */
public class SysConstants {

	/**
	 * 默认的时间格式话字符串
	 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

 

	/**
	 * 粉丝关注状态，0：未知状态
	 */
	public static final int SUBSCRIBE_NOTSURE = 0;

	/**
	 * 粉丝关注状态，1：已关注
	 */
	public static final int SUBSCRIBE_YES = 1;

	/**
	 * 粉丝关注状态，2：取消关注
	 */
	public static final int SUBSCRIBE_NO = 2;

	/**
	 * 优惠券状态未使用
	 */
	public static final int COUPON_STATUS_NEW = 0;

	/**
	 * 优惠券状态已使用
	 */
	public static final int COUPON_STATUS_USED = 1;

	/**
	 * 优惠券状态已过期
	 */
	public static final int COUPON_STATUS_OVERDUE = 2;
	
	/**
	 * 用户手机号码为空返回码
	 */
	public static final String USER_NO_MOBILE = "1000";
	
	/**
	 * 默认商品发货地编码，深圳：440300
	 */
	public static final String DEFAULT_CITY_CODE = "440300";

}
