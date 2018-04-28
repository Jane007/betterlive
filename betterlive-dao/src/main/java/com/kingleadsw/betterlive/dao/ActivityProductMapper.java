package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ActivityProduct;


/**
 * 活动对应的商品规格 dao层
 * 2017-03-15 by chen
 */
public interface ActivityProductMapper extends BaseMapper<ActivityProduct>{

	int addBatchActivityProduct(List<ActivityProduct> list);              //活动对应的商品规格
	
	int deleteActivityProductByAid(String activityId);                    //根据活动ID删除活动对应的商品规格
	
	/**
	 * 根据商品id，查询商品的活动最低价格
	 * @param PageData productId：商品id，activityType：活动类型，1：预售；2：专题
	 * @return
	 */
	float queryMinProductPrice(PageData pd);

	/**
	 * 根据条件查询活动商品
	 * @param pd
	 * @return
	 */
	ActivityProduct queryActivityProductByParams(PageData pd);
   
}