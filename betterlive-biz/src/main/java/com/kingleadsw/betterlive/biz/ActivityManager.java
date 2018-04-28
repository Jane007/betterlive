package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Activity;
import com.kingleadsw.betterlive.vo.ActivityVo;


/**
 * 活动 实际交互层
 * 2017-03-15 by chen
 */
public interface ActivityManager extends BaseManager<ActivityVo,Activity>{
	
	int insertActivity(ActivityVo activity);                //增加活动
	
	int updateActivityByAid(PageData pd);                   //根据活动ID修改活动
	 
	int deleteByAid(String aId);                            //根据活动ID删除活动 

    List<ActivityVo> findActivityListPage(PageData pd);     //根据条件分页查询活动
    
    List<ActivityVo> findListActivity(PageData pd);     	//根据条件查询全部活动
    
    ActivityVo findActivity(PageData pd);                   //根据条件查询单个活动详细
}
