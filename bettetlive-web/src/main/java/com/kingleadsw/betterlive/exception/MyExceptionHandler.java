package com.kingleadsw.betterlive.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.core.page.ResultBean;

public class MyExceptionHandler implements HandlerExceptionResolver {
	
	private static final Logger logger = Logger.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("404_jsp");
		try {
			//如果是ajax请求，响应error错误码及错误描述
			if (request.getHeader("accept").indexOf("application/json") > -1 || 
					(request.getHeader("X-Requested-With") != null && 
					request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
				String result = new ResultBean("error", "系统出现异常").toJsonString();
				PrintWriter out = response.getWriter();
				out.print(result.toString());
				out.close();
				return null;
			} 
		} catch(IOException ioe) {
			
		}
		
		if (ex instanceof MaxUploadSizeExceededException) { // 上传文件过大
			try {
				String result = new ResultBean("error", "上传文件过大").toJsonString();
				response.getWriter().write(result);
			} catch (IOException e) {
				 logger.error("MyExceptionHandler/resolveException --error", e);  
			}
		} else {
			logger.error("系统异常");
		}
		return mv;
	}

}
