package com.kingleadsw.betterlive.pay.alipay.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.pay.alipay.config.AlipayConfig;
import com.kingleadsw.betterlive.pay.alipay.sign.RSA;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AliPaySubmit {
	
	/**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara, String appPriKey) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.AliPayForm_SignType.equals("RSA") ){
        	mysign = RSA.sign(prestr, appPriKey, AlipayConfig.AliPayForm_Charset);
        }
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String appPriKey) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara, appPriKey);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.AliPayForm_SignType);
        return sPara;
    }

    /**
     * 请求参数(加签名)
     */
    public static Map<String, String> buildRequest(Map<String, String> sParaTemp, String appPriKey) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, appPriKey);
        return sPara;
    }
    

	/**
	 * 请求业务参数(以:分隔)
	 */
	public static String buildBizRequest(Map<String, String> busParam) {
        //生成业务请求参数(排序)字符串
        //把数组所有元素，按照“参数:参数值”的模式用“,”字符拼接成字符串
        return AlipayCore.createColonLinkString(busParam); 
	}
	
    /**
     * 异步返回结果(验签)
     */
	public static String buildResponseDecode(Map<String, String> sPara, String orderNo, String totalPrice, String alipayPublicKey) {
		//支付宝验签
		if (AlipayNotify.verify(sPara, alipayPublicKey)) {
			//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
			String outTradeNo = sPara.get("out_trade_no");
			if (!orderNo.equals(outTradeNo)) {
//				System.out.println("订单号不匹配!回单:"+outTradeNo);
				return "";
			}
//			//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
			String totalAmount = sPara.get("total_fee");
			//支付宝的回参名total_amount
			if(AlipayConfig.ali_public_key_app.equals(alipayPublicKey)){
				totalAmount = sPara.get("total_amount");
			}
			//测试时金额被改成0.01,正式上线请打开注释
//			if (!totalPrice.equals(totalAmount)) {
//				System.out.println("金额不匹配!需付:"+totalPrice+", 实付" + totalAmount+"回单:"+outTradeNo);
//				return null;
//			}
			//3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
			String sellerId = sPara.get("seller_id");
			if (!AlipayConfig.get_be_payed.equals(sellerId)) {
				System.out.println("收款方不匹配!回传:"+sellerId);
				return null;
			}
			//
//			//4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
//			String respAppId = sPara.get("app_id"); 
//			if (!AlipayConfig.AliPay_APPID_HUIHUO.equals(respAppId) && !AlipayConfig.AliPay_APPID_HUIHUO.equals(respAppId)) {
//				System.out.println("appId不匹配!回传:"+ respAppId);
//				return null;
//			}
			return "SUCCESS";
		}
		return "";
	}
	
	//wap版支付--参数拼接
	/**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("RSA") ){
        	//wap版和app公私钥可共用同一对
        	mysign = RSA.sign(prestr, AlipayConfig.Private_Key_HUIHUO, AlipayConfig.input_charset);
        }
        return mysign;
    }
}

