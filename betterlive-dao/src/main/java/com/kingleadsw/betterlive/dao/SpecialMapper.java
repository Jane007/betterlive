package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Special;

public interface SpecialMapper extends  BaseMapper<Special>{

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
     * @param Special
     * @return
     */
    public int updateSpecial(Special special);

   
    /**
     *  删除专题
     */
    public int deleteSpecialById(int specialId);


	public int hideSpecialHomeFlag(PageData hideSpeParam);


	public Special queryOneByTutorial(PageData specialParams);

	public Special queryOneByProductId(PageData specialParams);

	public Special queryOneSpecByProductId(PageData specialParams);


	public List<Special> queryTutorialListPage(PageData pd);

}
