package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialArticle;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

public interface SpecialArticleManager extends BaseManager<SpecialArticleVo,SpecialArticle> {

	List<SpecialArticleVo> querySpecialArticleListPage(PageData pd);

	int hideSpecialHomeFlag(PageData hideSpeParam);

	List<SpecialArticleVo> queryCircleArticleListPage(PageData pd);

	SpecialArticleVo queryCircleOne(PageData pd);
	
	/**
     * 修改审核状态
     * @param pd
     * @return
     */
    int auditArticle(SpecialArticleVo pd);
    
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
    List<SpecialArticleVo>  queryTopThreeArticle(PageData pd);

}
