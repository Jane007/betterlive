package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UserRole;


/**
 * 用户角色
 * 2017-03-07 by chen 
 */
public interface UserRoleService extends BaseService<UserRole>{
	
	/**
	 * 根据用户ID删除
	 * @param userId
	 * @return
	 */
	int deleteByUserId(int userId);
}