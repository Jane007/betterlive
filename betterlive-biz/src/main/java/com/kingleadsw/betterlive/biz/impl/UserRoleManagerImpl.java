package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.UserRoleManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UserRole;
import com.kingleadsw.betterlive.service.UserRoleService;
import com.kingleadsw.betterlive.vo.UserRoleVo;


/**
 * 角色用户
 * 2017-03-07 by chen
 */
@Component
@Transactional
public class UserRoleManagerImpl extends BaseManagerImpl<UserRoleVo,UserRole> implements UserRoleManager{
    @Autowired
     private UserRoleService userRoleService;
    @Override
    protected BaseService<UserRole> getService() {        return userRoleService;    }}