package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 文章期刊
 *
 */
public class ArticlePeriodical extends BasePO{

	private int periodicalId;
	private String periodicalTitle;	//标题
	private Date createTime;	//创建时间
	private String periodical;	//期数
	private int status;	//状态1默认 0 删除
	
	public int getPeriodicalId() {
		return periodicalId;
	}
	public void setPeriodicalId(int periodicalId) {
		this.periodicalId = periodicalId;
	}
	public String getPeriodicalTitle() {
		return periodicalTitle;
	}
	public void setPeriodicalTitle(String periodicalTitle) {
		this.periodicalTitle = periodicalTitle;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPeriodical() {
		return periodical;
	}
	public void setPeriodical(String periodical) {
		this.periodical = periodical;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
