package com.kingleadsw.betterlive.controller.web.singlecoupon;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
/**
 * 单品红包
 * @author zhangjing
 *
 * @date 2017年5月9日
 */
@Controller
@RequestMapping(value = "/admin/singlecoupon")
public class SingleCouponController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(SingleCouponController.class);
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/singlecoupon/list_coupon");
		return mv;
	}
	
	@RequestMapping(value="/queryCouponAllJson")
	@ResponseBody
	public void queryCouponAllJson(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		
		List<SingleCouponVo> listCoupon =singleCouponManager.queryListPage(pd);
		
		if(listCoupon!=null && listCoupon.size()>0){
			this.outEasyUIDataToJson(pd, listCoupon, response);
		}else{
			this.outEasyUIDataToJson(pd, new ArrayList<SingleCouponVo>(), response);
		}
	}
	@RequestMapping(value="/toAddCoupon")
	public ModelAndView toAddCoupon(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/singlecoupon/add_coupon");
		PageData pd = this.getPageData();
		
		pd.put("productStatus", 1);
		//pd.put("salePromotion", "YES");
		List<ProductVo> products  =productManager.queryProductList(pd);    
		model.addAttribute("products", products);
		return mv;
	}
	
	
	/**
	 * 添加单品红包
	 * @param request
	 * @param response
	 * @param salePromotionVo
	 */
	@RequestMapping(value="/addCoupon")
	public void addCoupon(HttpServletRequest request, HttpServletResponse response,SingleCouponVo singleCouponVo){
		logger.info("连接地址： /admin/singlecoupon/addCoupon ,操作：增加单品红包。  操作状态： 开始");
		JSONObject json = new JSONObject();
		//PageData pd = this.getPageData();
		String specIds = request.getParameter("speclist");
		String[] spacIds = specIds.split(",");
		if(StringUtil.isNull(specIds) || spacIds == null || specIds.length() <= 0){
			json.put("result", "fail");
			json.put("msg", "修改单品红包失败");
			this.outJson(json.toString(), response);
			return;
		}
		String banner = "";
	   if(StringUtils.isNotEmpty(singleCouponVo.getCouponBanner()) 
			   && -1 != singleCouponVo.getCouponBanner().lastIndexOf(",")){
		   banner=singleCouponVo.getCouponBanner().substring(0, singleCouponVo.getCouponBanner().lastIndexOf(","));
		   singleCouponVo.setCouponBanner(banner);
        }
		if(singleCouponVo.getCouponId()!=null&&singleCouponVo.getCouponId()>0){//修改
			int result = singleCouponManager.updateByPrimaryKey(singleCouponVo);
			if(result>0){
				int count  = this.updateCouponSpec(specIds,singleCouponVo);
				if(count<=0){
					json.put("result", "fail");
					json.put("msg", "修改单品红包规格失败");
				}else{
					json.put("result", "success");
					json.put("msg", "修改单品红包规格成功");
				}
			}else{
				json.put("result", "fail");
				json.put("msg", "修改单品红包失败");
			}
			
		}else{//新增
			
			PageData pdt = new PageData();
    		pdt.put("pStatus", 1);
    		pdt.put("specStatus", 1);
    		pdt.put("specIds", specIds);
    		List<ProductSpecVo> pss = productSpecManager.queryListProductSpec(pdt);
    		if(pss == null || pss.size() < spacIds.length){
    			json.put("result", "fail");
    			json.put("msg", "存在已下架的商品");
    			this.outJson(json.toString(), response);
        		return;
    		}
			int result=singleCouponManager.insert(singleCouponVo);
			if(result>0){
				if(StringUtils.isNotBlank(specIds)){
					int count = this.AddCouponSpec(specIds, singleCouponVo);
						if(count<=0){
							json.put("result", "fail");
							json.put("msg", "新增单品红包规格失败");
						}else{
							json.put("result", "success");
							json.put("msg", "新增单品红包规格成功");
						}
					}
				}else{//插入失败
					json.put("result", "fail");
					json.put("msg", "新增单品红包失败");
				}
		}
		
			this.outJson(json.toString(), response);
			logger.info("连接地址：/admin/singlecoupon/addCoupon ,操作：增加单品红包。  操作状态： 结束");
		}
	
	
	
	
	@RequestMapping(value="/toEditCoupon")
	public ModelAndView toEditCoupon(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/singlecoupon/edit_coupon");
		PageData pd = this.getPageData();
		
		String couponId = pd.getString("couponId");
		SingleCouponVo spvo = new SingleCouponVo();
		if(StringUtils.isNotBlank(couponId)){
			spvo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(couponId));
			model.addAttribute("singleCouponVo", spvo);
		}
		pd.put("productStatus", 1);
		List<ProductVo> products  =productManager.queryProductList(pd);  
		String productIds = "";
		String productName = "";
		String specIds = "";
		List<SingleCouponSpecVo> psvo = spvo.getListSpec();
		if(psvo!=null && psvo.size()>0){
			for (SingleCouponSpecVo couponSpecVo : psvo) {
				if(!productName.contains(couponSpecVo.getProductName())){
					productName+=couponSpecVo.getProductName()+",";
				}
				productIds+=couponSpecVo.getProductId()+",";
				specIds+=couponSpecVo.getSpecId()+",";
			}
		}
		model.addAttribute("specIds", specIds);
		model.addAttribute("products", products);
		model.addAttribute("productIds", productIds);
		model.addAttribute("productName", productName);
		return mv;
	}
	
	
	
	/**
	 * 逻辑删除促销活动
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delSingleCoupon")
	public void delSingleCoupon(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/singlecoupon/delSingleCoupon ,操作：单删除品红包。  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String couponIdArray = pd.getString("couponIdArray");
		int result=0;
		if(StringUtils.isNotBlank(couponIdArray)){
			String[] couponIdArr = couponIdArray.split(",");
			for (String pid : couponIdArr) {
				if(StringUtils.isNotBlank(pid)){
					SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(pid));
					singleCouponVo.setStatus(0);//1正常，0删除
					 result = singleCouponManager.updateByPrimaryKey(singleCouponVo);
					
				}
			}
			if(result>0){
				json.put("result", "succ");
			}else{
				json.put("result", "fail");
				json.put("msg", "删除单个红包活动失败！");
			}
			
		}
		logger.info("连接地址： /admin/singlecoupon/delSingleCoupon ,操作：单删除品红包。  操作状态： 结束");
		this.outObjectToJson(json, response);
	}
	
	@RequestMapping(value="/querySpecByProjectIdModify")
	public void querySpecByProjectIdModify(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		String productIdArray = pd.getString("productIdArray");
		String couponId = pd.getString("couponId");
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
			
				list = pvo.getListSpecVo();
				for (ProductSpecVo productSpecVo : list) {
					pd.put("couponId", couponId);
					pd.put("specId", productSpecVo.getSpec_id());
					SingleCouponSpecVo scsv = singleCouponSpecManager.queryOne(pd);
					if(scsv!=null){
						productSpecVo.setLinkUrl(scsv.getLinkUrl());
					}
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
	
	
	private int updateCouponSpec(String specIds,SingleCouponVo singleCouponVo){
		int count=0;
		singleCouponVo = singleCouponManager.selectByPrimaryKey(singleCouponVo.getCouponId());
		List<SingleCouponSpecVo> listSpecs = singleCouponVo.getListSpec();
		String url = WebConstant.MAIN_SERVER+"/weixin/singlecoupon/getRedpacket?couponId="+singleCouponVo.getCouponId();

		if(StringUtils.isNotBlank(specIds)){
			if(specIds.contains(",")){
				String[] array = specIds.split(",");
				
					if(array.length>=listSpecs.size()){//要新增
						for (String specId : array) {
							PageData spcpd = new PageData();
							spcpd.put("specId", Integer.parseInt(specId));
							spcpd.put("couponId", singleCouponVo.getCouponId());
							SingleCouponSpecVo scpv = singleCouponSpecManager.queryOne(spcpd);
							if(scpv==null){
								ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
								scpv = new SingleCouponSpecVo();
								scpv.setProductId(psvo.getProduct_id());
								scpv.setSpecId(psvo.getSpec_id());
								scpv.setCouponId(singleCouponVo.getCouponId());
								String linkUrl=url+"&specId="+specId;
								scpv.setLinkUrl(linkUrl);
								count = singleCouponSpecManager.insert(scpv);
							}else{//查出有数据,不做任何修改
								count=1;
							}
						}
					}else{//要删除
						PageData spcpd = new PageData();
						spcpd.put("couponId", singleCouponVo.getCouponId());
						spcpd.put("editSpec", "YES");
						spcpd.put("ids", array);
						count = singleCouponSpecManager.delete(spcpd);
					}
				
			}else{//一个规格做活动
				String[] array ={specIds};
				PageData spcpd = new PageData();
				spcpd.put("couponId", singleCouponVo.getCouponId());
				spcpd.put("editSpec", "YES");
				spcpd.put("ids", array);
				spcpd.put("specId", Integer.parseInt(specIds));
				
				SingleCouponSpecVo scpv = singleCouponSpecManager.queryOne(spcpd);
				if(listSpecs != null && listSpecs.size()==1&&scpv!=null){
					count=1;
				}else if(listSpecs != null && listSpecs.size()>1&&scpv!=null){
					count = singleCouponSpecManager.delete(spcpd);
				}else{
					if(scpv==null){
						ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
						scpv = new SingleCouponSpecVo();
						scpv.setCouponId(singleCouponVo.getCouponId());
						scpv.setSpecId(Integer.parseInt(specIds));
						scpv.setProductId(psvo.getProduct_id());
						String linkUrl=url+"&specId="+specIds;
						scpv.setLinkUrl(linkUrl);
						count = singleCouponSpecManager.insert(scpv);
					}else{
						count = 1;
					}
				}
				
			}
		}else{//所有的规格都删除了
			PageData spcpd = new PageData();
			spcpd.put("couponId", singleCouponVo.getCouponId());
			count = singleCouponSpecManager.delete(spcpd);
		}
		return count;
	}
	private int AddCouponSpec(String specIds,SingleCouponVo singleCouponVo){
		int count=0;
		String url = WebConstant.MAIN_SERVER+"/weixin/singlecoupon/getRedpacket?couponId="+singleCouponVo.getCouponId();
		if(specIds.contains(",")){
			String[] array = specIds.split(",");
			for (String sid : array) {
				PageData spcpd = new PageData();
				spcpd.put("specId", Integer.parseInt(sid));
				ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
				spcpd.put("couponId", singleCouponVo.getCouponId());
				SingleCouponSpecVo psv = new SingleCouponSpecVo();
				psv.setProductId(psvo.getProduct_id());
				psv.setSpecId(psvo.getSpec_id());
				psv.setCouponId(singleCouponVo.getCouponId());
				String linkUrl=url+"&specId="+sid;
				psv.setLinkUrl(linkUrl);
				count = singleCouponSpecManager.insert(psv);
				
			}
		}else{//一个规格
			PageData spcpd = new PageData();
			spcpd.put("specId", specIds);
			ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
			SingleCouponSpecVo psv = new SingleCouponSpecVo();
			psv.setProductId(psvo.getProduct_id());
			psv.setSpecId(psvo.getSpec_id());
			psv.setCouponId(singleCouponVo.getCouponId());
			String linkUrl=url+"&specId="+specIds;
			psv.setLinkUrl(linkUrl);
			count = singleCouponSpecManager.insert(psv);
		}
		return count;
	}

}
