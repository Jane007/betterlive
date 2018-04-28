package com.kingleadsw.betterlive.util.wx;



import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.wx.bean.PayRefundData;


/**
 * 微信退款
 * @author Administrator
 *
 */
public class WxPayReturnUtil
{

    private static final Logger logger = Logger
            .getLogger(WxPayReturnUtil.class);

    public synchronized static PayRefundData payRefund(
            Map<String, String> param)
    {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        param.put("appid", WebConstant.WX_APPID);
        param.put("mch_id", WebConstant.WX_MCH_ID);
        param.put("nonce_str", StringUtil.get32UUID());
        param.put("op_user_id", WebConstant.WX_MCH_ID);
        String sign = WxSign.getSign(param);
        param.put("sign", sign);
        String xml = configXml(param);
        String resultString = refundSend(url, xml, "utf-8");
        logger.info("退款返回信息：" + resultString);
        
        return getResult(resultString);
    }
    
    private  static PayRefundData getResult(String result){
    	try {
			  PayRefundData resultData=(PayRefundData)XmlParser.parser(result, PayRefundData.class);
			  
			  return resultData;
		} catch (DocumentException e) {
			logger.error(e.toString(), e);
		}
    	return null;
    }
    
    
    private static String configXml(Map<String, String> param)
    {
        StringBuffer bf = new StringBuffer();
        bf.append("<xml>");
        for (Entry<String, String> entry : param.entrySet())
        {
            bf.append("<" + entry.getKey() + ">");
            bf.append(entry.getValue());
            bf.append("</" + entry.getKey() + ">");
        }
        bf.append("</xml>");
        return bf.toString();
    }
/*    public static void main(String[] args)
    {
        Map<String, String> param = new HashMap<String, String>();

        // 退款单号
        param.put("out_refund_no",  PtwopUtil.createOrderCode("R"));
        // 订单号
        param.put("out_trade_no", "O201511141841448540");
        
        //退款金额以分为单位
        param.put("refund_fee", "1");
        
        //订单总额金额以分为单位
        param.put("total_fee", "1");
        
        param.put("transaction_id", "1006560396201511141602848951");
        
    }*/

    private static String refundSend(String url, String data, String charset)
    {

        KeyStore keyStore;
        StringEntity stEntity;
        try
        {
            HttpPost httppost = new HttpPost(url);

            keyStore = KeyStore.getInstance("PKCS12");

            FileInputStream instream = new FileInputStream(new File(
                    WebConstant.WX_CERTFILEPATH+"apiclient_cert.p12"));
            
            //商户id作为商户作为秘钥
            keyStore.load(instream, WebConstant.WX_MCH_ID.toCharArray());
            SSLContext sslcontext;
            sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, WebConstant.WX_MCH_ID.toCharArray())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            // Httpclient SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf).build();

            httppost.addHeader("Content-Type", "text/xml");
            stEntity = new StringEntity(data, charset);
            httppost.setEntity(stEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String resultString = "";
                resultString = EntityUtils.toString(entity, charset);
                return resultString;
            }

        } catch (Exception e)
        {
            logger.error("退款请求错误：" + e.toString(), e);
        }
        return null;
    }

}
