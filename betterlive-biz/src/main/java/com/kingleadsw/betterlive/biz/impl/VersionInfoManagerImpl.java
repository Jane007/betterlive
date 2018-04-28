package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.VersionInfoManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.VersionInfo;
import com.kingleadsw.betterlive.service.VersionInfoService;
import com.kingleadsw.betterlive.vo.VersionInfoVo;

/**
 * 版本更新信息 实际交互实现层
 */
@Component
@Transactional
public class VersionInfoManagerImpl extends BaseManagerImpl<VersionInfoVo,VersionInfo> implements VersionInfoManager{
	@Autowired
	private VersionInfoService versionInfoService;

	@Override
	protected BaseService<VersionInfo> getService() {
		return versionInfoService;
	}

	
}
