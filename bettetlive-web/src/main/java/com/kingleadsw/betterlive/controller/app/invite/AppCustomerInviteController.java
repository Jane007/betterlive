package com.kingleadsw.betterlive.controller.app.invite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.InviteRecordManager;
import com.kingleadsw.betterlive.biz.InviteRewardManager;
import com.kingleadsw.betterlive.biz.SysInviteManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.InviteRecordVo;
import com.kingleadsw.betterlive.vo.InviteRewardVo;
import com.kingleadsw.betterlive.vo.SysInviteVo;

/**
 * 邀请好友
 *
 */
@Controller
@RequestMapping(value = "/app/customerinvite")
public class AppCustomerInviteController  extends AbstractWebController {
	private static Logger logger = Logger.getLogger(AppCustomerInviteController.class);
	
	@Autowired
	private SysInviteManager sysInviteManager;
	
	@Autowired
	private InviteRewardManager inviteRewardManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private InviteRecordManager inviteRecordManager;
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
			logger.error("/app/customerinvite/queryInviteReasons", e);
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
			logger.error("/app/customerinvite/queryInviteRankingList", e);
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
			logger.error("/app/customerinvite/queryFirstInviteCouponTip", e);
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
			logger.error("/app/customerinvite/queryInviteCouponsInfo", e);
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
			if(StringUtil.isNull(pd.getString("token"))){
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			if(StringUtil.isNull(pd.getString("inviteReasonId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("邀请信息错误");
			}
			CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer == null){
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
			if(StringUtil.isNull(pd.getString("token"))){
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			if(StringUtil.isNull(pd.getString("recordId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("分享出错");
			}
			CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
			if(customer == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			Map<String, Object> map = this.inviteRewardManager.checkFirstInvite(customer, pd.getInteger("recordId"));
			return map;
		} catch (Exception e) {
			logger.error("/app/customerinvite/inviteCallback", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
}
