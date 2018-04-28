package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SystemGuideMapper;
import com.kingleadsw.betterlive.model.SystemGuide;
import com.kingleadsw.betterlive.service.SystemGuideService;


@Service
public class SystemGuideServiceImp  extends BaseServiceImpl<SystemGuide> implements SystemGuideService {

	@Autowired
	private SystemGuideMapper systemGuideMapper;
	
	
	@Override
	protected BaseMapper<SystemGuide> getBaseMapper() {
		
		return systemGuideMapper;
	}

	
}
