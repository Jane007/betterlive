package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class ArticleRelationPro extends BasePO {

	private int relationProId;
	private int articleId;
	private int productId;
	private Date createTime;
	
	//------------非原始字段-------------
	private String productName;
	
	public int getRelationProId() {
		return relationProId;
	}
	public void setRelationProId(int relationProId) {
		this.relationProId = relationProId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
