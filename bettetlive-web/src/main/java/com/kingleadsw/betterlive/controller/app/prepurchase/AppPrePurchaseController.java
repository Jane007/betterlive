package com.kingleadsw.betterlive.controller.app.prepurchase;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
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
 * 
 * @author chentengfei
 * @date 2017年4月27日
 */
@Controller
@RequestMapping(value = "/app/prepurchase")
public class AppPrePurchaseController extends AbstractWebController {
	private static Logger logger = Logger.getLogger(AppPrePurchaseController.class);
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
	@Autowired
	private CustomerManager customerManager;

	/**
	 * 获取预售列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/app/prepurchase/list--->begin");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		pd.put("status", 1);// PreProduct正常状态
		List<PreProductVo> list = preProductManager.queryListPage(pd);
		map.put("preList", list);
		logger.info("/app/prepurchase/list--->end");

		return CallBackConstant.SUCCESS.callback(map);
	}

	/**
	 * 查询预售详情
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/details")
	@ResponseBody
	public Map<String, Object> details(HttpServletRequest request) {
		logger.info("查询预售详情信息开始");
		PageData pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = pd.getString("token");
		
		pd.put("status", 1);  //商品上架
		pd.put("isOnline", 1); //线上商品
		ProductVo product = productManager.selectProductByOption(pd);

		if (null == product) {
			return CallBackConstant.DATA_NOT_FOUND.callbackError("该商品已经下架");
		}

		String details = product.getDetails();
		if (null != details && !"".equals(details)) {
			product.setParamAndValue(details.split("@\\#@\\$"));
		}

		if (null != product.getProduct_logo() && !"".equals(product.getProduct_logo())) {
			String[] pictureArrays = product.getProduct_logo().split(",");
			product.setPictureArray(pictureArrays);
		}

		List<CommentVo> comments = commentManager.queryCommentListByProductId(product.getProduct_id());
		resultMap.put("comments", comments);


		// 查询是否存在活动 ，如果是 就加入活动价
		PageData pdData = new PageData();
		pdData.put("productId", String.valueOf(product.getProduct_id()));
		pdData.put("activityType", 1);// 预售
		// 查看商品是否存在活动
		List<ActivityProductVo> listActivtyProduct = activityProductManager.queryList(pdData);

		if (listActivtyProduct != null && listActivtyProduct.size() > 0) {
			// 设置每个商品规格的活动价
			List<ProductSpecVo> speclist = product.getListSpecVo();
			for (ActivityProductVo spec : listActivtyProduct) {// 修改规格的专题价
				PageData spd = new PageData();
				spd.put("preId", spec.getActivity_id());
				PreProductVo preProductVo = preProductManager.selectPreProductByOption(spd);
				if (preProductVo != null) {// 预售
					Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
					Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
					if (dt.after(new Date()) && start.before(new Date())) {
						if (speclist != null && speclist.size() > 0) {
							for (ProductSpecVo productSpecVo : speclist) {
								if (productSpecVo.getSpec_id().equals(spec.getSpec_id())) {// 设置每个商品规格的活动价
									productSpecVo.setActivity_price(spec.getActivity_price());
								}
							}
						}
					}
				}
			}
		}

		// 产品详情下面显示 猜你喜欢
		PageData pageData = new PageData();
		pageData.put("productStatus", 1); // 商品状态为上架
		pageData.put("eatRecommend", "1"); // 是猜你喜欢
		List<ProductVo> guessLikeList = productManager.queryProductList(pageData);

		/**
		 * 获取众筹信息
		 */
		PreProductVo preProduct = preProductManager.selectPreProductByOption(pd);
		if (preProduct != null) {
			Date raiseStart = DateUtil.stringToDate(preProduct.getRaiseStart());
			Date raiseEnd = DateUtil.stringToDate(preProduct.getRaiseEnd());
			Date now = new Date();
			if (now.after(raiseStart) && now.before(raiseEnd)) {
				resultMap.put("isActive", "true"); // 是否有效
			}
		}

		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (customer != null) {
			// 根据条件查出此用户是否买过此产品
			PageData productPd = new PageData();
			productPd.put("productId", product.getProduct_id());
			productPd.put("customerId", customer.getCustomer_id());
			if (StringUtils.isNotBlank(preProduct.getRaiseStart())) {
				productPd.put("startTime", preProduct.getRaiseStart());
			}
			if (StringUtils.isNotBlank(preProduct.getRaiseEnd())) {
				productPd.put("endTime", preProduct.getRaiseEnd());
			}

			String canBuy = "YES";
			int restCopy = 1000; // 剩余购买数量
			// productPd.put("status", 2); //已付款的订单
			productPd.put("hasBuy", "hasBuy");// 已付款的订单
			int hasBuy = 0;
			int limitNum = -1; // -1不限购
			int buyMS = restCopy; // 马上购买不算购物车里面的购买数量
			if (preProduct.getRaiseStart() != null
					&& preProduct.getRaiseEnd() != null
					&& preProduct.getLimitBuy() != null
					&& preProduct.getLimitBuy() > 0) {
				Date raiseStart = DateUtil.stringToDate(preProduct.getRaiseStart());
				Date raiseEnd = DateUtil.stringToDate(preProduct.getRaiseEnd());
				Date now = new Date();
				if (now.after(raiseStart) && now.before(raiseEnd)) {
					restCopy = preProduct.getLimitBuy() - hasBuy;
					limitNum = preProduct.getLimitBuy();
					if (hasBuy > preProduct.getLimitBuy()) {
						canBuy = "NO";
					}
				}
				// 查出之前下过的单
				List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(productPd);
				if (orderProductlist != null && orderProductlist.size() > 0) {
					for (OrderProductVo orderProductVo : orderProductlist) {
						hasBuy += orderProductVo.getQuantity();
					}
					restCopy = preProduct.getLimitBuy() - hasBuy;
				}
			}

			buyMS = restCopy;
			// 从购物车里面查数量有多少
			pd.put("customerId", customer.getCustomer_id());
			pd.put("productId", product.getProduct_id());
			List<ShoppingCartVo> listShoppingCartVo = shoppingCartManager.queryListShoppingCart(pd);
			int carCanAdd = 100;
			if (canBuy.equals("YES")) {
				if (preProduct.getLimitBuy() != null && preProduct.getLimitBuy() > 0) {
					carCanAdd = preProduct.getLimitBuy();
				}
			}
			int cartNums = 0;// 购物车里面的数量
			if (listShoppingCartVo != null && listShoppingCartVo.size() > 0) {
				for (ShoppingCartVo shoppingCartVo : listShoppingCartVo) {
					cartNums += shoppingCartVo.getAmount();
				}
				restCopy = restCopy - cartNums;
				if (canBuy.equals("YES")) {
					carCanAdd = restCopy - cartNums;
				}
			}

			resultMap.put("buyMS", buyMS);
			resultMap.put("limitNum", limitNum);
			resultMap.put("cartNums", cartNums);
			resultMap.put("hasBuy", hasBuy);
			resultMap.put("carCanAdd", carCanAdd);
			resultMap.put("canBuy", canBuy);
			resultMap.put("restCopy", restCopy);
		}

