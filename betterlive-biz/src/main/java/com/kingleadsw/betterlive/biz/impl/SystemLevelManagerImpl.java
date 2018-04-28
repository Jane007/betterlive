package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SystemLevel;
import com.kingleadsw.betterlive.service.SystemLevelService;
import com.kingleadsw.betterlive.vo.SystemLevelVo;

/**
 * 版本更新信息 实际交互实现层
 */
@Component
@Transactional
public class SystemLevelManagerImpl extends BaseManagerImpl<SystemLevelVo, SystemLevel> implements SystemLevelManager{
	@Autowired
	private SystemLevelService systemLevelService;

	@Override
	protected BaseService<SystemLevel> getService() {
		return systemLevelService;
	}

	
}
