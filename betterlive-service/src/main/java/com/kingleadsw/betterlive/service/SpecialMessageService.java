package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialMessage;

public interface SpecialMessageService extends BaseService<SpecialMessage> {
	List<SpecialMessage> queryMessageListPage(PageData pd);
}
