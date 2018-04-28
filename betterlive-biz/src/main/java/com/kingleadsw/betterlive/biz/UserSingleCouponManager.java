package com.kingleadsw.betterlive.biz;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.UserSingleCoupon;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

public interface UserSingleCouponManager extends BaseManager<UserSingleCouponVo, UserSingleCoupon>{
	
	Map<String,Object> insertSingleCoupon(PageData pd) throws ParseException;
	
	Map<String,Object> insertSingleCouponByProductId(PageData pd) throws ParseException;

	List<UserSingleCouponVo> queryExpiringList(PageData pd);
	
	Map<String,Object> CheckPhoneNoCode(PageData pd);
	
}
