package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 版本更新信息 VO
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class VersionInfoVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = 2516302904797043070L;
	
	private int versionId; //版本内容ID
	private int versionCode; //版本标识
	private String versionName; //版本名
	private String versionDescribe;	//描述
	private Date createTime;	//创建时间
	private String downloadUrl;	//下载地址
	private String bakUrl;	//安卓APP应用市场地址-备用
	
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionDescribe() {
		return versionDescribe;
	}
	public void setVersionDescribe(String versionDescribe) {
		this.versionDescribe = versionDescribe;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getBakUrl() {
		return bakUrl;
	}
	public void setBakUrl(String bakUrl) {
		this.bakUrl = bakUrl;
	}
}