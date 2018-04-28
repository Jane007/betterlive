package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysDict;
import com.kingleadsw.betterlive.service.SysDictService;
import com.kingleadsw.betterlive.vo.SysDictVo;


/**
 * 系统配置
 * 2017-03-08
 */
@Component
@Transactional
public class SysDictManagerImpl extends BaseManagerImpl<SysDictVo,SysDict> implements SysDictManager{
    @Autowired
     private SysDictService sysDictService;
    @Override
    protected BaseService<SysDict> getService() {        return sysDictService;    }
    
    /**
     * 根据字典code查询字典
     * @param pageData
     * @return
     */
    @Override
    public SysDictVo selectByCode(PageData pageData) {
        return po2voer.transfer(new SysDictVo(),sysDictService.selectByCode(pageData));
    }
    
    /**
     * 查询所有的系统参数
     * 
     * @param pd
     * @return
     */
	@Override
	public List<SysDictVo> querySysDictListPage(PageData pd) {
		return po2voer.transfer(SysDictVo.class,sysDictService.querySysDictListPage(pd));
	}
	
	/**
     * id 查找系统参数
     * 
     * @param sys_dict_id
     * @return
     */
	@Override
	public SysDictVo querySysDictBysysDictId(int sys_dict_id) {
		return po2voer.transfer(new SysDictVo(),sysDictService.querySysDictBysysDictId(sys_dict_id));
	}
	
	 /**
     * 增加新系统参数
     * @param sysDictVo
     * @return
     */
	@Override
	public int insertSysDict(SysDictVo sysDictVo) {
		SysDict sysDict=vo2poer.transfer(new SysDict(), sysDictVo);
		return sysDictService.insertSysDict(sysDict);
	}

	 /**
     * 修改系统参数
     * @param sysDictVo
     * @return
     */
	@Override
	public int updateSysDict(SysDictVo sysDictVo) {
		SysDict sysDict=vo2poer.transfer(new SysDict(), sysDictVo);
		return sysDictService.updateSysDict(sysDict);
	}
	
	 /**
     * 编码查找
     * @param dict_code
     * @return
     */
	@Override
	public SysDictVo selectSysDictByDictCode(String dict_code) {
		return po2voer.transfer(new SysDictVo(),sysDictService.selectSysDictByDictCode(dict_code));
	}
}