package com.kingleadsw.betterlive.controller.web.area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.AreaManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.AreaVo;

@Controller
@RequestMapping("/admin/area")
public class AreaController extends AbstractWebController {
	
	@Autowired
	private AreaManager areaManager;
	
	
	/**
	 * 查询省市区
	 * 2016-11-24  by chen
	 */
	@RequestMapping(value = "/findAllAreaJson")
	@ResponseBody
	public Map<String, Object>  findAllAreaJson(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		
		List<AreaVo> list=areaManager.findAllAreaInfo(pd);
		
		
		if(null!=list && list.size()>0){
			respMap.put("result", "succ");
			respMap.put("data",list);
		}else{
			respMap.put("result", "fail");
		}
		return respMap;
	}
	
	
	/**
	 * 查询省市区
	 * 2016-11-24  by chen
	 */
	@RequestMapping(value = "/findOnlineAreaJson")
	@ResponseBody
	public Map<String, Object>  findOnlineAreaJson(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		pd.put("isOnline", 1);
		List<AreaVo> list=areaManager.findAllAreaInfo(pd);
		
		
		if(null!=list && list.size()>0){
			respMap.put("result", "succ");
			respMap.put("data",list);
		}else{
			respMap.put("result", "fail");
		}
		return respMap;
	}
}
