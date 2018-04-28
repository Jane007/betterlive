package com.kingleadsw.betterlive.controller.web.newpro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
/***
 * 每周新品推荐
 * @author zhangjing
 * @date 2017年3月14日 上午9:55:06
 */
@Controller
@RequestMapping("/admin/newproduct")
public class NewProductController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(NewProductController.class);
	@Autowired
	private ExtensionManager extensionManager;
	
	@Autowired
	private ProductManager productManager;
	
	//每周新品推荐的商品
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		pd.put("stauts", 1);
		ModelAndView mv = new ModelAndView("admin/new/list_new");
		List<ProductVo> products = productManager.queryProductList(pd);
		
		pd.put("extensionType", 1);
		pd.put("isHomepage", 1);
		List<ExtensionVo> list = extensionManager.queryListPage(pd);
		List<ProductVo> productlist = products;
		if(list!=null&&list.size()!=0){
			for (ExtensionVo extensionVo : list) {
				for (ProductVo productVo : products) {
					if(extensionVo.getProductId().equals(productVo.getProduct_id())&&extensionVo.getExtensionType()==1){
						productlist.remove(productVo);
						break;
					}
				}
			}
		}
		
		
		model.addAttribute("size", list.size());
		model.addAttribute("products", productlist);//每周新品推荐的商品列表
		return mv;
	}
	
	
	/**
	 * 查询每周新品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryNewAllJson")
	@ResponseBody
	public void queryNewAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		pd.put("extensionType", 1);
		List<ExtensionVo> list = extensionManager.queryListPage(pd);
		
		if(null !=list && list.size()>0){
			
			for (ExtensionVo extensionVo : list) {
				pd.put("productId", extensionVo.getProductId());
				pd.put("status","1");
				ProductVo pvo = productManager.selectProductByOption(pd);
				extensionVo.setProductName(pvo.getProduct_name());
			}
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<ExtensionVo>(), response);
		}
		
	}
	
	/**
	 * 根据id查询每周新品
	 */
	@RequestMapping(value="/queryNewJson")
	@ResponseBody
	public void queryNewJson(HttpServletRequest request, HttpServletResponse response){
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
			pd.put("status","1");
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
	
	
	@RequestMapping(value="/addNew")
	@ResponseBody
	public void addRecommend(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/newproduct/addNew ,操作：增加/修改每周新品信息。  操作状态： 开始");
		//PageData pd = new PageData();
		
		PageData pd = this.getPageData();
		int count=0;
		JSONObject json = new JSONObject();
		
		PageData extpd = new PageData();
		extpd.put("productId", pd.getString("productId"));
		extpd.put("extensionType", pd.getString("extensionType"));
		ExtensionVo extensionVo = extensionManager.selectExtensionByOption(extpd);
		
//		List<ExtensionVo> list = new ArrayList<ExtensionVo>();
//		if(StringUtils.isNotBlank(ps.getString("isHomepage"))&&ps.getString("isHomepage").equals("1")){
//			ps.put("productId", null);
//			ps.put("isHomepage", ps.getInteger("isHomepage"));
//			list = extensionManager.queryList(ps);
//		}
//		if(list!=null&&list.size()>=5){
//			json.put("result", "fail");
//			if(StringUtils.isNotBlank(pd.getString("extensionType"))&&pd.getString("extensionType").equals("1")){
//				json.put("msg", "每周新品首页最多是四个！");
//			}else{
//				json.put("msg", "人气推荐首页最多是四个！");
//			}
//			
//		}else{
		if(null!=extensionVo){//修改
			if(StringUtils.isNotBlank(pd.getString("isHomepage"))){
				extensionVo.setIsHomepage(Integer.parseInt(pd.getString("isHomepage")));
			}
			if(StringUtils.isNotBlank(pd.getString("extensionType"))){
				extensionVo.setExtensionType(Integer.parseInt(pd.getString("extensionType")));
			}
			count = extensionManager.updateExtension(extensionVo);
			if(count>0){
        		json.put("result", "succ");
				json.put("msg", "修改每周新品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "修改每周新品失败！");
        	}
		}else{//新增
			extensionVo = new ExtensionVo();
			extensionVo.setExtensionType(Integer.parseInt(pd.getString("extensionType")));
			extensionVo.setIsHomepage(Integer.parseInt("0"));
			extensionVo.setProductId(pd.getInteger("productId"));
			count = extensionManager.insertExtension(extensionVo);
			if(count>0){
        		json.put("result", "succ");
				json.put("msg", "增加每周新品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "增加每周新品失败！");
        	}
		}
	
	this.outObjectToJson(json, response);
	logger.info("连接地址： /admin/newproduct/addNew ,操作：增加/修改每周新品信息。    操作状态：结束");
}
	
	/**
	 * 根据id删除每周新品
	 */
	@RequestMapping(value="/delNew")
	@ResponseBody
	public Map<String,Object> delNew(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		
		ExtensionVo extensionVo = extensionManager.selectExtensionByOption(pd);
		if(extensionVo==null){
			return CallBackConstant.SUCCESS.callback();
		}else{
			int result = extensionManager.deleteExtensionById(extensionVo.getExtensionId());
			if(result>0){
				return CallBackConstant.SUCCESS.callback();
			}else{
				return CallBackConstant.FAILED.callback("没有该记录");
			}
		}
		
	}
	

}
