package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SpecialVo extends BaseVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3949777745092328273L;
	
	private Integer specialId; 				//专题id
		
	private String specialName; 			//专题名称
	
	private String specialCover;   			//专题封面
	
	private String specialTitle;  			//专题标题
	
	private String  specialIntroduce;    	//专题介绍
	
	private byte specialType; 			//专题类型: 1 限时活动, 2 限量抢购, 3 团购活动 , 4 美食教程
	
	private String specialPage; 			//专题页面路径
	
	private String startTime;    				//专题开始时间
	
	private String endTime;     				//专题结束时间
	  
	private String createTime;        		//创建时间
	
	private Integer status;					//默认是1，0删除
	
	private String listImg;     				//专团购图片
	
	private int homeFlag;	//是否推荐到首页 0否 1是
	
	private int fakeSalesCopy;	//虚拟销量
	
	private int specialSort;   //排序
	
	private String objValue;	//如果是视频，则为视频时长
	
	private int objTypeId;	//视频分类ID
	
	private String productLabel; //标签
	
	private ProductSpecVo productSpecVo;
	
	private long longStart;	//long型开始时间
	private long longEnd;	//long型结束时间
	
	private int collectionCount;	//收藏数
	private int praiseCount;	//点赞数
	private int commentCount;	//评论数
	private int shareCount;	//分享次数
	private int isCollection;	//是否收藏
	private int isPraise;	//是否点赞
	private SysGroupVo sysGroupVo;	//团购信息
	

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

	public SysGroupVo getSysGroupVo() {
		return sysGroupVo;
	}

	public void setSysGroupVo(SysGroupVo sysGroupVo) {
		this.sysGroupVo = sysGroupVo;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public ProductSpecVo getProductSpecVo() {
		return productSpecVo;
	}

	public void setProductSpecVo(ProductSpecVo productSpecVo) {
		this.productSpecVo = productSpecVo;
	}

	public long getLongStart() {
		return longStart;
	}

	public void setLongStart(long longStart) {
		this.longStart = longStart;
	}

	public long getLongEnd() {
		return longEnd;
	}

	public void setLongEnd(long longEnd) {
		this.longEnd = longEnd;
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
