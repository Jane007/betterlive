package com.kingleadsw.betterlive.controller.web.role;

import java.util.ArrayList;
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

import com.kingleadsw.betterlive.biz.RoleManager;
import com.kingleadsw.betterlive.biz.RoleMenuManager;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.RoleMenuVo;
import com.kingleadsw.betterlive.vo.RoleVo;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private RoleMenuManager roleMenuManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/role/list_role");
		return mv;
	}
	
	/**
	 * 分页查询角色信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryRoleAllJson")
	@ResponseBody
	public void queryRoleAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<RoleVo> roles = this.roleManager.queryListPage(pd);
		if (roles != null) {
			this.outEasyUIDataToJson(pd, roles, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<RoleVo>(),
					response);
		}
	}
	
	/**
	 * 新增角色页面
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddRole")
	public ModelAndView toAddRole(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/role/add_role");
		return mv;
	}
	
	
	@RequestMapping(value = "/addRole")
	@ResponseBody
	public Map<String, Object> addRole(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String roleName = multipartRequest.getParameter("roleName");
			
			RoleVo roleVo = new RoleVo();
			roleVo.setRoleName(roleName);

			int	iret = roleManager.insert(roleVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}else{
				roleVo.setRoleType(roleVo.getRoleId());
				roleManager.updateByPrimaryKey(roleVo);
			}
		} catch (Exception e) {
			logger.error("/admin/admin/addRole------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
	/**
	 * 批量删除角色
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delRole")
	@ResponseBody
	public Map<String, Object> delRole(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String roleIdArray = request.getParameter("roleIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(roleIdArray)) {
				String[] roleIds = roleIdArray.split(",");
				for (String roleId : roleIds) {
					
					iret = roleManager.deleteByPrimaryKey(Integer.parseInt(roleId)); 
							
				}
			}
			
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/role/delRole------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "删除成功");
		return map;
	}
	
	/**
	 * 跳转更新角色界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdateRole")
	public ModelAndView toUpdateRole(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/role/update_role");
		PageData pd = this.getPageData();
		Integer roleId = pd.getInteger("roleId");
		RoleVo vo = roleManager.selectByPrimaryKey(roleId);
		model.addAttribute("role", vo);
		return mv;
	}
	
	@RequestMapping(value = "/editRole")
	@ResponseBody
	public Map<String, Object> editRole(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String roleId = multipartRequest.getParameter("roleId");
			String roleName = multipartRequest.getParameter("roleName");
			String roleType = multipartRequest.getParameter("roleType");
			
			
			RoleVo roleVo = new RoleVo();
			roleVo.setRoleId(Integer.parseInt(roleId));
			roleVo.setRoleName(roleName);
			roleVo.setRoleType(Integer.parseInt(roleType));
			
			int iret = roleManager.updateByPrimaryKey(roleVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "修改失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/admin/editRole------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
	}
	
	@RequestMapping(value = "/roleMenuChange")
	@ResponseBody
	public Map<String, Object> roleMenuChange(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String rid = request.getParameter("rid");
			String ids = request.getParameter("ids");
			PageData pd = new PageData();
			pd.put("roleId", rid);
			int	iret = roleMenuManager.delete(pd);
			if (StringUtils.isNotBlank(ids)) {
				String[] menuIds = ids.split(",");
				for (String menuId : menuIds) {
					RoleMenuVo roleMenu = new RoleMenuVo();
					roleMenu.setRoleId(Integer.parseInt(rid));
					roleMenu.setMenuId(Integer.parseInt(menuId));
					iret = roleMenuManager.insert(roleMenu); 
				}
			}
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/admin/roleMenuChange------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
}
