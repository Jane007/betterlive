package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.InviteRewardMapper;
import com.kingleadsw.betterlive.model.InviteReward;
import com.kingleadsw.betterlive.service.InviteRewardService;

@Service
public class InviteRewardServiceImpl extends BaseServiceImpl<InviteReward>  implements InviteRewardService {

	@Autowired
	private InviteRewardMapper inviteRewardMapper;
	@Override
	protected BaseMapper<InviteReward> getBaseMapper() {
		return inviteRewardMapper;
	}
	
	@Override
	public List<InviteReward> queryInviteRankingList(PageData pd) {
		return inviteRewardMapper.queryInviteRankingList(pd);
	}

}
