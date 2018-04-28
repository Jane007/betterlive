package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.BannerInfoManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.BannerInfo;
import com.kingleadsw.betterlive.service.BannerInfoService;
import com.kingleadsw.betterlive.vo.BannerInfoVo;
/**
 * banner 实际交互实现层
 * @author zhangjing
 * @date 2017年3月8日 下午6:48:21
 */
@Component
@Transactional
public class BannerInfoManagerImpl  extends BaseManagerImpl<BannerInfoVo,BannerInfo> implements BannerInfoManager{

	@Autowired
	 private BannerInfoService bannerInfoService;
	@Override
	protected BaseService<BannerInfo> getService() {
		return bannerInfoService;
	}
	@Override
	public int insertSelective(BannerInfoVo record) {
		int res = 0;
		BannerInfo banner = new BannerInfo();
		banner.setBannerImg(record.getBannerImg());
		banner.setBannerTitle(record.getBannerTitle());
		banner.setBannerType(record.getBannerType());
		banner.setBannerUrl(record.getBannerUrl());
		banner.setStatus(record.getStatus());
		banner.setObjectId(record.getObjectId());
		banner.setIsSort(record.getIsSort());
		res = bannerInfoService.insertSelective(banner);
		return res;
	}
	@Override
	public int updateByPrimaryKeySelective(BannerInfoVo record) {
		int res = 0;
		BannerInfo banner = new BannerInfo();
		banner.setBannerImg(record.getBannerImg());
		banner.setBannerTitle(record.getBannerTitle());
		banner.setBannerType(record.getBannerType());
		banner.setBannerUrl(record.getBannerUrl());
		banner.setStatus(record.getStatus());
		banner.setBannerId(record.getBannerId());
		banner.setObjectId(record.getObjectId());
		banner.setIsSort(record.getIsSort());
		res = bannerInfoService.updateByPrimaryKeySelective(banner);
		return res;
	}
	@Override
	public List<BannerInfoVo> queryBannersListPage(PageData pd) {
		return po2voer.transfer(BannerInfoVo.class,bannerInfoService.queryBannersListPage(pd));
		
	}
	@Override
	public List<BannerInfoVo> queryAllBannersList(PageData pd) {
		return po2voer.transfer(BannerInfoVo.class,bannerInfoService.queryAllBannersList(pd));
	}

}
