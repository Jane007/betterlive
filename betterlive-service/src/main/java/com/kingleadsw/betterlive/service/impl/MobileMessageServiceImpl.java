package com.kingleadsw.betterlive.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.MobileMessageMapper;
import com.kingleadsw.betterlive.model.MobileMessage;
import com.kingleadsw.betterlive.service.MobileMessageService;



/**
 * 手机短信消息  service 实现层
 */
@Service
public class MobileMessageServiceImpl extends BaseServiceImpl<MobileMessage> implements MobileMessageService{
	@Autowired
	private  MobileMessageMapper mobileMessageMapper;

	/**
	 * @return
	 */
	@Override
	protected BaseMapper<MobileMessage> getBaseMapper() {
		return mobileMessageMapper;
	}

}
