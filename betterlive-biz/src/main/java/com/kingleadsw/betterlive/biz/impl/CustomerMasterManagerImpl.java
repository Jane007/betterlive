package com.kingleadsw.betterlive.biz.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerMasterManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.CustomerMaster;
import com.kingleadsw.betterlive.service.CustomerMasterService;
import com.kingleadsw.betterlive.vo.CustomerMasterVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * 美食达人 实际交互实现层
 */
@Component
@Transactional
public class CustomerMasterManagerImpl extends BaseManagerImpl<CustomerMasterVo, CustomerMaster> implements CustomerMasterManager {
	@Autowired
	private CustomerMasterService customerMasterService;

	@Override
	protected BaseService<CustomerMaster> getService() {
		return customerMasterService;
	}

	@Override
	public List<CustomerMasterVo> queryHotList(PageData pd) {
		List<CustomerMaster> list = customerMasterService.queryHotList(pd);
		if(list == null || list.size() <= 0){
			return new ArrayList<CustomerMasterVo>();
		}
		List<CustomerMasterVo> resls = new ArrayList<CustomerMasterVo>();
		for (int i = 0; i < list.size(); i++) {
			CustomerMaster customerMaster = list.get(i);
			CustomerMasterVo custMasterVo = po2voer.transfer(new CustomerMasterVo(), customerMaster);
			
			if(customerMaster.getCustomer() != null){
				Customer customer = customerMaster.getCustomer();
				CustomerVo customerVo=new CustomerVo();
				customerVo.setCustomer_id(customer.getCustomer_id());
				customerVo.setNickname(customer.getNickname());
				customerVo.setMobile(customer.getMobile());
				customerVo.setHead_url(customer.getHead_url());
				custMasterVo.setCustomerVo(customerVo);
			}
			resls.add(custMasterVo);
		}
		return resls;
	}

	
}
