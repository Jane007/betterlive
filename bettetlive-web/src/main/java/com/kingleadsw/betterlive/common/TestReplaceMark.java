package com.kingleadsw.betterlive.common;


public class TestReplaceMark {
	
	public static void main(String[] args) {
		String str = "小的"; //原字符串
		String mark = "*"; //替换后的标记
		if (str == null || "".equals(str)) {
			return;
		}
		
		String html = "";
		int len = str.length();
		if (len == 1) {  //如果字符长度为1，输出原来的字符串
			html = str;
		} else if (len == 2) { //字符长度为2，替换后面一个字符
			html = replaceStrTomark(str, 1, 0, mark); 
		} else { //字符长度大于2，保留首尾的字符，其余的替换
			html = replaceStrTomark(str, 1, 1, mark);
		}
		System.out.println(html);
	}

	/** 
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替 
     *  
     * @param content 传入的字符串 
     * @param startNum  保留前面字符的位数 
     * @param endNum 保留后面字符的位数 
     * @param mark 替换的标记
     * @return 带星号的字符串 
     */  
  
    private static String replaceStrTomark(String content, int startNum, int endNum, String mark) {  
    	
        if (startNum >= content.length() || startNum < 0) {  
            return content;  
        }  
        if (endNum >= content.length() || endNum < 0) {  
            return content;  
        }  
        if (startNum + endNum >= content.length()) {  
            return content;  
        }  
        String starStr = "";  
        int len = content.length() - startNum - endNum;
        for (int i = 0; i < len; i++) {  
            starStr = starStr + mark;  
        }  
        return content.substring(0, startNum) + starStr  
                + content.substring(content.length() - endNum, content.length());  
  
    }  

}
