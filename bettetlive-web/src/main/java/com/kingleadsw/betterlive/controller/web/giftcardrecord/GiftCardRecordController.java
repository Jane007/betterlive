package com.kingleadsw.betterlive.controller.web.giftcardrecord;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 后台礼品卡使用记录
 * @author zhangjing
 *
 * 2017年3月17日
 */
@Controller
@RequestMapping(value = "/admin/giftcardrecord")
public class GiftCardRecordController extends AbstractWebController{

	private static Logger logger = Logger.getLogger(GiftCardRecordController.class);
	@Autowired
	private GiftCardRecordManager giftCardRecordManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/giftcardrecord/list_giftcardrecord");
		return mv;
	}
	@RequestMapping(value = "/queryGiftCardAllJson")
	@ResponseBody
	public void queryGiftCardAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		logger.info("/admin/giftcardrecord/queryGiftCardAllJson----->begin");
		PageData pd = this.getPageData();
//		CustomerVo customer = Constants.getCustomer(httpRequest);
//		pd.put("customerId", customer.getCustomer_id());
		List<GiftCardRecordVo> list=giftCardRecordManager.queryListPage(pd); 
		if(list==null || list.size()==0){
			list = new ArrayList<GiftCardRecordVo>();
		}
		this.outEasyUIDataToJson(pd, list, response);
		logger.info("/admin/giftcardrecord/queryGiftCardAllJson----->end");
	}
}
