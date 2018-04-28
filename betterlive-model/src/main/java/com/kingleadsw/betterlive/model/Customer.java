package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;

public class Customer extends BasePO {
	
	private static final long serialVersionUID = -2324993796910113082L;
	
	private Integer customer_id;		//客户id
	private String openid;			//公众号唯一标识
	private String unionid;  		//微信unionid
	private String head_url;		//用户头像地址
    private String nickname;		//客户昵称
    private Integer sex;  			//客户性别
    private String birthday;  		//生日
    private String mobile;			//手机号
    private String password;  		//账户登录密码
    private String create_time;		//创建时间
    private String pay_pwd;      	//支付密码
	private Integer subscribe;       //是否关注
	private String source; //用户来源
    private String token; //用户token
    private String signature;       //个性签名
    private Integer inviterId;		//邀请人ID
    private String yktAgrNo;			//一卡通支付客户协议号
    
    
    private Integer levelId;		//等级id
    private String levelName;		//会员等级名称
    private BigDecimal currentIntegral; //当前会员可用积分
    private BigDecimal accumulativeIntegral; //会员累积积分
   
    
    //-----------------------------------------------
    
	public String getOpenid() {
		return openid;
	}
	public String getYktAgrNo() {
		return yktAgrNo;
	}
	public void setYktAgrNo(String yktAgrNo) {
		this.yktAgrNo = yktAgrNo;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHead_url() {
		return head_url;
	}
	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getPay_pwd() {
		return pay_pwd;
	}
	public void setPay_pwd(String pay_pwd) {
		this.pay_pwd = pay_pwd;
	}
	public Integer getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Integer getInviterId() {
		return inviterId;
	}
	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public BigDecimal getCurrentIntegral() {
		return currentIntegral;
	}
	public void setCurrentIntegral(BigDecimal currentIntegral) {
		this.currentIntegral = currentIntegral;
	}
	public BigDecimal getAccumulativeIntegral() {
		return accumulativeIntegral;
	}
	public void setAccumulativeIntegral(BigDecimal accumulativeIntegral) {
		this.accumulativeIntegral = accumulativeIntegral;
	}
	 
	
}