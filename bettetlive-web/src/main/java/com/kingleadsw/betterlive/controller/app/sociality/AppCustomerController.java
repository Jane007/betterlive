package com.kingleadsw.betterlive.controller.app.sociality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.redis.RedisService;

/**
 * app 接口
 * 发现——个人主页——设置
 * 2017-10-20 by fang
 */
@Controller
@RequestMapping(value = "/app/customer")
public class AppCustomerController extends AbstractWebController{
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private RedisService redisService;

}
