package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerGuideManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerGuide;
import com.kingleadsw.betterlive.service.CustomerGuideService;
import com.kingleadsw.betterlive.vo.CustomerGuideVo;


@Component
@Transactional
public class CustomerGuideManagerImpl extends BaseManagerImpl<CustomerGuideVo,CustomerGuide> implements CustomerGuideManager {

	@Autowired
	private CustomerGuideService customerGuideService;
	
	@Override
	protected BaseService<CustomerGuide> getService() {
		
		return customerGuideService;
	}


}