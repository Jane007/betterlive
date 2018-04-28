package com.kingleadsw.betterlive.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

import java.io.Serializable;


/**
 * 系统邀请好友信息
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class SysInviteVo  extends BaseVO implements Serializable{
	private static final long serialVersionUID = -6985898958874601281L;
	
	private int sysInviteId;		//id
    private String dictType;		//1.活动信息，2.邀请理由, 3.每天首次分享获取的优惠券，4.邀请获得的优惠券, 5新用户单品红包
    private int objId;				//如果是活动信息，则表示活动启用状态 1启用 0关闭，如果是优惠券类型，则对应优惠券ID；如果是新用户单品红包，则值为单品红包ID
    private String objValue;		//如果类型是1值为邀请好友封面图, 类型2值为邀请理由, 类型3值为获取红包的概率值
    private String sysDesc;			//字典描述
    private String createTime;		//创建时间
    
	public int getSysInviteId() {
		return sysInviteId;
	}
	public void setSysInviteId(int sysInviteId) {
		this.sysInviteId = sysInviteId;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public String getObjValue() {
		return objValue;
	}
	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}
	public String getSysDesc() {
		return sysDesc;
	}
	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
}