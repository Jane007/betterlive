package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Admin;



/**
 * 管理员 dao层
 * 2017-03-07 by chen
 */
public interface AdminMapper extends BaseMapper<Admin>{


	/**
	 * <!-- 校验用户登陆信息 -->
	  * Admin
	 */
	Admin checkLoginInfo(PageData pd);
	
	/**
	 * 后台获取列表
	 * @param pd
	 * @return
	 */
	List<Admin> getListPage(PageData pd);
	
	/**
	 * 根据登录账号获取admin
	 * @param mobile
	 * @return
	 */
	Admin queryAdminByLoginname(String loginname);
	
	/**
	  * 根据用户名称查询是否是平台运营人员
	  * Admin
	 */
	Admin findplatformYunyingByPageData(PageData pd);
	
	/**
	 * 根据姓名获取admin
	 * @param mobile
	 * @return
	 */
	Admin queryAdminByUsernamee(String username);
}