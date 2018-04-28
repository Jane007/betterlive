package com.kingleadsw.betterlive.controller.web.feedback;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.FeedBackManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.FeedBackVo;
@Controller
@RequestMapping("/admin/feedback")
public class FeedBackController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(FeedBackController.class);
	
	@Autowired
	private FeedBackManager feedbackmanager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/feedback/list_feedback");
		return mv;
	}
	
	/**
	 * 分页查询商品标签信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryFeedBackAllJson")
	@ResponseBody
	public void queryProductLabelAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<FeedBackVo> feedbackvo = feedbackmanager.queryListPage(pd);
//		for (FeedBackVo feedBackVo2 : feedbackvo) {
//			System.err.println(feedBackVo2.getContact());
//		}
		if (feedbackvo != null) {
			this.outEasyUIDataToJson(pd, feedbackvo, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<FeedBackVo>(),
					response);
		}
	}
	
	/**
	 * 删除反馈信息
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value="/delFeedBack")
	@ResponseBody
	public Map<String, Object> delProductlabel(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String feedBackIdArray = request.getParameter("feedBackIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(feedBackIdArray)) {
				String[] feedbackId = feedBackIdArray.split(",");
				for (String feedlId : feedbackId) {
					iret = feedbackmanager.deleteByPrimaryKey(Integer.parseInt(feedlId));
				}
			}
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/productlabel/delproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "删除成功");
		return map;
	}
}
