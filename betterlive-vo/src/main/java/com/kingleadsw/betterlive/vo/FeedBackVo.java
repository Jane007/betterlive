package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.Date;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 意见反馈
 */
public class FeedBackVo extends BaseVO implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6922679337249245050L;

	private int id;// 主键id

	private String content;// 反馈内容

	private String contact;// 联系方式

	private Date create_time; // 创建时间

	private Date update_time; // 修改时间

	private Integer customer_id; // 关联的用户id,没有则为0

	private Integer target;// 来源,0为ios,1为android

	private String nickname;
	
	private String mobile;
	
	
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	
}
