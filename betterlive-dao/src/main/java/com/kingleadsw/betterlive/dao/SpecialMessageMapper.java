package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialMessage;

public interface SpecialMessageMapper extends BaseMapper<SpecialMessage> {

	List<SpecialMessage> queryMessageListPage(PageData pd);
}
