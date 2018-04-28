package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.UserGroupMapper;
import com.kingleadsw.betterlive.model.UserGroup;
import com.kingleadsw.betterlive.service.UserGroupService;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroup> implements UserGroupService {

	@Autowired
    private UserGroupMapper userGroupMapper;
	    
    @Override
    protected BaseMapper<UserGroup> getBaseMapper() {
        return userGroupMapper;
    }

	@Override
	public int insertUserGroup(UserGroup userGroup) {
		return userGroupMapper.insertUserGroup(userGroup);
	}
	
	@Override
	public List<UserGroup> queryListPage(PageData pd) {
		return userGroupMapper.queryListPage(pd);
	}
}
