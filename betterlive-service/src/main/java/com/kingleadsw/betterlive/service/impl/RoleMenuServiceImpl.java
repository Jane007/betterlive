package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.RoleMenuMapper;
import com.kingleadsw.betterlive.model.RoleMenu;
import com.kingleadsw.betterlive.service.RoleMenuService;


/**
 * author szx
 * date 2016-11-18 15 40
 **/
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements RoleMenuService{
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    protected BaseMapper<RoleMenu> getBaseMapper() {
        return roleMenuMapper;
    }}