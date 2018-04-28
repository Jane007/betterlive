package com.kingleadsw.betterlive.controller.app.share;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ShareManager;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ShareVo;

@Controller
@RequestMapping(value = "/app/share")
public class AppShareController extends AbstractWebController {
	
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ShareManager shareManager;
	@Autowired
	private CustomerIntegralRecordManager customerIntegralRecordManager;
	
	/**
	 * 添加分享内容
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/addShare")
	@ResponseBody
	public Map<String, Object> addShare(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();

		String token = pd.getString("token");  	
		int customerId = 0;
		
	
		//用户标识
		if (StringUtil.isNotNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
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
		
		String integral = "0";
		int result = 0;
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
			result = customerIntegralRecordManager.queryIntegralRecordCount(cl);
			
			if (result == 0) {
				// 分享成功领取积分奖励
				pd.clear();
				pd.put("integralType", integralType);
				pd.put("objId", objId);
				pd.put("customerId", customerId);
				pd.put("myShareId", shareVo.getShareId());
				int integralFlag = this.shareManager.shareIntegralRecord(pd);
				if (integralFlag == IntegralConstants.COMMON_STATUS_YES) {
					integral = IntegralConstants.SHARE_PROFIT_INTEGRAL.toString();
				}
			}
		}
		Map<String,Object> map = CallBackConstant.SUCCESS.callback(shareVo.getShareId());
		map.put("integral", integral);
		return map;
	}
}
