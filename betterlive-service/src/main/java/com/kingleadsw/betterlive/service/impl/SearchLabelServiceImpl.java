package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SearchLabelMapper;
import com.kingleadsw.betterlive.model.SearchLabel;
import com.kingleadsw.betterlive.service.SearchLabelService;

@Service("/searchLabelServiceImpl")
public class SearchLabelServiceImpl extends BaseServiceImpl<SearchLabel>  implements SearchLabelService {

	@Autowired
	private SearchLabelMapper searchLabelMapper;
	@Override
	protected BaseMapper<SearchLabel> getBaseMapper() {
		return searchLabelMapper;
	}

}
