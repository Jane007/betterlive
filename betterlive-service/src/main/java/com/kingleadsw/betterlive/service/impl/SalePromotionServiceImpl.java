package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SalePromotionMapper;
import com.kingleadsw.betterlive.model.SalePromotion;
import com.kingleadsw.betterlive.service.SalePromotionService;
/**
 * 促销活动服务层
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
@Service
public class SalePromotionServiceImpl extends BaseServiceImpl<SalePromotion> implements SalePromotionService{
	@Autowired
	private SalePromotionMapper salePromotionMapper;
	
	@Override
	protected BaseMapper<SalePromotion> getBaseMapper() {
		return salePromotionMapper;
	}

}
