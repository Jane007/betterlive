package com.kingleadsw.betterlive.util.wx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.pay.wechat.config.PayConfigUtil;


public class WxSign {
	private static final Logger logger = Logger.getLogger(WxSign.class);

	public static String getSign(Map<String, String> map) {
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != "" && !"sign".equals(entry.getKey())) {
				sb.setLength(0);
				list.add(sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&").toString());
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		sb.setLength(0);
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
 
		sb.append("key=").append(WebConstant.MCH_API_KEY); 
		
		String result = sb.toString();
		logger.info("WxSign Before:" + result);
		result = MD5.MD5Encode(result).toUpperCase();
		logger.info("WxSign After:" + result);
		return result;
	}
	
	public static String getSign2(Map<String, String> map) {
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != "" && !"sign".equals(entry.getKey())) {
				sb.setLength(0);
				list.add(sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&").toString());
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		sb.setLength(0);
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
 
		sb.append("key=").append(PayConfigUtil.API_NOTWEB_KEY); 
		
		String result = sb.toString();
		logger.info("WxSign Before:" + result);
		result = MD5.MD5Encode(result).toUpperCase();
		logger.info("WxSign After:" + result);
		return result;
	}
}
