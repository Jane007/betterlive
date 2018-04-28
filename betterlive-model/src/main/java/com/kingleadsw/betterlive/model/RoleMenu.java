package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 角色菜单
 * 2017-03-07 by chen
 */
public class RoleMenu extends BasePO{
    private Integer roleMenuId;

    private Integer roleId;

    private Integer menuId;

    public Integer getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Integer roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}