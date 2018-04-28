package com.kingleadsw.betterlive.dao;


import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialArticleType;

public interface SpecialArticleTypeMapper extends BaseMapper<SpecialArticleType> {

	int updateStatusByPrimaryKey(PageData pd);

}
