package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialMessageManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialMessage;
import com.kingleadsw.betterlive.service.SpecialMessageService;
import com.kingleadsw.betterlive.vo.SpecialMessageVo;

@Component
@Transactional
public class SpecialMessageManagerImpl extends BaseManagerImpl<SpecialMessageVo,SpecialMessage> implements SpecialMessageManager {
	@Autowired
	private SpecialMessageService sepcialmessageservice;
	
	@Override
	protected BaseService<SpecialMessage> getService() {
		return sepcialmessageservice;
	}

	@Override
	public List<SpecialMessageVo> queryMessageListPage(PageData pd) {
		List<SpecialMessage> specialmessage=sepcialmessageservice.queryMessageListPage(pd);
		List<SpecialMessageVo> specialmessageVo = po2voer.transfer(SpecialMessageVo.class,specialmessage);
		return specialmessageVo;
	}

	
}
