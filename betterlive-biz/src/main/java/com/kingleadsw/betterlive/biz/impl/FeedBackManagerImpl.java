package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.FeedBackManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.FeedBack;
import com.kingleadsw.betterlive.service.FeedBackService;
import com.kingleadsw.betterlive.vo.FeedBackVo;

@Component
@Transactional
public class FeedBackManagerImpl extends BaseManagerImpl<FeedBackVo, FeedBack>
		implements FeedBackManager {

	@Autowired
	private FeedBackService feedBackService;

	@Override
	protected BaseService<FeedBack> getService() {
		// TODO Auto-generated method stub
		return feedBackService;
	}
}
