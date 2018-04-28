package com.kingleadsw.betterlive.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.model.Freight;
import com.kingleadsw.betterlive.model.Postage;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.service.FreightService;
import com.kingleadsw.betterlive.service.PostageService;
import com.kingleadsw.betterlive.vo.FreightVo;
import com.kingleadsw.betterlive.vo.PostageVo;

@Component
@Transactional
public class PostageManagerImpl extends BaseManagerImpl<PostageVo, Postage> implements PostageManager {

	private Logger logger = Logger.getLogger(PostageManagerImpl.class);
	
	@Autowired
	private PostageService postageService;
	
	
	@Override
	protected BaseService<Postage> getService() {
		return postageService;
	}

}
