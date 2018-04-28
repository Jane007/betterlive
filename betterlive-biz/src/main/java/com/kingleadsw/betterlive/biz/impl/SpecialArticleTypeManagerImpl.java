package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialArticleType;
import com.kingleadsw.betterlive.service.SpecialArticleTypeService;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;

@Component
@Transactional
public class SpecialArticleTypeManagerImpl extends BaseManagerImpl<SpecialArticleTypeVo, SpecialArticleType> implements SpecialArticleTypeManager {
	@Autowired
	private SpecialArticleTypeService specialArticleTypeService;
	
	@Override
	protected BaseService<SpecialArticleType> getService() {
		return specialArticleTypeService;
	}

	@Override
	public int updateStatusByPrimaryKey(PageData pd) {
		return specialArticleTypeService.updateStatusByPrimaryKey(pd);
	}

	
}
