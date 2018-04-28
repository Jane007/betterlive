package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ArticlePeriodical;
import com.kingleadsw.betterlive.vo.ArticlePeriodicalVo;

public interface ArticlePeriodicalManager extends BaseManager<ArticlePeriodicalVo, ArticlePeriodical>{

	/**
	 * 查询已被使用的期刊
	 * @param pd
	 * @return
	 */
	List<ArticlePeriodicalVo> queryByArticleListPage(PageData pd);

}
