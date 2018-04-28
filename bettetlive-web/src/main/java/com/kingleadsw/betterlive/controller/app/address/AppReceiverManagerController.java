package com.kingleadsw.betterlive.controller.app.address;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ReceiverAddressManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.controller.wx.myaddress.WxReceiverManagerController;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ReceiverAddressVo;

/**
 * 
 * @author xz
 * 2017-4-11
 * 收货地址管理
 */
@Controller
@RequestMapping(value = "/app/addressmanager")
public class AppReceiverManagerController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxReceiverManagerController.class);

	@Autowired
	private ReceiverAddressManager receiverAddressManager;
	@Autowired
	private CustomerManager customerManager;
	
	/**
	 * 根据客户ID查询所有的收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toReceiverAddress")
	@ResponseBody
	public Map<String,Object> toReceiverAddress(HttpServletRequest request) {
		logger.info("/app/addressmanager/toReceiverAddress begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		
		String token=pd.getString("token");    //用户标识
		if(null==token || "".equals(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(null == customer){
			map.put("msg","用户信息不能为空");
			return CallBackConstant.PARAMETER_ERROR.callback(map);
		}
		
		
		pd.put("customerId", customer.getCustomer_id().toString());
		List<ReceiverAddressVo> listReceiverAddress = null;
		try {
			listReceiverAddress = receiverAddressManager.queryList(pd);
		} catch (Exception e) {
			logger.error("/app/addressmanager/toReceiverAddress  app端查询用户收货地址出现异常");
			
			map.put("msg","查询用户收货地址异常");
			return CallBackConstant.FAILED.callback(map);
		}
		
		map.put("listReceiverAddress",listReceiverAddress);
		
		logger.info("/app/addressmanager/toReceiverAddress  end");
		
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	
	
	
	
	
	
	/**
	 * 设为默认地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatedeDaultReceive")
	@ResponseBody
	public Map<String,Object>  updatedeDaultReceive(HttpServletRequest request){
		logger.info("/app/receiveraddress/updatedeDaultReceive， begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd=this.getPageData();
		String receiverId = pd.getString("receiverId"); 	//收货人id
		String token=pd.getString("token");    				//用户标识
		if(null==token || "".equals(token)){
			logger.error("token不能为空");
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		if(null==receiverId || "".equals(receiverId)){
			logger.error("receiverId不能为空");
			return CallBackConstant.PARAMETER_ERROR.callbackError("收获地址id不能为空");
		}
		
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(null == customer){
			logger.error("用户登陆超时");
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		//先根据客户ID把该用户所有的地址都设置非默认
		pd.put("customerId",customer.getCustomer_id());
		int result = receiverAddressManager.updateReceiverToDefault(pd);
		
		logger.info("/app/receiveraddress/updatedeDaultReceive， end，result：" + result);
		if (result>1) {
			return CallBackConstant.SUCCESS.callback(map);
		} else {
			map.put("msg","设置默认地址失败");
			return CallBackConstant.FAILED.callback(map);
		}
	}
	
	
	/**
	 * 添加/修改 收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findReceiverAddress",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findReceiverAddress(HttpServletRequest request, HttpServletResponse response){
		logger.info("/app/addressmanager/findReceiverAddress----begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd = this.getPageData();
		
		String receiverId = pd.getString("receiverId");
		String token=pd.getString("token");    				//用户标识
		if(null==token || "".equals(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		if(null==receiverId || "".equals(receiverId)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("");
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(null == customer){
			logger.error("用户信息不能为空");
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		pd.put("customerId",customer.getCustomer_id());
		
		ReceiverAddressVo receiverAddressVo = null;
		try {
			receiverAddressVo = receiverAddressManager.selectReceiverAddressByOption(pd);
			
		} catch (Exception e) {
			logger.error("/app/addressmanager/findReceiverAddress----出现异常");
			
			return CallBackConstant.FAILED.callback("查询收货地址异常");
		}
		
		
		logger.info("/app/addressmanager/findReceiverAddress----end");
		
		if(null!=receiverAddressVo){
			map.put("receiverAddressVo", receiverAddressVo);
			map.put("receiverId", receiverId);
			map.put("addressCode",receiverAddressVo.getProvinceId()+","+receiverAddressVo.getCityId()+","+receiverAddressVo.getAreaId());
			return CallBackConstant.SUCCESS.callback(map);
		
		}else{
			map.put("msg", "查询收货地址为空");
			return CallBackConstant.FAILED.callback(map);
		}
		
	}
	
	
	@RequestMapping(value = "/addAdress",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addAdress(HttpServletRequest request, HttpServletResponse response){
		logger.info("/app/addressmanager/addAdress----begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		PageData pd = this.getPageData();
		int isDefault = Integer.parseInt( pd.getString("isDefault"));
		String code = pd.getString("code");//地区code
		String area = pd.getString("area");
		String address = pd.getString("addressDetail");
		String receiverName = pd.getString("receiveName");
		String receiveMobile = pd.getString("receiveMobile");
		String receiverId = pd.getString("receiverId");
		String token=pd.getString("token");    				//用户标识
		
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(area)  || StringUtil.isEmpty(address) ||
		   StringUtil.isEmpty(receiverName) || StringUtil.isEmpty(receiveMobile) )
		{
			map.put("msg","参数不能为空");
			return CallBackConstant.PARAMETER_ERROR.callback(map);
		}
		
		if(StringUtil.isEmpty(token)){
			logger.error("token不能为空");
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(null==customer){
			logger.error("用户信息不能为空");
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		try {
			area=URLDecoder.decode(area,"UTF-8");
			address=URLDecoder.decode(address,"UTF-8");
			receiverName=URLDecoder.decode(receiverName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.info("/app/addressmanager/addAdress----解码失败 ");
			e1.printStackTrace();
		}
		
		String detail = "";
		
		ReceiverAddressVo receiverAddressVo = new ReceiverAddressVo();
		receiverAddressVo.setCustomerId(customer.getCustomer_id());
		receiverAddressVo.setIsDefault(Byte.parseByte(pd.getString("isDefault")));
		if(StringUtils.isNotBlank(code)){
			receiverAddressVo.setProvinceId(Integer.parseInt(code.split(",")[0]));
			receiverAddressVo.setCityId(Integer.parseInt(code.split(",")[1]));
			receiverAddressVo.setAreaId(Integer.parseInt(code.split(",")[2]));
		}
		if(StringUtils.isNotBlank(area)){
			detail = area+";"+address;
		}
		receiverAddressVo.setReceiverName(receiverName);
		receiverAddressVo.setMobile(receiveMobile);
		receiverAddressVo.setAddress(detail);
		int count =0;
		
		try {
			if(receiverId.equals("0")){
				count= receiverAddressManager.insertReceiverAddress(receiverAddressVo);
				if(isDefault==1){
					receiverAddressManager.editReceiverAddress(receiverAddressVo);
				}
			}else{
				receiverAddressVo.setReceiverId(Integer.parseInt(receiverId));
				count= receiverAddressManager.updateReceiverAddress(receiverAddressVo);
				if(count>0 && isDefault==1){
					int iret =receiverAddressManager.editReceiverAddress(receiverAddressVo);
				}
				
			}
			/*if(receiverId.equals("0")){
				count= receiverAddressManager.insertReceiverAddress(receiverAddressVo);
			}else{
				receiverAddressVo.setReceiverId(Integer.parseInt(receiverId));
				count= receiverAddressManager.updateReceiverAddress(receiverAddressVo);
			}*/
			
		} catch (NumberFormatException e) {
			logger.error("/app/addressmanager/addAdress----出现异常 ");
			
			map.put("msg", "出现异常");
			return CallBackConstant.FAILED.callback(map);
		}
		
		
		logger.info("/app/addressmanager/addAdress----end");
		if(count>0){
			map.put("msg", "操作成功");
			return CallBackConstant.SUCCESS.callback(map);
		}else{
			map.put("msg", "操作失败");
			return CallBackConstant.FAILED.callback(map);
		}
		
	}
	
	
	/**
	 * 删除收货地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteReceiverAddress",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteReceiverAddress(HttpServletRequest request, HttpServletResponse response){
		logger.info("/app/addressmanager/deleteReceiverAddress   begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		 
		PageData pd=this.getPageData();
		String receiverId=pd.getString("receiverId");
		String token=pd.getString("token");    				//用户标识
		
		if(null==receiverId || "".equals(receiverId)){
			map.put("msg","receiverId不能为空");
			return CallBackConstant.PARAMETER_ERROR.callback(map);
		}
		
		if(null==token || "".equals(token)){
			map.put("msg","token不能为空");
			return CallBackConstant.TOKEN_ERROR.callback(map);
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(null==customer){
			map.put("msg","用户信息不能为空");
			return CallBackConstant.LOGIN_TIME_OUT.callback(map);
		}
		
		pd.put("customerId",customer.getCustomer_id().toString());
		int result=0;
		try {
			result = receiverAddressManager.deleteReceiverAddressById(pd);
		} catch (Exception e) {
			logger.error("/app/addressmanager/deleteReceiverAddress   出现异常");
			
			map.put("msg","删除收货地址异常");
			return CallBackConstant.FAILED.callback(map);
			
		}
		
		logger.info("/app/addressmanager/deleteReceiverAddress   end");
		
		if(result>0){
			map.put("msg","删除收货地址成功");
			return CallBackConstant.SUCCESS.callback(map);
		}else{
			map.put("msg","删除收货地址失败");
			return CallBackConstant.FAILED.callback(map);
		}
	}
	
	
	/**
	 * 根据客户ID查询所有的收货地址（下单时使用）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toChooseAddress")
	@ResponseBody
	public Map<String,Object> toChooseAddress(HttpServletRequest request) {
		logger.info("/app/addressmanager/toChooseAddress/begin");
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		PageData pd =this.getPageData();
		
		String returnType=pd.getString("returnType");
		String receiverId=pd.getString("receiverId");
		
		String token=pd.getString("token");  							//用户标识
		if (StringUtil.isEmpty(token)) {
			map.put("msg", "token不能为空");
			return CallBackConstant.TOKEN_ERROR.callback(map);
		}
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		if (null==customerVo) {
			map.put("msg", "用户信息为空");
			return CallBackConstant.LOGIN_TIME_OUT.callback(map);
		}
		
		List<ReceiverAddressVo> listReceiverAddress=null;
		
		logger.info("/app/addressmanager/toChooseAddress/end");
		
		try {
			pd.put("customerId", String.valueOf(customerVo.getCustomer_id()));
			listReceiverAddress = receiverAddressManager.queryList(pd);
			
			
			if(null!=listReceiverAddress && listReceiverAddress.size()>0){
				map.put("returnType", returnType);              //根据商品多少来判定是单个购买还是从购物车结算 
				map.put("receiverId",receiverId);              //已选择的收货地址ID
				map.put("listReceiverAddress", listReceiverAddress);
				
				return CallBackConstant.SUCCESS.callback(map);
			}
			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("/app/addressmanager/toChooseAddress/end --error");
			map.put("msg","查询用户收获地址异常");
			return CallBackConstant.FAILED.callback(map);
		}
	}
	
	
}
