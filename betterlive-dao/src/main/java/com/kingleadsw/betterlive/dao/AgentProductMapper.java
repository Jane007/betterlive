package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.AgentProduct;

public interface AgentProductMapper extends BaseMapper<AgentProduct> {

	List<AgentProduct> queryAgentProductListPage(PageData pd);

}
