package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.StringUtil;

/**
 * 用户粉丝
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class CustomerFansVo extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = -9139119347995781669L;
	
	private int fansId;
	
	private int customerId;
	
	private int concernedId;
	
	//----------非原始字段----------
	private int customerCount;
	private int concernedCount;
	private String nickname;	//昵称
	private String headUrl;		//头像
	private int isConcerned;	//是否关注
	private int isConcernedMe;	//对方是否关注了我
	private String signature;	//个性签名
	private List<SpecialArticleVo> articleVos;//用户文章

	public int getFansId() {
		return fansId;
	}

	public void setFansId(int fansId) {
		this.fansId = fansId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getConcernedId() {
		return concernedId;
	}

	public void setConcernedId(int concernedId) {
		this.concernedId = concernedId;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getConcernedCount() {
		return concernedCount;
	}

	public void setConcernedCount(int concernedCount) {
		this.concernedCount = concernedCount;
	}

	public String getNickname() {
		if(StringUtil.isNull(nickname)){
			nickname = "小挥挥";
		}
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadUrl() {
		if(StringUtil.isNull(headUrl)){
			headUrl = "http://www.hlife.shop/huihuo/resources/images/default_photo.png";
		}
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public int getIsConcerned() {
		return isConcerned;
	}

	public void setIsConcerned(int isConcerned) {
		this.isConcerned = isConcerned;
	}

	public int getIsConcernedMe() {
		return isConcernedMe;
	}

	public void setIsConcernedMe(int isConcernedMe) {
		this.isConcernedMe = isConcernedMe;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<SpecialArticleVo> getArticleVos() {
		return articleVos;
	}

	public void setArticleVos(List<SpecialArticleVo> articleVos) {
		this.articleVos = articleVos;
	}
	
	
	
}
