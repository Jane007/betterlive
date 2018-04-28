package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysDict;


/**
 * 系统配置 service 层
 * 2017-03-08
 */
public interface SysDictService extends BaseService<SysDict>{

    /**
     * 根据字典code查询字典
     * @param pageData
     * @return
     */
    SysDict selectByCode(PageData pageData);
    
    /**
     * 查询所有的系统参数
     * 
     * @param pd
     * @return
     */
    public List<SysDict> querySysDictListPage(PageData pd);

    /**
     * id 查找系统参数
     * 
     * @param sys_dict_id
     * @return
     */
    public SysDict querySysDictBysysDictId(int sys_dict_id);

    /**
     * 增加新系统参数
     * 
     * @param sysDictVo
     * @return
     */
    public int insertSysDict(SysDict sysDict);

    /**
     * 修改系统参数
     * 
     * @param sysDictVo
     * @return
     */
    public int updateSysDict(SysDict sysDict);

    /**
     * 编码查找
     * 
     * @param dict_code
     * @return
     */
    public SysDict selectSysDictByDictCode(String dict_code);
    
    /**
     * 根据字典编码从缓存中或者数据库中查询字典对象</br>
     * 如果没有获取到该对象，则发送告警短信给超级管理员
     * @param dict_code
     * @return
     */
    SysDict getSysDictByCodeFromRedisOrDb(String dict_code);
}