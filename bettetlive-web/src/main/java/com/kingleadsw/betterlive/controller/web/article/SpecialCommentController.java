package com.kingleadsw.betterlive.controller.web.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;


/***
 * 专题评论
 */
@Controller
@RequestMapping("/admin/specialcomment")
public class SpecialCommentController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(SpecialCommentController.class);

	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private SpecialCommentManager specialCommentManager;
	
	/**
	 * 查询社区文章评论列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/findaSpecialList")
	public ModelAndView findListaSpecial(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("admin/article/list_comment_special");
		return mv;
	}
	
	
	/**
	 * 查看单个全部评论
	 * @param req
	 * @param resp
	 * @param model
	 * @return这个
	 */
	@RequestMapping(value = "/findIdList")
	public ModelAndView findIdList(HttpServletRequest req,
			HttpServletResponse resp,Model model) {
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView("admin/article/article_comment_replys");
		int id = Integer.parseInt(pd.getString("id"));
		model.addAttribute("id",id);
		return mv;
	}
	
	/**
	 * 查看单个全部评论
	 * @param req
	 * @param resp
	 * @param model
	 * @return这个
	 */
	@RequestMapping(value = "/findVideoIdList")
	public ModelAndView findVideoIdList(HttpServletRequest req,
			HttpServletResponse resp,Model model) {
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView("admin/article/comment_video_replys");
		int id = Integer.parseInt(pd.getString("id"));
		model.addAttribute("id",id);
		return mv;
	}
	
	
	/**
	 * 专题：美食教程评论
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/findVideoReview")
	public ModelAndView findVideoReview(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/special/list_comment_video");
		return mv;
	}
	
	/**
	 * 查询社区评论信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryArticleCommentJson")
	@ResponseBody
	public void queryArticleCommentJson(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		List<SpecialCommentVo> listSpecial = null;

		Integer articleFrom = pd.getInteger("articleFrom");
		
		if (articleFrom == null) {
			json.put("result", "fail");
			json.put("msg", "操作异常！");
			this.outObjectToJson(json, response);
		}
		
		pd.put("specialType", 5);
		try {
			
			//动态、文章的评论点赞均为5
			pd.put("commentPraiseType", 5);
			listSpecial = specialCommentManager.queryCommentByTypeListPage(pd);
			if (listSpecial == null) {
				listSpecial = new ArrayList<SpecialCommentVo>();
			}
		} catch (Exception e) {
			logger.error("查询社区文章异常....", e);

			listSpecial = new ArrayList<SpecialCommentVo>();
			this.outEasyUIDataToJson(pd, listSpecial, response);
			return;
		}

		logger.info("查询社区评论....结束");
		this.outEasyUIDataToJson(pd, listSpecial, response);
	}
	
	/**
	 * 审核评论
	 * @param httpRequest
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkSpecial")
	@ResponseBody
	public String checkSpecial(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		
		String commentId = httpRequest.getParameter("commentId");
		String status = httpRequest.getParameter("status");
		String opinion = httpRequest.getParameter("opinion");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("commentId", commentId);
		map.put("status", status);
		map.put("opinion", opinion);
		
		int result = specialCommentManager.updateReplyById(map);

		JSONObject json = new JSONObject();
		if (result > 0) {
			json.put("result", "succ");
		} else {
			json.put("result", "fail");
		}
		return json.toString();
	}
	
	/**
	 * 根据评论ID和专题ID删除评论
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delCommentInfo")
	@ResponseBody
	public Map<String, Object> delCommentInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		int result = specialCommentManager.delCommentBycId(pd);
		if (result > 0) {
			respMap.put("result", "succ");
		} else {
			respMap.put("result", "fail");
		}
		return respMap;
	}
	
	/**
	 * 单个评论回复列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/queryCommentByReplys")
	@ResponseBody
	public  Map<String, Object> queryCommentByReplys(HttpServletRequest req,
			HttpServletResponse resp) {
		PageData pd = this.getPageData();
		Map<String, Object> respMap = new HashMap<String, Object>();
		int id = Integer.parseInt(pd.getString("commentId"));
		List<SpecialCommentVo> listSpecial = null;
		try {
			listSpecial = specialCommentManager.queryCommentByRootListPage(id);
			if(listSpecial == null){
				listSpecial = new ArrayList<SpecialCommentVo>();
			}
			
			respMap.put("result", "succ");
			respMap.put("listComment", listSpecial);
			return respMap;
		} catch (Exception e) {
			logger.error("查询评论异常....", e);
			listSpecial = new ArrayList<SpecialCommentVo>();
			respMap.put("result", "fail");
			return respMap;
		}
		
	}
	/**
	 * 回复评论
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getIsCustom")
	@ResponseBody
	public Map<String, Object> getIsCustom(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> respMap = new HashMap<String, Object>();
		int commentId = Integer.parseInt(pd.getString("commentId"));
		try {
			SpecialCommentVo comm= specialCommentManager.queryCommentById(commentId);
			if (comm !=null) {
				respMap.put("result", "succ");
				respMap.put("comm", comm);
			} else {
				respMap.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询商品评论异常....", e);
		}
		return respMap;
	}
	
	/**
	 * 回复视频评论
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getVideoIsCustom")
	@ResponseBody
	public Map<String, Object> getVideoIsCustom(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> respMap = new HashMap<String, Object>();
		int commentId = Integer.parseInt(pd.getString("commentId"));
		try {
			SpecialCommentVo comm= specialCommentManager.queryCommentVideoById(commentId);
			if (comm !=null) {
				respMap.put("result", "succ");
				respMap.put("comm", comm);
			} else {
				respMap.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询视频评论异常....", e);
		}
		return respMap;
	}
	/**
	 * 添加回复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addReplyComment")
	@ResponseBody
	public Map<String, Object> addReplyComment(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		try {
			//回复的消息
			Integer parentId = Integer.parseInt(pd.getString("parentId"));
			//回复消息的主贴
			String replyMsg = pd.getString("content");
			Integer subMsgType = pd.getInteger("subMsgType");
			if(StringUtil.isNull(replyMsg)){
				map.put("result", "fail");
				map.put("msg", "评论内容不能为空");
				return map;
			}
			if(subMsgType == null){
				map.put("result", "fail");
				map.put("msg", "评论类型异常");
				return map;
			}
			SpecialCommentVo checkComment = specialCommentManager.queryCommentById(parentId);
			if (checkComment == null) {
				map.put("result", "fail");
				map.put("msg", "评论的内容不存在");
				return map;
			} 
			if(checkComment.getCustomerId().intValue() == 19761){
				map.put("result", "fail");
				map.put("msg", "不能自己回复自己");
				return map;
			}
			
			Integer rootId = 0;
			if(checkComment.getRootId() == 0){
				rootId = checkComment.getCommentId();
			}else{
				rootId = checkComment.getRootId();
			}
			SpecialCommentVo commentVo = new SpecialCommentVo();
			commentVo.setCustomerId(19761);//18690
			commentVo.setContent(replyMsg);
			commentVo.setParentId(parentId);
			commentVo.setRootId(rootId);
			commentVo.setStatus(2);
			commentVo.setIsCustomService(1);
			commentVo.setSpecialId(checkComment.getSpecialId());
			commentVo.setSpecialType(checkComment.getSpecialType());
			
			int iret = specialCommentManager.insertComment(commentVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "评论失败");
				return map;
			}
			
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
			//0是 13代表精选   1是12代表动态   0和1在这里主要是一个type(5)
			msgVo.setSubMsgType(subMsgType);
			msgVo.setMsgTitle("您有一条新的客服评论");
			msgVo.setMsgDetail(replyMsg);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(checkComment.getCustomerId());
			msgVo.setObjId(commentVo.getCommentId());
			messageManager.insert(msgVo);
		} catch (Exception e) {
			logger.error("/admin/productlabel/addproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "评论成功");
		return map;
	}
	
	
	/**
	 * 添加视频评论回复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addVideoReplyComment")
	@ResponseBody
	public Map<String, Object> addVideoReplyComment(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		try {
			//回复的消息
			Integer parentId = Integer.parseInt(pd.getString("parentId"));
			//回复消息的主贴
			String replyMsg = pd.getString("content");
			Integer subMsgType = pd.getInteger("subMsgType");
			if(StringUtil.isNull(replyMsg)){
				map.put("result", "fail");
				map.put("msg", "评论内容不能为空");
				return map;
			}
			if(subMsgType == null){
				map.put("result", "fail");
				map.put("msg", "评论类型异常");
				return map;
			}
			SpecialCommentVo checkComment = specialCommentManager.queryCommentVideoById(parentId);
			if (checkComment == null) {
				map.put("result", "fail");
				map.put("msg", "评论的内容不存在");
				return map;
			} 
			if(checkComment.getCustomerId().intValue() == 19761){
				map.put("result", "fail");
				map.put("msg", "不能自己回复自己");
				return map;
			}
			
			Integer rootId = 0;
			if(checkComment.getRootId() == 0){
				rootId = checkComment.getCommentId();
			}else{
				rootId = checkComment.getRootId();
			}
			SpecialCommentVo commentVo = new SpecialCommentVo();
			commentVo.setCustomerId(19761);//18690
			commentVo.setContent(replyMsg);
			commentVo.setParentId(parentId);
			commentVo.setRootId(rootId);
			commentVo.setStatus(2);
			commentVo.setIsCustomService(1);
			commentVo.setSpecialId(checkComment.getSpecialId());
			commentVo.setSpecialType(checkComment.getSpecialType());
			
			int iret = specialCommentManager.insertComment(commentVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "评论失败");
				return map;
			}
			
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
			msgVo.setSubMsgType(subMsgType);
			msgVo.setMsgTitle("您有一条新的客服评论");
			msgVo.setMsgDetail(replyMsg);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(checkComment.getCustomerId());
			msgVo.setObjId(commentVo.getCommentId());
			messageManager.insert(msgVo);
		} catch (Exception e) {
			logger.error("/admin/productlabel/addproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "评论成功");
		return map;
	}
	/**
	 * 查询视频评论全部信息
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/queryVideoJson")
	@ResponseBody
	public void queryVideoJson(HttpServletRequest req,
			HttpServletResponse resp) {
		PageData pd = this.getPageData();
		List<SpecialCommentVo> listSpecial = null;
		
		pd.put("specialType", 4);
		try {
			listSpecial = specialCommentManager.queryCommentByVideoListPage(pd);
			if (listSpecial == null) {
				listSpecial = new ArrayList<SpecialCommentVo>();
			}
		} catch (Exception e) {
			logger.error("查询视频评论异常....", e);
			listSpecial = new ArrayList<SpecialCommentVo>();
			this.outEasyUIDataToJson(pd, listSpecial, resp);
			return;
		}
		
		
		logger.info("查询视频评论....结束");


		this.outEasyUIDataToJson(pd, listSpecial, resp);
		
	}
}


	

