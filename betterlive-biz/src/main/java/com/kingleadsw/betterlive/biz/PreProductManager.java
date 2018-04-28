package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.PreProduct;
import com.kingleadsw.betterlive.vo.PreProductVo;

public interface PreProductManager extends BaseManager<PreProductVo,PreProduct>{
	
	 /**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
	@Override
	List<PreProductVo> queryListPage(PageData pd);
    
    /**
     * 查询数据
     * @param pd
     * @return
     */
	@Override
	List<PreProductVo> queryList(PageData pd);
    
    
    /**
     * 根据条件查询单个预售商品
     * @param pageData
     * @return
     */
	PreProductVo selectPreProductByOption(PageData pageData);
	
	/**
	 * 插入预售商品
	 *@author zhangjing
	 *@date 2017年3月11日 上午11:54:02
	 *@parameter
	 *@return
	 */
	int insertPreProduct(PreProductVo preProductVo);
	
	/**
	 * 更新预售商品
	 *@author zhangjing
	 *@date 2017年3月11日 上午11:57:08
	 *@parameter
	 *@return
	 */
	int updatePreProduct(PreProductVo preProductVo);
	
	/**
	 * 删除预售商品
	 *@author zhangjing
	 *@date 2017年3月11日 下午12:00:37
	 *@parameter
	 *@return
	 */
	int deletePreProductById(int preId);

}
