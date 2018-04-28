package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.DeliverArea;
import com.kingleadsw.betterlive.vo.DeliverAreaVo;



/**
 * 商品配送地区   实际交换层
 * 2017-04-17 by chen
 */
public interface DeliverAreaManager extends BaseManager<DeliverAreaVo,DeliverArea>{
	/**
	 *  //批量新增商品配送地区
	 * @param list
	 * @return
	 */
	int addBatchDeliverArea(List<DeliverAreaVo> list);                   
	
	/**
	 * //修改商品配送地区
	 * @param list
	 * @return
	 */
	int updateDeliverAreaByDid(DeliverAreaVo deliverAreaVo);            	    
	
	/**
	 * //删除商品配送地区
	 * @param pId
	 * @return
	 */
	int deleteDeliverAreaByPid(String pId);              		  		 
	
//	DeliverArea queryDeliverAreaByDid(PageData pagedata);               //查询单个商品规格
	
	/**
	 * //根据条件查询商品全部配送地区
	 * @param pagedata
	 * @return
	 */
	List<DeliverAreaVo> queryListDeliverArea(PageData pagedata);     		 
	
}