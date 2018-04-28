package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialArticleMapper;
import com.kingleadsw.betterlive.model.SpecialArticle;
import com.kingleadsw.betterlive.service.SpecialArticleService;

@Service
public class SpecialArticleServiceImpl extends BaseServiceImpl<SpecialArticle> implements SpecialArticleService{
	
	@Autowired
	private SpecialArticleMapper specialArticleContentMapper;
	
	@Override
	protected BaseMapper<SpecialArticle> getBaseMapper() {
		return specialArticleContentMapper;
	}

	@Override
	public List<SpecialArticle> querySpecialArticleListPage(PageData pd) {
		return specialArticleContentMapper.querySpecialArticleListPage(pd);
	}

	@Override
	public int hideSpecialHomeFlag(PageData hideSpeParam) {
		return specialArticleContentMapper.hideSpecialHomeFlag(hideSpeParam);
	}

	@Override
	public List<SpecialArticle> queryCircleArticleListPage(PageData pd) {
		return specialArticleContentMapper.queryCircleArticleListPage(pd);
	}

	@Override
	public SpecialArticle queryCircleOne(PageData pd) {
		return specialArticleContentMapper.queryCircleOne(pd);
	}

	@Override
	public int auditArticle(SpecialArticle pd) {
		return specialArticleContentMapper.auditArticle(pd);
	}
	
	@Override
	public int querySpecialArticleCount(PageData pd) {
		return specialArticleContentMapper.querySpecialArticleCount(pd);
	}

	@Override
	public List<SpecialArticle> queryTopThreeArticle(PageData pd) {
		return specialArticleContentMapper.queryTopThreeArticle(pd);
	}
	
}
