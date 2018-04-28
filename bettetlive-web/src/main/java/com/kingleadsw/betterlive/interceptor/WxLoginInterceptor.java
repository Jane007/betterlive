package com.kingleadsw.betterlive.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.util.IpAndMac;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.service.ShoppingCartService;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * 微信拦截器，拦截微信页面请求</br>
 * 在请求前判断session中有没有用户信息，若没有，则进行静默授权并缓存用户信息
 * @author ltp
 *
 */
public class WxLoginInterceptor implements HandlerInterceptor {
	
	private String[] exceptFilterUrls;// 利用正则映射到需要拦截的路径

	private  static Logger logger =Logger.getLogger(WxLoginInterceptor.class);
	
	public String[] getExceptFilterUrls() {
		return exceptFilterUrls;
	}

	public void setExceptFilterUrls(String[] exceptFilterUrls) {
		this.exceptFilterUrls = exceptFilterUrls;
	}

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		String params = request.getQueryString();
		String uri = request.getRequestURI();
		
		String requestWith = request.getHeader("x-requested-with");
		//判断是否ajax请求
		if(StringUtil.isNotNull(requestWith) && requestWith.equalsIgnoreCase("XMLHttpRequest")){
			return true;
		}
		
		String ip = IpAndMac.getIpAddr(request);
		
		logger.info("请求ip：" + ip);
		
		CustomerVo customer = Constants.getCustomer(request);
		
		if(customer == null){//登陆不走授权流程
			customer =(CustomerVo) request.getSession().getAttribute("yk_customer");
		}
		if(customer != null){
			return true;
		}
		
		if (params != null) {
			url += "?"+request.getQueryString();
			if(StringUtil.isNotNull(request.getParameter("source"))){
				request.getSession().setAttribute("source", request.getParameter("source"));
			}
		}
		
		logger.info("请求url="+url);
		
		// 用户登录开关，在本地开发环境可关闭此开关即：false
		if (!"true".equals(WebConstant.ISMEMBER)) {
			return true;
		}
		
		String userAgent = request.getHeader("user-agent").toLowerCase();  
		if (userAgent.indexOf("micromessenger") <= 0) {// 不是微信浏览器  
    		return true;
		}  
		
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		
		for (String except : exceptFilterUrls) {
			if (uri.indexOf(except) != -1) {
				if (null == code || "".equals(code)) {
					String authUrl = WxUtil.getAuthUrl(url.toString());
					response.sendRedirect(authUrl);
					return false;
				} else if (null != state && "userinfo".equals(state)){
					// 用户确认授权方式请求
    				WxUtil.authLogin(request);
    				return true;
				}
			}
		}
		
		//code为空：无需网页授权方式访问直接跳转至页面；code不为空，网页授权方式访问。
    	if(null == code || "".equals(code)){
				logger.info("拦截到微信code和用户信息为空，转发到静默授权页面");
				String authUrl = WxUtil.getAuthUrlInvoke(url.toString());
				response.sendRedirect(authUrl);
				return false;
    	} else{
    		// code不为空，网页授权方式访问，且已经获取到code
			if ("basic".equals(state)) {
				WxUtil.slientLogin(request);
			} else if ("userinfo".equals(state)) {
				// 用户确认授权方式请求
				WxUtil.authLogin(request);
			} else {
				WxUtil.slientLogin(request);
			}
    	}
		return true;
	}	
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		CustomerVo customer = Constants.getCustomer(request);
		int cartCnt = 0;
		if (customer != null && customer.getCustomer_id() != null) {
			cartCnt = shoppingCartService.queryShoppingCartCnt(customer.getCustomer_id());
		}
		if(modelAndView != null && cartCnt!=0){
			modelAndView.addObject("cartCnt", cartCnt);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
