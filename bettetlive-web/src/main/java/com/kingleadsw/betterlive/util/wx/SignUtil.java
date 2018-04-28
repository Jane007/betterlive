package com.kingleadsw.betterlive.util.wx;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.core.util.MapKeyComparator;

public class SignUtil {
	private static String token = "kaaBuGsAEPa1MehEzBHHa8NAm8SA9maA";
	
	protected static Logger logger = Logger.getLogger(SignUtil.class);
	
	
	public static String getSign(Map<String, String> map, String sign) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> resultMap = sortMapByKey(map);
        sb.setLength(0);
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            sb.append(entry.getValue());
        }
        String result = sb.toString();
        result += sign;
        logger.info("Sign Before:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.info("Sign After:" + result);
        return result;
    }
	
	/**根据key排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
	
	/**
	 * 19 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonc
	 * @return 25
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		// Arrays.sort(arr);
		sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");

			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("SignUtil/checkSignature --error", e);
		}
		content = null;

		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	

	/**
	 * 54 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return 58
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 68 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return 72
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	public static void sort(String a[]) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j].compareTo(a[i]) < 0) {
					String temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	/**
	 * jsapi签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> jsApiTicktSign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = new StringBuilder("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(nonce_str)
				.append("&timestamp=").append(timestamp).append("&url=").append(url).toString();
		logger.info("jsapi_ticket待加密串：" + string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			logger.info("jsapi_ticket加密后：" + signature);
		} catch (NoSuchAlgorithmException e) {
			logger.error("SignUtil/jsApiTicktSign --error", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("SignUtil/jsApiTicktSign --error", e);
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	/**
	 * jsapi签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> jsApiTicktSign(String jsapi_ticket) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = new StringBuilder("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(nonce_str)
				.append("&timestamp=").append(timestamp).toString();
		
		logger.info("jsapi_ticket待加密串：" + string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			logger.info("jsapi_ticket加密后：" + signature);
		} catch (NoSuchAlgorithmException e) {
			logger.error("SignUtil/jsApiTicktSign --error", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("SignUtil/jsApiTicktSign --error", e);
		}
		 
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}
	
	
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}