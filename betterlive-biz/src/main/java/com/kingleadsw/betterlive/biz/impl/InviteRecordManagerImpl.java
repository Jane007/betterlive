package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.InviteRecordManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.InviteRecord;
import com.kingleadsw.betterlive.service.InviteRecordService;
import com.kingleadsw.betterlive.vo.InviteRecordVo;

/**
 * 邀请好友记录信息 实际交互实现层
 */
@Component
@Transactional
public class InviteRecordManagerImpl extends BaseManagerImpl<InviteRecordVo,InviteRecord> implements InviteRecordManager{
	@Autowired
	private InviteRecordService inviteRecordService;

	@Override
	protected BaseService<InviteRecord> getService() {
		return inviteRecordService;
	}

	
}
