package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.FeedBack;

public interface FeedBackMapper extends BaseMapper<FeedBack> {
	int insertFeedBack(FeedBack feedBack); // 新增反馈

	List<FeedBack> selectFeedBack(); // 查询反馈
}
