package com.kingleadsw.betterlive.controller.web.productspec;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.ProductSpecVo;

/**
 * 商品
 * 2017-03-08 by chen
 * @author Coder
 */
@Controller
@RequestMapping("/admin/productspec")
public class ProductSpecController  extends AbstractWebController{
	protected Logger logger = Logger.getLogger(ProductSpecController.class);
	
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	
	
	/**
	 * 查询商品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryListProductSpecJson")
	@ResponseBody
	public void queryListProductSpecJson(HttpServletRequest request,HttpServletResponse response) {
		JSONObject json=new JSONObject();
		
		PageData pd = this.getPageData();
		
		List<ProductSpecVo>  listProductSpec =productSpecManager.queryListProductSpec(pd);
		
		if(listProductSpec!=null&&!listProductSpec.isEmpty() && listProductSpec.size()>0){
			json.put("result", "succ");
			json.put("list", listProductSpec);
		}else{
			json.put("result", "fail");
			json.put("msg", "查询规格失败");
		}
		outJson(json.toString(), response);
		
	}
	
	/**
	 * 查询商品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryListProductSpecJsonToADD")
	@ResponseBody
	public void queryListProductSpecJsonToADD(HttpServletRequest request,HttpServletResponse response) {
		JSONObject json=new JSONObject();
		
		PageData pd = this.getPageData();
		
		List<ProductSpecVo>  listProductSpec =productSpecManager.queryProductSpecListPage(pd);
		
		if(listProductSpec!=null&&!listProductSpec.isEmpty() && listProductSpec.size()>0){
			json.put("result", "succ");
			json.put("list", listProductSpec);
		}else{
			json.put("result", "fail");
			json.put("msg", "查询规格失败");
		}
		outJson(json.toString(), response);
		
	}
	
	
	
}
