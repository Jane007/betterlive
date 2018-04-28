package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SysCashMapper;
import com.kingleadsw.betterlive.model.SysCash;
import com.kingleadsw.betterlive.service.SysCashService;

@Service
public class SysCashServiceImpl extends BaseServiceImpl<SysCash> implements SysCashService {
	
    @Autowired
    private SysCashMapper sysCashMapper;
    
    @Override
    protected BaseMapper<SysCash> getBaseMapper() {
        return sysCashMapper;
    }

}
