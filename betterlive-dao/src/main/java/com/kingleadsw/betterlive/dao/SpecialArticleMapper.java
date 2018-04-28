package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialArticle;

public interface SpecialArticleMapper extends BaseMapper<SpecialArticle> {

	List<SpecialArticle> querySpecialArticleListPage(PageData pd);

	int hideSpecialHomeFlag(PageData hideSpeParam);

	List<SpecialArticle> queryCircleArticleListPage(PageData pd);

	SpecialArticle queryCircleOne(PageData pd);
	
	int auditArticle(SpecialArticle pd);
	
	/**
     * 根据审核状态统计数量
     * @param pd
     * @return
     */
    int querySpecialArticleCount(PageData pd);
    /**
     * 发现好友要显示最近三篇文章
     * @param
     * @return
     * @author zhangjing 2018年1月31日 上午11:30:36
     */
    List<SpecialArticle>  queryTopThreeArticle(PageData pd);

}
