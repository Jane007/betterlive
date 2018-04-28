package com.kingleadsw.betterlive.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.dao.AgentAccountMapper;
import com.kingleadsw.betterlive.dao.AgentSystemConfigMapper;
import com.kingleadsw.betterlive.dao.CustomerAgentMapper;
import com.kingleadsw.betterlive.dao.CustomerMapper;
import com.kingleadsw.betterlive.model.AgentAccount;
import com.kingleadsw.betterlive.model.AgentSystemConfig;
import com.kingleadsw.betterlive.model.CustomerAgent;
import com.kingleadsw.betterlive.service.AgentAccountService;
@Service
public class AgentAccountServiceImpl extends BaseServiceImpl<AgentAccount>
		implements AgentAccountService {
	private static Logger logger = Logger
			.getLogger(AgentAccountServiceImpl.class);
	@Autowired
	private AgentAccountMapper agentAccountMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerAgentMapper customerAgentMapper;
	@Autowired
	private AgentSystemConfigMapper agentSystemConfigMapper;

	@Override
	protected BaseMapper<AgentAccount> getBaseMapper() {
		return agentAccountMapper;
	}
	@Override
	public Map<String, Object> saveAgentAccount(PageData pd) {

		try {
			int customerId = pd.getInteger("customerId");
			if (customerId<=0) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("请先登录本系统");
			}

			PageData checkConfigParams = new PageData();
			checkConfigParams.put("isDefault", 1);
			checkConfigParams.put("status", 0);
			AgentSystemConfig sysConfig = agentSystemConfigMapper.queryOne(checkConfigParams);
			if (sysConfig == null) {
				return CallBackConstant.DATA_NOT_FOUND.callbackError("代理收益占比不存在");
			}
			pd.put("sysConfigId",sysConfig.getConfigId());
			// 2、代理人和用户账号关联
			PageData caParams = new PageData();
			caParams.put("customerId", customerId);
			CustomerAgent customerAgent = customerAgentMapper.queryOne(caParams);
			if (customerAgent != null) {
				String msg = "";
				if (customerAgent.getStatus().intValue() == 1) {
					msg = "，且账号已经被冻结";
					return CallBackConstant.DATA_HAD_FOUND.callbackError("该用户已经是代理"+ msg);
				}
			}else{
				// 3、创建代理信息
				customerAgent = this.createCustomerAgent(pd);
			}
			

			
			// 4、创建代理人账户
			PageData ppd = new PageData();
			ppd.put("agentId",customerAgent.getAgentId());
			AgentAccount agentAccount = agentAccountMapper.queryOne(ppd);
			if(agentAccount==null){//没有账户就新建一个，有就用之前的那个
				agentAccount = new AgentAccount();
				agentAccount.setAgentId(customerAgent.getAgentId());
				agentAccount.setStatus(pd.getInteger("status"));
				int count = agentAccountMapper.insert(agentAccount);
				if (count > 0) {
					return CallBackConstant.SUCCESS.callback();
				}
			}
			
			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("AgentAccountServiceImp.saveAgentAccount", e);
			return CallBackConstant.FAILED.callback();
		}
	}

	// 代理人和用户账号关联
	private CustomerAgent createCustomerAgent(PageData pd) {
		try {
			String code = pd.getString("code");
			CustomerAgent customerAgent = new CustomerAgent();
			customerAgent = new CustomerAgent();
			customerAgent.setCustomerId(pd.getInteger("customerId"));
			customerAgent.setAgentCode(code);
			customerAgent.setStatus(pd.getInteger("status"));
			if (pd.get("sysConfigId")!=null) {
				String objString = pd.get("sysConfigId").toString();
				customerAgent.setSysConfigId(Long.parseLong(objString));
			}
			int count = customerAgentMapper.insert(customerAgent);
			if (count > 0) {
				return customerAgent;
			}
		} catch (Exception e) {
			logger.error("插入代理人时报错，请联系后台工作人员", e);
		}
		return null;

	}

}
