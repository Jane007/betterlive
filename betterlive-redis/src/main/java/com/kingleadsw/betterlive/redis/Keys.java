package com.kingleadsw.betterlive.redis;

/**
 * redis缓存key
 **/
public class Keys {

	
	/**
	 * 计算爱豆文章排名分值,锁key
	 */
	public final static String ARTICLESORT_TASK_LOCK_KEY="articlesort_task_lock_key";
	
	public final static String STATICS_KEYWORD_TASK_LOCK="statics_keyword_task_lock";

	/**
	 * 邀请成功注册获取空白券次数
	 */
	public final static String INVITED_REGISTED_SUCC_COUNT="invited_registed_succ_count";
	/**
	 * 评论消费订单获得积分
	 */
	public final static String COMMENT_ORDER_INTEGRAL="comment_order_integral";

	/**
	 * 城市列表缓存
	 */
	public final static String PARENT_CITY_KEY_PREINDEX = "parent_city_key_preindex";
	
	/**
	 * 缓存最热文章
	 */
	public final static String KEY_HOTARTICLEKEY="hotArticleKey";
	
	/**
	 * 赞统计
	 */
	public final static String KEY_ZAN_COUNT="key_zan_count";
	
	public final static String KEY_ZAN_COUNT_TIME="key_zan_count_time";

	/**
	 * 用户是否注册邀请成功过5个人
	 */
	public final static String KEY_REGISTER_GET_SYSTEM_COUPON_FLAG = "key_register_get_system_coupon_flag";
	
	/**
	 * 用户登录后缓存用户信息的token信息
	 */
	public final static String APP_TOKEN_PREFIX = "app_token_";
	
	
	/**
	 * 用户登录后缓存用户信息的token信息
	 */
	public final static String APP_CUSTOMER_PREFIX = "app_customer_";
	
   
}
