package com.kingleadsw.betterlive.dao;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.OrderSales;

public interface OrderSalesMapper extends BaseMapper<OrderSales>{

	List<Map<String, Object>> queryMonthCostAmountSum(Map<String, Object> map);

	List<Map<String, Object>> queryDayCostAmountSum(
			Map<String, Object> condition);

	List<Map<String, Object>> queryOtherMonthCostAmountSum(
			Map<String, Object> map);

	List<Map<String, Object>> queryOtherDayCostAmountSum(
			Map<String, Object> condition);

	List<OrderSales> queryTotalByCustSourceListPage(
			PageData pd);
	
	List<OrderSales> queryTotalQuantityByCustSourceList(
			PageData pd);
	
	List<String> queryTotalOrderIdByCustSourceList(
			PageData pd);
	
	List<String> queryTotalOrderIdByOrderSourceList(
			PageData pd);

	List<OrderSales> queryOtherTotalByCustSourceListPage(PageData pd);

	List<OrderSales> queryTotalByOrderSourceListPage(PageData pd);
	
	List<OrderSales> queryProductCountByCustSourceList(PageData pd);

	List<OrderSales> queryOtherTotalByOrderSourceListPage(PageData pd);

	List<OrderSales> queryProductSalesListPage(PageData pd);

	List<OrderSales> queryOtherProductSalesListPage(PageData pd);

}
