package com.kingleadsw.betterlive.controller.wx.special;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;

/**
 * 微信版专题评价
 */
@Controller
@RequestMapping(value = "/weixin/specialcomment")
public class WxSpecialCommentController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(WxSpecialCommentController.class);
	
	@Autowired
	private SpecialCommentManager specialCommentManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SpecialMananger specialManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	/**
	 *  专题评论详情
	 */
	@RequestMapping(value = "/specialCommentDetail")
	@ResponseBody
    public Map<String,Object> specialCommentDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();

    	int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		if(StringUtil.isNull(pd.getString("commentPraiseType"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("评论点赞类型为空");
		}
		if(StringUtil.isNull(pd.getString("specialType"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("评论类型为空");
		}
		try {
			
			pd.put("status", 2);
			pd.put("currentId", customerId);
			List<SpecialCommentVo> commentList = this.specialCommentManager.queryCommentInfoListPage(pd);
			if(commentList == null){
				commentList = new ArrayList<SpecialCommentVo>();
			}
			Map<String, Object> map = CallBackConstant.SUCCESS.callback(commentList);
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/specialcomment/specialCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
    }
	
	
	/**
	 *  添加评论
	 */
	@RequestMapping(value = "/addComment")
	@ResponseBody
    public Map<String,Object> addComment(HttpServletRequest request) throws Exception {  
		PageData pd=this.getPageData();

		int customerId = 0;
		
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return CallBackConstant.TOKEN_TIME_OUT.callbackError("还没有登录哦");
		}
		customerId = customer.getCustomer_id();
		
		if(StringUtil.isNull(pd.getString("specialId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("专题ID为空");
		}
		if(StringUtil.isNull(pd.getString("content"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("评论内容为空");
		}
		if(StringUtil.isNull(pd.getString("specialType"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("评论类型为空");
		}
	
		int specialType = pd.getInteger("specialType");
		int specialId = pd.getInteger("specialId");
		try {
			SpecialCommentVo replyVo = new SpecialCommentVo();
			int parentCustId = 0;
			if(StringUtil.isNotNull(pd.getString("parentId"))){
				replyVo = this.specialCommentManager.queryCommentVideoById(pd.getInteger("parentId"));
				parentCustId = replyVo.getCustomerId();
				replyVo.setParentId(pd.getInteger("parentId"));
			}
			
			if(StringUtil.isNotNull(pd.getString("rootId"))){
				replyVo.setRootId(pd.getInteger("rootId"));
			}
			replyVo.setIsCustomService(0);
			replyVo.setCustomerId(customerId);
			replyVo.setContent(pd.getString("content"));
			replyVo.setSpecialId(specialId);
			replyVo.setSpecialType(specialType);
			
			
			
			int subMsgType = 14;
			
			if(specialType == 5){	//文章、动态
				SpecialArticleVo savo = this.specialArticleManager.selectByPrimaryKey(pd.getInteger("specialId"));
				if(savo == null){
					return CallBackConstant.DATA_NOT_FOUND.callback();
				}
				
				if(savo.getArticleFrom() == 0){
					subMsgType = 13;
				}else{
					if(StringUtil.isNotNull(pd.getString("parentId"))){
						subMsgType = 12;
					}else{
						subMsgType = 22;
						parentCustId = savo.getCustomerId();
					}
				}
			}
			int count = this.specialCommentManager.insertComment(replyVo);
			
			if(count > 0){
				if(parentCustId > 0){	//评论的主体是用户才发消息
					MessageVo msgVo = new MessageVo();
					msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
					msgVo.setSubMsgType(subMsgType);
					msgVo.setMsgTitle("您有一条评论消息");
					msgVo.setMsgDetail(pd.getString("content"));
					msgVo.setIsRead(0);
					msgVo.setCustomerId(parentCustId);
					msgVo.setObjId(replyVo.getCommentId());
					messageManager.insert(msgVo);
				}
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/app/specialcomment/addComment ----error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
