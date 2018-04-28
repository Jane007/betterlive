package com.kingleadsw.betterlive.controller.app.integral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ProductRedeemManager;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ProductRedeemVo;

/**
 * app1.2.7版本 金币优惠购
 */
@Controller
@RequestMapping("/app/productredeem")
public class AppProductRedeemController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(AppProductRedeemController.class);
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductRedeemManager productRedeemManager;

	/**
	 * 优惠购列表 首页
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/queryGoldBuyBaseInfo")
	@ResponseBody
	public Map<String, Object> queryGoldBuyBaseInfo(HttpServletRequest req, HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}

		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if (customer == null) {
			return CallBackConstant.NOT_EXIST.callback();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bannerStatus", IntegralConstants.COMMON_STATUS_YES);
		map.put("bannerUrl", "http://images.hlife.shop/5c4b656c072b46b18a321f1566d9b709.jpg");
		map.put("bannerDetailUrl",
				"http://www.hlife.shop/huihuo/subject/ZnqWlofz18/index.html?addShopCart=1&customerId="
						+ customer.getCustomer_id()
						+ "&ppd=" + customer.getPassword());
		map.put("listTile", "限量精选，先到先得");
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	//查询优惠购商品列表
	@RequestMapping(value="/queryProductRedeems")
	@ResponseBody
	public Map<String,Object> queryProductRedeems(HttpServletRequest req){
		PageData pd = this.getPageData();
		String token = pd.getString("token"); // 用户标识
		
		if (StringUtil.isEmpty(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if (null == customer) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		} 
		pd.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
		List<ProductRedeemVo> list = productRedeemManager.queryProductRedeemsListPage(pd);
		return CallBackConstant.SUCCESS.callback(list);
	}
	
	//查询优惠购商品列表
	@RequestMapping(value="/queryOrderRedeemRules")
	@ResponseBody
	public Map<String,Object> queryOrderRedeemRules(HttpServletRequest req){
		Map<String, Object> result = new HashMap<String, Object>();
		return CallBackConstant.SUCCESS.callback(result);
	}
}
