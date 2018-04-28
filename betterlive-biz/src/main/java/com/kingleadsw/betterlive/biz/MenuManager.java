package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Menu;
import com.kingleadsw.betterlive.vo.MenuVo;



/**
 * 菜单
 * 2017-03-07 by chen
 */
public interface MenuManager extends BaseManager<MenuVo,Menu>{
	
	List<MenuVo> queryMenuList();
	
	/**
	  * 根据角色id查询父级菜单
	  * List<MenuVo>
	 */
	List<MenuVo> findParentMenusByRoleId(PageData pd);
	
	/**
	  * 查询父级菜单
	  * List<MenuVo>
	 */
	List<MenuVo> findParentMenus(PageData pd);
	
	/**
	  * 根据角色id和父级菜单id查询子级菜单
	  * List<MenuVo>
	 */
	List<MenuVo> findChildrenMenusByRoleId(PageData pd);
	
	
	/**
	 * 根据权限获取菜单
	 * @param roleId
	 * @return
	 */
	List<MenuVo> queryPowerMenuList(int roleId);
}