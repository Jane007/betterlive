package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Message;

public interface MessageService extends BaseService<Message>{

	int selectCountByUnread(PageData pd);

	Message selectByLastUnread(PageData pd);

	List<Message> queryCouponMsgListPage(PageData pd);

	List<Message> queryTransMsgListPage(PageData pd);
	
	/**
	 * 挥货活动分页
	 * @param pd
	 * @return
	 */
	List<Message> querySpecialMsgListPage(PageData pd);
	
	/**
	 * 挥货评价消息分页--旧版
	 * @param pd
	 * @return
	 */
	List<Message> queryCommentMsgListPage(PageData pd);

	Message selectByLast(PageData pd);

	/**
	 * 我的互动消息
	 * @param pd
	 * @return
	 */
	List<Message> queryCommentMsgByGroupSubTypeListPage(PageData pd);

	
	int updateProductMessageReadStatus(PageData pd);

	int updateDynamicMessageReadStatus(PageData pd);

	int updateArticleMessageReadStatus(PageData pd);

	List<Message> queryPraiseMsgByGroupSubTypeListPage(PageData pd);

	int updatePraiseMsgReadStatus(PageData pd);

}
