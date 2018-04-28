package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.service.CouponInfoService;
import com.kingleadsw.betterlive.service.CouponManagerService;
import com.kingleadsw.betterlive.service.MessageService;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * 红包  实际交互实现层
 * 2017-03-08 by chen
 */
@Component
@Transactional
public class CouponInfoManagerImpl extends BaseManagerImpl<CouponInfoVo,CouponInfo> implements CouponInfoManager{
	
	private static Logger logger = Logger.getLogger(CouponInfoManagerImpl.class);
	
	@Autowired
	private CouponInfoService couponInfoService;
	
	@Autowired
	private CouponManagerService couponManagerService;
	@Autowired
	private MessageService messageService;	
	
	//增加用户领取优惠券
	@Override
	public int insertUserCoupon(CouponInfoVo couponInfoVo) {
		CouponInfo couponInfo= vo2poer.transfer(new CouponInfo(), couponInfoVo);
		int count = couponInfoService.insertUserCoupon(couponInfo);
		if(count > 0){
			couponInfoVo.setCoupon_id(couponInfo.getCoupon_id());
		}
		return count;
	}
	
	//修改用户领取优惠券
	@Override
	public int updateUserCouponByCid(PageData pd) {
		return couponInfoService.updateUserCouponByCid(pd);
	}
	
	 //删除用户领取优惠券 
	@Override
	public int deleteByCid(String cId) {
		return couponInfoService.deleteByCid(cId);
	}

	//根据条件分页查询用户领取优惠券
	@Override
	public List<CouponInfoVo> findUserCouponListPage(PageData pd) {
		return po2voer.transfer(CouponInfoVo.class,couponInfoService.findUserCouponListPage(pd));
	}
	
	
	
	 //根据条件查询全部用户领取优惠券
	@Override
	public List<CouponInfoVo> findListUserCoupon(PageData pd) {
		return po2voer.transfer(CouponInfoVo.class,couponInfoService.findListUserCoupon(pd));
	}
	
	 //根据条件查询单个用户领取优惠券详细
	@Override
	public CouponInfoVo findUserCoupon(PageData pd) {
		return po2voer.transfer( new CouponInfoVo(),couponInfoService.findUserCoupon(pd));
	}

	
	@Override
	protected BaseService<CouponInfo> getService() {
		return couponInfoService;
	}

	
	 //根据订单ID查询分享券（优惠券）领取
	@Override
	public int findShareCountByOrderId(int orderId) {
		return couponInfoService.findShareCountByOrderId(orderId);
	}

	
	/**
	 * 根据手机 ，用户ID或者 订单ID   查询用户是否已经领取过红包
	 */
	@Override
	public int findUserShareCount(PageData pd) {
		return couponInfoService.findUserShareCount(pd);
	}

	@Override
	public List<CouponInfoVo> findCouponListPage(PageData pd) {
		return po2voer.transfer(CouponInfoVo.class,couponInfoService.findCouponListPage(pd));
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.biz.CouponInfoManager#insertRegisterCoupon(com.kingleadsw.betterlive.model.Customer)
	 */
	@Override
	public int insertRegisterCoupon(CustomerVo customerVo) {
		Customer customer = new Customer();
		customer.setCustomer_id(customerVo.getCustomer_id());
		customer.setMobile(customerVo.getMobile());
		return couponInfoService.insertRegisterCoupon(customer);
	}

	@Override
	public List<CouponInfoVo> queryExpiringList(PageData pd) {
		return po2voer.transfer(CouponInfoVo.class,couponInfoService.queryExpiringList(pd));
	}

	@Override
	public int batchInsertCouponInfo(PageData pd) {
		String cmIds = pd.getString("cmIds");
		int count=0;
		if(StringUtil.isNull(cmIds)){
			return count;
		}
		try {
			
			String[] youhuiids = cmIds.split(",");
			for (int i = 0; i < youhuiids.length; i++) {
				PageData pd1 = new PageData();
				pd1.put("cmId", youhuiids[i]);
				pd1.put("customerId", pd.getInteger("customerId"));
				CouponManager couponManager = couponManagerService.findCouponManager(pd1);
				if(couponManager == null){
					continue;
				}
				List<CouponInfo> cvo = couponInfoService.findListUserCoupon(pd1);
				if(cvo != null && cvo.size()>0){
					continue;
				}
				String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
				String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
				String title = "恭喜您获得了一张优惠券";
				CouponInfoVo couponInfoVo=new CouponInfoVo();
				
				couponInfoVo.setMobile(pd.getString("mobile"));
				couponInfoVo.setCustomer_id(pd.getInteger("customerId"));
				
				BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
				
				couponInfoVo.setCm_id(couponManager.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期'
				couponInfoVo.setFrom_user_id(Integer.parseInt(couponManager.getCreate_by()));
				
				int result = this.insertUserCoupon(couponInfoVo);
				if(result > 0){
					count++;
					//发系统消息给用户
					Message msgVo = new Message();
					msgVo.setMsgType(2);
					msgVo.setSubMsgType(1);
					msgVo.setMsgTitle(title);
					String couponContent = "";
					if(!StringUtil.isEmpty(couponManager.getCoupon_content())){
						couponContent = couponManager.getCoupon_content();
					}
					msgVo.setMsgDetail(couponContent);
					msgVo.setIsRead(0);
					msgVo.setCustomerId(pd.getInteger("customerId"));
					msgVo.setObjId(couponInfoVo.getCoupon_id());
					messageService.insert(msgVo);
				}
					
			}
		} catch (Exception e) {
			logger.error("CouponInfoManagerImpl.batchInsertCouponInfo --error", e);
		}
		return count;
	}



}
