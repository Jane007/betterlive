package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysGroup;
import com.kingleadsw.betterlive.service.SysGroupService;
import com.kingleadsw.betterlive.vo.SysGroupVo;

@Component
@Transactional
public class SysGroupManagerImpl extends BaseManagerImpl<SysGroupVo,SysGroup> implements SysGroupManager {

	@Autowired
	private SysGroupService sysGroupService;
	@Override
	protected BaseService<SysGroup> getService() {
		return sysGroupService;
	}

}
