package com.kingleadsw.betterlive.common;

/**
 * 描 述：webservice 接口返回实体
 * 
 * @author
 * @date 
 * @version
 */
public class WsResult {
	private String returnCode; // 返回操作代码
	private String returnMsg; // 返回操作信息
	private Object data = null; // 返回操作结果

	public static final String SUCCESS_CODE = "000000";
	public static final String SUCCESS_INFO = "成功";
	
	public static final String FAILURE_CODE = "000001";
	public static final String FAILURE_INFO = "失败";
	
	public static final String PARAMETER_CODE = "000002";
	public static final String PARAMETER_INFO = "请求参数错误";
	
	public static final String TIMEOUT_CODE = "000003";
	public static final String TIMEOUT_INFO = "该账号长时间未登录，请重新登录";
	public static final String OTHER_EQUIPMENT_INFO = "该账户在其它设备登录，请重新登录";
	
	public static final String UNPAYSALE_CODE = "000004";
	public static final String UNPAYSALE_INFO = "订单中存在未支付的信息，请勿重复提交";
	
	public static final String SERVICEOUT_CODE = "000005";
	public static final String SERVICEOUT_INFO = "服务时间过期";
	
	public static final String IMPORTANT_CODE = "000006";
	public static final String IMPORTANT_INFO = "重要错误提示";

	public static WsResult SUCCESS()
	{
		return new WsResult(SUCCESS_CODE, SUCCESS_INFO, null);
	}
	
	public static WsResult FAILURE(){
		return new WsResult(FAILURE_CODE, FAILURE_INFO, null);
	}
	
	public static WsResult PARAMETER(){
		return new WsResult(PARAMETER_CODE, PARAMETER_INFO, null);
	}
	
	public static WsResult TIMEOUT(){
		return new WsResult(TIMEOUT_CODE, TIMEOUT_INFO, null);
	}
	
	public static WsResult OTHEREQUIPMENT(){
		return new WsResult(TIMEOUT_CODE, OTHER_EQUIPMENT_INFO, null);
	}
	
	public static WsResult SERVICEOUT(){
		return new WsResult(SERVICEOUT_CODE, SERVICEOUT_INFO, null);
	}
	
	public static WsResult UNPAYSALE(){
		return new WsResult(UNPAYSALE_CODE, UNPAYSALE_INFO, null);
	}

	public WsResult(){
		
	}
	
	public WsResult(String returnCode, String returnMsg){
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}
	
	public WsResult(Object data){
		this.returnCode = SUCCESS_CODE;
		this.returnMsg = SUCCESS_INFO;
		this.data = data;
	}
	
	public WsResult(String returnCode, String returnMsg, Object data){
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.data = data;
	}
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

