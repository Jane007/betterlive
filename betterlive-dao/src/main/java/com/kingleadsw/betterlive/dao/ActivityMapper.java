package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Activity;


/**
 * 活动  dao层
 * 2017-03-15 by chen
 */
public interface ActivityMapper extends BaseMapper<Activity>{

	int insertActivity(Activity activity);                //增加活动
	
	int updateActivityByAid(PageData pd);                 //根据活动ID修改活动
	 
	int deleteByAid(String aId);                          //根据活动ID删除活动 

    List<Activity> findActivityListPage(PageData pd);     //根据条件分页查询活动
    
    List<Activity> findListActivity(PageData pd);     	  //根据条件查询全部活动
    
    Activity findActivity(PageData pd);                   //根据条件查询单个活动详细
   
}