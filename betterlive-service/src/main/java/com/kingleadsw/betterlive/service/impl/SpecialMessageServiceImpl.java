package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialMessageMapper;
import com.kingleadsw.betterlive.model.SpecialMessage;
import com.kingleadsw.betterlive.service.SpecialMessageService;

@Service
public class SpecialMessageServiceImpl extends BaseServiceImpl<SpecialMessage> implements SpecialMessageService{
	
	@Autowired
	private SpecialMessageMapper specialmessagemapper;
	
	@Override
	protected BaseMapper<SpecialMessage> getBaseMapper() {
		return specialmessagemapper;
	}

	@Override
	public List<SpecialMessage> queryMessageListPage(PageData pd) {
		return specialmessagemapper.queryMessageListPage(pd);
	}
	
}
