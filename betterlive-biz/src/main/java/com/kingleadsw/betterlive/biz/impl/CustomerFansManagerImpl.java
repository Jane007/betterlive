package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerFans;
import com.kingleadsw.betterlive.service.CustomerFansService;
import com.kingleadsw.betterlive.vo.CustomerFansVo;

@Component
@Transactional
public class CustomerFansManagerImpl extends BaseManagerImpl<CustomerFansVo, CustomerFans> implements CustomerFansManager{

	@Autowired
	private CustomerFansService customerFansService;
	
	@Override
	protected BaseService<CustomerFans> getService() {
		return customerFansService;
	}

	@Override
	public CustomerFansVo queryFansCount(PageData pd) {
		return po2voer.transfer(new CustomerFansVo(),customerFansService.queryFansCount(pd));
	}

	@Override
	public List<CustomerFansVo> queryMyFansListPage(PageData pd) {
		return generator.transfer(CustomerFansVo.class, customerFansService.queryMyFansListPage(pd));
	}
	
	@Override
	public List<CustomerFansVo> queryMyConcernedListPage(PageData pd) {
		return generator.transfer(CustomerFansVo.class, customerFansService.queryMyConcernedListPage(pd));
	}

	@Override
	public List<CustomerFansVo> queryOtherFansListPage(PageData pd) {
		return generator.transfer(CustomerFansVo.class, customerFansService.queryOtherFansListPage(pd));
	}

	@Override
	public List<CustomerFansVo> queryOtherConcernedListPage(PageData pd) {
		return generator.transfer(CustomerFansVo.class, customerFansService.queryOtherConcernedListPage(pd));
	}

	@Override
	public List<CustomerFansVo> queryFindFriendListPage(PageData pd) {
		return generator.transfer(CustomerFansVo.class, customerFansService.queryFindFriendListPage(pd));
	}

}
