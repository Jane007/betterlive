package com.kingleadsw.betterlive.core.service;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.dto.BasePO;
import com.kingleadsw.betterlive.core.page.PageData;

/**
 * Created by Administrator on 2016-08-08.
 */
public abstract class BaseServiceImpl<PO extends BasePO> implements BaseService<PO>{

    private BaseMapper<PO> baseMapper;

    /**
     * 建议在子类中重写该方法
     * @return
     */
    protected  abstract BaseMapper<PO> getBaseMapper();

    @Override
    public List<PO> queryListPage(PageData pd) {
        return getBaseMapper().queryListPage(pd);
    }
    

    @Override
    public PO queryOne(PageData pd) {
        return getBaseMapper().queryOne(pd);
    }
    @Override
    public List<PO> queryList(PageData pd) {
        return getBaseMapper().queryList(pd);
    }
    @Override
    public PO selectByPrimaryKey(Integer id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public int delete(PageData pd) {
        return getBaseMapper().delete(pd);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(PO pd) {
        return getBaseMapper().updateByPrimaryKey(pd);
    }
    
    /**
     * 按条件修改
     * @param record
     * @return
     */
    @Override
    public int updatePageData(PageData pd) {
    	return getBaseMapper().updatePageData(pd);
    }

    @Override
    public int updateByPrimaryKeySelective(PO record) {
        return getBaseMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertPageData(PageData pd) {
        return getBaseMapper().insertPageData(pd);
    }

    @Override
    public int insert(PO record) {
        return getBaseMapper().insert(record);
    }

    /**
     * 从list集合批量插入数据到数据库
     * @param list
     * @return
     */
    public int insertBatchFromList(List<PO> list) {
    	return getBaseMapper().insertBatchFromList(list);
    }
    
    public int insertSelective(PO record){
    	return getBaseMapper().insertSelective(record);
    }
 
}
