package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ArticleRelationProVo extends BaseVO implements Serializable {
	private static final long serialVersionUID = 5205279229168872065L;
	
	private int relationProId;
	private int articleId;
	private int productId;
	private Date createTime;
	
	//----------非原始字段----------
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
