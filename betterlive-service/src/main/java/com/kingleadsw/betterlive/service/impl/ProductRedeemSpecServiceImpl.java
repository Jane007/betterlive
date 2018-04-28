package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ProductRedeemSpecMapper;
import com.kingleadsw.betterlive.model.ProductRedeemSpec;
import com.kingleadsw.betterlive.service.ProductRedeemSpecService;

@Service
public class ProductRedeemSpecServiceImpl extends BaseServiceImpl<ProductRedeemSpec>  implements ProductRedeemSpecService {

	@Autowired
	private ProductRedeemSpecMapper productRedeemSpecMapper;
	
	@Override
	protected BaseMapper<ProductRedeemSpec> getBaseMapper() {
		return productRedeemSpecMapper;
	}

	@Override
	public ProductRedeemSpec queryMinRedeemSpec(PageData ordParams) {
		return productRedeemSpecMapper.queryMinRedeemSpec(ordParams);
	}

}
