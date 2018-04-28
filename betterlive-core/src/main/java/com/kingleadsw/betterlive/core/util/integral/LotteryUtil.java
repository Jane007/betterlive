package com.kingleadsw.betterlive.core.util.integral;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Random;

import com.kingleadsw.betterlive.core.constant.IntegralConstants;

public class LotteryUtil {

	/**
	 * 比较概率，抽奖
	 * @param day  连续签到天数
	 * @return
	 */
	public static BigDecimal compareToRate(int signDay) {
		BigDecimal c = BigDecimal.valueOf(Math.random() * (100 - 1) + 1); // 中奖概率（1-100之间）
		c = c.setScale(2, BigDecimal.ROUND_HALF_UP); // 保留两位小数，且四舍五入
		if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[0]) { // 还没签到，现在签到的第一天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[1]) <= 0) {  //35%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[2]) <= 0) {  //30%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[3]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[4]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIRSTDAY[5]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[1]) { // 还没签到，现在签到的第二天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[1]) <= 0) {  //30%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[2]) <= 0) {  //30%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[3]) <= 0) {  //20%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[4]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SECONDDAY[5]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[2]) { // 还没签到，现在签到的第三天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[1]) <= 0) {  //20%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[2]) <= 0) {  //30%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[3]) <= 0) {  //25%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[4]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_THIRDDAY[5]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[3]) { // 还没签到，现在签到的第四天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[1]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[2]) <= 0) {  //25%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[3]) <= 0) {  //35%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[4]) <= 0) {  //20%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FORTHDAY[5]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[4]) { // 还没签到，现在签到的第五天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[1]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[2]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[3]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[4]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_FIFTHDAY[5]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[5]) { // 还没签到，现在签到的第六天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[1]) <= 0) {  //10%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[2]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[3]) <= 0) {  //20%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[4]) <= 0) {  //40%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[5]) <= 0) {  //15%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}else if (signDay == IntegralConstants.LOTTERY_SIGN_DAY[6]) { // 还没签到，现在签到的第七天
			if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[0]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[0];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[1]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[1];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[2]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[2];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[3]) <= 0) {  //5%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[3];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[4]) <= 0) {  //30%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[4];
			}else if (c.compareTo(IntegralConstants.LOTTERY_SIGN_RATE_SIXHDAY[5]) <= 0) {  //50%
				return IntegralConstants.LOTTERY_SIGN_INTEGRAL[5];
			}
		}
		return BigDecimal.ZERO;
	}
	
	/** 
	  * 随机排序 
	  * @param list 要进行随机排序的数据集合 
	  * @return 排序结果 
	  */  
	 public static BigDecimal[] getList(BigDecimal[] list){  
	     //数组长度  
	     int count = list.length;  
	     //结果集  
	     BigDecimal[] resultList = new BigDecimal[count];  
	     //用一个LinkedList作为中介  
	     LinkedList<BigDecimal> temp = new LinkedList<BigDecimal>();  
	     //初始化temp  
	     for(int i = 0; i<count; i++){  
	         temp.add((BigDecimal)list[i]);  
	     }  
	     //取数  
	     Random rand = new Random();  
	     for(int i = 0;i<count; i++){  
	         int num = rand.nextInt(count - i);  
	         resultList[i] = (BigDecimal) temp.get(num);  
	         temp.remove(num);  
	     }  
	       
	     return resultList;  
	 }
	
	/**
	 * 每日签到抽奖的奖励描述排序，无规则排序
	 * @param clickIndex 当前抽奖点击的单元格下标
	 * @param signIntegral 抽中积分
	 */
	public static String[] lotteryDescSorts(int clickIndex, BigDecimal signIntegral) {
		// 将奖品规格随机排序
		BigDecimal[] specs = LotteryUtil.getList(IntegralConstants.LOTTERY_SIGN_INTEGRAL_SPECS);

		// 抽奖弹窗，初始化每个单元格对应的值
		String[] specsDesc = new String[6];
		for (int i = 0; i < specs.length; i++) {
			if (specs[i].compareTo(BigDecimal.ZERO) == 0) {
				specsDesc[i] = "谢谢参与";
			} else {
				specsDesc[i] = "金币" + specs[i] + "个";
			}
		}

		String oldDesc = specsDesc[clickIndex];
		String clickDesc = "";
		//找到未替换前的单元格的值
		for (int i = 0; i < specs.length; i++) {
			if (signIntegral.compareTo(specs[i]) == 0) {
				clickDesc = specsDesc[i];
				// 将初始化时需替换的值放到当前位置
				specsDesc[i] = oldDesc;
				break;
			}
		}
		
		//将用户当前点击的单元格的规格匹配到正确的位置
		specsDesc[clickIndex] = clickDesc;
		
		return specsDesc;
	}
	
}
