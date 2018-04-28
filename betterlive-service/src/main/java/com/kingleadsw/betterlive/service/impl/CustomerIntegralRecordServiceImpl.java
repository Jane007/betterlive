package com.kingleadsw.betterlive.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CustomerIntegralRecordMapper;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;
import com.kingleadsw.betterlive.service.CustomerIntegralRecordService;



/**
 * 管理员  service 实现层
 * 2017-03-07 by chen
 */
@Service
public class CustomerIntegralRecordServiceImpl extends BaseServiceImpl<CustomerIntegralRecord> implements CustomerIntegralRecordService{
    @Autowired
    private CustomerIntegralRecordMapper customerIntegralRecordMapper;

    @Override
    protected BaseMapper<CustomerIntegralRecord> getBaseMapper() {
        return customerIntegralRecordMapper;
    }

	@Override
	public int queryIntegralRecordCount(PageData cl) {
		return customerIntegralRecordMapper.queryIntegralRecordCount(cl);
	}

	@Override
	public List<CustomerIntegralRecord> queryWeekDailyBonus(PageData pd) {
		return customerIntegralRecordMapper.queryWeekDailyBonus(pd);
	}

	@Override
	public List<CustomerIntegralRecord> queryAwardListPage(PageData pd) {
		return customerIntegralRecordMapper.queryAwardListPage(pd);
	}

	@Override
	public BigDecimal queryIntegralNumByParams(PageData pd) {
		return customerIntegralRecordMapper.queryIntegralNumByParams(pd);
	}

	@Override
	public List<CustomerIntegralRecord> queryOverdueList(PageData pd) {
		return customerIntegralRecordMapper.queryOverdueList(pd);
	}

	@Override
	public int batchSave(List<CustomerIntegralRecord> recordList) {
		return customerIntegralRecordMapper.batchSave(recordList);
	}

	@Override
	public List<CustomerIntegralRecord> queryCustSharePraiseList(PageData pd) {
		return customerIntegralRecordMapper.queryCustSharePraiseList(pd);
	}

}
