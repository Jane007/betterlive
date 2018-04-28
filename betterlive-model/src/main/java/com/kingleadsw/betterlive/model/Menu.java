package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;


/**
 * 菜单
 * 2017-03-07 by chen
 */
public class Menu extends BasePO{
    private Integer menuId;		//菜单id

    private Integer parentId;	//父级菜单id

    private String menuName;	//菜单名称

    private String menuType;	//菜单类型，1：菜单，2：按钮

    private String menuUrl;		//菜单链接地址

    private Integer menuOrd;	//菜单展示顺序

    private String status;		//菜单状态，0：无效，1：有效
    
    private String parentName;		//父菜单名

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuOrd() {
        return menuOrd;
    }

    public void setMenuOrd(Integer menuOrd) {
        this.menuOrd = menuOrd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}