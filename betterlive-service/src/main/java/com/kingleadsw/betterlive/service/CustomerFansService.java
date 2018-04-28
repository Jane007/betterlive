package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerFans;

public interface CustomerFansService extends BaseService<CustomerFans> {

	CustomerFans queryFansCount(PageData pd);

	List<CustomerFans> queryMyFansListPage(PageData pd);
	
	List<CustomerFans> queryMyConcernedListPage(PageData pd);

	List<CustomerFans> queryOtherFansListPage(PageData pd);

	List<CustomerFans> queryOtherConcernedListPage(PageData pd);

	List<CustomerFans> queryFindFriendListPage(PageData pd);

}
