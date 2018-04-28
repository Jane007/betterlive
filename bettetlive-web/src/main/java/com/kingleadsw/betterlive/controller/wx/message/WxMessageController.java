package com.kingleadsw.betterlive.controller.wx.message;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 微信消息管理
 * @author xz
 */
@Controller
@RequestMapping(value = "/weixin/message")
public class WxMessageController extends AbstractWebController{
	
	private Logger logger = Logger.getLogger(WxMessageController.class);

	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private SpecialMananger specialManager;
	
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	@Autowired
	private CommentManager commentManager;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private SpecialCommentManager specialCommentManager;
	
	@Autowired SpecialMananger specialMananger;
	
	@Autowired
    private RedisService redisService;
	
	/**
	 * 查询所有未读消息的数量
	 */                     
	@RequestMapping(value="/readAllUnreadCount")
	@ResponseBody
	public Map<String,Object> readIndexUnreadCount(HttpServletRequest req, HttpServletResponse resp){

		CustomerVo customer = Constants.getCustomer(req);
		int msgCount = 0; //未读消息默认为0
		if(customer != null && customer.getCustomer_id() != null){
			//未读消息
			PageData pd = this.getPageData();
			pd.put("customerId", customer.getCustomer_id());
			msgCount = messageManager.selectCountByUnread(pd);
		}
		
		return CallBackConstant.SUCCESS.callback(msgCount);
	}
	
