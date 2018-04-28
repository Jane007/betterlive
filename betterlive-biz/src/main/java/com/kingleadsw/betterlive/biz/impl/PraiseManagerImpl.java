package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PraiseManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Praise;
import com.kingleadsw.betterlive.service.PraiseService;
import com.kingleadsw.betterlive.vo.PraiseVo;

/**
 * 点赞 实际交互实现层
 */
@Component
@Transactional
public class PraiseManagerImpl extends BaseManagerImpl<PraiseVo,Praise> implements PraiseManager{
	@Autowired
	private PraiseService praiseService;

	@Override
	protected BaseService<Praise> getService() {
		return praiseService;
	}

	@Override
	public int insertPraise(PraiseVo praiseVo) {
		Praise praise = new Praise();
		praise.setCustomerId(praiseVo.getCustomerId());
		praise.setObjId(praiseVo.getObjId());
		praise.setPraiseType(praiseVo.getPraiseType());
		praise.setShareCustomerId(praiseVo.getShareCustomerId());
		int count = praiseService.insertPraise(praise);
		praiseVo.setPraiseId(praise.getPraiseId());
		return count;
	}

	
}
