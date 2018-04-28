package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SystemLevelMapper;
import com.kingleadsw.betterlive.model.SystemLevel;
import com.kingleadsw.betterlive.service.SystemLevelService;
@Service
public class SystemLevelServiceImpl extends BaseServiceImpl<SystemLevel> implements SystemLevelService{
    @Autowired
	private SystemLevelMapper systemLevelMapper;
	
	@Override
	protected BaseMapper<SystemLevel> getBaseMapper() {
		return systemLevelMapper;
	}

}
