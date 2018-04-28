package com.kingleadsw.betterlive.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * 截断字符串并以指定符号替代的tag
 * @author ltp
 * 
 */
public class CutStringTag extends TagSupport {
	
	private static final long serialVersionUID = -2330718903150475079L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CutStringTag.class);

	String content;
	String mark = "";
	Integer size;

	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		String html = cutString(content, size, mark);
		try {
			this.pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			logger.error("tag CutStringTag error", e);
		}
		return EVAL_PAGE;
	}

	/**
	 * 将str中超过len长度的字符替换为mark标记
	 * @param str 符串
	 * @param len 截取长度
	 * @param mark 截取替换的标记
	 * @return
	 */
	public String cutString(String str, int len, String mark) {
		len = len * 2;
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 255) {
				counter++;
			} else {
				counter = counter + 2;
			}
			if (counter > len) {
				String result = sb.toString().trim();
				result += mark;
				return result;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}