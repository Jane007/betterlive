package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 * 预售 模型
 * @author zhangjing
 * @date 2017年3月11日 上午10:55:00
 */
public class PreProduct extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7591814732516721827L;
	
	private Integer preId;   					//预购商品id
	  
	private Integer productId;         			//产品id
	
	private String preName;      			//预购标题
	
	private BigDecimal discountPrice; 		//预购价
	
	private Integer  raiseTarget;				//众筹目标
	
	private String raiseStart; 				//众筹开始时间
	
	private String raiseEnd;				//众筹结束时间
	
	private BigDecimal raiseMoney; 			//已筹金额
	
	private Integer supportNum;				//支持人数
	
	private String  deliverTime;			//发货时间
	
	private Integer rankOrder;					//排序，排序越小越靠前
	
	private String createTime;				//创建时间
	
	private String productLogo;				//logo
	
	private Integer limitBuy;               //限购数量
	
	private Integer status;  //商品状态
	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPreId() {
		return preId;
	}

	public void setPreId(Integer preId) {
		this.preId = preId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getRaiseTarget() {
		return raiseTarget;
	}

	public void setRaiseTarget(Integer raiseTarget) {
		this.raiseTarget = raiseTarget;
	}

	public String getRaiseStart() {
		return raiseStart;
	}

	public void setRaiseStart(String raiseStart) {
		this.raiseStart = raiseStart;
	}

	public String getRaiseEnd() {
		return raiseEnd;
	}

	public void setRaiseEnd(String raiseEnd) {
		this.raiseEnd = raiseEnd;
	}

	public BigDecimal getRaiseMoney() {
		return raiseMoney;
	}

	public void setRaiseMoney(BigDecimal raiseMoney) {
		this.raiseMoney = raiseMoney;
	}


	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Integer getRankOrder() {
		return rankOrder;
	}

	public void setRankOrder(Integer rankOrder) {
		this.rankOrder = rankOrder;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Integer supportNum) {
		this.supportNum = supportNum;
	}

	public String getProductLogo() {
		return productLogo;
	}

	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}

	public Integer getLimitBuy() {
		return limitBuy;
	}

	public void setLimitBuy(Integer limitBuy) {
		this.limitBuy = limitBuy;
	}

	
	

}
