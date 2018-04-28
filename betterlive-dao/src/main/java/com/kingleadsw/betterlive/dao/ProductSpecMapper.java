package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductSpec;


/**
 * 商品规格  dao层
 * 2017-03-08 by chen
 */
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {
	
	int addBatchProductSpec(List<ProductSpec> list);                    //新增商品规格
	
	int updateProductSpecBySid(ProductSpec productSpec);               //编辑商品规格
	
	int deleteProductSpecBySid(String pId);              		  //删除商品规格
	
	ProductSpec queryProductSpecByOption(PageData pagedata);           //查询单个商品规格
	
	List<ProductSpec> queryProductSpecListPage(PageData pagedata);     //根据条件分页查询商品规格
	
	List<ProductSpec> queryListProductSpec(PageData pagedata);     		//根据条件查询全部商品规格
	
	
}
