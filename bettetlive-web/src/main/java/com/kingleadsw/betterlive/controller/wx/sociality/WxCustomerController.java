package com.kingleadsw.betterlive.controller.wx.sociality;

import java.util.HashMap;
import java.util.Map;

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

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.IpAndMac;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.HttpClientTool;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;


/**
 * 微信端  个人主页资料管理
 * 2017-03-13 by chen
 *
 */
@Controller
@RequestMapping(value = "/weixin/customer")
public class WxCustomerController extends AbstractWebController {
	private static Logger logger = Logger.getLogger(WxCustomerController.class);
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private RedisService redisService;
	/**
	 * 进入个人资料设置页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCustomerModify")
	public ModelAndView toCustomerModify(HttpServletRequest request,HttpServletResponse response){
		logger.info(" /weixin/customer/toCustomerModify  start ...");
		ModelAndView mv = new ModelAndView("weixin/sociality/customer_modify");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		mv.addObject("customer",customer);
		logger.info(" /weixin/customer/toCustomerModify  end ...");
		return mv;
	}
	
	@RequestMapping(value = "/changeSex")
	@ResponseBody
	public Map<String, Object> changeSex(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(" /weixin/customer/changeSex  start ...");
		Map<String, Object> map = new HashMap<String, Object>();
		String sex=request.getParameter("sex");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			map.put("result", "failure");
			map.put("msg", "请先登录");
			 return map;
		}
		PageData pd = new PageData();
 		pd.put("sex", sex);
 		pd.put("customer_id", customer.getCustomer_id());
		int iret = customerManager.updateCustoemrById(pd);
		if (iret <= 0) {
			map.put("result", "fail");
			map.put("msg", "修改失败");
			return map;
		}
		map.put("result", "success");
		map.put("msg", "修改成功");
		logger.info(" /weixin/customer/changeSex  end ...");
		return map;
	}
	
	/**
	 * 进入昵称修改页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateNickName")
	public ModelAndView toUpdateNickName(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info(" /weixin/customer/toUpdateNickName  start ...");
		ModelAndView mv = new ModelAndView("weixin/sociality/update_name");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		model.addAttribute("nickName", customer.getNickname());
		logger.info(" /weixin/customer/toUpdateNickName  end ...");
		return mv;
	}
	
	@RequestMapping(value = "/editNickName")
	@ResponseBody
	public Map<String, Object> editNickName(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(" /weixin/customer/editNickName  start ...");
		Map<String, Object> map = new HashMap<String, Object>();
		String nickName=request.getParameter("nickName");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			map.put("result", "failure");
			map.put("msg", "请先登录");
			 return map;
		}
		PageData pd = new PageData();
 		pd.put("nickname", nickName);
 		pd.put("customer_id", customer.getCustomer_id());
		int iret = customerManager.updateCustoemrById(pd);
		if (iret <= 0) {
			map.put("result", "fail");
			map.put("msg", "修改失败");
			return map;
		}
		map.put("result", "success");
		map.put("msg", "修改成功");
		logger.info(" /weixin/customer/editNickName  end ...");
		return map;
	}
	
	/**
	 * 进入个人签名修改页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateSignature")
	public ModelAndView toUpdateSignature(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info(" /weixin/customer/toUpdateSignature  start ...");
		ModelAndView mv = new ModelAndView("weixin/sociality/update_signature");
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		customer = customerManager.selectByPrimaryKey(customer.getCustomer_id());
		model.addAttribute("signature", customer.getSignature());
		logger.info(" /weixin/customer/toUpdateSignature  end ...");
		return mv;
	}
	
	@RequestMapping(value = "/editSignature")
	@ResponseBody
	public Map<String, Object> editSignature(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(" /weixin/customer/editSignature  start ...");
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			map.put("result", "failure");
			map.put("msg", "请先登录");
			 return map;
		}
		
		String signature=request.getParameter("signature");
		if(StringUtil.isNull(signature) || signature.length() <= 0){
			map.put("result", "fail");
			map.put("msg", "签名不能为空");
			return map;
		}
		if(signature.length() > 25){
			map.put("result", "fail");
			map.put("msg", "限制25个字");
			return map;
		}
		PageData pd = new PageData();
 		pd.put("signature", signature);
 		pd.put("customer_id", customer.getCustomer_id());
		int iret = customerManager.updateCustoemrById(pd);
		if (iret <= 0) {
			map.put("result", "fail");
			map.put("msg", "修改失败");
			return map;
		}
		map.put("result", "success");
		map.put("msg", "修改成功");
		logger.info(" /weixin/customer/editSignature  end ...");
		return map;
	}
	
	/**
	 * 去修改绑定手机
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateNewPhone")
	public ModelAndView toUpdateNewPhone(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info(" /weixin/customer/toUpdateNewPhone  start ...");
		
		ModelAndView mv=new ModelAndView("weixin/sociality/wx_bindingnewphone");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			mv.setViewName("redirect:/weixin/tologin");
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
		logger.info(" /weixin/customer/toUpdateNewPhone  end ...");
		return mv;
	}
	
	/**
	 * 个人主页更换手机号码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/editNewPhone")
	@ResponseBody
	public void editNewPhone(HttpServletRequest request,HttpServletResponse response,Model model){
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
			logger.error("/weixin/usercenter/editNewPhone --error", e);
		}
		
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 修改个人头像
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editHeadUrl")
	@ResponseBody
	public void editHeadUrl(HttpServletRequest request ,HttpServletResponse response){
		logger.info(" /weixin/customer/editHeadUrl  start ...");
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		CustomerVo customer = Constants.getCustomer(request);
		if(customer==null || customer.getCustomer_id() == null){
			json.put("result", "failure");
			json.put("msg", "请先登录");
			this.outObjectToJson(json, response);
			return;
		}
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
					json.put("msg", "请上传正确的图片类型");
					this.outObjectToJson(json, response);
					return;
				}
	 			
	    		insertPannerImg = ImageUpload.uploadFile(touImg, homeFilePath);
			}else{
				json.put("result", "fail");
				json.put("msg", "请上传图片");
				this.outObjectToJson(json, response);
				return;
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
			logger.error("/weixin/usercenter/editHeadUrl", e);
			
			json.put("result", "exec");
			json.put("msg", "出现异常");
		}
		
		
		logger.info(" /weixin/customer/editHeadUrl  end ...");
		this.outObjectToJson(json, response);
	}
	
	@RequestMapping(value = "/bindWeChat")
	@ResponseBody
	public void bindWeChat(HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		logger.info(" /weixin/customer/bindWeChat  start ...");
		String url = request.getRequestURL().toString();
		String params = request.getQueryString();
		if (params != null) {
			url += "?"+request.getQueryString();
		}
		
		
		String ip = IpAndMac.getIpAddr(request);
		
		logger.info("请求ip：" + ip);
		
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		
		
		if (null == code || "".equals(code)) {
			logger.info("进入网页静默授权页面");
			String authUrl = WxUtil.getAuthUrl(url.toString());
			response.sendRedirect(authUrl);
			return;
		}else{
			// code不为空，网页授权方式访问，且已经获取到code
			if ("userinfo".equals(state)) {
				logger.info("code:"+code);
				if (StringUtil.isNull(code)) {  //用户拒绝授权
		            logger.info("用户没有用户code");
		            return;
		        }
				
				
				String get_access_token_url = new StringBuilder(WebConstant.WX_ACCESS_TOKEN_URL).append("?appid=").append(WebConstant.WX_APPID).append("&secret=")
		        		.append(WebConstant.WX_SECRET).append("&code=").append(code).append("&grant_type=authorization_code").toString();

		        JSONObject jsonObject = HttpClientTool.httpsRequest(get_access_token_url, "GET", null);
		        if (jsonObject != null) {
		        	Object open_id = jsonObject.get("openid");
		        	Object union_id = jsonObject.get("unionid");	//多个平台绑定微信使用unionid作为唯一标识
		            if (open_id == null || union_id == null) {
		            	logger.error("调用网页静默授权获取用户信息失败！");
		            	logger.info("errcode:	" + jsonObject.get("errcode") 
		            			+"；errmsg:  " + jsonObject.get("errmsg"));
		            	return;
		            }else{
		            	String openid = String.valueOf(open_id);
		            	String unionid = String.valueOf(union_id);
		                PageData pd = new PageData();
		                pd.put("unionid",union_id);
		            	CustomerVo checkCust = this.customerManager.queryOne(pd);
						if(checkCust != null && checkCust.getCustomer_id() > 0){
							 response.sendRedirect(request.getContextPath()+"/common/toFuwubc?ttitle=绑定微信提示信息&tipsContent=微信号已被其他账户绑定");
						}else{
			                logger.info("openid:   " + openid);
			                CustomerVo customer = Constants.getCustomer(request);
			                customer.setOpenid(openid);
			                customer.setUnionid(unionid);
			                customerManager.updateByPrimaryKey(customer);
			                logger.info("设置用户信息到session：" + customer);
			                Constants.setCustomer(request, customer);
			                response.sendRedirect(request.getContextPath()+"/weixin/customer/toCustomerModify");
						}
		            }
		        	
		        }
			}
		}
		
		logger.info(" /weixin/customer/bindWeChat  end ...");
	}
}
