package com.kingleadsw.betterlive.controller.wx.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.GroupJoinManager;
import com.kingleadsw.betterlive.biz.PostageManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
import com.kingleadsw.betterlive.biz.UserGroupManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.CustomerSourceUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.GroupJoinVo;
import com.kingleadsw.betterlive.vo.PostageVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;
import com.kingleadsw.betterlive.vo.UserGroupVo;

@Controller
@RequestMapping(value = "/weixin/productgroup")
public class WxProductGroupController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(WxProductGroupController.class);
	
	@Autowired
	private SysGroupManager sysGroupManager;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private GroupJoinManager groupJoinManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialMananger specialManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private PostageManager postageManager; 
	/**
	 * 参团参数校验
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkJoinGroup")
	@ResponseBody
	public Map<String, Object> checkJoinGroup(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入团购：参团页");
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback("用户信息不存在");
		}
		int customerId = customer.getCustomer_id();
		
		if(StringUtil.isNull(pd.getString("userGroupId"))){	//团ID
			return CallBackConstant.PARAMETER_ERROR.callbackError("团购ID为空");
		}
		if(StringUtil.isNull(pd.getString("productId"))){	//商品ID
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品ID为空");
		}
		if(StringUtil.isNull(pd.getString("specialId"))){	//专题ID
			return CallBackConstant.PARAMETER_ERROR.callbackError("专题ID为空");
		}
		try {
			
			//专题
			PageData specialParams = new PageData();
			specialParams.put("status", 1);
			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", pd.getString("productId"));
			specialParams.put("specialType", 3);
			specialParams.put("specialId", pd.getString("specialId"));
			SpecialVo specialVo = specialManager.queryOneByProductId(specialParams);
			if(specialVo == null){
				return CallBackConstant.FAILED.callbackError("团购活动已下架");
			}
			
			PageData propd = new PageData();
			propd.put("productId", pd.getString("productId"));
			propd.put("status", 1);
			ProductVo product = productManager.selectProductByOption(propd);
			if(null == product){
				return CallBackConstant.FAILED.callbackError("产品已下架");
			}
			
			pd.put("status", 1);
			UserGroupVo usergroup = this.userGroupManager.queryOne(pd);
			if(usergroup == null){
				return CallBackConstant.FAILED.callbackError("此团已结束");
			}
			pd.put("groupId", usergroup.getGroupId());
			SysGroupVo sysgroup = this.sysGroupManager.queryOne(pd);
			if(sysgroup == null){
				return CallBackConstant.FAILED.callbackError("团购活动不存在");
			}
			if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
				return CallBackConstant.FAILED.callbackError("此团人数已达上限");
			}
			
			specialParams.clear();
			specialParams.put("activityId", pd.getString("specialId"));
			specialParams.put("productId", usergroup.getProductId());
			specialParams.put("specId", usergroup.getSpecId());
			ActivityProductVo ap = this.activityProductManager.queryOne(specialParams);
			if(ap == null){
				return CallBackConstant.FAILED.callbackError("商品规格不存在");
			}
			specialParams.put("proStatus", 1);
			specialParams.put("specStatus", 1);
			ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(specialParams);
			if(specVo == null){
				return CallBackConstant.FAILED.callbackError("商品规格已下架");
			}
			if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
				return CallBackConstant.FAILED.callbackError("库存不够");
			}
			specVo.setActivity_price(ap.getActivity_price());
			
			pd.put("customerId", customerId);
			pd.put("specId", usergroup.getSpecId());
			GroupJoinVo groupjoin = this.groupJoinManager.queryOne(pd);
			if(groupjoin != null){
				if(specVo.getLimit_max_copy() > 0 && specVo.getLimit_end_time() != null
						&& specVo.getLimit_start_time() != null){
					Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
					Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
					if((dt.after(new Date())&&start.before(new Date()))
							&& groupjoin.getTotalBuyNum() >= specVo.getLimit_max_copy()){
						return CallBackConstant.FAILED.callbackError("已达到购买上限");
					}
				}
			}
			
			if(customer.getCustomer_id() == usergroup.getOriginator()){
				return CallBackConstant.FAILED.callbackError("不能参与自己开的团");
			}
			
			return CallBackConstant.SUCCESS.callback(resultMap);
		} catch (Exception e) {
			logger.error("去参团异常...", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 去参团
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/toJoinGroup")
	public ModelAndView toJoinGroup(HttpServletRequest request,HttpServletResponse response) {
		logger.info("weixin进入团购：参团页");
		
		ModelAndView mv=new ModelAndView();
		
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null){
			customer = new CustomerVo();
		}
		if(StringUtil.isNull(pd.getString("userGroupId"))){	//团ID
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "您访问的团购信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isNull(pd.getString("productId"))){	//商品ID
			mv.addObject("tipsTitle", "商品信息提示");
			mv.addObject("tipsContent", "您访问的商品信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		if(StringUtil.isNull(pd.getString("specialId"))){	//专题ID
			mv.addObject("tipsTitle", "团购信息提示");
			mv.addObject("tipsContent", "您访问的团购信息不存在");
			mv.setViewName("/weixin/fuwubc");
			return mv;
		}
		try {
			
			//专题
			PageData specialParams = new PageData();
//			specialParams.put("status", 1);
//			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
//			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", pd.getString("productId"));
			specialParams.put("specialType", 3);
			specialParams.put("specialId", pd.getString("specialId"));
			SpecialVo specialVo = specialManager.queryOneByProductId(specialParams);
			if(specialVo == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已结束");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			specialVo.setLongStart(sdf.parse(specialVo.getStartTime()).getTime());
			specialVo.setLongEnd(sdf.parse(specialVo.getEndTime()).getTime());
			
			PageData propd = new PageData();
			propd.put("productId", pd.getString("productId"));
			propd.put("status", 1);
			ProductVo product = productManager.selectProductByOption(propd);
			if(null == product){
				mv.addObject("tipsTitle", "商品信息提示");
				mv.addObject("tipsContent", "您访问的商品信息不存在");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
//			pd.put("status", 1);
			UserGroupVo usergroup = this.userGroupManager.queryOne(pd);
			if(usergroup == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			pd.put("groupId", usergroup.getGroupId());
			SysGroupVo sysgroup = this.sysGroupManager.queryOne(pd);
			if(sysgroup == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
//			if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
//				mv.setViewName("/weixin/404");
//				return mv;
//			}
			
			specialParams.clear();
			specialParams.put("activityId", pd.getString("specialId"));
			specialParams.put("productId", usergroup.getProductId());
			specialParams.put("specId", usergroup.getSpecId());
			ActivityProductVo ap = this.activityProductManager.queryOne(specialParams);
			if(ap == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			specialParams.put("proStatus", 1);
			specialParams.put("specStatus", 1);
			ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(specialParams);
			if(specVo == null){
				mv.addObject("tipsTitle", "商品信息提示");
				mv.addObject("tipsContent", "您访问的商品已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
//			if(specVo.getStock_copy() == null || specVo.getStock_copy().intValue() <= 0){
//				mv.setViewName("/weixin/404");
//				return mv;
//			}
			specVo.setActivity_price(ap.getActivity_price());
			
//			pd.put("customerId", customerId);
//			pd.put("specId", usergroup.getSpecId());
//			GroupJoinVo groupjoin = this.groupJoinManager.queryOne(pd);
//			if(groupjoin != null){
//				if(specVo.getLimit_max_copy() > 0 && specVo.getLimit_end_time() != null
//						&& specVo.getLimit_start_time() != null){
//					Date dt = DateUtil.stringToDate(specVo.getLimit_end_time());
//					Date start = DateUtil.stringToDate(specVo.getLimit_start_time());
//					if((dt.after(new Date())&&start.before(new Date()))
//							&& groupjoin.getTotalBuyNum() >= specVo.getLimit_max_copy()){
//						mv.setViewName("/weixin/404");
//						return mv;
//					}
//				}
//			}
			
			PageData gjs = new PageData();
			gjs.put("userGroupId", usergroup.getUserGroupId());
			gjs.put("status", 1);
			List<GroupJoinVo> groupJoins = this.groupJoinManager.queryList(gjs);
			
			List<String> groupRules = new ArrayList<String>();
        	if(StringUtil.isNotNull(sysgroup.getDesc1())){
        		groupRules.add(sysgroup.getDesc1());
        	}
        	if(StringUtil.isNotNull(sysgroup.getDesc2())){
        		groupRules.add(sysgroup.getDesc2());
        	}
        	if(StringUtil.isNotNull(sysgroup.getDesc3())){
        		groupRules.add(sysgroup.getDesc3());
        	}
        	if(StringUtil.isNotNull(sysgroup.getDesc4())){
        		groupRules.add(sysgroup.getDesc4());
        	}
        	
        	String orderSource = "wexin_joinGroup_"+pd.getString("specialId");
        	orderSource = CustomerSourceUtil.checkOrderSource(request, pd.getString("source"), pd.getString("orderSource"), orderSource);
        	
			int tuanFlag = 0;
			int hasNum = 0;
			String tuanDesc = "";
			long crrt = System.currentTimeMillis();
			if(specialVo.getLongEnd() <= crrt || specialVo.getStatus().intValue() != 1){
				hasNum = sysgroup.getLimitCopy() - usergroup.getCustNum();
				if(usergroup.getCustNum() >= sysgroup.getLimitCopy()){
					tuanFlag = 1; //活动结束,且团已成功
					tuanDesc = "已满"+hasNum+"人，下次再来吧";
				}else{
					tuanFlag = 2; //活动结束,且团已失败
					tuanDesc = "时间已结束，遗憾缺"+hasNum+"人，下次再来吧";
				}
			}else if(usergroup.getStatus() == 1){
				tuanFlag = 3; //此团正在进行中
				hasNum = sysgroup.getLimitCopy() - usergroup.getCustNum();
				if(hasNum == 1){
					tuanDesc = "就等你1个了，一起享受拼价吧";
				}else if (hasNum > 1){
					tuanDesc = "剩余"+hasNum+"个名额，一起享受拼价吧";
				}else{
					tuanFlag = 4;
					tuanDesc = "已满"+sysgroup.getLimitCopy()+"人，快邀请好友再次开团吧";
				}
			}else{
				tuanFlag = 4;
				tuanDesc = "已满"+sysgroup.getLimitCopy()+"人，快邀请好友再次开团吧";
			}
        	
			int fakeSalesCopy = product.getFake_sales_copy();
			product.setSalesVolume(product.getSalesVolume()+fakeSalesCopy);
			PageData postagepd= new PageData();
			postagepd.put("productId", product.getProduct_id());
			PostageVo postageVo = postageManager.queryOne(postagepd);
			if (null != postageVo) {
				mv.addObject("postageId",postageVo.getPostageId());
				mv.addObject("postageMsg",postageVo.getPostageMsg());
			}
			mv.addObject("tuanFlag",tuanFlag);
			mv.addObject("tuanDesc",tuanDesc);
			mv.addObject("orderSource", orderSource);
        	mv.addObject("groupRules", groupRules);
			mv.addObject("groupJoins", groupJoins);
			mv.addObject("sysGroup", sysgroup);
			mv.addObject("product", product);
//			mv.addObject("myGroupJoin", groupjoin);
			mv.addObject("usergroup", usergroup);
			mv.addObject("productSpecVo", specVo);
			mv.addObject("specialVo", specialVo);
			mv.addObject("mobile",customer.getMobile());
			mv.addObject("customerId",customer.getCustomer_id());
			mv.addObject("currentTime", System.currentTimeMillis());
			mv.setViewName("/weixin/goods/wx_gogroupsd");
			return mv;
		} catch (Exception e) {
			logger.info("去参团异常...");
			mv.setViewName("/weixin/404");
			return mv;
		}
	}
	
	
	/**
	 * 去参团列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/toJoinGroups")
	public ModelAndView toJoinGroups(HttpServletRequest request,HttpServletResponse response) {
		logger.info("weixin进入团购：参团列表页");
		
		ModelAndView mv=new ModelAndView();
		
		PageData pd = this.getPageData();
		try {
			CustomerVo customer = Constants.getCustomer(request);
			PageData spd=new PageData();
			spd.put("specialId", pd.getString("specialId"));
			SpecialVo specialVo = specialManager.queryOne(spd);
			if(null == specialVo){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			spd.clear();
			spd.put("productId", pd.getString("productId"));
			spd.put("status", 1);
			ProductVo product = productManager.selectProductByOption(spd);
			if(null == product){
				mv.addObject("tipsTitle", "商品信息提示");
				mv.addObject("tipsContent", "您访问的商品已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			SysGroupVo sysGroupVo = sysGroupManager.queryOne(pd);
			if(sysGroupVo == null){
				mv.addObject("tipsTitle", "团购信息提示");
				mv.addObject("tipsContent", "您访问的团购活动已下架");
				mv.setViewName("/weixin/fuwubc");
				return mv;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long etime = sdf.parse(specialVo.getEndTime()).getTime();
			long ntime = System.currentTimeMillis();
			int tuanFlag = 0;
			if(etime <= ntime || specialVo.getStatus().intValue() != 1){
				tuanFlag = 1;
			}
			
			mv.addObject("tuanFlag", tuanFlag);
			mv.addObject("specialVo", specialVo);
			mv.addObject("product", product);
			mv.addObject("sysGroupVo", sysGroupVo);
			mv.addObject("customerId", customer.getCustomer_id());
			mv.setViewName("/weixin/goods/wx_go_groupsds");
			return mv;
		} catch (Exception e) {
			logger.info("去参团列表异常...");
			mv.setViewName("/weixin/404");
			return mv;
		}
	}
	
	
	/**
	 * 开团列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/joinGroupList")
	@ResponseBody
	public Map<String, Object> joinGroupList(HttpServletRequest request,HttpServletResponse response) {
		logger.info("进入团购：参团列表页");
		
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("productId"))){
			return CallBackConstant.FAILED.callbackError("商品信息丢失");
		}
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.FAILED.callbackError("活动信息丢失");
		}
		try {
			
			SysGroupVo sysGroupVo = sysGroupManager.queryOne(pd);
			if(sysGroupVo == null){
				return CallBackConstant.FAILED.callbackError("活动不存在");
			}
			
			List<UserGroupVo> userGroups = new ArrayList<UserGroupVo>();
			pd.put("statusLine", 1);
			userGroups = this.userGroupManager.queryListPage(pd);
			if(userGroups == null){
				userGroups = new ArrayList<UserGroupVo>();
			}
				
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", userGroups);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("去参团异常...", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
}
