package com.kingleadsw.betterlive.service;

import java.util.Map;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.AgentAccount;

public interface AgentAccountService extends BaseService<AgentAccount>{
	/**
	 * 新增保存账户到数据库
	 * @param
	 * @return
	 * @author zhangjing 2018年3月12日 下午5:34:45
	 */
	Map<String,Object> saveAgentAccount(PageData pd);

}
