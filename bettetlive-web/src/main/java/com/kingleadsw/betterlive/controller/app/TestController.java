/**
 * @author dlb
 * 2017年5月25日 下午6:22:47
 */
package com.kingleadsw.betterlive.controller.app;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.OrderVo;

@Controller("testController")
@RequestMapping(value = "/common")
public class TestController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private OrderProductManager orderProductManager; 
	/**
	 * 个人中心查看全部订单
	 * 订单查询
	 */
	
	@RequestMapping(value="/updateStatusToOrderProduct")
	@ResponseBody
	public Map<String,Object> findList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd=new PageData();
		try{
			List<OrderVo> orderVoList=orderManager.findAllorders(new PageData());
			if(orderVoList!=null&&orderVoList.size()>0){
				for(OrderVo orderVo:orderVoList){
					if(orderVo.getStatus()!=null){
						pd.put("status", orderVo.getStatus());
						pd.put("orderId", orderVo.getOrder_id());
						int result=orderProductManager.updateOrderProductStatusByOrderId(pd);
						logger.info("更新订单order_id："+pd.getInteger("order_id")+"，订单状态为:"+pd.getInteger("status")+"的订单商品"+result+"条");
					}
				}
			}
		}catch(Exception e){
			logger.error("common/updateStatusToOrderProduct", e); 
		}
		
		
		
		
		
		return  CallBackConstant.SUCCESS.callback();
	}
	
	
}
