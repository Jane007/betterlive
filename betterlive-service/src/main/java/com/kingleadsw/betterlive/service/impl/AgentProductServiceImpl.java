package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.AgentProductMapper;
import com.kingleadsw.betterlive.model.AgentProduct;
import com.kingleadsw.betterlive.service.AgentProductService;

@Service
public class AgentProductServiceImpl extends BaseServiceImpl<AgentProduct> implements AgentProductService {

	@Autowired
	private AgentProductMapper agentProductMapper;
	
	@Override
	protected BaseMapper<AgentProduct> getBaseMapper() {
		return agentProductMapper;
	}

	@Override
	public List<AgentProduct> queryAgentProductListPage(PageData pd) {
		return agentProductMapper.queryAgentProductListPage(pd);
	}

}
