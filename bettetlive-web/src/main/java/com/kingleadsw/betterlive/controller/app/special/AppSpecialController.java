package com.kingleadsw.betterlive.controller.app.special;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
/**
 * app专题控制器
 * @author zhangjing
 * @date 2017年3月8日 下午2:43:12
 */
@Controller
@RequestMapping("/app/special")
public class AppSpecialController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(AppSpecialController.class);
	
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	/**
	 * 查询专题信息(旧版)
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/app/special/list--->begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if (StringUtil.isNoNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
//		pd.put("endTime", "endTime");  //只查询结束时间在当前时间之前的专题
		pd.put("specialType", 1);
		pd.put("collectionType", 2);
		pd.put("status", 1);
		pd.put("customerId", customerId);
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		resultMap.put("specialList", list);
		logger.info("/app/special/list--->end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询美食教程信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/tutorialList")
	@ResponseBody
	public Map<String, Object> tutorialList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/app/special/tutorialList--->begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if (StringUtil.isNoNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		pd.put("specialType", 4);
		pd.put("status", 1);
		pd.put("collectionType", 3);
		pd.put("praiseType", 2);
		pd.put("shareType", 3);
		pd.put("customerId", customerId);
		List<SpecialVo> list = specialMananger.queryTutorialListPage(pd);
		resultMap.put("specialList", list);
		logger.info("/app/special/tutorialList--->end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询限量抢购信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/limitList")
	@ResponseBody
	public Map<String, Object> limitList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/app/special/limitList--->begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		pd.put("specialType", 2);
		pd.put("status", 1);
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		resultMap.put("specialList", list);
		logger.info("/app/special/limitList--->end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询团购活动
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/groupList")
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/app/special/groupList--->begin");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		pd.put("specialType", 3);
		pd.put("status", 1);
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		for (SpecialVo specialVo : list) {
			PageData sysGroupPageData = new PageData();
			sysGroupPageData.put("specialId", specialVo.getSpecialId());
			SysGroupVo sysGroupVo = sysGroupManager.queryOne(pd);
			specialVo.setSysGroupVo(sysGroupVo);
		}
		resultMap.put("specialList", list);
		logger.info("/app/special/groupList--->end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	
	/**
	 * 美食教程详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toSpecialTutorial")
	@ResponseBody
	public Map<String, Object> toSpecialTutorial(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("id为空");
		}
		
		String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if (StringUtil.isNoNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		PageData specialParams = new PageData();
		specialParams.put("specialId", pd.getString("specialId"));
		specialParams.put("customerId", customerId);
		specialParams.put("collectionType", 3);
		specialParams.put("praiseType", 2);
		specialParams.put("shareType", 3);
		specialParams.put("specialType", 4);
		SpecialVo specialVo = this.specialMananger.queryOneByTutorial(specialParams);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("specialVo", specialVo);
		map.put("customerId", customerId);
		map.put("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 限时活动详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toSpecialDelicacyRecommend")
	@ResponseBody
	public Map<String, Object> toSpecialDelicacyRecommend(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("id为空");
		}
		
		String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if (StringUtil.isNoNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		PageData specialParams = new PageData();
		specialParams.put("specialId", pd.getString("specialId"));
		specialParams.put("customerId", customerId);
		specialParams.put("collectionType", 2);
		specialParams.put("shareType", 2);
		SpecialVo specialVo = this.specialMananger.queryOneByTutorial(specialParams);
		
		return CallBackConstant.SUCCESS.callback(specialVo);
	}

}
