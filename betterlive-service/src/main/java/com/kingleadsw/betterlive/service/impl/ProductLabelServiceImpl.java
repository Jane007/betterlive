package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ProductLabelMapper;
import com.kingleadsw.betterlive.model.ProductLabel;
import com.kingleadsw.betterlive.service.ProductLabelService;

@Service("/productLabelServiceImpl")
public class ProductLabelServiceImpl extends BaseServiceImpl<ProductLabel>  implements ProductLabelService {

	@Autowired
	private ProductLabelMapper productLabelMapper;
	
	
	@Override
	protected BaseMapper<ProductLabel> getBaseMapper() {
		return productLabelMapper;
	}


	@Override
	public int insertProductlabel(ProductLabel productlabel) {
		return productLabelMapper.insertProductlabel(productlabel);
	}


	@Override
	public int delProductlabel(int productLabelId) {
		return productLabelMapper.delProductlabel(productLabelId);
	}

	@Override
	public int editproductlabel(ProductLabel productlabel) {
		return productLabelMapper.editproductlabel(productlabel);
	}


	

	
	
	

}
