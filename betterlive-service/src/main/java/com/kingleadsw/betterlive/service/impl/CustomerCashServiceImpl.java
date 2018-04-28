package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerCashMapper;
import com.kingleadsw.betterlive.model.CustomerCash;
import com.kingleadsw.betterlive.service.CustomerCashService;

@Service
public class CustomerCashServiceImpl extends BaseServiceImpl<CustomerCash> implements CustomerCashService {
	
    @Autowired
    private CustomerCashMapper customerCashMapper;
    
    @Override
    protected BaseMapper<CustomerCash> getBaseMapper() {
        return customerCashMapper;
    }

}
