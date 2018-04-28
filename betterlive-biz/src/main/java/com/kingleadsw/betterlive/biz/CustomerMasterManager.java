package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerMaster;
import com.kingleadsw.betterlive.vo.CustomerMasterVo;

public interface CustomerMasterManager  extends BaseManager<CustomerMasterVo, CustomerMaster> {

	List<CustomerMasterVo> queryHotList(PageData pd);

}
