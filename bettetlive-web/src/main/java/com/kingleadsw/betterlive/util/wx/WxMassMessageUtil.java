package com.kingleadsw.betterlive.util.wx;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.common.util.HttpClientTool;


/**
 * 微信群发消息给公众号
 * @author Administrator
 *
 */
public class WxMassMessageUtil {
	
	private static Logger log = Logger.getLogger(WxMassMessageUtil.class);

	 /**
     * 主动推送信息接口(群发)
     */
    private String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token={0}";
    
    /**
     * @desc 推送信息
     * @param token
     * @param msg
     * @return
     */
    public String sendMessage(String token, String msg){
        try{
            log.info("\n\nsendMessage start.token:"+token+",msg:"+msg);
            String url = MessageFormat.format(this.sendMsgUrl, token);

            String response = (String) HttpClientTool.post(url, msg, "utf-8");
            return response;

        }catch (Exception e) {
            log.error("get user info exception", e);
            return null;
        }
    }

}
