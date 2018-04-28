package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.DeliverAreaMapper;
import com.kingleadsw.betterlive.model.DeliverArea;
import com.kingleadsw.betterlive.service.DeliverAreaService;



/**
 * 商品配送地区  service 实现层
 * 2017-04-17 by chen
 */
@Service
public class DeliverAreaServiceImpl extends BaseServiceImpl<DeliverArea> implements DeliverAreaService{
	
	@Autowired
	private DeliverAreaMapper deliverAreaMapper;

	@Override
	public int addBatchDeliverArea(List<DeliverArea> list) {
		return deliverAreaMapper.addBatchDeliverArea(list);
	}

	@Override
	public int updateDeliverAreaByDid(DeliverArea deliverArea) {
		return deliverAreaMapper.updateDeliverAreaByDid(deliverArea);
	}

	@Override
	public int deleteDeliverAreaByPid(String pId) {
		return deliverAreaMapper.deleteDeliverAreaByPid(pId);
	}

	@Override
	public List<DeliverArea> queryListDeliverArea(PageData pagedata) {
		return deliverAreaMapper.queryListDeliverArea(pagedata);
	}

	@Override
	protected BaseMapper<DeliverArea> getBaseMapper() {
		return deliverAreaMapper;
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.service.DeliverAreaService#queryDeliverByAreaCode(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public DeliverArea queryDeliverByAreaCode(PageData pd) {
		return deliverAreaMapper.queryDeliverByAreaCode(pd);
	}
	
	
}
