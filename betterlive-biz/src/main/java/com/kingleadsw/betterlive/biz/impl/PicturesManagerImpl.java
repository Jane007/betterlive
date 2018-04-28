package com.kingleadsw.betterlive.biz.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Pictures;
import com.kingleadsw.betterlive.service.PicturesService;
import com.kingleadsw.betterlive.vo.PicturesVo;
@Component
@Transactional
public class PicturesManagerImpl extends BaseManagerImpl<PicturesVo,Pictures> implements PicturesManager{
	@Autowired
	private PicturesService picturesService;
	@Override
	protected BaseService<Pictures> getService() {
		return picturesService;
	}
	@Override
	public int updatePicturesStatus(PageData pd) {
		return picturesService.updatePicturesStatus(pd);
	}
	@Override
	public int insertBatchFromList(List<PicturesVo> pictures) {
		List<Pictures> list= generator.transfer(Pictures.class,pictures);
		return picturesService.insertBatchFromList(list);
	}
	@Override
	public int updatePicturesSort(PageData pd) {
		
		return picturesService.updatePicturesSort(pd);
	}
}
