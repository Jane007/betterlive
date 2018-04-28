package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Message;


/**
 * 消息  dao层
 */
public interface MessageMapper extends BaseMapper<Message>{

	int selectCountByUnread(PageData pd);

	Message selectByLastUnread(PageData pd);

	List<Message> queryCouponMsgListPage(PageData pd);

	List<Message> queryTransMsgListPage(PageData pd);

	List<Message> querySpecialMsgListPage(PageData pd);

	List<Message> queryCommentMsgListPage(PageData pd);

	Message selectByLast(PageData pd);

	/**
	 * 查询消息子类列表
	 * @param pd
	 * @return
	 */
	List<Message> queryCommentMsgByGroupSubTypeListPage(PageData pd);

	/**
	 * 查看我的商品评论未读回复消息数
	 * @param pd
	 * @return
	 */
	int queryProductCommentByUnreadMsgCount(PageData pd);

	/**
	 * 查看我的动态未读评论消息数
	 * @param pd
	 * @return
	 */
	int queryDynamicCommentByUnreadMsgCount(PageData pd);

	/**
	 * 查看我的精选/视频未读评论消息数
	 * @param pd
	 * @return
	 */
	int queryArticleCommentByUnreadMsgCount(PageData params);

	/**
	 * 修改未读状态
	 * @param pd
	 * @return
	 */
	int updateProductMessageReadStatus(PageData pd);

	/**
	 * 查看我的商品评论人数
	 * @param params
	 * @return
	 */
	int queryProductCommentByMsgTotalCount(PageData params);

	/**
	 * 查看我的动态被评论数
	 * @param params
	 * @return
	 */
	int queryDynamicCommentByMsgTotalCount(PageData params);

	/**
	 * 查看我的动态/文章/视频评论消息
	 * @param params
	 * @return
	 */
	int queryArticleCommentByMsgTotalCount(PageData params);

	/**
	 * 更新我的动态被评论消息状态
	 * @param pd
	 * @return
	 */
	int updateDynamicMessageReadStatus(PageData pd);

	/**
	 * 更新我的动态/文章/视频消息读取状态 
	 * @param pd
	 * @return
	 */
	int updateArticleMessageReadStatus(PageData pd);

	/**
	 * 我的点赞未读消息列表
	 * @param pd
	 * @return
	 */
	List<Message> queryPraiseMsgByGroupSubTypeListPage(PageData pd);

	/**
	 * 点赞未读消息数
	 * @param params
	 * @return
	 */
	int queryPraiseByUnreadMsgCount(PageData params);

	/**
	 * 查看点赞人数
	 * @param params
	 * @return
	 */
	int queryPraiseByMsgTotalCount(PageData params);

	/**
	 * 点赞消息状态
	 * @param pd
	 * @return
	 */
	int updatePraiseMsgReadStatus(PageData pd);

}