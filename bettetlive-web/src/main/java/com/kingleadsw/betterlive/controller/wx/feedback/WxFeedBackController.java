package com.kingleadsw.betterlive.controller.wx.feedback;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.FeedBackManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.FeedBackVo;

@Controller
@RequestMapping(value = "/weixin/feedback")
public class WxFeedBackController extends AbstractWebController {
	private static Logger logger = Logger
			.getLogger(WxFeedBackController.class);

	@Autowired
	private FeedBackManager feedBackManager;

	@Autowired
	private CustomerManager customerManager;

	
	@RequestMapping("/toFeedBackUs")
	public ModelAndView toFeedBackUs(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_feedback");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/addFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFeedBack(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("/wexin/feedBackManager/addFeedBack----begin");

		PageData pd = this.getPageData();

		String content = pd.getString("content");
		String contact = pd.getString("contact");
		Integer target = 2;
		int customerId = 0;
		CustomerVo customer = Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}

		if (StringUtil.isEmpty(content) || StringUtil.isEmpty(contact)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("参数不能为空");
		}
		FeedBackVo feedBack = new FeedBackVo();
		feedBack.setCustomer_id(customerId);
		feedBack.setContent(content);
		feedBack.setContact(contact);
		feedBack.setTarget(target);
		int count = 0;

		try {
			count = feedBackManager.insert(feedBack);
			if (count > 0) {
				return CallBackConstant.SUCCESS.callback();
			}
			
			return CallBackConstant.FAILED.callback();
		} catch (NumberFormatException e) {
			logger.info("/wexin/feedBackManager/addFeedBack----出现异常 ");
			return CallBackConstant.FAILED.callback();
		}
	}
}
