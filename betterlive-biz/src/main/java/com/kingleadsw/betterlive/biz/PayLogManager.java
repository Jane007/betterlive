package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.PayLog;
import com.kingleadsw.betterlive.vo.PayLogVo;


/**
 * 支付日志 实际交互层
 * 2017-03-21 by chen
 */
public interface PayLogManager extends BaseManager<PayLogVo,PayLog>{
	
	int insertPayLog(PayLogVo payLog);   	 					//添加支付日志
	
	int editPayLog(PageData pd);  		  					//更新支付日志

	PayLogVo findPayLog(PageData pd);    						//根据条件查询支付日志
	
	List<PayLogVo> findPayLogListPage(PageData pd);    		//根据条件分页查询支付日志
	
	/**
	 *  根据旧订单编号更新订单编号
	 * @param pd
	 * @return
	 */
	int updateOrderCodeByOrdeId(PageData pd);
}
