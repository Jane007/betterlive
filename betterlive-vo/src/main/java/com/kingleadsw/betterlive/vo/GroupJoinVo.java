package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.StringUtil;

/**
 * 用户拼团信息
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class GroupJoinVo extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = 710858636655944254L;
	
	private int groupJoinId;			//用户拼团ID
	private int userGroupId; 			//开团信息ID
	private int groupId;				//系统开团信息
	private int specialId;				//活动ID
	private int originator;				//开团发起人ID
	private Date createTime;			//创建时间
	private int status;					//状态 0待完成，1已完成，2失败
	private int productId;				//商品ID
	private int customerId;				//参团人ID
	private int specId;					//商品规格ID
	private int buyNum;					//用户团购单次购买次数
	private int totalBuyNum;			//用户当前团购买总数量
	
	private String mobile;				//参团人电话	
	private String custImg;				//参团人头像	
	
	public int getGroupJoinId() {
		return groupJoinId;
	}
	public void setGroupJoinId(int groupJoinId) {
		this.groupJoinId = groupJoinId;
	}
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
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getSpecId() {
		return specId;
	}
	public void setSpecId(int specId) {
		this.specId = specId;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public int getTotalBuyNum() {
		return totalBuyNum;
	}
	public void setTotalBuyNum(int totalBuyNum) {
		this.totalBuyNum = totalBuyNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCustImg() {
		if(StringUtil.isNull(custImg)){
			custImg = "http://www.hlife.shop/huihuo/resources/images/default_photo.png";
		}
		return custImg;
	}
	public void setCustImg(String custImg) {
		this.custImg = custImg;
	}
}
