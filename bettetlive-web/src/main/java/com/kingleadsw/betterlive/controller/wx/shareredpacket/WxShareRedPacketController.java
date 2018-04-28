package com.kingleadsw.betterlive.controller.wx.shareredpacket;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.PayLogManager;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.dao.MessageMapper;
import com.kingleadsw.betterlive.model.Message;import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.wx.SignUtil;
import com.kingleadsw.betterlive.util.wx.service.WeixinService;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.PayLogVo;
import com.kingleadsw.betterlive.vo.SysDictVo;

/**
 * 微信端  红包管理
 * 2017-03-30 by chen
 *
 */
@Controller
@RequestMapping(value = "/weixin/shareredpacket")
public class WxShareRedPacketController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxShareRedPacketController.class);

	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private WeixinService weixinService;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private MessageMapper messageMapper;	
	
	//进入分享红包url地址
	private static final String share_redpackets_url =WebConstant.MAIN_SERVER+ "/weixin/shareredpacket/toSendRedPacket?orderCode=%1$s";
		
	
	/**
	 *支付成功订单详情页面 发红包
	 */
	@RequestMapping(value = "/toSendRedPacket")
	public ModelAndView toSendRedPacket(HttpServletRequest request){
		String msg = "toSendRedPacket";
		logger.info("/weixin/shareredpacket/"+msg+" begin");
		
		ModelAndView modelAndView = new ModelAndView();
		CustomerVo customerVo=Constants.getCustomer(request);     
		
		PageData pd =this.getPageData();
		String orderCode=pd.getString("orderCode"); 
		
		pd.put("customerId", String.valueOf(customerVo.getCustomer_id()));
		
		String jsApiTicket = weixinService.getJsApiTicket();
		String share_url = String.format(share_redpackets_url, orderCode);
		Map<String, String> ticketMap = SignUtil.jsApiTicktSign(jsApiTicket, share_url);
		if (ticketMap != null) {
			ticketMap.put("appId", WebConstant.WX_APPID);
		}
		modelAndView.addObject("shareUser", customerVo.getNickname());
		modelAndView.addObject("shareInfo", ticketMap);
		CouponManagerVo couponManager = null;
		//系统参数配置： 支付成功后可以分享的红包总数
		pd.put("dictCode","SHARE_REDPACKED_NUM");
		SysDictVo vo = sysDictManager.selectByCode(pd);
		if(null!=vo && orderCode!=null && !"".equals(orderCode)){
			modelAndView.addObject("sharecount", vo.getDictValue());
		}else{
			modelAndView.addObject("sharecount",null);
		}
		pd.put("couponType","1");   //分享券
		couponManager = couponManagerManager.findCouponManager(pd);
		modelAndView.addObject("orderCode",orderCode);
		modelAndView.addObject("coupon", couponManager);
		
		modelAndView.setViewName("weixin/redpacket/wx_share_redpacket");
		
		logger.info("/weixin/shareredpacket/"+msg+" end");
		return modelAndView;
	}
	
	
	/**
	 * 用户领取红包  
	 * 如果用户存在手机号码就直接添加然后显示在前台
	 * 否则就提示用户绑定手机号码
	 */
	@RequestMapping("/getShareRedPacket")
	public ModelAndView getShareRedPacket(HttpServletRequest request){
		logger.info("/weixin/shareredpacket/getShareRedPacket,begin");
		
   		ModelAndView modelAndView = new ModelAndView("weixin/redpacket/wx_go_redpacket");
		
		PageData pd = this.getPageData();
		String orderCode = pd.getString("orderCode");
		
		//订单支付详情
		PayLogVo payLogInfo = null;
		
		//根据ID查询优惠券信息
		CouponManagerVo couponManager = null;
		CouponInfoVo couponInfoVo = null;
		
		String endDate = null;  //红包有效期截止日期
		String flag = "succ";
		
		CustomerVo customerVo = Constants.getCustomer(request);
		
		
//		CustomerVo coustomerVo=Constants.getCustomer(request);
		
		OrderVo orderVo =orderManager.findOrder(pd);
		if(null == orderVo ){
			logger.error("用户领取红包，但该红包对应的订单不存在，领取失败");
			modelAndView.addObject("msg", "该红包对应的订单不存在");
			modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
			return modelAndView;
		}
		
		
		String mobile = customerVo.getMobile();
		if(null == mobile || "".equals(mobile)){
			logger.info("用户【" +customerVo.getOpenid()+ "】，手机号码为空，不允许领取红包");
			modelAndView.addObject("msg", "该红包对应的订单不存在");
			modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
			return modelAndView;
		}
		
		try {
			pd.put("couponType","1");   //分享券
			couponManager = couponManagerManager.findCouponManager(pd);
			
			BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
			couponManager.setCoupon_money(String.valueOf(couponMoney.intValue()));
			
			BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
			couponManager.setUsemin_money(String.valueOf(useminMoney.intValue()));
			
			//系统参数配置： 支付成功后可以分享的红包总数
			pd.put("dictCode","SHARE_REDPACKED_NUM");
			SysDictVo vo = sysDictManager.selectByCode(pd);
			//根据订单ID查询已付款订单中领取优惠券的数量
			int couponCount=couponInfoManager.findShareCountByOrderId(orderVo.getOrder_id());
			if(couponCount>=Integer.parseInt(vo.getDictValue())){
				logger.error("用户领取红包，但该红包已被领取完，领取失败");
				modelAndView.addObject("msg", "该红包已被领取完");
				modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
				return modelAndView;
			}
		
			
			//查询支付日志
			payLogInfo = payLogManager.findPayLog(pd);
			
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(DateUtil.stringToDate(payLogInfo.getPay_time()));
			calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
			
			if(null == couponManager || null == payLogInfo || 
					null==payLogInfo.getPay_time() || "".equals(payLogInfo.getPay_time())){
				logger.error("用户领取红包，但该红包对应的分享券或者支付信息不存在，领取失败");
				modelAndView.addObject("msg", "该红包对应的分享券或者支付信息不存在");
				modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
				return modelAndView;
			}else{
				Integer finishDate =Integer.parseInt(DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd").replaceAll("-",""));
				Integer currentDate =Integer.parseInt(DateUtil.FormatDate(new Date(),"yyyy-MM-dd").replaceAll("-",""));
				
				if(finishDate<currentDate){
					logger.error("用户领取红包，但该红包已过期，领取失败");
					modelAndView.addObject("msg", "该红包已过期");
					modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
					return modelAndView;
				}
			}
			
			
			endDate = DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
			
			if(null != mobile && !"".equals(mobile)){
				PageData pageData = new PageData();
				pageData.put("mobile",mobile);
		//		pageData.put("customerId",String.valueOf(customerVo.getCustomer_id()));
				pageData.put("orderId",String.valueOf(payLogInfo.getOrder_id()));
				int hasCount=couponInfoManager.findUserShareCount(pageData);
				
				if(hasCount>0){
					flag="exist";
				}else{
					String currentDate = DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
					
					couponInfoVo=new CouponInfoVo();
					couponInfoVo.setMobile(mobile);
					couponInfoVo.setCustomer_id(customerVo.getCustomer_id());
					couponInfoVo.setCm_id(couponManager.getCm_id());
					couponInfoVo.setCoupon_money(couponMoney.intValue());
					
					couponInfoVo.setStarttime(currentDate);
					couponInfoVo.setEndtime(endDate);
					couponInfoVo.setStart_money(useminMoney.intValue());
					couponInfoVo.setCoupon_from(2);
					couponInfoVo.setStatus(0);    //未使用
					couponInfoVo.setFrom_user_id(payLogInfo.getCustomer_id());
					couponInfoVo.setFrom_order_id(payLogInfo.getOrder_id());
					couponInfoVo.setOrder_code(orderCode);
					int count =  couponInfoManager.insertUserCoupon(couponInfoVo);
					if(count > 0){
						//发系统消息给用户
						Message msgVo = new Message();
						msgVo.setMsgType(2);
						msgVo.setSubMsgType(1);
						msgVo.setMsgTitle("恭喜您获得了一个红包");
						String couponContent = "";
						if(StringUtil.isNotNull(couponManager.getCoupon_content())){
							couponContent = couponManager.getCoupon_content();
						}
						msgVo.setMsgDetail(couponContent);
						msgVo.setIsRead(0);
						msgVo.setCustomerId(payLogInfo.getCustomer_id());
						msgVo.setObjId(couponInfoVo.getCoupon_id());
						messageMapper.insert(msgVo);
					}			
				}
					
			}
			
			
		} catch (Exception e) {
			logger.error("查询订单为空"+e);
			
			flag="fail";
			modelAndView.setViewName("weixin/redpacket/getRedpacketFinish");
			return modelAndView;
			
		}
		
		
		modelAndView.addObject("orderCode",orderCode);
		modelAndView.addObject("mobile",mobile);
		modelAndView.addObject("flag",flag);
		modelAndView.addObject("coupon",couponManager);
		modelAndView.addObject("endDate",endDate);
		
		logger.info("/weixin/shareredpacket/getShareRedPacket, end");
		 
		 return modelAndView;
	}
	
	
	
	/**
	 * 通过手机号码绑定用户
	 * 然后添加优惠券
	 * 2017-04-31 by chen
	 */
	@RequestMapping("/getUserShareRedPacket")
	@ResponseBody
	public void getUserShareRedPacket(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="getUserShareRedPacket";
		logger.info(" /weixin/shareredpacket/ "+msg+"  start ...");
		
		JSONObject json=new JSONObject();  
		
		CustomerVo customer=Constants.getCustomer(request);
		
		PageData pd = this.getPageData();
		String orderCode = pd.getString("orderCode");
		
		String mobile=pd.getString("mobile");
		String code=pd.getString("code");
		String redisCode=redisService.getString(mobile); //验证码  保存本地缓存，有效期5分钟
		
		if(code.equals(redisCode)){
			PageData pds=new PageData();
			pds.put("mobile",mobile);
			pds.put("customer_id", customer.getCustomer_id());
			
			try {
				int result=customerManager.updateCustoemrById(pds);
				if(result>0){
					customer.setMobile(mobile);
					Constants.setCustomer(request, customer);
					
					
					//系统参数配置： 支付成功后可以分享的红包总数
					pd.put("dictCode","SHARE_REDPACKED_NUM");
					SysDictVo vo = sysDictManager.selectByCode(pd);
					
					//查询订单
					PageData pdata=new PageData();
					pdata.put("orderCode",orderCode);
					OrderVo orderVo =orderManager.findOrder(pdata);
					
					//根据订单ID查询已付款订单中领取优惠券的数量
					int couponCount=couponInfoManager.findShareCountByOrderId(orderVo.getOrder_id());
					if(couponCount>=Integer.parseInt(vo.getDictValue())){
						logger.error("用户领取红包，但该红包已被领取完，领取失败");
						json.put("result","successful");
					}else{
						String currentDate = DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
						
						//根据ID查询优惠券信息
						pd.put("couponType","1");   //分享券
						CouponManagerVo couponManager = couponManagerManager.findCouponManager(pd);
						
						//查询支付日志
						//订单支付详情
						PayLogVo payLogInfo = payLogManager.findPayLog(pd);
						
						Calendar calendar = Calendar.getInstance();  
						calendar.setTime(DateUtil.stringToDate(payLogInfo.getPay_time()));
						calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
						
						String endDate = DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
						
						CouponInfoVo couponInfoVo=new CouponInfoVo();
						BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
						couponManager.setCoupon_money(String.valueOf(couponMoney.intValue()));
						
						BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
						couponManager.setUsemin_money(String.valueOf(useminMoney.intValue()));
						
						couponInfoVo.setMobile(mobile);
						couponInfoVo.setCustomer_id(customer.getCustomer_id());
						couponInfoVo.setCm_id(couponManager.getCm_id());
						couponInfoVo.setCoupon_money(couponMoney.intValue());
						
						couponInfoVo.setStarttime(currentDate);
						couponInfoVo.setEndtime(endDate);
						couponInfoVo.setStart_money(useminMoney.intValue());
						couponInfoVo.setCoupon_from(2);
						couponInfoVo.setStatus(0);   //未使用
						couponInfoVo.setFrom_user_id(payLogInfo.getCustomer_id());
						couponInfoVo.setFrom_order_id(payLogInfo.getOrder_id());
						couponInfoVo.setOrder_code(orderCode);
						couponInfoManager.insertUserCoupon(couponInfoVo);
						
						json.put("result","succ");
					}
					
				}else{
					throw new Exception();
				}
				
			} catch (Exception e) {
				json.put("result","fail");
				
				logger.error("绑定用户手机号码!", e);
			}
			
		}else{
			json.put("result","fail");
			json.put("msg","验证码错误或已过期");
		}
		
		logger.info(" /weixin/shareredpacket/ "+msg+"  end ...");
		
		this.outObjectToJson(json, response);
	}
	
}
