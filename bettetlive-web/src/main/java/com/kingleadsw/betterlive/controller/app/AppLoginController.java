package com.kingleadsw.betterlive.controller.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.IpAndMac;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.Keys;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * Created by ltp on 2017-04-18.
 * app登陆接口
 */
@Controller
@RequestMapping("/app/login")
public class AppLoginController extends AbstractWebController {

    protected Logger logger = Logger.getLogger(AppLoginController.class);

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private RedisService redisService;

    /**
     * app登陆
     * @return
     */
    @RequestMapping("/in")
    @ResponseBody
    public Map<String,Object> in(){
        PageData pageData = this.getPageData();
        String mobile=pageData.getString("mobile");
        if(StringUtil.isNull(mobile)){
            return CallBackConstant.PARAMETER_ERROR.callbackError("没有接收到电话号码");
        }
        String password = StringUtil.nullToString(pageData.get("password"), "");
        if (StringUtil.isNull(password)) {
        	return CallBackConstant.PARAMETER_ERROR.callbackError("密码不能为空");
        }
        
        try {
            pageData.remove("password");

            CustomerVo customerVo = customerManager.queryOne(pageData);
            if(null == customerVo){
                return CallBackConstant.NOT_EXIST.callbackError("没有查询到用户信息");
            }
            password = password.toLowerCase();
            if(!password.equals(customerVo.getPassword())){
                return CallBackConstant.FAILED.callbackError("密码输入错误");
            }
            String token = StringUtil.get32UUID();
            customerVo.setToken(token);
            //不能将登陆密码和支付密码返回给客户端
            customerVo.setPassword(null); //将登陆密码设置为null
            if (customerVo.getPay_pwd() != null && !"".equals(customerVo.getPay_pwd())) {
            	customerVo.setIs_paypwd(1);
            } else {
            	customerVo.setIs_paypwd(0);
            }
            
            customerVo.setPay_pwd(null); //将支付设置为null
            redisService.setex(Keys.APP_TOKEN_PREFIX + token, mobile, WebConstant.TOKEN_TIME);
            redisService.setObject(Keys.APP_CUSTOMER_PREFIX + mobile, customerVo);
            redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + mobile, WebConstant.TOKEN_TIME);
            
            return CallBackConstant.SUCCESS.callback(customerVo);
        }catch (Exception e){
            logger.error("根据电话号码：" + pageData.get("phone")+"查询信息失败");
            return CallBackConstant.FAILED.callback();
        }
    }
    


    /**
     * app退出
     * @return
     */
    @RequestMapping("/out")
    @ResponseBody
    public Map<String,Object> out(){
    	logger.info("app退出登陆，开始");
        PageData pageData = getPageData();
        if(StringUtil.isNull(pageData.get("token"))){
        	logger.error("token不能为空");
            return CallBackConstant.PARAMETER_ERROR.callbackError("没有接收到token");
        }
        logger.info("token：" + pageData.getString("token"));
        
        String cutomerToken = Keys.APP_TOKEN_PREFIX + pageData.getString("token");
        CustomerVo customer = redisService.getObject(cutomerToken);
        
        if (customer != null) {
        	logger.info("退出用户id：" + customer.getCustomer_id());
        	//清空用户token
        	redisService.delKey(Keys.APP_TOKEN_PREFIX + pageData.getString("token"));
        	//清空用户缓存
        	redisService.delKey(Keys.APP_CUSTOMER_PREFIX + pageData.getString("token"));
        }
        
        logger.info("app退出登陆，结束");
        
        return CallBackConstant.SUCCESS.callback("退出登录成功");
    }
    
    
    /**
     * app用户注册
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> register(HttpServletRequest request) {
    	logger.info("app用户注册开始");
    	try {
			logger.info("注册IP：" + IpAndMac.getIpAddr(request));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        PageData pageData = this.getPageData();
        if(StringUtil.isNull(pageData.get("mobile"))){
            return CallBackConstant.PARAMETER_ERROR.callbackError("没有接收到电话号码");
        }
        String password = StringUtil.nullToString(pageData.get("password"), "");
        if (StringUtil.isNull(password)) {
        	return CallBackConstant.PARAMETER_ERROR.callbackError("密码不能为空");
        }
        
        try {
            pageData.remove("password");

            CustomerVo customerVo = customerManager.queryOne(pageData);
            if(null != customerVo){  //用户已经注册
                return CallBackConstant.REGISTERED.callback();
            }
            
            password = password.toLowerCase();
            customerVo = new CustomerVo();
            customerVo.setPassword(password);
            customerVo.setMobile(pageData.getString("mobile"));
            customerManager.insertCustomer(customerVo);
            logger.info("app用户注册结束");
            return CallBackConstant.SUCCESS.callBackByMsg("注册成功");
        }catch (Exception e){
            logger.error("用户" + pageData.get("phone")+"注册失败");
            return CallBackConstant.FAILED.callback();
        }
    }
    
    
    /**
     * app第三方登录
     * @return
     */
    @RequestMapping("/otherlogin")
    @ResponseBody
    public Map<String,Object> otherLogin(HttpServletRequest request) {
    	logger.info("app用户第三方登录开始");
    	String unionid=request.getParameter("unionid");
        if(StringUtil.isNull(unionid)){
        	return CallBackConstant.PARAMETER_ERROR.callbackError("unionid不能为空");
        }
        
        try {
        	
        	PageData pageData=new PageData();
        	pageData.put("unionid", unionid);
            CustomerVo customerVo = customerManager.queryOne(pageData);
            if(null != customerVo){
            	String mobile = customerVo.getMobile();
            	if(StringUtil.isNotNull(mobile)){//手机号码不为空，表示用户已经绑定过手机，直接登录进去即可
            		logger.info("用户已经绑定手机号：" + mobile);
            		 String token = StringUtil.get32UUID();
                     customerVo.setToken(token);
                     //不能将登陆密码和支付密码返回给客户端
                     customerVo.setPassword(null); //将登陆密码设置为null
                     if (customerVo.getPay_pwd() != null && !"".equals(customerVo.getPay_pwd())) {
                     	customerVo.setIs_paypwd(1);
                     } else {
                     	customerVo.setIs_paypwd(0);
                     }
                     
                     customerVo.setPay_pwd(null); //将支付设置为null
                     redisService.setex(Keys.APP_TOKEN_PREFIX + token, mobile, WebConstant.TOKEN_TIME);
                     redisService.setObject(Keys.APP_CUSTOMER_PREFIX + mobile, customerVo);
                     redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + mobile, WebConstant.TOKEN_TIME);
                     return CallBackConstant.SUCCESS.callback(customerVo);
            	}
                return CallBackConstant.SUCCESS.callback(customerVo);
            } else {
            	CustomerVo customerVo2 = new CustomerVo();
            	customerVo2.setUnionid(unionid);
            	return CallBackConstant.SUCCESS.callback(customerVo2);
            }
        } catch (Exception e){
        	logger.error("/app/login/otherlogin --error", e);
            return CallBackConstant.FAILED.callback();
        }
        
    }

}
