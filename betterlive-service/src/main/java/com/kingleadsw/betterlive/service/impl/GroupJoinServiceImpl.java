package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.GroupJoinMapper;
import com.kingleadsw.betterlive.model.GroupJoin;
import com.kingleadsw.betterlive.service.GroupJoinService;

@Service
public class GroupJoinServiceImpl extends BaseServiceImpl<GroupJoin> implements GroupJoinService {

	@Autowired
    private GroupJoinMapper groupJoinMapper;
	    
    @Override
    protected BaseMapper<GroupJoin> getBaseMapper() {
        return groupJoinMapper;
    }

	@Override
	public int insertGroupJoin(GroupJoin groupJoin) {
		return groupJoinMapper.insertGroupJoin(groupJoin);
	}
}
