package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.biz.SearchLabelManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Label;
import com.kingleadsw.betterlive.model.SearchLabel;
import com.kingleadsw.betterlive.service.LabelService;
import com.kingleadsw.betterlive.service.SearchLabelService;
import com.kingleadsw.betterlive.vo.LabelVo;
import com.kingleadsw.betterlive.vo.SearchLabelVo;

/**
 * 标签 实际交互实现层
 */
@Component
@Transactional
public class SearchLabelManagerImpl extends BaseManagerImpl<SearchLabelVo,SearchLabel> implements SearchLabelManager{
	@Autowired
	private SearchLabelService searchLabelService;

	@Override
	protected BaseService<SearchLabel> getService() {
		return searchLabelService;
	}

	
}
