package com.kingleadsw.betterlive.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.dao.AdminMapper;
import com.kingleadsw.betterlive.model.Admin;
import com.kingleadsw.betterlive.service.AdminService;



/**
 * 管理员  service 实现层
 * 2017-03-07 by chen
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected BaseMapper<Admin> getBaseMapper() {
        return adminMapper;
    }

	/**
	 * <!-- 校验用户登陆信息 -->
	  * Admin
	 */
	@Override
	public Map<String,Object> checkLoginInfo(HttpServletRequest request) {
		Map<String,Object> result=new HashMap<String,Object>();
		String loginname=request.getParameter("loginname");
		String password=request.getParameter("password");
		String code=request.getParameter("code");
		Admin admin=this.queryAdminByLoginname(loginname);
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
		result.put("admin", admin);
		result.put("result", "success");
		result.put("msg", "登录成功");
		return result;
    }

	@Override
	public List<Admin> getListPage(PageData pd) {
		return adminMapper.getListPage(pd);
	}

	@Override
	public Admin queryAdminByLoginname(String loginname) {
		return adminMapper.queryAdminByLoginname(loginname);
	}

	/**
	  * 根据用户名称查询是否是平台运营人员
	  * Admin
	 */	
	@Override
	public Admin findplatformYunyingByPageData(PageData pd) {
		return adminMapper.findplatformYunyingByPageData(pd);
	}
	@Override
	public Admin queryAdminByUsernamee(String username) {
		// TODO Auto-generated method stub
		return adminMapper.queryAdminByUsernamee(username);
	}
	
}
