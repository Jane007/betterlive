package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Share;

public interface ShareService extends BaseService<Share>{

	public int insertShare(Share share);
}
