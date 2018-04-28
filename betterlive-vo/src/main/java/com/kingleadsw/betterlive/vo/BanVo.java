package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 禁止非法操作的手机号或IP
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class BanVo  extends BaseVO implements Serializable{

	private static final long serialVersionUID = 1554871546157286830L;
	
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