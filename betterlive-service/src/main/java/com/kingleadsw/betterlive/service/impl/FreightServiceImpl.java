package com.kingleadsw.betterlive.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.FreightMapper;
import com.kingleadsw.betterlive.model.Freight;
import com.kingleadsw.betterlive.service.FreightService;

@Service
public class FreightServiceImpl extends BaseServiceImpl<Freight> implements FreightService {

	@Autowired
    private FreightMapper freightMapper;
	
	@Override
	protected BaseMapper<Freight> getBaseMapper() {
		return freightMapper;
	}

	@Override
	public Freight queryFreightByAreaCode(Map<String, String> areaMap) {
		return freightMapper.queryFreightByAreaCode(areaMap);
	}
	

}
