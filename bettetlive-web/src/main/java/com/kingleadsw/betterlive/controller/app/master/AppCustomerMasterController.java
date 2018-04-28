package com.kingleadsw.betterlive.controller.app.master;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerMasterManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.CustomerMasterVo;


/**
 * 
 * 美食达人
 * 
 */
@Controller
@RequestMapping(value = "/app/customermaster")
public class AppCustomerMasterController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(AppCustomerMasterController.class);
	
	@Autowired
	private CustomerMasterManager customerMasterManager;
	
    /**
     * 搜索框默认值
     */
    @RequestMapping("/queryHotList")
    @ResponseBody
    public Map<String,Object> queryHotList(HttpServletRequest request) {
    	PageData pd = new PageData();
    	try {
    		pd.put("rowStart", 0);
    		pd.put("pageSize", 15);
    		pd.put("status", 1);
    		pd.put("recommendFlag", 1);
    		List<CustomerMasterVo> list = customerMasterManager.queryHotList(pd);
		   return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/customermaster/queryHotList --error", e);
		   return CallBackConstant.FAILED.callback();
		}
    }
	

}
