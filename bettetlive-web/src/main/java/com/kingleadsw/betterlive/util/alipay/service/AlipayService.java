package com.kingleadsw.betterlive.util.alipay.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.common.Constant;
import com.kingleadsw.betterlive.common.WsResult;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.pay.alipay.config.AlipayConfig;
import com.kingleadsw.betterlive.pay.alipay.model.AlipayRequst;
import com.kingleadsw.betterlive.pay.alipay.util.AliPayBizParam;
import com.kingleadsw.betterlive.pay.alipay.util.AliPayForm;
import com.kingleadsw.betterlive.pay.alipay.util.AliPayFormForWap;
import com.kingleadsw.betterlive.pay.alipay.util.AliPayQuery;
import com.kingleadsw.betterlive.pay.alipay.util.AliPayQueryBizParam;
import com.kingleadsw.betterlive.pay.alipay.util.AliPaySubmit;
import com.kingleadsw.betterlive.pay.alipay.util.AlipayCore;
import com.kingleadsw.betterlive.pay.alipay.util.httpclient.AliOrderRequest;
import com.kingleadsw.betterlive.vo.OrderVo;


/**
 * 支付宝服务
 * @copright 
 * @author 
 * @date 
 * @version 
 */
@Service("alipayService")
public class AlipayService {
	
//	@Autowired
//	private OrderInfoService orderInfoService;
//	@Autowired
//	private EquipmentApplyService equipmentApplyService;
//	@Autowired
//	private AppointmentInfoService appointmentInfoService;
	@Autowired
	private  OrderManager orderManager;
	
	private static Logger logger = Logger.getLogger(AlipayService.class);
	
	//---------------------支付宝请求支付并加签Start--------------------------------//
	/**
	 * 支付宝APP支付(预支付)
	 * @param request
	 * @param orderName 订单名称
	 * @param orderNo 本地订单号(非第三方)
	 * @param totalPrice 在线支付金额
	 * @param appId 微信appId
	 * @param notifyUrl 支付结果notify地址
	 * @param appPriKey 支付宝app私钥
	 * @return
	 */
//	public static AlipayRequst aliPrepayPay(HttpServletRequest request, String orderName, String orderNo, String totalPrice, String appId, String notifyUrl, String appPriKey) {
	public static AlipayRequst aliPrepayPay(HttpServletRequest request,String totalPrice, String orderNo,  String orderName, String notifyUrl) {
		//封装阿里支付需要的信息 start
		AliPayForm form = new AliPayForm();
		String appId = AlipayConfig.AliPay_APPID_HUIHUO;
		String appPriKey = AlipayConfig.Private_Key_HUIHUO_APP;
		form.setApp_id(appId);
		form.setMethod(AlipayConfig.AliPayForm_Method_Pay);
		form.setFormat(AlipayConfig.AliPayForm_Format);
		form.setCharset(AlipayConfig.AliPayForm_Charset);
		form.setSign_type(AlipayConfig.AliPayForm_SignType);
		form.setTimestamp(DateUtil.getCurrentDateInSecond());
		form.setVersion(AlipayConfig.AliPayForm_Version);
		form.setNotify_url(notifyUrl);
		//业务参数封装
		AliPayBizParam busParam = new AliPayBizParam();
		//超时125min
		busParam.setTimeout_express(AlipayConfig.AliPayForm_Timeout_Express);
		busParam.setProduct_code(AlipayConfig.AliPayForm_ProductCode);
		busParam.setTotal_amount(totalPrice);
		busParam.setSubject(orderName);
		busParam.setBody(orderName);
		//这里加
		busParam.setApp_pay("Y");
		busParam.setOut_trade_no(orderNo);
		//元为单位
		Map<String, String> sParaTemp = new HashMap<String, String>();
		Map<String, String> busParaTemp = new HashMap<String, String>();
		try {
			busParaTemp = BeanUtils.describe(busParam);
		} catch (IllegalAccessException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		} catch (InvocationTargetException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		} catch (NoSuchMethodException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		}
		busParaTemp.remove("class");
		//业务参数拼串
		String busReque = AliPaySubmit.buildBizRequest(busParaTemp);
		form.setBiz_content(busReque);
		try {
			sParaTemp = BeanUtils.describe(form);
		} catch (IllegalAccessException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		} catch (InvocationTargetException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		} catch (NoSuchMethodException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		}
		sParaTemp.remove("class");
		//封装阿里支付需要的信息end
		Map<String, String> signMap = AliPaySubmit.buildRequest(sParaTemp, appPriKey);
		AlipayRequst alipayRequst = new AlipayRequst();
		try {
			BeanUtils.copyProperties(alipayRequst, busParam);
			BeanUtils.copyProperties(alipayRequst, form);
		} catch (IllegalAccessException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		} catch (InvocationTargetException e) {
			logger.error("AlipayService/aliPrepayPay --error", e);
		}
		String sign = (String) signMap.get("sign");
		if (StringUtils.isBlank(sign)) {
			return null;
		}
		
		//请求参数encode--start
		signMap.put("sign", sign);
		Set<String> keySet = signMap.keySet();
		Map<String, String> encodeMap = new HashMap<String, String>();
		for (String key : keySet) {
			String value = signMap.get(key);
			try {
				String encodeV = URLEncoder.encode(value, AlipayConfig.AliPayForm_Charset);
				encodeMap.put(key, encodeV);
			} catch (UnsupportedEncodingException e) {
				logger.error("AlipayService/aliPrepayPay --error", e);
			}
		}
		String encodeStr = AlipayCore.createLinkString(encodeMap);
		alipayRequst.setSign(sign);
		alipayRequst.setApp_id(encodeStr);
		
//		alipayRequst.setApp_id(encodeMap.get("app_id"));
//		alipayRequst.setMethod(encodeMap.get("method"));
//		alipayRequst.setFormat(encodeMap.get("format"));
//		alipayRequst.setCharset(encodeMap.get("charset"));
//		alipayRequst.setSign_type(encodeMap.get("sign_type"));
//		alipayRequst.setTimestamp(encodeMap.get("timestamp"));
//		alipayRequst.setVersion(encodeMap.get("version"));
//		alipayRequst.setNotify_url(encodeMap.get("notify_url"));
//		alipayRequst.setBiz_content(encodeMap.get("biz_content"));
		//订单号回传给app
//		alipayRequst.setOrderNo(orderNo);
		return alipayRequst;
	}
	//---------------------支付宝请求支付并加签Start--------------------------------//

