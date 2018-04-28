package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.vo.OrderProductVo;


/**
 * 订单商品   实际交互层
 * 2017-03-10 by chen
 */
public interface OrderProductManager extends BaseManager<OrderProductVo,OrderProduct>{
	
	int addBatchOrderProduct(List<OrderProductVo> listOrderProducts);   	//添加订单商品
	
	int editOrderProductById(PageData pd);  	//更新订单商品
	
	int editOrderProductByPdId(PageData pd); 	  				    //更新订单商品

	OrderProductVo findOrderProduct(PageData pd);    						//根据条件查询订单商品
	
//	List<OrderProductVo> findOrderProductListPage(PageData pd);    	    //根据条件分页查询订单商品
	
	List<OrderProductVo> findListOrderProduct(PageData pd);               //根据条件查询所有订单商品
	
	int editOrderProductByOrderProductId(PageData pd);
	
	int updateOrderProductStatusByOrderId(PageData pd);

	/**
	 * 订单数量，按订单状态分组
	 * @param ordpd
	 * @return
	 */
	List<Map<String, Object>> queryMyOrderNum(PageData ordpd);

	/**
	 * 查询订单商品数量
	 * @param pdData
	 * @return
	 */
	int queryOrderProductQuantity(PageData pdData);
}
