package com.kingleadsw.betterlive.controller.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.AdminManager;
import com.kingleadsw.betterlive.biz.RoleManager;
import com.kingleadsw.betterlive.biz.UserRoleManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.RoleVo;
import com.kingleadsw.betterlive.vo.UserRoleVo;

@Controller
@RequestMapping("/admin/admin")
public class AdminController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	
	@Autowired
	private AdminManager adminManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private UserRoleManager userRoleManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/admin/list_admin");
		return mv;
	}
	
	/**
	 * 分页查询管理员信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryAdminAllJson")
	@ResponseBody
	public void queryAdminAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<AdminVo> admins = this.adminManager.queryListPage(pd);
		if (admins != null) {
			this.outEasyUIDataToJson(pd, admins, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<AdminVo>(),
					response);
		}
	}
	
	/**
	 * 新增管理员页面
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddAdmin")
	public ModelAndView toAddAdmin(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		PageData pd = this.getPageData();
		List<RoleVo> roles = this.roleManager.queryList(pd);
		ModelAndView mv = new ModelAndView(
				"admin/admin/add_admin");
		model.addAttribute("roles", roles);
		return mv;
	}
	
	
	@RequestMapping(value = "/addAdmin")
	@ResponseBody
	public Map<String, Object> addAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String username = multipartRequest.getParameter("username");
			String loginname = multipartRequest.getParameter("loginname");
			String password = multipartRequest.getParameter("password");
			String mobile = multipartRequest.getParameter("mobile");
			
			String roleId = multipartRequest.getParameter("roleId");
			AdminVo admin=(AdminVo)getSessionObject(WebConstant.SESSION_ADMIN_USER);
			AdminVo adminVo = new AdminVo();
			if (null!=multipartRequest.getParameter("sex") && !"".equals(multipartRequest.getParameter("sex"))) {
				int sex = Integer.parseInt(multipartRequest.getParameter("sex"));
				adminVo.setSex(sex);
			}
			adminVo.setUsername(username);
			adminVo.setLoginname(loginname);
			adminVo.setPassword(password);
			adminVo.setMobile(mobile);
			adminVo.setCreateBy(admin.getStaffId()); //获取当前系统登录用户id
			adminVo.setCreateTime(new Date());
			adminVo.setRoleId(1);

			int	iret = adminManager.insert(adminVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}else{
				UserRoleVo userRoleVo = new UserRoleVo();
				userRoleVo.setRoleId(Integer.parseInt(roleId));
				userRoleVo.setUserId(adminVo.getStaffId());
				userRoleManager.insert(userRoleVo);
			}
		} catch (Exception e) {
			logger.error("/admin/admin/addAdmin------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
	/**
	 * 批量删除管理员
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delAdmin")
	@ResponseBody
	public Map<String, Object> delAdmin(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String staffIdArray = request.getParameter("staffIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(staffIdArray)) {
				String[] staffIds = staffIdArray.split(",");
				for (String staffId : staffIds) {
					
					iret = adminManager.deleteByPrimaryKey(Integer.parseInt(staffId)); 
							
				}
			}
			
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/label/delLabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "删除成功");
		return map;
	}
	
	/**
	 * 跳转更新管理员界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdateAdmin")
	public ModelAndView toUpdateAdmin(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/admin/update_admin");
		PageData pd = this.getPageData();
		Integer staffId = pd.getInteger("staffId");
		PageData pd1 = new PageData();
		pd1.put("staffId", staffId);
		AdminVo vo = adminManager.queryOne(pd1);
		List<RoleVo> roles = this.roleManager.queryList(pd);
		model.addAttribute("admin", vo);
		model.addAttribute("roles", roles);
		return mv;
	}
	
	@RequestMapping(value = "/editAdmin")
	@ResponseBody
	public Map<String, Object> editAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String staffId = multipartRequest.getParameter("staffId");
			String username = multipartRequest.getParameter("username");
			String loginname = multipartRequest.getParameter("loginname");
			String password = multipartRequest.getParameter("password");
			String mobile = multipartRequest.getParameter("mobile");
			String roleId = multipartRequest.getParameter("roleId");
			
			AdminVo admin=(AdminVo)getSessionObject(WebConstant.SESSION_ADMIN_USER);
			
			AdminVo adminVo = new AdminVo();
			adminVo.setStaffId(Integer.parseInt(staffId));
			adminVo.setUsername(username);
			adminVo.setLoginname(loginname);
			adminVo.setPassword(password);
			adminVo.setMobile(mobile);
			if (null!=multipartRequest.getParameter("sex") && !"".equals(multipartRequest.getParameter("sex"))) {
				int sex = Integer.parseInt(multipartRequest.getParameter("sex"));
				adminVo.setSex(sex);
			}
			adminVo.setRoleId(Integer.parseInt(roleId));
			adminVo.setUpdateBy(admin.getStaffId()); //获取当前系统登录用户id
			adminVo.setUpdateTime(new Date());
			
			int iret = adminManager.updateByPrimaryKey(adminVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "修改失败");
				return map;
			}else{
				UserRoleVo userRoleVo = new UserRoleVo();
				userRoleVo.setRoleId(Integer.parseInt(roleId));
				userRoleVo.setUserId(adminVo.getStaffId());
				int res = userRoleManager.updateByPrimaryKey(userRoleVo);
				if (res <= 0) {
					map.put("result", "fail");
					map.put("msg", "修改失败");
					return map;
				}
			}
		} catch (Exception e) {
			logger.error("/admin/admin/editAdmin------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
	}
	
}
