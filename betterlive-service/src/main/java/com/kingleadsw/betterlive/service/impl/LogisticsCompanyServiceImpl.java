package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.LogisticsCompanyMapper;
import com.kingleadsw.betterlive.model.LogisticsCompany;
import com.kingleadsw.betterlive.service.LogisticsCompanyService;

@Service("/logisticsCompanyService")
public class LogisticsCompanyServiceImpl extends BaseServiceImpl<LogisticsCompany>  implements LogisticsCompanyService{

	@Autowired
	private LogisticsCompanyMapper logisticsCompanyMapper;
	@Override
	protected BaseMapper<LogisticsCompany> getBaseMapper() {
		return logisticsCompanyMapper;
	}

}
