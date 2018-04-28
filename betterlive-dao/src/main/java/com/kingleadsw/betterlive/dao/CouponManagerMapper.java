package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CouponManager;


/**
 * 优惠券管理实体类
 * 2017-03-14 by chen
 */
public interface CouponManagerMapper extends BaseMapper<CouponManager>{

	int insertCouponManager(CouponManager couponManager);              //增加优惠券管理
	
	int updateCouponManagerByCmId(PageData pd);                        //修改优惠券管理
	 
	int deleteByCmId(String cmId);                                    //删除优惠券 

    List<CouponManager> findCouponMangerListPage(PageData pd);         //根据条件分页查询优惠券管理
    
    List<CouponManager> findListCouponManager(PageData pd);     	   //根据条件查询全部优惠券管理 
    
    CouponManager findCouponManager(PageData pd);                      //根据条件查询单个优惠券管理详细

	List<CouponManager> queryEffectiveCouponList(PageData couponParam);	//查询有效的优惠券
	
	List<CouponManager> queryNewUserCouponList(PageData couponParam);	//查询新手没购买过首页要显示的券
	
   
}