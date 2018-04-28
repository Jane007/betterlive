package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UserGroup;
import com.kingleadsw.betterlive.service.UserGroupService;
import com.kingleadsw.betterlive.vo.UserGroupVo;

@Component
@Transactional
public class UserGroupManagerImpl extends BaseManagerImpl<UserGroupVo, UserGroup> implements UserGroupManager {

	@Autowired
	private UserGroupService userGroupService;
	@Override
	protected BaseService<UserGroup> getService() {
		return userGroupService;
	}
	@Override
	public int insertUserGroup(UserGroupVo userGroupVo) {
		UserGroup userGroup = new UserGroup();
		userGroup.setOriginator(userGroupVo.getOriginator());
		userGroup.setProductId(userGroupVo.getProductId());
		userGroup.setStatus(userGroupVo.getStatus());
		userGroup.setSpecId(userGroupVo.getSpecId());
		userGroup.setGroupId(userGroupVo.getGroupId());
		userGroup.setSpecialId(userGroupVo.getSpecialId());
		int count = userGroupService.insertUserGroup(userGroup);
		if(count > 0 && userGroup.getUserGroupId() > 0){
			userGroupVo.setUserGroupId(userGroup.getUserGroupId());
		}
		return count;
	}
}
