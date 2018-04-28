package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.DeliverArea;


/**
 * 商品配送地区   dao层
 * 2017-04-17 by chen
 */
public interface DeliverAreaMapper extends BaseMapper<DeliverArea> {
	/**
	 *  //批量新增商品配送地区
	 * @param list
	 * @return
	 */
	int addBatchDeliverArea(List<DeliverArea> list);                   
	
	/**
	 * //修改商品配送地区
	 * @param list
	 * @return
	 */
	int updateDeliverAreaByDid(DeliverArea deliverArea);            	    
	
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
	List<DeliverArea> queryListDeliverArea(PageData pagedata);     		 
	
	/**
	 * 根据商品id，地区编码查询商品的配送信息
	 * @param pd productId：商品id，areaCode：地区编码
	 * @return 商品配送信息对象
	 */
	DeliverArea queryDeliverByAreaCode(PageData pd);
	
}
