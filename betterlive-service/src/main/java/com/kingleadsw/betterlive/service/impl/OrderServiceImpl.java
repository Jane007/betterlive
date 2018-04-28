package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OrderMapper;
import com.kingleadsw.betterlive.model.Order;
import com.kingleadsw.betterlive.service.OrderService;


/**
 * 订单 service 实现层
 * 2017-03-10 by chen
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order>  implements OrderService{
	@Autowired
	private OrderMapper orderMapper;
	
	 //添加订单
	@Override
	public int insertOrder(Order order) {
		return orderMapper.insertOrder(order);
	}
	
	//更新订单
	@Override
	public int editOder(PageData pd) {
		return orderMapper.editOder(pd);
	}
	
	//根据条件查询订单
	@Override
	public Order findOrder(PageData pd) {
		return orderMapper.findOrder(pd);
	}
	
	//根据条件分页查询订单
	@Override
	public List<Order> findAllorderListPage(PageData pd) {
		return orderMapper.findAllorderListPage(pd);
	}
	
	//根据用户id查询历史购买订单
	@Override
	public List<Order> findHistoryOrderByCustomerId(int customerId) {
		return orderMapper.findHistoryOrderByCustomerId(customerId);
	}
	
	//查询 订单时间超过系统设置时间的商品订单 且  已锁定的积分和优惠券
	@Override
	public List<Order> findOrderLockIntegerAndCoupon(PageData pd) {
		return orderMapper.findOrderLockIntegerAndCoupon(pd);
	}
	
	//查询订单详情 
	@Override
	public Order queryOrderDetails(PageData pd) {
		return orderMapper.queryOrderDetails(pd);
	}
	
	
	@Override
	protected BaseMapper<Order> getBaseMapper() {
		return orderMapper;
	}
	
	
	/**
	 * 合并用户时,需要合并用户的订单
	 * @param pd  newCustomerId customerId
	 * @return
	 */
	@Override
	public int modifyCustomerOrderBycId(PageData pd) {
		return orderMapper.modifyCustomerOrderBycId(pd);
	}

	@Override
	public int updateOrderCodeByOrdeId(PageData pd) {
		return orderMapper.updateOrderCodeByOrdeId(pd);
	}
	
	//根据条件分页查询订单
	@Override
	public List<Order> findAllorder2ListPage(PageData pd) {
		return orderMapper.findAllorder2ListPage(pd);
	}
	
	
	public List<Order> findAllorders(PageData pd){
		return orderMapper.findAllorders(pd);
	}

	@Override
	public int queryOrderCountByCust(int customerId) {
		return orderMapper.queryOrderCountByCust(customerId);
	}

	@Override
	public int queryOrderCountByParams(PageData ordpd) {
		return orderMapper.queryOrderCountByParams(ordpd);
	}

}
