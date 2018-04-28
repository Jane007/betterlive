package com.kingleadsw.betterlive.controller.app.message;

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

import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysGroupManager;
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
 * app消息管理
 * @author xz
 */
@Controller
@RequestMapping(value = "/app/message")
public class AppMessageController extends AbstractWebController{
	
	private Logger logger = Logger.getLogger(AppMessageController.class);

	@Autowired
	private MessageManager messageManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private SpecialMananger specialManager;
	
	@Autowired
	private SysGroupManager sysGroupManager;
	
	@Autowired
	private CommentManager commentManager;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private SpecialArticleManager specialArticleManager;
	
	@Autowired
	private SpecialCommentManager specialCommentManager;
	
	
	@Autowired
    private RedisService redisService;
	
	/**
	 * 查询所有未读消息的数量
	 */                     
	@RequestMapping(value="/readAllUnreadCount")
	@ResponseBody
	public Map<String,Object> readIndexUnreadCount(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		String token = pd.getString("token");  							//用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.SUCCESS.callback(0);
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		int msgCount = 0; //未读消息默认为0
		if(customer != null){
			//未读消息
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
		//未读消息
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.SUCCESS.callback(0);
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		int commentMsgCount = 0; //未读评论消息默认为0
		int praiseMsgCount = 0;	//未读点赞消息
		if(customer != null){
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
	public Map<String,Object> showUnread(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		int hhActCount = 0;
		int sysCount = 0;
		int couponCount = 0;
		int transCount = 0;
		int friendCount = 0;
		try {
			
			String token = pd.getString("token");  							//用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (null == customer) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			//挥货活动
			pd.put("msgType", MessageVo.MSGTYPE_ACT);
			pd.put("customerId", customer.getCustomer_id());
			hhActCount = this.messageManager.selectCountByUnread(pd);
			
			MessageVo hhAct = this.messageManager.selectByLast(pd);
			if(hhAct == null){
				hhAct = new MessageVo();
			}
			
			//系统消息
			pd.put("msgType", MessageVo.MSGTYPE_SYS);
			sysCount = this.messageManager.selectCountByUnread(pd);
			
			MessageVo msg = this.messageManager.selectByLast(pd);
			if(msg == null){
				msg = new MessageVo();
			}
			
			//优惠券、红包消息
			pd.put("msgType", MessageVo.MSGTYPE_COUPON);
			couponCount = this.messageManager.selectCountByUnread(pd);
			
			MessageVo coupon = this.messageManager.selectByLast(pd);
			if(coupon == null){
				coupon = new MessageVo();
			}
			
			//交易消息
			pd.put("msgType", MessageVo.MSGTYPE_TRANS);
			transCount = this.messageManager.selectCountByUnread(pd);
			
			MessageVo trans = this.messageManager.selectByLast(pd);
			if(trans == null){
				trans = new MessageVo();
			}
			
			//好友消息
			pd.put("msgType", MessageVo.MSGTYPE_FRIENDS);
			friendCount = this.messageManager.selectCountByUnread(pd);
			
			MessageVo friend = this.messageManager.selectByLast(pd);
			if(friend == null){
				friend = new MessageVo();
			}
			
			result.put("hhActCount", hhActCount);	//挥货活动未读数
			result.put("hhAct", hhAct);	//最新未读挥货活动
			result.put("msgCount", sysCount);	//系统消息未读数
			result.put("sys", msg);	//最新未读系统消息
			result.put("couponCount", couponCount);	//优惠券。红包未读数
			result.put("coupon", coupon);	//最新未读优惠券、红包消息
			result.put("transCount", transCount);	//交易未读数
			result.put("trans", trans);	//最新未读交易信息
			result.put("friendCount", friendCount);	//好友消息未读数
			result.put("friend", friend);	//最新未读好友信息
			return CallBackConstant.SUCCESS.callback(result);
		} catch (Exception e) {
			logger.error("/app/message/showIndexUnread, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * App2.0版本 查询消息未读数
	 */                     
	@RequestMapping(value="/showMsgUnread")
	@ResponseBody
	public Map<String,Object> showMsgUnread(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			
			String token = pd.getString("token");  							//用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);

			pd.put("customerId", customer.getCustomer_id());
			
			//挥货活动
			pd.put("msgType", MessageVo.MSGTYPE_ACT);
			MessageVo hhAct = this.messageManager.selectByLast(pd);

			//系统消息
			pd.put("msgType", MessageVo.MSGTYPE_SYS);
			MessageVo sys = this.messageManager.selectByLast(pd);
			
			//优惠券、红包消息
			pd.put("msgType", MessageVo.MSGTYPE_COUPON);
			MessageVo coupon = this.messageManager.selectByLast(pd);
			
			//交易消息
			pd.put("msgType", MessageVo.MSGTYPE_TRANS);
			MessageVo trans = this.messageManager.selectByLast(pd);
			
			//好友消息
			pd.put("msgType", MessageVo.MSGTYPE_FRIENDS);
			MessageVo friend = this.messageManager.selectByLast(pd);
			
			//新增关注
			pd.put("msgType", MessageVo.MSGTYPE_FOLLOW);
			MessageVo follow = this.messageManager.selectByLast(pd);
			
			List<MessageVo> msgList = new ArrayList<MessageVo>();
			String activityContent = "暂无最新消息";
			if(hhAct != null && hhAct.getMsgId() > 0){
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", hhAct.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				hhAct.setUnreadCount(count);
				
				if(StringUtil.isNotNull(hhAct.getMsgDetail())){
					activityContent = hhAct.getMsgDetail();
				}
			}else{
				hhAct = new MessageVo();
			}
			hhAct.setMsgDetail(activityContent);
			msgList.add(hhAct);
			
			String assetContent = "暂无最新消息";
			if(coupon != null && coupon.getMsgId() > 0){
				
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", coupon.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				coupon.setUnreadCount(count);
				
				if(StringUtil.isNotNull(coupon.getMsgDetail())){
					assetContent = coupon.getMsgDetail();
				}
			}else{
				coupon = new MessageVo();
			}
			coupon.setMsgDetail(assetContent);
			msgList.add(coupon);
			
			String interactContent = "暂无最新消息";
			if(friend != null && friend.getMsgId() > 0){
				
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", friend.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				friend.setUnreadCount(count);
				
				if(StringUtil.isNotNull(friend.getMsgDetail())){
					interactContent = friend.getMsgDetail();
				}
			}else{
				friend = new MessageVo();
			}
			friend.setMsgDetail(interactContent);
			msgList.add(friend);
			
			
			
			
			
			String transContent = "暂无最新消息";
			if(trans != null && trans.getMsgId() > 0){
				
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", trans.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				trans.setUnreadCount(count);
				
				if(StringUtil.isNotNull(trans.getMsgDetail())){
					transContent = trans.getMsgDetail();
				}
			}else{
				trans = new MessageVo();
			}
			trans.setMsgDetail(transContent);
			msgList.add(trans);
			
			String sysContent = "暂无最新消息";
			if(sys != null && sys.getMsgId() > 0){
				
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", sys.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				sys.setUnreadCount(count);
				
				sysContent = sys.getMsgDetail();
				if(StringUtil.isNotNull(sys.getMsgDetail())){
					sysContent = sys.getMsgDetail();
				}
			}else{
				sys = new MessageVo();
			}
			sys.setMsgDetail(sysContent);
			msgList.add(sys);
			
			String follewContent = "暂无最新消息";
			if(follow != null && follow.getMsgId() >0){
				
				PageData checkCount = new PageData();
				checkCount.put("customerId", customer.getCustomer_id());
				checkCount.put("msgType", follow.getMsgType());
				int count = this.messageManager.selectCountByUnread(checkCount);
				follow.setUnreadCount(count);
				
				if(StringUtil.isNotNull(follow.getMsgDetail())){
					follewContent = follow.getMsgDetail();
				}
			}else{
				follow = new MessageVo();
			}
			follow.setMsgDetail(follewContent);
			msgList.add(follow);
			
			
			
			return CallBackConstant.SUCCESS.callback(msgList);
		} catch (Exception e) {
			logger.error("/app/message/showIndexUnread, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 使消息变成已读
	 */                     
	@RequestMapping(value="/readMessage")
	@ResponseBody
	public Map<String,Object> readMessage(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			String token = pd.getString("token");  							//用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (null == customer) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			if(pd.get("msgType") == null || pd.getInteger("msgType") < 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("消息类型获取失败");
			}
			
			if(pd.getInteger("msgType").intValue() != 4){
				MessageVo message = new MessageVo();
				message.setCustomerId(customer.getCustomer_id());
				message.setMsgType(pd.getInteger("msgType"));
				message.setIsRead(1);
				this.messageManager.updateByPrimaryKeySelective(message);
			}
			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("/app/message/readMessage, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 消息列表
	 */                     
	@RequestMapping(value="/queryMsgList")
	@ResponseBody
	public Map<String,Object> queryMsgList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		List<MessageVo> mssages = new ArrayList<MessageVo>();
		try {
			String token = pd.getString("token");  							//用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (null == customer) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			if(pd.get("msgType") == null || pd.getInteger("msgType") < 0){
				return CallBackConstant.PARAMETER_ERROR.callbackError("消息类型获取失败");
			}
			
			int msgType = pd.getInteger("msgType");
			pd.put("customerId", customer.getCustomer_id());
			if(msgType == MessageVo.MSGTYPE_SYS){ //系统消息
				mssages = this.messageManager.queryListPage(pd);
			}else if(msgType == MessageVo.MSGTYPE_ACT){ //挥货活动
				mssages = this.messageManager.querySpecialMsgListPage(pd);
				if(mssages != null && mssages.size() > 0){
					for (MessageVo msgVo : mssages) {
						long nowTime = System.currentTimeMillis();
						if(msgVo.getObjTime().getTime() <= nowTime){
							msgVo.setObjStatus("0"); //失效
						}
						if(msgVo.getObjType() == 1){ //限时活动
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
			result.put("messages", mssages);
			return CallBackConstant.SUCCESS.callback(mssages);
		} catch (Exception e) {
			logger.error("/app/message/queryMsgList, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 我的互动消息列表
	 */                     
	@RequestMapping(value="/queryInteractCommentMsgList")
	@ResponseBody
	public Map<String,Object> queryInteractCommentMsgList(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		List<MessageVo> mssages = new ArrayList<MessageVo>();
		try {
			String token = pd.getString("token"); //用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (null == customer) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			pd.put("customerId", customer.getCustomer_id());
			mssages = messageManager.queryCommentMsgByGroupSubTypeListPage(pd);
			if(mssages == null){
				mssages = new ArrayList<MessageVo>();
			}
			return CallBackConstant.SUCCESS.callback(mssages);
		} catch (Exception e) {
			logger.error("/app/message/queryInteractCommentMsgList, error", e);
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
			String token = pd.getString("token"); //用户标识
			if (StringUtil.isNull(token)) {
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (null == customer) {
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			
			pd.put("customerId", customer.getCustomer_id());
			mssages = messageManager.queryPraiseMsgByGroupSubTypeListPage(pd);
			if(mssages == null){
				mssages = new ArrayList<MessageVo>();
			}
			return CallBackConstant.SUCCESS.callback(mssages);
		} catch (Exception e) {
			logger.error("/app/message/queryInteractPraiseMsgList, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 *  app1.2.4消息页我的评论详情
	 */
	@RequestMapping(value = "/productMessageDetail")
	@ResponseBody
    public Map<String,Object> productMessageDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();

    	String token = pd.getString("token");  	
		int customerId = 0;
		Integer commentId = pd.getInteger("commentId");
		Integer myCommentId = pd.getInteger("myCommentId");
		
		if(commentId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
		}
		
		if(myCommentId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("我的商品评论ID为空");
		}
		
		if(StringUtil.isNull(token)){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		customerId = customer.getCustomer_id();
		
		try {
			//评论内容
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			cpd.put("currentId", customerId);
			CommentVo myCommentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(myCommentVo == null){
				return CallBackConstant.FAILED.callbackError("评论不存在");
			}
			
			ProductVo productVo = this.productManager.selectByPrimaryKey(myCommentVo.getProduct_id());
			pd.clear();
			pd.put("commentId", myCommentId);
			pd.put("customerId", customerId);
			messageManager.updateProductMessageReadStatus(pd);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("commentVo", myCommentVo);
			map.put("productVo", productVo);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/productcomment/productMessageDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
    }
	
	/**
	 * 消息页我的动态评论详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dynamicMessageCommentDetail")
	@ResponseBody
	public  Map<String,Object> dynamicMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		Integer articleId = pd.getInteger("articleId");
		if(articleId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("文章ID为空");
		}
		
		String token = pd.getString("token");
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		int customerId = 0;
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		customerId = customer.getCustomer_id();
		
		pd.put("status", 1);
		SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
		if(saVo == null){
			return CallBackConstant.DATA_NOT_FOUND.callbackError("文章信息不存在");
		}
		
		try {
			pd.clear();
			pd.put("articleId", articleId);
			pd.put("customerId", customerId);
			messageManager.updateDynamicMessageReadStatus(pd);
			return CallBackConstant.SUCCESS.callback(saVo);
		} catch (Exception e) {
			logger.error("/app/message/dynamicMessageCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dynamicMessageCommentReply")
	@ResponseBody
	public  Map<String,Object> dynamicMessageCommentReply(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		Integer articleId = pd.getInteger("articleId");
		if(articleId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("文章ID为空");
		}
		
		int customerId = customer.getCustomer_id();
		
		try {
			
			pd.put("status", 1);
			SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
			if(saVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("动态已下架或已被删除");
			}
			
			pd.put("commentPraiseType", 5);
			pd.put("currentId", customerId);
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("评论已下架或已被删除");
			}
			return CallBackConstant.SUCCESS.callback(commentVo);
		} catch (Exception e) {
			logger.error("/app/message/dynamicMessageCommentReply --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/articleMessageCommentDetail")
	@ResponseBody
	public  Map<String,Object> articleMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		Integer myCommentId = pd.getInteger("myCommentId");
		if(myCommentId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("我的评论ID为空");
		}
		
		Integer subMsgType = pd.getInteger("subMsgType");
		if(subMsgType == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("消息类型异常");
		}
		
		int customerId = customer.getCustomer_id();
		
		try {
			
			pd.put("commentPraiseType", 5);
			pd.put("currentId", customerId);
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("评论已下架或已被删除");
			}
			
			pd.put("status", 1);
			pd.put("articleId", commentVo.getSpecialId());
			SpecialArticleVo saVo = specialArticleManager.queryOne(pd);
			if(saVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("文章已下架或已被删除");
			}
			
			pd.clear();
			pd.put("commentId", myCommentId);
			pd.put("subMsgType", subMsgType);
			pd.put("customerId", customer.getCustomer_id());
			messageManager.updateArticleMessageReadStatus(pd);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saVo", saVo);
			map.put("commentVo", commentVo);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/message/articleMessageCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 消息页我的动态回复详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/videoMessageCommentDetail")
	@ResponseBody
	public  Map<String,Object> videoMessageCommentDetail(HttpServletRequest request,HttpServletResponse response,Model model) {
		PageData pd = this.getPageData();
		
		String token = pd.getString("token");
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		Integer myCommentId = pd.getInteger("myCommentId");
		if(myCommentId == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("我的评论ID为空");
		}
		
		Integer subMsgType = pd.getInteger("subMsgType");
		if(subMsgType == null){
			return CallBackConstant.PARAMETER_ERROR.callbackError("消息类型异常");
		}
		
		int customerId = customer.getCustomer_id();
		
		try {
			
			pd.put("commentPraiseType", 3);
			pd.put("currentId", customerId);
			SpecialCommentVo commentVo = this.specialCommentManager.queryCommentByCommId(pd);
			if(commentVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("评论已下架或已被删除");
			}
			
			pd.put("status", 1);
			pd.put("specialId", commentVo.getSpecialId());
			SpecialVo saVo = specialManager.queryOne(pd);
			if(saVo == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("文章已下架或已被删除");
			}
			
			pd.clear();
			pd.put("commentId", myCommentId);
			pd.put("subMsgType", subMsgType);
			pd.put("customerId", customer.getCustomer_id());
			messageManager.updateArticleMessageReadStatus(pd);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saVo", saVo);
			map.put("commentVo", commentVo);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/message/videoMessageCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	/**
	 * 使点赞消息已读
	 */                     
	@RequestMapping(value="/checkPraiseRead")
	@ResponseBody
	public Map<String,Object> checkPraiseRead(HttpServletRequest req, HttpServletResponse resp){
		PageData pd =this.getPageData();
		try {
			String token = pd.getString("token");
			if(StringUtil.isNull(pd.getString("token"))){
				return CallBackConstant.TOKEN_ERROR.callback();
			}
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
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
			logger.error("/app/message/checkPraiseRead, error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
}
