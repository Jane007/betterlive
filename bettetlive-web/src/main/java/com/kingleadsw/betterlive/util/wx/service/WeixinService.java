package com.kingleadsw.betterlive.util.wx.service;



import com.kingleadsw.betterlive.core.page.ResultBean;
import com.kingleadsw.betterlive.util.wx.bean.ScanMessage;
import com.kingleadsw.betterlive.util.wx.bean.SubscribeMessage;
import com.kingleadsw.betterlive.util.wx.bean.TextMessage;

import net.sf.json.JSONObject;


public interface WeixinService {

	/**
     * 获取公众账号的access_token</br>
     * 1.首先从redis缓存中获取key为access_token的字符串token值</br>
     * 2.如果没有获取到，调用微信的接口获取，并将access_token缓存
     * @return access_token
     */
    String getAccessToken();
    
    /**
     * 刷新公众账号的access_token，限200（次/天）   </br>
     * 主要用户调用getAccessToken后，本系统判定token有效，但调用微信接口后，token失效，需要重新获取
     * @return access_token
     */
    String refreshAccessToken();
    
    /**
     * 获取jsapi_ticket
     * @return jsapi_ticket
     */
    String getJsApiTicket();
    
    /**
     * 用户关注、取消关注时，对用户信息进行更新
     * @param subMessage  关注事件推送时，消息推送对象
     * @return
     */
    ResultBean updateSubscribeUser(SubscribeMessage subMessage) throws Exception;
    
    /**
     * 发送消息给微信用户
     * @param content
     * @param openId
     * @return
     */
    JSONObject sendMessageToCustomer(String content,String openId);

    /**
     * 处理扫码事件
     * @param scanMessage
     * @return
     */
	ResultBean processScanEvent(ScanMessage scanMessage);

	/**
	 * 处理文本消息
	 * @param textMessage
	 * @return
	 */
	ResultBean processTextMsg(TextMessage textMessage);

}
