package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Pictures;

/**
 * @author ltp
 * @since JDK1.7
 * @history 2017年3月28日 下午2:27:49 
 */
public interface PicturesService extends BaseService<Pictures> {

	int updatePicturesStatus(PageData pd);
	
	int updatePicturesSort(PageData pd);
}
