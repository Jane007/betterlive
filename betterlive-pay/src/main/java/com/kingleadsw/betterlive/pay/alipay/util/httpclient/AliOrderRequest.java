package com.kingleadsw.betterlive.pay.alipay.util.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.kingleadsw.betterlive.common.Constant;
import com.kingleadsw.betterlive.common.util.JsonUtil;
import com.kingleadsw.betterlive.pay.alipay.config.AlipayConfig;
import com.kingleadsw.betterlive.pay.alipay.sign.RSA;

/**
 * 支付宝统一支付请求
 * @copright 
 * @author 
 * @date 2
 */
public class AliOrderRequest {

	private static Logger logger = Logger.getLogger(AliOrderRequest.class);
	
	/**
	 * 获取统一支付返回参数
	 */
	 public static Map<String, Object> getOrderReturnPara(String orderNo, String totalPrice, String url, Map<String, String> paraMap, String appPriKey, String aliPubKey){
		 Map<String, Object> resultMap = new HashMap<String, Object>();
		 AlipayClient alipayClient = new DefaultAlipayClient(url,paraMap.get("app_id"), appPriKey,"json","UTF-8", aliPubKey);
		 AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//		 System.out.println(paraMap.get("biz_content"));
		 request.setBizContent(paraMap.get("biz_content"));
		//支付宝API抛出异常,需捕获处理
		 AlipayTradeQueryResponse response = null; 
		try {
			 //支付宝API抛出异常,需捕获处理--这步就已经验签了
			 response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			logger.error("AliOrderRequest/getOrderReturnPara --error", e);
			return null;
		}finally {
			//解析返回结果
			if (response != null) {
				resultMap.put("order_status", "fail");
				resultMap.put("code", response.getCode());
				String body = response.getBody();
				Map<String, String> queryMap = JsonUtil.jsonToMap(body);
				//验签
				String aliResp = queryMap.get("alipay_trade_query_response");
				String respSign = JsonUtil.jsonToMap(response.getBody()).get("sign");
				if (AlipayConfig.AliPayQuery_ReturnCode_Success.equals(resultMap.get("code"))) {
					//验签成功后的4点判断--
					if (RSA.verify(aliResp, respSign, AlipayConfig.ali_public_key, "UTF-8")) {
						//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
						String outTradeNo = response.getOutTradeNo();
						//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
						String totalAmount = response.getTotalAmount();
						if (!orderNo.equals(outTradeNo) || !totalPrice.equals(totalAmount)) {
							resultMap.put("order_status", "FAIL");
							resultMap.put("sub_msg", "订单或金额不匹配");
							return resultMap;
						}else {
							String tradeStatus = response.getTradeStatus();
							if (!StringUtils.isBlank(tradeStatus)) {
								if (AlipayConfig.AliNotify_TradeState_SUCCESS.equals(tradeStatus) || AlipayConfig.AliNotify_TradeState_FINISHED.equals(tradeStatus)) {
									//验证通过付款成功, 修改订单状态
									resultMap.put("order_status", "SUCCESS");
								}else {
									resultMap.put("order_status", "FAIL");
								}
							}
						}
					} else {
						resultMap.put("order_status", "FAIL");
						resultMap.put("sub_msg", "验签失败");
						return resultMap;
					}
				}else {
					resultMap.put("order_status", response.getSubCode());
				}
				resultMap.put("out_trade_no", response.getOutTradeNo());
				resultMap.put("sub_msg", response.getSubMsg());
			}
			resultMap.put("payType", Constant.payType.ALIPAY.getIndex());
		}
	     return resultMap;
	  }
	 
	 /**
	  * 退款返回response
	  * @param orderNo
	  * @param totalPrice
	  * @param url
	  * @param paraMap
	  * @param appPriKey
	  * @param aliPubKey
	  * @return
	  */
	 public static Map<String, Object> getRefundReturnPara(String orderNo, String refundAmount, String url, Map<String, String> paraMap, String appPriKey, String aliPubKey){
		 Map<String, Object> resultMap = null;
		 AlipayClient alipayClient = new DefaultAlipayClient(url,paraMap.get("app_id"), appPriKey,"json","UTF-8", aliPubKey);
		 AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		 request.setBizContent(paraMap.get("biz_content"));
		//支付宝API抛出异常,需捕获处理
		 AlipayTradeRefundResponse response = null;
		try {
			 //支付宝API抛出异常,需捕获处理--这步就已经验签了
			 response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			logger.error("AliOrderRequest/getRefundReturnPara --error", e);
			return null;
		}finally {
			resultMap = new HashMap<String, Object>();
			//解析返回结果
			if (response != null) {
				resultMap.put("orderStatus", "fail");
				resultMap.put("code", response.getCode());
				String body = response.getBody();
				Map<String, String> queryMap = JsonUtil.jsonToMap(body);
				//验签
				String aliResp = queryMap.get("alipay_trade_refund_response");
				String respSign = JsonUtil.jsonToMap(response.getBody()).get("sign");
				//验签成功后的4点判断--
				if (RSA.verify(aliResp, respSign, AlipayConfig.ali_public_key, "UTF-8")) {
					//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
					String outTradeNo = response.getOutTradeNo();
					//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
					String refundFee = response.getRefundFee();
					if (!orderNo.equals(outTradeNo) || !refundAmount.equals(refundFee)) {
						resultMap.put("sub_msg", "Error:订单号或退款金额不匹配");
						return resultMap;
					}
				} else {
					resultMap.put("sub_msg", "Error:验签失败");
					return resultMap;
				}
				resultMap.put("out_trade_no", response.getOutTradeNo());
				resultMap.put("sub_msg", response.getSubMsg() == null ? "success" : response.getSubMsg());
			}
			resultMap.put("payType", Constant.payType.ALIPAY.getIndex());
		}
	    return resultMap;
	  }
}

