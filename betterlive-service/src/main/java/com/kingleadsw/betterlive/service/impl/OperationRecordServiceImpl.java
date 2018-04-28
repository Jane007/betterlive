package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OperationRecordMapper;
import com.kingleadsw.betterlive.model.OperationRecord;
import com.kingleadsw.betterlive.service.OperationRecordService;

@Service
public class OperationRecordServiceImpl extends BaseServiceImpl<OperationRecord> implements OperationRecordService {

	@Autowired
    private OperationRecordMapper operationRecordMapper;
	    
    @Override
    protected BaseMapper<OperationRecord> getBaseMapper() {
        return operationRecordMapper;
    }

}
