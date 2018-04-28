package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerVo;


/**
 *优惠券管理 实际交互层
 * 2017-03-14 by chen
 */
public interface CouponInfoManager extends BaseManager<CouponInfoVo,CouponInfo>{
	
	int insertUserCoupon(CouponInfoVo couponManager);              //增加用户领取优惠券
	
	int updateUserCouponByCid(PageData pd);                        //修改用户领取优惠券
	 
	int deleteByCid(String cId);                                   //删除用户领取优惠券 

    List<CouponInfoVo> findUserCouponListPage(PageData pd);        //根据条件分页查询用户领取优惠券
    
    List<CouponInfoVo> findCouponListPage(PageData pd);              //根据条件分页查询用户领取优惠券(有效与无效)
    
    List<CouponInfoVo> findListUserCoupon(PageData pd);     	   //根据条件查询全部用户领取优惠券
    
    CouponInfoVo findUserCoupon(PageData pd);                      //根据条件查询单个用户领取优惠券详细
    
    int findShareCountByOrderId(int orderId);                    //根据订单ID查询分享券（优惠券）领取
    
    /**
     * 根据手机 ，用户ID或者 订单ID   查询用户是否已经领取过红包
     * @param pd
     * @return
     */
    int findUserShareCount(PageData pd);
    
    /**
     * 用户注册之后，给用户发放新手券
     * @param customerVo  用户对象
     * @return 操作影响行数
     */
    int insertRegisterCoupon(CustomerVo customerVo);

    /**
     * 即将过期的优惠券
     * @param pd
     * @return
     */
	List<CouponInfoVo> queryExpiringList(PageData pd);
	/**
	 * 批量插入优惠券
	 * @param
	 * @return
	 * @author zhangjing 2018年1月5日 下午2:37:21
	 */
	int batchInsertCouponInfo(PageData pd);
	
	
}
