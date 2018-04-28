package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerMasterMapper;
import com.kingleadsw.betterlive.model.CustomerMaster;
import com.kingleadsw.betterlive.service.CustomerMasterService;

@Service
public class CustomerMasterServiceImpl extends BaseServiceImpl<CustomerMaster> implements CustomerMasterService {

	@Autowired
	private CustomerMasterMapper customerMasterMapper;
	@Override
	protected BaseMapper<CustomerMaster> getBaseMapper() {
		return customerMasterMapper;
	}
	@Override
	public List<CustomerMaster> queryHotList(PageData pd) {
		return customerMasterMapper.queryHotList(pd);
	}

}
