package com.kingleadsw.betterlive.task;

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
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.SysDictVo;

@Component("orderFinshJob")
public class OrderFinshJob {

	private static final Logger logger=Logger.getLogger(OrderFinshJob.class);
	
	@Autowired
	private OrderManager orderManager;
	
 
	
	@Autowired
	private SysDictManager sysDictManager;
	
	@Autowired
	private OrderProductManager orderProductManager;	
	public void execute(){
		try{
			PageData sysDictPd=new PageData();
			sysDictPd.put("dictCode", SysDictKey.SYSTEM_ORDER_RECEIVING);
			
			SysDictVo dictVo=sysDictManager.selectByCode(sysDictPd);
			String value="7";
			if(dictVo != null && StringUtil.isNull(dictVo.getDictValue())){
				value = dictVo.getDictValue();
			}
			int day=Integer.parseInt(value);
			
			//截止day天前的发货订单
			Date cutTime=DateUtil.getPreAfterDate(new Date(),-day);
			
			PageData pd=new PageData();
			
			//已发货订单
			pd.put("sub_status", OrderConstants.STATUS_ORDER_SEND);
			pd.put("cutTime", cutTime);
			List<OrderProductVo> list=orderProductManager.findListOrderProduct(pd);
			if(list!=null && list.size() > 0){
				
				PageData editOrderPd=new PageData();
				
				for(OrderProductVo order:list){
					//改为待评价
					editOrderPd.put("status",OrderConstants.STATUS_ORDER_WAIT_COMMENT);
					editOrderPd.put("orderId", order.getOrder_id());
					editOrderPd.put("orderpro_id", order.getOrderpro_id());
//					orderManager.editOder(editOrderPd);
					orderProductManager.updateOrderProductStatusByOrderId(editOrderPd);		
				}
			}	
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
}
