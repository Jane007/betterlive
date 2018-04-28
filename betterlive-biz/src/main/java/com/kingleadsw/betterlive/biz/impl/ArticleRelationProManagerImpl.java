package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ArticleRelationProManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ArticleRelationPro;
import com.kingleadsw.betterlive.service.ArticleRelationProService;
import com.kingleadsw.betterlive.vo.ArticleRelationProVo;

@Component
@Transactional
public class ArticleRelationProManagerImpl extends BaseManagerImpl<ArticleRelationProVo, ArticleRelationPro> implements ArticleRelationProManager{

	@Autowired
	private ArticleRelationProService articleRelationProService;

	@Override
	protected BaseService<ArticleRelationPro> getService() {
		return articleRelationProService;
	}

	@Override
	public int deleteByArticleIds(int articleIds) {
		return articleRelationProService.deleteByArticleIds(articleIds);
	}

	@Override
	public int insertAll(List<ArticleRelationProVo> linkVos) {
		List<ArticleRelationPro> links= generator.transfer(ArticleRelationPro.class,linkVos) ;
		return articleRelationProService.insertAll(links);
	}
	

}
