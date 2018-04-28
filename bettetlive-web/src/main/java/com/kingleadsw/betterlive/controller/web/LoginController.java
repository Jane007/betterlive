/**
 * @author ptz
 * 2016年11月18日 上午10:45:20
 */
package com.kingleadsw.betterlive.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.AdminManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MenuManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.MenuVo;


/**
 * @author ptz
 * 2016年11月18日 上午10:45:20
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends AbstractWebController{

	protected Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private MenuManager menuManager;
	
	@Autowired
    private AdminManager adminManager;
	
	
	@Autowired
    private CustomerManager customerManager;
	
	
    @Autowired
    private RedisService redisService;
	
	@RequestMapping(value="/tologin")
	public String toLogin(HttpServletRequest request){
		PageData pd = this.getPageData();
		if (pd.get("pageView") == null) {
			PageView pageView = new PageView(10, 1);
			pd.put("pageView", pageView);
		}
		
		return "admin/admin_login";
	}
	
	@RequestMapping("/checklogin")
	@ResponseBody
	public Map<String,Object> checkLogin(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		logger.info("后台管理员登陆开始");
		String loginname=request.getParameter("loginname");
		String password=request.getParameter("password");
		String code=request.getParameter("code");

		AdminVo admin=adminManager.queryAdminByLoginname(loginname);
		
		if(admin == null){
			result.put("result", "failure");
			result.put("msg", "用户名不存在");
			return result;
		}else{
			if(!password.equals(admin.getPassword())){
				result.put("result", "failure");
				result.put("msg", "密码不正确");
				return result;
			}
		}
		
		
		if(StringUtil.isNull(loginname)){
			result.put("result", "failure");
			result.put("msg", "用户名不能为空");
			return result;
		}
		
		if(StringUtil.isNull(password)){
			result.put("result", "failure");
			result.put("msg", "密码不能为空");
			return result;
		}
		
		if(StringUtil.isNull(code)){
			result.put("result", "failure");
			result.put("msg", "验证码不能为空");
			return result;
		}
		
		String safecode=(String) request.getSession().getAttribute("safecode");
		if(!code.equalsIgnoreCase(safecode)){
			result.put("result", "failure");
			result.put("msg", "验证码不正确");
			return result;
		}
	
		result.put("result", "success");
		result.put("msg", "登录成功");
		HttpSession session=request.getSession();
		
		session.setAttribute(WebConstant.SESSION_ADMIN_USER, admin);
		
		Constants.setAdmin(request, admin);
		logger.info("后台管理员登陆结束");
		return result;
	}
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model){
		
		AdminVo admin=(AdminVo)getSessionObject(WebConstant.SESSION_ADMIN_USER);
		
		List<MenuVo> list = menuManager.queryPowerMenuList(admin.getRoleId());
		List<MenuVo> childList = new ArrayList<MenuVo>();
		
		if(null == list || list.size() <= 0){
			return "/admin/admin_login";
		}
		
		for(int i = list.size() -1;i > 0; i --){
			if(null != list.get(i).getParentId()){
				childList.add(list.get(i));
				list.remove(i);
			}
		}
		
		model.addAttribute("list", list);
		model.addAttribute("childList", childList);
		
		model.addAttribute("username", admin.getUsername());
		return "admin/main";
	}
	
	@RequestMapping(value="/dologout")
	public String toLogout(HttpServletRequest request){
		//销毁session对象
		request.getSession().invalidate();
		return "admin/admin_login";
	}
	
	@RequestMapping(value="/sysconfig")
	public String test(HttpServletRequest request){
		return "admin/sysconfig";
	}
}
