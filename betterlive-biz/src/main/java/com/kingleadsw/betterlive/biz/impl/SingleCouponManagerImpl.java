package com.kingleadsw.betterlive.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.service.SingleCouponService;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
/**
 * 单品活动数据交互层
 * @author zhangjing
 *
 * @date 2017年5月9日
 */
@Component
@Transactional
public class SingleCouponManagerImpl extends BaseManagerImpl<SingleCouponVo, SingleCoupon> implements SingleCouponManager{
	@Autowired
	private SingleCouponService singleCouponService;
	
	
	@Override
	public SingleCouponVo selectByPrimaryKey(Integer id) {
		SingleCoupon singleCoupon = singleCouponService.selectByPrimaryKey(id);
		SingleCouponVo singleCouponVo = po2voer.transfer(new SingleCouponVo(), singleCoupon);
		List<SingleCouponSpecVo> list = new ArrayList<SingleCouponSpecVo>();
		if(singleCoupon != null && singleCoupon.getListSpec()!=null&&singleCoupon.getListSpec().size()>0){
			for (SingleCouponSpec singleCouponSpec : singleCoupon.getListSpec()) {
				SingleCouponSpecVo scvo = new SingleCouponSpecVo();
				scvo.setCouponId(singleCouponSpec.getCouponId());
				scvo.setCouponSpecId(singleCouponSpec.getCouponSpecId());
				scvo.setProductId(singleCouponSpec.getProductId());
				scvo.setProductName(singleCouponSpec.getProductName());
				scvo.setSpecId(singleCouponSpec.getSpecId());
				scvo.setSpecName(singleCouponSpec.getSpecName());
				scvo.setLinkUrl(singleCouponSpec.getLinkUrl());
				list.add(scvo);
			}
			singleCouponVo.setListSpec(list);
		}
		return singleCouponVo;
	}






	@Override
	protected BaseService<SingleCoupon> getService() {
		return singleCouponService;
	}






	@Override
	public List<SingleCouponVo> queryEffectiveCouponList(PageData couponParam) {
		return po2voer.transfer(SingleCouponVo.class,singleCouponService.queryEffectiveCouponList(couponParam));
	}






	@Override
	public List<SingleCouponVo> queryEffectiveCouponListNew(PageData couponParam) {
		return po2voer.transfer(SingleCouponVo.class,singleCouponService.queryEffectiveCouponListNew(couponParam));
	}

}
