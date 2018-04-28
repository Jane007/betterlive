package com.kingleadsw.betterlive.controller.web.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityManager;
import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.ActivityProductVo;
import com.kingleadsw.betterlive.vo.ActivityVo;

@Controller
@RequestMapping(value = "/admin/activity")
public class ActivityController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityManager activityManager;
	@Autowired
	private ActivityProductManager activityProductManager; 
	
	/**
	 * 跳转活动管理页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toActivityManager")
	public ModelAndView toCoupon(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/activity/list_activity_manager");
		return modelAndView;
	}
	
	
	/**
	 * 分页查询活动管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/queryActivityManagerAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryActivityManagerAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryActivityManagerAllJson";
		logger.info("/admin/activity/"+msg+" begin");
		
		PageData pd = this.getPageData();
	    
		List<ActivityVo> list=null; 
	    		
		try {
			list=activityManager.findActivityListPage(pd);
		} catch (Exception e) {
			logger.error("查询优惠券管理出现异常.... ", e);
		}
	    
	    if(null==list || list.isEmpty()){
	    	list=new ArrayList<ActivityVo>();
	    }
		
		this.outEasyUIDataToJson(pd, list, response);
        
		logger.info("--->结束调用/admin/activity/"+msg);
		
	} 
	
	
	
	
	/**
	 * 根据id查询活动券管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/findActivityManager")
	public void findActivityManager(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "findActivityManager";
		logger.info("/admin/activity/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		String activityId=pd.getString("activityId");
		
		if(StringUtil.isEmpty(activityId)){
			json.put("result", "failure");
			json.put("msg", "请选择要修改的记录");
			outJson(json.toString(), response);
			return ;
		}
		
		ActivityVo activityVo=null;
		try {
			activityVo=activityManager.findActivity(pd);
			if(null !=activityVo ){
				json.put("result", "succ");
				
			}else{
				json.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询优惠券管理详细 异常  方法： /admin/activity/"+msg, e);
			
			json.put("result", "exec");
		}
		
		json.put("activityInfo", activityVo);
		
		
		outJson(json.toString(), response);
		
		logger.info("--->结束调用/admin/activity/"+msg);
	}
	
	
	
	/**
	 * 增加活动管理
	 * @param httpRequest
	 * @return
	 * 
	 * 新增时先查询活动管理是否存在相同的名称    是:新增失败   否： 开始新增
	 */
	@RequestMapping(value="/addActivityManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void addActivityManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "addCouponManager";
		logger.info("/admin/activity/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
	    
//	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
		String theme=pd.getString("theme");          		//活动主题
		String productId=pd.getString("productId");       //活动产品ID
		String starttime=pd.getString("starttime");  	  //开始时间
		String endtime=pd.getString("endtime");   		  //结束时间
		
		
		 //商品规格与价格总数
        String teaTypeLength=pd.getString("teaTypeLength");        
        String teaId=null;
        String teaActivityPrices=null;
        boolean bool=true;
        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
        ActivityProductVo activityProductVo=null;
        for (int i =1; i < Integer.parseInt(teaTypeLength)+1; i++) {
        	teaId=pd.getString("teaId_"+i);
        	teaActivityPrices=pd.getString("teaSizeActivityPrice_"+i);
        	if("".equals(teaId) || null ==teaId ||"".equals(teaActivityPrices) || null ==teaActivityPrices){
        		bool=false;
        		
        		json.put("result", "fail");
				json.put("msg", "规格与优惠价格为空！位置："+i);
        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品活动。  操作状态： 失败！ 原因:  规格与优惠价格出现错误  位置： "+i);
        	}else{
        		activityProductVo=new ActivityProductVo();
        		activityProductVo.setSpec_id(Integer.parseInt(teaId));
        		activityProductVo.setActivity_price(teaActivityPrices);
        		listactivityProductSpec.add(activityProductVo);
        	}
		}
		
        if(bool){
		
		    int result=0;
			try {
				
				PageData pageData=new PageData();
				pageData.put("theme",theme);
				
				ActivityVo activityVo=activityManager.findActivity(pageData);
				if(null ==activityVo){
					ActivityVo activity=new ActivityVo();
					activity.setActivity_theme(theme);             
					activity.setProduct_id(Integer.parseInt(productId));
					activity.setStarttime(starttime);
					activity.setEndtime(endtime);
					
					activityManager.insertActivity(activity);
					
					if(null !=activity.getActivity_id() && activity.getActivity_id()>0){
						for (int i = 0; i <listactivityProductSpec.size(); i++) {
							listactivityProductSpec.get(i).setActivity_id(activity.getActivity_id());
						}
						result=activityProductManager.addBatchActivityProduct(listactivityProductSpec);
						
					}else{
						throw new Exception();
					}
					
					if(result>0){
						json.put("result", "succ");
						json.put("msg", "新增成功");
					}else{
						json.put("result", "fail");
						json.put("msg", "新增失败");
					}
					
				}else{
					json.put("result", "failure");
					json.put("msg", "已存在相同名称的活动");
				}
				
			} catch (Exception e) {
				logger.error("新增活动管理异常    方法:  /admin/activity/"+msg, e);
				
				json.put("result", "exec");
		    	json.put("msg", "出现异常");
			}
        }
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/activity/"+msg);
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
	@RequestMapping(value="/editActivityManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void editActivityManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "editActivityManager";
		logger.info("/admin/activity/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
	    
//	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
	    String activityId=pd.getString("activityId");     //活动ID
		String theme=pd.getString("theme");               //活动主题
		String pId=pd.getString("pId");      			 //活动产品ID
		
		 //商品规格与价格总数
        String teaTypeLength=pd.getString("teaTypeLength");        
        String teaId=null;
        String teaActivityPrices=null;
        boolean bool=true;
        List<ActivityProductVo> listactivityProductSpec=new ArrayList<ActivityProductVo>();
        ActivityProductVo activityProductVo=null;
        for (int i =1; i < Integer.parseInt(teaTypeLength)+1; i++) {
        	teaId=pd.getString("teaId_"+i);
        	teaActivityPrices=pd.getString("teaSizeActivityPrice_"+i);
        	if("".equals(teaId) || null ==teaId ||"".equals(teaActivityPrices) || null ==teaActivityPrices){
        		bool=false;
        		
        		json.put("result", "fail");
				json.put("msg", "规格与优惠价格为空！位置："+i);
        		logger.info("连接地址： /admin/product/addProduct , 操作：增加商品活动。  操作状态： 失败！ 原因:  规格与优惠价格出现错误  位置： "+i);
        	}else{
        		activityProductVo=new ActivityProductVo();
        		activityProductVo.setActivity_id(Integer.parseInt(activityId));
        		activityProductVo.setSpec_id(Integer.parseInt(teaId));
        		activityProductVo.setActivity_price(teaActivityPrices);
        		listactivityProductSpec.add(activityProductVo);
        	}
		}
        
        if(bool){
		    int result=0;
		    boolean bl=true;
			try {
				if(null==activityId|| "".equals(activityId) || null==pId|| "".equals(pId)){
					throw new Exception();
				}
				
				PageData pageData=new PageData();
				pageData.put("activityId",activityId);
				ActivityVo activityVo=activityManager.findActivity(pageData);
				
				if(null !=activityVo){
					
					PageData pagedata=new PageData();
					pagedata.put("theme",theme);
					List<ActivityVo>  listActivity=activityManager.findListActivity(pagedata);
					
					if(!listActivity.isEmpty() && listActivity.size()>0){
						for (int i = 0; i <listActivity.size(); i++) {
							if(listActivity.get(i).getActivity_id() !=activityVo.getActivity_id()){
								bl=false;
								
								json.put("result", "failure");
								json.put("msg", "已存在相同名称的活动");
								
								break;
							}
						}
						
					}
					
					if(bl){
						
						result = activityManager.updateActivityByAid(pd);
						if(result>0){
							json.put("result", "succ");
							json.put("msg", "更新成功");
							
							activityProductManager.deleteActivityProductByAid(activityId);
							activityProductManager.addBatchActivityProduct(listactivityProductSpec);
							
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
				logger.error("更新优惠券管理异常    方法:  /admin/activity/"+msg, e);
				
				json.put("result", "exec");
		    	json.put("msg", "出现异常");
			}
        }
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/activity/"+msg);
	}
}
