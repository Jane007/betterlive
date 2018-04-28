package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ClassifyBanner;
import com.kingleadsw.betterlive.vo.ClassifyBannerVo;

public interface ClassifyBannerManager extends BaseManager<ClassifyBannerVo,ClassifyBanner>{

	
	List<ClassifyBannerVo> queryclassifybannerListPage(PageData pd);
	
	int addclassifybanner(ClassifyBannerVo record);
}
