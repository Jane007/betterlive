package com.kingleadsw.betterlive.controller.app.guide;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerGuideManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SystemGuideManager;
import com.kingleadsw.betterlive.core.constant.CustomerGuideConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerGuideVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SystemGuideVo;



@Controller
@RequestMapping("/app/customerGuide")
public class AppCustomerGuideController extends AbstractWebController {
	
	protected Logger logger = Logger.getLogger(AppCustomerGuideController.class);
	
	@Autowired
	private CustomerGuideManager customerGuideManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SystemGuideManager systemGuideManager;
	
	/**
	 * 个人中心引导页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appCheckPersonalGuide", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> appCheckPersonalGuide(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();	
	
		String token = pd.getString("token"); // 用户标识		

		String customerLotterySign = CustomerGuideConstants.GUIDE_STATUS_NO;		
		String customerIntegral = CustomerGuideConstants.GUIDE_STATUS_NO;
		
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
		
		CustomerVo customervo = customerManager.queryCustomerByToken(token);
		if (customervo != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", customervo.getCustomer_id());

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
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("customerLotterySign", customerLotterySign);
		result.put("customerIntegral", customerIntegral);
		
		return CallBackConstant.SUCCESS.callback(result);		
	}
	
	/**
	 * 完成插入数据CustomerGuide
	 * 
	 */
	@RequestMapping(value="/appSaveGuide", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> appSaveGuide(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		String type = request.getParameter("type"); //type=0/1/2/3
		pd.put("guideType", type);
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		SystemGuideVo sysGuide = this.systemGuideManager.queryOne(pd);
		if(sysGuide == null){
			return CallBackConstant.FAILED.callback();
		}
		pd.put("customerId", customer.getCustomer_id());
		CustomerGuideVo customerGuideVo = customerGuideManager.queryOne(pd);
		
		if (customerGuideVo == null) {
			//当为0的时候同时插入2条数据
			if (Integer.valueOf(type) == CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ZERO);
				customerGuideManager.insertPageData(pd);
				
				
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_ONE);
				sysGuide = this.systemGuideManager.queryOne(pd);
			}
			if(sysGuide != null){
				customerGuideVo = customerGuideManager.queryOne(pd);
				if (customerGuideVo == null) {
					customerGuideManager.insertPageData(pd);
				}
			}
			else {
				customerGuideManager.insertPageData(pd);
			}
		}
		return CallBackConstant.SUCCESS.callback();
	}
	
	
	/**
	 * 圈子引导
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appCheckCircleGuide", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> appCheckCircleGuide(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();	
		
		String token = pd.getString("token"); // 用户标识		
		String customerCircle = CustomerGuideConstants.GUIDE_STATUS_NO;
	
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_TWO);
		
		SystemGuideVo sysGuide = this.systemGuideManager.queryOne(pd);
		
		if (sysGuide != null) { //圈子
			customerCircle = CustomerGuideConstants.GUIDE_STATUS_YES;//弹窗
		}
		CustomerVo customervo = customerManager.queryCustomerByToken(token);
		if (customervo != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", customervo.getCustomer_id());

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

		return CallBackConstant.SUCCESS.callback(customerCircle);
			
	}
	/**
	 * 精选引导
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appCheckSelectedGuide", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> appCheckSelectedGuide(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		
		String token = pd.getString("token"); // 用户标识				
		String customerSelected = CustomerGuideConstants.GUIDE_STATUS_NO;
		
		pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
		pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_THREE);
		SystemGuideVo sysSignGuide = this.systemGuideManager.queryOne(pd);
		 
		if (sysSignGuide != null) { //精选
			customerSelected = CustomerGuideConstants.GUIDE_STATUS_YES;//弹窗
		}
		CustomerVo customervo = customerManager.queryCustomerByToken(token);
		if (customervo != null) {
			pd.clear();
			pd.put("status", CustomerGuideConstants.SYSGUIDE_STATUS_YES);
			pd.put("customerId", customervo.getCustomer_id());

			if (sysSignGuide != null) {
				pd.put("guideType", CustomerGuideConstants.CUSTOMER_GUIDE_TYPE_THREE);
				CustomerGuideVo customerGuide = customerGuideManager.queryOne(pd);
				if (customerGuide == null) {
					customerSelected = CustomerGuideConstants.GUIDE_STATUS_YES; //精选弹窗
				} else {
					customerSelected = CustomerGuideConstants.GUIDE_STATUS_NO;//不需要弹窗
				}
				
			}
		}
		return CallBackConstant.SUCCESS.callback(customerSelected);			
	}
}
