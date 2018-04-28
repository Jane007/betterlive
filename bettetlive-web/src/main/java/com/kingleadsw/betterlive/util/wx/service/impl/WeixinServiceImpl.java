package com.kingleadsw.betterlive.util.wx.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.bean.MessageHandler;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.consts.SysConstants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.ResultBean;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.util.HttpClientTool;
import com.kingleadsw.betterlive.util.JsonUtil;
import com.kingleadsw.betterlive.util.wx.MessageUtil;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.util.wx.bean.ScanMessage;
import com.kingleadsw.betterlive.util.wx.bean.SubscribeMessage;
import com.kingleadsw.betterlive.util.wx.bean.TextMessage;
import com.kingleadsw.betterlive.util.wx.service.WeixinService;
import com.kingleadsw.betterlive.vo.CustomerVo;


@Service(value = "weixinService")
public class WeixinServiceImpl implements WeixinService {
	
private final static Logger logger = Logger.getLogger(WeixinServiceImpl.class);
    
	@Autowired
    private RedisService redisService;
    
    //获取jsapi_ticket地址
    private static final String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%1$s&type=jsapi";
    
    /**
     * 公众账号access_token过期时间，7200秒</br>
     * 这里设置7000，让系统中的先过期，避免在同一时间点上系统的有效，但微信无效的问题
     */
    private static int expires_in = 7000;
    
    @Autowired
    private CustomerManager  customerManager;

	/**
     * 获取公众账号的access_token</br>
     * 1.首先从redis缓存中获取key为access_token的字符串token值</br>
     * 2.如果没有获取到，调用微信的接口获取，并将access_token缓存
     * @return access_token
     */
    @Override
    public String getAccessToken() {   
    	//先从缓存中获取access_token
    	String access_token = redisService.getString("access_token");
    	logger.info("当前缓存的access_token为：  "+access_token);
    	if (StringUtils.isNotBlank(access_token)) {  //缓存存在，直接返回
    		return access_token;
    	}
    	
        String url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=").append(WebConstant.WX_APPID).append("&secret=")
        		.append(WebConstant.WX_SECRET).toString();
        JSONObject jsonObj = HttpClientTool.httpsRequest(url, "GET", null);
        if(jsonObj.containsKey("access_token")){
        	access_token = jsonObj.getString("access_token");
	        if (StringUtil.isNoNull(jsonObj) && StringUtil.isNotNull(access_token)) {
	        	redisService.setex("access_token", access_token, expires_in);
	        } else {
	        	logger.info("获取公众账号access_token失败，响应为空");
	        }
	        logger.info("当前使用的access_token为：  "+access_token);
        }
        return access_token;
    }
    
    /**
     * 刷新公众账号的access_token，限200（次/天）   </br>
     * 主要用户调用getAccessToken后，本系统判定token有效，但调用微信接口后，token失效，需要重新获取
     * @return access_token
     */
    @Override
    public String refreshAccessToken() {
    	logger.info("获取公众账号access_token开始");
    	//先从缓存中获取access_token
        String url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=").
        		append(WebConstant.WX_APPID).append("&secret=").append(WebConstant.WX_SECRET).toString();

        JSONObject jsonObj = HttpClientTool.httpsRequest(url, "GET", null);
        String access_token = jsonObj.getString("access_token");
        if (StringUtil.isNotNull(access_token)) {
        	redisService.setex("access_token", access_token, expires_in);
        } else {
        	logger.info("获取公众账号access_token失败，响应为空");
        }
        
        logger.info("获取公众账号access_token结束");
        return access_token;
    }

	
	/**
     * 获取jsapi_ticket
     * @return jsapi_ticket
     */
    @Override
    public String getJsApiTicket() {
    	logger.info("获取jsapi_ticket开始");
    	String jsApiTicket = redisService.getString("jsapi_ticket");
    	if (StringUtil.isNotNull(jsApiTicket)) {
    		return jsApiTicket;
    	}
    	
    	String access_token = getAccessToken();
    	String ticket_url = String.format(jsapi_ticket_url, access_token);
    	JSONObject jsonObject = HttpClientTool.httpsRequest(ticket_url, "GET", null);
    	if (jsonObject.get("errcode") != null && !"0".equals(jsonObject.getString("errcode"))) {
    		logger.error("获取jsapi_ticket失败，errorMsg：" + jsonObject);
    		return null;
    	}
    	 
    	jsApiTicket = jsonObject.getString("ticket");
    	if (StringUtil.isNotNull(jsApiTicket)) {
    		redisService.setex("jsapi_ticket", jsApiTicket, 7000);
    	} else {
    		//不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
    		if ("40014".equals(jsonObject.getString("errcode"))) {
    			//return getJsApiTicket();
    		}
    		logger.error("获取jsapi_ticket异常");
    		logger.error("errcode：" + jsonObject.getString("errcode"));
    		logger.error("errmsg：" + jsonObject.getString("errmsg"));
    	}
    	
    	logger.info("获取jsapi_ticket结束，tickt:"+jsApiTicket);
    	return jsApiTicket;
    }

