package com.kingleadsw.betterlive.controller.wx.integral;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ProductRedeemManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ProductRedeemVo;

/**
 * wx1.2.7版本 金币优惠购
 */
@Controller
@RequestMapping("/weixin/productredeem")
public class WxProductRedeemController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(WxProductRedeemController.class);
	
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
	@RequestMapping(value="/toGoldBuy")
	public ModelAndView toGoldBuy(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("weixin/integral/wx_goldbuy");
		
		CustomerVo customer = Constants.getCustomer(req);
		if(null == customer){
			mv = new ModelAndView("weixin/wx_login");
			return mv;
		}
		
		mv.addObject("bannerStatus", IntegralConstants.COMMON_STATUS_YES);
		mv.addObject("bannerUrl", "http://images.hlife.shop/5c4b656c072b46b18a321f1566d9b709.jpg");
		mv.addObject("bannerDetailUrl", "http://www.hlife.shop/huihuo/subject/ZnqWlofz18/index.html?addShopCart=1");
		mv.addObject("listTile", "限量精选，先到先得");
		return mv;
	}
	
	//查询优惠购商品列表
	@RequestMapping(value="/queryProductRedeems")
	@ResponseBody
	public Map<String,Object> queryProductRedeems(HttpServletRequest req){
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(req);
		if(null == customer){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		pd.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
		List<ProductRedeemVo> list = productRedeemManager.queryProductRedeemsListPage(pd);
		return CallBackConstant.SUCCESS.callbackPageInfo(list, pd.get("pageView"));
	}
	
}
