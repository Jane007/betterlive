package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.convert.BeanConverter;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.Area;
import com.kingleadsw.betterlive.model.DeliverArea;
import com.kingleadsw.betterlive.model.Extension;
import com.kingleadsw.betterlive.model.Pictures;
import com.kingleadsw.betterlive.model.Product;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.service.AreaService;
import com.kingleadsw.betterlive.service.DeliverAreaService;
import com.kingleadsw.betterlive.service.ExtensionService;
import com.kingleadsw.betterlive.service.PicturesService;
import com.kingleadsw.betterlive.service.ProductService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.vo.ExtensionVo;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 商品  实际交互实现层
 * 2017-03-08 by chen
 */
@Component
@Transactional
public class ProductManagerImpl extends BaseManagerImpl<ProductVo,Product> implements ProductManager {
	
	private static final Logger logger = Logger.getLogger(ProductManagerImpl.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@Autowired
	private PicturesService picturesService;
	
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private DeliverAreaService deliverAreaService;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private ProductLabelManager productLabelManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductSpecManager productSpecManager;
	
	/**
    * 根据条件查询单个商品
    * @param pageData
    * @return
    */
	@Override
	public ProductVo selectProductByOption(PageData pageData) {
		Product product=productService.selectProductByOption(pageData);
		if(null == product){
			return null;
		}
		String detailExplain = product.getShare_explain();
		if(StringUtil.isNotNull(product.getDetailExplain())){
			detailExplain = product.getDetailExplain();
		}
		product.setDetailExplain(detailExplain);
		
		ProductVo productVo=po2voer.transfer(new ProductVo(),product);
		if(CollectionUtils.isNotEmpty(product.getListSpecVo())){
			List<ProductSpecVo> listProductSpecVo=new ArrayList<ProductSpecVo>(product.getListSpecVo().size());
			ProductSpecVo productSpecVo=null;
			for (ProductSpec productSpec : product.getListSpecVo()) {
				productSpecVo = new ProductSpecVo();
				productSpecVo.setSpec_id(productSpec.getSpec_id());
				productSpecVo.setSpec_name(productSpec.getSpec_name());
				productSpecVo.setSpec_price(productSpec.getSpec_price());
				productSpecVo.setSpec_img(productSpec.getSpec_img());
				productSpecVo.setStock_copy(productSpec.getStock_copy());
				productSpecVo.setSales_copy(productSpec.getSales_copy());
				productSpecVo.setLimit_max_copy(productSpec.getLimit_max_copy());
				productSpecVo.setLimit_start_time(productSpec.getLimit_start_time());
				productSpecVo.setLimit_end_time(productSpec.getLimit_end_time());
				productSpecVo.setPackage_desc(productSpec.getPackage_desc());
				if(productSpec.getDiscount_price() != null){
					productSpecVo.setDiscount_price(productSpec.getDiscount_price());
				}
				listProductSpecVo.add(productSpecVo);
			}
			productVo.setListSpecVo(listProductSpecVo);
		}
		
		
		if(CollectionUtils.isNotEmpty(product.getPictures())){
	//		List<PicturesVo> listProductBannerVo = new ArrayList<PicturesVo>(product.getPictures().size());
	//		PicturesVo productBannerVo = null;
			String[] pictureArray=new String[product.getPictures().size()];
			//for (Pictures pic : product.getPictures()) {
			for (int i = 0; i < product.getPictures().size(); i++) {
				
				/*productBannerVo = new PicturesVo();
				productBannerVo.setPicture_id(pic.getPicture_id());
				productBannerVo.setObject_id(pic.getObject_id());
				productBannerVo.setPicture_type(pic.getPicture_type());
				productBannerVo.setCreate_time(pic.getCreate_time());
				productBannerVo.setOriginal_img(pic.getOriginal_img());
				productBannerVo.setSmall_img(pic.getSmall_img());
				listProductBannerVo.add(productBannerVo);*/
				pictureArray[i]=product.getPictures().get(i).getOriginal_img();
			}
		//	productVo.setPictures(listProductBannerVo);
			
			productVo.setPictureArray(pictureArray);
		}
		
		if(CollectionUtils.isNotEmpty(product.getExtensionVos())){
			List<ExtensionVo> extensionVos = new ArrayList<ExtensionVo>(product.getExtensionVos().size());
			ExtensionVo extensionVo = null;
			for (Extension ext : product.getExtensionVos()) {
				extensionVo = new ExtensionVo();
				extensionVo.setExtensionId(ext.getExtensionId());
				extensionVo.setExtensionType(ext.getExtensionType());
				extensionVo.setIsHomepage(ext.getIsHomepage());
				extensionVo.setProductId(ext.getProductId());
				extensionVos.add(extensionVo);
			}
			productVo.setExtensionVos(extensionVos);
		}
		return productVo ;
	}
	
	/**
    * 查询所有的商品
    * @param pd
    * @return
    */
	@Override
	public List<ProductVo> queryProductList(PageData pd) {
		List<Product> listProduct=productService.queryProductList(pd);
		List<ProductVo> listProductVo=null;
		
		if(null !=listProduct && listProduct.size()>0){
			listProductVo=new ArrayList<ProductVo>();
			for (Product product : listProduct) {
				ProductVo productVo=po2voer.transfer(new ProductVo(),product);
				List<ProductSpecVo> listProductSpecVo= generator.transfer(ProductSpecVo.class,product.getListSpecVo()) ;
				productVo.setListSpecVo(listProductSpecVo);
				List<ExtensionVo> extensions = generator.transfer(ExtensionVo.class, product.getExtensionVos());
				productVo.setExtensionVos(extensions);
				listProductVo.add(productVo);
			}
		}
		return listProductVo;
	}
	
	
	/**
     * 分页查询商品
     * @param pd
     * @return
     */
	@Override
	public List<ProductVo> queryProductListPage(PageData pd) {
		return po2voer.transfer(ProductVo.class,productService.queryProductListPage(pd));
	}
	
	@Override
	public List<ProductVo> queryNotExistInExtension(PageData pd) {
		List<Product> listProduct = productService.queryNotExistInExtension(pd);
		List<ProductVo> listProductVo=null;
		
		if(null !=listProduct && listProduct.size()>0){
			listProductVo=new ArrayList<ProductVo>();
			for (Product product : listProduct) {
				ProductVo productVo=po2voer.transfer(new ProductVo(),product);
				List<ProductSpecVo> listProductSpecVo= generator.transfer(ProductSpecVo.class,product.getListSpecVo()) ;
				productVo.setListSpecVo(listProductSpecVo);
				List<ExtensionVo> extensions = generator.transfer(ExtensionVo.class, product.getExtensionVos());
				productVo.setExtensionVos(extensions);
				listProductVo.add(productVo);
			}
		}
		return listProductVo;
	}
   /**
    * 增加商品
    * @param product
    * @return
    */
	@Override
	public int insertProduct(ProductVo productVo) {
		int rst = 0;
		
		Product product = vo2poer.transfer(new Product(), productVo);
		rst = productService.insertProduct(product);
		productVo.setProduct_id(product.getProduct_id());
		
		if(product.getProduct_id() > 0){
			String [] proBannerArr = productVo.getPictureArray();
			List<Pictures> pictures = new  ArrayList<Pictures>(proBannerArr.length);
			Pictures picture = null; 
			for (int i = 0; i < proBannerArr.length; i++) {
				picture = new Pictures();
				picture.setObject_id(product.getProduct_id());
				picture.setOriginal_img(proBannerArr[i]);
				picture.setPicture_type(1);  //设置图片类型为商品轮播图
				picture.setSmall_img(proBannerArr[i]);
				pictures.add(picture);
			}
			picturesService.insertBatchFromList(pictures);
			ProductSpec productSpec = new ProductSpec();
			List<ProductSpec> listSpec = BeanConverter.convert(ProductSpec.class, product.getListSpecVo());
			for (int i = 0; i <listSpec.size(); i++) {
				productSpec.setStatus(1);
				listSpec.get(i).setStatus(1);   //启用
				listSpec.get(i).setProduct_id(product.getProduct_id());
			}
			rst = productSpecService.addBatchProductSpec(listSpec);
			
			//每周新品和人气推荐
			List<Extension> extensionList = new ArrayList<Extension>();
			if (productVo.getWeekly() == 1) {  //设置了每周推荐
				Extension extension = new Extension();
				extension.setExtensionType(1);
				extension.setProductId(productVo.getProduct_id());
				extension.setIsHomepage(productVo.getWeekly_homepage());
				extensionList.add(extension);
			}
			if (productVo.getTuijian() == 1) {   //设置了人气推荐
				Extension extension = new Extension();
				extension.setExtensionType(2);
				extension.setProductId(productVo.getProduct_id());
				extension.setIsHomepage(productVo.getWeekly_homepage());
				extensionList.add(extension);
			}
			if (!extensionList.isEmpty()) {
				extensionService.insertBatchFromList(extensionList);
			}
			
			
			 DeliverArea deliverArea=null;
		        List<DeliverArea> listAreas=new ArrayList<DeliverArea>();
		        
		        String deliverAddress=product.getDeliverAddress();
		        if("3".equals(deliverAddress)){
		        	
		        	String deliverType=product.getDeliverType();          //全国配送
		        	if("1".equals(deliverType)){  //1 是全部的ID 
		        		PageData pd=new PageData();
		    			pd.put("parentId", 0);
		    			pd.put("notAreaIds", "710000,810000,820000");   //除去港澳台
		    			List<Area> list=areaService.findAllAreaInfo(pd);
		        		
		    			if(null!=list && list.size()>0){
		    				for (int i = 0; i < list.size(); i++) {
		    					deliverArea=new DeliverArea(); 
		    					deliverArea.setProductId(product.getProduct_id());
		    					deliverArea.setAreaCode(list.get(i).getAreaId().toString());    //配送地区Id
		    					deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
		    					deliverArea.setAllChild(1);                                     //是否包含下级
		    					
		    					listAreas.add(deliverArea);
							}
		    			}
		    			
		        	}else{
		        		String areaIds =product.getAreaIds();          		//地区
		        		
		        		if(null!=areaIds && !"".equals(areaIds)){
		        			String[] areaIdArray=areaIds.split(",");
		        			
		        			for (int j = 0; j < areaIdArray.length; j++) {
		        				if(null==areaIdArray[j] || "".equals(areaIdArray[j])){
		        					continue;
		        				}
		        				deliverArea=new DeliverArea(); 
		        				deliverArea.setProductId(product.getProduct_id());
		        				deliverArea.setAreaCode(areaIdArray[j]);    //配送地区Id
		        				deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
		        				deliverArea.setAllChild(1);                                     //是否包含下级
		    	        		
		    	        		listAreas.add(deliverArea);
							}
		        		}
		        	}
		        }else if("1".equals(deliverAddress)){      //本市配送
		        	deliverArea=new DeliverArea(); 
		        	deliverArea.setProductId(product.getProduct_id());
    				deliverArea.setAreaCode(product.getWarehouse());    //配送地区Id
    				deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
    				deliverArea.setAllChild(1);                                     //是否包含下级
	        		listAreas.add(deliverArea);
	        		
		        }else{                            //本省配送
		        	PageData pd=new PageData();
	    			pd.put("areaId", product.getWarehouse());
	    			Area area=areaService.findAreaById(pd);
		        	
		        	deliverArea=new DeliverArea(); 
		        	deliverArea.setProductId(product.getProduct_id());
    				deliverArea.setAreaCode(area.getParentId().toString());    		//配送地区Id
    				deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
    				deliverArea.setAllChild(1);                                     //是否包含下级
	        		listAreas.add(deliverArea);
		        }
		        
		        deliverAreaService.addBatchDeliverArea(listAreas);
		        
		} else {
			logger.error("增加商品失败，商品基本信息插入失败");
		}
		
		return rst;
	}
	
	
	/**
    * 修改商品
    * 
    * @param productVo
    * @return
    */
	@Override
	public int updateProduct(ProductVo productVo) {
		int rst = 0;
		
		Product vo = vo2poer.transfer(new Product(), productVo);
		rst = productService.updateProduct(vo);
		String [] proBannerArr = productVo.getPictureArray();
		List<String> proBanners = new ArrayList<String>();
		
		if(proBannerArr!=null&&proBannerArr.length>0){//String[]转成数组
			for (int i = 0; i < proBannerArr.length; i++) {
				proBanners.add(proBannerArr[i]);
			}
		}
		PageData pd= new PageData();
		pd.put("objectId", productVo.getProduct_id());
		pd.put("pictureType", 1);
		List<Pictures> picturelist = picturesService.queryList(pd);
		
		if(picturelist!=null&&picturelist.size()>0){
			for (Pictures pictures : picturelist) {
				for (String banner : proBannerArr) {//里有的就删掉
					if(banner.equals(pictures.getOriginal_img())&&banner.equals(pictures.getSmall_img())){
						proBanners.remove(banner);
					}
				}
			}
		}
		
		
		
		List<Pictures> pictures = new  ArrayList<Pictures>();
		
		Pictures picture = null; 
		if(proBanners.size()>0){//如果全删除掉了，就要删除，否则就不用删除，还是用原来的
			picturesService.delete(pd);
		}
		if(proBanners!=null&&proBanners.size()>0){
			for (int i = 0; i < proBanners.size(); i++) {
				picture = new Pictures();
				picture.setObject_id(productVo.getProduct_id());
				picture.setOriginal_img(proBanners.get(i));
				picture.setPicture_type(1);  //设置图片类型为商品轮播图
				picture.setSmall_img(proBanners.get(i));
				pictures.add(picture);
			}
		}
		
		if(pictures.size()!=0){
			picturesService.insertBatchFromList(pictures);
		}
		
		
	    /**
	     * 配送地址:
	     *  1  删除配送地址
	     *  2 增加配送地址
	     */
		deliverAreaService.deleteDeliverAreaByPid(productVo.getProduct_id().toString());
		
	    DeliverArea deliverArea=null;
        List<DeliverArea> listAreas=new ArrayList<DeliverArea>();
	        
        String deliverAddress=productVo.getDeliverAddress();
        if("3".equals(deliverAddress)){
        	
        	String deliverType=productVo.getDeliverType();          //全国配送
        	if("1".equals(deliverType)){  //1 是全部的ID 
        		PageData pds=new PageData();
    			pds.put("parentId", 0);
    			pds.put("notAreaIds", "710000,810000,820000");   //除去港澳台
    			List<Area> list=areaService.findAllAreaInfo(pds);
        		
    			if(null!=list && list.size()>0){
    				for (int i = 0; i < list.size(); i++) {
    					deliverArea=new DeliverArea(); 
    					deliverArea.setProductId(productVo.getProduct_id());
    					deliverArea.setAreaCode(list.get(i).getAreaId().toString());    //配送地区Id
    					deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
    					deliverArea.setAllChild(1);                                     //是否包含下级
    					
    					listAreas.add(deliverArea);
					}
    			}	
			}else{
        		String areaIds =productVo.getAreaIds();          		//地区
        		
        		if(null!=areaIds && !"".equals(areaIds)){
        			String[] areaIdArray=areaIds.split(",");
        			
        			for (int j = 0; j < areaIdArray.length; j++) {
        				if(null==areaIdArray[j] || "".equals(areaIdArray[j])){
        					continue;
        				}
        				deliverArea=new DeliverArea(); 
        				deliverArea.setProductId(productVo.getProduct_id());
        				deliverArea.setAreaCode(areaIdArray[j]);    //配送地区Id
        				deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
        				deliverArea.setAllChild(1);                                     //是否包含下级
    	        		
    	        		listAreas.add(deliverArea);
					}
        		}
        	}
	    }else if("1".equals(deliverAddress)){      //本市配送
	        deliverArea=new DeliverArea(); 
        	deliverArea.setProductId(productVo.getProduct_id());
			deliverArea.setAreaCode(productVo.getWarehouse());    //配送地区Id
			deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
			deliverArea.setAllChild(1);                                     //是否包含下级
			listAreas.add(deliverArea);
 		
        }else{                            //本省配送
        	PageData pds=new PageData();
        	pds.put("areaId", productVo.getWarehouse());
		    Area area=areaService.findAreaById(pds);
        	
        	deliverArea=new DeliverArea(); 
        	deliverArea.setProductId(productVo.getProduct_id());
			deliverArea.setAreaCode(area.getParentId().toString());    		//配送地区Id
			deliverArea.setDeliverType(Integer.parseInt(deliverAddress));   //配送地址类型
			deliverArea.setAllChild(1);                                     //是否包含下级
			listAreas.add(deliverArea);
        }
        deliverAreaService.addBatchDeliverArea(listAreas);
       
        
      //每周新品和人气推荐
    	PageData extension = new PageData();
		extension.put("extensionType", 1);
		extension.put("productId", productVo.getProduct_id());
		Extension rqtj = extensionService.selectExtensionByOption(extension);
		if (productVo.getWeekly() == 1) {  //设置了每周推荐
			if(rqtj == null || rqtj.getExtensionId() <= 0){ //新增
				rqtj = new Extension();
				rqtj.setExtensionType(1);
				rqtj.setProductId(productVo.getProduct_id());
				rqtj.setIsHomepage(productVo.getWeekly_homepage());
				extensionService.insertExtension(rqtj);
			}else{ //修改
				rqtj.setIsHomepage(productVo.getWeekly_homepage());
				extensionService.updateExtension(rqtj);
			}
		}else{ //没设置每周推荐
			if(rqtj != null && rqtj.getExtensionId() > 0){ //已存在则删除
				extensionService.deleteExtensionById(rqtj.getExtensionId());
			}
		}
		
		extension.clear();
		extension.put("extensionType", 2);
		extension.put("productId", productVo.getProduct_id());
		rqtj = extensionService.selectExtensionByOption(extension);
		if (productVo.getTuijian() == 1) {   //设置了人气推荐
			if(rqtj == null || rqtj.getExtensionId() <= 0){ //新增
				rqtj = new Extension();
				rqtj.setExtensionType(2);
				rqtj.setProductId(productVo.getProduct_id());
				rqtj.setIsHomepage(productVo.getTuijian_homepage());
				extensionService.insertExtension(rqtj);
			}else{ //修改
				rqtj.setIsHomepage(productVo.getTuijian_homepage());
				extensionService.updateExtension(rqtj);
			}
		}else{ //没设置人气推荐
			if(rqtj != null && rqtj.getExtensionId() > 0){ //已存在则删除
				extensionService.deleteExtensionById(rqtj.getExtensionId());
			}
		}
		return rst;
	}
	
	
	/**
     *  删除商品
     */
	@Override
	public int deleteProductById(int productId) {
		int rst=productService.deleteProductById(productId);
		return rst;
	}

	
	@Override
	protected BaseService<Product> getService() {
		return productService;
	}

	
	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.biz.ProductManager#updateProductStatus(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public int updateProductStatus(PageData pd) {
		return productService.updateProductStatus(pd);
	}

