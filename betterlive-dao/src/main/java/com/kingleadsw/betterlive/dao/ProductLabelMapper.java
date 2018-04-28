package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductLabel;

public interface ProductLabelMapper extends BaseMapper<ProductLabel>{

	 /**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */

	@Override
	List<ProductLabel> queryListPage(PageData pd);
    
    /**
     * 查询数据
     * @param pd
     * @return
     */
    @Override
	List<ProductLabel> queryList(PageData pd);
    
    /**
     * 增加商品
     * @param product
     * @return
     */
     int insertProductlabel(ProductLabel productlabel);
    /**
     * 删除标签
     * @param productLabelId
     * @return
     */
	int delProductlabel(int productLabelId);
	
	/**
	 * 修改
	 */
	 int editproductlabel(ProductLabel productlabel);
}
