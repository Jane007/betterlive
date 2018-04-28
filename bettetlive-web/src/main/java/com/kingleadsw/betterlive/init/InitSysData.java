package com.kingleadsw.betterlive.init;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.kingleadsw.betterlive.util.alipay.service.OneCardPayService;

/**
 * 所以的bean初始化完成后，调用初始化系统基础数据
 * @author Administrator
 *
 */
public class InitSysData implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = Logger.getLogger(InitSysData.class);
 
	@Autowired
	private OneCardPayService oneCardPayService;
	/**
	 * 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//系统会存在两个容器，一个是root application context ,
		//另一个就是我们自己的 projectName-servlet  context（作为root application context的子容器）。
		//这种情况下，就会造成onApplicationEvent方法被执行两次。
		 if (event.getApplicationContext().getParent() == null) {
	           new Thread(new Runnable() {
				   @Override
				   public void run() {
					   //初始化地址信息到缓存
					   long startTime = System.currentTimeMillis();
					   logger.info("开始初始化省市县数据.......");
					   initDistrict();
					   oneCardPayService.showTimer();
					   logger.info("初始化省市县数据结束.......");
					   logger.info("花费总时间："+(System.currentTimeMillis() - startTime));
				   }
			   }).start();
		 }
	}
	/**
	 * 初始化地址信息到缓存
	 */
	private void initDistrict(){
 		 
	}
		
}
