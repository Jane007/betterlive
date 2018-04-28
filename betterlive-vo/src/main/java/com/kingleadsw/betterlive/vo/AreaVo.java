package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 省市区
 * 2017-03-09  by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class AreaVo  extends BaseVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer areaId;//地区名称

    private String areaCode;//地区编码

    private String parentId;//父级地区id

    private String areaName;//地区名称

    private String postalCode;//邮政编码

    private String keyword;//地区关键字

    private String iFramework;//结构框架名称，下划线分割
    
 //   private Integer isOnline; //是否上线    0，未上线; 1，已上线
    /**
	 * @return the isOnline
	 */
/*	public Integer getIsOnline() {
		return isOnline;
	}*/

	/**
	 * @param isOnline the isOnline to set
	 */
/*	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}*/

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode == null ? null : postalCode.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getiFramework() {
        return iFramework;
    }

    public void setiFramework(String iFramework) {
        this.iFramework = iFramework == null ? null : iFramework.trim();
    }
}