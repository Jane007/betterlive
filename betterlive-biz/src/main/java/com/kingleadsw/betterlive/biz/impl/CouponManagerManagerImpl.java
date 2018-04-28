package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.model.Order;
import com.kingleadsw.betterlive.service.CouponManagerService;
import com.kingleadsw.betterlive.service.OrderService;
import com.kingleadsw.betterlive.vo.CouponManagerVo;

/**
 * 商品  实际交互实现层
 * 2017-03-08 by chen
 */
@Component
@Transactional
public class CouponManagerManagerImpl extends BaseManagerImpl<CouponManagerVo,CouponManager> implements CouponManagerManager{
	@Autowired
	private CouponManagerService couponManagerService;
	@Autowired
	private OrderService orderService;

	
	//增加优惠券管理
	@Override
	public int insertCouponManager(CouponManagerVo couponManagerVo) {
		CouponManager couponManager=vo2poer.transfer(new CouponManager(),couponManagerVo);
		return couponManagerService.insertCouponManager(couponManager);
	}

	//修改优惠券管理
	@Override
	public int updateCouponManagerByCmId(PageData pd) {
		return couponManagerService.updateCouponManagerByCmId(pd);
	}
	
	//删除优惠券 
	@Override
	public int deleteByCmId(String cmId) {
		return couponManagerService.deleteByCmId(cmId);
	}
	
	//根据条件分页查询优惠券管理
	@Override
	public List<CouponManagerVo> findCouponMangerListPage(PageData pd) {
		return   po2voer.transfer(CouponManagerVo.class,couponManagerService.findCouponMangerListPage(pd));
	}

	@Override
	public List<CouponManagerVo> findListCouponManager(PageData pd) {
		return   po2voer.transfer(CouponManagerVo.class,couponManagerService.findListCouponManager(pd));
	}

	@Override
	public CouponManagerVo findCouponManager(PageData pd) {
	
		return   po2voer.transfer(new CouponManagerVo(),couponManagerService.findCouponManager(pd));
	}

	@Override
	protected BaseService<CouponManager> getService() {
		return couponManagerService;
	}

	@Override
	public List<CouponManagerVo> queryEffectiveCouponList(PageData couponParam) {
		return po2voer.transfer(CouponManagerVo.class,couponManagerService.queryEffectiveCouponList(couponParam));
	}

	@Override
	public List<CouponManagerVo> queryNewUserCouponList(PageData couponParam) {
		couponParam.put("couponType", 2);
		String couponIds = couponParam.getString("couponIds");
		int customerId=0;
		if(couponParam.getInteger("customerId")!=null){
			customerId=couponParam.getInteger("customerId");
		}
		if(StringUtils.isNotBlank(couponIds)){
			String[] ids = couponIds.split(",");
			couponParam.put("cmIds", ids);
			
			//查出用户的历史订单
			List<Order> orderVos =orderService.findHistoryOrderByCustomerId(customerId);
			 if(orderVos==null || orderVos.size()==0){
				return po2voer.transfer(CouponManagerVo.class,couponManagerService.queryNewUserCouponList(couponParam));
			 }
		}
		return null;
	}

}
