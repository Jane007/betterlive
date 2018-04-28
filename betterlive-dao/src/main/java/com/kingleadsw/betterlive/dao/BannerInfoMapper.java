package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.BannerInfo;

public interface BannerInfoMapper  extends BaseMapper<BannerInfo> {
    int deleteByPrimaryKey(Integer bannerId);

    int insert(BannerInfo record);

    int insertSelective(BannerInfo record);

    BannerInfo selectByPrimaryKey(Integer bannerId);

    int updateByPrimaryKeySelective(BannerInfo record);

    int updateByPrimaryKey(BannerInfo record);
    
    List<BannerInfo> queryBannersListPage(PageData pd);
    
    List<BannerInfo> queryAllBannersList(PageData pd);
}