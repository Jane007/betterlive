package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PropIndex;

public interface PropIndexService extends BaseService<PropIndex> {
	/**
	 * 查询放首页下载占位地址
	 * @param
	 * @return
	 * @author zhangjing 2018年4月11日 上午11:06:56
	 */
	PropIndex queryOneForIndex(PageData pd);

}
