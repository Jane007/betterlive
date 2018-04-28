package com.kingleadsw.betterlive.dao;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.Share;

public interface ShareMapper extends BaseMapper<Share>{
	public int insertShare(Share share);
}
