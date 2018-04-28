package com.kingleadsw.betterlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.OrderProductMapper;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.service.OrderProductService;


/**
 * 订单商品   service 实现层
 * 2017-03-10 by chen
 */
@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct>  implements OrderProductService{
	@Autowired
	private OrderProductMapper orderProductMapper;

	@Override
	public int addBatchOrderProduct(List<OrderProduct> listOrderProducts) {
		return orderProductMapper.addBatchOrderProduct(listOrderProducts);
	}

	@Override
	public int editOrderProductById(PageData pd) {
		return orderProductMapper.editOrderProductById(pd);
	}
	
	@Override
	public int editOrderProductByPdId(PageData pd) {
		return orderProductMapper.editOrderProductByPdId(pd);
	}

	@Override
	public OrderProduct findOrderProduct(PageData pd) {
		return orderProductMapper.findOrderProduct(pd);
	}

	@Override
	public List<OrderProduct> findListOrderProduct(PageData pd) {
		return orderProductMapper.findListOrderProduct(pd);
	}

	@Override
	protected BaseMapper<OrderProduct> getBaseMapper() {
		return orderProductMapper;
	}

	@Override
	public int editOrderProductByOrderProductId(PageData pd) {
		return orderProductMapper.editOrderProductByOrderProductId(pd);
	}
	
	//更新订单商品状态
	@Override
	public int updateOrderProductStatusByOrderId(PageData pd){
		return orderProductMapper.updateOrderProductStatusByOrderId(pd);
	}

	@Override
	public List<Map<String, Object>> queryMyOrderNum(PageData ordpd) {
		return orderProductMapper.queryMyOrderNum(ordpd);
	}

	@Override
	public int queryOrderProductQuantity(PageData ordParams) {
		return orderProductMapper.queryOrderProductQuantity(ordParams);
	}                 
	

}
