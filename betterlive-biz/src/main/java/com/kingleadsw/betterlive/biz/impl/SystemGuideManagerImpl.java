package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SystemGuideManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SystemGuide;
import com.kingleadsw.betterlive.service.SystemGuideService;
import com.kingleadsw.betterlive.vo.SystemGuideVo;

@Component
@Transactional
public class SystemGuideManagerImpl extends BaseManagerImpl<SystemGuideVo,SystemGuide> implements SystemGuideManager {

	@Autowired
	private SystemGuideService systemGuideService;
	
	@Override
	protected BaseService<SystemGuide> getService() {
		return systemGuideService;
	}

}
