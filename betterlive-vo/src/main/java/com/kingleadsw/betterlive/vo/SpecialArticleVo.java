package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.StringUtil;

/**
 * 美食推荐文章
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SpecialArticleVo extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int articleId;		//id
	private String content;		//内容
	private String createTime;		//创建时间
	private String publishTime;		//发布时间
	private String author;		//作者名字
	private int articleTypeId;		//文章类型ID
	private String articleTitle;	//标题
	private String articleIntroduce;	//简介
	private String articleCover;	//封面图
	private int status;			//状态1已发布（上架） 2草稿箱 3待审核 4未通过审核（下架） 0 删除
	private int homeFlag;
	private int periodicalId;	//期刊ID
	private int articleFrom;	//文章来源：0精选文章，1美食圈
	private int customerId;		//作者（用户）ID
	private int recommendMore;	//推荐到更多动态 1是 0否
	private String homeCover;	//主页宽图封面
	private String opinion; //意见
	private int homeSorts;	//商城首页排序字段 升序
	private int recommendFlag; //推荐到主页：0否，1是
	//-----------------非原始字段
	private int collectionCount;	//收藏数
	private int praiseCount;	//点赞数
	private int commentCount;	//评论数
	private int shareCount;	//分享次数
	private int isCollection;	//是否收藏
	private int isPraise;	//是否点赞
	private String articleTypeName;	//文章分类名称
	private String headUrl;	//用户头像
	private String fomartTime;	//格式化时间
	
	private int picWidth;
	private int picHeight;
	private String periodical;	//期刊号
	
	private List<PicturesVo> pictures;	//文章图片
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getArticleTypeId() {
		return articleTypeId;
	}
	public void setArticleTypeId(int articleTypeId) {
		this.articleTypeId = articleTypeId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleIntroduce() {
		return articleIntroduce;
	}
	public void setArticleIntroduce(String articleIntroduce) {
		this.articleIntroduce = articleIntroduce;
	}
	public String getArticleCover() {
		return articleCover;
	}
	public void setArticleCover(String articleCover) {
		this.articleCover = articleCover;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getHomeFlag() {
		return homeFlag;
	}
	public void setHomeFlag(int homeFlag) {
		this.homeFlag = homeFlag;
	}
	public int getPeriodicalId() {
		return periodicalId;
	}
	public void setPeriodicalId(int periodicalId) {
		this.periodicalId = periodicalId;
	}
	public int getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getIsCollection() {
		return isCollection;
	}
	public void setIsCollection(int isCollection) {
		this.isCollection = isCollection;
	}
	public int getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(int isPraise) {
		this.isPraise = isPraise;
	}
	public int getArticleFrom() {
		return articleFrom;
	}
	public void setArticleFrom(int articleFrom) {
		this.articleFrom = articleFrom;
	}
	public String getArticleTypeName() {
		return articleTypeName;
	}
	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public String getOpinion() {
		return opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getHeadUrl() {
		if(StringUtil.isNull(headUrl)){
			headUrl = "http://www.hlife.shop/huihuo/resources/images/default_photo.png";
		}
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getFomartTime() {
		return fomartTime;
	}
	public void setFomartTime(String fomartTime) {
		this.fomartTime = fomartTime;
	}
	public int getRecommendMore() {
		return recommendMore;
	}
	public void setRecommendMore(int recommendMore) {
		this.recommendMore = recommendMore;
	}
	public int getPicWidth() {
		return picWidth;
	}
	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}
	public int getPicHeight() {
		return picHeight;
	}
	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}
	public String getPeriodical() {
		return periodical;
	}
	public void setPeriodical(String periodical) {
		this.periodical = periodical;
	}
	public List<PicturesVo> getPictures() {
		return pictures;
	}
	public void setPictures(List<PicturesVo> pictures) {
		this.pictures = pictures;
	}
	public String getHomeCover() {
		return homeCover;
	}
	public void setHomeCover(String homeCover) {
		this.homeCover = homeCover;
	}
	public int getHomeSorts() {
		return homeSorts;
	}
	public void setHomeSorts(int homeSorts) {
		this.homeSorts = homeSorts;
	}
	public int getRecommendFlag() {
		return recommendFlag;
	}
	public void setRecommendFlag(int recommendFlag) {
		this.recommendFlag = recommendFlag;
	}
	
}
