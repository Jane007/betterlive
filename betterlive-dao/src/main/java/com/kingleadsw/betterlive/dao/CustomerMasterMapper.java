package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerMaster;

public interface CustomerMasterMapper extends BaseMapper<CustomerMaster>{

	List<CustomerMaster> queryHotList(PageData pd);

}
