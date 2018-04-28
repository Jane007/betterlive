package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Extension;

public interface ExtensionService extends BaseService<Extension>{
	
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
	 * 查询列表
	 */
	@Override
	List<Extension> queryList(PageData pd);
	
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
	 * 根据id删除推荐商品
	 *@author zhangjing
	 *@date 2017年3月13日 下午2:28:03
	 *@parameter
	 *@return
	 */
	int deleteExtensionById(int extensionId);

}
