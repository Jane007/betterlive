package com.kingleadsw.betterlive.model;

import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 手机接收短信消息
 */
public class MobileMessage extends BasePO{
	private static final long serialVersionUID = 6383736777743129622L;
	
	private int msgId;			//消息ID
    private int msgType;		//短信类型 1验证码， 2 营销短信
    private String msgContent;	//消息内容
    private String requestIp;	//消息内容
    private String requestMobile;	//访问地址
    private int customerId;		//用户ID
    private Date sendTime;	//发送时间
    private int sendFlag;	//发送状态：1成功 0失败
    
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
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequestMobile() {
		return requestMobile;
	}
	public void setRequestMobile(String requestMobile) {
		this.requestMobile = requestMobile;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
}