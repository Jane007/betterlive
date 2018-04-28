package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CustomerIntegralRecordVo extends BaseVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long recordId;				//用户积分记录ID
	private long customerId;			//用户ID
	private BigDecimal integral;			//积分数
	private Integer integralType;		//积分类型：0系统赠送,1每日签到,2发动态,3分享文章（动态、精选）,4分享视频,5、分享文章点赞 6、分享视频点赞 ,7订单支付
	private Integer recordType;			//收支类型：0收入 1支出
	private String createTime;			//创建时间
	private Integer status;				//状态：0等待中，1已处理，2已失效
	private long objId;					//业务主键ID：文章/视频/订单明细ID
	
	//----------------------------------------非数据库字段
	private Integer wday;				//周几
	
	private int praiseCount;	//点赞数
	private String imgUrl;		//图片
	private String title;		//标题
	private String linkUrl;		//链接地址
	private String shareDesc;	//分享描述
	private String appDesc;		//app描述
	private Integer productId;	//productId，供页面使用
	public long getRecordId() {
		return recordId;
	}
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getIntegral() {
		return integral;
	}
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}
	public Integer getIntegralType() {
		return integralType;
	}
	public void setIntegralType(Integer integralType) {
		this.integralType = integralType;
	}
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
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
	public long getObjId() {
		return objId;
	}
	public void setObjId(long objId) {
		this.objId = objId;
	}
	public Integer getWday() {
		return wday;
	}
	public void setWday(Integer wday) {
		this.wday = wday;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getShareDesc() {
		return shareDesc;
	}
	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
