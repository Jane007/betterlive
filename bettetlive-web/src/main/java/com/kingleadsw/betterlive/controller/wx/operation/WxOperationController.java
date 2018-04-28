package com.kingleadsw.betterlive.controller.wx.operation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OperationManager;
import com.kingleadsw.betterlive.biz.OperationRecordManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.biz.VersionInfoManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.SysConstants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.wechat.Md5Util;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.OperationRecordVo;
import com.kingleadsw.betterlive.vo.OperationVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;
import com.kingleadsw.betterlive.vo.VersionInfoVo;

/**
 * 运营推广活动
 *
 */
@Controller
@RequestMapping(value = "/weixin/operation")
public class WxOperationController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(WxOperationController.class);
	
	@Autowired
	private OperationManager operationManager;
	
	@Autowired
	private OperationRecordManager operationRecordManager;
	
	@Autowired
	private VersionInfoManager versionInfoManager;
	
	@Autowired
	private SingleCouponManager singleCouponManager;
	
	@Autowired
	private CouponManagerManager couponManagerManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	
	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SpecialMananger specialMananger;
	
	@Autowired
	private ProductSpecManager productSpecManager;
	
	/**
	 * 普通注册链接
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/basicDownApp")
	public ModelAndView basicDownApp(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/operation/downApp_dow");
		PageData pd = new PageData();
		VersionInfoVo verInfo = versionInfoManager.queryOne(pd);
		modelAndView.addObject("verInfo", verInfo);
		modelAndView.addObject("iosDownloadUrl", WebConstant.IOS_HUIHUO_APPSTORE_LOCAL);	
		
		return modelAndView;
	}
	
	/**
	 * 版本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downAppByJson")
	@ResponseBody
	public Map<String, Object> downAppByJson(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		VersionInfoVo verInfo = versionInfoManager.queryOne(pd);
		map.put("verInfo", verInfo);
		map.put("iosDownloadUrl", WebConstant.IOS_HUIHUO_APPSTORE_LOCAL);
		return map;
	}
	
	/**
	 * 注册下载APP链接  送优惠券或红包  活动类型为1或2
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toRegisterByOperation")
	public ModelAndView toRegisterByOperation(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/operation/downApp_gift");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("operationId"))){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		OperationVo operationVo = operationManager.queryOne(pd);
		if(operationVo == null){
			modelAndView.addObject("tipsTitle", "活动已结束");
			modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		PageData couponPd = new PageData();
		int existCoupon = 0;	//是否存在该券
		String couponName = "";
		if(operationVo.getOperationType() == 1){ //下载送优惠券
			couponPd.put("cmId", operationVo.getObjId());
			
			CouponManagerVo sysCoupon = couponManagerManager.findCouponManager(couponPd);
			if(sysCoupon == null){
				existCoupon = 1;
			}else{
				couponName = sysCoupon.getCoupon_name();
			}
			modelAndView.addObject("existCoupon", existCoupon);
			modelAndView.addObject("couponName", couponName);
		}else if(operationVo.getOperationType() == 2){ //下载送单品红包
			couponPd.put("couponSpecId", operationVo.getObjId());
			List<SingleCouponSpecVo>  scs = singleCouponSpecManager.queryList(couponPd);
			if(scs == null || scs.size() <= 0){
				existCoupon = 1;
			}else{
				couponName = scs.get(0).getCouponName();
				//专题
				PageData specialParams = new PageData();
				specialParams.put("status", 1);
				specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("productId", scs.get(0).getProductId());
				SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
				
				PageData activityMap = new PageData();
				activityMap.put("productId", scs.get(0).getProductId());
				activityMap.put("proStatus", 1);
				activityMap.put("specStatus", 1);
				if(specialVo != null){
					activityMap.put("activityId", specialVo.getSpecialId());
				}
				ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
				if(proSpec == null){
					modelAndView.addObject("tipsTitle", "活动已结束");
					modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
					modelAndView.setViewName("/weixin/fuwubc");
					return modelAndView;
				}
				
				int specialId = 0;
				int specialType = 0;
				if(specialVo != null){
					specialId = scs.get(0).getProductId();
					specialType = specialVo.getSpecialType();
				}
				modelAndView.addObject("productId", scs.get(0).getProductId());
				modelAndView.addObject("speicalId", specialId);
				modelAndView.addObject("speicalType", specialType);
			}
			modelAndView.addObject("existCoupon", existCoupon);
			modelAndView.addObject("couponName", couponName);
		}
		
		String regSource = "operation_" + pd.getString("operationId");
		if(StringUtil.isNotNull(pd.getString("source"))){
			regSource = pd.getString("source");
		}
		
		modelAndView.addObject("operationVo", operationVo);
		modelAndView.addObject("regSource", regSource);
		return modelAndView;
	}
	
	/**
	 * 注册送券或红包
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/registerByOperation")
	@ResponseBody
	public Map<String, Object> registerByOperation(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();

			if(StringUtil.isNull(pd.getString("operationId"))){
				result.put("flag", 0);
				result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
				return result;
			}
			
			String regSource = "operation_" + pd.getString("operationId");
			if(StringUtil.isNotNull(pd.getString("source"))){
				regSource = pd.getString("source");
			}
			result.put("regSource", regSource);
			
			
			pd.put("status", 1);
			pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			OperationVo operationVo = operationManager.queryOne(pd);
			if(operationVo == null){
				result.put("flag", 0);
				result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
				return result;
			}
			
			if(StringUtil.isNull(pd.getString("mobile"))){
				result.put("flag", 0);
				result.put("msg", "请输入手机号码");
				return result;
			}
			
			CustomerVo customer=Constants.getCustomer(request);
			int regFlag = 0;
			if(customer == null || customer.getCustomer_id() == null){	//注册
				customer = customerManager.selectByMobile(pd.getString("mobile").trim());
				if(customer == null){	//手机号码没被使用
					CustomerVo ykCust = (CustomerVo)request.getSession().getAttribute("yk_customer");
					customer = new CustomerVo();
	            	customer.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
	            	customer.setSource(regSource);
	            	customer.setMobile(pd.getString("mobile"));
	            	if(ykCust != null){
	            		customer.setSubscribe(ykCust.getSubscribe());
	            		customer.setNickname(ykCust.getNickname());
	    				customer.setOpenid(ykCust.getOpenid());
	    				customer.setUnionid(ykCust.getUnionid());
	    				customer.setSex(ykCust.getSex());
	            	}else{
	            		customer.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
		            	customer.setNickname("");
	            	}
	            	
	            	Random rnd = new Random();
	        		int num = rnd.nextInt(89999) + 10000000;
	        		String pwd = Md5Util.md532(String.valueOf(num));
	        		customer.setPassword(pwd);
	        		int count = customerManager.insertCustomer(customer);
	        		if(count <= 0){
	        			result.put("flag", 0);
						result.put("msg", "注册失败");
						return result;
	        		}
	        		
	        		String msg = "恭喜您成为挥货平台的会员，您的登录密码是	["+num+"]，请及时修改您的登录密码";
	        		SendMsgUtil.sendMessage(pd.getString("mobile").trim(), msg);
	        		customer.setPassword(null);
	        		Constants.setCustomer(request, customer);
	        		request.getSession().removeAttribute("yk_customer");
					
	        		couponInfoManager.insertRegisterCoupon(customer); //新手红包
				}else{
					regFlag = 1;
				}
			}else{	//检查手机号码 并绑定
				CustomerVo customerVo = customerManager.selectByPrimaryKey(customer.getCustomer_id());
				if(StringUtil.isNotNull(customerVo.getMobile()) && !customerVo.getMobile().equals(pd.getString("mobile").trim())){
					result.put("flag", 0);
					result.put("msg", "您输入的手机号与账户绑定的不一致");
					return result;
				}
				CustomerVo custVo = customerManager.selectByMobile(pd.getString("mobile").trim());
				if(custVo != null && custVo.getCustomer_id().intValue() != customer.getCustomer_id().intValue()){
					result.put("flag", 0);
					result.put("msg", "您输入的手机号已绑定账户");
					return result;
				}
				
				regFlag = 1;
				if(StringUtil.isNull(customerVo.getMobile()) && custVo == null){
					PageData custpd = new PageData();
					custpd.put("customer_id", customer.getCustomer_id());
					custpd.put("mobile", pd.getString("mobile"));
					this.customerManager.updateCustoemrById(custpd);
					couponInfoManager.insertRegisterCoupon(customer); //新手红包
					customer.setMobile(pd.getString("mobile"));
					customer.setPassword(null);
	        		Constants.setCustomer(request, customer);
	        		regFlag = 0;
        		}
			}
			
			if(operationVo.getOperationType() == 3){
				String msg = "海量优惠券等你来拿，单张金额最高可达88元";
				if(regFlag == 1){
					msg = "您已注册过<br>下进入挥货商城参与更多优惠";
				}
				result.put("flag", 1);
				result.put("msg", msg);
				result.put("regFlag", regFlag);
				return result;
			}
			
			int custId = customer.getCustomer_id();
			PageData oppd = new PageData();
			oppd.put("customerId", custId);
			oppd.put("operationId", operationVo.getOperationId());
			oppd.put("sysObjId", operationVo.getObjId());
			OperationRecordVo rec = this.operationRecordManager.queryOne(oppd);
			
			int subMsgType = 0;
			int flag = 0;
			String msgTitle="";
			String msgContent="";
			String showMoney = "";
			if(rec != null){
				String couponType="优惠券";
				if(operationVo.getOperationType() == 2){
					couponType = "红包";
				}
				result.put("flag", 2);
				result.put("msg", "您已经领过"+couponType+"了<br>进入挥货商城参与更多优惠");
				return result;
			}
			
			int objId = 0;
			if(operationVo.getOperationType() == 1){	//送优惠券
				PageData couponPd = new PageData();
				couponPd.put("cmId", operationVo.getObjId());
				CouponManagerVo sysCoupon = couponManagerManager.findCouponManager(couponPd);
				if(sysCoupon == null){
					result.put("flag", 0);
					result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
					return result;
				}
				
				String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
				
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,Integer.parseInt(sysCoupon.getUsemax_date()));
				
				String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
				
				CouponInfoVo couponInfoVo = new CouponInfoVo();
				couponInfoVo.setMobile(pd.getString("mobile"));
				couponInfoVo.setCustomer_id(custId);
				
				BigDecimal couponMoney=new BigDecimal(sysCoupon.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(sysCoupon.getUsemin_money());
				
				showMoney = couponMoney.toString()+"元优惠券";
				
				couponInfoVo.setCm_id(sysCoupon.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
				couponInfoVo.setFrom_user_id(0);
				couponInfoManager.insertUserCoupon(couponInfoVo);
				
				objId = couponInfoVo.getCoupon_id();
				subMsgType = 1;
				flag = 1;

				msgTitle = "恭喜您获得了一张优惠券";
				if(StringUtil.isNotNull(sysCoupon.getCoupon_content())){
					msgContent = sysCoupon.getCoupon_content();
				}
						
			}else if(operationVo.getOperationType() == 2){	//送单品红包
				PageData couponPd = new PageData();
				couponPd.put("couponSpecId", operationVo.getObjId());
				List<SingleCouponSpecVo>  scs = singleCouponSpecManager.queryList(couponPd);
				if(scs == null || scs.size() <= 0){
					result.put("flag", 0);
					result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
					return result;
				}
				SingleCouponSpecVo spe = scs.get(0);
				
				SingleCouponVo sysSingleCoupon = singleCouponManager.selectByPrimaryKey(spe.getCouponId());
				if(sysSingleCoupon == null){
					result.put("flag", 0);
					result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
					return result;
				}
				
				
				UserSingleCouponVo usc = new UserSingleCouponVo();
				usc.setCouponId(scs.get(0).getCouponId());
				usc.setCustomerId(custId);
				usc.setMobile(pd.getString("mobile"));
				usc.setFullMoney(sysSingleCoupon.getFullMoney());
				usc.setProductId(spe.getProductId());
				usc.setSpecId(spe.getSpecId());
				usc.setCouponMoney(sysSingleCoupon.getCouponMoney());
				usc.setStartTime(sysSingleCoupon.getStartTime());
				usc.setEndTime(sysSingleCoupon.getEndTime());
				usc.setStatus(0);
				this.userSingleCouponManager.insert(usc);
				
				objId = usc.getUserSingleId();
				subMsgType = 2;
				flag = 1;
				msgTitle = "恭喜您获得了一个单品红包";
				showMoney = sysSingleCoupon.getCouponMoney().toString()+"元红包";
				
				if(StringUtil.isNotNull(sysSingleCoupon.getCouponContent())){
					msgContent = sysSingleCoupon.getCouponContent();
				}
			}
			
			if(flag == 1){
				//发系统消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(2);
				msgVo.setSubMsgType(subMsgType);
				msgVo.setMsgTitle(msgTitle);
				msgVo.setMsgDetail(msgContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(custId);
				msgVo.setObjId(objId);
				messageManager.insert(msgVo);
				
				rec = new OperationRecordVo();
				rec.setCustomerId(custId);
				rec.setSysObjId(operationVo.getObjId());
				rec.setObjId(objId);
				rec.setOperationType(operationVo.getOperationType());
				rec.setOperationId(operationVo.getOperationId());
				this.operationRecordManager.insert(rec);
				
				result.put("flag", 1);
				result.put("msg", "您成功领取了"+showMoney+"，已经发送至您的账号");
				return result;
			}
		} catch (Exception e) {
			logger.error("/weixin/operation/registerByOperation --error", e);
			result.put("flag", 0);
			result.put("msg", "注册出现异常");
			return result;
		}
		result.put("flag", 0);
		result.put("msg", "注册失败了");
		return result;
	}
	
	/**
	 * 注册送单品红包页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toRegisterBySingleCoupon")
	public ModelAndView toRegisterBySingleCoupon(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/operation/downApp_ling");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("operationId"))){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		OperationVo operationVo = operationManager.queryOne(pd);
		if(operationVo == null){
			modelAndView.addObject("tipsTitle", "活动已结束");
			modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		PageData couponPd = new PageData();
		int existCoupon = 0;	//是否存在该券
		String couponName = "";
		if(operationVo.getOperationType() == 2){ //下载送单品红包
			couponPd.put("couponSpecId", operationVo.getObjId());
			List<SingleCouponSpecVo>  scs = singleCouponSpecManager.queryList(couponPd);
			if(scs == null || scs.size() <= 0){
				existCoupon = 1;
			}else{
				couponName = scs.get(0).getCouponName();
				//专题
				PageData specialParams = new PageData();
				specialParams.put("status", 1);
				specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specialParams.put("productId", scs.get(0).getProductId());
				SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
				
				PageData activityMap = new PageData();
				activityMap.put("productId", scs.get(0).getProductId());
				activityMap.put("proStatus", 1);
				activityMap.put("specStatus", 1);
				if(specialVo != null){
					activityMap.put("activityId", specialVo.getSpecialId());
				}
				ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
				if(proSpec == null){
					modelAndView.addObject("tipsTitle", "活动已结束");
					modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
					modelAndView.setViewName("/weixin/fuwubc");
					return modelAndView;
				}
				
				int specialId = 0;
				int specialType = 0;
				if(specialVo != null){
					specialId = scs.get(0).getProductId();
					specialType = specialVo.getSpecialType();
				}
				modelAndView.addObject("productId", scs.get(0).getProductId());
				modelAndView.addObject("speicalId", specialId);
				modelAndView.addObject("speicalType", specialType);
			}
			modelAndView.addObject("existCoupon", existCoupon);
			modelAndView.addObject("couponName", couponName);
		}
		
		String regSource = "operation_" + pd.getString("operationId");
		if(StringUtil.isNotNull(pd.getString("source"))){
			regSource = pd.getString("source");
		}
		
		modelAndView.addObject("operationVo", operationVo);
		modelAndView.addObject("regSource", regSource);
		return modelAndView;
	}
	
	/**
	 * 注册送单品红包
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/registerBySingleCoupon")
	@ResponseBody
	public Map<String, Object> registerBySingleCoupon(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("operationId"))){
			result.put("flag", 0);
			result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
			return result;
		}
		pd.put("status", 1);
		pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		OperationVo operationVo = operationManager.queryOne(pd);
		if(operationVo == null){
			result.put("flag", 0);
			result.put("msg", "该活动已结束啦<br>进入挥货商城参与更多优惠");
			return result;
		}
//		PageData couponPd = new PageData();
//		int existCoupon = 0;	//是否存在该券
//		String couponName = "";
//		couponPd.put("couponSpecId", operationVo.getObjId());
//		List<SingleCouponSpecVo>  scs = singleCouponSpecManager.queryList(couponPd);
		if(StringUtil.isNull(pd.getString("mobile"))){
			result.put("flag", 0);
			result.put("msg", "请输入手机号码");
			return result;
		}
		String regSource = "operation_" + pd.getString("operationId");
		if(StringUtil.isNotNull(pd.getString("source"))){
			regSource = pd.getString("source");
		}
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){	//注册

			CustomerVo newCustomer = customerManager.selectByMobile(pd.getString("mobile").trim());
			if(newCustomer == null){	//手机号码没被使用
				customer = (CustomerVo)request.getSession().getAttribute("yk_customer");
				newCustomer = new CustomerVo();
				newCustomer.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
				newCustomer.setSource(regSource);
				newCustomer.setMobile(pd.getString("mobile"));
				if(customer != null){
					newCustomer.setOpenid(customer.getOpenid());
					newCustomer.setUnionid(customer.getUnionid());
					newCustomer.setSubscribe(customer.getSubscribe());
					newCustomer.setNickname(customer.getNickname());
					newCustomer.setSex(customer.getSex());
				}else{
					newCustomer.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
					newCustomer.setNickname("");
				}

            	Random rnd = new Random();
        		int num = rnd.nextInt(89999) + 10000000;
        		String pwd = Md5Util.md532(String.valueOf(num));
        		newCustomer.setPassword(pwd);
        		int count = customerManager.insertCustomer(newCustomer);
        		if(count <= 0){
        			result.put("flag", 0);
					result.put("msg", "注册失败");
					return result;
        		}
        		
        		String msg = "恭喜您成为挥货平台的会员，您的登录密码是["+num+"]，请及时修改您的登录密码！";
        		SendMsgUtil.sendMessage(pd.getString("mobile").trim(), msg);
        		newCustomer.setPassword(null);
        		Constants.setCustomer(request, newCustomer);
        		
        		request.getSession().removeAttribute("yk_customer");
        		
        		couponInfoManager.insertRegisterCoupon(newCustomer); //新手红包
        		customer = newCustomer;//供下面程序使用
			}else{
				result.put("flag", 2);
				result.put("msg", "您已注册过<br>进入挥货商城参与更多优惠");
				return result;
			}
		}else{//检查手机号码 并绑定
			CustomerVo customerVo = customerManager.selectByPrimaryKey(customer.getCustomer_id());
			if(StringUtil.isNotNull(customerVo.getMobile()) && !customerVo.getMobile().equals(pd.getString("mobile").trim())){
				result.put("flag", 0);
				result.put("msg", "您输入的手机号与账户绑定的不一致");
				return result;
			}
			CustomerVo custVo = customerManager.selectByMobile(pd.getString("mobile").trim());
			if(custVo != null && custVo.getCustomer_id().intValue() != customer.getCustomer_id().intValue()){
				result.put("flag", 0);
				result.put("msg", "您输入的手机号已绑定账户");
				return result;
			}
			
			if(StringUtil.isNull(customerVo.getMobile()) && custVo == null){
				PageData custpd = new PageData();
				custpd.put("customer_id", customer.getCustomer_id());
				custpd.put("mobile", pd.getString("mobile"));
				this.customerManager.updateCustoemrById(custpd);
				couponInfoManager.insertRegisterCoupon(customer); //新手红包
				customer.setMobile(pd.getString("mobile"));
				customer.setPassword(null);
        		Constants.setCustomer(request, customer);
    		}else{
    			result.put("flag", 2);
				result.put("msg", "您已注册过<br>进入挥货商城参与更多优惠");
				return result;
    		}
		}
		
		
		int objId = 0;
		PageData couponPd = new PageData();
		couponPd.put("couponSpecId", operationVo.getObjId());
		List<SingleCouponSpecVo>  scs = singleCouponSpecManager.queryList(couponPd);
		if(scs == null || scs.size() <= 0){
			result.put("flag", 0);
			result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
			return result;
		}
		SingleCouponSpecVo spe = scs.get(0);
		
		SingleCouponVo sysSingleCoupon = singleCouponManager.selectByPrimaryKey(spe.getCouponId());
		if(sysSingleCoupon == null){
			result.put("flag", 0);
			result.put("msg", "活动已结束<br>进入挥货商城参与更多优惠");
			return result;
		}
		UserSingleCouponVo usc = new UserSingleCouponVo();
		usc.setCouponId(scs.get(0).getCouponId());
		usc.setCustomerId(customer.getCustomer_id());
		usc.setMobile(pd.getString("mobile"));
		usc.setFullMoney(sysSingleCoupon.getFullMoney());
		usc.setProductId(spe.getProductId());
		usc.setSpecId(spe.getSpecId());
		usc.setCouponMoney(sysSingleCoupon.getCouponMoney());
		usc.setStartTime(sysSingleCoupon.getStartTime());
		usc.setEndTime(sysSingleCoupon.getEndTime());
		usc.setStatus(0);
		this.userSingleCouponManager.insert(usc);
		objId = usc.getUserSingleId();
		//发系统消息给用户
		MessageVo msgVo = new MessageVo();
		msgVo.setMsgType(2);
		msgVo.setSubMsgType(2);
		msgVo.setMsgTitle("恭喜您获得了一个单品红包");
		msgVo.setMsgDetail(sysSingleCoupon.getCouponContent());
		msgVo.setIsRead(0);
		msgVo.setCustomerId(customer.getCustomer_id());
		msgVo.setObjId(objId);
		messageManager.insert(msgVo);
		
		OperationRecordVo rec = new OperationRecordVo();
		rec.setCustomerId(customer.getCustomer_id());
		rec.setSysObjId(operationVo.getObjId());
		rec.setObjId(objId);
		rec.setOperationType(operationVo.getOperationType());
		rec.setOperationId(operationVo.getOperationId());
		this.operationRecordManager.insert(rec);
		
		result.put("flag", 1);
		result.put("msg", "您成功领取了"+sysSingleCoupon.getCouponMoney().toString()+"元红包<br>进入挥货商城参与更多优惠");
		return result;
		
	}
	
	/**
	 * 无优惠券下载APP
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/basicRegDownApp")
	public ModelAndView basicRegDownApp(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/operation/downApp_basicReg");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("operationId"))){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		OperationVo operationVo = operationManager.queryOne(pd);
		if(operationVo == null){
			modelAndView.addObject("tipsTitle", "活动已结束");
			modelAndView.addObject("tipsContent", "该活动已结束啦，进入挥货商城参与更多优惠");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		String regSource = "operation_" + pd.getString("operationId");
		if(StringUtil.isNotNull(pd.getString("source"))){
			regSource = pd.getString("source");
		}
		
		VersionInfoVo verInfo = versionInfoManager.queryOne(pd);
		modelAndView.addObject("verInfo", verInfo);
		modelAndView.addObject("iosDownloadUrl", WebConstant.IOS_HUIHUO_APPSTORE_LOCAL);	
		modelAndView.addObject("operationVo", operationVo);
		modelAndView.addObject("regSource", regSource);
		return modelAndView;
	}
	
	/**
	 * 无优惠券注册
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/baseicReg")
	@ResponseBody
	public Map<String, Object> baseicReg(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("operationId"))){
			result.put("flag", 0);
			result.put("msg", "您访问的页面不存在");
			return result;
		}
		pd.put("status", 1);
		pd.put("startTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		OperationVo operationVo = operationManager.queryOne(pd);
		if(operationVo == null){
			result.put("flag", 0);
			result.put("msg", "您访问的页面已下架<br>进入挥货商城参与更多优惠");
			return result;
		}
		if(StringUtil.isNull(pd.getString("mobile"))){
			result.put("flag", 0);
			result.put("msg", "请输入手机号码");
			return result;
		}
		String regSource = "operation_" + pd.getString("operationId");
		if(StringUtil.isNotNull(pd.getString("source"))){
			regSource = pd.getString("source");
		}
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){	//注册
			CustomerVo newCustomer = customerManager.selectByMobile(pd.getString("mobile").trim());
			if(newCustomer == null){	//手机号码没被使用
				customer = (CustomerVo)request.getSession().getAttribute("yk_customer");
				newCustomer = new CustomerVo();
				newCustomer.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
				newCustomer.setSource(regSource);
				newCustomer.setMobile(pd.getString("mobile"));
				if(customer != null){
					newCustomer.setSubscribe(customer.getSubscribe());
					newCustomer.setNickname(customer.getNickname());
					newCustomer.setOpenid(customer.getOpenid());
					newCustomer.setUnionid(customer.getUnionid());
					newCustomer.setSex(customer.getSex());
				}else{
					newCustomer.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
					newCustomer.setNickname("");
				}
            	Random rnd = new Random();
        		int num = rnd.nextInt(89999) + 10000000;
        		String pwd = Md5Util.md532(String.valueOf(num));
        		newCustomer.setPassword(pwd);
        		int count = customerManager.insertCustomer(newCustomer);
        		if(count <= 0){
        			result.put("flag", 0);
					result.put("msg", "注册失败");
					return result;
        		}
        		
        		String msg = "恭喜您成为挥货平台的会员，您的登录密码是["+num+"]，请及时修改您的登录密码";
        		SendMsgUtil.sendMessage(pd.getString("mobile").trim(), msg);
        		newCustomer.setPassword(null);
        		Constants.setCustomer(request, newCustomer);
        		
        		request.getSession().removeAttribute("yk_customer");
        		
        		couponInfoManager.insertRegisterCoupon(newCustomer); //新手红包
			}else{
				result.put("flag", 2);
				result.put("msg", "您已注册过<br>进入挥货商城参与更多优惠");
				return result;
			}
		}else{//检查手机号码 并绑定
			CustomerVo customerVo = customerManager.selectByPrimaryKey(customer.getCustomer_id());
			if(StringUtil.isNotNull(customerVo.getMobile()) && !customerVo.getMobile().equals(pd.getString("mobile").trim())){
				result.put("flag", 0);
				result.put("msg", "您输入的手机号与账户绑定的不一致");
				return result;
			}
			CustomerVo custVo = customerManager.selectByMobile(pd.getString("mobile").trim());
			if(custVo != null && custVo.getCustomer_id().intValue() != customer.getCustomer_id().intValue()){
				result.put("flag", 0);
				result.put("msg", "您输入的手机号已绑定账户");
				return result;
			}
			
			if(StringUtil.isNull(customerVo.getMobile()) && custVo == null){
				PageData custpd = new PageData();
				custpd.put("customer_id", customer.getCustomer_id());
				custpd.put("mobile", pd.getString("mobile"));
				this.customerManager.updateCustoemrById(custpd);
				couponInfoManager.insertRegisterCoupon(customer); //新手红包
				customer.setMobile(pd.getString("mobile"));
				customer.setPassword(null);
        		Constants.setCustomer(request, customer);
    		}else{
    			result.put("flag", 2);
				result.put("msg", "您已注册过<br>进入挥货商城参与更多优惠");
				return result;
    		}
		}
		
		result.put("flag", 1);
		result.put("msg", "您已注册成功<br>进入挥货商城参与更多优惠");
		return result;
		
	}
	
	/**
	 * 新老用户领券
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downAppByxlallPage")
	public ModelAndView downAppByxlallPage(HttpServletRequest request,HttpServletResponse response) {
		
		
		ModelAndView modelAndView=new ModelAndView("weixin/operation/downApp_xlall");
		long nowTime = System.currentTimeMillis();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			PageData pd = this.getPageData();
			String couponIds = pd.getString("couponIds");
			if(StringUtil.isNull(couponIds)){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "红包参数异常");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			long startTime = sdf.parse("2017-11-08 00:00:00").getTime();
			long endTime = sdf.parse("2018-12-31 23:59:59").getTime();
			if(startTime > nowTime || endTime <= nowTime){
				modelAndView.addObject("tipsTitle", "注册有礼");
				modelAndView.addObject("tipsContent", "活动已经结束啦~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			String regSource = "operation_" + pd.getString("operationId");
			if(StringUtil.isNotNull(pd.getString("source"))){
				regSource = pd.getString("source");
			}
			
			VersionInfoVo verInfo = versionInfoManager.queryOne(pd);
			modelAndView.addObject("verInfo", verInfo);
			modelAndView.addObject("iosDownloadUrl", WebConstant.IOS_HUIHUO_APPSTORE_LOCAL);	
			modelAndView.addObject("regSource", regSource);
			modelAndView.addObject("couponIds", couponIds);
			return modelAndView;
		} catch (ParseException e) {
			logger.error("/weixin/operation/downAppByxlallPage", e);
		}
		
		return modelAndView;
	}
	
	/**
	 * 新老用户领券
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downAppByxlall")
	@ResponseBody
	public Map<String, Object> downAppByxlall(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nowTime = System.currentTimeMillis();
		long startTime;
		try {
			String couponIds = pd.getString("couponIds");
			if(StringUtil.isNull(couponIds)){
				result.put("flag", 0);
				result.put("msg", "活动参数异常");
				return result;
			}
			String[] couponIdArray = couponIds.split(",");
			if(StringUtil.isNull(couponIdArray)){
				result.put("flag", 0);
				result.put("msg", "活动参数异常");
				return result;
			}
			startTime = sdf.parse("2017-11-08 00:00:00").getTime();
			long endTime = sdf.parse("2018-12-31 23:59:59").getTime();
			if(startTime > nowTime || endTime <= nowTime){
				result.put("flag", 3);
				result.put("msg", "活动已结束");
				return result;
			}
			
			String checkCode = pd.getString("checkCode");
			if (StringUtil.isNull(checkCode)) {
				result.put("flag", 0);
				result.put("msg", "验证码不能为空");
				return result;
			}
			if(StringUtil.isNull(pd.getString("mobile"))){
				result.put("flag", 0);
				result.put("msg", "请输入手机号码");
				return result;
			}
			
			if (!checkCode.equals(redisService.getString(pd.getString("mobile").trim()))) {
				result.put("flag", 0);
				result.put("msg", "验证码错误或已过期");
				return result;
			}
			
			redisService.delKey(pd.getString("mobile").trim());
			String regSource = "operation_xlall";
			if(StringUtil.isNotNull(pd.getString("source"))){
				regSource = pd.getString("source");
			}
			String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
			CustomerVo customer = customerManager.selectByMobile(pd.getString("mobile").trim());
			CustomerVo myCustVo = Constants.getCustomer(request);
			if((myCustVo == null || myCustVo.getCustomer_id() == null) && customer == null){	//注册
				customer = (CustomerVo)request.getSession().getAttribute("yk_customer");
				myCustVo = new CustomerVo();
				myCustVo.setSource(regSource);
				myCustVo.setMobile(pd.getString("mobile"));
				myCustVo.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
				
				if(customer != null){
					myCustVo.setSubscribe(customer.getSubscribe());
					myCustVo.setNickname(customer.getNickname());
					myCustVo.setOpenid(customer.getOpenid());
					myCustVo.setUnionid(customer.getUnionid());
					myCustVo.setSex(customer.getSex());
				}else{
					myCustVo.setSubscribe(SysConstants.SUBSCRIBE_NOTSURE);
					myCustVo.setNickname("");
				}
            	Random rnd = new Random();
        		int num = rnd.nextInt(89999) + 10000000;
        		String pwd = Md5Util.md532(String.valueOf(num));
        		myCustVo.setPassword(pwd);
        		int count = customerManager.insertCustomer(myCustVo);
        		if(count <= 0){
        			result.put("flag", 0);
					result.put("msg", "注册失败");
					return result;
        		}
        		
        		String msg = "恭喜您成为挥货平台的会员，您的登录密码是["+num+"]，请及时修改您的登录密码！";
        		SendMsgUtil.sendMessage(pd.getString("mobile").trim(), msg);
        		myCustVo.setPassword(null);
        		Constants.setCustomer(request, myCustVo);
        		request.getSession().removeAttribute("yk_customer");
    			
        		couponInfoManager.insertRegisterCoupon(myCustVo); //新手红包
        		
        		int res = 0;
     			int ling = 0;
     			int gq = 0;
    			BigDecimal totalMoney = BigDecimal.ZERO;
    			CouponInfoVo couponInfoVo = new CouponInfoVo();
				
				couponInfoVo.setMobile(myCustVo.getMobile());
				couponInfoVo.setCustomer_id(myCustVo.getCustomer_id());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
				couponInfoVo.setFrom_user_id(0);
				
    			PageData cpd = new PageData();
    			for (int i = 1; i < couponIdArray.length; i++) {
					CouponManagerVo cmVo = couponManagerManager.selectByPrimaryKey(Integer.parseInt(couponIdArray[i]));
					if(cmVo == null || cmVo.getCm_id() <= 0){
						gq++;
						continue;
					}
					
					cpd.clear();
					cpd.put("cmId", cmVo.getCm_id());
					cpd.put("customerId", myCustVo.getCustomer_id());
					List<CouponInfoVo> cps = this.couponInfoManager.findListUserCoupon(cpd);
					if(cps != null && cps.size() > 0){
						ling++;
						continue;
					}
					
					BigDecimal couponMoney=new BigDecimal(cmVo.getCoupon_money());
					BigDecimal useminMoney=new BigDecimal(cmVo.getUsemin_money());
					Calendar calendar = Calendar.getInstance();  
					calendar.setTime(new Date());
					calendar.add(Calendar.DATE,Integer.parseInt(cmVo.getUsemax_date()));
					String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
					couponInfoVo.setEndtime(endDate);
					couponInfoVo.setStart_money(useminMoney.intValue());
					couponInfoVo.setCm_id(cmVo.getCm_id());
					couponInfoVo.setCoupon_money(couponMoney.intValue());
	        		this.couponInfoManager.insertUserCoupon(couponInfoVo);
	        		
	        		//发系统消息给用户
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(2);
					msgVo.setSubMsgType(1);
					msgVo.setMsgTitle("恭喜您获得了一张优惠券");
					String couponContent = "";
					if(StringUtil.isNotNull(cmVo.getCoupon_content())){
						couponContent = cmVo.getCoupon_content();
					}
					msgVo.setMsgDetail(couponContent);
					msgVo.setIsRead(0);
					msgVo.setCustomerId(myCustVo.getCustomer_id());
					msgVo.setObjId(couponInfoVo.getCoupon_id());
					messageManager.insert(msgVo);
					res++;
					totalMoney = totalMoney.add(couponMoney);
					myCustVo.setPassword(null);
	        		Constants.setCustomer(request, myCustVo);
				}
    			
    			if(ling+gq == couponIdArray.length-1){
    				msg = "活动已结束~";
    				if(ling > 0){
    					msg = "您已经领取过该红包啦<br>快去使用吧";
    				}
    				result.put("flag", 3);
    				result.put("ling", ling);
    				result.put("msg", msg);
    				return result;
    			}else{
    				result.put("flag", 1);
    				result.put("regFlag", 2);
					result.put("msg", "恭喜您，成功领取了<strong>"+res+"张</strong>"+totalMoney+"元的组合券！");
					return result;
    			}
			}else{//检查手机号码 并绑定
				if(customer != null && myCustVo != null){
					if(customer.getCustomer_id().intValue() != myCustVo.getCustomer_id().intValue()){ //当前登录用户不是正在领券的用户
						request.getSession().invalidate();
					}
					
				}else if (myCustVo != null && customer == null){
					customer = myCustVo;
					customer.setMobile(pd.getString("mobile").trim());
					PageData custpd = new PageData();
					custpd.put("customer_id", customer.getCustomer_id());
					custpd.put("mobile", customer.getMobile());
					this.customerManager.updateCustoemrById(custpd);
				}
				
    			CouponManagerVo cmVo = couponManagerManager.selectByPrimaryKey(Integer.parseInt(couponIdArray[0]));
        		if(cmVo == null){
        			result.put("flag", 3);
					result.put("msg", "活动已结束");
					return result;
        		}
        		PageData cpd = new PageData();
				cpd.put("cmId", cmVo.getCm_id());
				cpd.put("customerId", customer.getCustomer_id());
				List<CouponInfoVo> cps = this.couponInfoManager.findListUserCoupon(cpd);
				if(cps != null && cps.size() > 0){
					result.put("flag", 3);
					result.put("msg", "您已经领取过该红包啦<br>快去使用吧");
					return result;
				}
				
				BigDecimal couponMoney=new BigDecimal(cmVo.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(cmVo.getUsemin_money());
				
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,Integer.parseInt(cmVo.getUsemax_date()));
				
				String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
				CouponInfoVo couponInfoVo = new CouponInfoVo();
				couponInfoVo.setMobile(customer.getMobile());
				couponInfoVo.setCustomer_id(customer.getCustomer_id());
				couponInfoVo.setCm_id(cmVo.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
				couponInfoVo.setFrom_user_id(0);
        		this.couponInfoManager.insertUserCoupon(couponInfoVo);
        		//发系统消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(2);
				msgVo.setSubMsgType(1);
				msgVo.setMsgTitle("恭喜您获得了一张优惠券");
				String couponContent = "";
				if(StringUtil.isNotNull(cmVo.getCoupon_content())){
					couponContent = cmVo.getCoupon_content();
				}
				msgVo.setMsgDetail(couponContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(customer.getCustomer_id());
				msgVo.setObjId(couponInfoVo.getCoupon_id());
				messageManager.insert(msgVo);
				
				customer.setPassword(null);
        		Constants.setCustomer(request, customer);
        		
				result.put("regFlag", 1);
        		result.put("flag", 1);
				result.put("msg", "恭喜您，成功领取了<strong>1</strong>张"+couponInfoVo.getCoupon_money()+"元无门槛优惠券");
				return result;
			}
			
		} catch (ParseException e) {
			logger.error("/weixin/operation/downAppByxlall", e);
			result.put("flag", 0);
			result.put("msg", "出现异常啦~");
			return result;
		}
		
	}
	
}
