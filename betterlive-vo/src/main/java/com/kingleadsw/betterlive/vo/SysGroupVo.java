package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 系统开团信息
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SysGroupVo extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = 2696902349371475855L;
	
	private int groupId; 				//开团信息ID
	private int groupCopy;				//可开团数量
	private int limitCopy;				//可拼团人数
	private int specialId;				//活动ID
	private int joinCopy;				//已团份数
	private int productId;				//产品ID;
	
	private String desc1;		//规则1
	private String desc2;		//规则2
	private String desc3;		//规则3
	private String desc4;		//规则4
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupCopy() {
		return groupCopy;
	}
	public void setGroupCopy(int groupCopy) {
		this.groupCopy = groupCopy;
	}
	public int getLimitCopy() {
		return limitCopy;
	}
	public void setLimitCopy(int limitCopy) {
		this.limitCopy = limitCopy;
	}
	public int getSpecialId() {
		return specialId;
	}
	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}
	public int getJoinCopy() {
		return joinCopy;
	}
	public void setJoinCopy(int joinCopy) {
		this.joinCopy = joinCopy;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getDesc3() {
		return desc3;
	}
	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}
	public String getDesc4() {
		return desc4;
	}
	public void setDesc4(String desc4) {
		this.desc4 = desc4;
	}
	
}
