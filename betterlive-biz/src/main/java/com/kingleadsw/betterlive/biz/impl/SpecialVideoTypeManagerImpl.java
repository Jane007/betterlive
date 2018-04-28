package com.kingleadsw.betterlive.biz.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SpecialVideoTypeManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialVideoType;
import com.kingleadsw.betterlive.service.SpecialService;
import com.kingleadsw.betterlive.service.SpecialVideoTypeService;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

@Component
@Transactional
public class SpecialVideoTypeManagerImpl extends BaseManagerImpl<SpecialVideoTypeVo, SpecialVideoType> implements SpecialVideoTypeManager {
	@Autowired
	private SpecialVideoTypeService specialVideoTypeService;
	
	@Autowired
	private SpecialMananger specialMananger;
	
	@Override
	protected BaseService<SpecialVideoType> getService() {
		return specialVideoTypeService;
	}

	@Override
	public int updateStatusByPrimaryKey(PageData pd) {
		return specialVideoTypeService.updateStatusByPrimaryKey(pd);
	}

	@Override
	public List<SpecialVideoTypeVo> queryVideoTypeListPage(PageData pd) {
		pd.put("status", 1);
		List<SpecialVideoTypeVo> list = (List<SpecialVideoTypeVo>) po2voer.transfer(SpecialVideoTypeVo.class,specialVideoTypeService.queryVideoTypeListPage(pd));
		if(list == null){
			return new ArrayList<SpecialVideoTypeVo>();
		}
		PageData spd = new PageData();
		spd.put("specialType", 4);
		spd.put("status", 1);
		spd.put("collectionType", 3);
		spd.put("praiseType", 2);
		spd.put("shareType", 3);
		spd.put("customerId", pd.getInteger("customerId"));
		spd.put("rowStart", 0);
		spd.put("pageSize", 2);
		List<SpecialVideoTypeVo> results = new ArrayList<SpecialVideoTypeVo>();
		for (SpecialVideoTypeVo vo : list) {
			spd.put("objTypeId", vo.getTypeId());
			List<SpecialVo> videos = specialMananger.queryTutorialListPage(spd);
			if(videos != null && videos.size() > 0){
				vo.setSpecialList(videos);
				results.add(vo);
			}
		}
		
		return results;
	}

	
}
