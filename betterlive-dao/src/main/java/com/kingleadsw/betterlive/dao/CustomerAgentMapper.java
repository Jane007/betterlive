package com.kingleadsw.betterlive.dao;


import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.CustomerAgent;

public interface CustomerAgentMapper extends BaseMapper<CustomerAgent>{
	public CustomerAgent findAgentBySource(String orderSource);
	
	public int isAgentProduct(int productId);
}
