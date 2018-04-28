package com.kingleadsw.betterlive.controller.wx.agent;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.AgentProductManager;
import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.AgentProductVo;

/**
 * 代理商品
 *
 */
@Controller
@RequestMapping(value = "/weixin/agentproduct")
public class WxAgentProductController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxAgentProductController.class);

	@Autowired
	private AgentProductManager agentProductManager;
	@Autowired
	private CustomerAgentManager customerAgentManager;
	
	
	/**
	 * 代理商品列表页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/productList")
	public ModelAndView productList(HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView modelAndView = new ModelAndView("weixin/agent/wx_agent_products");
		return modelAndView;
	}
	
	/**
	 * 代理商品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryProductList")
	@ResponseBody
	public Map<String, Object> queryProductList(HttpServletRequest request,HttpServletResponse response){
		try {
			PageData pd = this.getPageData();

			List<AgentProductVo> productVos = agentProductManager.queryAgentProductListPage(pd);
			return CallBackConstant.SUCCESS.callbackPageInfo(productVos, pd.get("pageView"));
		} catch (Exception e) {
			logger.error("/weixin/agentproduct/queryProductList", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
