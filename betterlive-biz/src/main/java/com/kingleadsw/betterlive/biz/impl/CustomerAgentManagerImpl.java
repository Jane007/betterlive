package com.kingleadsw.betterlive.biz.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerAgent;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.service.CustomerAgentService;
import com.kingleadsw.betterlive.vo.CustomerAgentVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;

@Component
@Transactional
public class CustomerAgentManagerImpl extends BaseManagerImpl<CustomerAgentVo, CustomerAgent> implements CustomerAgentManager {

	private Logger logger = Logger.getLogger(CustomerAgentManagerImpl.class);
	
	@Autowired
	private CustomerAgentService customerAgentService;
	
	@Autowired
    private RedisService redisService;
	
	@Override
	protected BaseService<CustomerAgent> getService() {
		return customerAgentService;
	}
	
	@Override
	public CustomerAgentVo findAgentBySource(String orderSource) {
		CustomerAgent customerAgent = customerAgentService.findAgentBySource(orderSource);
		return po2voer.transfer(new CustomerAgentVo(),customerAgent);
	}

	@Override
	public void addAccountRecord(CustomerAgentVo customerAgent,OrderProductVo orderProductVo,String totalPrice) {
		OrderProduct orderProduct = (OrderProduct) generator.transfer(new OrderProduct(), orderProductVo);
		customerAgentService.addAccountRecord(vo2poer.transfer(new CustomerAgent(), customerAgent),orderProduct,totalPrice);
	}

}
