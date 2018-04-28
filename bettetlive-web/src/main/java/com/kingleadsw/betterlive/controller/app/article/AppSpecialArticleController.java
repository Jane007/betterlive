package com.kingleadsw.betterlive.controller.app.article;

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

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
/**
 * app专题美食推荐文章控制器
 */
@Controller
@RequestMapping("/app/specialarticle")
public class AppSpecialArticleController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(AppSpecialArticleController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	/**
	 * 查询美食推荐类型
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryArticleTypes")
	@ResponseBody
	public Map<String, Object> queryArticleTypes(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<SpecialArticleTypeVo> sats = this.specialArticleTypeManager.queryList(pd);
		List<SpecialArticleTypeVo> typeResults = new ArrayList<SpecialArticleTypeVo>();
		typeResults.add(new SpecialArticleTypeVo(-1, "全部"));
		if(sats != null && sats.size() > 0){
			typeResults.addAll(sats);
		}
		return CallBackConstant.SUCCESS.callback(typeResults);
	}
	/**
	 * 查询美食推荐
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialArticleList")
	@ResponseBody
	public Map<String, Object> querySpecialArticleList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/app/specialarticle/querySpecialArticleList--->begin");
		PageData pd = this.getPageData();
		int customerId = 0;
		
		String token = pd.getString("token");  	
		//用户标识
		if (StringUtil.isNoNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
//		pd.put("specialType", 1);
//		pd.put("collectionType", 2);
		pd.put("status", 1);
		pd.put("customerId", customerId);
		List<SpecialArticleVo> list = specialArticleManager.querySpecialArticleListPage(pd);
		if(list == null){
			list = new ArrayList<SpecialArticleVo>();
		}
		logger.info("/app/specialarticle/querySpecialArticleList--->end");
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	
	/**
	 * 美食推荐详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toSpecialArticle")
	@ResponseBody
	public Map<String, Object> toSpecialArticle(HttpServletRequest req, HttpServletResponse resp, Model model){
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
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
		specialParams.put("articleId", pd.getString("articleId"));
		specialParams.put("customerId", customerId);
//		specialParams.put("collectionType", 4);
//		specialParams.put("praiseType", 4);
//		specialParams.put("shareType", 4);
//		specialParams.put("specialType", 5);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(specialParams);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("specialArticleVo", specialArticleVo);
		map.put("customerId", customerId);
		return CallBackConstant.SUCCESS.callback(map);
	}

}
