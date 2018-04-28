package com.kingleadsw.betterlive.util.alipay.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.alipay.sign.Base64;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderVo;

@Service("oneCardPayService")
public class OneCardPayService{
	static String publicKey;
	/**
	 * 一卡通支付参数打包方法
	 */
	public String oneCardPack(OrderVo orderVo,CustomerVo customer,String type) {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", getNowTime());
		reqData.put("branchNo", WebConstant.YKT_BRANCH_NO);
		reqData.put("merchantNo", WebConstant.YKT_MERCHANT_NO);
		reqData.put("date", getDate(orderVo.getOrder_time()));
		reqData.put("orderNo", orderVo.getOrder_code());
		reqData.put("amount", orderVo.getPay_money());
		reqData.put("expireTimeSpan", "30");
		reqData.put("payNoticeUrl", WebConstant.YKT_PAY_NOTICE_URL);
		reqData.put("payNoticePara","customer_id|"+customer.getCustomer_id());
		if ("wx".equals(type)) {
			reqData.put("returnUrl", WebConstant.YKT_RETURN_URL+"?orderCode="+orderVo.getOrder_code());
		} else if ("app".equals(type)) {
			reqData.put("returnUrl", "Http://catchMe");
		}
		
		if (StringUtil.isNotNull(customer.getYktAgrNo())) {
			reqData.put("agrNo",customer.getYktAgrNo());//已签约客户协议号
		}else{
			reqData.put("agrNo", StringUtil.get32UUID());//新签约协议号
			reqData.put("merchantSerialNo", getNowTime());
		}
		reqData.put("mobile", orderVo.getMobile());
		reqData.put("riskLevel", "3");
		reqData.put("signNoticeUrl", WebConstant.YKT_SIGN_NOTICE_URL);
		reqData.put("signNoticePara", "customer_id|"+customer.getCustomer_id());
		return buildParam(reqData);
	}
	
	/**
	 * 一卡通单笔订单查询接口参数
	 */
	public String querySingleOrder(OrderVo orderVo) {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", getNowTime());
		reqData.put("branchNo", WebConstant.YKT_BRANCH_NO);
		reqData.put("merchantNo", WebConstant.YKT_MERCHANT_NO);
		reqData.put("type", "B");
		reqData.put("date", getDate(orderVo.getOrder_time()));
		reqData.put("orderNo", orderVo.getOrder_code());
		return buildParam(reqData);
	}

