package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.BannerInfoMapper;
import com.kingleadsw.betterlive.model.BannerInfo;
import com.kingleadsw.betterlive.service.BannerInfoService;

@Service("/bannerInfoService")
public class BannerInfoServiceImpl extends BaseServiceImpl<BannerInfo> implements BannerInfoService {

	@Autowired
	private BannerInfoMapper bannerInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer bannerId) {
		return bannerInfoMapper.deleteByPrimaryKey(bannerId);
	}

	@Override
	public int insertSelective(BannerInfo record) {
		return bannerInfoMapper.insertSelective(record);
	}

	@Override
	public BannerInfo selectByPrimaryKey(Integer bannerId) {
		return bannerInfoMapper.selectByPrimaryKey(bannerId);
	}

	@Override
	public int updateByPrimaryKeySelective(BannerInfo record) {
		return bannerInfoMapper.updateByPrimaryKeySelective(record);
	}
	

	@Override
	public List<BannerInfo> queryBannersListPage(PageData pd) {
		return bannerInfoMapper.queryBannersListPage(pd);
	}
	
	@Override
	public List<BannerInfo> queryAllBannersList(PageData pd){
		return bannerInfoMapper.queryAllBannersList(pd);
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.core.service.BaseServiceImpl#getBaseMapper()
	 */
	@Override
	protected BaseMapper<BannerInfo> getBaseMapper() {
		return bannerInfoMapper;
	}

}
