package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Customer;

/**
 * author ptz
 * date 2017-1-18 15 40
 **/
public interface CustomerService extends BaseService<Customer>{
	
	/**
	 * 根据条件分页查询用户
	 * @param pd
	 * @return
	 */
	List<Customer> getListPage(PageData pd);
	
	/**
	 * 根据条件查询全部的用户
	 * @param pd
	 * @return
	 */
	List<Customer> findListCustomer(PageData pd);       	
	
	/**
	 * 根据条件查询全部的用户 （有手机号的）
	 * @param pd
	 * @return
	 */
	List<Customer> findListCustomerByObject(PageData pd);       
	
	/**
	 * 根据条件查询单个用户信息
	 * @param pd
	 * @return
	 */
	Customer findCustomer(PageData pd);         
	
	/**
	 * 微信登陆
	 * @param pd
	 * @return
	 */

	
	/**
	 * 根据会员ID更新会员信息
	 * @param pd
	 * @return
	 */
	int updateCustoemrById(PageData pd);               
	
	/**
	 * 根据用户ID删除用户  （只用于 合并 微信与app端的用户） 
	 * @param customerId
	 * @return
	 */
	int delCustomerByCid(String customerId);        
	
	/**
	 * 根据token查询用户信息
	 * @param token token字符串
	 * @return Customer 用户对象
	 */
	Customer queryCustomerByToken(String token);
	
	Customer selectByMobile(String mobile);
	
	
	
}