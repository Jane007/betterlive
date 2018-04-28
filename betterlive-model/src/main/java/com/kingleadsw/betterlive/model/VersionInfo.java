package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 版本更新信息 实体类
 *
 */
public class VersionInfo extends BasePO {

	private static final long serialVersionUID = -5968813799273848844L;
	
	private int versionId; //版本内容ID
	private int versionCode; //版本标识
	private String versionName; //版本名
	private String versionDescribe;	//描述
	private Date createTime;	//创建时间
	private String downloadUrl;	//安卓APP下载地址
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
