package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ClassifyBanner;

public interface ClassifyBannerMapper  extends BaseMapper<ClassifyBanner> {
	
List<ClassifyBanner> queryclassifybannerListPage(PageData pd);
	
	int addclassifybanner(ClassifyBanner record);
}