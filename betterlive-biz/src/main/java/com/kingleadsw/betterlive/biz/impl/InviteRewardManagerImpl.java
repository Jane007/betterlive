package com.kingleadsw.betterlive.biz.impl;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.InviteRewardManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.model.CouponManager;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.InviteRecord;
import com.kingleadsw.betterlive.model.InviteReward;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.model.SysInvite;
import com.kingleadsw.betterlive.model.UserSingleCoupon;
import com.kingleadsw.betterlive.service.CouponInfoService;
import com.kingleadsw.betterlive.service.CouponManagerService;
import com.kingleadsw.betterlive.service.CustomerService;
import com.kingleadsw.betterlive.service.InviteRecordService;
import com.kingleadsw.betterlive.service.InviteRewardService;
import com.kingleadsw.betterlive.service.MessageService;
import com.kingleadsw.betterlive.service.OrderService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.service.SingleCouponService;
import com.kingleadsw.betterlive.service.SingleCouponSpecService;
import com.kingleadsw.betterlive.service.SysInviteService;
import com.kingleadsw.betterlive.service.UserSingleCouponService;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.InviteRewardVo;
import com.kingleadsw.betterlive.vo.MessageVo;

/**
 * 系统邀请好友信息 实际交互实现层
 */
@Component
@Transactional
public class InviteRewardManagerImpl extends BaseManagerImpl<InviteRewardVo,InviteReward> implements InviteRewardManager{
	protected Logger logger = Logger.getLogger(InviteRewardManagerImpl.class);
	
	@Autowired
	private InviteRewardService inviteRewardService;
	
	@Autowired
	private InviteRecordService inviteRecordService;
	
	@Autowired
	private SysInviteService sysInviteService;
	
	@Autowired
	private CouponManagerService couponManagerService;
	
	@Autowired
	private CouponInfoService couponInfoService;
	
	@Autowired
	private SingleCouponService singleCouponService;
	
	@Autowired
	private UserSingleCouponService userSingleCouponService;
	
	@Autowired
	private SingleCouponSpecService singleCouponSpecService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;

	@Override
	protected BaseService<InviteReward> getService() {
		return inviteRewardService;
	}

	@Override
	public List<InviteRewardVo> queryInviteRankingList(PageData pd) {
		return generator.transfer(InviteRewardVo.class,inviteRewardService.queryInviteRankingList(pd));
	}

	@Override
	public Map<String, Object> checkFirstInvite(CustomerVo customerVo, int recordId) {
		InviteRecord inviteRecord = inviteRecordService.selectByPrimaryKey(recordId);
		if(inviteRecord == null){
			return CallBackConstant.FAILED.callbackError("哎呀，分享失败啦");
		}
		
		//分享成功
		PageData pd = new PageData();
		pd.put("recordId", inviteRecord.getRecordId());
		pd.put("inviteFlag", 1);
		inviteRecordService.updateRecordByParam(pd);
		
		int count = inviteRecordService.checkInviteByDay(customerVo.getCustomer_id());
		Map<String, Object> result = new HashMap<String, Object>();
		if(count == 1){	//每天的首次分享获取券
		    //每日首次分享获券
			result = insertUserCouponByShare(customerVo);
			return result;
		}else{
			result.put("hasFirstGift", 0); //没有券
			return CallBackConstant.SUCCESS.callback(result);
		}
	}
	
	/**
	 * 每日首次分享获取券
	 * @param customerVo
	 * @return
	 */
	private Map<String, Object> insertUserCouponByShare(CustomerVo customerVo){
		PageData pd = new PageData();
		pd.put("dictType", 3);
		pd.put("objValue", 0);
	    List<SysInvite> minCoupons = sysInviteService.queryBySortListPage(pd);
	    if(minCoupons == null){
	    	return CallBackConstant.FAILED.callbackError("活动信息异常");
	    }
		pd.put("objValue", 1);
	    List<SysInvite> highCoupons = sysInviteService.queryBySortListPage(pd);
	    if(highCoupons == null){
	    	return CallBackConstant.FAILED.callbackError("活动信息异常");
	    }
	    Random random= new Random();// 定义随机类
	    int rateNum=random.nextInt(10);// 返回(0,10)集合中的整数，注意不包括10
	    
	    int couponId = 0;
	    SysInvite checkCoupon = null;
	    if(rateNum < 9){ //9成概率, result下标从0算起
	    	rateNum=random.nextInt(minCoupons.size());// 返回(0,3)集合中的整数，注意不包括3
	    	checkCoupon = minCoupons.get(rateNum);
	    }else{	//1成概率
	    	rateNum=random.nextInt(highCoupons.size())+minCoupons.size();// 返回(3,7)集合中的整数，包括7
	    	checkCoupon = highCoupons.get(rateNum);
	    }
	    if(checkCoupon == null){
    		return CallBackConstant.FAILED.callbackError("活动信息异常");
    	}
	    couponId = checkCoupon.getObjId();
	    pd.clear();
	    pd.put("cmId", couponId);
	    CouponManager couponManager = couponManagerService.findCouponManager(pd);
	    if(couponManager == null){
	    	return CallBackConstant.FAILED.callbackError("活动信息异常");
	    }
	    
		BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
		BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
		
		String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, Integer.parseInt(couponManager.getUsemax_date()));
		
