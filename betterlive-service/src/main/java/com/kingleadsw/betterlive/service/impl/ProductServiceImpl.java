package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ActivityProductMapper;
import com.kingleadsw.betterlive.dao.ProductMapper;
import com.kingleadsw.betterlive.model.Product;
import com.kingleadsw.betterlive.service.ProductService;


/**
 * 商品 service 实现 层
 * 2017-03-08 by chen
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product>  implements ProductService{
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ActivityProductMapper activityProductMapper;
	
	 /**
     * 根据条件查询单个商品
     * @param pageData
     * @return
     */
	@Override
	public Product selectProductByOption(PageData pageData) {
		return productMapper.selectProductByOption(pageData);
	}
	
	/**
     * 查询所有的商品
     * @param pd
     * @return
     */
	@Override
	public List<Product> queryProductList(PageData pd) {
		return productMapper.queryProductList(pd);
	}
	
	/**
     * 分页查询商品
     * @param pd
     * @return
     */
	@Override
	public List<Product> queryProductListPage(PageData pd) {
		return productMapper.queryProductListPage(pd);
	}
	
	 /**
     * 增加商品
     * @param product
     * @return
     */
	@Override
	public int insertProduct(Product product) {
		return productMapper.insertProduct(product);
	}
	
	/**
     * 修改商品
     * 
     * @param sysDictVo
     * @return
     */
	@Override
	public int updateProduct(Product product) {
		return productMapper.updateProduct(product);
	}
	

    /**
     *  删除商品
     */
	@Override
	public int deleteProductById(int productId) {
		return productMapper.deleteProductById(productId);
	}

	@Override
	public List<Product> queryNotExistInExtension(PageData pd) {
		return productMapper.queryNotExistInExtension(pd);
	}
	
	@Override
	protected BaseMapper<Product> getBaseMapper() {
		return productMapper;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.ProductService#updateProductStatus(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public int updateProductStatus(PageData pd) {
		return productMapper.updateProductStatus(pd);
	}

	@Override
	public List<Product> queryExtensionListPage(PageData pd) {
		return productMapper.queryExtensionListPage(pd);
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.ProductService#queryMinProductPrice(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public float queryMinProductPrice(PageData pd) {
		return activityProductMapper.queryMinProductPrice(pd);
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.ProductService#queryProductBaseInfoList(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public List<Product> queryProductBaseInfoList(PageData pd) {
		return productMapper.queryProductBaseInfoList(pd);
	}

	@Override
	public List<Product> queryProductListByLabel(PageData pd) {
		return productMapper.queryProductListByLabel(pd);
	}

	@Override
	public List<Product> queryListByArticle(PageData pd) {
		return productMapper.queryListByArticle(pd);
	}

	@Override
	public List<Product> queryRegisterProductListPage(PageData pd) {
		return productMapper.queryRegisterProductListPage(pd);
	}

}
