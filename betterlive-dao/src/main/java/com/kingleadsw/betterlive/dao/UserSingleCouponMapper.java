package com.kingleadsw.betterlive.dao;

import java.util.List;import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;import com.kingleadsw.betterlive.model.UserSingleCoupon;

public interface UserSingleCouponMapper extends BaseMapper<UserSingleCoupon>{

	List<UserSingleCoupon> queryExpiringList(PageData pd);}
