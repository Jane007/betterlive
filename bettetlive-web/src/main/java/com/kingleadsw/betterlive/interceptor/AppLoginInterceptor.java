package com.kingleadsw.betterlive.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.JacksonUtil;
import com.kingleadsw.betterlive.core.util.SignUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;

public class AppLoginInterceptor implements HandlerInterceptor {
	
	private static Logger logger = Logger.getLogger(AppLoginInterceptor.class);
	
	@Autowired
	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
			if(StringUtil.isNull(request.getParameter("token"))){
				this.outObjectToJson(
						CallBackConstant.TOKEN_ERROR.callbackError("token不能为空"),
						response);
				return false;
			}
			
			String isAuth = request.getParameter("isAuth");
			if("true".equals(isAuth)){
				return true;
			}
			
			String token = request.getParameter("token");
			try {
				String keys = redisService.getString(token);
				if(StringUtil.isNull(keys)){
					this.outObjectToJson(
							CallBackConstant.LOGIN_TIME_OUT.callbackError("登陆超时"),
							response);
					return false;
				}

				Map<String,String> params=new HashMap<String,String>();
				Map properties = request.getParameterMap();
				Iterator entries = properties.entrySet().iterator();
				Map.Entry entry;

				StringBuffer sb = new StringBuffer();
				String name = "";
				String value = "";
				String authV = "";
				String key = "aebb71d041d59a7d6a7257a204238aa0";
				while (entries.hasNext()) {
					entry = (Map.Entry) entries.next();
					name = (String) entry.getKey();
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
					if("auth".equals(name)){
						authV = value;
					}
					//所以的参数放入集合
					if(!"auth".equals(name)){
						params.put(name, value);
					}
				}
				try {
					String result= SignUtil.getSign(params, key);
					if(!result.equals(authV.toUpperCase())){
						outObjectToJson(CallBackConstant.PARAMETER_ERROR.callback("鉴权失败"),response);
						return false;
					}
				}catch (Exception e){
					logger.error("AppLoginInterceptor/preHandle --error", e);
					outObjectToJson(CallBackConstant.PARAMETER_ERROR.callback("鉴权失败"), response);
					return false;
				}

			}catch (Exception e){
				logger.error("AppLoginInterceptor/preHandle --error", e);
				this.outObjectToJson(
						CallBackConstant.TOKEN_TIME_OUT.callbackError("登陆超时"),
						response);
				return false;
			}
		return true;
	}
	/**
	 * object 类型 转化为 json 输出.
	 * 
	 * @param obj
	 * @param response
	 * 
	 */
	protected void outObjectToJson(Object obj, HttpServletResponse response) {
		try {
			String str = JacksonUtil.serializeObjectToJson(obj);
			outJson(str, response);
		} catch (Exception e) {
			logger.error("AppLoginInterceptor/outObjectToJson --error", e);
		}
	}
	/**
	 * 输出json.
	 * @param str json字符串
	 * @param response
	 */
	protected void outJson(String str, HttpServletResponse response) {
		try {
			if (response != null) {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(str);
			}
		} catch (IOException e) {
			logger.error("AppLoginInterceptor/outJson --error", e);
		}
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
