package com.kingleadsw.betterlive.controller.app.advert;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
@Controller
@RequestMapping(value = "/app/advert")
public class AppAdvertController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(AppAdvertController.class);
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private CouponManagerManager couponManagerManager;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private UserSingleCouponManager  userSingleCouponManager;
	
	/**
	 * 广告位需要的元素 linkUrl 跳转url, imageUrl 图片url ,on 1有效 0无效
	 * @param
	 * @return
	 * @author zhangjing 2018年1月4日 下午3:53:17
	 */
	@RequestMapping(value = "/getAdvert",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAdvert(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("on", "1");
			map.put("linkUrl", WebConstant.MAIN_SERVER+"/weixin/advert/toAdvertGift");
			map.put("imageUrl", "http://images.hlife.shop/advertising20180107.jpg");
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/advert/getAdvert", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 广告页一键领取
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveMultiCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveMultiCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/app/advert/receiveMultiCoupon  begin");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		if(StringUtil.isNotNull(pd.getString("token"))){
			CustomerVo customer = this.customerManager.queryCustomerByToken(pd.getString("token"));
			
			if(customer == null){
				map.put("flag",1011);
				return CallBackConstant.FAILED.callback(map);
			}
			//广告页一键领取优惠券
			String cmIds = WebConstant.NEW_USER_COUPON;
			PageData couponParam = new PageData();
			if(customer != null){
				String[] ids = cmIds.split(",");
				couponParam.put("couponIds", ids);
				couponParam.put("couponType", 2);
				couponParam.put("customerId",customer.getCustomer_id());
				List<CouponInfoVo> list = couponInfoManager.findListUserCoupon(couponParam);
				if(list!=null&&ids!=null){
					if(ids.length<=list.size()){//说明已经领完了所有的红包了
						map.put("flag",1405);
						return CallBackConstant.SUCCESS.callback(map);
					}
				}
			}
			
			if(StringUtil.isNotNull(cmIds)){
				PageData pdd = new PageData();
				pdd.put("cmIds",cmIds);
				pdd.put("customerId", customer.getCustomer_id());
				pdd.put("mobile", customer.getMobile());
				int count = couponInfoManager.batchInsertCouponInfo(pdd);
				if(count>0){
					map.put("flag",1010);
					return CallBackConstant.SUCCESS.callback(map);	
				}
				return CallBackConstant.FAILED.callback();	
			}
			
		}
		return CallBackConstant.FAILED.callback();	
	}
		
		
}
