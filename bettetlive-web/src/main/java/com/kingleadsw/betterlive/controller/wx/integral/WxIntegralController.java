package com.kingleadsw.betterlive.controller.wx.integral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.CustomerLotteryManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerIntegralRecordVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SystemLevelVo;

/**
 * wx1.2.7版本 我的金币
 */
@Controller
@RequestMapping("/weixin/integral")
public class WxIntegralController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(WxIntegralController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SystemLevelManager systemLevelManager;
	@Autowired
	private CustomerIntegralRecordManager customerIntegralRecordManager;
	@Autowired
	private CustomerLotteryManager customerLotteryManager;
	
	/**
	 * 我的金币 首页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toIntegral")
	public ModelAndView toIntegral(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/integral/wx_integral");
		PageData pd = new PageData();
		String backUrl = pd.getString("backUrl");
		CustomerVo customer = Constants.getCustomer(req);
		if(null == customer || customer.getCustomer_id() == null){
			mv = new ModelAndView("weixin/wx_login");
			return mv;
		}
		pd.put("customer_id", customer.getCustomer_id());
		customer = customerManager.queryOne(pd);
		
		pd.clear();
		pd.put("systemLevelId", customer.getLevelId());
		pd.put("status", 0);
		SystemLevelVo systemLevel = systemLevelManager.queryOne(pd);
		
		//检查当前用户是否有赞且是否获得金币，此逻辑只能在未读消息前面。不能动
		customerIntegralRecordManager.checkArticleAndVideoPraise(customer.getCustomer_id());
		
		pd.clear();
		pd.put("customerId", customer.getCustomer_id());
		pd.put("status", IntegralConstants.RECORD_STATUS_WAIT);
		pd.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		int untreatedCount = customerIntegralRecordManager.queryIntegralRecordCount(pd);
		
		mv.addObject("backUrl", backUrl);
		mv.addObject("customer", customer);
		mv.addObject("systemLevel", systemLevel);
		mv.addObject("untreatedCount", untreatedCount);
		return mv;
	}
	
	/**
	 * 查询收支明细
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/findIntegralRecord")
	@ResponseBody
	public Map<String,Object> findIntegralRecord(HttpServletRequest req){
		PageData pd = this.getPageData();
		pd.put("status", IntegralConstants.RECORD_STATUS_YES);
		pd.put("checkIntegral", IntegralConstants.COMMON_STATUS_YES);
		List<CustomerIntegralRecordVo> list = customerIntegralRecordManager.queryListPage(pd);
		if (null == list) {
			list = new ArrayList<CustomerIntegralRecordVo>();
		}
		return CallBackConstant.SUCCESS.callbackPageInfo(list, pd.get("pageView"));
	}
	
	/**
	 * 我的金币等级帮助页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toHelp")
	public ModelAndView toHelp(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/integral/wx_helpmygold");
		PageData pd = new PageData();
		mv.addObject("backUrl", pd.getString("backUrl"));
		return mv;
	}
	
	/**
	 * 轻松赢金币帮助页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toHelpeasy")
	public ModelAndView toHelpeasy(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/integral/wx_helpeasy");
		return mv;
	}
	
	/**
	 *我的主页等级帮助页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/helpHonor")
	public ModelAndView helpHonor(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/integral/wx_helphonor");
		return mv;
	}
	
	/**
	 * 轻松赢金币
	 * @param request
	 * @param modelView
	 * @return
	 */
	@RequestMapping(value="/toWinIntegral")
	public ModelAndView toWinIntegral(HttpServletRequest request,ModelAndView modelView){
	
		ModelAndView modelAndView = new ModelAndView("weixin/integral/wx_win_integral");
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("weixin/wx_login");
		}
		PageData pd = new PageData();
		//2种类型共有的查询条件
		pd.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		pd.put("customerId", customerVo.getCustomer_id());	
		pd.put("checkStatus", "0,1");
		pd.put("checkDay", IntegralConstants.COMMON_STATUS_YES);
		
		//分享文章（动态、精选）
		pd.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_THREE);
		BigDecimal articleIntegralNum = customerIntegralRecordManager.queryIntegralNumByParams(pd);

		//文章点赞
		pd.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_FIVE);
		BigDecimal articlePraiseIntegralNum = customerIntegralRecordManager.queryIntegralNumByParams(pd);

		
		//发动态查询
		pd.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_TWO);
