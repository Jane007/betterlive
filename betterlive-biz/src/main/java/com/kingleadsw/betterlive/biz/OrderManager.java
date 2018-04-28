package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Order;
import com.kingleadsw.betterlive.vo.OrderVo;


/**
 * 订单 实际交互层
 * 2017-03-10 by chen
 */
public interface OrderManager extends BaseManager<OrderVo,Order>{
	
	int insertOrder(OrderVo order);   	 					 //添加订单
	
	Map<String, Object> createOrder(PageData pd);			//创建订单
	
	int editOder(PageData pd);  		  					//更新订单

	OrderVo findOrder(PageData pd);    						//根据条件查询订单
	
	List<OrderVo> findAllorderListPage(PageData pd);    		 //根据条件分页查询订单
	
	List<OrderVo> findHistoryOrderByCustomerId(int customerId);  //根据用户id查询历史购买订单
	
	List<OrderVo> findOrderLockIntegerAndCoupon(PageData pd);    //查询 订单时间超过系统设置时间的商品订单 且  已锁定的积分和优惠券
	
	OrderVo queryOrderDetails(PageData pd);               		 //查询订单详情  
	
	int cancelOrder(OrderVo order);
	
	int updateOrderCodeByOrdeId(PageData pd);
	
	List<OrderVo> findAllorder2ListPage(PageData pd);
	
	List<OrderVo> findAllorders(PageData pd);

	/**
	 * @param pd
	 * @return
	 */
	OrderVo findOrder2(PageData pd);

	List<OrderVo> findAllorder3ListPage(PageData pd);

	OrderVo findOrder4(PageData pd);

	OrderVo findOrder3(PageData pd);

	/**
	 * 用户购买数量
	 * @param customerId
	 * @return
	 */
	int queryOrderCountByCust(int customerId);

	/**
	 * 根据条件查询订单数
	 * @param ordpd
	 * @return
	 */
	int queryOrderCountByParams(PageData ordpd);

	/**
	 * 用户个人心订单
	 * @param customer_id
	 * @return
	 */
	Map<String, Integer> queryMyOrderNums(Integer customerId);
	
}