	/**
	 * 查询订单结果
	 * @param orderNo 本地订单号(非第三方)
	 * @param transactionId	支付回执订单号
	 * @param totalprice 在线支付总金额
	 * @param appId	微信appId
	 * @param appPriKey 微信app私钥
	 * @param appPubKey 微信app公钥
	 */
	public static WsResult orderQueryResult(String orderNo, String transactionId, String totalPrice, String appId, String appPriKey, String aliPubKey) {
		WsResult wsResult = new WsResult(); 
		//封装阿里支付需要的信息 start
		AliPayQuery query = new AliPayQuery();
		query.setApp_id(appId);
		query.setMethod(AlipayConfig.AliPayForm_Method_Query);
		query.setFormat(AlipayConfig.AliPayForm_Format);
		query.setCharset(AlipayConfig.AliPayForm_Charset);
		query.setSign_type(AlipayConfig.AliPayForm_SignType);
		query.setTimestamp(DateUtil.getCurrentDateInSecond());
		query.setVersion(AlipayConfig.AliPayForm_Version);
		//业务参数封装
		AliPayQueryBizParam bizParam = new AliPayQueryBizParam();
		if (!StringUtils.isBlank(orderNo)) {//参数是那几个我让前端调用
			bizParam.setOut_trade_no(orderNo);
			bizParam.setTrade_no("");
		}else if (!StringUtils.isBlank(transactionId)) {
			bizParam.setTrade_no(transactionId);
			bizParam.setOut_trade_no("");
		}else {
			return null;
		}
		Map<String, String> sParaTemp = new HashMap<String, String>();
		Map<String, String> busParaTemp = new HashMap<String, String>();
		try {
			busParaTemp = BeanUtils.describe(bizParam);
		} catch (IllegalAccessException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		} catch (InvocationTargetException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		} catch (NoSuchMethodException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		}
		busParaTemp.remove("class");
		//业务参数拼串
		String bizReque = AliPaySubmit.buildBizRequest(busParaTemp);
		query.setBiz_content(bizReque);
		try {
			sParaTemp = BeanUtils.describe(query);
		} catch (IllegalAccessException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		} catch (InvocationTargetException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		} catch (NoSuchMethodException e) {
			logger.error("AlipayService/orderQueryResult --error", e);
		}
		sParaTemp.remove("class");
		//封装阿里支付需要的信息end
		Map<String, String> signMap = AliPaySubmit.buildRequest(sParaTemp, appPriKey);
//		String jsonParam = AlipayCore.createLinkString(signMap);
		//发起请求
		Map<String, Object> resultMap = AliOrderRequest.getOrderReturnPara(orderNo, totalPrice, AlipayConfig.AliPayForm_OrderUrl, signMap, appPriKey, aliPubKey);
		if (resultMap != null) {
			//业务处理成功(标识已经支付???):10000, 不存在的订单返回40004 
			Map<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("orderStatus", (String)resultMap.get("order_status"));
			stateMap.put("orderNo", (String)resultMap.get("out_trade_no"));
			stateMap.put("payType", Constant.payType.ALIPAY.getIndex());
			stateMap.put("totalPrice", (String)resultMap.get("total_amount"));
			if ("SUCCESS".equals(resultMap.get("order_status"))) {
				wsResult = new WsResult(WsResult.SUCCESS_CODE, (String)resultMap.get("sub_msg"), stateMap);
			}else {
				wsResult = new WsResult(WsResult.FAILURE_CODE, (String)resultMap.get("sub_msg"), stateMap);
			}
			return wsResult;
		}else {
			wsResult = new WsResult(WsResult.FAILURE_CODE, "查询失败,请重试!", null);
			return wsResult;
		}
	}
	
