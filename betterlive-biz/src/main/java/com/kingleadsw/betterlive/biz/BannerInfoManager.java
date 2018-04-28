package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.BannerInfo;
import com.kingleadsw.betterlive.vo.BannerInfoVo;

public interface BannerInfoManager extends BaseManager<BannerInfoVo,BannerInfo>{
	
	@Override
	int deleteByPrimaryKey(Integer bannerId);

    int insertSelective(BannerInfoVo record);

    @Override
	BannerInfoVo selectByPrimaryKey(Integer bannerId);

    @Override
	int updateByPrimaryKeySelective(BannerInfoVo record);
    
    List<BannerInfoVo> queryBannersListPage(PageData pd);
    
    List<BannerInfoVo> queryAllBannersList(PageData pd);

}
