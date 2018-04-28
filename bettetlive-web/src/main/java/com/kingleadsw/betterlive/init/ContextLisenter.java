package com.kingleadsw.betterlive.init;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kingleadsw.betterlive.consts.WebConstant;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;


/**
 * 初始化全局上下配置
 */
public class ContextLisenter implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ContextLisenter.class);
	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		ContextUtil.setCrasContext(ctx);

		// 初始化application
		this.initApplication(event);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * 初始化application环境.<br/>
	 * 
	 * @param event
	 *            servlet上下文事件对象
	 */
	private void initApplication(ServletContextEvent event) {
		try{
			if (null != event) {
				ServletContext sc = event.getServletContext();

				/* 初始化application级别的变量。 */
				sc.setAttribute("mainserver", WebConstant.MAIN_SERVER);
				// 前端页面资源路径
				sc.setAttribute("resourcepath", WebConstant.RESOURCE_PATH);
				//骑牛link
				sc.setAttribute("qiniulink",WebConstant.QINIU_LINK);
				
				sc.setAttribute("appId",WebConstant.WX_APPID);

			 
				/**
				 * 创建文件上传根路劲
				 */
				File uploadpath = new File(WebConstant.UPLOAT_ROOT_PATH);
				if(!uploadpath.exists()){
					if(uploadpath.mkdirs()){
						logger.info("文件上传根路劲：" + WebConstant.UPLOAT_ROOT_PATH+"已创建......");
					}else{
						logger.error("文件上传根路劲：" + WebConstant.UPLOAT_ROOT_PATH+"创建失败......");
					}
				}
				/**
				 * 创建图片路劲
				 */
				uploadpath = new File(WebConstant.UPLOAT_ROOT_PATH + File.separator + WebConstant.UPLOAT_ROOT_PATH_IMAGE);
				if(!uploadpath.exists()){
					if(uploadpath.mkdirs()) {
						logger.info("图片路劲：" + WebConstant.UPLOAT_ROOT_PATH + File.separator + WebConstant.UPLOAT_ROOT_PATH_IMAGE + "已创建......");
					}else{
						logger.error("图片路劲：" + WebConstant.UPLOAT_ROOT_PATH + File.separator + WebConstant.UPLOAT_ROOT_PATH_IMAGE + "创建失败......");
					}
				}

		 
				AppServerUtil.setClassesPath(event.getServletContext().getRealPath("/"));
				AppServerUtil.setWebRootPath(event.getServletContext().getRealPath("WEB-INF/classes"));
			}
			
			initPayConfig();
		}catch(Exception e){
			logger.error("系统初始化配置项出现异常", e);
		}

	}
	
	private void initPayConfig(){

		logger.info("初始化支付通知接口，微信：" + WebConstant.WX_ORDER_NOTIFY_URL);
		System.setProperty("wx.pay.notify.path", WebConstant.WX_ORDER_NOTIFY_URL);
		
		
	}

}