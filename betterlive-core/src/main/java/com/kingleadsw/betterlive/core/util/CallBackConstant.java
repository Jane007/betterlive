package com.kingleadsw.betterlive.core.util;

import java.util.HashMap;
import java.util.Map;

public enum CallBackConstant {

	SUCCESS(1010, "操作成功"),
	FAILED(1020, "操作失败"),
	PARAMETER_ERROR(1021,"参数异常"),
	NOT_EXIST(1022,"该用户名不存在"),
	REGISTERED(1023,"该手机号码已注册"),
	TOKEN_TIME_OUT(1024,"会话超时"),
	USER_MISMATCH(1025,"用户信息不匹配"),
	REQUEST_TIME_OUT(1026,"请求超时"),
	VERIFI_CODE_ERROR(1027,"验证码不一致"),
	LOGIN_TIME_OUT(1011,"登录超时，请重新登录"),
	TOKEN_ERROR(1033,"token不能为空"),
	INVITEDCODE_WRONG(1030,"邀请码有误"),
	DATA_TIME_TOU(1028,"数据过期"),
	PASSWORD_WRONG(1029,"密码错误"),
	DATA_NOT_FOUND(1404,"数据未找到"),
	DATA_HAD_FOUND(1405,"数据已存在"),

	UPLOAD_FILE_ERROR(1025,"文件上传失败");

	private Integer code;

	private String msg;
	
	private String json;
	
	private CallBackConstant(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public Map<String, Object> callback() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	public Map<String, Object> callBackByMsg(String msg) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", msg);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	/**
	 * 返回json
	 * @param obj
	 * @return
	 */
	public Map<String, Object> callback(Object obj) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		map.put("data", obj);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	/**
	 * 返回json
	 * @param obj
	 * @return
	 */
	public Map<String, Object> callbackPageInfo(Object obj, Object pageInfo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		map.put("data", obj);
		map.put("pageInfo", pageInfo);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	
	/**
	 * 返回错误信息
	 * @param msg 提示信息
	 * @return
	 */
	public Map<String, Object> callbackError(String msg) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", msg);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	/**
	 * 正常返回json信息
	 * @param msg 提示信息
	 * @param json 
	 * @return
	 */
	public Map<String, Object> callback(String msg, String json) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", msg);
		map.put("data", json);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	public Map<String, Object> callbackUrl(String msg, String url) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", msg);
		map.put("data", url);
		map.put("currtime",System.currentTimeMillis());
		return map;
	}
	
	

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("{\"code\":\"");
		res.append(this.code);
		res.append("\", \"msg\":\"");
		res.append(this.msg);
		res.append("\", \"data\":");
		res.append(this.json);
		res.append("}");
		return res.toString();
	}
	
}
