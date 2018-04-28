package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PromotionSpecMapper;
import com.kingleadsw.betterlive.model.PromotionSpec;
import com.kingleadsw.betterlive.service.PromotionSpecService;
/**
 * 促销规格关联服务层
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
@Service
public class PromotionSpecServiceImpl extends BaseServiceImpl<PromotionSpec> implements PromotionSpecService{
	@Autowired
	private PromotionSpecMapper promotionSpecService;
	
	
	@Override
	protected BaseMapper<PromotionSpec> getBaseMapper() {
		return promotionSpecService;
	}

}
