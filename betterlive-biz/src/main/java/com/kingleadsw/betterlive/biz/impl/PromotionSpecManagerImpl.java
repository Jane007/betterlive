package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PromotionSpec;
import com.kingleadsw.betterlive.service.PromotionSpecService;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;

/**
 * 活动 规格实际交互实现层
 * 
 */
@Component
@Transactional
public class PromotionSpecManagerImpl extends BaseManagerImpl<PromotionSpecVo,PromotionSpec> implements PromotionSpecManager{
	@Autowired
	private PromotionSpecService  promotionSpecService;


	@Override
	protected BaseService<PromotionSpec> getService() {
		return promotionSpecService;
	}

}
