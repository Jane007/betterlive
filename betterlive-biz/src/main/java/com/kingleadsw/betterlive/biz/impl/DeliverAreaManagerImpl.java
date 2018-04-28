package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.DeliverAreaManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.DeliverArea;
import com.kingleadsw.betterlive.service.DeliverAreaService;
import com.kingleadsw.betterlive.vo.DeliverAreaVo;


/**
 * 商品配送地区   实际交换层
 * 2017-04-17 by chen
 */
@Component
@Transactional
public class DeliverAreaManagerImpl extends BaseManagerImpl<DeliverAreaVo,DeliverArea> implements DeliverAreaManager{
    @Autowired
     private DeliverAreaService deliverAreaService;

	@Override
	public int addBatchDeliverArea(List<DeliverAreaVo> list) {
		List<DeliverArea> listVo=vo2poer.transfer(DeliverArea.class, list);
		return deliverAreaService.addBatchDeliverArea(listVo);
	}

	
	@Override
	public int updateDeliverAreaByDid(DeliverAreaVo deliverAreaVo) {
		DeliverArea deliverArea=vo2poer.transfer(new DeliverArea(),deliverAreaVo);
		return deliverAreaService.updateDeliverAreaByDid(deliverArea);
	}

	@Override
	public int deleteDeliverAreaByPid(String pId) {
		return deliverAreaService.deleteDeliverAreaByPid(pId);
	}

	@Override
	public List<DeliverAreaVo> queryListDeliverArea(PageData pagedata) {
		return po2voer.transfer(DeliverAreaVo.class,deliverAreaService.queryListDeliverArea(pagedata));
	}

	@Override
	protected BaseService<DeliverArea> getService() {
		return deliverAreaService;
	}

   
    
 
}
