package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.PayLogMapper;
import com.kingleadsw.betterlive.model.PayLog;
import com.kingleadsw.betterlive.service.PayLogService;


/**
 * 支付日志 service 实现层
 * 2017-03-21 by chen
 */
@Service
public class PayLogServiceImpl extends BaseServiceImpl<PayLog>  implements PayLogService{
	@Autowired
	private PayLogMapper payLogMapper;

	@Override
	public int insertPayLog(PayLog payLog) {
		return payLogMapper.insertPayLog(payLog);
	}

	@Override
	public int editPayLog(PageData pd) {
		return payLogMapper.editPayLog(pd);
	}

	@Override
	public PayLog findPayLog(PageData pd) {
		return payLogMapper.findPayLog(pd);
	}

	@Override
	public List<PayLog> findPayLogListPage(PageData pd) {
		return payLogMapper.findPayLogListPage(pd);
	}

	@Override
	protected BaseMapper<PayLog> getBaseMapper() {
		return payLogMapper;
	}
	
	
	/**
	 * 合并用户时,需要合并用户的支付日志 
	 * @param pd
	 * @return
	 */
	@Override
	public int modifyCustomerPaylogBycId(PageData pd) {
		return payLogMapper.modifyCustomerPaylogBycId(pd);
	}

	/**
	 *  根据旧订单编号更新订单编号
	 * @param pd
	 * @return
	 */
	@Override
	public int updateOrderCodeByOrdeId(PageData pd) {
		// TODO Auto-generated method stub
		return payLogMapper.updateOrderCodeByOrdeId(pd);
	}
	
	

}