//		BigDecimal dynamicIntegralNum = customerIntegralRecordManager.queryIntegralNumByParams(pd);
		//发动态领取次数
		int dynamicNum = customerIntegralRecordManager.queryIntegralRecordCount(pd);
		
		//视频
		pd.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_FOUR);		
		BigDecimal videoIntegralNum = customerIntegralRecordManager.queryIntegralNumByParams(pd);
		
		//视频点赞
		pd.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_SIX);		
		BigDecimal videoPraiseIntegralNum = customerIntegralRecordManager.queryIntegralNumByParams(pd);
		
		BigDecimal dynamicIntegralNum = BigDecimal.ZERO;
		if(dynamicNum == 1) {
			dynamicIntegralNum = new BigDecimal("20");
		}else if(dynamicNum >= IntegralConstants.LIMIT_DYNAMIC_TIMES) {
			dynamicIntegralNum = IntegralConstants.MAX_FORTY;
		}
		
		//总获得积分,查出所有类型动态,分享文章,视频的,点赞的
		BigDecimal integralSum = articleIntegralNum
								 .add(dynamicIntegralNum)
								 .add(videoIntegralNum)
								 .add(articlePraiseIntegralNum)
								 .add(videoPraiseIntegralNum);
		
		BigDecimal articleSum = articleIntegralNum
				 				   .add(articlePraiseIntegralNum)
								   .add(videoIntegralNum)	
								   .add(videoPraiseIntegralNum);
		
		BigDecimal remain = BigDecimal.ZERO;
		BigDecimal articleRemain = BigDecimal.ZERO;
		BigDecimal dynamicRemain = BigDecimal.ZERO;
		
		//避免出现非法操作或异常出现负数
		if(IntegralConstants.MAX_INTEGRAL.compareTo(integralSum) == 1) {
			remain = IntegralConstants.MAX_INTEGRAL.subtract(integralSum);
		}
		if(IntegralConstants.MAX_FORTY.compareTo(dynamicIntegralNum) == 1) {
			dynamicRemain = IntegralConstants.MAX_FORTY.subtract(dynamicIntegralNum);
		}
		if(IntegralConstants.MAX_TWENTY_FIVE.compareTo(articleSum) == 1) {
			articleRemain = IntegralConstants.MAX_TWENTY_FIVE.subtract(articleSum);
		}
		
		//还剩多少总积分
		modelAndView.addObject("remain", BigDecimalUtil.subZeroAndDot(remain)); //今日未完成任务的积分，0表示已完成
		modelAndView.addObject("articleRemain", BigDecimalUtil.subZeroAndDot(articleRemain)); //今日未完成文章分享任务的积分,和视频，0表示已完成
		modelAndView.addObject("dynamicRemain", BigDecimalUtil.subZeroAndDot(dynamicRemain));	//今日未完成发动态的积分，0表示已完成
		return modelAndView;
	}

	/**
	 * 去领取金币页面
	 * 
	 * 
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 上午11:00:26
	 */
	@RequestMapping(value = "/togetAward")
	public ModelAndView togetAward(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView mv = new ModelAndView("weixin/integral/wx_getaward");
		PageData pd = new PageData();

		CustomerVo customer = Constants.getCustomer(request);
		if (null == customer || customer.getCustomer_id() == null) {
			mv = new ModelAndView("weixin/wx_login");
			return mv;
		}
		pd.put("customer_id", customer.getCustomer_id());
		customer = customerManager.queryOne(pd);
		//manager组装数据签到进度条
		pd.put("customerId", customer.getCustomer_id());
		TreeMap<Integer, String> treeMap = customerIntegralRecordManager.installWeekCheckData(pd);
		
		
		//每日签到活动信息
		Map<String, Object> lotterySignInfo = this.customerLotteryManager.queryCustomerSignInfo(customer.getCustomer_id());
		
		//超过一年未领取的就改变是状态为失效
		customerIntegralRecordManager.updateOverDueStatus(pd);
		
		model.addAttribute("treeMap", treeMap);
		model.addAttribute("currentIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		model.addAttribute("headUrl", customer.getHead_url());
		model.addAttribute("lotterySignStatus", lotterySignInfo.get("lotterySignStatus"));
		model.addAttribute("checkLottery", lotterySignInfo.get("checkLottery"));
		model.addAttribute("serialSign", lotterySignInfo.get("serialSign"));
		return mv;
	}

	/**
	 * 查询符合条件领取金币的相关记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAWardList")
	@ResponseBody
	public Map<String, Object> getAWardList(HttpServletRequest request) {
		PageData pd = this.getPageData();
		PageView pv = (PageView) pd.get("pageView");
		if (pd.get("pageView") == null) {
			pv = new PageView();
			pd.put("pageView", pv);
		}
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		pd.put("customerId", customer.getCustomer_id());
		pd.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		pd.put("mainServer", WebConstant.MAIN_SERVER);
		pd.put("showExceptOne", IntegralConstants.COMMON_STATUS_YES);
		List<CustomerIntegralRecordVo> articlelist = customerIntegralRecordManager.queryAwardListPage(pd);
		
		pv.setRecords(articlelist);
		return CallBackConstant.SUCCESS.callback(pv);
	}

	/**
	 * 领取金币
	 * @param
	 * @return
	 * @author zhangjing 2018年4月2日 上午9:08:32
	 */
	@RequestMapping(value = "/getGoldAWard")
	@ResponseBody
	public Map<String, Object> getGoldAWard(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		return customerIntegralRecordManager.getGoldAward(pd);
	}
	
	/**
	 * 每日签到抽奖
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getLotterySign")
	@ResponseBody
	public Map<String, Object> getLotterySign(HttpServletRequest request,HttpServletResponse response){
		CustomerVo cust = Constants.getCustomer(request);
		if(cust == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		PageData pd = this.getPageData();
		if (StringUtil.isNull(pd.getString("clickIndex"))) { // 当前点击的抽奖编号，编号从0开始
			return CallBackConstant.PARAMETER_ERROR.callbackError("出现异常");
		}
		return customerLotteryManager.getLotterySign(cust.getCustomer_id(), pd.getInteger("clickIndex"));
	}
}
