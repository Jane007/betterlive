package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.InviteRecordMapper;
import com.kingleadsw.betterlive.model.InviteRecord;
import com.kingleadsw.betterlive.service.InviteRecordService;

@Service
public class InviteRecordServiceImpl extends BaseServiceImpl<InviteRecord>  implements InviteRecordService {

	@Autowired
	private InviteRecordMapper inviteRecordMapper;
	@Override
	protected BaseMapper<InviteRecord> getBaseMapper() {
		return inviteRecordMapper;
	}
	@Override
	public int checkInviteByDay(int customerId) {
		return inviteRecordMapper.checkInviteByDay(customerId);
	}
	@Override
	public int updateRecordByParam(PageData pd) {
		return inviteRecordMapper.updateRecordByParam(pd);
	}
	@Override
	public List<InviteRecord> queryInviteRecordListByFinish(int customerId) {
		return inviteRecordMapper.queryInviteRecordListByFinish(customerId);
	}

}
