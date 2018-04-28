package com.kingleadsw.betterlive.core.service;


import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;
import com.kingleadsw.betterlive.core.page.PageData;

public interface BaseService<PO extends BasePO>{
    /**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
    List<PO> queryListPage(PageData pd);
    /**
     * 查询列表
     * @param pd
     * @return
     */
    List<PO> queryList(PageData pd);

    /**
     * 查询单个记录
     * @param pd
     * @return
     */
    PO queryOne(PageData pd);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    PO selectByPrimaryKey(Integer id);

    /**
     * 删除
     *
     * @param pd
     * @return
     */
    int delete(PageData pd);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 修改
     */
    int updateByPrimaryKey(PO record);

    /**
     * 按条件修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PO record);

    /**
     * 插入
     * @param pd
     * @return
     */
    int insertPageData(PageData pd);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(PO record);
    
    /**
     * 从list集合批量插入数据到数据库
     * @param list
     * @return
     */
    int insertBatchFromList(List<PO> list);

    int insertSelective(PO record);
    
    
	int updatePageData(PageData pd);
}