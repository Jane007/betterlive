package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.InviteReward;

public interface InviteRewardService extends BaseService<InviteReward> {

	List<InviteReward> queryInviteRankingList(PageData pd);

}
