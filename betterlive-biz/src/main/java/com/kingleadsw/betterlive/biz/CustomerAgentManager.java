package com.kingleadsw.betterlive.biz;


import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.CustomerAgent;
import com.kingleadsw.betterlive.vo.CustomerAgentVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;

/**
 * 分销代理商逻辑类
 * @author ltp
 * @date 2017年3月10日16:48:03
 *
 */
public interface CustomerAgentManager extends BaseManager<CustomerAgentVo, CustomerAgent> {
	public CustomerAgentVo findAgentBySource(String orderSource);
	
	public void addAccountRecord(CustomerAgentVo customerAgent,OrderProductVo orderProductVo,String totalPrice);
}
