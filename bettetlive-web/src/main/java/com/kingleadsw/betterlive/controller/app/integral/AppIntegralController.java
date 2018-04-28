package com.kingleadsw.betterlive.controller.app.integral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.CustomerLotteryManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerIntegralRecordVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SystemLevelVo;

/**
 * app1.2.7版本 我的金币
 */
@Controller
@RequestMapping("/app/integral")
public class AppIntegralController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(AppIntegralController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SystemLevelManager systemLevelManager;
	@Autowired
	private CustomerIntegralRecordManager customerIntegralRecordManager;
	@Autowired
	private CustomerLotteryManager customerLotteryManager;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 我的金币 首页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toIntegral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toIntegral(HttpServletRequest req, HttpServletResponse resp){
		PageData pd = this.getPageData();
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		pd.put("customer_id", customer.getCustomer_id());
		customer = customerManager.queryOne(pd);
		
		pd.clear();
		pd.put("systemLevelId", customer.getLevelId());
		pd.put("status", 0);
		SystemLevelVo systemLevel = systemLevelManager.queryOne(pd);
		
		//检查当前用户是否有赞且是否获得金币，此逻辑只能在未读消息前面。不能动
		customerIntegralRecordManager.checkArticleAndVideoPraise(customer.getCustomer_id());
		
		PageData cl = new PageData();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("status", IntegralConstants.RECORD_STATUS_WAIT);
		cl.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		int untreatedCount = customerIntegralRecordManager.queryIntegralRecordCount(cl);
		
		String helpUrl = WebConstant.MAIN_SERVER+"/weixin/integral/toHelp";
		String levelHelpUrl = WebConstant.MAIN_SERVER + "/weixin/integral/helpHonor";
		String growthValue = "0,50,100,1000,5000,10000,30000,60000,100000";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("currentIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		map.put("accumulativeIntegral", BigDecimalUtil.subZeroAndDot(customer.getAccumulativeIntegral()));
		map.put("levelName", systemLevel.getLevelName());
		map.put("imgUrl", systemLevel.getImgUrl());
		map.put("untreatedCount", untreatedCount);
		map.put("helpUrl", helpUrl);
		map.put("growthValue", growthValue);
		map.put("levelHelpUrl", levelHelpUrl);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 轻松赢奖励
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appWinIntegral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> appWinIntegral(HttpServletRequest request,HttpServletResponse response){			
		PageData pd = this.getPageData();
		
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		} 		
		//2种类型共有的查询条件,用户id,收益或支出,是否有效
		pd.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		pd.put("customerId", customer.getCustomer_id());	
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
		
		//总获得积分,查出所有类型动态,分享文章,视频的
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
		BigDecimal dynamicRemain = BigDecimal.ZERO;
		BigDecimal articleRemain = BigDecimal.ZERO;
		
		//避免出现非法操作或异常出现负数
		if(IntegralConstants.MAX_INTEGRAL.compareTo(integralSum) == 1){
			remain = IntegralConstants.MAX_INTEGRAL.subtract(integralSum);
		}
		if(IntegralConstants.MAX_FORTY.compareTo(dynamicIntegralNum) == 1){
			dynamicRemain = IntegralConstants.MAX_FORTY.subtract(dynamicIntegralNum);
		}
		if(IntegralConstants.MAX_TWENTY_FIVE.compareTo(articleSum) == 1){
			articleRemain = IntegralConstants.MAX_TWENTY_FIVE.subtract(articleSum);
		}
		
		String helpeasyUrl = WebConstant.MAIN_SERVER+"/weixin/integral/toHelpeasy";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("remain", BigDecimalUtil.subZeroAndDot(remain)); //今日未完成任务的积分，0表示已完成
		map.put("articleRemain", BigDecimalUtil.subZeroAndDot(articleRemain)); //今日未完成文章分享任务的积分，0表示已完成
		map.put("dynamicRemain", BigDecimalUtil.subZeroAndDot(dynamicRemain)); //今日未完成视频分享任务的积分，0表示已完成
		map.put("helpeasyUrl", helpeasyUrl);
		return CallBackConstant.SUCCESS.callback(map);		
		
	}
	
	/**
	 * 我的金币页面数据接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/findIntegralRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findIntegralRecord(HttpServletRequest request,
			HttpServletResponse response){
		PageData pd = this.getPageData();
		
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		} 
		pd.put("customerId", customer.getCustomer_id());
		pd.put("status", IntegralConstants.RECORD_STATUS_YES);
		pd.put("checkIntegral", IntegralConstants.COMMON_STATUS_YES);
		List<CustomerIntegralRecordVo> list = customerIntegralRecordManager.queryListPage(pd);
		if (null == list) {
			list = new ArrayList<CustomerIntegralRecordVo>();
		}
		
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	
	/**
	 * 去领取金币页面
	 * 
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 上午11:00:26
	 */
	@RequestMapping(value = "/getAwardHeadInfo")
	@ResponseBody
	public Map<String, Object> getAwardHeadInfo(HttpServletRequest request,HttpServletResponse response,Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();

		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
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
		
		//帮助页接口地址
		String helpUrl = WebConstant.MAIN_SERVER+"/weixin/integral/toHelp";

		
		map.put("treeMap", treeMap);
		map.put("currentIntegral", BigDecimalUtil.subZeroAndDot(customer.getCurrentIntegral()));
		map.put("headUrl", customer.getHead_url());
		map.put("checkLottery", lotterySignInfo.get("checkLottery"));
		map.put("serialSign", lotterySignInfo.get("serialSign"));
		map.put("lotterySignStatus", lotterySignInfo.get("lotterySignStatus"));
		map.put("helpUrl", helpUrl);
		return CallBackConstant.SUCCESS.callback(map);
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
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
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
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		return customerIntegralRecordManager.getGoldAward(pd);
	}
	
	/**
	 * 每日签到抽奖
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getLotterySign")
	@ResponseBody
	public Map<String, Object> getLotterySign(HttpServletRequest request, HttpServletResponse response) {
		PageData pd = this.getPageData();

		String token = pd.getString("token"); // 用户标识

		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}

		if (StringUtil.isEmpty(pd.getString("clickIndex"))) { // 当前点击的抽奖编号，编号从0开始
			return CallBackConstant.PARAMETER_ERROR.callbackError("出现异常");
		}
		return customerLotteryManager.getLotterySign(customer.getCustomer_id(), pd.getInteger("clickIndex"));
	}
	
	/**
	 * 金币活动开关
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getIntegralSwitch")
	@ResponseBody
	public Map<String, Object> getIntegralSwitch(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		map.put("discoveryHelp", WebConstant.MAIN_SERVER+"/weixin/discovery/toHelp");
		return CallBackConstant.SUCCESS.callback(map);
	}

}
