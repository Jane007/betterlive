package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.kingleadsw.betterlive.biz.SystemLotteryManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SystemLottery;
import com.kingleadsw.betterlive.service.SystemLotteryService;
import com.kingleadsw.betterlive.vo.SystemLotteryVo;

/**
 * 2018-03-28 by fang
 */
@Component
@Transactional
public class SystemLotteryManagerImpl extends BaseManagerImpl<SystemLotteryVo,SystemLottery> implements SystemLotteryManager{
    @Autowired
     private SystemLotteryService systemLotteryService;
    
    @Override
    protected BaseService<SystemLottery> getService() {
    	return systemLotteryService;
    }
}
