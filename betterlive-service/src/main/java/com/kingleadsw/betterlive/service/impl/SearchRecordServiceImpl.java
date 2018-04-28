package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SearchRecordMapper;
import com.kingleadsw.betterlive.model.SearchRecord;
import com.kingleadsw.betterlive.service.SearchRecordService;

@Service("/searchRecordServiceImpl")
public class SearchRecordServiceImpl extends BaseServiceImpl<SearchRecord>  implements SearchRecordService {

	@Autowired
	private SearchRecordMapper searchRecordMapper;
	
	@Override
	protected BaseMapper<SearchRecord> getBaseMapper() {
		return searchRecordMapper;
	}

}
