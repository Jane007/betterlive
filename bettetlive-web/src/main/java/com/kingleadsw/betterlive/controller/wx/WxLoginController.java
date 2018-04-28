package com.kingleadsw.betterlive.controller.wx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerFansManager;
import com.kingleadsw.betterlive.biz.CustomerLotteryManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.biz.InviteRecordManager;
import com.kingleadsw.betterlive.biz.InviteRewardManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CustomerFansVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GiftCardVo;
import com.kingleadsw.betterlive.vo.InviteRecordVo;
import com.kingleadsw.betterlive.vo.SystemLevelVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;
@Controller
@RequestMapping("/weixin")
public class WxLoginController extends AbstractWebController{
	
	@Autowired
    private CustomerManager customerManager;
	@Autowired
	private RedisService redisService;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CollectionManager collectionManager;
	@Autowired
	private CustomerFansManager customerFansManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private GiftCardManager giftCardManager;
	@Autowired
	private InviteRewardManager inviteRewardManager;
	@Autowired
	private InviteRecordManager inviteRecordManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private SystemLevelManager systemLevelManager;
	@Autowired
	private CustomerLotteryManager customerLotteryManager;
	
	@RequestMapping(value="/toMyIndex")
	public ModelAndView toMyIndex(HttpServletRequest request,ModelAndView modelView){
		ModelAndView modelAndView = new ModelAndView("weixin/usercenter/wx_user_index");
		
		String source = request.getParameter("source");
		CustomerVo customer = Constants.getCustomer(request); 
		if(StringUtils.isNotBlank(source)){
			modelAndView.addObject("source",source);
		}
	
		int circleCount = 0;	//动态数量
		
		int concernedCount = 0; //我的粉丝数量
		int customerCount = 0;	//我关注的数量
		int collectionCount = 0; //收藏数量
		int couponCount = 0;	//红包数量
		int uscCount = 0;		//单品红包数量
		int unreadMessageCount=0;//未读消息
		BigDecimal myGiftMoney = BigDecimal.ZERO; //礼品卡的钱
		int requirementIntegral = 0;	//当前等级要求积分
		Integer customerId = 0;
		
		if(customer != null && customer.getCustomer_id() != null){
			customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
			customerId = customer.getCustomer_id();
			PageData cl = new PageData();
			cl.put("customerId", customerId);
			cl.put("articleFrom", 1);
			cl.put("statusLine", "1,3");
			//我的动态数量
			circleCount = specialArticleManager.querySpecialArticleCount(cl);
			
			cl.clear();
			cl.put("customerId", customerId);
			CustomerFansVo fansVo = customerFansManager.queryFansCount(cl);
			
			if(fansVo != null){
				concernedCount = fansVo.getConcernedCount();
				customerCount = fansVo.getCustomerCount();
			}
			
			cl.put("collectionTypes", "1,3,4");
			collectionCount = collectionManager.queryCollectionCount(cl);
			
			cl.clear();
			cl.put("canusecoupon", 0);
			cl.put("customerId", customer.getCustomer_id());
			List<CouponInfoVo> couponList = couponInfoManager.findUserCouponListPage(cl);
			
			if(couponList != null && couponList.size() > 0){
				couponCount = couponList.size();
			}
			
			List<UserSingleCouponVo> uscs = userSingleCouponManager.queryListPage(cl);
			
			if(uscs != null && uscs.size() > 0){
				uscCount = uscs.size();
			}
			
			cl.clear();
			cl.put("customerId", customerId);
			cl.put("status", 1);
			List<GiftCardVo> myGiftCards = giftCardManager.findListGiftCard(cl);
			
			
			if (myGiftCards != null &&  myGiftCards.size() > 0) {
				BigDecimal sum = giftCardManager.querySumCardMoney(cl);
				BigDecimal used = giftCardManager.querySumUsedMoney(cl);
				myGiftMoney = sum.subtract(used);
			}
			
			cl.clear();
			cl.put("customerId", customerId);
			unreadMessageCount = messageManager.selectCountByUnread(cl);
			
			
			PageData sysLevelParams = new PageData();
			sysLevelParams.put("status", 0);
			sysLevelParams.put("systemLevelId", customer.getLevelId());
			SystemLevelVo sysLevelVo = systemLevelManager.queryOne(sysLevelParams);
			if (sysLevelVo != null && sysLevelVo.getSystemLevelId() != null) {
				requirementIntegral = sysLevelVo.getRequirementIntegral();
			}
		}
		
		//订单数量查询
		Map<String, Integer> orderNums = this.orderManager.queryMyOrderNums(customerId);
		//每日签到活动信息
		Map<String, Object> lotterySignInfo = this.customerLotteryManager.queryCustomerSignInfo(customerId);
		
		//订单
		modelAndView.addObject("waitPayOrderNum", orderNums.get("waitPayOrderNum"));
		modelAndView.addObject("waitDeliveryOrderNum", orderNums.get("waitDeliveryOrderNum"));
		modelAndView.addObject("waitReceiveOrderNum", orderNums.get("waitReceiveOrderNum"));
		modelAndView.addObject("waitCommentOrderNum", orderNums.get("waitCommentOrderNum"));
		
		modelAndView.addObject("circleCount", circleCount);
		modelAndView.addObject("concernedCount", concernedCount);
		modelAndView.addObject("customerCount", customerCount);
		modelAndView.addObject("collectionCount", collectionCount);
		modelAndView.addObject("couponCount", couponCount);
		modelAndView.addObject("uscCount", uscCount);
		modelAndView.addObject("myGiftMoney", myGiftMoney);
		modelAndView.addObject("unreadMessageCount", unreadMessageCount);
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("requirementIntegral", requirementIntegral);
		
		modelAndView.addObject("lotterySignStatus", lotterySignInfo.get("lotterySignStatus"));
		modelAndView.addObject("checkLottery", lotterySignInfo.get("checkLottery"));
		modelAndView.addObject("serialSign", lotterySignInfo.get("serialSign"));
		return modelAndView;
	}
	
