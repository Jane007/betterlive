package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 角色实体类
 * 2017-03-07  by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class RoleVo  extends BaseVO implements Serializable{
    private Integer roleId;//角色id

    private String roleName;//角色名称
    
    private int roleType;//角色类型
    
    public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}