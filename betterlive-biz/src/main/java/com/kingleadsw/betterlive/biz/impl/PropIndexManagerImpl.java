package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PropIndexManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PropIndex;
import com.kingleadsw.betterlive.service.PropIndexService;
import com.kingleadsw.betterlive.vo.PropIndexVo;

@Component
@Transactional
public class PropIndexManagerImpl extends BaseManagerImpl<PropIndexVo, PropIndex> implements PropIndexManager {

	@Autowired
	private PropIndexService propIndexService;
	
	@Override
	protected BaseService<PropIndex> getService() {
		return propIndexService;
	}

	@Override
	public PropIndexVo queryOneForIndex(PageData pd) {
		return po2voer.transfer(new PropIndexVo(), propIndexService.queryOneForIndex(pd));
	}

}
