package com.kingleadsw.betterlive.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 收货地址Vo
 * @author zhangjing
 * @date 2017年3月16日 上午10:40:01
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ReceiverAddressVo extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 637116306829005902L;
	
	
	private Integer receiverId;   		//收货人id
	
	private Integer customerId;	  		//用户id
	
	private String mobile;				//手机号码
	
	private String receiverName; 		//收货人姓名
	     
	private Integer provinceId;			//省份
	
	private Integer cityId;				//城市
	
	private Integer areaId;				//区(镇)
	
	private String address;				//收货人详细地址
	
	private byte isDel;					//状态，1：有效，0：删除
	
	private byte isDefault;				//是否默认，1：是，0：否
	
	
	private String createTime;     		//创建时间

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getIsDel() {
		return isDel;
	}

	public void setIsDel(byte isDel) {
		this.isDel = isDel;
	}

	public byte getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	
}
