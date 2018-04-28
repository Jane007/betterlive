package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;


/**
 * 标签
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ProductLabelVo  extends BaseVO implements Serializable{
	
	private static final long serialVersionUID = -654107832911249663L;
	
	private int productLabelId; //标签主键
	private int labelType; //1新品、2拼团、3抢购、4满减、5爆款、6自定义
	private String labelTitle; //标签名称
	private String showStart;	//启用时间
	private String showEnd;	//启用截止时间
	private int productId;	//商品ID
	private int status;		//状态 0 正常 1失效
	private String product_code;      //商品编号
	private String product_name;      //商品名称
	private Integer product_status;           //状态：  1上架 2 下架
	private String createTime;
	
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public int getProductLabelId() {
		return productLabelId;
	}
	public void setProductLabelId(int productLabelId) {
		this.productLabelId = productLabelId;
	}
	public int getLabelType() {
		return labelType;
	}
	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}
	public String getLabelTitle() {
		return labelTitle;
	}
	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getShowStart() {
		return showStart;
	}
	public void setShowStart(String showStart) {
		this.showStart = showStart;
	}
	public String getShowEnd() {
		return showEnd;
	}
	public void setShowEnd(String showEnd) {
		this.showEnd = showEnd;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getProduct_status() {
		return product_status;
	}
	public void setProduct_status(Integer product_status) {
		this.product_status = product_status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	

}