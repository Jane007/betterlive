package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OperationMapper;
import com.kingleadsw.betterlive.model.Operation;
import com.kingleadsw.betterlive.service.OperationService;

@Service
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements OperationService {

	@Autowired
    private OperationMapper operationMapper;
	    
    @Override
    protected BaseMapper<Operation> getBaseMapper() {
        return operationMapper;
    }

}
