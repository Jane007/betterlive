package com.kingleadsw.betterlive.biz.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.model.SingleCoupon;
import com.kingleadsw.betterlive.model.SingleCouponSpec;
import com.kingleadsw.betterlive.model.UserSingleCoupon;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.service.CustomerService;
import com.kingleadsw.betterlive.service.MessageService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.service.SingleCouponService;
import com.kingleadsw.betterlive.service.SingleCouponSpecService;
import com.kingleadsw.betterlive.service.UserSingleCouponService;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;
@Component
@Transactional
public class UserSingleCouponManagerImpl extends BaseManagerImpl<UserSingleCouponVo, UserSingleCoupon> implements UserSingleCouponManager{
	@Autowired
	private UserSingleCouponService userSingleCouponService;
	@Autowired
	private SingleCouponService singleCouponService;
	@Autowired
	private ProductSpecService productSpecService;
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MessageService messageService;	
	@Autowired
	private SingleCouponSpecService  singleCouponSpecService;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	protected BaseService<UserSingleCoupon> getService() {
		return userSingleCouponService;
	}
	
	@Override
	public Map<String, Object> insertSingleCoupon(PageData pd) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		String phoneNum = pd.getString("phoneNum");
		String couponId = pd.getString("couponId");
		int customerId = pd.getInteger("customerId");
		String specId = pd.getString("specId");
		if(StringUtils.isBlank(couponId)){//根据红包id查出此红包
			map.put("result", "fail");
			map.put("msg", "非法请求");
			return map;
		}
		SingleCoupon singleCoupon= singleCouponService.selectByPrimaryKey(Integer.parseInt(couponId));
		if(singleCoupon == null){
			map.put("result", "fail");
			map.put("msg", "您来晚了，红包已被抢完了");
			return map;
		}
		
