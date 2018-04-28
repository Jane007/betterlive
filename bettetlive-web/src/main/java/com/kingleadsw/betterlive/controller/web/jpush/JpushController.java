package com.kingleadsw.betterlive.controller.web.jpush;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.kingleadsw.betterlive.biz.ProductGroupManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.jpush.JpushUtil;
import com.kingleadsw.betterlive.vo.ProductGroupVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

@Controller
@RequestMapping("/admin/jpush")
public class JpushController extends AbstractWebController {
	private Logger logger = Logger.getLogger(JpushController.class);
	
	@Autowired
	private SpecialMananger specialMananger; 
	
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private ProductGroupManager productGroupManager;
	
	/**
	 * 访问推送页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPush")
	public ModelAndView toPush(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView md = new ModelAndView("/admin/jpush/push_data");
		return md;
	}
	
    /**
     * 推送消息
     * @return
     */
    @RequestMapping("/pushData")
    @ResponseBody
    public Map<String,Object> pushData(HttpServletRequest request,HttpServletResponse response){
    	PageData pd = this.getPageData();
    	if(StringUtil.isNull(pd.getString("pushTypeId"))){
    		return CallBackConstant.PARAMETER_ERROR.callbackError("推送标识为空");
    	}
    	if(StringUtil.isNull(pd.getString("pushContent"))){
    		return CallBackConstant.PARAMETER_ERROR.callbackError("推送内容");
    	}
    	
    	final String pushTypeId = pd.getString("pushTypeId");
    	//推送标题 
//    	final String pushTitle = pd.getString("pushTitle");
//    	if(pushTypeId.equals("8") || pushTypeId.equals("9")){
//	    	if(StringUtil.isNull(pushTitle)){
//	    		return CallBackConstant.PARAMETER_ERROR.callbackError("请填写推送标题");
//	    	}
//    	}
    	final String pushContent = pd.getString("pushContent");
    	final String specialId = pd.getString("specialId");
    	final String articleTitle = pd.getString("articleTitle");
    	final String articleId = pd.getString("articleId");
    	final String customerId = pd.getString("customerId");
    	final String productId = pd.getString("productId");
    	final String userGroupId = pd.getString("userGroupId");
    	
		Thread messageThread = new Thread() {
			@Override
			public void run() {
		    	Map<String,String> extra = new LinkedHashMap<String, String>();
		    	extra.put("type", pushTypeId);
		    	if(pushTypeId.equals("0")){	//首页专题Banner
					extra.put("specialId", specialId);
				}else if(pushTypeId.equals("1")){	//动态文章详情
					extra.put("articleTitle", articleTitle);
					extra.put("articleId", articleId);
					extra.put("customerId", customerId);
					extra.put("status", "1");
				}else if(pushTypeId.equals("2")){	//文章详情
					extra.put("articleTitle", articleTitle);
					extra.put("articleId", articleId);
				}else if(pushTypeId.equals("3")){	//视频详情
					extra.put("specialId", specialId);
				}else if(pushTypeId.equals("4")){	//商品详情
					extra.put("productId", productId);
				}else if(pushTypeId.equals("5")){	//团购详情
					extra.put("productId", productId);
					extra.put("specialId", specialId);
					extra.put("activityType", "3");
				}else if(pushTypeId.equals("6")){	//参团详情
					extra.put("productId", productId);
					extra.put("userGroupId", userGroupId);
					extra.put("specialId", specialId);
				}else if(pushTypeId.equals("7")){	//限量抢购详情
					extra.put("productId", productId);
					extra.put("specialId", specialId);
					extra.put("activityType", "2");
				}
//				else if(pushTypeId.equals("8")){	//限量抢购列表
//					
//				}else if(pushTypeId.equals("9")){	//团购列表	
//				}
		    	JpushUtil.push("", "", pushContent, extra);
			}
		};
		messageThread.start();
		
        return CallBackConstant.SUCCESS.callback();
    }
    
    /**
     * 查询需要推送的内容对象
     * @return
     */
    @RequestMapping("/queryPrePushDatas")
    @ResponseBody
    public void queryPrePushDatas(HttpServletRequest request,HttpServletResponse response){
    	PageData pd = this.getPageData();
    	try {
    		int type = pd.getInteger("type");
    		pd.remove("type");
    		if(type == 0){	//专题
    			pd.put("status", 1);
    			pd.put("specialType", 1);
    			pd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
    			pd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
    			List<SpecialVo> specials = specialMananger.querySpecialListPage(pd);
    			if(specials == null){
    				specials = new ArrayList<SpecialVo>();
    			}
				this.outEasyUIDataToJson(pd, specials, response);
    		}else if(type == 1){	//动态文章
    			pd.put("status", 1);
    			pd.put("articleFrom", 1);
    			List<SpecialArticleVo> specialArticles = specialArticleManager.queryCircleArticleListPage(pd);
    			if(specialArticles == null){
    				specialArticles = new ArrayList<SpecialArticleVo>();
    			}
				this.outEasyUIDataToJson(pd, specialArticles, response);
    		}else if(type == 2){	//精选文章
    			pd.put("status", 1);
    			pd.put("articleFrom", 0);
    			List<SpecialArticleVo> specialArticles = specialArticleManager.querySpecialArticleListPage(pd);
    			if(specialArticles == null){
    				specialArticles = new ArrayList<SpecialArticleVo>();
    			}
				this.outEasyUIDataToJson(pd, specialArticles, response);
    		}else if(type == 3){	//视频
    			pd.put("status", 1);
    			pd.put("specialType", 4);
    			List<SpecialVo> specials = specialMananger.querySpecialListPage(pd);
    			if(specials == null){
    				specials = new ArrayList<SpecialVo>();
    			}
				this.outEasyUIDataToJson(pd, specials, response);
    		}else if(type == 4){	//商品
    			pd.put("status", 1);
    			pd.put("hasNoPro", 1);
    			List<ProductVo> products = productManager.queryProductListPage(pd);
    			if(products == null){
    				products = new ArrayList<ProductVo>();
    			}
				this.outEasyUIDataToJson(pd, products, response);
    		}else if(type == 5){	//团购
    			pd.put("status", 1);
    			pd.put("specialType", 3);
    			List<SpecialVo> specials = specialMananger.querySpecialListPage(pd);
    			if(specials == null){
    				specials = new ArrayList<SpecialVo>();
    			}
				this.outEasyUIDataToJson(pd, specials, response);
    		}else if (type == 6){	//参团
    			List<ProductGroupVo> productGroups = productGroupManager.queryListPage(pd);
    			if(productGroups == null){
    				productGroups = new ArrayList<ProductGroupVo>();
    			}
				this.outEasyUIDataToJson(pd, productGroups, response);
    		}else if(type == 7){	//限量抢购
    			pd.put("status", 1);
    			pd.put("specialType", 2);
    			List<SpecialVo> specials = specialMananger.querySpecialListPage(pd);
    			if(specials == null){
    				specials = new ArrayList<SpecialVo>();
    			}
				this.outEasyUIDataToJson(pd, specials, response);
    		}
		} catch (Exception e) {
			logger.error("/admin/jpush/queryPrePushDatas", e);
		}
    }
}
