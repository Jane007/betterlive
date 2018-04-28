package com.kingleadsw.betterlive.controller.wx.article;

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

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
/**
 * wx前端显示专题文章
 */
@Controller
@RequestMapping("/weixin/specialarticle")
public class WxSpecialArticleController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(WxSpecialArticleController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private PicturesManager picturesManager;
	
	
	/**
	 * 查询美食推荐
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialArticleList")
	@ResponseBody
	public Map<String, Object> querySpecialArticleList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/weixin/specialarticle/querySpecialArticleList--->begin");
		PageData pd = this.getPageData();
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
//		pd.put("endTime", "endTime");  //只查询结束时间在当前时间之前的专题
//		pd.put("specialType", 1);
//		pd.put("collectionType", 2);
		pd.put("status", 1);
		pd.put("customerId", customerId);
		List<SpecialArticleVo> list = specialArticleManager.querySpecialArticleListPage(pd);
		if(list == null){
			list = new ArrayList<SpecialArticleVo>();
		}
		logger.info("/weixin/specialarticle/querySpecialArticleList--->end");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	/**
	 * 美食推荐详情
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toSpecialArticle")
	public ModelAndView toSpecialArticle(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/special/wx_special_article");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("articleId"))){
			mv.addObject("tipsTitle", "美食推荐信息提示");
			mv.addObject("tipsContent", "您访问的美食推荐信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null){
			customerId = customer.getCustomer_id();
		}
		
		PageData params = new PageData();
		params.put("articleId", pd.getString("articleId"));
		params.put("customerId", customerId);
//		params.put("collectionType", 4);
//		params.put("praiseType", 4);
//		params.put("shareType", 4);
//		params.put("specialType", 5);
		SpecialArticleVo specialArticleVo = this.specialArticleManager.queryOne(params);
		
		model.addAttribute("specialArticleVo", specialArticleVo);
		model.addAttribute("customerId", customerId);
		return mv;
	}
}
