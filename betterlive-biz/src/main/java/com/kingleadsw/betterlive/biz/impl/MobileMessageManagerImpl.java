package com.kingleadsw.betterlive.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.MobileMessageManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.MobileMessage;
import com.kingleadsw.betterlive.service.MobileMessageService;
import com.kingleadsw.betterlive.vo.MobileMessageVo;

/**
 * 手机短信消息 实际交互实现层
 */
@Component
@Transactional
public class MobileMessageManagerImpl extends BaseManagerImpl<MobileMessageVo,MobileMessage> implements MobileMessageManager{
	@Autowired
	private MobileMessageService messageService;

	@Override
	protected BaseService<MobileMessage> getService() {
		return messageService;
	}

	
	
}
