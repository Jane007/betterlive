package com.kingleadsw.betterlive.controller.wx.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
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
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityManager;
import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerAgentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.biz.InviteRewardManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PayLogManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductRedeemSpecManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.ReceiverAddressManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.biz.UseLockManager;
import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.common.util.HttpClientTool;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.alipay.config.AlipayConfig;
import com.kingleadsw.betterlive.pay.alipay.util.AliPaySubmit;
import com.kingleadsw.betterlive.util.CustomerSourceUtil;
import com.kingleadsw.betterlive.util.TokenProcessor;
import com.kingleadsw.betterlive.util.alipay.service.AlipayService;
import com.kingleadsw.betterlive.util.alipay.service.OneCardPayService;
import com.kingleadsw.betterlive.util.wx.WxSign;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.util.wx.XmlParser;
import com.kingleadsw.betterlive.util.wx.bean.WxNotifyData;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgBodyDto;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgDataDto;
import com.kingleadsw.betterlive.util.wx.templatemessage.util.WxTemplateMessageUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerAgentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;
import com.kingleadsw.betterlive.vo.GiftCardVo;
import com.kingleadsw.betterlive.vo.GroupJoinVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.PayLogVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.ProductRedeemSpecVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;
import com.kingleadsw.betterlive.vo.ReceiverAddressVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
import com.kingleadsw.betterlive.vo.UseLockVo;
import com.kingleadsw.betterlive.vo.UserGroupVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

/**
 * 支付
 * 2017-03-13 by chen
 *
 */
@Controller
@RequestMapping(value = "/weixin/payment")
public class PaymentController extends AbstractWebController {


	private static Logger logger = Logger.getLogger(PaymentController.class);

	@Autowired
	private OrderManager orderManager;
	@Autowired
	private CouponInfoManager couponInfoManager; 
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private ReceiverAddressManager receiverAddressManager;
	@Autowired
	private ShoppingCartManager shoppingCartManager;
	@Autowired
	private ActivityManager activityManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private UseLockManager useLockManager;
	@Autowired
	private OrderProductManager orderProductManager; 
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private GiftCardRecordManager giftCardRecordManager;
	
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ActivityProductManager activityProductManager;
//	@Autowired
//	private PreProductManager preProductManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SalePromotionManager salePromotionManager;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	
	@Autowired
	private MessageManager messageManager;	
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
	@Autowired
	private PostageManager postageManager; 
	@Autowired
	private AlipayService aliPayService;
	@Autowired
	private OneCardPayService oneCardPayService;
	@Autowired
	private CustomerAgentManager customerAgentManager;
	@Autowired
	private ProductRedeemSpecManager productRedeemSpecManager;
	@Autowired
	private InviteRewardManager inviteRewardManager;
	
	/**
	 * 前往支付宝跳转页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toAliPaySkip")
	public ModelAndView toAliPaySkip(HttpServletRequest httpRequest){ 
		ModelAndView modelAndView =new ModelAndView("weixin/order/api_pay_skip");
		PageData pd = this.getPageData();
		OrderVo orderVo = this.orderManager.findOrder(pd);
		modelAndView.addObject("orderCode", pd.getString("orderCode"));
		modelAndView.addObject("orderStatus", orderVo.getStatus());
		return modelAndView;
	}
	
	//支付宝请求参数拼接bao
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(value="/alipay")
	public void alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String payBusinessId = request.getParameter("payBusinessId");
	 	response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String html = ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
	                "<html>\n" +
	                "\t<head>\n" +
	                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
	                "\t\t<title>支付宝手机网页支付</title>\n" +
	                "\t</head>");
	    //建立请求
	    String sHtmlText = aliPayService.aliPay( payBusinessId, request);
	    html += sHtmlText;
	    html += "\t<body>\n" +
	            "\t</body>\n" +
	             "</html>";
	    out.println(html);
	 }
	
	/**
	 * 一卡通下单参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/oneCardPay",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public String oneCardPay(HttpServletRequest request,HttpServletResponse response){
		CustomerVo customer=Constants.getCustomer(request);
		if (StringUtil.isNull(customer.getYktAgrNo())) {
			PageData custData = new PageData();
			custData.put("customer_id", customer.getCustomer_id());
			customer=customerManager.queryOne(custData);
			Constants.setCustomer(request, customer);
		}
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		OrderVo orderVo = this.orderManager.findOrder(pd);
		String oneCardPack = oneCardPayService.oneCardPack(orderVo,customer,"wx");
		json.put("oneCardPack", oneCardPack);
		json.put("payApi", WebConstant.YKT_PAY_API);
		json.put("result", "succ");
		return json.toString();
	}
	
	//************************一卡通支付结果异步通知--start************//
		@RequestMapping(value="/oneCardPayNotify")
		public void oneCardPayNotify(HttpServletRequest request, HttpServletResponse response) {
			PageData pd = this.getPageData();
			JSONObject json = JSONObject.fromObject(pd.getString("jsonRequestData"));
			JSONObject noticeData = json.getJSONObject("noticeData");
			PageData custData=new PageData();
			String[] noticePara =  noticeData.getString("merchantPara").split("\\|");
			custData.put("customer_id", noticePara[1]);
			CustomerVo customer=customerManager.queryOne(custData);
			Map map = noticeData;
			StringBuffer buffer = new StringBuffer();
			List<String> keyList = oneCardPayService.sortParams(map);
			for (String key : keyList) {
				buffer.append(key).append("=").append(map.get(key)).append("&");
			}
			try {
				if (oneCardPayService.isValidSignature(buffer.substring(0, buffer.length()-1).toString(), json.getString("sign"))) {
					notifyUpdateAboutOrder(request,response, noticeData.getString("orderNo"),noticeData.getString("bankSerialNo"),"3",customer);
				}else {
					logger.info("警告,接收到来自未知的回调请求!");
				}
				
			} catch (Exception e) {
				logger.error("/weixin/payment/oneCardPayNotify --error", e);
			}
		}
		//************************一卡通签约结果异步通知--start************//
		@RequestMapping(value="/oneCardSignNotify")
		public void oneCardSignNotify(HttpServletRequest request, HttpServletResponse response) {
			PageData pd = this.getPageData();
			JSONObject json = JSONObject.fromObject(pd.getString("jsonRequestData"));
			JSONObject noticeData = json.getJSONObject("noticeData");
			Map map = noticeData;
			StringBuffer buffer = new StringBuffer();
			List<String> keyList = oneCardPayService.sortParams(map);
			for (String key : keyList) {
				buffer.append(key).append("=").append(map.get(key)).append("&");
			}
			if (oneCardPayService.isValidSignature(buffer.substring(0, buffer.length()-1).toString(), json.getString("sign"))) {
//				保存客户协议号
				PageData customer=new PageData();
				String[] noticePara =  noticeData.getString("noticePara").split("\\|");
				customer.put(noticePara[0], noticePara[1]);
				customer.put("yktAgrNo", noticeData.getString("agrNo"));
				int res = customerManager.updateCustoemrById(customer);
				if (res == 1) {
//					Constants.setCustomer(request, customer);
				}else{
					logger.info("用户一卡通签约信息记录失败!");
				}
			}else {
				logger.info("警告,接收到来自未知的回调请求!");
			}
		}
	
