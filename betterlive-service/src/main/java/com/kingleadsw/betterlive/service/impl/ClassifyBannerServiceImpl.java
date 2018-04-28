package com.kingleadsw.betterlive.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ClassifyBannerMapper;
import com.kingleadsw.betterlive.model.ClassifyBanner;
import com.kingleadsw.betterlive.service.ClassifyBannerService;

@Service
public class ClassifyBannerServiceImpl extends BaseServiceImpl<ClassifyBanner> implements ClassifyBannerService {

	@Autowired
	private ClassifyBannerMapper bannerInfoMapper;

	@Override
	protected BaseMapper<ClassifyBanner> getBaseMapper() {
		return bannerInfoMapper;
	}
	/**
	 * 分页查询classifybanner
	 */
	@Override
	public List<ClassifyBanner> queryclassifybannerListPage(PageData pd) {
		return bannerInfoMapper.queryclassifybannerListPage(pd);
	}
	@Override
	public int addclassifybanner(ClassifyBanner record) {
		return bannerInfoMapper.addclassifybanner(record);
	}

}
