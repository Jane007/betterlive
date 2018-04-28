package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerGuideMapper;
import com.kingleadsw.betterlive.model.CustomerGuide;
import com.kingleadsw.betterlive.service.CustomerGuideService;

@Service
public class CustomerGuideServiceImp extends BaseServiceImpl<CustomerGuide> implements CustomerGuideService {

	
	@Autowired
	CustomerGuideMapper customerGuideMapper;
	@Override
	protected BaseMapper<CustomerGuide> getBaseMapper() {
		
		return customerGuideMapper;
	}
	
}
