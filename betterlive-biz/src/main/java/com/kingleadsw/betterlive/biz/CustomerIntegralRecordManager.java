package com.kingleadsw.betterlive.biz;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;
import com.kingleadsw.betterlive.vo.CustomerIntegralRecordVo;


public interface CustomerIntegralRecordManager extends BaseManager<CustomerIntegralRecordVo,CustomerIntegralRecord>{

	/**
	 * 根据条件获取积分记录数
	 * @param cl
	 * @return
	 */
	int queryIntegralRecordCount(PageData cl);

	/**
	 * 一周签到情况
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 下午2:07:59
	 */
	List<CustomerIntegralRecordVo> queryWeekDailyBonus(PageData pd);
	
	
	/**
	 * 查出领金币列表
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 下午2:25:03
	 */
	List<CustomerIntegralRecordVo> queryAwardListPage(PageData pd);
	/**
	 * 组装七天签到数据
	 * @param
	 * @return
	 * @author zhangjing 2018年3月30日 下午6:32:21
	 */
	TreeMap<Integer, String> installWeekCheckData(PageData pd);
	
	
	/**
	 * 领取金币
	 * @param
	 * @return
	 * @author zhangjing 2018年4月2日 上午9:10:26
	 */
	Map<String, Object> getGoldAward(PageData pd);
	
	/**
	 * 根据条件统计积分
	 * @param pd
	 * @return
	 */
	BigDecimal queryIntegralNumByParams(PageData pd);
	
	
	
	/**
	 * 查询过期没领取金币的记录
	 * @param
	 * @return
	 * @author zhangjing 2018年4月3日 下午2:45:34
	 */
	List<CustomerIntegralRecordVo> queryOverdueList(PageData pd);
	
	/**
	 * 更新过期状态
	 * @param
	 * @return
	 * @author zhangjing 2018年4月3日 下午2:53:54
	 */
	Integer updateOverDueStatus(PageData pd);

	/**
	 * 检查当前用户是否有赞且是否获得金币
	 * @param customerId
	 * @return
	 */
	int checkArticleAndVideoPraise(Integer customerId);
	

}