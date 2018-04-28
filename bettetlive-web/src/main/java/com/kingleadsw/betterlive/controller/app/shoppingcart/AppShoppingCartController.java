package com.kingleadsw.betterlive.controller.app.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;
import com.kingleadsw.betterlive.vo.SpecialVo;


/**
 * App 端购物车
 * 2017-04-24 by chen
 *
 */
@Controller
@RequestMapping(value = "/app/shoppingcart")
public class AppShoppingCartController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(AppShoppingCartController.class);

	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private OrderProductManager orderProductManager; 
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductLabelManager productLabelManager;
	@Autowired
	private PostageManager postageManager; 
	@Autowired
	private SalePromotionManager salePromotionManager; 
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	
	/**
	 * 根据客户ID查询购物车中的所有商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toshoppingcar")
	@ResponseBody
	public Map<String, Object> toShoppingcar(HttpServletRequest request, HttpServletResponse response) {
		String msg = "toshoppingcar";
		logger.info("/app/shoppingcar/"+msg+" begin");
		
		JSONObject json = new JSONObject(); 
		PageData pds = this.getPageData();
		String token = pds.getString("token");    //用户标识
		if (token == null || "".equals(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		if (customerVo == null) {
			logger.info("用户信息为空-------！");
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		PageData pd = new PageData();
		pd.put("customerId", customerVo.getCustomer_id());
		List<ShoppingCartVo> listShoppingCar=shoppingCartManager.queryListShoppingCart(pd);
		if (listShoppingCar != null && listShoppingCar.size() > 0) {
			for (ShoppingCartVo shoppingCartVo : listShoppingCar) {
				//查询是否存在活动 ，如果是 就加入活动价\
				PageData specialParams = new PageData();
				specialParams.put("status", 1);
				specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("productId", shoppingCartVo.getProduct_id());
				SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
				
				PageData pdData=new PageData();
				pdData.put("productId", String.valueOf(shoppingCartVo.getProduct_id()));
				pdData.put("specId", shoppingCartVo.getSpec_id());
				if(specialVo != null){
					pdData.put("activityId", specialVo.getSpecialId());
				}
				ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pdData);
				if(psvo.getDiscount_price() != null && psvo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
					shoppingCartVo.setDiscount_price(psvo.getDiscount_price()+"");
				}
				
				shoppingCartVo.setSpec_price(psvo.getSpec_price());
				shoppingCartVo.setStatus(psvo.getStatus());

				if(specialVo != null){
					String specialName = "";
					if(StringUtil.isNotEmpty(specialVo.getSpecialName())){
						specialName = specialVo.getSpecialName();
					}else{
						specialName = specialVo.getSpecialTitle();
					}
					
					shoppingCartVo.setActivity_type(Byte.toString(specialVo.getSpecialType()));
					shoppingCartVo.setActivity_id(specialVo.getSpecialId()+"");
					shoppingCartVo.setActivity_price(psvo.getActivity_price());
					shoppingCartVo.setSpecialString(specialName);
				}
				
				PageData plpd = new PageData();
				plpd.put("productId", psvo.getProduct_id());
				plpd.put("nowTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				plpd.put("status", 0);
				ProductLabelVo productLabelVo = productLabelManager.queryOne(plpd);
				if(productLabelVo != null && StringUtil.isNotEmpty(productLabelVo.getLabelTitle())){
					shoppingCartVo.setLabel_name(productLabelVo.getLabelTitle()); //商品标签
				}
				
				
				//限购时间
				if(psvo.getLimit_max_copy() != -1
						&& StringUtils.isNotBlank(psvo.getLimit_start_time())  
						&& StringUtils.isNotBlank(psvo.getLimit_end_time())){
					
					Date dt = DateUtil.stringToDate(psvo.getLimit_end_time());
					Date start = DateUtil.stringToDate(psvo.getLimit_start_time());
					if(dt.after(new Date())&&start.before(new Date())){//限购时间内
						pdData.put("startTime", psvo.getLimit_start_time());
						pdData.put("endTime", psvo.getLimit_end_time());
						
						pdData.put("checkStatus", "2,3,4,5");//已付款的订单
						pdData.put("customerId", customerVo.getCustomer_id());
						int orderQuantity = orderProductManager.queryOrderProductQuantity(pdData);
						
						if(orderQuantity > 0){
							int rest = psvo.getLimit_max_copy() - orderQuantity;
							shoppingCartVo.setRest_copy(rest);
						}else{
							shoppingCartVo.setRest_copy(psvo.getLimit_max_copy());
						}
						shoppingCartVo.setLimit_start_time(psvo.getLimit_start_time());
						shoppingCartVo.setLimit_end_time(psvo.getLimit_end_time());
						shoppingCartVo.setLimit_max_copy(psvo.getLimit_max_copy());
						shoppingCartVo.setHasBuy_copy(orderQuantity);
					}else{
						shoppingCartVo.setLimit_max_copy(-1);
					}
				} else {
					shoppingCartVo.setLimit_max_copy(-1);
					shoppingCartVo.setHasBuy_copy(0);
					shoppingCartVo.setRest_copy(psvo.getStock_copy());
				}
				shoppingCartVo.setStock_copy(psvo.getStock_copy());
				//查询商品满减活动信息
				PageData salePd = new PageData();
				salePd.put("productId", shoppingCartVo.getProduct_id());
				salePd.put("specId", shoppingCartVo.getSpec_id());
				SalePromotionVo salePromotionVo = salePromotionManager.queryOne(salePd);
				if (null != salePromotionVo) {
					shoppingCartVo.setSalePromotionString("满￥"+salePromotionVo.getFullMoney()+"，总价可减￥"+salePromotionVo.getCutMoney());
				}
				
				String strPayMoney = shoppingCartVo.getSpec_price();
				if(StringUtil.isNotEmpty(shoppingCartVo.getActivity_price()) 
						&& new BigDecimal(shoppingCartVo.getActivity_price()).compareTo(BigDecimal.ZERO) != -1){
					strPayMoney = shoppingCartVo.getActivity_price();
				}else if(StringUtil.isNotEmpty(shoppingCartVo.getDiscount_price()) 
						&& new BigDecimal(shoppingCartVo.getDiscount_price()).compareTo(BigDecimal.ZERO) != -1){
					strPayMoney = shoppingCartVo.getDiscount_price();
				}
				
				BigDecimal payMoney = new BigDecimal(strPayMoney).multiply(new BigDecimal(shoppingCartVo.getAmount()));
				
				//查询商品优惠券、红包数量
				salePd.put("customerId", customerVo.getCustomer_id());
				salePd.put("payMoney", payMoney);
				int couponNum = shoppingCartManager.findCountByCoupon(salePd);
				if (couponNum > 0) {
					shoppingCartVo.setCouponNumString("该商品有"+couponNum+"张券可用");
				}
				
				//查询金币优惠购信息
				salePd.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
				ProductRedeemSpecVo productRedeemSpecVo = productRedeemSpecManager.queryOne(salePd);
				if (null != productRedeemSpecVo) {
					shoppingCartVo.setIntegralString(BigDecimalUtil.subZeroAndDot(productRedeemSpecVo.getNeedIntegral())
												+ "个金币可抵￥"
												+ productRedeemSpecVo.getDeductibleAmount());
				}
			}
		}
		List<PostageVo> postageVoList = postageManager.queryListPage(new PageData());
		if (null != postageVoList) {
			for (PostageVo postageVo : postageVoList) {
				json.put("postage",postageVo.getPostage());
				json.put("productIds",postageVo.getProductIds());
				json.put("meetConditions",postageVo.getMeetConditions());
			}
		}else{
			json.put("postage",0);
			json.put("productIds","");
		}
		json.put("listShoppingCart", listShoppingCar);
		json.put("mobile", customerVo.getMobile());
		
		return CallBackConstant.SUCCESS.callback(json);
	
	}
	
	
	
	/**
	 *加入购物车
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addShoppingCar",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addShoppingCar(HttpServletRequest request, HttpServletResponse response){
		logger.info("APP端进入添加购物车.... 开始");
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		
		String productId=pd.getString("productId");
		String productSpecId=pd.getString("productSpecId");
		String amount=pd.getString("amount");
		String extension_type=pd.getString("extension_type");           //主要区分 预购与普通商品
		String token=pd.getString("token");  							//用户标识
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		
		CustomerVo customerVo =  customerManager.queryCustomerByToken(token);
		
		if(customerVo==null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		if(StringUtil.isEmpty(productId)){
			return CallBackConstant.FAILED.callBackByMsg("商品不存在，请重新选择商品~");
			
		}
		
		//先查询产品是否上架
		pd.put("status","1");
		ProductVo productVo = productManager.selectProductByOption(pd);
		
		
		if(null==productVo){
			return CallBackConstant.FAILED.callbackError("该商品已下架");
		}
		
		if(StringUtil.isEmpty(productSpecId)){
			return CallBackConstant.FAILED.callbackError("请选择商品规格~");
		}
		
		if(StringUtil.isEmpty(amount)){
			return CallBackConstant.FAILED.callbackError("请选择购买数量");
		}
		
		ShoppingCartVo shoppingCartVo=new ShoppingCartVo();
		
		
		try {
			shoppingCartVo.setCustomer_id(customerVo.getCustomer_id());
			shoppingCartVo.setProduct_id(Integer.parseInt(productId)  );
			shoppingCartVo.setSpec_id(Integer.parseInt(productSpecId));
			shoppingCartVo.setAmount(Integer.parseInt(amount));
			
			if(null!=extension_type && !"".equals(extension_type)){
				shoppingCartVo.setExtension_type(extension_type);
			}
			
			PageData pspd = new PageData();
			pspd.put("specId", productSpecId);
			pspd.put("specStatus", 1);
			ProductSpecVo spe = productSpecManager.queryProductSpecByOption(pspd);
			if(spe == null){
				return CallBackConstant.FAILED.callbackError("该规格商品已下架~");
			}
			//进行查询购物车数量，用于页面的购物车标记刷新使用
			int result=shoppingCartManager.addShoppingCart(shoppingCartVo);
		    if(result>0){
		    	
				int cartCnt = shoppingCartManager.queryShoppingCartCnt(customerVo.getCustomer_id());
				if (cartCnt ==  0) {
					map.put("cartCnt", "");
				} else {
					map.put("cartCnt", cartCnt);
				}
				
				logger.info("APP端进入添加购物车.... 结束");
				
				return CallBackConstant.SUCCESS.callBackByMsg("已成功添加到购物车");
			}else{
				
				
				logger.info("APP端进入添加购物车.... 结束");
				
				return CallBackConstant.FAILED.callbackError("添加到购物车失败");
			}
		} catch (NumberFormatException e) {
			logger.error("APP端进入添加购物车.... 出现异常", e);
			
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	
	/**
	 * 删除购物车中的商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteShoppingCarById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteShoppingCarById(HttpServletRequest request, HttpServletResponse response){
		String msg = "deleteShoppingCarById";
		logger.info("/app/shoppingcart/"+msg+" begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		String carIds=pd.getString("cartId");
		String flag=pd.getString("flag");
		String token=pd.getString("token");    //用户标识
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token); 
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		if(StringUtil.isEmpty(flag)){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		if(!"true".equals(flag)){
			if(StringUtil.isEmpty(carIds) ){
				return CallBackConstant.PARAMETER_ERROR.callbackError("请先选择要删除的记录");
			}
		}
		
		
		pd.put("customerId",String.valueOf(customer.getCustomer_id()));
		
		int result=0;
		
		if("true".equals(flag)){
			result=shoppingCartManager.deleteShoppingCartByCid(pd);
		}else{
			result=shoppingCartManager.deleteShoppingCartByCidAndCid(pd);
		}
		
		
		if(result>0){
			int cartCnt = shoppingCartManager.queryShoppingCartCnt(customer.getCustomer_id());
			map.put("cartCnt", cartCnt);
			
			logger.info("/app/shoppingcart/"+msg+" end");
			
			return CallBackConstant.SUCCESS.callback(map);
		}else{
			logger.info("/app/shoppingcart/"+msg+" end");
			return CallBackConstant.FAILED.callback();
		}
			
		
	}
	
	
	/**
	 * 修改购物车中的商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateAmount",method={RequestMethod.POST})
	@ResponseBody
	public Map<String,Object>  updateAmount(HttpServletRequest request){
		logger.info("/app/shoppingcart/updateAmount begin");
		
		PageData pd=this.getPageData();
		String cartId=pd.getString("cartId");
		String editType=pd.getString("editType");
		
		String coalition=pd.getString("coalition");        //是否合并  2 就是合并
		String coalition_pid=pd.getString("coalition_pid");  //需要删除的 购物车ID
		
		if(!"1".equals(editType) && !"2".equals(editType)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("非法参数");
		}
		
		String productSpecId=pd.getString("productSpecId");
		String amount=pd.getString("amount");
		String token=pd.getString("token");    //用户标识
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token); 
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		pd.put("customerId", customer.getCustomer_id());
		if(StringUtil.isEmpty(cartId)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("请先选择要修改的记录");
		}
		
		if(StringUtil.isEmpty(amount)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("请先选择要修改的数量");
		}
		
		if("1".equals(editType) ){
			if(StringUtil.isEmpty(productSpecId)){
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品规格为空");
			}
		}
		
		if(StringUtils.isNotBlank(amount)){//限购
			PageData pds = new PageData();
			pds.put("cartId",cartId);
			pds.put("customer_id", customer.getCustomer_id());
			ShoppingCartVo spcv = shoppingCartManager.queryOne(pds);
			if(spcv!=null){
				pds.put("specId", productSpecId);
				ProductSpecVo productSpecVo = productSpecManager.queryProductSpecByOption(pds);
				
				if(productSpecVo!=null){
					if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
						int limitMaxCopy = productSpecVo.getLimit_max_copy();
						if(limitMaxCopy>-1){//限购
							Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
							Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
							if(dt.after(new Date())&&start.before(new Date())){//限购时间内
								PageData productPd = new PageData();
								productPd.put("startTime", productSpecVo.getLimit_start_time());
								productPd.put("endTime", productSpecVo.getLimit_end_time());
								
								//按产品id 和规格查出所有买过此商品的订单详情
								productPd.put("checkStatus", "2,3,4,5");//已付款的订单
								productPd.put("specId", productSpecVo.getSpec_id());//规格
								productPd.put("productId", productSpecVo.getProduct_id());//商品ID
								int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
								if(orderQuantity > 0){
									int restCopy = limitMaxCopy - orderQuantity;
									if (restCopy > 0 && Integer.parseInt(amount) > restCopy) {
										return CallBackConstant.FAILED.callbackError("您已购买" + orderQuantity + "件，只可加" + restCopy + "件");
									}else if(restCopy <= 0) {
										return CallBackConstant.FAILED.callbackError("您已购买最高限量" + limitMaxCopy + "件，不可再购买");
									}
								}else{//没买过此商品的时候就和限购数量比
									if(Integer.parseInt(amount) > limitMaxCopy){
										return CallBackConstant.FAILED.callbackError("购买数量超过最大限购数" + limitMaxCopy + "件");
									}
								}
							}
						}
					}
				}
			}
			
		}
		
		if("2".equals(coalition) && null!=coalition_pid && !"".equals(coalition_pid) ){
			pd.put("cartId",coalition_pid);
			shoppingCartManager.deleteShoppingCartByCidAndCid(pd);
		}
		
		pd.put("cartId",cartId);
		int result=shoppingCartManager.updateShoppingCartByCid(pd);
		
		logger.info("/app/shoppingcart/updateAmount end");
		
		if(result>0){
			return CallBackConstant.SUCCESS.callBackByMsg("修改成功");
		}else{
			return CallBackConstant.FAILED.callbackError("修改失败");
		}
		
	}
	
	/**
	 * 获取购物车商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getCartCnt",method={RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getCartCnt(HttpServletRequest request){
		logger.info("/app/shoppingcart/getCartCnt begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		
		String token=pd.getString("token");    //用户标识
		
		if(StringUtil.isEmpty(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token); 
		if(null == customer){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		logger.info("用户【"+customer.getCustomer_id()+"】"+"获取购物车商品数量");
		logger.info("/app/shoppingcart/getCartCnt end");
		
		try {
			int cartCnt = shoppingCartManager.queryShoppingCartCnt(customer.getCustomer_id());
			
			map.put("cartCnt", cartCnt);
			return CallBackConstant.SUCCESS.callback(map);
			
		} catch (Exception e) {
			logger.error("/app/shoppingcart/getCartCnt 出现异常", e);
			
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 验证用户能不能买商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/vilidateLimit")
	@ResponseBody
	public Map<String, Object> vilidateLimit(HttpServletRequest request){
		PageData pd = this.getPageData();
		String token = pd.getString("token"); // 用户标识
		CustomerVo customer = null;
		//用户标识
		if(StringUtils.isBlank(token)){
			return CallBackConstant.TOKEN_ERROR.callbackError("用户信息为空");
		}
		
		if(StringUtils.isBlank(pd.getString("cartIds"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("请选择商品");
		}
		
		customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
		}
		
		pd.put("customerId", String.valueOf(customer.getCustomer_id()));
		List<ShoppingCartVo>  listShoppingCartVo=shoppingCartManager.queryListShoppingCart(pd);
		if(listShoppingCartVo ==null || listShoppingCartVo.size() <= 0){
			return CallBackConstant.FAILED.callbackError("您的购物车还有没商品哦~");
		}
		
		for (ShoppingCartVo shoppingCartVo : listShoppingCartVo) {
			//限购操作
			PageData productPd = new PageData();
			productPd.put("productId", shoppingCartVo.getProduct_id());
			productPd.put("customerId", customer.getCustomer_id());
			productPd.put("specId", shoppingCartVo.getSpec_id());
			ProductSpecVo productSpecVo = productSpecManager.queryProductSpecByOption(productPd);
			if(productSpecVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("存在已下架的商品");
			}
			
			if(productSpecVo.getStatus().intValue() != 1 || productSpecVo.getProductStatus() != 1){
				return CallBackConstant.FAILED.callbackError(productSpecVo.getProduct_name()+"已下架");
			}
			
			int limitMaxCopy = productSpecVo.getLimit_max_copy()==null?-1:productSpecVo.getLimit_max_copy();
			if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
				if(limitMaxCopy>-1){//限购
					Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
					Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
					if(dt.after(new Date())&&start.before(new Date())){//限购时间内

						productPd.put("startTime", productSpecVo.getLimit_start_time());
						productPd.put("endTime", productSpecVo.getLimit_end_time());
						
						//按产品id 和规格查出所有买过此商品的订单详情
						productPd.put("checkStatus", "2,3,4,5");//已付款的订单
						productPd.put("specId", productSpecVo.getSpec_id());//规格
						int orderQuantity = orderProductManager.queryOrderProductQuantity(productPd);
						if(orderQuantity > 0) {
							int restCopy = limitMaxCopy - orderQuantity;
							if (restCopy > 0 && shoppingCartVo.getAmount() > restCopy) {
								return CallBackConstant.FAILED.callbackError("您已购买" + shoppingCartVo.getProduct_name() + orderQuantity + "件，只可加" + restCopy + "件");
							}else if(restCopy <= 0) {
								return CallBackConstant.FAILED.callbackError("您已购买" + shoppingCartVo.getProduct_name() + "最高限量" + limitMaxCopy + "件，不可再购买");
							}
						}else{//没买过此商品的时候就和限购数量比
							if(shoppingCartVo.getAmount() > limitMaxCopy){
								return CallBackConstant.FAILED.callbackError("购买" + shoppingCartVo.getProduct_name() + "数量超过最大限购数" + limitMaxCopy + "件");
							}
						}
					}
				}
			}
		}

		return CallBackConstant.SUCCESS.callback();
	}
	@RequestMapping(value="/calculateCopy",method={RequestMethod.POST},produces="text/html;charset=utf-8")
	@ResponseBody
	public String calculateCopy(HttpServletRequest request){
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		CustomerVo customer = Constants.getCustomer(request);
		String cartId=pd.getString("cartId");
		pd.put("customer_id",customer.getCustomer_id());
		pd.put("cart_id",Integer.parseInt(cartId));
		
		//限购操作
		ShoppingCartVo shoppingCartVo = shoppingCartManager.queryOne(pd);
		List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
		boolean isSpec = false;			//是否是专题	
		boolean isPre = false;			//是否是预购
		int prelimit=-1;				//预购不限购
		String preDate = "";
		String canBuy = "YES";			//是否可以加
		if(shoppingCartVo!=null){
			pd.clear();
			pd.put("productId", shoppingCartVo.getProduct_id());
			pd.put("specId", shoppingCartVo.getSpec_id());
			pd.put("status","1");
			ProductVo product = productManager.selectProductByOption(pd);
			listActivtyProduct = activityProductManager.queryList(pd);
			if(listActivtyProduct!=null&&listActivtyProduct.size()>0){
				for (ActivityProductVo activty : listActivtyProduct) {//符合时间的活动
					PageData spd=new PageData();
					if(activty.getActivity_type()==2){	
						spd.put("specialId", activty.getActivity_id());
						spd.put("status", 1);
						SpecialVo svo = specialMananger.selectSpecialByOption(spd);
						if(svo!=null&&svo.getSpecialType()==1){//限时活动
							Date dt = DateUtil.stringToDate(svo.getEndTime());
							Date start = DateUtil.stringToDate(svo.getStartTime());
							if(dt.after(new Date())&&start.before(new Date())){
								isSpec=true;//专题里面的限购
									break;
							}
						}
					}else if(activty.getActivity_type()==1){//预购
						spd.put("preId", activty.getActivity_id());
							PreProductVo preProductVo = preProductManager.selectPreProductByOption(spd);
							if(preProductVo!=null){
								Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
								Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
								if(dt.after(new Date())&&start.before(new Date())){
									isPre=true;
									preDate = preProductVo.getRaiseStart();
									prelimit = preProductVo.getLimitBuy()==null?-1:preProductVo.getLimitBuy();
									break;
								}
							}
						}
					
					}
				
				}
				json.put("result", canBuy);	
				json.put("specId", shoppingCartVo.getSpec_id());	
		}
				
				
		return json.toString();
	}
	
	/**
	 * 根据用户收货地址判断该收货地址是否支持配送
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/vilidateDeliver")
	@ResponseBody
	public Map<String,Object> vilidateDeliver(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String province_id = request.getParameter("province_id"); //省编码
		String city_id = request.getParameter("city_id"); //市编码
		String area_id = request.getParameter("area_id"); //区编码
		String product_ids = request.getParameter("product_ids"); //区编码
		if (StringUtil.isEmpty(province_id) || StringUtil.isEmpty(city_id) 
				|| StringUtil.isEmpty(area_id) || StringUtil.isEmpty(product_ids)) {
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		resultMap = shoppingCartManager.queryDeliver(product_ids, province_id, city_id, area_id);
		
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
}
