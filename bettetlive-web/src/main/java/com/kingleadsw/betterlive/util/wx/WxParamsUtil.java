package com.kingleadsw.betterlive.util.wx;



import java.util.HashMap;
import java.util.Map;

public class WxParamsUtil
{
    /**
     * 获取用户参数
     * @param state
     * @return
     */
    public static Map<String,String> getParams(String state){
        String[] array=state.split(",");
        int cnt=array.length;
        Map<String,String> params=new HashMap<String,String>();
        for(int i=0 ;i<cnt;i++){
            String str=array[i];
            String[] ss=str.split("=");
            String key=ss[0];
            String value=ss[1];
            params.put(key, value);
        }
        return params;
    }
}
