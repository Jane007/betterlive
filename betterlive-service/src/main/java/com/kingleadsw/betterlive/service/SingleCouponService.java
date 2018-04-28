package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SingleCoupon;

public interface SingleCouponService extends BaseService<SingleCoupon>{

	List<SingleCoupon> queryEffectiveCouponList(PageData couponParam);
	
	
	List<SingleCoupon> queryEffectiveCouponListNew(PageData couponParam);

}