	/**
	 * 微信下单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pay",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public String pay(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		if (StringUtil.isNull(pd.getString("token")) ||
				!TokenProcessor.isTokenvalid(request)) { //判断提交表单token是否为空
			//如果token为空，那么该页面不是通过正常渠道进入或者已经提交过，token被清空
			json.put("result", "failure");
			json.put("msg", "该订单已经提交，请到个人中心查看该订单详情");
			return json.toString();
		}
		
		String productIds=pd.getString("productIds");        //购买的商品Id
		String specIds=pd.getString("specIds");              //规格Id               
		String amounts=pd.getString("amounts");              //购买数量	
		String receiveId=pd.getString("receiverId");         //收货地址 ID
		String useCouponId=pd.getString("useCouponId");         //使用的优惠券ID
		String payType=pd.getString("payType");     		   //支付类型
		String giftcardId=pd.getString("giftcardId");          //购物卡ID  使用礼品卡   useGiftCard
		String usesinglecouponId = pd.getString("usesinglecouponId");//使用单品红包优惠券
		String cartIds=pd.getString("cartIds");      //购物车Id
		String orderSource=pd.getString("orderSource");
		String messageInfo = pd.getString("message_info"); //留言
		String goldDeductSpecId = pd.getString("goldDeductSpecId");	//普通金币抵扣活动ID
		String redeemSpecId = pd.getString("redeemSpecId");		//金币优惠购
		
		String orderCode=generateOrderCode();
		CustomerVo customer=Constants.getCustomer(request);
		if (customer == null || customer.getCustomer_id() == null) {
			json.put("result", "failure");
			json.put("msg", "用户信息为空");
			return json.toString();
		}
		
		CustomerSourceUtil.removeOrderSource(request);
		
		logger.info("Customer用户id为"+customer.getCustomer_id()+"--进入了微信下单------");
		
		
		if(productIds==null||"".equals(productIds) || specIds==null||"".equals(specIds)
				 || amounts==null||"".equals(amounts)){
			json.put("result", "failure");
			json.put("msg", "productId为空");
			return json.toString();
		}
		
		String[] productIdArray=productIds.split(",");
		String[] specIdArray=specIds.split(",");
		String[] amountArray=amounts.split(",");
		
		
		BigDecimal price=null;
		BigDecimal payPrice=null;
		
		//查询订单商品存进订单商品集合
		List<OrderProductVo> listOrderProducts=null;
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		//获取订单商品金额
		Map<String,Object> map=getOrderPrice(productIdArray,specIdArray,amountArray,customer, 1, 0);
		
		if("not".equals(map.get("exist"))){
			json.put("result", "notexist");
			json.put("msg", "商品已下架，请重新下单");
			return json.toString();
		}
		if(map.get("noStockCopy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("noStockCopy"));
			return json.toString();
		}
		if(map.get("CannotBuy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("CannotBuy"));
			return json.toString();
		}
		price=(BigDecimal) map.get("price");
		
		logger.info("商品总价price----------------"+price);
		payPrice=(BigDecimal)map.get("payPrice");
		listOrderProducts=(List<OrderProductVo>) map.get("listOrderProducts");
		
		PageData pagedata=new PageData();
		pagedata.put("customerId", String.valueOf(customer.getCustomer_id()));
		
		//收货地址   查询运费
		pagedata.put("receiverId",receiveId);
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.selectReceiverAddressByOption(pagedata);
		String postage = "0.00";
		if(null == receiverAddressVo){
			json.put("result", "failure");
			json.put("msg", "无效的收货地址");
			return json.toString();
		}else{
			if(null != receiverAddressVo){
				BigDecimal totalFullPrice = new BigDecimal(0);
				String meet_conditions = "0.00";
				
				for (int i = 0; i < listOrderProducts.size(); i++) {
					PageData postagePd = new PageData();
					postagePd.put("productId", listOrderProducts.get(i).getProduct_id());
					PostageVo postageVo = postageManager.queryOne(postagePd);
					if (postageVo != null) {
						if(StringUtil.isNotNull(listOrderProducts.get(i).getActivity_price())){
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getActivity_price()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}else if(StringUtil.isNotNull(listOrderProducts.get(i).getDiscount_price())){
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getDiscount_price()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}else {
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getPrice()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}
						meet_conditions = postageVo.getMeetConditions();
						postage = postageVo.getPostage();
					}
				}
				if (totalFullPrice.floatValue() < Float.valueOf(meet_conditions)) {
					payPrice=payPrice.add(new BigDecimal(postage));
					logger.info("商品实际payPrice加运费----------------"+payPrice);
				}else{
					postage = "0.00";
				}
			}
		}
		
		//锁定优惠券或者礼品卡实体类
		UseLockVo useLockInfo=null;
		
		//优惠券
		CouponInfoVo couponInfo=null;
		if(StringUtil.isNotNull(useCouponId)){         
			pagedata.put("canusecoupon","0");
			pagedata.put("couponId",useCouponId);
			couponInfo=couponInfoManager.findUserCoupon(pagedata);
			
			if(null==couponInfo ){
				json.put("result", "failure");
				json.put("msg", "无效的优惠卷");
				return json.toString();
			}else{
				BigDecimal startMoney=new BigDecimal(couponInfo.getStart_money());
				if(startMoney.compareTo(price)==1){
					json.put("result", "failure");
					json.put("msg", "非法使用的优惠卷");
					return json.toString();
				}
				
				PageData pd2 = new PageData();
				pd2.put("couponId", useCouponId);
				pd2.put("customerId", String.valueOf(customer.getCustomer_id()));
				pd2.put("lockStatus","1");
				UseLockVo lockCoupon=useLockManager.findUseLock(pd2);     //根据用户ID ,优惠券Id,锁定状态（已锁定）  查询优惠券是否被使用
				
				if(null !=lockCoupon && couponInfo.getCoupon_id()==lockCoupon.getUse_coupon_id()){
					json.put("result", "failure");
					json.put("msg", "优惠券已在订单编号："+lockCoupon.getOrder_code()+"使用!");
					return json.toString();
				}else{
					
					//用户礼品卡 锁定实体类
					useLockInfo=new UseLockVo();
					useLockInfo.setOrder_code(orderCode);
					useLockInfo.setCustomer_id(customer.getCustomer_id());
					useLockInfo.setUse_coupon_id(Integer.parseInt(useCouponId));
					useLockInfo.setLock_status(1);      //已使用 （使用完）
					useLockManager.insertUseLock(useLockInfo);
					logger.info("customerId为"+customer.getCustomer_id()+"使用了优惠券Use_coupon_id--"+useCouponId);
				}
				
				payPrice=payPrice.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}
		}
		
		logger.info("商品实际payPrice减去了优惠活动券---------------"+payPrice);
		
		/**
		 * 使用的礼品卡
		 * 
		 * 说明:
		 *   1 .查看用户礼品卡可用总余额
		 *   2 .如果礼品卡余额大于或者等于支付就直接返回支付成功
		 *   2. 如果礼品卡余额小于支付就减去在去支付
		 * 
		 */
		String giftCardNos="";
		BigDecimal useGiftMoney=new BigDecimal(0);
		if("useGiftCard".equals(giftcardId)){
			
			PageData pd2 = new PageData();
			pd2.put("customerId", String.valueOf(customer.getCustomer_id()));
			pd2.put("lockStatus","1");
			List<UseLockVo> lockGiftCards=useLockManager.findListUseLock(pd2);     //根据用户ID ,优惠券Id,锁定状态（已锁定  使用完）  查询优惠券是否被使用
			
			BigDecimal canUseTotalMoney=giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
			BigDecimal useLockMoney=new BigDecimal(0);
			
			if(null!=lockGiftCards && lockGiftCards.size()>0){
				for (int i = 0; i < lockGiftCards.size(); i++) {
					if(null!=lockGiftCards.get(i).getUse_giftcard_no()){
						//累加所有已使用的礼品卡金额
						useLockMoney=useLockMoney.add(new BigDecimal(lockGiftCards.get(i).getUse_card_money()));
					}
				}
			}
			
			if(useLockMoney.intValue()>0){
				canUseTotalMoney=canUseTotalMoney.subtract(useLockMoney);
			}
			
			if(null!=canUseTotalMoney && canUseTotalMoney.compareTo(new BigDecimal(0))>0){
				PageData pds=new PageData();
				pds.put("status","1");
				pds.put("customerId",String.valueOf(customer.getCustomer_id()));
				List<GiftCardVo> listGiftCards=giftCardManager.findListGiftCard(pds);
				
				//去除已锁定的礼品卡
				if(null!=listGiftCards && listGiftCards.size()>0){
					int cardCount=listGiftCards.size();
					for (int i = 0; i <cardCount ; i++) {
						if(null!=lockGiftCards && lockGiftCards.size()>0){
							for (int j = 0; j < lockGiftCards.size(); j++) {
								if(listGiftCards.get(i).getCard_no().equals(lockGiftCards.get(j).getUse_giftcard_no())){
									listGiftCards.remove(i);
									cardCount=listGiftCards.size();
								}
							}
						}else{
							break;
						}
					}
					
				}
				if(null!=listGiftCards && listGiftCards.size()>0){
					pds.put("status","5");   //未使用完
					List<GiftCardVo> listnNotALlGiftCards=giftCardManager.findListGiftCard(pds);
					if(null!=listnNotALlGiftCards && listnNotALlGiftCards.size()>0 ){
						
						for (int j = 0; j <listGiftCards.size() ; j++) {
							for (int i = 0; i < listnNotALlGiftCards.size(); i++) {
								if(listGiftCards.get(j).getCard_no().equals(listnNotALlGiftCards.get(i).getCard_no())){
									BigDecimal useMoney=new BigDecimal(listnNotALlGiftCards.get(i).getCard_money());
									if(null!=listGiftCards.get(j).getCard_money() && !"".equals(listGiftCards.get(j).getCard_money())){
										useMoney=useMoney.add(new BigDecimal(listGiftCards.get(j).getCard_money()));
									}
									listGiftCards.get(j).setCard_use(String.valueOf(useMoney));
								}
							}
						}	
					}
				}
				
				
				if(null!=listGiftCards && listGiftCards.size()>0){
					
					for (int i = 0; i < listGiftCards.size(); i++) {
						 //礼品卡可使用金额
						BigDecimal leftMomey=null;
						if(null!=listGiftCards.get(i).getCard_use() && !"".equals(listGiftCards.get(i).getCard_use())){
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money()).subtract(new BigDecimal(listGiftCards.get(i).getCard_use()));
						}else{
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money());
						}
						
						//先判 支付总价 - 礼品卡可用金额  >=0  直接锁定
						if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO) >=0 ){
							payPrice=payPrice.subtract(leftMomey);
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLockVo();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(leftMomey));
							useLockInfo.setLock_status(1);      //已使用 （使用完）
							useLockManager.insertUseLock(useLockInfo);
							
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");
							useGiftMoney=useGiftMoney.add(leftMomey);
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							
					    //否则, 未使用完  5   在退出循环 	
						}else if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO)==-1){
				//			BigDecimal useMoney=leftMomey.subtract(payPrice);
							
							useGiftMoney=useGiftMoney.add(payPrice);
							
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLockVo();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(payPrice));
							useLockInfo.setLock_status(5);      //未使用完
							useLockManager.insertUseLock(useLockInfo);
							payPrice=BigDecimal.ZERO;
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");

							break;
						}
					}
					
				}else{
					json.put("result", "failure");
					json.put("msg", "礼品卡余额已被锁定");
					return json.toString();
				}
			}else{
				json.put("result", "failure");
				json.put("msg", "礼品卡余额为空");
				return json.toString();
			}
		}
		
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
			if(giftCardNos.lastIndexOf(",")!=-1){
				giftCardNos=giftCardNos.substring(0,giftCardNos.lastIndexOf(","));
			}
		}
		
		OrderVo orderVo=new OrderVo();
		orderVo.setOrder_code(orderCode);  						 //订单编号
		orderVo.setCustomer_id(customer.getCustomer_id());       //客户
		
		orderVo.setOrder_type(1);   							//订单类型；1：线上订单，2：线下订单
		orderVo.setReceiver(receiverAddressVo.getReceiverName());   //收货人
		orderVo.setMobile(receiverAddressVo.getMobile());           //手机号码
		orderVo.setAddress(receiverAddressVo.getAddress());
		orderVo.setFreight(postage);                //运费
		
		if(null !=useCouponId && !"".equals(useCouponId)){          //优惠券
			orderVo.setUse_coupon(Integer.parseInt(useCouponId));
			orderVo.setConpon_money(String.valueOf(couponInfo.getCoupon_money()));
		}
		
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡       
			orderVo.setUse_gift_card(giftCardNos);
			orderVo.setGitf_card_money(String.valueOf(useGiftMoney));
		}
		
		orderVo.setMessage_info(messageInfo);//留言
		
		orderVo.setPay_type(Integer.parseInt(payType));         ///支付方式，1：微信；2：支付宝   3:一卡通   4 ：礼品卡
		orderVo.setTotal_price(String.valueOf(price));        //订单总金额
		boolean canSingleCoupon=false;   //单品红包是否可用
		if(payPrice.floatValue()<0){
			payPrice=new BigDecimal(0);
		}
		UserSingleCouponVo uscVo = new UserSingleCouponVo();
		if(StringUtils.isNotBlank(usesinglecouponId)&&StringUtils.isBlank(giftcardId)){
			PageData singlePd = new PageData();
			singlePd.put("startTime", new Date());
			singlePd.put("endTime", new Date());
			singlePd.put("status", 0);
			singlePd.put("userSingleId", usesinglecouponId);
			uscVo = userSingleCouponManager.queryOne(singlePd);
			if(uscVo!=null){  //查询到对应单品红包，满足条件则抵扣
					
					if(listOrderProducts!=null&&listOrderProducts.size()>0){
						for(OrderProductVo vo:listOrderProducts){
							String totalPrice=vo.getPrice();
//							if(StringUtil.isNotNull(vo.getActivity_price())){
//								totalPrice=vo.getActivity_price();
//							}
							BigDecimal totalProce2=new BigDecimal(totalPrice).multiply(new BigDecimal(vo.getQuantity()));
							if(uscVo.getProductId().intValue()==vo.getProduct_id().intValue()
									&&	totalProce2.compareTo(uscVo.getFullMoney())>=0 
									&& (orderVo.getCustomer_id().intValue()==uscVo.getCustomerId().intValue())){
								payPrice = payPrice.subtract(uscVo.getCouponMoney());
								orderVo.setUse_single_coupon_id(Integer.parseInt(usesinglecouponId));
								vo.setUser_single_coupon_id(Integer.parseInt(usesinglecouponId));
								vo.setCoupon_money(uscVo.getCouponMoney());
								canSingleCoupon=true;
								break;
							}
						}
					}
				
				
			}
		}
		
		BigDecimal custCurrentIntegral = BigDecimal.ZERO;
		if(customer.getCurrentIntegral() != null){
			custCurrentIntegral = customer.getCurrentIntegral();
		}
		
		//金币优惠购
		ProductRedeemSpecVo redeemSpecVo = new ProductRedeemSpecVo();
		if(StringUtil.isNotNull(redeemSpecId)
				&& Long.parseLong(redeemSpecId) > 0 && payPrice.compareTo(BigDecimal.ZERO) == 1){
			PageData rdData = new PageData();
			rdData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
			rdData.put("productId", Integer.parseInt(productIds));
			rdData.put("specId", Integer.parseInt(specIds));
			rdData.put("redeemSpecId", redeemSpecId);
			redeemSpecVo = this.productRedeemSpecManager.queryOne(rdData);
			
			if(redeemSpecVo != null){
				BigDecimal calculateMoney = redeemSpecVo.getDeductibleAmount();
				BigDecimal calculateIntegral = redeemSpecVo.getNeedIntegral();
				if(custCurrentIntegral.compareTo(calculateIntegral) >= 0){
					custCurrentIntegral = custCurrentIntegral.subtract(calculateIntegral);
					orderVo.setTotalRedeemMoney(calculateMoney);
					orderVo.setTotalRedeemIntegral(calculateIntegral);
					payPrice = payPrice.subtract(calculateMoney);
				}
			}
		}
		
		BigDecimal totalGoldMoney = BigDecimal.ZERO;
		BigDecimal totalGoldIntegral = BigDecimal.ZERO;
		
		if(!listOrderProducts.isEmpty() && listOrderProducts.size()>0){
			for (int ii = 0; ii < listOrderProducts.size(); ii++) {
				//普通金币抵扣
				ProductRedeemSpecVo goldSpecVo = new ProductRedeemSpecVo();
				if(StringUtil.isNotNull(goldDeductSpecId)
						&& Long.parseLong(goldDeductSpecId) > 0 && payPrice.compareTo(BigDecimal.ZERO) == 1
						&& custCurrentIntegral.compareTo(new BigDecimal(IntegralConstants.MIN_USE_THRESHOLD)) >= 0){
					PageData rdData = new PageData();
					rdData.put("redeemType", IntegralConstants.PRODUCT_REDEEM_TYPE_ZERO);
					rdData.put("productId", Integer.parseInt(productIds));
					rdData.put("specId", Integer.parseInt(specIds));
					rdData.put("redeemSpecId", goldDeductSpecId);
					goldSpecVo = this.productRedeemSpecManager.queryOne(rdData);
					if(goldSpecVo != null){
						BigDecimal calculateIntegral = payPrice.divide(new BigDecimal(IntegralConstants.DEDUCTION_RATE), 2, BigDecimal.ROUND_HALF_UP);
						if(calculateIntegral.compareTo(new BigDecimal(IntegralConstants.MAX_USE_THRESHOLD)) >= 0) { //超出最高使用上限则按上限算
							calculateIntegral = new BigDecimal(IntegralConstants.MAX_USE_THRESHOLD);
						}
						
						if(calculateIntegral.compareTo(custCurrentIntegral) >= 0){ //当前支付金额需要的金币数大于等于当前金币数
							BigDecimal calculateMoney = custCurrentIntegral.multiply(new BigDecimal(IntegralConstants.DEDUCTION_RATE));
							payPrice = payPrice.subtract(calculateMoney);
							listOrderProducts.get(ii).setGoldIntegral(custCurrentIntegral);
							listOrderProducts.get(ii).setGoldMoney(calculateMoney);
							listOrderProducts.get(ii).setGoldSpecId(goldSpecVo.getRedeemSpecId());
							totalGoldMoney = totalGoldMoney.add(calculateMoney);
							totalGoldIntegral = totalGoldIntegral.add(custCurrentIntegral);
						}else{
							BigDecimal hasIntegral = custCurrentIntegral.subtract(calculateIntegral);
							BigDecimal calculateMoney = payPrice;
							payPrice = payPrice.subtract(calculateMoney);
							BigDecimal goldIntegral = custCurrentIntegral.subtract(hasIntegral);
							custCurrentIntegral = hasIntegral;
							listOrderProducts.get(ii).setGoldIntegral(goldIntegral);
							listOrderProducts.get(ii).setGoldMoney(calculateMoney);
							listOrderProducts.get(ii).setGoldSpecId(goldSpecVo.getRedeemSpecId());
							totalGoldMoney = totalGoldMoney.add(calculateMoney);
							totalGoldIntegral = totalGoldIntegral.add(goldIntegral);
						}
						
					}
				}
			}
		}
		
		if(totalGoldMoney.compareTo(BigDecimal.ZERO) == 1) {
			orderVo.setTotalGoldMoney(totalGoldMoney);
		}
		
		if(totalGoldIntegral.compareTo(BigDecimal.ZERO) == 1) {
			orderVo.setTotalRedeemIntegral(totalGoldIntegral);
		}
		
		logger.info("customerId为"+customer.getCustomer_id()+"订单号为orderVoCode--"+orderVo.getOrder_code()+"商品总价为---"
		+orderVo.getTotal_price()+"-------商品实际支付payPrice--"+payPrice);
		
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		orderVo.setPay_money(String.valueOf(payPrice));       //实付金额
		//(List<OrderProductVo>) map.get("listOrderProducts");
		
		orderVo.setOrderSource(orderSource);
		//插入订单记录
		orderManager.insertOrder(orderVo);
		
		//把订单ID添加进入订单商品 且 进行增加操作
		if(!listOrderProducts.isEmpty() && listOrderProducts.size()>0){
			PageData pspd = new PageData();
			for (int i = 0; i < listOrderProducts.size(); i++) {
				listOrderProducts.get(i).setOrder_id(orderVo.getOrder_id());
				listOrderProducts.get(i).setSub_order_code(generateOrderCode());
				listOrderProducts.get(i).setStatus(1); //待支付
				if(uscVo!=null){//证明是单品活动
					if(listOrderProducts.get(i).getProduct_id().equals(uscVo.getProductId())
							&&listOrderProducts.get(i).getSpec_id().equals(uscVo.getSpecId())&&orderVo.getCustomer_id().equals(uscVo.getCustomerId())){
						listOrderProducts.get(i).setUser_single_coupon_id(uscVo.getUserSingleId());
						listOrderProducts.get(i).setCoupon_money(uscVo.getCouponMoney());
					}
				}
				
				pspd.put("specId", listOrderProducts.get(i).getSpec_id());
				ProductSpecVo pspec = this.productSpecManager.queryProductSpecByOption(pspd);
				if(pspec != null){
					int stockCopy = pspec.getStock_copy() - listOrderProducts.get(i).getQuantity();
					if(stockCopy < 0){
						stockCopy = 0;
					}
					ProductSpecVo pps = new ProductSpecVo();
					pps.setSpec_id(pspec.getSpec_id());
					pps.setStock_copy(stockCopy);
					productSpecManager.updateProductSpecBySid(pps);
				}
			}
			
			orderProductManager.addBatchOrderProduct(listOrderProducts);
		}
		
		
		/**
		 * 从购物车中结算商品，下单后需要删除购物车 
		 * 2017-2-10  by chen
		 */
		if(null!=cartIds && !"".equals(cartIds)){
			PageData pds=new PageData();
			pds.put("customerId", String.valueOf(customer.getCustomer_id()));
			pds.put("cartId",cartIds);
			shoppingCartManager.deleteShoppingCartByCidAndCid(pds);
		}
		
		try {
			logger.info("记录支付日志: startting... ");
			
			//记录支付日志
			PayLogVo payLogInfo=new PayLogVo();
			payLogInfo.setOrder_id(orderVo.getOrder_id());
			payLogInfo.setOrder_code(orderCode);
			payLogInfo.setCustomer_id(customer.getCustomer_id());
			payLogInfo.setPay_type(Integer.parseInt(payType));
			payLogInfo.setPay_money(orderVo.getPay_money());
			
			if(null !=useCouponId && !"".equals(useCouponId)){          //优惠券
				payLogInfo.setCoupon_id(Integer.parseInt(useCouponId));
				payLogInfo.setConpon_money(String.valueOf(couponInfo.getCoupon_money()));
			}
			
			if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
				payLogInfo.setGift_card_no(giftCardNos);
				payLogInfo.setGitf_card_money(String.valueOf(useGiftMoney));
			}
			
			//是否可用单品红包
			if(canSingleCoupon){
				payLogInfo.setUse_single_coupon_id(Integer.parseInt(usesinglecouponId));
			}
			
			payLogManager.insertPayLog(payLogInfo);
			
			logger.info("记录支付日志: 完成... ");
		} catch (Exception e1) {
			logger.error("记录支付日志: 异常... ", e1);
		}
		
		
		//如果支付的钱为0 ,就直接调用支付成功后 的方法
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		if(payPrice.compareTo(BigDecimal.ZERO)==0){
			logger.info("支付金额==0时，不走微信支付------------------------------------------------------------");
			//1.修改订单状态
			//2.修改优惠券状态
			//3.修改用户礼品卡和优惠券的锁定状态
			try {
				//如果礼品卡金额足够扣钱，则支付方式直接是礼品卡支付
				if(!"4".equals(payType)){
					payType = "1";
				}
				notifyUpdateAboutOrder(request,response, orderCode,"", payType,customer);
				json.put("result", "succ");
				json.put("orderCode", orderCode);
				json.put("notify", "succ");	//标识前端不走微信支付
			} catch (Exception e) {
				logger.error("使用礼品卡或者优惠券后,订单应支付总价为0,调用支付成功后的方法出现异常", e);
			}
			//1是微信
		}else{
			
			String payMoney = orderVo.getPay_money();
			// 用户登录开关，在本地开发环境可关闭此开关即：false
			if (!"true".equals(WebConstant.ISMEMBER)) {
				payMoney = "0.01";
			}
			String openId = customer.getOpenid();
			if("1".equals(payType)){
				logger.info("openId------"+openId+"-----支付金额>=0时，微信支付----------------------------------------------------"+payPrice);
				try {
					// 配置微信支付参数
					Map<String, String> payParam = WxUtil.configPayParam(request, Float.parseFloat(payMoney), orderCode, openId, "挥货-购买商品",WebConstant.WX_PAY_NOTIFY);
					json.put("result", "succ");
					json.put("orderCode", orderCode);
					json.put("tradeNo", orderCode);
					json.put("orderId", orderVo.getOrder_id());
					json.put("payData", payParam);
					TokenProcessor.resetToken(request);  //清空token
				} catch (NumberFormatException e) {
					json.put("result", "failure");
					json.put("msg","系统异常");
					logger.error("/weixin/payment/pay --error", e);
				} catch (Exception e) {
					json.put("result", "failure");
					json.put("msg","系统异常");
					logger.error("/weixin/payment/pay --error", e);
				}
				
			}else if("2".equals(payType) || "3".equals(payType)){
				json.put("orderCode", orderVo.getOrder_code());
				json.put("result", "succ");
				TokenProcessor.resetToken(request);  //清空token
			}
			logger.info(json.toString());
		}
		
		return json.toString();
	}
	
	/**
	 * 微信团购下单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/payByGroup",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public String payByGroup(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		if (StringUtil.isNull(pd.getString("token")) ||
				!TokenProcessor.isTokenvalid(request)) { //判断提交表单token是否为空
			//如果token为空，那么该页面不是通过正常渠道进入或者已经提交过，token被清空
			json.put("result", "failure");
			json.put("msg", "该订单已经提交，请到个人中心查看该订单详情");
			return json.toString();
		}
		
		
		String productIds=pd.getString("productIds");        //购买的商品Id
		String specIds=pd.getString("specIds");              //规格Id               
		String amounts=pd.getString("amounts");              //购买数量
		
		String receiveId=pd.getString("receiverId");         //收货地址 ID
		String payType=pd.getString("payType");     		   //支付类型
		String giftcardId=pd.getString("giftcardId");          //购物卡ID  使用礼品卡   useGiftCard
		String specialId=pd.getString("specialId");	//活动ID
		String userGroupId=pd.getString("userGroupId");	    //开团ID
		String orderSource=pd.getString("orderSource");
		String messageInfo = pd.getString("message_info");  //留言
		String orderCode=generateOrderCode();
		CustomerVo customer=Constants.getCustomer(request);
		if (customer == null || customer.getCustomer_id() == null) {
			json.put("result", "failure");
			json.put("msg", "用户信息为空");
			return json.toString();
		}
		
		CustomerSourceUtil.removeOrderSource(request);
		
		logger.info("Customer用户id为"+customer.getCustomer_id()+"--进入了微信下单------");
		
		
		if(productIds==null||"".equals(productIds) || specIds==null||"".equals(specIds)
				 || amounts==null||"".equals(amounts)){
			json.put("result", "failure");
			json.put("msg", "productId为空");
			return json.toString();
		}
		
		String[] productIdArray=productIds.split(",");
		String[] specIdArray=specIds.split(",");
		String[] amountArray=amounts.split(",");
		
		PageData spepd = new PageData();
		spepd.put("specialId", specialId);
		spepd.put("productId", productIdArray[0]);
		spepd.put("specialType", 3);
		spepd.put("status", 1);
		spepd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		spepd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		SpecialVo specialVo = this.specialMananger.queryOneByProductId(spepd);
		if(specialVo == null){
			json.put("msg","团购活动已下架");
			return json.toString();
		}
		spepd.put("specialId", specialId);
		SysGroupVo sysgroup = sysGroupManager.queryOne(spepd);
		if(sysgroup == null){
			json.put("msg","团购活动已下架");
			return json.toString();
		}
		
		UserGroupVo usergroup = null;
		if(StringUtil.isNoNull(userGroupId) && Integer.parseInt(userGroupId) > 0){
			spepd.put("userGroupId", userGroupId);
			usergroup = this.userGroupManager.queryOne(spepd);
			if(usergroup == null || usergroup.getStatus() != 1){
				json.put("msg","此团已结束拼单");
				return json.toString();
			}
			if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
				json.put("msg","此团已完成拼单");
				return json.toString();
			}
		}else{ //开团
			if(sysgroup.getJoinCopy() >= sysgroup.getGroupCopy()){
				json.put("msg","可开团数量已达到上限");
				return json.toString();
			}
		}
		
		BigDecimal price=null;
		BigDecimal payPrice=null;
		
		//查询订单商品存进订单商品集合
		List<OrderProductVo> listOrderProducts=null;
		//获取订单商品金额
		Map<String,Object> map=getOrderPrice(productIdArray,specIdArray,amountArray,customer, 0, specialVo.getSpecialId());
		
		if("not".equals(map.get("exist"))){
			json.put("result", "notexist");
			json.put("msg", "商品已下架，请重新下单");
			return json.toString();
		}
		if(map.get("noStockCopy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("noStockCopy"));
			return json.toString();
		}
		if(map.get("CannotBuy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("CannotBuy"));
			return json.toString();
		}
		price=(BigDecimal) map.get("price");
		
		logger.info("商品总价price----------------"+price);
		payPrice=(BigDecimal)map.get("payPrice");
		listOrderProducts=(List<OrderProductVo>) map.get("listOrderProducts");
		
		PageData pagedata=new PageData();
		pagedata.put("customerId", String.valueOf(customer.getCustomer_id()));
		
		//收货地址   查询运费
		pagedata.put("receiverId",receiveId);
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.selectReceiverAddressByOption(pagedata);
		String postage = "0.00";
		
		if(null == receiverAddressVo){
			json.put("result", "failure");
			json.put("msg", "无效的收货地址");
			return json.toString();
		}else{
			if(null != receiverAddressVo){
				//Map<String, Object> freightMap=freightManager.getFreightByAreaCode(String.valueOf(receiverAddressVo.getProvinceId()),String.valueOf(receiverAddressVo.getCityId()));
				
				BigDecimal totalFullPrice = new BigDecimal(0);
				String meet_conditions = "0.00";
				
				for (int i = 0; i < listOrderProducts.size(); i++) {
					PageData postagePd = new PageData();
					postagePd.put("productId", listOrderProducts.get(i).getProduct_id());
					PostageVo postageVo = postageManager.queryOne(postagePd);
					if (postageVo != null) {
						if(StringUtil.isNotNull(listOrderProducts.get(i).getActivity_price())){
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getActivity_price()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}else if(StringUtil.isNotNull(listOrderProducts.get(i).getDiscount_price())){
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getDiscount_price()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}else {
							totalFullPrice = totalFullPrice.add(new BigDecimal(listOrderProducts.get(i).getPrice()).multiply(new BigDecimal(listOrderProducts.get(i).getQuantity())));
						}
						meet_conditions = postageVo.getMeetConditions();
						postage = postageVo.getPostage();
					}
				}
				if (totalFullPrice.floatValue() < Float.valueOf(meet_conditions)) {
					payPrice=payPrice.add(new BigDecimal(postage));
					logger.info("商品实际payPrice加运费----------------"+payPrice);
				}
			}
		}
		
		//锁定优惠券或者礼品卡实体类
		UseLockVo useLockInfo=null;
		
		/**
		 * 使用的礼品卡
		 * 
		 * 说明:
		 *   1 .查看用户礼品卡可用总余额
		 *   2 .如果礼品卡余额大于或者等于支付就直接返回支付成功
		 *   2. 如果礼品卡余额小于支付就减去在去支付
		 * 
		 */
		String giftCardNos="";
		BigDecimal useGiftMoney=new BigDecimal(0);
		if("useGiftCard".equals(giftcardId)){
			
			PageData pd2 = new PageData();
			pd2.put("customerId", String.valueOf(customer.getCustomer_id()));
			pd2.put("lockStatus","1");
			List<UseLockVo> lockGiftCards=useLockManager.findListUseLock(pd2);     //根据用户ID ,优惠券Id,锁定状态（已锁定  使用完）  查询优惠券是否被使用
			
			BigDecimal canUseTotalMoney=giftCardManager.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
			BigDecimal useLockMoney=new BigDecimal(0);
			
			if(null!=lockGiftCards && lockGiftCards.size()>0){
				for (int i = 0; i < lockGiftCards.size(); i++) {
					if(null!=lockGiftCards.get(i).getUse_giftcard_no()){
						//累加所有已使用的礼品卡金额
						useLockMoney=useLockMoney.add(new BigDecimal(lockGiftCards.get(i).getUse_card_money()));
					}
				}
			}
			
			if(useLockMoney.intValue()>0){
				canUseTotalMoney=canUseTotalMoney.subtract(useLockMoney);
			}
			
			if(null!=canUseTotalMoney && canUseTotalMoney.compareTo(new BigDecimal(0))>0){
				PageData pds=new PageData();
				pds.put("status","1");
				pds.put("customerId",String.valueOf(customer.getCustomer_id()));
				List<GiftCardVo> listGiftCards=giftCardManager.findListGiftCard(pds);
				
				//去除已锁定的礼品卡
				if(null!=listGiftCards && listGiftCards.size()>0){
					int cardCount=listGiftCards.size();
					for (int i = 0; i <cardCount ; i++) {
						if(null!=lockGiftCards && lockGiftCards.size()>0){
							for (int j = 0; j < lockGiftCards.size(); j++) {
								if(listGiftCards.get(i).getCard_no().equals(lockGiftCards.get(j).getUse_giftcard_no())){
									listGiftCards.remove(i);
									cardCount=listGiftCards.size();
								}
							}
						}else{
							break;
						}
					}
					
				}
				if(null!=listGiftCards && listGiftCards.size()>0){
					pds.put("status","5");   //未使用完
					List<GiftCardVo> listnNotALlGiftCards=giftCardManager.findListGiftCard(pds);
					if(null!=listnNotALlGiftCards && listnNotALlGiftCards.size()>0 ){
						
						for (int j = 0; j <listGiftCards.size() ; j++) {
							for (int i = 0; i < listnNotALlGiftCards.size(); i++) {
								if(listGiftCards.get(j).getCard_no().equals(listnNotALlGiftCards.get(i).getCard_no())){
									BigDecimal useMoney=new BigDecimal(listnNotALlGiftCards.get(i).getCard_money());
									if(null!=listGiftCards.get(j).getCard_money() && !"".equals(listGiftCards.get(j).getCard_money())){
										useMoney=useMoney.add(new BigDecimal(listGiftCards.get(j).getCard_money()));
									}
									listGiftCards.get(j).setCard_use(String.valueOf(useMoney));
								}
							}
						}	
					}
				}
				
				
				if(null!=listGiftCards && listGiftCards.size()>0){
					
					for (int i = 0; i < listGiftCards.size(); i++) {
						 //礼品卡可使用金额
						BigDecimal leftMomey=null;
						if(null!=listGiftCards.get(i).getCard_use() && !"".equals(listGiftCards.get(i).getCard_use())){
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money()).subtract(new BigDecimal(listGiftCards.get(i).getCard_use()));
						}else{
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money());
						}
						
						//先判 支付总价 - 礼品卡可用金额  >=0  直接锁定
						if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO) >=0 ){
							payPrice=payPrice.subtract(leftMomey);
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLockVo();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(leftMomey));
							useLockInfo.setLock_status(1);      //已使用 （使用完）
							useLockManager.insertUseLock(useLockInfo);
							
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");
							useGiftMoney=useGiftMoney.add(leftMomey);
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							
					    //否则, 未使用完  5   在退出循环 	
						}else if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO)==-1){
				//			BigDecimal useMoney=leftMomey.subtract(payPrice);
							
							useGiftMoney=useGiftMoney.add(payPrice);
							
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLockVo();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(payPrice));
							useLockInfo.setLock_status(5);      //未使用完
							useLockManager.insertUseLock(useLockInfo);
							payPrice=BigDecimal.ZERO;
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");

							break;
						}
					}
					
				}else{
					json.put("result", "failure");
					json.put("msg", "礼品卡余额已被锁定");
					return json.toString();
				}
			}else{
				json.put("result", "failure");
				json.put("msg", "礼品卡余额为空");
				return json.toString();
			}
		}
		
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
			if(giftCardNos.lastIndexOf(",")!=-1){
				giftCardNos=giftCardNos.substring(0,giftCardNos.lastIndexOf(","));
			}
		}
		
		OrderVo orderVo=new OrderVo();
		orderVo.setOrder_code(orderCode);  						 //订单编号
		orderVo.setCustomer_id(customer.getCustomer_id());       //客户
		
		orderVo.setOrder_type(1);   							//订单类型；1：线上订单，2：线下订单
		orderVo.setReceiver(receiverAddressVo.getReceiverName());   //收货人
		orderVo.setMobile(receiverAddressVo.getMobile());           //手机号码
		orderVo.setAddress(receiverAddressVo.getAddress());
		orderVo.setFreight(postage);                //运费
		orderVo.setMessage_info(messageInfo);       //留言
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡       
			orderVo.setUse_gift_card(giftCardNos);
			orderVo.setGitf_card_money(String.valueOf(useGiftMoney));
		}
		
		orderVo.setPay_type(Integer.parseInt(payType));         ///支付方式，1：微信；2：支付宝    微信平台都是 1 
		orderVo.setTotal_price(String.valueOf(price));        //订单总金额
		boolean canSingleCoupon=false;   //单品红包是否可用
		if(payPrice.floatValue()<0){
			payPrice=new BigDecimal(0);
		}
		
		logger.info("customerId为"+customer.getCustomer_id()+"订单号为orderVoCode--"+orderVo.getOrder_code()+"商品总价为---"
		+orderVo.getTotal_price()+"-------商品实际支付payPrice--"+payPrice);
		
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		orderVo.setPay_money(String.valueOf(payPrice));       //实付金额
		//(List<OrderProductVo>) map.get("listOrderProducts");
		
		GroupJoinVo groupJoin = null;
		if(usergroup == null || StringUtil.isNull(userGroupId)){ //开团
			usergroup = new UserGroupVo();
			usergroup.setOriginator(customer.getCustomer_id());
			usergroup.setProductId(Integer.parseInt(productIdArray[0]));
			usergroup.setStatus(0);
			usergroup.setSpecId(Integer.parseInt(specIdArray[0]));
			usergroup.setGroupId(sysgroup.getGroupId());
			usergroup.setSpecialId(sysgroup.getSpecialId());
			this.userGroupManager.insertUserGroup(usergroup);
			groupJoin = new GroupJoinVo();
			groupJoin.setBuyNum(Integer.parseInt(amountArray[0]));
			groupJoin.setCustomerId(customer.getCustomer_id());
			groupJoin.setOriginator(usergroup.getOriginator());
			groupJoin.setProductId(Integer.parseInt(productIdArray[0]));
			groupJoin.setStatus(0);
			groupJoin.setSpecId(Integer.parseInt(specIdArray[0]));
			groupJoin.setGroupId(sysgroup.getGroupId());
			groupJoin.setUserGroupId(usergroup.getUserGroupId());
			groupJoin.setSpecialId(sysgroup.getSpecialId());
			this.groupJoinManager.insertGroupJoin(groupJoin);
		}else if(StringUtil.isNotNull(userGroupId)){ //参团
			groupJoin = new GroupJoinVo();
			groupJoin.setBuyNum(Integer.parseInt(amountArray[0]));
			groupJoin.setCustomerId(customer.getCustomer_id());
			groupJoin.setOriginator(usergroup.getOriginator());
			groupJoin.setProductId(Integer.parseInt(productIdArray[0]));
			groupJoin.setStatus(0);
			groupJoin.setSpecId(Integer.parseInt(specIdArray[0]));
			groupJoin.setGroupId(sysgroup.getGroupId());
			groupJoin.setUserGroupId(usergroup.getUserGroupId());
			groupJoin.setSpecialId(sysgroup.getSpecialId());
			this.groupJoinManager.insertGroupJoin(groupJoin);
		}else{
			json.put("result", "failure");
			json.put("msg", "团购失败");
			return json.toString();
		}
		
		orderVo.setGroupJoinId(groupJoin.getGroupJoinId());
		orderVo.setOrderSource(orderSource);
		orderManager.insertOrder(orderVo);
		
		//把订单ID添加进入订单商品 且 进行增加操作
		if(!listOrderProducts.isEmpty() && listOrderProducts.size()>0){
			PageData pspd = new PageData();
			for (int i = 0; i < listOrderProducts.size(); i++) {
				listOrderProducts.get(i).setOrder_id(orderVo.getOrder_id());
				listOrderProducts.get(i).setSub_order_code(generateOrderCode());
				listOrderProducts.get(i).setStatus(1); //待支付
				
				pspd.put("specId", listOrderProducts.get(i).getSpec_id());
				ProductSpecVo pspec = this.productSpecManager.queryProductSpecByOption(pspd);
				if(pspec != null){
					int stockCopy = pspec.getStock_copy() - listOrderProducts.get(i).getQuantity();
					if(stockCopy < 0){
						stockCopy = 0;
					}
					ProductSpecVo pps = new ProductSpecVo();
					pps.setSpec_id(pspec.getSpec_id());
					pps.setStock_copy(stockCopy);
					productSpecManager.updateProductSpecBySid(pps);
				}
			}
			
			orderProductManager.addBatchOrderProduct(listOrderProducts);
		}
		
		
		try {
			logger.info("记录支付日志: startting... ");
			
			//记录支付日志
			PayLogVo payLogInfo=new PayLogVo();
			payLogInfo.setOrder_id(orderVo.getOrder_id());
			payLogInfo.setOrder_code(orderCode);
			payLogInfo.setCustomer_id(customer.getCustomer_id());
			payLogInfo.setPay_type(Integer.parseInt(payType));
			payLogInfo.setPay_money(orderVo.getPay_money());
			
			if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
				payLogInfo.setGift_card_no(giftCardNos);
				payLogInfo.setGitf_card_money(String.valueOf(useGiftMoney));
			}
			
			payLogManager.insertPayLog(payLogInfo);
			
			logger.info("记录支付日志: 完成... ");
		} catch (Exception e1) {
			logger.error("记录支付日志: 异常... ", e1);
		}
		
		//如果支付的钱为0 ,就直接调用支付成功后 的方法
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		if(payPrice.compareTo(BigDecimal.ZERO)==0){
			logger.info("支付金额==0时，不走微信支付------------------------------------------------------------");
			//1.修改订单状态
			//2.修改优惠券状态
			//3.修改用户礼品卡和优惠券的锁定状态
			try {
				
				if(!"4".equals(payType)){
					payType = "1";
				}
				notifyUpdateAboutOrder(request,response, orderCode,"", payType, customer);
				json.put("result", "succ");
				json.put("orderCode", orderCode);
				json.put("notify", "succ");
			} catch (Exception e) {
				logger.error("使用礼品卡或者优惠券后,订单应支付总价为0,调用支付成功后的方法出现异常", e);
			}
			
		}else{
			String openId = customer.getOpenid();
			if("1".equals(payType)){
				logger.info("openId------"+openId+"-----支付金额>0时，微信支付----------------------------------------------------"+payPrice);
				try {
					orderVo.setPay_money(orderVo.getPay_money());
					// 配置微信支付参数
					Map<String, String> payParam = WxUtil.configPayParam(request, Float.parseFloat(orderVo.getPay_money()), orderCode, openId, "挥货-购买商品",WebConstant.WX_PAY_NOTIFY);
					json.put("result", "succ");
					json.put("orderCode", orderCode);
					json.put("tradeNo", orderCode);
					json.put("orderId", orderVo.getOrder_id());
					json.put("payData", payParam);
					TokenProcessor.resetToken(request);  //清空token
				} catch (NumberFormatException e) {
					json.put("result", "failure");
					json.put("msg","系统异常");
					logger.error("/weixin/payment/payByGroup --error", e);
				} catch (Exception e) {
					json.put("result", "failure");
					json.put("msg","系统异常");
					logger.error("/weixin/payment/payByGroup --error", e);
				}
				
			}else if("2".equals(payType) || "3".equals(payType)){
				json.put("orderCode", orderVo.getOrder_code());
				json.put("result", "succ");
				TokenProcessor.resetToken(request);  //清空token
			}
			logger.info(json.toString());
		}
		
		return json.toString();
	}
	
	
	/**
	 * 支付线下微信下单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/payxx",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public String newOrderxx(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		if (StringUtil.isNull(pd.getString("token")) ||
				!TokenProcessor.isTokenvalid(request)) { //判断提交表单token是否为空
			//如果token为空，那么该页面不是通过正常渠道进入或者已经提交过，token被清空
			json.put("result", "failure");
			json.put("msg", "该订单已经提交，请到个人中心查看该订单详情");
			return json.toString();
		}
		
		
		String productIds=pd.getString("productIds");        //购买的商品Id
		String specIds=pd.getString("specIds");              //规格Id               
		String amounts=pd.getString("amounts");              //购买数量
		
		String receiveId=pd.getString("receiverId");         //收货地址 ID
		
		String payType=pd.getString("payType");     		   //支付类型
		
		String orderSource=pd.getString("orderSource");
		String messageInfo = pd.getString("message_info");//留言
		
		String orderCode=generateOrderCode();
		CustomerVo customer=Constants.getCustomer(request);
		if (customer == null || customer.getCustomer_id() == null) {
			json.put("result", "failure");
			json.put("msg", "用户信息为空");
			return json.toString();
		}
		
		CustomerSourceUtil.removeOrderSource(request);

		logger.info("Customer用户id为"+customer.getCustomer_id()+"--进入了微信下单------");
		
		
		if(productIds==null||"".equals(productIds) || specIds==null||"".equals(specIds)
				 || amounts==null||"".equals(amounts)){
			json.put("result", "failure");
			json.put("msg", "productId为空");
			return json.toString();
		}
		
		String[] productIdArray=productIds.split(",");
		String[] specIdArray=specIds.split(",");
		String[] amountArray=amounts.split(",");
		
		
		BigDecimal price=null;
		BigDecimal payPrice=null;
		
		//查询订单商品存进订单商品集合
		List<OrderProductVo> listOrderProducts=null;
//		OrderProductVo orderProduct=null; 
		
		
		
		//获取订单商品金额
		Map<String,Object> map=getOrderPricexx(productIdArray,specIdArray,amountArray,customer);
		
		if("not".equals(map.get("exist"))){
			json.put("result", "notexist");
			json.put("msg", "商品已下架，请重新下单");
			return json.toString();
		}
		if(map.get("noStockCopy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("noStockCopy"));
			return json.toString();
		}
		if(map.get("CannotBuy")!=null){
			json.put("result", "failure");
			json.put("msg", map.get("CannotBuy"));
			return json.toString();
		}
		price=(BigDecimal) map.get("price");
		
		logger.info("商品总价price----------------"+price);
		payPrice=(BigDecimal)map.get("payPrice");
//		orderProduct=(OrderProductVo) map.get("orderProduct");
		listOrderProducts=(List<OrderProductVo>) map.get("listOrderProducts");
		
		PageData pagedata=new PageData();
		pagedata.put("customerId", String.valueOf(customer.getCustomer_id()));
		
		//收货地址   查询运费
		pagedata.put("receiverId",receiveId);
		ReceiverAddressVo receiverAddressVo=receiverAddressManager.selectReceiverAddressByOption(pagedata);
		if(null == receiverAddressVo){
			json.put("result", "failure");
			json.put("msg", "无效的收货地址");
			return json.toString();
		}
				
		
		logger.info("商品实际payPrice---------------"+payPrice);
		
		
		
		
		OrderVo orderVo=new OrderVo();
		orderVo.setOrder_code(orderCode);  						 //订单编号
		orderVo.setCustomer_id(customer.getCustomer_id());       //客户
		
	
		orderVo.setReceiver(receiverAddressVo.getReceiverName());   //收货人
		orderVo.setMobile(receiverAddressVo.getMobile());           //手机号码
		orderVo.setAddress(receiverAddressVo.getAddress());
		orderVo.setFreight("0");                //运费
		orderVo.setMessage_info(messageInfo);                  //留言
		orderVo.setOrder_type(2);   							// 1线上，2线下
		orderVo.setPay_type(Integer.parseInt(payType));         ///支付方式，1：微信；2：支付宝    微信平台都是 1 
		orderVo.setTotal_price(String.valueOf(price));        //订单总金额
		if(payPrice.floatValue()<0){
			payPrice=new BigDecimal(0);
		}
		
		
		logger.info("customerId为"+customer.getCustomer_id()+"订单号为orderVoCode--"+orderVo.getOrder_code()+"商品总价为---"
		+orderVo.getTotal_price()+"-------商品实际支付payPrice--"+payPrice);
		
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		orderVo.setPay_money(String.valueOf(payPrice));       //实付金额
		orderVo.setOrderSource(orderSource);
		orderManager.insertOrder(orderVo);
		
		//把订单ID添加进入订单商品 且 进行增加操作
		if(!listOrderProducts.isEmpty() && listOrderProducts.size()>0){
			PageData pspd = new PageData();
			for (int i = 0; i < listOrderProducts.size(); i++) {
				listOrderProducts.get(i).setOrder_id(orderVo.getOrder_id());
				listOrderProducts.get(i).setSub_order_code(generateOrderCode());
				
				pspd.put("specId", listOrderProducts.get(i).getSpec_id());
				ProductSpecVo pspec = this.productSpecManager.queryProductSpecByOption(pspd);
				if(pspec != null){
					int stockCopy = pspec.getStock_copy() - listOrderProducts.get(i).getQuantity();
					if(stockCopy < 0){
						stockCopy = 0;
					}
					ProductSpecVo pps = new ProductSpecVo();
					pps.setSpec_id(pspec.getSpec_id());
					pps.setStock_copy(stockCopy);
					productSpecManager.updateProductSpecBySid(pps);
				}
			}
			
			orderProductManager.addBatchOrderProduct(listOrderProducts);
		}
		
		
		
		
		try {
			logger.info("记录支付日志: startting... ");
			
			//记录支付日志
			PayLogVo payLogInfo=new PayLogVo();
			payLogInfo.setOrder_id(orderVo.getOrder_id());
			payLogInfo.setOrder_code(orderCode);
			payLogInfo.setCustomer_id(customer.getCustomer_id());
			payLogInfo.setPay_type(Integer.parseInt(payType));
			payLogInfo.setPay_money(String.valueOf(price));
			payLogManager.insertPayLog(payLogInfo);
			
			logger.info("记录支付日志: 完成... ");
		} catch (Exception e1) {
			logger.error("记录支付日志: 异常... ", e1);
		}
		
		
		//如果支付的钱为0 ,就直接调用支付成功后 的方法
		if(payPrice.compareTo(BigDecimal.ZERO) == -1){
			payPrice = BigDecimal.ZERO;
		}
		if(payPrice.compareTo(BigDecimal.ZERO)==0){
			logger.info("支付金额==0时，不走微信支付------------------------------------------------------------");
			//1.修改订单状态
			//2.修改优惠券状态
			//3.修改用户礼品卡和优惠券的锁定状态
			try {
				notifyUpdateAboutOrder(request,response, orderCode,"","1",customer);
				json.put("result", "succ");
				json.put("orderCode", orderCode);
				json.put("notify", "succ");
			} catch (Exception e) {
				logger.error("使用礼品卡或者优惠券后,订单应支付总价为0,调用支付成功后的方法出现异常", e);
			}
			
		}else{
			String openId = customer.getOpenid();
			logger.info("openId------"+openId+"-----支付金额>=0时，微信支付----------------------------------------------------"+payPrice);
			try {
				String payMoney = orderVo.getPay_money();
				// 用户登录开关，在本地开发环境可关闭此开关即：false
				if (!"true".equals(WebConstant.ISMEMBER)) {
					payMoney = "0.01";
				}
				// 配置微信支付参数
				Map<String, String> payParam = WxUtil.configPayParam(request, Float.parseFloat(payMoney), orderCode, openId, "挥货-购买商品",WebConstant.WX_PAY_NOTIFY);
				json.put("result", "succ");
				json.put("orderCode", orderCode);
				json.put("tradeNo", orderCode);
				json.put("orderId", orderVo.getOrder_id());
				json.put("payData", payParam);
				TokenProcessor.resetToken(request);  //清空token
			} catch (NumberFormatException e) {
				json.put("result", "failure");
				json.put("msg","系统异常");
				logger.error("/weixin/payment/payxx --error", e);
			} catch (Exception e) {
				json.put("result", "failure");
				json.put("msg","系统异常");
				logger.error("/weixin/payment/payxx --error", e);
			}
			
			logger.info(json.toString());
		}
		
		return json.toString();
	}
	
	
	
	
	/**
	 * 个人中心  --> 全部订单  去支付
	 *  2017-02-27 by chen
	 */
	@RequestMapping(value = "/nextPrePay")
	@ResponseBody
    public String nextPrePay(HttpServletRequest request) {  
//    	logger.info("开始微信支付...");
    	logger.info("读取页面返回后台的参数信息，开始 ...");
    	JSONObject json=new JSONObject();
    	PageData pd=this.getPageData();
    	
    	CustomerVo customerVo = Constants.getCustomer(request);
    	logger.info("customerVo--"+customerVo.getCustomer_id()+"----去支付开始");
    	//订单编号
		String orderCode=null;
		String orderId=null;
		OrderVo order=null;
		
			orderCode = pd.getString("orderCode");
			orderId = pd.getString("orderId");
			if(null==orderCode || "".equals(orderCode) ||  null==orderId || "".equals(orderId) ){
				json.put("result", "failure");
				json.put("msg","订单ID或编号为空!");
				return json.toString();
			}
			
			pd.put("customerId",String.valueOf(customerVo.getCustomer_id()));
			logger.info("customerVo--"+customerVo.getCustomer_id()+"----去支付orderId--"+orderId+"------orderCode--"+orderCode);
			order=orderManager.findOrder(pd);
			if(null ==order || null==order.getTotal_price() || "".equals(order.getPay_money()) ){
				json.put("result", "failure");
				json.put("msg","订单ID或编号为空!");
				return json.toString();
			}
			
			
			PageData pdData = new PageData();
			pdData.put("customerId", order.getCustomer_id());
			
			//订单详情
			List<OrderProductVo> orderProductlist = order.getListOrderProductVo();
			if(orderProductlist!=null){
				
				for (OrderProductVo orderProductVo : orderProductlist) {
					pdData.put("productId", orderProductVo.getProduct_id());
					pdData.put("specId", orderProductVo.getSpec_id());
					ProductSpecVo productSpecVo = productSpecManager.queryProductSpecByOption(pdData);
					if(productSpecVo!=null){
						int limitMaxCopy = productSpecVo.getLimit_max_copy();
						int hasBuy=0;
						//走限购流程
						if(StringUtils.isNotBlank(productSpecVo.getLimit_start_time())&&StringUtils.isNotBlank(productSpecVo.getLimit_end_time())){
							if(limitMaxCopy>-1){//限购
								
								//先查这次订单数量有没有超过购买数量
								if(orderProductVo.getQuantity()>limitMaxCopy){
									json.put("result", "failure");
									json.put("canBuy", "NO");
									json.put("msg",orderProductVo.getProduct_name()+"已限购"+limitMaxCopy+"件" );
									return json.toString();
								}
								
								Date dt = DateUtil.stringToDate(productSpecVo.getLimit_end_time());
								Date start = DateUtil.stringToDate(productSpecVo.getLimit_start_time());
								if(dt.after(new Date())&&start.before(new Date())){//限购时间内
									PageData productPd = new PageData();
									productPd.put("startTime", productSpecVo.getLimit_start_time());
									productPd.put("endTime", productSpecVo.getLimit_end_time());
									productPd.put("customerId", order.getCustomer_id());
									productPd.put("hasBuy", "hasBuy");//已付款的订单
									productPd.put("productId", orderProductVo.getProduct_id());
									productPd.put("specId", orderProductVo.getSpec_id());
									//按产品id 和规格查出所有买过此商品的订单详情
									List<OrderProductVo> orderProducts = orderProductManager.findListOrderProduct(productPd);//查出之前下过的单
									if(orderProducts!=null && orderProducts.size()>0){//在限期内买过此产品
										
										for (OrderProductVo orderpvo : orderProducts) {
											hasBuy+=orderpvo.getQuantity();
										}
										int restCopy = limitMaxCopy-hasBuy;
										if(restCopy<=0){
											json.put("result", "failure");
											json.put("canBuy", "NO");
											json.put("msg",orderProductVo.getProduct_name()+"已限购"+limitMaxCopy+"件,您已经购买"+hasBuy+"件" );
											return json.toString();
										}
									}
								}
							}
						}
					}
				}
			}
			
			
			
			
    	try {
    		String openId=customerVo.getOpenid();
    		
			//更新新的支付订单号
			//orderCode=generateOrderCode();
			//PageData pd2=new PageData();
			//pd2.put("orderId", order.getOrder_id());
			//pd2.put("orderCode", orderCode);
			//orderManager.updateOrderCodeByOrdeId(pd2);
			
			//PageData pd3=new PageData();
			//pd3.put("oldOrderCode", order.getOrder_code());
			//pd3.put("newOrderCode", orderCode);
			//payLogManager.updateOrderCodeByOrdeId(pd3);
			
			String payMoney = order.getPay_money();
			// 用户登录开关，在本地开发环境可关闭此开关即：false
			if (!"true".equals(WebConstant.ISMEMBER)) {
				payMoney = "0.01";
			}
			
			
			//区分微信或支付宝支付
			if(order.getPay_type() != null){
				if(order.getPay_type() == 1){
					logger.info("开始微信支付-------------------------");
					// 配置微信支付参数
					Map<String, String> payParam = WxUtil.configPayParam(request, Float.parseFloat(payMoney), orderCode, openId, "挥货-购买商品",WebConstant.WX_PAY_NOTIFY);
					json.put("result", "succ");
					json.put("orderCode", orderCode);
					json.put("tradeNo", orderCode);
					json.put("payData", payParam);
					json.put("payType", 1);
					logger.info("结束微信支付-------------------------");
				}else if(order.getPay_type() == 2 || order.getPay_type() ==3){
					json.put("tradeNo", orderCode);
					json.put("result", "succ");
					json.put("payType", order.getPay_type());
				}else{
					json.put("result", "failure");
					json.put("msg","订单中支付方式未知请检查");
				}
			}else{
				json.put("result", "failure");
				json.put("msg","订单中不存在支付方式请检查");
			}
		} catch (NumberFormatException e) {
			json.put("result", "failure");
			json.put("msg","系统异常");
			logger.error("/weixin/payment/nextPrePay --error", e);
		} catch (Exception e) {
			json.put("result", "failure");
			json.put("msg","系统异常");
			logger.error("/weixin/payment/nextPrePay --error", e);
		}
    	
		return json.toString();  
    }
	
	
	/**
	 * 微信支付通知</br>
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value="/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		InputStream input;
		try {
			logger.info("-----------进入微信支付通知回调方法-----------");
			input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			
			
			StringBuffer buffer = new StringBuffer("");
			String str = "";

			// 解析字符串
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			logger.info("支付成功通知报文：" + buffer.toString());

			WxNotifyData notifyData = (WxNotifyData) XmlParser.parser(buffer.toString(), WxNotifyData.class);
			if("SUCCESS".equals(notifyData.getResult_code())){
				String tradeNo = notifyData.getOut_trade_no();
				String transactionId = notifyData.getTransaction_id();
				PageData pd = new PageData();
				pd.put("orderCode", tradeNo);
				OrderVo order = orderManager.findOrder(pd);
				CustomerVo customer=new CustomerVo();
				if(order!=null){
					pd.put("customer_id", order.getCustomer_id());
					customer = customerManager.queryOne(pd);
					logger.info("支会成功回调查到用户--------"+customer.getOpenid());
				}
				Map<String, String> map = getFieldMap(notifyData);
				String sign = WxSign.getSign(map);
				logger.info("交易号:"+tradeNo);
				logger.info("生成的签名：  " + sign);
				logger.info("返回的签名：  " + notifyData.getSign());
				// 签名验证
				if (sign.equals(notifyData.getSign()) || (order != null && order.getStatus() == 1)) {
					
					//1.修改订单状态
					//2.修改优惠券状态
					//3.修改用户礼品卡和优惠券的锁定状态
					//4.支会成功后要改变预售的支持人数和金额;
					//5.购买成功之后修改库存/单个红包使用状态
					notifyUpdateAboutOrder(request,response, tradeNo,transactionId,"1",customer);
					
					
					
					// 如果已经完成支付则直接返回
					String result = getReturnContent();
					this.outString(result, response);
				
				
				
				
				} else {
					logger.info("签名不正确");
					this.outString(getFailedReturnContent(), response);
				}
			} else {	//取消支付或支付失败
				
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	//************************支付宝支付结果异步通知--start************//
	@RequestMapping(value="/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
		InputStream input;
		try {
			logger.info("-----------进入支付宝支付通知回调方法-wap----------");
			input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer("");
			String str = "";
			// 解析字符串
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			//
			logger.info("支付成功通知报文：" + buffer.toString());
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html");
             //获取支付结果
             String tradeStatus = request.getParameter("trade_status");
             //获取统一支付订单号
             String payBusinessId = request.getParameter("out_trade_no");
             if(StringUtils.isBlank(payBusinessId)){
            	 logger.error("FAIL:支付宝支付单号为空");
             }
             //交易未成功
             if(!(AlipayConfig.AliNotify_TradeState_SUCCESS.equals(tradeStatus) || 
            		 				AlipayConfig.AliNotify_TradeState_FINISHED.equals(tradeStatus))){
     	    	 logger.error("交易失败:"+tradeStatus);
     	     }else {
				//验证回传通知的签名(防止"假通知")--安全
     	    	Map<String,String> params = HttpClientTool.requestAli2Map(request);
     	    	//1.订单号和支付金额初始话(先用支付宝返回的)
     	    	String orderCode_DB = payBusinessId;
//     	    	String totalPrice_DB = params.get("total_amount");
     	    	String totalPrice_DB = params.get("total_fee");
     	    	//2.订单是否存在
     	    	Map<String, String> orderMap = aliPayService.checkOrderExit(payBusinessId);
     	    	if (orderMap != null) {
//					orderNo_DB = orderMap.get("orderNo_DB");
					totalPrice_DB = orderMap.get("totalPrice_DB");
				}else {
					logger.error("notify失败,不存在的订单");
				}
     	    	PageData pd = new PageData();
				pd.put("orderCode", payBusinessId);
				OrderVo order = orderManager.findOrder(pd);
     	    	CustomerVo customer=new CustomerVo();
				if(order!=null){
					pd.put("customer_id", order.getCustomer_id());
					customer = customerManager.queryOne(pd);
					logger.info("支付宝支付会成功回调到用户-wap-------"+customer.getOpenid());
				}
				//String sign = DictionarySortUtil.createSign(map, PayConfigUtil.API_NOTWEB_KEY);
				logger.info("交易号:"+payBusinessId);
//					logger.info("生成的签名：  " + sign);
//					logger.info("返回的签名：  " + notifyData.getSign());
				//3.验证sign--是否支付宝回传
				String checkSign = AliPaySubmit.buildResponseDecode(params, orderCode_DB, totalPrice_DB, AlipayConfig.ali_public_key);
				if ("SUCCESS".equals(checkSign)) {
					notifyUpdateAboutOrder(request,response, payBusinessId,request.getParameter("trade_no"),"2",customer);
					//
					// 如果已经完成支付则直接返回
					String result = getReturnContent();
					this.outString(result, response);
				}else {
			 		logger.info("签名不正确");
					this.outString(getFailedReturnContent(), response);
				}
     	     }
			} catch (Exception e) {
				logger.error("支付宝异步通知发生异常-wap", e);
			}
	}
	//************************支付宝支付结果异步通知--end************//
	
	@RequestMapping("/test")
	@ResponseBody
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeNo=request.getParameter("tradeNo");
		CustomerVo customer=Constants.getCustomer(request);
		notifyUpdateAboutOrder(request,response,tradeNo,null,"1",customer);
	}
	
	/**
	 * @param response
	 * @throws Exception 
	 */
	public void notifyUpdateAboutOrder(HttpServletRequest request,HttpServletResponse response,String tradeNo,String transactionId,String payType,CustomerVo customer) throws Exception{
		PageData pd =new PageData();
		
		try {
			
			//查询支付日志
			pd.put("orderCode", tradeNo);
			PayLogVo payLogInfo=payLogManager.findPayLog(pd);
			
			if(null!=payLogInfo){
		//		logger.info("支付状态:"+payLogInfo.getPay_time());   //支付时间不为空就是没有支付
				//订单已支付，不需要处理逻辑
				if(null!=payLogInfo.getPay_time()&& !"".equals(payLogInfo.getPay_time())){
					this.outString(getReturnContent(), response);
					return ;
				}
				
				/**
				 * 更新支付日志信息    流水号，支付类型，支付时间
				 */
				pd.clear();
				if(null!=transactionId && !"".equals(transactionId)){
					pd.put("transId",transactionId);
				}
				pd.put("payType",payType);
				pd.put("orderId",payLogInfo.getOrder_id());
				pd.put("customerId",payLogInfo.getCustomer_id());
				payLogManager.editPayLog(pd);
				
				PageData pd3=new PageData();
				pd3.put("customer_id", payLogInfo.getCustomer_id());
				CustomerVo customerVo = customerManager.queryOne(pd3);
				 
				pd.clear();
				pd.put("orderId",String.valueOf(payLogInfo.getOrder_id()));
				pd.put("orderCode",String.valueOf(payLogInfo.getOrder_code()));
				OrderVo orderVo =orderManager.findOrder(pd);
				
				if(null!=orderVo){
					/**
					 * 更新订单支付状态和支付类型
					 */
					pd.put("status","2");       //订单待发货(已付款)
					pd.put("payType",payType);
					pd.put("customerId", payLogInfo.getCustomer_id());
					orderManager.editOder(pd);
					
					BigDecimal useIntegral = BigDecimal.ZERO;
					if(orderVo.getTotalRedeemIntegral() != null 
							&& orderVo.getTotalRedeemIntegral().compareTo(BigDecimal.ZERO) == 1){
						useIntegral = orderVo.getTotalRedeemIntegral();
					}
					if(orderVo.getTotalGoldIntegral() != null 
							&& orderVo.getTotalGoldIntegral().compareTo(BigDecimal.ZERO) == 1){
						useIntegral = useIntegral.add(orderVo.getTotalGoldIntegral());
					}
					
					BigDecimal hasIntegral = customer.getCurrentIntegral();
					if(useIntegral.compareTo(BigDecimal.ZERO) == 1){
						hasIntegral = hasIntegral.subtract(useIntegral);
						customer.setCurrentIntegral(hasIntegral);
						this.customerManager.updateByPrimaryKeySelective(customer);
					}
					
					//订单支付成功发系统消息给用户
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(MessageVo.MSGTYPE_TRANS);
					msgVo.setSubMsgType(5);
					msgVo.setMsgTitle("订单已支付");
					msgVo.setMsgDetail("您的挥货订单"+payLogInfo.getOrder_code()+"已完成支付，请耐心等待商家发货");
					msgVo.setIsRead(0);
					msgVo.setCustomerId(payLogInfo.getCustomer_id());
					msgVo.setObjId(payLogInfo.getOrder_id());
					messageManager.insert(msgVo);			
				}else{
					throw new Exception();	
				}
				
				
				if(payLogInfo.getUse_single_coupon_id()!=null && !"".equals(payLogInfo.getUse_single_coupon_id())){
					logger.info("用户customer_id为-"+payLogInfo.getCustomer_id()+"准备更新用户单品红包使用信息。。。 ");
					UserSingleCouponVo userSingleCouponVo=new UserSingleCouponVo();
					userSingleCouponVo.setUserSingleId(payLogInfo.getUse_single_coupon_id());
					userSingleCouponVo.setUsedTime(DateUtil.dateToString(new Date()));
					userSingleCouponVo.setStatus(1);   
					userSingleCouponManager.updateByPrimaryKey(userSingleCouponVo);
					logger.info("完成更新用户使用单品红包。。。 finish");
				}
				
				
				//更换锁定用户使用的礼品卡与优惠券
				if( (null != payLogInfo.getCoupon_id() && !"".equals(payLogInfo.getCoupon_id())) || 
					 	(null != payLogInfo.getGift_card_no() && !"".equals(payLogInfo.getGift_card_no()))){
					
					//根据条件查询 锁定记录      不为空 已被锁定   为空  解除锁定  
					PageData pdData=new PageData();
					pdData.put("orderCode", tradeNo);
					pdData.put("customerId", payLogInfo.getCustomer_id());
					pdData.put("lockStatus",1);    //完全用完余额
					
					logger.info("用户customer_id为-"+payLogInfo.getCustomer_id()+"调用了useLockManager.findUseLock()方法---订单orderCode--"+tradeNo);
					
					List<UseLockVo> listUseLockInfo=useLockManager.findListUseLock(pdData);
					
					List<UseLockVo> listUserLock=new ArrayList<UseLockVo>();
					if(null!=listUseLockInfo && listUseLockInfo.size()>0){
						listUserLock.addAll(listUseLockInfo);
					}
					
					pdData.put("lockStatus",5);      //用了部分礼品卡余额
					listUseLockInfo=useLockManager.findListUseLock(pdData);
					if(null!=listUseLockInfo && listUseLockInfo.size()>0){
						listUserLock.addAll(listUseLockInfo);
					}
					
					if(null!=listUserLock && listUserLock.size()>0){
						pdData.put("orderCode",tradeNo);    			//锁定订单编号
						pdData.put("lockStatus","3");   				//成功支付
						useLockManager.editUseLockById(pdData);
						logger.info("成功支付调用了useLockManager.editUseLockById()方法");
					}
					
					
					
					logger.info("用户customer_id为-"+payLogInfo.getCustomer_id()+"准备更新用户优惠券使用。。。 ");
					
					//增加使用优惠券使用记录
					if(payLogInfo.getCoupon_id()!=null && !"".equals(payLogInfo.getCoupon_id())){
						logger.info("开始更新用户使用优惠券。。。 start");
						logger.info("优惠券id:"+payLogInfo.getCoupon_id());
						
						CouponInfoVo couponInfo=new CouponInfoVo();
						couponInfo.setCoupon_id(payLogInfo.getCoupon_id());           
						couponInfo.setUsed_time(DateUtil.dateToString(new Date()));          
						couponInfo.setStatus(1);    								  
						couponInfo.setOrder_code(tradeNo);                          
						couponInfo.setFrom_order_id(orderVo.getOrder_id());          
						PageData pds=new PageData();
						pds.put("status","1");                                         //已使用     
						pds.put("orderCode",tradeNo);                                  //使用的订单编号 
						pds.put("usedTime",DateUtil.dateToString(new Date()));         //使用时间       
						pds.put("orderId",String.valueOf(orderVo.getOrder_id()));      //使用的订单ID
						pds.put("couponId",payLogInfo.getCoupon_id());                 //使用的优惠券ID
						
						couponInfoManager.updateUserCouponByCid(pds);
						
						logger.info("结束更新用户使用优惠券。。。 end");
					}
					
					
					/**
					 *  礼品卡说明：      如果礼品卡的钱不用完，添加进入锁定状态 （1 全部使用    5没有使用完） 
					 *       1. 如果使用使用礼品卡的金额正好与 订单金额一致   那么 不存在多出一个 5没有使用完  礼品卡号
					 *       2. 如果礼品卡的金额与订单金额不一致  那么存在多出一个礼品卡 5没有使用完 卡号  
					 */
					
					//增加使用礼品卡记录
					if(payLogInfo.getGift_card_no()!=null && !"".equals(payLogInfo.getGift_card_no())){
						logger.info("优惠券ids:"+payLogInfo.getGift_card_no());
						
						for (int i = 0; i < listUserLock.size(); i++) {      //锁定的钱
							
							
							if(null ==listUserLock.get(i).getUse_coupon_id() && null!=listUserLock.get(i).getUse_giftcard_no()
									 && null!=listUserLock.get(i).getUse_card_money()){
								
								String cardNo=listUserLock.get(i).getUse_giftcard_no();
								String cardMoeny=listUserLock.get(i).getUse_card_money();
								
								//修改礼品卡，应该查询出这张卡   以前使用多少钱 +现在使用的钱   修改状态为已用完  且 记录使用的总金额
								PageData datas=new PageData();
								datas.put("giftCardNo",cardNo);
								datas.put("customerId", payLogInfo.getCustomer_id());
								GiftCardVo giftCardVo = giftCardManager.findGiftCard(datas);
								
								BigDecimal cardTotalMoeny=new BigDecimal(giftCardVo.getCard_money());
								
								BigDecimal leftMoney=new BigDecimal(cardMoeny);
								if(null!=giftCardVo.getCard_use()&& !"".equals(giftCardVo.getCard_use())){
									leftMoney=leftMoney.add(new BigDecimal(giftCardVo.getCard_use()));
								}
								
								
								//然后把  对应的卡   剩下使用的钱+已使用的钱
								datas.clear();
								datas.put("cardUseMoney",String.valueOf(leftMoney));
								datas.put("cardId",giftCardVo.getCard_id());
								
								//锁定状态为 1 （已用完） 就修改礼品卡状态为 3 已用完 
								if(listUserLock.get(i).getLock_status()==1 || cardTotalMoeny.compareTo(leftMoney)==0 ){
									datas.put("status","3");
								}
								
								giftCardManager.updateGiftCardByGid(datas);
								
								
								GiftCardRecordVo giftCardRecordVo=new GiftCardRecordVo();
								giftCardRecordVo.setCardNo(listUserLock.get(i).getUse_giftcard_no());
								giftCardRecordVo.setCustomerId(customer.getCustomer_id());
								giftCardRecordVo.setOrderNo(tradeNo);
								giftCardRecordVo.setRecordRemak("订单编号:"+tradeNo+" 使用");
								giftCardRecordVo.setRecordType((byte)2);
								giftCardRecordVo.setMoney(new BigDecimal(cardMoeny));
								giftCardRecordManager.insertGiftCardRecord(giftCardRecordVo);
								
								
							}
						}
					}
					
				}
				
				//4.支会成功后要改变预售的支持人数和金额;
				logger.info("支付成功回调修改预购进度开始--------");
				PageData orpd = new PageData();
				orpd.put("orderId", orderVo.getOrder_id());
				orpd.put("orderCode", orderVo.getOrder_code());
				List<OrderProductVo> orderProducts = orderProductManager.findListOrderProduct(orpd);
				
				//分销系统录入代理人收益信息
				//判断订单来源是否为代理分销
				CustomerAgentVo customerAgent = customerAgentManager.findAgentBySource(orderVo.getOrderSource());
				if (null != customerAgent && orderProducts.size() == 1) {
					if(new BigDecimal(orderVo.getPay_money()).compareTo(BigDecimal.ZERO) == 1){ //实付金额大于0
						customerAgentManager.addAccountRecord(customerAgent,orderProducts.get(0),orderVo.getPay_money());
					}
				}
				
				if(orderProducts!=null&&orderProducts.size()>0){
					for (OrderProductVo orderProductVo : orderProducts) {
						orpd.clear();
						orpd.put("productId", orderProductVo.getProduct_id());
						orpd.put("specId", orderProductVo.getSpec_id());
						
//						List<PreProductVo> preProductVo = preProductManager.queryList(orpd);//根据条件查出预购商品
//						
//						if(preProductVo!=null&&preProductVo.size()>0){
//							for (PreProductVo pre : preProductVo) {
//								
//								//根据条件查出此用户是否买过此产品
//								PageData productPd = new PageData();
//								productPd.put("productId", orderProductVo.getProduct_id());
//								productPd.put("customerId", orderVo.getCustomer_id());
//								productPd.put("startTime", pre.getRaiseStart());
//								productPd.put("endTime", orderVo.getOrder_time());
//								productPd.put("hasBuy", "hasBuy");//已付款的订单
//								List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(productPd);
//								logger.info("之前下单----------------------------====》");
//								
//								
//								BigDecimal raiseMoney = new BigDecimal(orderProductVo.getPrice()).multiply(new BigDecimal(orderProductVo.getQuantity()));
//								if(org.apache.commons.lang.StringUtils.isNotBlank(orderProductVo.getActivity_price()) && new BigDecimal(orderProductVo.getActivity_price()).compareTo(BigDecimal.ZERO)==1){
//									raiseMoney = new BigDecimal(orderProductVo.getActivity_price()).multiply(new BigDecimal(orderProductVo.getQuantity()));
//								}else if(StringUtil.isNotNull(orderProductVo.getDiscount_price())  && new BigDecimal(orderProductVo.getDiscount_price()).compareTo(BigDecimal.ZERO)==1){
//									raiseMoney = new BigDecimal(orderProductVo.getDiscount_price()).multiply(new BigDecimal(orderProductVo.getQuantity()));
//								}
//								if(orderProductlist!=null&&orderProductlist.size()>0){//买过此产品的客户算一份
//									pre.setSupportNum(pre.getSupportNum());
//								}else{//没买过的支持人数要加1
//									pre.setSupportNum(pre.getSupportNum()+1);
//								}
//								
//								pre.setRaiseMoney(pre.getRaiseMoney().add(raiseMoney));
//								preProductManager.updatePreProduct(pre);//修改预购进度
//								
//							}
//						}
						PageData pd4=new PageData();
						pd4.put("status", 2);//订单状态，0：已删除；1：待付款；2：待发货；3：待签收；4：待评价；5：已完成;6:系统取消;7:已退款
						pd4.put("orderpro_id", orderProductVo.getOrderpro_id());
						orderProductManager.editOrderProductByOrderProductId(pd4);
						
					}
				}
				
				logger.info("支付成功回调修改预购成功--------");
				
				StringBuilder productName=new StringBuilder();
				//5.购买成功之后修改库存
				logger.info("支付成功后回调，修改库存开始----------------------------------");
				if(orderProducts!=null&&orderProducts.size()>0){
					for (OrderProductVo orderProductVo : orderProducts) {
						orpd.clear();
						orpd.put("productId", orderProductVo.getProduct_id());
						orpd.put("specId", orderProductVo.getSpec_id());
						orpd.put("status","1");
						//判断库存 此处逻辑变更，在支付前先减除份额
//						ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(orpd);
//						if(specVo!=null&&specVo.getStock_copy()!=null){
//							int saleOut = orderProductVo.getQuantity();
//							int sale = specVo.getSales_copy()==null?1:specVo.getSales_copy();
//							specVo.setSales_copy(sale+saleOut);//卖出的份数
//							logger.info("卖出去份数--------"+specVo.getSales_copy()+"rrrrrrrrrrrrrrrrrrrr=======");
//							//库存就要减去卖出去的份数
//							int stock = specVo.getStock_copy()-saleOut;
//							if(stock>=0){
//								specVo.setStock_copy(stock);
//							}else{//超卖
//								specVo.setStock_copy(0);
//								logger.info("用户"+customer.getCustomer_id()+"购买产品ID为"+orderProductVo.getProduct_id()+"购买数量为"+saleOut+"支付成功后回调，库存超卖"+stock+"份-----------------------------------");
//
//							}
//							int count  = productSpecManager.updateProductSpecBySid(specVo);
//							if(count>0){
//								logger.info("用户"+customer.getCustomer_id()+"购买产品ID为"+orderProductVo.getProduct_id()+"购买数量为"+saleOut+"支付成功后回,库存有"+specVo.getStock_copy()+"件-----------------------------------");
//
//							}
//						}
						productName.append(orderProductVo.getProduct_name()+" ");
						logger.info("单品红包状态修改开始=========================================================");
						PageData singlePd = new PageData();
						if(orderProductVo.getUser_single_coupon_id()!=null&&orderProductVo.getUser_single_coupon_id()>0){
							singlePd.put("userSingleId", orderProductVo.getUser_single_coupon_id());
							UserSingleCouponVo uscv = userSingleCouponManager.queryOne(singlePd);
							if(uscv!=null){
								uscv.setStatus(1);//1已使用状态
								userSingleCouponManager.updateByPrimaryKey(uscv);
							}
						}
						logger.info("单品红包状态修改结束=========================================================");
						
						if(orderVo.getGroupJoinId() > 0){
							try {
								PageData pad = new PageData();
								pad.put("groupJoinId", orderVo.getGroupJoinId());
								GroupJoinVo groupJoinVo = this.groupJoinManager.queryOne(pad);
								if(groupJoinVo != null){
									groupJoinVo.setStatus(1);
									this.groupJoinManager.updateByPrimaryKey(groupJoinVo);
									
									pad.clear();
									pad.put("userGroupId", groupJoinVo.getUserGroupId());
									UserGroupVo userGroupVo = this.userGroupManager.queryOne(pad);
									pad.put("groupId", userGroupVo.getGroupId());
									SysGroupVo sysGroupVo = this.sysGroupManager.queryOne(pad);
									if(userGroupVo !=null){
										if(userGroupVo.getOriginator() == groupJoinVo.getOriginator() && userGroupVo.getStatus() == 0){
											userGroupVo.setStatus(1);
											this.userGroupManager.updateByPrimaryKey(userGroupVo);
											if(sysGroupVo.getJoinCopy() < sysGroupVo.getGroupCopy()){
												sysGroupVo.setJoinCopy(sysGroupVo.getJoinCopy()+1);
												this.sysGroupManager.updateByPrimaryKey(sysGroupVo);
											}
										}else if(userGroupVo.getStatus() == 1){
											PageData gjpd = new PageData();
											gjpd.put("userGroupId", userGroupVo.getUserGroupId());
											gjpd.put("status", 1);
											List<GroupJoinVo> gjs = groupJoinManager.queryList(gjpd);
											if(gjs.size() > 0 && gjs.size() >= sysGroupVo.getLimitCopy()){
												userGroupVo.setStatus(2);
												this.userGroupManager.updateByPrimaryKey(userGroupVo);
											}
										}
										
									}
								}
							} catch (Exception e) {
								logger.error("团购，回调groupJoinManager.queryOne异常", e);
							}
						}
					}
				}
				
				logger.info("支付成功后回调，修改库存结束-----------------------------------");
				
				
				//支付成功发送微信信息
				 if(WebConstant.ISMEMBER.equals("true")){
					 this.wechartSendMessage(customerVo, orderVo);
				 }
			}
			
		} catch (NumberFormatException e) {
			logger.error("调用了 notifyUpdateAboutOrder() 方法  异常", e);
		}
	}
	
	/**
	 * 微信发消息（购买成功后发）
	 * @param
	 * @return
	 * @author zhangjing 2017年12月28日 下午2:18:01
	 */
	private void wechartSendMessage(CustomerVo customerVo,OrderVo orderVo){
		String openid = customerVo.getOpenid();
		if (customerVo != null && StringUtil.isNotNull(openid)) {
			List<TemplateMsgDataDto> data = new ArrayList<TemplateMsgDataDto>();
			String many = "";
			if (orderVo.getListOrderProductVo().size() > 1) {
				many = " 等多件";
			}
			TemplateMsgDataDto name = new TemplateMsgDataDto("name", orderVo
					.getListOrderProductVo().get(0).getProduct_name()
					+ many, "#000");
			TemplateMsgDataDto remark = new TemplateMsgDataDto("remark", "订单号："
					+ orderVo.getOrder_code()
					+ "\n我们已收到您的付款，开始为您打包商品，请耐心等待\n如有问题请致电"
					+ WebConstant.SERVICE_PHONE + "或添加客服微信:huibuokefu123进行联系！",
					"#000");
			data.add(remark);
			data.add(name);
			String url = WebConstant.MAIN_SERVER + "/weixin/order/findList?status=2";
			TemplateMsgBodyDto dto = new TemplateMsgBodyDto(openid,
					WebConstant.BUYGOODS_TEMPLATEID, url, "#000", data,
					WxUtil.getAccessToken());
			try {
				WxTemplateMessageUtil.send(dto);
			} catch (JSONException e) {
				logger.error("WxTemplateMessageUtil/test --error", e);
			} catch (IOException e) {
				logger.error("WxTemplateMessageUtil/test --error", e);
			}
		}
	}
	
	//获取订单商品价格
	public Map<String,Object> getOrderPricexx(String[] productIdArray,String[] specIdArray,String[] amountArray,CustomerVo customer){
		
		OrderProductVo orderProduct=null;
		List<OrderProductVo> listOrderProducts=new ArrayList<OrderProductVo>();
		
		BigDecimal totalPrice=new BigDecimal(0);
		BigDecimal payPrice=new BigDecimal(0);
		Map<String,Object> map=new HashMap<String, Object>();
		
		//查询购买的商品价格信息
		PageData pageData=new PageData();
		for (int i = 0; i < productIdArray.length; i++) {
			BigDecimal price=new BigDecimal(0);
			
			pageData.put("productId", productIdArray[i]);
			pageData.put("specId", specIdArray[i]);
			
			pageData.put("status","1");
			ProductVo product = productManager.selectProductByOption(pageData);
			if(null==product){
				map.put("exist","not");
				return map;
			}
			//判断库存
			ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pageData);
			
			if(specVo!=null&&specVo.getStock_copy()!=null){
				int restCopy = specVo.getStock_copy()-Integer.parseInt(amountArray[i]);
				if(restCopy<0){
					map.put("noStockCopy",product.getProduct_name()+"没有足够的库存");
					return map;
				}
			}
			
			
			logger.info("商品id是productId---"+productIdArray[i]+"--商品名称为"+product.getProduct_name()+"--------规格specId-----"+specIdArray[i]);
			

			
			if(null!=product&&null!=specVo){
				orderProduct=new OrderProductVo();
				orderProduct.setProduct_id(product.getProduct_id());
				orderProduct.setProduct_name(product.getProduct_name());
				orderProduct.setSpec_id(specVo.getSpec_id());
				orderProduct.setSpec_name(specVo.getSpec_name());
				orderProduct.setPrice(specVo.getSpec_price());
				price = new BigDecimal(specVo.getSpec_price());
				
				orderProduct.setQuantity(Integer.parseInt(amountArray[i]));
				
			}
			
			if(StringUtil.isNoNull(specVo.getDiscount_price()) && !specVo.getDiscount_price().equals("-1")){
				orderProduct.setDiscount_price(specVo.getDiscount_price()+"");
			}
			
			orderProduct.setIs_evaluate(0);
			listOrderProducts.add(orderProduct);
			
			price=price.multiply(new BigDecimal(amountArray[i]));          //计算出商品总价格
			totalPrice=totalPrice.add(price);
			if(StringUtil.isNotNull(orderProduct.getActivity_price()) && new BigDecimal(orderProduct.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getActivity_price()));
				payPrice=payPrice.add(payMoney);
			}else if(StringUtil.isNotNull(orderProduct.getDiscount_price()) && new BigDecimal(orderProduct.getDiscount_price()).compareTo(BigDecimal.ZERO) >= 0){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getDiscount_price()));
				payPrice=payPrice.add(payMoney);
			}else{
				payPrice=totalPrice;
			}
			
			
			
			//限购调整specVo
			PageData pd = new PageData();
			pd.put("productId", product.getProduct_id());
			pd.put("specId", specVo.getSpec_id());
			
			pd.put("hasBuy", "hasBuy");//已付款的订单
			pd.put("customerId", customer.getCustomer_id());
			int hasBuy = 0;
			int restCopy = 100;
			if((specVo.getLimit_max_copy() != -1)
					&& StringUtils.isNotBlank(specVo.getLimit_start_time())
					&& StringUtils.isNotBlank(specVo.getLimit_end_time())){
				Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
				Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
				if(dt.after(new Date())&&start.before(new Date())){//限购时间内
						if(specVo.getLimit_max_copy()!=null&&specVo.getLimit_max_copy()>0){//说明是限购的
							pd.put("startTime", specVo.getLimit_start_time());
							pd.put("endTime", specVo.getLimit_end_time());
							List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(pd);//查出之前下过的单
							if(orderProductlist!=null && orderProductlist.size()>0){
								for (OrderProductVo orderProductVo : orderProductlist) {
									hasBuy+=orderProductVo.getQuantity();
								}
								restCopy = specVo.getLimit_max_copy()-hasBuy;
								if(restCopy<=0){
									map.put("CannotBuy",product.getProduct_name()+"已经超出最大限购数量，不可再购买" );
									return map;
								}
							}
						}
				}
				
			}
			
		}
		map.put("payPrice", payPrice);
		map.put("price", totalPrice);
		map.put("listOrderProducts", listOrderProducts);
		
		return map;
	}

	//获取订单商品价格
	public Map<String,Object> getOrderPrice(String[] productIdArray,String[] specIdArray,String[] amountArray,CustomerVo customer, int activityFlag, int specialId){
		
		OrderProductVo orderProduct=null;
		List<OrderProductVo> listOrderProducts=new ArrayList<OrderProductVo>();
		
		BigDecimal totalPrice=new BigDecimal(0);
		BigDecimal payPrice=new BigDecimal(0);
		Map<String,Object> map=new HashMap<String, Object>();
		BigDecimal totalPayMoney = BigDecimal.ZERO;
		
		//查询购买的商品价格信息
		PageData pageData=new PageData();
		for (int i = 0; i < productIdArray.length; i++) {
			BigDecimal price=new BigDecimal(0);
			
			pageData.put("productId", productIdArray[i]);
			pageData.put("specId", specIdArray[i]);
			
			pageData.put("status","1");
			ProductVo product = productManager.selectProductByOption(pageData);
			if(null==product){
				map.put("exist","not");
				return map;
			}
			//判断库存
			ProductSpecVo specVo = productSpecManager.queryProductSpecByOption(pageData);
			
			if(specVo!=null&&specVo.getStock_copy()!=null){
				int restCopy = specVo.getStock_copy()-Integer.parseInt(amountArray[i]);
				if(restCopy<0){
					map.put("noStockCopy",product.getProduct_name()+"没有足够的库存");
					return map;
				}
			}
			
			
			logger.info("商品id是productId---"+productIdArray[i]+"--商品名称为"+product.getProduct_name()+"--------规格specId-----"+specIdArray[i]);
			
			//List<ProductSpecVo> listProduct=productSpecManager.queryListProductSpec(pageData);
			List<ActivityProductVo> listActivtyProduct = new ArrayList<ActivityProductVo>();//查看商品是否存在活动
			pageData.put("activityFlag", activityFlag);
			if(activityFlag == 0){
				pageData.put("activityId", specialId);
			}
			listActivtyProduct = activityProductManager.queryList(pageData);
			
			//优惠多少钱
			BigDecimal leftMoney=null;
			
//			PreProductVo preProductVo = new PreProductVo();
			SpecialVo svo = new SpecialVo();
			//计算此商品的总价格
			if(listActivtyProduct!=null&&listActivtyProduct.size()>=0){
				String actPrice = "";
				for (ActivityProductVo spec : listActivtyProduct) {//符合时间的活动
					PageData spd=new PageData();
//					if(spec.getActivity_type()==1){//预售
//						spd.put("preId", spec.getActivity_id());
//						 preProductVo = preProductManager.selectPreProductByOption(spd);
//						if(preProductVo!=null){
//							Date dt = DateUtil.stringToDate(preProductVo.getRaiseEnd());
//							Date start = DateUtil.stringToDate(preProductVo.getRaiseStart());
//							if(dt.after(new Date())&&start.before(new Date())){
//								actPrice=spec.getActivity_price();
//								logger.info(preProductVo.getPreName()+"---此预售商品活动价格为------"+actPrice);
//								break;
//							}
//						}
//						
//					}else{//专题
						spd.put("specialId", spec.getActivity_id());
						spd.put("status", 1);
						 svo = specialMananger.selectSpecialByOption(spd);
						if(svo!=null){
							Date dt = DateUtil.stringToDate(svo.getEndTime());
							Date start = DateUtil.stringToDate(svo.getStartTime());
							if(dt.after(new Date())&&start.before(new Date())){
								actPrice=spec.getActivity_price();
								logger.info(svo.getSpecialName()+"---此专题商品活动价格为------"+actPrice);
								break;
							}
						}
//					}
					
				}
				if(actPrice!=""){
					leftMoney=new BigDecimal(actPrice);
				}
				
			}
			
			if(null!=product&&null!=specVo){
				orderProduct=new OrderProductVo();
				orderProduct.setProduct_id(product.getProduct_id());
				orderProduct.setProduct_name(product.getProduct_name());
				orderProduct.setSpec_id(specVo.getSpec_id());
				orderProduct.setSpec_name(specVo.getSpec_name());
				orderProduct.setPrice(specVo.getSpec_price());
				price = new BigDecimal(specVo.getSpec_price());
				
				orderProduct.setQuantity(Integer.parseInt(amountArray[i]));
				
			}
			if(null !=leftMoney){
				orderProduct.setActivity_price(String.valueOf(leftMoney));
			}else if(StringUtil.isNoNull(specVo.getDiscount_price()) && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) == 1 && activityFlag == 1){
				orderProduct.setDiscount_price(specVo.getDiscount_price()+"");
			}
			
			orderProduct.setIs_evaluate(0);
			orderProduct.setIfCoupon(product.getIfCoupon());
			listOrderProducts.add(orderProduct);
			
			price=price.multiply(new BigDecimal(amountArray[i]));          //计算出商品总价格
			totalPrice=totalPrice.add(price);
			
			if(StringUtil.isNotNull(orderProduct.getActivity_price())){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getActivity_price()));
				payPrice=payPrice.add(payMoney);
				totalPayMoney = totalPayMoney.add(payMoney);
			}else if(StringUtil.isNotNull(orderProduct.getDiscount_price()) && activityFlag == 1){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getDiscount_price()));
				payPrice=payPrice.add(payMoney);
				totalPayMoney = totalPayMoney.add(payMoney);
			}else{
				totalPayMoney = totalPayMoney.add(price);
				payPrice=totalPayMoney;
			}
			
			
			
			//限购调整specVo
			PageData pd = new PageData();
			pd.put("productId", product.getProduct_id());
			pd.put("specId", specVo.getSpec_id());
			
			pd.put("hasBuy", "hasBuy");//已付款的订单
			pd.put("customerId", customer.getCustomer_id());
			int hasBuy = 0;
			int restCopy = 100;
			if(StringUtils.isNotBlank(specVo.getLimit_start_time())&&StringUtils.isNotBlank(specVo.getLimit_end_time())){
				Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
				Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
				if(dt.after(new Date())&&start.before(new Date())){//限购时间内
						if(specVo.getLimit_max_copy()!=null&&specVo.getLimit_max_copy()>0){//说明是限购的
							pd.put("startTime", specVo.getLimit_start_time());
							pd.put("endTime", specVo.getLimit_end_time());
							List<OrderProductVo> orderProductlist = orderProductManager.findListOrderProduct(pd);//查出之前下过的单
							if(orderProductlist!=null && orderProductlist.size()>0){
								for (OrderProductVo orderProductVo : orderProductlist) {
									hasBuy+=orderProductVo.getQuantity();
								}
								restCopy = specVo.getLimit_max_copy()-hasBuy;
								if(restCopy<=0){
									map.put("CannotBuy",product.getProduct_name()+"已经超出最大限购数量，不可再购买" );
									return map;
								}
							}
						}
				}
				
			}
		}
		
		if(activityFlag == 1){
			logger.info("getOrderPrice()-----加入满减活动  ------------------------开始------------");
			//加入满减活动
			PageData mcps = new PageData();
			mcps.clear();
			mcps.put("startTime", new Date());
			mcps.put("endTime", new Date());
			mcps.put("status",1);
			List<SalePromotionVo> listSalePromotion = salePromotionManager.queryList(mcps);
			BigDecimal buyAmount = BigDecimal.ZERO;
			if(listSalePromotion!=null && listSalePromotion.size()>0){
				for (SalePromotionVo salePromotionVo : listSalePromotion) {
					PageData sspd = new PageData();
					sspd.put("promotionId", salePromotionVo.getPromotionId());
					List<PromotionSpecVo> listPromotions = promotionSpecManager.queryList(sspd);
					if(listPromotions!=null && listPromotions.size()>0){//根据活动的id查出所有参加此活动的商品规格
						for (PromotionSpecVo promotionSpecVo : listPromotions) {
							boolean outbreak = false;
							for (OrderProductVo orderProductVo : listOrderProducts) {
								if(orderProductVo.getProduct_id().equals(promotionSpecVo.getProductId())&&orderProductVo.getSpec_id().equals(promotionSpecVo.getSpecId())){//相等说明是参加满减活动的
									//计算支付金额
									BigDecimal totalPay = BigDecimal.ZERO;
									if(StringUtils.isNotBlank(orderProductVo.getActivity_price()) && new BigDecimal(orderProductVo.getActivity_price()).compareTo(BigDecimal.ZERO)==1){
										totalPay = new BigDecimal(orderProductVo.getActivity_price()).multiply(new BigDecimal(orderProductVo.getQuantity()));
									}else if (StringUtils.isNotBlank(orderProductVo.getDiscount_price()) && new BigDecimal(orderProductVo.getDiscount_price()).compareTo(BigDecimal.ZERO)==1){
										totalPay = new BigDecimal(orderProductVo.getDiscount_price()).multiply(new BigDecimal(orderProductVo.getQuantity()));
									}else{
										totalPay = new BigDecimal(orderProductVo.getPrice()).multiply(new BigDecimal(orderProductVo.getQuantity()));
									}
									buyAmount = buyAmount.add(totalPay);
									if(totalPrice.compareTo(salePromotionVo.getFullMoney())>=0){//满多少减多少
										payPrice = payPrice.subtract(salePromotionVo.getCutMoney());//支付金额减多少
										outbreak = true;
										map.put("salePromotionVo", salePromotionVo);
										
										//订单详情记录满减活动
										orderProductVo.setPromotion_id(promotionSpecVo.getPromotionId());
										orderProductVo.setFull_money(salePromotionVo.getFullMoney());
										orderProductVo.setCut_money(salePromotionVo.getCutMoney());
										logger.info("getOrderPrice()-----商品参加了满"+salePromotionVo.getFullMoney()+"减"+salePromotionVo.getCutMoney()+"----活动");
										break;
									}
								}
							}
							if(outbreak){//如果第一个商品就满足了，就退出循环
								logger.info("getOrderPrice()-----商品参加了"+salePromotionVo.getPromotionName()+"----活动");
								break;
							}
							
						}
					}
				}
			}
			logger.info("getOrderPrice()-----加入满减活动  ------------------------结束------------");
		}
		map.put("payPrice", payPrice);
		map.put("price", totalPrice);
		map.put("listOrderProducts", listOrderProducts);
		
		return map;
	}
	
	
	// 输出返回结果
	private String getReturnContent() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml>");
		buffer.append("<return_code><![CDATA[SUCCESS]]></return_code>");
		buffer.append("<return_msg><![CDATA[OK]]></return_msg>");
		buffer.append("</xml>");
		return buffer.toString();
	}
	
	private String getFailedReturnContent() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml>");
		buffer.append("<return_code><![CDATA[FAILED]]></return_code>");
		buffer.append("<return_msg><![CDATA[NOTOK]]></return_msg>");
		buffer.append("</xml>");
		return buffer.toString();
	}
		
	// 将类成员放入map中
	private Map<String, String> getFieldMap(WxNotifyData notifyData) {
		Map<String, String> map = new HashMap<String, String>();

		Field[] fields = notifyData.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (field.get(notifyData) != null) {
					map.put(field.getName(), String.valueOf(field.get(notifyData)));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error(e, e);
			}
		}

		return map;
	}
		
}
