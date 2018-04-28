package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.RoleMenuManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.RoleMenu;
import com.kingleadsw.betterlive.service.RoleMenuService;
import com.kingleadsw.betterlive.vo.RoleMenuVo;

/**
 * 角色菜单
 * 2017-03-07 by chen
 */
@Component
@Transactional
public class RoleMenuManagerImpl extends BaseManagerImpl<RoleMenuVo,RoleMenu> implements RoleMenuManager{
    @Autowired
     private RoleMenuService roleMenuService;
    @Override
    protected BaseService<RoleMenu> getService() {        return roleMenuService;    }}