package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.RoleManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Role;
import com.kingleadsw.betterlive.service.RoleService;
import com.kingleadsw.betterlive.vo.RoleVo;

/**
 * author szx
 * date 2016-11-18 15 59
 **/
@Component
@Transactional
public class RoleManagerImpl extends BaseManagerImpl<RoleVo,Role> implements RoleManager{
    @Autowired
     private RoleService roleService;
    @Override
    protected BaseService<Role> getService() {        return roleService;    }}