package com.kingleadsw.betterlive.controller.web.menu;

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

import com.kingleadsw.betterlive.biz.MenuManager;
import com.kingleadsw.betterlive.biz.RoleManager;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.MenuVo;

@Controller
@RequestMapping("/admin/menu")
public class MenuController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private MenuManager menuManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest req,
			HttpServletResponse resp,Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/menu/list_menu");
		List<MenuVo> parentMenus = menuManager.findParentMenus(new PageData());
		mv.addObject("parentMenus", parentMenus);
		return mv;
	}
	
	/**
	 * 分页查询角色信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryMenuAllJson")
	@ResponseBody
	public void queryMenuAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<MenuVo> menus = this.menuManager.queryListPage(pd);
		if (menus != null) {
			this.outEasyUIDataToJson(pd, menus, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<MenuVo>(),
					response);
		}
	}
	
	/**
	 * 根据角色Id查询菜单树
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryMenuByRoleId")
	@ResponseBody
	public void queryMenuByRoleId(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		String roleId = pd.getString("roleId");
		pd.put("status", 1);
		List<MenuVo> menus = this.menuManager.queryListPage(pd);
		List<MenuVo> menusList = menuManager.queryPowerMenuList(Integer.parseInt(roleId));
		if (menus != null) {
			//this.outEasyUIDataToJson(pd, menus, response);
		    List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>(); 
		      
		    for(int i = 0; i < menus.size(); i++){ 
		      HashMap<String,Object> hm = new HashMap<String,Object>();  //最外层，父节点       
		      hm.put("id",menus.get(i).getMenuId());//id属性 ，数据传递  
		      hm.put("name",menus.get(i).getMenuName()); //name属性，显示节点名称  
		      hm.put("pId", menus.get(i).getParentId()); 
		      if (null!=menusList) {
		    	  for (int j = 0; j < menusList.size(); j++) {
						if (hm.get("id").equals(menusList.get(j).getMenuId())) {
							hm.put("checked", true);
						}
					}
			}
		      list.add(hm); 
		    } 
		      
		    try {
				this.outEasyUIDataToJson(pd, list,
						response);
			} catch (Exception e) {
				logger.error("/admin/menu/queryMenuByRoleId", e);
			} 
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<MenuVo>(),
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
	@RequestMapping(value = "/toAddMenu")
	public ModelAndView toAddMenu(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/menu/add_menu");
		List<MenuVo> parentMenus = menuManager.findParentMenus(new PageData());
		model.addAttribute("parentMenus", parentMenus);
		return mv;
	}
	
	
	@RequestMapping(value = "/addMenu")
	@ResponseBody
	public Map<String, Object> addMenu(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String parentId = multipartRequest.getParameter("parentId");
			String menuName = multipartRequest.getParameter("menuName");
			String menuType = multipartRequest.getParameter("menuType");
			String menuUrl = multipartRequest.getParameter("menuUrl");
			String menuOrd = multipartRequest.getParameter("menuOrd");
			String status = multipartRequest.getParameter("status");
			
			MenuVo menuVo = new MenuVo();
			if (null != parentId && parentId != "" && parentId.length() > 0) {
				menuVo.setParentId(Integer.parseInt(parentId));
			}
			
			menuVo.setMenuName(menuName);
			menuVo.setMenuType(menuType);
			menuVo.setMenuUrl(menuUrl);
			menuVo.setMenuOrd(Integer.parseInt(menuOrd));
			menuVo.setStatus(status);

			int	iret = menuManager.insert(menuVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/admin/addAdmin------error", e);
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
	@RequestMapping(value="/delMenu")
	@ResponseBody
	public Map<String, Object> delMenu(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String menuIdArray = request.getParameter("menuIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(menuIdArray)) {
				String[] menuIds = menuIdArray.split(",");
				for (String menuId : menuIds) {
					
					iret = menuManager.deleteByPrimaryKey(Integer.parseInt(menuId)); 
							
				}
			}
			
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/Menu/delMenu------error", e);
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
	@RequestMapping(value="/toUpdateMenu")
	public ModelAndView toUpdateMenu(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/menu/update_menu");
		PageData pd = this.getPageData();
		Integer menuId = pd.getInteger("menuId");
		MenuVo vo = menuManager.selectByPrimaryKey(menuId);
		List<MenuVo> parentMenus = menuManager.findParentMenus(new PageData());
		model.addAttribute("parentMenus", parentMenus);
		model.addAttribute("menu", vo);
		return mv;
	}
	
	@RequestMapping(value = "/editMenu")
	@ResponseBody
	public Map<String, Object> editMenu(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String menuId = multipartRequest.getParameter("menuId");
			String parentId = multipartRequest.getParameter("parentId");
			String menuName = multipartRequest.getParameter("menuName");
			String menuType = multipartRequest.getParameter("menuType");
			String menuUrl = multipartRequest.getParameter("menuUrl");
			String menuOrd = multipartRequest.getParameter("menuOrd");
			String status = multipartRequest.getParameter("status");
			
			MenuVo menuVo = new MenuVo();
			menuVo.setMenuId(Integer.parseInt(menuId));
			
			if (null != parentId && parentId != "" && parentId.length() > 0) {
				menuVo.setParentId(Integer.parseInt(parentId));
			}
			menuVo.setMenuName(menuName);
			menuVo.setMenuType(menuType);
			menuVo.setMenuUrl(menuUrl);
			menuVo.setMenuOrd(Integer.parseInt(menuOrd));
			menuVo.setStatus(status);
			
			int iret = menuManager.updateByPrimaryKey(menuVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "修改失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/menu/editMenu------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
	}
	
}
