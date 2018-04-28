package com.kingleadsw.betterlive.util.jpush;

import java.util.Map;

import org.apache.log4j.Logger;




import com.kingleadsw.betterlive.consts.WebConstant;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushUtil {
   private static final Logger logger =  Logger.getLogger(JpushUtil.class);

    //ios应用的key
    private static final String iosAppKey = "0e14dc533e3a12c65b8cfd5d";
    //ios应用的Secret
    private static final String iosMasterSecret = "67af7f6e48dca05f16e36f84";
    
    //ios应用的key
    private static final String andriodAppKey = "55e6d81ff8abad1f8821d729";
    //ios应用的Secret
    private static final String andriodMasterSecret = "113c88d1a5ad6251fbb3fc46";
    
	private static String isMember = WebConstant.ISMEMBER;

	 /**
     * ios推送
     * @param alias 指定ID推送
     * @param content 推送内容
     * @param title 推送标题
     * @return 推送操作实列
     */
    public static PushPayload pushNotifitonByIos(String alias,String content,String title, Map<String,String> extra) {
    
    	if(!"true".equals(isMember)){	//测试环境
    		return PushPayload.newBuilder()
                    .setPlatform(Platform.ios()) 
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .incrBadge(1)
                                    .setBadge(0)
                                    .setSound("")
                                    .setAlert(content)
                                    .addExtras(extra).build())
                            .build())
                   	.setOptions(Options.newBuilder().setApnsProduction(false).build()) 
                            .build();
    	}else{	//生产环境
    		return PushPayload.newBuilder()
                    .setPlatform(Platform.ios()) 
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .incrBadge(1)
                                    .setBadge(0)
                                    .setSound("")
                                    .setAlert(content)
                                    .addExtras(extra).build())
                            .build())
                   	.setOptions(Options.newBuilder().setApnsProduction(true).build()) 
                            .build();
    	}
    	
    }
    
    /**
     * andriod推送
     * @param alias 指定ID推送
     * @param content 推送内容
     * @param title 推送标题
     * @return 推送操作实列
     */
    public static PushPayload pushNotifitonByAndroid(String alias,String content,String title, Map<String,String> extra) {
    	if(!"true".equals(isMember)){	//测试环境
	    	return PushPayload.newBuilder()
	                .setPlatform(Platform.android()) 
	                .setAudience(Audience.tag("debug"))
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                                  .setTitle(title)
	                                  .setAlert(content)
	                                  .addExtras(extra).build())
	                        .build()).build();
    	}else{ //生产环境
    		return PushPayload.newBuilder()
                    .setPlatform(Platform.android()) 
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                      .setTitle(title)
                                      .setAlert(content)
                                      .addExtras(extra).build())
                            .build()).build();
    	}
    }

    /**
     * 推送信息 ios和android 设置的极光推送 别名(最好唯一)
     * @param alias 指定用户推送
     * @param title 标题
     * @param content  内容
     * @param extra 参数
     * @return 推送结果 
     */
    public static final boolean push(String alias, String title, String content, Map<String,String> extra){
		try {
			
			//ios推送
			JPushClient jpushClient1 = new JPushClient(iosMasterSecret, iosAppKey);
			PushPayload payload1 = pushNotifitonByIos(alias, content, title, extra);
			jpushClient1.sendPush(payload1);
			
			//android推送
			JPushClient jpushClient2 = new JPushClient(andriodMasterSecret, andriodAppKey);
			PushPayload payload2 = pushNotifitonByAndroid(alias, content, title, extra);
			jpushClient2.sendPush(payload2);
		} catch (Exception e) {
			logger.error("JpushUtil --error", e);
		} 
		return true;
    }

	    
}
