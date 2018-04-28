package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SingleCouponSpecMapper;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.service.SingleCouponSpecService;
@Service
public class SingleCouponSpecServiceImpl extends BaseServiceImpl<SingleCouponSpec> implements SingleCouponSpecService{
	@Autowired
	private SingleCouponSpecMapper singleCouponSpecMapper;
	
	@Override
	protected BaseMapper<SingleCouponSpec> getBaseMapper() {
		return singleCouponSpecMapper;
	}


}
