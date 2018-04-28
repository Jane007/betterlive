package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.service.ActivityProductService;
import com.kingleadsw.betterlive.vo.ActivityProductVo;

/**
 * 活动对应的商品规格 实际交互实现层
 * 2017-03-15 by chen
 */
@Component
@Transactional
public class ActivityProductManagerImpl extends BaseManagerImpl<ActivityProductVo,ActivityProduct> implements ActivityProductManager{
	@Autowired
	private ActivityProductService activityProductService;
	
	
	 //活动对应的商品规格
	@Override
	public int addBatchActivityProduct(List<ActivityProductVo> list) {
		List<ActivityProduct> lists=vo2poer.transfer(ActivityProduct.class, list);
		return activityProductService.addBatchActivityProduct(lists);
	}
	
	//根据活动ID删除活动对应的商品规格
	@Override
	public int deleteActivityProductByAid(String activityId) {
		return activityProductService.deleteActivityProductByAid(activityId);
	}

	@Override
	protected BaseService<ActivityProduct> getService() {
		return activityProductService;
	}

	/**
	 * 根据商品id，查询商品的活动最低价格
	 * @param PageData productId：商品id，activityType：活动类型，1：预售；2：专题
	 * @return
	 */
	@Override
	public float queryMinProductPrice(PageData pd) {
		return activityProductService.queryMinProductPrice(pd);
	}
}
