package com.kingleadsw.betterlive.controller.web.banner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.BannerInfoManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.BannerInfoVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVo;



/**
 * 首页banner图管理controller
 */
@Controller
@RequestMapping(value = "/admin/banner")
public class AdminBannerController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(AdminBannerController.class);
	
	@Autowired
	private BannerInfoManager bannerInfoManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private SpecialMananger specialMananger;
	
	/**
	 * 跳转首页banner图管理页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/tobannerlist")
	public ModelAndView findListAccessories(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/admin/banner/list_banner");
		return mv;
	}
	
	
	/**
	 * 查询banner列表
	 */
	@RequestMapping(value="/querybannerlistpage")
	@ResponseBody
	public void queryBannerListPage(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		List<BannerInfoVo>  list=bannerInfoManager.queryBannersListPage(pd);
		outEasyUIDataToJson(pd, list, response);
	}
	
	
	/**
	 * 增加或修改banner
	 */
	@RequestMapping(value="/addbanner")
	@ResponseBody
	public Map<String,Object> addBanner(HttpServletRequest request, HttpServletResponse response,BannerInfoVo banner){
		
		int result=0;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile bannerImg = multipartRequest.getFile("bannerImg1");
		String homeFilePath = "bannerHome/";
		String insertPannerImg = "";
		long fileSize = 1024 * 1024;
    	if(bannerImg.getSize()>fileSize){    		
    		return CallBackConstant.FAILED.callback("请选择小于1兆的图片");
		}
    	insertPannerImg =ImageUpload.uploadFile(bannerImg, homeFilePath);
		if(StringUtil.isNotEmpty(insertPannerImg)){
			banner.setBannerImg(insertPannerImg);
		}
		
		if(banner.getBannerId()!=null&&banner.getBannerId()>0){
			result=bannerInfoManager.updateByPrimaryKeySelective(banner);
		}else{
			banner.setCreateTime(new Date());
			result=bannerInfoManager.insertSelective(banner);
		}
		
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * banner 类型变化查出相应的实体
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bannerTypeChange")
	@ResponseBody
	public Map<String,Object> bannerTypeChange(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String type = pd.getString("bannerType");//1：系统文章，2：预购id，3：专题活动id，4：产品id
		Map<String,Object> map = new HashMap<String, Object>();
		pd.put("status", 1);
		try {
			if(type.equals("2")){
				List<PreProductVo> list = preProductManager.queryList(pd);//2：预购id
				map.put("list", list);
				map.put("bannerUrl", "weixin/prepurchase/toPreProductdetail?productId=");
			}else if(type.equals("3")){//专题活动
				pd.put("specialType", 1);
				List<SpecialVo> list = specialMananger.querySpecialList(pd);
				map.put("list", list);
				map.put("bannerUrl", "");
			}else if(type.equals("4")){//4：产品id
				List<ProductVo> list = productManager.queryProductList(pd);
				map.put("list", list);
				map.put("bannerUrl", "weixin/product/towxgoodsdetails?productId=");
			}
		} catch (Exception e) {
			logger.error("/admin/banner/bannerTypeChange -- error", e);
		}
		return map;
	}
	
	/**
	 * 根据id查询banner
	 */
	@RequestMapping(value="/querybannerjson")
	public void queryBannerJson(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String bannerId=pd.getString("bannerId");
		JSONObject json = new JSONObject();
		if(StringUtil.isEmpty(bannerId)){
			json.put("msg", "bannerid不能为空");
			return;
		}
		
		BannerInfoVo bannerInfo = bannerInfoManager.selectByPrimaryKey(Integer.parseInt(bannerId));
		if(bannerInfo!=null){
			json.put("code", "1010");
			json.put("data", bannerInfo);
			this.outObjectToJson(json, response);
		}else{
			json.put("msg", "没有该记录");
			this.outObjectToJson(json, response);
		}
	}
	
	/**
	 * 根据id删除banner
	 */
	@RequestMapping(value="/delbanner")
	@ResponseBody
	public Map<String,Object> delbanner(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String bannerId=pd.getString("bannerId");
		if(StringUtil.isEmpty(bannerId)){
			return CallBackConstant.FAILED.callback("bannerid不能为空");
		}
		
		int result = bannerInfoManager.deleteByPrimaryKey(Integer.parseInt(bannerId));
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback("没有该记录");
		}
	}
	
	
	
	
}
