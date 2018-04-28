package com.kingleadsw.betterlive.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ProductGroupMapper;
import com.kingleadsw.betterlive.model.ProductGroup;
import com.kingleadsw.betterlive.service.ProductGroupService;

@Service
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroup> implements ProductGroupService {

	@Autowired
    private ProductGroupMapper productGroupMapper;
	    
    @Override
    protected BaseMapper<ProductGroup> getBaseMapper() {
        return productGroupMapper;
    }
}
