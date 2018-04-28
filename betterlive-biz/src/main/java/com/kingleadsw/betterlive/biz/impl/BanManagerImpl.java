package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.BanManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Ban;
import com.kingleadsw.betterlive.service.BanService;
import com.kingleadsw.betterlive.vo.BanVo;

/**
 * 禁止非法操作 实际交互实现层
 */
@Component
@Transactional
public class BanManagerImpl extends BaseManagerImpl<BanVo,Ban> implements BanManager{
	@Autowired
	private BanService banService;

	@Override
	protected BaseService<Ban> getService() {
		return banService;
	}

	
	
}
