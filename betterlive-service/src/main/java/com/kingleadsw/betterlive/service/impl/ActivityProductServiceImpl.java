package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ActivityProductMapper;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.service.ActivityProductService;



/**
 * 活动对应的商品规格  service 实现层
 * 2017-03-15 by chen
 */
@Service
public class ActivityProductServiceImpl extends BaseServiceImpl<ActivityProduct>  implements ActivityProductService{
	@Autowired
	private  ActivityProductMapper activityProductMapper;

	//活动对应的商品规格
	@Override
	public int addBatchActivityProduct(List<ActivityProduct> list) {
		return activityProductMapper.addBatchActivityProduct(list);
	}
	
	//根据活动ID删除活动对应的商品规格
	@Override
	public int deleteActivityProductByAid(String activityId) {
		return activityProductMapper.deleteActivityProductByAid(activityId);
	}

	/**
	 * 根据商品id，查询商品的活动最低价格
	 * @param productId 商品
	 * @return
	 */
	@Override
	protected BaseMapper<ActivityProduct> getBaseMapper() {
		return activityProductMapper;
	}

	@Override
	public float queryMinProductPrice(PageData pd) {
		return activityProductMapper.queryMinProductPrice(pd);
	}

	@Override
	public ActivityProduct queryActivityProductByParams(PageData pd) {
		return activityProductMapper.queryActivityProductByParams(pd);
	}
	

}
