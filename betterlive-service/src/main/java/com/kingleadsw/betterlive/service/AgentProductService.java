package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.AgentProduct;

public interface AgentProductService  extends BaseService<AgentProduct> {

	List<AgentProduct> queryAgentProductListPage(PageData pd);

}
