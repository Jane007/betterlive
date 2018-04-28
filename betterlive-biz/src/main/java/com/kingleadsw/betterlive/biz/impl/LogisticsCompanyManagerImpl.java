package com.kingleadsw.betterlive.biz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.LogisticsCompanyManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.LogisticsCompany;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.service.LogisticsCompanyService;
import com.kingleadsw.betterlive.service.OrderProductService;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.LogisticsCompanyVo;
import com.kingleadsw.betterlive.vo.OrderVo;



@Component
@Transactional
public class LogisticsCompanyManagerImpl extends  BaseManagerImpl<LogisticsCompanyVo, LogisticsCompany> implements LogisticsCompanyManager{

	@Autowired
	private LogisticsCompanyService logisticsCompanyService;
	
	@Autowired
	private OrderProductService orderProductService;

	
	@Override
	protected BaseService<LogisticsCompany> getService() {
		return logisticsCompanyService;
	}

	
	@Override
	public Map<String,Object> saveLogisticsInfo(PageData pd) {
		 
		 String  company_code=pd.getString("company_code");
		 String  logistics_code=pd.getString("logistics_code");
		 PageData pd2=new PageData();
		 if(pd.getInteger("order_product_id") != null){
			 pd2.put("orderpro_id", pd.getInteger("order_product_id"));
		 }else if (StringUtil.isNotNull(pd.getString("sub_order_code"))){
			 pd2.put("sub_order_code", pd.getString("sub_order_code"));
		 }
		 OrderProduct orderProduct=orderProductService.queryOne(pd2);
		 
		 if(orderProduct==null){
			 return CallBackConstant.FAILED.callbackError("订单不存在");
		 }
		//订单状态，0：已删除；1：待付款；2：待发货；3：待签收；4：待评价；5：已完成;6:系统取消;7:已退款 
		 if(orderProduct.getStatus()<=1 ){
			 return CallBackConstant.FAILED.callbackError("未付款商品不能发货");
		 }
		 
		 if(orderProduct.getStatus()>=4 ){
			 return CallBackConstant.FAILED.callbackError("已签收商品不能再发货");
		 }
		 
		 pd2.put("company_code", company_code);
		 pd2.put("logistics_code", logistics_code);
		 pd2.put("send_time", DateUtil.getCurrentTime());
		 pd2.put("status", 3);//已发货，将订单改为待签收 
		 pd2.put("orderpro_id", orderProduct.getOrderpro_id());
		 orderProductService.editOrderProductByOrderProductId(pd2);
		 
		return CallBackConstant.SUCCESS.callback();
	}

}
