package com.kingleadsw.betterlive.controller.web.coupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CouponManagerManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.CouponManagerVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
@Controller
@RequestMapping(value = "/admin/coupon")
public class CouponController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(CouponController.class);
	
	@Autowired
	private CouponManagerManager couponManagerManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private MessageManager messageManager;	
	
	/**
	 * 跳转优惠券管理页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toCouponManager")
	public ModelAndView toCoupon(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/coupon/list_coupon_manager");
		return modelAndView;
	}
	
	
	/**
	 * 查询优惠券管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/queryCouponManagerAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryCouponManagerAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryCouponManagerByPages";
		logger.info("/admin/coupon/"+msg+" begin");
		
		PageData pd = this.getPageData();
		List<CouponManagerVo> list=null; 
	    		
		try {
			list=couponManagerManager.findCouponMangerListPage(pd);
		} catch (Exception e) {
			logger.error("查询优惠券管理出现异常.... ", e);
			
		}
	    
	    if(null==list || list.isEmpty()){
	    	list=new ArrayList<CouponManagerVo>();
	    }
		
		this.outEasyUIDataToJson(pd, list, response);
        
		logger.info("--->结束调用/admin/coupon/"+msg);
		
	} 
	
	
	/**
	 * 跳转用户优惠券管理页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toUserCoupon")
	public ModelAndView toUserCoupon(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/coupon/list_user_couponinfo");
		return modelAndView;
	}
	
	
	/**
	 * 分页查询优惠券管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/queryUserCouponAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryUserCouponAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryUserCouponAllJson";
		logger.info("/admin/coupon/"+msg+" begin");
		
		PageData pd = this.getPageData();
		List<CouponInfoVo> list=null; 
	    		
		try {
			list=couponInfoManager.findUserCouponListPage(pd);
		} catch (Exception e) {
			logger.error("查询优惠券管理出现异常.... ", e);
		}
	    
	    if(null==list || list.isEmpty()){
	    	list=new ArrayList<CouponInfoVo>();
	    }
		
		this.outEasyUIDataToJson(pd, list, response);
        
		logger.info("--->结束调用/admin/coupon/"+msg);
		
	} 
	
	/**
	 * 查询优惠券类型为补偿券 的优惠券
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/queryCompensateCouponAllJson")
	@ResponseBody
	public Map<String,Object> queryCompensateCouponAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryCompensateCouponAllJson";
		logger.info("/admin/coupon/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		
		PageData pd = new PageData();
		pd.put("couponType","2");
		
		List<CouponManagerVo> list=null; 
	    		
		try {
			list=couponManagerManager.findListCouponManager(pd);
			if(!list.isEmpty() && list.size()>0){
				json.put("result","succ");
				json.put("list",list);
			}else{
				json.put("result","fail");
				json.put("msg","没有查询到类型为补偿券的优惠券");
			}
		} catch (Exception e) {
			logger.error("查询优惠券管理出现异常.... ",e);
			
			json.put("result","exec");
			json.put("msg","查询出现异常");
		}
	    
	    logger.info("--->结束调用/admin/coupon/"+msg);
		
	    return CallBackConstant.SUCCESS.callback(json);
        
	} 
	
	
	/**
	 * 后台管理员增加用户优惠券
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/addUserCouponInfo",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void addUserCouponInfo(HttpServletRequest request,HttpServletResponse response){
		String msg = "addUserCouponInfo";
		logger.info("/admin/coupon/"+msg+" begin");
		
        JSONObject json=new JSONObject();
        CouponInfoVo couponInfoVo=null;
        CustomerVo customerVo=null;
        		
        AdminVo admin=Constants.getAdmin(request);
        PageData pd=this.getPageData();
        
        String toObject=pd.getString("toObject"); 
        String mobile=pd.getString("mobile");
        String cmId=pd.getString("cmId");
        
        if(null==toObject || "".equals(toObject)){
        	json.put("result", "failure");
			json.put("msg", "请选择发放对象");
			outJson(json.toString(), response);
			return;
        }else{
        	if("2".equals(toObject)){
        		if(null==mobile || "".equals(mobile)){
        			json.put("result", "failure");
        			json.put("msg", "请输入用户手机号码");
        			outJson(json.toString(), response);
        			return;
        		}else{
        			customerVo=customerManager.findCustomer(pd);
        			if(null ==customerVo){
        				json.put("result", "failure");
            			json.put("msg", "用户不存在");
            			outJson(json.toString(), response);
            			return;
        			}
        		}
        	}
        }
        
        if(null ==cmId || "".equals(cmId)){
        	json.put("result", "failure");
			json.put("msg", "请选择优惠券");
			outJson(json.toString(), response);
			return;
        }
        
        
        //根据ID查询优惠券信息
		CouponManagerVo couponManager=null;
		int result=0; 
		try {
			couponManager = couponManagerManager.findCouponManager(pd);
			
			if(null==couponManager){
				json.put("result", "failure");
				json.put("msg", "优惠券不存在");
				outJson(json.toString(), response);
				return;
			}
			
			String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
			
			
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
			
			String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
			
			String title = "恭喜您获得了一张优惠券";
			if(couponManager.getCoupon_type().intValue() == 3){
				title = "恭喜您获得了一个红包";
			}
			if("1".equals(toObject)){
				
				//List<CustomerVo> listcustomerVo=customerManager.findListCustomer(pd);
				
				List<CustomerVo> listcustomerVo=customerManager.findListCustomerByObject(pd);
				for (int i = 0; i < listcustomerVo.size(); i++) {
					couponInfoVo=new CouponInfoVo();
					
					couponInfoVo.setMobile(listcustomerVo.get(i).getMobile());
					couponInfoVo.setCustomer_id(listcustomerVo.get(i).getCustomer_id());
					
					BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
					BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
					
					couponInfoVo.setCm_id(couponManager.getCm_id());
					couponInfoVo.setCoupon_money(couponMoney.intValue());
					couponInfoVo.setStarttime(currentDate);
					couponInfoVo.setEndtime(endDate);
					couponInfoVo.setStart_money(useminMoney.intValue());
					couponInfoVo.setCoupon_from(2);
					couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
					couponInfoVo.setFrom_user_id(admin.getStaffId());
					
					result = couponInfoManager.insertUserCoupon(couponInfoVo);
					//发系统消息给用户
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(2);
					msgVo.setSubMsgType(1);
					msgVo.setMsgTitle(title);
					String couponContent = "";
					if(StringUtil.isNotNull(couponManager.getCoupon_content())){
						couponContent = couponManager.getCoupon_content();
					}
					msgVo.setMsgDetail(couponContent);
					msgVo.setIsRead(0);
					msgVo.setCustomerId(listcustomerVo.get(i).getCustomer_id());
					msgVo.setObjId(couponInfoVo.getCoupon_id());
					messageManager.insert(msgVo);
				}				
			}else{
				couponInfoVo=new CouponInfoVo();
				
				
				BigDecimal couponMoney=new BigDecimal(couponManager.getCoupon_money());
				BigDecimal useminMoney=new BigDecimal(couponManager.getUsemin_money());
				
				couponInfoVo.setMobile(customerVo.getMobile());
				couponInfoVo.setCustomer_id(customerVo.getCustomer_id());
				
				couponInfoVo.setCm_id(couponManager.getCm_id());
				couponInfoVo.setCoupon_money(couponMoney.intValue());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(useminMoney.intValue());
				couponInfoVo.setCoupon_from(1);
				couponInfoVo.setStatus(0);//0：未使用，1：已使用，2：已过期',
				couponInfoVo.setFrom_user_id(admin.getStaffId());
				
				result = couponInfoManager.insertUserCoupon(couponInfoVo);
				//发系统消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(2);
				msgVo.setSubMsgType(1);
				msgVo.setMsgTitle(title);
				String couponContent = "";
				if(StringUtil.isNotNull(couponManager.getCoupon_content())){
					couponContent = couponManager.getCoupon_content();
				}
				msgVo.setMsgDetail(couponContent);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(customerVo.getCustomer_id());
				msgVo.setObjId(couponInfoVo.getCoupon_id());
				messageManager.insert(msgVo);		
			}
			if(result>0){
				json.put("result", "succ");
				json.put("msg", "添加成功");
			}else{
				json.put("result", "fail");
				json.put("msg", "添加失败");
			}
			
		} catch (Exception e) {
			logger.error("系统后台管理人员发放用户优惠券失败...");
			
			json.put("result", "exec");
			json.put("msg", "添加异常");
		}
        
		outJson(json.toString(), response);
		logger.info("--->结束调用/admin/coupon/"+msg);
		
	}
	
	/**
	 * 根据id查询优惠券管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/findCouponManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void findCouponManager(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "findCouponManager";
		logger.info("/admin/coupon/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		String cmId=pd.getString("cmId");
		
		if(StringUtil.isNull(cmId)){
			json.put("result", "failure");
			json.put("msg", "请选择要修改的记录");
			outJson(json.toString(), response);
			return ;
		}
		
		CouponManagerVo couponManagerVo=null;
		try {
			couponManagerVo=couponManagerManager.findCouponManager(pd);
			if(null !=couponManagerVo ){
				json.put("result", "succ");
			}else{
				json.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询优惠券管理详细 异常  方法： /admin/coupon/"+msg, e);
			json.put("result", "exec");
			outJson(json.toString(), response);
		}
		
		json.put("couponManagerInfo", couponManagerVo);
		
		
		outJson(json.toString(), response);
		
		logger.info("--->结束调用/admin/coupon/"+msg);
	}
	
	
	/**
	 * 增加优惠券管理
	 * @param httpRequest
	 * @return
	 * 
	 * 新增时先查询优惠券管理是否存在相同的名称    是:新增失败   否： 开始新增
	 * 
	 * 分享券 只能添加一种, 所以: 需要判断是否存在优惠券名称 或者 分享券
	 * 补偿券可以有多种 
	 */
	@RequestMapping(value="/addCouponManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void addCouponManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "addCouponManager";
		logger.info("/admin/coupon/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
	    
	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
		String couponName=pd.getString("couponName").replaceAll(" ","");     //优惠券名称
		String endDate=pd.getString("endDate");           //优惠券有效期
		String couponMoney=pd.getString("couponMoney");   //优惠券金额
		String useMinMoney=pd.getString("useMinMoney");   //使用门槛金额（大于多少金额才可以使用）
		String couponType=pd.getString("couponType");     //优惠券类型
		String couponContent=pd.getString("coupon_content");	//描述
		String homeFlag=pd.getString("home_flag");	//是否推荐到首页 0 否   1是
		
	    int result=0;
		try {
			PageData pageData=new PageData();
			if("1".equals(couponType)){  //分享券
				pageData.put("couponTypeOrName",couponName);
			} else if ("3".equals(couponType)) {  //新手券
				pageData.put("couponType", 3);
			} else{
				pageData.put("couponName",couponName);
			}
			
			
			CouponManagerVo couponManager=couponManagerManager.findCouponManager(pageData);
			if(null == couponManager){
				Integer couponTypes=Integer.parseInt(couponType);
				
				couponManager = new CouponManagerVo();
				couponManager.setCoupon_name(couponName);       
				couponManager.setUsemax_date(endDate);
				couponManager.setCoupon_money(couponMoney);
				couponManager.setUsemin_money(useMinMoney);
				couponManager.setCreate_by(String.valueOf(admin.getStaffId()));
				couponManager.setCoupon_type(couponTypes);
				couponManager.setGet_source(couponTypes);     
				couponManager.setCoupon_content(couponContent);
				couponManager.setHome_flag(Integer.parseInt(homeFlag));
				
				result = couponManagerManager.insertCouponManager(couponManager);
				if(result>0){
					json.put("result", "succ");
					json.put("msg", "新增成功");
				}else{
					json.put("result", "fail");
					json.put("msg", "新增失败");
				}
			}else{
				json.put("result", "failure");
				json.put("msg", "已存在相同类型或名称的优惠券");
			}
			
		} catch (Exception e) {
			logger.error("新增优惠券管理异常    方法:  /admin/coupon/"+msg, e);
			
			json.put("result", "exec");
	    	json.put("msg", "出现异常");
		}
	   
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/coupon/"+msg);
	} 
	
	
	
	/**
	 * 更新优惠券管理
	 * @param httpRequest
	 * @return
	 * 
	 * 更新时先查询优惠券管理是否存在    是:开始更新   否： 跟新失败
	 * 
	 * 注意： 需要根据 名称与ID ,如果存在 就判断 是不是同一个  是 ：开始更新  否：更新失败 
	 */
	@RequestMapping(value="/editCouponManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void editCouponManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "addCouponManager";
		logger.info("/admin/coupon/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
	    
	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
	    String cmId=pd.getString("cmId");     			  //优惠券ID
	    String couponName=pd.getString("couponName").replaceAll(" ","");     //优惠券名称
		String couponType=pd.getString("couponType");     //优惠券类型
		
	    int result=0;
	    boolean bool=true;
		try {
			if(null==cmId|| "".equals(cmId)){
				throw new Exception();
			}
			
			PageData pageData=new PageData();
			pageData.put("cmId",cmId);
			CouponManagerVo couponManager=couponManagerManager.findCouponManager(pageData);
			if(null !=couponManager){
				
				PageData pagedata=new PageData();
				if("1".equals(couponType)){
					pagedata.put("couponTypeOrName",couponName);
				}else{
					pagedata.put("couponName",couponName);
				}
				List<CouponManagerVo>  listCouponManager=couponManagerManager.findListCouponManager(pagedata);
				
				if(null != listCouponManager && !listCouponManager.isEmpty() && listCouponManager.size()>0){
					for (int i = 0; i <listCouponManager.size(); i++) {
						if(listCouponManager.get(i).getCm_id() !=couponManager.getCm_id()){
							bool=false;
							
							json.put("result", "failure");
							json.put("msg", "已存在相同类型或名称的优惠券");
							
							break;
						}
					}
					
				}
				
				if(bool){
					pd.put("create_by", String.valueOf(admin.getStaffId()));
					result = couponManagerManager.updateCouponManagerByCmId(pd);
					if(result>0){
						json.put("result", "succ");
						json.put("msg", "更新成功");
					}else{
						json.put("result", "fail");
						json.put("msg", "更新失败");
					}
				}	
			}else{
				json.put("result", "failure");
				json.put("msg", "没有查询到优惠券");
			}
			  
		} catch (Exception e) {
			logger.error("更新优惠券管理异常    方法:  /admin/coupon/"+msg, e);
			json.put("result", "exec");
	    	json.put("msg", "出现异常");
		}
	   
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/coupon/"+msg);
	} 
}
