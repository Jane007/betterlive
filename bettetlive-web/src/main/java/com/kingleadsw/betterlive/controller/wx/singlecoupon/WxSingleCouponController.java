package com.kingleadsw.betterlive.controller.wx.singlecoupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.pay.wechat.Md5Util;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

@Controller
@RequestMapping(value = "/weixin/singlecoupon")
public class WxSingleCouponController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxSingleCouponController.class);
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	
	
	@RequestMapping(value = "/getRedpacket")
	public ModelAndView getRedpacket(HttpServletRequest request, HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/singlecoupon/single_redpacket2");
		PageData pd = this.getPageData();
		String couponId = pd.getString("couponId");
		String specId = pd.getString("specId");
		int operateFlag = 0;
		
		if(StringUtil.isNull(couponId)){
			modelAndView.addObject("tipsTitle", "领红包信息提示");
			modelAndView.addObject("tipsContent", "非法请求");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
	
		if(StringUtil.isNull(specId)){
			modelAndView.addObject("tipsTitle", "领红包信息提示");
			modelAndView.addObject("tipsContent", "非法请求");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		try {
			
			int flagkey = 1;
			CustomerVo customerVo=Constants.getCustomer(request);
			
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(couponId));
			if(singleCouponVo == null){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			pd.put("specStatus", 1);
			ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pd);
			if(psvo == null){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			String endTime = singleCouponVo.getEndTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			Date dt = sdf.parse(endTime);
			endTime = sdf.format(dt);
			long systime = System.currentTimeMillis();
			if(singleCouponVo.getStatus().intValue() == 0
					|| dt.getTime() <= systime){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			singleCouponVo.setEndTime(endTime);
			
			if(customerVo != null && customerVo.getCustomer_id() != null){
				String phoneNumber = customerVo.getMobile();
				pd.put("customerId", customerVo.getCustomer_id());
				
				if(StringUtils.isNotBlank(phoneNumber)){
					pd.put("mobile", phoneNumber);
					UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(pd);
					if(!StringUtil.isNull(uscVo)){//每个人只可以领取一个单品红包 //是否已領取
						flagkey = 2;
					}
				}
			}
			
			String regSource = "single_"+couponId+"_"+specId;
			if(StringUtil.isNotNull(pd.getString("source"))){
				regSource = pd.getString("source");
			}
			
			if("238".equals(couponId) && "382".equals(specId)){
				pd.clear();
				pd.put("couponId", couponId);
				pd.put("specId", specId);
				List<UserSingleCouponVo> list = this.userSingleCouponManager.queryList(pd);
				if(list != null && list.size() >= 300){
					modelAndView.addObject("tipsTitle", "领红包信息提示");
					modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
					modelAndView.setViewName("/weixin/fuwubc");
					return modelAndView;
				}
			}
			
			operateFlag = 1;
			model.addAttribute("regSource", regSource);
			model.addAttribute("singleCouponVo", singleCouponVo);
			model.addAttribute("productId", psvo.getProduct_id());
			model.addAttribute("productName", psvo.getProduct_name());
			model.addAttribute("customer", customerVo);
			model.addAttribute("couponId", couponId);
			model.addAttribute("specId", specId);
			model.addAttribute("flagkey", flagkey);
			model.addAttribute("operateFlag", operateFlag);
			
			String couponBanner = singleCouponVo.getCouponBanner();
			if(StringUtil.isNotNull(couponId) && Integer.parseInt(couponId) == 111){	//凯特芒果单品红包 couponId=111
				modelAndView.setViewName("weixin/singlecoupon/single_kaite");
				return modelAndView;
			}else if(StringUtil.isNotNull(couponId) && Integer.parseInt(couponId) == 115){	//初生蛋单品红包 couponId=115
				modelAndView.setViewName("weixin/singlecoupon/single_csd");
				return modelAndView;
			}else if(StringUtil.isNotNull(couponId) && Integer.parseInt(couponId) == 110){	//海陆双鲜单品红包 couponId=110
				modelAndView.setViewName("weixin/singlecoupon/single_hlsx");
				return modelAndView;
			}else if(StringUtil.isNotNull(couponId) && Integer.parseInt(couponId) == 238
					&& Integer.parseInt(specId) == 382){	//海陆双鲜单品红包 couponId=238
				modelAndView.setViewName("weixin/singlecoupon/single_redpacket");
				return modelAndView;
			}else if (StringUtil.isNotNull(couponBanner)){
				modelAndView.setViewName("weixin/singlecoupon/single_redpacket3");
				return modelAndView;
			}
		} catch (ParseException e) {
			logger.info("/weixin/singlecoupon/getRedpacket --error", e);
			modelAndView.addObject("tipsTitle", "领红包信息提示");
			modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
			modelAndView.setViewName("/weixin/fuwubc");
		}
		return modelAndView;
	}
	

	/**
	 * goTo 一键领取单品红包
	 * @param request couponId 红包id ,productId 产品id
	 * @author zhangjing 2017年11月21日 下午5:02:04
	 */
	@RequestMapping(value="getClickRedPacket")
	public ModelAndView getClickRedpacket(HttpServletRequest request, HttpServletResponse response,Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/singlecoupon/single_redpackets");
		CustomerVo customerVo=Constants.getCustomer(request);
		
		PageData pd = this.getPageData();
		String couponId = pd.getString("couponId");
		String productId = pd.getString("productId");
		int operateFlag = 0;
		try {
			if(StringUtil.isNull(couponId)){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "非法请求");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
		
			if(StringUtil.isNull(productId)){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "非法请求");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}

			List<SingleCouponSpecVo> scspec = singleCouponSpecManager.queryList(pd);
			if(scspec == null || scspec.size() == 0){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(couponId));
			if(singleCouponVo == null){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			String endTime = singleCouponVo.getEndTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long systime = System.currentTimeMillis();
		
			Date dt = sdf.parse(endTime);
			endTime = sdf.format(dt);
			if(singleCouponVo.getStatus().intValue() == 0
					|| dt.getTime() <= systime){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			singleCouponVo.setEndTime(endTime);
			
			ProductVo prvo = productManager.selectByPrimaryKey(Integer.parseInt(productId));
			if(prvo == null){
				modelAndView.addObject("tipsTitle", "领红包信息提示");
				modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			int flagkey = 1;
			if(customerVo != null && customerVo.getCustomer_id() != null){
				String phoneNumber = customerVo.getMobile();
				if(StringUtils.isNotBlank(phoneNumber)){
					pd.put("customerId", customerVo.getCustomer_id());
					pd.put("mobile", phoneNumber);
					List<UserSingleCouponVo> uscVoList = userSingleCouponManager.queryList(pd);
					if(uscVoList != null && scspec != null && uscVoList.size()==scspec.size()){//每个人只可以领取一个单品红包 //是否已領取
						flagkey = 2;
					}
				}
			}
			String regSource = "single_"+couponId;
			if(StringUtil.isNotNull(pd.getString("source"))){
				regSource = pd.getString("source");
			}
			
			operateFlag = 1;
			model.addAttribute("productId", productId);
			model.addAttribute("productName", prvo.getProduct_name());
			model.addAttribute("customer", customerVo);
			model.addAttribute("couponId", couponId);
			model.addAttribute("flagkey", flagkey);
			model.addAttribute("operateFlag", operateFlag);
			model.addAttribute("regSource", regSource);
			model.addAttribute("singleCouponVo", singleCouponVo);
		} catch (ParseException e) {
			logger.info("/weixin/singlecoupon/getClickRedPacket --error", e);
			modelAndView.addObject("tipsTitle", "领红包信息提示");
			modelAndView.addObject("tipsContent", "您来晚了，红包已被抢完了~");
			modelAndView.setViewName("/weixin/fuwubc");
		}
		return modelAndView;
	}
	
	
	/**
	 * 校验用户短信验证码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping("couponReceive")
	@ResponseBody
	public String couponReceive(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/singlecoupon/couponReceive--->begin");
		JSONObject json = new JSONObject(); 
		CustomerVo customer = Constants.getCustomer(request);
		PageData pd = this.getPageData();
		pd.put("customerId", customer.getCustomer_id());
		try {
			Map<String, Object> map = userSingleCouponManager.insertSingleCoupon(pd);
			if(map.get("result").equals("fail")){
				json.put("result", -1);
				json.put("msg", map.get("msg"));
			}else{
				json.put("result", 0);
			}
		
		} catch (Exception e) {
			logger.error("couponReceive error:"+e.getMessage());
			json.put("result", -1);
			json.put("msg", "系统异常");
		}
		logger.info("/weixin/singlecoupon/couponReceive--->end");
		return json.toString();
	}
	
	
	
	/**
	 * 直接领取单品多个规格的红包
	 * @param
	 * @return
	 * @author zhangjing 2017年11月21日 下午6:48:55
	 */
	@RequestMapping("getCouponsByProjectId")
	@ResponseBody
	public String getCouponsByProjectId(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/singlecoupon/getCouponsByProjectId--->begin");
		JSONObject json = new JSONObject(); 
		CustomerVo customer = Constants.getCustomer(request);
		PageData pd = this.getPageData();
		pd.put("customerId", customer.getCustomer_id());
		String phoneNum = pd.getString("phoneNum");
	
		if(StringUtils.isBlank(phoneNum)&&StringUtils.isNotBlank(customer.getMobile())){
			pd.put("phoneNum", customer.getMobile());
		}
		try {
			Map<String, Object> map = userSingleCouponManager.insertSingleCouponByProductId(pd);
			json.put("result", map.get("code"));
			json.put("msg", map.get("msg"));
			return json.toString();
		
		} catch (Exception e) {
			logger.error("couponReceive error:"+e.getMessage());
			json.put("result", -1);
			json.put("msg", "系统异常");
		}
		logger.info("/weixin/singlecoupon/getCouponsByProjectId--->end");
		return json.toString();
	}
	
	
	
	
	
	/**
	 * 校验用户短信验证码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkSmsCode")
	@ResponseBody
	public JSONObject checkSmsCode(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/singlecoupon/checkSmsCode--->begin");
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");
		
		//---->日志信息
		logger.info("校验操作页面传入的：手机号:"+phoneNum+",验证码:"+checkCode);
		JSONObject json = new JSONObject(); 
		//验证手机号
		Map<String,Object> mapError = userSingleCouponManager.CheckPhoneNoCode(pd);
		if(mapError!=null){
			if(mapError.get("code") != null && mapError.get("code").toString().equals("1020")){
				json.put("result", mapError.get("code"));
				json.put("msg", mapError.get("msg"));
				return json;
			}
		}	
		
		CustomerVo customer = Constants.getCustomer(request);
		
		//插入新手红包
		json = insertRegisterCoupon(customer,pd,request);
		if(json!=null&&json.size()>0){
			return json;
		}
		customer = Constants.getCustomer(request);
		if(customer!=null){
			customer.setPassword(null);
		}
		Constants.setCustomer(request, customer);
		//发送单品红包
		pd.put("customerId", customer.getCustomer_id());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = userSingleCouponManager.insertSingleCoupon(pd);
		} catch (ParseException e) {
			logger.error("/weixin/singlecoupon/couponReceive ------error", e);
		}
		String result = (String)map.get("result");
		if(result.equals("success")){
			json.put("result", "success");
			json.put("msg", "单品红包发放成功");
		}else{
			json.put("result", "fail");
			json.put("msg", map.get("msg"));
		}

		logger.info("/weixin/singlecoupon/checkSmsCode--->end");
		return json;
	}
	
	
	
	/**
	 * 校验用户短信验证码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkCodeNewCoupon")
	@ResponseBody
	public JSONObject checkCodeNewCoupon(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/singlecoupon/checkCodeNewCoupon--->begin");
		PageData pd = this.getPageData();
		String phoneNum = pd.getString("phoneNum");
		String checkCode = pd.getString("checkCode");
		String source = request.getSession().getAttribute("source")==null?"":request.getSession().getAttribute("source").toString();
		pd.put("regSource", source);
		//---->日志信息
		logger.info("校验操作页面传入的：手机号:"+phoneNum+",验证码:"+checkCode);
		JSONObject json = new JSONObject();
		//验证手机号
		Map<String,Object> mapError = userSingleCouponManager.CheckPhoneNoCode(pd);
		if(mapError!=null){
			if(mapError.get("code") != null && mapError.get("code").toString().equals("1020")){
				json.put("result", mapError.get("code"));
				json.put("msg", mapError.get("msg"));
				return json;
			}
		}
		//以下代码可以写到manager层
		
		CustomerVo customer = Constants.getCustomer(request);
		//插入新手红包
		json = insertRegisterCoupon(customer,pd,request);
		if(json!=null&&json.size()>0){
			return json;
		}
		if(customer!=null){
			customer.setPassword(null);
		}
		customer.setPassword(null);
		Constants.setCustomer(request, customer);
		//发送单品红包
		pd.put("customerId", customer.getCustomer_id());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = userSingleCouponManager.insertSingleCouponByProductId(pd);
			json.put("result", map.get("code"));
			json.put("msg", map.get("msg"));
			return json;
		} catch (ParseException e) {
			logger.error("/weixin/singlecoupon/checkCodeNewCoupon ------error", e);
		}
		

		logger.info("/weixin/singlecoupon/checkCodeNewCoupon--->end");
		return json;
	}
	
	
	/**
	 * 插入新手红包
	 * @param
	 * @return
	 * @author zhangjing 2017年11月21日 下午6:42:18
	 */
	private JSONObject insertRegisterCoupon(CustomerVo customer,PageData pd,HttpServletRequest request){
		JSONObject json = new JSONObject(); 
	
		String phoneNum = pd.getString("phoneNum");
		
		String regSource = pd.getString("regSource");
		int count=0;
		
		pd.put("mobile",phoneNum);
		 
		CustomerVo ykcustomer =  (CustomerVo)request.getSession().getAttribute("yk_customer");
		  if(customer ==null){//session 里面没有再从数据库里拿
			  customer=customerManager.findCustomer(pd);
			  if(customer==null){
				  customer = new CustomerVo();
				  customer.setMobile(phoneNum);
				  customer.setNickname("");
				  customer.setHead_url(WebConstant.USER_DEFAULT_PHOTO);
				  customer.setSource(regSource);
				  customer.setMobile(pd.getString("mobile"));
				  Random rnd = new Random();
				  int num = rnd.nextInt(89999) + 10000000;
				  String pwd = Md5Util.md532(String.valueOf(num));
				  customer.setPassword(pwd);
				  if(ykcustomer!=null){
					  customer.setSubscribe(ykcustomer.getSubscribe());
					  customer.setNickname(ykcustomer.getNickname());
					  customer.setOpenid(ykcustomer.getOpenid());
					  customer.setUnionid(ykcustomer.getUnionid());
					  customer.setSex(ykcustomer.getSex());
				  }
				  count = customerManager.insertCustomer(customer);
				  if(count <= 0){
					  json.put("flag", 0);
					  json.put("msg", "注册失败");
					  return json;
				  }
				  
				  String msg = "恭喜您成为挥货平台的会员，您的登录密码是["+num+"]，请及时修改您的登录密码！";
				  SendMsgUtil.sendMessage(pd.getString("mobile").trim(), msg);
				  couponInfoManager.insertRegisterCoupon(customer); //新手红包
			  }
			  customer.setPassword(null);
			  Constants.setCustomer(request, customer);
			  request.getSession().removeAttribute("yk_customer");
		}else{
		
			PageData cpd = new PageData();
			cpd.put("customer_id", customer.getCustomer_id());
			customer = customerManager.queryOne(cpd);
			if(StringUtil.isNotNull(customer.getMobile()) && !phoneNum.equals(customer.getMobile())){ ////判断填写的手机号码是否和用户绑定的一致
				json.put("result", "fail");
				json.put("msg", "您输入的手机号与账户绑定的不一致");
				logger.info(phoneNum + "您输入的手机号与账户绑定的不一致");
				return json;
			} 
			
			CustomerVo customerVo=customerManager.selectByMobile(phoneNum);
			if(customerVo != null && customer.getCustomer_id().intValue() != customerVo.getCustomer_id().intValue()){
				json.put("result", "fail");
				json.put("msg", "您输入的手机号已被使用");
				logger.info(phoneNum + "已注册");
				return json;
			}
			
			if(StringUtil.isNull(customer.getMobile())){ //用户未绑定手机号，走绑定逻辑
				customer.setMobile(phoneNum);
				PageData cuspd = new PageData();
				cuspd.put("customer_id", customer.getCustomer_id());
				cuspd.put("mobile", phoneNum);
				this.customerManager.updateCustoemrById(cuspd);
				couponInfoManager.insertRegisterCoupon(customer); //新手红包
			} 
		}
		return json;
	}
	
	
	

}
