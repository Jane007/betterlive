package com.kingleadsw.betterlive.controller.web.preproduct;

import java.math.BigDecimal;
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

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.ExtensionManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductVo;

/***
 * 预售商品 控制
 * @author zhangjing
 * @date 2017年3月11日 下午4:32:41
 */
@Controller
@RequestMapping("/admin/preproduct")
public class AdminPreProductController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(AdminPreProductController.class);
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private ExtensionManager extensionManager;
	
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/pre/list_preproduct");
		return mv;
	}

	@RequestMapping(value="/queryPreProductAllJson")
	@ResponseBody
	public void queryPreProductAllJson(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		List<PreProductVo> list = preProductManager.queryListPage(pd);
		if(null !=list && list.size()>0){
			for (PreProductVo preProductVo : list) {
				pd.put("activityId", preProductVo.getPreId());
				List<ActivityProductVo> l = activityProductManager.queryList(pd);
				preProductVo.setActivityProductVo(l);
			}
		
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<PreProductVo>(), response);
		}
	}
	
	
	@RequestMapping(value="/toAddPreProduct")
	public ModelAndView toAddPreProduct(HttpServletRequest request,HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/pre/add_preproduct");
		PageData pd = this.getPageData();
		pd.put("activityType", 1);
		List<ProductVo> products = productManager.queryNotExistInExtension(pd);
		
		model.addAttribute("products", products);
		return mv;
	}
	
	@RequestMapping(value="/toEditPreProduct")
	public ModelAndView toEditPreProduct(HttpServletRequest request,HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		String preId = (String) pd.get("preId");
		pd.put("preId", Integer.parseInt(preId));
		pd.put("status", 1);
		PreProductVo preProduct = preProductManager.selectPreProductByOption(pd);
		ModelAndView mv = new ModelAndView("admin/pre/edit_preproduct");
		List<ProductVo> products = productManager.queryProductList(pd);
		model.addAttribute("products", products);
		model.addAttribute("preProduct", preProduct);
		return mv;
	}
	@RequestMapping(value="/addPreProduct")
	@ResponseBody
	public void addPreProduct(HttpServletRequest request, HttpServletResponse response,PreProductVo preProductVo){
		logger.info("连接地址： /admin/preproduct/addPreProduct ,操作：增加/修改预售商品信息。  操作状态： 开始");
		
		int count=0;
		JSONObject json = new JSONObject();
		 //商品规格与价格总数
        String teaTypeLength=request.getParameter("teaTypeLength");         
        String teaId=null;
        String teaActivityPrices=null;
        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
        ActivityProductVo activityProductVo=null;
        BigDecimal lowPrice = BigDecimal.ZERO;
		if(null!=preProductVo.getPreId()&&preProductVo.getPreId()>0){//修改
			
			PageData pd = this.getPageData();
			pd.put("activityId", preProductVo.getPreId());
			listactivityProductSpec = activityProductManager.queryList(pd);
			if(listactivityProductSpec!=null){
				lowPrice = new BigDecimal(listactivityProductSpec.get(0).getActivity_price());
			}
			
			for (ActivityProductVo activtyVo : listactivityProductSpec) {
				teaActivityPrices=request.getParameter("teaSizeActivityPrice_"+activtyVo.getSpec_id());
				activtyVo.setActivity_price(teaActivityPrices);
				activityProductManager.updateByPrimaryKey(activtyVo);
				if(new BigDecimal(teaActivityPrices).compareTo(lowPrice)==-1){
					lowPrice = new BigDecimal(teaActivityPrices);
				}
				
			}
			preProductVo.setDiscountPrice(lowPrice);
			count = preProductManager.updatePreProduct(preProductVo);
			if(count>0){
        		json.put("result", "succ");
				json.put("msg", "修改预售商品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "修改预售商品失败！");
        	}
		}else{//新增
			 count = preProductManager.insertPreProduct(preProductVo);
			if(Integer.parseInt(teaTypeLength)>0){
				lowPrice = new BigDecimal(request.getParameter("teaSizeActivityPrice_1"));
			}
			  for (int i =1; i < Integer.parseInt(teaTypeLength)+1; i++) {
		        	teaId=request.getParameter("teaId_"+i);
		        	teaActivityPrices=request.getParameter("teaSizeActivityPrice_"+i);
		        	if("".equals(teaId) || null ==teaId ||"".equals(teaActivityPrices) || null ==teaActivityPrices){
		        		
		        		json.put("result", "fail");
						json.put("msg", "规格与优惠价格为空！位置："+i);
		        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品活动。  操作状态： 失败！ 原因:  规格与优惠价格出现错误  位置： "+i);
		        	}else{
		        		activityProductVo=new ActivityProductVo();
		        		activityProductVo.setActivity_id(preProductVo.getPreId());
		        		activityProductVo.setSpec_id(Integer.parseInt(teaId));
		        		activityProductVo.setActivity_price(teaActivityPrices);
		        		activityProductVo.setActivity_type(1);//1是预售 2是专题
		        		activityProductVo.setProduct_id(preProductVo.getProductId());
		        		listactivityProductSpec.add(activityProductVo);
		        		if(new BigDecimal(teaActivityPrices).compareTo(lowPrice)==-1){
		        			lowPrice = new BigDecimal(teaActivityPrices);
		        		}
		        	}
				}
			  preProductVo.setDiscountPrice(lowPrice);
			  count = preProductManager.updatePreProduct(preProductVo);
			  int result=activityProductManager.addBatchActivityProduct(listactivityProductSpec);
			  
			  //判断该商品的活动价格是否比单品的设置价格低
			  
			if(count>0&&result>0){
        		json.put("result", "succ");
				json.put("msg", "增加预售商品成功！");
        	}else{
        		json.put("result", "fail");
				json.put("msg", "增加预售商品失败！");
        	}
		}
		this.outObjectToJson(json, response);
		logger.info("连接地址： /admin/preproduct/addPreProduct ,操作：增加/修改预售商品信息。  操作状态：结束");
	}
	
	
	
	@RequestMapping(value="/delPreProduct")
	@ResponseBody
	public Map<String,Object> delPreProduct(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String preId=pd.getString("preId");
		if(StringUtils.isBlank(preId)){
			return CallBackConstant.FAILED.callback("预售id不能为空");
		}
		activityProductManager.deleteActivityProductByAid(preId);//预售规格对应的价格删除
		int result = preProductManager.deletePreProductById(Integer.parseInt(preId));
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback("没有该记录");
		}
	}
}
