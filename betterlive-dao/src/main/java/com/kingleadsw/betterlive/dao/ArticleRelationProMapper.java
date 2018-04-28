package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.model.ArticleRelationPro;

public interface ArticleRelationProMapper extends BaseMapper<ArticleRelationPro>{

	int deleteByArticleIds(int articleIds);

	int insertAll(List<ArticleRelationPro> links);

}
