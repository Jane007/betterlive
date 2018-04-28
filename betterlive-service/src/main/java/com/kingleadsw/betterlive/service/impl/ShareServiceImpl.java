package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ShareMapper;
import com.kingleadsw.betterlive.model.Share;
import com.kingleadsw.betterlive.service.ShareService;

@Service
public class ShareServiceImpl extends BaseServiceImpl<Share> implements ShareService {
	@Autowired
	private ShareMapper shareMapper;
	
	@Override
	protected BaseMapper<Share> getBaseMapper() {
		return shareMapper;
	}
	
	@Override
	public int insertShare(Share share) {
		return shareMapper.insertShare(share);
	}
}
