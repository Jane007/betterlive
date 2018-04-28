package com.kingleadsw.betterlive.core.page;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 下拉框 Bean 类
 * value 下拉框值
 * text 下拉框文本值
 * @author fox
 */
public class ComboBean  implements Serializable {
	
	public static final int DEFAULT_BEAN_VALUE = -1;
	
	private static final String DEFAULT_BEAN_VALUE_STR = DEFAULT_BEAN_VALUE + "";
	
	private static final String DEFAULT_BEAN_TEXT = "请选择";
	
	/**
	 * 默认
	 */
	private static final ComboBean DEFAULT_BEAN = new ComboBean(DEFAULT_BEAN_VALUE + "", DEFAULT_BEAN_TEXT);
	
	/**
	 * 值为 空字符的 下拉选项
	 */
	private static final ComboBean DEFAULT_BEAN_OF_EMPTY_STRING_VALUE = new ComboBean("", DEFAULT_BEAN_TEXT);
	private List<ComboBean> children=new ArrayList<ComboBean>();
	
	public List<ComboBean> getChildren()
    {
        return children;
    }

    public void setChildren(List<ComboBean> children)
    {
        this.children = children;
    }

    public ComboBean(String value, String text) {
		this.value = value;
		this.text = text;
	}
	private String value;
	private String text;
	public String getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
	
	public static ComboBean getDefaultComboBean(){
		return DEFAULT_BEAN;
	}
	
	public static ComboBean getDefaultBeanOfEmptyStringValue() {
		return DEFAULT_BEAN_OF_EMPTY_STRING_VALUE;
	}
	
	public static String getDefaultValueStr() {
		return DEFAULT_BEAN_VALUE_STR;
	}
	
}
