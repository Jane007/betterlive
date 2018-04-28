package com.kingleadsw.betterlive.controller.wx.advert;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

@Controller
@RequestMapping(value = "/weixin/advert")
public class WxAdvertController extends AbstractWebController{
	private static Logger logger = Logger.getLogger(WxAdvertController.class);
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private CouponManagerManager couponManagerManager;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private UserSingleCouponManager  userSingleCouponManager;

	@RequestMapping(value = "/toAdvertising")
	public ModelAndView toAdvertising(HttpServletRequest request,HttpServletResponse response,Model model) {
		return new ModelAndView("weixin/advert/wx_advertising");
	}
	
	@RequestMapping(value = "/toAdvertGift")
	public ModelAndView toAdvertGift(HttpServletRequest request,HttpServletResponse response,Model model) {
		return new ModelAndView("weixin/advert/wx_advert_git");
	}
	
	/**
	 * 广告页一键领取
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveMultiCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveMultiCoupon(HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
		}

		try {
			//广告页一键领取优惠券
			String cmIds = WebConstant.NEW_USER_COUPON;
			if(StringUtil.isNull(cmIds)){
				return CallBackConstant.DATA_NOT_FOUND.callback();	
			}
			
			PageData couponParam = new PageData();
			String[] ids = cmIds.split(",");
			
			if(ids == null){
				return CallBackConstant.DATA_NOT_FOUND.callback();	
			}
			couponParam.put("couponIds", ids);
			couponParam.put("couponType", 2);
			couponParam.put("customerId",customer.getCustomer_id());
			List<CouponInfoVo> list = couponInfoManager.findListUserCoupon(couponParam);
			
			if(list != null && ids.length <= list.size()){//说明已经领完了所有的红包了
				return CallBackConstant.DATA_HAD_FOUND.callback(map);
			}
			
			PageData pd = new PageData();
			pd.put("cmIds",cmIds);
			pd.put("customerId", customer.getCustomer_id());
			pd.put("mobile", customer.getMobile());
			int count = couponInfoManager.batchInsertCouponInfo(pd);
			if(count>0){
				return CallBackConstant.SUCCESS.callback();	
			}
			return CallBackConstant.FAILED.callback();	
		} catch (Exception e) {
			logger.error("/weixin/advert/receiveMultiCoupon --error", e);
			return CallBackConstant.FAILED.callback();	
		}
	}
}
