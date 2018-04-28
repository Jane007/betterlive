package com.kingleadsw.betterlive.controller.app.singlecoupon;

import java.text.SimpleDateFormat;
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

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SingleCouponManager;
import com.kingleadsw.betterlive.biz.SingleCouponSpecManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SingleCouponSpecVo;
import com.kingleadsw.betterlive.vo.SingleCouponVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

@Controller
@RequestMapping(value = "/app/singlecoupon")
public class AppSingleCouponController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(AppSingleCouponController.class);
	
	@Autowired
	private SingleCouponManager singleCouponManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private SingleCouponSpecManager singleCouponSpecManager;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 获取红包信息
	 */                     
	@RequestMapping(value="/queryCouponInfo")
	@ResponseBody
	public Map<String,Object> queryCouponInfo(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			int hasFlag = 0;
		
			if (StringUtil.isNull(pd.getString("couponId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("单品红包ID为空");
			}
			
			if (StringUtil.isNull(pd.getString("specId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品规格ID为空");
			}
			
			pd.put("specStatus", 1);
			ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pd);
			if(psvo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("商品已下架");
			}
			
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(pd.getString("couponId")));
			if(singleCouponVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("红包不存在");
			}
			
			int hasMobile = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer != null && StringUtil.isNotNull(customer.getMobile())){
					hasMobile = 1;
					pd.put("mobile", customer.getMobile());
					pd.put("customerId", customer.getCustomer_id());
					UserSingleCouponVo uscVo = userSingleCouponManager.queryOne(pd);
					if(uscVo != null){//每个人只可以领取一个单品红包 //是否已領取
						hasFlag = 1;
					}
				}
				
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = sdf.parse(singleCouponVo.getEndTime());
			singleCouponVo.setEndTime(sdf.format(dt));
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("hasFlag", hasFlag);
			result.put("hasMobile", hasMobile);
			result.put("productId", psvo.getProduct_id());
			result.put("productName", psvo.getProduct_name());
			result.put("singleCouponVo", singleCouponVo);
			return CallBackConstant.SUCCESS.callback(result);
		} catch (Exception e) {
			logger.error("/app/singlecoupon/queryCouponInfo  ---error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	
	/**
	 * 获取红包信息
	 */                     
	@RequestMapping(value="/receiveCouponInfo")
	@ResponseBody
	public Map<String,Object> receiveCouponInfo(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			
			if (StringUtil.isNull(pd.getString("couponId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("单品红包ID为空");
			}
			
			if (StringUtil.isNull(pd.getString("specId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品规格ID为空");
			}
			
			String token = pd.getString("token");
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			if(StringUtil.isNull(customer.getMobile())){
				return CallBackConstant.FAILED.callbackError("还没有绑定手机号");
			}
			
			pd.put("specStatus", 1);
			ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(pd);
			if(psvo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("商品已下架");
			}
			
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(pd.getString("couponId")));
			if(singleCouponVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("红包不存在");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			pd.put("phoneNum", customer.getMobile());
			pd.put("customerId", customer.getCustomer_id());
			map = userSingleCouponManager.insertSingleCoupon(pd);
			if(map.get("result") != null && map.get("result").toString().equals("fail")){
				return CallBackConstant.FAILED.callbackError(map.get("msg").toString());
			}
			return CallBackConstant.SUCCESS.callBackByMsg("领取成功");
		} catch (Exception e) {
			logger.error("/app/singlecoupon/receiveCouponInfo  ---error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	
	/**
	 * 发放红包到账户上面
	 */                     
	@RequestMapping(value="/receiveCouponInfoNew")
	@ResponseBody
	public Map<String,Object> receiveCouponInfoNew(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			
			if (StringUtil.isNull(pd.getString("couponId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("单品红包ID为空");
			}
			
			if (StringUtil.isNull(pd.getString("productId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品规格ID为空");
			}
			
			String token = pd.getString("token");
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			if(StringUtil.isNull(customer.getMobile())){
				return CallBackConstant.FAILED.callbackError("还没有绑定手机号");
			}
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(pd.getString("couponId")));
			if(singleCouponVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("红包不存在");
			}
			
			List<SingleCouponSpecVo> listSpec = singleCouponVo.getListSpec();
			
			if(listSpec == null  ){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("产品规格红包不存在");
			}
			
			pd.put("specStatus", 1);
			Map<String, Object> map = new HashMap<String, Object>();
			pd.put("phoneNum", customer.getMobile());
			pd.put("customerId", customer.getCustomer_id());
			int count = 0;
			for (SingleCouponSpecVo singleCouponSpecVo : listSpec) {//立即领取一个产品的所有规格红包
				pd.put("specId", singleCouponSpecVo.getSpecId()+"");
				map = userSingleCouponManager.insertSingleCoupon(pd);
				String result = (String)map.get("result");
				if(!result.equals("success")){
					continue;
				}
				count++;
			}
			if(count <= 0){
				return CallBackConstant.FAILED.callbackError("活动已结束");
			}
			return CallBackConstant.SUCCESS.callBackByMsg("领取成功");
		} catch (Exception e) {
			logger.error("/app/singlecoupon/receiveCouponInfo  ---error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 获取红包信息
	 */                     
	@RequestMapping(value="/getClickRedPacket")
	@ResponseBody
	public Map<String,Object> getClickRedPacket(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			int hasFlag = 0;
		
			if (StringUtil.isNull(pd.getString("couponId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("单品红包ID为空");
			}
			
			if (StringUtil.isNull(pd.getString("productId"))) {
				return CallBackConstant.PARAMETER_ERROR.callbackError("产品ID为空");
			}
			
			
			SingleCouponVo singleCouponVo = singleCouponManager.selectByPrimaryKey(Integer.parseInt(pd.getString("couponId")));
			if(singleCouponVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("红包不存在");
			}
			ProductVo prvo = productManager.selectByPrimaryKey(Integer.parseInt(pd.getString("productId")));
			if(prvo==null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("产品不存在");
			}
			
			int hasMobile = 0;
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer != null && StringUtil.isNotNull(customer.getMobile())){
					hasMobile = 1;
					pd.put("mobile", customer.getMobile());
					pd.put("customerId", customer.getCustomer_id());
					List<SingleCouponSpecVo> scspec = singleCouponSpecManager.queryList(pd);
					List<UserSingleCouponVo> uscVoList = userSingleCouponManager.queryList(pd);
					
					if(scspec != null && uscVoList != null && uscVoList.size()==scspec.size()){//每个人只可以领取一个单品红包 //是否已領取
						hasFlag = 1;
					}
					
				}
				
			}
			Map<String, Object> result = new HashMap<String, Object>();
			List<SingleCouponSpecVo> listSpec = singleCouponSpecManager.queryList(pd);
			if(listSpec!=null&&listSpec.size()!=0){
				result.put("specId", listSpec.get(0).getSpecId());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = sdf.parse(singleCouponVo.getEndTime());
			singleCouponVo.setEndTime(sdf.format(dt));
			
			
			result.put("hasFlag", hasFlag);
			result.put("hasMobile", hasMobile);
			result.put("productId", prvo.getProduct_id());
			result.put("productName", prvo.getProduct_name());
			result.put("singleCouponVo", singleCouponVo);
			return CallBackConstant.SUCCESS.callback(result);
		} catch (Exception e) {
			logger.error("/app/singlecoupon/queryCouponInfo  ---error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	
	
	
	/**
	 * 直接领取单品多个规格的红包
	 * @param
	 * @return
	 * @author zhangjing 2017年11月21日 下午6:48:55
	 */
	@RequestMapping("getCouponsByProjectId")
	@ResponseBody
	public Map<String,Object> getCouponsByProjectId(HttpServletRequest request,HttpServletResponse response){
		logger.info("/app/singlecoupon/getCouponsByProjectId--->begin");
		PageData pd =this.getPageData();
		try{
			if(StringUtil.isNotNull(pd.getString("token"))){
				CustomerVo customer = customerManager.queryCustomerByToken(pd.getString("token"));
				if(customer!=null){
					pd.put("customerId", customer.getCustomer_id());
					pd.put("phoneNum", customer.getMobile());
					Map<String, Object> map = userSingleCouponManager.insertSingleCouponByProductId(pd);
					return map;
				}else{
					return CallBackConstant.FAILED.callback("用户为空");
				}
				
			}else{
				return CallBackConstant.TOKEN_ERROR.callback();
			}
		}catch (Exception e) {
			logger.error("/app/singlecoupon/getCouponsByProjectId  ---error", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
