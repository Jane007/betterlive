package com.kingleadsw.betterlive.controller.web.label;

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
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SearchLabelManager;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.LabelVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SearchLabelVo;

@Controller
@RequestMapping("/admin/label")
public class LabelController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LabelManager labelManager;
	
	@Autowired
	private SearchLabelManager searchLabelManager;
	
	@Autowired
	private ProductManager productManager;
	
	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		PageData pd = this.getPageData();
		
		pd.put("productStatus", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);
//		List<PostageVo> list = postageManager.queryListPage(pd);
//		if (null == list) {
//			list = new ArrayList<PostageVo>();
//		}
//		String postageMap = "";  
//		    for (PostageVo vo : list) {  
//		    	postageMap += vo.getPostageId() +"="+vo.getProductIds()+"|";
//		    }  
//		model.addAttribute("postageMap", postageMap);
		ModelAndView mv = new ModelAndView(
				"admin/label/list_label");
		mv.addObject("products", products);
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
				.queryListPage(pd);
		if (labels != null) {
			this.outEasyUIDataToJson(pd, labels, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<LabelVo>(),
					response);
		}
	}
	
	/**
	 * 新增标签页面
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddLabel")
	public ModelAndView toAddLabel(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/label/add_label");
		return mv;
	}
	
	
	@RequestMapping(value = "/addlabel")
	@ResponseBody
	public Map<String, Object> addlabel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String labelType = multipartRequest.getParameter("labelType");
			String labelName = multipartRequest.getParameter("labelName");
			String labelSort = multipartRequest.getParameter("labelSort");
			String homeFlag = multipartRequest.getParameter("homeFlag");
			int status = Integer.parseInt(multipartRequest.getParameter("status"));
			LabelVo labelVo = new LabelVo();
			labelVo.setLabelName(labelName);
			labelVo.setLabelType(Integer.parseInt(labelType));
			labelVo.setLabelSort(Integer.parseInt(labelSort));
			labelVo.setSearchCount(0);
			labelVo.setStatus(status);
			labelVo.setHomeFlag(Integer.parseInt(homeFlag));
			if (homeFlag.equals("1")) {
				labelManager.updateHomeFlag();
			}
			
			int	iret = labelManager.insert(labelVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/label/addlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
	/**
	 * 批量删除标签
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delLabel")
	@ResponseBody
	public Map<String, Object> delLabel(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String labelIdArray = request.getParameter("labelIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(labelIdArray)) {
				String[] labelIds = labelIdArray.split(",");
				for (String labelId : labelIds) {
					
					iret = labelManager.deleteByPrimaryKey(Integer.parseInt(labelId));
							
				}
			}
			
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "删除失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/label/delLabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "删除成功");
		return map;
	}
	
	/**
	 * 跳转更新标签界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdateLabel")
	public ModelAndView toUpdateLabel(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/label/update_label");
		PageData pd = this.getPageData();
		int labelId = pd.getInteger("labelId");
		PageData pd1 = new PageData();
		pd1.put("labelId", labelId);
		LabelVo vo = labelManager.queryOne(pd1);
		
		model.addAttribute("label", vo);
		
		return mv;
	}
	
	@RequestMapping(value = "/editlabel")
	@ResponseBody
	public Map<String, Object> editlabel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			String chooseProductIds = request.getParameter("chooseProductIds");
			String labelId = request.getParameter("labelId");
			int iret = 0;
			PageData pd = this.getPageData();
			pd.put("labelId", labelId);
			LabelVo labelVo = labelManager.queryOne(pd);
			
			if (null != chooseProductIds) {
				if (",".equals(chooseProductIds)) {
					chooseProductIds = "";
				}
				labelVo.setProductIds(chooseProductIds);
				iret = labelManager.updateByPrimaryKey(labelVo);
				if (iret <= 0) {
					map.put("result", "fail");
					map.put("msg", "匹配商品失败");
					return map;
				}
			}else{
				String labelType = request.getParameter("labelType");
				String labelName = request.getParameter("labelName");
				String labelSort = request.getParameter("labelSort");
				String status = request.getParameter("status");
				String homeFlag = request.getParameter("homeFlag");
				
				labelVo.setLabelName(labelName);
				labelVo.setLabelType(Integer.parseInt(labelType));
				labelVo.setLabelSort(Integer.parseInt(labelSort));
				labelVo.setLabelId(Integer.parseInt(labelId));
				labelVo.setStatus(Integer.parseInt(status));
				labelVo.setHomeFlag(Integer.parseInt(homeFlag));
				if (homeFlag.equals("1")) {
					labelManager.updateHomeFlag();
				}
				iret = labelManager.updateByPrimaryKey(labelVo);
				if (iret <= 0) {
					map.put("result", "fail");
					map.put("msg", "修改失败");
					return map;
				}
			}
		} catch (Exception e) {
			logger.error("/admin/productlabel/editproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
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
