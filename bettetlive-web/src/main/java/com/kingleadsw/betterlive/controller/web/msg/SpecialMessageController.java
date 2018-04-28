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
import com.kingleadsw.betterlive.biz.SpecialMessageManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.SpecialMessageVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 系统消息管理
 *
 */
@Controller
@RequestMapping(value = "/admin/specialmessage")
public class SpecialMessageController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(SpecialMessageController.class);
	
	
	@Autowired
	private SpecialMessageManager specialMessageMananger;
	
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
	
	
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/msg/list_message_special");
		return mv;
	}
	
	/**
	 * 查询限时活动，限时抢购，拼团活动
	 * specialType 1：限时活动， 2限量抢购，3团购活动
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialAllJson")
	@ResponseBody
	public void querySpecialAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<SpecialMessageVo> list = specialMessageMananger.queryMessageListPage(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<SpecialMessageVo>(), response);
		}
	}
	
	

	
	@RequestMapping(value = "/toAddMessageSpecial")
	public ModelAndView toAddMsg(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/msg/edit_message_special");
		return mv;
	}
	
	@RequestMapping(value = "/saveSpecialMessage")
	@ResponseBody
	public Map<String, Object> saveSpecialMessage(HttpServletRequest httpRequest,HttpServletResponse response){
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
				if(msgType == 1){ 							//挥货活动
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
			logger.error("/admin/specialmessage/saveSpecialMessage------error", e);
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
