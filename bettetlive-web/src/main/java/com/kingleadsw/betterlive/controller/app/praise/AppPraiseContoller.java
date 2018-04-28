package com.kingleadsw.betterlive.controller.app.praise;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.PraiseManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.PraiseVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;

@Controller
@RequestMapping(value = "/app/praise")
public class AppPraiseContoller extends AbstractWebController{
	private static Logger logger = Logger.getLogger(AppPraiseContoller.class);
	@Autowired
	private PraiseManager praiseManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private SpecialCommentManager specialCommentManager;
	
	/**
	 * 点赞
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/addPraise", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPraise(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		PageData pd = this.getPageData();
		String token = pd.getString("token"); // 用户标识
		CustomerVo customer = null;
		//用户标识
		if(StringUtil.isNull(token)){
			return CallBackConstant.TOKEN_ERROR.callbackError("用户信息为空");
		}
		
		customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
		}

		if(StringUtil.isNull(pd.getString("praiseType"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("点赞类型为空");
		}
		if(StringUtil.isNull(pd.getString("objId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("业务ID为空");
		}

		try {
			int praiseType = pd.getInteger("praiseType");
			int objId = pd.getInteger("objId");
			
			pd.put("customerId", customer.getCustomer_id());
			pd.put("praiseType", praiseType);
			pd.put("objId", objId);
			
			PraiseVo praiseVo = this.praiseManager.queryOne(pd);
			if(praiseVo != null){
				return CallBackConstant.FAILED.callbackError("不能重复点赞");
			}
			
			int shareCustomerId = 0;
			String checkSHareCust = pd.getString("shareCustomerId");
			if(StringUtil.isNotNull(checkSHareCust) && !checkSHareCust.equals("0")){
				shareCustomerId = Integer.parseInt(checkSHareCust);
			}
			
			PraiseVo prv = new PraiseVo();
			prv.setCustomerId(customer.getCustomer_id());
			prv.setPraiseType(praiseType);
			prv.setObjId(objId);
			prv.setShareCustomerId(shareCustomerId);
			int count = praiseManager.insertPraise(prv);
			
			if (count <= 0) {
				return CallBackConstant.FAILED.callback();
			}
			
			int sendCustId = 0;
			int subMsgType = 0;
			String content = customer.getNickname() + "点赞了您的评论";
			if(praiseType == 1){	//商品评论点赞
				subMsgType = 15;
				CommentVo cmmtVo = commentManager.selectByPrimaryKey(objId);
				sendCustId = cmmtVo.getCustomer_id();
			}
//				else if(praiseType == 2){	//视频点赞
//					subMsgType = 21;
//				}
			else if(praiseType == 3){	//视频评论点赞
				subMsgType = 20;
				SpecialCommentVo spcmmtVo = specialCommentManager.queryCommentVideoById(objId);
				sendCustId = spcmmtVo.getCustomerId();
			
			}else if(praiseType == 4){	//精选、动态点赞
				SpecialArticleVo sav = this.specialArticleManager.selectByPrimaryKey(objId);
				if(sav.getArticleFrom() == 0){ //精选
//						subMsgType = 19;
				}else{	//动态
					subMsgType = 16;
					sendCustId = sav.getCustomerId();
					content = customer.getNickname() + "点赞了您的动态";
				}
			}else if(praiseType == 5){	//精选、动态评论点赞
				SpecialCommentVo spcmmtVo = specialCommentManager.queryCommentVideoById(objId);
				SpecialArticleVo sav = this.specialArticleManager.selectByPrimaryKey(spcmmtVo.getSpecialId());
				if(sav.getArticleFrom() == 0){ //精选
					subMsgType = 18;
				}else{	//动态
					subMsgType = 17;
				}
				sendCustId = spcmmtVo.getCustomerId();
			}
			
			if(sendCustId > 0){
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
				msgVo.setSubMsgType(subMsgType);
				msgVo.setMsgTitle("您有一条点赞消息");
				msgVo.setMsgDetail(content);
				msgVo.setIsRead(0);
				msgVo.setCustomerId(sendCustId);
				msgVo.setObjId(prv.getPraiseId());
				messageManager.insert(msgVo);
			}
			
			return CallBackConstant.SUCCESS.callback(prv.getPraiseId());
		} catch (NumberFormatException e) {
			logger.error("/app/praise/addPraise----error:"+ e);
			return CallBackConstant.FAILED.callback();
		}

	}
	
	/**
	 * 取消点赞
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/canselPraise", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> canselPraise(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("/app/praise/canselPraise----begin");

		PageData pd = this.getPageData();

		String token = pd.getString("token"); // 用户标识

		//用户标识
		if(StringUtil.isNull(token)){
			return CallBackConstant.TOKEN_ERROR.callbackError("用户信息为空");
		}else{
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
			}
		}

		if(StringUtil.isNull(pd.getString("praiseId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("id为空");
		}

		int count = 0;
		try {
			PraiseVo praiseVo = this.praiseManager.selectByPrimaryKey(pd.getInteger("praiseId"));
			if(praiseVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("点赞内容不存在");
			}
			
			count = praiseManager.deleteByPrimaryKey(pd.getInteger("praiseId"));

			if (count > 0) {
				int subMsgType = 0;
				if(praiseVo.getPraiseType() == 1){	//商品评论点赞
					subMsgType = 15;
				}
//				else if(praiseVo.getPraiseId() == 2){	//视频点赞
//					subMsgType = 21;
//				}
				else if(praiseVo.getPraiseType() == 3){	//视频评论点赞
					subMsgType = 20;
				}else if(praiseVo.getPraiseType() == 4){	//精选、动态点赞
					SpecialArticleVo sav = this.specialArticleManager.selectByPrimaryKey(praiseVo.getObjId());
					if(sav.getArticleFrom() == 0){ //精选
//						subMsgType = 19;
					}else{	//动态
						subMsgType = 16;
					}
				}else if(praiseVo.getPraiseType() == 5){	//精选、动态评论点赞
					SpecialArticleVo sav = this.specialArticleManager.selectByPrimaryKey(praiseVo.getObjId());
					if(sav.getArticleFrom() == 0){ //精选
						subMsgType = 18;
					}else{	//动态
						subMsgType = 17;
					}
				}
				if(subMsgType > 0){
					PageData delPd = new PageData();
					delPd.put("subMsgType", subMsgType);
					delPd.put("objId", praiseVo.getPraiseId());
					messageManager.delete(delPd);
				}
				return CallBackConstant.SUCCESS.callback();
			} else {
				return CallBackConstant.FAILED.callback();
			}
		} catch (NumberFormatException e) {
			logger.error("/app/praise/canselPraise----error:"+ e);
			return CallBackConstant.FAILED.callback();
		}


	}
}
