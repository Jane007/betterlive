package com.kingleadsw.betterlive.controller.web.admin;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.AdminManager;
import com.kingleadsw.betterlive.biz.RoleManager;
import com.kingleadsw.betterlive.biz.UserRoleManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.AdminVo;

@Controller
@RequestMapping("/admin/personal")
public class PersonalInformationController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(LoginController.class);

	
	@Autowired
	private AdminManager adminManager;
	
	@Autowired
	private RoleManager roleManager;
	
	
	@Autowired
	private UserRoleManager userRoleManager;
	
	@RequestMapping(value = "/personalList")
	public ModelAndView personalList(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("admin/admin/personal_admin");
		return mv;
	}	
	
	
	@RequestMapping(value = "/editAdmin")
	@ResponseBody
	public Map<String, Object> editAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			
			String username = multipartRequest.getParameter("username");
			String loginname = multipartRequest.getParameter("loginname");
			String password = multipartRequest.getParameter("password");
			String mobile = multipartRequest.getParameter("mobile");
			String headUrl = multipartRequest.getParameter("headUrl");
			
			MultipartFile touImg = multipartRequest.getFile("touxiang");
			String homeFilePath = "admintouxiang/";
			String insertPannerImg = "";
		
	    	if(touImg.getSize() > 0){
//	    		String fileName = touImg.getOriginalFilename(); // 原始文件名
//	 			String prefix = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
	    		insertPannerImg = ImageUpload.uploadFile(touImg, homeFilePath);
	    	}else{
	    		insertPannerImg=headUrl;
	    	}
			
			AdminVo admin=(AdminVo)getSessionObject(WebConstant.SESSION_ADMIN_USER);
			Integer staffId = admin.getStaffId();
		
			AdminVo adminVo = new AdminVo();
			
			adminVo.setStaffId(staffId);
			adminVo.setUsername(username);
			adminVo.setLoginname(loginname);
			adminVo.setPassword(password);
			adminVo.setMobile(mobile);
			if (null!=multipartRequest.getParameter("sex") && !"".equals(multipartRequest.getParameter("sex"))) {
				int sex = Integer.parseInt(multipartRequest.getParameter("sex"));
				adminVo.setSex(sex);
			}
		
			adminVo.setUpdateBy(admin.getStaffId()); //获取当前系统登录用户id
			adminVo.setHeadUrl(insertPannerImg);
			adminVo.setUpdateTime(new Date());
			
			int iret = adminManager.updateByPrimaryKey(adminVo);
			if (iret <= 0) {
				return CallBackConstant.FAILED.callback();
			}
			adminVo.setRoleId(admin.getRoleId());
			adminVo.setRoleName(admin.getRoleName());
			adminVo.setRoleType(admin.getRoleType());
			request.getSession().setAttribute(WebConstant.SESSION_ADMIN_USER,adminVo);
			return CallBackConstant.SUCCESS.callback(adminVo);
		} catch (Exception e) {
			logger.error("/admin/admin/editAdmin------error", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
}
