package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GroupJoin;
import com.kingleadsw.betterlive.service.GroupJoinService;
import com.kingleadsw.betterlive.vo.GroupJoinVo;

@Component
@Transactional
public class GroupJoinManagerImpl extends BaseManagerImpl<GroupJoinVo, GroupJoin> implements GroupJoinManager {

	@Autowired
	private GroupJoinService groupJoinService;
	@Override
	protected BaseService<GroupJoin> getService() {
		return groupJoinService;
	}
	
	@Override
	public int insertGroupJoin(GroupJoinVo groupJoinVo) {
		GroupJoin groupJoin = new GroupJoin();
		groupJoin.setBuyNum(groupJoinVo.getBuyNum());
		groupJoin.setCustomerId(groupJoinVo.getCustomerId());
		groupJoin.setOriginator(groupJoinVo.getOriginator());
		groupJoin.setProductId(groupJoinVo.getProductId());
		groupJoin.setStatus(groupJoinVo.getStatus());
		groupJoin.setSpecId(groupJoinVo.getSpecId());
		groupJoin.setGroupId(groupJoinVo.getGroupId());
		groupJoin.setUserGroupId(groupJoinVo.getUserGroupId());
		groupJoin.setSpecialId(groupJoinVo.getSpecialId());
		int count = groupJoinService.insertGroupJoin(groupJoin);
		if(count > 0 && groupJoin.getGroupJoinId() > 0){
			groupJoinVo.setGroupJoinId(groupJoin.getGroupJoinId());
		}
		return count;
	}

}
