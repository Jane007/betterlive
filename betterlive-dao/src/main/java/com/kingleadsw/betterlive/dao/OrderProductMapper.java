package com.kingleadsw.betterlive.dao;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.OrderProduct;

/**
 * 订单商品 dao层
 * 2017-03-10 by chen
 */
public interface OrderProductMapper extends BaseMapper<OrderProduct>{

	int addBatchOrderProduct(List<OrderProduct> listOrderProducts);   	//添加订单商品
	
	int editOrderProductById(PageData pd);  		  				    //更新订单商品
	
	int editOrderProductByPdId(PageData pd);  		  				    //更新订单商品单个

	OrderProduct findOrderProduct(PageData pd);    						//根据条件查询订单商品
	
//	List<OrderProduct> findOrderProductListPage(PageData pd);    	    //根据条件分页查询订单商品
	
	List<OrderProduct> findListOrderProduct(PageData pd);               //根据条件查询所有订单商品  
	
	int editOrderProductByOrderProductId(PageData pd);                      //更新订单商品单个
	
	int updateOrderProductStatusByOrderId(PageData pd);                 //更新订单商品状态

	List<Map<String, Object>> queryMyOrderNum(PageData ordpd);			//我的订单数量
	
	int queryOrderProductQuantity(PageData ordParams);					//我的订单商品数量
}