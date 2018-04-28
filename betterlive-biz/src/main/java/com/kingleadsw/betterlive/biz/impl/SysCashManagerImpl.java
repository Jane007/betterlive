package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SysCashManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysCash;
import com.kingleadsw.betterlive.service.SysCashService;
import com.kingleadsw.betterlive.vo.SysCashVo;

@Component
@Transactional
public class SysCashManagerImpl extends BaseManagerImpl<SysCashVo,SysCash> implements SysCashManager {

	@Autowired
	private SysCashService sysCashService;
	@Override
	protected BaseService<SysCash> getService() {
		return sysCashService;
	}

}
