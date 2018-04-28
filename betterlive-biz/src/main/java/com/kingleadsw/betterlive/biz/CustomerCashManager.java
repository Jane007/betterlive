package com.kingleadsw.betterlive.biz;

import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerCash;
import com.kingleadsw.betterlive.vo.CustomerCashVo;

public interface CustomerCashManager  extends BaseManager<CustomerCashVo, CustomerCash> {

	/**
	 * 兑换礼物
	 * @param pd
	 * @return
	 */
	Map<String, Object> getCashGift(PageData pd);

}
