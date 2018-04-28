package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CouponManagerMapper;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.service.CouponManagerService;

/**
 * 商品 service 实现 层 2017-03-08 by chen
 */
@Service
public class CouponManagerServiceImpl extends BaseServiceImpl<CouponManager>
		implements CouponManagerService {
	@Autowired
	private CouponManagerMapper couponManagerMapper;

	// 增加优惠券管理
	@Override
	public int insertCouponManager(CouponManager couponManager) {
		return couponManagerMapper.insertCouponManager(couponManager);
	}

	// 修改优惠券管理
	@Override
	public int updateCouponManagerByCmId(PageData pd) {
		return couponManagerMapper.updateCouponManagerByCmId(pd);
	}

	// 删除优惠券
	@Override
	public int deleteByCmId(String cmId) {
		return couponManagerMapper.deleteByCmId(cmId);
	}

	// 根据条件分页查询优惠券管理
	@Override
	public List<CouponManager> findCouponMangerListPage(PageData pd) {

		return couponManagerMapper.findCouponMangerListPage(pd);
	}

	// 根据条件查询全部优惠券管理
	@Override
	public List<CouponManager> findListCouponManager(PageData pd) {
		return couponManagerMapper.findListCouponManager(pd);
	}

	// 根据条件查询单个优惠券管理详细
	@Override
	public CouponManager findCouponManager(PageData pd) {
		return couponManagerMapper.findCouponManager(pd);
	}

	@Override
	protected BaseMapper<CouponManager> getBaseMapper() {
		return couponManagerMapper;
	}

	@Override
	public List<CouponManager> queryEffectiveCouponList(PageData couponParam) {
		return couponManagerMapper.queryEffectiveCouponList(couponParam);
	}

	@Override
	public List<CouponManager> queryNewUserCouponList(PageData couponParam) {
		return couponManagerMapper.queryNewUserCouponList(couponParam);
	}

}
