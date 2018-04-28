package com.kingleadsw.betterlive.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.dao.CouponInfoMapper;
import com.kingleadsw.betterlive.dao.CouponManagerMapper;
import com.kingleadsw.betterlive.dao.MessageMapper;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.service.CouponInfoService;


/**
 * 用户 优惠券  service 实现层
 * 2017-03-14 by chen
 */
@Service
public class CouponInfoServiceImpl extends BaseServiceImpl<CouponInfo>  implements CouponInfoService{
	private static Logger logger = Logger.getLogger(CouponInfoServiceImpl.class);
	
	@Autowired
	private CouponInfoMapper couponInfoMapper;
	
	@Autowired
	private CouponManagerMapper couponManagerMapper;
	
	@Autowired
	private MessageMapper messageMapper;	

	//增加用户领取优惠券
	@Override
	public int insertUserCoupon(CouponInfo couponInfo) {
		return couponInfoMapper.insertUserCoupon(couponInfo);
	}
	
	//修改用户领取优惠券
	@Override
	public int updateUserCouponByCid(PageData pd) {
		return couponInfoMapper.updateUserCouponByCid(pd);
	}
	
	//删除用户领取优惠券 
	@Override
	public int deleteByCid(String cId) {
		return couponInfoMapper.deleteByCid(cId);
	}
	
	//根据条件分页查询用户领取优惠券
	@Override
	public List<CouponInfo> findUserCouponListPage(PageData pd) {
		return couponInfoMapper.findUserCouponListPage(pd);
	}
	
	 //根据条件查询全部用户领取优惠券
	@Override
	public List<CouponInfo> findListUserCoupon(PageData pd) {
		return couponInfoMapper.findListUserCoupon(pd);
	}
	
	//根据条件查询单个用户领取优惠券详细
	@Override
	public CouponInfo findUserCoupon(PageData pd) {
		return couponInfoMapper.findUserCoupon(pd);
	}

	@Override
	protected BaseMapper<CouponInfo> getBaseMapper() {
		return couponInfoMapper;
	}

	
	 //根据订单ID查询分享券（优惠券）领取
	@Override
	public int findShareCountByOrderId(int orderId) {
		return couponInfoMapper.findShareCountByOrderId(orderId);
	}
	
	
	 //根据手机 ，用户ID， 订单ID   查询用户是否已经领取过红包
	@Override
	public int findUserShareCount(PageData pd) {
		return couponInfoMapper.findUserShareCount(pd);
	}

	

	/**
     * 合并用户时,需要合并用户的优惠券
     * @param pd
     * @return
     */
	@Override
	public int modifyCustomerCouponBycId(PageData pd) {
		return couponInfoMapper.modifyCustomerCouponBycId(pd);
	}
	

	@Override
	public List<CouponInfo> findCouponListPage(PageData pd) {
		return couponInfoMapper.findCouponListPage(pd);
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.CouponInfoService#insertRegisterCoupon(com.kingleadsw.betterlive.model.Customer)
	 */
	@Override
	public int insertRegisterCoupon(Customer customer) {
		logger.info("用户绑定手机号后，发放新手券开始");
		logger.info("用户信息，用户id：" + customer.getCustomer_id() + "，手机号码：" + customer.getMobile());
		int result = 0;
		//查询新手红包的设置信息
		PageData pd = new PageData();
		pd.put("couponType", 3);  //查询分享券
		CouponManager couponManager = couponManagerMapper.findCouponManager(pd);
		if (couponManager == null) {
			logger.error("系统没有设置新手券的相关信息");
			return result;
		}
		
		CouponInfo couponInfo = new CouponInfo();
		couponInfo.setCm_id(couponManager.getCm_id());
		couponInfo.setMobile(customer.getMobile());
		couponInfo.setCustomer_id(customer.getCustomer_id());
		BigDecimal couponMoney = new BigDecimal(couponManager.getCoupon_money());
		couponInfo.setCoupon_money(couponMoney.intValue());
		couponInfo.setStarttime(DateUtil.getCurrentDate());
		int expireDay = Integer.valueOf(couponManager.getUsemax_date());
		String endTime = DateUtil.getDate(DateUtil.dateChange(new Date(), expireDay));
		couponInfo.setEndtime(endTime);
		couponInfo.setStart_money(Integer.valueOf(couponManager.getUsemin_money()));
		couponInfo.setCoupon_from(1);  //注册新人所得
		couponInfo.setStatus(0); //新手红包未使用
		result = couponInfoMapper.insertUserCoupon(couponInfo);
		if(result > 0){
			//发系统消息给用户
			Message msgVo = new Message();
			msgVo.setMsgType(2);
			msgVo.setSubMsgType(1);
			msgVo.setMsgTitle("恭喜您获得了一个新手红包");
			String couponContent = "";
			if(StringUtil.isNotNull(couponManager.getCoupon_content())){
				couponContent = couponManager.getCoupon_content();
			}
			msgVo.setMsgDetail(couponContent);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(customer.getCustomer_id());
			msgVo.setObjId(couponInfo.getCoupon_id());
			messageMapper.insert(msgVo);
		}			
		logger.info("给用户发放优惠券数据库操作影响行：" + result);

		return result;
	}
	
@Override
	public List<CouponInfo> queryExpiringList(PageData pd) {
		return couponInfoMapper.queryExpiringList(pd);
	}

}
