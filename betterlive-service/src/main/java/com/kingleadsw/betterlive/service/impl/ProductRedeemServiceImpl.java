package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ProductRedeemMapper;
import com.kingleadsw.betterlive.model.ProductRedeem;
import com.kingleadsw.betterlive.service.ProductRedeemService;

@Service
public class ProductRedeemServiceImpl extends BaseServiceImpl<ProductRedeem>  implements ProductRedeemService {

	@Autowired
	private ProductRedeemMapper productRedeemMapper;
	
	@Override
	protected BaseMapper<ProductRedeem> getBaseMapper() {
		return productRedeemMapper;
	}

	@Override
	public List<ProductRedeem> queryProductRedeemsListPage(PageData pd) {
		return productRedeemMapper.queryProductRedeemsListPage(pd);
	}

}
