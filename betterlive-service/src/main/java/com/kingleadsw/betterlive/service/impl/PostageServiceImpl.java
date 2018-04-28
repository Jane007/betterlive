package com.kingleadsw.betterlive.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PostageMapper;
import com.kingleadsw.betterlive.model.Postage;
import com.kingleadsw.betterlive.service.PostageService;

@Service
public class PostageServiceImpl extends BaseServiceImpl<Postage> implements PostageService {

	@Autowired
    private PostageMapper postageMapper;
	
	@Override
	protected BaseMapper<Postage> getBaseMapper() {
		return postageMapper;
	}


}
