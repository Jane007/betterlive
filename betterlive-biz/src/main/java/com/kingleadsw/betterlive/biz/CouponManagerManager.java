package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.vo.CouponManagerVo;


/**
 *优惠券管理 实际交互层
 * 2017-03-14 by chen
 */
public interface CouponManagerManager extends BaseManager<CouponManagerVo,CouponManager>{

	int insertCouponManager(CouponManagerVo couponManagerVo);              //增加优惠券管理
	
	int updateCouponManagerByCmId(PageData pd);                        	   //修改优惠券管理
	 
	int deleteByCmId(String cmId);                                         //删除优惠券 

    List<CouponManagerVo> findCouponMangerListPage(PageData pd);           //根据条件分页查询优惠券管理
    
    List<CouponManagerVo> findListCouponManager(PageData pd);     	   	   //根据条件查询全部优惠券管理 
    
    CouponManagerVo findCouponManager(PageData pd);                        //根据条件查询单个优惠券管理详细

	List<CouponManagerVo> queryEffectiveCouponList(PageData couponParam);	//查询有效的优惠券
	
	List<CouponManagerVo> queryNewUserCouponList(PageData couponParam);	//查询新手没购买过首页要显示的券
	
	
}
