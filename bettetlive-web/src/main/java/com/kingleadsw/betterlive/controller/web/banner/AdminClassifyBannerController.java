package com.kingleadsw.betterlive.controller.web.banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ClassifyBannerManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.ClassifyBannerVo;
import com.kingleadsw.betterlive.vo.ProductVo;

/**
 * 商品广告Banner
 *
 */
@Controller
@RequestMapping(value = "/admin/classifybanner")
public class AdminClassifyBannerController  extends AbstractWebController{
	@Autowired
	private ClassifyBannerManager classifyBannerManager;
	@Autowired
	private ProductManager productManager;
	
	/**
	 * 商品列表页Banner
	 * @param httpRequest
	 * @return
	 */
	/**
	 * 根据id查询banner
	 */
	@RequestMapping(value="/queryclassfiybannerjson")
	@ResponseBody
	public Map<String,Object> queryclassfiybannerjson(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String bannerId=pd.getString("classifyBannerId");
		if(StringUtil.isEmpty(bannerId)){
			return CallBackConstant.PARAMETER_ERROR.callback("ID为空");
		}
		
		ClassifyBannerVo bannerInfo = classifyBannerManager.queryOne(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(bannerInfo!=null){
			map.put("flag", 1);
			map.put("bannerVo", bannerInfo);
			return map;
		}
		map.put("flag", 0);
		return map;
	}
	
	
	
	@RequestMapping(value = "/findList")
	public ModelAndView findListTaste(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView(
				"admin/classifybanner/list_classifybanner");
		PageData pd = new PageData();
		pd.put("productStatus", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);
		mv.addObject("products", products);
		return mv;
	}
	/**
	 * 查询分类banner
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryclassifybannerlistpage")
	@ResponseBody
	public void queryclassifybannerListPage(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		List<ClassifyBannerVo>  list= classifyBannerManager.queryclassifybannerListPage(pd);
		if (null == list) {
			list = new ArrayList<ClassifyBannerVo>();
		}
		outEasyUIDataToJson(pd, list, response);
	}
	
	/**
	 * 增加或修改banner
	 */
	@RequestMapping(value="/addclassifybanner")
	@ResponseBody
	public Map<String,Object> addclassifybanner(HttpServletRequest request, HttpServletResponse response,ClassifyBannerVo banner){
		
		int result=0;
		if(banner.getClassifyBannerId()!=null&&banner.getClassifyBannerId()>0){
			result=classifyBannerManager.updateByPrimaryKey(banner);
		}else{
			result=classifyBannerManager.addclassifybanner(banner);
		}
		
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 根据id删除banner
	 */
	@RequestMapping(value="/delclassfiybanner")
	@ResponseBody
	public Map<String,Object> delbanner(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String bannerId=pd.getString("classifyBannerId");
		if(StringUtil.isEmpty(bannerId)){
			return CallBackConstant.FAILED.callback("bannerid不能为空");
		}
		
		int result = classifyBannerManager.deleteByPrimaryKey(Integer.parseInt(bannerId));
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback("没有该记录");
		}
	}
	
}
