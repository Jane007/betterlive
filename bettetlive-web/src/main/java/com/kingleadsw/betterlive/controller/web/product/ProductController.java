package com.kingleadsw.betterlive.controller.web.product;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.AreaManager;
import com.kingleadsw.betterlive.biz.BannerInfoManager;
import com.kingleadsw.betterlive.biz.DeliverAreaManager;
import com.kingleadsw.betterlive.biz.ExtensionManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.common.MatrixToImageWriterEx;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.controller.web.LoginController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.init.AppServerUtil;
import com.kingleadsw.betterlive.vo.AreaVo;
import com.kingleadsw.betterlive.vo.BannerInfoVo;
import com.kingleadsw.betterlive.vo.DeliverAreaVo;
import com.kingleadsw.betterlive.vo.ExtensionVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;

/**
 * 商品
 * 2017-03-08 by chen
 * @author Coder
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController  extends AbstractWebController{
	protected Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private ProductManager productManager;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private DeliverAreaManager deliverAreaManager;
	@Autowired
	private BannerInfoManager bannerInfoManager;
	@Autowired
	private ExtensionManager extensionManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	
	/**
	 * 跳转查询商品页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findListTaste(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/product/list_product");
		return mv;
	}
	
	
	/**
	 * 分页查询商品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryProductAllJson")
	@ResponseBody
	public void queryProductAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<ProductVo>  listProduct =productManager.queryProductListPage(pd);
		
//		PageData pp = this.getPageData();
//		List<PreProductVo> preProducts = preProductManager.queryList(pp);
		
		//List<ProductVo>  haspreproducts = new ArrayList<ProductVo>();
		
		if(null !=listProduct && listProduct.size()>0){
			
			for (int i = 0; i <listProduct.size(); i++) {
				String details=listProduct.get(i).getDetails();
				
				if(null!=details && !"".equals(details)){
					listProduct.get(i).setParamAndValue(details.split("@\\#@\\$"));
				}
				
				if(null!=listProduct.get(i).getProduct_logo() && !"".equals(listProduct.get(i).getProduct_logo())){
					String[] pictureArrays=listProduct.get(i).getProduct_logo().split(",");
					listProduct.get(i).setPictureArray(pictureArrays);
				}
				
			}
			
			
//			for (ProductVo productVo : listProduct) {//判断商品有没有添加为预售
//				productVo.setDesabled(1);
//				if(null!=preProducts && preProducts.size()>0){
//					for (PreProductVo preProductVo : preProducts) {
//						if(preProductVo.getProductId()==productVo.getProduct_id()){
//							productVo.setDesabled(0);
//							break;
//						}
//					}
//				}
//				
//			}
			
			this.outEasyUIDataToJson(pd, listProduct, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<ProductVo>(), response);
		}
		
	}
	
	/**
	 * 查询商品信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryListProductAllJson")
	@ResponseBody
	public void queryListProductAllJson(HttpServletRequest request,HttpServletResponse response) {
		JSONObject json=new JSONObject();
		
		PageData pd = this.getPageData();
		
		List<ProductVo>  listProduct =productManager.queryProductList(pd);
		
		if(!listProduct.isEmpty() && listProduct.size()>0){
			json.put("result", "succ");
			json.put("list", listProduct);
		}else{
			json.put("result", "fail");
		}
		outJson(json.toString(), response);
		
	}
	
	
	/**
	 * 跳转新增商品界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toAddProduct",produces="application/json;charset=utf-8")
	public ModelAndView toAddProduct(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/product/add_product");
		PageData pd=new PageData();
		pd.put("parentId", 0);
		List<AreaVo> list=areaManager.findAllAreaInfo(pd);
		model.addAttribute("list", list);
		List<ExtensionVo> listExtends = new ArrayList<ExtensionVo>();
		PageData ps = new PageData();
		ps.put("isHomepage", 1);
		listExtends = extensionManager.queryList(ps);
		if(listExtends!=null&&listExtends.size()>=4){//是否显示首页
			model.addAttribute("productDisable", 1);
		}else{
			model.addAttribute("productDisable", 0);
		}
		
		return mv;
	}
	
	/**
	 * 增加商品信息
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/addProduct")
	public void addProduct(HttpServletRequest request, HttpServletResponse response) {
		logger.info("连接地址： /admin/product/addProduct ,操作：增加商品信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String productName=multipartRequest.getParameter("productName");     	//商品名称
	        String productStatus=multipartRequest.getParameter("productStatus");    //商品状态  1 上架  2 下架
	        String productLove=multipartRequest.getParameter("productLove");        //猜你喜欢
	        String productPrompt=multipartRequest.getParameter("productPrompt");    //温馨提示
	        String productIntroduce=multipartRequest.getParameter("productIntroduce");   //商品介绍
	        String productUrl=multipartRequest.getParameter("product_logo");               //商品封面
	        String productBanner = multipartRequest.getParameter("pictureArray");  //商品轮播图
	        String orderNum = multipartRequest.getParameter("order_num");          //排序
	        String ifCoupon = multipartRequest.getParameter("ifCoupon");          //是否能使用优惠券和红包
	       
	        String cityId = multipartRequest.getParameter("cityId");          		//仓库所在地址
	        String deliverAddress = multipartRequest.getParameter("deliverAddress");          		//地址类型；1：同城；2：本省；3：自定义
	        String deliverType= multipartRequest.getParameter("deliverType");          //全国配送
    		String areaIds = multipartRequest.getParameter("areaIds");          		//配送地区
    		String shareExplain = multipartRequest.getParameter("shareExplain"); 	//简介
    		String isOnline =  multipartRequest.getParameter("isOnline");			//1:线上，2:线下
    		String productType =  multipartRequest.getParameter("product_type");			//商品分类
    		String isFreight = multipartRequest.getParameter("is_freight"); 	//是否免运费 1是 0否
    		String isQuality =  multipartRequest.getParameter("is_quality");			//是否权威质检 1是 0否
    		String isTesting =  multipartRequest.getParameter("is_testing");			//是否专业测评 1是 0否
    		String fakeSalesCopy =  multipartRequest.getParameter("fakeSalesCopy");			//虚拟销量
    		MultipartFile homeweeklyFirstFile = multipartRequest.getFile("homeweekly_first_img"); //首页每周新片的第一张图
    		MultipartFile homeweeklyAfterFile = multipartRequest.getFile("homeweekly_after_img"); //首页每周新片的非第一张图
    		MultipartFile homefameFile = multipartRequest.getFile("homefame_img"); //首页人气推荐图
    		long fileSize = 1024 * 1024;
    	
	        if( null==cityId|| "".equals(cityId) ){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，仓库所在地址为空");
				this.outJson(json.toString(), response);
				return;
	        }
   			
	        if( null==deliverAddress|| "".equals(deliverAddress) ){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，配送地址类型为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        
	        if( "3".equals(deliverAddress) &&( null==areaIds|| "".equals(areaIds) ) &&( null==deliverType|| "".equals(deliverType) ) ){
   			
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，商品配送地址为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        
	        //商品封面图
	        if(StringUtil.isNull(productUrl)){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，商品封面图为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(StringUtil.isNull(productBanner)){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，商品轮播图为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(StringUtil.isNull(cityId)){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，仓库所在地址为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(homeweeklyFirstFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页每周新品只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(homeweeklyAfterFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页每周新品只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        if(homefameFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页人气推荐只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        //商品参数总数	
	        String productTypeLength=multipartRequest.getParameter("productTypeLength");        
	        String productSize="";
	        String productNames=null;
	        String productPrices=null;
	        boolean bl=true;
	        for (int i =1; i < Integer.parseInt(productTypeLength)+1; i++) {
	        	productNames=multipartRequest.getParameter("sizeOrParam_"+i);
	        	productPrices=multipartRequest.getParameter("sizeOrValue_"+i);
	        	if("".equals(productNames) || null ==productNames ||"".equals(productPrices) || null ==productPrices){
	        		bl=false;
	        		json.put("result", "fail");
					json.put("msg", "商品参数为空！位置："+i);
	        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品信息。  操作状态： 失败！ 原因:  商品参数出现错误  位置： "+i);
	        		this.outJson(json.toString(), response);
	        		return;
	        	}else{
	        		productSize+=productNames+"&#&$"+productPrices+"@#@$";
	        	}
			}
	        
	        //商品规格与价格总数
	        String teaTypeLength = multipartRequest.getParameter("teaTypeLength");        
	        String teaNames = null;
	        String teaPrices = null;
	        String teaCopy = null;  			//库存
	        boolean bool = true;
	        String limitCopy = null;				//限购份数
	        String startTime = null; 			//限购开始时间
	        String endTime = null;				//限购结束时间
	        float minPrice = 0f;    //商品规格最低价格
	        String packageDec=null;	//套餐说明
	        String discountPrice=null;
	        List<ProductSpecVo> listSpec= new ArrayList<ProductSpecVo>();
	        ProductSpecVo productSpecVo=null;
	        for (int i = 1; i < Integer.parseInt(teaTypeLength)+1; i++) {
	        	teaNames = multipartRequest.getParameter("teaSize_"+i);
	        	teaPrices = multipartRequest.getParameter("teaSizePrice_"+i);
	        	teaCopy = multipartRequest.getParameter("teaCopy_"+i);
	        	limitCopy = multipartRequest.getParameter("teaLimitMaxCopy_"+i);
	        	startTime = multipartRequest.getParameter("teaLimitStartTime_"+i);
	        	endTime = multipartRequest.getParameter("teaLimitEndTime_"+i);
	        	packageDec = multipartRequest.getParameter("teaPackageDesc_"+i);
	        	discountPrice = multipartRequest.getParameter("teaDiscountPrice_"+i);
	        	MultipartFile specFile = multipartRequest.getFile("specImg" + i);  //规格图片
	        	
	        	String specFilePath = "probanner/";
	        	String specImgPath = "";
	        	if(specFile.getSize()<fileSize){
	        		specImgPath=ImageUpload.uploadFile(specFile, specFilePath);
	        	}else{
	        		json.put("result", "fail");
					json.put("msg", "只能上传小于1M的图片");
					this.outJson(json.toString(), response);
					return;
				}
	        	
	        	if("".equals(teaNames) || null == teaNames ||"".equals(teaPrices) || null ==teaPrices){
	        		bool = false;
	        		
	        		json.put("result", "fail");
					json.put("msg", "规格与价格为空！位置："+i);
	        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品信息。  操作状态： 失败！ 原因:  商品规格出现错误  位置： "+i);
	        		this.outJson(json.toString(), response);
	        		return;
	        	}else{
	        		productSpecVo=new ProductSpecVo();
	        		productSpecVo.setSpec_name(teaNames);
	        		productSpecVo.setSpec_price(teaPrices);
	        		productSpecVo.setSpec_img(specImgPath);
	        		productSpecVo.setPackage_desc(packageDec);
	        		if(StringUtil.isNotNull(discountPrice)){
	        			productSpecVo.setDiscount_price(new BigDecimal(discountPrice));
	        		}
	        		if(StringUtils.isNotBlank(limitCopy)){
	        			productSpecVo.setLimit_max_copy(Integer.parseInt(limitCopy));
	        		}
	        		if(StringUtils.isNotBlank(startTime)){
	        			productSpecVo.setLimit_start_time(startTime);
	        		}
	        		if(StringUtils.isNotBlank(endTime)){
	        			productSpecVo.setLimit_end_time(endTime);
	        		}
	        		
	        		
	        		
	        		if(StringUtils.isNotBlank(teaCopy)){
	        			productSpecVo.setStock_copy(Integer.parseInt(teaCopy));
	        		}
	        		listSpec.add(productSpecVo);
	        		
	        		//获取设置的规格最低价格
	        		if (i == 1) {  //如果i为1，则最小价格为第一个规格
	        			minPrice = Float.parseFloat(teaPrices);
	        		} else {   //否则循环对比规格值，如果小于上一个值，则重新赋值
	        			if (Float.parseFloat(teaPrices) < minPrice) {
	        				minPrice = Float.parseFloat(teaPrices);
	        			}
	        		}
	        	}
			}

	        if(bool && bl){
	        	String homeFilePath = "proHome/";
	        	String homeFirstImg = "";
	           	String homeAfterImg = "";
	         	String homefameImg = "";
	         	if(homeweeklyFirstFile.getSize() > 0){
	         		homeFirstImg = ImageUpload.uploadFile(homeweeklyFirstFile, homeFilePath);
	    		}
	         	
	         	if(homeweeklyAfterFile.getSize() > 0){
	         		homeAfterImg = ImageUpload.uploadFile(homeweeklyAfterFile, homeFilePath);
	         
	    		}
	         	
	         	if(homefameFile.getSize() > 0){
	         		homefameImg = ImageUpload.uploadFile(homefameFile, homeFilePath);
	         	}
	         	
	           	
	        	if(null!=productSize&& !"".equals(productSize) && -1!=productSize.lastIndexOf("@#@$")){
	        		productSize=productSize.substring(0, productSize.lastIndexOf("@#@$"));
	        	}
	        	
	        	Integer weekly = Integer.valueOf(request.getParameter("weekly"));    //是否每周新品；1：是，0：否
	        	Integer weekly_homepage = Integer.valueOf(request.getParameter("weekly_homepage")); //每周新品是否首页展示
	        	Integer tuijian = Integer.valueOf(request.getParameter("tuijian"));    //是否人气推荐
	        	Integer tuijian_homepage = Integer.valueOf(request.getParameter("tuijian_homepage"));    //人气推荐是否首页显示
	        	
				ProductVo product = new ProductVo();
				if(productBanner.startsWith(",")){
					productBanner = productBanner.substring(1,productBanner.length());
				}
				String [] bannerArr = productBanner.split(",");
				
	        	
				product.setProduct_code(StringUtil.generateShortUuid());   //商品编号
				product.setProduct_name(productName);                      //商品名称  
				product.setPrice("" + minPrice);  						   //产品最低价格
				product.setStatus(Integer.parseInt(productStatus));        //商品状态  1上架 2下架
				product.setEat_recommend(productLove);                     //是否推荐 
				product.setPrompt(productPrompt);                          //温馨提示
				product.setIntroduce(productIntroduce);                    //商品介绍
				product.setProduct_logo(productUrl);                       //商品封面
				product.setDetails(productSize);                               //商品介绍
				product.setPictureArray(bannerArr);
				product.setListSpecVo(listSpec);
				product.setWeekly(weekly);
				product.setWeekly_homepage(weekly_homepage);
				product.setTuijian(tuijian);
				product.setTuijian_homepage(tuijian_homepage);
				product.setShare_explain(shareExplain);
				product.setIs_online(Integer.parseInt(isOnline));
				product.setHomeweekly_first_img(homeFirstImg);
				product.setHomeweekly_after_img(homeAfterImg);
				product.setHomefame_img(homefameImg);
				if(StringUtils.isNotBlank(orderNum)){
					product.setOrder_num(Integer.parseInt(orderNum));
				}else{
					product.setOrder_num(0);
				}
				if(StringUtils.isNotBlank(fakeSalesCopy)){
					product.setFake_sales_copy(Integer.parseInt(fakeSalesCopy));
				}
				
				product.setWarehouse(cityId);
				product.setDeliverAddress(deliverAddress);
				product.setDeliverType(deliverType);
				product.setDeliver_type(Integer.parseInt(deliverAddress));
				product.setAreaIds(areaIds);
				product.setProduct_type(Integer.parseInt(productType));
				product.setIs_freight(Integer.parseInt(isFreight));
				product.setIs_quality(Integer.parseInt(isQuality));
				product.setIs_testing(Integer.parseInt(isTesting));
				product.setIfCoupon(Integer.parseInt(ifCoupon));
				
				int result = productManager.insertProduct(product);
				
				if(result>0){
					json.put("result", "succ");
					json.put("msg", "添加商品成功！");
					this.outToJson(json, response);
	        		return;
				}else{
					json.put("result", "fail");
					json.put("msg", "添加商品失败！");
				}
	        }	
		}catch(Exception e){
			json.put("result", "exce");
			json.put("msg", "添加商品失败,出现异常！");
			logger.error("连接地址： /admin/product/addProduct ,操作：增加商品信息。  操作状态： 失败！ 原因:出现异常");
		}
		
		logger.info("连接地址： /admin/product/addProduct ,操作：增加商品信息。  操作状态：结束");
		this.outJson(json.toString(), response);
	}
	
	
	/**
	 * 跳转更新商品界面
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toUpdateProduct")
	public ModelAndView toUpdateProduct(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/product/edit_product");
		PageData pd=this.getPageData();
		
		String productId=pd.getString("productId");
		
		pd.put("parentId", 0);
		List<AreaVo> list=areaManager.findAllAreaInfo(pd);
		
		model.addAttribute("list", list);
		model.addAttribute("productId", productId);
		
		return mv;
	}
	/**
	 * 逻辑删除商品状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delProduct")
	public void delProduct(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/product/delProduct ,操作：删除商品信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String productIdArray = pd.getString("productIdArray");
		int result = 0;
		if(StringUtils.isNotBlank(productIdArray)){
			
			List<BannerInfoVo> listBanner=bannerInfoManager.queryAllBannersList(pd);
			if(null==listBanner){
				String[] productId = productIdArray.split(",");
				for (String sid : productId) {
					pd.put("productId", Integer.parseInt(sid));
					//ProductVo productVo = productManager.selectProductByOption(pd);
					//productVo.setStatus(3);//商品状态，1：上架，2：下架，3：删除
					pd.put("status", 3);  //商品状态为已删除
					//int count = productManager.updateProduct(productVo);
					result = productManager.updateProductStatus(pd);
				}
				
				if(result>0){
					json.put("result", "succ");
				}else{
					json.put("result", "fail");
					json.put("msg", "删除产品失败！");
				}
				
			}else{
				String productIds="";
				for (int i = 0; i < listBanner.size(); i++) {
					productIds+=listBanner.get(i).getObjectId()+",";
				}
				if(null!=productIds && !"".equals(productIds) && productIds.lastIndexOf(",")!=-1){
					productIds=productIds.substring(0,productIds.lastIndexOf(","));
				}
				
				json.put("result", "fail");
				json.put("msg", "删除产品:"+productIds+" 失败,正在使用Banner图展示");
			}	
		}
		
		logger.info("连接地址： /admin/product/delProduct ,操作：删除商品信息。  操作状态： 结束");
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 修改商品信息
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/editProduct")
	public void editProduct(HttpServletRequest request, HttpServletResponse response) {
		logger.info("连接地址： /admin/product/editProduct ,操作：更新商品信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			String product_id=multipartRequest.getParameter("product_id");          //商品ID
	        String productName=multipartRequest.getParameter("productName");     	//商品名称
	        String productStatus=multipartRequest.getParameter("productStatus");    //商品状态  1 上架  2 下架
	        String productLove=multipartRequest.getParameter("productLove");        //猜你喜欢
	        String productPrompt=multipartRequest.getParameter("productPrompt");    //温馨提示
	        String productIntroduce=multipartRequest.getParameter("productIntroduce");   //商品介绍
	        String productUrl=multipartRequest.getParameter("productUrl");               //商品封面
	        String productBanner = multipartRequest.getParameter("pictureArray");  //商品轮播图
	        String orderNum = multipartRequest.getParameter("order_num");          //排序
	        String ifCoupon = multipartRequest.getParameter("ifCoupon");          //是否允许使用优惠券和红包
	        
	        String cityId = multipartRequest.getParameter("cityId");          		//仓库所在地址
	        String deliverAddress = multipartRequest.getParameter("deliverAddress");          		//地址类型；1：同城；2：本省；3：自定义
	        String deliverType= multipartRequest.getParameter("deliverType");          //全国配送
    		String areaIds = multipartRequest.getParameter("areaIds");          		//配送地区
    		
    		String shareExplain = multipartRequest.getParameter("shareExplain"); 	//分享时简介
    		String isOnline =  multipartRequest.getParameter("isOnline");			////1:线上，2:线下
    		String productType = multipartRequest.getParameter("product_type");		//商品分类
    		String isFreight = multipartRequest.getParameter("is_freight"); 	//是否免运费 1是 0否
    		String isQuality =  multipartRequest.getParameter("is_quality");			//是否权威质检 1是 0否
    		String isTesting =  multipartRequest.getParameter("is_testing");			//是否专业测评 1是 0否
    		String fakeSalesCopy =  multipartRequest.getParameter("fakeSalesCopy");			//虚拟销量
    		MultipartFile homeweeklyFirstFile = multipartRequest.getFile("homeweekly_first_img"); //首页每周新片的第一张图
    		MultipartFile homeweeklyAfterFile = multipartRequest.getFile("homeweekly_after_img"); //首页每周新片的非第一张图
    		MultipartFile homefameFile = multipartRequest.getFile("homefame_img"); //首页人气推荐图
        	long fileSize = 1024 * 1024;
        	
	        if(StringUtil.isNull(cityId)){
	        	json.put("result", "fail");
				json.put("msg", "修改商品失败，仓库所在地址为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if( null==deliverAddress|| "".equals(deliverAddress) ){
	        	json.put("result", "fail");
				json.put("msg", "修改商品失败，配送地址类型为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if( "3".equals(deliverAddress) &&( null==areaIds|| "".equals(areaIds) ) &&( null==deliverType|| "".equals(deliverType) ) ){
   			
	        	json.put("result", "fail");
				json.put("msg", "修改商品失败，商品配送地址为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(homeweeklyFirstFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页每周新品只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        if(homeweeklyAfterFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页每周新品只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        if(homefameFile.getSize() > fileSize){
	        	json.put("result", "fail");
				json.put("msg", "首页人气推荐只能上传小于1M的图片");
				this.outJson(json.toString(), response);
				return;
	        }
	        
	        //商品参数总数	
	        String productTypeLength=multipartRequest.getParameter("productTypeLength");        
	        String productSize="";
	        String productNames=null;
	        String productPrices=null;
	        boolean bl=true;
	        for (int i =1; i < Integer.parseInt(productTypeLength)+1; i++) {
	        	productNames=multipartRequest.getParameter("sizeOrParam_"+i);
	        	productPrices=multipartRequest.getParameter("sizeOrValue_"+i);
	        	if("".equals(productNames) || null ==productNames ||"".equals(productPrices) || null ==productPrices){
	        		bl=false;
	        		
	        		json.put("result", "fail");
					json.put("msg", "商品参数为空！位置："+i);
	        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品信息。  操作状态： 失败！ 原因:  商品参数出现错误  位置： "+i);
	        		this.outJson(json.toString(), response);
					return;
	        	}else{
	        		productSize+=productNames+"&#&$"+productPrices+"@#@$";
	        	}
			}
	        
	        //商品规格与价格总数
	        String teaTypeLength=multipartRequest.getParameter("teaTypeLength");        
	        String teaNames=null;
	        String teaPrices=null;
	        String teaCopy=null;
	        String limitCopy = null;				//限购份数
	        String startTime = null; 			//限购开始时间
	        String endTime = null;				//限购结束时间
	        float minPrice = 0f;    			//商品规格最低价格
	        String packageDesc = null;			//套餐说明
	        boolean bool = true;
	        String discountPrice=null;
	        List<ProductSpecVo> listSpec=new ArrayList<ProductSpecVo>();
	        PageData prod = this.getPageData();
	        prod.put("productId", Integer.parseInt(product_id));
	        ProductVo productquery = productManager.selectProductByOption(prod);
	        List<ProductSpecVo> productSpec = productquery.getListSpecVo();
	        ProductSpecVo productSpecVo=null;
	        //规格改造
	        for (int i = 0; i < productSpec.size(); i++) { //修改或删除已有的规格
	        	ProductSpecVo spec = productSpec.get(i);
	        	teaNames=multipartRequest.getParameter("teaSize_"+spec.getSpec_id());
		        teaPrices=multipartRequest.getParameter("teaSizePrice_"+spec.getSpec_id());
		        teaCopy = multipartRequest.getParameter("teaCopy_"+spec.getSpec_id());
		    	limitCopy = multipartRequest.getParameter("teaLimitMaxCopy_"+spec.getSpec_id());
	        	startTime = multipartRequest.getParameter("teaLimitStartTime_"+spec.getSpec_id());
	        	endTime = multipartRequest.getParameter("teaLimitEndTime_"+spec.getSpec_id());
	        	packageDesc = multipartRequest.getParameter("teaPackageDesc_"+spec.getSpec_id());
	        	discountPrice = multipartRequest.getParameter("teaDiscountPrice_"+spec.getSpec_id());
		        MultipartFile specFile = multipartRequest.getFile("specImg" + spec.getSpec_id());  //规格图片
		        String images = multipartRequest.getParameter("specImges"+spec.getSpec_id());
		        String specFilePath = "probanner/";
		        String specImgPath="";
		        if(specFile != null && specFile.getSize() > 0 && specFile.getSize() < fileSize){
		        	specImgPath = ImageUpload.uploadFile(specFile, specFilePath);
		        }else{		        	 
		        	 specImgPath = images;
		        }
		        
		        if(StringUtils.isNotBlank(teaNames)){
		        	spec.setSpec_name(teaNames);
			        spec.setSpec_price(teaPrices);
			        spec.setSpec_img(specImgPath);
			        spec.setPackage_desc(packageDesc);
			    	if(StringUtils.isNotBlank(teaCopy)){
			    		spec.setStock_copy(Integer.parseInt(teaCopy));
	        		}
			    	if(StringUtils.isNotBlank(limitCopy)){
			    		spec.setLimit_max_copy(Integer.parseInt(limitCopy));
	        		}
			    	if(StringUtils.isNotBlank(startTime)){
	        			spec.setLimit_start_time(startTime);
	        		}else{
	        			spec.setLimit_start_time(null);
	        		}
	        		if(StringUtils.isNotBlank(endTime)){
	        			spec.setLimit_end_time(endTime);
	        		}else{
	        			spec.setLimit_end_time(null);
	        		}
			    	if(StringUtils.isNotBlank(discountPrice)){
			    		spec.setDiscount_price(new BigDecimal(discountPrice));
			    	}
			        spec.setProduct_id(Integer.parseInt(product_id));
			        spec.setStatus(1);//正常
			        //获取设置的规格最低价格
	        		if (i == 0) {  //如果i为1，则最小价格为第一个规格
	        			minPrice = Float.parseFloat(teaPrices);
	        		} else {   //否则循环对比规格值，如果小于上一个值，则重新赋值
	        			if (Float.parseFloat(teaPrices) < minPrice) {
	        				minPrice = Float.parseFloat(teaPrices);
	        			}
	        		}
		        }else{
		        	spec.setStatus(3);//删除
		        }
		        productSpecManager.updateProductSpecBySid(spec);
			}
	        
	        if(productSpec.size()<Integer.parseInt(teaTypeLength)){//增加了新的规格
	            for (int i =productSpec.size()+1; i < Integer.parseInt(teaTypeLength)+1; i++) {
	        		teaNames=multipartRequest.getParameter("teaSize_"+i);
	    	        teaPrices=multipartRequest.getParameter("teaSizePrice_"+i);
	    	        teaCopy = multipartRequest.getParameter("teaCopy_"+i);
	    	        limitCopy = multipartRequest.getParameter("teaLimitMaxCopy_"+i);
		        	startTime = multipartRequest.getParameter("teaLimitStartTime_"+i);
		        	endTime = multipartRequest.getParameter("teaLimitEndTime_"+i);
		        	discountPrice = multipartRequest.getParameter("teaDiscountPrice_"+i);
		        	packageDesc = multipartRequest.getParameter("teaPackageDesc_"+i);
	    	        MultipartFile specFile = multipartRequest.getFile("specImg" + i);  //规格图片
	    	        String images = multipartRequest.getParameter("specImges"+i);
	    	        String specFilePath = "probanner/";
	    	        String specImgPath="";
	    	        if(specFile!=null&&specFile.getSize()!=0 ||specFile.getSize()<fileSize){
	    	        	specImgPath = ImageUpload.uploadFile(specFile, specFilePath);
	    	        }else{
	    	        	specImgPath = images;
	    	        }
	    	        if("".equals(teaNames) || null ==teaNames ||"".equals(teaPrices) || null ==teaPrices){
	    	        	bool=false;
	    	        	json.put("result", "fail");
	    				json.put("msg", "规格与价格为空！位置："+i);
	    	        	logger.info("连接地址： /admin/product/addProduct , 操作：增加商品信息。  操作状态： 失败！ 原因:  商品规格出现错误  位置： "+i);
	    	        }else{
	    	        	productSpecVo=new ProductSpecVo();
	    	        	productSpecVo.setProduct_id(Integer.parseInt(product_id));
	    	        	productSpecVo.setSpec_name(teaNames);
	    	        	productSpecVo.setSpec_price(teaPrices);
	    	        	productSpecVo.setSpec_img(specImgPath);
	    	        	productSpecVo.setPackage_desc(packageDesc);
	    	        	if(StringUtils.isNotBlank(discountPrice)){
	    	        		productSpecVo.setDiscount_price(new BigDecimal(discountPrice));
				    	}
	    	        	if(StringUtils.isNotBlank(limitCopy)){
		        			productSpecVo.setLimit_max_copy(Integer.parseInt(limitCopy));
		        		}
	    	        	if(StringUtils.isNotBlank(startTime)){
		        			productSpecVo.setLimit_start_time(startTime);
		        		}
		        		if(StringUtils.isNotBlank(endTime)){
		        			productSpecVo.setLimit_end_time(endTime);
		        		}
		        		
	    	        	if(StringUtils.isNotBlank(teaCopy)){
	    	        		productSpecVo.setStock_copy(Integer.parseInt(teaCopy));
		        		}
	    	        	productSpecVo.setStatus(1);   //启用
	    	        	listSpec.add(productSpecVo);
	    	        	
	    	        	//寻找最低价
	        			if (Float.parseFloat(teaPrices) < minPrice) {
	        				minPrice = Float.parseFloat(teaPrices);
	        			}
	    	        }
		        }
	            if(listSpec.size()>0){
	            	 int count = productSpecManager.addBatchProductSpec(listSpec); 
	            	 if(count<=0){
	            		json.put("result", "fail");
	 					json.put("msg", "更新商品规格失败！");
	            	 }
	            }
	        }

	        if(StringUtil.isNull(productBanner)){
	        	json.put("result", "fail");
				json.put("msg", "新增商品失败，商品轮播图为空");
				this.outJson(json.toString(), response);
				return;
	        }
	        if(bool && bl){
	        	String homeFilePath = "proHome/";
	        	String homeFirstImg = null;
	        	String homeAfterImg = null;
	        	String homefameImg = null;
	        	if(homeweeklyFirstFile.getSize() > 0){
	         		homeFirstImg = ImageUpload.uploadFile(homeweeklyFirstFile, homeFilePath);
	    		}
	         	
	         	if(homeweeklyAfterFile.getSize() > 0){
	         		homeAfterImg = ImageUpload.uploadFile(homeweeklyAfterFile, homeFilePath);
	    		}
	         	
	         	if(homefameFile.getSize() > 0){
	         		homefameImg = ImageUpload.uploadFile(homefameFile, homeFilePath);
	         	}
	        	Integer weekly = 0;
	        	Integer weekly_homepage = 0;
	           	Integer tuijian = 0;
	         	Integer tuijian_homepage = 0;
	        	if(StringUtil.isNotNull(request.getParameter("weekly"))){
	        		weekly = Integer.valueOf(request.getParameter("weekly"));    //是否每周新品；1：是，0：否
	        	}
	        	if(StringUtil.isNotNull(request.getParameter("weekly_homepage"))){
	        		weekly_homepage = Integer.valueOf(request.getParameter("weekly_homepage")); //每周新品是否首页展示
	        	}
	        	if(StringUtil.isNotNull(request.getParameter("tuijian"))){
	        		tuijian = Integer.valueOf(request.getParameter("tuijian"));    //是否人气推荐
	        	}
	        	if(StringUtil.isNotNull(request.getParameter("tuijian_homepage"))){
	        		tuijian_homepage = Integer.valueOf(request.getParameter("tuijian_homepage"));    //人气推荐是否首页显示
	        	}
	        	
	        	if(null!=productSize&& !"".equals(productSize) && -1!=productSize.lastIndexOf("@#@$")){
	        		productSize=productSize.substring(0, productSize.lastIndexOf("@#@$"));
	        	}
	        	
	        	//去掉图片的结尾带  , 标识
		        if(-1 != productUrl.lastIndexOf(",")){
		        	productUrl=productUrl.substring(0, productUrl.lastIndexOf(","));
		        }
		      
				ProductVo product=new ProductVo();
				if(productBanner.startsWith(",")){
					productBanner = productBanner.substring(1,productBanner.length());
				}
				
				//查询商品是否有参加专题活动，如果有，则查询活动最低价格
				PageData priceData = new PageData();
				priceData.put("productId", product_id);
				priceData.put("activityType", 2);  //2:专题
				float activePrice = activityProductManager.queryMinProductPrice(priceData);
				if (activePrice != -1 && activePrice < minPrice) {
					minPrice = activePrice;
				}
				
				String [] bannerArr = productBanner.split(",");
				product.setProduct_id(Integer.parseInt(product_id));       //商品ID 
				product.setProduct_name(productName);                      //商品名称  
				product.setPrice(minPrice + "");						   //规格最低价格
				product.setStatus(Integer.parseInt(productStatus));        //商品状态  1上架 2下架
				product.setEat_recommend(productLove);                     //是否推荐 
				product.setPrompt(productPrompt);                          //温馨提示
				product.setIntroduce(productIntroduce);                    //商品介绍
				product.setProduct_logo(productUrl);                       //商品封面
				product.setDetails(productSize);                           //商品介绍
				product.setPictureArray(bannerArr);							//banner轮播图
				int result=0;
				if(StringUtils.isNotBlank(orderNum)){
					product.setOrder_num(Integer.parseInt(orderNum));
				}else{
					product.setOrder_num(0);
				}
				if(StringUtils.isNotBlank(fakeSalesCopy)){
					product.setFake_sales_copy(Integer.parseInt(fakeSalesCopy));
				}
				product.setWarehouse(cityId);
				product.setDeliverAddress(deliverAddress);
				product.setDeliverType(deliverType);
				product.setDeliver_type(Integer.parseInt(deliverAddress));
				product.setAreaIds(areaIds);
				product.setShare_explain(shareExplain);
				product.setIs_online(Integer.parseInt(isOnline));
				product.setWeekly(weekly);
				product.setWeekly_homepage(weekly_homepage);
				product.setTuijian(tuijian);
				product.setTuijian_homepage(tuijian_homepage);
				product.setHomeweekly_first_img(homeFirstImg);
				product.setHomeweekly_after_img(homeAfterImg);
				product.setHomefame_img(homefameImg);
				product.setProduct_type(Integer.parseInt(productType));
				product.setIs_freight(Integer.parseInt(isFreight));
				product.setIs_quality(Integer.parseInt(isQuality));
				product.setIs_testing(Integer.parseInt(isTesting));
				product.setIfCoupon(Integer.parseInt(ifCoupon));
				result=productManager.updateProduct(product);
				
				if(result>0){
					json.put("result", "succ");
					json.put("msg", "更新商品成功！");
				}else{
					json.put("result", "fail");
					json.put("msg", "更新商品失败！");
				}
	        }
			logger.info("连接地址： /admin/product/addProduct ,操作：更新商品信息。  操作状态：结束");
		}catch(Exception e){
			json.put("result", "exce");
			json.put("msg", "更新商品失败,出现异常！");
			logger.error("连接地址： /admin/product/addProduct ,操作：更新商品信息。  操作状态： 失败！ 原因:出现异常");
		}
		
		this.outJson(json.toString(), response);
	}
	
	/**
	 * 查询商品类型信息
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryProductJson")
	@ResponseBody
	public Map<String, Object> queryProductTypeJson(HttpServletRequest request,HttpServletResponse response) {
		JSONObject json=new JSONObject();
		
		try {
			PageData pd = this.getPageData();
			
			ProductVo product = productManager.selectProductByOption(pd);
			List<ExtensionVo> extension = product.getExtensionVos();
//			PageData pp = this.getPageData();
//			List<PreProductVo> preProducts = preProductManager.queryList(pp);
			if(null !=product){
				String detail=product.getDetails();
				
		
				
				if(null!=extension){
					for (ExtensionVo extensionVo : extension) {
						if(extensionVo.getExtensionType()==1){//1：每周新品；2：人气推荐
							product.setWeekly(1);
							if(extensionVo.getIsHomepage()==1){
								product.setWeekly_homepage(1);
							}
						}else if(extensionVo.getExtensionType()==2){
							product.setTuijian(1);
							if(extensionVo.getIsHomepage()==1){
								product.setTuijian_homepage(1);
							}
						}
					}
				}
				
				if( null!=detail&& !"".equals(detail)){
					product.setParamAndValue(detail.split("@\\#@\\$"));     //产品参数
				}
				
	//			product.setPictureArray(product.getProduct_logo().split(","));
				
				product.setPictureArray(product.getPictureArray());
				
				
				
				
				//是否可推荐
//				if(null!=preProducts&&preProducts.size()>0){
//					for (PreProductVo preProductVo : preProducts) {
//						if(preProductVo.getProductId()==product.getProduct_id()){
//							product.setDesabled(1);
//						}
//					}
//					
//				}
				
//				List<ExtensionVo> listExtends = new ArrayList<ExtensionVo>();
//				PageData ps = new PageData();
//				ps.put("isHomepage", 1);
//				listExtends = extensionManager.queryList(ps);
//				if(listExtends!=null&&listExtends.size()>=4){//是否显示首页
//					product.setDesabled(1);
//				}
				
				json.put("product",product);      //商品信息
				
	//			json.put("listSpec",listSpec);    //商品规格与价格
				
				List<AreaVo> list=null;
				if(null!=product.getWarehouse() && !"".equals(product.getWarehouse())){
					pd.put("areaId",product.getWarehouse());
					list=areaManager.queryAreaByCid(pd);
				}
				
				if(null==list ){
					list=new ArrayList<AreaVo>();
				}
				
				json.put("list",list);      //仓库所在地址
				
				
				//配送地址类型
				List<DeliverAreaVo> listDeliver=null;
				//自定义
				if(product.getDeliver_type() != null && 3 == product.getDeliver_type()){
					PageData pagedata=new PageData();
					pagedata.put("productId",product.getProduct_id().toString());
					listDeliver=deliverAreaManager.queryListDeliverArea(pagedata);
				}
				
				if(null==listDeliver ){
					listDeliver=new ArrayList<DeliverAreaVo>();
				}
				
				json.put("listDeliver",listDeliver);      //商品配送地址
				
				//未完全选中的城市
				List<AreaVo> listCity=areaManager.queryCityByPid(product.getProduct_id().toString());
				if(null==listCity){
					listCity=new ArrayList<AreaVo>();
				}
				json.put("listCity",listCity);      //商品配送地址
			}
		} catch (Exception e) {
			logger.error("/admin/order/queryProductJson --error", e);
			
			json.put("result","fail");
			json.put("msg","查询出现异常");
		}
		
		return CallBackConstant.SUCCESS.callback(json);
	}
	
    public void SaveFileFromInputStream(InputStream stream,String path,String[] filesname) throws IOException{
    	FileOutputStream fs=null;
    	
    	for (int i = 0; i < filesname.length; i++) {
    		if(null ==filesname[i] || "".equals(filesname[i])){
    			continue;
    		}
   // 		fs=new FileOutputStream( path + "/"+ filesname);
    		fs=new FileOutputStream( path + "/"+ filesname[i]);
            byte[] buffer =new byte[1024*1024];
            int byteread = 0; 
            while ((byteread=stream.read(buffer))!=-1)
            {
               fs.write(buffer,0,byteread);
               fs.flush();
            } 
		}
    	
        fs.close();
        stream.close();      
    }  
    
    /**
     * 删除图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/delProdImg")
    @ResponseBody
    public Map<String,Object> delProdImg(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("连接地址： /admin/product/delProdImg ,操作：删除图片  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String pictureId = pd.getString("pictureId");
		int result = 0;
		if(StringUtils.isBlank(pictureId)){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		pd.put("pictureId", pictureId);		
		pd.put("status", 0);
		result = this.picturesManager.updatePicturesStatus(pd);
		if(result>0){
			json.put("result", "succ");
			
		}else{
			json.put("result", "fail");
			json.put("msg", "删除图片失败！");
		}
	
		return CallBackConstant.SUCCESS.callback();
    }
    /**
     * 商品排序
     * @param request
     * @param response
     * @return
     */
    
    @RequestMapping(value="/goodsSort")
    @ResponseBody
    public Map<String,Object> goodsSort(HttpServletRequest request, HttpServletResponse response){
    	logger.info("连接地址： /admin/product/goodsSort ,操作：删除图片  操作状态： 开始");
    	
		PageData pd = this.getPageData();
		String pictureId = pd.getString("pictureId");
		
	
		if(StringUtils.isBlank(pictureId)){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		int result = picturesManager.updatePicturesSort(pd);
		
		if(result<=0){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		return CallBackConstant.SUCCESS.callback();
				
	}
  
    /**
	 * 上传图片名称
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/uploadImg")
	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String msg = "uploadImg";
		logger.info("/admin/uploadImg/"+msg+" begin");
		JSONObject json = new JSONObject();
		
		try{
			
			//获得文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			MultipartFile multipartFiles = multipartRequest.getFile("file");
			
			String filename = null;
			if(multipartFiles != null){
	        	filename = multipartFiles.getOriginalFilename();  //原始文件名
	        	String prefix = filename.substring(filename.lastIndexOf("."));   //文件后缀名
	        	filename = StringUtil.get32UUID() + prefix;  //新的文件名称
	        	logger.info("图片名称：" + filename);
	        }else{
	        	logger.error("上传图片内容为空");
	        	throw new Exception();
	        }
			
//	        List<MultipartFile> multipartFile = multipartRequest.getFiles("file");
	        
	        //文件保存目录路径  
	        String savePath = WebConstant.UPLOAT_ROOT_PATH;
	        //创建文件夹  
	        File dirFile = new File(savePath);  
	        if (!dirFile.exists()) {  
	            dirFile.mkdirs();  
	        }
			SaveFileFromInputStream(multipartFiles.getInputStream(),savePath, filename);

			String filePath = savePath + File.separator + filename;
			logger.info("图片存储在本地路径：" + filename);
			String imgUrl = ImageUpload.simpleUpload(filePath, filename);
			if (imgUrl == null || "".equals(imgUrl)) {
				imgUrl = WebConstant.MAIN_SERVER + File.separator+ "images" + File.separator + filename;
			}
			
			logger.info("上传图片地址:" + imgUrl);
			json.put("result", "success");
			json.put("imgurl", imgUrl);
			json.put("msg", "上传图片成功！");
			
			logger.info("--->结束调用/admin/uploadImg/"+msg);
		}catch(Exception e){
			json.put("result", "failure");
			json.put("msg", "上传图片,出现异常！");
		}
		
		this.outObjectToJson(json, response);
	}
	
	
 	/**
	 * 加载商品logo上传控件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/productlogo.html")
	public ModelAndView loadProLogo(HttpServletRequest request, HttpServletResponse response,Model model) {
		ModelAndView mv = new ModelAndView("admin/product/productlogo");
		PageData pd=this.getPageData();
		//String productId=pd.getString("productId");
		ProductVo product = productManager.selectProductByOption(pd);
		if(product==null){
			product = new ProductVo();
		}
		model.addAttribute("product", product);
		return mv;
	}
	
	
	
	/**
	 * 加载商品轮播图上传控件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/productbanner.html")
	public ModelAndView loadProBanner(HttpServletRequest request, HttpServletResponse response,Model model) {
		ModelAndView mv = new ModelAndView("admin/product/productbanner");
		PageData pd=this.getPageData();
		//String productId=pd.getString("productId");
//		ProductVo product = productManager.selectProductByOption(pd);
//		if(product==null){aa
//			product = new ProductVo();
//		}
		pd.put("objectId", pd.getString("productId"));
		pd.put("pictureType", 1);
		List<PicturesVo> pictures = new ArrayList<PicturesVo>();
		
		if(StringUtils.isNotBlank(pd.getString("productId"))){
			pd.put("status", 1);
			pictures = picturesManager.queryList(pd);
		}
		model.addAttribute("pictures", pictures);
		return mv;
	}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/createCode")
	@ResponseBody
	public void createCode(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String text = WebConstant.MAIN_SERVER+"/weixin/product/toxxgoodsdetails?productId="+pd.getString("productId");  
        int width = 200;  
        int height = 200;  
        String format = "jpg";
        String logoImg = AppServerUtil.getClassesPath() +"resources"+ File.separator+"weixin"+ File.separator+"img"+ File.separator+"diahui-code.jpg";
        
        @SuppressWarnings("rawtypes")
		Hashtable hints= new Hashtable();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
         BitMatrix bitMatrix;
         InputStream in = null;
 		OutputStream os = null;
		try {
			BitMatrix matrix = MatrixToImageWriterEx.createQRCode(text, width, height);
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			Path file = new File("new.jpg").toPath();
			//MatrixToImageWriter.writeToPath(bitMatrix, format, file);
			MatrixToImageWriterEx.writeToFile(matrix, format, file.toAbsolutePath().toString(), logoImg);
			
			logger.info("图片所在地------file---"+file.toAbsolutePath());
			// 以文件流形式导出
		
			InputStream inputStream = new FileInputStream(file.toAbsolutePath().toString());
			
			@SuppressWarnings("resource")
			InputStream fis = new BufferedInputStream(inputStream);

			// 导出excel
			String fileName = "生成二维码.jpg";
			String encodingName = new String(fileName.getBytes("GB2312"), "ISO8859_1");
			response.reset();
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename=" + encodingName);
			os = response.getOutputStream();
			byte[] b = new byte['?'];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			os.flush();
		} catch (WriterException e) {
			logger.error("/admin/product/createCode --error", e);
		}  catch (IOException e) {
			logger.error("/admin/product/createCode --error", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (os != null) {
					os.close();
				}
				
			} catch (IOException e) {
				logger.error("finally关闭流异常", e);
			}
		}
	}
	
	 /**
	  * 保存上传的图片
	  * @param stream
	  * @param path
	  * @param filename
	  * @throws IOException
	  */
	 private void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException{      
        FileOutputStream fs=new FileOutputStream( path + File.separator + filename);
        byte[] buffer =new byte[1024*1024];
        int byteread = 0; 
        while ((byteread=stream.read(buffer))!=-1)
        {
           fs.write(buffer,0,byteread);
           fs.flush();
        } 
        fs.close();
        stream.close();      
    } 
	 
}
