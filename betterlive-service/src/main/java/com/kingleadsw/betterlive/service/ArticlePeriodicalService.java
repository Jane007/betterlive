package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ArticlePeriodical;

public interface ArticlePeriodicalService extends BaseService<ArticlePeriodical>{

	List<ArticlePeriodical> queryByArticleListPage(PageData pd);

}
