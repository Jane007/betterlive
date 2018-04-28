package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SysDict;
import com.kingleadsw.betterlive.vo.SysDictVo;




/**
 * 系统配置
 * 2017-03-08
 */
public interface SysDictManager extends BaseManager<SysDictVo,SysDict>{

    SysDictVo selectByCode(PageData pageData);
    

    /**
     * 查询所有的系统参数
     * 
     * @param pd
     * @return
     */
    public List<SysDictVo> querySysDictListPage(PageData pd);

    /**
     * id 查找系统参数
     * 
     * @param sys_dict_id
     * @return
     */
    public SysDictVo querySysDictBysysDictId(int sys_dict_id);

    /**
     * 增加新系统参数
     * 
     * @param sysDictVo
     * @return
     */
    public int insertSysDict(SysDictVo sysDictVo);

    /**
     * 修改系统参数
     * 
     * @param sysDictVo
     * @return
     */
    public int updateSysDict(SysDictVo sysDictVo);

    /**
     * 编码查找 
     * 
     * @param dict_code
     * @return
     */
    public SysDictVo selectSysDictByDictCode(String dict_code);
}