package com.kingleadsw.betterlive.core.enums;

import com.kingleadsw.betterlive.core.util.StringUtil;

/**
 * Created by ptz on 2017-02-14.
 * 验证码模板
 */
public enum SmsTempleEnums {
    BINDMOBILE("绑定手机号","bindmobile","您正在使用挥货平台进行短信认证，您的验证码是:{}，请在 5分钟内完成验证。"),
    REGISTER("用户注册","register","您正在使用挥货平台进行短信认证，您的验证码是:{}，请在 5分钟内完成验证。"),
    FORGETPWD("忘记密码","forgetpwd","您正在使用挥货平台进行短信认证，您的验证码是:{}，请在 5分钟内完成验证。"),
    SETPAYPWD("设置支付密码","setpaypwd","您正在使用挥货平台进行短信认证，您的验证码是:{}，请在 5分钟内完成验证。"),
    DEFAULT("默认模板","default","您的验证码为：{},请务必保密");
    
    private SmsTempleEnums(String name, String verfiType,String msgTemplate){
        this.name = name;
        this.verfiType = verfiType;
        this.msgTemplate = msgTemplate;
    }


    private String msgTemplate;
    private String name;
    private String verfiType;


    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerfiType() {
        return verfiType;
    }

    public void setVerfiType(String verfiType) {
        this.verfiType = verfiType;
    }

    public SmsTempleEnums getTemp(String verfiType){
        if(StringUtil.isNull(verfiType)){
            return DEFAULT;
        }
        SmsTempleEnums smsTempleEnums[] = SmsTempleEnums.values();
        for(SmsTempleEnums smsTempleEnum : smsTempleEnums){
            if(smsTempleEnum.getVerfiType().equals(verfiType)){
                return smsTempleEnum;
            }
        }
        return DEFAULT;
    }
}
