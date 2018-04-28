package com.kingleadsw.betterlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PicturesMapper;
import com.kingleadsw.betterlive.model.Pictures;
import com.kingleadsw.betterlive.service.PicturesService;

/**
 * @author ltp
 * @since JDK1.7
 * @history 2017年3月28日 下午2:42:08 
 */
@Service
public class PicturesServiceImpl extends BaseServiceImpl<Pictures> implements PicturesService {

	@Autowired
	private PicturesMapper picturesMapper;

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.core.service.BaseServiceImpl#getBaseMapper()
	 */
	@Override
	protected BaseMapper<Pictures> getBaseMapper() {
		return picturesMapper;
	}

	@Override
	public int updatePicturesStatus(PageData pd) {
		return picturesMapper.updatePicturesStatus(pd);
	}

	@Override
	public int updatePicturesSort(PageData pic) {
		
		return  picturesMapper.updatePicturesSort(pic);
	}

}
