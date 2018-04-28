package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.NoticeManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Notice;
import com.kingleadsw.betterlive.service.NoticeService;
import com.kingleadsw.betterlive.vo.NoticeVo;

/**
 * 活动 实际交互实现层
 * 2017-03-15 by chen
 */
@Component
@Transactional
public class NoticeManagerImpl extends BaseManagerImpl<NoticeVo,Notice> implements NoticeManager{
	@Autowired
	private NoticeService noticeService;

	@Override
	public int insertNotice(NoticeVo noticeVo) {
		Notice notice=vo2poer.transfer(new Notice(),noticeVo);
		return noticeService.insertNotice(notice);
	}

	@Override
	public int updateNoticeByNid(NoticeVo noticeVo) {
		Notice notice=vo2poer.transfer(new Notice(),noticeVo);
		
		return noticeService.updateNoticeByNid(notice);
	}

	@Override
	public int deleteByNid(String nId) {
		return noticeService.deleteByNid(nId);
	}

	@Override
	public List<NoticeVo> findNoticeListPage(PageData pd) {
		return po2voer.transfer(NoticeVo.class,noticeService.findNoticeListPage(pd));
	}

	@Override
	public List<NoticeVo> findListNotice(PageData pd) {
		return po2voer.transfer(NoticeVo.class,noticeService.findListNotice(pd));
	}

	@Override
	public NoticeVo findNotice(PageData pd) {
		return po2voer.transfer(new NoticeVo(),noticeService.findNotice(pd));
	}

	@Override
	protected BaseService<Notice> getService() {
		return noticeService;
	}
	
}
