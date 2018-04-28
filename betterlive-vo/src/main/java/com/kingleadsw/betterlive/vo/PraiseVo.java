package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;
import java.util.Date;


/**
 * 点赞实体类
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class PraiseVo  extends BaseVO implements Serializable{

	private static final long serialVersionUID = -5869051954704233989L;
	
	private int praiseId;	//点赞ID
	private int customerId;	//点赞人ID
	private Date praiseTime;	//点赞时间
	private int praiseType;	//点赞类型 1商品买单评论点赞  2美食教程点赞 3美食教程评论点赞 4文章点赞 5文章评论点赞
	private int objId;	//业务主键ID
	private int shareCustomerId;	//分享人ID
	
	public int getPraiseId() {
		return praiseId;
	}
	public void setPraiseId(int praiseId) {
		this.praiseId = praiseId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getPraiseTime() {
		return praiseTime;
	}
	public void setPraiseTime(Date praiseTime) {
		this.praiseTime = praiseTime;
	}
	public int getPraiseType() {
		return praiseType;
	}
	public void setPraiseType(int praiseType) {
		this.praiseType = praiseType;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public int getShareCustomerId() {
		return shareCustomerId;
	}
	public void setShareCustomerId(int shareCustomerId) {
		this.shareCustomerId = shareCustomerId;
	}

}