package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SystemLotteryVo extends BaseVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long systemLotteryId;			//系统抽奖表ID
	private Integer lotteryType;			//抽奖类型：1每日签到
	private BigDecimal lotteryIntegral;		//赠送积分
	private BigDecimal probability;			//抽中概率
	private String remark;					//备注：可填奖励名称
	private long creator;					//创建人ID
	private String createTime;				//创建时间
	private long modifier;					//修改人ID
	private String modifyTime;				//修改时间
	private Integer status;					//状态：0启用，1失效
	
	public long getSystemLotteryId() {
		return systemLotteryId;
	}
	public void setSystemLotteryId(long systemLotteryId) {
		this.systemLotteryId = systemLotteryId;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public BigDecimal getLotteryIntegral() {
		return lotteryIntegral;
	}
	public void setLotteryIntegral(BigDecimal lotteryIntegral) {
		this.lotteryIntegral = lotteryIntegral;
	}
	public BigDecimal getProbability() {
		return probability;
	}
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getCreator() {
		return creator;
	}
	public void setCreator(long creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getModifier() {
		return modifier;
	}
	public void setModifier(long modifier) {
		this.modifier = modifier;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
