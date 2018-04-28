package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CollectionMapper;
import com.kingleadsw.betterlive.model.Collection;
import com.kingleadsw.betterlive.service.CollectionService;

@Service
public class CollectionServiceImpl extends BaseServiceImpl<Collection>  implements CollectionService {

	@Autowired
	private CollectionMapper collectionMapper;
	@Override
	protected BaseMapper<Collection> getBaseMapper() {
		return collectionMapper;
	}
	@Override
	public int insertCollection(Collection c) {
		return collectionMapper.insertCollection(c);
	}
	@Override
	public int queryCollectionCount(PageData pd) {
		return collectionMapper.queryCollectionCount(pd);
	}
}
