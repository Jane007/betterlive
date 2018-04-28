package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Notice;


/**
 * 公告管理 service 层
 * 2017-04-10 by chen
 */
public interface NoticeService extends BaseService<Notice> {
	
	int insertNotice(Notice notice);                  //增加公告
	
	int updateNoticeByNid(Notice notice);               //根据公告ID修改活动
	 
	int deleteByNid(String nId);                      //根据公告ID删除活动 

    List<Notice> findNoticeListPage(PageData pd);     //根据条件分页查询公告
    
    List<Notice> findListNotice(PageData pd);     	  //根据条件查询全部公告
    
    Notice findNotice(PageData pd);                   //根据条件查询单个公告详细
}
