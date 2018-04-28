package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.AreaManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Area;
import com.kingleadsw.betterlive.service.AreaService;
import com.kingleadsw.betterlive.vo.AreaVo;



/**
 * author szx
 * date 2016-11-18 16 02
 **/
@Component
@Transactional
public class AreaManagerImpl extends BaseManagerImpl<AreaVo,Area> implements AreaManager{
    @Autowired
     private AreaService areaService;
    @Override
    protected BaseService<Area> getService() {        
    	return areaService;    
    }
    
    /**
	 * 查询省市区  省的prent_id=0
	 */
	@Override
	public List<AreaVo> findAllAreaInfo(PageData pd) {
		return po2voer.transfer(AreaVo.class,areaService.findAllAreaInfo(pd));
	}


	@Override
	public int updateIsOnlineById(AreaVo area) {
		return areaService.updateIsOnlineById(vo2poer.transfer(new Area(), area));
	}

	
	@Override
	public AreaVo findAreaById(PageData pd) {
		return po2voer.transfer(new AreaVo(),areaService.findAreaById(pd));
	}

	@Override
	public List<AreaVo> queryAreaByCid(PageData pd) {
		return po2voer.transfer(AreaVo.class,areaService.queryAreaByCid(pd));
	}

	@Override
	public List<AreaVo> queryCityByPid(String productId) {
		return po2voer.transfer(AreaVo.class,areaService.queryCityByPid(productId));
	}
}