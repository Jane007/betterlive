package com.kingleadsw.betterlive.init;


import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@Component
public class ContextUtil {
	private static final Logger logger = Logger.getLogger(ContextUtil.class);

	private static WebApplicationContext webAppContext;

	static void setCrasContext(WebApplicationContext applicationContext) {
		webAppContext = applicationContext;
	}

	public static Object getBean(String name) {
		if (StringUtils.isEmpty(name)) {
			logger.warn("Get bean from content. but bean name is empty..");
			return null;
		}
		try {
			return webAppContext.getBean(name);
		} catch (BeansException e) {
			logger.warn("Get bean from content. but bean not exist..");
			return null;
		}
	}

	public static <T> T getBean(String name, Class<T> type) {
		if (StringUtils.isEmpty(name)) {
			logger.warn("Get bean from content. but bean name is empty..");
			return null;
		}
		try {
			return (T) webAppContext.getBean(name, type);
		} catch (BeansException e) {
			logger.warn("Get bean from content. but bean not exist..");
			return null;
		}
	}
}
