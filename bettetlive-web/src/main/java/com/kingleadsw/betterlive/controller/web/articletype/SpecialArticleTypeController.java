package com.kingleadsw.betterlive.controller.web.articletype;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;

/***
 * 专题文章管理
 */
@Controller
@RequestMapping("/admin/specialarticletype")
public class SpecialArticleTypeController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(SpecialArticleTypeController.class);
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	
	/**
	 * 专题文章类型
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/articletype/list_article_type");
		return mv;
	}
	
	
	/**
	 * 查询专题文章
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryArticleTypeAllJson")
	@ResponseBody
	public void queryArticleTypeAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		List<SpecialArticleTypeVo> list = specialArticleTypeManager.queryListPage(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<SpecialArticleTypeVo>(), response);
		}
	}
	
	/**
	 * 新增专题文章页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddArticleType")
	public ModelAndView toAddArticleType(HttpServletRequest request,HttpServletResponse resp){
		return new ModelAndView("admin/articletype/add_article_type");
	}
	
	@RequestMapping(value="/toEditArticleType")
	public ModelAndView toEditArticleType(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		SpecialArticleTypeVo sart = specialArticleTypeManager.selectByPrimaryKey(Integer.parseInt(pd.getString("typeId")));
		
		ModelAndView mv = new ModelAndView("admin/articletype/edit_article_type");
		mv.addObject("sart", sart);
		return mv;
	}
	
	
	@RequestMapping(value="/editArticleType")
	@ResponseBody
	public Map<String, Object> editArticleType(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String typeId=multipartRequest.getParameter("typeId");   	//文章类型ID
	        String typeName=multipartRequest.getParameter("typeName");   	//类型名称
	        String sort=multipartRequest.getParameter("sort");   	//文章
	        String status=multipartRequest.getParameter("status");   	//状态
	    	String typeCover = multipartRequest.getParameter("typeCover");	//图片
	        
	        int count = 0;
	    	SpecialArticleTypeVo sat = new SpecialArticleTypeVo();
        	sat.setTypeName(typeName);
        	sat.setStatus(Integer.parseInt(status));
        	sat.setSort(Integer.parseInt(sort));
        	sat.setTypeCover(typeCover);
        	
	        if(StringUtil.isNull(typeId)){	//新增
	        	count = specialArticleTypeManager.insert(sat);
	        }else{	//修改
	        	sat.setTypeId(Integer.parseInt(typeId));
	        	count = specialArticleTypeManager.updateByPrimaryKeySelective(sat);
	        }
	        
        	if(count>0){
				respMap.put("result", "succ");
				respMap.put("msg", "保存成功");
			}else{
				respMap.put("result", "fail");
				respMap.put("msg", "保存失败！");
			}
	        
		}catch(Exception e){
			logger.error("/specialarticletype/addArticleType --error", e);
			respMap.put("result", "fail");
			respMap.put("msg", "出现异常！");
		}
		return respMap;
	}
	
	/**
	 * 修改文章类型状态
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
        	int count = specialArticleTypeManager.updateStatusByPrimaryKey(pd);
        	if(count>0){
        		return CallBackConstant.SUCCESS.callback();
			}else{
				return CallBackConstant.FAILED.callback();
			}
	        
		}catch(Exception e){
			logger.error("/specialarticletype/editTypeStatus --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
