package com.kingleadsw.betterlive.core.page;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kingleadsw.betterlive.core.util.JacksonUtil;


public class EasyUIPageBean  implements Serializable {
	
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	public static final EasyUIPageBean NONE = new EasyUIPageBean(0L, new ArrayList<Object>());

	private Long total;
	private List<? extends Object> rows;
	private String result;
	private String message;
	
	public static final EasyUIPageBean createEasyUIPageBean(Long total, List<? extends Object> rows) {
		return new EasyUIPageBean(total, rows);
	}
	
	public static final EasyUIPageBean createFailureEasyUIPageBean(String message) {
		return new EasyUIPageBean(message);
	}
	
	private EasyUIPageBean(Long total, List<? extends Object> rows) {
		this.total = total;
		this.rows = rows;
		this.result = SUCCESS;
	}
	private EasyUIPageBean(String message) {
		this.total = 0L;
		this.rows = new ArrayList(0);
		this.result = FAILURE;
		this.message = message;
	}
	
	public String toJsonString() {
		return JacksonUtil.serializeObjectToJson(this);
	}
	public Long getTotal() {
		return total;
	}
	public List<? extends Object> getRows() {
		return rows;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}
	
}
