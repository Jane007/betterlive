package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Extension;

/**
 * 商品推荐/人气 dao
 * @author zhangjing
 * @date 2017年3月13日 下午2:11:30
 */
public interface ExtensionMapper extends BaseMapper<Extension>{
	/**
	 * 分页查询列表
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:17:16
	 *@parameter
	 *@return
	 */
	@Override
	List<Extension> queryListPage(PageData pd);
	
	
	
	/**
	 *  根据条件查询单个推荐商品
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:23:05
	 *@parameter
	 *@return
	 */
	Extension selectExtensionByOption(PageData pageData);
	
	/**
	 * 插入推荐商品
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:25:41
	 *@parameter
	 *@return
	 */
	int insertExtension(Extension extension);
	
	
	/**
	 * 更新推荐商品
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:26:26
	 *@parameter
	 *@return
	 */
	int updateExtension(Extension extension);
	
	/**
	 * 更新推荐状态
	 * @param extensions
	 * @return
	 */
	int updateExtensions(Exception extensions);
	/**
	 * 根据id删除推荐商品
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:28:03
	 *@parameter
	 *@return
	 */
	int deleteExtensionById(int extensionId);
}
