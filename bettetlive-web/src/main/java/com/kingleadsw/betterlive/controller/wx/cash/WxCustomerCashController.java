package com.kingleadsw.betterlive.controller.wx.cash;

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

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerCashManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.SysCashManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * 礼品券兑换专区
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/weixin/customercash")
public class WxCustomerCashController extends AbstractWebController{

	private static Logger logger = Logger.getLogger(WxCustomerCashController.class);
	
	@Autowired
	private SysCashManager sysCashManager;
	
	@Autowired
	private CustomerCashManager customerCashManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private CouponManagerManager couponManagerManager;
	
	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private RedisService redisService;
	/**
	 * 查询商品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/toCashGift")
	@ResponseBody
	public ModelAndView toCashGift(HttpServletRequest request,HttpServletResponse response, Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/cash/wx_cash_activity");
		PageData pd = this.getPageData();
		int customerId = 0;
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo != null && customerVo.getCustomer_id() != null){
			customerId = customerVo.getCustomer_id();
		}
		
		modelAndView.addObject("customerId", customerId);
		if(StringUtil.isNotNull(pd.getString("sysId")) && "2".equals(pd.getString("sysId"))){
			modelAndView.setViewName("weixin/cash/wx_cash_unicom");
		}
		return modelAndView;
	}
	
	/**
	 * 领取礼物
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/getCashGift")
	@ResponseBody
	public Map<String, Object> getCashGift(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		if(StringUtil.isNull(pd.getString("codeValue"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("请输入兑换码");
		}
		
		try {
			customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
			String mobile = "";
			if(StringUtil.isNotNull(customer.getMobile())){
				mobile = customer.getMobile();
			}
			pd.put("customerId", customer.getCustomer_id());
			pd.put("mobile", mobile);
			return this.customerCashManager.getCashGift(pd);
		} catch (Exception e) {
			logger.error("/weixin/customercash/getCashGift", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
