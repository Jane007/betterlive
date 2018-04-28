package com.kingleadsw.betterlive.controller.wx.personalcenter;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.BanManager;
import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MobileMessageManager;
import com.kingleadsw.betterlive.biz.SysInviteManager;
import com.kingleadsw.betterlive.biz.SystemLevelManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.BanVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MobileMessageVo;
import com.kingleadsw.betterlive.vo.SysInviteVo;

/**
 * 微信端  个人中心管理
 * 2017-03-21 by chen
 */
@Controller
@RequestMapping(value = "/weixin/usercenter")
public class WxUserCenterController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxUserCenterController.class);
	
	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private RedisService redisService;

	@Autowired
	private CouponInfoManager couponInfoManager;
	
	@Autowired
	private MobileMessageManager mobileMessageManager;
	
	@Autowired
	private BanManager banManager;
	@Autowired
	private SysInviteManager sysInviteManager;
	@Autowired
	private SystemLevelManager systemLevelManager;
	
	/**
	 * 个人设置
	 */
	@RequestMapping("/toUserSetting")
	public ModelAndView toUserSetting(HttpServletRequest request,HttpServletResponse response){
		logger.info("/weixin/usercenter/toUserSetting  start ...");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_shezhi");
		logger.info("/weixin/usercenter/toUserSetting  end ...");
		return mv;
	}
	
	/**
	 * 我的收藏
	 */
	@RequestMapping("/toMyCollection")
	public ModelAndView toMyCollection(HttpServletRequest request,HttpServletResponse response){
		String msg="toMyCollection";
		logger.info("/weixin/usercenter/toMyCollection/ "+msg+"  start ...");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		ModelAndView mv=new ModelAndView("weixin/collection/wx_myCollection");
		mv.addObject("integralSwitch", IntegralConstants.INTEGRAL_SWITCH);
		mv.addObject("customerId", customer.getCustomer_id());
		logger.info(" /weixin/usercenter/toMyCollection/ "+msg+"  end ...");
		return mv;
	}
	
	/**
	 * 个人资料
	 */
	@RequestMapping("/toUserInfo")
	public ModelAndView toUserInfo(HttpServletRequest request,HttpServletResponse response){
		String msg="toUserInfo";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_user_info");
		mv.addObject("customer",customer);
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
	}
	
	/**
	 * 联系客服
	 */
	@RequestMapping("/contactUs")
	public ModelAndView contactUs(HttpServletRequest request,HttpServletResponse response){
		String msg="contactUs";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_contactus");
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
	}
	/**
	 * 账户信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAccount")
	public ModelAndView toAccount(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="toAccount";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_account");

		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		String phoneNum = customer.getMobile();
		String phone = "";
		if(StringUtils.isNotBlank(phoneNum)){
			phone = phoneNum.substring(0,3)+"****"+phoneNum.substring(7,phoneNum.length());
		}
		model.addAttribute("phone",phone);
		model.addAttribute("payPwd",customer.getPay_pwd());
		model.addAttribute("customer",customer);
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
		
	}
	/**
	 * 去修改手机
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateNewPhone")
	public ModelAndView toUpdateNewPhone(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="toUpdateNewPhone";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_bindingnewphone");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			mv.setViewName("weixin/wx_login");
			return mv;
		}
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		String phoneNum = customer.getMobile();
		String phone = "";
		if(StringUtils.isNotBlank(phoneNum)){
			phone = phoneNum.substring(0,3)+"****"+phoneNum.substring(7,phoneNum.length());
		}
		model.addAttribute("phone",phone);
		model.addAttribute("payPwd",customer.getPay_pwd());
		model.addAttribute("customer",customer);
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
		
	}
	
	
	/**
	 * 邀请信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSysInvite")
	@ResponseBody
	public Map<String,Object> getSysInvite(HttpServletRequest request){
		PageData pd = this.getPageData();
		
		pd.put("dictType", 1);
		SysInviteVo sysInviteVo = sysInviteManager.queryOne(pd);
		int inviteStatus = 0; //关闭
		String inviteBanner = "";
		if(sysInviteVo != null){
			inviteStatus = sysInviteVo.getObjId();
			inviteBanner = sysInviteVo.getObjValue();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("inviteStatus",inviteStatus);
		result.put("inviteBanner",inviteBanner);
		return CallBackConstant.SUCCESS.callback(result);
	}
	
	/**
	 * 个人中心更换手机号码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/updateNewPhone")
	@ResponseBody
	public void updateNewPhone(HttpServletRequest request,HttpServletResponse response,Model model){
		JSONObject json=new JSONObject(); 
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			json.put("result","failure");
			json.put("msg","请先登录");
			this.outObjectToJson(json, response);
			return;
		}
		
		PageData pd = this.getPageData();
		String mobile=pd.getString("mobile");
		if(StringUtil.isNull(mobile)){
			json.put("result","failure");
			json.put("msg","请输入手机号码");
			this.outObjectToJson(json, response);
			return;
		}
		PageData pds=new PageData();
		pds.put("mobile",mobile);
		int result=0;
		
		try {
			CustomerVo customerVo=customerManager.findCustomer(pd);
			//用户为空直接绑定手机号码
			if(null==customerVo){
				pds.put("customer_id", customer.getCustomer_id());
				result=customerManager.updateCustoemrById(pds);
			//用户不为空 且 只有App端绑定，删除app端信息,把app账户信息同步到微信账户	
			}else if(customerVo != null && StringUtil.isNotNull(customer.getMobile())
					&& !customer.getMobile().equals(customerVo.getMobile())){
				result = -1;
				json.put("result","exist");
				json.put("msg","手机号码已被注册");
			}
			
			if(result>=0){
				customer.setMobile(mobile);
				Constants.setCustomer(request, customer);
				redisService.delKey(mobile);
				json.put("result","succ");
				json.put("msg","绑定成功");
			}
			
		} catch (Exception e) {
			json.put("result","fail");
			json.put("msg","绑定失败,出现异常");
			logger.error("/weixin/usercenter/updateNewPhone --error", e);
		}
		
		this.outObjectToJson(json, response);
	}
	/**
	 * 去修改支付密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdatePayPwd")
	public ModelAndView toUpdatePayPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="toUpdatePayPwd";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_updatepaypwd");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			mv.setViewName("weixin/wx_login");
			return mv;
		}
		
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
		
	}
	
	@RequestMapping("/toUpdatepwd")
	public ModelAndView toUpdatepwd(HttpServletRequest request,HttpServletResponse response,Model model){
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(customer.getMobile().trim().length()==0||customer.getMobile()==null){
			return new ModelAndView("weixin/shezhi/wx_bindMobile");
		}
		String msg="toUpdatepwd";
		logger.info(" /weixin/shehzi/ "+msg+"  start ...");
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_gaiPassword");
		logger.info(" /weixin/shezhi/ "+msg+"  end ...");
		return mv;
	}
	
	@RequestMapping("/toAffirmpwd")
	public ModelAndView toAffirmpwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="toAffirmpwd";
		logger.info(" /weixin/shehzi/ "+msg+"  start ...");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(customer.getMobile().trim().length()==0||customer.getMobile()==null){
			return new ModelAndView("weixin/shezhi/wx_bindMobile");
		}
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_wangPassword");
		mv.addObject("customer", customer);
		logger.info(" /weixin/shezhi/ "+msg+"  end ...");
		return mv;
	}
	
	@RequestMapping("/affirmPayPwd")
	@ResponseBody
	public void affirmPayPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="affirmPayPwd";
		JSONObject json=new JSONObject(); 
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		PageData pd = this.getPageData();
		String oldPwd = pd.getString("payPwd");
		CustomerVo customer = Constants.getCustomer(request);
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		
		if(!oldPwd.equals(customer.getPay_pwd())){
			json.put("msg", "faild");
		}else{
			json.put("msg", "success");
		}
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		this.outObjectToJson(json, response);
	}
	
	
	
	@RequestMapping("/affirmPassword")
	@ResponseBody
	public void affirmPassword(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="affirmPassword";
		JSONObject json=new JSONObject(); 
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		PageData pd = this.getPageData();
		String password = pd.getString("password");
		CustomerVo customer = Constants.getCustomer(request);
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(!password.equals(customer.getPassword())){
			json.put("msg", "faild");
		}else{
			json.put("msg", "success");
		}
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		this.outObjectToJson(json, response);
	}
	
	
	@RequestMapping("/doUpdatePassword")
	@ResponseBody
	public void doUpdatePassword(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="doUpdatePassword";
		JSONObject json=new JSONObject(); 
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		PageData pd = this.getPageData();
		String password = pd.getString("password");
		CustomerVo customer = Constants.getCustomer(request);
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		if(password.equals(customer.getPassword())){
			json.put("msg", "fils");
			json.put("desc", "新旧密码不能一致");
			this.outObjectToJson(json, response);
		}
		pd.put("customer_id", customer.getCustomer_id());
		int count = customerManager.updateCustoemrById(pd);
		customer.setPassword(pd.getString("passwrod"));
		if(count>0){
			Constants.setCustomer(request, customer);
			json.put("msg", "success");
		}else{
			json.put("msg", "faild");
			json.put("desc", "修改失败");
		}
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		this.outObjectToJson(json, response);
	}
	
	/***
	 * 修改支付密码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/updatePayPwd")
	@ResponseBody
	public void doUpdatePayPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="doUpdatePayPwd";
		JSONObject json=new JSONObject(); 
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		PageData pd = this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		pd.put("customer_id", customer.getCustomer_id());
		int count = customerManager.updateCustoemrById(pd);
		customer.setPay_pwd(pd.getString("pay_pwd"));
		if(count>0){
			Constants.setCustomer(request, customer);
			json.put("msg", "success");
		}else{
			json.put("msg", "faild");
		}
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		this.outObjectToJson(json, response);
	}
	/**
	 * 去修改支付密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/fogetPayPwd")
	public ModelAndView fogetPayPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="fogetPayPwd";
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_forgetpaypwd");
		mv.addObject("customer", customer);
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
		
	}
	/**
	 * 修改个人信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateUserInfo")
	@ResponseBody
	public void updateUserInfo(HttpServletRequest request ,HttpServletResponse response){
		String msg = "updateUserInfo";
		logger.info("/weixin/usercenter/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer==null || customer.getCustomer_id() == null){
			json.put("result", "failure");
			json.put("msg", "请先登录");
			this.outObjectToJson(json, response);
			return;
		}
		
		String sex = request.getParameter("sex");
		String birthday=request.getParameter("birthday");
		String nickname=request.getParameter("nickname");
		pd.put("sex", sex);
		pd.put("birthday", birthday);
		pd.put("nickname", nickname);
		pd.put("customer_id",customer.getCustomer_id());
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			MultipartFile touImg = multipartRequest.getFile("touxiang");
			String homeFilePath = "touxiang/";
			String insertPannerImg = "";
		
	    	if(touImg.getSize() > 0){
	    		String fileName = touImg.getOriginalFilename(); // 原始文件名
	 			String prefix = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
				switch (prefix.toLowerCase()) {
				case ".jpg":
					break;
				case ".jpeg":
					break;
				case ".bmp":
					break;
				case ".png":
					break;
				default:
					json.put("result", "fail");
					json.put("msg", "请上传正确的文件类型");
					this.outObjectToJson(json, response);
					return;
				}
	    		insertPannerImg = ImageUpload.uploadFile(touImg, homeFilePath);
			}
	    	pd.put("head_url", insertPannerImg);
			int result= customerManager.updateCustoemrById(pd);
			
			//这里 是 理论上修改，需要检验是否正确
			PageData pd1 = new PageData();
			pd1.put("customer_id", customer.getCustomer_id());
			pd1.put("head_url", customer.getHead_url());
			
			CustomerVo customer1 = customerManager.queryOne(pd1);
			Constants.setCustomer(request, customer1);
			
			if(result>0){
				json.put("result", "succ");
				json.put("msg", "修改成功");
			}else{
				json.put("result", "fail");
				json.put("msg", "修改失败");
			}
		} catch (Exception e) {
			json.put("result", "exec");
			json.put("msg", "出现异常");
			logger.info("/weixin/usercenter/"+msg+"  --error", e);
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			logger.info("/weixin/usercenter/"+msg+"  --error", e);
		}
	}
	
	
	/**
	 * 下单时 使用礼品卡
	 * 
	 * 目前礼品卡与优惠券不能够同时使用,所以需要去除优惠券,在绑定礼品卡
	 */
	@RequestMapping("/affirmPayPwdByOrder")
	@ResponseBody
	public void affirmPayPwdByOrder(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="affirmPayPwdByOrder";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		
		JSONObject json=new JSONObject(); 
		
		CustomerVo customer=Constants.getCustomer(request);
		
		PageData pd = this.getPageData();
		String oldPwd = pd.getString("payPwd");
		
		//保存用户购买到session中, 每次去读取缓存中是否存在需要购买的商品     
		String key="readybuy_"+customer.getCustomer_id();               //缓存参数
		PageData pds=(PageData) request.getSession().getAttribute(key);
	     
		if(!oldPwd.equals(customer.getPay_pwd())){
			json.put("result", "fail");
			
			pds.put("useGiftCard","fail");
			this.outObjectToJson(json, response);
			return;
		}
		
		json.put("result", "succ");
		pds.put("useGiftCard","succ");
		
		request.getSession().setAttribute(key,pds);
		
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		
		this.outObjectToJson(json, response);
		return;
	}

	
	/**
	 * 微信个人中心绑定手机号码
	 */
	@RequestMapping("/toBoundPhone")
	public ModelAndView toBoundPhone(HttpServletRequest request,HttpServletResponse response){
		String msg="toBoundPhone";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_bindMobile");
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
	}
	
	@RequestMapping("/toAboutUs")
	public ModelAndView toAboutUs(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_about");
		return mv;
	}
	
	@RequestMapping("/toContactUs")
	public ModelAndView toContactUs(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_contact_us");
		return mv;
	}
	
	@RequestMapping("/toIntroduce")
	public ModelAndView toIntroduce(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_introduce");
		return mv;
	}
	
	
	
	@RequestMapping("/toAccountUs")
	public ModelAndView toAccountUs(HttpServletRequest request,HttpServletResponse response , Model model){
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		ModelAndView mv=new ModelAndView("weixin/shezhi/wx_account");
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		String phoneNum =customer.getMobile();
		String phone = "";
		if(StringUtils.isNotBlank(phoneNum)){
			phone = phoneNum.substring(0,3)+"****"+phoneNum.substring(7,phoneNum.length());
		}
		model.addAttribute("phone",phone);
		return mv;
	}
	
	@RequestMapping("/toEditNewPhone")
	public ModelAndView toEditNewPhone(HttpServletRequest request,HttpServletResponse response,Model model){
		String msg="toEditNewPhone";
		logger.info(" /weixin/usercenter/ "+msg+"  start ...");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}

		String phoneNum = customer.getMobile();
		String phone = "";
		if(StringUtils.isNotBlank(phoneNum)){
			phone = phoneNum.substring(0,3)+"****"+phoneNum.substring(7,phoneNum.length());
		}
		model.addAttribute("phone",phone);
		model.addAttribute("customer",customer);
		ModelAndView mv=new ModelAndView("weixin/usercenter/wx_bindingnewphone");
		logger.info(" /weixin/usercenter/ "+msg+"  end ...");
		return mv;
		
	}
	
	
	/**
	 * 注册
	 * 2017-03-30 by chen
	 */
	@RequestMapping("/toRegeditUser")
	@ResponseBody
	public void toRegeditUser(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info(" /weixin/usercenter/toRegeditUser,start ...");
		
		JSONObject json=new JSONObject(); 
		
		CustomerVo customer=Constants.getCustomer(request);
		
		PageData pd = this.getPageData();
		
		String mobile=pd.getString("mobile");
		String code=pd.getString("code");
		String pwd = pd.getString("password");
		
		if(StringUtil.isNull(mobile)){
			json.put("result","fail");
			json.put("msg","请输入手机号码");
			this.outObjectToJson(json, response);
			return;
		}
		if(StringUtil.isNull(pwd)){
			json.put("result","fail");
			json.put("msg","请输入密码");
			this.outObjectToJson(json, response);
			return;
		}
		if(StringUtil.isNull(code)){
			json.put("result","fail");
			json.put("msg","验证码错误");
			this.outObjectToJson(json, response);
			return;
		}
		
		String redisCode=redisService.getString(mobile); //验证码  保存本地缓存，有效期5分钟
		if(StringUtil.isNull(redisCode)){
			json.put("result","fail");
			json.put("msg","验证码已过期");
			this.outObjectToJson(json, response);
			return;
		}
		if(!code.equals(redisCode)){
			json.put("result","fail");
			json.put("msg","验证码错误");
			this.outObjectToJson(json, response);
			return;
		}
		
		int result = 0;
		PageData pds=new PageData();
		pds.put("mobile",mobile);
		try {
				
			CustomerVo customerVo=customerManager.findCustomer(pds);
			if(customerVo != null){
				json.put("result","exist");
				json.put("msg","手机号码已被注册");
				this.outObjectToJson(json, response);
				return;
			} 
			customerVo = new CustomerVo();
			customer.setMobile(mobile);
    		customer.setPassword(pwd);
    		if(StringUtil.isNotNull(pd.getString("source"))){
    			customer.setSource(pd.getString("source"));
    		}else{
    			customer.setSource("weixin_browser");
    		}
    		
			result = customerManager.insertCustomer(customer);
			result += couponInfoManager.insertRegisterCoupon(customer);
			
			if(result > 0){
				redisService.delKey(mobile);
				if(customer != null){
					Constants.removeUser(request);
				}
				Constants.setCustomer(request, customerVo);
				json.put("result","succ");
				json.put("msg","绑定成功");
			}
				
			} catch (Exception e) {
				json.put("result","fail");
				json.put("msg","绑定失败,出现异常");
				logger.info("注册异常!", e);
				this.outObjectToJson(json, response);
			}
			
		
		logger.info("/weixin/usercenter/toRegeditUser，end ...");
		
		this.outObjectToJson(json, response);
	}
	
	
	/**
	 * 注册
	 * 2017-03-30 by chen
	 */
	@RequestMapping("/bindMobile")
	@ResponseBody
	public void bindMobile(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info(" /weixin/usercenter/bindMobile,start ...");
		
		JSONObject json=new JSONObject(); 
		
		CustomerVo customer=Constants.getCustomer(request);
		
		if(customer == null || customer.getCustomer_id() == null){
			json.put("result","fail");
			json.put("msg","还没有登录");
			this.outObjectToJson(json, response);
			return;
		}
		
		PageData pd = this.getPageData();
		
		String mobile=pd.getString("mobile");
		String code=pd.getString("verifycode");
		
		if(StringUtil.isNull(mobile)){
			json.put("result","fail");
			json.put("msg","请输入手机号码");
			this.outObjectToJson(json, response);
			return;
		}
		if(StringUtil.isNull(code)){
			json.put("result","fail");
			json.put("msg","验证码错误");
			this.outObjectToJson(json, response);
			return;
		}
		
		String redisCode=redisService.getString(mobile); //验证码  保存本地缓存，有效期5分钟
	
		if(StringUtil.isNull(redisCode)){
			json.put("result","fail");
			json.put("msg","验证码已过期");
			this.outObjectToJson(json, response);
			return;
		}
		if(!code.equals(redisCode)){
			json.put("result","fail");
			json.put("msg","验证码错误");
			this.outObjectToJson(json, response);
			return;
		}
		
		int result = 0;
		PageData pds=new PageData();
		pds.put("mobile",mobile);
		try {
				
			CustomerVo customerVo=customerManager.findCustomer(pds);
			
			if(customerVo != null && customer.getCustomer_id()!=null){
				if(customerVo.getCustomer_id().intValue() != customer.getCustomer_id().intValue()){
					json.put("result","exist");
					json.put("msg","手机号码已被使用");
					this.outObjectToJson(json, response);
				}
			}
			pds.put("customer_id", customer.getCustomer_id());
			result = customerManager.updateCustoemrById(pds);
			
			if(result > 0){
				customer.setMobile(mobile);
				redisService.delKey(mobile);
				Constants.setCustomer(request, customer);
				json.put("result","succ");
				json.put("msg","绑定成功");
			}
				
			} catch (Exception e) {
				json.put("result","fail");
				json.put("msg","绑定失败,出现异常");
				logger.info("绑定手机号异常!", e);
				this.outObjectToJson(json, response);
			}
			
		
		logger.info("/weixin/usercenter/bindMobile，end ...");
		
		this.outObjectToJson(json, response);
	}
	
	
	/**
	 * 发送验证码  手机  或者 邮箱     默认保存在本地缓存5分钟
	 */
	@RequestMapping(value = "/sendEmail")
	public void sendEmail(HttpServletRequest httpRequest,HttpServletResponse response) {
		PageData pd = this.getPageData();
		String selectType = pd.getString("selectType");
		String phone = pd.getString("eMail");
		
		//随机生成 
		Random rnd = new Random();
        int num = rnd.nextInt(89999) + 10000;
        /*num=33225;*/
        JSONObject json = new JSONObject();
        json.put("msg", "获取验证码成功");
		json.put("result", "succ");
		logger.info(phone + "获取验证码【"+num+"】");
        if("phone".equals(selectType)){
        	try {
        		PageData msgpd = new PageData();
    			msgpd.put("requestIp", getIpAddr(httpRequest));
    			List<BanVo> banIps = this.banManager.queryListPage(msgpd);
    			if(banIps != null && banIps.size() > 0){
    			 	json.put("result", "fail");
    	        	json.put("msg", "频繁请求，已被禁止访问");
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			msgpd.clear();
    			msgpd.put("requestMobile", phone);
    			List<BanVo> banPhones = this.banManager.queryListPage(msgpd);
    			if(banPhones != null && banPhones.size() > 0){
    			 	json.put("result", "fail");
    	        	json.put("msg", "频繁请求，已被禁止访问");
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			
    			msgpd.clear();
    			msgpd.put("requestIp", getIpAddr(httpRequest));
    			msgpd.put("msgType", 1);
    			msgpd.put("queryFlag", 2);
    			List<MobileMessageVo> msgIps = mobileMessageManager.queryListPage(msgpd);
    			if(msgIps != null && msgIps.size() >= 5){
    				BanVo banVo = new BanVo();
    				banVo.setRequestIp(getIpAddr(httpRequest));
    				banVo.setRequestMobile("");
    				banManager.insert(banVo);
    				json.put("result", "fail");
    	        	json.put("msg", "频繁请求，已被禁止访问");
    	        	this.outObjectToJson(json, response);
    				return;
    			}
    			
    			msgpd.clear();
    			msgpd.put("requestMobile", phone);
    			msgpd.put("msgType", 1);
    			msgpd.put("queryFlag", 2);
    			List<MobileMessageVo> msgPhones = mobileMessageManager.queryListPage(msgpd);
    			if(msgPhones != null && msgPhones.size() >= 5){
    				BanVo banVo = new BanVo();
    				banVo.setRequestIp("");
    				banVo.setRequestMobile(phone);
    				banManager.insert(banVo);
    				json.put("result", "fail");
    	        	json.put("msg", "频繁请求，已被禁止访问");
    	        	this.outObjectToJson(json, response);
    				return;
    			}
    			
    			CustomerVo customer = Constants.getCustomer(httpRequest);
            	int custId = 0;
            	if(customer != null && customer.getCustomer_id() != null){
            		custId = customer.getCustomer_id();
            	}

            	msgpd.clear();
    			msgpd.put("msgType", 1);
    			msgpd.put("queryFlag", 1);
    			msgpd.put("requestIp", getIpAddr(httpRequest));
    			msgpd.put("tag", 1);
    			List<MobileMessageVo> msgips = mobileMessageManager.queryListPage(msgpd);
    			if(msgips != null && msgips.size() > 2){
    			 	json.put("result", "fail");
    	        	json.put("msg", "一分钟内只能请求发送两次");
    	        	logger.info("["+getIpAddr(httpRequest)+"]" + "一分钟内只能请求发送两次");
    	        	this.insertMobileMessage(custId, num, phone, httpRequest, 0);
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			 
    			msgpd.put("tag", 60);
    			List<MobileMessageVo> msgipvos = mobileMessageManager.queryListPage(msgpd);
    			if(msgipvos != null && msgipvos.size() > 3){
    				json.put("result", "fail");
    	        	json.put("msg", "一小时内只能请求发送三次");
    	        	logger.info("["+getIpAddr(httpRequest)+"]" + "一小时内只能请求发送三次");
    	        	this.insertMobileMessage(custId, num, phone, httpRequest, 0);
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			
    			msgpd.clear();
    			msgpd.put("tag", 1);
    			msgpd.put("msgType", 1);
    			msgpd.put("queryFlag", 1);
    			msgpd.put("requestMobile", phone);
    			List<MobileMessageVo> msgms = mobileMessageManager.queryListPage(msgpd);
    			if(msgms != null && msgms.size() > 1){
    			 	json.put("result", "fail");
    	        	json.put("msg", "一分钟内只能请求发送两次");
    	        	logger.info(phone + "1分钟内请求发送次数不能超过2条");
    	        	this.insertMobileMessage(custId, num, phone, httpRequest, 0);
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			
    			msgpd.put("tag", 60);
    			List<MobileMessageVo> msgs = mobileMessageManager.queryListPage(msgpd);
    			if(msgs != null && msgs.size() > 3){
    			 	json.put("result", "fail");
    	        	json.put("msg", "一小时内只能请求发送三次");
    	        	logger.info(phone + "1小时内请求发送次数不能超过3条");
    	        	this.insertMobileMessage(custId, num, phone, httpRequest, 0);
    	        	this.outObjectToJson(json, response);
    	        	return;
    			}
    			
        		boolean sendResult = SendMsgUtil.sendCheckCode(phone, num);
        		int sendFlag = 0;
    	        if (sendResult) { //发送成功
    	        	sendFlag = 1;
    	        	redisService.setex(phone, String.valueOf(num), 300); //验证码  保存本地缓存，有效期5分钟
    	        	json.put("result", "succ");
    	        	json.put("msg", "短信发送成功");
    	        	logger.info(phone + "获取验证码【"+num+"】,有效期5分钟");
    	        } else {
    	        	json.put("result", "fail");
    	        	json.put("msg", "短信发送失败");
    	        	logger.error(phone + "获取验证码【"+num+"】失败");
    	        }
    	      	this.insertMobileMessage(custId, num, phone, httpRequest, sendFlag);
			} catch (Exception e) {
				json.put("msg", "获取验证码失败");
				json.put("result", "fail");
				logger.error("/weixin/usercenter/sendEmail --error", e);
			}
		}
        
		this.outObjectToJson(json, response);
	}
	
	public void insertMobileMessage(int custId, int num, String phoneNum, HttpServletRequest request, int flag){
    	MobileMessageVo mmvo = new MobileMessageVo();
    	mmvo.setCustomerId(custId);
    	mmvo.setMsgContent("["+phoneNum+"]绑定手机号，验证码["+num+"]获取成功");
    	mmvo.setMsgType(1);
    	mmvo.setRequestIp(getIpAddr(request));
    	mmvo.setRequestMobile(phoneNum);
    	mmvo.setSendFlag(flag);
    	mobileMessageManager.insert(mmvo);
    }
	
	/**
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
	
}
