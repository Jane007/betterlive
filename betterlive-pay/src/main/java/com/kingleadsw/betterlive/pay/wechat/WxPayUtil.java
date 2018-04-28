package com.kingleadsw.betterlive.pay.wechat;

import com.kingleadsw.betterlive.common.PayResData;
import com.kingleadsw.betterlive.common.util.HttpClientTool;
import com.kingleadsw.betterlive.common.util.XmlUtil;
import com.kingleadsw.betterlive.core.util.IpAndMac;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.wechat.config.PayConfigUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class WxPayUtil {    
	
	private final static Logger logger = Logger.getLogger(WxPayUtil.class);
	
	
	
	/**
	 * 微信网页(公众号)支付工具方法
	 * amount 支付的总费用
	 * sn 微信交易号 (唯一性) 对于我们这里的订单编号
	 * goods_desc 商品描述
	 * pay_notify 微信支付异步回调地址
	 * openId 微信用户的openid
	 * paySecret 支付密钥
	 * Map<String,String>
	 */
	public static  Map<String, String> wechatWEBPay(HttpServletRequest request,float amount,
            String sn, String goods_desc,String pay_notify,String openId,String paySecret){
		try {
			return configPayParam(request,amount,sn,openId, goods_desc,pay_notify,PayConfigUtil.WEIXIN_PREPAY_TRADE_TYPE_JSAPI,paySecret);
		} catch (Exception e) {
			logger.error("WxPayUtil/wechatWEBPay --error", e);
			return null;
		}
	}
	
	/**
	 * 微信app支付工具方法
	 * amount 支付的总费用
	 * sn 微信交易号 (唯一性) 对于我们这里的订单编号
	 * goods_desc 商品描述
	 * pay_notify 微信支付异步回调地址
	 * paySecret 支付密钥
	 * Map<String,String>
	 */
	public static  Map<String, String> wechatAppPay(HttpServletRequest request,float amount,
            String sn, String goods_desc,String pay_notify,String paySecret){
		try {
			return configPayParam(request,amount,sn,null, goods_desc,pay_notify,PayConfigUtil.WEIXIN_PREPAY_TRADE_TYPE_APP,paySecret);
		} catch (Exception e) {
			logger.error("WxPayUtil/wechatAppPay --error", e);
			return null;
		}
	}
	
	
	
	
    public static Map<String, String> configPayParam(HttpServletRequest request,float amount,
            String sn,String openId, String goods_desc,String pay_notify,String payType,String paySecret) throws Exception {
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        Map<String, String> params = new HashMap<String, String>();
        String nonce_str = StringUtil.get32UUID();
        
        params.put("appid", PayConfigUtil.APP_NOTWEB_ID);
        params.put("body", goods_desc);
        params.put("mch_id", PayConfigUtil.MCH_NOTWEB_ID);
        params.put("nonce_str", nonce_str);
        params.put("notify_url", pay_notify);
        params.put("out_trade_no", sn);
        params.put("spbill_create_ip", IpAndMac.getIpAddr(request));
        //params.put("spbill_create_ip", "139.196.30.90");
        params.put("total_fee", String.valueOf((int) (amount* 100)));
        params.put("trade_type",payType);
        
        if(PayConfigUtil.WEIXIN_PREPAY_TRADE_TYPE_JSAPI.equals(payType)){
        	params.put("openid", openId);
        }
       // String paySecret="380e0febf3f949c3bbdb0d729c7be529";
        String sign = DictionarySortUtil.createSign(params, paySecret);

        params.put("sign", sign);

        logger.info("--------------------微信支付页面请求开始---------------------------");

        String xml = configXml(params);

        logger.info("签名请求报文：" + xml);

        String result = HttpClientTool.post(url, xml, "utf-8");
        PayResData payReseData = (PayResData) XmlUtil.getObjectFromXML(result, PayResData.class);

        Map<String, String> payParam = new HashMap<String, String>();

        
        if(PayConfigUtil.WEIXIN_PREPAY_TRADE_TYPE_JSAPI.equals(payType)){
            payParam.put("appId", PayConfigUtil.APP_NOTWEB_ID);
            payParam.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            payParam.put("nonceStr", nonce_str);
        	payParam.put("package", "prepay_id=" + payReseData.getPrepay_id());
        	payParam.put("signType", "MD5");
        	
        }else{
        	payParam.put("appid", PayConfigUtil.APP_NOTWEB_ID);
        	payParam.put("partnerid", PayConfigUtil.MCH_NOTWEB_ID);
        	payParam.put("noncestr", nonce_str);
        	payParam.put("prepayid",  payReseData.getPrepay_id());
        	payParam.put("package", PayConfigUtil.WEIXIN_PREPAY_PACKAGE);
        	payParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        }
        
       
        sign=DictionarySortUtil.createSign(payParam, paySecret);
        payParam.put("paySign", sign);

        return payParam;

    }
    
    
    public static String configXml(Map<String, String> params) throws IOException {
        StringBuffer buffer=new StringBuffer();
        
        buffer.append("<xml>");
        for(Entry<String,String> entry:params.entrySet()){
            buffer.append("<").append(entry.getKey()).append(">");
            buffer.append(entry.getValue());
            buffer.append("</").append(entry.getKey()).append(">");
        }
        buffer.append("</xml>");
        
        return buffer.toString();
     } }
