package com.kingleadsw.betterlive.core.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    /**  
     * 使用java正则表达式去掉多余的.与0  
     * @param s  
     * @return   
     */    
	public static String subZeroAndDot(String s) {
		if(StringUtil.isNull(s)){
			return "0";
		}
		
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
	
   /**  
     * 使用java正则表达式去掉多余的.与0  
     * @param BigDecimal value 
     * @return   
     */    
	public static String subZeroAndDot(BigDecimal value) {
		if(value == null){
			return "0";
		}
		String s = value.toString();
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
    
}
