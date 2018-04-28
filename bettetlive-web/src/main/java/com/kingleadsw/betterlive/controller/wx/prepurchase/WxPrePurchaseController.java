package com.kingleadsw.betterlive.controller.wx.prepurchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
/**
 * 预购管理
 * @author zhangjing
 * @date 2017年3月14日 下午5:53:55
 */
@Controller
@RequestMapping(value = "/weixin/prepurchase")
public class WxPrePurchaseController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxPrePurchaseController.class);
	
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ProductManager productManager; 
	
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private OrderProductManager orderProductManager; 
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	

	
	@RequestMapping("findList")
	public ModelAndView findList(HttpServletRequest request,HttpServletResponse response){
		logger.info("进入微信预购....开始");
		
		ModelAndView modelAndView=new ModelAndView("weixin/prepurchase/wx_prepurchase");
		logger.info("进入微信预购....结束");
		return modelAndView;
	}
	@RequestMapping(value = "/queryPreProductAllJson")
	@ResponseBody
	public void queryPreProductAllJson(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/prepurchase/queryPreProductAllJson--->begin");
		JSONObject json=new JSONObject(); 
		PageData pd = this.getPageData();
		pd.put("status", 1);//PreProduct正常状态
		List<PreProductVo> list = preProductManager.queryListPage(pd);
		json.put("list", list);
		logger.info("/weixin/prepurchase/queryPreProductAllJson--->end");
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 跳转到预购详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toPreProductdetail")
	public ModelAndView toPreProductdetail(HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView();
		PageData pd = this.getPageData();
		
		String extension_type = pd.getString("extension_type");    //商品类型     3 预售商品
		
		CustomerVo customer=Constants.getCustomer(request);
		pd.put("status","1");
		ProductVo product  =productManager.selectProductByOption(pd);
		
		if(null==product){
			modelAndView.addObject("tipsTitle", "商品信息提示");
			modelAndView.addObject("tipsContent", "您访问的商品已下架");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}	
		
		String details=product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		List<CommentVo> comments=commentManager.queryCommentListByProductId(product.getProduct_id());
		modelAndView.addObject("comments",comments);
		
		
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
	
		pdData.put("activityType", 1);//预售
		listActivtyProduct = activityProductManager.queryList(pdData);
		
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			//设置每个商品规格的活动价
			List<ProductSpecVo> speclist = product.getListSpecVo();
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				PageData spd=new PageData();
				spd.put("preId", spec.getActivity_id());
				PreProductVo preProductVo = preProductManager.selectPreProductByOption(spd);
				if(preProductVo!=null){//预售
					Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
					Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
					if(dt.after(new Date())&&start.before(new Date())){
						if(speclist!=null&&speclist.size()>0){
							for (ProductSpecVo productSpecVo : speclist) {
								if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
								}
							}
						}
					}
				}
				
			}
		}
		
		/**
		 * 产品详情下面显示 猜你喜欢
		 */
		PageData pageData=new PageData();
		pageData.put("productStatus", 1);  //商品状态为上架
		pageData.put("eatRecommend","1");  //是猜你喜欢
		List<ProductVo> listProduct=productManager.queryProductList(pageData);
		
		
		/**
		 * 获取众筹信息
		 */
		PreProductVo preProduct = preProductManager.selectPreProductByOption(pd);
		if (preProduct != null) {
			Date raiseStart = DateUtil.stringToDate(preProduct.getRaiseStart());
			Date raiseEnd = DateUtil.stringToDate(preProduct.getRaiseEnd());
			Date now = new Date();
			if (now.after(raiseStart) && now.before(raiseEnd)) {
				modelAndView.addObject("isActive", "true");
			}
		}
		
		
		
		//根据条件查出此用户是否买过限购产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		productPd.put("customerId", customer.getCustomer_id());
		List<ProductSpecVo> speclists = product.getListSpecVo();
		if(speclists!=null&&speclists.size()>0){
			for (ProductSpecVo productSpecVo : speclists) {
				int hasBuy = 0;
				int restCopy = 100;
				if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
					
					Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
					Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
					if(dt.after(new Date())&&start.before(new Date())){//限购时间内				
						if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
								if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())){
									productPd.put("startTime", productSpecVo.getLimit_start_time());
								}
								if(StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
									productPd.put("endTime", productSpecVo.getLimit_end_time());
								}
								productPd.put("hasBuy", "hasBuy");//已付款的订单
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								//按产品id 和规格查出所有买过此商品的订单详情
								List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(productPd);//查出之前下过的单
								if(orderProductlist!=null && orderProductlist.size()>0){
									
									for (OrderProductVo orderProductVo : orderProductlist) {
										hasBuy+=orderProductVo.getQuantity();
									}
									restCopy = productSpecVo.getLimit_max_copy()-hasBuy;
									productSpecVo.setRest_copy(restCopy);
									productSpecVo.setHasBuy_copy(hasBuy);
								}else{
									productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
									productSpecVo.setHasBuy_copy(0);
								}
						}
					}else{//限购时间外的不限购-1表示不限购
						productSpecVo.setLimit_max_copy(-1);
					}
				}
			}
			
			
		}		
		
		
		
		
			
		modelAndView.addObject("extension_type",extension_type);
		modelAndView.addObject("productInfo",product);
		modelAndView.addObject("listProduct",listProduct);
		modelAndView.addObject("preProduct",preProduct);
		modelAndView.addObject("mobile",customer.getMobile());
		
		modelAndView.setViewName("weixin/prepurchase/wx_prepurchase_detail");
		
		return modelAndView;
	} 
	
	
	/**
	 * 查询商品规格
	 */
	@RequestMapping(value = "/queryProductSpecAllJson")
	@ResponseBody
	public void queryProductSpecAllJson(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/prepurchase/queryProductSpecAllJson--->begin");
		JSONObject json=new JSONObject(); 
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		try {
			pd.put("pStatus", 1); //查询有效的商品
			pd.put("specStatus", 1); //查询有效的商品规格
			ProductVo pv = productManager.selectProductByOption(pd);
			List<ProductSpecVo> list = pv.getListSpecVo();
			PageData productPd = new PageData();
			List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
			productPd.put("customerId", customer.getCustomer_id());
			if(list!=null&&list.size()>0){
				for (ProductSpecVo productSpecVo : list) {
					int hasBuy = 0;
					int restCopy = 100;
					pd.put("specId", productSpecVo.getSpec_id());
					listActivtyProduct = activityProductManager.queryList(pd);
					if(productSpecVo.getLimit_max_copy() != -1 && StringUtils.isNotBlank(productSpecVo.getLimit_start_time()) 
							&& StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
						
						Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
						Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
						if(dt.after(new Date())&&start.before(new Date())){//限购时间内
							if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
								productPd.put("productId", productSpecVo.getProduct_id());
								productPd.put("startTime", productSpecVo.getLimit_start_time());
								productPd.put("endTime", productSpecVo.getLimit_end_time());
								
								productPd.put("hasBuy", "hasBuy");//已付款的订单
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								//按产品id 和规格查出所有买过此商品的订单详情
								List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(productPd);//查出之前下过的单
								if(orderProductlist!=null && orderProductlist.size()>0){
										
									for (OrderProductVo orderProductVo : orderProductlist) {
										hasBuy+=orderProductVo.getQuantity();
									}
									restCopy = productSpecVo.getLimit_max_copy()-hasBuy;
									productSpecVo.setRest_copy(restCopy);
									productSpecVo.setHasBuy_copy(hasBuy);
								}else{
									productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
									productSpecVo.setHasBuy_copy(0);
								}
								List<ShoppingCartVo>  listShoppingCartVo=shoppingCartManager.queryListShoppingCart(productPd);
								int carNums=0;
								int carCanAdd=0;
								if(listShoppingCartVo!=null&&listShoppingCartVo.size()>0){
									for (ShoppingCartVo shoppingCartVo : listShoppingCartVo) {
										carNums+=shoppingCartVo.getAmount();
									}
									carCanAdd = productSpecVo.getLimit_max_copy()-carNums;
									productSpecVo.setCarNums(carNums);
									productSpecVo.setCarCanAdd(carCanAdd);
								}else{
									productSpecVo.setCarNums(0);
									productSpecVo.setCarCanAdd(productSpecVo.getLimit_max_copy());
								}
							}
						}else{//限购时间外
							productSpecVo.setLimit_max_copy(-1);
						}
					} else {  //商品没有限购
						productSpecVo.setLimit_max_copy(-1);
						productSpecVo.setHasBuy_copy(0);
						productSpecVo.setRest_copy(productSpecVo.getStock_copy());
					}
					
					if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
						for (ActivityProductVo spec : listActivtyProduct) {//符合时间的活动
							PageData spd=new PageData();
							if(spec.getActivity_type()==1){//预售
								spd.put("preId", spec.getActivity_id());
								PreProductVo preProductVo = preProductManager.selectPreProductByOption(spd);
								if(preProductVo!=null){
									Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
									Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
									if(dt.after(new Date())&&start.before(new Date())){
										productSpecVo.setActivity_price(spec.getActivity_price());
										break;
									}
								}
								
							}else{//专题
								spd.put("specialId", spec.getActivity_id());
								spd.put("status", 1);
								SpecialVo svo = specialMananger.selectSpecialByOption(spd);
								if(svo!=null){
									Date dt = DateUtil.stringToDate(svo.getEndTime());
									Date start = DateUtil.stringToDate(svo.getStartTime());
									if(dt.after(new Date())&&start.before(new Date())){
										productSpecVo.setActivity_price(spec.getActivity_price());
										break;
									}
								}
							}
						}
					}
				}
			}
			
			//需要对过期的活动进行筛选
			if(!list.isEmpty() && list.size()>0){
				json.put("result","succ");
				json.put("list", list);
			}else{
				json.put("result","fail");
			}
		} catch (Exception e) {
			logger.error("/weixin/prepurchase/queryProductSpecAllJson---> exception ", e);
			
			json.put("result","exec");
		}
		
		logger.info("/weixin/prepurchase/queryProductSpecAllJson--->end");
		this.outObjectToJson(json, response);
	}
	
	
}
