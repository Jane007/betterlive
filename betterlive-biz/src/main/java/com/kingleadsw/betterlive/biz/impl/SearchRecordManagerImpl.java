package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SearchRecordManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SearchRecord;
import com.kingleadsw.betterlive.service.SearchRecordService;
import com.kingleadsw.betterlive.vo.SearchRecordVo;

/**
 * 搜索记录 实际交互实现层
 */
@Component
@Transactional
public class SearchRecordManagerImpl extends BaseManagerImpl<SearchRecordVo,SearchRecord> implements SearchRecordManager{
	@Autowired
	private SearchRecordService searchRecordService;

	@Override
	protected BaseService<SearchRecord> getService() {
		return searchRecordService;
	}

	
}
