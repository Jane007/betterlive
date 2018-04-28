package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SingleCoupon;

public interface SingleCouponMapper extends BaseMapper<SingleCoupon>{

	List<SingleCoupon> queryEffectiveCouponList(PageData couponParam);
	
	List<SingleCoupon> queryEffectiveCouponListNew(PageData couponParam);

}
