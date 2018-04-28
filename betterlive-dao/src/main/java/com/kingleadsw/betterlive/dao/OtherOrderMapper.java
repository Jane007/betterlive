package com.kingleadsw.betterlive.dao;

import java.util.Map;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.OtherOrder;

public interface OtherOrderMapper extends BaseMapper<OtherOrder>{

	int createOrders(Map<String, Object> params);

}
