package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.BannerInfo;
import com.kingleadsw.betterlive.model.Pictures;

/**
 * 图片操作dao层
 * @author ltp
 * @date 2017-3-28 14:34:36
 */
public interface PicturesMapper extends BaseMapper<Pictures> {

	int updatePicturesStatus(PageData pd);

	int updatePicturesSort(PageData pic);

}