package com.kingleadsw.betterlive.dao;


import java.math.BigDecimal;
import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;



/**
 * 管理员 dao层
 * 2017-03-07 by chen
 */
public interface CustomerIntegralRecordMapper extends BaseMapper<CustomerIntegralRecord>{

	int queryIntegralRecordCount(PageData cl);
	
	/**
	 * 一周签到情况
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 下午2:07:59
	 */
	List<CustomerIntegralRecord> queryWeekDailyBonus(PageData pd);
	
	
	/**
	 * 查出领金币列表
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 下午2:25:03
	 */
	List<CustomerIntegralRecord> queryAwardListPage(PageData pd);

	BigDecimal queryIntegralNumByParams(PageData pd);
	
	/**
	 * 查询过期没领取金币的记录
	 * @param
	 * @return
	 * @author zhangjing 2018年4月3日 下午2:43:08
	 */
	List<CustomerIntegralRecord> queryOverdueList(PageData pd);

	/**
	 * 批量新增
	 * @param recordList
	 * @return
	 */
	int batchSave(List<CustomerIntegralRecord> recordList);

	/**
	 * 查询当前分享内容的点赞数收益
	 * @param pd
	 * @return
	 */
	List<CustomerIntegralRecord> queryCustSharePraiseList(PageData pd);
	

}