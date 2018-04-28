package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Message;
import com.kingleadsw.betterlive.service.MessageService;
import com.kingleadsw.betterlive.vo.MessageVo;

/**
 * 消息 实际交互实现层
 */
@Component
@Transactional
public class MessageManagerImpl extends BaseManagerImpl<MessageVo,Message> implements MessageManager{
	@Autowired
	private MessageService messageService;

	@Override
	protected BaseService<Message> getService() {
		return messageService;
	}

	@Override
	public int selectCountByUnread(PageData pd) {
		return messageService.selectCountByUnread(pd);
	}

	@Override
	public MessageVo selectByLastUnread(PageData pd) {
		return po2voer.transfer(new MessageVo(), messageService.selectByLastUnread(pd));
	}

	@Override
	public List<MessageVo> queryCouponMsgListPage(PageData pd) {
		return po2voer.transfer(MessageVo.class, messageService.queryCouponMsgListPage(pd));
	}

	@Override
	public List<MessageVo> queryTransMsgListPage(PageData pd) {
		
		return po2voer.transfer(MessageVo.class, messageService.queryTransMsgListPage(pd));
	}
	/**
	 * 挥货活动分页
	 */
	@Override
	public List<MessageVo> querySpecialMsgListPage(PageData pd) {
		
		return po2voer.transfer(MessageVo.class, messageService.querySpecialMsgListPage(pd));
	}
	
	/**
	 * 评价消息分页
	 */
	@Override
	public List<MessageVo> queryCommentMsgListPage(PageData pd) {
		return po2voer.transfer(MessageVo.class, messageService.queryCommentMsgListPage(pd));
	}

	@Override
	public MessageVo selectByLast(PageData pd) {
		return po2voer.transfer(new MessageVo(), messageService.selectByLast(pd));
	}

	@Override
	public List<MessageVo> queryCommentMsgByGroupSubTypeListPage(PageData pd) throws Exception {
		return po2voer.transfer(MessageVo.class, messageService.queryCommentMsgByGroupSubTypeListPage(pd));
	}

	@Override
	public int updateProductMessageReadStatus(PageData pd) {
		return messageService.updateProductMessageReadStatus(pd);
	}

	@Override
	public int updateDynamicMessageReadStatus(PageData pd) {
		return messageService.updateDynamicMessageReadStatus(pd);
	}

	@Override
	public int updateArticleMessageReadStatus(PageData pd) {
		return messageService.updateArticleMessageReadStatus(pd);
	}

	@Override
	public List<MessageVo> queryPraiseMsgByGroupSubTypeListPage(PageData pd) {
		return po2voer.transfer(MessageVo.class, messageService.queryPraiseMsgByGroupSubTypeListPage(pd));
	}

	@Override
	public int updatePraiseMsgReadStatus(PageData pd) {
		return messageService.updatePraiseMsgReadStatus(pd);
	}
	
}
