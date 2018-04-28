package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.UseLock;
import com.kingleadsw.betterlive.vo.UseLockVo;


/**
 * 锁定礼品卡和优惠券  实际交互层
 * 2017-03-21 by chen
 */
public interface UseLockManager extends BaseManager<UseLockVo,UseLock>{
	
	/**
	 * 新增锁定记录
	 * @param useLock
	 * @return
	 */
	int insertUseLock(UseLockVo useLock);   	 			
	
	/**
	 * 更新锁定记录
	 * @param pd
	 * @return
	 */
	int editUseLockById(PageData pd);  		  				

	/**
	 * 根据条件查询锁定记录
	 * @param pd
	 * @return
	 */
	UseLockVo findUseLock(PageData pd);    					
	
	/**
	 * 根据条件查询所有锁定记录
	 * @param pd
	 * @return
	 */
	List<UseLockVo> findListUseLock(PageData pd);    		
	
	/**
	 * 查询用户锁定的礼品卡金额
	 * @param customerId
	 * @return
	 */
	float findCustomerLockMoney(String customerId);           
}
