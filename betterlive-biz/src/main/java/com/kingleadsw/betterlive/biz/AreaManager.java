package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Area;
import com.kingleadsw.betterlive.vo.AreaVo;



/**
 * author szx
 * date 2016-11-18 15 59
 **/
public interface AreaManager extends BaseManager<AreaVo,Area>{
	
	/**
	 * 查询省市区  省的prent_id=0
	 */
	List<AreaVo> findAllAreaInfo(PageData pd);
	
	/**
	 * 根据地区id更新城市是否上线
	  * 2016年12月6日 下午4:44:39
	  * int
	 */
	int updateIsOnlineById(AreaVo area);
	
	AreaVo findAreaById(PageData pd);
	
	/**
	 * 根据地区Id查询本省的地区 
	 * @param pd
	 * @return
	 */
	List<AreaVo> queryAreaByCid(PageData pd);
	
	
	/**
	 *  根据产品Id查询配送镇区的上一级
	 * @param productId
	 * @return
	 */
	List<AreaVo> queryCityByPid(String productId);
	
}