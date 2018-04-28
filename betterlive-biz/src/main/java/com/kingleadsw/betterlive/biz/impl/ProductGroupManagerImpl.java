package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ProductGroupManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductGroup;
import com.kingleadsw.betterlive.service.ProductGroupService;
import com.kingleadsw.betterlive.vo.ProductGroupVo;

@Component
@Transactional
public class ProductGroupManagerImpl extends BaseManagerImpl<ProductGroupVo, ProductGroup> implements ProductGroupManager {

	@Autowired
	private ProductGroupService productGroupService;
	@Override
	protected BaseService<ProductGroup> getService() {
		return productGroupService;
	}
}
