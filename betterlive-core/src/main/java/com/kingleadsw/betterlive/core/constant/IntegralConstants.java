package com.kingleadsw.betterlive.core.constant;

import java.math.BigDecimal;

/**
 * 积分体系常量类
 *
 */
public class IntegralConstants {
	
	public static final int INTEGRAL_SWITCH = 0;//积分活动提示开关（0：开，1：关）
	
	public static final int MIN_USE_THRESHOLD = 500;	//最低使用门槛500个
	
	public static final int MAX_USE_THRESHOLD = 1000;	//最高使用门槛1000个
	
	public static final double DEDUCTION_RATE = 0.01;	//金币兑换金额比例 
	
	public static final BigDecimal MAX_INTEGRAL = new BigDecimal("105");//分享和发帖的总积分
	
	public static final BigDecimal MAX_FORTY = new BigDecimal("40");//发帖(最高可得积分)
	
	public static final BigDecimal MAX_TWENTY_FIVE = new BigDecimal("65") ;//分享(最高可得积分15)+点赞最高积分50
	
	public static final int INTEGRAL_RECORD_TYPE_ONE = 1;//每日签到
	
	public static final int INTEGRAL_RECORD_TYPE_TWO = 2;//发动态
	
	public static final int INTEGRAL_RECORD_TYPE_THREE = 3;//分享文章（动态、精选）
	
	public static final int INTEGRAL_RECORD_TYPE_FOUR = 4;//分享视频
	
	public static final int INTEGRAL_RECORD_TYPE_FIVE = 5;//分享文章点赞
	
	public static final int INTEGRAL_RECORD_TYPE_SIX = 6;//分享视频点赞
	
	public static final int INTEGRAL_RECORD_TYPE_SERVEN = 7;//订单支付
		
	public static final int RECORD_INCOME_YES = 0;//是否为收益：是（收益）
	
	public static final int RECORD_INCOME_NO = 1;//是否为收益：否（支出）
	
	public static final int RECORD_STATUS_WAIT = 0;//收支记录状态：等待中
	
	public static final int RECORD_STATUS_YES = 1;//收支记录状态：已处理（已到账）
	
	public static final int RECORD_STATUS_FAIL = 2;//收支记录状态：已失效
	
	
	public static final int LOTTERY_TYPE_SIGN = 1;	//活动类型  1每日签到
	
	public static final int LOTTERY_STATUS_YES = 0;	//每日抽奖活动状态 0启用
	
	public static final int LOTTERY_STATUS_NO = 1;	//每日抽奖活动状态 1失效、结束
	
	public static final int PRODUCT_REDEEM_STATUS_YES = 0;	//积分活动状态 0启用
	
	public static final int PRODUCT_REDEEM_STATUS_NO = 1;	//积分活动状态 1失效
	
	public static final int PRODUCT_REDEEM_TYPE_ZERO = 0;	//积分活动类型 0 优惠购
	
	public static final int PRODUCT_REDEEM_TYPE_ONE = 1;	//积分活动类型 1 金币抵扣
	
	public static final BigDecimal SHARE_PROFIT_INTEGRAL = new BigDecimal("3");	//每天单次分享不同文章可获取3积分
	
	public static final int LIMIT_DYNAMIC_TIMES = 2;	//每天发动态获取奖励次数
	
	public static final int LIMIT_ARTICLE_TIMES = 5;	//每天文章/视频分享可获取奖励次数
	
	public static final int LIMIT_PRAISE_TIMES = 10;	//每天每篇文章点赞最高奖励次数
	
	public static final int COMMON_STATUS_NO = 0; //公共状态类型：禁用、失效
	
	public static final int COMMON_STATUS_YES = 1; //公共状态类型：启用
	
	
	/*********************************签到有奖 - start **********************************/
	// 签到天数：第一天到第七天[0-6]
	public static final int[] LOTTERY_SIGN_DAY = { 0, 1, 2, 3, 4, 5, 6};
	
