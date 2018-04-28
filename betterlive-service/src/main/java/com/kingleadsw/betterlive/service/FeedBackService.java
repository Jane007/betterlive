package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.FeedBack;

public interface FeedBackService extends BaseService<FeedBack> {

	/**
	 * 发表意见反馈
	 * 
	 * @param feedBack
	 * @return
	 */
	public boolean insertFeedBack(FeedBack feedBack);

	/**
	 * 查询意见反馈
	 * 
	 * @return
	 */
	public List<FeedBack> selectFeedBack();

}
