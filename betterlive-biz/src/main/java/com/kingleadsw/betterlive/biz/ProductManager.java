package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Product;
import com.kingleadsw.betterlive.vo.ProductVo;


/**
 * 商品 实际交互层
 * 2017-03-08 by chen
 */
public interface ProductManager extends BaseManager<ProductVo,Product>{

	 /**
    * 根据条件查询单个商品
    * @param pageData
    * @return
    */
	public ProductVo selectProductByOption(PageData pageData);
   
   
   /**
    * 查询所有的商品
    * @param pd
    * @return
    */
	public List<ProductVo> queryProductList(PageData pd);
	
	
	
	/**
	    * 查询所有的商品  用于新增标签
	    * @param pd
	    * @return
	    */
		public List<ProductVo> queryProductListByLabel(PageData pd);
	
	/**
     * 分页查询商品
     * @param pd
     * @return
     */
	public List<ProductVo> queryProductListPage(PageData pd);
	

   /**
    * 增加商品
    * @param product
    * @return
    */
   public int insertProduct(ProductVo productVo);

   /**
    * 修改商品
    * 
    * @param sysDictVo
    * @return
    */
   public int updateProduct(ProductVo productVo);

  
   /**
    *  删除商品
    */
   public int deleteProductById(int productId);
   
   /**
    * 查询不存在于扩展表里的产品数据
    * @param pd
    * @return
    */
   public List<ProductVo> queryNotExistInExtension(PageData pd);
   
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
   List<ProductVo> queryExtensionListPage(PageData pd);
   
   /**
    * 查询猜你喜欢的商品
    * @param pd 查询条件
    * @return
    */
	List<ProductVo> queryGuessLikeList(PageData pd);
	
/**
	 * 首页每周新品/人气推荐
	 * @param status
	 * @param extensionType
	 * @param isHomepage
	 * @param pd
	 * @param pageSize
	 * @param activityFlag 可参与活动。 1可参与限时、限量活动、美食教程
	 * @return
	 */
	public List<ProductVo> queryIndexProduct(int status,int extensionType,int isHomepage,PageData pd, int pageSzie, int acitivityFlag);


	/**
	 * 根据文章关联的产品查询产品
	 * @param pd
	 * @return
	 */
	public List<ProductVo> queryListByArticle(PageData pd);
	
	/**
	 * 分享后页面产品展示区域
	 * @param
	 * @return
	 * @author zhangjing 2018年4月24日 下午6:44:32
	 */
	List<ProductVo> queryRegisterProductListPage(PageData pd);

}
