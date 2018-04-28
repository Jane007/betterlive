package com.kingleadsw.betterlive.biz.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Label;
import com.kingleadsw.betterlive.service.LabelService;
import com.kingleadsw.betterlive.vo.LabelVo;

/**
 * 标签 实际交互实现层
 */
@Component
@Transactional
public class LabelManagerImpl extends BaseManagerImpl<LabelVo,Label> implements LabelManager{
	@Autowired
	private LabelService labelService;

	@Override
	protected BaseService<Label> getService() {
		return labelService;
	}

	@Override
	public List<LabelVo> queryReportListPage(PageData pd) {
		return po2voer.transfer(LabelVo.class,labelService.queryReportListPage(pd));
	}

	@Override
    public int updateHomeFlag() {
        return labelService.updateHomeFlag();
    }
	
}
