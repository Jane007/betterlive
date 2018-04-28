package com.kingleadsw.betterlive.util;

import javax.servlet.http.HttpServletRequest;

import com.kingleadsw.betterlive.core.util.StringUtil;

public class CustomerSourceUtil {

	/**
	 * 判断是否有订单来源
	 * @param req 			//httpRequest
	 * @param source		//访问地址中的带参的来源
	 * @param orderSource	//访问地址中带参的订单来源
	 * @param defaultSource	//默认来源
	 * @return
	 */
	public static String checkOrderSource(HttpServletRequest req, String source, String orderSource, String defaultSource){
		if(StringUtil.isNotNull(source)){
			defaultSource = source;
		}else if(StringUtil.isNotNull(orderSource)){ //兼容旧版来源
			defaultSource = orderSource;
		}else if(StringUtil.isNoNull(req.getSession().getAttribute("source"))){ //新版来源
			defaultSource = (String)req.getSession().getAttribute("source");
		}
		req.getSession().setAttribute("orderSource", defaultSource);
		return defaultSource;
	}
	
	/**
	 * 获取订单来源
	 * @param req //httpRequest
	 * @return
	 */
	public static String getOrderSource(HttpServletRequest req, String source){
		if(StringUtil.isNull(source)
				&& StringUtil.isNoNull(req.getSession().getAttribute("orderSource"))){
			source = (String)req.getSession().getAttribute("orderSource");
		}
		return source;
	}

	/**
	 * 清除缓存中的订单来源
	 * @param request
	 */
	public static void removeOrderSource(HttpServletRequest request) {
		request.getSession().removeAttribute("orderSource");
		request.getSession().removeAttribute("source");
	}
}
