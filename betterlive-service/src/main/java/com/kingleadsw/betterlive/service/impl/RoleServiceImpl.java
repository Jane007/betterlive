package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.RoleMapper;
import com.kingleadsw.betterlive.model.Role;
import com.kingleadsw.betterlive.service.RoleService;



/**
 * 角色
 * 2017-03-07 by chen 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
    @Autowired
    private RoleMapper roleMapper;

    @Override
    protected BaseMapper<Role> getBaseMapper() {
        return roleMapper;
    }}