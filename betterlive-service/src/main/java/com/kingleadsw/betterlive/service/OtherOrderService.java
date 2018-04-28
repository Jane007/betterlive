package com.kingleadsw.betterlive.service;


import java.util.Map;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.OtherOrder;

public interface OtherOrderService extends BaseService<OtherOrder> {

	int createOrders(Map<String, Object> params);

}
