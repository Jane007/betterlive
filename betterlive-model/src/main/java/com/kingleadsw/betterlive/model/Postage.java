package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 运费对象
 * @author ltp
 *
 */
public class Postage extends BasePO {

	private static final long serialVersionUID = 2113733973361727864L;
	
	private int postageId;			//运费模板Id，自增长
	private String postageName;		//运费模板名称
	private int postageType;		//模板类型：1价格类
	private String meetConditions;		//包邮的条件（满包邮是价格）
	private String postage;		//不满足条件的运费价格
	private String productIds;		//匹配本模板的商品id集合
	private String postageMsg;     //商品详情页关于运费模板的描述
	
	public String getPostageMsg() {
		return postageMsg;
	}
	public void setPostageMsg(String postageMsg) {
		this.postageMsg = postageMsg;
	}
	public int getPostageId() {
		return postageId;
	}
	public void setPostageId(int postageId) {
		this.postageId = postageId;
	}
	public String getPostageName() {
		return postageName;
	}
	public void setPostageName(String postageName) {
		this.postageName = postageName;
	}
	public int getPostageType() {
		return postageType;
	}
	public void setPostageType(int postageType) {
		this.postageType = postageType;
	}
	public String getMeetConditions() {
		return meetConditions;
	}
	public void setMeetConditions(String meetConditions) {
		this.meetConditions = meetConditions;
	}
	public String getPostage() {
		return postage;
	}
	public void setPostage(String postage) {
		this.postage = postage;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	
	
}
