package com.kingleadsw.betterlive.service;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialComment;


/**
 * 专题评论 service 层
 */
public interface SpecialCommentService extends BaseService<SpecialComment> {

	SpecialComment queryCommentByCommId(PageData pd);

	List<SpecialComment> queryCommentInfoListPage(PageData pd);

	int insertComment(SpecialComment comment);

	/**
	 * 审核
	 * @param map
	 * @return
	 */
	int updateReplyById(Map<String, String> map);
	
	/**
	 * 全部评论
	 * @param pd
	 * @return
	 */
	List<SpecialComment> queryCommentByTypeListPage(PageData pd);
	
	/**
	 * 查询单个评论的全部回复
	 * @param rootId
	 * @return
	 */
	
	List<SpecialComment> queryCommentByRootListPage(Integer rootId);
	
	/**
	 * 根据comment_id
	 * @param comment_id
	 * @return
	 */
	SpecialComment queryCommentById(Integer comment_id);
	
	/**
	 *  根据评论ID和专题ID删除评论
	 * @param pd
	 * @return
	 */
	int delCommentBycId(PageData pd);
	
	/**
	 * 全部视频评论
	 * @param pd
	 * @return
	 */
	List<SpecialComment> queryCommentByVideoListPage(PageData pd);

	SpecialComment queryCommentVideoById(Integer comment_id);
}
