package com.kingleadsw.betterlive.init;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.controller.app.pay.AppPaymentController;

/**
 * 字符串处理工具类
 * @date Sep 16, 2015
 * @time 12:06:10 PM
 * @author Wusongti
 */
public class StringUtil implements Serializable {
	private static Logger logger = Logger.getLogger(AppPaymentController.class);
	
	/**
	 * 序列化id.
	 */
	private static final long serialVersionUID = -4070301361637214448L;

	/**
	 * 日期格式字符串.
	 */
	public static final String DEFAULT_TIEMSTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 大于号.
	 */
	public static final int HIGHEST_SPECIAL = '>';

	/**
	 * 20.
	 */
	public static final int LENGHT_STRING = 20;

	/**
	 * 长度增加值.
	 */
	public static final int LENGHT_ADD = 5;

	/**
	 * 逗号.
	 */
	private static final String COMMA_SYMBOL = ",";

	/**
     * 
     */
	public static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];

	static {
		specialCharactersRepresentation['&'] = "&amp;".toCharArray();
		specialCharactersRepresentation['<'] = "&lt;".toCharArray();
		specialCharactersRepresentation['>'] = "&gt;".toCharArray();
		specialCharactersRepresentation['"'] = "&#034;".toCharArray();
		specialCharactersRepresentation['\''] = "&#039;".toCharArray();
	}

	public StringUtil() {
		super();
	}
	
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	public static String nullToString(Object obj) {
		if (obj == null) {
			return "";
		}
		String value = obj.toString().replaceAll(" ", "");
		if ("null".equals(value) || "".equals(value)) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * 
	 * isNotEmpty:(判断不为空).
	 * 
	 * @author Administrator
	 * @param str
	 *            字符串
	 * @return 不为空-true; 空-false
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * isEmpty:(判断为空).
	 * 
	 * @author Administrator
	 * @param str
	 *            字符串
	 * @return 空-true; 不为空-false
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * toArray:(将字符串转换为字符数组).
	 * 
	 * @author Administrator
	 * @param value
	 *            待转字符串
	 * @param delimeters
	 *            分隔符
	 * @return 字符数组
	 */
	public static String[] toArray(String value, String delimeters) {
		return (String[]) toList(value, delimeters).toArray();
	}

	/**
	 * 
	 * toList:(将字符串转换为字符列表).
	 * 
	 * @author Administrator
	 * @param value
	 *            待转字符串
	 * @param delimeters
	 *            分隔符
	 * @return 字符列表
	 */
	public static List<Object> toList(String value, String delimeters) {
		List<Object> list = new ArrayList<Object>();
		if (isEmpty(value)) {
			return list;
		}
		StringTokenizer st = new StringTokenizer(value, delimeters, false);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
	
	
    /**
     * 
     * toStringList:(将字符串转换为字符列表).
     * @author Administrator 
     * @param value 待转字符串
     * @param delimeters 分隔符
     * @return 字符列
     */
    public static List<String> toStringList(String value, String delimeters) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(value)) {
            return list;
        }
        StringTokenizer st = new StringTokenizer(value, delimeters, false);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

	/**
	 * 
	 * toListByStr:(将字符串通过分隔符转换为字符串列表).
	 * 
	 * @author Administrator
	 * @param value
	 *            待转字符串
	 * @param separator
	 *            分隔符
	 * @return 字符串列表
	 */
	public static List<Object> toListByStr(String value, String separator) {
		List<Object> list = new ArrayList<Object>();
		String token;
		int index0 = 0;
		int index1 = value.indexOf(separator, 0);
		if (index1 == -1) {
			list.add(value);
		} else {
			while (true) {
				token = value.substring(index0, index1);
				if (StringUtil.isNotEmpty(token)) {
					list.add(token);
				}
				index0 = index1 + separator.length();
				index1 = value.indexOf(separator, index0);
				if (index1 == -1) {
					token = value.substring(index0);
					if (StringUtil.isNotEmpty(token)) {
						list.add(token);
					}
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toStringByList(List<String> list,String separator){
		StringBuffer sb = new StringBuffer();
		if(list != null){
			for(int i = 0 ;i<list.size();i++){
				sb.append(list.get(i));
				if(i != list.size() - 1){
					sb.append(separator);
				}
			}
		}
		return sb.toString();
		
	}
	
	/**
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toStringByList(String[] array,String separator){
		StringBuffer sb = new StringBuffer();
		if(array != null){
			for(int i = 0 ;i<array.length;i++){
				sb.append(array[i]);
				if(i != array.length - 1){
					sb.append(separator);
				}
			}
		}
		return sb.toString();
		
	}
	
	

	/**
	 * 
	 * convertToQuestionMark:(将列表转换为问号字符串).
	 * 
	 * @author Administrator
	 * @param s
	 *            待转列表
	 * @return 问号字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String convertToQuestionMark(List s) {
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.size(); index++) {
			buf.append("?,");
		}
		int end = buf.lastIndexOf(COMMA_SYMBOL);
		return buf.substring(0, end);
	}

	/**
	 * 
	 * convertToQuestionMark:(将对象数组转换为问号字符串).
	 * 
	 * @author Administrator
	 * @param s
	 *            待转对象列表
	 * @return 问号字符串
	 */
	public static String convertToQuestionMark(Object[] s) {
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append("?,");
		}
		int end = buf.lastIndexOf(COMMA_SYMBOL);
		return buf.substring(0, end);
	}

	/**
	 * 
	 * convertToStr:(将字符串数组转换为字符串).
	 * 
	 * @author Administrator
	 * @param s
	 *            待转字符串数组
	 * @return 转换后的字符串
	 */
	public static String convertToStr(String[] s) {
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append("'").append(s[index]).append("'").append(COMMA_SYMBOL);
		}
		int end = buf.lastIndexOf(COMMA_SYMBOL);
		return buf.substring(0, end);
	}

	/**
	 * 
	 * convertToStr:(将Long型数组转换为字符串).
	 * 
	 * @author Administrator
	 * @param s
	 *            待转数组
	 * @return 转换后字符串
	 */
	public static String convertToStr(Long[] s) {
		StringBuffer buf = new StringBuffer();
		for (int index = 0; index < s.length; index++) {
			buf.append(s[index]).append(COMMA_SYMBOL);
		}
		int end = buf.lastIndexOf(COMMA_SYMBOL);
		return buf.substring(0, end);
	}

	/**
	 * 
	 * exist:(判断字符串数组中是否包含某个字符穿).
	 * 
	 * @author Administrator
	 * @param arr
	 *            字符串数组
	 * @param item
	 *            目标字符串
	 * @return 包含-true;不包含-false
	 */
	public static boolean exist(String[] arr, String item) {
		if (arr != null) {
			for (int index = 0; index < arr.length; index++) {
				if (arr[index].equals(item)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * escapeXml:(将字符串中需要转移的字符替换为转移字符).
	 * 
	 * @author Administrator
	 * @param buffer
	 *            待转字符串
	 * @return 转移后的字符串
	 */
	public static String escapeXml(String buffer) {
		int start = 0;
		int length = buffer.length();
		char[] arrayBuffer = buffer.toCharArray();
		StringBuffer escapedBuffer = null;

		for (int i = 0; i < length; i++) {
			char c = arrayBuffer[i];
			if (c <= HIGHEST_SPECIAL) {
				char[] escaped = specialCharactersRepresentation[c];
				if (escaped != null) {
					// create StringBuffer to hold escaped xml string
					if (start == 0) {
						escapedBuffer = new StringBuffer(length + LENGHT_ADD);
					}
					// add unescaped portion
					if (start < i) {
						escapedBuffer.append(arrayBuffer, start, i - start);
					}
					start = i + 1;
					// add escaped xml
					escapedBuffer.append(escaped);
				}
			}
		}
		// no xml escaping was necessary
		if (start == 0) {
			return buffer;
		}
		// add rest of unescaped portion
		if (start < length) {
			escapedBuffer.append(arrayBuffer, start, length - start);
		}
		return escapedBuffer.toString();
	}

	/**
	 * 
	 * trimTo20Char:(字符串截取长度20).
	 * 
	 * @author Administrator
	 * @param str
	 *            待截取的字符串
	 * @return 截取后的字符串
	 */
	public static String trimTo20Char(String str) {
		if (str.length() > LENGHT_STRING) {
			return str.substring(0, LENGHT_STRING) + "...";
		} else {
			return str;
		}
	}

	/**
	 * 
	 * escapeVBS:(转移字符).
	 * 
	 * @author Administrator
	 * @param str
	 *            待转字符串
	 * @return 转义后的字符串
	 */
	public static String escapeVBS(String str) {
		if (str == null)
			return "";

		StringBuffer sb = new StringBuffer();
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\"') {
				sb.append("\"&chr(34)&\"");
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * replaceMatchAsSQL:(替换匹配符).
	 * 
	 * @author Administrator
	 * @param str
	 *            待转字符串
	 * @return 替换后的字符串
	 */
	public static String replaceMatchAsSQL(String str) {
		if (isEmpty(str)) {
			return null;
		}
		boolean isOnlyWildcard = true;
		char[] chs = str.toCharArray();
		for (char ch : chs) {
			if (ch != '?' && ch != '*') {
				isOnlyWildcard = false;
				break;
			}
		}
		if (isOnlyWildcard) {
			return null;
		}
		return escapeSQL(str).replaceAll("\\*", "%").replaceAll("\\?", "_");
	}

	/**
	 * 
	 * escapeSQL:(转义字符).
	 * 
	 * @author Administrator
	 * @param str
	 *            待转字符串
	 * @return 转义后的字符串
	 */
	public static String escapeSQL(String str) {
		boolean isWildcard = false;
		char[] chs = str.toCharArray();
		for (char ch : chs) {
			if (ch == '%' || ch == '_') {
				isWildcard = true;
				break;
			}
		}
		if (!isWildcard) {
			return str;
		}
		return str.replaceAll("%", "/%").replaceAll("_", "/_");
	}

	/**
	 * 
	 * getParameter:(获取参数).
	 * 
	 * @author Administrator
	 * @param parameterSuffix
	 *            后缀
	 * @param parameterName
	 *            参数名称
	 * @return 后缀存在-参数名_后缀；后缀不存在-空字符串
	 */
	public static String getParameter(Long parameterSuffix, String parameterName) {
		if (parameterSuffix == null || isEmpty(parameterName)) {
			return "";
		}
		return parameterName + "_" + String.valueOf(parameterSuffix);
	}

	/**
	 * 
	 * decodeURI:(解码RUI).
	 * 
	 * @author Administrator
	 * @param uri
	 *            统一资源标识符
	 * @return 解码后的URI
	 */
	public static String decodeURI(String uri) {
		try {
			return java.net.URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("StringUtil/decodeURI --error", e);
		}
		return null;
	}

	/**
	 * 
	 * isLong:(是否是Long型).
	 * 
	 * @author Administrator
	 * @param value
	 *            待判断字符串
	 * @return 是-ture;否-false
	 */
	public static boolean isLong(String value) {
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		if (value.matches("[1-9]{1}[0-9]*")) {
			return true;
		}
		return false;
	}
	
	/**
	 * isInteger:判断字符串是否是Integer型.
	 * @author ltp
	 * @param value 待判断字符串
	 * @return 是-ture;否-false
	 */
	public static boolean isInteger(String value) {
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("^[0-9]{1,}$");
        Matcher isNum = pattern.matcher(value);
        return isNum.matches();
	}
	
	
	/**
	 * isFloat:判断字符串是否为合法的float格式.
	 * @author ltp
	 * @param value 待判断字符串
	 * @return 是-ture;否-false
	 */
	public static boolean isFloat(String value) {
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		return value.matches("^(-?\\d+)(\\.\\d+)?$");
	}
	
	/**
	 * 
	 * exchangeExp:(交换表达式).
	 * 
	 * @author Administrator
	 * @param newexp
	 *            新表达式
	 * @param oldexp
	 *            旧表达式
	 * @param content
	 *            内容
	 * @return 交换后的表达式
	 */
	public static String exchangeExp(String newexp, String oldexp,
			String content) {
		if (StringUtil.isEmpty(content)) {
			return content;
		}
		if (newexp.equals(oldexp)) {
			return content;
		}
		String result = content;
		int o1 = result.indexOf(oldexp);
		int index = 0;
		while (-1 != o1) {
			result = exchangeExp1(newexp, oldexp, result, index);
			index = o1 + newexp.length();
			o1 = result.indexOf(oldexp, index);
		}
		return result;
	}

	/**
	 * 
	 * exchangeExp1:(交换表达式).
	 * 
	 * @author Administrator
	 * @param newexp
	 *            新表达式
	 * @param oldexp
	 *            旧表达式
	 * @param content
	 *            待处理字符串
	 * @param index
	 *            索引
	 * @return 交换后的字符串
	 */
	private static String exchangeExp1(String newexp, String oldexp,
			String content, int index) {
		int o1 = content.indexOf(oldexp, index);
		if (-1 == o1) {
			return content;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(content.substring(0, o1));
		sb.append(newexp);
		sb.append(content.substring(o1 + oldexp.length(), content.length()));
		return sb.toString();
	}

	/**
	 * 
	 * empty:(判断对象是否为空).
	 * 
	 * @author Administrator
	 * @param obj
	 *            待判断对象
	 * @return 为空返回true,否则返回false
	 */
	public static boolean empty(Object obj) {

		boolean flag = false;
		if (obj == null) {
			flag = true;
		} else if (obj instanceof String && "".equals(obj)) {
			flag = true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			flag = true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			flag = true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			flag = true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * exceptSplit:(去掉字符串最后的逗号).
	 * 
	 * @author Administrator
	 * @param str
	 *            待处理的字符串
	 * @return 处理后的字符串
	 */
	public static StringBuilder exceptSplit(StringBuilder str) {
		Integer len = str.length();
		if (len > 0) {
			Integer _str = (str.lastIndexOf(COMMA_SYMBOL) + 1);
			if (len.equals(_str)) {
				str.deleteCharAt(len - 1);
			}
		}
		return str;
	}
	public static String formatDecimal(double d){
		DecimalFormat df = new DecimalFormat("#####0.00 ");
		return df.format(d);
		
	}

	/**
	 * 10位纯数字
	 * @return
	 */
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z" };     
	
	  
	public static String generateShortUuid() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }
	    
	    SimpleDateFormat sdf=new SimpleDateFormat("yy");
	    shortBuffer.append(sdf.format(new Date()));
	    
	    return shortBuffer.toString();  
	  
	}  
	
	public static String getRandomCode(int length){
		StringBuffer sb = new StringBuffer(); 
		for(int i=0;i<length;i++){
			sb.append(new Random().nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 获取随机字符和数字的组合
	 * @param length
	 * @param num
	 * @return
	 */
	public static List<String> genCodes(int length, long num) {

		List<String> results = new ArrayList<String>();

		for (int j = 0; j < num; j++) {
			String val = "";

			Random random = new Random();
			for (int i = 0; i < length; i++) {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

				if ("char".equalsIgnoreCase(charOrNum)) // 字符串
				{
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
				{
					val += String.valueOf(random.nextInt(10));
				}
			}
			val = val.toLowerCase();
			if (results.contains(val)) {
				continue;
			} else {
				results.add(val);
			}
		}
		return results;

	}
	
	public static void main(String[] args) {
		List<String> objs = genCodes(6, 300);
		for (int i = 0; i < objs.size(); i++) {
			System.out.println(objs.get(i));
		}
	}
}
