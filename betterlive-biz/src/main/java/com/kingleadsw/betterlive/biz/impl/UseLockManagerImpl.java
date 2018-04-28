package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.UseLockManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.UseLock;
import com.kingleadsw.betterlive.service.UseLockService;
import com.kingleadsw.betterlive.vo.UseLockVo;


/**
 * 锁定礼品卡和优惠券  实际交互实现层
 * 2017-03-21 by chen
 */
@Component
@Transactional
public class UseLockManagerImpl extends BaseManagerImpl<UseLockVo,UseLock> implements UseLockManager{
	@Autowired
	private UseLockService useLockService;

	@Override
	public int insertUseLock(UseLockVo useLock) {
		UseLock uselock=vo2poer.transfer(new UseLock(),useLock);
		return useLockService.insertUseLock(uselock);
	}

	@Override
	public int editUseLockById(PageData pd) {
		return useLockService.editUseLockById(pd);
	}

	@Override
	public UseLockVo findUseLock(PageData pd) {
		return po2voer.transfer(new UseLockVo(),useLockService.findUseLock(pd));
	}

	@Override
	public List<UseLockVo> findListUseLock(PageData pd) {
		return po2voer.transfer(UseLockVo.class,useLockService.findListUseLock(pd));
	}

	@Override
	protected BaseService<UseLock> getService() {
		return useLockService;
	}

	@Override
	public float findCustomerLockMoney(String customerId) {
		return useLockService.findCustomerLockMoney(customerId);
	}

}
