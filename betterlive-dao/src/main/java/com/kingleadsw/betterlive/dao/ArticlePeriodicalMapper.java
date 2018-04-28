package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ArticlePeriodical;

public interface ArticlePeriodicalMapper extends BaseMapper<ArticlePeriodical>{

	List<ArticlePeriodical> queryByArticleListPage(PageData pd);

}
