package com.kingleadsw.betterlive.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.BanMapper;
import com.kingleadsw.betterlive.model.Ban;
import com.kingleadsw.betterlive.service.BanService;


/**
 * 禁止非法访问    service 实现层
 */
@Service
public class BanServiceImpl extends BaseServiceImpl<Ban> implements BanService{
	@Autowired
	private  BanMapper banMapper;

	/**
	 * @return
	 */
	@Override
	protected BaseMapper<Ban> getBaseMapper() {
		return banMapper;
	}

}
