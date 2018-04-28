package com.kingleadsw.betterlive.controller.wx.invite;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.InviteRecordManager;
import com.kingleadsw.betterlive.biz.InviteRewardManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SysInviteManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.InviteRecordVo;
import com.kingleadsw.betterlive.vo.InviteRewardVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.SysInviteVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

@Controller
@RequestMapping(value = "/weixin/customerinvite")
public class WxCustomerInviteController  extends AbstractWebController {
	private static Logger logger = Logger.getLogger(WxCustomerInviteController.class);
	
	@Autowired
	private SysInviteManager sysInviteManager;
	
	@Autowired
	private InviteRewardManager inviteRewardManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private InviteRecordManager inviteRecordManager;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private MessageManager messageManager;
	
	
	@RequestMapping(value="/gotoInvited")
	public ModelAndView gotoInvited(HttpServletRequest request){
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		String backUrl = pd.getString("backUrl");
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin?backUrl="+backUrl);
		}
		ModelAndView mv=new ModelAndView("weixin/invite/wx_invite_friend");
		mv.addObject("customer",customer);
		mv.addObject("backUrl", backUrl);
		return mv;
	}
	/**
	 * 邀请理由
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryInviteReasons")
	@ResponseBody
	public Map<String,Object> queryInviteReasons(HttpServletRequest request){
		
		try {
			PageData pd = new PageData();
			pd.put("dictType", 2);
			List<SysInviteVo> invites = sysInviteManager.queryBySortListPage(pd);
			if(invites == null){
				invites = new ArrayList<SysInviteVo>();
			}
			return CallBackConstant.SUCCESS.callback(invites);
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/queryInviteReasons", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 邀请好友榜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryInviteRankingList")
	@ResponseBody
	public Map<String,Object> queryInviteRankingList(HttpServletRequest request){
		try {
			PageData pd = new PageData();
			pd.put("rowStart", 0);
			pd.put("pageSize", 10);
			List<InviteRewardVo> inviteRankings = inviteRewardManager.queryInviteRankingList(pd);
			if(inviteRankings == null){
				inviteRankings = new ArrayList<InviteRewardVo>();
			}
			return CallBackConstant.SUCCESS.callback(inviteRankings);
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/queryInviteRankingList", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 每日首次邀请好友的券信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryFirstInviteCouponTip")
	@ResponseBody
	public Map<String,Object> queryFirstInviteCouponTip(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("titleInfo", "每日首次分享最高可获得10元优惠券");
			map.put("couponMoney", "10");
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/queryFirstInviteCouponTip", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 邀请注册送券信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryInviteCouponsInfo")
	@ResponseBody
	public Map<String,Object> queryInviteCouponsInfo(HttpServletRequest request){
		try {
			PageData pd = new PageData();
			List<SysInviteVo> inviteCouponInfos = this.sysInviteManager.queryInviteCouponsInfo(pd);
			if(inviteCouponInfos == null){
				inviteCouponInfos = new ArrayList<SysInviteVo>();
			}
			return CallBackConstant.SUCCESS.callback(inviteCouponInfos);
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/queryInviteCouponsInfo", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 邀请注册送券信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/shareRegister")
	public ModelAndView shareRegister(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("weixin/invite/wx_customer_invite");
		try {
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getString("customerId"))){
				mav.addObject("tipsTitle", "访问出错");
				mav.addObject("tipsContent", "您访问的页面不存在");
				mav.setViewName("/weixin/fuwubc");
				return mav;
			}
			
			if(StringUtil.isNull(pd.getString("sysInviteId"))){
				mav.addObject("tipsTitle", "访问出错");
				mav.addObject("tipsContent", "您访问的页面不存在");
				mav.setViewName("/weixin/fuwubc");
				return mav;
			}
			
			if(StringUtil.isNull(pd.getString("recordId"))){
				mav.addObject("tipsTitle", "访问出错");
				mav.addObject("tipsContent", "您访问的页面不存在");
				mav.setViewName("/weixin/fuwubc");
				return mav;
			}
			
			//邀请理由
			SysInviteVo sysi = this.sysInviteManager.queryOne(pd);
			if(sysi != null){
				mav.addObject("inviteInfo", sysi);
			}
			//邀请人信息
			CustomerVo customerVo = this.customerManager.selectByPrimaryKey(pd.getInteger("customerId"));
			if(customerVo == null){
				customerVo = new CustomerVo();
			}
			
			CustomerVo myCustVo = Constants.getCustomer(request);
			int orderNum = 0;
			if(myCustVo == null){
				myCustVo = new CustomerVo();
			}else{
				orderNum = orderManager.queryOrderCountByCust(myCustVo.getCustomer_id());
			}
			
			mav.addObject("customerVo", customerVo);
			mav.addObject("myCust", myCustVo);
			mav.addObject("orderNum", orderNum);
			mav.addObject("recordId", pd.getString("recordId"));
			return mav;
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/shareRegister", e);
			mav.addObject("tipsTitle", "访问出错");
			mav.addObject("tipsContent", "您访问的页面不存在");
			mav.setViewName("/weixin/fuwubc");
			return mav;
		}
	}
	
	
	
	
	/**
	 * 领取优惠券
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/receiveHandredCoupon",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> receiveHandredCoupon(HttpServletRequest request) throws ParseException {
		logger.info("/weixin/customerinvite/receiveHandredCoupon  begin");
		
		try{
			PageData pd = this.getPageData();
			//用户
			CustomerVo customer=Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户访问超时");
			}
			String couponIds = WebConstant.SHARE_REGISTER_HUNDRED_COUPONS;
			if(StringUtil.isNull("couponIds")){
				return CallBackConstant.PARAMETER_ERROR.callbackError("暂无优惠券");
			}
			//判断是不是新用户
			if(customer!=null){//当用户不为空的时候，判断有没有领取过这些券
				pd.put("customerId", customer.getCustomer_id());
				pd.put("couponIds", couponIds.split(","));
				List<CouponInfoVo> list =couponInfoManager.findListUserCoupon(pd);
				if(list!=null && list.size()>0){//说明已经领取过了
					return CallBackConstant.DATA_HAD_FOUND.callback();
				}
			}
			
			PageData couponParam = new PageData();
			couponParam.put("couponIds", couponIds);
			couponParam.put("couponType", 2);
			couponParam.put("customerId", customer.getCustomer_id());
			List<CouponManagerVo> newUserCoupon = this.couponManagerManager.queryNewUserCouponList(couponParam);
			if(newUserCoupon==null || newUserCoupon.size()==0){//说明是老用户
				return CallBackConstant.USER_MISMATCH.callback();
			}
			for (CouponManagerVo couponManager : newUserCoupon) {
				String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
				String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
				String title = "恭喜您获得了一张优惠券";
				CouponInfoVo couponInfoVo=new CouponInfoVo();
				
				couponInfoVo.setMobile(customer.getMobile());
				couponInfoVo.setCustomer_id(customer.getCustomer_id());
				
				BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
				
				couponInfoVo.setCm_id(couponManager.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期'
				couponInfoVo.setFrom_user_id(Integer.parseInt(couponManager.getCreate_by()));
				
				int result = couponInfoManager.insertUserCoupon(couponInfoVo);
				if(result > 0){
					//发系统消息给用户
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(2);
					msgVo.setSubMsgType(1);
					msgVo.setMsgTitle(title);
					String couponContent = "";
					if(StringUtil.isNotNull(couponManager.getCoupon_content())){
						couponContent = couponManager.getCoupon_content();
					}
					msgVo.setMsgDetail(couponContent);
					msgVo.setIsRead(0);
					msgVo.setCustomerId(customer.getCustomer_id());
					msgVo.setObjId(couponInfoVo.getCoupon_id());
					messageManager.insert(msgVo);
				}
			}
			
			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/receiveHandredCoupon  error", e);
			return CallBackConstant.FAILED.callbackError("领取优惠券异常");
		}

	}
	/**
	 * 分享之后产品列表
	 * @param
	 * @return
	 * @author zhangjing 2018年4月25日 上午11:53:11
	 */
	@RequestMapping(value="/queryProdcuts")
	@ResponseBody
	public Map<String,Object> queryProdcuts(HttpServletRequest request){
		try{
			PageData pd = this.getPageData();
			PageView pv = new PageView();
			if (pd.get("pageView") == null) {
				pv.setPageSize(10);
				pd.put("pageView", pv);
			}else{
				pv = (PageView) pd.get("pageView");
			}
			List<ProductVo> list = productManager.queryRegisterProductListPage(pd);
			if(list==null || list.size()==0){
				return CallBackConstant.DATA_NOT_FOUND.callback();
			}
			pv.setRecords(list);
			return CallBackConstant.SUCCESS.callback(pv);
		}catch (Exception e) {
			logger.error("/app/customerinvite/queryProdcuts", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
	
	
	
	/**
	 * 增加分享记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addInviteRecord")
	@ResponseBody
	public Map<String,Object> addInviteRecord(HttpServletRequest request){
		try {
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getString("inviteReasonId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("邀请信息错误");
			}
			CustomerVo customer = Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			InviteRecordVo inviteRecordVo = new InviteRecordVo();
			inviteRecordVo.setInviteId(customer.getCustomer_id());
			inviteRecordVo.setInviteReasonId(pd.getInteger("inviteReasonId"));
			inviteRecordVo.setInviteFlag(0);
			inviteRecordVo.setCustomerId(0);
			int count = inviteRecordManager.insert(inviteRecordVo);
			if(count > 0){
				return CallBackConstant.SUCCESS.callback(inviteRecordVo.getRecordId());
			}
			
			return CallBackConstant.FAILED.callbackError("邀请出错");
		} catch (Exception e) {
			logger.error("/app/customerinvite/queryInviteCouponsInfo", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 分享成功处理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/inviteCallback")
	@ResponseBody
	public Map<String,Object> inviteCallback(HttpServletRequest request){
		try {
			PageData pd = this.getPageData();
			if(StringUtil.isNull(pd.getString("recordId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("分享出错");
			}
			CustomerVo customer = Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			Map<String, Object> map = this.inviteRewardManager.checkFirstInvite(customer, pd.getInteger("recordId"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/inviteCallback", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 获取新人券
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRegCoupons")
	@ResponseBody
	public Map<String,Object> queryRegCoupons(HttpServletRequest request){
		try {
			PageData pd = this.getPageData();
			List<Map<String, Object>> list = this.sysInviteManager.queryRegCouponListPage(pd);
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			return CallBackConstant.SUCCESS.callback(list);
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/queryRegCoupons", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 新用户领券
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/receiveCoupon")
	@ResponseBody
	public Map<String,Object> receiveCoupon(HttpServletRequest request){
		try {
			PageData pd = this.getPageData();
			CustomerVo customerVo = Constants.getCustomer(request);
			if(customerVo == null || customerVo.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			if(StringUtil.isNull(pd.getString("couponId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("领取红包出错了~");
			}
			if(StringUtil.isNull(pd.getString("recordId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("领取红包出错了~");
			}
			
			int orderNum = orderManager.queryOrderCountByCust(customerVo.getCustomer_id());
			if(orderNum > 0){
				return CallBackConstant.DATA_HAD_FOUND.callbackError("您已经是会员了");
			}
			
			pd.put("customerId", customerVo.getCustomer_id());
			UserSingleCouponVo usCoupon = userSingleCouponManager.queryOne(pd);
			if(usCoupon != null && usCoupon.getUserSingleId().intValue() > 0){
				return CallBackConstant.FAILED.callbackError("您已经领取过券了，可立即购买");
			}
			
			Map<String, Object> map = inviteRewardManager.receiveCoupon(customerVo.getCustomer_id(), customerVo.getMobile(), pd.getInteger("couponId"), pd.getInteger("recordId"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/receiveCoupon", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 检查是否有券可领
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkMoreCoupon")
	@ResponseBody
	public Map<String,Object> checkMoreCoupon(HttpServletRequest request){
		try {
			PageData pd = this.getPageData();
			
			CustomerVo customerVo = Constants.getCustomer(request);
			if(customerVo == null || customerVo.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			List<SingleCouponVo> listSc = this.singleCouponManager.queryEffectiveCouponListNew(pd);
			SingleCouponVo sc = new SingleCouponVo();
			if(listSc != null && listSc.size() > 0){
				sc = listSc.get(0);
			}
			return CallBackConstant.SUCCESS.callback(sc.getCouponId());
		} catch (Exception e) {
			logger.error("/weixin/customerinvite/checkMoreCoupon", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}