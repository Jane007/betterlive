package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.MenuManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Menu;
import com.kingleadsw.betterlive.service.MenuService;
import com.kingleadsw.betterlive.vo.MenuVo;

import java.util.List;


/**
 * 菜单
 * 2017-03-07 by chen
 */
@Component
@Transactional
public class MenuManagerImpl extends BaseManagerImpl<MenuVo,Menu> implements MenuManager{
    @Autowired
     private MenuService menuService;
    @Override
    protected BaseService<Menu> getService() {        return menuService;    }
	@Override
	public List<MenuVo> queryMenuList() {
		List<Menu> list = menuService.queryMenuList();
		return po2voer.transfer(MenuVo.class, list);
	}
	@Override
	public List<MenuVo> queryPowerMenuList(int roleId) {
		// TODO Auto-generated method stub
		return po2voer.transfer(MenuVo.class, menuService.queryPowerMenuList(roleId));
	}
	
	/**
	  * 根据角色id查询父级菜单
	  * List<MenuVo>
	 */
	@Override
	public List<MenuVo> findParentMenusByRoleId(PageData pd) {
		return po2voer.transfer(MenuVo.class, menuService.findParentMenusByRoleId(pd));
	}
	
	/**
	  * 根据角色id和父级菜单id查询子级菜单
	  * List<MenuVo>
	 */
	@Override
	public List<MenuVo> findChildrenMenusByRoleId(PageData pd) {
		return po2voer.transfer(MenuVo.class, menuService.findChildrenMenusByRoleId(pd));
	}
	@Override
	public List<MenuVo> findParentMenus(PageData pd) {
		return po2voer.transfer(MenuVo.class, menuService.findParentMenus(pd));
	}
	
}