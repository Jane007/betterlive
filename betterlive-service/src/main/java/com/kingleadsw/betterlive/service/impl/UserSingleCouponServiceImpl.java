package com.kingleadsw.betterlive.service.impl;

import java.util.List;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.UserSingleCouponMapper;
import com.kingleadsw.betterlive.model.UserSingleCoupon;
import com.kingleadsw.betterlive.service.UserSingleCouponService;
@Service
public class UserSingleCouponServiceImpl extends BaseServiceImpl<UserSingleCoupon> implements UserSingleCouponService{
	@Autowired
	private UserSingleCouponMapper userSingleCouponMapper;
	
	@Override
	protected BaseMapper<UserSingleCoupon> getBaseMapper() {
		return userSingleCouponMapper;
	}

	@Override
	public List<UserSingleCoupon> queryExpiringList(PageData pd) {
		return userSingleCouponMapper.queryExpiringList(pd);
	}}
