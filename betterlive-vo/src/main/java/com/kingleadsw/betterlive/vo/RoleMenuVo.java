package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 角色关联菜单实体类
 * 2017-03-07  by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class RoleMenuVo  extends BaseVO implements Serializable{
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