package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.VersionInfoMapper;
import com.kingleadsw.betterlive.model.VersionInfo;
import com.kingleadsw.betterlive.service.VersionInfoService;

@Service
public class VersionInfoServiceImpl extends BaseServiceImpl<VersionInfo>  implements VersionInfoService {

	@Autowired
	private VersionInfoMapper versionInfoMapper;
	@Override
	protected BaseMapper<VersionInfo> getBaseMapper() {
		return versionInfoMapper;
	}

}
