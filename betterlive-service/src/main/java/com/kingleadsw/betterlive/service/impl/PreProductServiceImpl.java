package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PreProductMapper;
import com.kingleadsw.betterlive.model.PreProduct;
import com.kingleadsw.betterlive.service.PreProductService;

@Service
public class PreProductServiceImpl extends BaseServiceImpl<PreProduct>
		implements PreProductService {
	@Autowired
	private PreProductMapper preProductMapper;
	
	
	
	@Override
	public List<PreProduct> queryListPage(PageData pd){
		return preProductMapper.queryListPage(pd);
	}
	
	@Override
	public List<PreProduct> queryList(PageData pd){
		return preProductMapper.queryList(pd);
	}
	/**
	 * 根据条件查询单个预售商品
	 */
	@Override
	public PreProduct selectPreProductByOption(PageData pageData) {
		return preProductMapper.selectPreProductByOption(pageData);
	}
	
	/**
	 * 插入预售商品
	 */
	@Override
	public int insertPreProduct(PreProduct preProduct) {
		return preProductMapper.insertPreProduct(preProduct);
	}

	/**
	 * 更新预售商品
	 */
	@Override
	public int updatePreProduct(PreProduct preProduct) {
		return preProductMapper.updatePreProduct(preProduct);
	}

	/**
	 * 删除预售商品
	 */
	@Override
	public int deletePreProductById(int preId) {
		return preProductMapper.deletePreProductById(preId);
	}

	@Override
	protected BaseMapper<PreProduct> getBaseMapper() {
		return preProductMapper;
	}

	

}
