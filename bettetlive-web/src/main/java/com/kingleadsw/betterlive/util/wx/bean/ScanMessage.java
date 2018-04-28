package com.kingleadsw.betterlive.util.wx.bean;





/**
 * 扫码事件推送
 * 
 * @author Administrator
 *
 */
public class ScanMessage extends Message {
	/**
	 * 开发者微信号
	 */
	private String ToUserName;

	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;

	/**
	 * 消息创建时间 （整型）
	 */
	private String CreateTime;

	/**
	 * 消息类型
	 */
	private String MsgType;

	/**
	 * 事件类型
	 */
	private String Event;

	/**
	 * 事件KEY值，设置的跳转URL
	 */
	private String EventKey;

	private String Ticket;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String Ticket) {
		this.Ticket = Ticket;
	}

}
