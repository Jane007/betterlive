package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.model.Customer;


/**
 * 用户 优惠券  service层
 * 2017-03-14 by chen
 */
public interface CouponInfoService extends BaseService<CouponInfo> {
	
	/**
	 * 增加用户领取优惠券
	 * @param couponManager
	 * @return
	 */
	int insertUserCoupon(CouponInfo couponManager);              
	
	/**
	 * 修改用户领取优惠券
	 * @param pd
	 * @return
	 */
	int updateUserCouponByCid(PageData pd);                      
	 
	/**
	 * 删除用户领取优惠券
	 * @param cId
	 * @return
	 */
	int deleteByCid(String cId);                                  

	/**
	 * 根据条件分页查询用户领取优惠券
	 * @param pd
	 * @return
	 */
    List<CouponInfo> findUserCouponListPage(PageData pd);        
    
    /**
     * 根据条件查询全部用户领取优惠券
     * @param pd
     * @return
     */
    List<CouponInfo> findListUserCoupon(PageData pd);     	     

    List<CouponInfo> findCouponListPage(PageData pd);            //根据条件分页查询用户领取优惠券(有效与无效)
    
    
    /**
     * 根据条件查询单个用户领取优惠券详细
     * @param pd
     * @return
     */
    CouponInfo findUserCoupon(PageData pd);                      
    
    /**
     * 根据订单ID查询分享券（优惠券）领取
     * @param orderId
     * @return
     */
    int findShareCountByOrderId(int orderId);                    
    
    /**
     * 根据手机 ，用户ID， 订单ID   查询用户是否已经领取过红包
     * @param pd
     * @return
     */
    int findUserShareCount(PageData pd);                         
    
    
    /**
     * 合并用户时,需要合并用户的优惠券
     * @param pd
     * @return
     */
    int modifyCustomerCouponBycId(PageData pd);

    /**
     * 用户注册之后，给用户发放新手券
     * @param customer  用户对象
     * @return
     */
    int insertRegisterCoupon(Customer customer);
    
	List<CouponInfo> queryExpiringList(PageData pd);}
