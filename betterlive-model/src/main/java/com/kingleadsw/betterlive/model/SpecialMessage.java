package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class SpecialMessage  extends BasePO{
	private int msgId;			//消息ID
    private int msgType;		//消息类型 ：0 系统消息； 1 挥货活动；2 优惠券、红包； 3 交易消息； 4 好友消息 
    private String msgTitle;	//标题
    private String msgDetail;	//消息内容
    private String msgLocal;	//访问地址
    private int isRead;			//0 未读； 1 已读
    private int customerId;		//用户ID
    private int objId;			//业务主键ID
    private Date createTime;	//创建时间
    private int subMsgType;		//消息详细分类：0默认，1获取优惠券，2获取红包，3优惠券过期提醒，4红包过期提醒，5订单已支付，6订单已发货，7订单已退款，8物流公告，9评价消息，10商品公告
    private int delFlag;		//删除标识 0整常，1已删除
    
    private Date objTime; 		 //有效期/发布时间等
    private String objStatus; 	 	 //业务状态  0 失效（通用），1有效（通用）
    private String imgLocal;	 //图片地址
    private String nickname;	//用户名或电话
    private String mobile;	//用户名或电话
	
	private String specialName; 			//专题名称
	
	private String specialTitle;  			//专题标题
	
	private String  specialIntroduce;    	//专题介绍
	
	private byte specialType; 			//专题类型，1：限时活动
	
	private String startTime;    				//专题开始时间
	
	private String endTime;     				//专题结束时间
	  
	private Integer status;					//删除是1，0默认

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgDetail() {
		return msgDetail;
	}

	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}

	public String getMsgLocal() {
		return msgLocal;
	}

	public void setMsgLocal(String msgLocal) {
		this.msgLocal = msgLocal;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSubMsgType() {
		return subMsgType;
	}

	public void setSubMsgType(int subMsgType) {
		this.subMsgType = subMsgType;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Date getObjTime() {
		return objTime;
	}

	public void setObjTime(Date objTime) {
		this.objTime = objTime;
	}

	public String getObjStatus() {
		return objStatus;
	}

	public void setObjStatus(String objStatus) {
		this.objStatus = objStatus;
	}

	public String getImgLocal() {
		return imgLocal;
	}

	public void setImgLocal(String imgLocal) {
		this.imgLocal = imgLocal;
	}

	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
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

	public byte getSpecialType() {
		return specialType;
	}

	public void setSpecialType(byte specialType) {
		this.specialType = specialType;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
