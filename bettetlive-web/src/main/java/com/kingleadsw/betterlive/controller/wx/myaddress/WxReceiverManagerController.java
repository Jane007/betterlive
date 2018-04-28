package com.kingleadsw.betterlive.controller.wx.myaddress;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ReceiverAddressManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ReceiverAddressVo;


/**
 * 微信端 收货地址管理
 * 2017-03-13 by chen
 *
 */
@Controller
@RequestMapping(value = "/weixin/addressmanager")
public class WxReceiverManagerController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(WxReceiverManagerController.class);

	@Autowired
	private ReceiverAddressManager receiverAddressManager;
	
	
	/**
	 * 根据客户ID查询所有的收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toReceiverAddress")
	public ModelAndView toReceiverAddress(HttpServletRequest request) {
		String msg = "toReceiverAddress";
		logger.info("/weixin/addressmanager/"+msg+" begin");
		
		ModelAndView modelAndView=new ModelAndView("weixin/receiveraddress/wx_list_receiver");

		CustomerVo customerVo=Constants.getCustomer(request);     
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		PageData pd = new PageData();
		pd.put("customerId", String.valueOf(customerVo.getCustomer_id()));
		List<ReceiverAddressVo> listReceiverAddress=receiverAddressManager.queryList(pd);
		
		modelAndView.addObject("listReceiverAddress", listReceiverAddress);
		
		logger.info("/weixin/addressmanager/"+msg+" end");
		return modelAndView;
	}
	
	
	/**
	 * 根据客户ID查询所有的收货地址（下单时使用）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toChooseAddress")
	public ModelAndView toChooseAddress(HttpServletRequest request) {
		logger.info("/weixin/addressmanager/toChooseAddress/begin");
		
		ModelAndView modelAndView=new ModelAndView("weixin/receiveraddress/wx_choose_orderreceiver");

		CustomerVo customerVo=Constants.getCustomer(request);     
		if(customerVo == null || customerVo.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		PageData pd = new PageData();
		pd.put("customerId", String.valueOf(customerVo.getCustomer_id()));
		List<ReceiverAddressVo> listReceiverAddress=receiverAddressManager.queryList(pd);
		
		
		PageData pds =this.getPageData();
		String returnType=pds.getString("returnType");
		String receiverId=pds.getString("receiverId");
		
		modelAndView.addObject("returnType", returnType);              //根据商品多少来判定是单个购买还是从购物车结算 
		modelAndView.addObject("receiverId",receiverId);              //已选择的收货地址ID
		
		modelAndView.addObject("listReceiverAddress", listReceiverAddress);
		
		logger.info("/weixin/addressmanager/toChooseAddress/end");
		return modelAndView;
	}
	
	
	/**
	 * 添加收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toAddReceiverAddress")
	private ModelAndView toAddReceiverAddress(HttpServletRequest request, HttpServletResponse response,Model model){
		CustomerVo customerVo=Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		PageData pd = this.getPageData();
		String receiverId = pd.getString("receiverId");
		ReceiverAddressVo receiverAddressVo = new ReceiverAddressVo();
		receiverAddressVo.setReceiverId(Integer.parseInt(receiverId));
		if(StringUtils.isNotBlank(receiverId)){
			if(!receiverId.equals("0")){
				receiverAddressVo = receiverAddressManager.selectReceiverAddressByOption(pd);
				model.addAttribute("addressCode",receiverAddressVo.getProvinceId()+","+receiverAddressVo.getCityId()+","+receiverAddressVo.getAreaId());
			}
		}
		ModelAndView mv=new ModelAndView("weixin/receiveraddress/wx_add_receiver");
		model.addAttribute("receiverAddressVo",receiverAddressVo);
		return mv;
	}
	
	
	
	/**
	 * 编辑收货地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toAddReceiverAddressFromOrder")
	private ModelAndView toAddReceiverAddressFromOrder(HttpServletRequest request, HttpServletResponse response,Model model){
		CustomerVo customerVo=Constants.getCustomer(request);
		if(customerVo == null || customerVo.getCustomer_id() == null){
			 return new ModelAndView("redirect:/weixin/tologin");
		}
		
		PageData pd = this.getPageData();
		String receiverId = pd.getString("receiverId");
		ReceiverAddressVo receiverAddressVo = new ReceiverAddressVo();
		receiverAddressVo.setReceiverId(Integer.parseInt(receiverId));
		if(StringUtils.isNotBlank(receiverId)){
			if(!receiverId.equals("0")){
				receiverAddressVo = receiverAddressManager.selectReceiverAddressByOption(pd);
				model.addAttribute("addressCode",receiverAddressVo.getProvinceId()+","+receiverAddressVo.getCityId()+","+receiverAddressVo.getAreaId());
			}
		}
		ModelAndView mv=new ModelAndView("weixin/receiveraddress/wx_add_receiver");
		
		String returnType=pd.getString("returnType");
		
		model.addAttribute("returnType", returnType);              //根据商品多少来判定是单个购买还是从购物车结算 
		model.addAttribute("receiverAddressVo",receiverAddressVo);
		return mv;
	}
	
	/**
	 * 下单时，选择收货地址  --> 添加收货地址
	 * 先根据 存储的 key 获取选择的商品具体信息。。。
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toAddReceiverFromOrder")
	private ModelAndView toAddReceiverFromOrder(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/receiveraddress/wx_add_receiver");
		PageData pd = this.getPageData();
		String returnType = pd.getString("returnType");
		String receiverId=pd.getString("receiverId");
		
		mv.addObject("returnType",returnType);                           //返回页面来源
		mv.addObject("receiverId",receiverId);
		return mv;
	}
	
	
	@RequestMapping(value = "/doAddAdress")
	@ResponseBody
	public void doAddAdress(HttpServletRequest request, HttpServletResponse response){
		logger.info("/weixin/addressmanager/doAddAdress----begin");
		
		JSONObject json=new JSONObject(); 
		PageData pd = this.getPageData();
		String areaCode = pd.getString("areaCode");//地区code
		String area = pd.getString("area");
		String address = pd.getString("addressDetail");
		String receiverName = pd.getString("receiveName");
		String receiveMobile = pd.getString("receiveMobile");
		String receiverId = pd.getString("receiverId");
		int isDefault = Integer.parseInt( pd.getString("isDefault"));
		String detail = "";
		CustomerVo customer = Constants.getCustomer(request);
		ReceiverAddressVo receiverAddressVo = new ReceiverAddressVo();
		receiverAddressVo.setCustomerId(customer.getCustomer_id());
		if (StringUtils.isNotEmpty(pd.getString("isDefault"))) {
			receiverAddressVo.setIsDefault(Byte.parseByte(pd.getString("isDefault")));
		} else {
			receiverAddressVo.setIsDefault(Byte.parseByte("0"));
		}
		if(StringUtils.isNotBlank(areaCode)){
			receiverAddressVo.setProvinceId(Integer.parseInt(areaCode.split(",")[0]));
			receiverAddressVo.setCityId(Integer.parseInt(areaCode.split(",")[1]));
			receiverAddressVo.setAreaId(Integer.parseInt(areaCode.split(",")[2]));
		}
		if(StringUtils.isNotBlank(area)){
			detail = area+";"+address;
		}
		receiverAddressVo.setReceiverName(receiverName);
		receiverAddressVo.setMobile(receiveMobile);
		receiverAddressVo.setAddress(detail);
		int count =0;
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
			
		if(count>0){
			json.put("msg", "success");
		}else{
			json.put("msg", "faild");
		}
		logger.info("/weixin/addressmanager/doAddAdress----end");
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 删除收货地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteReceiverAddress",method={RequestMethod.POST},produces="text/html;charset=utf-8")
	@ResponseBody
	public String deleteReceiverAddress(HttpServletRequest request, HttpServletResponse response){
		String msg = "deleteReceiverAddress";
		logger.info("/weixin/receiveraddress/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		String receiverId=pd.getString("receiverId");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer==null || customer.getCustomer_id() == null){
			json.put("result", "failure");
			json.put("msg", "请先登录");
			return json.toString();
		}
		
		if(StringUtil.isEmpty(receiverId)){
			json.put("result", "failure");
			json.put("msg", "请先选择要删除的记录");
			return json.toString();
		}
		
		pd.put("customerId",String.valueOf(customer.getCustomer_id()));
		
		int result=receiverAddressManager.deleteReceiverAddressById(pd);
		
		if(result>0){
			json.put("result", "succ");
			json.put("msg", "删除成功");
		}else{
			json.put("result", "fail");
			json.put("msg", "删除失败");
		}
			
		logger.info("/weixin/receiveraddress/"+msg+" end");
		return json.toString();
	}
	
	
	/**
	 * 设为默认地址
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatedeDaultReceive",method={RequestMethod.POST},produces="text/html;charset=utf-8")
	@ResponseBody
	public String updatedeDaultReceive(HttpServletRequest request){
		logger.info("/weixin/receiveraddress/updatedeDaultReceive， begin");
		
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String receiverId = pd.getString("receiverId");
		
		CustomerVo customer = Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			json.put("result", "failure");
			json.put("msg", "请先登录");
			return json.toString();
		}
		
		if(StringUtil.isEmpty(receiverId)){
			json.put("result", "failure");
			json.put("msg", "请先选择要修改的记录");
			return json.toString();
		}
		pd.put("receriverId", receiverId);
		ReceiverAddressVo address = receiverAddressManager.queryOne(pd);
		if(address.getIsDefault()!=1){
			pd.put("customerId", customer.getCustomer_id());
			int result = receiverAddressManager.updateReceiverToDefault(pd);
			
			if(result > 1){
				json.put("result", "succ");
				json.put("msg", "修改成功");
			}else{
				json.put("result", "fail");
				json.put("msg", "修改失败");
			}
		}else{
			address.setIsDefault(Byte.parseByte("0"));
			int result = receiverAddressManager.updateReceiverAddress(address);
			if(result == 1){
				json.put("result", "succ");
				json.put("msg", "修改成功");
			}else{
				json.put("result", "fail");
				json.put("msg", "修改失败");
			}
		}
		logger.info("/weixin/receiveraddress/updatedeDaultReceive， end");
		return json.toString();
	}
	
}
