package com.kingleadsw.betterlive.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OtherOrderMapper;
import com.kingleadsw.betterlive.model.OtherOrder;
import com.kingleadsw.betterlive.service.OtherOrderService;

@Service
public class OtherOrderServiceImpl extends BaseServiceImpl<OtherOrder>  implements OtherOrderService{
	@Autowired
	private OtherOrderMapper otherOrderMapper;
	
	@Override
	protected BaseMapper<OtherOrder> getBaseMapper() {
		return otherOrderMapper;
	}

	@Override
	public int createOrders(Map<String, Object> params) {
		return otherOrderMapper.createOrders(params);
	}

}
