package com.kingleadsw.betterlive.dao;


import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.AgentSystemConfig;

/**
 * 商品 dao层
 * 2017-03-08
 */
public interface AgentSystemConfigMapper extends BaseMapper<AgentSystemConfig>{
	public String findProportionByConfigId(long ConfigId);
}