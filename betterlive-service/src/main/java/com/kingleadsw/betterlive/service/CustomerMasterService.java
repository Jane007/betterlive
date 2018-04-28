package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerMaster;

public interface CustomerMasterService extends BaseService<CustomerMaster> {

	List<CustomerMaster> queryHotList(PageData pd);

}
