package com.kingleadsw.betterlive.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;


@Component("messageJob")
public class MessageJob {
	
	private static final Logger logger=Logger.getLogger(MessageJob.class);
	
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private MessageManager messageManager;
	
	public void execute(){
		try{
			PageData pd=new PageData();
			List<UserSingleCouponVo> singleCouponList = userSingleCouponManager.queryExpiringList(pd);
			
			//优惠券即将过期提醒
			List<CouponInfoVo> couponList = couponInfoManager.queryExpiringList(pd);
			if(couponList != null && couponList.size() > 0){
				PageData cppd = new PageData();
				cppd.put("msgType", 2);
				cppd.put("subMsgType", 3);
				for (CouponInfoVo couponInfoVo : couponList) {
					String title = "您有一张优惠券即将过期";
					if(couponInfoVo.getCoupon_type() == 3){
						title = "您有一个红包即将过期";
					}
					cppd.put("objId", couponInfoVo.getCoupon_id());
					cppd.put("customerId", couponInfoVo.getCustomer_id());
					MessageVo check = messageManager.queryOne(cppd);
					if(check != null && check.getMsgId() > 0){
						continue;
					}
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(MessageVo.MSGTYPE_COUPON);
					msgVo.setSubMsgType(3);
					msgVo.setMsgTitle(title);
					msgVo.setMsgDetail("["+couponInfoVo.getCoupon_name()+"]"+"即将过期，小主赶快使用哦");
					msgVo.setIsRead(0);
					msgVo.setCustomerId(couponInfoVo.getCustomer_id());
					msgVo.setObjId(couponInfoVo.getCoupon_id());
					messageManager.insert(msgVo);
				}
			}
			
			//红包即将过期提醒
			if(singleCouponList != null && singleCouponList.size() > 0){
				PageData cppd = new PageData();
				cppd.put("msgType", 2);
				cppd.put("subMsgType", 4);
				for (UserSingleCouponVo userSingleCouponVo : singleCouponList) {
					
					cppd.put("objId", userSingleCouponVo.getUserSingleId());
					cppd.put("customerId", userSingleCouponVo.getCustomerId());
					MessageVo check = messageManager.queryOne(cppd);
					if(check != null && check.getMsgId() > 0){
						continue;
					}
					
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(MessageVo.MSGTYPE_COUPON);
					msgVo.setSubMsgType(4);
					msgVo.setMsgTitle("您有一个红包即将过期");
					msgVo.setMsgDetail("["+userSingleCouponVo.getCouponName()+"]"+"即将过期，小主赶快使用哦");
					msgVo.setIsRead(0);
					msgVo.setCustomerId(userSingleCouponVo.getCustomerId());
					msgVo.setObjId(userSingleCouponVo.getUserSingleId());
					messageManager.insert(msgVo);
				}
			}
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
	}

}