	/**
	 * 对参数签名：
	 * 对reqData所有请求参数按从a到z的字典顺序排列，如果首字母相同，按第二个字母排列，以此类推。排序完成后按将所有键值对以“&”符号拼接。
	 * 拼接完成后再加上商户密钥。示例：param1=value1&param2=value2&...&paramN=valueN&secretKey
	 * 
	 * @param reqDataMap
	 *            请求参数
	 * @param secretKey
	 *            商户密钥
	 */
	public static String sign(Map<String, String> reqDataMap, String secretKey) {
		StringBuffer buffer = new StringBuffer();
		List<String> keyList = sortParams(reqDataMap);
		for (String key : keyList) {
			buffer.append(key).append("=").append(reqDataMap.get(key)).append("&");
		}
		buffer.append(secretKey);// 商户密钥

		try {
			// 创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			// 传入要加密的字符串,按指定的字符集将字符串转换为字节流
			messageDigest.update(buffer.toString().getBytes("UTF-8"));
			byte byteBuffer[] = messageDigest.digest();

			// 將 byte转换为16进制string
			StringBuffer strHexString = new StringBuffer();
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			return strHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 对参数按字典顺序排序，不区分大小写
	 */
	public static List<String> sortParams(Map<String, String> reqDataMap) {
		List<String> list = new ArrayList<String>(reqDataMap.keySet());
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String[] temp = { o1.toLowerCase(), o2.toLowerCase() };
				Arrays.sort(temp);
				if (o1.equalsIgnoreCase(temp[0])) {
					return -1;
				} else if (temp[0].equalsIgnoreCase(temp[1])) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return list;
	}
	
	
	/**
	 * 获取当前时间戳
	 */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(System.currentTimeMillis());
	}
	
	public static String getDate(String orderTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = format1.parse(orderTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format.format(date);
	}
	
	public static String buildParam(Map<String, String> reqDataMap) {
		JSONObject jsonParam = new JSONObject();
		try {
			jsonParam.put("version", "1.0");
			jsonParam.put("charset", "UTF-8");// 支持GBK和UTF-8两种编码
			jsonParam.put("sign", sign(reqDataMap,WebConstant.YKT_SECRET_KEY));
			jsonParam.put("signType", "SHA-256");
			jsonParam.put("reqData", reqDataMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonParam.toString();
	}
	
	/**
	 * 查询公钥参数
	 * @return
	 */
	public static String doBusiness() {
		
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", getNowTime());
		reqData.put("txCode", "FBPK");
		reqData.put("branchNo",  WebConstant.YKT_BRANCH_NO);
		reqData.put("merchantNo", WebConstant.YKT_MERCHANT_NO);
		return buildParam(reqData);
	}
	
	/**
	 * 公钥验签
	 * @param strToSign 待签名参数
	 * @param strSign	签名数据
	 * @param publicKey 公钥
	 * @return
	 */
	
	public static boolean isValidSignature(String strToSign, String strSign)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decode(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));    
          
            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");  
          
            signature.initVerify(pubKey);  
            signature.update(strToSign.getBytes("UTF-8") );  
          
            boolean bverify = signature.verify(Base64.decode(strSign));  
            return bverify;                
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
}
	
	
	
	/**
	 * 发送POST请求
	 */
	public static String uploadParam(String jsonParam, String url, String charset) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection urlCon = (HttpURLConnection) httpUrl.openConnection();
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setReadTimeout(5 * 1000);
			out = new OutputStreamWriter(urlCon.getOutputStream(), charset);// 指定编码格式
			out.write("jsonRequestData=" + jsonParam);
			out.flush();

			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charset));
			String str = null;
			while ((str = in.readLine()) != null) {
				result.append(str);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
	
	public static void showTimer() {
			String result = uploadParam(doBusiness(),WebConstant.YKT_FIND_PUB_KEY, "UTF-8");
			if (StringUtil.isNull(result)) {
				return;
			}
			JSONObject json = JSONObject.fromObject(result);
	        JSONObject rspData = json.getJSONObject("rspData");
	        if(rspData.containsKey("fbPubKey")){
	        	publicKey = rspData.getString("fbPubKey");
	        }
	       TimerTask task = new TimerTask() {
	          @Override
	           public void run() {
	        	  String result = uploadParam(doBusiness(),WebConstant.YKT_FIND_PUB_KEY, "UTF-8");
		  			if (StringUtil.isNull(result)) {
		  				return;
		  			}
	              JSONObject json = JSONObject.fromObject(uploadParam(doBusiness(),WebConstant.YKT_FIND_PUB_KEY, "UTF-8"));
	              JSONObject rspData = json.getJSONObject("rspData");
	              if(rspData.containsKey("fbPubKey")){
	            	  publicKey = rspData.getString("fbPubKey");
	              }
	           }
	       };

	       //设置执行时间
	       Calendar calendar =Calendar.getInstance();
	       int year = calendar.get(Calendar.YEAR);
	       int month = calendar.get(Calendar.MONTH);
	       int day =calendar.get(Calendar.DAY_OF_MONTH);//每天
	       //定制每天的02:15:00执行，
	       calendar.set(year, month, day, 02,15, 00);
	       Date date = calendar.getTime();
	       Timer timer = new Timer();
//	       System.out.println(date);
	       
//	       int period = 2 * 1000;
	       //每天的date时刻执行task，每隔2秒重复执行
	       timer.schedule(task, date);
	       //每天的date时刻执行task, 仅执行一次
	       //timer.schedule(task, date);
	    }
}
