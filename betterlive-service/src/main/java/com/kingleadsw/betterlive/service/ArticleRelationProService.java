package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ArticleRelationPro;

public interface ArticleRelationProService  extends BaseService<ArticleRelationPro>{

	int deleteByArticleIds(int articleIds);

	int insertAll(List<ArticleRelationPro> links);

}
