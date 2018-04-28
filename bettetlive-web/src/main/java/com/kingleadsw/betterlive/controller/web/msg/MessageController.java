package com.kingleadsw.betterlive.controller.web.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 系统消息管理
 *
 */
@Controller
@RequestMapping(value = "/admin/message")
public class MessageController extends AbstractWebController {
	private static Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private OrderProductManager orderProductManager;
	
	@Autowired
	private SpecialMananger specialMananger;
	
	@Autowired
	private CustomerManager customerManager;
	
	@RequestMapping(value = "/list")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/msg/list_message");
		return mv;
	}
	
	@RequestMapping(value = "/queryMsgList")
	@ResponseBody
	public void queryMsgAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		PageData pd = this.getPageData();
		List<MessageVo> messageList = new ArrayList<MessageVo>();
		try {
			messageList =  messageManager.queryListPage(pd);
			if(messageList == null){
				messageList = new ArrayList<MessageVo>();
			}
		} catch (Exception e) {
			logger.error("/admin/message/queryMsgAllJson------error", e);
		}
		this.outEasyUIDataToJson(pd, messageList, response);
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest httpRequest,HttpServletResponse response){
		PageData pd = this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			messageManager.deleteByPrimaryKey(pd.getInteger("msgId"));
			result.put("flag", 1);
			return result;
		} catch (Exception e) {
			logger.error("/admin/message/delete------error", e);
			result.put("flag", 0);
			return result;
		}
	}
	
	@RequestMapping(value = "/toAddMsg")
	public ModelAndView toAddMsg(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/msg/edit_message");
		return mv;
	}
	
	@RequestMapping(value = "/saveSend")
	@ResponseBody
	public Map<String, Object> saveSend(HttpServletRequest httpRequest,HttpServletResponse response){
		PageData pd = this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PageData params = new PageData();
			
			int msgType = pd.getInteger("msgType");
			int subMsgType = pd.getInteger("subMsgType");
			String msgTitle = pd.getString("msgTitle");
			String msgDetail = pd.getString("msgDetail");
			String objId = pd.getString("objId");
			
			int sendCrowd = pd.getInteger("sendCrowd");
			String objName = pd.getString("objName");
			
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgDetail(msgDetail);
			msgVo.setMsgType(msgType);
			msgVo.setSubMsgType(subMsgType);
			msgVo.setMsgTitle(msgTitle);
			msgVo.setIsRead(0);
			msgVo.setObjId(0);
			if(sendCrowd == 2){ //单个用户发
				if(msgType == 0){ 							//系统消息
					if(subMsgType == 0){ //系统公告
						if(StringUtil.isNull(objName)){
							result.put("flag", 0);
							result.put("msg", "请输入用户手机号");
							return result;
						}
						params.put("mobile", objName);
						CustomerVo vo = customerManager.queryOne(params);
						if(vo == null){
							result.put("flag", 0);
							result.put("msg", "用户手机号不存在");
							return result;
						}
						msgVo.setCustomerId(vo.getCustomer_id());
					}else if(subMsgType == 8){ //物流公告
						if(StringUtil.isNull(objName)){
							result.put("flag", 0);
							result.put("msg", "请输入物流单号");
							return result;
						}
						params.put("logistics_code", objName);
						OrderProductVo opvo = orderProductManager.queryOne(params);
						if(opvo == null){
							result.put("flag", 0);
							result.put("msg", "物流单号不存在");
							return result;
						}
						params.clear();
						params.put("orderId",opvo.getOrder_id());
						OrderVo orderVo = orderManager.queryOne(params);
						msgVo.setCustomerId(orderVo.getCustomer_id());
						msgVo.setObjId(opvo.getOrderpro_id());
					}
				}else if(msgType == 1){ 							//挥货活动
					if (subMsgType == 10){	//活动公告
						if(StringUtil.isNull(objId)){
							result.put("flag", 0);
							result.put("msg", "请选择活动");
							return result;
						}
						if(StringUtil.isNull(objName)){
							result.put("flag", 0);
							result.put("msg", "请输入用户手机号");
							return result;
						}
						params.put("mobile", objName);
						CustomerVo vo = customerManager.queryOne(params);
						if(vo == null){
							result.put("flag", 0);
							result.put("msg", "用户手机号不存在");
							return result;
						}
						
						params.clear();
						params.put("specialId", objId);
						SpecialVo specialVo = specialMananger.selectSpecialByOption(params);
						if(specialVo == null){
							result.put("flag", 0);
							result.put("msg", "活动不存在");
							return result;
						}
						
						msgVo.setCustomerId(vo.getCustomer_id());
						msgVo.setObjId(Integer.parseInt(objId));
					}
				}else if(msgType == 3){  //交易消息
					if (subMsgType == 7){
						if(StringUtil.isNull(objName)){
							result.put("flag", 0);
							result.put("msg", "订单编号不能为空");
							return result;
						}
						
						params.put("sub_order_code", objName);
						OrderProductVo opvo = orderProductManager.queryOne(params);
						if(opvo == null){
							result.put("flag", 0);
							result.put("msg", "订单编号不存在");
							return result;
						}
						params.clear();
						params.put("orderId",opvo.getOrder_id());
						OrderVo orderVo = orderManager.queryOne(params);
						msgVo.setCustomerId(orderVo.getCustomer_id());
						msgVo.setObjId(opvo.getOrderpro_id());
					}
				}else{
					result.put("flag", 0);
					result.put("msg", "操作异常");
					return result;
				}
				this.messageManager.insert(msgVo);	//发送系统站内消息
			}else if (sendCrowd == 1){	//所有用户
				if(msgType != 0 && msgType != 1 && msgType != 3){
					result.put("flag", 0);
					result.put("msg", "操作异常");
					return result;
				}
				
				if(msgType == 1){ 							//挥货活动
					if (subMsgType == 10){	//活动公告
						if(StringUtil.isNull(objId)){
							result.put("flag", 0);
							result.put("msg", "请选择活动");
							return result;
						}
						params.clear();
						params.put("specialId", objId);
						SpecialVo specialVo = specialMananger.selectSpecialByOption(params);
						if(specialVo == null){
							result.put("flag", 0);
							result.put("msg", "活动不存在");
							return result;
						}
						
						msgVo.setObjId(Integer.parseInt(objId));
					}
				}
				
				this.sendMsgByAllCust(msgVo);
			}
			
			result.put("flag", 1);
			result.put("msg", "消息发送成功");
			return result;
		} catch (Exception e) {
			logger.error("/admin/message/saveSend------error", e);
			result.put("msg", "操作异常");
			result.put("flag", 0);
			return result;
		}
	}
	
	private void sendMsgByAllCust(final MessageVo msg) {
		final List<CustomerVo> custs = this.customerManager
				.findListCustomer(new PageData());
		Thread messageThread = new Thread() {
			@Override
			public void run() {
				for (CustomerVo customerVo : custs) {
					msg.setCustomerId(customerVo.getCustomer_id());
					messageManager.insert(msg);
				}
			}
		};
		messageThread.start();
	}
}