	/**
	 * 查询所有未读消息的数量
	 */                     
	@RequestMapping(value="/readInteractUnreadCount")
	@ResponseBody
	public Map<String,Object> readInteractUnreadCount(HttpServletRequest req, HttpServletResponse resp){

		CustomerVo customer = Constants.getCustomer(req);
		int commentMsgCount = 0; //未读评论消息默认为0
		int praiseMsgCount = 0;	//未读点赞消息
		if(customer != null){
			//未读消息
			PageData pd = this.getPageData();
			pd.put("customerId", customer.getCustomer_id());
			pd.put("msgType", 4);
			pd.put("flag", 0);
			commentMsgCount = messageManager.selectCountByUnread(pd);
			pd.put("flag", 1);
			praiseMsgCount = messageManager.selectCountByUnread(pd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentMsgCount", commentMsgCount);
		map.put("praiseMsgCount", praiseMsgCount);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 查询消息未读数
	 */                     
	@RequestMapping(value="/showUnread")
	@ResponseBody
	public ModelAndView showMsgUnread(HttpServletRequest req, HttpServletResponse resp, Model model){
		ModelAndView modelAndView=new ModelAndView("weixin/news/news");
		
		PageData pd =this.getPageData();
		MessageVo hhAct = new MessageVo();
		MessageVo sys = new MessageVo();
		MessageVo coupon = new MessageVo();
		MessageVo trans = new MessageVo();
		MessageVo friend = new MessageVo();
		MessageVo follow = new MessageVo();
		
		try {
			
			CustomerVo customer=Constants.getCustomer(req);
			if(customer != null && customer.getCustomer_id() != null){
				//挥货活动
				pd.put("msgType", MessageVo.MSGTYPE_ACT);
				pd.put("customerId", customer.getCustomer_id());
				hhAct = this.messageManager.selectByLast(pd);
				
				//系统消息
				pd.put("msgType", MessageVo.MSGTYPE_SYS);
				sys = this.messageManager.selectByLast(pd);
				
				//优惠券、红包消息
				pd.put("msgType", MessageVo.MSGTYPE_COUPON);
				coupon = this.messageManager.selectByLast(pd);
				
				//交易消息
				pd.put("msgType", MessageVo.MSGTYPE_TRANS);
				trans = this.messageManager.selectByLast(pd);
				
				//好友消息
				pd.put("msgType", MessageVo.MSGTYPE_FRIENDS);
				friend = this.messageManager.selectByLast(pd);
				
				//新增关注消息
				pd.put("msgType", MessageVo.MSGTYPE_FOLLOW);
				follow = this.messageManager.selectByLast(pd);
			}
			
			int activityCount = 0;
			String activityContent = "暂无最新消息";
			String activityTime = "";
			if(hhAct != null && hhAct.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", hhAct.getMsgType());
				activityCount = this.messageManager.selectCountByUnread(checkCount);
				if(StringUtil.isNotNull(hhAct.getMsgDetail())){
					activityContent = hhAct.getMsgDetail();
				}
				
				activityTime = hhAct.getCreateTimeStr();
			}
			
			int assetCount = 0;
			String assetContent = "暂无最新消息";
			String assetTime = "";
			if(coupon != null && coupon.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", coupon.getMsgType());
				assetCount = this.messageManager.selectCountByUnread(checkCount);
				
				if(StringUtil.isNotNull(coupon.getMsgDetail())){
					assetContent = coupon.getMsgDetail();
				}
				assetTime = coupon.getCreateTimeStr();
			}
			
			int interactCount = 0;
			String interactContent = "暂无最新消息";
			String interactTime = "";
			if(friend != null && friend.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", friend.getMsgType());
				interactCount = this.messageManager.selectCountByUnread(checkCount);
				
				if(StringUtil.isNotNull(friend.getMsgDetail())){
					interactContent = friend.getMsgDetail();
				}
				interactTime = friend.getCreateTimeStr();
			}
			
			int transCount = 0;
			String transContent = "暂无最新消息";
			String transTime = "";
			if(trans != null && trans.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", trans.getMsgType());
				transCount = this.messageManager.selectCountByUnread(checkCount);
				
				if(StringUtil.isNotNull(trans.getMsgDetail())){
					transContent = trans.getMsgDetail();
				}
				transTime = trans.getCreateTimeStr();
			}
			
			int sysCount = 0;
			String sysContent = "暂无最新消息";
			String sysTime = "";
			if(sys != null && sys.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", sys.getMsgType());
				sysCount = this.messageManager.selectCountByUnread(checkCount);
				
				if(StringUtil.isNotNull(sys.getMsgDetail())){
					sysContent = sys.getMsgDetail();
				}
				sysTime = sys.getCreateTimeStr();
			}
			
			int followCount = 0;
			String followContent = "暂无最新消息";
			String followTime = "";
			if(follow != null && follow.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", follow.getMsgType());
				followCount = this.messageManager.selectCountByUnread(checkCount);
				
				if(StringUtil.isNotNull(follow.getMsgDetail())){
					followContent = follow.getMsgDetail();
				}
				followTime = follow.getCreateTimeStr();
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("activityCount", activityCount);	//挥货活动未读数
			result.put("activityContent", activityContent);	//最新未读挥货活动消息内容
			result.put("activityTime", activityTime);	//最新未读挥货活动时间
			
			result.put("assetCount", assetCount);	//我的资产未读数
			result.put("assetContent", assetContent);	//最新未读我的资产消息内容
			result.put("assetTime", assetTime);	//最新未读我的资产时间
			
			result.put("interactCount", interactCount);	//我的互动未读数
			result.put("interactContent", interactContent);	//最新未读我的互动消息内容
			result.put("interactTime", interactTime);	//我的最新未读互动消息时间
			
			result.put("transCount", transCount);	//我的交易未读数
			result.put("transContent", transContent);	//最新未读我的交易消息内容
			result.put("transTime", transTime);	//最新未读我的交易消息时间
			
			result.put("sysCount", sysCount);	//我的系统消息未读数
			result.put("sysContent", sysContent);	//我的系统最新未读消息内容
			result.put("sysTime", sysTime);	//最新未读系统消息时间
			
			result.put("followCount", followCount);	//新增关注消息未读数
			result.put("followContent", followContent);	//新增关注最新未读消息内容
			result.put("followTime", followTime);	//最新未读新增关注消息时间
			modelAndView.addObject("result", result);
		} catch (Exception e) {
			logger.error("/weixin/message/showIndexUnread, error", e);
			modelAndView.setViewName("/weixin/404");
		}
		return modelAndView;
	}
	
	/**
	 * 访问消息列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMessageList")
	public ModelAndView toMessageList(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/news");
		PageData pd = this.getPageData();
		if(StringUtil.isNull(pd.getString("msgType"))){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		int msgType = pd.getInteger("msgType");
		if(msgType == 0){ //系统消息
			modelAndView.setViewName("/weixin/news/Systemmessage");
		}else if(msgType == 1){ //活动
			modelAndView.setViewName("/weixin/news/hhactivity");
		}else if(msgType == 2){ //优惠券、红包
			modelAndView.setViewName("/weixin/news/Couponmessage");
		}else if(msgType == 3){ //交易消息
			modelAndView.setViewName("/weixin/news/Transactionmessage");
		}else if(msgType == 4){ //好友消息
			modelAndView.setViewName("/weixin/news/msg_interact");
		}else if(msgType == 5){ //新增关注消息
			modelAndView.setViewName("/weixin/news/msg_newattention");
		}
		
		CustomerVo customer=Constants.getCustomer(request);
		int customerId = 0;
		if(customer != null && customer.getCustomer_id() != null && msgType != 4){
			//使消息已读
			MessageVo message = new MessageVo();
			message.setCustomerId(customer.getCustomer_id());
			message.setMsgType(msgType);
			message.setIsRead(1);
			this.messageManager.updateByPrimaryKeySelective(message);
			customerId = customer.getCustomer_id();
		}
		
		modelAndView.addObject("customerId", customerId);
		return modelAndView;
	}
	
	/**
	 * 消息列表
	 */                     
	@RequestMapping(value="/queryMsgList")
	@ResponseBody
	public Map<String,Object> queryMsgList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		List<MessageVo> mssages = new ArrayList<MessageVo>();
		try {
			CustomerVo customer=Constants.getCustomer(req);
			
			if(pd.get("msgType") == null || pd.getInteger("msgType") < 0){
				return CallBackConstant.PARAMETER_ERROR.callback("消息类型异常");
			}
			if(customer != null && customer.getCustomer_id() != null){
				int msgType = pd.getInteger("msgType");
				pd.put("customerId", customer.getCustomer_id());
				if(msgType == MessageVo.MSGTYPE_SYS){ //系统消息
					mssages = this.messageManager.queryListPage(pd);
				}else if(msgType == MessageVo.MSGTYPE_ACT){ //挥货活动
					mssages = this.messageManager.querySpecialMsgListPage(pd);
					if(mssages != null && mssages.size() > 0){
						for (MessageVo msgVo : mssages) {
							long nowTime = System.currentTimeMillis();
							if(msgVo.getObjType() == 1){ //限时活动
								if(msgVo.getObjTime().getTime() <= nowTime){
									msgVo.setObjStatus("0"); //失效
								}
								PageData spd = new PageData();
								spd.put("collectionType", 2);
								spd.put("shareType", 2);
								spd.put("praiseType", 4);
								spd.put("customerId", customer.getCustomer_id());
								spd.put("specialId", msgVo.getObjId());
								SpecialVo spvo = this.specialManager.queryOneByTutorial(spd);
								msgVo.setSpecialVo(spvo);
							}else if(msgVo.getObjType() == 2 || msgVo.getObjType() == 3){ //限量抢购、团购
								if(msgVo.getObjTime().getTime() <= nowTime){
									msgVo.setObjStatus("0"); //失效
								}
								PageData spd = new PageData();
								spd.put("specialId", msgVo.getObjId());
								SpecialVo spvo = this.specialManager.queryOneSpecByProductId(spd);
								msgVo.setSpecialVo(spvo);
							}
//							else if(msgVo.getObjType() == 4){	//美食教程
//								if(msgVo.getObjTime().getTime() <= nowTime){
//									msgVo.setObjStatus("0"); //失效
//								}
//								PageData spd = new PageData();
//								spd.put("collectionType", 2);
//								spd.put("shareType", 2);
//								spd.put("customerId", customer.getCustomer_id());
//								spd.put("specialId", msgVo.getObjId());
//								SpecialVo spvo = this.specialManager.queryOneByTutorial(spd);
//								msgVo.setSpecialVo(spvo);
//							}
						}
					}
				}else if(msgType == MessageVo.MSGTYPE_COUPON){ //优惠券、红包
					mssages = this.messageManager.queryCouponMsgListPage(pd);
					if(mssages != null && mssages.size() > 0){
						for (MessageVo couponVo : mssages) {
							long nowTime = System.currentTimeMillis();
							if(couponVo.getObjTime().getTime() <= nowTime){
								couponVo.setObjStatus("0"); //失效
							}
						}
					}
				}else if(msgType == MessageVo.MSGTYPE_TRANS){ //交易消息
					mssages = this.messageManager.queryTransMsgListPage(pd);
				
				}else if(msgType == MessageVo.MSGTYPE_FRIENDS){ //互动消息
					mssages = this.messageManager.queryCommentMsgListPage(pd);
				}
				if(mssages == null){
					mssages = new ArrayList<MessageVo>();
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", mssages);
			map.put("code", 1010);
			map.put("msg", "操作成功");
			map.put("currtime",System.currentTimeMillis());
			map.put("pageInfo", pd.get("pageView"));
			return map;
		} catch (Exception e) {
			logger.error("/weixin/message/queryMsgList, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 我的互动评论消息列表
	 */                     
	@RequestMapping(value="/queryInteractCommentMsgList")
	@ResponseBody
	public Map<String,Object> queryInteractCommentMsgList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		List<MessageVo> mssages = new ArrayList<MessageVo>();
		try {
			CustomerVo customer=Constants.getCustomer(req);
			Map<String, Object> result = new HashMap<String, Object>();
			if(customer == null || customer.getCustomer_id() == null){
				result = CallBackConstant.SUCCESS.callback(mssages);
				result.put("pageInfo", pd.get("pageView"));
				return result;
			}
			
			pd.put("customerId", customer.getCustomer_id());
			mssages = messageManager.queryCommentMsgByGroupSubTypeListPage(pd);
			if(mssages == null){
				mssages = new ArrayList<MessageVo>();
			}
			result = CallBackConstant.SUCCESS.callback(mssages);
			result.put("pageInfo", pd.get("pageView"));
			return result;
		} catch (Exception e) {
			logger.error("/weixin/message/queryInteractCommentMsgList, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 我的互动点赞消息列表
	 */                     
	@RequestMapping(value="/queryInteractPraiseMsgList")
	@ResponseBody
	public Map<String,Object> queryInteractPraiseMsgList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		List<MessageVo> mssages = new ArrayList<MessageVo>();
		try {
			CustomerVo customer=Constants.getCustomer(req);
			Map<String, Object> result = new HashMap<String, Object>();
			if(customer == null || customer.getCustomer_id() == null){
				result = CallBackConstant.SUCCESS.callback(mssages);
				result.put("pageInfo", pd.get("pageView"));
				return result;
			}
			
			pd.put("customerId", customer.getCustomer_id());
			mssages = messageManager.queryPraiseMsgByGroupSubTypeListPage(pd);
			if(mssages == null){
				mssages = new ArrayList<MessageVo>();
			}
			result = CallBackConstant.SUCCESS.callback(mssages);
			result.put("pageInfo", pd.get("pageView"));
			return result;
		} catch (Exception e) {
			logger.error("/weixin/message/queryInteractPraiseMsgList, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 消息页我的评论详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMessageCommentDetail")
	public ModelAndView toMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/product_msg_detail");
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		Integer commentId = pd.getInteger("commentId");
		Integer myCommentId = pd.getInteger("myCommentId");
		int customerId = customer.getCustomer_id();
		
		if(commentId == null){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
	
		if(myCommentId == null){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		//评论内容
		PageData cpd = new PageData();
		cpd.put("commentId", commentId);
		cpd.put("currentId", customerId);
		CommentVo myCommentVo = this.commentManager.queryCommentListByCommId(cpd);
		if(myCommentVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		
		ProductVo productVo = this.productManager.selectByPrimaryKey(myCommentVo.getProduct_id());
		pd.clear();
		pd.put("commentId", myCommentId);
		pd.put("customerId", customerId);
		messageManager.updateProductMessageReadStatus(pd);
		model.addAttribute("customerId", customer.getCustomer_id());
		model.addAttribute("commentVo", myCommentVo);
		model.addAttribute("productVo", productVo);
		return modelAndView;
	}
	
	/**
	 * 消息页我的动态评论详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toDynamicMessageCommentDetail")
	public ModelAndView toDynamicMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/dynamic_msg_detail");
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		Integer articleId = pd.getInteger("articleId");
		if(articleId == null){
			modelAndView.addObject("tipsTitle", "访问出错");
			modelAndView.addObject("tipsContent", "您访问的页面不存在");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
		if(saVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.clear();
		pd.put("articleId", articleId);
		pd.put("customerId", customer.getCustomer_id());
		messageManager.updateDynamicMessageReadStatus(pd);
		
		model.addAttribute("customerId", customer.getCustomer_id());
		model.addAttribute("saVo", saVo);
		return modelAndView;
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toDynamicMessageCommentReply")
	public ModelAndView toDynamicMessageCommentReply(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/dynamic_msg_replys");
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		pd.put("status", 1);
		SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
		if(saVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("commentPraiseType", 5);
		pd.put("currentId", customer.getCustomer_id());
		SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
		if(commentVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		model.addAttribute("customerId", customer.getCustomer_id());
		model.addAttribute("commentVo", commentVo);
		return modelAndView;
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toArticleMessageCommentDetail")
	public ModelAndView toArticleMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/article_msg_detail");
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		Integer myCommentId = pd.getInteger("myCommentId");
		if(myCommentId == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		Integer subMsgType = pd.getInteger("subMsgType");
		if(subMsgType == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("commentPraiseType", 5);
		pd.put("currentId", customer.getCustomer_id());
		SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
		if(commentVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		pd.put("articleId", commentVo.getSpecialId());
		SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
		if(saVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.clear();
		pd.put("commentId", myCommentId);
		pd.put("subMsgType", subMsgType);
		pd.put("customerId", customer.getCustomer_id());
		messageManager.updateArticleMessageReadStatus(pd);
		model.addAttribute("customerId", customer.getCustomer_id());
		model.addAttribute("commentVo", commentVo);
		model.addAttribute("saVo", saVo);
		return modelAndView;
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toVideoMessageCommentDetail")
	public ModelAndView toVideoMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/video_msg_detail");
		PageData pd = this.getPageData();
		CustomerVo customer=Constants.getCustomer(request);
		if(customer == null || customer.getCustomer_id() == null){
			return new ModelAndView("redirect:/weixin/tologin");
		}
		
		Integer myCommentId = pd.getInteger("myCommentId");
		if(myCommentId == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		Integer subMsgType = pd.getInteger("subMsgType");
		if(subMsgType == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("commentPraiseType", 3);
		pd.put("currentId", customer.getCustomer_id());
		SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
		if(commentVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.put("status", 1);
		pd.put("specialId", commentVo.getSpecialId());
		SpecialVo saVo = specialManager.queryOne(pd);
		if(saVo == null){
			modelAndView.addObject("tipsTitle", "评论消息提示");
			modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
			modelAndView.setViewName("/weixin/fuwubc");
			return modelAndView;
		}
		
		pd.clear();
		pd.put("commentId", myCommentId);
		pd.put("subMsgType", subMsgType);
		pd.put("customerId", customer.getCustomer_id());
		messageManager.updateArticleMessageReadStatus(pd);
		model.addAttribute("customerId", customer.getCustomer_id());
		model.addAttribute("commentVo", commentVo);
		model.addAttribute("saVo", saVo);
		return modelAndView;
	}
	
	/**
	 * 访问消息列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPraiseMsg")
	public ModelAndView toPraiseMsg(HttpServletRequest request,HttpServletResponse response,Model model) {
		ModelAndView modelAndView=new ModelAndView("weixin/news/msg_praise");
		
		CustomerVo customer=Constants.getCustomer(request);
		int customerId = 0;
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		modelAndView.addObject("customerId", customerId);
		return modelAndView;
	}
	
	/**
	 * 使点赞消息已读
	 */                     
	@RequestMapping(value="/checkPraiseRead")
	@ResponseBody
	public Map<String,Object> checkPraiseRead(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			CustomerVo customer=Constants.getCustomer(req);
			if(customer == null || customer.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			if(pd.getInteger("subMsgType") == null){
				return CallBackConstant.PARAMETER_ERROR.callback();
			}
			if(pd.getInteger("objId") == null){
				return CallBackConstant.PARAMETER_ERROR.callback();
			}
			int subMsgType = pd.getInteger("subMsgType");
			int praiseType = 1;
			if(subMsgType == 16){//我的动态被点赞
				praiseType = 4;
			}else if(subMsgType == 17 || subMsgType == 18){ //动态评论点赞,文章评论点赞
				praiseType = 5;
			}else if(subMsgType == 20){ //视频评论点赞
				praiseType = 3;
			}
			pd.put("customerId", customer.getCustomer_id());
			pd.put("praiseType", praiseType);
			this.messageManager.updatePraiseMsgReadStatus(pd);
			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("/weixin/message/checkPraiseRead, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
}
