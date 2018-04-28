package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 用户开团信息
 *
 */
public class UserGroup  extends BasePO {
	
	private static final long serialVersionUID = -2780247338838993292L;
	
	private int userGroupId; 			//用户团购信息ID
	private int groupId;				//系统开团信息
	private int specialId;				//活动ID
	private int originator;				//开团发起人ID
	private Date createTime;			//创建时间
	private int status;					//状态 0预开团，1进行中，2已完成，3失败
	private int productId;				//商品ID
	private int specId;					//商品规格
	
	private String nickName;			//昵称
	private String custImg;				//用户头像
	private int custNum;		//当前参团人数
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getCustImg() {
		return custImg;
	}
	public void setCustImg(String custImg) {
		this.custImg = custImg;
	}
	
}
