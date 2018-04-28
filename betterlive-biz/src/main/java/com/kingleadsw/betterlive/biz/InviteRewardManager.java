package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.InviteReward;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.InviteRewardVo;

public interface InviteRewardManager  extends BaseManager<InviteRewardVo, InviteReward> {

	/**
	 * 排行榜
	 * @param pd
	 * @return
	 */
	List<InviteRewardVo> queryInviteRankingList(PageData pd);

	/**
	 * 分享邀请成功处理
	 * @param customerVo
	 * @param recordId
	 * @return
	 */
	Map<String, Object> checkFirstInvite(CustomerVo customerVo, int recordId);

	/**
	 * 新用户领券
	 * @param customerId
	 * @param mobile
	 * @param couponId
	 * @param	recordId
	 * @return
	 */
	Map<String, Object> receiveCoupon(int customerId, String mobile, int couponId, int recordId);

	/**
	 * 检查邀请者是否可领奖励
	 * @param recordId
	 * @param customerId
	 * @return
	 */
	int giveInviterReward(int recordId, int customerId);
	
	

}
