package com.kingleadsw.betterlive.service;

import java.util.Map;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Freight;

public interface FreightService extends BaseService<Freight> {
	 
	/**
	 * 根据地区编码，查询该地区的邮费信息
	 * @param areaMap，areaCode:地区编码；provinceCode:省编码
	 * @return
	 */
	public Freight queryFreightByAreaCode(Map<String, String> areaMap);
	
}
