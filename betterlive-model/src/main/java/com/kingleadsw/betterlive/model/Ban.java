package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 禁止非法操作的手机号或IP
 */
public class Ban extends BasePO{
	private static final long serialVersionUID = -1113225940798020666L;
	
	private int banId;
	private String requestIp;
	private String requestMobile;
	
	public int getBanId() {
		return banId;
	}
	public void setBanId(int banId) {
		this.banId = banId;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequestMobile() {
		return requestMobile;
	}
	public void setRequestMobile(String requestMobile) {
		this.requestMobile = requestMobile;
	}
	

}