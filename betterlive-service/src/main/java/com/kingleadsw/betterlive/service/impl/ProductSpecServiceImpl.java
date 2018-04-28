package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ProductSpecMapper;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.service.ProductSpecService;

import java.util.List;



/**
 * 管理员  service 实现层
 * 2017-03-07 by chen
 */
@Service
public class ProductSpecServiceImpl extends BaseServiceImpl<ProductSpec> implements ProductSpecService{
	
	@Autowired
	private ProductSpecMapper productSpecMapper;
	
	
	//新增商品规格
	@Override
	public int addBatchProductSpec(List<ProductSpec> list) {
		return productSpecMapper.addBatchProductSpec(list);
	}

	 //编辑商品规格
	@Override
	public int updateProductSpecBySid(ProductSpec productSpec) {
		return productSpecMapper.updateProductSpecBySid(productSpec);
	}
	
	//删除商品规格
	@Override
	public int deleteProductSpecBySid(String pId) {
		return productSpecMapper.deleteProductSpecBySid(pId);
	}
	
	 //查询单个商品规格
	@Override
	public ProductSpec queryProductSpecByOption(PageData pagedata) {
		return productSpecMapper.queryProductSpecByOption(pagedata);
	}
	
	//根据条件分页查询商品规格
	@Override
	public List<ProductSpec> queryProductSpecListPage(PageData pagedata) {
		return productSpecMapper.queryProductSpecListPage(pagedata);
	}

	//根据条件查询全部商品规格
	@Override
	public List<ProductSpec> queryListProductSpec(PageData pagedata) {
		return productSpecMapper.queryListProductSpec(pagedata);
	}

	@Override
	protected BaseMapper<ProductSpec> getBaseMapper() {
		return productSpecMapper;
	}
    
	
}
