package com.kingleadsw.betterlive.controller.wx.concern;

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

import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;

@Controller
@RequestMapping(value = "/weixin/concern")
public class WxConcernContoller extends AbstractWebController{
	private static Logger logger = Logger.getLogger(WxConcernContoller.class);
	@Autowired
	private CustomerFansManager customerFansManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private MessageManager messageManager;
	
	/**
	 * 关注好友
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/addConcern", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addConcern(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("/weixin/concern/addConcern----begin");

		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		//用户标识
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
		}

		if(StringUtil.isNull(pd.getString("concernCustId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("关注对象的ID为空");
		}

		CustomerFansVo fansVo = new CustomerFansVo();
		try {
			//check exists
			PageData checkpd = new PageData();
			checkpd.put("customerId", customer.getCustomer_id());
			checkpd.put("concernedId", pd.getInteger("concernCustId"));
			fansVo = this.customerFansManager.queryOne(checkpd);
			if(fansVo != null && fansVo.getFansId() > 0){
				return CallBackConstant.FAILED.callbackError("不能重复关注");
			}
			
			fansVo = new CustomerFansVo();
			fansVo.setCustomerId(customer.getCustomer_id());
			fansVo.setConcernedId(pd.getInteger("concernCustId"));
			int count = customerFansManager.insert(fansVo);
			logger.info("/weixin/concern/addConcern----end");
			
			if (count > 0) {
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgDetail("又有1枚吃货关注你啦，快去看看吧！");
				msgVo.setMsgType(5);
				msgVo.setSubMsgType(24);
				msgVo.setMsgTitle("【"+customer.getNickname()+"】关注你了");
				msgVo.setIsRead(0);
				msgVo.setObjId(fansVo.getFansId());
				msgVo.setCustomerId(fansVo.getConcernedId());
				this.messageManager.insert(msgVo);	//发送系统站内消息
				return CallBackConstant.SUCCESS.callback(fansVo.getFansId());
			}
			return CallBackConstant.FAILED.callback();
		} catch (NumberFormatException e) {
			logger.error("/weixin/concern/addConcern----error:"+ e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 取消关注好友
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/cancelConcern", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelConcern(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("/weixin/concern/cancelConcern----begin");

		PageData pd = this.getPageData();

		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
		}

		if(StringUtil.isNull(pd.getString("fansId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("id为空");
		}

		try {
			int count = customerFansManager.deleteByPrimaryKey(pd.getInteger("fansId"));
			if (count > 0) {
				pd.put("objId", pd.getInteger("fansId"));
				pd.put("msgType", 5);
				this.messageManager.delete(pd);
			}
			
			if (count > 0) {
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (NumberFormatException e) {
			logger.error("/weixin/concern/cancelConcern----error:"+ e);
			return CallBackConstant.FAILED.callback();
		}


	}
}
