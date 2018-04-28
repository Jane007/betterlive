package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.AreaMapper;
import com.kingleadsw.betterlive.model.Area;
import com.kingleadsw.betterlive.service.AreaService;



/**
 * author szx
 * date 2016-11-18 15 40
 **/
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService{
    @Autowired
    private AreaMapper areaMapper;

    @Override
    protected BaseMapper<Area> getBaseMapper() {
        return areaMapper;
    }
    
    /**
	 * 查询省市区  省的prent_id=0
	 */
	@Override
	public List<Area> findAllAreaInfo(PageData pd) {
		return areaMapper.findAllAreaInfo(pd);
	}

	/**
	 * 根据地区id更新城市是否上线
	  * 2016年12月6日 下午4:44:39
	  * int
	 */
	@Override
	public int updateIsOnlineById(Area area) {
		return areaMapper.updateIsOnlineById(area);
	}

	@Override
	public Area findAreaById(PageData pd) {
		return areaMapper.findAreaById(pd);
	}

	@Override
	public List<Area> queryByName(String areaName) {
		return areaMapper.queryByName(areaName);
	}

	@Override
	public List<Area> queryAreaByCid(PageData pd) {
		return areaMapper.queryAreaByCid(pd);
	}

	@Override
	public List<Area> queryCityByPid(String productId) {
		return areaMapper.queryCityByPid(productId);
	}

}