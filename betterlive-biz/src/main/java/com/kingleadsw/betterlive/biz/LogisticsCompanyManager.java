package com.kingleadsw.betterlive.biz;

import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.LogisticsCompany;
import com.kingleadsw.betterlive.vo.LogisticsCompanyVo;


public interface LogisticsCompanyManager  extends BaseManager<LogisticsCompanyVo,LogisticsCompany>{

	Map<String,Object> saveLogisticsInfo(PageData pd);

}
