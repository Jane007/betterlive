package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.vo.ActivityProductVo;


/**
 * 活动对应的商品规格 实际交互层
 * 2017-03-15 by chen
 */
public interface ActivityProductManager extends BaseManager<ActivityProductVo,ActivityProduct>{
	
	int addBatchActivityProduct(List<ActivityProductVo> list);              //活动对应的商品规格
	
	int deleteActivityProductByAid(String activityId);                    //根据活动ID删除活动对应的商品规格
	
	/**
	 * 根据商品id，查询商品的活动最低价格
	 * @param PageData productId：商品id，activityType：活动类型，1：预售；2：专题
	 * @return
	 */
	float queryMinProductPrice(PageData pd);
	
}
