package com.kingleadsw.betterlive.service;


import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialArticleType;

public interface SpecialArticleTypeService extends BaseService<SpecialArticleType> {

	int updateStatusByPrimaryKey(PageData pd);
}
