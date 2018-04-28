package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.vo.SingleCouponVo;

public interface SingleCouponManager extends BaseManager<SingleCouponVo, SingleCoupon>{

	/**
	 * 查询有效的单品红包
	 * @param couponParam
	 * @return
	 */
	List<SingleCouponVo> queryEffectiveCouponList(PageData couponParam);
	
	
	/**
	 *首页展示以红包id分组去掉重复的红包 查询有效的单品红包
	 * @param couponParam
	 * @return
	 */
	List<SingleCouponVo> queryEffectiveCouponListNew(PageData couponParam);

}
