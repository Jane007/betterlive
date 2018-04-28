package com.kingleadsw.betterlive.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ArticlePeriodicalManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ArticlePeriodical;
import com.kingleadsw.betterlive.service.ArticlePeriodicalService;
import com.kingleadsw.betterlive.vo.ArticlePeriodicalVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

@Component
@Transactional
public class ArticlePeriodicalManagerImpl extends BaseManagerImpl<ArticlePeriodicalVo, ArticlePeriodical> implements ArticlePeriodicalManager{

	@Autowired
	private ArticlePeriodicalService specialPeriodicalService;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	@Override
	protected BaseService<ArticlePeriodical> getService() {
		return specialPeriodicalService;
	}
	@Override
	public List<ArticlePeriodicalVo> queryByArticleListPage(PageData pd) {
		pd.put("status", 1);
		List<ArticlePeriodicalVo> list = po2voer.transfer(ArticlePeriodicalVo.class, specialPeriodicalService.queryByArticleListPage(pd));
		if(list == null){
			return new ArrayList<ArticlePeriodicalVo>();
		}
		List<ArticlePeriodicalVo> results = new ArrayList<ArticlePeriodicalVo>();
		if(list != null && list.size() > 0){
			PageData apd = new PageData();
			for (ArticlePeriodicalVo articlePeriodicalVo : list) {
				apd.clear();
				apd.put("status", 1);
				apd.put("customerId", pd.getInteger("customerId"));
				apd.put("periodicalId", articlePeriodicalVo.getPeriodicalId());
				List<SpecialArticleVo> saVo = specialArticleManager.querySpecialArticleListPage(apd);
				if(saVo != null && saVo.size() > 0){
					articlePeriodicalVo.setSpecialArticleList(saVo);
					results.add(articlePeriodicalVo);
				}
			}
		}
		return results;
	}

}
