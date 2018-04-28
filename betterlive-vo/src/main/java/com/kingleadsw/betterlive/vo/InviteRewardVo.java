package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.util.StringUtil;

/**
 * 邀请好友奖励
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class InviteRewardVo extends BaseVO {
	
	private int rewardId;		//id
    private int customerId;		//用户ID
    private int rewardType;		//奖励类型1 每日首次分享获得优惠券 2邀请注册获取券 3新用户获取单品红包
    private int objId;	//业务主键ID 类型为1/2值为用户优惠券，类型为3值为单品红包
    private String createTime;	//创建时间
    
    //非原始字段
    private String objName;		//业务名称
    private int objCount;		//数量
    private String nickname;	//昵称
    private String headUrl;		//用户头像
    
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getRewardType() {
		return rewardType;
	}
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public int getObjCount() {
		return objCount;
	}
	public void setObjCount(int objCount) {
		this.objCount = objCount;
	}
	public String getNickname() {
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
    
}
