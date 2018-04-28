package com.kingleadsw.betterlive.biz.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductRedeemSpec;
import com.kingleadsw.betterlive.service.ProductRedeemSpecService;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;

@Component
@Transactional
public class ProductRedeemSpecManagerImpl extends BaseManagerImpl<ProductRedeemSpecVo, ProductRedeemSpec> implements ProductRedeemSpecManager {
	
	protected Logger logger = Logger.getLogger(ProductRedeemSpecManagerImpl.class);
	
	@Autowired
	private ProductRedeemSpecService productRedeemSpecService;

	@Override
	protected BaseService<ProductRedeemSpec> getService() {
		return productRedeemSpecService;
	}

}
