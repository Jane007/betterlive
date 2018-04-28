package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialVideoType;

public interface SpecialVideoTypeMapper extends BaseMapper<SpecialVideoType> {

	int updateStatusByPrimaryKey(PageData pd);

	List<SpecialVideoType> queryVideoTypeListPage(PageData pd);

}
