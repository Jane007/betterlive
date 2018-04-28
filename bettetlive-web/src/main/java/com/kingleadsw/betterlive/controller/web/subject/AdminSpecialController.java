package com.kingleadsw.betterlive.controller.web.subject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SpecialVideoTypeManager;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.core.util.ZipUtil;
import com.kingleadsw.betterlive.init.FileUpload;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
/***
 * 后台对专题管理
 * @author zhangjing
 * @date 2017年3月8日 下午2:22:17
 */
@Controller
@RequestMapping("/admin/special")
public class AdminSpecialController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(AdminSpecialController.class);
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private SpecialVideoTypeManager specialVideoTypeManager;
	
	
	//专题活动上传到服务器存储路径
	private static final String SUBJECT_PATH = WebConstant.UPLOAT_ROOT_PATH 
			+ File.separator + "subject" + File.separator;
	
	/**
	 * 专题
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/special/list_special");
		return mv;
	}
	
	/**
	 * 专题：限量抢购
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findLimitList")
	public ModelAndView findLimitList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/special/list_limit_special");
		PageData ppd = new PageData();
		ppd.put("picture_type", 4);
		PicturesVo limitPic = picturesManager.queryOne(ppd);
		if(limitPic == null){
			limitPic = new PicturesVo();
			limitPic.setPicture_type(4);
		}
		mv.addObject("limitPic", limitPic);
		return mv;
	}
	
	/**
	 * 专题：限量抢购
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findGroupList")
	public ModelAndView findGroupList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/special/list_group_special");
		PageData ppd = new PageData();
		ppd.put("picture_type", 3);
		PicturesVo groupPic = picturesManager.queryOne(ppd);
		if(groupPic == null){
			groupPic = new PicturesVo();
			groupPic.setPicture_type(3);
		}
		mv.addObject("groupPic", groupPic);
		return mv;
	}
	
	/**
	 * 专题：美食教程
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findTutorialList")
	public ModelAndView findTutorialList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/special/list_tutorial_special");
		return mv;
	}
	
	
	/**
	 * 查询限时活动，限量抢购，拼团活动
	 * specialType 1：限时活动， 2限量抢购，3团购活动
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialAllJson")
	@ResponseBody
	public void querySpecialAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("specialType"))){
			pd.put("specialType", 1);
		}
		
		List<SpecialVo> list = specialMananger.querySpecialListPage(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<SpecialVo>(), response);
		}
	}
	
	/**
	 * 查询所有专题
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/querySpecialListJson")
	@ResponseBody
	public Map<String,Object> querySpecialListJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<SpecialVo> list =null;
		
		JSONObject json=new JSONObject();
		try {
			if(StringUtil.isNull(pd.getString("specialType"))){
				pd.put("specialType", 1);
			}
			list = specialMananger.querySpecialList(pd);
		} catch (Exception e) {
			logger.error("查询出现异常");
			
		}
		
		if(null==list || list.isEmpty()){
			list=new ArrayList<SpecialVo>();
		}
		
		json.put("list", list);
		return CallBackConstant.SUCCESS.callback(json);
	}
	
	
	/**
	 * 新增专题页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddSpecial")
	public ModelAndView toAdd(HttpServletRequest request,HttpServletResponse resp,Model model){
		ModelAndView md = new ModelAndView("admin/special/add_special");
		PageData pd = this.getPageData();
		//排除已有活动的商品
		pd.put("hasNoPro", 1);
		pd.put("productStatus", 1);
		pd.put("isOnline", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);    
		
		model.addAttribute("products", products);
		return md;
	}
	
	
	@RequestMapping(value="/querySpecByProjectId")
	public void querySpecByProjectId(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		String productIdArray = pd.getString("productIdArray");
		Map<String, Object> respMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(productIdArray)){
			String[] productId=productIdArray.split(",");
			for (String pid : productId) {
				pd.put("productId", Integer.parseInt(pid));
				ProductVo pvo = productManager.selectProductByOption(pd);
				List<ProductSpecVo> list = productSpecManager.queryList(pd);
				if(respMap.get(pvo.getProduct_name()+"_"+pvo.getProduct_id())==null){
					respMap.put(pvo.getProduct_name()+"_"+pvo.getProduct_id(), list);
				}
			}
		}
		this.outToJson(respMap, resp);
	}
	
	
	@RequestMapping(value="/querySpecByProjectIdModify")
	public void querySpecByProjectIdModify(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		String productIdArray = pd.getString("productIdArray");
		String sid = (String) pd.get("specialId");
		if(StringUtils.isNotBlank(sid)){
			pd.put("activityId", Integer.parseInt(sid));
		}
		Map<String, Object> respMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(productIdArray)){
			if(productIdArray.endsWith(",")){
				productIdArray = productIdArray.substring(0,productIdArray.length()-1);
			}
			String[] productId=productIdArray.split(",");
		
			for (String pid : productId) {
				pd.put("productId", Integer.parseInt(pid));
				ProductVo pvo = productManager.selectProductByOption(pd);
				List<ProductSpecVo> list = new ArrayList<ProductSpecVo>();
				if(StringUtils.isBlank(sid)){
					list = pvo.getListSpecVo();
				}else{
					list = productSpecManager.queryListProductSpec(pd);
				}
				if(list==null||list.size()==0){
					list = productSpecManager.queryList(pd);
				}
				if(respMap.get(pvo.getProduct_name()+"_"+pvo.getProduct_id())==null){
					respMap.put(pvo.getProduct_name()+"_"+pvo.getProduct_id(), list);
				}
			}
		}
		this.outToJson(respMap, resp);
	}
	
	@RequestMapping(value="/toEditSpecial")
	public ModelAndView toEditSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		String sid = (String) pd.get("specialId");
		
		
		//pd.put("activityType",1);
		//List<ProductVo> list = productManager.queryNotExistInExtension(pd);
		
		//选择产品
//		PageData pds = this.getPageData();
//		pds.put("activityType", 1);
//		List<ActivityProductVo> yushou = activityProductManager.queryList(pd);//预售产品
//		List<ProductVo> list  = new ArrayList<ProductVo>();
		pd.put("productStatus", 1);
		pd.put("hasNoPro", 1);
		pd.put("editFlag", 1);
		pd.put("isOnline", 1);
		List<ProductVo> list = productManager.queryProductList(pd);
//		for (ProductVo productVo : productslist) {
//			for (ActivityProductVo activityProductVo : yushou) {
//				if(productVo.getProduct_id()!=activityProductVo.getProduct_id()){ 
//					if(!list.contains(productVo)){
//						list.add(productVo);
//					}
//					
//				}
//			}
//		}
		
		
		
		
		pd.put("specialId", Integer.parseInt(sid));
		SpecialVo special = specialMananger.selectSpecialByOption(pd);
		PageData pdt = this.getPageData();
		pdt.put("activityId", Integer.parseInt(sid));
		pdt.put("activityType",2);
		List<ActivityProductVo> listactivityProductSpec=activityProductManager.queryList(pdt);
		List<ProductSpecVo> speclist = new ArrayList<ProductSpecVo>();
		List<ProductVo> products = new ArrayList<ProductVo>();
		String activtylistId = "";
		String productIds = "";
		String productName = "";
		Set<ProductVo> set = new HashSet<ProductVo>();
		if(listactivityProductSpec!=null&&listactivityProductSpec.size()!=0){
			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
				pd.put("productId",null);
				pd.put("specId", activityProductVo.getSpec_id());
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
				pd.put("productId",pspec.getProduct_id());
				ProductVo product = productManager.selectProductByOption(pd);
				activtylistId +=activityProductVo.getActivity_spec_id()+",";
				speclist.add(pspec);
				if(!set.contains(product)){
					set.add(product);
				}
				
			}
		
			if(set!=null&&set.size()>0){
				for (ProductVo productVo : set) {
					if(productVo!=null){
						products.add(productVo);
						productName +=productVo.getProduct_name()+",";
						productIds +=productVo.getProduct_id()+",";
					}
					
				}
			}
			
			
			if(productName.endsWith(",")){
				productName = productName.substring(0,productName.length()-1);
			}
			
		}
		
		model.addAttribute("products", products);
		
		ModelAndView mv = new ModelAndView("admin/special/edit_special");
		model.addAttribute("special", special);
		model.addAttribute("products", list);
		model.addAttribute("activtylistId", activtylistId);
		model.addAttribute("productIds", productIds);
		model.addAttribute("productName", productName);
		return mv;
	}
	
	
	@RequestMapping(value="/addSpecial")
	@ResponseBody
	public Map<String, Object> addSpecial(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("连接地址： /admin/special/addSpecial ,操作：增加/修改专题信息。  操作状态： 开始");
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String specialName=multipartRequest.getParameter("specialName");     	//专题名称
	        String specialType=multipartRequest.getParameter("specialType");      //专题分类
	        String specialTitle=multipartRequest.getParameter("specialTitle");   	//专题标题
	        String specialCover=multipartRequest.getParameter("specialCover");    //专题封面
	        String specialIntroduce=multipartRequest.getParameter("specialIntroduce");        //介绍

	        MultipartFile multipartFiles = multipartRequest.getFile("file1");
	        String specialId=multipartRequest.getParameter("specialId");        //专题id
	        String status=multipartRequest.getParameter("status");
	        String spaclist = multipartRequest.getParameter("speclist");		//规格列表
	        String homeFlag = multipartRequest.getParameter("homeFlag");	//是否推荐到首页
//	        String fakeSalesCopy = multipartRequest.getParameter("fakeSalesCopy");	//手填销量
	        String activityType = multipartRequest.getParameter("activityType");	//活动类型
	        MultipartFile homefameImg = multipartRequest.getFile("list_img"); //XX列表图片
	        String specialSort = multipartRequest.getParameter("specialSort");	//活动类型
	        String homeFilePath = "specialHome/";
	        String listImg = "";
	        if(homefameImg != null && homefameImg.getSize() > 0){
	        	listImg = ImageUpload.uploadFile(homefameImg, homeFilePath);
	        }
	        String specialPage = "";
	        String subjectIndex = "";  //专题首页面路径
	        if(multipartFiles != null&&multipartFiles.getSize()!=0){  //专题页面文件解析压缩包
	        	if (!ZipUtil.isEndsWithZip(multipartFiles.getOriginalFilename())) {
	        		respMap.put("result", "fail");
					respMap.put("msg", "专题页面请上传zip压缩包，且压缩包根目录必须包含index.html文件！");
	        		return respMap;
	        	}
	        	//文件保存目录路径  
	        	String subjectCode = StringUtil.generateShortUuid();
	        	String savePath = SUBJECT_PATH + subjectCode;
	        	specialPage = savePath + File.separator + FileUpload.fileUp(multipartFiles, savePath, multipartFiles.getOriginalFilename().split("\\.")[0]);
		        logger.info("专题页面地址路径：" + savePath);
		        ZipUtil.unzip(specialPage, savePath);   //解压专题文件夹
		        subjectIndex = WebConstant.MAIN_SERVER + "/subject/" + subjectCode + "/index.html";
	        }else{
	        	if(StringUtils.isNotEmpty(specialId)){
	        		PageData pageData = new PageData();
	        		pageData.put("specialId", Integer.parseInt(specialId));
	        		subjectIndex = specialMananger.selectSpecialByOption(pageData).getSpecialPage();
	        	}
	        }
	        String startTime=multipartRequest.getParameter("startTime");   //专题开始时间
	        String endTime=multipartRequest.getParameter("endTime");       //专题结束时间
	        
	        if(-1 != specialCover.lastIndexOf(",")){
	        	specialCover=specialCover.substring(0, specialCover.lastIndexOf(","));
	        }
	        
	        SpecialVo special = new SpecialVo();
	        special.setSpecialName(specialName);
	        special.setSpecialTitle(specialTitle);
	        special.setSpecialCover(specialCover);
	        special.setListImg(listImg);
	        special.setSpecialType(Byte.parseByte(specialType));
	        
	        special.setHomeFlag(Integer.parseInt(homeFlag));
	        if(StringUtils.isNotBlank(status)){					//状态
	        	special.setStatus(Integer.parseInt(status));
	        }else{
	        	special.setStatus(1);
	        }
	        if(StringUtil.isNull(special.getSpecialName())){
	        	special.setSpecialName(special.getSpecialTitle());
	        }
	        if(StringUtils.isNotBlank(specialSort)){					//状态
	        	special.setSpecialSort(Integer.parseInt(specialSort));
	        }else{
	        	special.setSpecialSort(0);
	        }
	        special.setSpecialPage(subjectIndex);
	        special.setStartTime(startTime);
	        special.setEndTime(endTime);
	        special.setSpecialIntroduce(specialIntroduce);
//	        if(StringUtil.isNotNull(fakeSalesCopy)){
//	        	special.setFakeSalesCopy(Integer.parseInt(fakeSalesCopy));
//	        }
	        
	        
	        //专题关联产品activty_product表
	       
	        
	        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
	        List<ActivityProductVo> activityProductSpeclist=new ArrayList<ActivityProductVo>();
	       
	        Set<String> productIdSet=new HashSet<String>(); 
	        
	        int count = 0;
	        if(StringUtils.isNotEmpty(specialId)){//修改
	        	special.setSpecialId(Integer.parseInt(specialId));
//	        	if("1".equals(homeFlag)){ //如果当前专题推荐首页，则先取消原有推荐
//	        		PageData hideSpeParam = new PageData();
//	        		hideSpeParam.put("specialId", Integer.parseInt(specialId));
//	        		hideSpeParam.put("specialType", specialType);
//	        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
//	        	}
	        	count = specialMananger.updateSpecial(special);
	        	//专题关联产品activty_product表
	        	 if(StringUtils.isNotBlank(spaclist)){
	        		 int result=0;
		 	        	String[] spacIds = spaclist.split(",");
		 	        	PageData old = new PageData();
		 	        	String spcIdArry = "";
		 	        	if(spaclist.endsWith(",")){
		 	        		spcIdArry=spaclist.substring(0,spaclist.length()-1);
		 	        	}
		 	        	
		 	        	old.put("activityId", Integer.parseInt(specialId));
		 	        	
		 	        	old.put("specIds", spcIdArry);
		 	        	old.put("activityType", activityType);
		 	        	activityProductManager.delete(old);				//删除没在这个规格之内的数据
		 	        	for (String sid : spacIds) {
		 	        		String price = multipartRequest.getParameter("activty_"+sid);
		 	        		PageData pd = new PageData();
		 	        		pd.put("specId",Integer.parseInt(sid));
		 	        		pd.put("activityType", activityType);
		 	        		pd.put("activityId", Integer.parseInt(specialId));
		 	        		listactivityProductSpec = activityProductManager.queryList(pd);//如果数据库查有集合，那就修改，否则就新增
		 	        		
		 	        		if(null!=listactivityProductSpec&&listactivityProductSpec.size()!=0){
		 	        			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
		 	        				activityProductVo.setActivity_price(price);
				 	        		activityProductVo.setActivity_spec_id(activityProductVo.getActivity_spec_id());
				 	        		activityProductManager.updateByPrimaryKey(activityProductVo);
				 	        		
				 	        		//产品ID集合
				 	        		productIdSet.add(activityProductVo.getProduct_id().toString());
		 	        			}
		 	        		}else{//否则就新增
		 	        			PageData pdt = new PageData();
			 	        		pdt.put("specId", Integer.parseInt(sid));
			 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
			 	        		
			 	        		//产品ID集合
			 	        		productIdSet.add(sid);
			 	        		
		 	        			ActivityProductVo activityProductVo=new ActivityProductVo();
		 	        			activityProductVo.setActivity_id(special.getSpecialId());
				        		activityProductVo.setSpec_id(Integer.parseInt(sid));
				        		activityProductVo.setActivity_price(price);
				        		activityProductVo.setActivity_type(Integer.parseInt(activityType));//1是预售 2是专题 3
				        		activityProductVo.setProduct_id(ps.getProduct_id());
				        		activityProductSpeclist.add(activityProductVo);
		 	        		}
		 	        	}
		 	        	if(null!=activityProductSpeclist&&activityProductSpeclist.size()!=0){
		 	        		result=activityProductManager.addBatchActivityProduct(activityProductSpeclist);
		 	        		if(result<=0){
		 	        			respMap.put("result", "fail");
		 						respMap.put("msg", "新增规格价格失败");
		 						return respMap;
		 	        		}
		 	        	}
	        	 }
		 	        	
	        	if(count>0){
	        		respMap.put("result", "succ");
					respMap.put("msg", "修改专题成功！");
	        	}else{
	        		respMap.put("result", "fail");
					respMap.put("msg", "修改专题失败！");
	        	}
	        }else{
	        	
	        	//专题关联产品activty_product表
	        	if(StringUtils.isNotBlank(spaclist)){
	        		PageData pdt = new PageData();
	        		pdt.put("pStatus", 1);
	        		pdt.put("specStatus", 1);
	        		pdt.put("specIds", spaclist);
	        		List<ProductSpecVo> pss = productSpecManager.queryListProductSpec(pdt);
	        		if(pss == null || pss.size() < spaclist.split(",").length){
	        			respMap.put("result", "fail");
						respMap.put("msg", "存在已下架的商品");
		        		return respMap;
	        		}
	        		
	        		count = specialMananger.insertSpecial(special);
	        		
	 	        	String[] spacIds = spaclist.split(",");
	 	        	for (String sid : spacIds) {
	 	        		pdt.clear();
	 	        		pdt.put("specId", Integer.parseInt(sid));
	 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
	 	        		String price = multipartRequest.getParameter("activty_"+sid);
	 	        		
	 	        		ActivityProductVo activityProductVo=new ActivityProductVo();
		        		activityProductVo.setActivity_id(special.getSpecialId());
		        		activityProductVo.setSpec_id(Integer.parseInt(sid));
		        		activityProductVo.setActivity_price(price);
		        		activityProductVo.setActivity_type(Integer.parseInt(activityType));//1是预售 2是专题 3限量抢购 4拼团活动
		        		activityProductVo.setProduct_id(ps.getProduct_id());
		        		listactivityProductSpec.add(activityProductVo);
					}
	 	        }

//	        	if("1".equals(homeFlag)){ //如果当前专题推荐首页，则先取消原有推荐
//	        		PageData hideSpeParam = new PageData();
//	        		hideSpeParam.put("specialType", specialType);
//	        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
//	        	}
	        	
	        	//非必填数据
	        	if(listactivityProductSpec!=null&&listactivityProductSpec.size()>0){
	        		 int result=activityProductManager.addBatchActivityProduct(listactivityProductSpec);
	        		 if(result<=0){
	        			respMap.put("result", "fail");
	 					respMap.put("msg", "专题保存活动失败！");
	 					return respMap;
	        		 }
	        	}
	        	
	        	if(count>0){
					respMap.put("result", "succ");
					respMap.put("msg", "专题保存成功！");
				}else{
					respMap.put("result", "fail");
					respMap.put("msg", "专题保存失败！");
				}
	        }
	        
			logger.info("连接地址： /admin/special/addSpecial ,操作：增加/修改专题信息。  操作状态：结束");
		}catch(Exception e){
			respMap.put("result", "exce");
			respMap.put("msg", "专题保存失败,出现异常！");
			logger.error("连接地址：  /admin/special/addSpecial ,操作：增加/修改专题信息。  操作状态： 失败！ 原因:出现异常", e);
		}
		return respMap;
	}
	
	
	@RequestMapping(value="/delSpecial")
	@ResponseBody
	public void delSpecial(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/special/delSpecial ,操作：删除专题信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		int count = 0 ;
		int status = Integer.parseInt( pd.getString("checkStatus"));
		String specialIdArray = request.getParameter("specialIdArray");
		if(status==0){
			SpecialVo specialvo = null;
			if(StringUtil.isNull(pd.getString("checkStatus"))){
				json.put("result", "fail");
				json.put("msg", "操作异常！");
				this.outObjectToJson(json, response);
			}else{
				 status = pd.getInteger("checkStatus");
			}
			if (StringUtils.isNotBlank(specialIdArray)) {
				String[] special = specialIdArray.split(",");
				for (String specialId : special) {
					pd.put("specialId",specialId );
						specialvo = specialMananger.selectSpecialByOption(pd);
						specialvo.setStatus(status);
					 	count = specialMananger.updateSpecial(specialvo);	
				}
			}
		}else{
			pd.put("specialId", specialIdArray);
			SpecialVo specialVo = specialMananger.selectSpecialByOption(pd);
			if(StringUtil.isNull(pd.getString("checkStatus"))){
				json.put("result", "fail");
				json.put("msg", "操作异常！");
				this.outObjectToJson(json, response);
			}else{
				specialVo.setStatus(status);
			}
				count = specialMananger.updateSpecial(specialVo);
		}
		if(count>0){
			json.put("result", "succ");
		}else{
			json.put("result", "fail");
			json.put("msg", "操作失败！");
		}
		logger.info("连接地址： /admin/special/delSpecial ,操作：修改活动状态。  操作状态： 结束");
		this.outObjectToJson(json, response);
	}
	
	/*@RequestMapping(value="/editSpecial")
	@ResponseBody
	public void editSpecial(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/special/editSpecial ,操作：删除专题信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		pd.put("specialId", pd.getInteger("specialId"));
		SpecialVo specialVo = specialMananger.selectSpecialByOption(pd);
		if(StringUtil.isNull(pd.getString("checkStatus"))){
			json.put("result", "fail");
			json.put("msg", "操作异常！");
			this.outObjectToJson(json, response);
		}else{
			specialVo.setStatus(pd.getInteger("checkStatus"));
		}
		int count = specialMananger.updateSpecial(specialVo);
		if(count>0){
			json.put("result", "succ");
		}else{
			json.put("result", "fail");
			json.put("msg", "操作失败！");
		}
		logger.info("连接地址： /admin/product/delSpecial ,操作：修改活动状态。  操作状态： 结束");
		this.outObjectToJson(json, response);
	}*/
	
	
	/**
	 * 新增限量抢购页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddLimitSpecial")
	public ModelAndView toAddLimit(HttpServletRequest request,HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/special/add_limit_special");
		PageData pd = this.getPageData();
		
		model.addAttribute("specialType", pd.getString("specialType"));
		model.addAttribute("activityType", pd.getString("activityType"));
		return mv;
	}
	
	@RequestMapping(value="/toEditLimitSpecial")
	public ModelAndView toEditLimitSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		String sid = (String) pd.get("specialId");
		
		//选择产品
		pd.put("productStatus", 1);
		pd.put("hasNoPro", 1);
		pd.put("editFlag", 1);
		pd.put("isOnline", 1);
		pd.put("extensionFlag", 1);
		List<ProductVo> productslist = productManager.queryProductList(pd);
		
		
		pd.put("specialId", Integer.parseInt(sid));
		SpecialVo special = specialMananger.selectSpecialByOption(pd);
		PageData pdt = this.getPageData();
		pdt.put("activityId", Integer.parseInt(sid));
		pdt.put("activityType",pd.getString("activityType"));
		List<ActivityProductVo> listactivityProductSpec=activityProductManager.queryList(pdt);
		List<ProductSpecVo> speclist = new ArrayList<ProductSpecVo>();
		List<ProductVo> products = new ArrayList<ProductVo>();
		String activtylistId = "";
		String productId = "";
		String productName = "";
		if(listactivityProductSpec!=null&&listactivityProductSpec.size()!=0){
			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
				pd.put("productId",null);
				pd.put("specId", activityProductVo.getSpec_id());
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
				pd.put("productId",pspec.getProduct_id());
				ProductVo product = productManager.selectProductByOption(pd);
				activtylistId +=activityProductVo.getActivity_spec_id()+",";
				speclist.add(pspec);
				productId = product.getProduct_id() +"";
				productName = product.getProduct_name();
			}
			
		}
		
		model.addAttribute("products", products);
		
		ModelAndView mv = new ModelAndView("admin/special/edit_limit_special");
		model.addAttribute("special", special);
		model.addAttribute("products", productslist);
		model.addAttribute("activtylistId", activtylistId);
		model.addAttribute("productId", productId);
		model.addAttribute("productName", productName);
		model.addAttribute("specialType", pd.getString("specialType"));
		model.addAttribute("activityType", pd.getString("activityType"));
		return mv;
	}
	
	/**
	 * 新增限量抢购页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddGroupSpecial")
	public ModelAndView toAddGroupSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/special/add_group_special");
		PageData pd = this.getPageData();
		
		model.addAttribute("specialType", pd.getString("specialType"));
		model.addAttribute("activityType", pd.getString("activityType"));
		return mv;
	}
	
	@RequestMapping(value="/toEditGroupSpecial")
	public ModelAndView toEditGroupSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		String sid = (String) pd.get("specialId");
		
		//选择产品
		pd.put("productStatus", 1);
		pd.put("hasNoPro", 1);
		pd.put("editFlag", 1);
		pd.put("isOnline", 1);
		pd.put("extensionFlag", 1);
		List<ProductVo> productslist = productManager.queryProductList(pd);
		
		
		pd.put("specialId", Integer.parseInt(sid));
		SpecialVo special = specialMananger.selectSpecialByOption(pd);
		PageData pdt = this.getPageData();
		pdt.put("activityId", Integer.parseInt(sid));
		pdt.put("activityType",pd.getString("activityType"));
		List<ActivityProductVo> listactivityProductSpec=activityProductManager.queryList(pdt);
		List<ProductSpecVo> speclist = new ArrayList<ProductSpecVo>();
		List<ProductVo> products = new ArrayList<ProductVo>();
		String activtylistId = "";
		String productId = "";
		String productName = "";
		if(listactivityProductSpec!=null&&listactivityProductSpec.size()!=0){
			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
				pd.put("productId",null);
				pd.put("specId", activityProductVo.getSpec_id());
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
				pd.put("productId",pspec.getProduct_id());
				ProductVo product = productManager.selectProductByOption(pd);
				activtylistId +=activityProductVo.getActivity_spec_id()+",";
				speclist.add(pspec);
				productId = product.getProduct_id() +"";
				productName = product.getProduct_name();
			}
			
		}
		
		PageData sysPd = new PageData();
		sysPd.put("specialId", special.getSpecialId());
		SysGroupVo sysGroup = this.sysGroupManager.queryOne(sysPd);
		
		model.addAttribute("products", products);
		model.addAttribute("sysGroup", sysGroup);
		
		ModelAndView mv = new ModelAndView("admin/special/edit_group_special");
		model.addAttribute("special", special);
		model.addAttribute("products", productslist);
		model.addAttribute("activtylistId", activtylistId);
		model.addAttribute("productId", productId);
		model.addAttribute("productName", productName);
		model.addAttribute("specialType", pd.getString("specialType"));
		model.addAttribute("activityType", pd.getString("activityType"));
		return mv;
	}
	
	@RequestMapping(value="/editGroupSpecial")
	@ResponseBody
	public Map<String, Object> editGroupSpecial(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("连接地址： /admin/special/editGroupSpecial ,操作：增加/修改活动信息。  操作状态： 开始");
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String specialName=multipartRequest.getParameter("specialName");     	//活动名称
	        String specialType=multipartRequest.getParameter("specialType");      //活动分类
	        String specialTitle=multipartRequest.getParameter("specialTitle");   	//活动标题
	        String specialCover=multipartRequest.getParameter("specialCover");    //活动封面
	        String specialIntroduce=multipartRequest.getParameter("specialIntroduce");        //介绍
	        String specialId=multipartRequest.getParameter("specialId");        //活动id
	        String status=multipartRequest.getParameter("status");
	        String spaclist = multipartRequest.getParameter("speclist");		//规格列表
//	        String homeFlag = multipartRequest.getParameter("homeFlag");	//是否推荐到首页
//	        String fakeSalesCopy = multipartRequest.getParameter("fakeSalesCopy");	//手填销量
	        String activityType = multipartRequest.getParameter("activityType");	//活动类型
	        String startTime=multipartRequest.getParameter("startTime");   //活动开始时间
	        String endTime=multipartRequest.getParameter("endTime");       //活动结束时间
	        String groupCopy=multipartRequest.getParameter("groupCopy");
	        String limitCopy=multipartRequest.getParameter("limitCopy");
	        String productId=multipartRequest.getParameter("productId");
	        String desc1 = multipartRequest.getParameter("desc1");	//规则1
	        String desc2 = multipartRequest.getParameter("desc2");	//规则2
	        String desc3 = multipartRequest.getParameter("desc3");	//规则3
	        String desc4 = multipartRequest.getParameter("desc4");	//规则4
	        String specialSort = multipartRequest.getParameter("specialSort");	//排序
//	    	MultipartFile homefameImg = multipartRequest.getFile("group_img"); //团购列表图片
	        
	        
	        if(-1 != specialCover.lastIndexOf(",")){
	        	specialCover=specialCover.substring(0, specialCover.lastIndexOf(","));
	        }
//	        String homeFilePath = "specialHome/";
	        
//	        String groupImg = "";
//	        if(homefameImg != null && homefameImg.getSize() > 0){
//	        	groupImg = ImageUpload.uploadFile(homefameImg, homeFilePath);
//	        }
	        SpecialVo special = new SpecialVo();
	        special.setSpecialName(specialName);
	        special.setSpecialTitle(specialTitle);
	        special.setSpecialCover(specialCover);
//	        special.setListImg(groupImg);
	        special.setSpecialType(Byte.parseByte(specialType));
//	        special.setHomeFlag(Integer.parseInt(homeFlag));
	        if(StringUtils.isNotBlank(status)){					//状态
	        	special.setStatus(Integer.parseInt(status));
	        }else{
	        	special.setStatus(1);
	        }
	        if(StringUtil.isNull(special.getSpecialName())){
	        	special.setSpecialName(special.getSpecialTitle());
	        }
	        
	        special.setStartTime(startTime);
	        special.setEndTime(endTime);
	        special.setSpecialIntroduce(specialIntroduce);
	        special.setSpecialPage("");
//	        if(StringUtil.isNotNull(fakeSalesCopy)){
//	        	special.setFakeSalesCopy(Integer.parseInt(fakeSalesCopy));
//	        }
	        if(StringUtils.isNotBlank(specialSort)){
	        	special.setSpecialSort(Integer.parseInt(specialSort));
	        }else{
	        	special.setSpecialSort(0);
	        }
	        
	        
	        //活动关联产品activty_product表
	       
	        
	        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
	        List<ActivityProductVo> activityProductSpeclist=new ArrayList<ActivityProductVo>();
	       
	        Set<String> productIdSet=new HashSet<String>(); 
	        
	        int count = 0;
	        if(StringUtils.isNotEmpty(specialId)){//修改
	        	special.setSpecialId(Integer.parseInt(specialId));
//	        	if("1".equals(homeFlag)){ //如果当前活动推荐首页，则先取消原有推荐
//	        		PageData hideSpeParam = new PageData();
//	        		hideSpeParam.put("specialId", Integer.parseInt(specialId));
//	        		hideSpeParam.put("specialType", specialType);
//	        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
//	        	}
	        	count = specialMananger.updateSpecial(special);
	        	//活动关联产品activty_product表
	        	 if(StringUtils.isNotBlank(spaclist)){
	        		 int result=0;
		 	        	String[] spacIds = spaclist.split(",");
		 	        	PageData old = new PageData();
		 	        	String spcIdArry = "";
		 	        	if(spaclist.endsWith(",")){
		 	        		spcIdArry=spaclist.substring(0,spaclist.length()-1);
		 	        	}
		 	        	
		 	        	old.put("activityId", Integer.parseInt(specialId));
		 	        	
		 	        	old.put("specIds", spcIdArry);
		 	        	old.put("activityType", activityType);
		 	        	activityProductManager.delete(old);				//删除没在这个规格之内的数据
		 	        	for (String sid : spacIds) {
		 	        		String price = multipartRequest.getParameter("activty_"+sid);
		 	        		PageData pd = new PageData();
		 	        		pd.put("specId",Integer.parseInt(sid));
		 	        		pd.put("activityType", activityType);
		 	        		pd.put("activityId", Integer.parseInt(specialId));
		 	        		listactivityProductSpec = activityProductManager.queryList(pd);//如果数据库查有集合，那就修改，否则就新增
		 	        		
		 	        		if(null!=listactivityProductSpec&&listactivityProductSpec.size()!=0){
		 	        			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
		 	        				activityProductVo.setActivity_price(price);
				 	        		activityProductVo.setActivity_spec_id(activityProductVo.getActivity_spec_id());
				 	        		activityProductManager.updateByPrimaryKey(activityProductVo);
				 	        		
				 	        		//产品ID集合
				 	        		productIdSet.add(activityProductVo.getProduct_id().toString());
		 	        			}
		 	        		}else{//否则就新增
		 	        			PageData pdt = new PageData();
			 	        		pdt.put("specId", Integer.parseInt(sid));
			 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
			 	        		
			 	        		//产品ID集合
			 	        		productIdSet.add(sid);
			 	        		
		 	        			ActivityProductVo activityProductVo=new ActivityProductVo();
		 	        			activityProductVo.setActivity_id(special.getSpecialId());
				        		activityProductVo.setSpec_id(Integer.parseInt(sid));
				        		activityProductVo.setActivity_price(price);
				        		activityProductVo.setActivity_type(Integer.parseInt(activityType));//1是预售 2是活动 3限量 4拼团
				        		activityProductVo.setProduct_id(ps.getProduct_id());
				        		activityProductSpeclist.add(activityProductVo);
		 	        		}
		 	        	}
		 	        	if(null!=activityProductSpeclist&&activityProductSpeclist.size()!=0){
		 	        		result=activityProductManager.addBatchActivityProduct(activityProductSpeclist);
		 	        		if(result<=0){
		 	        			respMap.put("result", "fail");
		 						respMap.put("msg", "新增规格价格失败");
		 	        		}
		 	        	}
	        	 }
		 	        	
	        	if(count>0){
	        		String groupId = multipartRequest.getParameter("groupId");
	        		SysGroupVo sysGroupVo = new SysGroupVo();
	        		sysGroupVo.setLimitCopy(Integer.parseInt(limitCopy));
	        		sysGroupVo.setGroupCopy(Integer.parseInt(groupCopy));
	        		sysGroupVo.setSpecialId(Integer.parseInt(specialId));
	        		sysGroupVo.setGroupId(Integer.parseInt(groupId));
	        		sysGroupVo.setProductId(Integer.parseInt(productId));
	        		if(StringUtil.isNotNull(desc1)){
	        			sysGroupVo.setDesc1(desc1);
	        		}
	        		if(StringUtil.isNotNull(desc2)){
	        			sysGroupVo.setDesc2(desc2);
	        		}
	        		if(StringUtil.isNotNull(desc3)){
	        			sysGroupVo.setDesc3(desc3);
	        		}
	        		if(StringUtil.isNotNull(desc4)){
	        			sysGroupVo.setDesc4(desc4);
	        		}
	        		sysGroupManager.updateByPrimaryKeySelective(sysGroupVo);
	        		respMap.put("result", "succ");
					respMap.put("msg", "修改活动成功！");
	        	}else{
	        		respMap.put("result", "fail");
					respMap.put("msg", "修改活动失败！");
	        	}
	        }else{
//	        	if("1".equals(homeFlag)){ //如果当前活动推荐首页，则先取消原有推荐
//	        		PageData hideSpeParam = new PageData();
//	        		hideSpeParam.put("specialType", specialType);
//	        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
//	        	}
	        	
	        	//活动关联产品activty_product表
	        	 if(StringUtils.isNotBlank(spaclist)){
        			PageData pdt = new PageData();
	        		pdt.put("pStatus", 1);
	        		pdt.put("specStatus", 1);
	        		pdt.put("specIds", spaclist);
	        		List<ProductSpecVo> pss = productSpecManager.queryListProductSpec(pdt);
	        		if(pss == null || pss.size() < spaclist.split(",").length){
	        			respMap.put("result", "fail");
						respMap.put("msg", "存在已下架的商品");
		        		return respMap;
	        		}
		        		
	        	 	count = specialMananger.insertSpecial(special);
	 	        	String[] spacIds = spaclist.split(",");
	 	        	for (String sid : spacIds) {
	 	        		pdt.clear(); 
	 	        		pdt.put("specId", Integer.parseInt(sid));
	 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
	 	        		String price = multipartRequest.getParameter("activty_"+sid);
	 	        		
	 	        		ActivityProductVo activityProductVo=new ActivityProductVo();
		        		activityProductVo.setActivity_id(special.getSpecialId());
		        		activityProductVo.setSpec_id(Integer.parseInt(sid));
		        		activityProductVo.setActivity_price(price);
		        		activityProductVo.setActivity_type(Integer.parseInt(activityType));
		        		activityProductVo.setProduct_id(ps.getProduct_id());
		        		listactivityProductSpec.add(activityProductVo);
					}
	 	        }
	        	 
	        	 if(listactivityProductSpec!=null&&listactivityProductSpec.size()>0){
	        		 int result=activityProductManager.addBatchActivityProduct(listactivityProductSpec);
	        		 if(result<=0){
	        			 respMap.put("result", "fail");
	 					 respMap.put("msg", "活动保存失败！");
	        		 }
	        	 }
	        	
	        	if(count>0){
	        		SysGroupVo sysGroupVo = new SysGroupVo();
	        		sysGroupVo.setLimitCopy(Integer.parseInt(limitCopy));
	        		sysGroupVo.setGroupCopy(Integer.parseInt(groupCopy));
	        		sysGroupVo.setSpecialId(special.getSpecialId());
	        		if (null != productId && !"".equals(productId)) {
	        			sysGroupVo.setProductId(Integer.parseInt(productId));
					}
	          		if(StringUtil.isNotNull(desc1)){
	        			sysGroupVo.setDesc1(desc1);
	        		}
	        		if(StringUtil.isNotNull(desc2)){
	        			sysGroupVo.setDesc2(desc2);
	        		}
	        		if(StringUtil.isNotNull(desc3)){
	        			sysGroupVo.setDesc3(desc3);
	        		}
	        		if(StringUtil.isNotNull(desc4)){
	        			sysGroupVo.setDesc4(desc4);
	        		}
	        		sysGroupManager.insert(sysGroupVo);
					respMap.put("result", "succ"); 
					respMap.put("msg", "活动保存成功！");
				}else{
					respMap.put("result", "fail");
					respMap.put("msg", "活动保存失败！");
				}
	        }
	        
			logger.info("连接地址： /admin/special/editGroupSpecial ,操作：增加/修改团购信息。  操作状态：结束");
		}catch(Exception e){
			respMap.put("result", "exce");
			respMap.put("msg", "专题保存失败,出现异常！");
			logger.error("连接地址：  /admin/special/editGroupSpecial ,操作：增加/修改团购信息。  操作状态： 失败！ 原因:出现异常");
		}
		return respMap;
	}
	
	/**
	 * 新增美食教程页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddTutorialSpecial")
	public ModelAndView toAddTutorialSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/special/add_tutorial_special");
		PageData pd = this.getPageData();
		//排除已有活动的商品
		pd.put("hasNoPro", 1);
		pd.put("productStatus", 1);
		pd.put("isOnline", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);    
		pd.clear();
		pd.put("status", 1);
		List<SpecialVideoTypeVo> vdoTypes = specialVideoTypeManager.queryListPage(pd);
		model.addAttribute("vdoTypes", vdoTypes);
		model.addAttribute("products", products);
		return mv;
	}
	
	@RequestMapping(value="/toEditTutorialSpecial")
	public ModelAndView toEditTutorialSpecial(HttpServletRequest request,HttpServletResponse resp,Model model){
		PageData pd = this.getPageData();
		String sid = (String) pd.get("specialId");
		
		
		//pd.put("activityType",1);
		//List<ProductVo> list = productManager.queryNotExistInExtension(pd);
		
		//选择产品
//		PageData pds = this.getPageData();
//		pds.put("activityType", 1);
//		List<ActivityProductVo> yushou = activityProductManager.queryList(pd);//预售产品
//		List<ProductVo> list  = new ArrayList<ProductVo>();
		pd.put("productStatus", 1);
		pd.put("hasNoPro", 1);
		pd.put("editFlag", 1);
		pd.put("isOnline", 1);
		List<ProductVo> list = productManager.queryProductList(pd);
//		for (ProductVo productVo : productslist) {
//			for (ActivityProductVo activityProductVo : yushou) {
//				if(productVo.getProduct_id()!=activityProductVo.getProduct_id()){ 
//					if(!list.contains(productVo)){
//						list.add(productVo);
//					}
//					
//				}
//			}
//		}
		
		
		
		
		pd.put("specialId", Integer.parseInt(sid));
		SpecialVo special = specialMananger.selectSpecialByOption(pd);
		PageData pdt = this.getPageData();
		pdt.put("activityId", Integer.parseInt(sid));
		pdt.put("activityType",5);
		List<ActivityProductVo> listactivityProductSpec=activityProductManager.queryList(pdt);
		List<ProductSpecVo> speclist = new ArrayList<ProductSpecVo>();
		List<ProductVo> products = new ArrayList<ProductVo>();
		String activtylistId = "";
		String productIds = "";
		String productName = "";
		Set<ProductVo> set = new HashSet<ProductVo>();
		if(listactivityProductSpec!=null&&listactivityProductSpec.size()!=0){
			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
				pd.put("productId",null);
				pd.put("specId", activityProductVo.getSpec_id());
				ProductSpecVo pspec = productSpecManager.queryProductSpecByOption(pd);
				pd.put("productId",pspec.getProduct_id());
				ProductVo product = productManager.selectProductByOption(pd);
				activtylistId +=activityProductVo.getActivity_spec_id()+",";
				speclist.add(pspec);
				if(!set.contains(product)){
					set.add(product);
				}
				
			}
		
			if(set!=null&&set.size()>0){
				for (ProductVo productVo : set) {
					if(productVo!=null){
						products.add(productVo);
						productName +=productVo.getProduct_name()+",";
						productIds +=productVo.getProduct_id()+",";
					}
					
				}
			}
			
			
			if(productName.endsWith(",")){
				productName = productName.substring(0,productName.length()-1);
			}
			
		}
		
		pd.clear();
		pd.put("status", 1);
		List<SpecialVideoTypeVo> vdoTypes = specialVideoTypeManager.queryListPage(pd);

		ModelAndView mv = new ModelAndView("admin/special/edit_tutorial_special");
		
		model.addAttribute("vdoTypes", vdoTypes);
		model.addAttribute("products", products);
		model.addAttribute("special", special);
		model.addAttribute("products", list);
		model.addAttribute("activtylistId", activtylistId);
		model.addAttribute("productIds", productIds);
		model.addAttribute("productName", productName);
		
		return mv;
	}
	
	@RequestMapping(value="/addTutorialSpecial")
	@ResponseBody
	public Map<String, Object> addTutorialSpecial(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("连接地址： /admin/special/addTutorialSpecial ,操作：增加/修改专题信息。  操作状态： 开始");
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String specialName=multipartRequest.getParameter("specialName");     	//专题名称
	        String specialType=multipartRequest.getParameter("specialType");      //专题分类
	        String specialTitle=multipartRequest.getParameter("specialTitle");   	//专题标题
	        String specialCover=multipartRequest.getParameter("specialCover");    //专题封面
	        String specialIntroduce=multipartRequest.getParameter("specialIntroduce");        //介绍
	        String specialId=multipartRequest.getParameter("specialId");        //专题id
	        String status=multipartRequest.getParameter("status");
	        String spaclist = multipartRequest.getParameter("speclist");		//规格列表
	        String homeFlag = multipartRequest.getParameter("homeFlag");	//是否推荐到首页
//	        String fakeSalesCopy = multipartRequest.getParameter("fakeSalesCopy");	//手填销量
	        String activityType = multipartRequest.getParameter("activityType");	//活动类型
	        String specialPage = multipartRequest.getParameter("specialPage");	//视频路径
	        String startTime=multipartRequest.getParameter("startTime");   //专题开始时间
	        String endTime=multipartRequest.getParameter("endTime");       //专题结束时间
	        String objTypeId=multipartRequest.getParameter("objTypeId");	//视频分类
	        String objValue=multipartRequest.getParameter("objValue");	//视频播放时长
	        
	        if(-1 != specialCover.lastIndexOf(",")){
	        	specialCover=specialCover.substring(0, specialCover.lastIndexOf(","));
	        }
	        
	        SpecialVo special = new SpecialVo();
	        special.setSpecialName(specialName);
	        special.setSpecialTitle(specialTitle);
	        special.setSpecialCover(specialCover);
	        special.setSpecialType(Byte.parseByte(specialType));
	        special.setHomeFlag(Integer.parseInt(homeFlag));
	        if(StringUtils.isNotBlank(status)){					//状态
	        	special.setStatus(Integer.parseInt(status));
	        }else{
	        	special.setStatus(1);
	        }
	        if(StringUtil.isNull(special.getSpecialName())){
	        	special.setSpecialName(special.getSpecialTitle());
	        }
	        
	        special.setSpecialPage(specialPage);
	        special.setStartTime(startTime);
	        special.setEndTime(endTime);
	        special.setSpecialIntroduce(specialIntroduce);
	        special.setSpecialSort(0);
	        special.setObjValue(objValue);
	        special.setObjTypeId(Integer.parseInt(objTypeId));
//	        if(StringUtil.isNotNull(fakeSalesCopy)){
//	        	special.setFakeSalesCopy(0);
//	        }
	        
	        
	        //专题关联产品activty_product表
	       
	        
	        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
	        List<ActivityProductVo> activityProductSpeclist=new ArrayList<ActivityProductVo>();
	       
	        Set<String> productIdSet=new HashSet<String>(); 
	        
	        int count = 0;
	        if(StringUtils.isNotEmpty(specialId)){//修改
	        	special.setSpecialId(Integer.parseInt(specialId));
	        	
	        	PageData hideSpeParam = new PageData();
        		hideSpeParam.put("specialType", specialType);
        		hideSpeParam.put("specialId", special.getSpecialId());
        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
	        	count = specialMananger.updateSpecial(special);
	        	//专题关联产品activty_product表
	        	 if(StringUtils.isNotBlank(spaclist)){
	        		 int result=0;
		 	        	String[] spacIds = spaclist.split(",");
		 	        	PageData old = new PageData();
		 	        	String spcIdArry = "";
		 	        	if(spaclist.endsWith(",")){
		 	        		spcIdArry=spaclist.substring(0,spaclist.length()-1);
		 	        	}
		 	        	
		 	        	old.put("activityId", Integer.parseInt(specialId));
		 	        	
		 	        	old.put("specIds", spcIdArry);
		 	        	old.put("activityType", activityType);
		 	        	activityProductManager.delete(old);				//删除没在这个规格之内的数据
		 	        	for (String sid : spacIds) {
		 	        		String price = multipartRequest.getParameter("activty_"+sid);
		 	        		PageData pd = new PageData();
		 	        		pd.put("specId",Integer.parseInt(sid));
		 	        		pd.put("activityType", activityType);
		 	        		pd.put("activityId", Integer.parseInt(specialId));
		 	        		listactivityProductSpec = activityProductManager.queryList(pd);//如果数据库查有集合，那就修改，否则就新增
		 	        		
		 	        		if(null!=listactivityProductSpec&&listactivityProductSpec.size()!=0){
		 	        			for (ActivityProductVo activityProductVo : listactivityProductSpec) {
		 	        				activityProductVo.setActivity_price(price);
				 	        		activityProductVo.setActivity_spec_id(activityProductVo.getActivity_spec_id());
				 	        		activityProductManager.updateByPrimaryKey(activityProductVo);
				 	        		
				 	        		//产品ID集合
				 	        		productIdSet.add(activityProductVo.getProduct_id().toString());
		 	        			}
		 	        		}else{//否则就新增
		 	        			PageData pdt = new PageData();
			 	        		pdt.put("specId", Integer.parseInt(sid));
			 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
			 	        		
			 	        		//产品ID集合
			 	        		productIdSet.add(sid);
			 	        		
		 	        			ActivityProductVo activityProductVo=new ActivityProductVo();
		 	        			activityProductVo.setActivity_id(special.getSpecialId());
				        		activityProductVo.setSpec_id(Integer.parseInt(sid));
				        		activityProductVo.setActivity_price(price);
				        		activityProductVo.setActivity_type(Integer.parseInt(activityType));//1是预售 2是专题 3
				        		activityProductVo.setProduct_id(ps.getProduct_id());
				        		activityProductSpeclist.add(activityProductVo);
		 	        		}
		 	        	}
		 	        	if(null!=activityProductSpeclist&&activityProductSpeclist.size()!=0){
		 	        		result=activityProductManager.addBatchActivityProduct(activityProductSpeclist);
		 	        		if(result<=0){
		 	        			respMap.put("result", "fail");
		 						respMap.put("msg", "新增规格价格失败");
		 	        		}
		 	        	}
	        	 }
		 	        	
	        	if(count>0){
	        		respMap.put("result", "succ");
					respMap.put("msg", "修改专题成功！");
	        	}else{
	        		respMap.put("result", "fail");
					respMap.put("msg", "修改专题失败！");
	        	}
	        }else{
	        	//专题关联产品activty_product表
	        	 if(StringUtils.isNotBlank(spaclist)){
        			PageData pdt = new PageData();
	        		pdt.put("pStatus", 1);
	        		pdt.put("specStatus", 1);
	        		pdt.put("specIds", spaclist);
	        		List<ProductSpecVo> pss = productSpecManager.queryListProductSpec(pdt);
	        		if(pss == null || pss.size() < spaclist.split(",").length){
	        			respMap.put("result", "fail");
						respMap.put("msg", "存在已下架的商品");
		        		return respMap;
	        		}
	        		
	        		PageData hideSpeParam = new PageData();
	        		hideSpeParam.put("specialType", specialType);
	        		specialMananger.hideSpecialHomeFlag(hideSpeParam);
	        		count = specialMananger.insertSpecial(special);
	 	        	String[] spacIds = spaclist.split(",");
	 	        	for (String sid : spacIds) {
	 	        		pdt.clear();
	 	        		pdt.put("specId", Integer.parseInt(sid));
	 	        		ProductSpecVo ps = productSpecManager.queryProductSpecByOption(pdt);
	 	        		String price = multipartRequest.getParameter("activty_"+sid);
	 	        		
	 	        		ActivityProductVo activityProductVo=new ActivityProductVo();
		        		activityProductVo.setActivity_id(special.getSpecialId());
		        		activityProductVo.setSpec_id(Integer.parseInt(sid));
		        		activityProductVo.setActivity_price(price);
		        		activityProductVo.setActivity_type(Integer.parseInt(activityType));//1是预售 2是专题 3限量抢购 4拼团活动
		        		activityProductVo.setProduct_id(ps.getProduct_id());
		        		listactivityProductSpec.add(activityProductVo);
					}
	 	        }
				if (listactivityProductSpec != null && listactivityProductSpec.size() > 0) {
					int result = activityProductManager.addBatchActivityProduct(listactivityProductSpec);
					if (result <= 0) {
						respMap.put("result", "fail");
						respMap.put("msg", "专题活动保存失败！");
					}
				}

				if (count > 0) {
					respMap.put("result", "succ");
					respMap.put("msg", "专题保存成功！");
				} else {
					respMap.put("result", "fail");
					respMap.put("msg", "专题保存失败！");
				}
	        }
	        
			logger.info("连接地址： /admin/special/addTutorialSpecial ,操作：增加/修改专题信息。  操作状态：结束");
		}catch(Exception e){
			respMap.put("result", "exce");
			respMap.put("msg", "专题保存失败,出现异常！");
			logger.error("连接地址：  /admin/special/addTutorialSpecial ,操作：增加/修改专题信息。  操作状态： 失败！ 原因:出现异常");
		}
		return respMap;
	}
	
	/**
	 * 增加或修改团购，限量首页banner
	 */
	@RequestMapping(value="/editSpecialBanner")
	@ResponseBody
	public Map<String,Object> editSpecialBanner(HttpServletRequest request, HttpServletResponse response,PicturesVo picturesVo){
		
		int result=0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			MultipartFile bannerImg = multipartRequest.getFile("bannerImg");
			
			String homeFilePath = "bannerHome/";
			String insertPannerImg = "";
			
			if(StringUtil.isNull(picturesVo.getPicture_type())){
				return CallBackConstant.FAILED.callbackError("图片类型不能为空");
			}
			if(picturesVo.getPicture_id()!=null&&picturesVo.getPicture_id()>0){
				if(bannerImg.getSize() > 0){
		    		insertPannerImg = ImageUpload.uploadFile(bannerImg, homeFilePath);
					picturesVo.setOriginal_img(insertPannerImg);
					
				}
				picturesVo.setSmall_img(picturesVo.getOriginal_img());
				result=picturesManager.updateByPrimaryKey(picturesVo);
			}else{
				if(bannerImg.getSize() > 0){
		    		insertPannerImg = ImageUpload.uploadFile(bannerImg, homeFilePath);
				}else{
					return CallBackConstant.FAILED.callbackError("图片不能为空");
				}
				if(StringUtil.isNull(insertPannerImg)){
					return CallBackConstant.FAILED.callbackError("图片不能为空");
				}
				picturesVo.setOriginal_img(insertPannerImg);
				picturesVo.setSmall_img(insertPannerImg);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				picturesVo.setCreate_time(sdf.format(new Date()));
				result=picturesManager.insert(picturesVo);
			}
		} catch (Exception e) {
			logger.error("/admin/special/editSpecialBanner --error", e);
		}
		
		if(result > 0){
			return CallBackConstant.SUCCESS.callback(picturesVo);
		}else{
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 删除首页封面图
	 */
	@RequestMapping(value="/delHomePic")
	@ResponseBody
	public Map<String,Object> delHomePic(HttpServletRequest request, HttpServletResponse response){
		
		int result=0;
		try {
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getInteger("pictureId"))){
				return CallBackConstant.FAILED.callbackError("图片Id不能为空");
			}
			
			result = this.picturesManager.updatePicturesStatus(pd);
		} catch (Exception e) {
			logger.error("/admin/special/delGroupHomePic --error", e);
		}
		
		if(result > 0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback();
		}
	}
}
