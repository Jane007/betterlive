package com.kingleadsw.betterlive.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.constant.SysDictKey;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.SysDictVo;

@Component("orderJob")
public class OrderJob {
	private static final Logger logger=Logger.getLogger(OrderJob.class);
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private OrderProductManager orderProductManager;
	
 
	
	@Autowired
	private SysDictManager sysDictManager;
	
	public void execute(){
		
		PageData pd=new PageData();
		pd.put("effectTime", effectTime());
		
		//待付款订单
		pd.put("status", OrderConstants.STATUS_ORDER_NOPAY);
		List<OrderVo> list=orderManager.queryList(pd);
		try{
			if(list!=null){
				for(OrderVo order:list){
					orderManager.cancelOrder(order);
					pd.clear();
					pd.put("orderId", order.getOrder_id());
					pd.put("status", OrderConstants.STATUS_ORDER_CANCEL);
					orderProductManager.updateOrderProductStatusByOrderId(pd);
				}
			}	
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 订单有效时间
	 * @return
	 */
	private  Date effectTime() {
		Date date=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		 
		PageData pd=new PageData();
		pd.put("dictCode", SysDictKey.SYSTEM_ORDER_EXPIRE);
		
		SysDictVo dictVo=sysDictManager.selectByCode(pd);
		String value=dictVo.getDictValue();
		if(StringUtil.isEmpty(value)){
			value="0.5";
		}
	
		float minute=-60*Float.parseFloat(value);
		c.add(Calendar.MINUTE, (int)minute);
		return c.getTime();
	}
	
}
