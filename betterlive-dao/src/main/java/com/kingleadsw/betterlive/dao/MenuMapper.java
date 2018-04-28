package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Menu;


/**
 * 菜单
 * 2017-03-07 by chen
 */
public interface MenuMapper extends BaseMapper<Menu>{

	List<Menu> queryMenuList();

	/**
	  * 根据角色id查询父级菜单
	  * List<Menu>
	 */
	List<Menu> findParentMenusByRoleId(PageData pd);
	
	/**
	  * 查询父级菜单
	  * List<Menu>
	 */
	List<Menu> findParentMenus(PageData pd);
	
	/**
	  * 根据角色id和父级菜单id查询子级菜单
	  * List<Menu>
	 */
	List<Menu> findChildrenMenusByRoleId(PageData pd);
	/**
	 * 根据权限获取菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> queryPowerMenuList(int roleId);
}