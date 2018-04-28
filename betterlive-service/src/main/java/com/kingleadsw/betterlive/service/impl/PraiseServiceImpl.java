package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PraiseMapper;
import com.kingleadsw.betterlive.model.Praise;
import com.kingleadsw.betterlive.service.PraiseService;

@Service("/praiseServiceImpl")
public class PraiseServiceImpl extends BaseServiceImpl<Praise>  implements PraiseService {

	@Autowired
	private PraiseMapper praiseMapper;
	@Override
	protected BaseMapper<Praise> getBaseMapper() {
		return praiseMapper;
	}
	@Override
	public int insertPraise(Praise praise) {
		return praiseMapper.insertPraise(praise);
	}

}
