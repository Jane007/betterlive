package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.AdminManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.Md5Utils;
import com.kingleadsw.betterlive.model.Admin;
import com.kingleadsw.betterlive.model.UserRole;
import com.kingleadsw.betterlive.service.AdminService;
import com.kingleadsw.betterlive.service.UserRoleService;
import com.kingleadsw.betterlive.vo.AdminVo;

import java.util.Date;
import java.util.List;


/**
 * 2017-03-07 by chen
 */
@Component
@Transactional
public class AdminManagerImpl extends BaseManagerImpl<AdminVo,Admin> implements AdminManager{
    @Autowired
     private AdminService adminService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Override
    protected BaseService<Admin> getService() {        return adminService;    }
	

	/**
	 * <!-- 校验用户登陆信息 -->
	  * AdminVo
	 */
//	@Override
//	public Map<String, Object> checkLoginInfo(PageData pd) {
//		return adminService.checkLoginInfo(pd);
//	}
	
    
	@Override
	public List<AdminVo> getListPage(PageData pd) {
		// TODO Auto-generated method stub
		return po2voer.transfer(AdminVo.class, adminService.getListPage(pd));
	}
	@Override
	public int addRole(PageData pd) {
		
		int rst = 0;
		
		int pass = (int)((Math.random()*9+1)*100000);
		Admin vo = new Admin();
		vo.setUsername(pd.getString("name"));
		vo.setLoginname(pd.getString("mobile"));
		vo.setPassword(Md5Utils.getMd5(String.valueOf(pass)));
		vo.setMobile(pd.getString("mobile"));
		vo.setCreateTime(new Date());
		rst = adminService.insert(vo);
		
		UserRole ur = new UserRole();
		ur.setRoleId(pd.getInteger("roleId"));
		ur.setUserId(vo.getStaffId());
		userRoleService.insert(ur);
		
		if(rst > 0){
			return pass;
		}
		return 0;
	}

	@Override
	public AdminVo queryAdminByLoginname(String loginname) {
		return po2voer.transfer(new AdminVo(), adminService.queryAdminByLoginname(loginname));
	}


	/**
	  * 根据用户名称查询是否是平台运营人员
	  * Admin
	 */
	@Override
	public AdminVo findplatformYunyingByPageData(PageData pd) {
		return po2voer.transfer(new AdminVo(), adminService.findplatformYunyingByPageData(pd));
	}


	@Override
	public AdminVo queryAdminByUsernamee(String username) {
		// TODO Auto-generated method stub
		return po2voer.transfer(new AdminVo(), adminService.queryAdminByUsernamee(username));
	}


	@Override
	public int delAdminAndRole(int staffId) {
		// TODO Auto-generated method stub
		int rst = 0;
		rst = adminService.deleteByPrimaryKey(staffId);
		userRoleService.deleteByUserId(staffId);
		return rst;
	}
	
}
