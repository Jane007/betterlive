package com.kingleadsw.betterlive.biz.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SysInviteManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysInvite;
import com.kingleadsw.betterlive.service.SysInviteService;
import com.kingleadsw.betterlive.vo.SysInviteVo;

/**
 * 系统邀请好友信息 实际交互实现层
 */
@Component
@Transactional
public class SysInviteManagerImpl extends BaseManagerImpl<SysInviteVo,SysInvite> implements SysInviteManager{
	@Autowired
	private SysInviteService sysInviteService;

	@Override
	protected BaseService<SysInvite> getService() {
		return sysInviteService;
	}

	@Override
	public List<SysInviteVo> queryBySortListPage(PageData pd) {
		return generator.transfer(SysInviteVo.class,sysInviteService.queryBySortListPage(pd));
	}

	@Override
	public List<SysInviteVo> queryInviteCouponsInfo(PageData pd) {
		return generator.transfer(SysInviteVo.class,sysInviteService.queryInviteCouponsInfo(pd));
	}

	@Override
	public List<Map<String, Object>> queryRegCouponListPage(PageData pd) {
		return sysInviteService.queryRegCouponListPage(pd);
	}

	
}
