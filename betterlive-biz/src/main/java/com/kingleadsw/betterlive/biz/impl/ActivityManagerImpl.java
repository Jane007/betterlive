package com.kingleadsw.betterlive.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ActivityManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Activity;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.service.ActivityService;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.ActivityVo;

/**
 * 活动 实际交互实现层
 * 2017-03-15 by chen
 */
@Component
@Transactional
public class ActivityManagerImpl extends BaseManagerImpl<ActivityVo,Activity> implements ActivityManager{
	@Autowired
	private ActivityService activityService;

	
	  //增加活动
	@Override
	public int insertActivity(ActivityVo activity) {
		Activity activities=vo2poer.transfer(new Activity(), activity);
		int ret=activityService.insertActivity(activities);
		activity.setActivity_id(activities.getActivity_id());
		return ret;
	}
	
	//根据活动ID修改活动
	@Override
	public int updateActivityByAid(PageData pd) {
		return activityService.updateActivityByAid(pd);
	}

	//根据活动ID删除活动 
	@Override
	public int deleteByAid(String aId) {
		return activityService.deleteByAid(aId);
	}
	
	 //根据条件分页查询活动
	@Override
	public List<ActivityVo> findActivityListPage(PageData pd) {
		List<Activity> listActivity=activityService.findActivityListPage(pd);
		List<ActivityVo> listActivityVo=null;
		
		if(null !=listActivity && listActivity.size()>0){
			listActivityVo=new ArrayList<ActivityVo>();
			for (Activity activity : listActivity) {
				ActivityVo activityVo=po2voer.transfer(new ActivityVo(),activity);
				List<ActivityProductVo> listActivityProduct= generator.transfer(ActivityVo.class,activity.getListActivityProduct()) ;
				activityVo.setListActivityProductVo(listActivityProduct);
				listActivityVo.add(activityVo);
			}
		}
		return listActivityVo;
	}

	 //根据条件查询全部活动
	@Override
	public List<ActivityVo> findListActivity(PageData pd) {
		
		List<Activity> listActivity=activityService.findListActivity(pd);
		List<ActivityVo> listActivityVo=null;
		
		if(null !=listActivity && listActivity.size()>0){
			listActivityVo=new ArrayList<ActivityVo>();
			for (Activity activity : listActivity) {
				ActivityVo activityVo=po2voer.transfer(new ActivityVo(),activity);
				List<ActivityProductVo> listActivityProduct= generator.transfer(ActivityVo.class,activity.getListActivityProduct()) ;
				activityVo.setListActivityProductVo(listActivityProduct);
				listActivityVo.add(activityVo);
			}
		}
		return listActivityVo;
	}

	 //根据条件查询单个活动详细
	@Override
	public ActivityVo findActivity(PageData pd) {
		
		Activity activity=activityService.findActivity(pd);
		if(null == activity){
			return null;
		}
		
		ActivityVo activityVo=po2voer.transfer(new ActivityVo(),activity);
		if(CollectionUtils.isNotEmpty(activity.getListActivityProduct())){
			List<ActivityProductVo> listActivityProductVo=new ArrayList<ActivityProductVo>(activity.getListActivityProduct().size());
			ActivityProductVo activityProductVo=null;
			for (ActivityProduct activityProduct: activity.getListActivityProduct() ) {
				activityProductVo=new ActivityProductVo();
				
			    activityProductVo.setSpec_id(activityProduct.getSpec_id());
			    activityProductVo.setActivity_spec_id(activityProduct.getActivity_spec_id());
			    activityProductVo.setActivity_price(activityProduct.getActivity_price());
			    activityProductVo.setSpec_name(activityProduct.getSpec_name());
			    activityProductVo.setSpec_price(activityProduct.getSpec_price());
			    
				listActivityProductVo.add(activityProductVo);
			}
			activityVo.setListActivityProductVo(listActivityProductVo);
		}
		
		return activityVo ;
	}

	@Override
	protected BaseService<Activity> getService() {
		return activityService;
	}
	
	
}
