package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Admin;
import com.kingleadsw.betterlive.vo.AdminVo;



/**
 * 管理员
 * 2017-03-07 by chen
 */
public interface AdminManager extends BaseManager<AdminVo,Admin>{
	
	/**
	 * <!-- 校验用户登陆信息 -->
	  * AdminVo
	 */
//	AdminVo checkLoginInfo(PageData pd);
	
	/**
	 * 后台获取列表
	 * @param pd
	 * @return
	 */
	List<AdminVo> getListPage(PageData pd);
	
	/**
	 * 添加角色
	 * @param pd
	 * @return
	 */
	int addRole(PageData pd);
	
	/**
	 * 根据登录账号获取admin
	 * @param mobile
	 * @return
	 */
	AdminVo queryAdminByLoginname(String loginname);
	
	/**
	  * 根据用户名称查询是否是平台运营人员
	  * Admin
	 */
	AdminVo findplatformYunyingByPageData(PageData pd);
	
	/**
	 * 根据姓名获取admin
	 * @param mobile
	 * @return
	 */
	AdminVo queryAdminByUsernamee(String username);
	
	/**
	 * 删除用户和角色
	 * @param staffId
	 * @return
	 */
	int delAdminAndRole(int staffId);
}