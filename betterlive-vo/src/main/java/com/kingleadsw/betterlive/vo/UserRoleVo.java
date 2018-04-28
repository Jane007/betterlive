package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 用户角色实体类
 * 2017-03-07  by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class UserRoleVo  extends BaseVO implements Serializable{
    private Integer userRoleId;//用户角色表id

    private Integer userId;//后台用户id

    private Integer roleId;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}