package com.kingleadsw.betterlive.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.OtherOrderManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.OtherOrder;
import com.kingleadsw.betterlive.service.OtherOrderService;
import com.kingleadsw.betterlive.vo.OtherOrderVo;

@Component
@Transactional
public class OtherOrderManagerImpl  extends BaseManagerImpl<OtherOrderVo,OtherOrder> implements OtherOrderManager{
	@Autowired
	private OtherOrderService otherOrderService;
	
	@Override
	protected BaseService<OtherOrder> getService() {
		return otherOrderService;
	}

	@Override
	public int createOrders(Map<String, Object> params) {
		return otherOrderService.createOrders(params);
	}

}
