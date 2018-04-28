package com.kingleadsw.betterlive.controller.wx.presentcard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;
/**
 * 礼品卡消费记录
 * @author zhangjing
 *
 * 2017年3月21日
 */
@Controller
@RequestMapping(value = "/weixin/presentcardrecord")
public class WxPresentCardRecordController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxPresentCardRecordController.class);
	
	@Autowired
	private GiftCardRecordManager giftCardRecordManager;
	
	@RequestMapping("findList")
	public ModelAndView findList(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info("进入微信我的礼品卡交易记录....开始");
		ModelAndView modelAndView=new ModelAndView("weixin/usercenter/wx_presentcardrecord");
		logger.info("进入微信我的礼品卡交易记录....结束");
		return modelAndView;
	}
	
	@RequestMapping("setPresentCardReCord")
	public ModelAndView setPresentCardReCord(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info("进入微信我的礼品卡交易记录....开始");
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		ModelAndView modelAndView=new ModelAndView("weixin/presentcard/wx_new_presentcardrecord");
		logger.info("进入微信我的礼品卡交易记录....结束");
		return modelAndView;
	}

	/**
	 * 查询专题信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryCardRecordAllJson")
	@ResponseBody
	public void queryCardRecordAllJson(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/weixin/presentcardrecord/queryCardRecordAllJson--------------begin");
		JSONObject json=new JSONObject(); 
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		
		if(customer!=null && customer.getCustomer_id() != null){
			pd.put("customerId", customer.getCustomer_id());
			List<GiftCardRecordVo> list =  giftCardRecordManager.queryListPage(pd);
			
			json.put("pageInfo", pd.get("pageView"));
			json.put("list", list);
		}else{
			logger.info("查询专题信息：用户信息为空");
		}
		
		logger.info("/weixin/presentcardrecord/queryCardRecordAllJson--------------end");
		this.outObjectToJson(json, response);
	}
}
