package com.kingleadsw.betterlive.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.MessageMapper;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.service.MessageService;



/**
 * 消息  service 实现层
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService{
	@Autowired
	private  MessageMapper messageMapper;

	/**
	 * @return
	 */
	@Override
	protected BaseMapper<Message> getBaseMapper() {
		return messageMapper;
	}

	@Override
	public int selectCountByUnread(PageData pd) {
		return messageMapper.selectCountByUnread(pd);
	}

	@Override
	public Message selectByLastUnread(PageData pd) {
		return messageMapper.selectByLastUnread(pd);
	}

	@Override
	public List<Message> queryCouponMsgListPage(PageData pd) {
		return messageMapper.queryCouponMsgListPage(pd);
	}

	@Override
	public List<Message> queryTransMsgListPage(PageData pd) {
		return messageMapper.queryTransMsgListPage(pd);
	}

	@Override
	public List<Message> querySpecialMsgListPage(PageData pd) {
		return messageMapper.querySpecialMsgListPage(pd);
	}

	@Override
	public List<Message> queryCommentMsgListPage(PageData pd) {
		return messageMapper.queryCommentMsgListPage(pd);
	}

	@Override
	public Message selectByLast(PageData pd) {
		return messageMapper.selectByLast(pd);
	}

	@Override
	public List<Message> queryCommentMsgByGroupSubTypeListPage(PageData pd) {
		List<Message> msgList = messageMapper.queryCommentMsgByGroupSubTypeListPage(pd);
		if(msgList == null){
			msgList = new ArrayList<Message>();
			return msgList;
		}
		
		PageData params = new PageData();
		params.put("customerId", pd.getInteger("customerId"));
		for (Message message : msgList) {
			int unreadCount = 0;
			int totalCount = 0;
			params.put("parentId", message.getParentId());
			params.put("subMsgType", message.getSubMsgType());
			if(message.getSubMsgType() == 9){	//商品评价消息数
				unreadCount = messageMapper.queryProductCommentByUnreadMsgCount(params);
				totalCount = messageMapper.queryProductCommentByMsgTotalCount(params);
			}else if(message.getSubMsgType() == 22){	//我的动态评论消息数
				unreadCount = messageMapper.queryDynamicCommentByUnreadMsgCount(params);
				totalCount = messageMapper.queryDynamicCommentByMsgTotalCount(params);
			}else{	//动态/精选/视频评论消息数
				unreadCount = messageMapper.queryArticleCommentByUnreadMsgCount(params);
				totalCount = messageMapper.queryArticleCommentByMsgTotalCount(params);
			}
			message.setTotalCount(totalCount);
			message.setUnreadCount(unreadCount);
		}
		return msgList;
	}

	@Override
	public int updateProductMessageReadStatus(PageData pd) {
		return messageMapper.updateProductMessageReadStatus(pd);
	}

	@Override
	public int updateDynamicMessageReadStatus(PageData pd) {
		return messageMapper.updateDynamicMessageReadStatus(pd);
	}

	@Override
	public int updateArticleMessageReadStatus(PageData pd) {
		return messageMapper.updateArticleMessageReadStatus(pd);
	}

	@Override
	public List<Message> queryPraiseMsgByGroupSubTypeListPage(PageData pd) {
		List<Message> msgList = messageMapper.queryPraiseMsgByGroupSubTypeListPage(pd);
		if(msgList == null){
			msgList = new ArrayList<Message>();
			return msgList;
		}
		
		PageData params = new PageData();
		params.put("customerId", pd.getInteger("customerId"));
		for (Message message : msgList) {
			int unreadCount = 0;
			int praiseType = 1;	//默认商品评论点赞
			if(message.getSubMsgType() == 16){
				praiseType = 4;
			}else if(message.getSubMsgType() == 17 || message.getSubMsgType() == 18){
				praiseType = 5;
			}else if(message.getSubMsgType() == 20){
				praiseType = 3;
			}
			params.put("praiseType", praiseType);
			
			params.put("objId", message.getParentId());
			params.put("subMsgType", message.getSubMsgType());
			unreadCount = messageMapper.queryPraiseByUnreadMsgCount(params);
			int totalCount = messageMapper.queryPraiseByMsgTotalCount(params);
			message.setTotalCount(totalCount);
			message.setUnreadCount(unreadCount);
		}
		return msgList;
	}

	@Override
	public int updatePraiseMsgReadStatus(PageData pd) {
		return messageMapper.updatePraiseMsgReadStatus(pd);
	}

}
