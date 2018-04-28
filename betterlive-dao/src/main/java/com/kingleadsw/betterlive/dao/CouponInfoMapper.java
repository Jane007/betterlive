package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CouponInfo;


/**
 * 用户 优惠券实体类
 * 2017-03-14 by chen
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo>{

	/**
	 * 增加用户领取优惠券
	 * @param couponManager
	 * @return
	 */
	int insertUserCoupon(CouponInfo couponInfo);              
	
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

    
    /**
     * 根据条件分页查询用户领取优惠券(有效与无效)
     * @param pd
     * @return
     */
    List<CouponInfo> findCouponListPage(PageData pd);

    
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
	 * 消费券过期
	 */	List<CouponInfo> queryExpiringList(PageData pd);
	 
}