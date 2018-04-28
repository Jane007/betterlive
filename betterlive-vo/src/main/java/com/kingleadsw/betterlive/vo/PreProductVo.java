package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class PreProductVo extends BaseVO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5177326780966379692L;

	private Integer preId;   					//预购商品id
	  
	private Integer productId;         			//产品id
	
	private String preName;      			//预购标题
	
	private BigDecimal discountPrice; 		//预购价
	
	private Integer  raiseTarget;				//众筹目标
	
	private String raiseStart; 				//众筹开始时间
	
	private String raiseEnd;				//众筹结束时间
	
	private BigDecimal raiseMoney; 			//已筹金额
	
	private Integer supportNum;				//支持人数
	
	private String  raiseTime;					//剩余时间
	
	private String productLogo;					//页面显示logo
	
	private  List<ActivityProductVo> activityProductVo;    //预售价和规格
	
	private String  deliverTime;			//发货时间
	
	private Integer rankOrder;					//排序，排序越小越靠前
	
	private Integer limitBuy;               //限购数量
	
	private String createTime;				//创建时间
	
    private Integer status;  //商品状态
	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public Integer getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Integer supportNum) {
		this.supportNum = supportNum;
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

	public String getRaiseTime() {
		try {
			if (StringUtils.isNotBlank(raiseTime)) {
				return raiseTime;
			}
			if (this.raiseEnd == null) {
				return StringUtils.EMPTY;
			}
			long curMillis = Calendar.getInstance().getTimeInMillis();
			Timestamp st = Timestamp.valueOf(raiseEnd);
			long endMillis = st.getTime();
			long remainingMillis = endMillis - curMillis;
			long day = remainingMillis / 86400000;
			long hour = remainingMillis % 86400000 / 3600000;
			// 计算规则待定
			if (day >= 1) {
				return day + "";
			} else if (day < 1 && hour > 0) {
				return "小于1";
			} else {
				return "已结束";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setRaiseTime(String raiseTime) {
		this.raiseTime = raiseTime;
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

	public String getProductLogo() {
		
		return productLogo;
	}

	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}

	public List<ActivityProductVo> getActivityProductVo() {
		return activityProductVo;
	}

	public void setActivityProductVo(List<ActivityProductVo> activityProductVo) {
		this.activityProductVo = activityProductVo;
	}

	public Integer getLimitBuy() {
		return limitBuy;
	}

	public void setLimitBuy(Integer limitBuy) {
		this.limitBuy = limitBuy;
	}
	
	

}
