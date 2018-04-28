package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.InviteReward;


/**
 * 邀请记录  dao层
 */
public interface InviteRewardMapper extends BaseMapper<InviteReward>{

	List<InviteReward> queryInviteRankingList(PageData pd);

}