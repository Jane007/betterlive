package com.kingleadsw.betterlive.biz;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.GroupJoin;
import com.kingleadsw.betterlive.vo.GroupJoinVo;

public interface GroupJoinManager extends BaseManager<GroupJoinVo,GroupJoin> {

	int insertGroupJoin(GroupJoinVo groupJoin);

}
