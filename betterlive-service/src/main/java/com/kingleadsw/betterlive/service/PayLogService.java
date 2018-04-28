package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PayLog;

/**
 * 支付日志  service 层
 * 2017-03-21 by chen
 */
public interface PayLogService extends BaseService<PayLog> {

	/**
	 * 添加支付日志
	 * @param payLog
	 * @return
	 */
	int insertPayLog(PayLog payLog);   	 					
	
	/**
	 * 更新支付日志
	 * @param pd
	 * @return
	 */
	int editPayLog(PageData pd);  		  					

	
	/**
	 * 根据条件查询支付日志
	 * @param pd
	 * @return
	 */
	PayLog findPayLog(PageData pd);    						
	
	
	/**
	 * 根据条件分页查询支付日志
	 * @param pd
	 * @return
	 */
	List<PayLog> findPayLogListPage(PageData pd);    		
	
	
	/**
	 * 合并用户时,需要合并用户的支付日志 
	 * @param pd
	 * @return
	 */
	int modifyCustomerPaylogBycId(PageData pd); 
	
	/**
	 *  根据旧订单编号更新订单编号
	 * @param pd
	 * @return
	 */
	int updateOrderCodeByOrdeId(PageData pd);
}
