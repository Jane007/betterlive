package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 系统配置
 * 2017-03-08 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SysDictVo  extends BaseVO implements Serializable{
    private Integer sysDictId;		//系统配置id
    private String dictCode;		//系统配置编码
    private String dictName;		//系统配置名称
    private int dictType;			//字典类型，1：系统内置，不允许删除；2：管理员添加
    private String dictValue;		//配置值
    private int status;				//字典状态，1：有效，2：禁用，-1：删除
    private String description;		//字典描述

    
    //--------------------------------------------
    public Integer getSysDictId() {
        return sysDictId;
    }
    public void setSysDictId(Integer sysDictId) {
        this.sysDictId = sysDictId;
    }
    public String getDictCode() {
        return dictCode;
    }
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }
    public String getDictName() {
        return dictName;
    }
    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }
    public int getDictType() {
        return dictType;
    }
    public void setDictType(int dictType) {
        this.dictType = dictType;
    }
    public String getDictValue() {
        return dictValue;
    }
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}