package com.kingleadsw.betterlive.util.kuaidi.demo;

import java.util.ArrayList;
import java.util.HashMap;






import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kingleadsw.betterlive.util.kuaidi.util.MD5;
import com.kingleadsw.betterlive.vo.LogisticsVo;


public class Kuaidi100Util {
	private static Logger logger = Logger.getLogger(Kuaidi100Util.class);
	
	/**
	 * k
	 * @param com 快递公司编号
	 * @param num 快递单号
	 * @return
	 */
	public static Map<String,Object> queryKuaidiInfo(String com,String num){
		
		
		Map<String,Object> map=new HashMap<String, Object>();
		JSONObject jsonObject=null;
		String param ="{\"com\":\""+com+"\",\"num\":\""+num+"\"}";
		String  key="SzSVTWXw7370";
		String  customer= "F2C1E52A427EEFD4C055861283263585";
		String sign = MD5.encode(param+key+customer);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param",param);
		params.put("sign",sign);
		params.put("customer",customer);
		String resp;
		try {
			resp = new HttpRequest().postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8").toString();
			jsonObject=new JSONObject(resp);
			if(jsonObject.has("message")){
				
				if(jsonObject.getString("message").equals("ok")){
					map.put("code", 1010);
					map.put("msg", "查询成功");
					
					if(jsonObject.has("num")){
						map.put("num",jsonObject.getString("nu"));
					}
					if(jsonObject.has("state")){
						map.put("state",jsonObject.getString("state"));
					}
					
					if(jsonObject.has("data")){
						//map.put("state",jsonObject.getString("state"));
						JSONArray array=jsonObject.getJSONArray("data");
						List<LogisticsVo> list=new ArrayList<LogisticsVo>();
						if(array!=null&&array.length()>0){
							for(int i=0;i<array.length();i++){
								JSONObject json2=array.getJSONObject(i);
								LogisticsVo vo=new LogisticsVo();
								vo.setFtime(json2.getString("ftime"));
								vo.setTime(json2.getString("time"));
								vo.setContext(json2.getString("context"));
								list.add(vo);
							}
						}
						map.put("data", list);
					}
					
					
					//map.put("data", jsonObject.getString("state"));
					
				}else{
					map.put("code", 1020);
					map.put("msg", jsonObject.getString("message"));
				}
				//map.put("", value)
			}
		} catch (Exception e) {
			logger.error("Kuaidi100Util/queryKuaidiInfo --error", e);
		}
		return map;
	}
	
	
}
