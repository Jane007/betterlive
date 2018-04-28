package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UserGroup;

public interface UserGroupService extends BaseService<UserGroup> {

	int insertUserGroup(UserGroup userGroup);
}
