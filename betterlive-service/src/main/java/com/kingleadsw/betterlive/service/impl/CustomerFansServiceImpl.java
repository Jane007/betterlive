package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerFansMapper;
import com.kingleadsw.betterlive.model.CustomerFans;
import com.kingleadsw.betterlive.service.CustomerFansService;

@Service
public class CustomerFansServiceImpl extends BaseServiceImpl<CustomerFans> implements CustomerFansService{

	@Autowired
	private CustomerFansMapper customerFansMapper;
	
	@Override
	protected BaseMapper<CustomerFans> getBaseMapper() {
		return customerFansMapper;
	}

	@Override
	public CustomerFans queryFansCount(PageData pd) {
		return customerFansMapper.queryFansCount(pd);
	}

	@Override
	public List<CustomerFans> queryMyFansListPage(PageData pd) {
		return customerFansMapper.queryMyFansListPage(pd);
	}
	
	@Override
	public List<CustomerFans> queryMyConcernedListPage(PageData pd) {
		return customerFansMapper.queryMyConcernedListPage(pd);
	}

	@Override
	public List<CustomerFans> queryOtherFansListPage(PageData pd) {
		return customerFansMapper.queryOtherFansListPage(pd);
	}

	@Override
	public List<CustomerFans> queryOtherConcernedListPage(PageData pd) {
		return customerFansMapper.queryOtherConcernedListPage(pd);
	}

	@Override
	public List<CustomerFans> queryFindFriendListPage(PageData pd) {
		return customerFansMapper.queryFindFriendListPage(pd);
	}

}
