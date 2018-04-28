package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.service.SingleCouponSpecService;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;

@Component
@Transactional
public class SingleCouponSpecManagerImpl extends BaseManagerImpl<SingleCouponSpecVo,SingleCouponSpec> implements SingleCouponSpecManager{
	@Autowired
	private SingleCouponSpecService singleCouponSpecService;
	
	@Override
	protected BaseService<SingleCouponSpec> getService() {
		return singleCouponSpecService;
	}

}
