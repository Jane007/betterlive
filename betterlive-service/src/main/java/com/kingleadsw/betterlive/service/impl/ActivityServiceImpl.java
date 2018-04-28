package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ActivityMapper;
import com.kingleadsw.betterlive.model.Activity;
import com.kingleadsw.betterlive.service.ActivityService;




/**
 * 活动  service 实现层
 * 2017-03-15 by chen
 */
@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity>  implements ActivityService{
	@Autowired
	private ActivityMapper activityMapper;

	 //增加活动
	@Override
	public int insertActivity(Activity activity) {
		return activityMapper.insertActivity(activity);
	}
	
	//根据活动ID修改活动
	@Override
	public int updateActivityByAid(PageData pd) {
		return activityMapper.updateActivityByAid(pd);
	}
	
	//根据活动ID删除活动 
	@Override
	public int deleteByAid(String aId) {
		return activityMapper.deleteByAid(aId);
	}
	
	 //根据条件分页查询活动
	@Override
	public List<Activity> findActivityListPage(PageData pd) {
		return activityMapper.findActivityListPage(pd);
	}
	
	 //根据条件查询全部活动
	@Override
	public List<Activity> findListActivity(PageData pd) {
		return activityMapper.findListActivity(pd);
	}
	
	//根据条件查询单个活动详细
	@Override
	public Activity findActivity(PageData pd) {
		return activityMapper.findActivity(pd);
	}

	@Override
	protected BaseMapper<Activity> getBaseMapper() {
		return activityMapper;
	}
	

}
