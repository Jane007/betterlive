package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.OrderSales;
import com.kingleadsw.betterlive.vo.OrderSalesVo;

public interface OrderSalesManager extends BaseManager<OrderSalesVo, OrderSales>{

	/**
	 * 挥货平台月度营收额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryMonthCostAmountSum(Map<String, Object> map);
	
	/**
	 * 挥货平台日营收额
	 * @param condition
	 * @return
	 */
	List<Map<String, Object>> queryDayCostAmountSum(
			Map<String, Object> condition);
	/**
	 * 其他平台月度营收额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryOtherMonthCostAmountSum(
			Map<String, Object> map);

	/**
	 * 其他平台日营收额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryOtherDayCostAmountSum(
			Map<String, Object> condition);

	/**
	 * 挥货平台用户来源统计
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryTotalByCustSourceListPage(
			PageData pd);
	
	/**
	 * 挥货平台用户来源统计商品销售份数
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryTotalQuantityByCustSourceList(
			PageData pd);
	
	List<String> queryTotalOrderIdByCustSourceList(
			PageData pd);
	
	List<String> queryTotalOrderIdByOrderSourceList(
			PageData pd);
	/**
	 * 其他平台的挥货用户来源统计
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryOtherTotalByCustSourceListPage(
			PageData pd);

	/**
	 * 挥货平台订单来源统计
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryTotalByOrderSourceListPage(PageData pd);
	
	/**
	 * 挥货平台用户来源统计商品销售份数
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryProductCountByCustSourceList(
			PageData pd);

	/**
	 * 根据挥货平台订单来源统计其他平台订单
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryOtherTotalByOrderSourceListPage(PageData pd);

	/**
	 * 挥货平台商品销量
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryProductSalesListPage(PageData pd);

	/**
	 * 其他平台商品销量
	 * @param pd
	 * @return
	 */
	List<OrderSalesVo> queryOtherProductSalesListPage(PageData pd);
}