		String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
		
		CouponInfo couponInfoVo=new CouponInfo();
		couponInfoVo.setMobile(customerVo.getMobile());
		couponInfoVo.setCustomer_id(customerVo.getCustomer_id());
		couponInfoVo.setCm_id(couponManager.getCm_id());
		couponInfoVo.setCoupon_money(couponMoney.intValue());
		couponInfoVo.setStarttime(currentDate);
		couponInfoVo.setEndtime(endDate);
		couponInfoVo.setStart_money(useminMoney.intValue());
		couponInfoVo.setCoupon_from(2);
		couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
		couponInfoVo.setFrom_user_id(1); //默认管理员
		
		int result = couponInfoService.insertUserCoupon(couponInfoVo);
		if(result > 0){
			InviteReward irVo = new InviteReward();
			irVo.setCustomerId(customerVo.getCustomer_id());
			irVo.setRewardType(1);
			irVo.setObjId(couponInfoVo.getCoupon_id());
			inviteRewardService.insert(irVo);
			
			//发系统消息给用户
			Message msgVo = new Message();
			msgVo.setMsgType(2);
			msgVo.setSubMsgType(1);
			msgVo.setMsgTitle("恭喜您获得了一张优惠券");
			String couponContent = "您参与[每日首次分享注册活动]获取了一张"+couponMoney+"元优惠券";
			msgVo.setMsgDetail(couponContent);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(customerVo.getCustomer_id());
			msgVo.setObjId(couponInfoVo.getCoupon_id());
			messageService.insert(msgVo);
		}
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("giftDesc", "成功获得"+couponMoney.intValue()+"元优惠券");
		datas.put("useRuleDesc", "挥货商城全场通用");
		datas.put("searchAddressDesc", "可到我的-优惠券里查看");
		datas.put("hasFirstGift", 1); //获得券
		return CallBackConstant.SUCCESS.callback(datas);
	}

	@Override
	public Map<String, Object> receiveCoupon(int customerId, String mobile, int couponId, int recordId) {
		//新用户领券
		PageData sgpd = new PageData();
		sgpd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		sgpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		sgpd.put("couponId", couponId);
		List<SingleCoupon> scs = this.singleCouponService.queryList(sgpd);
		if(scs == null || scs.size() <= 0){
			return CallBackConstant.FAILED.callbackError("活动已结束");
		}
		SingleCoupon singleCoupon = scs.get(0);
//		List<SingleCouponSpec> sces = this.singleCouponSpecService.queryList(sgpd);
//		if(sces == null || sces.size() <= 0){
//			return CallBackConstant.FAILED.callbackError("活动已结束");
//		}
		
		int hasCount = 0;
		List<SingleCouponSpec> sces = singleCoupon.getListSpec();
		PageData pdps = new PageData();
		for (SingleCouponSpec singleCouponSpec : sces) {
			pdps.put("productId", singleCouponSpec.getProductId());
			pdps.put("specId", singleCouponSpec.getSpecId());
			pdps.put("specStatus", 1);
			ProductSpec pspec = productSpecService.queryProductSpecByOption(pdps);
			if(pspec == null){
				continue;
			}
			UserSingleCoupon usCoupon = new UserSingleCoupon();
			//用户的信息
			usCoupon.setCustomerId(customerId);
			usCoupon.setMobile(mobile);
			//券的信息
			usCoupon.setCouponId(singleCoupon.getCouponId());
			usCoupon.setStartTime(singleCoupon.getStartTime());
			usCoupon.setEndTime(singleCoupon.getEndTime());
			usCoupon.setFullMoney(singleCoupon.getFullMoney());
			usCoupon.setCouponMoney(singleCoupon.getCouponMoney());
			usCoupon.setSpecId(singleCouponSpec.getSpecId());
			usCoupon.setProductId(singleCouponSpec.getProductId());
			int count = userSingleCouponService.insert(usCoupon);
			if(count>0){
				InviteReward irVo = new InviteReward();
				irVo.setCustomerId(customerId);
				irVo.setRewardType(3);
				irVo.setObjId(usCoupon.getUserSingleId());
				inviteRewardService.insert(irVo);
				
				//发系统消息给用户
				Message msgVo = new Message();
				msgVo.setMsgType(MessageVo.MSGTYPE_COUPON);
				msgVo.setSubMsgType(2);
				msgVo.setMsgTitle("恭喜您获得了一个单品红包");
				msgVo.setMsgDetail(singleCoupon.getCouponContent());
				msgVo.setIsRead(0);
				msgVo.setCustomerId(customerId);
				msgVo.setObjId(usCoupon.getUserSingleId());
				messageService.insert(msgVo);			
			}
			hasCount++;
		}
		if(hasCount > 0){
			return CallBackConstant.SUCCESS.callBackByMsg("领取成功");
		}
		return CallBackConstant.FAILED.callBackByMsg("活动已结束");
	}

	@Override
	public int giveInviterReward(int recordId, int customerId) {
		try {
			PageData pd = new PageData();
			pd.put("recordId", recordId);
			InviteRecord ir = inviteRecordService.queryOne(pd);
			if(ir == null){
				return 0;
			}
			
			int inviteId = ir.getInviteId();
			int inviteReasonId = ir.getInviteReasonId();
			if(ir.getInviteFlag() != 2){	//如果该链接已成功邀请走新增分享记录
				pd.put("inviteFlag", 2);
				pd.put("customerId", customerId);
				int count = inviteRecordService.updateRecordByParam(pd);
				if(count == 0){
					return 0;
				}
			}else{
				ir = new InviteRecord();
				ir.setInviteId(inviteId);
				ir.setCustomerId(customerId);
				ir.setInviteReasonId(inviteReasonId);
				ir.setInviteFlag(2);
				int count = inviteRecordService.insert(ir);
				if(count <= 0){
					return 0;
				}
			}
			List<InviteRecord> irList = inviteRecordService.queryInviteRecordListByFinish(ir.getInviteId());
			if(irList == null || irList.size() <= 0){
				return 0;
			}
			
			pd.clear();
			pd.put("dictType", 4);
			pd.put("objValue", irList.size());
			SysInvite checkCoupon = this.sysInviteService.queryOne(pd);
			if(checkCoupon == null){
				return 0;
			}
			
		    int couponId = checkCoupon.getObjId();
		    pd.clear();
		    pd.put("cmId", couponId);
		    CouponManager couponManager = couponManagerService.findCouponManager(pd);
		    if(couponManager == null){
		    	return 0;
		    }
		    Customer inviter = this.customerService.selectByPrimaryKey(ir.getInviteId());
		    if(inviter == null){
		    	return 0;
		    }
		    
			BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
			BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
			
			String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
			
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, Integer.parseInt(couponManager.getUsemax_date()));
			
			String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
			
			CouponInfo couponInfoVo=new CouponInfo();
			couponInfoVo.setMobile(inviter.getMobile());
			couponInfoVo.setCustomer_id(inviter.getCustomer_id());
			couponInfoVo.setCm_id(couponManager.getCm_id());
			couponInfoVo.setCoupon_money(couponMoney.intValue());
			couponInfoVo.setStarttime(currentDate);
			couponInfoVo.setEndtime(endDate);
			couponInfoVo.setStart_money(useminMoney.intValue());
			couponInfoVo.setCoupon_from(2);
			couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
			couponInfoVo.setFrom_user_id(1); //默认管理员
			
			int result = couponInfoService.insertUserCoupon(couponInfoVo);
			if(result <= 0){
				return 0;
			}
			
			InviteReward irVo = new InviteReward();
			irVo.setCustomerId(ir.getInviteId());
			irVo.setRewardType(2);
			irVo.setObjId(couponInfoVo.getCoupon_id());
			inviteRewardService.insert(irVo);
			
			//发系统消息给用户
			Message msgVo = new Message();
			msgVo.setMsgType(2);
			msgVo.setSubMsgType(1);
			msgVo.setMsgTitle("恭喜您获得了一张优惠券");
			String couponContent = "您参与[邀请注册活动]获取了一张"+couponMoney+"元优惠券";
			msgVo.setMsgDetail(couponContent);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(inviter.getCustomer_id());
			msgVo.setObjId(couponInfoVo.getCoupon_id());
			messageService.insert(msgVo);
		} catch (Exception e) {
			logger.error("/giveInviterReward --error", e);
		}
		return 1;
	}

}
