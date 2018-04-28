package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.AgentProduct;
import com.kingleadsw.betterlive.vo.AgentProductVo;

public interface AgentProductManager extends BaseManager<AgentProductVo, AgentProduct> {

	/**
	 * 查询代理商品
	 * @param pd
	 * @return
	 */
	List<AgentProductVo> queryAgentProductListPage(PageData pd);

}
