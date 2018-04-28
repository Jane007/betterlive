package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialVideoTypeMapper;
import com.kingleadsw.betterlive.model.SpecialVideoType;
import com.kingleadsw.betterlive.service.SpecialVideoTypeService;

@Service
public class SpecialVideoTypeServiceImpl extends BaseServiceImpl<SpecialVideoType> implements SpecialVideoTypeService{
	
	@Autowired
	private SpecialVideoTypeMapper specialVideoTypeMapper;
	
	@Override
	protected BaseMapper<SpecialVideoType> getBaseMapper() {
		return specialVideoTypeMapper;
	}

	@Override
	public int updateStatusByPrimaryKey(PageData pd) {
		return specialVideoTypeMapper.updateStatusByPrimaryKey(pd);
	}

	@Override
	public List<SpecialVideoType> queryVideoTypeListPage(PageData pd) {
		return specialVideoTypeMapper.queryVideoTypeListPage(pd);
	}
	
}
