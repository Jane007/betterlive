package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import com.kingleadsw.betterlive.core.dto.BaseVO;

public class LogisticsCompanyVo   extends BaseVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer companyId;

    private String companyCode;

    private String companyName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
}
