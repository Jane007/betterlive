package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Order;

/**
 * 订单 service 层
 * 2017-03-10 by chen
 */
public interface OrderService extends BaseService<Order> {

	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	int insertOrder(Order order);   	 					 
	
	/**
	 * 更新订单
	 * @param pd
	 * @return
	 */
	int editOder(PageData pd);  		  					
	
	/**
	 * 根据条件查询订单
	 * @param pd
	 * @return
	 */
	Order findOrder(PageData pd);    						
	
	/**
	 * 根据条件分页查询订单
	 * @param pd
	 * @return
	 */
	List<Order> findAllorderListPage(PageData pd);    		
	
	/**
	 * 根据用户id查询历史购买订单
	 * @param customerId
	 * @return
	 */
	List<Order> findHistoryOrderByCustomerId(int customerId);  
	
	
	/**
	 * 查询 订单时间超过系统设置时间的商品订单 且  已锁定的积分和优惠券
	 * @param pd
	 * @return
	 */
	List<Order> findOrderLockIntegerAndCoupon(PageData pd);    
	
	/**
	 * 查询订单详情
	 * @param pd
	 * @return
	 */
	Order queryOrderDetails(PageData pd);                  
	
	/**
	 * 合并用户时,需要合并用户的订单
	 * @param pd  newCustomerId customerId
	 * @return
	 */
	int modifyCustomerOrderBycId(PageData pd);
	
	int updateOrderCodeByOrdeId(PageData pd);

	/**
	  * dlb
	  * 2017年5月25日 下午5:21:43
	  * List<Order>
	  */
	List<Order> findAllorder2ListPage(PageData pd);
	
	List<Order> findAllorders(PageData pd);

	int queryOrderCountByCust(int customerId);

	int queryOrderCountByParams(PageData ordpd);
}
