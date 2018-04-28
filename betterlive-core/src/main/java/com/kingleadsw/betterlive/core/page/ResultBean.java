package com.kingleadsw.betterlive.core.page;



import java.io.Serializable;

import com.kingleadsw.betterlive.core.util.JacksonUtil;

/**
 * Ajax 请求的 返回结果类
 * @author fox
 */
public class ResultBean implements Serializable{
	
	/**
	 * 
	 */
	private String result = SUCCESS;
	/**
	 * 响应结果描述
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private Object respData;
	
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String TIMEOUT = "timeout";
	private static final String FORBIDDEN = "forbidden";
	private static final String SIMPLE_SUCCESS_JSON = new ResultBean(SUCCESS).toJsonString();
	private static final String SIMPLE_FAILURE_JSON = new ResultBean(FAILURE, null).toJsonString();
	private static final String SIMPLE_TIMEOUT_JSON = new ResultBean(TIMEOUT, "您的登录已超时，请重新登录").toJsonString();
	private static final String SIMPLE_FORBIDDEN_JSON = new ResultBean(FAILURE, FORBIDDEN, FORBIDDEN).toJsonString();
	
	public ResultBean(){
		
	}
	
	/**
	 * 构造方法私有，不允许在外部构建
	 * @param respData
	 */
	public ResultBean(Object respData){
		this.respData = respData;
	}
	
	public ResultBean(String result, String message){
		this.result = result;
		this.message = message;
	}
	public ResultBean(String result, String message, Object respData) {
		this.result = result;
		this.message = message;
		this.respData = respData;
	}
	
	/**
	 * 成功
	 * @param message
	 * @return
	 */
	public static ResultBean createResultBean(Object message){
		return new ResultBean(message);
	}
	
	/**
	 * 失败
	 * @param message
	 * @return
	 */
	public static ResultBean createFailureResultBean(String message){
		return new ResultBean(FAILURE, message);
	}
	
	/**
	 * Service异常
	 * @param message
	 * @return
	 */
	public static ResultBean createServiceFail(String message){
		return new ResultBean(FAILURE, message);
	}
	
	/**
	 * 附带响应数据的ResultBean
	 * @param message
	 * @return
	 */
	public static ResultBean createResultBean(String message, Object respData){
		return new ResultBean(SUCCESS, message, respData);
	}
	
	/**
	 * 附带类型参数的结果
	 * @param result 响应结果，一般为success或fail或其他响应码
	 * @param message 返回消息描述
	 * @return
	 */
	public static ResultBean getResultBean(String result, String message){
		return new ResultBean(result, message);
	}
	
	/**
	 * 获取成功 JSON 字符串
	 * @return
	 */
	public static String getSuccessJsonString(){
		return SIMPLE_SUCCESS_JSON;
	}
	
	/**
	 * 获取失败 JSON 字符串
	 * @return
	 */
	public static String getFailureJsonString(){
		return SIMPLE_FAILURE_JSON;
	}
	
	/**
	 * 获取超时 Json 字符串
	 * @return
	 */
	public static String getTimeOutJsonString(){
		return SIMPLE_TIMEOUT_JSON;
	}
	
	/**
	 * 获取鉴权失败字符串
	 * @return
	 */
	public static String getForbiddenJsonString(){
		return SIMPLE_FORBIDDEN_JSON;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getRespData() {
		return respData;
	}
	public void setRespData(Object respData) {
		this.respData = respData;
	}
	
	/**
	 * 转 Json 字符串
	 * @return
	 */
	public String toJsonString(){
		return JacksonUtil.serializeObjectToJson(this);
	}
}
