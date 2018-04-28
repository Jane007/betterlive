package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ArticlePeriodicalMapper;
import com.kingleadsw.betterlive.model.ArticlePeriodical;
import com.kingleadsw.betterlive.service.ArticlePeriodicalService;

@Service
public class ArticlePeriodicalServiceImpl extends BaseServiceImpl<ArticlePeriodical> implements ArticlePeriodicalService{

	@Autowired
	private ArticlePeriodicalMapper specialPeriodicalMapper;
	@Override
	protected BaseMapper<ArticlePeriodical> getBaseMapper() {
		return specialPeriodicalMapper;
	}
	@Override
	public List<ArticlePeriodical> queryByArticleListPage(PageData pd) {
		return specialPeriodicalMapper.queryByArticleListPage(pd);
	}

}
