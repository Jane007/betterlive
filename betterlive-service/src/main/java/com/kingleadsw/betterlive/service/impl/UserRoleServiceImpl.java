package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.UserRoleMapper;
import com.kingleadsw.betterlive.model.UserRole;
import com.kingleadsw.betterlive.service.UserRoleService;

/**
 * 用户角色
 * 2017-03-07  by chen
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    protected BaseMapper<UserRole> getBaseMapper() {
        return userRoleMapper;
    }

	@Override
	public int deleteByUserId(int userId) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteByUserId(userId);
	}}