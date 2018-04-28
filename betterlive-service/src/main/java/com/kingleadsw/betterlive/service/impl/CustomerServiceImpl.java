package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerMapper;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.service.CustomerService;

/**
 * author ptz
 * date 2017-1-18 15 40
 **/
@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService{
    @Autowired
    private CustomerMapper customerMapper;

	@Override
	public List<Customer> getListPage(PageData pd) {
		 
		return customerMapper.getListPage(pd);
	}

	
	
	//根据条件查询全部的用户
	@Override
	public List<Customer> findListCustomer(PageData pd) {
		return customerMapper.findListCustomer(pd);
	}
	
	
	//根据条件查询单个用户信息
	@Override
	public Customer findCustomer(PageData pd) {
		return customerMapper.findCustomer(pd);
	}

	@Override
	public int updateCustoemrById(PageData pd) {
		return customerMapper.updateCustoemrById(pd);
	}
	
	@Override
	protected BaseMapper<Customer> getBaseMapper() {
		return customerMapper;
	}

	@Override
	public List<Customer> findListCustomerByObject(PageData pd) {
		return customerMapper.findListCustomerByObject(pd);
	}

	@Override
	public int delCustomerByCid(String customerId) {
		return customerMapper.delCustomerByCid(customerId);
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.CustomerService#queryCustomerByToken(java.lang.String)
	 */
	@Override
	public Customer queryCustomerByToken(String token) {
		return customerMapper.queryCustomerByToken(token);
	}



	



	@Override
	public Customer selectByMobile(String mobile) {
		// TODO Auto-generated method stub
		return customerMapper.selectByMobile( mobile);
	}



	
}