package com.kingleadsw.betterlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SysInviteMapper;
import com.kingleadsw.betterlive.model.SysInvite;
import com.kingleadsw.betterlive.service.SysInviteService;

@Service
public class SysInviteServiceImpl extends BaseServiceImpl<SysInvite>  implements SysInviteService {

	@Autowired
	private SysInviteMapper sysInviteMapper;
	@Override
	protected BaseMapper<SysInvite> getBaseMapper() {
		return sysInviteMapper;
	}
	@Override
	public List<SysInvite> queryBySortListPage(PageData pd) {
		return sysInviteMapper.queryBySortListPage(pd);
	}
	@Override
	public List<SysInvite> queryInviteCouponsInfo(PageData pd) {
		return sysInviteMapper.queryInviteCouponsInfo(pd);
	}
	@Override
	public List<Map<String, Object>> queryRegCouponListPage(PageData pd) {
		return sysInviteMapper.queryRegCouponListPage(pd);
	}

}
