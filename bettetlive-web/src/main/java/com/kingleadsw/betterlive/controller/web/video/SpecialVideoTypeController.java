package com.kingleadsw.betterlive.controller.web.video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.SpecialVideoTypeManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;

/***
 * 视频分类管理
 */
@Controller
@RequestMapping("/admin/specialvideotype")
public class SpecialVideoTypeController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(SpecialVideoTypeController.class);
	@Autowired
	private SpecialVideoTypeManager specialVideoTypeManager;
	
	/**
	 * 专题视频类型
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/video/list_video_type");
		return mv;
	}
	
	
	/**
	 * 查询专题视频
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryVideoTypeAllJson")
	@ResponseBody
	public void queryArticleTypeAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		List<SpecialVideoTypeVo> list = specialVideoTypeManager.queryListPage(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<SpecialVideoTypeVo>(), response);
		}
	}
	
	/**
	 * 新增专题视频页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddVideoType")
	public ModelAndView toAddVideoType(HttpServletRequest request,HttpServletResponse resp){
		return new ModelAndView("admin/video/add_video_type");
	}
	
	@RequestMapping(value="/toEditVideoType")
	public ModelAndView toEditVideoType(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		SpecialVideoTypeVo sart = specialVideoTypeManager.selectByPrimaryKey(Integer.parseInt(pd.getString("typeId")));
		
		ModelAndView mv = new ModelAndView("admin/video/edit_video_type");
		mv.addObject("sart", sart);
		return mv;
	}
	
	
	@RequestMapping(value="/editVideoType")
	@ResponseBody
	public Map<String, Object> editArticleType(HttpServletRequest request, HttpServletResponse response){
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String typeId=multipartRequest.getParameter("typeId");   	//视频类型ID
	        String typeName=multipartRequest.getParameter("typeName");   	//类型名称
	        String sort=multipartRequest.getParameter("sort");   	//视频
	        String status=multipartRequest.getParameter("status");   	//状态
	    	String typeCover = multipartRequest.getParameter("typeCover");	//图片
	        
	        int count = 0;
	    	SpecialVideoTypeVo sat = new SpecialVideoTypeVo();
        	sat.setTypeName(typeName);
        	sat.setStatus(Integer.parseInt(status));
        	sat.setSort(Integer.parseInt(sort));
        	sat.setTypeCover(typeCover);
        	
	        if(StringUtil.isNull(typeId)){	//新增
	        	count = specialVideoTypeManager.insert(sat);
	        }else{	//修改
	        	sat.setTypeId(Integer.parseInt(typeId));
	        	count = specialVideoTypeManager.updateByPrimaryKeySelective(sat);
	        }
	        
        	if(count>0){
        		return CallBackConstant.SUCCESS.callback();
			}
        	return CallBackConstant.FAILED.callback();
		}catch(Exception e){
			logger.error("/admin/specialvideotype/editVideoType --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 修改视频类型状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/editTypeStatus")
	@ResponseBody
	public Map<String, Object> editTypeStatus(HttpServletRequest request, HttpServletResponse response){
		try{
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getString("typeId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("类型ID为空");
			}
        	int count = specialVideoTypeManager.updateStatusByPrimaryKey(pd);
        	if(count>0){
        		return CallBackConstant.SUCCESS.callback();
			}else{
				return CallBackConstant.FAILED.callback();
			}
	        
		}catch(Exception e){
			logger.error("/admin/specialvideotype/editTypeStatus --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
