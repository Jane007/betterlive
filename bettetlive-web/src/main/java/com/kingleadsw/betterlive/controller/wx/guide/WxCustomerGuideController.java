package com.kingleadsw.betterlive.controller.wx.guide;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerGuideManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SystemGuideManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.CustomerGuideConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerGuideVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SystemGuideVo;

/**
 * 用户新手指引
 *
 */
@Controller
@RequestMapping("/weixin/customerGuide")
public class WxCustomerGuideController extends AbstractWebController {
	
	protected Logger logger = Logger.getLogger(WxCustomerGuideController.class);
	@Autowired
	private CustomerGuideManager customerGuideManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SystemGuideManager systemGuideManager;
	
	/**
	 * 个人主页页新手引导
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("/checkPersonalGuide")
	@ResponseBody
	public Map<String, Object> checkPersonalGuide(HttpServletRequest request, HttpServletResponse resp) {
		String customerLotterySign = CustomerGuideConstants.GUIDE_STATUS_NO;
		String customerIntegral = CustomerGuideConstants.GUIDE_STATUS_NO;
		Map<String, Object> result = new HashMap<String, Object>();

		PageData pd = new PageData();
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO);
		SystemGuideVo sysSignGuide = this.systemGuideManager.queryOne(pd);
		if (sysSignGuide != null) { //每日签到
			customerLotterySign = CustomerGuideConstants.GUIDE_STATUS_YES;
		}

		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ONE);
		SystemGuideVo sysIntegralGuide = this.systemGuideManager.queryOne(pd);
		if (sysIntegralGuide != null) { //当前金币
			customerIntegral = CustomerGuideConstants.GUIDE_STATUS_YES;
		}

		CustomerVo cust = Constants.getCustomer(request);
		if (cust != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", cust.getCustomer_id());

			if (sysSignGuide != null) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO);
				CustomerGuideVo signGuide = customerGuideManager.queryOne(pd);
				if (signGuide == null) {
					customerLotterySign = CustomerGuideConstants.GUIDE_STATUS_YES; // 每日签到需要弹窗
				} else {
					customerLotterySign = CustomerGuideConstants.GUIDE_STATUS_NO;
				}
			}
			if (sysIntegralGuide != null) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ONE);
				CustomerGuideVo integralGuide = customerGuideManager.queryOne(pd);
				if (integralGuide == null) { // 判断需不需要弹窗
					customerIntegral = CustomerGuideConstants.GUIDE_STATUS_YES; // 我的金币需要弹窗
				} else {
					customerIntegral = CustomerGuideConstants.GUIDE_STATUS_NO;
				}
			}
		}

		result.put("customerLotterySign", customerLotterySign);
		result.put("customerIntegral", customerIntegral);
		return result;
	}
	
	/**
	 * 完成插入数据CustomerGuide
	 * 
	 */
	@RequestMapping("/saveGuide")
	@ResponseBody
	public Map<String,Object> saveGuide(HttpServletRequest request,HttpServletResponse resp){
		// 获取用户 在CustomerGuide表中插入数据
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
		}
		
		PageData pd = new PageData();
		String type = request.getParameter("type"); // type=0/1/2/3 这里要传type
		pd.put("guideType", type);
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		SystemGuideVo sysGuide = this.systemGuideManager.queryOne(pd);
		if(sysGuide == null){
			return CallBackConstant.FAILED.callback();
		}
		
		pd.put("customerId", customer.getCustomer_id());
		CustomerGuideVo customerGuideVo = customerGuideManager.queryOne(pd);
		
		if (customerGuideVo == null) {
			if (Integer.valueOf(type) == CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO);
				customerGuideManager.insertPageData(pd);
				
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ONE);
				sysGuide = this.systemGuideManager.queryOne(pd);
				if(sysGuide != null){
					customerGuideVo = customerGuideManager.queryOne(pd);
					if (customerGuideVo == null) {
						customerGuideManager.insertPageData(pd);
					}
				}
			} else {
				customerGuideManager.insertPageData(pd);
			}
		}
		 return CallBackConstant.SUCCESS.callback();
	}
	
	

	/**
	 * 精选引导
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/checkSelectedGuide")
	@ResponseBody
	public  Map<String, Object> checkSelectedGuide(HttpServletRequest request, HttpServletResponse resp){
	
		String customerSelected = CustomerGuideConstants.GUIDE_STATUS_NO;
		Map<String, Object> result = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_THREE);
		SystemGuideVo sysGuide = this.systemGuideManager.queryOne(pd);
		
		if (sysGuide != null) { //精选
			customerSelected = CustomerGuideConstants.GUIDE_STATUS_YES;//弹窗
		}
		CustomerVo cust = Constants.getCustomer(request);
		if (cust != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", cust.getCustomer_id());

			if (sysGuide != null) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_THREE);
				CustomerGuideVo customerGuide = customerGuideManager.queryOne(pd);
				if (customerGuide == null) {
					customerSelected = CustomerGuideConstants.GUIDE_STATUS_YES; //精选弹窗
				} else {
					customerSelected = CustomerGuideConstants.GUIDE_STATUS_NO;//不需要弹窗
				}
				
			}
		}
		result.put("customerSelected", customerSelected);
		return result;
			
	}
	
	/**
	 * 圈子引导
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/checkCircleGuide")
	@ResponseBody
	public  Map<String, Object> checkCircleGuide(HttpServletRequest request, HttpServletResponse resp){
		
		String customerCircle = CustomerGuideConstants.GUIDE_STATUS_NO;
		Map<String, Object> result = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_TWO);
		SystemGuideVo sysGuide = this.systemGuideManager.queryOne(pd);
		
		if (sysGuide != null) { //圈子
			customerCircle = CustomerGuideConstants.GUIDE_STATUS_YES;//圈子弹窗
		}
		CustomerVo cust = Constants.getCustomer(request);
		if (cust != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", cust.getCustomer_id());

			if (sysGuide != null) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_TWO);
				CustomerGuideVo customerGuide = customerGuideManager.queryOne(pd);
				if (customerGuide == null) {
					customerCircle = CustomerGuideConstants.GUIDE_STATUS_YES; //圈子弹窗
				} else {
					customerCircle = CustomerGuideConstants.GUIDE_STATUS_NO;//不需要弹窗
				}
				
			}
		}
	
		result.put("customerCircle", customerCircle);
		return result;
			
	}

}
