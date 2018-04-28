package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UseLock;

/**
 * 锁定礼品卡和优惠券  service层
 * 2017-03-21 by chen
 */
public interface UseLockService extends BaseService<UseLock> {

	/**
	 * 新增锁定记录
	 * @param useLock
	 * @return
	 */
	int insertUseLock(UseLock useLock);   	 				
	
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
	UseLock findUseLock(PageData pd);    					
	
	/**
	 * 根据条件查询所有锁定记录
	 * @param pd
	 * @return
	 */
	List<UseLock> findListUseLock(PageData pd);    			
	
	/**
	 * 查询用户锁定的礼品卡金额
	 * @param customerId
	 * @return
	 */
	float findCustomerLockMoney(String customerId);           
	
	/**
	 * 合并用户时,需要合并用户的锁定状态
	 * @param pd
	 * @return
	 */
	int modifyCustomerUseLockBycId(PageData pd);
}
