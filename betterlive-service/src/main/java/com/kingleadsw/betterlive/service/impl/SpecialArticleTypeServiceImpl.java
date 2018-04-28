package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialArticleTypeMapper;
import com.kingleadsw.betterlive.model.SpecialArticleType;
import com.kingleadsw.betterlive.service.SpecialArticleTypeService;

@Service
public class SpecialArticleTypeServiceImpl extends BaseServiceImpl<SpecialArticleType> implements SpecialArticleTypeService{
	
	@Autowired
	private SpecialArticleTypeMapper specialArticleTypeMapper;
	
	@Override
	protected BaseMapper<SpecialArticleType> getBaseMapper() {
		return specialArticleTypeMapper;
	}

	@Override
	public int updateStatusByPrimaryKey(PageData pd) {
		return specialArticleTypeMapper.updateStatusByPrimaryKey(pd);
	}
	
}
