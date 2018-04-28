package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 消息
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class MessageVo  extends BaseVO implements Serializable{
	private static final long serialVersionUID = -3020051658970505702L;

	    private int msgId;			//消息ID
	    private int msgType;		//消息类型 ：0 系统消息； 1 挥货活动；2 优惠券、红包； 3 交易消息； 4 好友消息 5,新增关注消息
	    private String msgTitle;	//标题
	    private String msgDetail;	//消息内容
	    private String msgLocal;	//访问地址
	    private int isRead;			//0 未读； 1 已读
	    private int customerId;		//用户ID
	    private int objId;			//业务主键ID
	    private Date createTime;	//创建时间
	    private String createTimeStr;//格式化时间
	    private int subMsgType;		//消息详细分类：0默认，1获取优惠券，2获取红包，3优惠券过期提醒，4红包过期提醒，5订单已支付，6订单已发货，
	    							//	7订单已退款，8物流公告，9互 动-商品评价消息，10专题公告，11系统-动态审核消息，12互动-动态文章评论消息，13互动-精选文章评论消息,
	    							//	14互动-视频评论消息,15互动-商品评论点赞,16互动-动态点赞,17互动-动态评论点赞,18互动-精选评论点赞,19互动-精选点赞,20互动-视频评论点赞,21互动-视频点赞
	    							//  22互动-我的动态被评论消息,23系统-评论审核消息, 24互动-关注消息
	    private int delFlag;		//删除标识 0整常，1已删除
	    
	    //-------------非原始字段
	    private Date objTime; 		 //有效期/发布时间等
	    private String objStatus; 	 //业务状态 0 失效（通用），1有效（通用）
	    private String imgLocal;	 //图片地址
	    private int objType;		//对象类型
	    private int totalCount;		//消息总数量
	    private int unreadCount;	//未读数量
	    private String objName;		//业务名称
	    private String customerName;	//用户名或电话
	    private int orderId;		//订单ID
	    private int parentId;		//父节点
	    private int rootId;			//根节点
	    
	    private SpecialVo specialVo;
	    
	    public static final int MSGTYPE_SYS = 0; //系统消息
	    public static final int MSGTYPE_ACT = 1; //挥货活动
	    public static final int MSGTYPE_COUPON = 2; //优惠券。红包
	    public static final int MSGTYPE_TRANS = 3; //交易消息
	    public static final int MSGTYPE_FRIENDS = 4; //我的互动
	    public static final int MSGTYPE_FOLLOW = 5; //新增关注
	    
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
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public int getObjType() {
			return objType;
		}
		public void setObjType(int objType) {
			this.objType = objType;
		}
		public SpecialVo getSpecialVo() {
			return specialVo;
		}
		public void setSpecialVo(SpecialVo specialVo) {
			this.specialVo = specialVo;
		}
		public int getOrderId() {
			return orderId;
		}
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
		public String getCreateTimeStr() {
			return createTimeStr;
		}
		public void setCreateTimeStr(String createTimeStr) {
			this.createTimeStr = createTimeStr;
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public int getUnreadCount() {
			return unreadCount;
		}
		public void setUnreadCount(int unreadCount) {
			this.unreadCount = unreadCount;
		}
		public String getObjName() {
			return objName;
		}
		public void setObjName(String objName) {
			this.objName = objName;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		public int getRootId() {
			return rootId;
		}
		public void setRootId(int rootId) {
			this.rootId = rootId;
		}
		
}