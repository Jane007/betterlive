package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.UserGroup;
import com.kingleadsw.betterlive.model.UserSingleCoupon;

public interface UserGroupMapper extends BaseMapper<UserGroup> {

	int insertUserGroup(UserGroup userGroup);
	List<UserGroup> queryListPage(PageData pd);
}
