package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 用户开团信息
 *
 */
public class ProductGroup  extends BasePO {

	private static final long serialVersionUID = 1766602041745731562L;
	
	private int userGroupId; 			//用户团购信息ID
	private int groupId;				//系统开团信息
	private int specialId;				//活动ID
	private int originator;				//开团发起人ID
	private String specialName;			//活动名称
	private String startTime;			//开始时间
	private String endTime;				//结束时间
	private int status;					//状态 0预开团，1进行中，2已完成，3失败
	private int productId;				//商品ID
	private int specId;					//商品规格
	private String productName;			//商品名称
	private String specName;			//规格名称
	private String nickName;			//昵称
//	private String custImg;				//用户头像
	private int custNum;		//当前参团人数
	private int limitCopy;		//参团人数上限
	private String specialCover;	//封面
	
	public int getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getSpecialId() {
		return specialId;
	}
	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}
	public int getOriginator() {
		return originator;
	}
	public void setOriginator(int originator) {
		this.originator = originator;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getCustNum() {
		return custNum;
	}
	public void setCustNum(int custNum) {
		this.custNum = custNum;
	}
	public int getSpecId() {
		return specId;
	}
	public void setSpecId(int specId) {
		this.specId = specId;
	}
//	public String getCustImg() {
//		if(StringUtil.isNull(custImg)){
//			custImg = "http://www.hlife.shop/huihuo/resources/images/default_photo.png";
//		}
//		return custImg;
//	}
//	public void setCustImg(String custImg) {
//		this.custImg = custImg;
//	}
	public String getSpecialName() {
		return specialName;
	}
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public int getLimitCopy() {
		return limitCopy;
	}
	public void setLimitCopy(int limitCopy) {
		this.limitCopy = limitCopy;
	}
	public String getSpecialCover() {
		return specialCover;
	}
	public void setSpecialCover(String specialCover) {
		this.specialCover = specialCover;
	}
	
}
