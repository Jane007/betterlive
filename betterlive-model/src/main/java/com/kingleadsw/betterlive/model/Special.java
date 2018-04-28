package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 专题
 * @author zhangjing
 * @date 2017年3月8日 下午3:27:25
 */
public class Special extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722398984069824962L;
	
	private Integer specialId; 				//专题id
	
	private String specialName; 			//专题名称
	
	private String specialCover;   			//专题封面
	
	private String specialTitle;  			//专题标题
	
	private String  specialIntroduce;    	//专题介绍
	
	private byte specialType; 				//专题类型: 1 限时活动, 2 限量抢购, 3 团购活动 , 4 美食教程
	
	private String specialPage; 			//专题页面路径
	
	private String startTime;    				//专题开始时间
	
	private String endTime;     				//专题结束时间
	  
	private String createTime;        		//创建时间
	
	private Integer status;					//默认是1，0删除
	
	private String listImg;     				//团购图片
	
	private int specialSort;   //排序
	
	private int homeFlag;	//是否推荐到首页 0否 1是
	
	private int fakeSalesCopy;	//虚拟销量
	
	private String objValue;	//如果是视频，则为视频时长
	private int objTypeId;	//视频分类ID
	
	private int collectionCount;	//收藏数
	private int praiseCount;	//点赞数
	private int commentCount;	//评论数
	private int shareCount;	//分享次数
	private int isCollection;	//是否收藏
	private int isPraise;	//是否点赞
	
	private ProductSpec productSpecVo;
	
	public Integer getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public byte getSpecialType() {
		return specialType;
	}

	public void setSpecialType(byte specialType) {
		this.specialType = specialType;
	}

	public String getSpecialPage() {
		return specialPage;
	}

	public void setSpecialPage(String specialPage) {
		this.specialPage = specialPage;
	}

	
	public String getSpecialCover() {
		return specialCover;
	}

	public void setSpecialCover(String specialCover) {
		this.specialCover = specialCover;
	}

	public String getSpecialTitle() {
		return specialTitle;
	}

	public void setSpecialTitle(String specialTitle) {
		this.specialTitle = specialTitle;
	}

	public String getSpecialIntroduce() {
		return specialIntroduce;
	}

	public void setSpecialIntroduce(String specialIntroduce) {
		this.specialIntroduce = specialIntroduce;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public int getHomeFlag() {
		return homeFlag;
	}

	public void setHomeFlag(int homeFlag) {
		this.homeFlag = homeFlag;
	}

	public int getFakeSalesCopy() {
		return fakeSalesCopy;
	}

	public void setFakeSalesCopy(int fakeSalesCopy) {
		this.fakeSalesCopy = fakeSalesCopy;
	}

	public ProductSpec getProductSpecVo() {
		return productSpecVo;
	}

	public void setProductSpecVo(ProductSpec productSpecVo) {
		this.productSpecVo = productSpecVo;
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

	public String getListImg() {
		return listImg;
	}

	public void setListImg(String listImg) {
		this.listImg = listImg;
	}

	public int getSpecialSort() {
		return specialSort;
	}

	public void setSpecialSort(int specialSort) {
		this.specialSort = specialSort;
	}

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	public int getObjTypeId() {
		return objTypeId;
	}

	public void setObjTypeId(int objTypeId) {
		this.objTypeId = objTypeId;
	}


}
