package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.FeedBackMapper;
import com.kingleadsw.betterlive.model.FeedBack;
import com.kingleadsw.betterlive.service.FeedBackService;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack> implements
		FeedBackService {
	@Autowired
	private FeedBackMapper feedBackMapper;

	@Override
	public boolean insertFeedBack(FeedBack feedBack) {
		return feedBackMapper.insertFeedBack(feedBack) > 0;
	}

	@Override
	public List<FeedBack> selectFeedBack() {
		return feedBackMapper.selectFeedBack();
	}

	@Override
	protected BaseMapper<FeedBack> getBaseMapper() {
		return feedBackMapper;
	}
}
