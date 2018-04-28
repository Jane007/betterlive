package com.kingleadsw.betterlive.dao;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialComment;

public interface SpecialCommentMapper extends BaseMapper<SpecialComment>{

	SpecialComment queryCommentByCommId(PageData pd);
	
	List<SpecialComment> queryCommentInfoListPage(PageData pd);

	int insertComment(SpecialComment comment);
	
	/**
	 * 文章审核
	 * @param map
	 * @return
	 */
	int updateReplyById(Map<String, String> map);
	
	/**
	 * 评论全部分页
	 * @param pd
	 * @return
	 */
	List<SpecialComment> queryCommentByTypeListPage(PageData pd);
	
	/**
	 * 评论视频全部分页
	 * @param pd
	 * @return
	 */
	List<SpecialComment> queryCommentByVideoListPage(PageData pd);
	
	/**
	 * 查询单个ID的所有评论
	 * @param rootId
	 * @return
	 */
	List<SpecialComment> queryCommentByRootListPage(Integer rootId);
	
	/**
	 * 根据comment_id
	 */
	SpecialComment queryCommentById(Integer comment_id);

	/**
	 * 根据comment_id(視頻)
	 */
	SpecialComment queryCommentVideoById(Integer comment_id);
	
	/**根据评论ID和专题ID删除评论
	 * 
	 * @param pd
	 * @return
	 */
	int delCommentBycId(PageData pd); 
	
}
