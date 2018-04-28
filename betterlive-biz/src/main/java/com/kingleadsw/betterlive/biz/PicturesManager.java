package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Pictures;
import com.kingleadsw.betterlive.vo.PicturesVo;

public interface PicturesManager extends BaseManager<PicturesVo,Pictures>{

	int updatePicturesStatus(PageData pd);

	int insertBatchFromList(List<PicturesVo> pictures);
	
	int updatePicturesSort(PageData pd);
}
