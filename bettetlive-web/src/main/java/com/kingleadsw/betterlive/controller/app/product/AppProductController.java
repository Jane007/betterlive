package com.kingleadsw.betterlive.controller.app.product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ActivityManager;
import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CollectionVo;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
import com.kingleadsw.betterlive.vo.UserGroupVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 会生活首页控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/app/product")
public class AppProductController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(AppProductController.class);

	@Autowired
	private ProductManager productManager;  
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ActivityManager activityManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private FreightManager freightManager;
	@Autowired
	private SalePromotionManager salePromotionManager;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private CollectionManager collectionManager;
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
	@Autowired
	private PostageManager postageManager; 
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	
	/**
	 * 查询商品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/productList")
	@ResponseBody
	public Map<String, Object> queryProductAllJson(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入每周新品、每日推荐列表开始");
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String type = pd.getString("extensionType");
		if (!StringUtil.isIntger(type)) {
        	return CallBackConstant.PARAMETER_ERROR.callbackError("列表类型为空");
        }
		
		List<ProductVo> listProduct = null;
		try {
			logger.info("extensionType：" + type);
			int extensionType = 0;
			if(!StringUtils.isEmpty(type)){
				extensionType = Integer.parseInt(type);
			}
			listProduct = this.queryProduct(1,extensionType,0,pd);//每周新品、人气推荐列表
		} catch (Exception e) {
			logger.error("/app/product/productList 查询产品异常...", e);
			return CallBackConstant.FAILED.callback();
		}
		
		resultMap.put("list", listProduct);
		logger.info("进入每周新品、每日推荐列表结束");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * 查询产品相关信息
	 *@author zhangjing
	 *@date 2017年3月14日 下午3:27:22
	 *@param status 商品状态，1：上架 ,2：下架，3：删除; 
	 *@param extensionType 1:每周新品，2：人气推荐
	 *@param isHomepage 1：是；0：否，最多只能有4个
	 *@param pd 请求参数封装
	 *@return
	 */
	private List<ProductVo> queryProduct(int status,int extensionType,int isHomepage,PageData pd){
 		pd.put("status",status);
		pd.put("extensionType", extensionType);
		pd.put("isHomepage", isHomepage);
		pd.put("isOnline", 1); 
		if (pd.get("pageView") == null) {
			PageView pv = new PageView();
			pv.setPageSize(4);
			pd.put("pageView", pv);
		}
		return productManager.queryExtensionListPage(pd);
	}
	
	
	/**
	 * 跳转到商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toAppGoodsdetails")
	@ResponseBody
	public Map<String, Object> toAppGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入app端查询商品详情页面");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		if(null == pd.getInteger("productId")){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品ID不能为空");
		}
		
		pd.put("status", 1); //上架的商品
		pd.put("isOnline", 1);  //只查询线上的商品
		ProductVo product = productManager.selectProductByOption(pd);
		if(null == product){
			return CallBackConstant.FAILED.callbackError("商品已下架");
		}
		
		String details = product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		
		
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
		pdData.put("activityType", 2);//专题
		listActivtyProduct = activityProductManager.queryList(pdData);
		String activityTheme="";
		String linkUrl="";//点专题跳转页面
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			for (ActivityProductVo spec : listActivtyProduct) {//符合时间的活动
				PageData spd=new PageData();
				spd.put("specialId", spec.getActivity_id());
				spd.put("status", 1);
				SpecialVo svo = specialMananger.selectSpecialByOption(spd);
				if(svo!=null&&svo.getSpecialType()==1){//限时活动
					Date dt = DateUtil.stringToDate(svo.getEndTime());
					Date start = DateUtil.stringToDate(svo.getStartTime());
					if(dt.after(new Date())&&start.before(new Date())){
						activityTheme = svo.getSpecialName();
						linkUrl = svo.getSpecialPage();
						break;
					}
				}
			}
			
			
			//设置每个商品规格的活动价
			List<ProductSpecVo> speclist = product.getListSpecVo();
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				PageData spd=new PageData();
				spd.put("specialId", spec.getActivity_id());
				spd.put("status", 1);
				SpecialVo svo = specialMananger.selectSpecialByOption(spd);
				if(svo!=null){//专题活动
					Date dt = DateUtil.stringToDate(svo.getEndTime());
					Date start = DateUtil.stringToDate(svo.getStartTime());
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
		
		List<CommentVo> comments = commentManager.queryCommentListByProductId(product.getProduct_id());
		map.put("comments", comments);
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		String token = request.getParameter("token");
		if (StringUtil.isNotNull(token)) {   //token不为空
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer != null) {  //用户不为空
				productPd.put("customerId", customer.getCustomer_id());
				List<ProductSpecVo> speclists = product.getListSpecVo();
				if(speclists!=null&&speclists.size()>0){
					for (ProductSpecVo productSpecVo : speclists) {
						int hasBuy = 0;
						int restCopy = 0;
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
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
									List<ShoppingCartVo> listShoppingCartVo = shoppingCartManager.queryListShoppingCart(productPd);
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
						}
					}
				}
			}
		}
		
		product.setSalesVolume(product.getSalesVolume()+product.getFake_sales_copy());
		map.put("productInfo", product);
		return CallBackConstant.SUCCESS.callback(map);
	} 
	
	
	
	/**
	 * 猜你喜欢
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toFavoriteGoods")
	@ResponseBody
	public Map<String, Object> toFavoriteGoods(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入app端查询商品详情页面");
		
		Map<String,Object> map =new HashMap<String, Object>();
		
		//产品详情下面显示 猜你喜欢
		PageData pageData=new PageData();
		pageData.put("eatRecommend","1");
		pageData.put("productStatus", 1);
		List<ProductVo> listProduct=productManager.queryProductList(pageData);
		if(!listProduct.isEmpty() && listProduct.size()>0){
			
			for (int i = 0; i <listProduct.size(); i++) {
				if(null!=listProduct.get(i).getProduct_logo() && !"".equals(listProduct.get(i).getProduct_logo())){
					String[] pictureArrays=listProduct.get(i).getProduct_logo().split(",");
					listProduct.get(i).setPictureArray(pictureArrays);
				}
			}
		}
		
		map.put("listProduct", listProduct);
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
     * 根据省级编码和市级编码获取该地市的运费信息
     */
    @RequestMapping("/getfreight")
    @ResponseBody
    public Map<String,Object> getfreight(HttpServletRequest request){
    	logger.info("获取运费信息开始");
        PageData pageData = this.getPageData();
        CustomerVo customer = null;
        try {
        	customer = Constants.getCustomer(request);
            if(customer == null){
                return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息为空");
            }
            String provinceCode = pageData.getString("provinceCode");  //省编码
            if (StringUtil.isNull(provinceCode)) {
            	return CallBackConstant.PARAMETER_ERROR.callbackError("省级编码为空");
            }
            String areaCode = pageData.getString("areaCode");  //地市编码
            if (StringUtil.isNull(areaCode)) {
            	return CallBackConstant.PARAMETER_ERROR.callbackError("地市编码为空");
            }
            
            return freightManager.getFreightByAreaCode(provinceCode, areaCode);
        }catch (Exception e){
            logger.error("用户【" + customer.getOpenid() + "】获取运费失败", e);
            return CallBackConstant.FAILED.callbackError("系统异常，请稍后重试");
        }
    }
    
    /**
	 * 点击购物车商品，查看商品详情</br>
	 * 分普通商品和预购商品 
	 */
	@RequestMapping(value = "/view")
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request) {  
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//1:获取页面参数与封装数据
		PageData pd = this.getPageData();
		String productId = pd.getString("productId");
		if (!StringUtil.isIntger(productId)) {
        	return CallBackConstant.PARAMETER_ERROR.callbackError("产品id不能为空");
        }
		
		//2:查询商品详情
		ProductVo product = productManager.selectProductByOption(pd);
		List<PreProductVo> pre = preProductManager.queryList(pd);
		
		if(null != product){
			//这里首先要分情况，查取商品的情况状态 
			//然后判断商品是否是线下商品（线下商品只能微信扫码购买）
			//判断是否下架
			if (product.getStatus() == 1) {  //商品上架
				if (product.getIs_online() == 2) {  //商品是线下商品
					return CallBackConstant.FAILED.callbackError("该商品是线下优惠商品，目前仅支持微信扫码购买");
				}
				
				if(null != pre && pre.size()>0){
					PreProductVo preProdcut = pre.get(0);
					resultMap.put("preId", preProdcut.getPreId());
					resultMap.put("productId", preProdcut.getProductId());
				} else {
					resultMap.put("productId", product.getProduct_id());
				}
				return CallBackConstant.SUCCESS.callback(resultMap);
			} else {  //商品已经下架
				return CallBackConstant.FAILED.callbackError("该商品已经下架");
			}
		} else {
			return CallBackConstant.FAILED.callbackError("商品信息不存在");
		}
    }
	
	/**
	 * app1.1每周新品/人气推荐列表
	 * @param page
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "/queryProductByExtension",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProductByExtension(HttpServletRequest request,HttpServletResponse response){
		logger.info("/app/product/queryProductByExtension--->begin");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		
		String type = pd.getString("extensionType");
		int extensionType = 0;
		if(!StringUtils.isEmpty(type)){
			extensionType = Integer.parseInt(type);
		}
		
		pd.put("orderFlag", 1);
		List<ProductVo> productVos = this.productManager.queryIndexProduct(1,extensionType,0,pd, 10, 1);
		if(productVos == null){
			productVos = new ArrayList<ProductVo>();
		}
		resultMap.put("list", productVos);
		logger.info("/app/index/product/queryProductByExtension--->end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	/**
	 * app1.1查询商品(商品分类查询)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryProductListByType")
	@ResponseBody
	public Map<String, Object> queryProductListByType(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入商品分类列表查看开始");
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		String strProductType = pd.getString("productType");
		if (!StringUtil.isIntger(strProductType)) {
        	return CallBackConstant.PARAMETER_ERROR.callbackError("商品类型为空");
        }
		List<ProductVo> listProduct = null;
		try {
			int productType = 0;
			if(!StringUtils.isEmpty(strProductType)){
				productType = Integer.parseInt(strProductType);
			}
			pd.put("productType", productType);//类型
			pd.put("productStatus", 1);//状态 正常
			pd.put("isOnline", 1);
			listProduct = this.productManager.queryProductListPage(pd);
			if(listProduct == null){
				listProduct = new ArrayList<ProductVo>();
			}else{
				for (ProductVo productVo : listProduct) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", productVo.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					PageData appd = new PageData();
					appd.put("productId", productVo.getProduct_id());
					appd.put("proStatus", 1);
					appd.put("specStatus", 1);
					if(specialVo != null){
						appd.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(appd);
					if(proSpec == null){
						continue;
					}
					
					productVo.setPrice(proSpec.getSpec_price());
					if (proSpec.getDiscount_price() != null && proSpec.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
						productVo.setDiscountPrice(proSpec.getDiscount_price()+"");
					}

					int fakeSalesCopy = productVo.getFake_sales_copy();
					if(proSpec.getTotal_sales_copy() != null){
						productVo.setSalesVolume(proSpec.getTotal_sales_copy()+fakeSalesCopy);
					}else{
						productVo.setSalesVolume(fakeSalesCopy);
					}
					
					if(specialVo == null){
						specialVo = new SpecialVo();
						specialVo.setSpecialId(0);
					}else{
						productVo.setActivityType(specialVo.getSpecialType());
						productVo.setIsActivity("NO");
						if(StringUtil.isNotNull(proSpec.getActivity_price()) && new BigDecimal(proSpec.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
							productVo.setActivityPrice(proSpec.getActivity_price());
							productVo.setIsActivity("YES");
						}
						
						productVo.setActivity_id(specialVo.getSpecialId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询产品异常...", e);
			return CallBackConstant.FAILED.callback();
		}
		
		resultMap.put("list", listProduct);
		logger.info("进入商品分类列表查看结束");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	
	@RequestMapping(value = "/queryProductTypeHead")
	@ResponseBody
	public Map<String, Object> queryProductTypeHead(HttpServletRequest request,HttpServletResponse response) {
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	
		Map<String,Object> type1 = new HashMap<String, Object>();
		type1.put("name", "全部");
		type1.put("urlPre", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/all-01.png?t=201801270209");
		type1.put("urlNow", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/all-02.png");
		type1.put("type", 0);
		Map<String,Object> type2 = new HashMap<String, Object>();
		type2.put("name", "星球推荐");
		type2.put("urlPre", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/fruits-01.png?t=201801270209");
		type2.put("urlNow", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/fruits-02.png");
		type2.put("type", 4);
		Map<String,Object> type3 = new HashMap<String, Object>();
		type3.put("name", "网红零食");
		type3.put("urlPre", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/snacks-01.png?t=201801270209");
		type3.put("urlNow", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/snacks-02.png");
		type3.put("type", 3);
		Map<String,Object> type4 = new HashMap<String, Object>();
		type4.put("name", "必备零食");
		type4.put("urlPre", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/recommend-01.png?t=201801270209");
		type4.put("urlNow", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/recommend-02.png");
		type4.put("type", 1);
		Map<String,Object> type5 = new HashMap<String, Object>();
		type5.put("name", "其他");
		type5.put("urlPre", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/kitchen-01.png?t=201801270209");
		type5.put("urlNow", "http://www.hlife.shop/huihuo/resources/weixin/img/goods/kitchen-02.png");
		type5.put("type", 2);
		
		result.add(type1);
		result.add(type2);
		result.add(type3);
		result.add(type4);
		result.add(type5);
			
		return CallBackConstant.SUCCESS.callback(result);
	}
	
	
	/**
	 * app1.1跳转到商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toShowGoodsdetails")
	@ResponseBody
	public Map<String, Object> toShowGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入app端查询商品详情页面");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("productId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品ID不能为空");
		}
		
		pd.put("status", 1); //上架的商品
		pd.put("isOnline", 1);  //只查询线上的商品
		ProductVo product = productManager.selectProductByOption(pd);
		if(null == product){
			return CallBackConstant.FAILED.callbackError("商品已下架");
		}
		
		String details = product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		//专题
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		PageData specialParams = new PageData();
		specialParams.put("status", 1);
		specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		specialParams.put("productId", product.getProduct_id());
		specialParams.put("activityFlag", 1);
		SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
		//设置每个商品规格的活动价
		List<ProductSpecVo> speclist = product.getListSpecVo();
		if(specialVo == null){
			specialVo = new SpecialVo();
			specialVo.setSpecialId(0);
		}else{
			//查询是否存在活动 ，如果是 就加入活动价
			PageData pdData=new PageData();
			pdData.put("productId", String.valueOf(product.getProduct_id()));
			pdData.put("activityFlag", 1);
			pdData.put("activityId", specialVo.getSpecialId());
			listActivtyProduct = activityProductManager.queryList(pdData);
			if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
				Date dt = DateUtil.stringToDate(specialVo.getEndTime());
				Date start = DateUtil.stringToDate(specialVo.getStartTime());
				
				
				for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
					if(specialVo!=null){//专题活动
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
		}
		//满减活动不需要登陆
		for (ProductSpecVo productSpecVo : speclist) {
			fullMoneyCut(productSpecVo);
		}
		product.setListSpecVo(speclist);
		
		//满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",product.getProduct_id());
		SalePromotionVo salePromotionVo = new SalePromotionVo();
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
		if(listPromotions!=null&&listPromotions.size()>0){
			salePromotionVo = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
		}
		
		
		PageData commpd = new PageData();
		commpd.put("parentFlag", 1);	//评论数（不包括回复数）
		commpd.put("status",2); //评论通过
		commpd.put("productId", product.getProduct_id());
		List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
		if(comments == null){
			comments = new ArrayList<CommentVo>();
		}
		map.put("comments", comments);
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		int collectionId = 0;
		String token = request.getParameter("token");
		if (StringUtil.isNotNull(token)) {   //token不为空
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer != null) {  //用户不为空
				productPd.put("customerId", customer.getCustomer_id());
				List<ProductSpecVo> speclists = product.getListSpecVo();
				if(speclists!=null&&speclists.size()>0){
					for (ProductSpecVo productSpecVo : speclists) {
						int restCopy = 0;
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
									productPd.put("startTime", productSpecVo.getLimit_start_time());
									productPd.put("endTime", productSpecVo.getLimit_end_time());
									
									productPd.put("checkStatus", "2,3,4,5");//已付款的订单
									productPd.put("customerId", String.valueOf(customer.getCustomer_id()));
									productPd.put("specId", productSpecVo.getSpec_id());//规格
									int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
									if(orderQuantity > 0){
										restCopy = productSpecVo.getLimit_max_copy() - orderQuantity;
										productSpecVo.setRest_copy(restCopy);
										productSpecVo.setHasBuy_copy(orderQuantity);
									}else{
										productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
										productSpecVo.setHasBuy_copy(0);
									}
									int carNums = shoppingCartManager.queryCartCountByParams(productPd);
									int carCanAdd=0;
									if(carNums > 0){
										carCanAdd = productSpecVo.getLimit_max_copy() - carNums;
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
						}
					
					}
				}
				
				PageData cpd = new PageData();
				cpd.put("objId", product.getProduct_id());
				cpd.put("customerId", customer.getCustomer_id());
				CollectionVo coll = this.collectionManager.queryOne(cpd);
				if(coll != null && coll.getCollectionId() > 0){
					collectionId = coll.getCollectionId();
				}
				//设置优惠券数量
				product = getCouponCount(product, customer);
			}
			
		}
		
		int fakeSalesCopy = product.getFake_sales_copy();
		product.setSalesVolume(product.getSalesVolume()+fakeSalesCopy);
		
		PageData postagepd= new PageData();
		postagepd.put("productId", product.getProduct_id());
		PostageVo postageVo = postageManager.queryOne(postagepd);
		if (null != postageVo) {
			map.put("postageId", postageVo.getPostageId());
			map.put("postageMsg",postageVo.getPostageMsg());
			map.put("meetConditions", "满"+postageVo.getMeetConditions()+"免运费");
		}else{
			map.put("postageId", 0);
		}
		
		
		map.put("collectionId", collectionId);
		map.put("productInfo", product);
		map.put("specialVo", specialVo);
		map.put("salePromotionVo", salePromotionVo);
		return CallBackConstant.SUCCESS.callback(map);
	} 
	
	/**
	 * app1.1为你推荐
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryLikeProducts")
	@ResponseBody
	public Map<String, Object> queryLikeProducts(HttpServletRequest request,HttpServletResponse response) {
		List<ProductVo> listProduct = new ArrayList<ProductVo>();
		try {
			PageData productPd = new PageData();
			productPd.put("eatRecommend", 1);
			productPd.put("rowStart", 0);
			productPd.put("pageSize", 2);
			productPd.put("productStatus", 1);
			productPd.put("isOnline", 1);
			listProduct = this.productManager.queryProductListPage(productPd);
			if(listProduct == null){
				listProduct = new ArrayList<ProductVo>();
			}else{
				for (ProductVo productVo : listProduct) {
					//专题
					PageData specialParams = new PageData();
					specialParams.put("status", 1);
					specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
					specialParams.put("productId", productVo.getProduct_id());
					SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
					
					//普通价
					PageData appd = new PageData();
					appd.put("productId", productVo.getProduct_id());
					appd.put("proStatus", 1);
					appd.put("specStatus", 1);
					if(specialVo != null){
						appd.put("activityId", specialVo.getSpecialId());
					}
					ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(appd);
					if(proSpec == null){
						continue;
					}
					
					productVo.setPrice(proSpec.getSpec_price());
					if (proSpec.getDiscount_price() != null && proSpec.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
						productVo.setDiscountPrice(proSpec.getDiscount_price()+"");
					}
					
					int fakeSalesCopy = productVo.getFake_sales_copy();
					if(proSpec.getTotal_sales_copy() != null){
						productVo.setSalesVolume(proSpec.getTotal_sales_copy()+fakeSalesCopy);
					}else{
						productVo.setSalesVolume(fakeSalesCopy);
					}
					
					if(specialVo == null){
						specialVo = new SpecialVo();
						specialVo.setSpecialId(0);
					}else{

						productVo.setActivityType(specialVo.getSpecialType());
						productVo.setIsActivity("NO");
						if(StringUtil.isNotNull(proSpec.getActivity_price()) && new BigDecimal(proSpec.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
							productVo.setActivityPrice(proSpec.getActivity_price());
							productVo.setIsActivity("YES");
						}
						productVo.setActivity_id(specialVo.getSpecialId());
					}
				}
			}
			return CallBackConstant.SUCCESS.callback(listProduct);
		} catch (Exception e) {
			logger.error("为你推荐异常：/app/product/queryLikeProducts", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * app1.1跳转到限量抢购商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toLimitGoodsdetails")
	@ResponseBody
	public Map<String, Object> toLimitGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入app端查询限量商品详情页面");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("productId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品ID不能为空");
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("限量活动ID不能为空");
		}
		
		PageData spd=new PageData();
		spd.put("specialId", pd.getString("specialId"));
		SpecialVo specialVo = specialMananger.queryOne(spd);
		if(specialVo == null || specialVo.getStatus().intValue() != 1){
			return CallBackConstant.FAILED.callbackError("活动已下架");
		}
		
		pd.put("status", 1); //上架的商品
		pd.put("isOnline", 1);  //只查询线上的商品
		ProductVo product = productManager.selectProductByOption(pd);
		if(null == product){
			return CallBackConstant.FAILED.callbackError("商品已下架");
		}
		
		String details = product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		int hasStock = 0;
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
		pdData.put("activityType", 3);//限量
		pdData.put("activityId", pd.getInteger("specialId"));
		List<ActivityProductVo> listActivtyProduct = activityProductManager.queryList(pdData);
		List<ProductSpecVo> isSpecs = new ArrayList<ProductSpecVo>();
		//设置每个商品规格的活动价
		List<ProductSpecVo> speclist = product.getListSpecVo();
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				if(specialVo!=null){//专题活动
//					Date dt = DateUtil.stringToDate(specialVo.getEndTime());
//					Date start = DateUtil.stringToDate(specialVo.getStartTime());
//					if(dt.after(new Date())&&start.before(new Date())){
						if(speclist!=null&&speclist.size()>0){
							for (ProductSpecVo productSpecVo : speclist) {
								if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
									hasStock = hasStock + productSpecVo.getStock_copy();
									isSpecs.add(productSpecVo);
								}
							}
						}
//					}
				}
				
			}
		}
		//满减活动不需要登陆
		for (ProductSpecVo productSpecVo : speclist) {
			fullMoneyCut(productSpecVo);
		}
		product.setListSpecVo(speclist);
		
		//满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",product.getProduct_id());
		SalePromotionVo salePromotionVo = new SalePromotionVo();
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
		if(listPromotions!=null&&listPromotions.size()>0){
			salePromotionVo = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
		}
		
		PageData commpd = new PageData();
		commpd.put("parentFlag", 1);	//评论数（不包括回复数）
		commpd.put("status",2); //评论通过
		commpd.put("productId", product.getProduct_id());
		List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
		if(comments == null){
			comments = new ArrayList<CommentVo>();
		}
		map.put("comments", comments);
		
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		int collectionId = 0;
		String token = request.getParameter("token");
		if (StringUtil.isNotNull(token)) {   //token不为空
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer != null) {  //用户不为空
				productPd.put("customerId", customer.getCustomer_id());
				if(isSpecs!=null&&isSpecs.size()>0){
					for (ProductSpecVo productSpecVo : isSpecs) {
						int restCopy = 0;
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
									productPd.put("startTime", productSpecVo.getLimit_start_time());
									productPd.put("endTime", productSpecVo.getLimit_end_time());
									
									//按产品id 和规格查出所有买过此商品的订单详情
									productPd.put("checkStatus", "2,3,4,5");//已付款的订单
									productPd.put("specId", productSpecVo.getSpec_id());//规格
									int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
									if(orderQuantity > 0){
										restCopy = productSpecVo.getLimit_max_copy() - orderQuantity;
										productSpecVo.setRest_copy(restCopy);
										productSpecVo.setHasBuy_copy(orderQuantity);
									}else{
										productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
										productSpecVo.setHasBuy_copy(0);
									}
									int carNums = shoppingCartManager.queryCartCountByParams(productPd);
									int carCanAdd=0;
									if(carNums > 0){
										carCanAdd = productSpecVo.getLimit_max_copy() - carNums;
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
						}
						
						PageData couponParam = new PageData();
						couponParam.put("customerId", customer.getCustomer_id());
						couponParam.put("productId", product.getProduct_id());
						couponParam.put("specId", productSpecVo.getSpec_id());
						couponParam.put("showSpec", "showSpec");
						List<SingleCouponVo> singleCoupons = this.singleCouponManager.queryEffectiveCouponListNew(couponParam);
						if(singleCoupons!=null&&singleCoupons.size()>0){
							productSpecVo.setSingleCouponCount(singleCoupons.size());
						}
					}
				}
				
				PageData cpd = new PageData();
				cpd.put("objId", product.getProduct_id());
				cpd.put("customerId", customer.getCustomer_id());
				CollectionVo coll = this.collectionManager.queryOne(cpd);
				if(coll != null && coll.getCollectionId() > 0){
					collectionId = coll.getCollectionId();
				}
				//红包数量
				product = getCouponCount(product, customer);
			}
		}
		
		int unrealSales = product.getSalesVolume()+product.getFake_sales_copy();
		PageData postagepd= new PageData();
		postagepd.put("productId", product.getProduct_id());
		PostageVo postageVo = postageManager.queryOne(postagepd);
		if (null != postageVo) {
			map.put("postageId", postageVo.getPostageId());
			map.put("postageMsg",postageVo.getPostageMsg());
			map.put("meetConditions", "满"+postageVo.getMeetConditions()+"免运费");
		}else{
			map.put("postageId", 0);
		}
		product.setListSpecVo(isSpecs);
		map.put("collectionId", collectionId);
		map.put("productInfo", product);
		map.put("specialVo", specialVo);
		map.put("salePromotionVo", salePromotionVo);
		map.put("unrealSales", unrealSales);
		map.put("hasStock", hasStock);
		return CallBackConstant.SUCCESS.callback(map);
	} 
	
	
	private ProductVo getCouponCount(ProductVo product,CustomerVo customer){
		//优惠券
				PageData coupon = new PageData();
				coupon.put("customerId", customer.getCustomer_id());
				coupon.put("productId", product.getProduct_id());
				List<ProductSpecVo> listSpecVo = product.getListSpecVo();
				if(listSpecVo!=null&&listSpecVo.size()>0){
					for (ProductSpecVo productSpecVo : listSpecVo) {
						coupon.put("price", productSpecVo.getSpec_price());
						coupon.put("canusecoupon","0");
						List<CouponInfoVo> couponManagers = couponInfoManager.findListUserCoupon(coupon);
						if(couponManagers!=null&&couponManagers.size()!=0){
							productSpecVo.setCouponCount(couponManagers.size());
						}
						coupon.put("specId", productSpecVo.getSpec_id());
						coupon.put("startTime", new Date());
						coupon.put("endTime", new Date());
						coupon.put("status", 0);
						coupon.put("fullMoney", productSpecVo.getSpec_price());
						coupon.put("canusecoupon", 0);
						UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(coupon);
						if(uscVo!=null){
							productSpecVo.setSingleCouponCount(1);
						}
						
						//fullMoneyCut(productSpecVo);
					}
					
				}
				
				return product;
	}
	
	
	private ProductSpecVo fullMoneyCut(ProductSpecVo productSpecVo){
		logger.info("fullMoneyCut()-----加入满减活动  ------------------------开始------------");
		//加入满减活动
		PageData mcps = new PageData();
		mcps.clear();
		mcps.put("startTime", new Date());
		mcps.put("endTime", new Date());
		mcps.put("status",1);
		mcps.put("productId",productSpecVo.getProduct_id());
		mcps.put("specId",productSpecVo.getSpec_id());
		List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(mcps);
			if(listPromotions!=null&&listPromotions.size()>0){
				SalePromotionVo salePromotion = salePromotionManager.selectByPrimaryKey(listPromotions.get(0).getPromotionId());
				productSpecVo.setPromoitionName(salePromotion.getPromotionName());
				productSpecVo.setFullMoney(salePromotion.getFullMoney().toString());
				productSpecVo.setCutMoney(salePromotion.getCutMoney().toString());
			}
			return productSpecVo;
		}
	/**
	 * app1.1跳转到团购商品详情页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toGroupGoodsdetails")
	@ResponseBody
	public Map<String, Object> toGroupGoodsdetails(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入app端查询团购商品详情页面");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		if(StringUtil.isNull(pd.getString("productId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品ID不能为空");
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("团购活动ID不能为空");
		}
		
		PageData spd=new PageData();
		spd.put("specialId", pd.getString("specialId"));
		SpecialVo specialVo = specialMananger.queryOne(spd);
		if(null == specialVo){
			return CallBackConstant.FAILED.callbackError("活动已下架");
		}
		SysGroupVo sysGroupVo = sysGroupManager.queryOne(spd);
		if(sysGroupVo == null){
			return CallBackConstant.FAILED.callbackError("活动已下架");
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
			specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
		} catch (Exception e) {
			logger.error("weixin/product/toGroupGoodsdetails parseTime error----", e);
		}
		spd.put("productId", sysGroupVo.getProductId());
		spd.put("specialId", specialVo.getSpecialId());
//		spd.put("isProgress", 1);
		spd.put("statusLine", 1);
		List<UserGroupVo> userGroups = this.userGroupManager.queryList(spd);
		if(userGroups == null){
			userGroups = new ArrayList<UserGroupVo>();
		}
		
		pd.put("status", 1); //上架的商品
		pd.put("isOnline", 1);  //只查询线上的商品
		ProductVo product = productManager.selectProductByOption(pd);
		if(null == product){
			return CallBackConstant.FAILED.callbackError("商品已下架");
		}
		
		String details = product.getDetails();
		if(null!=details && !"".equals(details)){
			product.setParamAndValue(details.split("@\\#@\\$"));
		}
		
		if(null!=product.getProduct_logo() && !"".equals(product.getProduct_logo())){
			String[] pictureArrays=product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}
		
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		//查询是否存在活动 ，如果是 就加入活动价
		PageData pdData=new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		//pdData.put("specId", product.getListSpecVo().get(0).getSpec_id());
		pdData.put("activityType", 4);//拼团
		pdData.put("activityId", pd.getInteger("specialId"));
		listActivtyProduct = activityProductManager.queryList(pdData);
		List<ProductSpecVo> isSpecs = new ArrayList<ProductSpecVo>();
		if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
			//设置每个商品规格的活动价
			List<ProductSpecVo> speclist = product.getListSpecVo();
			for (ActivityProductVo spec : listActivtyProduct) {//修改规格的专题价
				if(specialVo!=null){//专题活动
//					Date dt = DateUtil.stringToDate(specialVo.getEndTime());
//					Date start = DateUtil.stringToDate(specialVo.getStartTime());
//					if(dt.after(new Date())&&start.before(new Date())){
						if(speclist!=null&&speclist.size()>0){
							for (ProductSpecVo productSpecVo : speclist) {
								if(productSpecVo.getSpec_id().equals(spec.getSpec_id())){//设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
									isSpecs.add(productSpecVo);
								}
							}
						}
//					}
				}
				
			}
		}
		
		PageData commpd = new PageData();
		commpd.put("parentFlag", 1);	//评论数（不包括回复数）
		commpd.put("status",2); //评论通过
		commpd.put("productId", product.getProduct_id());
		List<CommentVo> comments = commentManager.queryCommentInfoListPage(commpd);
		if(comments == null){
			comments = new ArrayList<CommentVo>();
		}
		map.put("comments", comments);
		
		
		//根据条件查出此用户是否买过此产品
		PageData productPd = new PageData();
		productPd.put("productId", product.getProduct_id());
		
		int collectionId = 0;
		String token = request.getParameter("token");
		if (StringUtil.isNotNull(token)) {   //token不为空
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer != null) {  //用户不为空
				productPd.put("customerId", customer.getCustomer_id());
				
				if(isSpecs!=null&&isSpecs.size()>0){
					for (ProductSpecVo productSpecVo : isSpecs) {
						int restCopy = 0;
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								if(productSpecVo.getLimit_max_copy()!=null&&productSpecVo.getLimit_max_copy()>0){//说明是限购的
									productPd.put("startTime", productSpecVo.getLimit_start_time());
									productPd.put("endTime", productSpecVo.getLimit_end_time());
									
									//按产品id 和规格查出所有买过此商品的订单详情
									productPd.put("checkStatus", "2,3,4,5");//已付款的订单
									productPd.put("specId", productSpecVo.getSpec_id());//规格
									int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
									
									if(orderQuantity > 0){
										restCopy = productSpecVo.getLimit_max_copy() - orderQuantity;
										productSpecVo.setRest_copy(restCopy);
										productSpecVo.setHasBuy_copy(orderQuantity);
									}else{
										productSpecVo.setRest_copy(productSpecVo.getLimit_max_copy());
										productSpecVo.setHasBuy_copy(0);
									}
									int carNums = shoppingCartManager.queryCartCountByParams(productPd);
									int carCanAdd=0;
									if(carNums > 0){
										carCanAdd = productSpecVo.getLimit_max_copy() - carNums;
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
						}
					}
				}
				
				PageData cpd = new PageData();
				cpd.put("objId", product.getProduct_id());
				cpd.put("customerId", customer.getCustomer_id());
				CollectionVo coll = this.collectionManager.queryOne(cpd);
				if(coll != null && coll.getCollectionId() > 0){
					collectionId = coll.getCollectionId();
				}
			}
		}
		
		long currTime = System.currentTimeMillis();
		int specialStatus = 0;
		if(specialVo.getLongEnd() <= currTime || specialVo.getStatus().intValue() != 1){
			specialStatus = 1;
		}
		
		product.setListSpecVo(isSpecs);
		
		int unrealSales = product.getSalesVolume()+product.getFake_sales_copy();
		PageData postagepd= new PageData();
		postagepd.put("productId", product.getProduct_id());
		PostageVo postageVo = postageManager.queryOne(postagepd);
		if (null != postageVo) {
			map.put("postageId", postageVo.getPostageId());
			map.put("postageMsg",postageVo.getPostageMsg());
			map.put("meetConditions", "满"+postageVo.getMeetConditions()+"免运费");
		}else{
			map.put("postageId", 0);
		}
		map.put("specialStatus", specialStatus);
		map.put("unrealSales", unrealSales);
		map.put("collectionId", collectionId);
		map.put("productInfo", product);
		map.put("specialVo", specialVo);
		map.put("sysGroup", sysGroupVo);
		map.put("userGroups", userGroups);
		return CallBackConstant.SUCCESS.callback(map);
	} 
	
	/**
	 * app1.2购买商品时校验
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/checkGoods")
	@ResponseBody
	public Map<String, Object> checkGoods(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		if(pd.getInteger("productId") == null){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		if(pd.getInteger("specId") == null){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		pd.put("specStatus", 1);
		pd.put("proStatus", 1);
		ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pd);
		if(null == specVo){
			return CallBackConstant.FAILED.callbackError("商品已下架");
		}
		
		if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
			return CallBackConstant.FAILED.callbackError("商品库存不足");
		}
		
		String token = pd.getString("token");
		if(StringUtil.isNull("token")){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer == null) {  //用户不为空
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		if(pd.getInteger("specialId") != null){
			PageData spd = new PageData();
			spd.put("status", 1);
			spd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			spd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			spd.put("specialId", pd.getInteger("specialId"));
			SpecialVo spVo = this.specialMananger.queryOne(spd);
			if(spVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("您来晚了，商品已被抢光了~");
			}
		}
		
		
		pd.put("customerId", customer.getCustomer_id());
		if(StringUtils.isNotBlank(specVo.getLimit_start_time()) && StringUtils.isNotBlank(specVo.getLimit_end_time())){
			Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
			Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
			if(dt.after(new Date())&&start.before(new Date())){//限购时间内
				if(specVo.getLimit_max_copy()!=null&&specVo.getLimit_max_copy()>0){//说明是限购的
					pd.put("startTime", specVo.getLimit_start_time());
					pd.put("endTime", specVo.getLimit_end_time());
					pd.put("checkStatus", "2,3,4,5");//已付款的订单
					pd.put("specId", specVo.getSpec_id());//规格
					//按产品id 和规格查出所有买过此商品的订单详情
					int orderQuantity = orderProductManager.queryOrderProductQuantity(pd);
					if(orderQuantity > 0){
						int restCopy = specVo.getLimit_max_copy() - orderQuantity;
						if(restCopy <= 0){
							return CallBackConstant.FAILED.callbackError("商品库存不足");
						}
					}
				}
			}
		}
		
		return CallBackConstant.SUCCESS.callback();
	}
	
	/**
	 * 检查商品是否参与活动
	 * @param request
	 * @param response
	 * @param page
	 */
	@RequestMapping(value = "/checkActivityByProduct")
	@ResponseBody
	public Map<String,Object> checkActivityByProduct(HttpServletRequest request,HttpServletResponse response){
		PageData pd=this.getPageData();
		try {
			if(StringUtil.isNull(pd.getString("productId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("参数不能为空");
			}
			
			int productId = pd.getInteger("productId");
			
			//专题
			PageData specialParams = new PageData();
			specialParams.put("status", 1);
			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", productId);
			SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
			
			PageData activityMap = new PageData();
			activityMap.put("productId", productId);
			activityMap.put("proStatus", 1);
			activityMap.put("specStatus", 1);
			if(specialVo != null){
				activityMap.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
			if(proSpec == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("商品已下架");
			}
			
			int specialId = 0;
			int specialType = 0;
			if(specialVo != null && specialVo.getSpecialId().intValue() > 0){
				specialId = specialVo.getSpecialId();
				specialType = specialVo.getSpecialType();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("specialId", specialId);
			map.put("specialType", specialType);
			map.put("productId", productId);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/product/checkActivityByProduct -----error", e);
			return CallBackConstant.FAILED.callback();
		} 
	}
	/**
	 * APP活动促销
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appGetPromotion")
	@ResponseBody
	public Map<String,Object> getPromotion(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotNull(pd.getString("productId")) && StringUtil.isNotNull(pd.getString("specId"))){
			pd.put("productId", pd.getInteger("productId"));
			pd.put("specId", pd.getInteger("specId"));
			//满多少钱减多少钱
			SalePromotionVo salePromotion = this.salePromotionManager.queryOne(pd);
			if (salePromotion != null) {
				map.put("salePromotionStr", "满" + BigDecimalUtil.subZeroAndDot(salePromotion.getFullMoney()) +"元减" + BigDecimalUtil.subZeroAndDot(salePromotion.getCutMoney()) + "元");
			}
			//积分换购
			pd.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
			ProductRedeemSpecVo productRedeemSpec = productRedeemSpecManager.queryOne(pd);
			if (productRedeemSpec != null) {
				if(productRedeemSpec.getRedeemType() == 0) {
					map.put("productRedeemSpecStr", "使用" + BigDecimalUtil.subZeroAndDot(productRedeemSpec.getNeedIntegral()) + "个金币可抵" + BigDecimalUtil.subZeroAndDot(productRedeemSpec.getDeductibleAmount()) + "元 ");
				}
			}
		}
		return CallBackConstant.SUCCESS.callback(map);		
	}
}
