package com.kingleadsw.betterlive.dao;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.UserRole;

/**
 * 角色用户
 * 2017-03-07 by chen
 */
public interface UserRoleMapper extends BaseMapper<UserRole>{


	/**
	 * 根据用户ID删除
	 * @param userId
	 * @return
	 */
	int deleteByUserId(int userId);
}