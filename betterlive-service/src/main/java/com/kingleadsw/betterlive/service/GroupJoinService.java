package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GroupJoin;

public interface GroupJoinService extends BaseService<GroupJoin> {

	int insertGroupJoin(GroupJoin groupJoin);

}
