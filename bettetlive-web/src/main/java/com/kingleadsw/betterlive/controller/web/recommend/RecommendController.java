package com.kingleadsw.betterlive.controller.web.recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ExtensionManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.ExtensionVo;
import com.kingleadsw.betterlive.vo.ProductVo;
/**
 * 人气推荐商品
 * @author zhangjing
 * @date 2017年3月13日 下午3:38:23
 */
@Controller
@RequestMapping("/admin/recommend")
public class RecommendController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(RecommendController.class);
	@Autowired
	private ExtensionManager extensionManager;
	
	@Autowired
	private ProductManager productManager;
	
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		pd.put("stauts", 1);
		ModelAndView mv = new ModelAndView("admin/recommend/list_recommend");
		List<ProductVo> products = productManager.queryProductList(pd);
		
		pd.put("extensionType", 2);
		pd.put("isHomepage", 1);
		List<ExtensionVo> list = extensionManager.queryListPage(pd);
			
		List<ProductVo> productlist = products;
		if(list!=null&&list.size()!=0){
			for (ExtensionVo extensionVo : list) {
				for (ProductVo productVo : products) {
					if(extensionVo.getProductId().equals(productVo.getProduct_id())&&extensionVo.getExtensionType()==2){
						productlist.remove(productVo);
						break;
					}
				}
			}
		}
		
		model.addAttribute("size", list.size());
		model.addAttribute("products", productlist);
		return mv;
	}
	
	
	/**
	 * 查询推荐商品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryReconmmendAllJson")
	@ResponseBody
	public void queryReconmmendAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		pd.put("extensionType", 2);
		List<ExtensionVo> list = extensionManager.queryListPage(pd);
		
		if(null !=list && list.size()>0){
			
			for (ExtensionVo extensionVo : list) {
				pd.put("productId", extensionVo.getProductId());
				ProductVo pvo = productManager.selectProductByOption(pd);
				extensionVo.setProductName(pvo.getProduct_name());
			}
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<ProductVo>(), response);
		}
		
	}
	

	/**
	 * 根据id查询人气推荐
	 */
	@RequestMapping(value="/queryRecommendJson")
	@ResponseBody
	public void queryRecommendJson(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String extensionId=pd.getString("extensionId");
		
		JSONObject json = new JSONObject();
		if(StringUtil.isEmpty(extensionId)){
			json.put("msg", "extensionId不能为空");
			return;
		}
		pd.put("extensionId", Integer.parseInt(extensionId));
		ExtensionVo evo = extensionManager.selectExtensionByOption(pd);
		
		if(evo!=null){
			json.put("code", "1010");
			json.put("data", evo);
			pd.put("productId",evo.getProductId());
			ProductVo pvo = productManager.selectProductByOption(pd);
			evo.setProductName(pvo.getProduct_name());
			PageData pageData = new PageData();
			pageData.put("stauts", 1);
			List<ProductVo> products = productManager.queryProductList(pageData);
			json.put("products", products);
			this.outObjectToJson(json, response);
		}else{
			json.put("msg", "没有该记录");
			this.outObjectToJson(json, response);
		}
	}
	
	
	
	
	
	@RequestMapping(value="/addRecommend")
	@ResponseBody
	public void addRecommend(HttpServletRequest request, HttpServletResponse response,ExtensionVo extensionVo){
		logger.info("连接地址： /admin/recommend/addRecommend ,操作：增加/修改推荐商品信息。  操作状态： 开始");
		int count=0;
		JSONObject json = new JSONObject();
		extensionVo.setExtensionType(Integer.parseInt("2"));
		if(null!=extensionVo.getExtensionId()&&extensionVo.getExtensionId()>0){//修改
			count = extensionManager.updateExtension(extensionVo);
			if(count>0){
        		json.put("result", "succ");
				json.put("msg", "修改推荐商品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "修改推荐商品失败！");
        	}
		}else{//新增
			count = extensionManager.insertExtension(extensionVo);
			if(count>0){
        		json.put("result", "succ");
				json.put("msg", "增加推荐商品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "增加推荐商品失败！");
        	}
		}
		this.outObjectToJson(json, response);
		logger.info("连接地址：  /admin/recommend/addRecommend  ,操作：增加/修改推荐商品信息。  操作状态：结束");
	}
	
	
	/**
	 * 根据id删除人气推荐
	 */
	@RequestMapping(value="/delRecommend")
	@ResponseBody
	public Map<String,Object> delRecommend(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String extensionId=pd.getString("extensionId");
		if(StringUtil.isEmpty(extensionId)){
			return CallBackConstant.FAILED.callback("extensionId不能为空");
		}
		int result = extensionManager.deleteExtensionById(Integer.parseInt(extensionId));
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback("没有该记录");
		}
	}
	

	
}
