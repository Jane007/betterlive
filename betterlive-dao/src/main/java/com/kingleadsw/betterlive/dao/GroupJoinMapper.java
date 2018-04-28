package com.kingleadsw.betterlive.dao;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.GroupJoin;

public interface GroupJoinMapper extends BaseMapper<GroupJoin> {

	int insertGroupJoin(GroupJoin groupJoin);

}
