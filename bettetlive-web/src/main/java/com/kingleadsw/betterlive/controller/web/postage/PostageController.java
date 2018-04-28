package com.kingleadsw.betterlive.controller.web.postage;

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

import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.ProductVo;

/**
 * 
 * @author ltp
 * @date 2017年3月9日15:55:00
 * 运费管理控制器
 */
@Controller
@RequestMapping("/admin/postage")
public class PostageController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(PostageController.class);
	
	@Autowired
	private PostageManager postageManager; 
	
	@Autowired
	private ProductManager productManager;
	
	/**
	 * 跳转查询运费列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list")
	public ModelAndView findListPostage(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView mv = new ModelAndView("admin/postage/postage_list");
		PageData pd = this.getPageData();
		
		pd.put("productStatus", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);
		List<PostageVo> list = postageManager.queryListPage(pd);
		if (null == list) {
			list = new ArrayList<PostageVo>();
		}
		String postageMap = "";  
		    for (PostageVo vo : list) {  
		    	postageMap += vo.getPostageId() +"="+vo.getProductIds()+"|";
		    }  
		model.addAttribute("products", products);
		model.addAttribute("postageMap", postageMap);
		return mv;
	}
	
	/**
	 * 查询运费数据，返回json
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public void queryPostageAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<PostageVo> list = postageManager.queryListPage(pd);
		if (list != null) {
			this.outEasyUIDataToJson(pd, list, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<PostageVo>(),
					response);
		}
	}
	
	
	/**
	 * 新增运费页面
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddPostage")
	public ModelAndView toAddPostage(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/postage/add_postage");
		return mv;
	}
	
	
	@RequestMapping(value = "/addPostage")
	@ResponseBody
	public Map<String, Object> addPostage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String postageName = multipartRequest.getParameter("postageName");
			String postageType = multipartRequest.getParameter("postageType");
			String meetConditions = multipartRequest.getParameter("meetConditions");
			String postage = multipartRequest.getParameter("postage");
			String postageMsg = multipartRequest.getParameter("postageMsg");
			
			PostageVo postageVo = new PostageVo();
			postageVo.setPostageName(postageName);
			if (StringUtil.isNotNull(postageType)) {
				postageVo.setPostageType(Integer.parseInt(postageType));
			}
			postageVo.setMeetConditions(meetConditions);
			postageVo.setPostage(postage);
			postageVo.setPostageMsg(postageMsg);
			
			int	iret = postageManager.insert(postageVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/admin/addAdmin------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
	
	/**
	 * 跳转更新运费界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdatePostage")
	public ModelAndView toUpdatePostage(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/postage/update_postage");
		PageData pd = this.getPageData();
		PostageVo vo = postageManager.queryOne(pd);
		
		model.addAttribute("postage", vo);
		
		return mv;
	}
	
	@RequestMapping(value = "/editPostage")
	@ResponseBody
	public Map<String, Object> editPostage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String postageId = request.getParameter("postageId");
			String chooseProductIds = request.getParameter("chooseProductIds");
			PageData pd = this.getPageData();
			pd.put("postageId", postageId);
			PostageVo postageVo = postageManager.queryOne(pd);
			int iret = 0;
			if (null != chooseProductIds) {
				if (",".equals(chooseProductIds)) {
					chooseProductIds = "";
				}
				postageVo.setProductIds(chooseProductIds);
				iret = postageManager.updateByPrimaryKey(postageVo);
				if (iret <= 0) {
					map.put("result", "fail");
					map.put("msg", "匹配商品失败");
					return map;
				}
			}else if(null == chooseProductIds){
				String postageName = request.getParameter("postageName");
				String postageType = request.getParameter("postageType");
				String meetConditions = request.getParameter("meetConditions");
				String postage = request.getParameter("postage");
				String postageMsg = request.getParameter("postageMsg");
				
				if (StringUtil.isNotNull(postageId)) {
					postageVo.setPostageId(Integer.parseInt(postageId));
				}
				postageVo.setPostageName(postageName);
				if (StringUtil.isNotNull(postageType)) {
					postageVo.setPostageType(Integer.parseInt(postageType));
				}
				postageVo.setMeetConditions(meetConditions);
				postageVo.setPostage(postage);
				postageVo.setPostageMsg(postageMsg);
				iret = postageManager.updateByPrimaryKey(postageVo);
				if (iret <= 0) {
					map.put("result", "fail");
					map.put("msg", "修改失败");
					return map;
				}
			}
		} catch (Exception e) {
			logger.error("/admin/postage/editPostage------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
	}
	
	/**
	 * 批量删除运费
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delPostage")
	@ResponseBody
	public Map<String, Object> delPostage(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String postageIdArray = request.getParameter("postageIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(postageIdArray)) {
				String[] postageIds = postageIdArray.split(",");
				for (String postageId : postageIds) {
					iret = postageManager.deleteByPrimaryKey(Integer.parseInt(postageId));
				}
			}
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/postage/delPostage------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "删除成功");
		return map;
	}
}
