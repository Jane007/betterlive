package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.vo.ProductSpecVo;



/**
 * 商品规格
 * 2017-03-07 by chen
 */
public interface ProductSpecManager extends BaseManager<ProductSpecVo,ProductSpec>{
	int addBatchProductSpec(List<ProductSpecVo> list);                    //新增商品规格
	
	int updateProductSpecBySid(ProductSpecVo productSpecVo);               //编辑商品规格
	
	int deleteProductSpecBySid(String pId);              		  		//删除商品规格
	
	ProductSpecVo queryProductSpecByOption(PageData pagedata);           //查询单个商品规格
	
	List<ProductSpecVo> queryProductSpecListPage(PageData pagedata);     //根据条件分页查询商品规格
	
	List<ProductSpecVo> queryListProductSpec(PageData pagedata);     		//根据条件查询全部商品规格
}