package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 手机短信消息
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class MobileMessageVo  extends BaseVO implements Serializable{

	private static final long serialVersionUID = -3090313316397220343L;
		
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