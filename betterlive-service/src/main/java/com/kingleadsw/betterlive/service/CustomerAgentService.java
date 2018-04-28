package com.kingleadsw.betterlive.service;


import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerAgent;
import com.kingleadsw.betterlive.model.OrderProduct;

public interface CustomerAgentService extends BaseService<CustomerAgent> {
	public CustomerAgent findAgentBySource(String orderSource);
	
	public void addAccountRecord(CustomerAgent customerAgent, OrderProduct orderProduct,String totalPrice);
}
