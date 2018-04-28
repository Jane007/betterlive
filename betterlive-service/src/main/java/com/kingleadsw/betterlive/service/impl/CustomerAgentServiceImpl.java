package com.kingleadsw.betterlive.service.impl;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.AgentAccountMapper;
import com.kingleadsw.betterlive.dao.AgentAccountRecordMapper;
import com.kingleadsw.betterlive.dao.AgentSystemConfigMapper;
import com.kingleadsw.betterlive.dao.CustomerAgentMapper;
import com.kingleadsw.betterlive.model.AgentAccount;
import com.kingleadsw.betterlive.model.AgentAccountRecord;
import com.kingleadsw.betterlive.model.CustomerAgent;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.service.CustomerAgentService;

@Service
public class CustomerAgentServiceImpl extends BaseServiceImpl<CustomerAgent> implements CustomerAgentService {

	@Autowired
    private CustomerAgentMapper customerAgentMapper;
	@Autowired
    private AgentAccountMapper agentAccountMapper;
	@Autowired
	private AgentSystemConfigMapper agentSystemConfigMapper;
	@Autowired
	private AgentAccountRecordMapper agentAccountRecordMapper;
	
	
	@Override
	protected BaseMapper<CustomerAgent> getBaseMapper() {
		return customerAgentMapper;
	}

	@Override
	public CustomerAgent findAgentBySource(String orderSource) {
		return customerAgentMapper.findAgentBySource(orderSource);
	}

	@Override
	public void addAccountRecord(CustomerAgent customerAgent,OrderProduct orderProduct,String totalPrice) {
		//根据订单商品ID，查询商品是否为代理商品
		int result = customerAgentMapper.isAgentProduct(orderProduct.getProduct_id());
		if (result <= 0) {
			return;
		}
		//根据代理人ID，查询代理人收益比例
		String proportion = agentSystemConfigMapper.findProportionByConfigId(customerAgent.getSysConfigId());
		//计算本单收益
		String expectedIncome = String.format("%.2f",Double.parseDouble(totalPrice)*Double.parseDouble(proportion)/100);
		//根据代理人ID,查询到代理人账户信息，并更新账户不可提现余额
		PageData pd = new PageData();
		pd.put("agentId", customerAgent.getAgentId());
		AgentAccount agentAccount = agentAccountMapper.queryOne(pd);
		if (null == agentAccount) {
			return;
		}
		BigDecimal cashDisallowAmount = agentAccount.getCashDisallowAmount();
		BigDecimal accountAmount = agentAccount.getAccountAmount();
		agentAccount.setCashDisallowAmount(cashDisallowAmount.add(new BigDecimal(expectedIncome)));
		agentAccount.setAccountAmount(accountAmount.add(new BigDecimal(expectedIncome)));
		result = agentAccountMapper.updateByPrimaryKeySelective(agentAccount);
		if (result <= 0) {
			return;
		}
		//往收益记录表内，插入一条当前代理人账户收益信息
		AgentAccountRecord agentAccountRecord = new AgentAccountRecord();
		agentAccountRecord.setAccountId(agentAccount.getAccountId());
		agentAccountRecord.setAgentId(customerAgent.getAgentId());
		agentAccountRecord.setType(1);
		agentAccountRecord.setAmount(new BigDecimal(expectedIncome));
		//记录不可提现余额
		agentAccountRecord.setBeforeAmount(cashDisallowAmount);
		agentAccountRecord.setAfterAmount(cashDisallowAmount.add(new BigDecimal(expectedIncome)));
		agentAccountRecord.setObjId(orderProduct.getOrderpro_id());
		agentAccountRecord.setStatus(0);
		agentAccountRecordMapper.insert(agentAccountRecord);
	}
}