		//限购份数
		PageData upd = new PageData();
		upd.put("couponId",couponId);
		List<UserSingleCoupon> list = userSingleCouponService.queryList(upd);
		if(list!=null&&list.size()>0){
			if(singleCoupon.getLimitCopy()!=null&&singleCoupon.getLimitCopy()!=-1&&list.size()>=singleCoupon.getLimitCopy()){
				map.put("result", "fail");
				map.put("msg", "您来晚了，红包已被抢完了");
				return map;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = sdf.parse(singleCoupon.getEndTime());
		long systime = System.currentTimeMillis();
		if(singleCoupon.getStatus().intValue() == 0
				|| endTime.getTime() <= systime){
			map.put("result", "fail");
			map.put("msg", "您来晚了，红包已被抢完了");
			return map;
		}
		
		pd.put("customer_id", customerId);
		
		if(StringUtils.isBlank(phoneNum)){
			map.put("msg", "领取失败，请输入手机号码");
			map.put("result", "fail");
			return map;
		}
		
		pd.put("mobile", phoneNum);
		//更新用户的手机号码
		customerService.updateCustoemrById(pd);
		UserSingleCoupon usCoupon = userSingleCouponService.queryOne(pd);
		if(usCoupon != null){
			map.put("result", "fail");
			map.put("msg", "您已经领取过了");
			return map;
		}
		pd.put("specStatus", 1);
		ProductSpec pspec = productSpecService.queryProductSpecByOption(pd);
		if(pspec == null){
			map.put("result", "fail");
			map.put("msg", "您来晚了，红包已被抢完了");
			return map;
		}
		
		usCoupon = new UserSingleCoupon();
		//用户的信息
		usCoupon.setCustomerId(customerId);
		usCoupon.setMobile(phoneNum);
		//券的信息
		usCoupon.setCouponId(singleCoupon.getCouponId());
		usCoupon.setStartTime(singleCoupon.getStartTime());
		usCoupon.setEndTime(singleCoupon.getEndTime());
		usCoupon.setFullMoney(singleCoupon.getFullMoney());
		usCoupon.setCouponMoney(singleCoupon.getCouponMoney());
		usCoupon.setSpecId(Integer.parseInt(specId));
		usCoupon.setProductId(pspec.getProduct_id());
		int count = userSingleCouponService.insert(usCoupon);
		if(count>0){
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
			map.put("result", "success");
			
		}else{
			map.put("msg", "领取失败");
			map.put("result", "fail");
		}
		return map;
	}

	
	private Map<String, Object> insertUserCoupons(PageData pd,SingleCoupon singleCoupon){
		List<SingleCouponSpec> listSpec = singleCouponSpecService.queryList(pd);
		if(listSpec == null || listSpec.size() <= 0){
			return CallBackConstant.FAILED.callbackError("您来晚了，红包已被抢完了");
		}
		
		int count = 0;
		for (SingleCouponSpec singleCouponSpec : listSpec) {
			pd.put("specId", singleCouponSpec.getSpecId());
			UserSingleCoupon usCoupon = this.userSingleCouponService.queryOne(pd);
			if(usCoupon != null){
				count++;
				continue;
			}
			usCoupon = new UserSingleCoupon();
			//用户的信息
			usCoupon.setCustomerId(pd.getInteger("customerId"));
			usCoupon.setMobile(pd.getString("phoneNum"));
			//券的信息
			usCoupon.setCouponId(singleCoupon.getCouponId());
			usCoupon.setStartTime(singleCoupon.getStartTime());
			usCoupon.setEndTime(singleCoupon.getEndTime());
			usCoupon.setFullMoney(singleCoupon.getFullMoney());
			usCoupon.setCouponMoney(singleCoupon.getCouponMoney());
			usCoupon.setSpecId(singleCouponSpec.getSpecId());
			usCoupon.setProductId(singleCouponSpec.getProductId());
			count += userSingleCouponService.insert(usCoupon);
			//发系统消息给用户
			Message msgVo = new Message();
			msgVo.setMsgType(MessageVo.MSGTYPE_COUPON);
			msgVo.setSubMsgType(2);
			msgVo.setMsgTitle("恭喜您获得了一个单品红包");
			msgVo.setMsgDetail(singleCoupon.getCouponContent());
			msgVo.setIsRead(0);
			msgVo.setCustomerId(pd.getInteger("customerId"));
			msgVo.setObjId(usCoupon.getUserSingleId());
			messageService.insert(msgVo);
		}
		if(count > 0){
			return CallBackConstant.SUCCESS.callBackByMsg("领取成功");
		}
		Map<String, Object> map = CallBackConstant.FAILED.callbackError("您已经领取过了");
		map.put("hasFlag", 1);
		return map;
	}
	
	
	
	@Override
	public List<UserSingleCouponVo> queryExpiringList(PageData pd) {
		return po2voer.transfer(UserSingleCouponVo.class,userSingleCouponService.queryExpiringList(pd));	
	}

	@Override
	public Map<String, Object> insertSingleCouponByProductId(PageData pd)
			throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		String phoneNum = pd.getString("phoneNum");
		Integer couponId = pd.getInteger("couponId");
		Integer customerId = pd.getInteger("customerId");
		Integer productId = pd.getInteger("productId");
		
		
		if(couponId == null || couponId == 0){//根据红包id查出此红包
			return CallBackConstant.PARAMETER_ERROR.callbackError("非法请求");
		}
		if(customerId == null || customerId==0){
			return CallBackConstant.PARAMETER_ERROR.callbackError("您还没有登录，请先登录");
		}
		if(productId == null || productId==0){
			return CallBackConstant.PARAMETER_ERROR.callbackError("非法请求");
		}
		
		SingleCoupon singleCoupon= singleCouponService.selectByPrimaryKey(couponId);
		if(singleCoupon == null){
			return CallBackConstant.DATA_NOT_FOUND.callbackError("您来晚了，红包已被抢完了");
		}
		
		
		//限购份数
		PageData upd = new PageData();
		upd.put("couponId",couponId);
		List<UserSingleCoupon> list = userSingleCouponService.queryList(upd);
		if(list!=null&&list.size()>0){
			if(singleCoupon.getLimitCopy()!=null&&singleCoupon.getLimitCopy()!=-1&&list.size()>=singleCoupon.getLimitCopy()){
				return CallBackConstant.FAILED.callbackError("您来晚了，红包已被抢完了");
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = sdf.parse(singleCoupon.getEndTime());
		long systime = System.currentTimeMillis();
		if(singleCoupon.getStatus().intValue() == 0
				|| endTime.getTime() <= systime){
			return CallBackConstant.DATA_TIME_TOU.callbackError("您来晚了，红包已被抢完了");
		}
		
		pd.put("customer_id", customerId);
		
		if(StringUtils.isNotBlank(phoneNum)){
			pd.put("mobile", phoneNum);
			//更新用户的手机号码
			customerService.updateCustoemrById(pd);
			
		}
		map = insertUserCoupons(pd,singleCoupon);
		return map;
	}

	@Override
	public Map<String, Object> CheckPhoneNoCode(PageData pd) {
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");
		if (StringUtil.isNull(phoneNum)) { //判断是否有填写手机号码提交
			return CallBackConstant.FAILED.callbackError("手机号码不能为空");
		}
		
		if (StringUtil.isNull(checkCode)) {
			return CallBackConstant.FAILED.callbackError("验证码不能为空");
		}
		
		if (!checkCode.equals(redisService.getString(phoneNum))) {
			return CallBackConstant.FAILED.callbackError("验证码错误");
		}
		return CallBackConstant.SUCCESS.callback();
	}
	
	
	
}