		resultMap.put("productInfo", product);
		resultMap.put("guessLikeList", guessLikeList);
		resultMap.put("preProduct", preProduct);

		return CallBackConstant.SUCCESS.callback(resultMap);
	}

	/**
	 * 查询商品规格
	 */
	@RequestMapping(value = "/queryProductSpecAllJson")
	@ResponseBody
	public Map<String, Object> queryProductSpecAllJson(HttpServletRequest request, HttpServletResponse response) {
		logger.info("/app/prepurchase/queryProductSpecAllJson--->begin");

		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String productId = pd.getString("productId");

		if (null == productId || "".equals(productId)) {
			map.put("msg", "productId不能为空");
			return CallBackConstant.PARAMETER_ERROR.callback(map);
		}
		String token = pd.getString("token");
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		
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
			
			logger.info("/app/prepurchase/queryProductSpecAllJson--->end");

			// 需要对过期的活动进行筛选
			if (null != list && list.size() > 0) {
				map.put("list", list);
				return CallBackConstant.SUCCESS.callback(map);

			} else {
				return CallBackConstant.FAILED.callbackError("集合为空");
			}
		} catch (Exception e) {
			logger.info("/app/prepurchase/queryProductSpecAllJson---> exception ");

			return CallBackConstant.FAILED.callback();
		}
	}

}
