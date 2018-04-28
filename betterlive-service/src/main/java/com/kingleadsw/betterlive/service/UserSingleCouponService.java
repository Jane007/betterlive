package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UserSingleCoupon;

public interface UserSingleCouponService extends BaseService<UserSingleCoupon>{

List<UserSingleCoupon> queryExpiringList(PageData pd);}
