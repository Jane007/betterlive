package com.kingleadsw.betterlive.biz;

import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.OtherOrder;
import com.kingleadsw.betterlive.vo.OtherOrderVo;

public interface OtherOrderManager extends BaseManager<OtherOrderVo, OtherOrder>{

	/**
	 * 批量导入其他平台订单
	 * @param params
	 * @return
	 */
	int createOrders(Map<String, Object> params);

}
