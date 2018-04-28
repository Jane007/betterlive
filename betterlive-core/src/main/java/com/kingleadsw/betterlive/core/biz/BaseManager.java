package com.kingleadsw.betterlive.core.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;
import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.page.PageData;

public interface BaseManager<VO extends BaseVO,PO extends BasePO>{

    /**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
    List<VO> queryListPage(PageData pd);
    /**
     * 查询列表
     * @param pd
     * @return
     */
    List<VO> queryList(PageData pd);

    /**
     * 查询单个记录
     * @param pd
     * @return
     */
    VO queryOne(PageData pd);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    VO selectByPrimaryKey(Integer id);

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
     * 更新
     * @param pd
     * @return
     */
    int updateByPrimaryKey(VO pd);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(VO record);
    
    /**
     * 按条件修改
     * @param record
     * @return
     */
    int updatePageData(PageData pd);

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
    int insert(VO record);
    
    int insertSelective(VO record);
    
}