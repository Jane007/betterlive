package com.kingleadsw.betterlive.controller.web.deliverarea;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.AreaManager;
import com.kingleadsw.betterlive.biz.DeliverAreaManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.AreaVo;

/**
 * 配送地址
 * 2017-04-17 by chen
 */
@Controller
@RequestMapping("/admin/deliverarea")
public class DeliverAreaController  extends AbstractWebController{
	protected Logger logger = Logger.getLogger(DeliverAreaController.class);
	
	@Autowired
	private ProductManager productManager;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private RedisService redisService;
	@Autowired
	private DeliverAreaManager deliverAreaManage;
	
	/**
	 * 获取配送地址 -- 树形图
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/getTree")
	@ResponseBody
	public void getTree(HttpServletRequest request,HttpServletResponse response) {
		boolean bool=false;
		String productId=request.getParameter("productId");    //修改商品时用
		
		if(null!=productId && !"".equals(productId)){
			bool=true;
		}
		
		String deliverTree=redisService.getString("delivertree");
		if(!bool&& null!=deliverTree && !"".equals(deliverTree)){
			outJson(deliverTree, response);
		}else{
			PageData pd=new PageData();
			pd.put("parentId", 0);
			pd.put("notAreaIds", "710000,810000,820000");   //除去港澳台
			List<AreaVo> list=areaManager.findAllAreaInfo(pd);
			
			int count=0;
			List<AreaVo> listArea=null;
			List<AreaVo> listCountry=null;
			if(bool){
				listArea=areaManager.queryCityByPid(productId);
				
				if(null==listArea){
					bool=false;
				}
			}
			
			//循环省份，读取配送地址，拼接为指定格式的json数据
			StringBuffer sb=new StringBuffer();
			List<AreaVo> listCity=null;
			
			if(null!=list && list.size()>0){
				sb.append("[");
				
				sb.append("{\"id\":1,");
				sb.append("\"text\":\"全部\",");
				sb.append("\"state\":\"open\",");
				sb.append("\"checked\":false,"); 
				sb.append("\"children\":[");
				
				
				for (int i = 0; i < list.size(); i++) {
					sb.append("{\"id\":"+list.get(i).getAreaId()+",");
					sb.append("\"text\":\""+list.get(i).getAreaName()+"\",");
					sb.append("\"state\":\"closed\",");
					sb.append("\"checked\":false,");
		//			sb.append("\"checked\":false");
					
					sb.append("\"children\":[");  
					
					pd.clear();
					pd.put("parentId", list.get(i).getAreaId());
					listCity=areaManager.findAllAreaInfo(pd);
					
					if(null !=listCity && listCity.size()>0){
						for (int j = 0; j <listCity.size(); j++) {
							sb.append("{\"id\":"+ listCity.get(j).getAreaId()+",");
							sb.append("\"text\":\""+ listCity.get(j).getAreaName()+"\","); 
							sb.append("\"state\":\"closed\",");
				//			sb.append("\"checked\":false");
							
							
							boolean b=false;
							//若果存在某个城市中的某些镇区不配送,就查询该城市下的面镇区
							if(bool){
								for (int k= 0; k < listArea.size(); k++) {
									if(listCity.get(j).getAreaId()==Integer.parseInt(listArea.get(k).getParentId())){
										b=true;
										count++;
										sb.append("\"checked\":false,");
										
										pd.clear();
										pd.put("parentId", listCity.get(j).getAreaId());
										listCountry=areaManager.findAllAreaInfo(pd);      //查询下面的镇区
										if(null!=listCountry && listCountry.size()>0){
											
											sb.append("\"children\":[");
											
											for (int n = 0; n < listCountry.size(); n++) {
												sb.append("{\"id\":"+ listCountry.get(n).getAreaId()+",");
												sb.append("\"text\":\""+ listCountry.get(n).getAreaName()+"\","); 
												sb.append("\"state\":\"closed\",");
												sb.append("\"checked\":false");
												
												if(n+1==listCountry.size()){
													sb.append("}");
												}else{
													sb.append("},");
												}	
											}
											
											sb.append("]");
										}
										
										if(count==listArea.size()){
											bool=false;
										}
										
										break;	
									}
								}
								
								if(!b){
									sb.append("\"checked\":false");
								}
							}else{
								sb.append("\"checked\":false");
							}
							
							
							if(j+1==listCity.size()){
								sb.append("}");
							}else{
								sb.append("},");
							}	
						}	
						
					    sb.append("]");
					}
					
					if(i+1==list.size()){
						sb.append("}");
					}else{
						sb.append("},");
					}
				}
				sb.append("]");
				
				sb.append("}]");
				
				//把查询出的数据放进缓存
				redisService.setString("delivertree", sb.toString());
		
				outJson(sb.toString(), response);
			}
		}
	}
		
	/**
	 * 根据城市ID查询对应的区(镇)
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryTreeAreaJson")
	@ResponseBody
	public void queryTreeAreaJson(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		
		//根据城市ID缓存 json格式的地址
		String redisCity=pd.getString("parentId"); 
		String key="delivertree_"+redisCity;
		
		String deliverTree=redisService.getString(key);
		
		if(null!=deliverTree && !"".equals(deliverTree)){
			outJson(deliverTree, response);
		}else{
			StringBuffer sb=new StringBuffer();
			
			List<AreaVo> listArea=areaManager.findAllAreaInfo(pd);
			
			if(null !=listArea && listArea.size()>0){
				//	sb.append("\"children\":[");
				
				sb.append("[");
				
				for (int k = 0; k < listArea.size(); k++) {
					
					sb.append("{\"id\":"+ listArea.get(k).getAreaId()+",");
					sb.append("\"text\":\""+ listArea.get(k).getAreaName()+"\",");
					sb.append("\"attributes\":{\"code\":"+listArea.get(k).getAreaId()+"}");
					
					if(k+1==listArea.size()){
						sb.append("}");
					}else{
						sb.append("},");
					}
				}
				
				sb.append("]");
			}
			
			redisService.setString(key,sb.toString());
			outJson(sb.toString(), response);
		}
	}
	

}
