package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 优惠券兑换活动表 VO
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SysCashVo  extends BaseVO implements Serializable{
	private static final long serialVersionUID = 1408592495783379173L;
	
	private int sysId; //ID
	private String bandCoupon; //对应的系统优惠券ID
	private int bandType;	//兑换类型 1优惠券 2单品红包
	private String createTime;	//创建时间
	private String startTime;	//开始时间
	private String endTime;	//结束时间
	private int status;		//状态 0进行中 1失效
	private String activityName;	//兑换活动的名称
	private String tipPic;	//兑换区的背景说明图
	private int productId;	//商品ID
	
	public int getSysId() {
		return sysId;
	}
	public void setSysId(int sysId) {
		this.sysId = sysId;
	}
	public String getBandCoupon() {
		return bandCoupon;
	}
	public void setBandCoupon(String bandCoupon) {
		this.bandCoupon = bandCoupon;
	}
	public int getBandType() {
		return bandType;
	}
	public void setBandType(int bandType) {
		this.bandType = bandType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getTipPic() {
		return tipPic;
	}
	public void setTipPic(String tipPic) {
		this.tipPic = tipPic;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}