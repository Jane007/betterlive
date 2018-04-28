package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.LabelMapper;
import com.kingleadsw.betterlive.model.Label;
import com.kingleadsw.betterlive.service.LabelService;

@Service("/labelServiceImpl")
public class LabelServiceImpl extends BaseServiceImpl<Label>  implements LabelService {

	@Autowired
	private LabelMapper labelMapper;
	@Override
	protected BaseMapper<Label> getBaseMapper() {
		return labelMapper;
	}
	@Override
	public List<Label> queryReportListPage(PageData pd) {
		return labelMapper.queryReportListPage(pd);
	}
	
	public int updateHomeFlag(){
		return labelMapper.updateHomeFlag();
	}

}