	@RequestMapping(value="/tologin")
	public ModelAndView toLogin(HttpServletRequest request,ModelAndView modelView){
		ModelAndView modelAndView=new ModelAndView("weixin/usercenter/wx_user_index");
		String source = request.getParameter("source");
		CustomerVo customer =Constants.getCustomer(request); 
		if(StringUtils.isNotBlank(source)){
			modelAndView.addObject("source",source);
		}
		if(customer == null || customer.getCustomer_id() == null){
			modelAndView.setViewName("weixin/wx_login");
			return modelAndView;
		}
		if (StringUtil.isNull(customer.getMobile())) {
			return new ModelAndView("redirect:/weixin/usercenter/toBoundPhone");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if (customer == null) {
			modelAndView.setViewName("weixin/wx_login");
			return modelAndView;
		}
		
		int unreadMessageCount=0;//未读消息
		
		PageData cl = new PageData();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("articleFrom", 1);
		cl.put("statusLine", "1,3");
		//我的动态数量
		int circleCount = specialArticleManager.querySpecialArticleCount(cl);
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		CustomerFansVo fansVo = customerFansManager.queryFansCount(cl);
		int concernedCount = 0; //我的粉丝数量
		int customerCount = 0;	//我关注的数量
		if(fansVo != null){
			concernedCount = fansVo.getConcernedCount();
			customerCount = fansVo.getCustomerCount();
		}
		
		cl.put("collectionTypes", "1,3,4");
		int collectionCount = collectionManager.queryCollectionCount(cl);
		
		cl.clear();
		cl.put("canusecoupon", 0);
		cl.put("customerId", customer.getCustomer_id());
		List<CouponInfoVo> couponList = couponInfoManager.findUserCouponListPage(cl);
		int couponCount = 0;
		if(couponList != null && couponList.size() > 0){
			couponCount = couponList.size();
		}
		
		List<UserSingleCouponVo> uscs = userSingleCouponManager.queryListPage(cl);
		int uscCount = 0;
		if(uscs != null && uscs.size() > 0){
			uscCount = uscs.size();
		}
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("status", 1);
		List<GiftCardVo> myGiftCards = giftCardManager.findListGiftCard(cl);

		BigDecimal myGiftMoney = BigDecimal.ZERO;
		if (myGiftCards != null &&  myGiftCards.size() > 0) {
			BigDecimal sum = giftCardManager.querySumCardMoney(cl);
			BigDecimal used = giftCardManager.querySumUsedMoney(cl);
			myGiftMoney = sum.subtract(used);
		}
		
		cl.put("customerId", customer.getCustomer_id());
		unreadMessageCount = messageManager.selectCountByUnread(cl);
		
		int requirementIntegral = 0;	//当前等级要求积分
		PageData sysLevelParams = new PageData();
		sysLevelParams.put("status", 0);
		sysLevelParams.put("systemLevelId", customer.getLevelId());
		SystemLevelVo sysLevelVo = systemLevelManager.queryOne(sysLevelParams);
		if (sysLevelVo != null && sysLevelVo.getSystemLevelId() != null) {
			requirementIntegral = sysLevelVo.getRequirementIntegral();
		}

		//订单数量查询
		Map<String, Integer> orderNums = this.orderManager.queryMyOrderNums(customer.getCustomer_id());
		//每日签到活动信息
		Map<String, Object> lotterySignInfo = this.customerLotteryManager.queryCustomerSignInfo(customer.getCustomer_id());
		
		modelAndView.addObject("waitPayOrderNum", orderNums.get("waitPayOrderNum"));
		modelAndView.addObject("waitDeliveryOrderNum", orderNums.get("waitDeliveryOrderNum"));
		modelAndView.addObject("waitReceiveOrderNum", orderNums.get("waitReceiveOrderNum"));
		modelAndView.addObject("waitCommentOrderNum", orderNums.get("waitCommentOrderNum"));
		modelAndView.addObject("circleCount", circleCount);
		modelAndView.addObject("concernedCount", concernedCount);
		modelAndView.addObject("customerCount", customerCount);
		modelAndView.addObject("collectionCount", collectionCount);
		modelAndView.addObject("couponCount", couponCount);
		modelAndView.addObject("uscCount", uscCount);
		modelAndView.addObject("myGiftMoney", myGiftMoney);
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("unreadMessageCount", unreadMessageCount);
		modelAndView.addObject("requirementIntegral", requirementIntegral);
		modelAndView.addObject("lotterySignStatus", lotterySignInfo.get("lotterySignStatus"));
		modelAndView.addObject("checkLottery", lotterySignInfo.get("checkLottery"));
		modelAndView.addObject("serialSign", lotterySignInfo.get("serialSign"));
		/*redisService.setex(Keys.APP_TOKEN_PREFIX + customer.getToken(), customer.getMobile(), WebConstant.TOKEN_TIME);
	    redisService.setObject(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), customer);
	    redisService.expireKey(Keys.APP_CUSTOMER_PREFIX + customer.getMobile(), WebConstant.TOKEN_TIME);*/
		return modelAndView;
	}

	
	@RequestMapping(value="/toSubjectlogin")
	public ModelAndView toSubjectlogin(HttpServletRequest request,ModelAndView modelView){
		String backUrl = request.getParameter("backUrl");
		ModelAndView modelAndView=new ModelAndView("weixin/usercenter/wx_user_index");
		String source = request.getParameter("source");
		CustomerVo customer =Constants.getCustomer(request); 
		if(StringUtils.isNotBlank(source)){
			modelAndView.addObject("source",source);
		}
		if(customer == null || customer.getCustomer_id()==null){
			modelAndView.setViewName("weixin/wx_login");
			return modelAndView;
		}
		if(StringUtil.isNull(customer.getMobile())){
			 return new ModelAndView("redirect:/weixin/usercenter/toBoundPhone?backUrl=" + backUrl);
		}
		customer =  customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if (customer == null) {
			return new ModelAndView("redirect:/weixin/usercenter/toBoundPhone?backUrl=" + backUrl);
		}
		if(StringUtils.isNotBlank(backUrl)){
			 return new ModelAndView("redirect:"+backUrl);
		}


		int unreadMessageCount=0;//未读消息
		
		PageData cl = new PageData();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("articleFrom", 1);
		cl.put("statusLine", "1,3");
		//我的动态数量
		int circleCount = specialArticleManager.querySpecialArticleCount(cl);
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		CustomerFansVo fansVo = customerFansManager.queryFansCount(cl);
		int concernedCount = 0; //我的粉丝数量
		int customerCount = 0;	//我关注的数量
		if(fansVo != null){
			concernedCount = fansVo.getConcernedCount();
			customerCount = fansVo.getCustomerCount();
		}
		
		cl.put("collectionTypes", "1,3,4");
		int collectionCount = collectionManager.queryCollectionCount(cl);
		
		cl.clear();
		cl.put("canusecoupon", 0);
		cl.put("customerId", customer.getCustomer_id());
		List<CouponInfoVo> couponList = couponInfoManager.findUserCouponListPage(cl);
		int couponCount = 0;
		if(couponList != null && couponList.size() > 0){
			couponCount = couponList.size();
		}
		
		List<UserSingleCouponVo> uscs = userSingleCouponManager.queryListPage(cl);
		int uscCount = 0;
		if(uscs != null && uscs.size() > 0){
			uscCount = uscs.size();
		}
		
		cl.clear();
		cl.put("customerId", customer.getCustomer_id());
		cl.put("status", 1);
		List<GiftCardVo> myGiftCards = giftCardManager.findListGiftCard(cl);

		BigDecimal myGiftMoney = BigDecimal.ZERO;
		if (myGiftCards != null &&  myGiftCards.size() > 0) {
			BigDecimal sum = giftCardManager.querySumCardMoney(cl);
			BigDecimal used = giftCardManager.querySumUsedMoney(cl);
			myGiftMoney = sum.subtract(used);
		}
		
		cl.put("customerId", customer.getCustomer_id());
		unreadMessageCount = messageManager.selectCountByUnread(cl);
		
		int requirementIntegral = 0;	//当前等级要求积分
		PageData sysLevelParams = new PageData();
		sysLevelParams.put("status", 0);
		sysLevelParams.put("systemLevelId", customer.getLevelId());
		SystemLevelVo sysLevelVo = systemLevelManager.queryOne(sysLevelParams);
		if (sysLevelVo != null && sysLevelVo.getSystemLevelId() != null) {
			requirementIntegral = sysLevelVo.getRequirementIntegral();
		}

		//订单数量查询
		Map<String, Integer> orderNums = this.orderManager.queryMyOrderNums(customer.getCustomer_id());
		//每日签到活动信息
		Map<String, Object> lotterySignInfo = this.customerLotteryManager.queryCustomerSignInfo(customer.getCustomer_id());
		
		modelAndView.addObject("waitPayOrderNum", orderNums.get("waitPayOrderNum"));
		modelAndView.addObject("waitDeliveryOrderNum", orderNums.get("waitDeliveryOrderNum"));
		modelAndView.addObject("waitReceiveOrderNum", orderNums.get("waitReceiveOrderNum"));
		modelAndView.addObject("waitCommentOrderNum", orderNums.get("waitCommentOrderNum"));
		modelAndView.addObject("circleCount", circleCount);
		modelAndView.addObject("concernedCount", concernedCount);
		modelAndView.addObject("customerCount", customerCount);
		modelAndView.addObject("collectionCount", collectionCount);
		modelAndView.addObject("couponCount", couponCount);
		modelAndView.addObject("uscCount", uscCount);
		modelAndView.addObject("myGiftMoney", myGiftMoney);
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("unreadMessageCount", unreadMessageCount);
		modelAndView.addObject("requirementIntegral", requirementIntegral);
		modelAndView.addObject("lotterySignStatus", lotterySignInfo.get("lotterySignStatus"));
		modelAndView.addObject("checkLottery", lotterySignInfo.get("checkLottery"));
		modelAndView.addObject("serialSign", lotterySignInfo.get("serialSign"));
		return modelAndView;
	}
	@RequestMapping(value="/toRegister")
	public ModelAndView toRegister(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("weixin/wx_register");
		String source =  request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
		if(StringUtil.isNull(source)){
			source = "weixin_browser";
		}
		//分享注册逻辑
		int recordId = 0;
		if(StringUtil.isNotNull(request.getParameter("recordId"))){
			recordId = Integer.parseInt(request.getParameter("recordId"));
		}
		modelAndView.addObject("source", source);
		modelAndView.addObject("recordId", recordId);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/toBindPhone")
	public ModelAndView toBindPhone(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("weixin/wx_bindPhone");
		String source =  request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
		if(StringUtil.isNull(source)){
			source = "weixin_shouquan";
		}
		modelAndView.addObject("source", source);
		return modelAndView;
	}
	
	@RequestMapping(value="/addUserMobile")
	@ResponseBody
	public Map<String,Object> addUserMobile(HttpServletRequest request){
		int count=0;
		Map<String,Object> result=new HashMap<String,Object>();
		String source = request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
		String mobile =  request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("verifycode");
		if(StringUtil.isNull(mobile)){
			result.put("result","fail");
			result.put("msg","请输入手机号码");
			return result;
		}
		if(StringUtil.isNull(password)){
			result.put("result","fail");
			result.put("msg","请输入密码");
			return result;
		}
		if(StringUtil.isNull(code)){
			result.put("result","fail");
			result.put("msg","验证码错误");
			return result;
		}
		
		String redisCode=redisService.getString(mobile); //验证码  保存本地缓存，有效期5分钟
		if(StringUtil.isNull(redisCode)){
			result.put("result","fail");
			result.put("msg","验证码已过期");
			return result;
		}
		if(!code.equals(redisCode)){
			result.put("result","fail");
			result.put("msg","验证码错误");
			return result;
		}
		
		try {
			CustomerVo customerVo = this.customerManager.selectByMobile(mobile);
			if(customerVo != null){
				result.put("result","exist");
				result.put("msg","手机号码已被注册");
				return result;
			} 
			
			CustomerVo ykcustomer =  (CustomerVo)request.getSession().getAttribute("yk_customer");
			if(StringUtil.isNull(source)){
				if(ykcustomer != null && StringUtil.isNotNull(ykcustomer.getSource())){
					source = ykcustomer.getSource();
				}else{
					source="weixin_browser";
				}
			}
			
			int inviteId = 0;
			int recordId = 0;
			if(StringUtil.isNotNull(request.getParameter("recordId")) && StringUtil.isIntger(request.getParameter("recordId"))){
				recordId = Integer.parseInt(request.getParameter("recordId"));
				if(recordId > 0){
					InviteRecordVo irVo = this.inviteRecordManager.selectByPrimaryKey(recordId);
					if(irVo != null){
						inviteId = irVo.getInviteId();
					}
				}
			}
			
			CustomerVo checkCust = Constants.getCustomer(request);
			customerVo = new CustomerVo();
			customerVo.setMobile(mobile);
			customerVo.setPassword(password);
			customerVo.setSource(source);
			customerVo.setCreate_time(DateUtil.getCurrentDate());
			customerVo.setInviterId(inviteId);
			if(ykcustomer != null && checkCust == null){
				customerVo.setSubscribe(ykcustomer.getSubscribe());
				customerVo.setNickname(ykcustomer.getNickname());
				customerVo.setHead_url(ykcustomer.getHead_url());
				customerVo.setOpenid(ykcustomer.getOpenid());
				customerVo.setUnionid(ykcustomer.getUnionid());
				customerVo.setSex(ykcustomer.getSex());
			}
			
			count = customerManager.insertCustomer(customerVo);
			customerVo.setPassword(null);
			Constants.setCustomer(request, customerVo);
			request.getSession().removeAttribute("yk_customer");
			if(count>0){
				//发新手券
				couponInfoManager.insertRegisterCoupon(customerVo);
				
				if(recordId > 0){
					this.inviteRewardManager.giveInviterReward(recordId, customerVo.getCustomer_id());
				}
			}
			
			result.put("result","success");
			result.put("msg", "注册成功!");
			redisService.delKey(mobile);
			return result;
		} catch (Exception e) {
			result.put("result","fail");
			result.put("msg", "注册失败!");
			redisService.delKey(mobile);
			return result;
		}
	}
	
	@RequestMapping(value="/updatePasswordCenter")
	@ResponseBody
	public Map<String,Object> updatePasswordCenter(HttpServletRequest request){
		int count=0;
		Map<String,Object> result=new HashMap<String,Object>();
		String mobile =  request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		String redisCode=redisService.getString(mobile); //验证码  保存本地缓存，有效期5分钟
		if(!(code.equals(redisCode))){
			result.put("result","fail");
			result.put("msg", "您输入的验证码不正确");
			return result;
		}
		CustomerVo customerVo = new CustomerVo();
		customerVo.setMobile(mobile);
		customerVo.setPassword(password);
		count = customerManager.updateByPrimaryKeySelective(customerVo);
		customerVo.setPassword(null);
		if(count>0){
			result.put("result","success");
			result.put("msg", "设置成功!");
			redisService.delKey(mobile);
			return result;
		}else{
			result.put("result","failure");
     		result.put("msg", "设置失败!");
		}
		return result;
	}
	
	
	
	@RequestMapping("/checklogin")
	@ResponseBody
	public Map<String,Object> checkLogin(HttpServletRequest request,ModelAndView modelAndView){
		Map<String,Object> result=new HashMap<String,Object>();
		String mobile=request.getParameter("phoneval");
		String password=request.getParameter("password");
		if(StringUtil.isNull(mobile)){
			result.put("result", "failure");
			result.put("msg", "用户名不能为空");
			return result;
		}
		
		if(StringUtil.isNull(password)){
			result.put("result", "failure");
			result.put("msg", "密码不能为空");
			return result;
		}
		PageData pd = this.getPageData();
		pd.put("password", password);
		pd.put("mobile", mobile);
		CustomerVo customer=customerManager.queryOne(pd);
		if(customer == null){
			result.put("result", "failure");
			result.put("msg", "您输入的账户或密码错误");
			return result;
		}
		
		customer.setPassword(null);
		result.put("result", "success");
		result.put("msg", "登录成功");
		//静默授权用户信息放缓存
		CustomerVo cst = (CustomerVo) request.getSession().getAttribute("yk_customer");
		if(cst!=null){
			if(StringUtils.isNotBlank(cst.getOpenid())){
				customer.setOpenid(cst.getOpenid());
			}
			if(StringUtils.isNotBlank(cst.getUnionid())){
				customer.setUnionid(cst.getUnionid());
			}
			if((StringUtils.isBlank(customer.getHead_url()) || customer.getHead_url().contains("default_photo.png"))
					&& StringUtils.isNotBlank(cst.getHead_url())){
				customer.setHead_url(cst.getHead_url());
			}
		}
		customerManager.updateByPrimaryKey(customer);
		Constants.setCustomer(request, customer);
		return result;
	}
	
	@RequestMapping(value="/dologout")
	public ModelAndView toLogout(HttpServletRequest request){
		//销毁session对象
		Constants.removeUser(request);
		ModelAndView modelAndView=new ModelAndView("weixin/wx_index");
		return modelAndView;
	}
	@RequestMapping(value="/forgetPassword")
	public ModelAndView forgetPassword(){
		ModelAndView modelAndView=new ModelAndView("weixin/wx_forgetpsd");
		return modelAndView;
	}
	
	@RequestMapping("/getLoginInfo")
	@ResponseBody
	public Map<String,Object> getLoginInfo(HttpServletRequest request,ModelAndView modelAndView){
		CustomerVo cust = Constants.getCustomer(request);
		if(cust == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		return CallBackConstant.SUCCESS.callback();
	}
}
