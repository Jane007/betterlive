package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SingleCouponMapper;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.service.SingleCouponService;
/**
 * service 管理层
 * @author zhangjing
 *
 * @date 2017年5月9日
 */
@Service
public class SingleCouponServiceImpl extends BaseServiceImpl<SingleCoupon> implements SingleCouponService{
	@Autowired
	private SingleCouponMapper singleCouponMapper;
	@Override
	protected BaseMapper<SingleCoupon> getBaseMapper() {
		return singleCouponMapper;
	}
	@Override
	public List<SingleCoupon> queryEffectiveCouponList(PageData couponParam) {
		return singleCouponMapper.queryEffectiveCouponList(couponParam);
	}
	
	@Override
	public List<SingleCoupon> queryEffectiveCouponListNew(PageData couponParam) {
		return singleCouponMapper.queryEffectiveCouponListNew(couponParam);
	}

}
