package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Area;


/**
 * 省市区
 * 2017-03-09  by chen
 */
public interface AreaMapper extends BaseMapper<Area>{
	
	/**
	 * 查询省市区  省的prent_id=0
	 */
	List<Area> findAllAreaInfo(PageData pd);
	
	int updateIsOnlineById(Area area);
	
	Area findAreaById(PageData pd);
	
	/**
	 * 根据城市名称查询城市
	 * @param cityName
	 * @return
	 */
	List<Area> queryByName(String areaName);
	
	/**
	 * 根据地区Id查询本省的地区 
	 * @param pd
	 * @return
	 */
	List<Area> queryAreaByCid(PageData pd);
	
	/**
	 *  根据产品Id查询配送镇区的上一级
	 * @param productId
	 * @return
	 */
	List<Area> queryCityByPid(String productId);
}