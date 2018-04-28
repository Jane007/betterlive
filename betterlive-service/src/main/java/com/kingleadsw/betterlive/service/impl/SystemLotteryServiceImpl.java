package com.kingleadsw.betterlive.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SystemLotteryMapper;
import com.kingleadsw.betterlive.model.SystemLottery;
import com.kingleadsw.betterlive.service.SystemLotteryService;



/**
 * 管理员  service 实现层
 * 2017-03-07 by chen
 */
@Service
public class SystemLotteryServiceImpl extends BaseServiceImpl<SystemLottery> implements SystemLotteryService{
    @Autowired
    private SystemLotteryMapper systemLotteryMapper;

    @Override
    protected BaseMapper<SystemLottery> getBaseMapper() {
        return systemLotteryMapper;
    }
	
}
