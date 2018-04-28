package com.kingleadsw.betterlive.controller.wx.share;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.ShareManager;
import com.kingleadsw.betterlive.biz.VersionInfoManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ShareVo;
import com.kingleadsw.betterlive.vo.VersionInfoVo;

@Controller
@RequestMapping(value = "/weixin/share")
public class WxShareController extends AbstractWebController{

	private static Logger logger = Logger.getLogger(WxShareController.class);
	
	@Autowired
	private VersionInfoManager versionInfoManager;
	
	@Autowired
	private ShareManager shareManager;
	
	@Autowired
	private CustomerIntegralRecordManager customerIntegralRecordManager;
	/**
	 * 查询商品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/shareDownloadApp")
	@ResponseBody
	public ModelAndView shareDownloadApp(HttpServletRequest request,HttpServletResponse response, Model model) {
		logger.info("进入APP下载引导页....开始");
		ModelAndView modelAndView=new ModelAndView("weixin/downApp");
		PageData pd = this.getPageData();
		VersionInfoVo verInfo = versionInfoManager.queryOne(pd);
		model.addAttribute("verInfo", verInfo);
		model.addAttribute("iosDownloadUrl", WebConstant.IOS_HUIHUO_APPSTORE_LOCAL);
		logger.info("进入APP下载引导页....结束");
		return modelAndView;
	}
	
	
	/**
	 * 添加分享内容
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/addShare")
	@ResponseBody
	public Map<String, Object> addShare(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map  = new HashMap<String, Object>();
		CustomerVo customer = Constants.getCustomer(request);
		
		if (null == customer || customer.getCustomer_id() == null) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		int customerId = customer.getCustomer_id();
		pd.put("customerId", customerId);
		
		if(StringUtil.isNull(pd.getString("shareType"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("分享类型为空");
		}
		
		if(StringUtil.isNull(pd.getString("objId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("业务主键为空"); 
		}
		int shareType = pd.getInteger("shareType");
		int objId = pd.getInteger("objId");
		
		ShareVo shareVo = new ShareVo();
		shareVo.setShareType(shareType);
		shareVo.setCustomerId(customerId);
		shareVo.setObjId(objId);
		int count = this.shareManager.insertShare(shareVo);
		if(count <= 0){
			return CallBackConstant.FAILED.callback();
		}
		
		if(shareType != 3 && shareType != 4){
			return CallBackConstant.SUCCESS.callback(shareVo.getShareId());
		}
		
		if (IntegralConstants.INTEGRAL_SWITCH != 1) { // 分享获取金币积分开关判断
		
			int integralType = IntegralConstants.INTEGRAL_RECORD_TYPE_FOUR;
			if (shareType == 4) {
				integralType = IntegralConstants.INTEGRAL_RECORD_TYPE_THREE;
			}
			
			PageData cl = new PageData();
			cl.put("customerId", customerId);
			cl.put("integralType", integralType);
			cl.put("recordType", IntegralConstants.RECORD_INCOME_YES);
			cl.put("checkDay", 1);
			cl.put("objId", objId);
			int result = customerIntegralRecordManager.queryIntegralRecordCount(cl);
			
			if (result == 0) {
				map.put("hasIntegralFlag", 0);
				// 分享成功领取积分奖励
				pd.clear();
				pd.put("integralType", integralType);
				pd.put("objId", objId);
				pd.put("customerId", customerId);
				this.shareManager.shareIntegralRecord(pd);
				
			}else{
				map.put("hasIntegralFlag", 1);
				
			}
			
		}
		return CallBackConstant.SUCCESS.callback(map);
	}
}
