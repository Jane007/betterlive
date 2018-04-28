package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
/**
 * 促销活动Vo
 * @author zhangjing
 *
 * @date 2017年5月2日
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SalePromotionVo extends BaseVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2587423246058312856L;
	
	
	
	private Integer promotionId;    		//活动id
	private String promotionName;      		//活动名称
	private BigDecimal fullMoney;       	//满多少钱
	private BigDecimal cutMoney;       		//减多少钱
	private String promotionContent;    	//促销活动内容
	private String startTime ;				//活动开始时间
	private String endTime; 				//活动结束时间
	private String createTime;   			//活动创建时间
	private Integer status;					//状态 1正常 0 删除
	
	
	//*********** 非原始字段  *************
		private List<PromotionSpecVo> listSpec;		//活动规格集合
	
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public BigDecimal getFullMoney() {
		return fullMoney;
	}
	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}
	public BigDecimal getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(BigDecimal cutMoney) {
		this.cutMoney = cutMoney;
	}
	public String getPromotionContent() {
		return promotionContent;
	}
	public void setPromotionContent(String promotionContent) {
		this.promotionContent = promotionContent;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public List<PromotionSpecVo> getListSpec() {
		return listSpec;
	}
	public void setListSpec(List<PromotionSpecVo> listSpec) {
		this.listSpec = listSpec;
	}
	
	

}
