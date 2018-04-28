package com.kingleadsw.betterlive.controller.web.product;

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

import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductVo;

@Controller
@RequestMapping("/admin/productlabel")
public class ProductlabelController extends AbstractWebController {

	protected Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private ProductLabelManager productLabelManager;

	@Autowired
	private ProductManager productManager;

	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/productlabel/list_product_label");
		return mv;
	}

	/**
	 * 分页查询商品标签信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryProductLabelAllJson")
	@ResponseBody
	public void queryProductLabelAllJson(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<ProductLabelVo> labels = this.productLabelManager
				.queryProductList(pd);
		if (labels != null) {
			this.outEasyUIDataToJson(pd, labels, response);
		} else {
			this.outEasyUIDataToJson(pd, new ArrayList<ProductLabelVo>(),
					response);
		}
	}

	/**
	 * 新增专题页面
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddProductlabel")
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse resp, Model model) {
		ModelAndView mv = new ModelAndView(
				"admin/productlabel/add_productlabel");
		PageData pd = this.getPageData();

		List<ProductVo> products = productManager.queryProductListByLabel(pd);

		model.addAttribute("products", products);
		return mv;
	}

	@RequestMapping(value = "/addproductlabel")
	@ResponseBody
	public Map<String, Object> addproductlabel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String labelType = multipartRequest.getParameter("labelType");
			String labelTitle = multipartRequest.getParameter("labelTitle");
			String showStart = multipartRequest.getParameter("showStart");
			String showEnd = multipartRequest.getParameter("showEnd");
			ProductLabelVo productlabelvo = new ProductLabelVo();
			productlabelvo.setLabelTitle(labelTitle);
			productlabelvo.setLabelType(Integer.parseInt(labelType));
			productlabelvo.setShowStart(showStart);
			productlabelvo.setShowEnd(showEnd);
			productlabelvo.setStatus(0);

			String productIdArray = multipartRequest
					.getParameter("notDispatchs");
			int iret = 0;
			if (StringUtils.isNotBlank(productIdArray)) {
				String[] productId = productIdArray.split(",");
				for (String pid : productId) {
					productlabelvo.setProductId(Integer.parseInt(pid));
					iret = productLabelManager
							.insertProductlabel(productlabelvo);
				}
			}
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "新增失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/productlabel/addproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "新增成功");
		return map;
	}
	
	@RequestMapping(value = "/editproductlabel")
	@ResponseBody
	public Map<String, Object> editproductlabel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			String labelType = multipartRequest.getParameter("labelType");
			String labelTitle = multipartRequest.getParameter("labelTitle");
			String showStart = multipartRequest.getParameter("showStart");
			String showEnd = multipartRequest.getParameter("showEnd");
			String productLabelId = multipartRequest.getParameter("productLabelId");
			ProductLabelVo productlabelvo = new ProductLabelVo();
			productlabelvo.setLabelTitle(labelTitle);
			productlabelvo.setLabelType(Integer.parseInt(labelType));
			productlabelvo.setShowStart(showStart);
			productlabelvo.setShowEnd(showEnd);
			productlabelvo.setProductLabelId(Integer.parseInt(productLabelId));
			int iret = productLabelManager.editproductlabel(productlabelvo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "修改失败");
				return map;
			}
		} catch (Exception e) {
			logger.error("/admin/productlabel/editproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "修改成功");
		return map;
	}
	
	
	@RequestMapping(value="/delProductlabel")
	@ResponseBody
	public Map<String, Object> delProductlabel(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String productIdArray = request.getParameter("productIdArray");
			int iret = 0;
			if (StringUtils.isNotBlank(productIdArray)) {
				String[] productId = productIdArray.split(",");
				for (String productLabelId : productId) {
					
					iret = productLabelManager.delProductlabel(Integer.parseInt(productLabelId));
							
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
	
	/**
	 * 跳转更新商品界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdateProductlabel")
	public ModelAndView toUpdateProduct(HttpServletRequest request, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/productlabel/update_productlabel");
		PageData pd = this.getPageData();
		pd.put("parentId", 0);
		ProductLabelVo vo = productLabelManager.queryOne(pd);
		
		model.addAttribute("productlabel", vo);
		
		return mv;
	}
	
	
	
}
