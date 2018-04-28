package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PayLogManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PayLog;
import com.kingleadsw.betterlive.service.PayLogService;
import com.kingleadsw.betterlive.vo.PayLogVo;


/**
 * 支付日志 实际交互 实现层
 * 2017-03-21 by chen
 */
@Component
@Transactional
public class PayLogManagerImpl extends BaseManagerImpl<PayLogVo,PayLog> implements PayLogManager{
	@Autowired
	private PayLogService payLogService;

	@Override
	public int insertPayLog(PayLogVo payLog) {
		PayLog payLogs=vo2poer.transfer(new PayLog(),payLog);
		return payLogService.insertPayLog(payLogs);
	}

	@Override
	public int editPayLog(PageData pd) {
		return payLogService.editPayLog(pd);
	}

	@Override
	public PayLogVo findPayLog(PageData pd) {
		return po2voer.transfer(new PayLogVo(),payLogService.findPayLog(pd));
	}

	@Override
	public List<PayLogVo> findPayLogListPage(PageData pd) {
		return po2voer.transfer(PayLogVo.class,payLogService.findPayLogListPage(pd));
	}

	@Override
	protected BaseService<PayLog> getService() {
		return payLogService;
	}
	
	
	/**
	 *  根据旧订单编号更新订单编号
	 * @param pd
	 * @return
	 */
	public int updateOrderCodeByOrdeId(PageData pd){
		return payLogService.updateOrderCodeByOrdeId(pd);
	}
}
