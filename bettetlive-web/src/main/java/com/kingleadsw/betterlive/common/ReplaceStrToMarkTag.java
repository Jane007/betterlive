package com.kingleadsw.betterlive.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * 实现名字等字符串以特定符号替换的tag
 * @author ltp
 */
public class ReplaceStrToMarkTag extends TagSupport {

	private static final long serialVersionUID = -1235756684272492764L;

	private static final Logger logger = Logger.getLogger(ReplaceStrToMarkTag.class);

	String str; //原字符串
	String mark = ""; //替换后的标记
	static int markLen = 2;  //替换后标记的长度，即替换后共有多少个标记

	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() {
		logger.info("字符串以特定符号替换开始");
		logger.info("str: " + str);
		logger.info("mark: " + mark);
		if (str == null || "".equals(str)) {
			return EVAL_PAGE;
		}
		
		try {
			String html = "";
			int len = str.length();
			if (len == 1) {  //如果字符长度为1，输出原来的字符串
				html = str;
			} else if (len == 2) { //字符长度为2，替换后面一个字符
				html = replaceStrToMark(str, 1, 0, mark); 
			} else { //字符长度大于2，保留首尾的字符，其余的替换
				html = replaceStrToMark(str, 1, 1, mark);
			}
			
			this.pageContext.getOut().write(html);
		} catch (Exception e) {
			logger.error("ReplaceStrToMarkTag/doEndTag --error", e);
		}
		logger.info("字符串以特定符号替换结束");
		return EVAL_PAGE;
	}

	
	/** 
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替 
     *  
     * @param content 
     *            传入的字符串 
     * @param startNum 
     *            保留前面字符的位数 
     * @param endNum 
     *            保留后面字符的位数 
     * @param mark 替换的标记
     * @return 带星号的字符串 
     */  
  
    private static String replaceStrToMark(String content, int startNum, int endNum, String mark) {  
  
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
        if (len > markLen) {
        	len = markLen;
        }
        for (int i = 0; i < len; i++) {  
            starStr = starStr + mark;  
        }  
        return content.substring(0, startNum) + starStr  
                + content.substring(content.length() - endNum, content.length());  
  
    }  

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