	@Override
	public List<ProductVo> queryExtensionListPage(PageData pd) {
		List<Product> productList = productService.queryExtensionListPage(pd);
		if (productList != null && productList.size() > 0) {
			PageData activityMap = new PageData();
			for (Product product : productList) {
				activityMap.put("productId", product.getProduct_id());
				activityMap.put("activityType", 2);  //设置活动类型为专题
				float activityPrice = productService.queryMinProductPrice(activityMap);
				if (activityPrice != -1 && product.getPrice() != null 
						&& activityPrice < Float.parseFloat(product.getPrice())
						) {
					product.setPrice("" + activityPrice);
				}
			}
		}
		return generator.transfer(ProductVo.class, productList) ;
	}

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.biz.ProductManager#queryGuessLikeList(com.kingleadsw.betterlive.core.page.PageData)
	 */
	@Override
	public List<ProductVo> queryGuessLikeList(PageData pd) {
		pd.put("isOnline", 1);  //只查询线上的商品
		List<Product> listProduct = productService.queryProductBaseInfoList(pd);
		if (listProduct != null && listProduct.size() > 0) {
			for (Product product : listProduct) {
				pd.put("productId", product.getProduct_id());
				pd.put("activityType", 2);  //设置活动类型为专题
				float activityPrice = productService.queryMinProductPrice(pd);
				if (activityPrice != -1 && product.getPrice() != null 
						&& activityPrice < Float.parseFloat(product.getPrice())) {
					product.setPrice("" + activityPrice);
				}
			}
		}
		return generator.transfer(ProductVo.class, listProduct);
	}

