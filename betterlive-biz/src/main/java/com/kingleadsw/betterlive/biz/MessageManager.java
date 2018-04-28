package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.vo.MessageVo;


/**
 * 消息 实际交互层
 */
public interface MessageManager extends BaseManager<MessageVo,Message>{

	/**
	 * 用户未读消息
	 * @param pd
	 * @return
	 */
	int selectCountByUnread(PageData pd);
	
	/**
	 * 获取用户最新未读消息（一条）
	 * @param pd
	 * @return
	 */
	MessageVo selectByLastUnread(PageData pd);

	/**
	 * 交易成功分页
	 * @param pd
	 * @return
	 */
	List<MessageVo> queryTransMsgListPage(PageData pd);

	/**
	 * 分页
	 * @param pd
	 * @return
	 */
	List<MessageVo> queryCouponMsgListPage(PageData pd);
	
	/**
	 * 挥货活动分页
	 * @param pd
	 * @return
	 */
	List<MessageVo> querySpecialMsgListPage(PageData pd);
	
	/**
	 * 挥货评价消息分页--旧版
	 * @param pd
	 * @return
	 */
	List<MessageVo> queryCommentMsgListPage(PageData pd);

	MessageVo selectByLast(PageData pd);

	/**
	 * 我的互动评论消息列表
	 * @param pd
	 * @return
	 */
	List<MessageVo> queryCommentMsgByGroupSubTypeListPage(PageData pd) throws Exception;

	/**
	 * 更新商品评论消息读取状态
	 * @param pd
	 * @return
	 */
	int updateProductMessageReadStatus(PageData pd);

	/**
	 * 更新我的动态被评论消息读取状态
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
	 * 我的点赞未读消息
	 * @param pd
	 * @return
	 */
	List<MessageVo> queryPraiseMsgByGroupSubTypeListPage(PageData pd);

	/**
	 * 点赞消息状态
	 * @param pd
	 * @return
	 */
	int updatePraiseMsgReadStatus(PageData pd);


}
