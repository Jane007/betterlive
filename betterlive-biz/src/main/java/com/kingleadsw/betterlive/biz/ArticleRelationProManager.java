package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.ArticleRelationPro;
import com.kingleadsw.betterlive.vo.ArticleRelationProVo;

public interface ArticleRelationProManager extends BaseManager<ArticleRelationProVo, ArticleRelationPro> {

	int deleteByArticleIds(int articleIds);

	int insertAll(List<ArticleRelationProVo> linkVos);

}