	/**
	 * 1.1版项目查询(每周新品，人气推荐)
	 */
	@Override
	public List<ProductVo> queryIndexProduct(int status, int extensionType,
			int isHomepage, PageData pd, int pageSize, int acitivityFlag) {
		
		pd.put("status",status);
		pd.put("extensionType", extensionType);
		pd.put("isHomepage", isHomepage);
		pd.put("isOnline", 1);
		
		if (pd.get("pageView") == null) {
			PageView pv = new PageView();
			pv.setPageSize(pageSize);
			pd.put("pageView", pv);
		}
		
		List<Product> productList = productService.queryExtensionListPage(pd);
		if (productList != null && productList.size() > 0) {
			for (Product product : productList) {
				//专题
				PageData specialParams = new PageData();
				specialParams.put("status", 1);
				specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("productId", product.getProduct_id());
				SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
				
				PageData activityMap = new PageData();
				activityMap.put("productId", product.getProduct_id());
				activityMap.put("proStatus", 1);
				activityMap.put("specStatus", 1);
				if(specialVo != null){
					activityMap.put("activityId", specialVo.getSpecialId());
				}
				ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(activityMap);
				if(specVo == null){
					continue;
				}
				
				product.setPrice(specVo.getSpec_price());
				if (specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
					product.setDiscountPrice(specVo.getDiscount_price()+"");
				}
				int fakeSalesCopy = product.getFake_sales_copy();
				if(specVo.getTotal_sales_copy() != null){
					product.setSalesVolume(specVo.getTotal_sales_copy().intValue()+fakeSalesCopy);
				}else{
					product.setSalesVolume(fakeSalesCopy);
				}
				
				if(specialVo == null){
					specialVo = new SpecialVo();
					specialVo.setSpecialId(0);
					
				}else{
					product.setActivityType(specialVo.getSpecialType());
					product.setIsActivity("NO");
					if(StringUtil.isNotNull(specVo.getActivity_price()) && new BigDecimal(specVo.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
						product.setActivityPrice(specVo.getActivity_price());
						product.setIsActivity("YES");
					}
					product.setActivity_id(specialVo.getSpecialId());
				}
				PageData apParams = new PageData();
				apParams.put("productId", product.getProduct_id());
				apParams.put("status", 0);
				apParams.put("nowTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				ProductLabelVo plvo = productLabelManager.queryOne(apParams);
				if(plvo != null && StringUtil.isNotNull(plvo.getLabelTitle())){
					product.setLabelName(plvo.getLabelTitle());
				}
			}
		}else{
			productList = new ArrayList<Product>();
		}
		return generator.transfer(ProductVo.class, productList);
	}
	
	@Override
	public List<ProductVo> queryProductListByLabel(PageData pd) {
		List<Product> listProduct=productService.queryProductListByLabel(pd);
		List<ProductVo> listProductVo=po2voer.transfer(ProductVo.class,listProduct);		
		return listProductVo;
	}

	@Override
	public List<ProductVo> queryListByArticle(PageData pd) {
		try {
			
			List<Product> productList = productService.queryListByArticle(pd);
					
			if (productList != null && productList.size() > 0) {
				for (Product product : productList) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", product.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					PageData activityMap = new PageData();
					activityMap.put("productId", product.getProduct_id());
					activityMap.put("proStatus", 1);
					activityMap.put("specStatus", 1);
					if(specialVo != null){
						activityMap.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(activityMap);
					if(specVo == null){
						continue;
					}
					
					product.setPrice(specVo.getSpec_price());
					if (specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
						product.setDiscountPrice(specVo.getDiscount_price()+"");
					}
					int fakeSalesCopy = product.getFake_sales_copy();
					if(specVo.getTotal_sales_copy() != null){
						product.setSalesVolume(specVo.getTotal_sales_copy().intValue()+fakeSalesCopy);
					}else{
						product.setSalesVolume(fakeSalesCopy);
					}
					
					if(specialVo == null){
						specialVo = new SpecialVo();
						specialVo.setSpecialId(0);
					}else{
						product.setActivityType(specialVo.getSpecialType());
						product.setIsActivity("NO");
						if(StringUtil.isNotNull(specVo.getActivity_price()) && new BigDecimal(specVo.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
							product.setActivityPrice(specVo.getActivity_price());
							product.setIsActivity("YES");
						}
						product.setActivity_id(specialVo.getSpecialId());
					}
				}
			}else{
				productList = new ArrayList<Product>();
			}
			
			return generator.transfer(ProductVo.class, productList);
		} catch (Exception e) {
			logger.error("queryListByArticle", e);
			return new ArrayList<ProductVo>();
		}
	}

	/**
	 * 邀请好友分享后页面展示产品列表
	 */
	@Override
	public List<ProductVo> queryRegisterProductListPage(PageData pd) {
		List<Product> list = productService.queryRegisterProductListPage(pd);
		List<ProductVo> listVo = new ArrayList<ProductVo>();
		for (Product product : list) {
			ProductVo pvo = new ProductVo();
			pvo.setProduct_id(product.getProduct_id());
			pvo.setProduct_name(product.getProduct_name());
			pvo.setPrice(product.getPrice());
			pvo.setProduct_logo(product.getProduct_logo());
			pvo.setDiscountPrice(pvo.getDiscountPrice());
			if(pvo.getDiscountPrice()==null || pvo.getDiscountPrice().equals("-1")){
				pvo.setDiscountPrice(product.getPrice());
			}
			
			listVo.add(pvo);
		}
		return listVo;
	}
	
	
	
	
}
