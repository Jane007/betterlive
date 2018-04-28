package com.kingleadsw.betterlive.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ClassifyBannerManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ClassifyBannerVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * APP/微信端共用 分类Banner
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value ="/classifybanner")
public class ClassifyBannerController extends AbstractWebController{
	@Autowired
	private ClassifyBannerManager classifyBannerManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductSpecManager productSpecManager;
	
	/**
	 * 商品列表页Banner
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryClassifyBanner")
	@ResponseBody
	public Map<String, Object> queryClassifyBanner(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		pd.put("status", 1);
		ClassifyBannerVo clz = classifyBannerManager.queryOne(pd);
		if(clz == null){
			clz = new ClassifyBannerVo();
		}else if(StringUtil.isNotNull(clz.getProductId())){
			//活动
			PageData specialParams = new PageData();
			specialParams.put("status", 1);
			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", Integer.parseInt(clz.getProductId()));
			SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
			
			PageData activityMap = new PageData();
			activityMap.put("productId", clz.getProductId());
			activityMap.put("proStatus", 1);
			activityMap.put("specStatus", 1);
			if(specialVo != null){
				activityMap.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(activityMap);
			if(proSpec != null && null != specialVo){
				clz.setActivityType(specialVo.getSpecialType());
				clz.setSpecialId(specialVo.getSpecialId());
			}
		}
		return CallBackConstant.SUCCESS.callback(clz);
	}	
}
