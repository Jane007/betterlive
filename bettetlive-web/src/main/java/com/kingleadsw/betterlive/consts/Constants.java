package com.kingleadsw.betterlive.consts;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.model.Admin;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

public final class Constants {
	
	protected static final Logger logger = Logger.getLogger(Constants.class);
	
	public static final String SESSION_USER = "session_user";

	private static final String SESSION_VALIDATECODE = "session_validatecode";//验证码
	
	private static final String SESSION_SMSCODE = "session_smscode";//验证码
	
	public static final String SESSION_CUSTOMER = "customer";
	
	private static final String SESSION_ACCESS_URLS = "session_access_urls"; //系统能够访问的URL
	
	private static final String SESSION_MENUBTN_MAP = "session_menubtn_map"; //系统菜单按钮

	private static final String SESSION_ADMIN="admin";
	
	/**
	  * 设置session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static void setAttr(HttpServletRequest request,String key,Object value){
		 request.getSession(true).setAttribute(key, value);
	 }
	 
	 
	 /**
	  * 获取session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static Object getAttr(HttpServletRequest request,String key){
		 return request.getSession(true).getAttribute(key);
	 }
	 
	 /**
	  * 删除Session值
	  * @param request
	  * @param key
	  */
	 public static void removeAttr(HttpServletRequest request,String key){
		 request.getSession(true).removeAttribute(key);
	 }
 
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return SysUser
	  */
	 public static void removeUser(HttpServletRequest request){
		removeAttr(request, SESSION_USER);
	 }
	 
	 
	 /**
	  * 设置验证码 到session
	  * @param request
	  * @param user
	  */
	 public static void setValidateCode(HttpServletRequest request,String validateCode){
		 request.getSession(true).setAttribute(SESSION_VALIDATECODE, validateCode);
	 }
	 
	 
	 /**
	  * 从session中获取验证码
	  * @param request
	  * @return SysUser
	  */
	 public static String getValidateCode(HttpServletRequest request){
		return (String)request.getSession(true).getAttribute(SESSION_VALIDATECODE);
	 }
	 
	 
	 /**
	  * 从session中获删除验证码
	  * @param request
	  * @return SysUser
	  */
	 public static void removeValidateCode(HttpServletRequest request){
		removeAttr(request, SESSION_VALIDATECODE);
	 }
	 
	 /**
	  * 判断当前登录用户是否超级管理员
	  * @param request
	  * @return
	  */
	 public static void setAccessUrl(HttpServletRequest request,List<String> accessUrls){ //判断登录用户是否超级管理员
		 setAttr(request, SESSION_ACCESS_URLS, accessUrls);
	 }
	 
	 /**
	  * 判断URL是否可访问
	  * @param request
	  * @return
	  */
	 public static boolean isAccessUrl(HttpServletRequest request,String url){ 
		 List<String> accessUrls = (List)getAttr(request, SESSION_ACCESS_URLS);
		 if(accessUrls == null ||accessUrls.isEmpty() || !accessUrls.contains(url)){
			 return false;
		 }
		 return true;
	 }
	 
	 
	 /**
	  * 设置菜单按钮
	  * @param request
	  * @param btnMap
	  */
	 public static void setMemuBtnMap(HttpServletRequest request,Map<String,List> btnMap){ //判断登录用户是否超级管理员
		 setAttr(request, SESSION_MENUBTN_MAP, btnMap);
	 }
	 
	 /**
	  * 获取菜单按钮
	  * @param request
	  * @param btnMap
	  */
	 public static List<String> getMemuBtnListVal(HttpServletRequest request,String menuUri){ //判断登录用户是否超级管理员
		 Map btnMap  = (Map)getAttr(request, SESSION_MENUBTN_MAP);
		 if(btnMap == null || btnMap.isEmpty()){
			 return null;
		 }
		 return (List<String>)btnMap.get(menuUri);
	 }
	 
	 
	 /**
	  * 设置验证码 到session
	  * @param request
	  * @param user
	  */
	 public static void setSmsCode(HttpServletRequest request,String smsCode){
		HttpSession session= request.getSession(true);
		 session.setMaxInactiveInterval(90);
		 session.setAttribute(SESSION_SMSCODE, smsCode);
	 }
	 
	 
	 /**
	  * 从session中获取验证码
	  * @param request
	  * @return SysUser
	  */
	 public static String getSmsCode(HttpServletRequest request){
		return (String)request.getSession(true).getAttribute(SESSION_SMSCODE);
	 }
	 
	 
	 /**
	  * 设置用户信息 到session
	  * @param request
	  * @param Customer
	  */
	 public static void setCustomer(HttpServletRequest request,CustomerVo customer){
		 request.getSession(true).setAttribute(SESSION_CUSTOMER, customer);
	 }
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return Customer
	  */
	 public static CustomerVo getCustomer(HttpServletRequest request){
	     
//	     if("true".equals(WebConstant.ISMEMBER)){
			 if(request.getSession(true).getAttribute(SESSION_CUSTOMER) != null){
				 return (CustomerVo)request.getSession(true).getAttribute(SESSION_CUSTOMER);
			 }else{
				 return null;
			 }
//	     }
//	     else{
//	    	 request.getSession().setAttribute("isMember",WebConstant.ISMEMBER);
//	    	 CustomerVo m = new CustomerVo();
//	         m.setCustomer_id(1);
//	         m.setNickname("yo yo");
//	         m.setMobile("");
//	         m.setPay_pwd("e10adc3949ba59abbe56e057f20f883e");
//	         m.setPassword("bae5e3208a3c700e3db642b6631e95b9");
//	         m.setBirthday("1991-10-10");
//	         m.setSex(1);
//	         m.setHead_url("http://wx.qlogo.cn/mmopen/JTWa8vpxlSTT9uZRSbiavLqeic5Wqp921woJ36s7icvic0IIXahsTV33gU7Tmzg0vT7DVrGmJQ6gQ8ictNwiaVjvwBhPlsFoUYdKvf/0");
//	         //m.setOpenid("oeddluMXSI2pwaE73U5Hun5bx1II");
//	         m.setOpenid("oF4kgxAhik-Z2QTPO0iegUcVFxLc");
//	         //m.setPay_pwd("e10adc3949ba59abbe56e057f20f883e");
//	         return m;
//	     }
	 }
	 
	 
	 
	 
	 /**
	  * 设置管理员用户信息 到session
	  * @param request
	  * @param Customer
	  */
	 public static void setAdmin(HttpServletRequest request,AdminVo admin){
		 request.getSession(true).setAttribute(SESSION_ADMIN, admin);
	 }
	 
	 
	 public static void setAdminModel(HttpServletRequest request,Admin admin){
		 request.getSession(true).setAttribute(SESSION_ADMIN, admin);
	 }
	 
	 /**
	  * 从session中获取管理员信息
	  * @param request
	  * @return Customer
	  */
	 public static AdminVo getAdmin(HttpServletRequest request){
	       return (AdminVo)request.getSession(true).getAttribute(SESSION_ADMIN);
	 }
}
