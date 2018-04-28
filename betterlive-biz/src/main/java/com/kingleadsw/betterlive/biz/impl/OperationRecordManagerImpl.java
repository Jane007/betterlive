package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.OperationManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Operation;
import com.kingleadsw.betterlive.service.OperationService;
import com.kingleadsw.betterlive.vo.OperationVo;

@Component
@Transactional
public class OperationRecordManagerImpl extends BaseManagerImpl<OperationVo, Operation> implements OperationManager {

	@Autowired
	private OperationService operationService;
	@Override
	protected BaseService<Operation> getService() {
		return operationService;
	}

}
