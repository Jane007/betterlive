package com.kingleadsw.betterlive.dao;


import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Collection;

public interface CollectionMapper extends BaseMapper<Collection>{

	int insertCollection(Collection c);

	int queryCollectionCount(PageData pd);

}
