package com.kingleadsw.betterlive.controller.web.periodical;

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
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ArticlePeriodicalManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ArticlePeriodicalVo;

/***
 * 专题文章期刊
 */
@Controller
@RequestMapping("/admin/articleperiodical")
public class ArticlePeriodicalController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(ArticlePeriodicalController.class);
	@Autowired
	private ArticlePeriodicalManager articlePeriodicalManager;
	
	/**
	 * 专题文章期刊
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/periodical/list_article_periodical");
		return mv;
	}
	
	
	/**
	 * 查询期刊列表
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryListPage")
	@ResponseBody
	public void queryListPage(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		try {
			List<ArticlePeriodicalVo> list = articlePeriodicalManager.queryListPage(pd);
			if(null !=list && list.size()>0){
				this.outEasyUIDataToJson(pd, list, response);
			}else{
				this.outEasyUIDataToJson(pd,new ArrayList<ArticlePeriodicalVo>(), response);
			}
		} catch (Exception e) {
			logger.error("/admin/articleperiodical/queryListPage --error", e);
		}
	}
	
	/**
	 * 去新增期刊
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toAddArticlePeriodical")
	public ModelAndView toAddArticlePeriodical(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/periodical/add_article_periodical");
		return mv;
	}
	
	@RequestMapping(value="/addArticlePeriodical")
	@ResponseBody
	public Map<String, Object> addArticlePeriodical(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("连接地址： /admin/articleperiodical/addArticlePeriodical ,操作：新增文章期刊");
		
		try{
			String periodical=request.getParameter("periodical");   	//期数
			if(StringUtil.isNull(periodical)){
				return CallBackConstant.FAILED.callbackError("请输入期数");
			}  
	        String periodicalTitle=request.getParameter("periodicalTitle");   //期刊主题
	        if(StringUtil.isNull(periodicalTitle)){
	        	return CallBackConstant.FAILED.callbackError("请输入期刊主题");
	        }
	      
	        ArticlePeriodicalVo ap = new ArticlePeriodicalVo();
	        ap.setPeriodical(periodical);
	        ap.setPeriodicalTitle(periodicalTitle);
	        ap.setStatus(Integer.parseInt(request.getParameter("status")));
	        this.articlePeriodicalManager.insert(ap);
			
	        logger.info("连接地址： /admin/articleperiodical/addArticlePeriodical ,操作：增加文章期刊。  操作状态：结束");
			
			return CallBackConstant.SUCCESS.callback();
			
		}catch(Exception e){
			logger.info("连接地址：  /admin/articleperiodical/addArticlePeriodical --error:", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 去修改期刊
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toEditArticlePeriodical")
	public ModelAndView toEditArticlePeriodical(HttpServletRequest req, HttpServletResponse resp){
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView("admin/periodical/edit_article_periodical");
		ArticlePeriodicalVo ap = this.articlePeriodicalManager.queryOne(pd);
		mv.addObject("articlePeriodicalVo", ap);
		return mv;
	}
	
	@RequestMapping(value="/editArticlePeriodical")
	@ResponseBody
	public Map<String, Object> editArticlePeriodical(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("连接地址： /admin/articleperiodical/editArticlePeriodical ,操作：修改文章期刊");
		
		try{
			String periodicalId=request.getParameter("periodicalId");   	//主键ID
			if(StringUtil.isNull(periodicalId)){
				return CallBackConstant.FAILED.callbackError("主键ID丢失");
			}  
			
			String periodical=request.getParameter("periodical");   	//期数
			if(StringUtil.isNull(periodical)){
				return CallBackConstant.FAILED.callbackError("请输入期数");
			}  
	        String periodicalTitle=request.getParameter("periodicalTitle");   //期刊主题
	        if(StringUtil.isNull(periodicalTitle)){
	        	return CallBackConstant.FAILED.callbackError("请输入期刊主题");
	        }
	      
	        ArticlePeriodicalVo ap = new ArticlePeriodicalVo();
	        ap.setPeriodical(periodical);
	        ap.setPeriodicalTitle(periodicalTitle);
	        ap.setPeriodicalId(Integer.parseInt(periodicalId));
	        ap.setStatus(Integer.parseInt(request.getParameter("status")));
	        this.articlePeriodicalManager.updateByPrimaryKeySelective(ap);
	        
	    	logger.info("连接地址： /admin/articleperiodical/editArticlePeriodical ,操作：修改文章期刊。  操作状态：结束");
	    	
	        return CallBackConstant.SUCCESS.callback();
	        
		}catch(Exception e){
			logger.info("连接地址：  /admin/articleperiodical/editArticlePeriodical --error:", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 删除期刊 逻辑删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteArticlePeriodical")
	@ResponseBody
	public Map<String, Object> deleteArticlePeriodical(HttpServletRequest request, HttpServletResponse response){
		
		try{
			String periodicalId=request.getParameter("periodicalId");   	//主键ID
			if(StringUtil.isNull(periodicalId)){
				return CallBackConstant.FAILED.callbackError("主键ID丢失");
			}  
			
	        this.articlePeriodicalManager.deleteByPrimaryKey(Integer.parseInt(periodicalId));
	        return CallBackConstant.SUCCESS.callback();
	        
		}catch(Exception e){
			logger.info("连接地址：  /admin/articleperiodical/deleteArticlePeriodical --error:", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
