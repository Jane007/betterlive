package com.kingleadsw.betterlive.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.AgentAccountManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.AgentAccount;
import com.kingleadsw.betterlive.service.AgentAccountService;
import com.kingleadsw.betterlive.vo.AgentAccountVo;
@Component
@Transactional
public class AgentAccountManagerImpl extends BaseManagerImpl<AgentAccountVo, AgentAccount> implements AgentAccountManager{
	@Autowired
	private AgentAccountService agentAccountService;
	@Override
	protected BaseService<AgentAccount> getService() {
		return agentAccountService;
	}
	@Override
	public Map<String, Object> saveAgentAccount(PageData pd) {
		return agentAccountService.saveAgentAccount(pd);
	}
	

}
