package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Notice;
import com.kingleadsw.betterlive.vo.NoticeVo;


/**
 * 公告管理 实际交互层
 * 2017-04-10 by chen
 */
public interface NoticeManager extends BaseManager<NoticeVo,Notice>{
    int insertNotice(NoticeVo noticeVo);                  //增加公告
	
	int updateNoticeByNid(NoticeVo noticeVo);             //根据公告ID修改活动
	 
	int deleteByNid(String nId);                       	  //根据公告ID删除活动 

    List<NoticeVo> findNoticeListPage(PageData pd);       //根据条件分页查询公告
    
    List<NoticeVo> findListNotice(PageData pd);     	  //根据条件查询全部公告
    
    NoticeVo findNotice(PageData pd);                     //根据条件查询单个公告详细
}
