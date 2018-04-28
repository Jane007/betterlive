package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ClassifyBannerVo extends BaseVO  implements Serializable{
	
	private static final long serialVersionUID = -9104981107214250244L;

	private Integer classifyBannerId;

    private String bannerTitle;			//banner标题

    private String bannerImg;			//banner图片

    private Integer bannerType;			//0 商品分类：全部商品， 1商品分类：鲜香果蔬，2商品分类：厨房百味，3商品分类：零食特产，4商品分类：网红推荐 5 限量抢购，6每周新品，7人气推荐

    private String bannerUrl;			//链接地址
    
    private String createTime;			//创建时间
    
    private Integer status;			//状态
    
    private String productId;   //图片配置单个商品id
    
    //---------非原始字段
    private int activityType;	//商品活动类型
    private int specialId;		//商品活动ID

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getClassifyBannerId() {
		return classifyBannerId;
	}

	public void setClassifyBannerId(Integer classifyBannerId) {
		this.classifyBannerId = classifyBannerId;
	}

	public String getBannerTitle() {
		return bannerTitle;
	}

	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public Integer getBannerType() {
		return bannerType;
	}

	public void setBannerType(Integer bannerType) {
		this.bannerType = bannerType;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getSpecialId() {
		return specialId;
	}

	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}
}
