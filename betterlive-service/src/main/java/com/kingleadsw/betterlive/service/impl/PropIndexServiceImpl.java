package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PropIndexMapper;
import com.kingleadsw.betterlive.model.PropIndex;
import com.kingleadsw.betterlive.service.PropIndexService;

@Service
public class PropIndexServiceImpl extends BaseServiceImpl<PropIndex> implements PropIndexService {
	
	@Autowired
	private PropIndexMapper propIndexMapper;

	@Override
	protected BaseMapper<PropIndex> getBaseMapper() {
		return propIndexMapper;
	}

	@Override
	public PropIndex queryOneForIndex(PageData pd) {
		return propIndexMapper.queryOneForIndex(pd);
	}

}
