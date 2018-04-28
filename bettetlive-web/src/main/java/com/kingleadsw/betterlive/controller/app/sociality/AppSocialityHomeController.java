package com.kingleadsw.betterlive.controller.app.sociality;

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

import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

@Controller
@RequestMapping(value = "/app/socialityhome")
public class AppSocialityHomeController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(AppSocialityHomeController.class);
	
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
	@RequestMapping(value = "/querySocialityHome")
	@ResponseBody
	public Map<String, Object> querySocialityHome(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(customerVo == null || customerVo.getCustomer_id() <= 0){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		try {
			customerVo = customerManager.selectByPrimaryKey(customerVo.getCustomer_id());
			pd.put("customerId", customerVo.getCustomer_id());
			CustomerFansVo fansCountVo = customerFansManager.queryFansCount(pd);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fansCountVo", fansCountVo);
			map.put("customerVo", customerVo);
			map.put("bgUrl","http://images.hlife.shop/201802021541bg.png");
			map.put("levelHelpUrl", WebConstant.MAIN_SERVER + "/weixin/integral/helpHonor");
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/socialityhome/querySocialityHome --error", e);
			return CallBackConstant.FAILED.callback();
		}
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
		
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(customerVo == null || customerVo.getCustomer_id() <= 0){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}

		try {
			customerId = customerVo.getCustomer_id();
			pd.put("customerId", customerId);	//当前登录ID
			pd.put("authorId", customerId);	//我的用户ID（作者ID）
			pd.put("articleFrom", 1);
			pd.put("checkStatus", 1);
			List<SpecialArticleVo> list = specialArticleManager.queryCircleArticleListPage(pd);
			
			if(list == null){
				list = new ArrayList<SpecialArticleVo>();
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryMyDynamicList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * TA的主页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryOtherSocialityHome")
	@ResponseBody
	public Map<String, Object> queryOtherSocialityHome(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("otherCustomerId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("好友用户信息为空");
		}
		
		int myCustId = 0;
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo myCustomerVo = customerManager.queryCustomerByToken(pd.getString("token"));
			if(myCustomerVo != null && myCustomerVo.getCustomer_id() > 0){
				myCustId = myCustomerVo.getCustomer_id();
			}
		}
		try {
			int concernedId = pd.getInteger("otherCustomerId");
			
			CustomerVo customerVo = customerManager.selectByPrimaryKey(concernedId);
			if(customerVo == null || customerVo.getCustomer_id() <= 0){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("好友用户信息为空");
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fansCountVo", fansCountVo);
			map.put("concernedVo", concernedVo);
			map.put("customerVo", customerVo);
			map.put("bgUrl", "http://images.hlife.shop/201802021541bg.png");
			map.put("levelHelpUrl", WebConstant.MAIN_SERVER + "/weixin/integral/helpHonor");
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryOtherSocialityHome --error", e);
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
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo myCustomerVo = customerManager.queryCustomerByToken(pd.getString("token"));
			if(myCustomerVo != null && myCustomerVo.getCustomer_id() > 0){
				myCustId = myCustomerVo.getCustomer_id();
			}
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
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryOtherDynamicList --error", e);
			return CallBackConstant.FAILED.callback();
		}
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
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback(); 
		}
		CustomerVo myCustomerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(myCustomerVo == null || myCustomerVo.getCustomer_id() <= 0){
			return CallBackConstant.LOGIN_TIME_OUT.callback(); 
		}
			myCustId = myCustomerVo.getCustomer_id();
		try {
			pd.put("concernedId", myCustId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryMyFansListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryMyFansList --error", e);
			return CallBackConstant.FAILED.callback();
		}
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
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback(); 
		}
		CustomerVo myCustomerVo = customerManager.queryCustomerByToken(pd.getString("token"));
		if(myCustomerVo == null || myCustomerVo.getCustomer_id() <= 0){
			return CallBackConstant.LOGIN_TIME_OUT.callback(); 
		}
			myCustId = myCustomerVo.getCustomer_id();
		try {
			pd.put("customerId", myCustId);	//当前登录ID
			List<CustomerFansVo> list = this.customerFansManager.queryMyConcernedListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryMyConcernsList --error", e);
			return CallBackConstant.FAILED.callback();
		}
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
			int myCustId = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
				if(customerVo != null && customerVo.getCustomer_id() > 0){
					myCustId=customerVo.getCustomer_id();
				}
			}
			pd.put("myCustId", myCustId);
			List<CustomerFansVo> list = this.customerFansManager.queryOtherFansListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryOtherFansList --error", e);
			return CallBackConstant.FAILED.callback();
		}
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
			int myCustId = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customerVo = customerManager.queryCustomerByToken(pd.getString("token"));
				if(customerVo != null && customerVo.getCustomer_id() > 0){
					myCustId=customerVo.getCustomer_id();
				}
			}
			pd.put("myCustId", myCustId);
			List<CustomerFansVo> list = this.customerFansManager.queryOtherConcernedListPage(pd);
			
			if(list == null){
				list = new ArrayList<CustomerFansVo>();
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/app/socialityhome/queryOtherConcernsList --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
