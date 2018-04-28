package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Label;

public interface LabelMapper extends BaseMapper<Label>{

	List<Label> queryReportListPage(PageData pd);
	
	int updateHomeFlag();

}
