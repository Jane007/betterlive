package com.kingleadsw.betterlive.biz;

import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.AgentAccount;
import com.kingleadsw.betterlive.vo.AgentAccountVo;

public interface AgentAccountManager extends BaseManager<AgentAccountVo, AgentAccount>{
	/**
	 * 新增保存账户到数据库
	 * @param
	 * @return
	 * @author zhangjing 2018年3月12日 下午5:34:45
	 */
	Map<String,Object> saveAgentAccount(PageData pd);

}
