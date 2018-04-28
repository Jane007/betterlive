package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ArticleRelationProMapper;
import com.kingleadsw.betterlive.model.ArticleRelationPro;
import com.kingleadsw.betterlive.service.ArticleRelationProService;

@Service
public class ArticleRelationProServiceImpl extends BaseServiceImpl<ArticleRelationPro> implements ArticleRelationProService{

	@Autowired
	private ArticleRelationProMapper articleRelationProMapper;
	
	@Override
	protected BaseMapper<ArticleRelationPro> getBaseMapper() {
		return articleRelationProMapper;
	}

	@Override
	public int deleteByArticleIds(int articleIds) {
		return articleRelationProMapper.deleteByArticleIds(articleIds);
	}

	@Override
	public int insertAll(List<ArticleRelationPro> links) {
		return articleRelationProMapper.insertAll(links);
	}

}
