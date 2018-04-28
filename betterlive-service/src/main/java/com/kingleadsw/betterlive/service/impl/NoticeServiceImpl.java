package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.NoticeMapper;
import com.kingleadsw.betterlive.model.Notice;
import com.kingleadsw.betterlive.service.NoticeService;




/**
 * 公告管理 service 实现层
 * 2017-04-10 by chen
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice>  implements NoticeService{
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public int insertNotice(Notice notice) {
		return noticeMapper.insertNotice(notice);
	}

	@Override
	public int updateNoticeByNid(Notice notice) {
		return noticeMapper.updateNoticeByNid(notice);
	}

	@Override
	public int deleteByNid(String nId) {
		return noticeMapper.deleteByNid(nId);
	}

	@Override
	public List<Notice> findNoticeListPage(PageData pd) {
		return noticeMapper.findNoticeListPage(pd);
	}

	@Override
	public List<Notice> findListNotice(PageData pd) {
		return noticeMapper.findListNotice(pd);
	}

	@Override
	public Notice findNotice(PageData pd) {
		return noticeMapper.findNotice(pd);
	}

	@Override
	protected BaseMapper<Notice> getBaseMapper() {
		return noticeMapper;
	}


}
