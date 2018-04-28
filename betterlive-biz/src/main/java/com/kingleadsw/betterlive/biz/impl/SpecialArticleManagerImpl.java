package com.kingleadsw.betterlive.biz.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialArticle;
import com.kingleadsw.betterlive.service.SpecialArticleService;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

@Component
@Transactional
public class SpecialArticleManagerImpl extends BaseManagerImpl<SpecialArticleVo, SpecialArticle> implements SpecialArticleManager {
	@Autowired
	private SpecialArticleService specialArticleService;
	
	@Override
	protected BaseService<SpecialArticle> getService() {
		return specialArticleService;
	}

	@Override
	public List<SpecialArticleVo> querySpecialArticleListPage(PageData pd) {
		return po2voer.transfer(SpecialArticleVo.class, specialArticleService.querySpecialArticleListPage(pd));
	}

	@Override
	public int hideSpecialHomeFlag(PageData hideSpeParam) {
		return specialArticleService.hideSpecialHomeFlag(hideSpeParam);
	}

	@Override
	public List<SpecialArticleVo> queryCircleArticleListPage(PageData pd) {
		return po2voer.transfer(SpecialArticleVo.class, specialArticleService.queryCircleArticleListPage(pd));
	}

	@Override
	public SpecialArticleVo queryCircleOne(PageData pd) {
		return  po2voer.transfer(new SpecialArticleVo(), specialArticleService.queryCircleOne(pd));
	}

	@Override
    public int auditArticle(SpecialArticleVo pd) {
        return specialArticleService.auditArticle(vo2poer.transfer(createPo(),pd));
    }
	
	@Override
    public int querySpecialArticleCount(PageData pd) {
        return specialArticleService.querySpecialArticleCount(pd);
    }

	@Override
	public List<SpecialArticleVo> queryTopThreeArticle(PageData pd) {
		return po2voer.transfer(SpecialArticleVo.class, specialArticleService.queryTopThreeArticle(pd));
	}
}
