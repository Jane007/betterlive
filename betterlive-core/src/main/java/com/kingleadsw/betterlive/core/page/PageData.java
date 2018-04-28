package com.kingleadsw.betterlive.core.page;




import javax.servlet.http.HttpServletRequest;

import com.kingleadsw.betterlive.core.util.StringUtil;

import java.util.*;


public class PageData extends HashMap implements Map {
	
private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;
	PageView pv;
	
	public PageData(HttpServletRequest request){
		this.request = request;
		request.getParameterMap();
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";  
		String value = "";
		pv = setPage(pv,request);
		if (pv != null) {
			returnMap.put("pageView", pv);
		}
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			if("page".equals(name) || "pageIndex".equals(name) || "rows".equals(name)){
				continue;
			}
			Object valueObj = entry.getValue();
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
				
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public Integer getInteger(Object key)throws RuntimeException {
		Integer number=null;
		Object o=get(key);
		if(o == null){
			number=null;
			return number;
		}
		if(o instanceof Integer){
			return (Integer)o;
		}else if(o instanceof String){
			try{
				number=Integer.parseInt(getString(key));
			}catch(RuntimeException e){
				throw e;
			}
		}else{
			throw new RuntimeException();
		}
	
		return number;
	}
	
	public Long getLong(Object key)throws RuntimeException {
		Long number=null;
		Object o = get(key);
		if(o == null){
			number=null;
			return number;
		}
		if(o instanceof Long){
			return (Long)o;
		}else if(o instanceof String){
			try{
				number=Long.parseLong(getString(key));
			}catch(RuntimeException e){
				throw e;
			}
		}else{
			throw new RuntimeException();
		}
	
		return number;
	}
	
	public String getString(Object key) {
		if(get(key) == null){
			return "";
		}
		return (String)get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		map.putAll(t);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}

	public PageView setPage(PageView pv,HttpServletRequest request){
		if(StringUtil.isNoNull(request.getParameter("page")) || StringUtil.isNoNull(request.getParameter("pageIndex")) 
				|| StringUtil.isNoNull(request.getParameter("rows"))){
			if (StringUtil.isNoNull(request.getParameter("page"))) {
				if (pv == null) {
					pv = new PageView();
				}
				if (StringUtil.isNumber(request.getParameter("page"))) {
					pv.setPageNow(Integer.valueOf(request.getParameter("page")));
				} else {
					pv.setPageNow(1);
				}
			}
			if (StringUtil.isNoNull(request.getParameter("pageIndex"))) {
				if (pv == null) {
					pv = new PageView();
				}
				if (StringUtil.isNumber(request.getParameter("pageIndex"))) {
					pv.setPageNow(Integer.valueOf(request.getParameter("pageIndex")));
				} else {
					pv.setPageNow(1);
				}
			}
			if (StringUtil.isNoNull(request.getParameter("rows"))) {
				if (pv == null) {
					pv = new PageView();
				}
				if (StringUtil.isNumber(request.getParameter("rows"))) {
					pv.setPageSize(Integer.valueOf(request.getParameter("rows")));
				} else {
					pv.setPageSize(10);
				}
			}
		}
		return pv;
	}
	
}
