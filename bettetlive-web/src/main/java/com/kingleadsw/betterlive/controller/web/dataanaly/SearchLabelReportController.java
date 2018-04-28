package com.kingleadsw.betterlive.controller.web.dataanaly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.LabelManager;
import com.kingleadsw.betterlive.biz.SearchLabelManager;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.LabelVo;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SearchLabelVo;

@Controller
@RequestMapping("/admin/searchLabelReport")
public class SearchLabelReportController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LabelManager labelManager;
	
	@Autowired
	private SearchLabelManager searchLabelManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/dataanaly/list_label_report");
		return mv;
	}
	
	/**
	 * 分页查询热门标签信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryLabelAllJson")
	@ResponseBody
	public void queryLabelAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<LabelVo> labels = this.labelManager
				.queryReportListPage(pd);
		if (labels != null) {
			this.outEasyUIDataToJson(pd, labels, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<ProductLabelVo>(),
					response);
		}
	}
	
	
	
	/**
	 * 分页查询热门标签搜索记录列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findSearchList")
	public ModelAndView findSearchList(HttpServletRequest req,
			HttpServletResponse resp,Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/label/list_labelSearch");
		model.addAttribute("labelId", Integer.parseInt(req.getParameter("labelId")));
		if (null!= req.getParameter("startTime") && !"".equals(req.getParameter("startTime"))) {
			model.addAttribute("startTime", req.getParameter("startTime"));
		}
		if (null!= req.getParameter("endTime") && !"".equals(req.getParameter("endTime"))) {
			model.addAttribute("endTime", req.getParameter("endTime"));
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/querySearchLabelAllJson")
	@ResponseBody
	public void querySearchLabelAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<SearchLabelVo> searchLabels = this.searchLabelManager
				.queryListPage(pd);
		if (searchLabels != null) {
			this.outEasyUIDataToJson(pd, searchLabels, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<SearchLabelVo>(),
					response);
		}
	}
	
	
}
