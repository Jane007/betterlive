package com.kingleadsw.betterlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OrderSalesMapper;
import com.kingleadsw.betterlive.model.OrderSales;
import com.kingleadsw.betterlive.service.OrderSalesService;

@Service
public class OrderSalesServiceImpl extends BaseServiceImpl<OrderSales> implements OrderSalesService{

	@Autowired
	private OrderSalesMapper orderSalesMapper;
	
	@Override
	protected BaseMapper<OrderSales> getBaseMapper() {
		return orderSalesMapper;
	}
	
	@Override
	public List<Map<String, Object>> queryMonthCostAmountSum(Map<String, Object> map) {
		return orderSalesMapper.queryMonthCostAmountSum(map);
	}

	@Override
	public List<Map<String, Object>> queryDayCostAmountSum(
			Map<String, Object> condition) {
		return orderSalesMapper.queryDayCostAmountSum(condition);
	}

	@Override
	public List<Map<String, Object>> queryOtherMonthCostAmountSum(
			Map<String, Object> map) {
		return orderSalesMapper.queryOtherMonthCostAmountSum(map);
	}

	@Override
	public List<Map<String, Object>> queryOtherDayCostAmountSum(
			Map<String, Object> condition) {
		return orderSalesMapper.queryOtherDayCostAmountSum(condition);
	}

	@Override
	public List<OrderSales> queryTotalByCustSourceListPage(
			PageData pd) {
		return orderSalesMapper.queryTotalByCustSourceListPage(pd);
	}
	
	@Override
	public List<OrderSales> queryTotalQuantityByCustSourceList(
			PageData pd) {
		return orderSalesMapper.queryTotalQuantityByCustSourceList(pd);
	}
	
	@Override
	public List<String> queryTotalOrderIdByCustSourceList(
			PageData pd) {
		return orderSalesMapper.queryTotalOrderIdByCustSourceList(pd);
	}
	
	@Override
	public List<String> queryTotalOrderIdByOrderSourceList(
			PageData pd) {
		return orderSalesMapper.queryTotalOrderIdByOrderSourceList(pd);
	}

	@Override
	public List<OrderSales> queryOtherTotalByCustSourceListPage(PageData pd) {
		return orderSalesMapper.queryOtherTotalByCustSourceListPage(pd);
	}

	@Override
	public List<OrderSales> queryTotalByOrderSourceListPage(PageData pd) {
		return orderSalesMapper.queryTotalByOrderSourceListPage(pd);
	}
	
	@Override
	public List<OrderSales> queryProductCountByCustSourceList(PageData pd) {
		return orderSalesMapper.queryProductCountByCustSourceList(pd);
	}

	@Override
	public List<OrderSales> queryOtherTotalByOrderSourceListPage(PageData pd) {
		return orderSalesMapper.queryOtherTotalByOrderSourceListPage(pd);
	}

	@Override
	public List<OrderSales> queryProductSalesListPage(PageData pd) {
		return orderSalesMapper.queryProductSalesListPage(pd);
	}

	@Override
	public List<OrderSales> queryOtherProductSalesListPage(PageData pd) {
		return orderSalesMapper.queryOtherProductSalesListPage(pd);
	}

}
