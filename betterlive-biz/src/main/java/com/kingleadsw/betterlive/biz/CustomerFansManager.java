package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.CustomerFans;
import com.kingleadsw.betterlive.vo.CustomerFansVo;

public interface CustomerFansManager extends BaseManager<CustomerFansVo,CustomerFans>{

	/**
	 * 我的关注数和被关注数
	 * @param pd
	 * @return
	 */
	CustomerFansVo queryFansCount(PageData pd);

	/**
	 * 我的粉丝列表
	 * @param pd
	 * @return
	 */
	List<CustomerFansVo> queryMyFansListPage(PageData pd);
	
	/**
	 * 我的粉丝列表
	 * @param pd
	 * @return
	 */
	List<CustomerFansVo> queryMyConcernedListPage(PageData pd);

	/**
	 * TA的粉丝列表
	 * @param pd
	 * @return
	 */
	List<CustomerFansVo> queryOtherFansListPage(PageData pd);

	/**
	 * TA的关注列表
	 * @param pd
	 * @return
	 */
	List<CustomerFansVo> queryOtherConcernedListPage(PageData pd);

	/**
	 * 发现好友列表
	 * @param pd
	 * @return
	 */
	List<CustomerFansVo> queryFindFriendListPage(PageData pd);

}
