package com.kingleadsw.betterlive.controller.wx.sociality;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

@Controller
@RequestMapping(value = "/weixin/socialityhome")
public class WxSocialityHomeController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxSocialityHomeController.class);
	
	@Autowired
	private CustomerFansManager customerFansManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	/**
	 * 我的主页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSocialityHome")
	public ModelAndView toSocialityHome(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/personal");
		PageData pd = this.getPageData();
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			md.setViewName("weixin/wx_login");
			return md;
		}
		
		try {
			customerVo = customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			if(customerVo == null){
				md.addObject("tipsTitle", "用户信息提示");
				md.addObject("tipsContent", "账户信息丢失");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			pd.put("customerId", customerVo.getCustomer_id());
			CustomerFansVo fansCountVo = customerFansManager.queryFansCount(pd);
			if(fansCountVo == null){
				fansCountVo = new CustomerFansVo();
			}
			
			md.addObject("fansCountVo", fansCountVo);
			md.addObject("customerVo", customerVo);
			md.addObject("backUrl", pd.getString("backUrl"));
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/querySocialityHome --error", e);
		}
		return md;
	}
	
	/**
	 * 查询我的文章列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryMyDynamicList")
	@ResponseBody
	public Map<String, Object> queryMyDynamicList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		customerId = customer.getCustomer_id();
		try {
			pd.put("customerId", customerId);	//当前登录ID
			pd.put("authorId", customerId);	//我的用户ID（作者ID）
			pd.put("articleFrom", 1);
			pd.put("checkStatus", 1);
			List<SpecialArticleVo> list = specialArticleManager.queryCircleArticleListPage(pd);
			
			if(list == null){
				list = new ArrayList<SpecialArticleVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryMyDynamicList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 查询TA的文章列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryOtherDynamicList")
	@ResponseBody
	public Map<String, Object> queryOtherDynamicList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("otherCustId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("好友ID为空");
		}
		int myCustId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			myCustId = customer.getCustomer_id();
		}
		
		try {
			pd.put("customerId", myCustId);	//当前登录ID
			pd.put("authorId", pd.getString("otherCustId"));	//作者ID
			pd.put("articleFrom", 1);
			pd.put("status", 1);
			List<SpecialArticleVo> list = specialArticleManager.queryCircleArticleListPage(pd);
			
			if(list == null){
				list = new ArrayList<SpecialArticleVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryOtherDynamicList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 我的设置
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toCustomerModify")
	public ModelAndView toCustomerModify(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/customer_modify");
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			md.setViewName("weixin/wx_login");
			return md;
		}
		
		try {
			customerVo = customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			if(customerVo == null){
				md.addObject("tipsTitle", "用户信息提示");
				md.addObject("tipsContent", "账户信息丢失");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			md.addObject("customer", customerVo);
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toCustomerModify --error", e);
		}
		return md;
	}
	
	/**
	 * TA的主页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOtherSocialityHome")
	public ModelAndView toOtherSocialityHome(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/other_personal");
		PageData pd = this.getPageData();
		String backUrl = "";
		if(StringUtil.isNotNull(pd.getString("backUrl"))){
			backUrl = pd.getString("backUrl");
		}
		CustomerVo myCustVo = Constants.getCustomer(request);
		int myCustId = 0;
		if(myCustVo != null && myCustVo.getCustomer_id() > 0){
			myCustId = myCustVo.getCustomer_id();
		}
		
		if(StringUtil.isNull(pd.getString("otherCustomerId"))){
			md.addObject("tipsTitle", "用户信息提示");
			md.addObject("tipsContent", "账户信息丢失");
			md.setViewName("/weixin/fuwubc");
			return md;
		}
		try {
			int concernedId = pd.getInteger("otherCustomerId");
			
			CustomerVo customerVo = customerManager.selectByPrimaryKey(concernedId);
			if(customerVo == null){
				md.addObject("tipsTitle", "用户信息提示");
				md.addObject("tipsContent", "账户信息丢失");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			pd.put("customerId", concernedId);
			CustomerFansVo fansCountVo = customerFansManager.queryFansCount(pd);
			if(fansCountVo == null){
				fansCountVo = new CustomerFansVo();
			}
			
			CustomerFansVo concernedVo = new CustomerFansVo();
			if(myCustId > 0){
				pd.clear();
				pd.put("customerId", myCustId);
				pd.put("concernedId", concernedId);
				concernedVo = this.customerFansManager.queryOne(pd);
				if(concernedVo == null){
					concernedVo = new CustomerFansVo();	
				}
			}
			md.addObject("fansCountVo", fansCountVo);
			md.addObject("concernedVo", concernedVo);
			md.addObject("customerVo", customerVo);
			md.addObject("myCustId", myCustId);
			md.addObject("backUrl", backUrl);
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toOtherSocialityHome --error", e);
		}
		return md;
	}
	
	/**
	 * 我的粉丝列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyFans")
	public ModelAndView toMyFans(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/myFans");
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			md.setViewName("weixin/wx_login");
			return md;
		}
		
		try {
			customerVo = customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			if(customerVo == null){
				md.addObject("tipsTitle", "我的粉丝信息提示");
				md.addObject("tipsContent", "找不到页面啦~~~");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			md.addObject("backUrl", request.getParameter("backUrl"));
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toMyFans --error", e);
		}
		md.addObject("myCustId", customerVo.getCustomer_id());
		return md;
	}
	
	/**
	 * 查询我的粉丝列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryMyFansList")
	@ResponseBody
	public Map<String, Object> queryMyFansList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		int myCustId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback(); 
		}
		myCustId = customer.getCustomer_id();
		try {
			pd.put("concernedId", myCustId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryMyFansListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryMyFansList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 我的关注列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyConcerns")
	public ModelAndView toMyConcerns(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/myConcerns");
		
		CustomerVo customerVo = Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			md.setViewName("weixin/wx_login");
			return md;
		}
		
		try {
			customerVo = customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			if(customerVo == null){
				md.addObject("tipsTitle", "我的关注信息提示");
				md.addObject("tipsContent", "找不到页面啦~~~");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			md.addObject("backUrl", request.getParameter("backUrl"));
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toMyConcerns --error", e);
		}
		md.addObject("myCustId", customerVo.getCustomer_id());
		return md;
	}
	
	/**
	 * 查询我的关注列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryMyConcernsList")
	@ResponseBody
	public Map<String, Object> queryMyConcernsList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		int myCustId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback(); 
		}
		myCustId = customer.getCustomer_id();
		try {
			pd.put("customerId", myCustId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryMyConcernedListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("myCustId",myCustId);
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryMyConcernsList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * TA的粉丝列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOtherFans")
	public ModelAndView toOtherFans(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/otherFans");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("otherCustId"))){
			md.addObject("tipsTitle", "TA的粉丝信息提示");
			md.addObject("tipsContent", "找不到页面啦~~~");
			md.setViewName("/weixin/fuwubc");
			return md;
		}
		
		try {
			CustomerVo customerVo = customerManager.selectByPrimaryKey(pd.getInteger("otherCustId"));
			if(customerVo == null){
				md.addObject("tipsTitle", "TA的粉丝信息提示");
				md.addObject("tipsContent", "找不到页面啦~~~");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			
			int myCustId = 0;
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null && customer.getCustomer_id() > 0){
				myCustId = customer.getCustomer_id();
			}
			md.addObject("otherCustId", pd.getString("otherCustId"));
			md.addObject("myCustId", myCustId);
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toOtherFans --error", e);
		}
		return md;
	}
	
	/**
	 * 查询TA的粉丝列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryOtherFansList")
	@ResponseBody
	public Map<String, Object> queryOtherFansList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("concernedId"))){
			return CallBackConstant.DATA_NOT_FOUND.callback();
		}
		try {
			CustomerVo myCustVo = Constants.getCustomer(request);
			int myCustId = 0;
			if(myCustVo != null && myCustVo.getCustomer_id() > 0){
				myCustId = myCustVo.getCustomer_id();
			}
			
			pd.put("myCustId", myCustId);
			List<CustomerFansVo> list = this.customerFansManager.queryOtherFansListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryOtherFansList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * TA的关注列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOtherConcerns")
	public ModelAndView toOtherConcerns(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/weixin/sociality/otherConcerns");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("otherCustId"))){
			md.addObject("tipsTitle", "TA的关注信息提示");
			md.addObject("tipsContent", "找不到页面啦~~~");
			md.setViewName("/weixin/fuwubc");
			return md;
		}
		try {
			CustomerVo customerVo = customerManager.selectByPrimaryKey(pd.getInteger("otherCustId"));
			if(customerVo == null){
				md.addObject("tipsTitle", "TA的关注信息提示");
				md.addObject("tipsContent", "找不到页面啦~~~");
				md.setViewName("/weixin/fuwubc");
				return md;
			}
			int myCustId = 0;
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null && customer.getCustomer_id() > 0){
				myCustId = customer.getCustomer_id();
			}
			md.addObject("otherCustId", pd.getString("otherCustId"));
			md.addObject("myCustId", myCustId);
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/toOtherConcerns --error", e);
		}
		return md;
	}
	
	/**
	 * 查询TA的关注列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryOtherConcernsList")
	@ResponseBody
	public Map<String, Object> queryOtherConcernsList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("customerId"))){
			return CallBackConstant.DATA_NOT_FOUND.callback();
		}
		try {
			CustomerVo myCustVo = Constants.getCustomer(request);
			int myCustId = 0;
			if(myCustVo != null && myCustVo.getCustomer_id() > 0){
				myCustId = myCustVo.getCustomer_id();
			}
			
			pd.put("myCustId", myCustId);
			List<CustomerFansVo> list = this.customerFansManager.queryOtherConcernedListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/socialityhome/queryOtherConcernsList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
}