	/**
	 * 对notify参数验签并处理订单
	 * @param request
	 * @param response
	 * @param bizTypeName
	 */
/*	public void checkSignForNotifyDataAndDealOrder(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
		try {
			out = response.getWriter();
			 logger.info("支付宝支付异步通知开始.........");
             //获取支付结果
             String tradeStatus = request.getParameter("trade_status");
             //获取统一支付订单号
             String payBusinessId = request.getParameter("out_trade_no");
             if(StringUtils.isBlank(payBusinessId)){
            	 logger.info("FAIL:支付单号"+payBusinessId);
            	 out.println("FAIL");
             }
             logger.info("支付单号:"+payBusinessId);
             //交易未成功
             if(!(AlipayConfig.AliNotify_TradeState_SUCCESS.equals(tradeStatus) || 
            		 				AlipayConfig.AliNotify_TradeState_FINISHED.equals(tradeStatus))){
            	 out.println("FAIL");   
     	    	 logger.info("交易失败:"+tradeStatus);
     	     }else {
				//验证回传通知的签名(防止"假通知")--安全
     	    	Map<String,String> params = HttpClientTool.requestAli2Map(request);
     	    	//1.订单号和支付金额初始话(先用支付宝返回的)
     	    	String orderNo_DB = payBusinessId;
     	    	String totalPrice_DB = params.get("total_amount");
     	    	//2.订单是否存在
     	    	Map<String, String> orderMap = this.checkOrderExit(payBusinessId);
     	    	if (orderMap != null) {
					orderNo_DB = (String) orderMap.get("orderNo_DB");
					totalPrice_DB = (String) orderMap.get("totalPrice_DB");
				}else {
					logger.info("notify失败,不存在的订单");
				}
				//3.验证sign--是否支付宝回传
				String checkSign = AliPaySubmit.buildResponseDecode(params, orderNo_DB, totalPrice_DB);
				if ("SUCCESS".equals(checkSign)) {
					String result = "FAIL";
//					result = this.orderInfoService.notifyOrderPay(payBusinessId,request.getParameter("trade_no"),
//												Constant.payType.ALIPAY.getIndex(), params.get("total_amount"), null);
					logger.info(result+",订单号:"+payBusinessId);
					out.println(result);
				}else {
					logger.info("支付宝支付notify签名不匹配.........");
				}
     	     }
		} catch (IOException e) {
			if (out != null) {
				out.println("FAIL");
			}
			logger.error("支付宝支付异步通知发生异常");
        	logger.error(e);
		}
	}
*/	
	//检查订单是否存在
	public Map<String, String> checkOrderExit(String orderCode) {
		PageData pd = new PageData();
		pd.put("orderCode", orderCode);
		OrderVo order = orderManager.findOrder(pd);
		Map<String,String> orderMap = new HashMap<String, String>();
		//2.订单是否存在
		if(order != null){
    		orderMap.put("orderNo_DB", String.valueOf(order.getOrder_id()));
    		orderMap.put("totalPrice_DB", order.getPay_money());
		}else {
			return null;
		}
		return orderMap;
	}

	
	/**
	 * wap支付请求参数拼接
	 */
	public String aliPay(String payBusinessId,HttpServletRequest request) throws Exception{
		String domainUrl = WebConstant.MAIN_SERVER;
		PageData pd = new PageData();
		String orderCode = payBusinessId;
		pd.put("orderCode", orderCode);
		OrderVo order = orderManager.findOrder(pd);
		if(order != null){
			//封装阿里支付需要的信息 start
			AliPayFormForWap form = new AliPayFormForWap();
			form.setService(AlipayConfig.AliPayForm_Service);
			form.setPartner(AlipayConfig.partner);
			form.setSeller_id(AlipayConfig.get_be_payed);
			form.set_input_charset(AlipayConfig.input_charset);
			form.setPayment_type(AlipayConfig.paytype);	//支付类型(给支付宝的参数)
			form.setOut_trade_no(payBusinessId);
			form.setApp_pay("Y");
			form.setSubject("挥货-购买商品");
			//商品展示页
			form.setShow_url(domainUrl+AlipayConfig.AliPayForm_ShowUrl+order.getOrder_id()+"&orderCode="+orderCode);
			form.setNotify_url(domainUrl+AlipayConfig.AliPayForm_NotifyOrderUrl_WAP);
			form.setReturn_url(domainUrl+AlipayConfig.AliPayForm_ReturnUrl+orderCode);
			java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");  
			form.setTotal_fee(df.format(Double.parseDouble(order.getPay_money())));
			form.setBody("");
			form.setExtern_token("");
			Map<String,String> sParaTemp = BeanUtils.describe(form);
			sParaTemp.remove("class");
			//封装阿里支付需要的信息end
			String sHtmlText = AliPaySubmit.buildRequest(sParaTemp, AlipayConfig.AliPayForm_Method, AlipayConfig.AliPayForm_ButtonName);
			return sHtmlText;
		}
		return null;
	}
}
