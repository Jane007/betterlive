package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Praise;

public interface PraiseService extends BaseService<Praise> {

	int insertPraise(Praise praise);

}
