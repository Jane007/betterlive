package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.OperationRecordManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.OperationRecord;
import com.kingleadsw.betterlive.service.OperationRecordService;
import com.kingleadsw.betterlive.vo.OperationRecordVo;

@Component
@Transactional
public class OperationManagerImpl extends BaseManagerImpl<OperationRecordVo, OperationRecord> implements OperationRecordManager {

	@Autowired
	private OperationRecordService operationRecordService;
	@Override
	protected BaseService<OperationRecord> getService() {
		return operationRecordService;
	}

}