	@Override
	public ResultBean updateSubscribeUser(SubscribeMessage subMessage) throws Exception {
		ResultBean result = ResultBean.getResultBean("success", "关注处理成功");
		switch (subMessage.getEvent()) {
		case MessageHandler.EVENT_SUBSCRIBE:  //关注
			//createMember(subMessage);
			result.setRespData(getLastOperate(subMessage));
			//标示用户是新关注的，需要更新用户session信息
			redisService.setex(subMessage.getFromUserName() +"_subscribe", "subscribe", 3600);
			break;
		case MessageHandler.EVENT_UNSUBSCRIBE:  //取消关注
			PageData editData = new PageData();
			editData.put("openid", subMessage.getFromUserName());
			editData.put("subscribe", SysConstants.SUBSCRIBE_NO);  //取消关注
			//标示用户是取消关注，需要移除用户session信息
			redisService.setex(subMessage.getFromUserName() +"_subscribe", "unsubscribe", 3600);
			//customerManager.updateSubscribe(editData);
			break;
		default:
			break;
		}
		
		return result;
	}
	
	/**
	 * 用户关注公众号时，保存或更新用户信息
	 * @param message
	 * @throws Exception
	 */
	private void createMember(SubscribeMessage message) throws Exception {
		String accessToken = this.getAccessToken();
		String openid = message.getFromUserName();
		JSONObject userInfo = WxUtil.getUserInfo(openid, accessToken);
		if (userInfo != null) {
			logger.info(userInfo);
			logger.info("创建或更新微信用户：" + userInfo.get("nickname"));

			try {
				PageData queryData = new PageData();
				queryData.put("openid", openid);
				CustomerVo customer = customerManager.queryOne(queryData);
				if (customer != null) {
					PageData editData = new PageData();
					editData.put("openid", openid);
					editData.put("customerId", customer.getCustomer_id());
					editData.put("nickname", userInfo.get("nickname"));
					editData.put("headUrl", userInfo.get("headimgurl"));
					editData.put("subscribe", SysConstants.SUBSCRIBE_YES);
					customerManager.updateByPrimaryKey(customer);
				} else {
					if(userInfo.get("unionid") != null){
                		PageData pd = new PageData();
                    	String unionid = userInfo.getString("unionid");
        				pd.put("unionid", unionid);
        				try {
        					customer = customerManager.queryOne(pd);
        				} catch (Exception e1) {
        					logger.error(e1, e1);
        				}
                	}
    				if (customer == null) {
    					customer = new CustomerVo();
	                	customer.setOpenid(openid);
	                	customer.setSubscribe(SysConstants.SUBSCRIBE_YES);
	             //   	customer.setCtime(new Date());
	                	if(userInfo.get("unionid") != null){
	                		customer.setUnionid(userInfo.getString("unionid"));
	                	}
	                	if(userInfo.get("nickname") != null){ //未关注公众号的静默授权不能获取用户基本信息，先判断，否则出错
	                		customer.setNickname(userInfo.getString("nickname"));
		                	customer.setHead_url(userInfo.getString("headimgurl"));
	                	}else{
	                		customer.setNickname("游客");
	                		customer.setHead_url(WebConstant.MAIN_SERVER+"/resources/images/front/mut01.png");
	                	}
                		customerManager.insert(customer);
    				} else {
                    	if (userInfo.get("nickname") != null) {
                    		customer.setNickname(userInfo.getString("nickname"));
	                	}
                    	customer.setHead_url(userInfo.get("headimgurl") == null ? 
                    			WebConstant.MAIN_SERVER+"/resources/images/front/mut01.png" : userInfo.getString("headimgurl"));
                    	customer.setSubscribe(SysConstants.SUBSCRIBE_YES);
                    	customerManager.updateByPrimaryKeySelective(customer);
    				}
				}
			} catch (Exception e) {
				logger.error(e, e);
			}
		} else {
			throw new Exception("调用微信接口获取用户信息失败");
		}
	}
	
