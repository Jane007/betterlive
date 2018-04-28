package com.kingleadsw.betterlive.biz;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.PropIndex;
import com.kingleadsw.betterlive.vo.PropIndexVo;

public interface PropIndexManager extends BaseManager<PropIndexVo, PropIndex> {

	
	/**
	 * 查询放首页下载占位地址
	 * @param
	 * @return
	 * @author zhangjing 2018年4月11日 上午11:06:56
	 */
	PropIndexVo queryOneForIndex(PageData pd);
}
