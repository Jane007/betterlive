package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SysDictMapper;
import com.kingleadsw.betterlive.model.SysDict;
import com.kingleadsw.betterlive.service.SysDictService;


/**
 * 系统配置 service 实现层
 * 2017-03-08
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDict> implements SysDictService{
    @Autowired
    private SysDictMapper sysDictMapper;
    
    @Override
    protected BaseMapper<SysDict> getBaseMapper() {
        return sysDictMapper;
    }

    /**
     * 根据字典code查询字典
     * @param pageData
     * @return
     */
    @Override
    public SysDict selectByCode(PageData pageData){
        return sysDictMapper.selectByCode(pageData);
    }
    
    /**
     * 查询所有的系统参数
     * 
     * @param pd
     * @return
     */
    @Override
    public List<SysDict> querySysDictListPage(PageData pd) {
        return sysDictMapper.querySysDictListPage(pd);
    }

    /**
     * id 查找系统参数
     * 
     * @param sys_dict_id
     * @return
     */
    @Override
    public SysDict querySysDictBysysDictId(int sys_dict_id) {

        return sysDictMapper.querySysDictBysysDictId(sys_dict_id);
    }
    

    /**
     * 增加新系统参数
     * 
     * @param sysDictVo
     * @return
     */
    @Override
    public int insertSysDict(SysDict sysDict) {
        return sysDictMapper.insertSysDict(sysDict);
    }
    
    /**
     * 修改系统参数
     * 
     * @param sysDict
     * @return
     */
    @Override
    public int updateSysDict(SysDict sysDict) {
        return sysDictMapper.updateSysDict(sysDict);
    }
    
    /**
     * 编码查找
     * @param dict_code
     * @return
     */
    @Override
    public SysDict selectSysDictByDictCode(String dict_code) {
        return sysDictMapper.selectSysDictByDictCode(dict_code);
    }
    
    /**
     * 根据字典编码从缓存中或者数据库中查询字典对象，缓存中没有而数据库存在，则将此对象加入缓存</br>
     * 如果没有获取到该对象，则发送告警短信给超级管理员
     * @param dict_code
     * @return
     */
    @Override
    public SysDict getSysDictByCodeFromRedisOrDb(String dict_code) {
		return sysDictMapper.selectSysDictByDictCode(dict_code);
	}
}