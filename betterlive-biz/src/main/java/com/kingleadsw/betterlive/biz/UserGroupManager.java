package com.kingleadsw.betterlive.biz;


import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.UserGroup;
import com.kingleadsw.betterlive.vo.UserGroupVo;

public interface UserGroupManager extends BaseManager<UserGroupVo,UserGroup> {
	
	public int insertUserGroup(UserGroupVo userGroupVo);
	
}
