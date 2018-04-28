package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.SystemLevel;
import com.kingleadsw.betterlive.redis.Keys;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.service.CommentService;
import com.kingleadsw.betterlive.service.CouponInfoService;
import com.kingleadsw.betterlive.service.CustomerService;
import com.kingleadsw.betterlive.service.GiftCardRecordService;
import com.kingleadsw.betterlive.service.GiftCardService;
import com.kingleadsw.betterlive.service.OrderService;
import com.kingleadsw.betterlive.service.PayLogService;
import com.kingleadsw.betterlive.service.ReceiverAddressService;
import com.kingleadsw.betterlive.service.ShoppingCartService;
import com.kingleadsw.betterlive.service.SystemLevelService;
import com.kingleadsw.betterlive.service.UseLockService;
import com.kingleadsw.betterlive.vo.CustomerVo;

@Component
@Transactional
public class CustomerManagerImpl extends BaseManagerImpl<CustomerVo,Customer> implements CustomerManager {
	
	private static Logger logger = Logger.getLogger(CustomerManagerImpl.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ReceiverAddressService  receiverAddressService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CouponInfoService couponInfoService;
	@Autowired
	private GiftCardService giftCardService;
	@Autowired
	private GiftCardRecordService giftCardRecordService;
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UseLockService useLockService;
	@Autowired
	private SystemLevelService systemLevelService;
	
	@Autowired
	private RedisService redisServer;
	
	@Override
	protected BaseService<Customer> getService() {
		return customerService;
	}

	@Override
	public List<CustomerVo> getListPage(PageData pd) {
		return po2voer.transfer(CustomerVo.class, customerService.getListPage(pd));
	}
	
	
	//根据条件查询全部的用户
	@Override
	public List<CustomerVo> findListCustomer(PageData pd) {
		return  po2voer.transfer(CustomerVo.class, customerService.findListCustomer(pd));
	}
	
	 //根据条件查询单个用户信息
	@Override
	public CustomerVo findCustomer(PageData pd) {
		return  po2voer.transfer(new CustomerVo(), customerService.findCustomer(pd));
	}

	/**
	 * 根据会员ID更新会员信息
	 * @param pd
	 * @return
	 */
	@Override
	public int updateCustoemrById(PageData pd) {
		return customerService.updateCustoemrById(pd);
	}

	
	//根据条件查询全部的用户
	@Override
	public List<CustomerVo> findListCustomerByObject(PageData pd) {
			return  po2voer.transfer(CustomerVo.class, customerService.findListCustomerByObject(pd));
	}
	
	@Override
	public int delCustomerByCid(String customerId) {
		return customerService.delCustomerByCid(customerId);
	}
	
	/**
	 * 根据用户ID合并用户信息  （只用于 合并 微信与app端的用户）
	 * @param  修改用户数据  pd    修改依赖的其他数据  pds
	 * @return
	 */
	@Override
	public int modifyCustomerByCid(PageData pd, PageData pds) {
		logger.info("进入合并用户信息方法   method-->modifyCustomerByCid   start....  ");
		
		logger.info("修改用户信息    method-->modifyCustomerByCid ....  ");
		
		
		logger.info("修改用户信息....  ");
		
		/*//删除app端的账户信息
		customerService.delCustomerByCid(pds.getInteger("customerId").toString());*/
		
		//更新收货地址
		receiverAddressService.updateReceiverBycId(pds);
		
		/*//订单
		orderService.modifyCustomerOrderBycId(pds);*/
		
		/*//评论
		commentService.modifyCustomerCommentBycId(pds);
		
		//优惠券
		couponInfoService.modifyCustomerCouponBycId(pds);
		
		//礼品卡与使用记录 
		giftCardService.modifyCustomerGiftCardBycId(pds);
		
		giftCardRecordService.modifyCustomerGiftCardRecordBycId(pds);
		
		//支付记录
		payLogService.modifyCustomerPaylogBycId(pds);*/
		
		//购物车
		shoppingCartService.modifyCustomerShopCartBycId(pds);
		
		/*//锁定记录
		useLockService.modifyCustomerUseLockBycId(pds);*/
			
		int result=customerService.updateCustoemrById(pd);
		
		logger.info("进入合并用户信息方法   method-->modifyCustomerByCid   end....  ");
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.biz.CustomerManager#queryCustomerByToken(java.lang.String)
	 */
	@Override
	public CustomerVo queryCustomerByToken(String token) {
		logger.info("通过token获取用户信息开始，用户token：" + token);
		if (StringUtil.isNull(token)) {
			return null;
		}

		String mobile = redisServer.getString(Keys.APP_TOKEN_PREFIX + token);
		if (mobile == null) {
			return null;
		}
		
		CustomerVo customer = redisServer.getObject(Keys.APP_CUSTOMER_PREFIX + mobile);
		if (customer != null && token.equals(customer.getToken())) {
			logger.info("从缓存中获取到用户信息");
			return customer;
		}
		
		//从缓存中没有获取到用户信息，则根据token到数据库查询
		customer = po2voer.transfer(new CustomerVo(), customerService.queryCustomerByToken(token));
		if (customer != null) {
			//TODO:此处需要判断用户的token是否已经过期
			redisServer.setObject(Keys.APP_CUSTOMER_PREFIX + token, customer);
			logger.info("通过token获取用户信息成功，用户id：" + customer.getCustomer_id());
		}
		logger.info("通过token获取用户信息结束");
		return customer;
	}

	@Override
	public int insertCustomer(CustomerVo customerVo) {
		PageData sysLevelParams = new PageData();
		sysLevelParams.put("status", 0);
		sysLevelParams.put("level", 1);
		
		SystemLevel sysLevel = systemLevelService.queryOne(sysLevelParams);
		if(sysLevel != null){
			customerVo.setLevelId(sysLevel.getSystemLevelId());
			customerVo.setLevelName(sysLevel.getLevelName());
		}
		Customer cust = vo2poer.transfer(new Customer(), customerVo);
		int count = customerService.insertSelective(cust);
		customerVo.setCustomer_id(cust.getCustomer_id());
		return count;
	}

	

	@Override
	public CustomerVo selectByMobile(String mobile) {
		return po2voer.transfer(new CustomerVo(), customerService.selectByMobile(mobile));
	}

	@Override
	public Map<String, Object> upgradeCustomerLevel(int customerId, BigDecimal integral) {
		try {
			if (customerId == 0) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息不存在");
			}
			
			Customer cust = customerService.selectByPrimaryKey(customerId);
			
			SystemLevel sysLevel = systemLevelService.selectByPrimaryKey(cust.getLevelId());
			if(sysLevel == null || sysLevel.getStatus().intValue() != 0) {
				  return CallBackConstant.DATA_NOT_FOUND.callbackError("系统等级异常");
			}
			
			BigDecimal current = integral;
			BigDecimal leiji = integral;
			if(cust.getCurrentIntegral() != null){
				current = cust.getCurrentIntegral().add(integral);
			}
			if(cust.getAccumulativeIntegral() != null){
				leiji = cust.getAccumulativeIntegral().add(integral);
			}
			
			
			
			int custLevelId = cust.getLevelId();
			String custLevelName = cust.getLevelName();
			int requirementIntegral = sysLevel.getRequirementIntegral();
			
			if(leiji.compareTo(new BigDecimal(sysLevel.getRequirementIntegral())) >= 0) { 
				//升级
				PageData pd = new PageData();
				pd.put("level", sysLevel.getLevel() + 1);
				pd.put("status", 0);
				SystemLevel nextSysLevel = systemLevelService.queryOne(pd);
				if (nextSysLevel != null) {
					custLevelId = nextSysLevel.getSystemLevelId();
					custLevelName = nextSysLevel.getLevelName();
					requirementIntegral = nextSysLevel.getRequirementIntegral();
				}
			}
			
			cust.setLevelId(custLevelId);
			cust.setLevelName(custLevelName);
			cust.setCurrentIntegral(current);
			cust.setAccumulativeIntegral(leiji);
			int count = customerService.updateByPrimaryKeySelective(cust);
			if(count > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("custLevelName", custLevelName);
				map.put("currentIntegral", BigDecimalUtil.subZeroAndDot(current));
				map.put("accumulativeIntegral", BigDecimalUtil.subZeroAndDot(leiji));
				map.put("requirementIntegral", requirementIntegral);
				return CallBackConstant.SUCCESS.callback(map);
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("CustomerManagerImpl.upgradeCustomerLevel --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}

	
}
