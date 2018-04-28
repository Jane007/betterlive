package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductLabel;
import com.kingleadsw.betterlive.vo.ProductLabelVo;

public interface ProductLabelManager  extends BaseManager<ProductLabelVo,ProductLabel> {
	
	 /**
	    * 查询所有的商品
	    * @param pd
	    * @return
	    */
		public List<ProductLabelVo> queryProductList(PageData pd);
		
		
	 /**
	    * 增加商品标签
	    * @param product
	    * @return
	    */
	   public int insertProductlabel(ProductLabelVo productlabelVo);
		   
		   /**
			*  删除标签
			*/
		public int delProductlabel(int productLabelId);
		
		/**
		 * 修改标签
		 */
		public int editproductlabel(ProductLabelVo productlabelVo);

}
