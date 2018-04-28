package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.vo.ProductSpecVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 2017-03-07 by chen
 */
@Component
@Transactional
public class ProductSpecManagerImpl extends BaseManagerImpl<ProductSpecVo,ProductSpec> implements ProductSpecManager{
    @Autowired
     private ProductSpecService productSpecService;

    
    //新增商品规格
	@Override
	public int addBatchProductSpec(List<ProductSpecVo> list) {
		int ret=0;
		
		List<ProductSpec> listSpec=new ArrayList<ProductSpec>();
		
		for (int i = 0; i < list.size(); i++) {
			ProductSpec productSpec=vo2poer.transfer(new ProductSpec(), list.get(i));
			listSpec.add(productSpec);
		}
		
		ret=productSpecService.addBatchProductSpec(listSpec);
		
		return ret;
	}

	
	//编辑商品规格
	@Override
	public int updateProductSpecBySid(ProductSpecVo productSpecVo) {
		int ret=0;
		ProductSpec productSpec = vo2poer.transfer(new ProductSpec(), productSpecVo);
		
		ret=productSpecService.updateProductSpecBySid(productSpec);
		
		return ret;
	}
	
	//删除商品规格
	@Override
	public int deleteProductSpecBySid(String pId) {
		return productSpecService.deleteProductSpecBySid(pId);
	}

	
	//查询单个商品规格
	@Override
	public ProductSpecVo queryProductSpecByOption(PageData pagedata) {
		return po2voer.transfer(new ProductSpecVo(),productSpecService.queryProductSpecByOption(pagedata));
	}

	//根据条件分页查询商品规格
	@Override
	public List<ProductSpecVo> queryProductSpecListPage(PageData pagedata) {
		return po2voer.transfer(ProductSpecVo.class,productSpecService.queryProductSpecListPage(pagedata));
	}

	//根据条件查询全部商品规格
	@Override
	public List<ProductSpecVo> queryListProductSpec(PageData pagedata) {
		return po2voer.transfer(ProductSpecVo.class,productSpecService.queryListProductSpec(pagedata));
	}

	@Override
	protected BaseService<ProductSpec> getService() {
		return productSpecService;
	}
    
    
 
}
