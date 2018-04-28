package com.kingleadsw.betterlive.controller.wx.special;

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
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
/**
 * wx前端显示专题
 * @author zhangjing
 * @date 2017年3月8日 下午2:43:12
 */
@Controller
@RequestMapping("weixin/special")
public class SpecialController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(SpecialController.class);
	
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private SpecialCommentManager specialCommentManager;
	
	
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/special/wx_special");
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		pd.clear();
		pd.put("status", 1);
		
		List<SpecialArticleTypeVo> sats = this.specialArticleTypeManager.queryList(pd);
		List<SpecialArticleTypeVo> typeResults = new ArrayList<SpecialArticleTypeVo>();
		typeResults.add(new SpecialArticleTypeVo(-1, "全部"));
		if(sats != null && sats.size() > 0){
			typeResults.addAll(sats);
		}
		mv.addObject("specialArticleTypes", typeResults);
		mv.addObject("customerId", customerId);
		mv.addObject("type", type);
		return mv;
	}
	
	/**
	 * 查询专题信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialAllJson")
	@ResponseBody
	public Map<String,Object> querySpecialAllJson(HttpServletRequest request,HttpServletResponse response) {
		logger.info("/weixin/special/querySpecialAllJson--->begin");
		PageData pd = this.getPageData();
//		pd.put("endTime", "endTime");
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		logger.info("/weixin/special/querySpecialAllJson--->end");
		return map;
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
		PageData pd = this.getPageData();
		pd.put("specialType", 2);
		pd.put("status", 1);
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		if(list ==null)
		{
			list = new ArrayList<SpecialVo>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	@RequestMapping(value="/toTorialComment")
	public ModelAndView toTorialComment(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/special/wx_special_pl");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(req);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		PageData specialParams = new PageData();
		specialParams.put("specialId", pd.getString("specialId"));
		specialParams.put("customerId", customerId);
		specialParams.put("collectionType", 3);
		specialParams.put("shareType", 3);
		specialParams.put("praiseType", 2);
		specialParams.put("specialType", 4);
		SpecialVo specialVo = this.specialMananger.queryOneByTutorial(specialParams);
		String maodian = pd.getString("maodian");
		model.addAttribute("maodian", maodian);
		model.addAttribute("specialVo", specialVo);
		model.addAttribute("customerId", customerId);
		model.addAttribute("backUrl", pd.getString("backUrl"));
		return mv;
	}
	
	/**
	 * 查询美食教程信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/tutorialList")
	@ResponseBody
	public Map<String, Object> tutorialList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		pd.put("specialType", 4);
		pd.put("status", 1);
		pd.put("collectionType", 3);
		pd.put("praiseType", 2);
		pd.put("shareType", 3);
		pd.put("customerId", customerId);
		List<SpecialVo> list = specialMananger.queryTutorialListPage(pd);
		if(list == null){
			list = new ArrayList<SpecialVo>();
		}
		Map<String, Object> map = CallBackConstant.SUCCESS.callback(list);
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}

	/**
	 *  文章评论详情
	 */
	@RequestMapping(value = "/toVideoCommentDetail")
    public ModelAndView toVideoCommentDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();
    	ModelAndView mv = new ModelAndView("weixin/special/wx_special_comment_detail");
    	
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
	
		if(StringUtil.isNull(pd.getString("commentId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		int customerId = 0;
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		try {
			pd.put("currentId", customerId);
			pd.put("commentPraiseType", 3);
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				mv.addObject("tipsTitle", "评论信息提示");
				mv.addObject("tipsContent", "您访问的评论内容不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			mv.addObject("commentVo", commentVo);
			mv.addObject("customerId", customerId);
			mv.addObject("backUrl", pd.getString("backUrl"));
			return mv;
		} catch (Exception e) {
			logger.info("/weixin/specialarticle/toCommentDetail --error", e);
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您访问的评论内容不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
    }
	
	@RequestMapping(value="/toAddTorialComment")
	public ModelAndView toAddTorialComment(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("weixin/special/wx_special_comment");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("specialId"))){
			mv.addObject("tipsTitle", "评论信息提示");
			mv.addObject("tipsContent", "您要评论的信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		
		if(StringUtil.isNotNull(pd.getString("commentId"))){
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				commentVo=new SpecialCommentVo();
			}
			mv.addObject("commentVo", commentVo);
		}
		model.addAttribute("specialId", pd.getString("specialId"));
		model.addAttribute("specialType", pd.getString("specialType"));
		model.addAttribute("pageFlag", pd.getString("flag"));
		model.addAttribute("backUrl", pd.getString("backUrl"));
		return mv;
	}
}
