package com.kingleadsw.betterlive.controller.web.freight;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.FreightVo;

/**
 * 
 * @author ltp
 * @date 2017年3月9日15:55:00
 * 运费管理控制器
 */
@Controller
@RequestMapping("/admin/freight")
public class FreightController extends AbstractWebController {
	
	@Autowired
	private FreightManager freightManager; 
	
	/**
	 * 跳转查询运费列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list")
	public ModelAndView findListTaste(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/freight/freight_list");
		return mv;
	}
	
	/**
	 * 查询运费数据，返回json
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public void querySpecialAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<FreightVo> list = freightManager.queryListPage(pd);
		this.outEasyUIDataToJson(pd, list, response);
	}
	
}