	// 签到获取金币数规格
	public static BigDecimal[] LOTTERY_SIGN_INTEGRAL_SPECS = {
			new BigDecimal("0"), new BigDecimal("2"), new BigDecimal("4"),
			new BigDecimal("6"), new BigDecimal("8"), new BigDecimal("10") };
	
	// 签到获取金币数
	public static final BigDecimal[] LOTTERY_SIGN_INTEGRAL = {
			new BigDecimal("0"), new BigDecimal("2"), new BigDecimal("4"),
			new BigDecimal("6"), new BigDecimal("8"), new BigDecimal("10") };
		
	/* 签到1天抽奖概率
	 * 按0-4金币占0.7: 0.05(0金币), 0.35(2金币), 0.3(4金币), 6-10金币占0.3: 0.15(6金币), 0.1(8金币), 0.05(10金币)
	 * 按0.7、0.3转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_FIRSTDAY = {
			new BigDecimal("5"), new BigDecimal("40"),
			new BigDecimal("70"), new BigDecimal("85"),
			new BigDecimal("95"), new BigDecimal("100") };
	
	
	/* 签到2天抽奖概率
	 * 按0-4金币占0.65: 0.05(0金币), 0.3(2金币), 0.3(4金币), 6-10金币占0.35: 0.2(6金币), 0.1(8金币), 0.05(10金币)
	 * 按0.65、0.35转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_SECONDDAY = {
			new BigDecimal("5"), new BigDecimal("35"),
			new BigDecimal("65"), new BigDecimal("85"),
			new BigDecimal("95"), new BigDecimal("100") };
	
	/* 签到3天抽奖概率
	 * 按0-4金币占0.55: 0.05(0金币), 0.2(2金币), 0.3(4金币), 6-10金币占0.45: 0.25(6金币), 0.15(8金币), 0.05(10金币)
	 * 按0.55、0.45转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_THIRDDAY = {
			new BigDecimal("5"), new BigDecimal("25"),
			new BigDecimal("55"), new BigDecimal("80"),
			new BigDecimal("95"), new BigDecimal("100") };
	
	/* 签到4天抽奖概率
	 * 按0-4金币占0.4: 0.05(0金币), 0.1(2金币), 0.25(4金币), 6-10金币占0.6: 0.35(6金币), 0.2(8金币), 0.05(10金币)
	 * 按0.4、0.6转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_FORTHDAY = {
			new BigDecimal("5"), new BigDecimal("15"),
			new BigDecimal("40"), new BigDecimal("75"),
			new BigDecimal("95"), new BigDecimal("100") };
	
	/* 签到5天抽奖概率
	 * 按0-4金币占0.3: 0.05(0金币), 0.1(2金币), 0.15(4金币), 6-10金币占0.7: 0.35(6金币), 0.2(8金币), 0.15(10金币)
	 * 按0.3、0.7转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_FIFTHDAY = {
			new BigDecimal("5"), new BigDecimal("15"),
			new BigDecimal("30"), new BigDecimal("65"),
			new BigDecimal("85"), new BigDecimal("100") };
	
	/* 签到6天抽奖概率
	 * 按0-4金币占0.25: 0.05(0金币), 0.1(2金币), 0.1(4金币), 6-10金币占0.75: 0.2(6金币), 0.4(8金币), 0.15(10金币)
	 * 按0.25、0.75转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_SIXHDAY = {
			new BigDecimal("5"), new BigDecimal("15"),
			new BigDecimal("25"), new BigDecimal("45"),
			new BigDecimal("85"), new BigDecimal("100") };
	
	/* 签到7天抽奖概率
	 * 按0-4金币占0.25: 0.05(0金币), 0.05(2金币), 0.05(4金币), 6-10金币占0.75: 0.05(6金币), 0.3(8金币), 0.5(10金币)
	 * 按0.15、0.85转换实际概率值(%)
	 */ 
	public static final BigDecimal[] LOTTERY_SIGN_RATE_SEVENDAY = {
			new BigDecimal("5"), new BigDecimal("10"),
			new BigDecimal("15"), new BigDecimal("20"),
			new BigDecimal("50"), new BigDecimal("100") };
	
	/*********************************签到有奖 - end **********************************/
	
}
