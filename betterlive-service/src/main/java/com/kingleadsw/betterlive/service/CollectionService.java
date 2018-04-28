package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Collection;

public interface CollectionService extends BaseService<Collection> {

	int insertCollection(Collection c);

	int queryCollectionCount(PageData pd);

}
