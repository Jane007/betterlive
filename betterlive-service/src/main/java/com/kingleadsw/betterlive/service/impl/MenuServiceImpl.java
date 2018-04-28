package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.MenuMapper;
import com.kingleadsw.betterlive.model.Menu;
import com.kingleadsw.betterlive.service.MenuService;

import java.util.List;



/**
 * 菜单
 * 2017-03-07 by chen 
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService{
    @Autowired
    private MenuMapper menuMapper;

    @Override
    protected BaseMapper<Menu> getBaseMapper() {
        return menuMapper;
    }

	@Override
	public List<Menu> queryMenuList() {
		return menuMapper.queryMenuList();
	}

	/**
	  * 根据角色id查询父级菜单
	  * List<Menu>
	 */
	@Override
	public List<Menu> findParentMenusByRoleId(PageData pd) {
		return menuMapper.findParentMenusByRoleId(pd);
	}

	/**
	  * 根据角色id和父级菜单id查询子级菜单
	  * List<Menu>
	 */
	@Override
	public List<Menu> findChildrenMenusByRoleId(PageData pd) {
		return menuMapper.findChildrenMenusByRoleId(pd);
	}

	@Override
	public List<Menu> queryPowerMenuList(int roleId) {
		// TODO Auto-generated method stub
		return menuMapper.queryPowerMenuList(roleId);
	}

	@Override
	public List<Menu> findParentMenus(PageData pd) {
		return menuMapper.findParentMenus(pd);
	}
	
}