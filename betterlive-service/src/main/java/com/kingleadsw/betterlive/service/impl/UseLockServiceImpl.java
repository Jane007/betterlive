package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.UseLockMapper;
import com.kingleadsw.betterlive.model.UseLock;
import com.kingleadsw.betterlive.service.UseLockService;


/**
 * 锁定礼品卡和优惠券  service实现层
 * 2017-03-21 by chen
 */
@Service
public class UseLockServiceImpl extends BaseServiceImpl<UseLock>  implements UseLockService{
	@Autowired
	private UseLockMapper useLockMapper;

	@Override
	public int insertUseLock(UseLock useLock) {
		return useLockMapper.insertUseLock(useLock);
	}

	@Override
	public int editUseLockById(PageData pd) {
		return useLockMapper.editUseLockById(pd);
	}

	@Override
	public UseLock findUseLock(PageData pd) {
		return useLockMapper.findUseLock(pd);
	}

	@Override
	public List<UseLock> findListUseLock(PageData pd) {
		return useLockMapper.findListUseLock(pd);
	}

	@Override
	protected BaseMapper<UseLock> getBaseMapper() {
		return useLockMapper;
	}
	
	/**
	 *  查询用户锁定的礼品卡金额
	 */
	@Override
	public float findCustomerLockMoney(String customerId) {
		return useLockMapper.findCustomerLockMoney(customerId);
	}

	@Override
	public int modifyCustomerUseLockBycId(PageData pd) {
		return useLockMapper.modifyCustomerUseLockBycId(pd);
	}

	

}
