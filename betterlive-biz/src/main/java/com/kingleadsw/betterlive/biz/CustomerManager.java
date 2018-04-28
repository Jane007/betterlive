package com.kingleadsw.betterlive.biz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.vo.CustomerVo;


public interface CustomerManager extends BaseManager<CustomerVo,Customer>{
	 
	
	/**
	 * 根据条件分页查询用户
	 * @param pd
	 * @return
	 */
	List<CustomerVo> getListPage(PageData pd);
	
	/**
	 * 根据条件查询全部的用户
	 * @param pd
	 * @return
	 */
	List<CustomerVo> findListCustomer(PageData pd);       	
	
	/**
	 * 根据条件查询全部的用户 （有手机号的）
	 * @param pd
	 * @return
	 */
	List<CustomerVo> findListCustomerByObject(PageData pd);       
	
	/**
	 * 根据条件查询单个用户信息
	 * @param pd
	 * @return
	 */
	CustomerVo findCustomer(PageData pd);      
	
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
	 * 根据用户ID合并用户信息  （只用于 合并 微信与app端的用户）
	 * @param  修改用户数据  pd    修改依赖的其他数据  pds
	 * @return
	 */
	int modifyCustomerByCid(PageData pd,PageData pds);
	
	/**
	 * 根据用户token获取用户信息 </br>
	 * 首先通过token在缓存中获取，获取到了直接返回</br>
	 * 如果redis中没有缓存此用户，则根据token到数据库查询用户并缓存到redis
	 * @param token 用户token
	 * @return 用户对象
	 */
	CustomerVo queryCustomerByToken(String token);
	
	CustomerVo selectByMobile(String mobile);
	
	/**
	 * 用户注册
	 * @param moblie  注册用户手机号码
	 * @param password  用户密码
	 * @return
	 */
	int insertCustomer(CustomerVo customerVo);
	
	/**
	 * 检查用户当前等级是否升级、领取奖励金币
	 * @param customerId
	 * @param integral
	 * @return
	 */
	Map<String, Object> upgradeCustomerLevel(int customerId, BigDecimal integral);
	
}
