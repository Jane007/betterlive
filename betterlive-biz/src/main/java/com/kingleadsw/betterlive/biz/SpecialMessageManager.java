package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialMessage;
import com.kingleadsw.betterlive.vo.SpecialMessageVo;

public interface SpecialMessageManager extends BaseManager<SpecialMessageVo,SpecialMessage> {

	List<SpecialMessageVo> queryMessageListPage(PageData pd);
}
