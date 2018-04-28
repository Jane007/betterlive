package com.kingleadsw.betterlive.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.JacksonUtil;
import com.kingleadsw.betterlive.init.SpringContextHolder;
import com.kingleadsw.betterlive.redis.Keys;
import com.kingleadsw.betterlive.redis.RedisService;

public class AppReuqestAuthInterceptor implements HandlerInterceptor {
	
	private  static Logger logger = Logger.getLogger(AppReuqestAuthInterceptor.class);
	
	private String[] exceptFilterUrls;// 利用正则映射到需要拦截的路径
	
	public String[] getExceptFilterUrls() {
		return exceptFilterUrls;
	}

	public void setExceptFilterUrls(String[] exceptFilterUrls) {
		this.exceptFilterUrls = exceptFilterUrls;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;

		StringBuffer sb = new StringBuffer();
		String name = "";
		String value = "";
		String authV = "";  //签名字符串
		String time = "";   //请求时间
		String token = "";  //签名字符串
		Object page = null; //当前页码
		Object rows = null; //每页记录数
		String key = "aebb71d041d59a7d6a7257a204238aa0";

		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			if ("auth".equals(name)) {
				authV = value;
			} 
			if ("time".equals(name)) {
				time = value;
			}
			// 所以的参数放入集合
			if (!"auth".equals(name)) {
				params.put(name, value);
			}
			// token
			if ("token".equals(name)) {
				token = value;
			}
			if ("page".equals(name)) {
				page = value;
			}
			if ("rows".equals(name)) {
				rows = value;
			}
		}
		

		try {
		/*			if ("".equals(time)) {
				outObjectToJson(
						CallBackConstant.PARAMETER_ERROR.callback("请求超时"),
						response);
				return false;
			}
			if ("".equals(authV)) {
				outObjectToJson(
						CallBackConstant.PARAMETER_ERROR.callback("签名不能为空"),
						response);
				return false;
			}*/
			RedisService redisService = SpringContextHolder
					.getBean("redisService");
			if (!"".equals(token)) {
				String apptoken = redisService.getString(Keys.APP_TOKEN_PREFIX + token);
				if (apptoken == null) {
					this.outObjectToJson(CallBackConstant.TOKEN_TIME_OUT
							.callbackError("token已过期"), response);
					return false;
				} /*else {
					long times = Long.parseLong(tokenTime);
					long seconds = (System.currentTimeMillis() - times) / 1000;
					// 当前时间比较token创建时间， 如果大于设定时间
					if (seconds > WebConstant.TOKEN_TIME) {
						// token过期则删除掉，节省缓存空间
						redisService.delKey(token);
						// 输出信息
						this.outObjectToJson(CallBackConstant.TOKEN_TIME_OUT
								.callbackError("token已过期"), response);
						return false;
					}
				}*/
			}
			/*
			long timeTmp = Long.parseLong(time);
			if (System.currentTimeMillis() - timeTmp > 60 * 1000 * 3) {
				outObjectToJson(
						CallBackConstant.REQUEST_TIME_OUT.callback("请求超时"),
						response);
				return false;
			}
			if (request.getRequestURI().endsWith("list")) {
				if (HeaderUtil.getMobilOS(request.getHeader("user-agent")).isMobile()) {
					if (null == page || null == rows) {
						this.outObjectToJson(
								CallBackConstant.PARAMETER_ERROR
										.callbackError("page,rows参数缺失"),
								response);
						return false;
					}
				}
			}
			
			String result = SignUtil.getSign(params, key);
			*/			
			//取消参数校验
			/*if (!result.equals(authV.toUpperCase())) {
				outObjectToJson(
						CallBackConstant.PARAMETER_ERROR.callback("鉴权失败"),
						response);
				return false;
			}*/
			
			return true;
		} catch (Exception e) {
			logger.error("AppReuqestAuthInterceptor/preHandle --error", e);
			outObjectToJson(CallBackConstant.PARAMETER_ERROR.callback("鉴权失败"),
					response);
			return false;
		}
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
			// outString(str,response);
			outJson(JacksonUtil.serializeObjectToJson(obj), response);
		} catch (Exception e) {
			logger.error("AppReuqestAuthInterceptor/outJson --error", e);
		}
	}

	/**
	 * 输出json.
	 * 
	 * @param str
	 *            json字符串
	 * @param response
	 */
	protected void outJson(String str, HttpServletResponse response) {
		try {
			if (response != null) {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(str);
				// out.close();
			}
		} catch (IOException e) {
			logger.error("AppReuqestAuthInterceptor/outJson --error", e);
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
