package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductLabel;



public interface ProductLabelService extends BaseService<ProductLabel> {

	 /**
	    * 增加商品标签
	    * @param product
	    * @return
	    */
	   public int insertProductlabel(ProductLabel productlabel);
	   
	   /**
	     *  删除标签
	     */
	    public int delProductlabel(int productLabelId);

	    /**
	     * 修改标签
	     */
	    public int editproductlabel(ProductLabel productlabel);
	
}
