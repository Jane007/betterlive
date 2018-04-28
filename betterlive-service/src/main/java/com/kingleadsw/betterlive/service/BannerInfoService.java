/**
 * @author szx
 * 2017年2月16日 下午5:19:53
 */
package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.BannerInfo;

/**
 * @author szx
 * 2017年2月16日 下午5:19:53
 */
public interface BannerInfoService extends BaseService<BannerInfo>{


    @Override
	int deleteByPrimaryKey(Integer bannerId);

    int insertSelective(BannerInfo record);

    @Override
	BannerInfo selectByPrimaryKey(Integer bannerId);

    @Override
	int updateByPrimaryKeySelective(BannerInfo record);
    
    List<BannerInfo> queryBannersListPage(PageData pd);
    
    
    List<BannerInfo> queryAllBannersList(PageData pd);
}
