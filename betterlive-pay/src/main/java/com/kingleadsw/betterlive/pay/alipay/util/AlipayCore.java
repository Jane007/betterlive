package com.kingleadsw.betterlive.pay.alipay.util;

import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.alipay.config.AlipayConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {

	private static Logger logger = Logger.getLogger(AlipayCore.class);
	
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组,notify需要除去sign和sign_type
     */
    public static Map<String, String> paraFilterForNotify(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {	//notify验签需要过滤sign和sign_type
                continue;
            } 
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")){
//                || key.equalsIgnoreCase("sign_type")) {	//支付请求加签不需要过滤sign_type
                continue;
            } 
            result.put(key, value);
        }

        return result;
    }
    
    /** 
     * 把数组所有元素，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要参与字符拼接的参数组
     * @param sorts   是否需要排序 true 或者 false
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());

        Collections.sort(keys);

        
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord,String filename) {
        FileWriter writer = null;
        try {
            //获取系统配置日志路劲
            String sysConfig = System.getProperty("alipay.log");
            if(StringUtil.isNull(sysConfig)){
                String osName = System.getProperty("os.name");
                if(osName.toLowerCase().indexOf("window") < 0){
                    AlipayConfig.log_path = "home" + File.separator;
                }
            }else{
                AlipayConfig.log_path = sysConfig + File.separator;
            }
            writer = new FileWriter(AlipayConfig.log_path + "alipay_log_" + System.currentTimeMillis()+filename+".txt");
            writer.write(sWord);
        } catch (Exception e) {
			logger.error("AlipayCore/logResult --error", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                	logger.error("AlipayCore/logResult --error", e);
                }
            }
        }
    }
    
    /** 
     * 把数组所有元素排序，并按照“"参数":"参数值"”的模式用“,”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
	public static String createColonLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        //业务参数无需排序
        Collections.sort(keys);
        String prestr = "{";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (value == null || value.equals("")) {
            	//空属性值拼"". 最后一个不拼,
            	if (i == keys.size() - 1) {
            		prestr = prestr + "\"" + key + "\":\"\"";
				}else {
					prestr = prestr + "\"" + key + "\":\"\",";
				}
                continue;
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个,字符
                prestr = prestr + "\"" + key + "\":\"" + value +"\"";
            } else {
                prestr = prestr + "\"" + key + "\":\"" + value + "\",";
            }
        }

        return prestr+"}";
	}
}
