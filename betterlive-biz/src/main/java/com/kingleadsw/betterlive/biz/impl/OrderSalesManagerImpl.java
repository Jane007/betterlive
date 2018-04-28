package com.kingleadsw.betterlive.biz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.OrderSalesManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.OrderSales;
import com.kingleadsw.betterlive.service.OrderSalesService;
import com.kingleadsw.betterlive.vo.OrderSalesVo;

@Component
@Transactional
public class OrderSalesManagerImpl extends BaseManagerImpl<OrderSalesVo, OrderSales> implements OrderSalesManager{
	
	@Autowired
	private OrderSalesService orderSalesService;
	
	@Override
	protected BaseService<OrderSales> getService() {
		return orderSalesService;
	}

	@Override
	public List<Map<String, Object>> queryMonthCostAmountSum(Map<String, Object> map) {
		return orderSalesService.queryMonthCostAmountSum(map);
	}

	@Override
	public List<Map<String, Object>> queryDayCostAmountSum(
			Map<String, Object> condition) {
		return orderSalesService.queryDayCostAmountSum(condition);
	}

	@Override
	public List<Map<String, Object>> queryOtherMonthCostAmountSum(
			Map<String, Object> map) {
		return orderSalesService.queryOtherMonthCostAmountSum(map);
	}

	@Override
	public List<Map<String, Object>> queryOtherDayCostAmountSum(
			Map<String, Object> condition) {
		return orderSalesService.queryOtherDayCostAmountSum(condition);
	}

	@Override
	public List<OrderSalesVo> queryTotalByCustSourceListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryTotalByCustSourceListPage(pd));
		return list;
	}
	
	@Override
	public List<OrderSalesVo> queryTotalQuantityByCustSourceList(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryTotalQuantityByCustSourceList(pd));
		return list;
	}
	
	@Override
	public List<String> queryTotalOrderIdByCustSourceList(PageData pd) {
		List list = orderSalesService.queryTotalOrderIdByCustSourceList(pd);
		return list;
	}
	
	@Override
	public List<String> queryTotalOrderIdByOrderSourceList(PageData pd) {
		List list = orderSalesService.queryTotalOrderIdByOrderSourceList(pd);
		return list;
	}

	@Override
	public List<OrderSalesVo> queryOtherTotalByCustSourceListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryOtherTotalByCustSourceListPage(pd));
		return list;
	}

	@Override
	public List<OrderSalesVo> queryTotalByOrderSourceListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryTotalByOrderSourceListPage(pd));
		return list;
	}
	
	@Override
	public List<OrderSalesVo> queryProductCountByCustSourceList(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryProductCountByCustSourceList(pd));
		return list;
	}

	@Override
	public List<OrderSalesVo> queryOtherTotalByOrderSourceListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryOtherTotalByOrderSourceListPage(pd));
		return list;
	}

	@Override
	public List<OrderSalesVo> queryProductSalesListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryProductSalesListPage(pd));
		return list;
	}

	@Override
	public List<OrderSalesVo> queryOtherProductSalesListPage(PageData pd) {
		List<OrderSalesVo> list = generator.transfer(OrderSalesVo.class, orderSalesService.queryOtherProductSalesListPage(pd));
		return list;
	}

}
