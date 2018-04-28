package com.kingleadsw.betterlive.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Encoder;

public class TokenProcessor {
	/*
	 * 1.把构造函数私有 
	 * 2.自己创建一个
	 * 3.对外暴露一个方法，允许获取上面创建的对象
	 */
	private static final TokenProcessor instance = new TokenProcessor();

	private TokenProcessor() {
		
	}

	public static TokenProcessor getInstance() {
		return instance;
	}

	/**
	 * 服务器端生成一个token，保存在session中，并将该token返回给客户端
	 * @param request
	 * @return token 服务端生成的token
	 */
	public String generateToken(HttpServletRequest request) {
		String token = System.currentTimeMillis() + new Random().nextInt() + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(token.getBytes());
			// base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			token = encoder.encode(md5);
			request.getSession().setAttribute("token", token);
			
			return token;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 校验表单token是否与服务器的token一致
	 * @param request
	 * @return
	 */
	public static boolean isTokenvalid(HttpServletRequest request) {  
		 String client_token = request.getParameter("token");  //获取客户机带来的表单号  
		 String server_token = (String) request.getSession().getAttribute("token"); //获取服务器端的表单号  
         //判断服务器端有没有产生表单号，即服务器有没有为用户创建表单  
		 if(server_token == null){   
            return false;  
		 }  
          
		 //判断客户端有没有带表单号过来  
		 if(client_token == null){  
            return false;  
		 }  
          
         //判断客户端带来的表单号与服务器端存储的表单号是否一致，一致则通过验证，否则禁止提交表单  
		 return client_token.equals(server_token);  
    }
	
	/**
     * 清除服务器中的session信息
     * @param request
     * @return
     */
    public static void resetToken(HttpServletRequest request) { 
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        
        session.removeAttribute("token");
    }

}
