package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Label;

public interface LabelService extends BaseService<Label> {

	List<Label> queryReportListPage(PageData pd);
	
	int updateHomeFlag();

}