	private String getLastOperate(SubscribeMessage subMessage) {
		String url = redisService.getString("last_request_"+subMessage.getFromUserName());
		String content = redisService.getString("last_request_text_"+subMessage.getFromUserName()); 
		if (StringUtil.isNull(content)) {
			content = "恭喜你发现了零食百宝袋小挥挥\n"
					+ "赶巧，小挥挥1周年生日，各种零食折扣打的飞起~\n"
					+ "什么？光吃零食不够？打开抖音，立即参与挑战#挥货演技食力派#\n"
					+ "日常皮一下，很开熏！\n"
					+ "若有疑惑请致电小挥挥:4001869797 / 0755-82986161 / 0755-82986162；或添加小挥挥微信:huihuokefu / huihuokefu123";
		}
		String msg = "";
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(subMessage.getToUserName());
		textMessage.setToUserName(subMessage.getFromUserName());
		textMessage.setCreateTime(DateUtil.getCurrentTime());
		textMessage.setMsgType("text");
		logger.info("openid: "+subMessage.getFromUserName()+"，用户最后操作的连接： " + url);
		
		textMessage.setContent(content);
		msg = MessageUtil.textMessageToXml(textMessage);
		
		redisService.delKey("last_request_"+subMessage.getFromUserName());
		redisService.delKey("last_request_text_"+subMessage.getFromUserName());
		return msg;
	}
	
	@Override
	public JSONObject sendMessageToCustomer(String content,String openId){
		String accessToken = getAccessToken();
		JSONObject data = new JSONObject();
		data.put("touser", openId);
		data.put("msgtype", "text");
		JSONObject text = new JSONObject();
		text.put("content", content);
		data.put("text", text);
		String dataStr = JsonUtil.toJsonString(data);
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
		return HttpClientTool.httpsRequest(url, "POST", dataStr);
	}
	
	@Override
	public ResultBean processTextMsg(TextMessage textMessage){
		ResultBean result = ResultBean.getResultBean("success", "文本事件成功");
		result.setRespData("success");
		//用户消息
		String msg = textMessage.getContent();
		//回复的内容
		String content = "";
		if(msg.equals("8888")){
			content = "<a href=\"http://webprint.lomoment.com/?mid=GopcINTqKRpRJkDb\">点击上传照片</a>";
		} else {
			
		}
		//用户openId
		String openId = textMessage.getFromUserName();
		this.sendMessageToCustomer(content, openId);
		return result;
	}
	
	@Override
	public ResultBean processScanEvent(ScanMessage scanMessage){
		ResultBean result = ResultBean.getResultBean("success", "处理扫码事件成功");
		result.setRespData("SUCCESS");
		
		return result;
	}

}

