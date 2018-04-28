package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Product;

/**
 * 商品 dao层
 * 2017-03-08
 */
public interface ProductMapper extends BaseMapper<Product>{

    /**
     * 根据条件查询单个商品
     * @param pageData
     * @return
     */
	public Product selectProductByOption(PageData pageData);
    
    
    /**
     * 查询所有的商品
     * @param pd
     * @return
     */
	public List<Product> queryProductList(PageData pd);
	
	/**
     * 查询所有的商品 用于新增标签
     * @param pd
     * @return
     */
	public List<Product> queryProductListByLabel(PageData pd);
	
	
	/**
     * 分页查询商品
     * @param pd
     * @return
     */
	public List<Product> queryProductListPage(PageData pd);
	

    /**
     * 增加商品
     * @param product
     * @return
     */
    public int insertProduct(Product product);

    /**
     * 修改商品
     * 
     * @param sysDictVo
     * @return
     */
    public int updateProduct(Product product);

   
    /**
     *  删除商品
     */
    public int deleteProductById(int productId);
    
    /**
     * 查询不存在于扩展表里的产品数据
     * @param pd
     * @return
     */
    public List<Product> queryNotExistInExtension(PageData pd);
    
    /**
     * 根据商品id，修改商品状态
     * @param pd
     * @return
     */
    int updateProductStatus(PageData pd);
    
    /**
     * 查询存在于扩展表里上架的产品数据
     * @param pd status：商品状态，isHomepage：是否首页推荐，扩展类型：extensionType
     * @return
     */
    List<Product> queryExtensionListPage(PageData pd);
    
    /**
     * 根据条件，查询商品基本信息
     * @param pd
     * @return
     */
    List<Product> queryProductBaseInfoList(PageData pd);


    /**
	 * 根据文章关联的产品查询产品
	 * @param pd
	 * @return
	 */
	public List<Product> queryListByArticle(PageData pd);
	/**
	 * 分享后页面产品展示区域
	 * @param
	 * @return
	 * @author zhangjing 2018年4月24日 下午6:44:32
	 */
	List<Product> queryRegisterProductListPage(PageData pd);

}