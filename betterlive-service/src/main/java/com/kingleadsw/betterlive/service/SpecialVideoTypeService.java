package com.kingleadsw.betterlive.service;


import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialVideoType;

public interface SpecialVideoTypeService extends BaseService<SpecialVideoType> {

	int updateStatusByPrimaryKey(PageData pd);

	List<SpecialVideoType> queryVideoTypeListPage(PageData pd);
}
