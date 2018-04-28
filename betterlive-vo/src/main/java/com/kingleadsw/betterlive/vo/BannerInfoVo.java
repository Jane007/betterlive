package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.DateUtil;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class BannerInfoVo extends BaseVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4413303284164920361L;
	
    private Integer bannerId;      //轮播图id

    private String bannerTitle;		//轮播图标题

    private String bannerImg;		//轮播图片

    private Byte bannerType;		//轮播图类型1：自定义链接，2：预购id，3：专题活动id，4：产品id

    private Byte status;			//轮播图状态1：正常，:0：禁用

    private String bannerUrl;		//轮播图链接url
    
    private Date createTime;		//轮播图创建时间
    private String createTimeStr;	//轮播图创建时间
    
    private Integer objectId;		//关联实体对象id
    
    private int isSort;	//排序 升序
    
	public Integer getBannerId() {
		return bannerId;
	}
	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
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
	public Byte getBannerType() {
		return bannerType;
	}
	public void setBannerType(Byte bannerType) {
		this.bannerType = bannerType;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		if(createTime != null){
			createTimeStr=DateUtil.dateToStringByDay(createTime);
		}
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public int getIsSort() {
		return isSort;
	}
	public void setIsSort(int isSort) {
		this.isSort = isSort;
	}
    
    
    

}
