package com.kingleadsw.betterlive.biz;

import java.util.Map;

public interface CustomerLotteryManager {

	/**
	 * 用户每日签到信息
	 * @param customerId
	 * @return
	 */
	Map<String, Object> queryCustomerSignInfo(Integer customerId);
	
	/**
	 * 每日签到抽奖
	 * @param customerId
	 * @param clickIndex
	 * @return
	 */
	Map<String, Object> getLotterySign(int customerId, int clickIndex);
}
