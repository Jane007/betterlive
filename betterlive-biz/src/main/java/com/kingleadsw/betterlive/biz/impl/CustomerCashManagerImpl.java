package com.kingleadsw.betterlive.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerCashManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.model.CustomerCash;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.model.SysCash;
import com.kingleadsw.betterlive.model.UserSingleCoupon;
import com.kingleadsw.betterlive.service.CustomerCashService;
import com.kingleadsw.betterlive.service.SingleCouponService;
import com.kingleadsw.betterlive.service.SingleCouponSpecService;
import com.kingleadsw.betterlive.service.SysCashService;
import com.kingleadsw.betterlive.service.UserSingleCouponService;
import com.kingleadsw.betterlive.vo.CustomerCashVo;

@Component
@Transactional
public class CustomerCashManagerImpl extends BaseManagerImpl<CustomerCashVo,CustomerCash> implements CustomerCashManager {

	@Autowired
	private CustomerCashService customerCashService;
	@Autowired
	private SysCashService sysCashService;
	@Autowired
	private SingleCouponSpecService singleCouponSpecService;
	@Autowired
	private SingleCouponService singleCouponService;
	@Autowired
	private UserSingleCouponService userSingleCouponService;
	
	@Override
	protected BaseService<CustomerCash> getService() {
		return customerCashService;
	}
	
	/**
	 * 兑换礼物
	 */
	@Override
	public Map<String, Object> getCashGift(PageData pd) {
		PageData cpd = new PageData();
		cpd.put("codeValue", pd.getString("codeValue"));
		CustomerCash cash = this.customerCashService.queryOne(cpd);
		if(cash == null || cash.getRecordId() <= 0){
			return CallBackConstant.FAILED.callbackError("请输入正确的兑换码");
		}
		cpd.clear();
		//兑换不限时
//		cpd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
//		cpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		cpd.put("status", 0);
		cpd.put("sysId", cash.getSysId());
		SysCash sysCash = sysCashService.queryOne(cpd);
		if(sysCash == null){
			return CallBackConstant.FAILED.callbackError("兑换码已过期");
		}
		
		int myCustId = pd.getInteger("customerId");
		if(cash.getStatus() != 0){
			if(cash.getCustomerId() != myCustId){
				return CallBackConstant.FAILED.callbackError("请输入正确的兑换码");
			}
			return CallBackConstant.DATA_HAD_FOUND.callback("您已兑换过该礼品，可直接购买", sysCash.getProductId()+"");
		}
		
		if(sysCash.getBandType() == 2){
			return getSingleCoupon(cash, myCustId, pd.getString("mobile"));
		}
		return CallBackConstant.FAILED.callbackError("请输入正确的兑换码");
	}
	
	/**
	 * 兑换单品红包
	 * @param cash
	 * @param customerId
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> getSingleCoupon(CustomerCash cash, int customerId, String mobile) {
		PageData cpd = new PageData();
		cpd.put("couponSpecIds", cash.getBandCoupon());
		List<SingleCouponSpec> list = this.singleCouponSpecService.queryList(cpd);
		if(list == null || list.size() <= 0){
			return CallBackConstant.FAILED.callbackError("活动已过期");
		}
		int couponId = list.get(0).getCouponId();
		int productId = list.get(0).getProductId();
		cpd.clear();
		cpd.put("couponId", couponId);
		cpd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		cpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		List<SingleCoupon> scs = this.singleCouponService.queryList(cpd);
		if(scs == null || scs.size() <= 0){
			return CallBackConstant.FAILED.callbackError("活动已过期");
		}
		
		SingleCoupon sc = scs.get(0);
		
		cash.setCustomerId(customerId);
		cash.setStatus(1);
		cash.setReceiveTime(new Date());
		int count = this.customerCashService.updateByPrimaryKey(cash);
		if(count > 0){
			for (SingleCouponSpec singleCouponSpec : list) {
				UserSingleCoupon usc = new UserSingleCoupon();
				usc.setCouponId(couponId);
				usc.setFullMoney(sc.getFullMoney());
				usc.setCouponMoney(sc.getCouponMoney());
				usc.setCustomerId(cash.getCustomerId());
				usc.setProductId(singleCouponSpec.getProductId());
				usc.setSpecId(singleCouponSpec.getSpecId());
				usc.setMobile(mobile);
				usc.setStartTime(sc.getStartTime());
				usc.setEndTime(sc.getEndTime());
				this.userSingleCouponService.insert(usc);
			}
			return CallBackConstant.SUCCESS.callback(productId);
		}
		return CallBackConstant.FAILED.callback();
	}

}
