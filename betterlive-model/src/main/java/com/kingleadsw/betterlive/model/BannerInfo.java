package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;
import com.kingleadsw.betterlive.core.util.DateUtil;



public class BannerInfo  extends BasePO {

	private static final long serialVersionUID = -7305767066001143365L;

	private Integer bannerId;

    private String bannerTitle;			//banner标题

    private String bannerImg;			//banner图片

    private Byte bannerType;			//l轮播图链接类型，1：自定义链接，2：预购id，3：专题活动id，4：产品id

    private Byte status;				//状态：1：正常，:0：禁用

    private String bannerUrl;			//链接地址
    
    private Date createTime;			//创建时间
    
    private String createTimeStr;
    
    private Integer objectId;		//关联实体对象id
    
    private int isSort;	//排序 升序
    
    
    

   
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
        this.bannerTitle = bannerTitle == null ? null : bannerTitle.trim();
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg == null ? null : bannerImg.trim();
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
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
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