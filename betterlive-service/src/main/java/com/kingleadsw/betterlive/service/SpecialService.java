package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Special;

public interface SpecialService extends BaseService<Special> {
	 /**
     * 根据条件查询单个专题
     * @param pageData
     * @return
     */
	public Special selectSpecialByOption(PageData pageData);
    
    
    /**
     * 查询所有的专题
     * @param pd
     * @return
     */
	public List<Special> querySpecialList(PageData pd);

	
	/**
     * 分页查询专题
     * @param pd
     * @return
     */
	public List<Special> querySpecialListPage(PageData pd);
	
    /**
     * 增加专题
     * @param Special
     * @return
     */
    public int insertSpecial(Special special);

    /**
     * 修改专题
     * 
     * @param 
     * @return
     */
    public int updateSpecial(Special special);

   
    /**
     *  删除专题
     */
    public int deleteSpecialById(int specialId);

    /**
     * 取消首页推荐
     * @param hideSpeParam
     * @return
     */
	public int hideSpecialHomeFlag(PageData hideSpeParam);

	/**
	 * 美食教程
	 * @param specialParams
	 * @return
	 */
	public Special queryOneByTutorial(PageData specialParams);

	/**
	 * 美食教程列表
	 * @param pd
	 * @return
	 */
	public List<Special> queryTutorialListPage(PageData pd);

	/**
	 * 根据商品查询活动
	 * @param specialParams
	 * @return
	 */
	public Special queryOneByProductId(PageData specialParams);
	
	/**
	 * 根据商品查询活动以及最低价
	 * @param specialParams
	 * @return
	 */
	public Special queryOneSpecByProductId(PageData specialParams);

}
