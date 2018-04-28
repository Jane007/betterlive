package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.DateUtil;

/**
 * 文章期刊
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ArticlePeriodicalVo extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = 4257976606903988882L;
	
	private int periodicalId;
	private String periodicalTitle;	//标题
	private Date createTime;	//创建时间
	private String periodical;	//期数
	private int status;	//状态1默认 0 删除
	
	private String strCreateTime;
	
	private List<SpecialArticleVo> specialArticleList = new ArrayList<SpecialArticleVo>();
	
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
	public String getStrCreateTime() {
		strCreateTime = DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", createTime);
		return strCreateTime;
	}
	public void setStrCreateTime(String strCreateTime) {
		this.strCreateTime = strCreateTime;
	}
	public List<SpecialArticleVo> getSpecialArticleList() {
		return specialArticleList;
	}
	public void setSpecialArticleList(List<SpecialArticleVo> specialArticleList) {
		this.specialArticleList = specialArticleList;
	}
	
}
