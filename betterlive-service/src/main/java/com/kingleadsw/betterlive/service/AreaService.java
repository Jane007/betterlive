package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Area;



/**
 * author szx
 * date 2016-11-18 15 40
 **/
public interface AreaService extends BaseService<Area>{
	
	/**
	 * 查询省市区  省的prent_id=0
	 */
	List<Area> findAllAreaInfo(PageData pd);
	
	/**
	 * 根据地区id更新城市是否上线
	  * 2016年12月6日 下午4:44:39
	  * int
	 */
	int updateIsOnlineById(Area area);
	
	Area findAreaById(PageData pd);
	
	
	/**
	 * 根据城市名称查询城市
	 * @param cityName
	 * @return
	 */
	List<Area>queryByName(String areaName);
	
	
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