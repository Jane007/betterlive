package com.kingleadsw.betterlive.controller.web.productcomment;

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

import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.MessageVo;

@Controller
@RequestMapping(value = "/admin/productcomment")
public class ProductCommentController extends AbstractWebController {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CommentManager commentManager;

	@Autowired
	private MessageManager messageManager;

	/**
	 * 跳转查询商品评论页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findListComment(HttpServletRequest req,
			HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("admin/productcomment/list_comment");
		return mv;
	}
	
	@RequestMapping(value = "/findIdList")
	public ModelAndView findIdList(HttpServletRequest req,
			HttpServletResponse resp,Model model) {
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView("admin/productcomment/comment_detail");
		int id = Integer.parseInt(pd.getString("id"));
		model.addAttribute("id",id);
		return mv;
	}
	/**
	 * 单个评论回复列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/queryCommentByRootIdList")
	@ResponseBody
	public  Map<String, Object> queryCommentByRootIdList(HttpServletRequest req,
			HttpServletResponse resp) {
		PageData pd = this.getPageData();
		Map<String, Object> respMap = new HashMap<String, Object>();
		int id = Integer.parseInt(pd.getString("rootId"));
		List<CommentVo> listComment = null;
		try {
			listComment = commentManager.queryCommentByRootIdListPage(id);
			if (listComment!=null) {
				respMap.put("result", "succ");
				respMap.put("listComment", listComment);
			} else {
				respMap.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询商品评论异常....", e);
			listComment = new ArrayList<CommentVo>();
		}
		
		
		logger.info("查询商品评论....结束");

		return respMap;
	}
	
	@RequestMapping(value = "/queryCommentById")
	@ResponseBody
	public Map<String, Object> queryCommentById(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> respMap = new HashMap<String, Object>();
		int commentId = Integer.parseInt(pd.getString("commentId"));
		CommentVo comment = null;
		try {
			//不验证是否已经存在回复
			//CommentVo comm= commentManager.getIsCustom(commentId);
			comment = commentManager.queryCommentById(commentId);
			if (comment!=null) {
				
				respMap.put("result", "succ");
				respMap.put("comment", comment);
			} else {
				respMap.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询商品评论异常....", e);
		}
		
		
		logger.info("查询商品评论....结束");

		return respMap;
	}


	/**
	 * 查询商品评论信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryCommentAllJson")
	@ResponseBody
	public void queryCommentAllJson(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		logger.info("查询商品评论....开始");

		PageData pd = this.getPageData();
		List<CommentVo> listComment = null;
		
		try {
			listComment = commentManager.queryCommentInfoListPage(pd);
			if (listComment == null) {
				listComment = new ArrayList<CommentVo>();
			}
		} catch (Exception e) {
			logger.error("查询商品评论异常....", e);

			listComment = new ArrayList<CommentVo>();
		}

		logger.info("查询商品评论....结束");
		
		this.outEasyUIDataToJson(pd, listComment, response);
	}

	/**
	 * 根据评论ID和商品ID删除商品评论
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delCommentInfo")
	@ResponseBody
	public Map<String, Object> queryAccessoriesJson(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		int result = commentManager.delCommentBycId(pd);
		if (result > 0) {
			respMap.put("result", "succ");
		} else {
			respMap.put("result", "fail");
		}
		return respMap;
	}

	@RequestMapping(value = "/addReplyComment")
	@ResponseBody
	public Map<String, Object> addReplyComment(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer commentId = Integer.parseInt(request.getParameter("commentId"));
			
			String replyMsg = request.getParameter("replyMsg");
			
			if(StringUtil.isNull(replyMsg)){
				map.put("result", "fail");
				map.put("msg", "评论内容不能为空");
				return map;
			}
			
			CommentVo commentVo = commentManager.queryCommentById(commentId);
		
			if (commentVo == null) {
				map.put("result", "fail");
				map.put("msg", "评论的内容不存在");
				return map;
			} 
			if(commentVo.getCustomer_id().intValue() == 19761){
				map.put("result", "fail");
				map.put("msg", "不能自己回复自己");
				return map;
			}
			
			Integer rootId = 0;
			if(commentVo.getRoot_id() == 0){
				rootId = commentVo.getComment_id();
			}else{
				rootId = commentVo.getRoot_id();
			}
				
			commentVo.setCustomer_id(19761);//18690
			commentVo.setContent(replyMsg);
			commentVo.setParent_id(commentId);
			commentVo.setRoot_id(rootId);
			commentVo.setStatus(2);
			commentVo.setIs_custom_service(1);
			int iret = commentManager.insertComment(commentVo);
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "评论失败");
				return map;
			}
			CommentVo commentc = commentManager.queryCommentById(commentId);
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
			msgVo.setSubMsgType(9);
			msgVo.setMsgTitle("您有一条新的客服评论");
			msgVo.setMsgDetail(replyMsg);
			msgVo.setIsRead(0);
			msgVo.setCustomerId(commentc.getCustomer_id());
			msgVo.setObjId(commentVo.getComment_id());
			messageManager.insert(msgVo);
			
		} catch (Exception e) {
			logger.error("/admin/productlabel/addproductlabel------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "评论成功");
		return map;
	}

	/**
	 * 审核用户评论 2017-02-14 by chen
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkComment")
	@ResponseBody
	public String findCommentInfo(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String msg = "checkComment";
		logger.info("/admin/comment/" + msg + " begin");

		String commentId = httpRequest.getParameter("commentId");
		String status = httpRequest.getParameter("status");
//		String replyMsg = httpRequest.getParameter("content");

		Map<String, String> map = new HashMap<String, String>();
		map.put("commentId", commentId);
		map.put("status", status);
		int result = commentManager.updateReplyById(map);
		
		JSONObject json = new JSONObject();
		CommentVo comm =  commentManager.queryCommentById(Integer.parseInt(commentId));
		if (result > 0) {
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_SYS);
			msgVo.setSubMsgType(23);
			msgVo.setMsgTitle("您有一条新审核记录");
			if(comm.getStatus()==2){
				msgVo.setMsgDetail("["+comm.getContent()+"]已审核通过");
			}else if(comm.getStatus()==3){
				msgVo.setMsgDetail("["+comm.getContent()+"]审核不通过");
			}
			msgVo.setIsRead(0);
			msgVo.setCustomerId(comm.getCustomer_id());
			msgVo.setObjId(comm.getComment_id());
			messageManager.insert(msgVo);
			json.put("result", "succ");
		} else {
			json.put("result", "fail");
		}
		logger.info("--->结束调用/admin/comment/" + msg);
		return json.toString();
	}
}
