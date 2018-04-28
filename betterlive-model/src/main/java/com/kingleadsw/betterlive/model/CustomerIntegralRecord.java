package com.kingleadsw.betterlive.model;


import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class CustomerIntegralRecord extends BasePO {
	private static final long serialVersionUID = 1L;
	
	private long recordId;				//用户积分记录ID
	private long customerId;			//用户ID
	private BigDecimal integral;		//积分数
	private Integer integralType;		//积分类型：0系统赠送,1每日签到,2发动态,3分享文章（动态、精选）,4分享视频,5、分享文章点赞 6、分享视频点赞 ,7订单支付
	private Integer recordType;			//收支类型：0收入 1支出
	private String createTime;			//创建时间
	private Integer status;				//状态：0等待中，1已处理，2已失效
	private long objId;					//业务主键ID：文章/视频/订单明细ID
	private String modifyTime;			//修改时间 
	
	//##########非数据库字段#####################
	private Integer wday;				//周几
	private Long checkRecordId;			//校验积分记录ID
	private int praiseCount;			//点赞数
	
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
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getCheckRecordId() {
		return checkRecordId;
	}
	public void setCheckRecordId(Long checkRecordId) {
		this.checkRecordId = checkRecordId;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	
}
