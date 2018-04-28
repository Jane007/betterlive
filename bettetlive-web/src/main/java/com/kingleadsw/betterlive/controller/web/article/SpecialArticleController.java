package com.kingleadsw.betterlive.controller.web.article;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ArticlePeriodicalManager;
import com.kingleadsw.betterlive.biz.ArticleRelationProManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialArticleTypeManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.PicUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.ArticlePeriodicalVo;
import com.kingleadsw.betterlive.vo.ArticleRelationProVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/***
 * 专题文章管理
 */
@Controller
@RequestMapping("/admin/specialarticle")
public class SpecialArticleController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(SpecialArticleController.class);
	@Autowired
	private SpecialArticleTypeManager specialArticleTypeManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private ArticlePeriodicalManager articlePeriodicalManager;
	@Autowired
	private ArticleRelationProManager articleRelationProManager;
	@Autowired
	private PicturesManager picturesManager;
	@Autowired
	private SpecialCommentManager specialCommentManager;
	@Autowired
	private MessageManager messageManager;
	
	/**
	 * 美食推荐（精选）文章
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/article/list_special_article");
		return mv;
	}
	
	/**
	 * 社区文章
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findCircleList")
	public ModelAndView findCircleList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/article/list_circle_article");
		return mv;
	}
	

	/**
	 * 查询专题文章
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/querySpecialArticleAllJson")
	@ResponseBody
	public void querySpecialAllJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		List<SpecialArticleVo> list = specialArticleManager.querySpecialArticleListPage(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<SpecialVo>(), response);
		}
	}
	
	/**
	 * 新增专题文章页面
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAddSpecialArticle")
	public ModelAndView toAdd(HttpServletRequest request,HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/article/add_special_article");
		PageData tpd = new PageData();
		tpd.put("status", 1);
		List<SpecialArticleTypeVo> types = this.specialArticleTypeManager.queryList(tpd);
		
		tpd.put("status", 1);
		List<ArticlePeriodicalVo> articlePeriodicals = articlePeriodicalManager.queryList(tpd);
		mv.addObject("types", types);
		mv.addObject("articlePeriodicals", articlePeriodicals);
		return mv;
	}
	
	/**
	 * 去修改精选文章
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toEditSpecialArticle")
	public ModelAndView toEditSpecialArticle(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		SpecialArticleVo sart = specialArticleManager.queryOne(pd);
		
		PageData tpd = new PageData();
		tpd.put("status", 1);
		List<SpecialArticleTypeVo> types = this.specialArticleTypeManager.queryList(tpd);
		
		tpd.put("status", 1);
		List<ArticlePeriodicalVo> articlePeriodicals = articlePeriodicalManager.queryList(tpd);
		
		tpd.clear();
		tpd.put("articleId", sart.getArticleId());
		List<ArticleRelationProVo> arps = this.articleRelationProManager.queryListPage(tpd);
		String productIds = "";
		String productNames = "";
		if(arps != null && arps.size() > 0){
			for (int i = 0; i < arps.size(); i++) {
				ArticleRelationProVo arp = arps.get(i);
				productIds += arp.getProductId()+",";
				productNames += arp.getProductName()+",";
			}
			productIds = productIds.substring(0, productIds.length()-1);
			productNames = productNames.substring(0, productNames.length()-1);
		}
		ModelAndView mv = new ModelAndView("admin/article/edit_special_article");
		mv.addObject("types", types);
		mv.addObject("article", sart);
		mv.addObject("articlePeriodicals", articlePeriodicals);
		mv.addObject("productIds", productIds);
		mv.addObject("productNames", productNames);
		return mv;
	}
	
	/**
	 * 新增/修改文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addSpecialArticle")
	@ResponseBody
	public Map<String, Object> addSpecialArticle(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String articleTitle=multipartRequest.getParameter("articleTitle");   	//专题标题
	        String articleCover=multipartRequest.getParameter("articleCover");    //专题封面
//	        MultipartFile homeCoverFile = multipartRequest.getFile("homeCover");    //主页列表封面
	        String articleIntroduce=multipartRequest.getParameter("articleIntroduce");        //介绍

	        String articleId=multipartRequest.getParameter("articleId");        //专题文章id
	        String status=multipartRequest.getParameter("status");
	        String homeFlag = multipartRequest.getParameter("homeFlag");	//是否推荐到首页
	        String content = multipartRequest.getParameter("content");	//是文章内容
	        String author = multipartRequest.getParameter("author");	//作者昵称
	        String articleTypeId = multipartRequest.getParameter("articleTypeId");	//文章类型ID
	        String periodicalId = multipartRequest.getParameter("periodicalId");	//文章期数
	        String articleFrom = multipartRequest.getParameter("articleFrom");	//文章来源
	        String recommendMore = multipartRequest.getParameter("recommendMore");	//是否推荐到更多动态
	        String linkPro = multipartRequest.getParameter("productIds");	//关联的产品
	        String homeSorts = multipartRequest.getParameter("homeSorts");	//首页排序
	        String recommendFlag = multipartRequest.getParameter("recommendFlag");	//每日推荐
	        	        
	        if(-1 != articleCover.lastIndexOf(",") && StringUtil.isNotNull(articleCover)){
	        	articleCover=articleCover.substring(0, articleCover.lastIndexOf(","));
	        }
	        
//	    	String homeFilePath = "discoveryarticle/";
//	        String homeCover = "";
//	    	if(homeCoverFile != null && homeCoverFile.getSize() > 0){
//	    		homeCover = ImageUpload.uploadFile(homeCoverFile, homeFilePath);
//         	}
	        if(StringUtil.isNull(homeSorts)){
	        	homeSorts ="0";
	        }
	        SpecialArticleVo special = new SpecialArticleVo();
	        special.setArticleTitle(articleTitle);
	        special.setArticleCover(articleCover);
	        special.setHomeCover(articleCover); //暂时使用articleCover
	        if(StringUtil.isNotNull(articleIntroduce)){
	        	special.setArticleIntroduce(articleIntroduce);
	        }
	        special.setHomeFlag(Integer.parseInt(homeFlag));
	        special.setContent(content);
	        special.setAuthor(author);
	        special.setArticleTypeId(Integer.parseInt(articleTypeId));
	        special.setPeriodicalId(Integer.parseInt(periodicalId));
	        special.setArticleFrom(Integer.parseInt(articleFrom));
	        special.setRecommendMore(Integer.parseInt(recommendMore));
	        special.setHomeSorts(Integer.parseInt(homeSorts));
	        special.setRecommendFlag(Integer.parseInt(recommendFlag));
	        if(StringUtils.isNotBlank(status)){					//状态
	        	special.setStatus(Integer.parseInt(status));
	        }else{
	        	special.setStatus(1);
	        }
	        
	        if(special.getStatus() == 1){	//发布
	        	special.setPublishTime(DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
	        }
	        
	        int count = 0;
	        
	        if(StringUtils.isNotEmpty(articleId)){//修改
	        	special.setArticleId(Integer.parseInt(articleId));
	        	count = specialArticleManager.updateByPrimaryKeySelective(special);
		 	        	
	        	if(count>0){
	        		respMap.put("result", "succ");
					respMap.put("msg", "修改成功！");
	        	}else{
	        		respMap.put("result", "fail");
					respMap.put("msg", "修改失败！");
	        	}
	        }else{
	        	count = specialArticleManager.insert(special);
	        	
	        	if(count>0){
					respMap.put("result", "succ");
					respMap.put("msg", "文章保存成功！");
				}else{
					respMap.put("result", "fail");
					respMap.put("msg", "文章保存失败！");
				}
	        }
	        
	        if(count > 0){
	        	articleRelationProManager.deleteByArticleIds(special.getArticleId());
        		List<ArticleRelationProVo> linkVos = new ArrayList<ArticleRelationProVo>();
     	        if(StringUtil.isNotNull(linkPro) && linkPro.split(",").length > 0){
      	        	String[] arrayPro = linkPro.split(",");
      	        	for (String str : arrayPro) {
      	        		ArticleRelationProVo arp = new ArticleRelationProVo();
      	        		arp.setArticleId(special.getArticleId());
      	        		arp.setProductId(Integer.parseInt(str));
      	        		linkVos.add(arp);
      				}
      	        	articleRelationProManager.insertAll(linkVos);
      	        }
	        }
		}catch(Exception e){
			respMap.put("result", "exce");
			respMap.put("msg", "保存失败,出现异常！");
			logger.info("连接地址：  /admin/specialarticle/addSpecialArticle ,操作：增加/修改文章信息。  操作状态： 失败！ 原因:出现异常");
		}
		return respMap;
	}
	
	/**
	 * 新增/修改文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/editCircleArticle")
	@ResponseBody
	public Map<String, Object> editCircleArticle(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> respMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        String articleTitle=multipartRequest.getParameter("articleTitle");   	//专题标题

	        String articleId=multipartRequest.getParameter("articleId");        //专题文章id
	        String status=multipartRequest.getParameter("status");
	        String homeFlag = multipartRequest.getParameter("homeFlag");	//是否推荐到首页
	        String content = multipartRequest.getParameter("content");	//是文章内容
	        String articleTypeId = multipartRequest.getParameter("articleTypeId");	//文章类型ID
	        String periodicalId = multipartRequest.getParameter("periodicalId");	//文章期数
	        String articleFrom = multipartRequest.getParameter("articleFrom");	//文章来源
	        String linkPro = multipartRequest.getParameter("productIds");	//关联的产品
	        String recommendMore = multipartRequest.getParameter("recommendMore");	//是否推荐到更多动态
	        String homeSorts = multipartRequest.getParameter("homeSorts");	//首页排序
	        //图片
	        String pictureArray = multipartRequest.getParameter("pictureArray");
	        //自定义图片中的一张为封面图,兼容原 api,如果没传递 index，默认还是取第一个。。。
	        Integer mainPicIndex = Integer.parseInt(multipartRequest.getParameter("mainPicIndex")==null?"0":multipartRequest.getParameter("mainPicIndex"));	//图片
	        String articleIntroduce = multipartRequest.getParameter("articleIntroduce");	//简介
	    	
	    	if(StringUtil.isNull(articleId)){
				return CallBackConstant.PARAMETER_ERROR.callback("主键ID不能为空");
			}
	    	if(StringUtil.isNull(pictureArray) || pictureArray.split(",").length <= 0){
				return CallBackConstant.PARAMETER_ERROR.callback("还未选择需要上传的图片");
			}
			if(StringUtil.isNull(homeSorts)){
				homeSorts = "0";
			}
	        SpecialArticleVo special = this.specialArticleManager.selectByPrimaryKey(Integer.parseInt(articleId));
	        special.setArticleTitle(articleTitle);
	        special.setHomeFlag(Integer.parseInt(homeFlag));
	        special.setContent(content);
	        special.setArticleTypeId(Integer.parseInt(articleTypeId));
	        special.setPeriodicalId(Integer.parseInt(periodicalId));
	        special.setArticleFrom(Integer.parseInt(articleFrom));
	        special.setRecommendMore(Integer.parseInt(recommendMore));
	        special.setHomeSorts(Integer.parseInt(homeSorts));
	        if(StringUtil.isNotNull(articleIntroduce)){
	        	special.setArticleIntroduce(articleIntroduce);
	        }
	        
	        if(StringUtils.isNotBlank(status)){					//状态
	        	special.setStatus(Integer.parseInt(status));
	        }else{
	        	special.setStatus(1);
	        }
	        if(special.getStatus() == 1){	//发布
	        	special.setPublishTime(DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
	        }
	        
	    	PageData tpd = new PageData();
    		tpd.put("objectId", Integer.parseInt(articleId));
    		tpd.put("pictureType", 5);
    		tpd.put("status", 1);
    		List<PicturesVo> pctList = picturesManager.queryList(tpd);
    		List<PicturesVo> pics = new ArrayList<PicturesVo>();
  	        String[] pcArray = pictureArray.split(",");
  	        List<String> strPics = new ArrayList<String>();
  	        for (int i = 0; i < pcArray.length; i++) {
  	        	strPics.add(pcArray[i]);
			}
    		if(pctList!=null&&pctList.size()>0){
    			for (PicturesVo pictures : pctList) {
    				for (String banner : pcArray) {//里有的就删掉
    					if(banner.equals(pictures.getOriginal_img())){
    						strPics.remove(banner);
    					}
    				}
    			}
    		}
    		//如果没有新上传的图片，封面图也可能会换
    		special.setArticleCover(pcArray[mainPicIndex]);
    	
    		if(strPics.size() > 0){
    			//获取指定index的图片作为封面使用 pcArray,strPics可能存在处理index
    			for (String picStr : strPics) {
    				BufferedImage buffImg = PicUtil.getImg(picStr);
					PicturesVo pictureVo = new PicturesVo();
 	 				pictureVo.setOriginal_img(picStr);
 	 				pictureVo.setSmall_img(picStr);
 	 				pictureVo.setStatus(1);
 	 				pictureVo.setPicture_type(5);
 	 				pictureVo.setObject_id(Integer.parseInt(articleId));
 	 				pictureVo.setPicHeight(buffImg.getHeight());
	 				pictureVo.setPicWidth(buffImg.getWidth());
 	 				pics.add(pictureVo);
				}
    		}
	        
	        int count = specialArticleManager.updateByPrimaryKeySelective(special);
	 	        	
        	if(count>0){
        		if(pics.size() > 0){
        			picturesManager.insertBatchFromList(pics);
        		}
        		
        		articleRelationProManager.deleteByArticleIds(Integer.parseInt(articleId));
        		List<ArticleRelationProVo> linkVos = new ArrayList<ArticleRelationProVo>();
     	        if(StringUtil.isNotNull(linkPro) && linkPro.split(",").length > 0){
      	        	String[] arrayPro = linkPro.split(",");
      	        	for (String str : arrayPro) {
      	        		ArticleRelationProVo arp = new ArticleRelationProVo();
      	        		arp.setArticleId(Integer.parseInt(articleId));
      	        		arp.setProductId(Integer.parseInt(str));
      	        		linkVos.add(arp);
      				}
      	        	articleRelationProManager.insertAll(linkVos);
      	        }
        		respMap.put("result", "succ");
				respMap.put("msg", "修改成功！");
        	}else{
        		respMap.put("result", "fail");
				respMap.put("msg", "修改失败！");
        	}
	        
		}catch(Exception e){
			respMap.put("result", "exce");
			respMap.put("msg", "保存失败,出现异常！");
			logger.error("连接地址：  /admin/specialarticle/editCircleArticle ,操作：增加/修改文章信息。  操作状态： 失败！ 原因:出现异常", e);
		}
		return respMap;
	}
	
	
	/**
	 * 修改文章状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/editArticleSpecialStatus")
	@ResponseBody
	public void editArticleSpecialStatus(HttpServletRequest request, HttpServletResponse response){		
		int count = 0 ;
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();		
		if(StringUtil.isNull(pd.getString("articleId"))){
			json.put("result", "fail");
			json.put("msg", "业务主键ID不能为空");
			this.outObjectToJson(json, response);
			return;
		}		
		if(StringUtil.isNull(pd.getString("checkStatus"))){
			json.put("result", "fail");
			json.put("msg", "操作异常！");
			this.outObjectToJson(json, response);
			return;
		}
		
		SpecialArticleVo specialVo = specialArticleManager.queryOne(pd);
		if(specialVo == null){
			json.put("result", "fail");
			json.put("msg", "文章不存在");
			this.outObjectToJson(json, response);
			return;
		}
		
		//理由
		if(specialVo.getArticleFrom() == 1 && StringUtil.isNull(pd.getString("opinion"))){
			json.put("result", "fail");
			json.put("msg", "操作异常！");
			this.outObjectToJson(json, response);
			return;
		}		
		
		int status = pd.getInteger("checkStatus");
		String opinion = pd.getString("opinion"); //意见		
		specialVo.setStatus(status);
		specialVo.setOpinion(opinion);
		
		if(status == 1){
			specialVo.setPublishTime(DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
		}		
		count = specialArticleManager.updateByPrimaryKeySelective(specialVo);		
		if(count>0){
			json.put("result", "succ");
			
			if(specialVo.getArticleFrom() == 1){	//动态审核发消息给用户
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(MessageVo.MSGTYPE_SYS);
				msgVo.setSubMsgType(11);
				if(specialVo.getStatus()==1){
					msgVo.setMsgTitle(String.format("您的[%s]审核通过", specialVo.getArticleTitle()));
				}else if(specialVo.getStatus()==4){
					msgVo.setMsgTitle(String.format("您的[%s]未通过审核", specialVo.getArticleTitle()));
				}
				msgVo.setMsgDetail(specialVo.getOpinion());
				msgVo.setIsRead(0);
				msgVo.setCustomerId(specialVo.getCustomerId());
				msgVo.setObjId(specialVo.getArticleId());
				messageManager.insert(msgVo);
			}
		}else{
			json.put("result", "fail");
			json.put("msg", "操作失败！");
		}
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 去修改社区文章
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/toEditCircleArticle")
	public ModelAndView toEditCircleArticle(HttpServletRequest request,HttpServletResponse resp){
		PageData pd = this.getPageData();
		
		SpecialArticleVo sart = specialArticleManager.queryOne(pd);
		
		PageData tpd = new PageData();
		tpd.put("status", 1);
		List<SpecialArticleTypeVo> types = this.specialArticleTypeManager.queryList(tpd);
		
		tpd.put("status", 1);
		List<ArticlePeriodicalVo> articlePeriodicals = articlePeriodicalManager.queryList(tpd);
		
		tpd.clear();
		tpd.put("articleId", sart.getArticleId());
		List<ArticleRelationProVo> arps = this.articleRelationProManager.queryListPage(tpd);
		String productIds = "";
		String productNames = "";
		if(arps != null && arps.size() > 0){
			for (int i = 0; i < arps.size(); i++) {
				ArticleRelationProVo arp = arps.get(i);
				productIds += arp.getProductId()+",";
				productNames += arp.getProductName()+",";
			}
			productIds = productIds.substring(0, productIds.length()-1);
			productNames = productNames.substring(0, productNames.length()-1);
		}
		
		tpd.clear();
		tpd.put("objectId", sart.getArticleId());
		tpd.put("pictureType", 5);
		tpd.put("status", 1);
		List<PicturesVo> pctList = picturesManager.queryList(pd);
		String[] pictureArray=new String[pctList.size()];
		if(pctList != null && pctList.size() > 0){
			for (int i = 0; i < pictureArray.length; i++) {
				pictureArray[i]=pctList.get(i).getOriginal_img();
			}
		}
		
		ModelAndView mv = new ModelAndView("admin/article/edit_circle_article");
		mv.addObject("types", types);
		mv.addObject("article", sart);
		mv.addObject("articlePeriodicals", articlePeriodicals);
		mv.addObject("productIds", productIds);
		mv.addObject("productNames", productNames);
		mv.addObject("pictureArray", pictureArray);
		return mv;
	}
	
	/**
	 * 修改图片排序和逻辑刪除
	 * @param picId
	 * @param sort
	 * @param isDel
	 * @return
	 */
	@RequestMapping(value = "/updatePictureSort")
	@ResponseBody
	public Map<String,Object> updatePictureSort(HttpServletRequest request, HttpServletResponse response){
		PageData pd = this.getPageData();
		String pictureId = pd.getString("pictureId");
	
		if(StringUtils.isBlank(pictureId)){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		int result = picturesManager.updatePicturesSort(pd);
		
		if(result<=0){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		
		return CallBackConstant.SUCCESS.callback();
				
	}
	
	/**
     * 删除图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/delProdImg")
    @ResponseBody
    public Map<String,Object> delProdImg(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String pictureId = pd.getString("pictureId");
		int result = 0;
		if(StringUtils.isBlank(pictureId)){
			return CallBackConstant.PARAMETER_ERROR.callback();
		}
		pd.put("pictureId", pictureId);		
		pd.put("status", 0);
		result = this.picturesManager.updatePicturesStatus(pd);
		if(result>0){
			json.put("result", "succ");
			
		}else{
			json.put("result", "fail");
			json.put("msg", "删除图片失败！");
		}
	
		return CallBackConstant.SUCCESS.callback();
    }
	/**
	 * 查询图片
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryPictureList")
	@ResponseBody
	public Map<String, Object> queryPictureList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd.put("pictureType", 5);
		pd.put("status", 1);
		//增加一个排序查询方法,按照soft进行排序
		List<PicturesVo> list = picturesManager.queryList(pd);
		if(list == null){
			list = new ArrayList<PicturesVo>();
		}else{
			String[] pictureArray=new String[list.size()];
			for (int i = 0; i < pictureArray.length; i++) {
				pictureArray[i]=list.get(i).getOriginal_img();
			}
			
			map.put("pictureArray", pictureArray);
		}
		map.put("list", list);
		
		return map;
	}
	
}

