package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SysGroupMapper;
import com.kingleadsw.betterlive.model.SysGroup;
import com.kingleadsw.betterlive.service.SysGroupService;

@Service
public class SysGroupServiceImpl extends BaseServiceImpl<SysGroup> implements SysGroupService {
	
    @Autowired
    private SysGroupMapper sysGroupMapper;
    
    @Override
    protected BaseMapper<SysGroup> getBaseMapper() {
        return sysGroupMapper;
    }

}
