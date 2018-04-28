package com.kingleadsw.betterlive.biz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.service.OrderProductService;
import com.kingleadsw.betterlive.vo.OrderProductVo;


/**
 * 订单商品  实际交互 实现层
 * 2017-03-10 by chen
 */
@Component
@Transactional
public class OrderProductManagerImpl extends BaseManagerImpl<OrderProductVo,OrderProduct> implements OrderProductManager{
	@Autowired
	private OrderProductService orderProductService;

	@Override
	public int addBatchOrderProduct(List<OrderProductVo> listOrderProducts) {
		List<OrderProduct> listOrderProduct=vo2poer.transfer(OrderProduct.class,listOrderProducts);  
		return orderProductService.addBatchOrderProduct(listOrderProduct);
	}
	

	@Override
	public int editOrderProductById(PageData pd) {
		return orderProductService.editOrderProductById(pd);
	}
	
	@Override
	public int editOrderProductByPdId(PageData pd) {
		return orderProductService.editOrderProductByPdId(pd);
	}

	@Override
	public OrderProductVo findOrderProduct(PageData pd) {
		return  po2voer.transfer(new OrderProductVo(), orderProductService.findOrderProduct(pd));
	}

	@Override
	public List<OrderProductVo> findListOrderProduct(PageData pd) {
		return  po2voer.transfer(OrderProductVo.class, orderProductService.findListOrderProduct(pd));
	}

	@Override
	protected BaseService<OrderProduct> getService() {
		return orderProductService;
	}


	@Override
	public int editOrderProductByOrderProductId(PageData pd) {
		return orderProductService.editOrderProductByOrderProductId(pd);
	}

	@Override
	public int updateOrderProductStatusByOrderId(PageData pd){
		return orderProductService.updateOrderProductStatusByOrderId(pd);
	}

	@Override
	public List<Map<String, Object>> queryMyOrderNum(PageData ordpd) {
		return orderProductService.queryMyOrderNum(ordpd);
	}


	@Override
	public int queryOrderProductQuantity(PageData pdData) {
		return orderProductService.queryOrderProductQuantity(pdData);
	}
	


}
