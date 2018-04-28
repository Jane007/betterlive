package com.kingleadsw.betterlive.controller.app.feedback;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.FeedBackManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.FeedBackVo;

/**
 * 会生活首页控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/app/feedback")
public class AppFeedBackController extends AbstractWebController {

	private static Logger logger = Logger
			.getLogger(AppFeedBackController.class);

	@Autowired
	private FeedBackManager feedBackManager;

	@Autowired
	private CustomerManager customerManager;

	@RequestMapping(value = "/addFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFeedBack(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			PageData pd = this.getPageData();

			String content = pd.getString("content");
			String contact = pd.getString("contact");
			Integer target = pd.getInteger("target");
			String token = pd.getString("token"); // 用户标识

			if (StringUtil.isEmpty(content) || StringUtil.isEmpty(contact)) {
				return CallBackConstant.PARAMETER_ERROR.callback();
			}

			CustomerVo customer = null;
			if (StringUtil.isNotEmpty(token)) {
				customer = customerManager.queryCustomerByToken(token);
			}

			FeedBackVo feedBack = new FeedBackVo();
			feedBack.setCustomer_id(null != customer ? customer.getCustomer_id(): 0);
			feedBack.setContent(content);
			feedBack.setContact(contact);
			feedBack.setTarget(target);
			int count = feedBackManager.insert(feedBack);
			if (count > 0) {
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (NumberFormatException e) {
			logger.info("/app/feedBackManager/addFeedBack----出现异常 ");
			return CallBackConstant.FAILED.callback();
		}
	}

}
